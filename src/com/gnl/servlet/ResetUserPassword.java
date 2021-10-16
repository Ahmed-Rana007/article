package com.gnl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gnl.servlet.*;
import com.hbl.provisioning.ProvisioningAccounts;
import com.hbl.selfservice.OIMUtils;
import com.bea.httppubsub.json.JSONObject;
import oracle.iam.platform.OIMClient;
import soa.SendSMS;
/**
 * Servlet implementation class ResetUserPassword
 */
@WebServlet("/ResetUserPassword")
public class ResetUserPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetUserPassword() {
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
         HttpSession session = request.getSession();
         PrintWriter out = response.getWriter();
         OIMUtils oimUtils = new OIMUtils();
         JSONObject jsonResponse = new JSONObject();
		
		String userlogin = request.getParameter("userlogin").trim().toString();
		
		System.out.print(userlogin);
		
		ProvisioningAccounts pa = new ProvisioningAccounts();
		SendSMS soa = new SendSMS();
		PasswordGenerator passgen = new PasswordGenerator();
		String password = passgen.generateRandomPassword();
		System.out.println(password);
		String responseFlag = pa.resetPassword(userlogin,password);
		System.out.print("Done");
		System.out.print(responseFlag);
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		if(responseFlag == "changed") {
			
			try {
				HashMap<String, Object> userDetail;
				userDetail = oimUtils.getUserDetailByLogin(userlogin, oimClient);
				String mobile = userDetail.get("Mobile").toString();
				soa.sendSMStoReciever(mobile,"Your New Changed Password: ",password);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		jsonResponse.put("RESPONSE",responseFlag);
		out.write(jsonResponse.toString());
	}

}
