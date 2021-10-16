package com.hbl.certifications;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.iam.platform.OIMClient;

/**
 * Servlet implementation class CertificationsServlet
 */
@WebServlet("/CertificationsServlet")
public class CertificationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CertificationsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession  session=request.getSession();
		PrintWriter out = response.getWriter();
		CertificationsImpl certImpl = new CertificationsImpl();
		boolean status = false;
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		String action = request.getParameter("action");
		if(action.equals("certifyLinteItem"))
		{
			// certId:arr[0],taskId:arr[1],lnItemId:arr[2],entityId:arr[3]
			String certId  = request.getParameter("certId");
			String taskId  = request.getParameter("taskId");
			String lnItemId  = request.getParameter("lnItemId");
			String entityId  = request.getParameter("entityId");
			String catagory  = request.getParameter("catagory");
			status = certImpl.certifyLineItem(username, password, certId, taskId, lnItemId, entityId, catagory);
			if(status)
				out.print("Item Certified Successfully");
			else
				out.print("Problem in process, please contact system administrator");
		}
		else if(action.equals("revokeLinteItem"))
		{
			 
			String certId  = request.getParameter("certId");
			String taskId  = request.getParameter("taskId");
			String lnItemId  = request.getParameter("lnItemId");
			String entityId  = request.getParameter("entityId");
			String catagory  = request.getParameter("catagory");
			String comment  = request.getParameter("comment");
			System.out.print("COMMENTS"+comment);
			status = certImpl.revokeLineItem(username, password, certId, taskId, lnItemId, entityId, catagory,comment);
			 
			if(status)
				out.print("Item revoked Successfully");
			else
				out.print("Problem in process, please contact system administrator");
		}
		else if(action.equals("completeCertification"))
		{
			 
			String certId  = request.getParameter("certId");
			String taskId  = request.getParameter("taskId");
			status = certImpl.completeCertification(username, password, certId, taskId);
			 
			if(status)
				out.print("Certification Completed");
			else
				out.print("Problem in process, please contact system administrator");
		} 
		else
		{
			String userLogin = request.getParameter("userLogin");
			certImpl.getAssignedCertificationsTask(out, username,password );
		}
		
	}

}
