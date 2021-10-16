package com.gnl.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bea.httppubsub.json.JSONArray;
import com.bea.httppubsub.json.JSONObject;


import weblogic.net.http.HttpURLConnection;

/**
 * Servlet implementation class RequestDetailServlet
 */
@WebServlet("/RequestDetailServlet")
public class RequestDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestDetailServlet() {
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
	
		
		String trackID=request.getParameter("trackID");
		System.out.print("TID:"+trackID);
		String action = request.getParameter("action");
		String userLogin = request.getParameter("userLogin");
		HttpSession  session=request.getSession();
		PrintWriter out = response.getWriter();
		userLogin = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		
		System.out.println("Request Details Called");
		
		if(action.equals("requestDetails"))
		{
			System.out.println();
			requestDetials(trackID,out,userLogin,password);
			
		}
		
		
		
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
		
	/*	JSONArray array1 = obj.getJSONArray("requestTaskDetails");
		for(int i=0;i<array1.length();i++)
		{
			System.out.println(array1.getJSONObject(i));
		}
		*/
	
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


}
