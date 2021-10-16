package com.gnl.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hbl.selfservice.UserSessaion;

import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.vo.UserManagerResult;

import com.bea.httppubsub.json.JSONObject;
import com.hbl.provisioning.ProvisioningAccounts;
import oracle.iam.platform.OIMClient;


/**
 * Servlet implementation class UserOperationsController
 */
@WebServlet("/UserOperationsController")
public class UserOperationsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserOperationsController() {
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
		response.setContentType("text/plain");
		
		HttpSession session = request.getSession();
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		String userLogin  = (String)session.getAttribute("username");
	
		
		
		response.setContentType("application/json");
		 response.setCharacterEncoding("utf-8");
		 PrintWriter out = response.getWriter();
		 JSONObject jsonResponse = new JSONObject();
		 ProvisioningAccounts pa = new ProvisioningAccounts();
		String action = request.getParameter("action").trim().toString();
		//PrintWriter out = response.getWriter();
		if(action.equals("changePass"))
		{
			String newPassword = request.getParameter("newPassword");
			String confirmPassword = request.getParameter("confirmPassword");
			System.out.print(newPassword);
			if(confirmPassword.equals(newPassword))
			{
				boolean isChanged = pa.changePassowrd( newPassword,oimClient);
				System.out.print(isChanged);
				   jsonResponse.put("ID", isChanged);
					out.write(jsonResponse.toString());
					
			}
			else
			{
				out.print(" New passowrd and confirm password not matched!");
			}
		}
		else {
			String userkeytodisbale = request.getParameter("userkeytodisbale");
			System.out.println(userkeytodisbale);
			
			if(action.equals("enable"))
			{
				boolean isEnabled = enableUser(userkeytodisbale);
				if(isEnabled)
				{
					out.print("User "+userkeytodisbale +" enabled successfully!");
					
				}else {
					 
					out.print("User "+userkeytodisbale +" not enabled.");
					
				}
			}
			else if(action.equals("disable"))
			{
				boolean isDisabled = disableUser(userkeytodisbale);
				System.out.print(isDisabled);
				if(isDisabled)
				{
					out.print("User "+userkeytodisbale +" disabled successfully!");
				}else {
					 
					out.print("User "+userkeytodisbale +" not disabled.");
				}
				
			}
		else if(action.equals("lockUser"))
			{
				boolean isLocked = lockUser(userkeytodisbale);
				if(isLocked)
				{
					out.print("User "+userkeytodisbale +" locked successfully!");
				}else {
					 
					out.print("User "+userkeytodisbale +" not locked.");
				}
			}
			else if(action.equals("unLockUser"))
			{
				boolean isUnLocked = unlockUser(userkeytodisbale);
				if(isUnLocked)
				{
					out.print("User "+userkeytodisbale +" unlcocked successfully!");
				}else {
					 
					out.print("User "+userkeytodisbale +" not unlcocked.");
				}
			}
		}
		
		
		
	
	}
	
	public boolean disableUser(String userId) { //Function to disable user
		 UserManager userManager =null;
	        try {
	        	userManager = UserSessaion.getOimClient().getService(UserManager.class);
	            userManager.disable(userId, true);
	            System.out.println("\n Disabled user Successfully");
	            return true;
	            
	        } catch (Exception e) {
	           System.out.println("Kashif: "+e.getMessage().toString());
	        }  
	        return false;
	    }
	public boolean enableUser(String userId) { //Function to disable user
		 UserManager userManager =null;
	        try {
	        	userManager = UserSessaion.getOimClient().getService(UserManager.class);
	            userManager.enable(userId, true);
	            System.out.println("\n Enable user Successfully");
	           
	            return true;
	            
	            
	        } catch (Exception e) {
	           System.out.println("Kashif: "+e.getMessage().toString());
	        }  
	        return false;
	    }
	public boolean lockUser(String userId) { //Function to disable user
		 UserManager userManager =null;
	        try {
	        	userManager = UserSessaion.getOimClient().getService(UserManager.class);
	            userManager.lock(userId, true);
	            System.out.println("\n user locked Successfully");
	            return true;
	            
	        } catch (Exception e) {
	           System.out.println("Kashif: "+e.getMessage().toString());
	        }  
	        return false;
	    }
	public boolean unlockUser(String userId) { //Function to disable user
		 UserManager userManager =null;
	        try {
	        	userManager = UserSessaion.getOimClient().getService(UserManager.class);
	            userManager.unlock(userId, true);
	            System.out.println("\n user unlocked Successfully");
	            return true;
	            
	        } catch (Exception e) {
	           System.out.println("Kashif: "+e.getMessage().toString());
	        }  
	        return false;
	    }
	public boolean changePassowrd(String newPassword) { //Function to disable user
		
		 UserManager userManager =null;
	        try {
	        	
	        	userManager = UserSessaion.getOimClient().getService(UserManager.class);
	        	 userManager.changePassword(UserSessaion.getLoggedInUserLogin(), newPassword.toCharArray(), true,
                         null, false);
	            System.out.println("Password Successfully");
	            return true;
	            
	        } catch (Exception e) {
	           System.out.println("Kashif: "+e.getMessage().toString());
	           
	  		 
	  		  
	          
	        }  
	        return false;
	    }
	
	

}
