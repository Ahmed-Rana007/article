package com.gnl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hbl.approvalrequests.CustomRequest;
import com.hbl.encrypt.maker.CallSOAPWS;
import com.hbl.equation.limits.DatabaseConnection;
import com.hbl.selfservice.OIMUtils;

import oracle.iam.platform.OIMClient;

/**
 * Servlet implementation class ModifyFormServlet
 */
@WebServlet("/ModifyFormServlet")
public class ModifyFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyFormServlet() {
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
		
		
		System.out.println("MODIFY SERVLET CALLED");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		String userLogin  = (String)session.getAttribute("username");
		System.out.print("User Login:" +userLogin);
		String action  = request.getParameter("action");
		System.out.print("Action:  "+action);
		String appName = request.getParameter("AppName").trim().toString();
		String oiu_key = request.getParameter("oiu_key").trim().toString();
		String effectedUser  = request.getParameter("userkeytodisbale");
		System.out.println("Modified user login->>>>>>>>>>>>>>>>"+effectedUser);
		String effectedUserKey = null;
		OIMUtils oimUtils = new OIMUtils();
		try {
			effectedUserKey=oimUtils.getUserKey(effectedUser,oimClient);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(appName.contains("ActiveDirectory"))
		{
			System.out.println("MODIFY AD Method Start");
			
		String PagerAD = request.getParameter("PagerAD").trim().toString();
		String FaxAD = request.getParameter("FaxAD").trim().toString();
		String strFields = request.getParameter("strFields").trim().toString();
		
		System.out.println(PagerAD+" "+FaxAD+" "+oiu_key+" "+appName);
		System.out.println(strFields);
		
		String requestKey=null;
		CustomRequest cr = new CustomRequest();
		
		try {
			requestKey=cr.modifyADAccountRequest(oimClient,strFields,effectedUserKey,oiu_key);
			//System.out.print(cr.modifyADAccountRequest(oimClient,strFields,effectedUserKey));
			
			/////////////////////insert Form XML Data /////////////////////
			
			String xmlData =   request.getParameter("xml");
			System.out.println(xmlData);
			
			String xml = xmlData.replaceAll("\\\\", "");
			String xml1 = xml.substring(1, xml.length()-1);
			System.out.println(xml);
			System.out.println("Table26-----> "+xml1);
			
			if(requestKey!=null)
			{
				session.setAttribute("acctCatList",null);
				
				if(xml1 != null)
				{
				DatabaseConnection db = new DatabaseConnection();
				db.insetModChildXML(xml1, requestKey);
				
				}
			}
			
			///////////////////////////////////////////////////////////////
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("MODIFY AD Method END");
		out.print(requestKey);
	}
	
		else if(appName.contains("CP1"))
	{
		System.out.println("MODIFY CashPortal Method Start");
		
	String strFields = request.getParameter("strFields").trim().toString();
	
	System.out.println(oiu_key+" "+appName);
	System.out.println(strFields);
	
	String requestKey=null;
	CustomRequest cr = new CustomRequest();
	
	try {
		requestKey=cr.modifyCPAccountRequest(oimClient,strFields,effectedUserKey,oiu_key);
		//System.out.print(cr.modifyADAccountRequest(oimClient,strFields,effectedUserKey));
		
		/////////////////////insert Form XML Data /////////////////////
		
		String xmlData =   request.getParameter("xml");
		System.out.println(xmlData);
		
		String xml = xmlData.replaceAll("\\\\", "");
		String xml1 = xml.substring(1, xml.length()-1);
		System.out.println(xml);
		System.out.println("Table26-----> "+xml1);
		
		if(requestKey!=null)
		{
			session.setAttribute("acctCatList",null);
			
			if(xml1 != null)
			{
			DatabaseConnection db = new DatabaseConnection();
			db.insetModChildXML(xml1, requestKey);
			
			}
		}
		
		///////////////////////////////////////////////////////////////
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("MODIFY Cash Portal Method END");
	out.print(requestKey);
}
	/////////////////////////////AS400 Form Modify Request/////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////
		else if(appName.contains("AS400"))
		{
			System.out.println("MODIFY AS400 Method Start");
			
		String strFields = request.getParameter("strFields").trim().toString();
		
		System.out.println(oiu_key+" "+appName);
		System.out.println(strFields);
		
		String requestKey=null;
		CustomRequest cr = new CustomRequest();
		
		try {
			requestKey=cr.modifyAS400AccountRequest(oimClient,strFields,effectedUserKey,oiu_key);
			//System.out.print(cr.modifyADAccountRequest(oimClient,strFields,effectedUserKey));
			
			/////////////////////insert Form XML Data /////////////////////
			
			String xmlData =   request.getParameter("xml");
			System.out.println(xmlData);
			
			String xml = xmlData.replaceAll("\\\\", "");
			String xml1 = xml.substring(1, xml.length()-1);
			System.out.println(xml);
			System.out.println("Table26-----> "+xml1);
			
			if(requestKey!=null)
			{
				session.setAttribute("acctCatList",null);
				
				if(xml1 != null)
				{
				DatabaseConnection db = new DatabaseConnection();
				db.insetModChildXML(xml1, requestKey);
				
				}
			}
			
			///////////////////////////////////////////////////////////////
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("MODIFY Cash Portal Method END");
		out.print(requestKey);
	}
		
		
	/////////////////////////////////////EQUATION Form DATA///////////////////////////	
		else if(appName.contains("Equation"))
		{
			System.out.println("MODIFY AS400 Method Start");
			
		String strFields = request.getParameter("strFields").trim().toString();
		
		System.out.println(oiu_key+" "+appName);
		System.out.println(strFields);
		
		String requestKey=null;
		CustomRequest cr = new CustomRequest();
		
		try {
			requestKey=cr.modifyEquationAccountRequest(oimClient,strFields,effectedUserKey,oiu_key);
			//System.out.print(cr.modifyADAccountRequest(oimClient,strFields,effectedUserKey));
			
			/////////////////////insert Form XML Data /////////////////////
			
			String xmlData =   request.getParameter("xml");
			System.out.println(xmlData);
			
			String xml = xmlData.replaceAll("\\\\", "");
			String xml1 = xml.substring(1, xml.length()-1);
			System.out.println(xml);
			System.out.println("Equation XML-----> "+xml1);
			
			if(requestKey!=null)
			{
				session.setAttribute("acctCatList",null);
				
				if(xml1 != null)
				{
				DatabaseConnection db = new DatabaseConnection();
				db.insetModChildXML(xml1, requestKey);
				
				}
			}
			
			///////////////////////////////////////////////////////////////
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("MODIFY Equation Method END");
		out.print(requestKey);
	}
///////////////////////////////////EQUATION FORM DATA END/////////////////////////////////////////////

		/////////////////////////////////////EQUATION Group Form DATA///////////////////////////	
else if(appName.contains("EQGroup"))
{
System.out.println("MODIFY EQGroup Method Start");

String strFields = request.getParameter("strFields").trim().toString();
String UD_EQGROUP_AUTH_AMOUNT_FOR_LOC = request.getParameter("UD_EQGROUP_AUTH_AMOUNT_FOR_LOC").trim().toString();
String UD_EQGROUP_INTER_BRANCH_DEBIT = request.getParameter("UD_EQGROUP_INTER_BRANCH_DEBIT").trim().toString();
String UD_EQGROUP_INTER_BRANCH_CREDIT = request.getParameter("UD_EQGROUP_INTER_BRANCH_CREDIT").trim().toString();
String UD_EQGROUP_USER_TYPE = request.getParameter("UD_EQGROUP_USER_TYPE").trim().toString();
String UD_EQGROUP_AUTH_AMOUNT_FOR_L90 = request.getParameter("UD_EQGROUP_AUTH_AMOUNT_FOR_L90").trim().toString();
String UD_EQGROUP_AUTH_SPECIFIED_BRAN = request.getParameter("UD_EQGROUP_AUTH_SPECIFIED_BRAN").trim().toString();
String UD_EQGROUP_ALL_BRANCHES = request.getParameter("UD_EQGROUP_ALL_BRANCHES").trim().toString();
String UD_EQGROUP_AUTH_AMOUNT_FOR_INT = request.getParameter("UD_EQGROUP_AUTH_AMOUNT_FOR_INT").trim().toString();
String UD_EQGROUP_AUTH_AMOUNT_FOR_I52 = request.getParameter("UD_EQGROUP_AUTH_AMOUNT_FOR_I52").trim().toString();
String UD_EQGROUP_LIMIT_FOR_LOCAL_CRD = request.getParameter("UD_EQGROUP_LIMIT_FOR_LOCAL_CRD").trim().toString();
String UD_EQGROUP_SPECIFIED_BRANCHES = request.getParameter("UD_EQGROUP_SPECIFIED_BRANCHES").trim().toString();
String UD_EQGROUP_AUTH_ALL_BRANCHES = request.getParameter("UD_EQGROUP_AUTH_ALL_BRANCHES").trim().toString();
String UD_EQGROUP_LIMIT_FOR_LOCAL_DEB = request.getParameter("UD_EQGROUP_LIMIT_FOR_LOCAL_DEB").trim().toString();


System.out.println(oiu_key+" "+appName);
System.out.println(strFields);

String requestKey=null;
CustomRequest cr = new CustomRequest();

try {
requestKey=cr.modifyEquationGroupAccountRequest(oimClient,strFields,effectedUserKey,oiu_key,UD_EQGROUP_AUTH_AMOUNT_FOR_LOC
		,UD_EQGROUP_INTER_BRANCH_DEBIT,UD_EQGROUP_INTER_BRANCH_CREDIT,UD_EQGROUP_USER_TYPE
		,UD_EQGROUP_AUTH_AMOUNT_FOR_L90,UD_EQGROUP_AUTH_SPECIFIED_BRAN,UD_EQGROUP_ALL_BRANCHES,
		UD_EQGROUP_AUTH_AMOUNT_FOR_INT,UD_EQGROUP_AUTH_AMOUNT_FOR_I52,UD_EQGROUP_LIMIT_FOR_LOCAL_CRD,UD_EQGROUP_SPECIFIED_BRANCHES
		,UD_EQGROUP_AUTH_ALL_BRANCHES,UD_EQGROUP_LIMIT_FOR_LOCAL_DEB);
//System.out.print(cr.modifyADAccountRequest(oimClient,strFields,effectedUserKey));

/////////////////////insert Form XML Data /////////////////////

String xmlData =   request.getParameter("xml");
System.out.println(xmlData);

String xml = xmlData.replaceAll("\\\\", "");
String xml1 = xml.substring(1, xml.length()-1);
System.out.println(xml);
System.out.println("Equation Group XML-----> "+xml1);

if(requestKey!=null)
{
session.setAttribute("acctCatList",null);

if(xml1 != null)
{
DatabaseConnection db = new DatabaseConnection();
db.insetModChildXML(xml1, requestKey);

}
}

///////////////////////////////////////////////////////////////
} catch (ParseException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
System.out.println("MODIFY Equation Group Method END");
out.print(requestKey);
}
///////////////////////////////////EQUATION Group FORM DATA END/////////////////////////////////////////////
		
		
		///////////////////////////////////AS400 Child Data Ammendment///////////////////
		
		if(action.contains("AS400ChildAmmendment"))
		{
			System.out.println("MODIFY AS400 Method Start");
			
		
		System.out.println(oiu_key+" "+appName);
		
		String requestKey=null;
		CustomRequest cr = new CustomRequest();
		
		try {
			requestKey=cr.modifyAS400ChildDataRequest(oimClient,effectedUserKey,oiu_key);
			//System.out.print(cr.modifyADAccountRequest(oimClient,strFields,effectedUserKey));
			
			/////////////////////insert Form XML Data /////////////////////
			
			
			
			String xmlData =   request.getParameter("xml");
			System.out.println(xmlData);
			
			String xml = xmlData.replaceAll("\\\\", "");
			String xml1 = xml.substring(1, xml.length()-1);
			System.out.println(xml);
			System.out.println("TableAS400SCP-----> "+xml1);
			
			if(requestKey!=null)
			{
				session.setAttribute("acctCatList",null);
				
				if(xml1 != null)
				{
				DatabaseConnection db = new DatabaseConnection();
				db.insetChildAmmendmentXML(xml1, requestKey,effectedUser,"AS400ChildFormAmmendment");
				
				}
			}
			
			///////////////////////////////////////////////////////////////
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("AS400 Child Data Ammendment Method END");
		out.print(requestKey);
	}
		
		/////////////////////////////////////////////////////////////////////////////////
		
///////////////////////////////////Equation Child Data Ammendment///////////////////
		
if(action.contains("EQUATIONChildAmmendment"))
{
System.out.println("MODIFY Eqaution Method Start");


System.out.println(oiu_key+" "+appName);

String requestKey=null;
CustomRequest cr = new CustomRequest();

try {
requestKey=cr.modifyEquationChildDataRequest(oimClient,effectedUserKey,oiu_key);
//System.out.print(cr.modifyADAccountRequest(oimClient,strFields,effectedUserKey));

/////////////////////insert Form XML Data /////////////////////



String xmlData =   request.getParameter("xml");
System.out.println(xmlData);

String xml = xmlData.replaceAll("\\\\", "");
String xml1 = xml.substring(1, xml.length()-1);
System.out.println(xml);
System.out.println("Table26-----> "+xml1);

if(requestKey!=null)
{
session.setAttribute("acctCatList",null);

if(xml1 != null)
{
DatabaseConnection db = new DatabaseConnection();
db.insetChildAmmendmentXML(xml1, requestKey,effectedUser,"EquationChildFormAmmendment");

}
}

///////////////////////////////////////////////////////////////
} catch (ParseException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
System.out.println("AS400 Child Data Ammendment Method END");
out.print(requestKey);
}

/////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////Equation Group Child Data Ammendment///////////////////

if(action.contains("EquationGroupChildAmmendment"))
{
	System.out.println("MODIFY Equation Group Method Start");
	

System.out.println(oiu_key+" "+appName);

String requestKey=null;
CustomRequest cr = new CustomRequest();

try {
	requestKey=cr.modifyEquationGroupChildDataRequest(oimClient,effectedUserKey,oiu_key);
	//System.out.print(cr.modifyADAccountRequest(oimClient,strFields,effectedUserKey));
	
	/////////////////////insert Form XML Data /////////////////////
	
	
	
	String xmlData =   request.getParameter("xml");
	System.out.println(xmlData);
	
	String xml = xmlData.replaceAll("\\\\", "");
	String xml1 = xml.substring(1, xml.length()-1);
	System.out.println(xml);
	System.out.println("TableOptionID-----> "+xml1);
	
	if(requestKey!=null)
	{
		session.setAttribute("acctCatList",null);
		
		if(xml1 != null)
		{
		DatabaseConnection db = new DatabaseConnection();
		db.insetChildAmmendmentXML(xml1, requestKey,effectedUser,"EquationGroupChildFormAmmendment");
		
		}
	}
	
	///////////////////////////////////////////////////////////////
} catch (ParseException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
System.out.println("AS400 Child Data Ammendment Method END");
out.print(requestKey);
}

/////////////////////////////////////////////////////////////////////////////////




}

}
