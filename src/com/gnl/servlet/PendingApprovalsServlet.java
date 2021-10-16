package com.gnl.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.http.HTTPException;

import com.bea.httppubsub.json.JSONArray;
import com.bea.httppubsub.json.JSONObject;

import com.hbl.approvalrequests.PendingApprovalObj;
import com.hbl.approvalrequests.PredefinedConst;
import com.hbl.approvalrequests.SOADBConnection;
import com.hbl.approvalrequests.StatusCode;
import com.hbl.approvalrequests.UpdateRequestRestAPI;
import com.hbl.equation.limits.DatabaseConnection;
import com.hbl.equation.limits.servlet.LimitRequestObj;
import com.hbl.selfservice.OIMUtils;
import com.hbl.selfservice.objects.RoleUser;

import oracle.iam.platform.OIMClient;
import weblogic.net.http.HttpURLConnection;

/**
 * Servlet implementation class PendingApprovalsServlet
 */
@WebServlet("/PendingApprovalsServlet")
public class PendingApprovalsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PendingApprovalsServlet() {
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
		
		String action = request.getParameter("action");
		String userLogin = request.getParameter("userLogin");
		
		HttpSession  session=request.getSession();
		PrintWriter out = response.getWriter();
		userLogin = (String) session.getAttribute("username");
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		String password = (String) session.getAttribute("password");
		String requestId = request.getParameter("requestId");
		System.out.println(requestId);
		if(action.equals("approvals"))
		{
			//getPendingApprovals(userLogin,out);
			getPendingApprovals(userLogin, password,out,oimClient);
		}else if (action.equals("approvalsDetails"))
		{
			 
			//getApprovalDetails(requestId, out);
			requestDetials(requestId,out,userLogin,password);
		}
		else if (action.equals("approve"))
		{
			DatabaseConnection dbConnection = new DatabaseConnection();
			if(requestId.startsWith("LMT-"))
			{
				System.out.println("requestId: "+requestId);
				requestId = requestId.substring(4, requestId.length()).toString().trim();
				System.out.println("requestId: "+ requestId);
				//dbConnection.approveRequest(Integer.parseInt(requestId), oimClient)
				if(dbConnection.approveRequest(Integer.parseInt(requestId), oimClient))
				{
					System.out.println("Approved Successfully.!!");
					out.write("Approved Successfully.!!");
				}
				else
				{
					System.out.println("Some issue to process this request. ");
					out.write("Some issue to process this request. ");
				}
			}
			else
			{
				int reqResponse = 0;
				System.out.println("Password in Approve Condition: " + password);
				
				try {
					UpdateRequestRestAPI updateRequestAPI = new UpdateRequestRestAPI();
					reqResponse=updateRequestAPI.approveRequest(requestId.toString().trim(), getRequestTaskID(requestId.toString().trim()), userLogin, password.toString().trim());
					System.out.print("STATUS RESPONSE"+reqResponse);
					StatusCode st=new StatusCode();
					System.out.print(st.statusCode(reqResponse));
					out.print(st.statusCode(reqResponse));	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(requestId);
				System.out.println(userLogin);
				System.out.println(password);
				System.out.println(reqResponse);
			}
			
			
			
			 
		}
		else if (action.equals("reject"))
		{
			String reason = request.getParameter("reason");
			DatabaseConnection dbConnection = new DatabaseConnection();
			if(requestId.startsWith("LMT-"))
			{
				System.out.println("requestId: "+requestId);
				requestId = requestId.substring(4, requestId.length()).toString().trim();
				System.out.println("requestId: "+ requestId);
				//dbConnection.approveRequest(Integer.parseInt(requestId), oimClient)
				if(dbConnection.rejectRequest(Integer.parseInt(requestId), oimClient,reason))
				{
					System.out.println("Reject Successfully.!!");
					out.write("Reject Successfully.!!");
				}
				else
				{
					System.out.println("Some issue to process this request. ");
					out.write("Some issue to process this request. ");
				}
			}
			else {
			
			
			System.out.println("Reason: "+reason);
			int reqResponse = 0;
			
			try {
				UpdateRequestRestAPI updateRequestAPI = new UpdateRequestRestAPI();
				reqResponse=updateRequestAPI.rejectRequest(requestId.toString().trim(), getRequestTaskID(requestId.toString().trim()), userLogin, password,reason);
				System.out.print("STATUS RESPONSE"+reqResponse);
				StatusCode st=new StatusCode();
				System.out.print(st.statusCode(reqResponse));
				out.print(st.rejectStatusCode(reqResponse));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(requestId);
			System.out.println(userLogin);
			System.out.println(password);
			 
		}
		}
		else if (action.equals("claimReq"))
		{
			int reqResponse=0;
			try {
				UpdateRequestRestAPI updateRequestAPI = new UpdateRequestRestAPI();
				reqResponse=updateRequestAPI.claimRequest(requestId.toString().trim(), getRequestTaskID(requestId.toString().trim()), userLogin, password);
				System.out.print("STATUS RESPONSE"+reqResponse);
				StatusCode st=new StatusCode();
				System.out.print(st.statusCode(reqResponse));
				out.print(st.claimStatusCode(reqResponse));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(requestId);
			System.out.println(userLogin);
			System.out.println(password);
			 
		}
		//claimBtn
			
	}
	private void getPendingApprovals(String userLogin,PrintWriter out)
	{
		
		String approvalDetails ="";
		System.out.println("Controller Called");
		SOADBConnection soaDB ;
		Connection con =  SOADBConnection.connectSOADB();
		 
		// step4 execute query
		//like 'kashif.arif%' and STATE='ASSIGNED'"
		try {
			PreparedStatement pstmt = con.prepareStatement(PredefinedConst.PENDING_APPROVALS_QUERY);
			pstmt.setString(1, userLogin.trim().toLowerCase()+"%");
			pstmt.setString(2, "ASSIGNED");
			//ResultSet rs = stmt.executeQuery(sqlPendingApproval);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				
				approvalDetails+="<tr>"
			               +"<td style='width:500px' id='popdetails' name='popdetails' class='popdetails'><a href='"+rs.getString("REQ_ID")+"' name='lnkViews' data-toggle='modal' data-target='#MyPopup' class='popdetails'> "+ rs.getString("REQUEST_TYPE") + " Approval Request ID: "+rs.getString("REQ_ID")+"</a></td>"
			               +"<td>"+rs.getString("ITEM_NAME")+" </td>" 
			               +"<td>"+rs.getString("STATE")+" </td>" 
			               +"<td> 26-01-2020 </td>"  
			            +"<td>"  
			               +"<ul>"+ 
			            "<li><a href='\"+rs.getString(\"REQ_ID\")+\"' id='aprvbtn' name='aprvbtn' data-toggle='tooltip' data-placement='top' data-target='#MyPopup2222' title='Approve'><span class='label label-success'><i class='fa fa-check'></i></span></a></li>" + 
			            "<li><a href='\"+rs.getString(\"REQ_ID\")+\"' id='rjctBtn' name='rjctBtn'  data-toggle='tooltip' data-placement='top' data-target='#MyPopup2222' title='Reject'><span class='label label-danger'><i class='fa fa-remove'></i></span></a></li>" + 
			            "</ul>\"</td></tr>";
				/*
				System.out.print(rs.getString("ASSIGNEES") + "  " + rs.getString("ASSIGNEEGROUPSDISPLAYNAME") + "  "
						+ rs.getString("ASSIGNEEGROUPS") + " ");
				System.out.print(rs.getString("ASSIGNEEUSERS") + "  " + rs.getString("CREATOR") + "  "
						+ rs.getString("APPROVERS") + " ");
				System.out.print(rs.getString("REQUEST_TYPE") + "  " + rs.getString("ITEM_NAME") + "  "
						+ rs.getString("TITLE") + " ");
				System.out.print(rs.getString("IDENTIFICATIONKEY") + "  " + rs.getString("REQ_ID") + "  "
						+ rs.getString("TASKID") + " ");
				System.out.println(); */
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		out.print(approvalDetails);	
	}
	private void getApprovalDetails(String requestId,PrintWriter out)
	{
		
		String approvalDetails ="";
		System.out.println("Controller Called");
		SOADBConnection soaDB ;
		Connection con =  SOADBConnection.connectSOADB();
		 
		// step4 execute query
		//like 'kashif.arif%' and STATE='ASSIGNED'"
		try {
			
			System.out.println(requestId.toString().trim());
			PreparedStatement pstmt = con.prepareStatement(PredefinedConst.TRACK_REQUEST_QUERY);
			pstmt.setString(1, requestId.toString().trim().toLowerCase());
		//	pstmt.setString(2, "ASSIGNED");
			System.out.println(requestId);
			System.out.println(pstmt);
			//ResultSet rs = stmt.executeQuery(sqlPendingApproval);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				
				String assignees=rs.getString("ASSIGNEES");
				if (assignees!=null) {
				assignees= assignees.split(",")[0].toUpperCase();
				}
				String approver=rs.getString("APPROVERS");
				if (approver!=null) {
					approver= (approver.substring(approver.lastIndexOf(",") + 1).toUpperCase());
				}
				String outcome=rs.getString("STATE");
				if(outcome==null)
				{
					outcome=rs.getString("OUTCOME");
					System.out.println(outcome);
				}
				// approvalDetails+=" CREATORddddddddddd " +rs.getString("CREATOR") +" REQ_ID " +rs.getString("REQ_ID") +" REQUEST_TYPE "+rs.getString("REQUEST_TYPE")
				 //+" STATE "+ rs.getString("STATE")  ;
				
				approvalDetails+=
						
		       	
		        "<tr>" 
			               //+"<td style='width:150px ;font-size: 14px'>"+rs.getString("REQ_ID")+"</td>"
			               +"<td style='width:150px ;font-size: 14px'><a href='TrackRequest.jsp'>"+rs.getString("REQ_ID")+"</a></td>"
			               +"<td style='width:150px ;font-size: 14px'>"+rs.getString("REQUEST_TYPE")+" </td>"
			               +"<td style='width:150px ;font-size: 14px'>"+approver+" </td>" 
			               +"<td style='width:150px ;font-size: 14px'>"+assignees+" </td>" 
			               +"<td style='width:150px ;font-size: 14px'>" +outcome+ "</td>"  
			    +"<tr>";
			               
			   
				 /*
				System.out.print(rs.getString("ASSIGNEES") + "  " + rs.getString("ASSIGNEEGROUPSDISPLAYNAME") + "  "
						+ rs.getString("ASSIGNEEGROUPS") + " ");
				System.out.print(rs.getString("ASSIGNEEUSERS") + "  " + rs.getString("CREATOR") + "  "
						+ rs.getString("APPROVERS") + " ");
				System.out.print(rs.getString("REQUEST_TYPE") + "  " + rs.getString("ITEM_NAME") + "  "
						+ rs.getString("TITLE") + " ");
				System.out.print(rs.getString("IDENTIFICATIONKEY") + "  " + rs.getString("REQ_ID") + "  "
						+ rs.getString("TASKID") + " ");
				System.out.println(); */
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		out.print(approvalDetails);	
	}
	public void getPendingApprovals(String userName, String password,PrintWriter out, OIMClient oimClient) {
		String approvalDetails = null;
		try {
			// byte[] encodedBytes =
			// Base64.getEncoder().encode("xelsysadm:Hblpoc_1234".getBytes());
			String base64encodedString = Base64.getEncoder().encodeToString((userName+":"+password).getBytes("utf-8"));

			// String credential = Base64.encodeToString(
			// (userName+":"+password).getBytes("UTF-8"), Base64.DEFAULT);
			System.out.println(base64encodedString);
			
			for(int c=1;c<=5000;c=c+50)
			{
			URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/requests?view=pendingApprovals&offset="+c+"&limit="+(c+49));
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			//con.setRequestProperty("X-Requested-By", userName);
			con.setRequestProperty("uuid", userName);
			con.setRequestProperty("Authorization", "Basic "+base64Encoder(userName,password));
			/* Payload support */
			int status = con.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			System.out.println("Response status: " + status);
			System.out.println(content.toString());
			OIMUtils oimUtils = new OIMUtils();
			JSONObject obj = new JSONObject(content.toString());

			List<String> list = new ArrayList<String>();
			JSONArray array = obj.getJSONArray("requests");
			System.out.println("Request length:"+array.length());
			if(array.length() == 0)
			{
				System.out.println("Break called");
				break;
			}
			
			for(int i = 0 ; i < array.length() ; i++){
				String date="";
				System.out.println("Request ID:"+array.getJSONObject(i).getString("id"));
				date =array.getJSONObject(i).getString("created");
				//String newDate=date.replaceAll("[A-Z]", "");
				String newDate = date.substring(0, date.indexOf("T"));
				approvalDetails+="<tr>"
			               +"<td style='width=500px' id='popdetails' name='popdetails' class='popdetails'><a href='"+array.getJSONObject(i).getString("id")+"' name='lnkViews' data-toggle='modal' data-target='#MyPopup' class='popdetails'> "+array.getJSONObject(i).getString("id")+"</a></td>"
			               +"<td>"+newDate+"</td>" 
			               +"<td>"+array.getJSONObject(i).getString("state")+" </td>" 
			            
			               ////////////////////comment to show user pending requests
			              // +"<td><a href='UserDetailsServlet?userKey="+oimUtils.getUserKey(array.getJSONObject(i).getString("creator").toString().trim(), oimClient)+"'>"+array.getJSONObject(i).getString("creator")+" </a></td>"
			               //////////////////////////////////////////////////////
			               +"<td>"+array.getJSONObject(i).getString("creator")+"</td>"
			               //+"<td><a href='UserDetailsServlet?userKey="+array.getJSONObject(i).getString("creator").toString().trim()+"'>"+array.getJSONObject(i).getString("creator")+" </a></td>"

			             +"<td> "+array.getJSONObject(i).getString("assignee")+" </td>"  
			               +"<td>" + 
				            "  <ul class='list-inline'>" + 
				            "    <li> <a href='"+array.getJSONObject(i).getString("id")+"' id='aprvbtn' name='aprvbtn' data-toggle='modal' data-placement='top' data-target='#MyPopup2222' title='Approve'><span class='label label-success'><i class='fa fa-check'></i></span></a></li>" + 
				            "	 <li> <a href='"+array.getJSONObject(i).getString("id")+"' id='rjctBtn' name='rjctBtn'  data-toggle='modal' data-placement='top' data-target='#rejectReason' title='Reject'><span class='label label-danger'><i class='fa fa-remove'></i></span></a></li>" + 
				            
				            "  </ul>" + 
				            "</div></td></tr>";
				
			    //list.add(array.getJSONObject(i).getString("id"));
			    
			    System.out.println("\nRequest ID : "+i);
			    
			    System.out.println(array.getJSONObject(i).getString("id"));
			    System.out.println(array.getJSONObject(i).getString("state"));
			    System.out.println(array.getJSONObject(i).getString("status"));
			    System.out.println(array.getJSONObject(i).getString("assignee"));
			    System.out.println(array.getJSONObject(i).getString("taskId"));
			    System.out.println(array.getJSONObject(i).getString("creator"));
			    
			    System.out.println(array.getJSONObject(i).getString("created"));
			    System.out.println(array.getJSONObject(i).getString("title"));
			    System.out.println("--------");
			}
			}
			
			OIMUtils oimUtils = new OIMUtils();
			
			List<RoleUser> rolesList = oimUtils.fetchUserRole(oimUtils.getUserKey(userName, oimClient), oimClient);
			boolean isLamTeam = false;
			for(RoleUser roleS: rolesList)
			{
				if(roleS.getRoleName().equals("LAM TEAM"))
				{
					isLamTeam = true;
					break;
				}
			}
			
			DatabaseConnection dbConnection = new DatabaseConnection();
			LimitRequestObj limtReqObj;
			
			List<LimitRequestObj> limtReqList  = null;
			limtReqList = dbConnection.getPendingApprovalsByUser(oimUtils.getUserKey(userName, oimClient).toString().trim(), isLamTeam);
			
			if(limtReqList!=null)
			{
				String currentApprover = null;
				
				for(LimitRequestObj lmts : limtReqList )
				{
					
					if(lmts.getCURRENT_APPROVER().toString().trim().equals("LAM TEAM"))
					{
						currentApprover = lmts.getCURRENT_APPROVER().toString().trim();
					}
					else
					{
						currentApprover = oimUtils.getUserLogin( lmts.getCURRENT_APPROVER().toString().trim(), oimClient);
					}
					approvalDetails+="<tr>"
				               +"<td style='width=500px' id='popdetails' name='popdetails' class='popdetails'><a href='"+"LMT-"+lmts.getRequestId()+"' name='lnkViews' data-toggle='modal' data-target='#MyPopup' class='popdetails'> "+"LMT-"+lmts.getRequestId()+"</a></td>"
				               +"<td>"+lmts.getCREATE_DATE()+"</td>" 
				               +"<td>ASSIGNED </td>" 
				               +"<td><a href='#'>"+oimUtils.getUserLogin(lmts.getREQUESTER().toString().trim(), oimClient) +" </a></td>"
				               +"<td> "+currentApprover +" </td>"  
				               +"<td> <ul class='list-inline'>" + 
					            "    <li> <a href='"+"LMT-"+lmts.getRequestId()+"' id='aprvbtn' name='aprvbtn' data-toggle='modal' data-placement='top' data-target='#MyPopup2222' title='Approve'><span class='label label-success'><i class='fa fa-check'></i></span></a></li>" + 
					            "	 <li> <a href='"+"LMT-"+lmts.getRequestId()+"' id='rjctBtn' name='rjctBtn'  data-toggle='modal' data-placement='top' data-target='#rejectReason' title='Reject'><span class='label label-danger'><i class='fa fa-remove'></i></span></a></li>" + 
					            
					            "  </ul>" 
				               +"</td></tr>";
				}
			}
			
			
			

		} catch (HTTPException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
		
		out.print(approvalDetails);	
	}
	public String base64Encoder(String username, String password)
	{
		Base64.Encoder encoder = Base64.getEncoder();
		// Creating byte array
		byte byteArr[] = { 1, 2 };
		// encoding byte array
		byte byteArr2[] = encoder.encode(byteArr);
		System.out.println("Encoded byte array: " + byteArr2);
		byte byteArr3[] = new byte[5]; // Make sure it has enough size to store copied bytes
		int x = encoder.encode(byteArr, byteArr3); // Returns number of bytes written
		System.out.println("Encoded byte array written to another array: " + byteArr3);
		System.out.println("Number of bytes written: " + x);

		// Encoding string
		String str = encoder.encodeToString((username+":"+password).getBytes());
		System.out.println("Encoded string: " + str);
		return str;

	}

	private void requestDetials(String trackID,PrintWriter out,String userLogin,String password) throws IOException {
		// TODO Auto-generated method stub
		URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/requests/"+trackID);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		//con.setRequestProperty("X-Requested-By", userName);
		//con.setRequestProperty("uuid", userName);
		con.setRequestProperty("Authorization", "Basic "+base64Encoder(userLogin,password));
		/* Payload support */
		int status = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		con.disconnect();
		System.out.println("Response status: " + status);
		System.out.println(content.toString());
		JSONObject obj = new JSONObject(content.toString());
		
		JSONArray array = obj.getJSONArray("reqTargetEntities");
		
		
		for(int i=0;i<array.length();i++)
		{
			System.out.println(array.getJSONObject(i));
			String appFormData=array.getJSONObject(i).getString("appFormData");
			String entityType=array.getJSONObject(i).getString("entityType");
			String entityId=array.getJSONObject(i).getString("entityId");
			System.out.println("entityType :"+entityType);
			System.out.println("entityId :"+entityId);
			System.out.println(appFormData);
			String formValues = "";
			if(entityType.equals("ApplicationInstance")) {
			String appFormData1= appFormData.replace("[", "")
					.replace("]", "")
					.replace("\"", "")
					.replace("{", "")
					.replace("}", "");
			Map<String, String> hashMap1
            = new HashMap<String, String>();
			Map<String, String> hashMap2
            = new HashMap<String, String>();
			System.out.println(appFormData1);
			String  appFormData2[]=appFormData1.split(",");
			for(int k=0;k<appFormData2.length;k++)
			{
				String appFormData3[] = appFormData2[k].split(":");
			//	String stuRollNo = appFormData3[0].trim();
	            String stuName = appFormData3[1].trim();
			//	hashMap1.put(stuRollNo, stuName);
			//	hashMap1.put(stuRollNo, stuName);
				k++;
				 appFormData3 = appFormData2[k].split(":");
				String stuRollNo1 = appFormData3[0].trim();
	             String stuName1 = appFormData3[1].trim();
			//	hashMap2.put(stuRollNo1, stuName);
				hashMap2.put(stuName,stuName1);
			}
			System.out.println("HashMap1"+hashMap1);
			System.out.println("HashMap2"+hashMap2);
			
			
			for (Map.Entry<String, String> set : hashMap2.entrySet()) {
				 //   System.out.println(set.getKey() + " = " + set.getValue());
				    formValues+=set.getKey()+" = "+set.getValue()+"<br>";
			}
			System.out.println(hashMap2.get("Owner"));
			System.out.print(formValues);
			}
			String requestDetails = null;
			requestDetails+="<tr>"
						
						+"<td >"+entityType+"</td>"
						+"<td >"+entityId+"</td>"
						+"<td >"+formValues+"</td>"
		         + "</tr>";
			if(requestDetails.isEmpty())
			{
				requestDetails="";
				out.print(requestDetails);
			}
			else {
			out.print(requestDetails);
			}
		}
			
		}
		
	
	
	
	private String getRequestTaskID(String requestID)
	{
		SOADBConnection soaDB ;
		Connection con =  SOADBConnection.connectSOADB();
		String taskId = null;
		try {
			PreparedStatement pstmt = con.prepareStatement(PredefinedConst.TASK_ID_QUERY);
			pstmt.setString(1, requestID.toString().trim().toLowerCase());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				taskId = rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taskId;
	}

}
