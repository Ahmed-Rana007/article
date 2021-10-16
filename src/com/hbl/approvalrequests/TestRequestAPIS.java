package com.hbl.approvalrequests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.xml.ws.http.HTTPException;

import com.bea.httppubsub.json.JSONArray;
import com.bea.httppubsub.json.JSONObject;

import sun.net.www.protocol.http.HttpURLConnection;

public class TestRequestAPIS {

	public static void main(String[] args) {
		TestRequestAPIS tra = new TestRequestAPIS();
		//tra.approveRequest("50029", "b0dd5cb5-ef79-4a37-9cef-2f65731df905", "majid", "Test@1234");
		
		tra.getPendingApprovals("xelsysadm","Hblpoc_1234");

	}
	public void approveRequest(String reqeustId, String taskID, String userName, String password) {
		try {
			// byte[] encodedBytes =
			// Base64.getEncoder().encode("xelsysadm:Hblpoc_1234".getBytes());
			String base64encodedString = Base64.getEncoder().encodeToString("xelsysadm:password".getBytes("utf-8"));

			// String credential = Base64.encodeToString(
			// (userName+":"+password).getBytes("UTF-8"), Base64.DEFAULT);
			System.out.println(base64encodedString);

			URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/requests/50029");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("X-Requested-By", "xelsysadm");
			con.setRequestProperty("Authorization", "Basic "+base64Encoder(userName,password));
			/* Payload support */
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("{\n");
			out.writeBytes("  \"requestId\": \"50029\",\n");
			out.writeBytes("  \"taskId\": \"b0dd5cb5-ef79-4a37-9cef-2f65731df905\",\n");
			out.writeBytes("  \"action\": \"claim\"\n");
			 
			out.writeBytes("}");
			out.flush();
			out.close();

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
		} catch (HTTPException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	public void getPendingApprovals(String userName, String password) {
		try {
			// byte[] encodedBytes =
			// Base64.getEncoder().encode("xelsysadm:Hblpoc_1234".getBytes());
			String base64encodedString = Base64.getEncoder().encodeToString((userName+":"+password).getBytes("utf-8"));

			// String credential = Base64.encodeToString(
			// (userName+":"+password).getBytes("UTF-8"), Base64.DEFAULT);
			System.out.println(base64encodedString);

			URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/requests?view=pendingApprovals");
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
			for(int i = 0 ; i < array.length() ; i++){
			    list.add(array.getJSONObject(i).getString("id"));
			    
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
}
