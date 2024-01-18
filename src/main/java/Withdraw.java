

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
 * Servlet implementation class Withdraw
 */
@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Withdraw() {
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
			
			ResultSetMetaData rsmd=rs.getMetaData();
			int n=rsmd.getColumnCount();
			out.print("<center>");
			out.print("<h2>Before Withdrawal...</h2>");
			out.print("<table border='1'>");
			out.print("<tr>");
			for (int j = 1; j <=n; j++) {
				out.print("<td><font color=red>"+rsmd.getColumnName(j)+"</td>");
			}
			out.print("</tr>");
			
			if(rs.next()) {
				out.print("<tr>");
				for (int k = 1; k <=n; k++) {
					out.print("<td>"+rs.getString(k)+"</td>");
					if(k==n)
					{
						double bal=rs.getDouble(n);
						if(bal<amount) {
							out.print("<h3>"+"Your account balance is low  "+"<p>");
							out.print("<h3>"+"Transaction unsuccesfull ... ");
						}
						else {
							out.println("<h3>"+"You have withdrawn amount successfully");
				
			PreparedStatement ps1=con.prepareStatement("update KriCE_bank  set amount=amount-? where ACCOUNT_NUMBER=?  and name=? and password=?");
			ps1.setDouble(1, amount);
			ps1.setLong(2, accno);
			ps1.setString(3, name);
			ps1.setString(4, password);
			int i=ps1.executeUpdate();	
			

			PreparedStatement ps2=con.prepareStatement("select account_number,name, amount from KriCE_bank where ACCOUNT_NUMBER=? and name=? and password=?");
			ps2.setLong(1, accno);
			ps2.setString(2, name);
			ps2.setString(3, password);
			
			ResultSet rs2=ps2.executeQuery();
			
			

			out.print("</table>");
			out.print("<h2>After Withdrawal..."+amount+"rupees");
			out.print("<center>");
			out.print("<table border='1'>");
			out.print("<tr>");
			for (int j = 1; j <=n; j++) {
				out.print("<td><font color=red>"+rsmd.getColumnName(j)+"</td>");
			}
				out.print("</tr>");
				if(rs2.next()) {
					out.print("<tr>");
					for (int k1 = 1; k1 <=n; k1++) {
						out.print("<td>"+rs2.getObject(k1)+"</td>");
					}
					out.print("</center>");
			}
						}
					}
				}
			}
				out.print("</table>");

			out.print("</tr>");
			
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

}
