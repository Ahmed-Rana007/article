package soa;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

//import com.sun.net.ssl.HttpsURLConnection;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;
import weblogic.net.http.*;

public class TestSendSMS {

	public static void main(String[] args) {
		TestSendSMS tsm = new TestSendSMS();
		//tsm.sendSMSTest("923004154199","Majid");
	}
	public int Random_Otp() {
		Random ran = new Random();
		
		int num = ran.nextInt(9) + 1 ;
		for(int i=0;i<5;i++) {
			num = (num * 10) + ran.nextInt(9) + 1;	
		}
		System.out.println("Otp From Function: " + num);
		return num;
	}
	public void sendSMSTest(String reciever, int OTP)
	{
		try {
			
		    String certificatesTrustStorePath = "C:\\Program Files\\Java\\jdk1.8.0_31\\jre\\lib\\security\\cacerts";
			System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
			
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
			
			System.setProperty("org.jboss.security.ignoreHttpsHost","true");
			URL url = new URL("https://apis1.hbl.com:8343/sandbox/api/customer/alert/sms");
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			//HttpsURLConnectionImpl con = (HttpsURLConnectionImpl) url.openConnection();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
