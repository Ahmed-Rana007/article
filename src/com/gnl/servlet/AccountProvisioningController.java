package com.gnl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hbl.approvalrequests.MakeRequest;
import com.hbl.equation.limits.DatabaseConnection;
import com.hbl.provisioning.AccountCart;
import com.hbl.provisioning.ProvisioningAccounts;
import com.hbl.selfservice.OIMUtils;

import oracle.iam.platform.OIMClient;
import oracle.iam.provisioning.exception.ApplicationInstanceNotFoundException;
import oracle.iam.provisioning.exception.GenericAppInstanceServiceException;
import oracle.iam.reconciliation.utils.Sys;
import oracle.iam.request.vo.RequestBeneficiaryEntityAttribute;

/**
 * Servlet implementation class AccountProvisioningController
 */
@WebServlet("/AccountProvisioningController")
public class AccountProvisioningController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String appInstanceName = null;
	private String userLogin = null;
	private String action = null;
	private String entitlementListKey = null;
	private String entCodeValue = null;
	private String appInstanceKey = null;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountProvisioningController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		action = request.getParameter("action");
		
		HttpSession session = request.getSession();

		OIMUtils oimUtils = new OIMUtils();
		PrintWriter out = response.getWriter();

		System.out.println("Login->>>>>>>>>>>>>>>>>"+request.getParameter("selfLogin"));
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		List<AccountCart> acctCartList = null;
		if (action.equals("AddToCart")) {
			userLogin = request.getParameter("selfLogin");
			appInstanceName = request.getParameter("appInstanceName");
			appInstanceKey =request.getParameter("appInstanceKey");
			System.out.println("In Controller: " + appInstanceName + " = " + appInstanceKey+ " = " + userLogin);
			AccountCart acctCart = new AccountCart();
			AccountCart aS400App = new AccountCart();
			AccountCart equationApp = new AccountCart();
			
			if(appInstanceKey.toString().trim().equals("Mysis"))
			{
				aS400App.setUserLogin(userLogin);
				aS400App.setAppInstacneName("AS400");
				aS400App.setAppInstacneKey("2");
				aS400App.setItemType(1);
				equationApp.setUserLogin(userLogin);
				equationApp.setAppInstacneName("Equation");
				equationApp.setAppInstacneKey("165");
				equationApp.setItemType(1);
				
				
			}
			else
			{
				acctCart.setUserLogin(userLogin);
				acctCart.setAppInstacneName(appInstanceName);
				acctCart.setAppInstacneKey(appInstanceKey);
				acctCart.setItemType(1);
			}
			
			

			if (userLogin != null && appInstanceName != null) {

				if (session.getAttribute("acctCatList") != null) {
					acctCartList = (List<AccountCart>) session.getAttribute("acctCatList");
					if(appInstanceKey.toString().trim().equals("Mysis")) {
						acctCartList.add(aS400App);
						acctCartList.add(equationApp);
					}
					else {
						acctCartList.add(acctCart);
					}
					
					session.setAttribute("acctCatList", null);
					session.setAttribute("acctCatList", acctCartList);
				} else {
					acctCartList = new ArrayList<AccountCart>();
					
					if(appInstanceKey.toString().trim().equals("Mysis")) {
						acctCartList.add(aS400App);
						acctCartList.add(equationApp);
					}
					else {
						acctCartList.add(acctCart);
					}
					session.setAttribute("acctCatList", acctCartList);
					out.print("Item added in cart");
				}
			} else {
				out.print("Adding in cart issue");
			}
		} else if (action.equals("A2COthersLogin")) {
			userLogin = request.getParameter("othersLogin");
			appInstanceName = request.getParameter("appInstanceName");
			appInstanceKey =request.getParameter("appInstanceKey");
			System.out.println("In Controller: " + appInstanceName + " = " + appInstanceKey);
			AccountCart acctCart = new AccountCart();
			AccountCart aS400App = new AccountCart();
			AccountCart equationApp = new AccountCart();
			
			if(appInstanceKey.toString().trim().equals("Mysis"))
			{
				aS400App.setUserLogin(userLogin);
				aS400App.setAppInstacneName("AS400");
				aS400App.setAppInstacneKey("2");
				aS400App.setItemType(1);
				equationApp.setUserLogin(userLogin);
				equationApp.setAppInstacneName("Equation");
				equationApp.setAppInstacneKey("165");
				equationApp.setItemType(1);
				
				
			}
			else
			{
				acctCart.setUserLogin(userLogin);
				acctCart.setAppInstacneName(appInstanceName);
				acctCart.setAppInstacneKey(appInstanceKey);
				acctCart.setItemType(1);
			}
			if (userLogin != null && appInstanceName != null) {

				if (session.getAttribute("acctCatList") != null) {
					acctCartList = (List<AccountCart>) session.getAttribute("acctCatList");
					if(appInstanceKey.toString().trim().equals("Mysis")) {
						acctCartList.add(aS400App);
						acctCartList.add(equationApp);
						
					}
					else {
						acctCartList.add(acctCart);
					}
					session.setAttribute("acctCatList", null);
					session.setAttribute("acctCatList", acctCartList);
				} else {
					acctCartList = new ArrayList<AccountCart>();
					if(appInstanceKey.toString().trim().equals("Mysis")) {
						acctCartList.add(aS400App);
						acctCartList.add(equationApp);
					}
					else {
						acctCartList.add(acctCart);
					}
					session.setAttribute("acctCatList", acctCartList);
					out.print("Item added in cart");
				}
			} else {
				out.print("Adding in cart issue");
			}
		}

		else if (action.equals("RemoveInCart")) {
			String viewCartTable = null;
			acctCartList = (List<AccountCart>) session.getAttribute("acctCatList");
			appInstanceName = request.getParameter("appInstanceName");
			for (int i = 0; i < acctCartList.size(); i++) {
				if (acctCartList.get(i).getAppInstacneName().toString().trim()
						.equals(appInstanceName.toString().trim())) {
					acctCartList.remove(i);

				}
			}
			for (int i = 0; i < acctCartList.size(); i++) {
				viewCartTable += "<tr>" + "<td class='col-md-1'> <i class='glyphicon glyphicon-cog'>  </i></td>"
						+ "<td>" + "<a href='#'>   " + acctCartList.get(i).getAppInstacneName() + "</a>" + "</td>"
						+ "<td>" + acctCartList.get(i).getUserLogin() + "</td>"
						+ "<td><button class='btn btn-primary btn-sm removeCart' id='removeCart'>Remove</button></td>"
						+ "</tr>";

			}
			session.setAttribute("acctCatList", acctCartList);
			out.print(viewCartTable);
		} 
		else if(action.equals("AddEntToCart"))
		{
			System.out.print("in Add Entitlements");
			userLogin = request.getParameter("selfLogin");
			entitlementListKey = request.getParameter("entitleListKey");
			entCodeValue = request.getParameter("entName");
			
			System.out.println("In Controller- entitlementListKey:" + entitlementListKey + " =entCodeValue: "+entCodeValue +""+ userLogin);
			AccountCart acctCart = new AccountCart();
			acctCart.setUserLogin(userLogin);
			acctCart.setAppInstacneName(entCodeValue);
			acctCart.setAppInstacneKey(entitlementListKey);
			acctCart.setItemType(2);

			if (userLogin != null && entCodeValue != null && entitlementListKey!=null) {

				if (session.getAttribute("acctCatList") != null) {
					acctCartList = (List<AccountCart>) session.getAttribute("acctCatList");
					acctCartList.add(acctCart);
					session.setAttribute("acctCatList", null);
					session.setAttribute("acctCatList", acctCartList);
					out.print("Item added in cart 2");
				} else {
					acctCartList = new ArrayList<AccountCart>();
					acctCartList.add(acctCart);
					session.setAttribute("acctCatList", acctCartList);
					out.print("Item added in cart 1");
				}
			} else {
				out.print("Adding in cart issue");
			}
			
		}
		 else if (action.equals("AddOthrEntToCart")) {
			 
				userLogin = request.getParameter("othersLogin");
				entitlementListKey = request.getParameter("entitleListKey");
				entCodeValue = request.getParameter("entName");
				System.out.println("In Controller: " + appInstanceName + " = " + userLogin);
				AccountCart acctCart = new AccountCart();
				acctCart.setUserLogin(userLogin);
				acctCart.setAppInstacneName(entCodeValue);
				acctCart.setAppInstacneKey(entitlementListKey);
				acctCart.setItemType(2);
				if (userLogin != null && entCodeValue != null && entitlementListKey!=null) {

					if (session.getAttribute("acctCatList") != null) {
						acctCartList = (List<AccountCart>) session.getAttribute("acctCatList");
						acctCartList.add(acctCart);
						session.setAttribute("acctCatList", null);
						session.setAttribute("acctCatList", acctCartList);
					} else {
						acctCartList = new ArrayList<AccountCart>();
						acctCartList.add(acctCart);
						session.setAttribute("acctCatList", acctCartList);
						out.print("Item added in cart");
					}
				} else {
					out.print("Adding in cart issue");
				}
			}
		
		 else if (action.equals("AddRoleToCart")) {
				userLogin = request.getParameter("selfLogin");
				appInstanceName = request.getParameter("roleName");
				appInstanceKey =request.getParameter("roleKey");
				System.out.println("In Controller: " + appInstanceName + " = " + appInstanceKey+ " = " + userLogin);
				AccountCart acctCart = new AccountCart();
				acctCart.setUserLogin(userLogin);
				acctCart.setAppInstacneName(appInstanceName);
				acctCart.setAppInstacneKey(appInstanceKey);
				acctCart.setItemType(3);

				if (userLogin != null && appInstanceName != null) {

					if (session.getAttribute("acctCatList") != null) {
						acctCartList = (List<AccountCart>) session.getAttribute("acctCatList");
						acctCartList.add(acctCart);
						session.setAttribute("acctCatList", null);
						session.setAttribute("acctCatList", acctCartList);
					} else {
						acctCartList = new ArrayList<AccountCart>();
						acctCartList.add(acctCart);
						session.setAttribute("acctCatList", acctCartList);
						out.print("Item added in cart");
					}
				} else {
					out.print("Adding in cart issue");
				}
			} else if (action.equals("AddOthrRoleToCart")) {
				userLogin = request.getParameter("othersLogin");
				appInstanceName = request.getParameter("roleName");
				appInstanceKey = request.getParameter("roleKey");
				System.out.println("In Controller: " + appInstanceName + " = " + appInstanceKey);
				AccountCart acctCart = new AccountCart();
				acctCart.setUserLogin(userLogin);
				acctCart.setAppInstacneName(appInstanceName);
				acctCart.setAppInstacneKey(appInstanceKey);
				acctCart.setItemType(3);
				if (userLogin != null && appInstanceName != null) {

					if (session.getAttribute("acctCatList") != null) {
						acctCartList = (List<AccountCart>) session.getAttribute("acctCatList");
						acctCartList.add(acctCart);
						session.setAttribute("acctCatList", null);
						session.setAttribute("acctCatList", acctCartList);
					} else {
						acctCartList = new ArrayList<AccountCart>();
						acctCartList.add(acctCart);
						session.setAttribute("acctCatList", acctCartList);
						out.print("Item added in cart");
					}
				} else {
					out.print("Adding in cart issue");
				}
			}
		else if (action.equals("submitRequest")) {
			String justification = request.getParameter("accountJustification");
			if(justification.equals("")) {justification="-";}
			System.out.println("justification"+justification);
			
			String xmlHeader = "<?xml version=\"1.0\" standalone=\"yes\" ?> <ChildData>";
			String childXML = (String)session.getAttribute("ChildXml");
			String xmlFooter = "</ChildData>";
			System.out.println("ChildXML:"+childXML);
			System.out.print("in submitRequest");
			acctCartList = (List<AccountCart>) session.getAttribute("acctCatList");
			// userLogin = request.getParameter("selfLogin");
			userLogin = (String) session.getAttribute("username");
			String appInstName = acctCartList.get(0).getAppInstacneName();
			String appInstKey = acctCartList.get(0).getAppInstacneKey();
			Long appInstKy = null;
			String requestId = null;	
			System.out.println("appInstName: "+appInstName);
			if(childXML==null && appInstName.equals("Equation"))
			{
				out.print("Please enter Value in Table for Equation");
				return;
			}
			
			
			try {
				childXML=xmlHeader.concat(childXML).concat(xmlFooter);
				}catch(NullPointerException e)
				{
					System.out.println("No Child Data");
				}
				
			ProvisioningAccounts pa = new ProvisioningAccounts();
			MakeRequest mkRequest = new MakeRequest();
			if(acctCartList.size() > 0)
			{
				String userKey = null;
				oimUtils = new OIMUtils();
				try {
					userKey = oimUtils.getUserKey(acctCartList.get(0).getUserLogin().toString().trim(), oimClient);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				requestId = mkRequest.provisionAERRequest(oimClient, acctCartList,userKey,justification);
			}
			/* else if (acctCartList.size() > 1) {
				requestId = pa.createBulkApplicationRequest(oimClient, acctCartList, acctCartList.get(0).getUserLogin(), 2);
			} else {
				try {
					System.out.println(appInstName);
					if (appInstName.equals("ActiveDirectory")) {
						appInstKy = pa.getApplicationInstanceKey(appInstName, oimClient);
						System.out.println(acctCartList.get(0).getUserLogin());
						requestId = pa.createApplicationRequest(oimClient, appInstName, appInstKy, acctCartList.get(0).getUserLogin(), 1);
					} else if (appInstName.equals("Siebel8")) {
						appInstKy = pa.getApplicationInstanceKey(appInstName, oimClient);
						requestId = pa.createApplicationRequest(oimClient, appInstName, appInstKy, acctCartList.get(0).getUserLogin(), 2);
					}
					
				} catch (ApplicationInstanceNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (GenericAppInstanceServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			} */

			System.out.print(requestId);
			if(requestId!=null)
			{
				session.setAttribute("acctCatList",null);
				
				if(childXML != null)
				{
				DatabaseConnection db = new DatabaseConnection();
				db.insetChildXML(childXML, requestId);
				session.setAttribute("ChildXml",null);
				}
			}
			out.print("Your request ID " + requestId + " has been generated!");

		}
		else if(action.equals("updateAD"))
		{
			acctCartList = (List<AccountCart>) session.getAttribute("acctCatList");
			String itemType = request.getParameter("appInstanceType");
			
			String UD_ADUSER_FULLNAME = request.getParameter("UD_ADUSER_FULLNAME");
			String UD_ADUSER_ORGNAME = request.getParameter("UD_ADUSER_ORGNAME");
			List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
			RequestBeneficiaryEntityAttribute attr;
			if(UD_ADUSER_FULLNAME!=null || !UD_ADUSER_FULLNAME.equals(null))
			{
				attr = new RequestBeneficiaryEntityAttribute("Full Name", UD_ADUSER_FULLNAME, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			if(UD_ADUSER_ORGNAME!=null || !UD_ADUSER_ORGNAME.equals(null))
			{
				attr = new RequestBeneficiaryEntityAttribute("Organization Name", UD_ADUSER_ORGNAME, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			for(AccountCart accCrt : acctCartList)
			{
				if(accCrt.getItemType() == Integer.parseInt(itemType.toString().trim()) && accCrt.getAppInstacneName().equals("ActiveDirectory") )
				{
					accCrt.setAttrs(attrs);
				}
			}
			session.setAttribute("acctCatList", null);
			session.setAttribute("acctCatList", acctCartList);
		}
		else if(action.equals("updateAS400"))
		{
			acctCartList = (List<AccountCart>) session.getAttribute("acctCatList");
			String itemType = request.getParameter("appInstanceType");
			
	 
			
			String UD_AS400CON_USERCONTROLS = request.getParameter("UD_AS400CON_USERCONTROLS");
			String UD_AS400CON_GROUP_PROFILE = request.getParameter("UD_AS400CON_GROUP_PROFILE");
			String UD_AS400CON_INITIALMENU = request.getParameter("UD_AS400CON_INITIALMENU");
			String UD_AS400CON_INITIALPROGRAM = request.getParameter("UD_AS400CON_INITIALPROGRAM");
			String UD_AS400CON_UID = request.getParameter("UD_AS400CON_UID");
			String UD_AS400CON_CURRENT_LIBRARY = request.getParameter("UD_AS400CON_CURRENT_LIBRARY");
			String UD_AS400CON_LIMITCAP = request.getParameter("UD_AS400CON_LIMITCAP");
			
			///////////Child Data AS400/////////////
			///////////////////////////////////////
			String xmlData =   request.getParameter("xml");
			System.out.println(xmlData);
			
			String xml = xmlData.replaceAll("\\\\", "");
			String xml1 = xml.substring(1, xml.length()-1);
			System.out.println(xml);
			System.out.println("Table26-----> "+xml1);
			session.setAttribute("ChildXml", null);
			session.setAttribute("ChildXml", xml1);
			
			
			
			
			////////////////////////////////////
			
			List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
			RequestBeneficiaryEntityAttribute attr;
			if(UD_AS400CON_USERCONTROLS!=null || !UD_AS400CON_USERCONTROLS.equals(null))
			{
				attr = new RequestBeneficiaryEntityAttribute("User Class", UD_AS400CON_USERCONTROLS.trim(), RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
				
				
			}
			if(UD_AS400CON_GROUP_PROFILE!=null || !UD_AS400CON_GROUP_PROFILE.equals(null))
			{
				attr = new RequestBeneficiaryEntityAttribute("Group Profile", UD_AS400CON_GROUP_PROFILE.trim(), RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			if(UD_AS400CON_INITIALPROGRAM!=null || !UD_AS400CON_INITIALPROGRAM.equals(null))
			{
				attr = new RequestBeneficiaryEntityAttribute("Initial Program", UD_AS400CON_INITIALPROGRAM.trim(), RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			if(UD_AS400CON_INITIALMENU!=null || !UD_AS400CON_INITIALMENU.equals(null))
			{
				attr = new RequestBeneficiaryEntityAttribute("Initial Menu", UD_AS400CON_INITIALMENU.trim(), RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			
			if(UD_AS400CON_CURRENT_LIBRARY!=null || !UD_AS400CON_CURRENT_LIBRARY.equals(null))
			{
				attr = new RequestBeneficiaryEntityAttribute("Current Library", UD_AS400CON_CURRENT_LIBRARY.trim(), RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			if(UD_AS400CON_LIMITCAP!=null || !UD_AS400CON_LIMITCAP.equals(null))
			{
				attr = new RequestBeneficiaryEntityAttribute("Limit Capabilities", UD_AS400CON_LIMITCAP.trim(), RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			for(AccountCart accCrt : acctCartList)
			{
				if(accCrt.getItemType() == Integer.parseInt(itemType.toString().trim()) && accCrt.getAppInstacneName().equals("AS400")  )
				{
					accCrt.setAttrs(attrs);
				}
			}
			session.setAttribute("acctCatList", null);
			session.setAttribute("acctCatList", acctCartList);
		}
		else if(action.equals("updateEquation"))
		{
			acctCartList = (List<AccountCart>) session.getAttribute("acctCatList");
			String itemType = request.getParameter("appInstanceType");
			String UD_EQUATION_AUTH_ANY_BRANCH = request.getParameter("UD_EQUATION_AUTH_ANY_BRANCH");
			String UD_EQUATION_USER_CLASS = request.getParameter("UD_EQUATION_USER_CLASS");
			String UD_EQUATION_LIMIT_VIOLATION_AU = request.getParameter("UD_EQUATION_LIMIT_VIOLATION_AU");
			
			
			String xmlData =   request.getParameter("xml");
			System.out.println(xmlData);
			
			String xml = xmlData.replaceAll("\\\\", "");
			String xml1 = xml.substring(1, xml.length()-1);
			System.out.println(xml);
			System.out.println("Table26-----> "+xml1);
			session.setAttribute("ChildXml", null);
			session.setAttribute("ChildXml", xml1);
			
		 
			
			List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
			RequestBeneficiaryEntityAttribute attr;
		/*	if(UD_EQUATION_AUTH_ANY_BRANCH!=null || !UD_EQUATION_AUTH_ANY_BRANCH.equals(null))
			{
				attr = new RequestBeneficiaryEntityAttribute("Auth Any Branch", UD_EQUATION_AUTH_ANY_BRANCH, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}*/
			if(UD_EQUATION_USER_CLASS!=null || !UD_EQUATION_USER_CLASS.equals(null))
			{
				attr = new RequestBeneficiaryEntityAttribute("User Class", UD_EQUATION_USER_CLASS, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			/*if(UD_EQUATION_LIMIT_VIOLATION_AU!=null || !UD_EQUATION_LIMIT_VIOLATION_AU.equals(null))
			{
				attr = new RequestBeneficiaryEntityAttribute("Limit Violation Authority", UD_EQUATION_LIMIT_VIOLATION_AU, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			*/
			for(AccountCart accCrt : acctCartList)
			{
				if(accCrt.getItemType() == Integer.parseInt(itemType.toString().trim()) && accCrt.getAppInstacneName().equals("Equation")  )
				{
					accCrt.setAttrs(attrs);
				}
			}
			session.setAttribute("acctCatList", null);
			session.setAttribute("acctCatList", acctCartList);
		}
		else if(action.equals("updateCP1"))
		{
			String userLoginCp = "";
			acctCartList = (List<AccountCart>) session.getAttribute("acctCatList");
			//////////////////////
			OIMUtils oimUtils2 =new OIMUtils();
			
			try {
				userLoginCp = oimUtils.getUserEMPByLogin(acctCartList.get(0).getUserLogin(), oimClient);
				System.out.println("User Login CP->>>>>>---"+userLoginCp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			////////////////////
			String itemType = request.getParameter("appInstanceType");
			System.out.println("updateCP1: "+ itemType);
		//--	String UD_CP1USR_COMMENT = request.getParameter("UD_CP1USR_COMMENT");
			String UD_CP1USR_REGION_ID = request.getParameter("UD_CP1USR_REGION_ID");
		//--	String UD_CP1USR_USER_ID = request.getParameter("UD_CP1USR_USER_ID");
			String UD_CP1USR_DEPARTMENT_ID = request.getParameter("UD_CP1USR_DEPARTMENT_ID");
			String UD_CP1USR_USER_TYPE_ID = request.getParameter("UD_CP1USR_USER_TYPE_ID");
			System.out.println("updateCP1: "+ UD_CP1USR_REGION_ID +" " +  " "+ UD_CP1USR_DEPARTMENT_ID +" " +
			" "+ UD_CP1USR_USER_TYPE_ID);
			
			String xmlData =   request.getParameter("xml");
			System.out.println(xmlData);
			
			String xml = xmlData.replaceAll("\\\\", "");
			String xml1 = xml.substring(1, xml.length()-1);
			System.out.println(xml);
			System.out.println("Form Data CP-----> "+xml1);
			session.setAttribute("ChildXml", null);
			session.setAttribute("ChildXml", xml1);
			
			
			
			
			List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
			RequestBeneficiaryEntityAttribute attr;
		/*	if(UD_CP1USR_COMMENT!=null || !UD_CP1USR_COMMENT.equals(null))
			{
				attr = new RequestBeneficiaryEntityAttribute("Comment", UD_CP1USR_COMMENT, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}*/
			if(UD_CP1USR_REGION_ID!=null || !UD_CP1USR_REGION_ID.equals(null))
			{
				attr = new RequestBeneficiaryEntityAttribute("Regionid", UD_CP1USR_REGION_ID, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
//			if(UD_CP1USR_USER_ID!=null || !UD_CP1USR_USER_ID.equals(null))
//			{
//				attr = new RequestBeneficiaryEntityAttribute("User Id", UD_CP1USR_USER_ID, RequestBeneficiaryEntityAttribute.TYPE.String);	
//				attrs.add(attr);
//			}
			if(UD_CP1USR_DEPARTMENT_ID!=null || !UD_CP1USR_DEPARTMENT_ID.equals(null))
			{
				attr = new RequestBeneficiaryEntityAttribute("Departmentid", UD_CP1USR_DEPARTMENT_ID, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			if(UD_CP1USR_USER_TYPE_ID!=null || !UD_CP1USR_USER_TYPE_ID.equals(null))
			{
				attr = new RequestBeneficiaryEntityAttribute("Usertypeid", UD_CP1USR_USER_TYPE_ID, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			attr = new RequestBeneficiaryEntityAttribute("User Id", userLoginCp, RequestBeneficiaryEntityAttribute.TYPE.String);	
			attrs.add(attr);
			
			for(AccountCart accCrt : acctCartList)
			{
				if(accCrt.getItemType() == Integer.parseInt(itemType.toString().trim()) && accCrt.getAppInstacneName().equals("CP1")  )
				{
					accCrt.setAttrs(attrs);
				}
			}
			session.setAttribute("acctCatList", null);
			session.setAttribute("acctCatList", acctCartList);
		}
		else if(action.equals("updateEQGRP"))
		{
			acctCartList = (List<AccountCart>) session.getAttribute("acctCatList");
			String itemType = request.getParameter("appInstanceType");
			
			String UD_EQGROUP_INTER_BRANCH_DEBIT = request.getParameter("UD_EQGROUP_INTER_BRANCH_DEBIT");
			String UD_EQGROUP_INTER_BRANCH_CREDIT = request.getParameter("UD_EQGROUP_INTER_BRANCH_CREDIT");
			
			String UD_EQGROUP_USER_TYPE  = request.getParameter("UD_EQGROUP_USER_TYPE");
			String UD_EQGROUP_AUTH_AMOUNT_FOR_L90 = request.getParameter("UD_EQGROUP_AUTH_AMOUNT_FOR_L90");
			String UD_EQGROUP_AUTH_SPECIFIED_BRAN = request.getParameter("UD_EQGROUP_AUTH_SPECIFIED_BRAN");
			String UD_EQGROUP_GROUP_DESCRIPTION = request.getParameter("UD_EQGROUP_GROUP_DESCRIPTION");
			String UD_EQGROUP_ALL_BRANCHES  = request.getParameter("UD_EQGROUP_ALL_BRANCHES");
			String UD_EQGROUP_AUTH_AMOUNT_FOR_INT  = request.getParameter("UD_EQGROUP_AUTH_AMOUNT_FOR_INT");
			String UD_EQGROUP_AUTH_AMOUNT_FOR_I52 = request.getParameter("UD_EQGROUP_AUTH_AMOUNT_FOR_I52");
			String UD_EQGROUP_USER_GROUP = request.getParameter("UD_EQGROUP_USER_GROUP");
			String UD_EQGROUP_SPECIFIED_BRANCHES  = request.getParameter("UD_EQGROUP_SPECIFIED_BRANCHES");
			String UD_EQGROUP_LIMIT_FOR_LOCAL_CRD = request.getParameter("UD_EQGROUP_LIMIT_FOR_LOCAL_CRD");
			String UD_EQGROUP_AUTH_ALL_BRANCHES  = request.getParameter("UD_EQGROUP_AUTH_ALL_BRANCHES");
			String UD_EQGROUP_GROUP_TYPE  = request.getParameter("UD_EQGROUP_GROUP_TYPE");
			String UD_EQGROUP_JUSTIFICATION = request.getParameter("UD_EQGROUP_JUSTIFICATION");
			String UD_EQGROUP_LIMIT = request.getParameter("UD_EQGROUP_LIMIT");
			String UD_EQGROUP_LIMIT_FOR_LOCAL_DEB = request.getParameter("UD_EQGROUP_LIMIT_FOR_LOCAL_DEB");
			String UD_EQGROUP_AUTH_AMOUNT_FOR_LOC = request.getParameter("UD_EQGROUP_AUTH_AMOUNT_FOR_LOC");
			
			System.out.println("UD_EQGROUP_GROUP_DESCRIPTION:"+UD_EQGROUP_GROUP_DESCRIPTION);
			
			String xmlData =   request.getParameter("xml");
			System.out.println(xmlData);
			
			String xml = xmlData.replaceAll("\\\\", "");
			String xml1 = xml.substring(1, xml.length()-1);
			System.out.println(xml);
			System.out.println("Equation Group-----> "+xml1);
			session.setAttribute("ChildXml", null);
			session.setAttribute("ChildXml", xml1);
		
		 
			List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
			RequestBeneficiaryEntityAttribute attr;
			if(!UD_EQGROUP_INTER_BRANCH_DEBIT.isEmpty())
			{
				
				
				attr = new RequestBeneficiaryEntityAttribute("Inter Branch Debit",Integer.parseInt( UD_EQGROUP_INTER_BRANCH_DEBIT.toString().trim().concat("00")), RequestBeneficiaryEntityAttribute.TYPE.Integer);	
				attrs.add(attr);
			}
			if(!UD_EQGROUP_INTER_BRANCH_CREDIT.isEmpty())
			{
				
				attr = new RequestBeneficiaryEntityAttribute("Inter Branch Credit",Integer.parseInt( UD_EQGROUP_INTER_BRANCH_CREDIT.toString().trim().concat("00")), RequestBeneficiaryEntityAttribute.TYPE.Integer);	
				attrs.add(attr);
			}
			if(!UD_EQGROUP_USER_TYPE.isEmpty())
			{
				attr = new RequestBeneficiaryEntityAttribute("User Type", UD_EQGROUP_USER_TYPE, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			if(!UD_EQGROUP_AUTH_AMOUNT_FOR_L90.isEmpty())
			{
				attr = new RequestBeneficiaryEntityAttribute("Auth Amount for Local Credit",Integer.parseInt( UD_EQGROUP_AUTH_AMOUNT_FOR_L90.toString().trim().concat("00")), RequestBeneficiaryEntityAttribute.TYPE.Integer);	
				attrs.add(attr);
			}
			if(!UD_EQGROUP_AUTH_AMOUNT_FOR_LOC.isEmpty())
			{
				attr = new RequestBeneficiaryEntityAttribute("Auth Amount for Local Debit",Integer.parseInt( UD_EQGROUP_AUTH_AMOUNT_FOR_LOC.toString().trim().concat("00")), RequestBeneficiaryEntityAttribute.TYPE.Integer);	
				attrs.add(attr);
			}
			if(!UD_EQGROUP_AUTH_SPECIFIED_BRAN.isEmpty())
			{
				attr = new RequestBeneficiaryEntityAttribute("Auth Specified Branch", UD_EQGROUP_AUTH_SPECIFIED_BRAN, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			if(!UD_EQGROUP_GROUP_DESCRIPTION.isEmpty())
			{
				attr = new RequestBeneficiaryEntityAttribute("Group Description", UD_EQGROUP_GROUP_DESCRIPTION, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			
			if(!UD_EQGROUP_ALL_BRANCHES.isEmpty())
			{
				attr = new RequestBeneficiaryEntityAttribute("All Branches", UD_EQGROUP_ALL_BRANCHES, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			if(!UD_EQGROUP_AUTH_AMOUNT_FOR_INT.isEmpty())
			{
				
				attr = new RequestBeneficiaryEntityAttribute("Auth Amount for Inter Branch Credit",Integer.parseInt( UD_EQGROUP_AUTH_AMOUNT_FOR_INT.toString().trim().concat("00")), RequestBeneficiaryEntityAttribute.TYPE.Integer);	
				attrs.add(attr);
			} 
			if(!UD_EQGROUP_AUTH_AMOUNT_FOR_I52.isEmpty())
			{
				
				attr = new RequestBeneficiaryEntityAttribute("Auth Amount for Inter Branch Debit",Integer.parseInt( UD_EQGROUP_AUTH_AMOUNT_FOR_I52.toString().trim().concat("00")), RequestBeneficiaryEntityAttribute.TYPE.Integer);	
				attrs.add(attr);
			}
			if(!UD_EQGROUP_USER_GROUP.isEmpty())
			{
				attr = new RequestBeneficiaryEntityAttribute("User Group", UD_EQGROUP_USER_GROUP, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			if(!UD_EQGROUP_SPECIFIED_BRANCHES.isEmpty())
			{
				attr = new RequestBeneficiaryEntityAttribute("Specified Branches", UD_EQGROUP_SPECIFIED_BRANCHES, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			if(!UD_EQGROUP_LIMIT_FOR_LOCAL_CRD.isEmpty())
			{
				
				attr = new RequestBeneficiaryEntityAttribute("Limit for Local Crdeit",Integer.parseInt( UD_EQGROUP_LIMIT_FOR_LOCAL_CRD.toString().trim().concat("00")), RequestBeneficiaryEntityAttribute.TYPE.Integer);	
				attrs.add(attr);
			}
			if(!UD_EQGROUP_LIMIT_FOR_LOCAL_CRD.isEmpty())
			{
				
				attr = new RequestBeneficiaryEntityAttribute("Limit for Local Debit",Integer.parseInt( UD_EQGROUP_LIMIT_FOR_LOCAL_CRD.toString().trim().concat("00")), RequestBeneficiaryEntityAttribute.TYPE.Integer);	
				attrs.add(attr);
			}
			//
			if(!UD_EQGROUP_AUTH_ALL_BRANCHES.isEmpty())
			{
				attr = new RequestBeneficiaryEntityAttribute("Auth All Branches", UD_EQGROUP_AUTH_ALL_BRANCHES, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			if(!UD_EQGROUP_GROUP_TYPE.isEmpty())
			{
				attr = new RequestBeneficiaryEntityAttribute("Group Type", UD_EQGROUP_GROUP_TYPE, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
		/*	if(!UD_EQGROUP_JUSTIFICATION.isEmpty())
			{
				attr = new RequestBeneficiaryEntityAttribute("Justification", UD_EQGROUP_JUSTIFICATION, RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}*/
			if(!UD_EQGROUP_LIMIT.isEmpty())
			{
				
				attr = new RequestBeneficiaryEntityAttribute("Limit", UD_EQGROUP_LIMIT.toString().trim().concat("00"), RequestBeneficiaryEntityAttribute.TYPE.String);	
				attrs.add(attr);
			}
			for(AccountCart accCrt : acctCartList)
			{
				if(accCrt.getItemType() == Integer.parseInt(itemType.toString().trim()) && accCrt.getAppInstacneName().equals("EQGroup")  )
				{
					accCrt.setAttrs(attrs);
				}
			}
			session.setAttribute("acctCatList", null);
			session.setAttribute("acctCatList", acctCartList);
			out.print("Addedd in cart");
		}
	}

}
