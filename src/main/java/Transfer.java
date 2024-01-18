

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Transfer
 */
@WebServlet("/Transfer")
public class Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transfer() {
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
		long taccno=Long.parseLong(request.getParameter("taccno"));
		
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","andb","andb");
		
			PreparedStatement ps=con.prepareStatement("select amount from KriCE_bank where ACCOUNT_NUMBER=? and name=? and password=?");
			ps.setLong(1, accno);
			ps.setString(2, name);
			ps.setString(3, password);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				double mybal=rs.getDouble(1);
			out.print("<font color=blue>"+"<center><h3>My Original balance is "+mybal+"<P>");
			if(mybal>=amount)
			{
			
			
			out.print("Transfer amount is..."+amount+"rupees"+"<p>");
			PreparedStatement ps2=con.prepareStatement("select amount from KriCE_bank where ACCOUNT_NUMBER=?");
			ps2.setLong(1, taccno);
			ResultSet rs2=ps2.executeQuery();
			if(rs2.next()) {
			out.print("<font color=blue>"+"Target account balance is "+rs2.getDouble(1)+"<P>");
			}
			
			PreparedStatement ps3=con.prepareStatement("update KriCE_bank  set amount=amount - ? where ACCOUNT_NUMBER=? and name=? and password=?");
			ps3.setDouble(1, amount);
			ps3.setLong(2, accno);
			ps3.setString(3, name);
			ps3.setString(4, password);
			
			int i=ps3.executeUpdate();
			out.print(amount+" debited from your account "+"<P>");
			
			
			
			
			PreparedStatement ps4=con.prepareStatement("update KriCE_bank  set amount=amount + ? where ACCOUNT_NUMBER=?");
			ps4.setDouble(1, amount);
			ps4.setLong(2, taccno);
			
			int j=ps4.executeUpdate();	
			out.print(amount+" rupees is transfered successfully"+"<P>");
			
			PreparedStatement ps5=con.prepareStatement("select amount from KriCE_bank where ACCOUNT_NUMBER=?");
			ps5.setLong(1, taccno);
			ResultSet rs5=ps5.executeQuery();
		
			if(rs5.next()) {
			out.print("After transfer target account balance is "+rs5.getDouble(1)+"<P>");
			}

			
			PreparedStatement ps6=con.prepareStatement("select amount from KriCE_bank where ACCOUNT_NUMBER=? and name=? and password=?");
			ps6.setLong(1, accno);
			ps6.setString(2, name);
			ps6.setString(3, password);
			ResultSet rs6=ps6.executeQuery();
			if(rs6.next()) {
			out.print("After transfer my acount balance is "+rs6.getDouble(1));
			}
			con.close();
			
			}
			else {
				out.print("<h3>"+"Transaction unsuccessfull due to low balance...");
			}
			}
			
		}
		catch(Exception e){
			System.out.println(e);
		}
	
	
	}

}
