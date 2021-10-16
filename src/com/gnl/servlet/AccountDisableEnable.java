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
 * Servlet implementation class AccountDisableEnable
 */
@WebServlet("/AccountDisableEnable")
public class AccountDisableEnable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountDisableEnable() {
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
		
	     response.setContentType("application/json");
         response.setCharacterEncoding("utf-8");
		 
		String appName = request.getParameter("appName").trim().toString();
		String appKey = request.getParameter("appKey").trim().toString();
		String action = request.getParameter("action").trim().toString();
		String justifyNotes = request.getParameter("justification").trim().toString();
		String userLogin = request.getParameter("userlogin").trim().toString();
		String userjustification = "User Login: "+userLogin+"<br>Reason : "+justifyNotes;
		
		PrintWriter out = response.getWriter();
		System.out.println(userjustification);
	//	System.out.println("appName: " + appName +" appKey: "+appKey+" action "+ justifyNotes+" "+userLogin);
		ProvisioningAccounts pa = new ProvisioningAccounts();
		HttpSession session = request.getSession();
		//String userLogin  = (String)session.getAttribute("username");
	
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		 JSONObject jsonResponse = new JSONObject();
		 
		if (action.equals("acctDisable")) {
								
							//disable user account with Request
	//		String DisableRequestId = pa.disableAccountOfUser(oimClient, appName, appKey, userLogin,userjustification);
		
			//disable user account without Request
			String Response = pa.disableAccount(oimClient,appKey,appName, userLogin);
			
		jsonResponse.put("ID1", "ID!");
		 jsonResponse.put("ID", Response);
		 
		 out.write(jsonResponse.toString());
		
         
		//out.write(DisableRequestId); 
		
		}else {
			System.out.print("Enabled");
			String EnableRequestId = pa.enableAccountOfUser(oimClient, appName, appKey, userLogin,userjustification);
			
			jsonResponse.put("ID1", "ID2");
			 jsonResponse.put("ID", EnableRequestId);
			 
			 out.write(jsonResponse.toString());
			
		} 
	} 

}
