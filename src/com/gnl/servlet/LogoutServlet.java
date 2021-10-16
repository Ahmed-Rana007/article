package com.gnl.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hbl.selfservice.UserSessaion;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserSessaion.getOimClient().logout();
		HttpSession  session=request.getSession();
		//session.setAttribute("username", null);
		request.getRequestDispatcher("Logout.jsp").forward(request, response);
		session.setAttribute("username", null);
		session.setAttribute("password", null);
		session.setAttribute("oimClient", null);
		session.setAttribute("acctCatList", null);
		session.setAttribute("ChangePassword", null);
		response.getWriter().append("Served at: Logged Out ").append(request.getContextPath());
		//session.setAttribute("username", null);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
