package com.hbl.certifications;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CertRedirectServlet
 */
@WebServlet("/CertRedirectServlet")
public class CertRedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CertRedirectServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 
		response.setContentType("text/html;charset=ISO-8859-1");
		String action = request.getParameter("action");
		String certIdTask = request.getParameter("certifId");
		String cetIDTask[]=certIdTask.split("%");
		System.out.println(cetIDTask[0] + " "+ cetIDTask[1]);
		CertificationsImpl certImpl = new CertificationsImpl();
		List<CertifDetailsObj> certifDetailsLits = certImpl.getCertificationItems("","",cetIDTask[0] , cetIDTask[1],certIdTask);
		request.setAttribute("data", certifDetailsLits);
		// RequestDispatcher rd =  request.getRequestDispatcher("CertificationDetails.jsp");
		 //rd.forward(request, response);
		
		 response.sendRedirect("CertificationDetails.jsp?q="+certifDetailsLits);  
		 
	}

}
