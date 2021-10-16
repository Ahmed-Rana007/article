package com.gnl.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hbl.selfservice.OIMUser;
import com.hbl.selfservice.OIMUtils;
import com.hbl.selfservice.UserSessaion;
import com.hbl.selfservice.UsersList;

import oracle.iam.platform.OIMClient;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class UserDetails
 */
@WebServlet("/UserDetailsServlet")
public class UserDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Long userKey = null;
	private String userlogin = null;
	private String userFirstName = null;
	private String userLastName = null;
	private String userMiddleName = null;
	private String userDisplayName = null;
	private String userEmail =null;
	private String userOffice = null;
	private String department=null;
	private String userLineManager =null;
	private String userLineManagerEmail =null;
	private String lineManagerLogin =null;
	private String lineManagerUserkey = null;
	private String mobileNumber = null;
	private String act_key =null;
	private String lineManagerKey =null;
	private String userRole = null;
	private String branchName = null;
	private String branch_Code = null;
	private String personalNumber = null;
	private String branchAddress =null;
	private String organization =null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    OIMUser oimUser  = null;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Map<Long,UsersList> userList = UserSessaion.getUserList() ;
		
		HttpSession session = request.getSession();
		OIMUtils oimUtils = new OIMUtils();
		 OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		oimUser = oimUtils.getUsersDetailsByUserKey(oimClient,request.getParameter("userKey").toString().trim());
		userKey = oimUser.getUserKey();
		userlogin = oimUser.getUserlogin();
		userFirstName = oimUser.getUserFirstName();
		//userMiddleName = oimUser.getUserMiddleName().toString().trim();
		userLastName = oimUser.getUserLastName();
		//userLineManager = oimUser.getUserLineManager().toString().trim();
		userEmail = oimUser.getUserEmail();
		userDisplayName = userFirstName+" " + userLastName;
		personalNumber = oimUser.getPersonalNumber();
		mobileNumber = oimUser.getMobileNumber();
		branchName =oimUser.getBranchName();
		branch_Code =oimUser.getBranch_Code();
		organization =oimUser.getOrganization();
		department=oimUser.getDepartment();
		userRole=oimUser.getUserRole();
		request.setAttribute("userKeyDetails",userKey+"".toString());
		request.setAttribute("userLogin",userlogin );
		request.setAttribute("userFirstName",userFirstName);
		request.setAttribute("userLastName",userLastName);
		request.setAttribute("userDisplayName",userDisplayName);
		request.setAttribute("userEmail",userEmail);
		request.setAttribute("personalNumber", personalNumber);
		request.setAttribute("mobileNumber", mobileNumber);
		
		request.setAttribute("userRole", userRole);
			
		request.setAttribute("department", department);
		request.setAttribute("branchName", branchName);
		request.setAttribute("branch_Code", branch_Code);
		request.setAttribute("organization", organization);
		
		RequestDispatcher rd =  request.getRequestDispatcher("UserDetails.jsp");
		rd.forward(request, response);
		//request.getRequestDispatcher("UserDetails.jsp").forward(request, response);
		//response.getWriter().append("Served at: " + request.getParameter("userKey") + "== "+userList.get(Long.parseLong(request.getParameter("userKey"))).getUserLogin() +" ==").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
