package soa;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bea.httppubsub.json.JSONObject;
import com.hbl.selfservice.OIMUtils;

import oracle.iam.platform.OIMClient;

/**
 * Servlet implementation class OTP_Application_Servlet
 */
@WebServlet("/OTP_Application_Servlet")
public class OTP_Application_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public OTP_Application_Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		System.out.println("OTP_Application_Servlet Called");
		
		response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonResponse = new JSONObject();
		
		
		
		
		HttpSession session = request.getSession();
		
		String userLogin  = (String)session.getAttribute("username");
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		String result=null;
		OIMUtils oimUtils = new OIMUtils();
		try {
			 
			HashMap<String,Object> userDetail=oimUtils.getUserDetailByLogin(userLogin, oimClient);
			String mobile = null;
			if(userDetail.get("Mobile").toString() != null) {
				mobile = userDetail.get("Mobile").toString();
			}
			mobile = userDetail.get("Mobile").toString();
			System.out.println(userLogin); 	
			System.out.println(mobile);
			OTP_DB_Connection DB =new OTP_DB_Connection();
			result=DB.insetOTP_with_valid(userLogin,mobile);
			if(result.equals("Successfull"))
			{
				jsonResponse.put("Status", "Successfull");
				
				request.setAttribute("Status","");
				session.setAttribute("ChangePassword","true");
				
				System.out.println("Passwrd Status: "+request.getAttribute("ChangePassword"));
				
				System.out.println("Otp_True");
				/*RequestDispatcher dispatcher = getServletContext()
		                .getRequestDispatcher("/WEB-INF/views/otp.jsp");
		        dispatcher.forward(request, response);*/
				//request.getRequestDispatcher("/WEB-INF/views/otp.jsp").forward(request, response);
		        System.out.println("Otp_True end");
			}
			else if(result.equals("AlreadyGenerated"))
			{
				
				jsonResponse.put("Status", "AlreadyGenerated");
				session.setAttribute("ChangePassword","true");
				request.setAttribute("Status","OTP Has already Generated. Please Use OLD one or try after 2 Minutes");
				/*RequestDispatcher dispatcher = getServletContext()
		                .getRequestDispatcher("/WEB-INF/views/otp.jsp");
		        dispatcher.forward(request, response);*/
				System.out.println("Otp_false");
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.print(jsonResponse.toString());
	}

}
