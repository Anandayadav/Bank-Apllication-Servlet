

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
 * Servlet implementation class Balance
 */
@WebServlet("/Balance")
public class Balance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Balance() {
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

		
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","andb","andb");
			PreparedStatement ps=con.prepareStatement("select * from KriCE_bank where ACCOUNT_NUMBER=? and name=? and password=?");
			ps.setLong(1, accno);
			ps.setString(2, name);
			ps.setString(3, password);
			
			ResultSet rs=ps.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			int n=rsmd.getColumnCount();
			out.print("<center>");
			out.print("<table border='1'>");
			out.print("<tr>");
			
			if(rs.next()) {
				
				out.print("<b><h3>"+"Your account details...");
			for (int i = 1; i <=n; i++) {
				out.print("<td><font color=red>"+rsmd.getColumnName(i)+"</td>");
				}
			out.print("</tr>");
			
			out.print("<tr>");
				for (int i = 1; i <=n; i++) {
					out.print("<td>"+rs.getObject(i)+"</td>");
					}
				
				}
			
			
			else {
					out.print("<b><h3>"+"No such account found...");
				}
		
			
			out.print("</tr>");
			out.print("</center>");
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

}
