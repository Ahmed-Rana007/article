package com.gnl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hbl.provisioning.SearchAccountsForUser;
import com.hbl.selfservice.objects.GSonResponseFields;

import oracle.iam.platform.OIMClient;


/**
 * Servlet implementation class AccountDetailsController
 */
@WebServlet("/AccountDetailsController")
public class AccountDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountDetailsController() {
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
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
 
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
        
        Gson gson = new Gson(); 
        //JsonObject myObj = new JsonObject();
        String userLogin = request.getParameter("userLogin").trim().toString();
        String action = request.getParameter("action").trim().toString();
        SearchAccountsForUser saf = new SearchAccountsForUser();
        OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
        List<GSonResponseFields>  gsnObjList = null;
        if(action.equals("ADAcctDetails"))
        {
        	 gsnObjList = saf.getAccountData(oimClient,userLogin, "1", "AD");
        }
        else  if(action.equals("EquationAcctDetails"))
        {
       	 gsnObjList = saf.getAccountData(oimClient,userLogin, "165","EQ");
       }
        else  if(action.equals("AS400AcctDetails"))
        {
       	 gsnObjList = saf.getAccountData(oimClient,userLogin, "2","AS");
       }
        else  if(action.equals("CP1AcctDetails"))
        {
       	 gsnObjList = saf.getAccountData(oimClient,userLogin, "64", "CP");
       }
        else  if(action.equals("EQGroupDetails"))
        {
       	 gsnObjList = saf.getAccountData(oimClient,userLogin, "164", "EQG");
       }
        System.out.println("KAshif:: "+gsnObjList);
        /* JsonElement countryObj = gson.toJsonTree(gsnObjList);
       
        if(gsnObjList.size() > 0){
            myObj.addProperty("success", false);
        }
        else {
            myObj.addProperty("success", true);
        }
        //myObj.add("countryInfo", countryObj);
        //out.println(myObj.toString()); */
        out.print(gson.toJson(gsnObjList));
        
        
 
        out.close();
	}

}
