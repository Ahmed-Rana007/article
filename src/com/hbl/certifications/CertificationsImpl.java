package com.hbl.certifications;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.xml.ws.http.HTTPException;

import com.bea.httppubsub.json.JSONArray;
import com.bea.httppubsub.json.JSONObject;
import com.hbl.equation.limits.DatabaseConnection;
import com.hbl.equation.limits.servlet.LimitRequestObj;
import com.hbl.selfservice.OIMUtils;
import com.hbl.selfservice.objects.RoleUser;

import oracle.iam.platform.OIMClient;
//import weblogic.net.http.HttpURLConnection;

public class CertificationsImpl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrintWriter out = null;
		CertificationsImpl certImpl = new CertificationsImpl();
		// certImpl.getAssignedCertificationsTask(out, "muaaz", "Test@432");
		// certImpl.getAssignedCertificationsTaskLinks();
		// 191/tasks/e8ce1818-ea12-4fcf-b870-8ac12f7d3866/lineitems/173
		//certImpl.getCertificationItems("muaaz", "Test@432", "191", "e8ce1818-ea12-4fcf-b870-8ac12f7d3866", "173");
		// certImpl.getAssignedCertificationsUsers("191" ,
		// "e8ce1818-ea12-4fcf-b870-8ac12f7d3866");
		
		// certImpl.certifyLineItem("muaaz", "Test@432", "252", "291be361-a6a3-4c02-8c23-427053a0266a", "189", "226","");
		 certImpl.getPercentage("akhter", "Test@1234", "294", "f133d064-4097-4da3-93a7-608dac711e02");
	}

	void getAssignedCertificationsTask(PrintWriter out, String username, String password) {
		try {
			URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/certifications");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("username", "muaaz");
			// con.setRequestProperty("password", "Test@1234");
			con.setRequestProperty("Authorization", "Basic " + base64Encoder(username, password));
			int status = con.getResponseCode();
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

			JSONObject obj = new JSONObject(content.toString());

			List<String> list = new ArrayList<String>();
			JSONArray array = obj.getJSONArray("certifications");

			String assignedCertsHTML = null;

			for (int i = 0; i < array.length(); i++) {
				String date = "";
				date = array.getJSONObject(i).getString("createdDate");
				// String newDate=date.replaceAll("[A-Z]", "");
				String newDate = date.substring(0, date.indexOf("T"));

				// list.add(array.getJSONObject(i).getString("id"));

				System.out.println("\nRequest ID : " + i);

				System.out.println(array.getJSONObject(i).getString("name"));
				System.out.println(array.getJSONObject(i).getString("type"));
				System.out.println(array.getJSONObject(i).getString("id"));
				System.out.println(array.getJSONObject(i).getString("asignee"));
				System.out.println(array.getJSONObject(i).getString("soaTaskId"));
				System.out.println(array.getJSONObject(i).getString("createdDate"));

				System.out.println(array.getJSONObject(i).getString("taskId"));
				System.out.println(array.getJSONObject(i).getString("state"));
				System.out.println("--------");
				
				
				/////////////////////////////////Get Tasks Percentages/////////////////////////////
				CertificationsImpl certificationsImpl= new CertificationsImpl();
				certificationsImpl.getPercentage(username, password, array.getJSONObject(i).getString("id"), array.getJSONObject(i).getString("taskId"));
				
			////////////////////////////////////////////////////////////////////////////////////////
				
				
				
				/*
				 * assignedCertsHTML+= "<tr>\r\n" +
				 * " <td><a data-toggle='modal' data-target='#MyPopup' href='"+array.
				 * getJSONObject(i).getString("id")+"%"+array.getJSONObject(i).getString(
				 * "taskId")
				 * +"' id='certDetailsBtn' name='certDetailsBtn' class='certDetailsBtn'><lable>"
				 * +array.getJSONObject(i).getString("name")+"</lable></a></td>\r\n" +
				 * " <td>\r\n" +
				 * " <label>"+array.getJSONObject(i).getString("type")+"</label>\r\n" +
				 * "	 </td>\r\n" + "\r\n" +
				 * " <td><label>HBL Quarterly Access Reviews</label></td>\r\n" +
				 * " <td><label>"+date+"</label></td>\r\n" +
				 * " <td><label>"+array.getJSONObject(i).getString("asignee")+
				 * "</label></td>\r\n" + " </tr>";
				 */

				String buttonvaluepercentag =null;
				String progress =null;
				if(certificationsImpl.getPercentage(username, password, array.getJSONObject(i).getString("id"), array.getJSONObject(i).getString("taskId"))<100){
					buttonvaluepercentag = "hidden";
					progress ="In Progress";
				}
				else {
					buttonvaluepercentag = "visible";
					progress ="Completed";
				}
				
				
				assignedCertsHTML += "<tr style='color:#009591'>\r\n" + "	<td>\r\n"
						+ "		<h4 class='mb-1'><a data-toggle='modal' data-target='#MyPopup' href='"
						+ array.getJSONObject(i).getString("id") + "%" + array.getJSONObject(i).getString("taskId")
						+ "' id='certDetailsBtn' name='certDetailsBtn' class='certDetailsBtn'><lable>"
						+ array.getJSONObject(i).getString("name") + "</lable></a></h4>" + "		<small>Type: "
						+ array.getJSONObject(i).getString("type") + "</small>\r\n" + "	</td>\r\n" + "	<td></td>"
						+ "	<td></td>\r\n" + "</tr>\r\n" + "<tr style='color:#009591'>\r\n" + "	<td>\r\n"
						+ "		<p class='mb-1' style='padding-top:8px;'>Organization: HBL</p><small>Date Created: "
						+ array.getJSONObject(i).getString("createdDate") + "</small>\r\n" + "	</td>\r\n"
						+ "	<td>\r\n" + "	</td>\r\n" + "	<td>\r\n" + "		<h5>Certification ID: "
						+ array.getJSONObject(i).getString("id") + "</h5>\r\n"
						+ "		<span class='label label-default'>"+progress+"</span>\r\n" + "	</td>\r\n" + "</tr>\r\n"
						+ "\r\n" + "<tr style='color:#009591'>\r\n"
						+ "	<td style='color:#009591'><small style='font-weight:900;'>Progress</small></td>\r\n"
						+ "	<td></td>\r\n" + "	<td></td>\r\n" + "</tr>\r\n" + "<tr style='color:#009591'>\r\n"
						+ "	<td><div class='progress-bar' role='progressbar' style='width: 70%;background-color:#337ab7;margin-top:10px;' aria-valuenow='85' aria-valuemin='0' aria-valuemax='100'>"+certificationsImpl.getPercentage(username, password, array.getJSONObject(i).getString("id"), array.getJSONObject(i).getString("taskId"))+"%</div></td>\r\n"
						+ "	<td></td>\r\n" + "	<td><a name='certifComplete' id='certifComplete' href='"+array.getJSONObject(i).getString("id") + "%" + array.getJSONObject(i).getString("taskId")+"' style='margin-left:5px ; visibility:"+buttonvaluepercentag+"' class='btn btn-xs hblbtn certifComplete' data-toggle='modal' data-target='#MyPopup'> Sign-Off</a></td>\r\n" + "</tr>\r\n" + "<tr>\r\n" + "	<td><hr /></td>\r\n"
						+ "	<td><hr /></td>\r\n" + "	<td><hr /></td>\r\n" + "</tr>";

			}
			out.print(assignedCertsHTML);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	boolean certifyLineItem(String username, String password, String certId, String taskId, String lineItemId, String entityId, String catagory) {
		 
		try {
			URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/certifications/"+certId+"/tasks/"+taskId+"/lineitems/"+lineItemId+"");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("X-Requested-By", "1");
			con.setRequestProperty("Authorization", "Basic "+base64Encoder(username,password));
			/* Payload support */
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("{\n");
			out.writeBytes("   \"lineItemDetails\": [\n");
			out.writeBytes("      {\n");
			out.writeBytes("         \"entityId\": "+entityId+",\n");
			out.writeBytes("         \"entityType\": \""+catagory+"\",\n");
			out.writeBytes("         \"action\": \"certify\",\n");
			out.writeBytes("         \"fields\": [\n");
			out.writeBytes("             {\n");
			out.writeBytes("                \"name\": \"comment\",\n");
			out.writeBytes("                \"value\": \"Certified\"\n");
			out.writeBytes("              }\n");
			out.writeBytes("          ]\n");
			out.writeBytes("      }\n");
			out.writeBytes("    ]\n");
			out.writeBytes(" }\n");
			out.writeBytes("");
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
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	boolean revokeLineItem(String username, String password, String certId, String taskId, String lineItemId, String entityId, String catagory,String comment) {
		 
		try {
			URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/certifications/"+certId+"/tasks/"+taskId+"/lineitems/"+lineItemId+"");			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("X-Requested-By", "1");
			con.setRequestProperty("Authorization", "Basic "+base64Encoder(username,password));
			/* Payload support */
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("{\n");
			out.writeBytes("   \"lineItemDetails\": [\n");
			out.writeBytes("      {\n");
			out.writeBytes("         \"entityId\": "+entityId+",\n");
			out.writeBytes("         \"entityType\": \""+catagory+"\",\n");
			out.writeBytes("         \"action\": \"revoke\",\n");
			out.writeBytes("         \"fields\": [\n");
			out.writeBytes("             {\n");
			out.writeBytes("                \"name\": \"comment\",\n");
			out.writeBytes("                \"value\": \""+comment+"\"\n");
			out.writeBytes("              }\n");
			out.writeBytes("          ]\n");
			out.writeBytes("      }\n");
			out.writeBytes("    ]\n");
			out.writeBytes(" }\n");
			out.writeBytes("");
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
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	boolean completeCertification(String username, String password, String certId, String taskId) {
		 
		try {
			URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/certifications/"+certId+"/tasks/"+taskId+"");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("X-Requested-By", "1");
			con.setRequestProperty("Authorization", "Basic "+base64Encoder(username,password));
			/* Payload support */
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("{\n");
			out.writeBytes("\"action\" : \"complete\",\n");
			out.writeBytes("\n");
			out.writeBytes("\"fields\" : [{\"name\": \"password\", \"value\": \""+password+"\"}]\n");
			out.writeBytes("\n");
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
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	void getAssignedCertificationsTaskLinks() {
		try {
			URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/certifications/191");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("username", "muaaz");
			// con.setRequestProperty("password", "Test@1234");
			con.setRequestProperty("Authorization", "Basic " + base64Encoder("muaaz", "Test@1234"));
			int status = con.getResponseCode();
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

			JSONObject obj = new JSONObject(content.toString());

			List<String> list = new ArrayList<String>();
			JSONArray array = obj.getJSONArray("certificationDefinitions");
			System.out.println(array);

			for (int i = 0; i < array.length(); i++) {
				String date = "";

				// list.add(array.getJSONObject(i).getString("id"));

				// System.out.println("\nRequest ID : "+i);

				System.out.println("name: " + array.getJSONObject(i).getString("name"));
				System.out.println("value: " + array.getJSONObject(i).getString("value"));
				// System.out.println(array.getJSONObject(i).getString("totalResult"));

				System.out.println("--------");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public List<CertUserListObj> getAssignedCertificationsUsers(String username, String password, String certId,
			String soaTaskId) {
		CertUserListObj certUserListObj = null;
		List<CertUserListObj> certUserList = new ArrayList<CertUserListObj>();
		try {
			URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/certifications/" + certId
					+ "/tasks/" + soaTaskId + "/lineitems");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("username", "muaaz");
			// con.setRequestProperty("password", "Test@1234");
			con.setRequestProperty("Authorization", "Basic " + base64Encoder(username, password));
			int status = con.getResponseCode();
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

			JSONObject obj = new JSONObject(content.toString());

			List<String> list = new ArrayList<String>();
			JSONArray array = obj.getJSONArray("certLineItemList");
			System.out.println(array);

			for (int i = 0; i < array.length(); i++) {
				String date = "";

				// list.add(array.getJSONObject(i).getString("id"));

				// System.out.println("\nRequest ID : "+i);

				certUserListObj = new CertUserListObj();
				certUserListObj.setCreateUser(array.getJSONObject(i).getString("createUser"));
				certUserListObj.setIdentityStatus(array.getJSONObject(i).getString("identityStatus"));
				certUserListObj.setFirstName(array.getJSONObject(i).getString("firstName"));
				certUserListObj.setLastName(array.getJSONObject(i).getString("lastName"));
				certUserListObj.setUserLogin(array.getJSONObject(i).getString("userLogin"));
				certUserListObj.setPercentComplete(array.getJSONObject(i).getString("percentComplete"));
				certUserListObj.setId(array.getJSONObject(i).getString("id"));
				certUserListObj.setUpdateDate(array.getJSONObject(i).getString("updateDate"));
				certUserListObj.setCreateDate(array.getJSONObject(i).getString("certifyDate"));
				certUserListObj.setOrganization(array.getJSONObject(i).getString("organization"));
				certUserListObj.setIdentityStatus(array.getJSONObject(i).getString("identityStatus"));
				certUserListObj.setAccounts(array.getJSONObject(i).getString("accounts"));
				
				certUserList.add(certUserListObj);
				System.out.println("createUser: " + array.getJSONObject(i).getString("createUser"));
				System.out.println("identityStatus: " + array.getJSONObject(i).getString("identityStatus"));
				System.out.println("lastName: " + array.getJSONObject(i).getString("lastName"));
				System.out.println("firstName: " + array.getJSONObject(i).getString("firstName"));
				System.out.println("userLogin: " + array.getJSONObject(i).getString("userLogin"));
				System.out.println("id: " + array.getJSONObject(i).getString("id"));

				System.out.println("certifyDate: " + array.getJSONObject(i).getString("certifyDate"));
				System.out.println("organization: " + array.getJSONObject(i).getString("organization"));
				System.out.println("identityStatus: " + array.getJSONObject(i).getString("identityStatus"));
				System.out.println("accounts: " + array.getJSONObject(i).getString("accounts"));

				System.out.println(array.getJSONObject(i).getString("status"));

				System.out.println("--------");
			}

			return certUserList;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;

	}

	public List<CertifDetailsObj> getCertificationItems(String username, String password, String certId, String taskId,
			String itemId) {
		CertifDetailsObj certifDetailsObj = null;
		List<CertifDetailsObj> certifDetailsLits = new ArrayList<CertifDetailsObj>();
		System.out.println(certId + " = " + taskId);
		try {
			String urlConcat = "";
			URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/certifications/" + certId
					+ "/tasks/" + taskId + "/lineitems/" + itemId + "");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("username", "muaaz");
			
			// con.setRequestProperty("password", "Test@1234");
			con.setRequestProperty("Authorization", "Basic " + base64Encoder(username, password));
			int status = con.getResponseCode();
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

			JSONObject obj = new JSONObject(content.toString());

			List<String> list = new ArrayList<String>();
			JSONArray array = obj.getJSONArray("lineItemDetails");
			System.out.println(array);

			for (int i = 0; i < array.length(); i++) {
				String date = "";

				certifDetailsObj = new CertifDetailsObj();

				// list.add(array.getJSONObject(i).getString("id"));

				// System.out.println("\nRequest ID : "+i);

				String action;
				try{
					action=array.getJSONObject(i).getString("action");
					certifDetailsObj.setAction(array.getJSONObject(i).getString("action"));
				
				}catch (Exception e) {
					certifDetailsObj.setAction("-1");
					action="-1";
				}
				certifDetailsObj.setUserId(array.getJSONObject(i).getString("userId"));
				if (!array.getJSONObject(i).getString("category").equals("Role")) {
					certifDetailsObj.setEndPointId(array.getJSONObject(i).getString("endPointId"));
					certifDetailsObj.setEndPointName(array.getJSONObject(i).getString("endPointName"));
					
				}
				if (array.getJSONObject(i).getString("category").equals("Role")) 
				{
					certifDetailsObj.setCategory("Role");
				}
				if (array.getJSONObject(i).getString("category").equals("ApplicationInstance")) 
				{
					certifDetailsObj.setCategory("ACCOUNT");
				}
				else
				{
					certifDetailsObj.setCategory(array.getJSONObject(i).getString("category"));
				}
				
				certifDetailsObj.setCatalogType(array.getJSONObject(i).getString("catalogType"));
				certifDetailsObj.setDisplayName(array.getJSONObject(i).getString("displayName"));
				certifDetailsObj.setName(array.getJSONObject(i).getString("name"));
				certifDetailsObj.setIamId(array.getJSONObject(i).getString("iamId"));
				certifDetailsObj.setId(array.getJSONObject(i).getString("id"));
				certifDetailsObj.setEntityId(array.getJSONObject(i).getString("entityId"));

				System.out.println("name: " + array.getJSONObject(i).getString("entityId"));
				System.out.println("value: " + array.getJSONObject(i).getString("parentEntityId"));
				System.out.println("iamUserId: " + array.getJSONObject(i).getString("category"));
				System.out.println("fullName: " + array.getJSONObject(i).getString("displayName"));
				System.out.println("ACTION------: " + action);
				System.out.println("--------");

				certifDetailsLits.add(certifDetailsObj);

			}
			return certifDetailsLits;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
///////////////////////////////////////////////////////Get Percentage of TASKS////////////////////////////////
	
	
	public float getPercentage(String username, String password, String certId,
			String soaTaskId) {
		
		float percentage=0;
		float sumOfTaks=0;
		CertUserListObj certUserListObj = null;
		List<CertUserListObj> certUserList = new ArrayList<CertUserListObj>();
		try {
			URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/certifications/" + certId
					+ "/tasks/" + soaTaskId + "/lineitems");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("username", "muaaz");
			// con.setRequestProperty("password", "Test@1234");
			con.setRequestProperty("Authorization", "Basic " + base64Encoder(username, password));
			int status = con.getResponseCode();
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

			JSONObject obj = new JSONObject(content.toString());

			List<String> list = new ArrayList<String>();
			JSONArray array = obj.getJSONArray("certLineItemList");
			System.out.println(array);

			for (int i = 0; i < array.length(); i++) {
				System.out.println("array.length():"+array.length());
				System.out.println("percentComplete: " + array.getJSONObject(i).getString("percentComplete"));
				sumOfTaks+=Float.valueOf(array.getJSONObject(i).getString("percentComplete"));
				
				
				System.out.println("percentComplete: " + array.getJSONObject(i).getString("percentComplete"));

				System.out.println("sumOfTaks:"+sumOfTaks);
				System.out.println("--------");
			}
			
			percentage = (sumOfTaks/(array.length()*100))*100;
			System.out.println("percentage:"+percentage);
			return percentage;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return 0;

	}

	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	public String base64Encoder(String username, String password) {
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
		String str = encoder.encodeToString((username + ":" + password).getBytes());
		System.out.println("Encoded string: " + str);
		return str;

	}

}
