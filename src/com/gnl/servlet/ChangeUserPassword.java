package com.gnl.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bea.httppubsub.json.JSONObject;
import com.hbl.provisioning.ProvisioningAccounts;
import com.hbl.provisioning.UserOperationModifyAcc;

import oracle.iam.platform.OIMClient;

/**
 * Servlet implementation class ChangeUserPassword
 */
@WebServlet("/ChangeUserPassword")
public class ChangeUserPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeUserPassword() {
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
		String action= request.getParameter("action");
		if(action.equals("manualPassword"))
		{
			String status="";
			//String curPassword = request.getParameter("curPassword");
			String newPassword = request.getParameter("newPassword");
			String confirmPassword = request.getParameter("confirmPassword");
			
			String userLogin  = request.getParameter("targetUser");
			
			System.out.println(newPassword+" "+ confirmPassword+" "+userLogin);

			response.setContentType("application/json");
			 response.setCharacterEncoding("utf-8");
			 
			 PrintWriter out = response.getWriter();
			 JSONObject jsonResponse = new JSONObject();
			 UserOperationModifyAcc uoma = new UserOperationModifyAcc();
			//PrintWriter out = response.getWriter();
			
				
				System.out.print(newPassword);
				if(confirmPassword.equals(newPassword))
				{
					status = uoma.changePassword(userLogin, newPassword);
					System.out.print(status);
					   jsonResponse.put("STATUS", status);
						out.write(jsonResponse.toString());
						
				}
				else
				{
					jsonResponse.put("notMatched", "Y");
					out.write(jsonResponse.toString());
					
				}

			
		}
		else if(action.equals("bySelf")) {
		String status="";
		//String curPassword = request.getParameter("curPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		
		HttpSession session = request.getSession();
		
		String userLogin  = (String)session.getAttribute("username");

		response.setContentType("application/json");
		 response.setCharacterEncoding("utf-8");
		 
		 PrintWriter out = response.getWriter();
		 JSONObject jsonResponse = new JSONObject();
		 UserOperationModifyAcc uoma = new UserOperationModifyAcc();
		//PrintWriter out = response.getWriter();
		
			
			System.out.print(newPassword);
			if(confirmPassword.equals(newPassword))
			{
				status = uoma.changePassword(userLogin, newPassword);
				System.out.print(status);
				   jsonResponse.put("STATUS", status);
					out.write(jsonResponse.toString());
					
			}
			else
			{
				jsonResponse.put("notMatched", "Y");
				out.write(jsonResponse.toString());
				
			}
		}
	}

}
