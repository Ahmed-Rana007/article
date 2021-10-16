package soa;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import com.hbl.equation.limits.DatabaseConnection;

import java.time.LocalDateTime; 

public class OTP_DB_Connection {

	public OTP_DB_Connection() {
		// TODO Auto-generated constructor stub
	}
	
	public String insetOTP_with_valid(String user_login,String mobile_num)
	{	
		String response=null;
		DatabaseConnection dbobj = new DatabaseConnection();
		Connection con = dbobj.connectMSCDB();
		PreparedStatement stmt= null;
		
		////validation--------Start-------
		try { 
			String sql = "select STATUS from OTP_PWD where CREATED between (SYSDATE -1/720) and (SYSDATE) and STATUS = 'Active' and USR_LOGIN ='"+user_login+"'";
			stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
			if(rs.getString("STATUS").equals("Active")) {
				System.out.println("Please Try After 2 Minutes: Already OTP Generated");
				response="AlreadyGenerated";
				return response;
				}
			}
			
			{
				TestSendSMS ts = new TestSendSMS();
				int otp = ts.Random_Otp();
				System.out.println("Mobile Number: "+mobile_num);
				ts.sendSMSTest(mobile_num, otp);
				//<!----------------Store Otp in DB From Here.-------------->
				int i=0;
				try {
					 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
					 LocalDateTime now = LocalDateTime.now();  
					 System.out.println(dtf.format(now)); 
					 String s=Integer.toString(otp);
					 
					stmt = con.prepareStatement("Insert into OTP_PWD (OTP,CREATED,USR_LOGIN,STATUS,TYPE,TIME) values (?,?,?,?,?,?)");
					stmt.setString(1,s);  
					stmt.setTimestamp(2,new java.sql.Timestamp(System.currentTimeMillis()));  
					stmt.setString(3,user_login);  
					stmt.setString(4,"Active");
					stmt.setString(5,"12345678");
					stmt.setTimestamp(6,new java.sql.Timestamp(new java.util.Date().getTime()));
					i=stmt.executeUpdate(); 
					
					System.out.println(i+" records insterted");
					System.out.println("Request Updated");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
		    if (stmt != null) {
		        try {
		        	System.out.println("Statement close: " + con.isClosed());
		        	System.out.println("Statement close: " + con.isClosed());
		        	System.out.println("Statement close: " + stmt);
		        	stmt.close();
		        	System.out.println("Statement close end: " + stmt);
		        	System.out.println("Statement close: " + stmt.isClosed());
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (con != null) {
		        try {
		        	System.out.println("Statement close: " + con);
		            con.close();
		            System.out.println("Statement close: " + con.isClosed());
		            
		            System.out.println("Statement end: " + con);
		        } catch (SQLException e) { /* Ignored */}
		    }
		}
		response="Successfull";
		return response;
		//////////Valdation------------End	
		
	} 
	

	

	public static void main(String[] args) {
		OTP_DB_Connection DB =new OTP_DB_Connection();
		//DB.insetOTP_with_valid("Taimour.Anees",);

	}

}
