package com.hbl.equation.limits;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.hbl.equation.limits.servlet.LimitRequestObj;

import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.DriverManager;

import oracle.iam.platform.Platform;
public class DBConnection {
    public DBConnection() {
        super();
    }
    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION =
        "jdbc:oracle:thin:@10.9.166.57:1521:IDMPOC";
    private static final String DB_USER = "OIGP4_MSC";
    private static final String DB_PASSWORD = "hblpoc_123";
    
    public static void main(String arg[])
    {
       DBConnection dbCon = new DBConnection();
        dbCon.getLimits("COPCU3");
    }
    
    
    
    public List<LimitAttributes>  getLimits(String group)
    {
        List<LimitAttributes> limitAttrList = new ArrayList<LimitAttributes>();
        LimitAttributes lmtAttr = null;
      Connection dbConnection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "select  branch.ZCCGRP as groupName, branch.ZCCBRN as residentBranch, branch.ZCCBBN as branchNumber,branch.ZCCDLM as lastCCDate,\n" + 
        "lmt.ZXXGRP as groupName1, lmt.ZXXUTP as userType,lmt.ZXXTAB as allBranches, lmt.ZXXTSB as sps_all_brnch, lmt.ZXXTCR as lmtFrLclCrdt,\n" + 
        "lmt.ZXXTDR as lmtFrLclDbt, lmt.ZXXTID as intrBrnCrLmt, lmt.ZXXTID as intrBrnDbtLmt, lmt.ZXXAAB as authBrnch, lmt.ZXXASB as authSpsfdBrn, lmt.ZXXACR authAmtFrLclCrdt,\n" + 
        "lmt.ZXXADR as authAmtFrLclDbt, lmt.ZXXAIC as authAmtFrIntrBrnCrdt , lmt.ZXXAID as authAmtFrIntrBrnDbt, lmt.ZXXDLM as lastXXDate\n" + 
        "from ZCCPF branch\n" + 
        "inner join ZXXPF lmt\n" + 
        "on branch.ZCCGRP = lmt.ZXXGRP\n" + 
        "where ZCCGRP=?";
        try {
            stmt = dbConnection.prepareStatement(sql);
            stmt.setString(1, group);
            ResultSet rs = stmt.executeQuery();
            int counter=0;
            while(rs.next())
            {
                counter++;
                if(rs.getString("residentBranch").equals("1")) continue;
              lmtAttr = new LimitAttributes();
                
                lmtAttr.setGroupName("170~"+rs.getString("groupName"));
                lmtAttr.setResidentBranch(rs.getString("residentBranch"));
                lmtAttr.setBranchNumber(rs.getString("branchNumber"));
                lmtAttr.setLStringtCCDate(rs.getString("lastCCDate"));
                lmtAttr.setUserType(rs.getString("userType"));
                lmtAttr.setAllBranches(rs.getString("allBranches"));
                lmtAttr.setSps_all_brnch(rs.getString("sps_all_brnch"));
                lmtAttr.setLmtFrLclCrdt(rs.getString("lmtFrLclCrdt"));
                lmtAttr.setLmtFrLclDbt(rs.getString("lmtFrLclDbt"));
                lmtAttr.setIntrBrnCrLmt(rs.getString("intrBrnCrLmt"));
                lmtAttr.setIntrBrnDbtLmt(rs.getString("intrBrnDbtLmt"));
                lmtAttr.setAuthBrnch(rs.getString("authBrnch"));
                lmtAttr.setAuthSpsfdBrn(rs.getString("authSpsfdBrn"));
                lmtAttr.setAuthAmtFrLclCrdt(rs.getString("authAmtFrLclCrdt"));
                lmtAttr.setAuthAmtFrLclDbt(rs.getString("authAmtFrLclDbt"));
                lmtAttr.setAuthAmtFrIntrBrnCrdt(rs.getString("authAmtFrIntrBrnCrdt"));
                lmtAttr.setAuthAmtFrIntrBrnDbt(rs.getString("authAmtFrIntrBrnDbt"));
                lmtAttr.setLStringtXXDate(rs.getString("lastXXDate"));
                limitAttrList.add(lmtAttr);
                System.out.println(rs.getString("groupName") + " "+rs.getString("residentBranch")  + " "+rs.getString("branchNumber")  + " "
                                   +rs.getString("lastCCDate")  + " "+rs.getString("userType")  + " "+rs.getString("allBranches")  + " "
                                   +rs.getString("sps_all_brnch")  + " "+rs.getString("lmtFrLclCrdt")  + " "+rs.getString("lmtFrLclDbt")  + " "
                                    +rs.getString("intrBrnCrLmt")  + " "+rs.getString("intrBrnDbtLmt")  + " "+rs.getString("authBrnch")  + " "
                                   +rs.getString("authSpsfdBrn")  + " "+rs.getString("authAmtFrLclCrdt")  + " "+rs.getString("authAmtFrLclDbt")  + " "
                                   +rs.getString("authAmtFrIntrBrnCrdt")  + " "+rs.getString("authAmtFrIntrBrnDbt")  + " "+rs.getString("lastXXDate")  + " ");
              
          if(counter==3)
            break;
          }
        } catch (SQLException e) {
        }
      
      return limitAttrList;
    }
    private void connectDB()
    {   
        try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection connection = Platform.getOperationalDS().getConnection();
                    PreparedStatement stmt = null;
                    String sql = " ";
                    stmt = connection.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();
                    rs.next();
                    //as4ID = rs.getString(1);
                    
                }catch(SQLException ex)
                {
                    System.out.println("Error: "+ex.getMessage());
                } 
                catch(Exception ex)
                {
                    System.out.println("Error: "+ex.getMessage());        
                } 
                
                  
    }
    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName(DB_DRIVER);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection =
                    DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            System.out.println("Connected");
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }
}
