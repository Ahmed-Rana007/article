package com.hbl.selfservice;

import java.util.HashSet;
import java.util.Hashtable;

import java.util.List;
import java.util.Set;

import oracle.iam.platform.OIMClient;
import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.platform.entitymgr.vo.SearchCriteria;
import oracle.iam.identity.usermgmt.vo.User;


public class FindUsers {

    
	public void getUserDetails()
	{
        
    	
		try {
        UserManager usrMgr = UserSessaion.oimClient.getService(UserManager.class);

        SearchCriteria criteria = new SearchCriteria("User Login",
            "*", SearchCriteria.Operator.EQUAL);
        Set retSet = new HashSet();
        retSet.add("usr_key");
        retSet.add("User Login");
        retSet.add("First Name");
        retSet.add("Last Name");

        List<User> users = usrMgr.search(criteria, retSet, null);
        
        for(User user : users) {
            System.out.println("********LIST********"+user.getAttributeNames());
            
            Long usrKey = (Long)user.getAttribute("usr_key");
            String usrLogin = (String)user.getAttribute("User Login");
            String fn = (String)user.getAttribute("First Name");
            String ln = (String)user.getAttribute("Last Name");
            String test1 = (String)user.getAttribute("test1");
            String custom1 = (String)user.getAttribute("custom1");

            System.out.println(usrKey + " " + usrLogin + " " + fn + " " + ln + " " + test1 + " " + custom1);
        }

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
    }

}