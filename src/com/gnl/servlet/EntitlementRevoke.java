package com.gnl.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hbl.provisioning.ProvisioningAccounts;
import com.bea.httppubsub.json.JSONObject;
//import com.google.gson.JsonObject;
import weblogic.jdbc.wrapper.Array;

import oracle.iam.platform.OIMClient;

/**
 * Servlet implementation class EntitlementRevoke
 */
@WebServlet("/EntitlementRevoke")
public class EntitlementRevoke extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EntitlementRevoke() {
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
         String RequestId =null;
		String entName = request.getParameter("entName").trim().toString();
		String entKey = request.getParameter("entKey").trim().toString();
		ProvisioningAccounts pa = new ProvisioningAccounts();
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userLogin  = (String)session.getAttribute("username");
		String affected_user  = request.getParameter("affected_user");
		String action  = request.getParameter("action");
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		System.out.println("appName: " + entName +" appKey: "+entKey+ " Action: "+action+" User "+affected_user);
		
		
		JSONObject jsonResponse = new JSONObject();
		if(action.equals("revokeEntitlement")) {
		
		
		 RequestId = pa.revokeEntitlementOfUser(oimClient, entName, entKey, userLogin);
		
		 jsonResponse.put("ID",RequestId);
		 jsonResponse.put("EntName",entName);
		   
		
         out.write(jsonResponse.toString());
         //out.write(RevokeRequestId);
		}
		else if(action.equals("AddADGroup"))
		{
			System.out.println("Add group function called");
			 RequestId = pa.AddEntitlementOfUser(oimClient, entName, entKey, affected_user);
				
			 jsonResponse.put("ID",RequestId);
			 jsonResponse.put("EntName",entName);
			   
			
	         out.write(jsonResponse.toString());
			
		}
		else if(action.equals("RemoveADGroup"))
		{
			System.out.println("RemoveADGroup function called");
			 RequestId = pa.revokeEntitlementOfUser(oimClient, entName, entKey, affected_user);
				
			 jsonResponse.put("ID",RequestId);
			 jsonResponse.put("EntName",entName);
			   
			
	         out.write(jsonResponse.toString());
			
		}
	}
	
	

}
