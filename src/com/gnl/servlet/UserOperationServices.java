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

import oracle.iam.platform.OIMClient;

/**
 * Servlet implementation class UserOperationServices
 */
@WebServlet("/UserOperationServices")
public class UserOperationServices extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserOperationServices() {
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
		 
		String userKey = request.getParameter("userkeytodisbale").trim().toString();
		
		String action = request.getParameter("action").trim().toString();
		PrintWriter out = response.getWriter();
		System.out.println(userKey+""+action);
		
		ProvisioningAccounts pa = new ProvisioningAccounts();
		HttpSession session = request.getSession();
		//String userLogin  = (String)session.getAttribute("username");
	
		//OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		 JSONObject jsonResponse = new JSONObject();
		 
		if (action.equals("disable")) {
		boolean disableStatus = pa.disableUser(userKey);
		
		 jsonResponse.put("STATUS", disableStatus);
		 out.write(jsonResponse.toString());
		
        
		//out.write(DisableRequestId); 
		
		}else {
			
			boolean enableStatus = pa.enableUser(userKey);
			
			 jsonResponse.put("STATUS", enableStatus);
			 out.write(jsonResponse.toString());
			
		} 
	} 

		
	   
}
