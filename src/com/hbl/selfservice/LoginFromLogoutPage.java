package com.hbl.selfservice;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.iam.platform.OIMClient;

/**
 * Servlet implementation class LoginFromLogoutPage
 */
@WebServlet("/LoginFromLogoutPage")
public class LoginFromLogoutPage extends HttpServlet {
	
	LoginToOIM logInOIM =null;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginFromLogoutPage() {
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
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		 
		
		String userLogin = request.getParameter("userLogin").trim();
		String userPassword = request.getParameter("userPassword").trim();
		
		System.out.print(userLogin+" "+userPassword);
		
		
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: "+userLogin).append(request.getContextPath());
		if(userLogin.equals(null) || userPassword.equals(null)){  
	           request.setAttribute("msg", "please input the student_id or password!");
	           request.getRequestDispatcher("index.jsp").forward(request, response);  
	           return;  
	    } 
		else if(userLogin !=null && userPassword !=null)
		{
			
			logInOIM = new LoginToOIM();
			try {
				OIMClient oimClient  = logInOIM.loggedIntoOIM(userLogin, userPassword);
				
				OIMUtils oimUtils = new OIMUtils();
				String userKey = oimUtils.getUserKey(userLogin.toString(), oimClient);
				OIMUser oimUser = oimUtils.getUserDetails(userLogin, oimClient);
				
				if(oimClient !=null)
				{
					UserSessaion.oimClient  = oimClient;
					UserSessaion.loggedInUserLogin = userLogin;
					UserSessaion.setOimUser(oimUser);
					HttpSession  session=request.getSession();
					session.setAttribute("username", userLogin);
					session.setAttribute("oimClient",oimClient);
					session.setAttribute("oimUser", oimUser);
					//request.setAttribute("msg", "Successfully Logged IN" + userKey + "FName: "+ oimUser.getUserFirstName() + " LastName:  "+ oimUser.getUserLastName());
					request.setAttribute("msg",  oimUser.getUserFirstName().toUpperCase() + " "+ oimUser.getUserLastName().toUpperCase());
			        request.getRequestDispatcher("HomePage.jsp").forward(request, response); 
				}
				else
				{
					
				}
				
			}catch(Exception ex)
			{
			   // request.setAttribute("msg","Username or password invalid");
				//request.getRequestDispatcher("index.jsp").forward(request, response); 
			}
			
			
		}
		
		
		
		
	} 

}
