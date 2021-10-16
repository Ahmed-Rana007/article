package com.gnl.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hbl.selfservice.OIMUtils;

/**
 * Servlet implementation class CreateUser
 */
@WebServlet("/CreateUser")
public class CreateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		/*String userLoginGenerated = null;
		String userFirstName = request.getParameter("userFirstName");
		String userLineManager = request.getParameter("userLineManager");
		String userMiddleName = request.getParameter("userMiddleName");
		String userOrganization = request.getParameter("userOrganization");
		String userlastName = request.getParameter("userlastName");
		String userRole = request.getParameter("userRole");
		String userFullName = request.getParameter("userFullName");
		String userEmail = request.getParameter("userEmail");
		String userLogin = request.getParameter("userLogin");
		String userPassword = request.getParameter("userPassword");
		String userDepartment = request.getParameter("userDepartment");
		String userBranchCode = request.getParameter("userBranchCode");
		String userBranchAddress = request.getParameter("userBranchAddress");
		String userPersonalNumber = request.getParameter("userPersonalNumber");
		String userCommonName = request.getParameter("userCommonName");
		String userMobileNumber = request.getParameter("userMobileNumber");
		OIMUtils oimUtils = new OIMUtils();
		try {
			userLoginGenerated = oimUtils.createUser("1", userlastName, userFirstName, userMiddleName, userLineManager, "EMP", "EMP", null, userEmail, "Pakistan", userPersonalNumber, 
					userMobileNumber, userMobileNumber, "North", userBranchAddress, userMobileNumber, "Branch Manager", userBranchCode, "Depaerment1", "Lahore",
					userBranchCode, null, userBranchCode, null, "Department");
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		if(userLoginGenerated!=null)
		{
			 request.setAttribute("userLogin", "New User Create with Login ID"+ userLoginGenerated);
	           request.getRequestDispatcher("CreateUser.jsp").forward(request, response); 
		} */
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
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
		System.out.println(userFirstName);
		System.out.println(userMiddleName);
		System.out.println(userlastName);
		System.out.println(userEmail);
	}

}
