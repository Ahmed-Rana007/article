package com.gnl.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hbl.approvalrequests.PredefinedConst;
import com.hbl.approvalrequests.SOADBConnection;
import com.hbl.equation.limits.DatabaseConnection;

public class GetChildData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//getAS400CSGTable(2,"HARIS");
		//getAS400CSPTable(2,"HARIS");
		//getAS400CSPMap(2,"HARIS");
		getEquation26Map(165, "JAVED");
		getEquation7Map(165, "JAVED");
		getEquation34Map(165, "JAVED");
		getEquation27Map(165, "JAVED");
	}

	


	public static List getAS400CSGTable(int appInsstanceKey,String userLogin)
	{
		DatabaseConnection connection = null ;
		Connection con =  connection.connectOIMDB();
		List<String> AS400CSG = new ArrayList<>();
		String data = null;
		ResultSet rs =null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(PredefinedConst.Get_AS400_CSG);
			pstmt.setInt(1, appInsstanceKey);
			pstmt.setString(2, userLogin);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				data =rs.getString("UD_AS400CSG_ID");
				AS400CSG.add(data);
			
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (pstmt != null) {
		        try {
		        	pstmt.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		}
		return AS400CSG;
	}
	public static List getAS400CSPTable(int appInsstanceKey,String userLogin)
	{
		DatabaseConnection connection = null ;
		Connection con =  connection.connectOIMDB();
		List<String> AS400CSP = new ArrayList<>();
		String data = null;
		ResultSet rs=null;
		PreparedStatement pstmt =null;
		try {
			pstmt = con.prepareStatement(PredefinedConst.Get_AS400_CSP);
			pstmt.setInt(1, appInsstanceKey);
			pstmt.setString(2, userLogin);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				data =rs.getString("UD_AS400CSP_ID");
				AS400CSP.add(data);
			
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (pstmt != null) {
		        try {
		        	pstmt.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		}
		return AS400CSP;
	}
	
	public static HashMap<String, String> getAS400CSPMap(int appInsstanceKey,String userLogin)
	{
		DatabaseConnection connection = null ;
		Connection con =  connection.connectOIMDB();
		HashMap <String,String> AS400CSP = new HashMap<String,String>();
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(PredefinedConst.Get_AS400_CSP);
			pstmt.setInt(1, appInsstanceKey);
			pstmt.setString(2, userLogin);
			rs = pstmt.executeQuery();
			while (rs.next()) {
			
				AS400CSP.put(rs.getString("UD_AS400CSP_KEY"), rs.getString("UD_AS400CSP_ID"));
				
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (pstmt != null) {
		        try {
		        	pstmt.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		}
		for(Map.Entry m :AS400CSP.entrySet())
		{
			System.out.println(m.getKey()+":"+m.getValue());
		}
		
		return AS400CSP;
	}

	
	
	public static LinkedHashMap<String, String> getEquationGroupOptionMap(int appInsstanceKey,String userLogin)
	{
		DatabaseConnection connection = null ;
		Connection con =  connection.connectOIMDB();
		LinkedHashMap <String,String> EquationGroup = new LinkedHashMap<String,String>();
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(PredefinedConst.Get_Equation_Group);
			pstmt.setInt(1, appInsstanceKey);
			pstmt.setString(2, userLogin);
			rs = pstmt.executeQuery();
			while (rs.next()) {
			
				EquationGroup.put(rs.getString("UD_KFILPK25_KEY"), rs.getString("UD_KFILPK25_OPTION_ID"));
				
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (pstmt != null) {
		        try {
		        	pstmt.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		}
		for(Map.Entry m :EquationGroup.entrySet())
		{
			System.out.println(m.getKey()+":"+m.getValue());
		}
		
		return EquationGroup;
	}

//////////////////////////////////////////Get Equation Child Data///////////////////////////////////
	
	
	public static LinkedHashMap<String, String> getEquation26Map(int appInsstanceKey,String userLogin)
	{
		DatabaseConnection connection = null ;
		Connection con =  connection.connectOIMDB();
		LinkedHashMap <String,String> EQUATION26 = new LinkedHashMap<String,String>();
		ResultSet rs= null;
		PreparedStatement pstmt =null;
		try {
			 pstmt = con.prepareStatement(PredefinedConst.Get_EQUATION_26);
			pstmt.setInt(1, appInsstanceKey);
			pstmt.setString(2, userLogin);
			rs = pstmt.executeQuery();
			while (rs.next()) {
			
				EQUATION26.put("UD_KFILPK26_USER_BRANCH", rs.getString("UD_KFILPK26_USER_BRANCH"));
				EQUATION26.put("UD_KFILPK26_BRANCH_NO", rs.getString("UD_KFILPK26_BRANCH_NO"));
				EQUATION26.put("UD_KFILPK26_USR_CLASS", rs.getString("UD_KFILPK26_USR_CLASS"));
				EQUATION26.put("UD_KFILPK26_LANGUAGE_CODE", rs.getString("UD_KFILPK26_LANGUAGE_CODE"));
				EQUATION26.put("UD_KFILPK26_DATE_LAST_MAINTAIN", rs.getString("UD_KFILPK26_DATE_LAST_MAINTAIN"));
				EQUATION26.put("UD_KFILPK26_USER_GROUP", rs.getString("UD_KFILPK26_USER_GROUP"));
				EQUATION26.put("UD_KFILPK26_KEY", rs.getString("UD_KFILPK26_KEY"));
				
				
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (pstmt != null) {
		        try {
		        	pstmt.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		}
		for(Map.Entry m :EQUATION26.entrySet())
		{
			System.out.println(m.getKey()+":"+m.getValue());
		}
	
		return EQUATION26;
	}

	
	

	public static LinkedHashMap<String, String> getEquation7Map(int appInsstanceKey,String userLogin)
	{
		DatabaseConnection connection = null ;
		Connection con =  connection.connectOIMDB();
		LinkedHashMap <String,String> EQUATION7 = new LinkedHashMap<String,String>();
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(PredefinedConst.Get_EQUATION_7);
			pstmt.setInt(1, appInsstanceKey);
			pstmt.setString(2, userLogin);
			rs = pstmt.executeQuery();
			while (rs.next()) {
			
				EQUATION7.put("UD_KAPBASE7_UNIT_SERVER", rs.getString("UD_KAPBASE7_UNIT_SERVER"));
				EQUATION7.put("UD_KAPBASE7_INITIAL_MENU", rs.getString("UD_KAPBASE7_INITIAL_MENU"));
				EQUATION7.put("UD_KAPBASE7_AUTHORISED_UNIT", rs.getString("UD_KAPBASE7_AUTHORISED_UNIT"));
				EQUATION7.put("UD_KAPBASE7_KEY", rs.getString("UD_KAPBASE7_KEY"));
				
				
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (pstmt != null) {
		        try {
		        	pstmt.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		}
		for(Map.Entry m :EQUATION7.entrySet())
		{
			System.out.println(m.getKey()+":"+m.getValue());
		}
		
		return EQUATION7;
	}

	
	public static List<HashMap<String,String>> getEquation34Map(int appInsstanceKey,String userLogin)
	{
		DatabaseConnection connection = null ;
		Connection con =  connection.connectOIMDB();
		LinkedHashMap <String,String> EQUATION34 = new LinkedHashMap<String,String>();
		List<HashMap<String,String>> Equation34List = new ArrayList<HashMap<String,String>>();
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(PredefinedConst.Get_EQUATION_34);
			pstmt.setInt(1, appInsstanceKey);
			pstmt.setString(2, userLogin);
			rs = pstmt.executeQuery();
			while (rs.next()) {
			
				EQUATION34.put("UD_KFILPK34_PROCESS_INTER_BRAN", rs.getString("UD_KFILPK34_PROCESS_INTER_BRAN"));
				EQUATION34.put("UD_KFILPK34_GROUP", rs.getString("UD_KFILPK34_GROUP"));
				EQUATION34.put("UD_KFILPK34_INTER_BRANCH_DEBIT", rs.getString("UD_KFILPK34_INTER_BRANCH_DEBIT"));
				EQUATION34.put("UD_KFILPK34_RESIDENT_BRANCH", rs.getString("UD_KFILPK34_RESIDENT_BRANCH"));
				EQUATION34.put("UD_KFILPK34_INTER_BRANCH_CREDI", rs.getString("UD_KFILPK34_INTER_BRANCH_CREDI"));
				EQUATION34.put("UD_KFILPK34_BRANCH_NUMBER", rs.getString("UD_KFILPK34_BRANCH_NUMBER"));
				EQUATION34.put("UD_KFILPK34_LOCAL_CREDIT_AMOUN", rs.getString("UD_KFILPK34_LOCAL_CREDIT_AMOUN"));
				EQUATION34.put("UD_KFILPK34_LOCAL_DEBIT_AMOUNT", rs.getString("UD_KFILPK34_LOCAL_DEBIT_AMOUNT"));
				EQUATION34.put("UD_KFILPK34_DEBIT_AUTHORIZATIO", rs.getString("UD_KFILPK34_DEBIT_AUTHORIZATIO"));
				EQUATION34.put("UD_KFILPK34_CREDIT_AUTHORIZATI", rs.getString("UD_KFILPK34_CREDIT_AUTHORIZATI"));
				EQUATION34.put("UD_KFILPK34_KEY", rs.getString("UD_KFILPK34_KEY"));
				
				Equation34List.add(EQUATION34);
				
				EQUATION34 =new LinkedHashMap<String,String>();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (pstmt != null) {
		        try {
		        	pstmt.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		}
		for(HashMap<String, String> m :Equation34List)
		{
			//System.out.println(m.get("UD_KFILPK34_PROCESS_INTER_BRAN"));
			System.out.println(m);
			
		}
		
		return Equation34List;
	}


	
	public static LinkedHashMap<String, String> getEquation27Map(int appInsstanceKey,String userLogin)
	{
		DatabaseConnection connection = null ;
		Connection con =  connection.connectOIMDB();
		LinkedHashMap <String,String> EQUATION27 = new LinkedHashMap<String,String>();
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(PredefinedConst.Get_EQUATION_27);
			pstmt.setInt(1, appInsstanceKey);
			pstmt.setString(2, userLogin);
			rs = pstmt.executeQuery();
			while (rs.next()) {
			
				EQUATION27.put("UD_KFILPK27_DEFAULT_USER_LIMIT", rs.getString("UD_KFILPK27_DEFAULT_USER_LIMIT"));
				EQUATION27.put("UD_KFILPK27_CURRENCY_MNEMONIC", rs.getString("UD_KFILPK27_CURRENCY_MNEMONIC"));
				EQUATION27.put("UD_KFILPK27_DATE_LAST_MAINTAIN", rs.getString("UD_KFILPK27_DATE_LAST_MAINTAIN"));
				EQUATION27.put("UD_KFILPK27_APPLICATION_CODE", rs.getString("UD_KFILPK27_APPLICATION_CODE"));
				EQUATION27.put("UD_KFILPK27_KEY", rs.getString("UD_KFILPK27_KEY"));
				
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (pstmt != null) {
		        try {
		        	pstmt.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* Ignored */}
		    }
		}
		for(Map.Entry m :EQUATION27.entrySet())
		{
			System.out.println(m.getKey()+":"+m.getValue());
		}
		
				return EQUATION27;
	}

	
	////////////////////////////////////////////////////////////////////////////////////////////


}
