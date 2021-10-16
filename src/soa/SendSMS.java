/////////////////////////////////////////////////
/*
String certificatesTrustStorePath = "C:\\Program Files\\Java\\jdk1.8.0_31\\jre\\lib\\security\\cacerts";
				 System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
				
				System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
				
				
Set the path of your Certificate With the Lines of code.
Set The Path of JDK where u will Install In C or E drive. :)				


*/
//////////////////////////////////////////////////


package soa;
import javax.xml.soap.*;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

//import weblogic.net.http.HttpsURLConnection;
//import weblogic.net.http.SOAPHttpsURLConnection;
//weblogic.net.http.SOAPHttpsURLConnection cannot be cast to javax.net.ssl.HttpsURLConnection
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import oracle.security.crypto.cert.ValidationException;
import sun.security.validator.ValidatorException;
import weblogic.diagnostics.harvester.internal.Validators.ValidationError;
import weblogic.security.SSL.SSLValidationConstants;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class SendSMS {
	public static void main(String[] args) {
		 
		SendSMS tsm = new SendSMS();
		try {
		tsm.sendSMStoReciever("923004154199","21","12345");
		
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}
	
	public  void callWebService(String reciever,String message,String un)  {
	    
        try { 
       	 
          String body ="<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns1:process xmlns:ns1=\"http://xmlns.oracle.com/EMAIL/EMAIL/BPELProcess1\"><ns1:reciever>"+reciever+"</ns1:reciever><ns1:subject>"+message+"</ns1:subject><ns1:msg>"+un+"</ns1:msg></ns1:process></soap:Body></soap:Envelope>";
          StringEntity stringEntity = new StringEntity(body, "UTF-8");
          stringEntity.setChunked(true);

          // Request parameters and other properties.
        //  HttpPost httpPost = new HttpPost("http://192.168.0.96:9001/soa-infra/services/default/EMAIL/bpelprocess1_client_ep?WSDL"); DEV ENV
        //  HttpPost httpPost = new HttpPost("http://drelidmoamrepl02-int:9001/soa-infra/services/default/EMAIL/bpelprocess1_client_ep?WSDL"); //UAT ENV
          HttpPost httpPost = new HttpPost("http://192.168.1.164:9001/soa-infra/services/default/EMAIL/bpelprocess1_client_ep?WSDL"); //UAT-2
          httpPost.setEntity(stringEntity);

       
          httpPost.addHeader("Service", "bpelprocess1_client_ep");
          httpPost.addHeader("Port", "BPELProcess1_pt");
          httpPost.addHeader("SOAPAction", "process");
          httpPost.addHeader("Accept", "text/xml");
          httpPost.addHeader("Content-Type", "text/xml");
          

          // Execute and get the response.
          HttpClient httpClient = new DefaultHttpClient();
          HttpResponse response;
       
            response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();

          String strResponse = null;
          if (entity != null) {
              System.out.println("success :"+response);
             // strResponse = EntityUtils.toString(entity);
          }
    }
        catch (Exception e) {
                   System.out.println(e.toString());
               }
         
    }
   
	 public   Boolean Create(String reciever,String otp,String USERID) throws MalformedURLException, IOException {
		 DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
 		Date date = new Date();
 		String dat=dateFormat.format(date); //2016/11/16 12:08:43
 		DateFormat dateFormat2 = new SimpleDateFormat("HH:MM:00");
 		Date date2 = new Date();
 		String dat2=dateFormat2.format(date2);
 		
 		SimpleDateFormat dateFormatz = new SimpleDateFormat("yyMMddHHmmss");
		Date transSaction =  new Date();
		String trans=(dateFormatz.format(transSaction));
		trans=trans+reciever;
		
	    	    	//Code to make a webservice HTTP request
	    	    	String responseString = "";
	    	    	String outputString = "";
	    	    //	String wsURL = "http://10.2.131.21:7003/KMBOSBPRJT/EXTLSRVS/SOAP/_EXTXEP_OSBSRVS_PS_";
	    	    	String wsURL = "http://10.2.121.110:6662/KMBOSBPRJT/EXTLSRVS/SOAP/_EXTXEP_OSBSRVS_PS_"; //UAT ENV
	    	    	//System.out.println("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:osb=\"http://osb.tempuri.org/\">   <soapenv:Header/>   <soapenv:Body>      <osb:SEND_SMS_IN>         <osb:SEND_SMS_REQ>            <osb:_USER_ID_>OSBUSR003</osb:_USER_ID_>            <osb:_PASSWORD_>aMHoNTzGIKqWKjKgulhEaQ==</osb:_PASSWORD_>            <osb:_TRANSMISSION_DATE_>"+dat+"</osb:_TRANSMISSION_DATE_>            <osb:_TRANSMISSION_TIME_>"+dat2+"</osb:_TRANSMISSION_TIME_>            <osb:_CHANNEL_ID_>OIDM</osb:_CHANNEL_ID_>            <osb:_TRANSACTION_TYPE_>SEND_SMS</osb:_TRANSACTION_TYPE_>            <osb:_TRANSACTION_IDENTIFIER_>100</osb:_TRANSACTION_IDENTIFIER_>            <osb:_TRANSACTION_NUMBER_>1234567892</osb:_TRANSACTION_NUMBER_>            <osb:_RESERVED_DATA_></osb:_RESERVED_DATA_>            <osb:_DESTINATION_NUMBER_>"+reciever+"</osb:_DESTINATION_NUMBER_>            <osb:_MESSAGE_>Your OTP is "+otp+"</osb:_MESSAGE_>            <osb:_UNICODE_></osb:_UNICODE_>         </osb:SEND_SMS_REQ>      </osb:SEND_SMS_IN>   </soapenv:Body></soapenv:Envelope>");
	    	    	URL url = new URL(wsURL);
	    	    	URLConnection connection = url.openConnection();
	    	    	HttpURLConnection httpConn = (HttpURLConnection)connection;
	    	    	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	    	    	String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:osb=\"http://osb.tempuri.org/\">   <soapenv:Header/>   <soapenv:Body>      <osb:SEND_SMS_IN>         <osb:SEND_SMS_REQ>            <osb:_USER_ID_>OSBUSR003</osb:_USER_ID_>            <osb:_PASSWORD_>aMHoNTzGIKqWKjKgulhEaQ==</osb:_PASSWORD_>            <osb:_TRANSMISSION_DATE_>"+dat+"</osb:_TRANSMISSION_DATE_>            <osb:_TRANSMISSION_TIME_>"+dat2+"</osb:_TRANSMISSION_TIME_>            <osb:_CHANNEL_ID_>OIDM</osb:_CHANNEL_ID_>            <osb:_TRANSACTION_TYPE_>SEND_SMS</osb:_TRANSACTION_TYPE_>            <osb:_TRANSACTION_IDENTIFIER_>100</osb:_TRANSACTION_IDENTIFIER_>            <osb:_TRANSACTION_NUMBER_>"+trans+"</osb:_TRANSACTION_NUMBER_>            <osb:_RESERVED_DATA_></osb:_RESERVED_DATA_>            <osb:_DESTINATION_NUMBER_>"+reciever+"</osb:_DESTINATION_NUMBER_>            <osb:_MESSAGE_>Your OTP is "+otp+"</osb:_MESSAGE_>            <osb:_UNICODE_></osb:_UNICODE_>         </osb:SEND_SMS_REQ>      </osb:SEND_SMS_IN>   </soapenv:Body></soapenv:Envelope>";

	    	    	byte[] buffer = new byte[xmlInput.length()];
	    	    	buffer = xmlInput.getBytes();
	    	    	bout.write(buffer);
	    	    	byte[] b = bout.toByteArray();
	    	    	String SOAPAction = "<SOAP action of the webservice to be consumed>";
	    	    	// Set the appropriate HTTP parameters.
	    	    	httpConn.setRequestProperty("Content-Length",
	    	    	String.valueOf(b.length));
	    	    	httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
	    	    	//httpConn.setRequestProperty("SOAPAction", SOAPAction);
	    	    	httpConn.setRequestMethod("POST");
	    	    	httpConn.setDoOutput(true);
	    	    	httpConn.setDoInput(true);
	    	    	OutputStream out = httpConn.getOutputStream();
	    	    	//Write the content of the request to the outputstream of the HTTP Connection.
	    	    	out.write(b);
	    	    	out.close();
	    	    	//Ready with sending the request.

	    	    	//Read the response.
	    	    	InputStreamReader isr = null;
	    	    	if (httpConn.getResponseCode() == 200) {
	    	    	  isr = new InputStreamReader(httpConn.getInputStream());
	    	    	} else {
	    	    	  isr = new InputStreamReader(httpConn.getErrorStream());
	    	    	}

	    	    	BufferedReader in = new BufferedReader(isr);

	    	    	//Write the SOAP message response to a String.
	    	    	while ((responseString = in.readLine()) != null) {
	    	    	outputString = outputString + responseString;
	    	    	}
	    	    	outputString=outputString.toUpperCase();
	    	    	Boolean result=true;
	    	    	//System.out.println(outputString);
	    	    	return true;
	    	    	}
	 public boolean sendSMStoReciever(String reciever, String msg,String OTP) throws MalformedURLException, IOException
	 {
		 System.out.println("Kashif:: In sendSMStoReciever " + OTP );

			try {
				
				//AddVMOption -Djavax.net.ssl.trustStore=$JDK_HOME/jre/bin/cacerts.jks
						//AddVMOption -Djavax.net.ssl.trustStorePassword=<PASSWORD>
				
				//String pathToKeyStore = "/app/oracle/product/OIGP4_HOME/user_projects/domains/OIG_Domain/identity.jks";
				//System.setProperty("javax.net.ssl.keyStore", pathToKeyStore);
				String certificatesTrustStorePath = "C:\\Program Files\\Java\\jdk1.8.0_31\\jre\\lib\\security\\cacerts";
			//	String certificatesTrustStorePath = "/app/JDK/jdk1.8.0_241/jre/lib/security/cacerts";
				
				
				System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
				
				System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
				
				System.setProperty("javax.net.ssl.keyStorePassword", "Welcome965");
				
				String pathToTrustStore = "/app/oracle/product/OIGP4_HOME/user_projects/domains/OIG_Domain/identity.jks";
				System.setProperty("Djavax.net.ssl.trustStore", pathToTrustStore);
				
				
			//	System.setProperty("Djavax.net.ssl.trustStorePassword", "Welcome965");
				
				//URL url = new URL("https://10.9.166.18:8343/sandbox/api/customer/alert/sms");
				//HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
				
				String fURL = "https://apis1.hbl.com:8343/sandbox/api/customer/alert/sms";
				
				java.net.URL url = new URL(null, fURL,new sun.net.www.protocol.https.Handler());
				java.net.HttpURLConnection  con = (HttpsURLConnection) url.openConnection();
				
				
				con.setRequestMethod("POST");
				con.setRequestProperty("Accept", "application/json");
				con.setRequestProperty("Content-Type", "application/json");
				con.setRequestProperty("x-alert-type", "SMS");
				con.setRequestProperty("x-channel-id", "MB");
				con.setRequestProperty("x-country-code", "PK");
				con.setRequestProperty("x-req-id", "1234567");
				con.setRequestProperty("x-sub-channel-id", "MB");

				/* Payload support */
				con.setDoOutput(true);
				DataOutputStream out = new DataOutputStream(con.getOutputStream());
				out.writeBytes("{\"telco\": \"Telenor\",\"priority\":\"9\",\"shortCode\":\"\",\"msgLangType\":\"ENG\",\"recieverAddress\":\""+reciever+"\",\"message\":\"Your OTP is "+OTP+"\"}");
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
			}catch(SSLException e) {
				 
				System.out.print(e.getMessage() + " Cause:: "+ e.getCause());
				
			} 
			catch(ValidationException ex) {
				System.out.println(ex.getCause());
			}
			
			catch (Exception e) {
				System.out.print("Error: "+e.getMessage());
				System.out.print("Error: "+e.getStackTrace());
		
			}
			return false;
	 }
}
