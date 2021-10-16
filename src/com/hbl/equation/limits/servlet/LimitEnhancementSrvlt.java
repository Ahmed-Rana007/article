package com.hbl.equation.limits.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hbl.selfservice.OIMUtils;

import oracle.iam.platform.OIMClient;

/**
 * Servlet implementation class LimitEnhancementSrvlt
 */
@WebServlet("/LimitEnhancementSrvlt")
public class LimitEnhancementSrvlt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LimitEnhancementSrvlt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession  session=request.getSession();
		String userLogin = (String) session.getAttribute("username");
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		request.setAttribute("userKey", "123");
        request.getRequestDispatcher("equationLimits.jsp").forward(request, response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
}
