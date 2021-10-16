package com.gnl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.python.core.exceptions;

import com.bea.httppubsub.json.JSONObject;
import com.hbl.approvalrequests.CustomRequest;
import com.hbl.provisioning.ProvisioningAccounts;
import oracle.iam.platform.OIMClient;
import com.hbl.selfservice.OIMUtils;
import com.hbl.provisioning.UserOperationModifyAcc;
/**
 * Servlet implementation class LeaveManagement
 */
@WebServlet("/LeaveManagement")
public class LeaveManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeaveManagement() {
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
	         
		String startDate = request.getParameter("startDate").trim().toString();
		String endDate = request.getParameter("endDate").trim().toString();
		String leaveType = request.getParameter("leaveType").trim().toString();
		  
		HttpSession session = request.getSession();
		String userLogin  = (String)session.getAttribute("username");
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		OIMUtils oimu = new OIMUtils();
		CustomRequest cr = new CustomRequest();
		PrintWriter out = response.getWriter();
		JSONObject jsonResponse = new JSONObject();
		
		System.out.print(startDate+" "+endDate);
		
		
		try {
		String userKey = oimu.getUserKey(userLogin, oimClient);
		System.out.println(userKey);
		System.out.println(userLogin);
		
	String requestID =	cr.modifyUserRequest(oimClient, userKey, userLogin, startDate, endDate, leaveType);
		
		System.out.println(requestID);
		
		jsonResponse.put("ResultID",requestID);
		out.write(jsonResponse.toString());
		
		
		}catch(Exception e) {
		
		
		e.printStackTrace();
	}
		
		

} 
	
}
