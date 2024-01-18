

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
 * Servlet implementation class Closeaccount
 */
@WebServlet("/Closeaccount")
public class Closeaccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Closeaccount() {
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
		
			PreparedStatement ps=con.prepareStatement("create trigger prevent_update_on_specific_row before update on KriCE_bank for each row bigin if new.account_number=? then signal sqlstate '45000'"
					+ "set message_text='No upadate.' end if; end;");
			ps.setLong(1, accno);
			
		//int i=ps.executeUpdate();
			
			out.print("<center><h2>Your account is  deactivated...<P>");
			
		}
		
		catch (Exception e) {
			System.out.println(e);
		}
	
	
		
		
	}

}
