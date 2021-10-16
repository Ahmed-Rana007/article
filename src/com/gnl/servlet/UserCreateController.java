package com.gnl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bea.httppubsub.json.JSONObject;
import com.hbl.selfservice.OIMUtils;

import oracle.iam.platform.OIMClient;
import weblogic.jdbc.wrapper.Array;

/**
 * Servlet implementation class UserCreateController
 */
@WebServlet("/UserCreateController")
public class UserCreateController extends HttpServlet {
	private String userLoginGenerated = null;
	private OIMClient oimClient=null;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserCreateController() {
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
         // List<String> lst = new ArrayList<>() ;
         // lst.add("Arslan");
         // lst.add("Ammar");
         // lst.add("Kashif");
          //Map<String, List<String>> map = new HashMap<String, List<String>>();
          String userFirstName= request.getParameter("userFirstName");
          String userMiddleName= request.getParameter("userMiddleName");
          String userlastName= request.getParameter("userlastName");
          String userEmail= request.getParameter("userEmail");
          String userLogin= request.getParameter("userLogin");
          String userPassword= request.getParameter("userPassword");
          String confirmPassword= request.getParameter("confirmPassword");
          String userFullName= request.getParameter("userFullName");
          String ManagerEmpNum= request.getParameter("ManagerEmpNum");
          String userMobileNumber= request.getParameter("userMobileNumber");
          String userState= request.getParameter("userState");
          String userStreet= request.getParameter("userStreet");
          String userCity= request.getParameter("userCity");
          String userCountry= request.getParameter("userCountry");
          String userPersonalNumber= request.getParameter("userPersonalNumber");
          String userTitle= request.getParameter("userTitle");
          String userRegionCode= request.getParameter("userRegionCode");
          String userRegionName= request.getParameter("userRegionName");
          String userDepartment= request.getParameter("userDepartment");
          String userBranchCode= request.getParameter("userBranchCode");
          String Descrption = request.getParameter("Descrption"); 
          String userLineManager= request.getParameter("userLineManager");
          String userOrganization= request.getParameter("userOrganization");
           HttpSession  session=request.getSession();
           System.out.print("Org-----"+ userOrganization);
           System.out.print("Line-----"+ userLineManager);
          OIMUtils oimUtils = new OIMUtils();
          oimClient = (OIMClient) session.getAttribute("oimClient");
          
  		try {
  			//Long orgKey = oimUtils.getOrganizationKey(userOrganization.toString().trim(), oimClient)
  			userLoginGenerated = oimUtils.createUser("Xellerate Users", userlastName, userFirstName, userMiddleName, userLineManager, "EMP", "EMP", userLineManager, userEmail, userCountry, userPersonalNumber,null,
  					userMobileNumber, userState, userStreet, null, userTitle, null, userDepartment, userCity,userBranchCode, userRegionCode, userRegionName, userRegionCode, userDepartment,oimClient);
  			
  		}catch(Exception ex)
  		{
  			ex.printStackTrace();
  		}
  		PrintWriter out = response.getWriter();
  		if(userLoginGenerated!=null)
  		{
  			  //request.setAttribute("userLogin", "New User Create with Login ID"+ userLoginGenerated);
  	          // request.getRequestDispatcher("CreateUser.jsp").forward(request, response); 
  			out.print(" User Created Successfully" + userLoginGenerated);
  		}
          
		System.out.println(userLogin +" "+userFirstName +" "+ userlastName);
		/*if(action.equals("crateUser"))
		{
			JSONObject jsonResponse = new JSONObject();
			  jsonResponse.put("userLogin",  userLogin);
	         jsonResponse.put("firstName", "Kashif");  
	         jsonResponse.put("lastName", "Ali");
	         jsonResponse.put("email", "kashif.ali@techaccesspak.com");
	         PrintWriter out = response.getWriter();
	 		out.write(jsonResponse.toString());
		}*/
	} 

}
