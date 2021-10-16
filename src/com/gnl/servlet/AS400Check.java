package com.gnl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hbl.provisioning.AccountCart;
import com.hbl.selfservice.OIMUtils;
import com.hbl.selfservice.objects.AppInstanceObj;

import oracle.iam.platform.OIMClient;
import oracle.iam.provisioning.api.ProvisioningService;
import oracle.iam.provisioning.exception.GenericProvisioningException;
import oracle.iam.provisioning.exception.UserNotFoundException;
import oracle.iam.provisioning.vo.Account;

/**
 * Servlet implementation class AS400Check
 */
@WebServlet("/AS400Check")
public class AS400Check extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String userLogin = null;
	private String action = null;
	   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AS400Check() {
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
		action = request.getParameter("action");

		HttpSession session = request.getSession();

		OIMUtils oimUtils = new OIMUtils();
		PrintWriter out = response.getWriter();

		
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
	
		if (action.equals("AddToCart")) {
			userLogin = request.getParameter("selfLogin");
			try {
				out.print(oimUtils.checkAS400App(oimUtils.getUserKey(userLogin, oimClient), oimClient));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			else if (action.equals("otherLogin")) {
				userLogin = request.getParameter("othersLogin");
				try {
					out.print(oimUtils.checkAS400App(oimUtils.getUserKey(userLogin, oimClient), oimClient));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	}
			else if(action.equals("checkapps"))
			{
				
				userLogin = request.getParameter("logintoCheckapps");
				String appName = request.getParameter("appInstanceName");
				System.out.println("userLogin:"+userLogin+"  : "+"appName:"+appName);
				try {
					out.print(fetchProvisionedAccountsOfUser(oimUtils.getUserKey(userLogin, oimClient),oimClient,appName));
				} catch (UserNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (GenericProvisioningException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

}	
	
	
	public boolean fetchProvisionedAccountsOfUser(String usrKey, OIMClient oimClient,String appName) throws UserNotFoundException, GenericProvisioningException{
    	
	    System.out.println("usrKey:: "+ usrKey);
        List<Long> appInstanceKeys = new ArrayList<Long>();
        AppInstanceObj appInstanceObj =null;
        Map<Long,AppInstanceObj> acctProviosnedList=new Hashtable<Long,AppInstanceObj>();
             
         
            ProvisioningService provService = oimClient.getService(ProvisioningService.class);
            List<Account> provAccounts = provService.getAccountsProvisionedToUser(usrKey);
           // System.out.println(provAccounts.size() + " KEY: "+ usrKey);
            for(Account act : provAccounts){
            	
                if (((act.getAccountStatus().equalsIgnoreCase("Provisioned"))
                                || (act.getAccountStatus().equalsIgnoreCase("Enabled")) ||   (act.getAccountStatus().equalsIgnoreCase("Disabled"))
                                	) && act.getAppInstance().getApplicationInstanceName().equals(appName)){ 
                	
                	{	return true;}
                
         
                	
                }}
			return false;}}
        

