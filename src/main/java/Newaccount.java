
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Newaccount
 */
@WebServlet("/Newaccount")
public class Newaccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Newaccount() {
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
		String conpassword=request.getParameter("conpswrd");
		double amount=Double.parseDouble(request.getParameter("amount"));
		String address=request.getParameter("addr");
		long mobile_number=Long.parseLong(request.getParameter("phno"));
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","andb","andb");
			PreparedStatement ps=con.prepareStatement("insert into KriCE_bank values(?,?,?,?,?,?)");
			ps.setLong(1, accno);
			ps.setString(2, name);
			ps.setString(3, password);
			ps.setDouble(4, amount);
			ps.setString(5, address);
			ps.setLong(6, mobile_number);
			 
			if (password.equals(conpassword)) {
				int i=ps.executeUpdate();
				out.print("<center> <b>"+i+"Hello "+name+",  Your account created successfully");		
			}
			else
			{
				out.print("<center><B><h3>"+"Enter the right confirm password");
			}
		}
		
		catch(Exception e){
			out.print(e);
			out.print("Please check the inputs you provided once");
			}
	}

}
