package com.hbl.equation.limits;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hbl.approvalrequests.PredefinedConst;

public class RequestInitiate {

	private String request_key = null;
	private String user_key = null;
	private PreparedStatement pstmt =null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RequestInitiate ri = new RequestInitiate();
		ri.getRequestDetails();
	}

	void getRequestDetails() {

		try {
			Connection con = DatabaseConnection.connectMSCDB();			 
			pstmt = con.prepareStatement(PredefinedConst.EQUATION_LIMIT_REQUEST_QUERY);
			pstmt.setString(1, "123");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				request_key = rs.getInt(1)+"";
				user_key = rs.getString(2).toString();
				System.out.println(request_key);
			}
			con.close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
