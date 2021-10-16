package soa;

public class TestClass {
	
	public static void main (String arg[])
	{
		try {
		String command = "curl \"https://apis1.hbl.com:8343/sandbox/api/customer/alert/sms \" \\\r\n" + 
				"  -X POST \\\r\n" + 
				"  -d \"{\\\"telco\\\": \\\"Telenor\\\",\\\"priority\\\":\\\"9\\\",\\\"shortCode\\\":\\\"\\\",\\\"msgLangType\\\":\\\"ENG\\\",\\\"recieverAddress\\\":\\\"923004154199\\\",\\\"message\\\":\\\"Test message from HBL\\\"}\" \\\r\n" + 
				"  -H \"Accept: application/json\" \\\r\n" + 
				"  -H \"Content-Type: application/json\" \\\r\n" + 
				"  -H \"x-alert-type: SMS\" \\\r\n" + 
				"  -H \"x-channel-id: MB\" \\\r\n" + 
				"  -H \"x-country-code: PK\" \\\r\n" + 
				"  -H \"x-req-id: 1234567\" \\\r\n" + 
				"  -H \"x-sub-channel-id: MB\"";
		Process process = Runtime.getRuntime().exec(command);
		String ss= process.getInputStream().toString();
		System.out.println(ss+" Executed");
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	

}

