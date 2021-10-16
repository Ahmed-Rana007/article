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
import com.hbl.selfservice.OIMUtils;

import oracle.iam.platform.OIMClient;

/**
 * Servlet implementation class ModifyAccountDetails
 */
@WebServlet("/ModifyAccountDetails")
public class ModifyAccountDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyAccountDetails() {
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
	      
	      
		String firstName = request.getParameter("firstName").trim().toString();
		String Organization = request.getParameter("Organization").trim().toString();
		String lastName = request.getParameter("lastName").trim().toString();
		String fullName = request.getParameter("fullName").trim().toString();
		String email = request.getParameter("email").trim().toString();
		String branchCode = request.getParameter("branchCode").trim().toString();
		String branchAddress = request.getParameter("branchAddress").trim().toString();
		String personalNum = request.getParameter("personalNum").trim().toString();
		String CommonName = request.getParameter("CommonName").trim().toString();
		String mobileNum = request.getParameter("mobileNum").trim().toString();
		String userlogin = request.getParameter("userlogin").trim().toString();
		String lineManager = request.getParameter("lineManager").trim().toString();
		String middleName = request.getParameter("middleName").trim().toString();
		HttpSession session = request.getSession();
		String userLogin  = (String)session.getAttribute("username");
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		OIMUtils oimu = new OIMUtils();
		JSONObject jsonResponse = new JSONObject();
		PrintWriter out = response.getWriter();
		
		ProvisioningAccounts pa = new ProvisioningAccounts();
		pa.modifyUser(oimClient, userLogin);
		
		System.out.print("method called");
		
		
		
		
			
	
		/*	try {
				
				String userKey = oimu.getUserKey(userlogin, oimClient);
			//	System.out.println(userKey);
				//System.out.println(userlogin);
				
		  UserOperationModifyAcc.modifyAccDetails(userlogin,userKey,firstName, lineManager, middleName, Organization, lastName, fullName, email, branchCode, branchAddress, personalNum, CommonName, mobileNum);
			
			//System.out.println("method called");
			jsonResponse.put("RESPONSE","Updated");
			out.write(jsonResponse.toString());
			
			
			}catch(Exception e) {
			
			
			e.printStackTrace();
		} */
			


	}

}
