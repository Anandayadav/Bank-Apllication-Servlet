

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Deposit
 */
@WebServlet("/Deposit")
public class Deposit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deposit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		long accno=Long.parseLong(request.getParameter("accno"));
		String name=request.getParameter("name");
		String password=request.getParameter("pswrd");
		double amount=Double.parseDouble(request.getParameter("amount"));
		
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","andb","andb");
		
			PreparedStatement ps=con.prepareStatement("select account_number,name,amount from KriCE_bank where ACCOUNT_NUMBER=? and name=? and password=?");
			ps.setLong(1, accno);
			ps.setString(2, name);
			ps.setString(3, password);
			
			ResultSet rs=ps.executeQuery();
			
			PreparedStatement ps1=con.prepareStatement("update KriCE_bank  set amount=amount+? where ACCOUNT_NUMBER=?  and name=? and password=?");
			ps1.setDouble(1, amount);
			ps1.setLong(2, accno);
			ps1.setString(3, name);
			ps1.setString(4, password);
			int i=ps1.executeUpdate();
			out.print("<center>");
			out.println("<h3>"+"Amount deposited successfully");

			PreparedStatement ps2=con.prepareStatement("select account_number,name, amount from KriCE_bank where ACCOUNT_NUMBER=? and name=? and password=?");
			ps2.setLong(1, accno);
			ps2.setString(2, name);
			ps2.setString(3, password);
			
			ResultSet rs2=ps2.executeQuery();
			
			ResultSetMetaData rsmd=rs2.getMetaData();
			int n=rsmd.getColumnCount();
			
			out.print("<h3>Before depositing...</h3>");

			out.print("<table border='1'>");
			out.print("<tr>");
			for (int i1 = 1; i1 <=n; i1++) {
				out.print("<td><font color=red>"+rsmd.getColumnName(i1)+"</td>");
			}
			out.print("</tr>");
			
			if(rs.next()) {
				out.print("<tr>");
				for (int i1 = 1; i1 <=n; i1++) {
					out.print("<td>"+rs.getObject(i1)+"</td>");
				}
			}
			out.print("</table>");
			
			
			out.print("<h3>After depositing..."+amount+"rupees");
			
			out.print("<table border='1'>");
			out.print("<tr>");
			for (int j = 1; j <=n; j++) {
				out.print("<td><font color=red>"+rsmd.getColumnName(j)+"</td>");
			}
				out.print("</tr>");
				
				
				if(rs2.next()) {
					out.print("<tr>");
					for (int i1 = 1; i1 <=n; i1++) {
						out.print("<td>"+rs2.getObject(i1)+"</td>");
					}
			}
				out.print("</table>");

				
			out.print("</tr>");
			out.print("</center>");
		}
		catch(Exception e){
			System.out.println(e);
		}
	
	}

}
