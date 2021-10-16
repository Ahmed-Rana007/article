package com.hbl.equation.limits;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import com.hbl.approvalrequests.PredefinedConst;
import com.hbl.equation.limits.servlet.LimitRequestObj;
import com.hbl.selfservice.LoginToOIM;
import com.hbl.selfservice.OIMUtils;
import com.hbl.selfservice.objects.EquationLimitFiledsObject;

import Thor.API.tcResultSet;
import Thor.API.Exceptions.tcAPIException;
import Thor.API.Exceptions.tcUserNotFoundException;
import Thor.API.Operations.tcUserOperationsIntf;
import oracle.iam.OIMMigration.util.OIMMigrationUtility;
import oracle.iam.platform.OIMClient;

public class DatabaseConnection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatabaseConnection dbConnection = new DatabaseConnection();
		
		LoginToOIM login = new LoginToOIM();
		OIMClient oimCleint;
		try {
			//oimCleint = login.loggedIntoOIM("muaaz", "Test@123");
			//dbConnection.getLimt("muaaz",oimCleint);
			//dbConnection.initiateRequest("12112", "Modify Limits", "Awaiting for Approval","ATMCCU" ,"Level1", "1", "12", "5274", "1213", "1212", "LAM TEAM");
			//dbConnection.equationRequestHstory(1040, "30020", "Pending", null, "1");
			//dbConnection.equationRequestHstory(1040, "28020", "Pending", null, "2");
			//dbConnection.equationRequestHstory(1040, "LAM TEAM", "Pending", null, "3");
			//dbConnection.getPendingApprovalsByUser("30020");
			//dbConnection.getRequestDetails("XELSYSADM");
			///dbConnection.getRequestApprovalHistory("XELSYSADM");
			// dbConnection.approveRequest(1120);
			//dbConnection.acquireRequest(1120, "112233");
			dbConnection.getRequestData(1161);
			//String req = "LMT-12343113";
			//System.out.println(req);
			//req = req.substring(4, req.length());
			//System.out.println(req);
					
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static Connection connectMSCDB()
	{
		Connection con = null;
		try {
            // step1 load the driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // step2 create the connection object
             con = DriverManager.getConnection("jdbc:oracle:thin:@10.9.166.57:1521:IDMPOC", "OIGP4_MSC",
                            "hblpoc_123");

            if(con!=null)
            	System.out.println("Connected");  

    } catch (Exception e) {
            System.out.println(e);
            return null;
    }
		return con;
	}
	
	public static Connection connectOIMDB()
	{
		Connection con = null;
		try {
            // step1 load the driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // step2 create the connection object
             con = DriverManager.getConnection("jdbc:oracle:thin:@10.9.166.57:1521:IDMPOC", "OIGP4_OIM",
                            "hblpoc_123");

            if(con!=null)
            	System.out.println("Connected");

           

    } catch (Exception e) {
            System.out.println(e);
            return null;
    }
		return con;
	}
	public List<EquationLimitFiledsObject> getLimt(String userLogin, OIMClient oimClient)
	{
		Connection con = connectOIMDB();
		PreparedStatement pstmt;
		OIMUtils oimutils = new OIMUtils();
		String orc_key  = oimutils.getProcessInstanceKey(userLogin, oimClient);
		System.out.println("ORC_KEY:"+orc_key);
		
		List<EquationLimitFiledsObject> equationLimitFiledsObjectList = new ArrayList<EquationLimitFiledsObject>();
		if(orc_key==null)
		{return equationLimitFiledsObjectList;}
		try {
			pstmt = con.prepareStatement(PredefinedConst.EQUATION_LIMITS_OIM_SCHEMA);
			pstmt.setString(1, orc_key.toString().trim());
			ResultSet rs = pstmt.executeQuery();
			
			EquationLimitFiledsObject equationLimitFiledsObject =null;
			while (rs.next()) {
				System.out.print("Start");
				int UD_KFILPK34_DEBIT_AUTHORIZATIO =  rs.getBigDecimal("UD_KFILPK34_DEBIT_AUTHORIZATIO").divide(new BigDecimal(100)).intValue();
				int UD_KFILPK34_CREDIT_AUTHORIZATI=   rs.getBigDecimal("UD_KFILPK34_CREDIT_AUTHORIZATI").divide(new BigDecimal(100)).intValue();
				equationLimitFiledsObject = new EquationLimitFiledsObject(
						rs.getInt("UD_KFILPK34_KEY"),
						rs.getInt("ORC_KEY"), 
						"123", "12", 
						rs.getString("UD_KFILPK34_PROCESS_INTER_BRAN"), 
						rs.getString("UD_KFILPK34_GROUP"), 
						rs.getInt("UD_KFILPK34_INTER_BRANCH_DEBIT"), 
						rs.getString("UD_KFILPK34_RESIDENT_BRANCH"), 
						rs.getInt("UD_KFILPK34_INTER_BRANCH_DEBIT"), 
						rs.getString("UD_KFILPK34_BRANCH_NO"), 
						rs.getInt("UD_KFILPK34_LOCAL_CREDIT_AMOUN"), 
						rs.getInt("UD_KFILPK34_LOCAL_DEBIT_AMOUNT"), 
						UD_KFILPK34_DEBIT_AUTHORIZATIO,    //rs.getBigDecimal("UD_KFILPK34_DEBIT_AUTHORIZATIO")/100, 
						UD_KFILPK34_CREDIT_AUTHORIZATI,    //rs.getBigDecimal("UD_KFILPK34_CREDIT_AUTHORIZATI")/100, 
						"1123"); 
/*
				public EquationLimitFiledsObject(int uD_KFILPK34_KEY, int oRC_KEY, String uD_KFILPK34_CREATEBY,
						String uD_KFILPK34_REVOKE, int uD_KFILPK34_PROCESS_INTER_BRAN, String uD_KFILPK34_GROUP,
						int uD_KFILPK34_INTER_BRANCH_DEBIT, String uD_KFILPK34_RESIDENT_BRANCH, int uD_KFILPK34_INTER_BRANCH_CREDI,
						String uD_KFILPK34_BRANCH_NUMBER, int uD_KFILPK34_LOCAL_CREDIT_AMOUN, int uD_KFILPK34_LOCAL_DEBIT_AMOUNT,
						int uD_KFILPK34_DEBIT_AUTHORIZATIO, int uD_KFILPK34_CREDIT_AUTHORIZATI, String uD_EQUATION_KEY)
				*/
				equationLimitFiledsObjectList.add(equationLimitFiledsObject);
				//request_key = rs.getInt(1)+"";
				//user_key = rs.getString(2).toString();
				//System.out.println(rs.getString("UD_KFILPK34_RESIDENT_BRANCH") );
				/*System.out.println(rs.getInt("UD_KFILPK34_KEY") +
						rs.getInt("ORC_KEY") +
						"123"+ "12" +
						rs.getString("UD_KFILPK34_PROCESS_INTER_BRAN") +
						rs.getString("UD_KFILPK34_GROUP") +
						rs.getInt("UD_KFILPK34_INTER_BRANCH_DEBIT") +
						rs.getString("UD_KFILPK34_RESIDENT_BRANCH") + 
						rs.getInt("UD_KFILPK34_INTER_BRANCH_DEBIT") +
						rs.getString("UD_KFILPK34_BRANCH_NUMBER") + 
						rs.getInt("UD_KFILPK34_LOCAL_CREDIT_AMOUN") +
						rs.getInt("UD_KFILPK34_LOCAL_DEBIT_AMOUNT") +
						rs.getInt("UD_KFILPK34_DEBIT_AUTHORIZATIO") +
						rs.getInt("UD_KFILPK34_CREDIT_AUTHORIZATI") + 
						"1123");
				/*System.out.println(rs.getString("UD_KFILPK34_RESIDENT_BRANCH") +  " = "+
						rs.getInt("UD_KFILPK34_KEY") +" =  " + rs.getInt("ORC_KEY") + " = "+
						rs.getString("UD_KFILPK34_PROCESS_INTER_BRAN") +"  = " + rs.getString("UD_KFILPK34_GROUP") +  " = "+
						rs.getString("UD_KFILPK34_INTER_BRANCH_DEBIT") +"  = " +
						rs.getString("UD_KFILPK34_INTER_BRANCH_CREDI") +"  = " + rs.getString("UD_KFILPK34_BRANCH_NUMBER") +  " = "+
						rs.getString("UD_KFILPK34_LOCAL_CREDIT_AMOUN") +" =  " + rs.getString("UD_KFILPK34_LOCAL_DEBIT_AMOUNT") +  " = "+
						rs.getString("UD_KFILPK34_DEBIT_AUTHORIZATIO") +" = " + rs.getString("UD_KFILPK34_CREDIT_AUTHORIZATI") +  " = ");
			*/
			}
			//UD_KFILPK34_KEY,ORC_KEY,POL_KEY,UD_KFILPK34_UPDATEBY,UD_KFILPK34_UPDATE,UD_KFILPK34_CREATEBY,
			//UD_KFILPK34_CREATE,UD_KFILPK34_NOTE,UD_KFILPK34_ROWVER,UD_KFILPK34_DATA_LEVEL,UD_KFILPK34_VERSION,REQUEST_KEY ,
			//UD_KFILPK34_REVOKE,UD_KFILPK34_PROCESS_INTER_BRAN,UD_KFILPK34_GROUP,UD_KFILPK34_INTER_BRANCH_DEBIT,
			//UD_KFILPK34_RESIDENT_BRANCH,UD_KFILPK34_INTER_BRANCH_CREDI,UD_KFILPK34_BRANCH_NUMBER,UD_KFILPK34_LOCAL_CREDIT_AMOUN,
			//UD_KFILPK34_LOCAL_DEBIT_AMOUNT,UD_KFILPK34_DEBIT_AUTHORIZATIO,UD_KFILPK34_CREDIT_AUTHORIZATI,UD_EQUATION_KEY 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
		}
		System.out.println(equationLimitFiledsObjectList.size());
		return equationLimitFiledsObjectList;
	}
	public java.sql.Date getCurrentDatetime() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}
	public int initiateRequest(String requesterId, String requestType, String  requestStatus, 
			String approvelLevel, String state, String userKey, String orc_key,String group_Name , String firstLevelApprove, String secondLEvelApprver, String thirdlevelAppr )
	{
		String requestId =null;
		int generatedKey =0;
		Connection con = connectMSCDB();
		try {
			
//REQUEST_TYPE,REQUEST_STATUS,APPROVER_LEVEL,GROUP_NAME,STATUS,STATE,
			//USER_KEY,ORC_KEY,REQUESTER,FIRST_LEV_APPRVER,SEC_LEV_APPRVER,THIRD_LEV_APPRVER
		//	REQUEST_TYPE,REQUEST_STATUS,APPROVER_LEVEL,GROUP_NAME,STATUS,STATE,USER_KEY,ORC_KEY,REQUESTER,FIRST_LEV_APPRVER,SEC_LEV_APPRVER,THIRD_LEV_APPRVER
			PreparedStatement pstmt = con.prepareStatement(PredefinedConst.INITIATE_EQUATION_MODIFY_LIMIT,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, requestType);
			pstmt.setString(2, requestStatus);
			pstmt.setString(3, approvelLevel);
			pstmt.setString(4, group_Name);
			pstmt.setString(5, requestStatus);
			pstmt.setString(6, state);
			pstmt.setString(7, userKey);
			pstmt.setString(8, orc_key);
			java.sql.Date date = getCurrentDatetime();
			pstmt.setDate(9, date);
			pstmt.setString(10, userKey);
			pstmt.setString(11, firstLevelApprove);
			pstmt.setString(12, secondLEvelApprver);
			pstmt.setString(13, thirdlevelAppr);
			pstmt.setString(14, firstLevelApprove);
			 
			
			 
			
			  pstmt.execute();
			  PreparedStatement ps = con
				        .prepareStatement("select equ_request_sequence.currval from dual");
				 
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
				    generatedKey = (int) rs.getLong(1);
				}
				System.out.println("Inserted record's ID: " + generatedKey);
			return generatedKey;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//INITIATE_EQUATION_MODIFY_LIMIT = "Insert into EQU_REQUEST (REQUEST_TYPE,REQUEST_STATUS,APPROVER_LEVEL,"
			//	+ "GROUP_NAME,STATUS,STATE,USER_KEY,ORC_KEY,REQUESTER) values (?,?,?,?,?,?,?,?,?);";
		return 0;
	}
	public boolean equationRequestHstory(int requestId,String approveKey, String  action, 
			String approvelComments, String stage)
	{
		 
		int generatedKey =0;
		Connection con = connectMSCDB();
		try {
			
//REQUEST_TYPE,REQUEST_STATUS,APPROVER_LEVEL,GROUP_NAME,STATUS,STATE,
			//USER_KEY,ORC_KEY,REQUESTER,FIRST_LEV_APPRVER,SEC_LEV_APPRVER,THIRD_LEV_APPRVER
			PreparedStatement pstmt = con.prepareStatement(PredefinedConst.EQUATION_APPROVAL_HISTORY_UPDATE);
			pstmt.setInt(1, requestId);
			pstmt.setString(2, approveKey);
			pstmt.setString(3, action);
			pstmt.setString(4, approvelComments);
			pstmt.setString(5, stage);
			
			 
		       System.out.println();
			pstmt.execute();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//INITIATE_EQUATION_MODIFY_LIMIT = "Insert into EQU_REQUEST (REQUEST_TYPE,REQUEST_STATUS,APPROVER_LEVEL,"
			//	+ "GROUP_NAME,STATUS,STATE,USER_KEY,ORC_KEY,REQUESTER) values (?,?,?,?,?,?,?,?,?);";
		return false;
	}
	public boolean equationReqLimitData(List<EquationLimitFiledsObject> equationLimitFiledsObjectList, String orc_key, int reqeustId)
	{
		int REQUEST_ID =0;
		String GROUP_NAME =null;
		String UD_KFILPK34_PROCESS_INTER_BRAN;
		String UD_KFILPK34_GROUP = null; 
		String UD_KFILPK34_RESIDENT_BRANCH = null;
		String UD_KFILPK34_BRANCH_NUMBER;
		int UD_KFILPK34_INTER_BRANCH_DEBIT ;
		int UD_KFILPK34_INTER_BRANCH_CREDI ;
		int UD_KFILPK34_LOCAL_CREDIT_AMOUN;
		int UD_KFILPK34_LOCAL_DEBIT_AMOUNT;
		int UD_KFILPK34_DEBIT_AUTHORIZATIO;
		int UD_KFILPK34_CREDIT_AUTHORIZATI;
		int ROW_NUMBER_TO_UPATE;
		int generatedKey =0;
		int UD_KFILPK34_KEY=0;
		Connection con = connectMSCDB();
		if(equationLimitFiledsObjectList.size() > 0)
		{
			try {
				PreparedStatement pstmt;
				for(EquationLimitFiledsObject limits : equationLimitFiledsObjectList)
				{
					UD_KFILPK34_GROUP = limits.getUD_KFILPK34_GROUP();
					UD_KFILPK34_PROCESS_INTER_BRAN = limits.getUD_KFILPK34_PROCESS_INTER_BRAN();
					UD_KFILPK34_RESIDENT_BRANCH = limits.getUD_KFILPK34_RESIDENT_BRANCH();
					UD_KFILPK34_BRANCH_NUMBER = limits.getUD_KFILPK34_BRANCH_NUMBER();
					UD_KFILPK34_INTER_BRANCH_DEBIT = limits.getUD_KFILPK34_INTER_BRANCH_DEBIT();
					UD_KFILPK34_INTER_BRANCH_CREDI = limits.getUD_KFILPK34_INTER_BRANCH_CREDI();
					UD_KFILPK34_LOCAL_CREDIT_AMOUN= limits.getUD_KFILPK34_LOCAL_CREDIT_AMOUN();
					UD_KFILPK34_LOCAL_DEBIT_AMOUNT = limits.getUD_KFILPK34_LOCAL_DEBIT_AMOUNT();
					UD_KFILPK34_DEBIT_AUTHORIZATIO = limits.getUD_KFILPK34_DEBIT_AUTHORIZATIO();
					UD_KFILPK34_CREDIT_AUTHORIZATI = limits.getUD_KFILPK34_CREDIT_AUTHORIZATI();
					UD_KFILPK34_KEY = limits.getUD_KFILPK34_KEY();
					pstmt = con.prepareStatement(PredefinedConst.EQUATION_REQ_LIMIT_DATA_INSERT);
					pstmt.setInt(1, reqeustId);
					pstmt.setString(2, UD_KFILPK34_GROUP);
					pstmt.setString(3, "Modify");
					pstmt.setString(4, UD_KFILPK34_PROCESS_INTER_BRAN);
					pstmt.setString(5, UD_KFILPK34_GROUP);
					
					pstmt.setInt(6, UD_KFILPK34_INTER_BRANCH_DEBIT);
					pstmt.setString(7, UD_KFILPK34_RESIDENT_BRANCH);
					pstmt.setInt(8, UD_KFILPK34_INTER_BRANCH_CREDI);
					pstmt.setString(9, UD_KFILPK34_BRANCH_NUMBER);
					pstmt.setInt(10, UD_KFILPK34_LOCAL_CREDIT_AMOUN);
					pstmt.setInt(11, UD_KFILPK34_LOCAL_DEBIT_AMOUNT);
					
					pstmt.setInt(12, UD_KFILPK34_DEBIT_AUTHORIZATIO);
					pstmt.setInt(13, UD_KFILPK34_CREDIT_AUTHORIZATI);
					pstmt.setInt(14,UD_KFILPK34_KEY);
					pstmt.execute();
					
				}
				
				//(REQUEST_ID,GROUP_NAME,ACTION,"
				//		+ "UD_KFILPK34_PROCESS_INTER_BRAN,UD_KFILPK34_GROUP,"
					//	+ "UD_KFILPK34_INTER_BRANCH_DEBIT,UD_KFILPK34_RESIDENT_BRANCH,UD_KFILPK34_INTER_BRANCH_CREDI,"
					//	+ "UD_KFILPK34_BRANCH_NUMBER,UD_KFILPK34_LOCAL_CREDIT_AMOUN,UD_KFILPK34_LOCAL_DEBIT_AMOUNT,"
					//	+ "UD_KFILPK34_DEBIT_AUTHORIZATIO,UD_KFILPK34_CREDIT_AUTHORIZATI,ROW_NUMBER_TO_UPATE)
							
							
							
							return true;
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		}
		
		//INITIATE_EQUATION_MODIFY_LIMIT = "Insert into EQU_REQUEST (REQUEST_TYPE,REQUEST_STATUS,APPROVER_LEVEL,"
			//	+ "GROUP_NAME,STATUS,STATE,USER_KEY,ORC_KEY,REQUESTER) values (?,?,?,?,?,?,?,?,?);";
		return false;
	}
	
	public List<LimitRequestObj> getPendingApprovalsByUser(String userame, boolean isLAM)
	{
		System.out.println("Key to Get Limit Approvals: "+userame);
		String userKey = null;
		LimitRequestObj limtReqObj = null;
		List<LimitRequestObj> limtReqList = new ArrayList<LimitRequestObj>();
		Connection con = connectMSCDB();
		PreparedStatement pstmt;
		try {
			if(isLAM)
			{
				pstmt = con.prepareStatement(PredefinedConst.LIMITS_GET_PENDING_APPROVALS_WITH_LAM);
				pstmt.setString(1, userame);
				pstmt.setString(2, "LAM TEAM");
				pstmt.setString(3, "Awaiting for Approval");
			}
			else {
				pstmt = con.prepareStatement(PredefinedConst.LIMITS_GET_PENDING_APPROVALS);
				pstmt.setString(1, userame);
				pstmt.setString(2, "Awaiting for Approval");
			}
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				limtReqObj = new LimitRequestObj();
				limtReqObj.setRequestId(rs.getInt("REQUEST_ID"));
				limtReqObj.setREQUEST_TYPE(rs.getString("REQUEST_TYPE"));
				limtReqObj.setREQUEST_STATUS(rs.getString("REQUEST_STATUS"));
				limtReqObj.setREQUESTER(rs.getString("REQUESTER") );
				limtReqObj.setCURRENT_APPROVER(rs.getString("CURRENT_APPROVER"));
				limtReqObj.setSTATE(rs.getString("STATE"));
				limtReqObj.setREQUEST_STATUS(rs.getString("STATUS"));
				limtReqObj.setCREATE_DATE(rs.getDate("CREATE_DATE"));
				limtReqObj.setUPDATE_DATE(rs.getDate("UPDATE_DATE"));
				System.out.println(rs.getInt("REQUEST_ID") + " " + rs.getString("REQUEST_TYPE") + " " + rs.getString("REQUEST_STATUS")+ " " + rs.getString("APPROVER_LEVEL")+ " " + 
						rs.getString("GROUP_NAME")+ " " + rs.getString("STATE")+ " " + 
						rs.getString("STATUS")+ " " + rs.getString("USER_KEY")+ " " + rs.getString("REQUESTER") + " " + rs.getString("FIRST_LEV_APPRVER")+ " " + rs.getString("SEC_LEV_APPRVER")  				
						+rs.getString("THIRD_LEV_APPRVER")+ " " + rs.getString("CURRENT_APPROVER")+" "+ rs.getDate("CREATE_DATE"));

				limtReqList.add(limtReqObj);
			}
			return limtReqList;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	void getRequestDetails(String requestId)
	{
		Connection con = connectMSCDB();
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(PredefinedConst.LIMITS_GET_REQUEST_DETAILS);
			pstmt.setString(1, requestId);
			 
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getDate("CREATE_DATE"));
				/*System.out.println(rs.getInt("REQUEST_ID") + " " + rs.getString("REQUEST_TYPE") + " " + rs.getString("REQUEST_STATUS")+ " " + rs.getString("APPROVER_LEVEL")+ " " + 
						rs.getString("GROUP_NAME")+ " " + rs.getString("STATE")+ " " + 
						rs.getString("STATUS")+ " " + rs.getString("USER_KEY")+ " " + rs.getString("REQUESTER") + " " + rs.getString("FIRST_LEV_APPRVER")+ " " + rs.getString("SEC_LEV_APPRVER")  				
						+rs.getString("THIRD_LEV_APPRVER")+ " " + rs.getDate("CREATE_DATE") );
				 
*/
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	void getRequestApprovalHistory(String requestId)
	{
		Connection con = connectMSCDB();
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(PredefinedConst.LIMITS_APPROVAL_HISTORY);
			pstmt.setString(1, requestId);
			 
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				//System.out.println(rs.getString(1));
				//EQHST.APPRV_HIST_ID,EQHST.REQUEST_ID,EQHST.APPROVER_KEY,  " + 
				//"EQHST.APPROVAL_COMMENTS, EQHST.STAGE, EQHST.ACTION, " + 
				//"EQRQST.REQUEST_TYPE, EQRQST.STATE, EQRQST.CREATE_DATE, EQRQST.REQUESTER, EQRQST.STATUS
				System.out.println(rs.getInt("H_APPRV_HIST_ID") + " " + rs.getInt("H_REQUEST_ID") + " " +
				rs.getString("H_APPROVER_KEY")+ " " + rs.getString("H_APPROVAL_COMMENTS")+ " " + 
						rs.getString("H_STAGE")+ " " + rs.getString("H_ACTION")+ " " + 
						rs.getString("R_REQUEST_TYPE")+ " " + rs.getString("R_STATE")+ " " + rs.getString("R_CREATE_DATE") + " " +
						rs.getString("R_REQUESTER")+ " " + rs.getString("R_STATUS")   );
				 
					 
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	public boolean approveRequest(int requestId, OIMClient oimClient)
	{
		UpdateChildProcessForm ucp = new UpdateChildProcessForm();
		Connection con = connectMSCDB();
		int requestState = getRequestState(requestId, con);
		boolean status = false;
		String approver_key =null;
		if(requestState ==1)
		{
			System.out.println("Stage 1");
			approver_key = getNextApprover(  requestId,  con,  "SEC_LEV_APPRVER");
			status = updateRequest(requestId, con, "Approved", "2", approver_key,requestState);
			if(status)
				return true;
			else return false;
		}
		else if(requestState ==2)
		{
			System.out.println("Stage 2");
			approver_key = getNextApprover(  requestId,  con,  "THIRD_LEV_APPRVER");
			status = updateRequest(requestId, con, "Approved", "3", approver_key,requestState);
			if(status)
				return true;
			else return false;
		}
		else if(requestState ==3)
		{
			System.out.println("Stage 3");
			approver_key = getNextApprover(  requestId,  con,  "THIRD_LEV_APPRVER");
			status = updateRequest(requestId, con, "Approved", "Completed", approver_key,requestState);
			String USER_KEY  = getBenificiaryKey(requestId, con);
			if(status)
			{
				ucp.modifyEquationLimits(requestId, oimClient,USER_KEY);
				return true;
			}
				 
			else return false;
		}
		System.out.println(requestState);
		System.out.println(approver_key);
		return false;
	}
	private int getRequestState(int requestId, Connection con)
	{
		String state = null;
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("select APPROVER_LEVEL from EQU_REQUEST where REQUEST_ID =?");
			pstmt.setInt(1, requestId);
			 
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				state = rs.getString("APPROVER_LEVEL");
				 System.out.println("Kashif: "+rs.getString("APPROVER_LEVEL"));
			}
			if(state.equals("1"))
				return 1;
			if(state.equals("2"))
				return 2;
			if(state.equals("3"))
				return 3;
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}
	private String getNextApprover(int requestId, Connection con, String approverLevel)
	{
		String state = null;
		PreparedStatement pstmt;
		String approver_key =  null;
		 
		try {
			pstmt = con.prepareStatement("select "+approverLevel+" from EQU_REQUEST where REQUEST_ID =?");
			pstmt.setInt(1, requestId);
			 
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				approver_key = rs.getString(approverLevel);
				//System.out.println(rs.getString("STATE"));
			}
			return approver_key;
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	private String getBenificiaryKey(int requestId, Connection con)
	{
		String state = null;
		PreparedStatement pstmt;
		String USER_KEY =  null;
		 
		try {
			pstmt = con.prepareStatement("select  USER_KEY from EQU_REQUEST where REQUEST_ID =?");
			pstmt.setInt(1, requestId);
			 
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				USER_KEY = rs.getString("USER_KEY");
				//System.out.println(rs.getString("STATE"));
			}
			return USER_KEY;
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	private boolean updateRequest(int requestId, Connection con,String action,String stage, String approver_key, int requestState  )
	{
		int i = 0;
		PreparedStatement stmt;
		try {
			
			 if(requestState < 3)
			 {
				stmt = con.prepareStatement("update EQU_REQ_APPRV_HIST set ACTION =? , STATE=? ,APPROVAL_COMMENTS=? where REQUEST_ID = ? and ACTION=?");
				stmt.setString(1,action); 
				 
				stmt.setString(2,action);
				stmt.setString(3,"Comments goes here.");
				stmt.setInt(4,requestId);
				stmt.setString(5,"Pending");  
				  
				 i=stmt.executeUpdate();  
				System.out.println(i+" records updated"); 
			 }
			 else
			 {
				 stmt = con.prepareStatement("update EQU_REQ_APPRV_HIST set ACTION =? , STATE=? ,APPROVAL_COMMENTS=? where REQUEST_ID = ? and ACTION=?");
					stmt.setString(1,action); 
					 
					stmt.setString(2,"Completed");
					stmt.setString(3,"Comments goes here.");
					stmt.setInt(4,requestId);
					stmt.setString(5,"Pending");  
					  
					  i=stmt.executeUpdate();  
					System.out.println(i+" records updated"); 
					
			 }
			 if(requestState !=3)
			 {
				 	stmt = con.prepareStatement("Insert into EQU_REQ_APPRV_HIST (REQUEST_ID,APPROVER_KEY,ACTION,APPROVAL_COMMENTS,STAGE, STATE) values (?,?,?,?,?,?)" );
					stmt.setInt(1,requestId); 
					stmt.setString(2,approver_key); 
					stmt.setString(3,"Pending");  
					stmt.setString(4,null);
					stmt.setString(5,stage);
					stmt.setString(6,"ASSIGNED");
					i=stmt.executeUpdate(); 
					System.out.println(i+" records insterted");
			 }
			 
			 if(requestState==3)
			 {
				 	stmt = con.prepareStatement("UPDATE EQU_REQUEST SET STATUS=?, CURRENT_APPROVER =?, REQUEST_STATUS=? WHERE REQUEST_ID=?" );
				 	stmt.setString(1,"Completed"); 
				 	stmt.setString(2,approver_key); 
					 
					stmt.setString (3,"Completed");
					stmt.setInt (4,requestId);
					i=stmt.executeUpdate();  
					System.out.println(i+" records updated");
			 }else
			 {
				 stmt = con.prepareStatement("UPDATE EQU_REQUEST SET CURRENT_APPROVER =?, APPROVER_LEVEL=? WHERE REQUEST_ID=?" );
					stmt.setString(1,approver_key); 
					stmt.setString(2,stage); 
					stmt.setInt (3,requestId);
					i=stmt.executeUpdate();  
					System.out.println(i+" records updated"); 
			 }
				
		 

			System.out.println("Request Updated");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		 return false;
	}
	public boolean rejectRequest(int requestId, OIMClient oimClient,String reason)
	{
		Connection con = connectMSCDB();
		int requestState = 4;
		boolean status = false;
		String approver_key =null;
		approver_key = getNextApprover(  requestId,  con,  "CURRENT_APPROVER");
		if(requestState ==4)
		{
			System.out.println("Stage 4");
			status = updateRejectRequest(requestId, con, "Rejected", "Rejected", approver_key,requestState,reason);
			if(status)
			{
				return true;
			}
				 
			else return false;
		}
		System.out.println(requestState);
		System.out.println(approver_key);
		return false;
	}
	private boolean updateRejectRequest(int requestId, Connection con,String action,String stage, String approver_key, int requestState,String reason)
	{
		int i = 0;
		PreparedStatement stmt;
		try {
				
			 if(requestState ==4)
			 {
				stmt = con.prepareStatement("update EQU_REQ_APPRV_HIST set ACTION =? , STATE=? ,APPROVAL_COMMENTS=? where REQUEST_ID = ? and ACTION=?");
				stmt.setString(1,action); 
				 
				stmt.setString(2,action);
				stmt.setString(3,reason);
				stmt.setInt(4,requestId);
				stmt.setString(5,"Pending");  
				  
				 i=stmt.executeUpdate();  
				System.out.println(i+" records updated"); 
			 }
			  if(requestState==4)
			 {
				 	stmt = con.prepareStatement("UPDATE EQU_REQUEST SET STATUS=?, CURRENT_APPROVER =?, REQUEST_STATUS=? WHERE REQUEST_ID=?" );
				 	stmt.setString(1,"Rejected"); 
				 	stmt.setString(2,approver_key); 
					 
					stmt.setString (3,"Rejected");
					stmt.setInt (4,requestId);
					i=stmt.executeUpdate();  
					System.out.println(i+" records updated");
			 }
			System.out.println("Request Updated");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		 return false;
	}

	public void acquireRequest(int requestId,  String aquiuredBy  )
	{
		Connection con = connectMSCDB();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("update EQU_REQ_APPRV_HIST set ACQUIRED_BY = ? where REQUEST_ID = ? and STAGE= ?");
			stmt.setString(1,aquiuredBy); 
			stmt.setInt(2,requestId);
			stmt.setString(3,"3");
			int i=stmt.executeUpdate();  
			System.out.println(i+" records updated"); 
			
			stmt = con.prepareStatement("UPDATE EQU_REQUEST SET CURRENT_APPROVER =? WHERE REQUEST_ID=?" );
			stmt.setString(1,aquiuredBy); 
			stmt.setInt (2,requestId);
			i=stmt.executeUpdate();  
			System.out.println(i+" records updated");
			
			System.out.println("Request Updated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		 
	}
	public List<LimitAttributes> getRequestData(int requestId)
    {	
		Connection con = connectMSCDB();
		PreparedStatement pstmt;
		 List<LimitAttributes> limitAttrList = new ArrayList<LimitAttributes>();
	     LimitAttributes lmtAttr = null;
		try {
			
			pstmt = con.prepareStatement(PredefinedConst.GET_EQUATION_REQ_LIMIT_DATA);
			pstmt.setInt(1, requestId);
			 
			ResultSet rs = pstmt.executeQuery();
		
			//REQUEST_ID,GROUP_NAME,ACTION,"
			///		+ "UD_KFILPK34_PROCESS_INTER_BRAN,UD_KFILPK34_GROUP,"
			//		+ "UD_KFILPK34_INTER_BRANCH_DEBIT,UD_KFILPK34_RESIDENT_BRANCH,UD_KFILPK34_INTER_BRANCH_CREDI,"
				//	+ "UD_KFILPK34_BRANCH_NUMBER,UD_KFILPK34_LOCAL_CREDIT_AMOUN,UD_KFILPK34_LOCAL_DEBIT_AMOUNT,"
				//	+ "UD_KFILPK34_DEBIT_AUTHORIZATIO,UD_KFILPK34_CREDIT_AUTHORIZATI,ROW_NUMBER_TO_UPATE
			
			while(rs.next())
			{
				lmtAttr = new LimitAttributes();
				lmtAttr.setRowNumberToUpdate(rs.getString("ROW_NUMBER_TO_UPATE"));
				lmtAttr.setRequestId(requestId+"");
				lmtAttr.setLmtFrLclCrdt(rs.getInt("UD_KFILPK34_LOCAL_CREDIT_AMOUN")+"");
				lmtAttr.setLmtFrLclDbt(rs.getInt("UD_KFILPK34_LOCAL_DEBIT_AMOUNT")+"");
				lmtAttr.setAuthAmtFrLclCrdt(rs.getInt("UD_KFILPK34_CREDIT_AUTHORIZATI")+"00");
				lmtAttr.setAuthAmtFrLclDbt(rs.getInt("UD_KFILPK34_DEBIT_AUTHORIZATIO")+"00");
				
				lmtAttr.setBranchNumber(rs.getString("UD_KFILPK34_BRANCH_NUMBER"));
				lmtAttr.setResidentBranch(rs.getString("UD_KFILPK34_RESIDENT_BRANCH"));
				lmtAttr.setIntrBrnDbtLmt(rs.getString("UD_KFILPK34_INTER_BRANCH_DEBIT"));
				lmtAttr.setIntrBrnCrLmt(rs.getString("UD_KFILPK34_INTER_BRANCH_CREDI")); 
				System.out.println(rs.getInt("REQUEST_ID")+ " = "+ rs.getString("ROW_NUMBER_TO_UPATE") +" "+ rs.getInt("UD_KFILPK34_LOCAL_CREDIT_AMOUN") +" "
						+ rs.getInt("UD_KFILPK34_LOCAL_DEBIT_AMOUNT") +" "+ rs.getInt("UD_KFILPK34_BRANCH_NUMBER") +" "+ rs.getInt("UD_KFILPK34_RESIDENT_BRANCH") +" ");
				limitAttrList.add(lmtAttr);
			}
			if(limitAttrList.size() > 0)
			{
				return limitAttrList;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return null;
    }
	
	public void insetChildXML(String xml,  String requestID)
	{
		Connection con = connectMSCDB();
		PreparedStatement stmt=null;
		int i=0;
		try {
			stmt = con.prepareStatement("Insert into OIM_CHILD_REQ_DATA (NO,REQUEST_DATA,REQUEST_ID,REQUEST_STAGE,REQUEST_STATUS,USER_LOGIN,OPERATION_TYPE,REC_STATUS,REQ_XML_DATA) values (?,?,?,?,?,?,?,?,?)" );
			stmt.setInt(1,1); 
			stmt.setString(2,"1"); 
			stmt.setString(3,requestID);  
			stmt.setString(4,null);
			stmt.setString(5,null);
			stmt.setString(6,"MAJID");
			stmt.setString(7,"CREATE"); 
			stmt.setString(8,null); 
			stmt.setString(9,xml);
			i=stmt.executeUpdate(); 
			
			System.out.println(i+" records insterted");
			System.out.println("Request Updated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
		    if (stmt != null) {
		        try {
		        	stmt.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		}
		 
	} 
	public void insetModChildXML(String xml,  String requestID)
	{
		Connection con = connectMSCDB();
		PreparedStatement stmt=null;
		int i=0;
		try {
			stmt = con.prepareStatement("Insert into OIM_CHILD_REQ_DATA (NO,REQUEST_DATA,REQUEST_ID,REQUEST_STAGE,REQUEST_STATUS,USER_LOGIN,OPERATION_TYPE,REC_STATUS,REQ_XML_DATA) values (?,?,?,?,?,?,?,?,?)" );
			stmt.setInt(1,1); 
			stmt.setString(2,"1"); 
			stmt.setString(3,requestID);  
			stmt.setString(4,null);
			stmt.setString(5,null);
			stmt.setString(6,"MAJID");
			stmt.setString(7,"Modify"); 
			stmt.setString(8,null); 
			stmt.setString(9,xml);
			i=stmt.executeUpdate(); 
			
			System.out.println(i+" records insterted");
			System.out.println("Request Updated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
		    if (stmt != null) {
		        try {
		        	stmt.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		}
		 
	} 
	
	public void insetChildAmmendmentXML(String xml,  String requestID, String UserLogin,String Operation )
	{
		Connection con = connectMSCDB();
		PreparedStatement stmt=null;
		int i=0;
		try {
			stmt = con.prepareStatement("Insert into OIM_CHILD_REQ_DATA (NO,REQUEST_DATA,REQUEST_ID,REQUEST_STAGE,REQUEST_STATUS,USER_LOGIN,OPERATION_TYPE,REC_STATUS,REQ_XML_DATA) values (?,?,?,?,?,?,?,?,?)" );
			stmt.setInt(1,1); 
			stmt.setString(2,"1"); 
			stmt.setString(3,requestID);  
			stmt.setString(4,null);
			stmt.setString(5,null);
			stmt.setString(6,UserLogin);
			stmt.setString(7,Operation); 
			stmt.setString(8,null); 
			stmt.setString(9,xml);
			i=stmt.executeUpdate(); 
			
			System.out.println(i+" records insterted");
			System.out.println("Request Updated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
		    if (stmt != null) {
		        try {
		        	stmt.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		}
		 
	} 
	
	public int checkXML(String requestID)
	{
		Connection con = connectMSCDB();
		PreparedStatement stmt=null;
		int count  = 0;
		try {
			stmt = con.prepareStatement("Select count(*) from OIM_CHILD_REQ_DATA WHERE REQUEST_ID=?" );
			
			stmt.setString(1,requestID); 
			
			  
			 ResultSet rs = stmt.executeQuery();
				while(rs.next())
				{
					count = rs.getInt("COUNT(*)");
					//System.out.println(rs.getString("STATE"));
				}
				
				
			
			System.out.println(count+" of request");
			
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
		    if (stmt != null) {
		        try {
		        	stmt.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		}
		
		return count;
	} 
	
}
