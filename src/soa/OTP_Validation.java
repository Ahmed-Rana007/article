package soa;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bea.httppubsub.json.JSONObject;
import com.hbl.equation.limits.DatabaseConnection;
import com.hbl.selfservice.OIMUtils;
import com.hbl.selfservice.UserSessaion;

/**
 * Servlet implementation class OTP_Validation
 */
@WebServlet("/OTP_Validation")
public class OTP_Validation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OTP_Validation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String Field_Otp = request.getParameter("otp");
		System.out.println("OTP From Field: " + Field_Otp);
	
		HttpSession session = request.getSession();
		String userLogin  = (String)session.getAttribute("username");
		
		
		DatabaseConnection dbobj = new DatabaseConnection();
		Connection con = dbobj.connectMSCDB();
		PreparedStatement stmt= null;
		ResultSet rs =null;
		try { 
			String sql = "select OTP from OTP_PWD where CREATED between (SYSDATE -1/720) and (SYSDATE) and STATUS = 'Active' and USR_LOGIN =  '"+userLogin+"'";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			JSONObject jsonResponse = new JSONObject();
			jsonResponse.put("qq", "False");
			while(rs.next())
			{
			
			if(rs.getString("OTP").equals(Field_Otp)) {
				System.out.println("Validating OTP With DataBase:");
				
				
				jsonResponse.put("qq", "True");
				out.print(jsonResponse.toString());
				
				System.out.println("Otp_True");
				//request.getRequestDispatcher("/WEB-INF/views/ChangePassword.jsp").forward(request, response);
				
				
				}
			}
			if(jsonResponse.getString("qq").equals("False")) {
				
			System.out.println("OTP Not Match From DB:");
			jsonResponse.put("STATUS", "False");
			System.out.println("Otp_False");
			out.write(jsonResponse.toString());
			}
			//request.getRequestDispatcher("/WEB-INF/views/otp.jsp").forward(request, response);
			
		}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		finally {
		    if (stmt != null) {
		        try {
		        	System.out.println("Statement close: " + con.isClosed());
		        	System.out.println("Statement close: " + con.isClosed());
		        	System.out.println("Statement close: " + stmt);
		        	stmt.close();
		        	System.out.println("Statement close end: " + stmt);
		        	System.out.println("Statement close: " + stmt.isClosed());
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (rs != null) {
		        try {
		        	System.out.println("Statement close: " + rs.isClosed());
		        	System.out.println("Statement close: " + rs.isClosed());
		        	System.out.println("Statement close: " + rs);
		        	rs.close();
		        	System.out.println("Statement close end: " + rs);
		        	System.out.println("Statement close: " + rs.isClosed());
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (con != null) {
		        try {
		        	System.out.println("Statement close: " + con);
		            con.close();
		            System.out.println("Statement close: " + con.isClosed());
		            
		            System.out.println("Statement end: " + con);
		        } catch (SQLException e) { /* Ignored */}
		    }
		}
		
	}

}
