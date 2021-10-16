package com.hbl.approvalrequests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import weblogic.jdbc.wrapper.Array;

public class SOADBConnection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		connectSOADB();
		SOADBConnection sdb = new SOADBConnection();
		sdb.getPendingApprovals("kashif.arif");
	}

	public static Connection connectSOADB() {
		Connection con=null;
		try {
			// step1 load the driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// step2 create the connection object
			 con = DriverManager.getConnection("jdbc:oracle:thin:@10.9.166.57:1521:IDMPOC", "OIGP4_SOAINFRA",
					"hblpoc_123");

			// step3 create the statement object
			
			// step5 close the connection object
			//con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	void getPendingApprovals(String userLogin)
	{
		Connection con =  SOADBConnection.connectSOADB();
		// step4 execute query
		//like 'kashif.arif%' and STATE='ASSIGNED'"
		PendingApprovalObj pendingApprObj = null;
		try {
			PreparedStatement pstmt = con.prepareStatement(PredefinedConst.PENDING_APPROVALS_QUERY);
			pstmt.setString(1, userLogin.trim()+"%");
			pstmt.setString(2, "ASSIGNED");
			//ResultSet rs = stmt.executeQuery(sqlPendingApproval);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.print(rs.getString("ASSIGNEES") + "  " + rs.getString("ASSIGNEEGROUPSDISPLAYNAME") + "  "
						+ rs.getString("ASSIGNEEGROUPS") + " ");
				System.out.print(rs.getString("ASSIGNEEUSERS") + "  " + rs.getString("CREATOR") + "  "
						+ rs.getString("APPROVERS") + " ");
				System.out.print(rs.getString("REQUEST_TYPE") + "  " + rs.getString("ITEM_NAME") + "  "
						+ rs.getString("TITLE") + " ");
				System.out.print(rs.getString("IDENTIFICATIONKEY") + "  " + rs.getString("REQ_ID") + "  "
						+ rs.getString("TASKID") + " ");
				System.out.println();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static List<String> getRequestComments(String requestId)
	{
		Connection connection = SOADBConnection.connectSOADB();
		
		List<String> comments = new ArrayList<String>();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps = connection.prepareStatement(PredefinedConst.Get_Request_Comments);
			ps.setString(1, requestId);
			rs= ps.executeQuery();
			;
			while(rs.next())
			{
				comments.add("DATE"+": "+rs.getString("COMMENTDATE")+"<br> Comment: "+rs.getString("WFCOMMENT")+"<br>");
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
		
	}
}
