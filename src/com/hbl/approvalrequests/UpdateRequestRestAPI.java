package com.hbl.approvalrequests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;
import java.util.List;

import javax.security.auth.login.LoginException;

import com.hbl.selfservice.LoginToOIM;

import oracle.iam.platform.OIMClient;
import oracle.iam.request.api.RequestService;
import oracle.iam.request.exception.RequestServiceException;
import oracle.iam.request.vo.RequestComment;
//import weblogic.net.http.HttpsURLConnection;
//import javax.xml.ws.http.HTTPException;
import weblogic.net.http.HttpURLConnection;
import weblogic.db2util.externals.org.bouncycastle.util.encoders.Base64Encoder;

public class UpdateRequestRestAPI {

	public static void main(String[] args) {
		UpdateRequestRestAPI requestApiCall = new UpdateRequestRestAPI();
		//requestApiCall.approveRequest("48065", "09a806a0-e985-47f4-b909-36db7fbb4dc5", "xelsysadm", "Hblpoc_1234");
		//requestApiCall.claimRequest("50029", "b0dd5cb5-ef79-4a37-9cef-2f65731df905", "majid", "Test@1234");
		requestApiCall.addRequestComments("121093", "ZAHOOR.KHAN", "Test@1234", "TESTING");
		//45031
		//requestApiCall.rejectRequest("45031", "f628098d-88a4-47ca-bece-56260c8bb5d5", "xelsysadm", "Hblpoc_1234");
		//requestApiCall.base64Encoder("xelsysadm", "Hblpoc_1234");

	}

	public int approveRequest(String reqeustId, String taskID, String userName, String password) {
			int status = 0;
		try {
			System.out.println("Task ID: "+taskID);
			System.out.println("reqeustId ID: "+reqeustId);
			System.out.println("Username: "+userName);
			System.out.println("Password: "+password);
			// byte[] encodedBytes =
			// Base64.getEncoder().encode("xelsysadm:Hblpoc_1234".getBytes());
			String base64encodedString = Base64.getEncoder().encodeToString("xelsysadm:password".getBytes("utf-8"));

			// String credential = Base64.encodeToString(
			// (userName+":"+password).getBytes("UTF-8"), Base64.DEFAULT);
			System.out.println(base64encodedString);

			URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/requests/"+reqeustId.toString().trim());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("X-Requested-By", userName);
			con.setRequestProperty("Authorization", "Basic " + base64Encoder(userName,password));
			// con.setRequestProperty("Auhtorization", "Basic"+encodedBytes);

			/* Payload support */
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("{\n");
			out.writeBytes("  \"requestId\": \""+reqeustId+"\",\n");
			out.writeBytes("  \"taskId\": \""+taskID+"\",\n");
			out.writeBytes("  \"action\": \"approve\"\n");
			out.writeBytes("}");
			out.flush();
			out.close();

		   status = con.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			System.out.println("Response status: " + status);
			System.out.println(content.toString());
		}  catch (Exception ex) {
			ex.printStackTrace();
		}
		return status;

	}

	public int claimRequest(String reqeustId, String taskID, String userName, String password) {
		int status = 0;
		try {
			// byte[] encodedBytes =
			// Base64.getEncoder().encode("xelsysadm:Hblpoc_1234".getBytes());
			String base64encodedString = Base64.getEncoder().encodeToString("xelsysadm:password".getBytes("utf-8"));

			// String credential = Base64.encodeToString(
			// (userName+":"+password).getBytes("UTF-8"), Base64.DEFAULT);
			System.out.println(base64encodedString);

			URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/requests/"+reqeustId);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("X-Requested-By", userName);
			con.setRequestProperty("Authorization", "Basic " + base64Encoder(userName,password));
			// con.setRequestProperty("Auhtorization", "Basic"+encodedBytes);

			/* Payload support */
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("{\n");
			out.writeBytes("  \"requestId\": \""+reqeustId+"\",\n");
			out.writeBytes("  \"taskId\": \""+taskID+"\",\n");
			out.writeBytes("  \"action\": \"claim\"\n");
			out.writeBytes("}");
			out.flush();
			out.close();

		    status = con.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			System.out.println("Response status: " + status);
			System.out.println(content.toString());
		}
		 catch (Exception ex) {
			ex.printStackTrace();
		}
		return status;

	}
	public int rejectRequest(String reqeustId, String taskID, String userName, String password,String reason) {
		int status = 0;
		try {
			System.out.println("Task ID: "+taskID);
			System.out.println("reqeustId ID: "+reqeustId);
			// byte[] encodedBytes =
			// Base64.getEncoder().encode("xelsysadm:Hblpoc_1234".getBytes());
			String base64encodedString = Base64.getEncoder().encodeToString("xelsysadm:password".getBytes("utf-8"));

			// String credential = Base64.encodeToString(
			// (userName+":"+password).getBytes("UTF-8"), Base64.DEFAULT);
			System.out.println(base64encodedString);

			URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/requests/"+reqeustId.toString().trim());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("X-Requested-By", "xelsysadm");
			//con.setRequestProperty("Authorization", "Basic " + base64Encoder(userName,password));
			con.setRequestProperty("Authorization", "Basic " + base64Encoder("XELSYSADM","Hblpoc_1234"));
			// con.setRequestProperty("Auhtorization", "Basic"+encodedBytes);

			/* Payload support */
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("{\n");
			out.writeBytes("  \"requestId\": \""+reqeustId+"\",\n");
			out.writeBytes("  \"taskId\": \""+taskID+"\",\n");
			out.writeBytes("  \"action\": \"WITHDRAW\",\n");
			out.writeBytes("  \"actionComment\": \""+reason+"\"\n");
			out.writeBytes("}");
			out.flush();
			out.close();

		    status = con.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			System.out.println("Response status: " + status);
			System.out.println(content.toString());
		}  catch (Exception ex) {
			ex.printStackTrace();
		}
		return status;
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
	 /////////////////////////add comments in request////////////////
private void addRequestComments(String trackID,String userlogin,String upassword,String comments)
    {
		LoginToOIM connection=new LoginToOIM();
		OIMClient oimClient = null;
	
		try {
			//oimClient = connection.loggedIntoOIM("XELSYSADM", "New@12345");
			oimClient = connection.loggedIntoOIM(userlogin, upassword);
		} catch (LoginException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   		
	    	 System.out.println("Start");
	    	
	    	RequestService reqsrvc = oimClient.getService(RequestService.class);
	   
	    	try {
	    		RequestComment requestComment = new RequestComment();
	    		requestComment.setRequestComment("Testing");
				reqsrvc.addRequestComment(trackID,requestComment);
				System.out.print("Success");
			} catch (oracle.iam.request.exception.AccessDeniedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RequestServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	try {
			List<RequestComment> commentList = reqsrvc.getRequestComments(trackID);
			for(RequestComment s:commentList)
			{
				System.out.println(s);
			}
			} catch (RequestServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	System.out.println("End");
	    
		
    
		
	}

}
