package com.hbl.approvalrequests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;
import javax.xml.ws.http.HTTPException;

import com.bea.httppubsub.json.JSONArray;
import com.bea.httppubsub.json.JSONObject;
import com.gnl.servlet.PendingApprovalsServlet;
import com.hbl.selfservice.LoginToOIM;
import com.hbl.selfservice.OIMUtils;
import com.hbl.provisioning.ProvisioningAccounts;
import oracle.iam.platform.OIMClient;
import oracle.iam.request.api.RequestService;
import oracle.iam.request.exception.NoRequestPermissionException;
import oracle.iam.request.exception.RequestServiceException;
import oracle.iam.request.vo.Request;
import oracle.iam.request.vo.RequestConstants;
import oracle.iam.request.vo.RequestSearchCriteria;
import weblogic.net.http.HttpURLConnection;

public class ApprovalCounter {

	
	public static String pendingApprovalsCount(String userName, String password,OIMClient oimClient) {
		int count = 0;
		String count_1 ="";
		
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
				count++;
			}
			System.out.print("Pendong COUNT"+count);
			 count_1 = String.valueOf(count);
			 System.out.print("Pendong COUNT1   "+count_1);
		}catch (HTTPException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count_1;
		}
	public static String base64Encoder(String username, String password)
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
	
	//
	public String getPendingRequestCount(OIMClient oimClient, String userLogin) throws NoRequestPermissionException, RequestServiceException
    {
		int count=0;
		String count_1="";
		String userKey="";
		String requestIDarray[] = null;
		LoginToOIM connection=new LoginToOIM();
		ProvisioningAccounts pa = new ProvisioningAccounts();
		
     	
		try {
			 userKey = pa.getUserKey(userLogin, oimClient);
			 System.out.println("User Key---"+userKey);
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
	 
	    	RequestService reqsrvc = oimClient.getService(RequestService.class);
	    	try {
				List <Request> r=reqsrvc.getRequestsForUser(userKey, searchCriteria, null);
				
				for(Request a: r)
				{
					if(a.getRequestStatus().equals("Request Awaiting Approval")) {
						
					++count;
					}
				}
				
				count_1 = String.valueOf(count);
				
			} catch (RequestServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    
	    	
	 
	    	System.out.println(count_1);
	    	return count_1;
	    	
		
    }

	//
	
}
