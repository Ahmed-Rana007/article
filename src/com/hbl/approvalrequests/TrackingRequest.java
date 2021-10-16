package com.hbl.approvalrequests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;
import java.util.List;

import javax.security.auth.login.LoginException;

import com.bea.httppubsub.json.JSONArray;
import com.bea.httppubsub.json.JSONObject;
import com.hbl.selfservice.LoginToOIM;

import oracle.iam.catalog.api.CatalogService;
import oracle.iam.catalog.vo.Catalog;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.utils.vo.OIMType;
import oracle.iam.request.api.RequestService;
import oracle.iam.request.exception.NoRequestPermissionException;
import oracle.iam.request.exception.RequestServiceException;
import oracle.iam.request.vo.Request;
import oracle.iam.request.vo.Beneficiary;
import oracle.iam.request.vo.RequestBeneficiaryEntity;
import oracle.iam.request.vo.RequestConstants;
import oracle.iam.request.vo.RequestSearchCriteria;
//import oracle.iam.platform.workflowservice.api;
import weblogic.net.http.HttpURLConnection;

public class TrackingRequest {

	private void workflowServiceClient() {
		// TODO Auto-generated method stub

	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		LoginToOIM logoim = new LoginToOIM();
		//40090 
		try {

			OIMClient oimClient = logoim.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
			TrackingRequest tr = new TrackingRequest();
			//tr.getTrackingId(oimClient, "1");
			//tr.getTotalRequestByUser(oimClient, "1");
			tr.getJustification(oimClient, "121092");
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TrackingRequest tr1 = new TrackingRequest();
	}

	public void getTrackingId(OIMClient oimClient, String requestId) {

		try {
			RequestService reqService = oimClient.getService(RequestService.class);
			RequestSearchCriteria searchCriteria = new RequestSearchCriteria();
			searchCriteria.addExpression(RequestConstants.REQUESTER_KEY, requestId,
					RequestSearchCriteria.Operator.EQUAL);
			 Long numberOfRequest =
			 reqService.getNumberOfRequestsCreatedForUser(Long.parseLong(requestId) ,
			 searchCriteria);
			 System.out.println(numberOfRequest);

		} catch (RequestServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getTotalRequestByUser(OIMClient oimClient, String requestId) {

		try {
			RequestService reqService = oimClient.getService(RequestService.class);
			RequestSearchCriteria searchCriteria = new RequestSearchCriteria();
			searchCriteria.addExpression(RequestConstants.REQUESTER_KEY, requestId,
					RequestSearchCriteria.Operator.EQUAL);
			Long numberOfRequest = reqService.getNumberOfRequestsCreatedByUser(Long.parseLong(requestId),
					searchCriteria);
			System.out.println(numberOfRequest);
		} catch (RequestServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getJustification(OIMClient oimClient, String requestId) {

		String justification =null;
		try {
			RequestService reqService = oimClient.getService(RequestService.class);
			RequestSearchCriteria searchCriteria = new RequestSearchCriteria();
			searchCriteria.addExpression(RequestConstants.REQUESTER_KEY, requestId,
					RequestSearchCriteria.Operator.EQUAL);
			Request numberOfRequest = null;
			try {
				numberOfRequest = reqService.getBasicRequestData((requestId));
			} catch (NoRequestPermissionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			justification = numberOfRequest.getJustification();
			System.out.println(numberOfRequest.getJustification());
		} catch (RequestServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return justification;
	}
	public void getCatalogDetailsForRequest(OIMClient oimClient,String requestID)
			  {
//get required services
		RequestService requestService = oimClient.getService(RequestService.class);
		CatalogService catalogService = oimClient.getService(CatalogService.class);

//get request object
		//Request request = requestService.getBasicRequestData(requestID);
		 
		//List<Beneficiary> reqBeneficiaries = request.getBeneficiaries();
		
		/*for (Beneficiary beneficiary : reqBeneficiaries) {
			List requestBeneficiaryEntityList = beneficiary.getTargetEntities();
			for (RequestBeneficiaryEntity requestBeneficiaryEntity : requestBeneficiaryEntityList) {
				String entityKey = requestBeneficiaryEntity.getEntityKey();
				OIMType entityType = requestBeneficiaryEntity.getRequestEntityType();

				Catalog catalog = catalogService.getCatalogItemDetails(null, entityKey, entityType, null);

				System.out.println("Approver Role :: " + catalog.getApproverRole());
				System.out.println("Approver User :: " + catalog.getApproverUser());
				System.out.println("Category :: " + catalog.getCategoryName());
			}
		}*/
	}
	
	
	
	
	
	public String GetRequestJustification(String trackID,String userLogin,String password) throws IOException {
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
		
		String justification = obj.getString("reqJustification");
		System.out.println(justification);
		return justification;
		
							
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
