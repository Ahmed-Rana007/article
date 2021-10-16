package soa;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PR_ChangePasswordservlet
 */
@WebServlet("/PR_ChangePasswordservlet")
public class PR_ChangePasswordservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PR_ChangePasswordservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		System.out.println("doGet Called");
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("DoPostCalled");
		
		HttpSession session = request.getSession();
		String status=null;
		status =(String) session.getAttribute("ChangePassword");
		
		System.out.println("Passwrd Status in servlet: "+status);
		if(status.equals("true"))
		{
			System.out.println("Redirect Change Password Called");
			request.getRequestDispatcher("/WEB-INF/views/ChangePassword.jsp").forward(request, response);
				System.out.println("Redirect Change Password Called");
				session.setAttribute("ChangePassword","");
	}
	}

}
