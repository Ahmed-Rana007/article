package com.gnl.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hbl.provisioning.ProvisioningAccounts;

import oracle.iam.platform.OIMClient;
import com.bea.httppubsub.json.JSONObject;
//import com.google.gson.JsonObject;
import weblogic.jdbc.wrapper.Array;
/**
 * Servlet implementation class changeApplicationPassword
 */
@WebServlet("/changeApplicationPassword")
public class changeApplicationPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
     public boolean isMatched;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public changeApplicationPassword() {
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

		  
         response.setContentType("application/json");
         response.setCharacterEncoding("utf-8");
         
		String newPassword = request.getParameter("newPass").trim().toString();
		String confPassword = request.getParameter("confPass").trim().toString();
		String appName = request.getParameter("appName").trim().toString();
		String appKey = request.getParameter("appKey").trim().toString();
		//
		ProvisioningAccounts pa = new ProvisioningAccounts();
		HttpSession session = request.getSession();
		String userLogin  = (String)session.getAttribute("username");
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		//
		
		//PrintWriter out ;
		System.out.println("New Password: " + newPassword+" "+appName);
		//out = response.getWriter();
		//out.print("User");
		 JSONObject jsonResponse = new JSONObject();
		 
		 if (newPassword.equals(confPassword)) {
	   
		 jsonResponse.put("status",  "true");
		   
		 PrintWriter out= response.getWriter();
         out.write(jsonResponse.toString());
         
     	try {
         pa.changeAccountPassword(userLogin, oimClient,appName,newPassword);
        // out.write("done");
         
    	}catch (Exception e) {
			e.printStackTrace();
	
	}
         
		 }else {
			 
			 
			 jsonResponse.put("status",  "false");
			   
			 PrintWriter out= response.getWriter();
	         out.write(jsonResponse.toString());
			 
		 }
	 
		/*	
		
		ProvisioningAccounts pa = new ProvisioningAccounts();
		HttpSession session = request.getSession();
		String userLogin  = (String)session.getAttribute("username");
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		
		
			
		try {
			 pa.changeAccountPassword(userLogin, oimClient,appName,newPassword);
				out.write("done"); 
				
			}catch (Exception e) {
				e.printStackTrace();
		
		}*/
		
		
	} 

}
