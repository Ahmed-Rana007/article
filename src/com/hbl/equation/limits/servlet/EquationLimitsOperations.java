package com.hbl.equation.limits.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jvnet.hk2.internal.ThreeThirtyResolver;

import com.bea.httppubsub.json.JSONArray;
import com.bea.httppubsub.json.JSONObject;
import com.google.gson.Gson;
import com.hbl.approvalrequests.PredefinedConst;
import com.hbl.equation.limits.DatabaseConnection;
import com.hbl.provisioning.ProvisioningAccounts;
import com.hbl.selfservice.OIMUtils;
import com.hbl.selfservice.objects.EquationLimitFiledsObject;
import com.hbl.selfservice.objects.GSonResponseFields;
import com.tangosol.internal.sleepycat.je.ThreadInterruptedException;

import oracle.iam.platform.OIMClient;

/**
 * Servlet implementation class EquationLimitsOperations
 */
@WebServlet("/EquationLimitsOperations")
public class EquationLimitsOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EquationLimitsOperations() {
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
		DatabaseConnection dbConnection = new DatabaseConnection();
		String user_key = "34021";
		String action = request.getParameter("action");
		OIMUtils oimUtils = new OIMUtils();
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String userLogin = (String) session.getAttribute("username");
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		String jsonString = null;
		JSONObject jsonRecord = null;
		
		boolean isInitiated =false;
		if (action.equals("createRequest")) {
			List<EquationLimitFiledsObject> equationLimitFiledsObjectList = new ArrayList<EquationLimitFiledsObject>();
			EquationLimitFiledsObject equationLimitFiledsObject =null;
			int requst_generated_key = 0;
			String requestData = request.getParameter("requestData");
			JSONObject jsonObject = new JSONObject(requestData.toString());
			String UD_KFILPK34_GROUP = null;
			Iterator<String> keys = jsonObject.keys();
			while (keys.hasNext()) {
				String key = keys.next();

				if (jsonObject.get(key) instanceof JSONObject) {
					
					System.out.println("---Recrod Start--");
					jsonString = jsonObject.get(key).toString().trim();
					jsonRecord = new JSONObject(jsonString.toString());

					System.out.println(jsonRecord.get("UD_KFILPK34_RESIDENT_BRANCH"));

					System.out.println(jsonRecord.get("UD_KFILPK34_PROCESS_INTER_BRAN"));
					System.out.println(jsonRecord.get("UD_KFILPK34_INTER_BRANCH_DEBIT"));
					System.out.println(jsonRecord.get("UD_KFILPK34_INTER_BRANCH_DEBIT"));
					System.out.println(jsonRecord.get("UD_KFILPK34_INTER_BRANCH_CREDI"));
					System.out.println(jsonRecord.get("UD_KFILPK34_BRANCH_NUMBER"));
					System.out.println(jsonRecord.get("UD_KFILPK34_LOCAL_CREDIT_AMOUN"));
					System.out.println(jsonRecord.get("UD_KFILPK34_DEBIT_AUTHORIZATIO"));
					System.out.println(jsonRecord.get("UD_KFILPK34_GROUP"));
					System.out.println(jsonRecord.get("UD_KFILPK34_CREDIT_AUTHORIZATI"));
					 
					UD_KFILPK34_GROUP = jsonRecord.get("UD_KFILPK34_GROUP").toString().trim();
					equationLimitFiledsObject =   new EquationLimitFiledsObject(
							Integer.parseInt(jsonRecord.get("UD_KFILPK34_KEY").toString().trim()),
							2, 
							"123", "12", 
							jsonRecord.get("UD_KFILPK34_PROCESS_INTER_BRAN").toString().trim(), 
							jsonRecord.get("UD_KFILPK34_GROUP").toString().trim(), 
							Integer.parseInt(jsonRecord.get("UD_KFILPK34_INTER_BRANCH_DEBIT").toString().trim()), 
							jsonRecord.get("UD_KFILPK34_RESIDENT_BRANCH").toString().trim(), 
							Integer.parseInt(jsonRecord.get("UD_KFILPK34_INTER_BRANCH_DEBIT").toString().trim()), 
							jsonRecord.get("UD_KFILPK34_BRANCH_NUMBER").toString().trim(), 
							Integer.parseInt(jsonRecord.get("UD_KFILPK34_LOCAL_CREDIT_AMOUN").toString().trim()), 
							1, 
							Integer.parseInt(jsonRecord.get("UD_KFILPK34_DEBIT_AUTHORIZATIO").toString().trim()), 
							Integer.parseInt(jsonRecord.get("UD_KFILPK34_CREDIT_AUTHORIZATI").toString().trim()), 
							"12"); 
					equationLimitFiledsObjectList.add(equationLimitFiledsObject);
					System.out.println("---Recrod END---");

				}
			}
			try {
				user_key = oimUtils.getUserKey(userLogin, oimClient);
				String orc_key = oimUtils.getProcessInstanceKey(userLogin, oimClient);
				String firstLevelApprover = oimUtils.getManagerKey(userLogin, oimClient);
				String firstLevelUserLogin = oimUtils.getUserLogin(firstLevelApprover, oimClient);
				String secondLevelApprovel = oimUtils.getManagerKey(firstLevelUserLogin, oimClient);
				requst_generated_key = dbConnection.initiateRequest(user_key, "Modify Limits", "Awaiting for Approval",
						 "1", "1", user_key, orc_key, UD_KFILPK34_GROUP,firstLevelApprover,
						secondLevelApprovel, "LAM TEAM");
				if(requst_generated_key !=0)
				{
					System.out.println("");
					isInitiated =true;
					dbConnection.equationRequestHstory(requst_generated_key, firstLevelApprover, "Pending", null, "1");
					dbConnection.equationReqLimitData(equationLimitFiledsObjectList, orc_key, requst_generated_key);
				}
				else
				{
					isInitiated =false;
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
			List<String> list = new ArrayList<String>();
			// JSONObject array = obj.getJSONObject("002");

			// System.out.println(array.get("UD_KFILPK34_INTER_BRANCH_DEBIT"));
			// System.out.println("Ajax to Servlet:\n"+requestData);
			if(isInitiated)
				out.println("!Your Request id LMT-" + requst_generated_key + " is inittiated to modify limits..");
			else
				out.println("!Error while initialization of request, please contact your administrator..");

		} else {
			userLogin = request.getParameter("userLogin");
			ProvisioningAccounts pa = new ProvisioningAccounts();
			Connection con = DatabaseConnection.connectOIMDB();
			PreparedStatement pstmt;
			List<EquationLimitFiledsObject> equationLimitFiledsObjectList = new ArrayList<EquationLimitFiledsObject>();
			try {
				pstmt = con.prepareStatement(PredefinedConst.EQUATION_LIMITS_OIM_SCHEMA);
				pstmt.setString(1, "5274");
				ResultSet rs = pstmt.executeQuery();
				EquationLimitFiledsObject equationLimitFiledsObject = null;
				while (rs.next()) {

					equationLimitFiledsObject = new EquationLimitFiledsObject(rs.getInt("UD_KFILPK34_KEY"),
							rs.getInt("ORC_KEY"), "123", "12", rs.getString("UD_KFILPK34_PROCESS_INTER_BRAN"),
							rs.getString("UD_KFILPK34_GROUP"), rs.getInt("UD_KFILPK34_INTER_BRANCH_DEBIT"),
							rs.getString("UD_KFILPK34_RESIDENT_BRANCH"), rs.getInt("UD_KFILPK34_INTER_BRANCH_DEBIT"),
							rs.getString("UD_KFILPK34_BRANCH_NUMBER"), rs.getInt("UD_KFILPK34_LOCAL_CREDIT_AMOUN"),
							rs.getInt("UD_KFILPK34_LOCAL_DEBIT_AMOUNT"), rs.getInt("UD_KFILPK34_DEBIT_AUTHORIZATIO"),
							rs.getInt("UD_KFILPK34_CREDIT_AUTHORIZATI"), "1123");

					equationLimitFiledsObjectList.add(equationLimitFiledsObject);
					// request_key = rs.getInt(1)+"";
					// user_key = rs.getString(2).toString();
					System.out.println(rs.getString("UD_KFILPK34_RESIDENT_BRANCH") + " = "
							+ rs.getInt("UD_KFILPK34_KEY") + " =  " + rs.getInt("ORC_KEY") + " = "
							+ rs.getString("UD_KFILPK34_PROCESS_INTER_BRAN") + "  = "
							+ rs.getString("UD_KFILPK34_GROUP") + " = " + rs.getString("UD_KFILPK34_INTER_BRANCH_DEBIT")
							+ "  = " + rs.getString("UD_KFILPK34_INTER_BRANCH_CREDI") + "  = "
							+ rs.getString("UD_KFILPK34_BRANCH_NUMBER") + " = "
							+ rs.getString("UD_KFILPK34_LOCAL_CREDIT_AMOUN") + " =  "
							+ rs.getString("UD_KFILPK34_LOCAL_DEBIT_AMOUNT") + " = "
							+ rs.getString("UD_KFILPK34_DEBIT_AUTHORIZATIO") + " = "
							+ rs.getString("UD_KFILPK34_CREDIT_AUTHORIZATI") + " = ");

				}
				// UD_KFILPK34_KEY,ORC_KEY,POL_KEY,UD_KFILPK34_UPDATEBY,UD_KFILPK34_UPDATE,UD_KFILPK34_CREATEBY,
				// UD_KFILPK34_CREATE,UD_KFILPK34_NOTE,UD_KFILPK34_ROWVER,UD_KFILPK34_DATA_LEVEL,UD_KFILPK34_VERSION,REQUEST_KEY
				// ,
				// UD_KFILPK34_REVOKE,UD_KFILPK34_PROCESS_INTER_BRAN,UD_KFILPK34_GROUP,UD_KFILPK34_INTER_BRANCH_DEBIT,
				// UD_KFILPK34_RESIDENT_BRANCH,UD_KFILPK34_INTER_BRANCH_CREDI,UD_KFILPK34_BRANCH_NUMBER,UD_KFILPK34_LOCAL_CREDIT_AMOUN,
				// UD_KFILPK34_LOCAL_DEBIT_AMOUNT,UD_KFILPK34_DEBIT_AUTHORIZATIO,UD_KFILPK34_CREDIT_AUTHORIZATI,UD_EQUATION_KEY
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Gson gson = new Gson();
			out.print(gson.toJson(equationLimitFiledsObjectList));
		}

		// pa.getEquationLimits(oimClient,user_key);

	}

}
