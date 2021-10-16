package com.hbl.provisioning;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.login.LoginException;

import com.hbl.selfservice.LoginToOIM;
import com.hbl.selfservice.OIMUtils;
import com.hbl.selfservice.objects.GSonResponseFields;

import oracle.iam.platform.OIMClient;
import oracle.iam.platform.entitymgr.vo.SearchCriteria;
import oracle.iam.provisioning.api.ProvisioningConstants;
import oracle.iam.provisioning.api.ProvisioningServiceInternal;
import oracle.iam.provisioning.vo.Account;
import oracle.iam.provisioning.vo.AccountData;
import oracle.iam.reconciliation.utils.Sys;
 

public class SearchAccountsForUser {
 private static ProvisioningServiceInternal provisioningServiceInt= null;
 public static void main(String[] args) {
	 
	 LoginToOIM logOim = new LoginToOIM();
	 try {
		OIMClient oimClient = logOim.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
		SearchAccountsForUser saf = new SearchAccountsForUser();
		saf.getAccountLogin(oimClient,"MUHAMMAD.BABAR","164");
	} catch (LoginException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

 }
 public List<GSonResponseFields> getAccountData(OIMClient oimClient, String userLogin, String appInstance, String prefix)
 {
	 System.out.println(oimClient +" = "+ userLogin +" "+ appInstance);
	 List<GSonResponseFields> gsonResFields = new ArrayList<GSonResponseFields>();
	 try {
		 OIMUtils oimUtils = new OIMUtils();
		 OIMClient oimc = new OIMClient();
		 LoginToOIM ltoim = new LoginToOIM();
		 oimc=  ltoim.loggedIntoOIM("XELSYSADM", "Hblpoc_1234");
		 
		
		 provisioningServiceInt=oimc.getService(ProvisioningServiceInternal.class);
	   Set<String> userKeys = new HashSet<String>();
	    //userKeys.add("42021");
	    userKeys.add(oimUtils.getUserKey(userLogin, oimc));
	    System.out.println(userKeys);
	    //userKeys.add("30022");
	    
	   Map<String, String> controls = new HashMap<String, String>();
	   controls.put("Auditable", "false");
	   SearchCriteria userSearchCriteria = new SearchCriteria(ProvisioningConstants.AccountSearchAttribute.USER_ID.getId(), userKeys, SearchCriteria.Operator.IN);
	   SearchCriteria appInstSearchCriteria = new SearchCriteria(ProvisioningConstants.AccountSearchAttribute.APPINST_ID.getId(), appInstance, SearchCriteria.Operator.EQUAL);
	   SearchCriteria sc = new SearchCriteria(userSearchCriteria, appInstSearchCriteria, SearchCriteria.Operator.AND);
	   
	  Map<String,List<Account>> acctList =  provisioningServiceInt.searchAccounts(sc, controls);
	  System.out.println(acctList.size());
	  Map<String,List<Object>> normData = new HashMap<String,List<Object>>();
	  Map<String, Object> acctDataMap = null;
	  AccountData actData =null;
	  GSonResponseFields GSonResponseFieldsObj = null;
	  for(Map.Entry<String,List<Account>> accts : acctList.entrySet())
	  {
		  //System.out.println(acctList.get(accts.getKey()).get(0).getAccountDescriptiveField());
		  //System.out.println(acctList.get(accts.getKey()).get(0).getProvisionedBy());
		 // System.out.println("Parent Data\n"+acctList.get(accts.getKey()).get(0).getAccountData());
		  //System.out.println(acctList.get(accts.getKey()).get(0).getNormalizedData());
		  actData =acctList.get(accts.getKey()).get(0).getAccountData();
		  acctDataMap = actData.getData();
		   
		  normData = acctList.get(accts.getKey()).get(0).getNormalizedData();
		  System.out.println(normData);
		 // System.out.println(acctList.get(accts.getKey()).get(2).getAppInstance().getDisplayName());
	  }
	  /*for(Map.Entry<String,List<Object>> normDataLst : normData.entrySet())
	  {
		  if(normDataLst.getKey().equals("UD_KFILPK34") ) continue;
		  System.out.println(normDataLst.getKey()+" = "+normDataLst.getValue().toString().replace('[', ' ').toString().replace(']', ' ').trim());
	  }*/
	 
	  
	  for(Map.Entry<String,List<Object>> normDataLst : normData.entrySet())
	  {
		  GSonResponseFieldsObj = new GSonResponseFields(normDataLst.getKey().replace(' ', '_')+prefix, normDataLst.getKey(), normDataLst.getValue().toString().replace('[', ' ').toString().replace(']', ' ').trim());
		  System.out.println(normDataLst.getKey().replace(' ', '_')+prefix+" = "+normDataLst.getKey()+" = "+normDataLst.getValue().toString().replace('[', ' ').toString().replace(']', ' ').trim());
		  gsonResFields.add(GSonResponseFieldsObj);
	  }
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	 return gsonResFields;
	 
 }

 
 public String getAccountLogin(OIMClient oimClient, String userLogin, String appInstance)
 {
	 String accountID=null;
	 System.out.println(oimClient +" = "+ userLogin +" "+ appInstance);
	 List<GSonResponseFields> gsonResFields = new ArrayList<GSonResponseFields>();
	 try {
		 OIMUtils oimUtils = new OIMUtils();
		 OIMClient oimc = new OIMClient();
		 LoginToOIM ltoim = new LoginToOIM();
		 oimc=  ltoim.loggedIntoOIM("XELSYSADM", "Hblpoc_1234");
		 
		
		 provisioningServiceInt=oimc.getService(ProvisioningServiceInternal.class);
	   Set<String> userKeys = new HashSet<String>();
	    //userKeys.add("42021");
	    userKeys.add(oimUtils.getUserKey(userLogin, oimc));
	    System.out.println(userKeys);
	    //userKeys.add("30022");
	    
	   Map<String, String> controls = new HashMap<String, String>();
	   controls.put("Auditable", "false");
	   SearchCriteria userSearchCriteria = new SearchCriteria(ProvisioningConstants.AccountSearchAttribute.USER_ID.getId(), userKeys, SearchCriteria.Operator.IN);
	   SearchCriteria appInstSearchCriteria = new SearchCriteria(ProvisioningConstants.AccountSearchAttribute.APPINST_ID.getId(), appInstance, SearchCriteria.Operator.EQUAL);
	   SearchCriteria sc = new SearchCriteria(userSearchCriteria, appInstSearchCriteria, SearchCriteria.Operator.AND);
	   System.out.println("ok1:"+ProvisioningConstants.AccountSearchAttribute.USER_ID.getId());
	   System.out.println("ok2:"+ProvisioningConstants.AccountSearchAttribute.APPINST_ID.getId());
	  Map<String,List<Account>> acctList =  provisioningServiceInt.searchAccounts(sc, controls);
	  
	  System.out.println("SIZE:"+acctList.size());
	  Map<String,List<Object>> normData = new HashMap<String,List<Object>>();
	  Map<String, Object> acctDataMap = null;
	  AccountData actData =null;
	  GSonResponseFields GSonResponseFieldsObj = null;
	  for(Map.Entry<String,List<Account>> accts : acctList.entrySet())
	  {
		  System.out.println("ok");
			
		  //System.out.println(acctList.get(accts.getKey()).get(0).getAccountDescriptiveField());
		  //System.out.println(acctList.get(accts.getKey()).get(0).getProvisionedBy());
		 // System.out.println("Parent Data\n"+acctList.get(accts.getKey()).get(0).getAccountData());
		  //System.out.println(acctList.get(accts.getKey()).get(0).getNormalizedData());
		  actData =acctList.get(accts.getKey()).get(0).getAccountData();
		  acctDataMap = actData.getData();
		   
		  normData = acctList.get(accts.getKey()).get(0).getNormalizedData();
		  System.out.println(normData.get("User Group"));
		  accountID=normData.get("User ID").toString().substring(1, normData.get("User ID").toString().length() - 1);
		  System.out.println("Account ID: "+accountID+":"+normData.get("User ID"));
		 // System.out.println(acctList.get(accts.getKey()).get(2).getAppInstance().getDisplayName());
	  }}
	  catch (Exception e) {
			// TODO: handle exception
		}
	return accountID;
	 

	 }
 
}