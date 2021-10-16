package com.gnl.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.http.HTTPException;

import com.bea.httppubsub.json.JSONArray;
import com.bea.httppubsub.json.JSONObject;
import com.hbl.approvalrequests.PredefinedConst;
import com.hbl.approvalrequests.SOADBConnection;
import com.hbl.approvalrequests.StatusCode;
import com.hbl.approvalrequests.UpdateRequestRestAPI;
import com.hbl.provisioning.ProvisioningAccounts;
import com.hbl.selfservice.LoginToOIM;
import com.hbl.selfservice.OIMUtils;

import oracle.iam.platform.OIMClient;
import oracle.iam.request.api.RequestService;
import oracle.iam.request.exception.NoRequestPermissionException;
import oracle.iam.request.exception.RequestServiceException;
import oracle.iam.request.vo.Request;
import oracle.iam.request.vo.RequestConstants;
import oracle.iam.request.vo.RequestSearchCriteria;
import weblogic.net.http.HttpURLConnection;

/**
 * Servlet implementation class PendingRequestServlet
 */
@WebServlet("/PendingRequestServlet")
public class PendingRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PendingRequestServlet() {
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
		String userakey;
		String action = request.getParameter("action");
		String userLogin = request.getParameter("userLogin");
		
		HttpSession  session=request.getSession();
		PrintWriter out = response.getWriter();
		userLogin = (String) session.getAttribute("username");
		
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		String password = (String) session.getAttribute("password");
		String requestId = request.getParameter("requestId");
		System.out.println(requestId);
		String requestIdArray[] = null;
		
		ProvisioningAccounts pa = new ProvisioningAccounts();
		
		
		
		try {
			String userkey = pa.getUserKey(userLogin, oimClient);
			
			requestIdArray = getPendingRequest(oimClient,userkey);
		} catch (NoRequestPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RequestServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(action.equals("approvals"))
		{
			//getPendingApprovals(userLogin,out);
			//getPendingApprovals(userLogin, password,out,oimClient);
			
			//For Test
			
			getApprovalDetails(requestIdArray, out);
			
		}else if (action.equals("approvalsDetails"))
		{
			 
			getApprovalDetails(requestIdArray, out);
		}
			
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
			            +"<td>  <div class='btn-group'>" + 
			            "  <button class='btn btn-mini'>Action</button>" + 
			            "  <button class='btn btn-mini dropdown-toggle' data-toggle='dropdown'>" + 
			            "    <span class='caret'></span>" + 
			            "  </button>\r\n" + 
			            "  <ul class='dropdown-menu'>\r\n" + 
			            "    <li> <a href='"+rs.getString("REQ_ID")+"' id='aprvbtn' name='aprvbtn' data-toggle='tooltip' data-target='#MyPopup2222'> Approve </a></li>" + 
			            "	<li> <a href='"+rs.getString("REQ_ID")+"' id='rjctBtn' name='rjctBtn' data-toggle='tooltip' data-target='#MyPopup2222' > Reject </a></li>" + 
			            "	<li> <a href='"+rs.getString("REQ_ID")+"' id='reAsgnBtn' name='reAsgnBtn' data-toggle='tooltip' data-target='#MyPopup2222'> Re Assign </a></li>" + 
			            "	<li> <a href='"+rs.getString("REQ_ID")+"' id='claimBtn' name='claimBtn' data-toggle='tooltip' data-target='#MyPopup2222'> Claim </a></li>" + 

			            "  </ul>" + 
			            "</div></td></tr>";
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
	
	public String[] getPendingRequest(OIMClient oimClient, String userKey) throws NoRequestPermissionException, RequestServiceException
    {
		String requestIDarray[] = null;
		LoginToOIM connection=new LoginToOIM();
     	
		try {
			
			oimClient = connection.loggedIntoOIM("XELSYSADM", "Hblpoc_1234");
		} catch (LoginException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    
	        
	    	 
	    	 System.out.println("Start");
	    	RequestSearchCriteria searchCriteria = new RequestSearchCriteria();
	    	searchCriteria.setConjunctionOp(RequestSearchCriteria.Operator.AND);
	    	//searchCriteria.addExpression(RequestConstants.REQUESTER_USER_KEY,"*",RequestSearchCriteria.Operator.CONTAIN);
	    	searchCriteria.addExpression(RequestConstants.REQUESTER_KEY,userKey,RequestSearchCriteria.Operator.CONTAIN);
	    	Map <String, Object> config = new HashMap <String,Object>();
	    	config.put(RequestConstants.SEARCH_SORTORDER,RequestConstants.SortOrder.ASCENDING);
	    	config.put(RequestConstants.SEARCH_SORTBY, RequestConstants.REQUEST_ID);
	   /* 	
	    	//Search the requests based on the above criteria and config
	    	//returns an Request service instance*/
	    	RequestService reqsrvc = oimClient.getService(RequestService.class);
	    	try {
				List <Request> r=reqsrvc.getRequestsForUser(userKey, searchCriteria, null);
				int count=0;
				for(Request a: r)
				{
					if(a.getRequestStatus().equals("Request Awaiting Approval")) {
						
					System.out.println(++count);
					}
				}
				int i=0;
				String requestID[]=new String[count];
				
				
				for(Request a: r)
				{
					if(a.getRequestStatus().equals("Request Awaiting Approval")) {
						
					System.out.println(++count);
					requestID[i]=a.getRequestID();
					i++;
					requestIDarray=requestID;
					}
					System.out.println("REQUEST RAISED FOR USER"+a);
					
					System.out.println("Reqyest ID:   "+a.getRequestID());
					System.out.println("Requester Key"+a.getRequesterKey());
					System.out.println("Requester Status:  "+a.getRequestStatus());    //Request Awaiting Approval
					
				}
				
			} catch (RequestServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    
	    	
	 
	    	System.out.println("End");
	    	return requestIDarray;
		
    }
	
	
	private void getApprovalDetails(String requestId[],PrintWriter out)
	{
		
		String approvalDetails ="";
		System.out.println("Controller Called");
		SOADBConnection soaDB ;
		Connection con =  SOADBConnection.connectSOADB();
		 
		// step4 execute query
		//like 'kashif.arif%' and STATE='ASSIGNED'"
		try {
			for(int i=0;i<requestId.length;i++)
			{
			
			System.out.println(requestId[i].toString().trim());
			PreparedStatement pstmt = con.prepareStatement(PredefinedConst.TRACK_REQUEST_QUERY);
			pstmt.setString(1, requestId[i].toString().trim().toLowerCase());
		//	pstmt.setString(2, "ASSIGNED");
			System.out.println(requestId[i]);
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
			               +"<td style='width:150px ;font-size: 14px'>"+rs.getString("REQ_ID")+"</td>"
			            		   +"<td style='width:150px ;font-size: 14px'>"+rs.getString("CREATEDDATE")+" </td>" 
			               +"<td style='width:150px ;font-size: 14px'>"+rs.getString("REQUEST_TYPE")+" </td>" 
			            		    +"<td>"+rs.getString("ITEM_NAME")+" </td>"
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

			URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/requests?view=pendingApprovals&offset=1&limit=1000");
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
			
			JSONObject obj = new JSONObject(content.toString());

			List<String> list = new ArrayList<String>();
			JSONArray array = obj.getJSONArray("requests");
			OIMUtils oimUtils = new OIMUtils();
	
			
			for(int i = 0 ; i < array.length() ; i++){
				
				approvalDetails+="<tr>"
			               +"<td style='width=500px' id='popdetails' name='popdetails' class='popdetails'><a href='"+array.getJSONObject(i).getString("id")+"' name='lnkViews' data-toggle='modal' data-target='#MyPopup' class='popdetails'> "+array.getJSONObject(i).getString("id")+"</a></td>"
			               +"<td>"+array.getJSONObject(i).getString("created")+"</td>" 
			               +"<td>"+array.getJSONObject(i).getString("state")+" </td>" 
			               +"<td><a href='UserDetailsServlet?userKey="+oimUtils.getUserKey(array.getJSONObject(i).getString("creator").toString().trim(), oimClient)+"'>"+array.getJSONObject(i).getString("creator")+" </a></td>"
			               +"<td> "+array.getJSONObject(i).getString("assignee")+" </td>"  
			               +"<td>  <div class='btn-group'>" + 
				            "  <button class='btn btn-mini'>Action</button>" + 
				            "  <button class='btn btn-mini dropdown-toggle' data-toggle='dropdown'>" + 
				            "    <span class='caret'></span>" + 
				            "  </button>\r\n" + 
				            "  <ul class='dropdown-menu'>\r\n" + 
				            "    <li> <a href='"+array.getJSONObject(i).getString("id")+"' id='aprvbtn' name='aprvbtn' data-toggle='modal' data-target='#MyPopup33'> Approve </a></li>" + 
				            "	 <li> <a href='"+array.getJSONObject(i).getString("id")+"' id='rjctBtn' name='rjctBtn' data-toggle='modal' data-target='#MyPopup33' > Reject </a></li>" + 
				            "	 <li> <a href='"+array.getJSONObject(i).getString("id")+"' id='reAsgnBtn' name='reAsgnBtn' data-toggle='modal' data-target='#MyPopup33'> Re Assign </a></li>" + 
				            "	 <li> <a href='"+array.getJSONObject(i).getString("id")+"' id='claimBtn' name='claimBtn' data-toggle='modal' data-target='#MyPopup33'> Claim </a></li>" + 

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
	
	//PendingRequest Count
		//
}
