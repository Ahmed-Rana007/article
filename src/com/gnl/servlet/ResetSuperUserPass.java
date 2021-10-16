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
import com.hbl.approvalrequests.CustomRequest;
import com.hbl.selfservice.OIMUtils;

import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.vo.UserManagerResult;
import oracle.iam.platform.OIMClient;

/**
 * Servlet implementation class ResetSuperUserPass
 */
@WebServlet("/ResetSuperUserPass")
public class ResetSuperUserPass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetSuperUserPass() {
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
		
		System.out.print("Super Pssword Called");
		String action = request.getParameter("action").trim().toString();
		if (action.equals("UserEnable")) {
			
			System.out.println("EnableDisable Called");
			response.setContentType("application/json");
		      response.setCharacterEncoding("utf-8");
		         
			
		    String comments = request.getParameter("superUsername").trim().toString();
			String userLogintoEnable = request.getParameter("userLogintoEnable").trim().toString();
			
			
			  
			HttpSession session = request.getSession();
			String userLogin  = (String)session.getAttribute("username");
			OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
			OIMUtils oimu = new OIMUtils();
			CustomRequest cr = new CustomRequest();
			PrintWriter out = response.getWriter();
			JSONObject jsonResponse = new JSONObject();
			
			System.out.print("Comments: "+comments);
			
			
			try {
			String userKey = oimu.getUserKey(userLogintoEnable, oimClient);
			System.out.println("UserKey"+userKey);
			System.out.println("UserLoginToEnable:"+userLogintoEnable);
			System.out.println("EnableDisable Function called");	
		String requestID =	cr.enableUser(oimClient, userKey, userLogintoEnable, comments);
			
			System.out.println(requestID);
			
			jsonResponse.put("ResultID",requestID);
			out.write(jsonResponse.toString());
			
			
			}catch(Exception e) {
			
			
			e.printStackTrace();
		}
			

			
		}
		else if (action.equals("UserDisable")) {
			
			System.out.println("EnableDisable Called");
			response.setContentType("application/json");
		      response.setCharacterEncoding("utf-8");
		         
			
		    String comments = request.getParameter("disableUserReason").trim().toString();
			String userLogintoDisable = request.getParameter("userLogintoDisable").trim().toString();
			
			
			  
			HttpSession session = request.getSession();
			String userLogin  = (String)session.getAttribute("username");
			OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
			OIMUtils oimu = new OIMUtils();
			CustomRequest cr = new CustomRequest();
			PrintWriter out = response.getWriter();
			JSONObject jsonResponse = new JSONObject();
			
			System.out.print("Comments: "+comments);
			
			String requestID=null;	
			try {
			String userKey = oimu.getUserKey(userLogintoDisable, oimClient);
			System.out.println("UserKey"+userKey);
			System.out.println("UserLoginToEnable:"+userLogintoDisable);
			System.out.println("EnableDisable Function called");	
		 requestID =	cr.disableUser(oimClient, userKey, userLogintoDisable, comments);
		
		
			System.out.println(requestID);
			
			jsonResponse.put("ResultID",requestID);
			out.write(jsonResponse.toString());
			
			
			}catch(Exception e) {
			
			
			e.printStackTrace();
			requestID=e.getMessage();
			jsonResponse.put("ResultID",requestID);
			out.write(jsonResponse.toString());
		}
			

			
		}

		else if (action.equals("superPasswordReset"))
		{
		response.setContentType("application/json");
	      response.setCharacterEncoding("utf-8");
	         
		String superUsername = request.getParameter("superUsername").trim().toString();

		  
		HttpSession session = request.getSession();
		String userLogin  = (String)session.getAttribute("username");
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		OIMUtils oimu = new OIMUtils();
		CustomRequest cr = new CustomRequest();
		PrintWriter out = response.getWriter();
		JSONObject jsonResponse = new JSONObject();
		
		System.out.print(superUsername+" ");
		
		
		try {
		String userKey = oimu.getUserKey(userLogin, oimClient);
		System.out.println(userKey);
		System.out.println(userLogin);
		
	String requestID =	cr.modifySuperPassword(oimClient, userKey, userLogin, superUsername);
		
		System.out.println(requestID);
		
		jsonResponse.put("ResultID",requestID);
		out.write(jsonResponse.toString());
		
		
		}catch(Exception e) {
		
		
		e.printStackTrace();
	}
		
	}
		else if (action.equals("UserDelete")) {
			
			System.out.println("Delete User Function called");
			response.setContentType("application/json");
		      response.setCharacterEncoding("utf-8");
		         
			
		    String userLogintoDelete = request.getParameter("userLogintoDelete").trim().toString();
			
			
			  
			HttpSession session = request.getSession();
			OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
			PrintWriter out = response.getWriter();
			JSONObject jsonResponse = new JSONObject();
			
			
			
			
			try {
			UserManager userManager=null;
			
			userManager = oimClient.getService(UserManager.class);
			UserManagerResult userManagerResult=null;
			
			userManagerResult = userManager.delete(userLogintoDelete, true);
			
			System.out.println(userManagerResult.getStatus());
			
			jsonResponse.put("Result",userManagerResult.getStatus());
			out.write(jsonResponse.toString());
			
			
			}catch(Exception e) {
			
			
			e.printStackTrace();
		}
			

			
		}

		
	}
	}


