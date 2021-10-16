package soa;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestSendOTP_Email {

	public static void main(String[] args) {
		TestSendOTP_Email Esobj = new TestSendOTP_Email();
		
		String reciever =  "taimouranees0@gmail.com";
		String message = "Your Otp is: ";
		String un = "1234";
		Esobj.SendOTP_Email(reciever, message, un);
	}
	
	public void SendOTP_Email(String reciever,String message,String un) {
		  
        try { 
       	 
          String body ="<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns1:process xmlns:ns1=\"http://xmlns.oracle.com/EMAIL/EMAIL/BPELProcess1\"><ns1:reciever>"+reciever+"</ns1:reciever><ns1:subject>"+message+"</ns1:subject><ns1:msg>"+un+"</ns1:msg></ns1:process></soap:Body></soap:Envelope>";
          StringEntity stringEntity = new StringEntity(body, "UTF-8");
          stringEntity.setChunked(true);

        //  Request parameters and other properties.
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

}
