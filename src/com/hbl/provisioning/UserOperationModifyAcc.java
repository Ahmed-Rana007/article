package com.hbl.provisioning;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
//package com.massiveGaze.password;
import javax.security.auth.login.LoginException;

import com.bea.httppubsub.json.JSONObject;
import com.hbl.selfservice.LoginToOIM;


import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.platform.OIMClient;
//import oracle.iam.platform.authopss.api.AdminRoleService;
import oracle.iam.platform.authopss.vo.AdminRole;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.identity.usermgmt.vo.UserManagerResult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

import Thor.API.tcResultSet;
import java.util.Set;
import Thor.API.Operations.tcUserOperationsIntf;
import oracle.iam.identity.exception.NoSuchUserException;
import oracle.iam.identity.exception.UserAlreadyExistsException;
import oracle.iam.identity.exception.UserCreateException;
import oracle.iam.identity.exception.UserDeleteException;
import oracle.iam.identity.exception.UserDisableException;
import oracle.iam.identity.exception.UserEnableException;
import oracle.iam.identity.exception.UserLockException;
import oracle.iam.identity.exception.UserLookupException;
import oracle.iam.identity.exception.UserManagerException;
import oracle.iam.identity.exception.UserModifyException;
import oracle.iam.identity.exception.UserUnlockException;
import oracle.iam.identity.exception.ValidationFailedException;
import oracle.iam.platform.kernel.spi.PreProcessHandler;
import oracle.iam.platformservice.api.AdminRoleService;
import oracle.iam.platform.kernel.spi.PostProcessHandler;
import oracle.iam.identity.usermgmt.api.UserManagerConstants;
import oracle.iam.platform.context.ContextManager;
import oracle.iam.selfservice.self.selfmgmt.api.AuthenticatedSelfService;
//import com.massiveGaze.connection.Platform;



import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.api.UserManagerConstants;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.passwordmgmt.api.PasswordMgmtService;
import oracle.iam.passwordmgmt.vo.ValidationResult;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.entitymgr.vo.SearchCriteria;
import oracle.iam.provisioning.api.ProvisioningConstants;
import oracle.iam.provisioning.api.ProvisioningService;
import oracle.iam.provisioning.vo.Account;

//-----------------------------------------------Account Provision API--------------------
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import javax.security.auth.login.LoginException;



import oracle.iam.identity.exception.AccessDeniedException;
import oracle.iam.identity.exception.UserSearchException;
import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.api.UserManagerConstants;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.entitymgr.vo.SearchCriteria;
import oracle.iam.provisioning.api.ApplicationInstanceService;
import oracle.iam.provisioning.api.ProvisioningService;
import oracle.iam.provisioning.vo.Account;
import oracle.iam.provisioning.vo.AccountData;
import oracle.iam.provisioning.vo.ApplicationInstance;
import oracle.iam.provisioning.vo.FormField;
import oracle.iam.provisioning.vo.FormInfo;
import oracle.iam.request.api.RequestService;
import oracle.iam.request.exception.NoRequestPermissionException;
import oracle.iam.request.exception.RequestServiceException;
import oracle.iam.request.vo.Request;
import oracle.iam.request.vo.RequestConstants;
import oracle.iam.request.vo.RequestSearchCriteria;

import java.util.logging.Level; 
import java.util.logging.Logger; 
import com.bea.httppubsub.json.JSONObject;
//import com.google.gson.JsonObject;
import weblogic.jdbc.wrapper.Array;

import oracle.iam.platform.OIMClient;


public class UserOperationModifyAcc {

	 public static OIMClient oimClient;
	    public static UserManager userManager;

	       public  static void  connect() throws LoginException,
	                                        ValidationFailedException,
	                                        UserAlreadyExistsException,
	                                        UserCreateException {


	        final String OIM_PROVIDER_URL =
	            "t3://10.9.166.59:14000/oim";
	        final String AUTHWL_PATH = "E:\\HBL\\GUI\\29_Master\\SelfService\\WebContent\\WEB-INF\\lib\\authwl.conf";
	        final String APPSERVER_TYPE = "wls"; // WebLogic Server
	        final String FACTORY_INITIAL_TYPE =
	            "weblogic.jndi.WLInitialContextFactory";
	        final String OIM_ADMIN_USERNAME = "XELSYSADM";
	        final String OIM_ADMIN_PASSWORD = "Hblpoc_1234";
	        
	    

	        System.setProperty("java.security.auth.login.config", AUTHWL_PATH);
	        System.setProperty("APPSERVER_TYPE", APPSERVER_TYPE);

	        // Create an instance of OIMClient with OIM environment information
	        Hashtable<String, String> env = new Hashtable<String, String>();
	        env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL, FACTORY_INITIAL_TYPE);
	        env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, OIM_PROVIDER_URL);

	        // Establish an OIM Client
	        oimClient = new OIMClient(env);

	        // Login to OIM with System Administrator Credentials
	        oimClient.login(OIM_ADMIN_USERNAME, OIM_ADMIN_PASSWORD.toCharArray());
	        userManager = oimClient.getService(UserManager.class);
	       


	    }
	    public  static void  Devconnect() throws LoginException,
	                                        ValidationFailedException,
	                                        UserAlreadyExistsException,
	                                        UserCreateException {


	        final String OIM_PROVIDER_URL =
	            "t3://idmoig:14000/oim";
	        final String AUTHWL_PATH = "E:\\002_Development\\config\\authwl.conf";
	        final String APPSERVER_TYPE = "wls"; // WebLogic Server
	        final String FACTORY_INITIAL_TYPE =
	            "weblogic.jndi.WLInitialContextFactory";
	        final String OIM_ADMIN_USERNAME = "MAJID.ASGHAR";
	        final String OIM_ADMIN_PASSWORD = "Husky@123";

	        System.setProperty("java.security.auth.login.config", AUTHWL_PATH);
	        System.setProperty("APPSERVER_TYPE", APPSERVER_TYPE);

	        // Create an instance of OIMClient with OIM environment information
	        Hashtable<String, String> env = new Hashtable<String, String>();
	        env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL, FACTORY_INITIAL_TYPE);
	        env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, OIM_PROVIDER_URL);

	        // Establish an OIM Client
	        oimClient = new OIMClient(env);

	        // Login to OIM with System Administrator Credentials
	        oimClient.login(OIM_ADMIN_USERNAME, OIM_ADMIN_PASSWORD.toCharArray());
	        userManager = oimClient.getService(UserManager.class);


	    }


	    ////////////////////////////////////////


	    public static void modifyUser1(String user_key) {
	        
	        User user = new User(user_key.toString());
	        //user.setAttribute("act_key", new Long(126));
	        user.setAttribute("Mobile", "+92-300-1295291");
	        
	        
	        try {
	            userManager.modify(user);
	            
	        } catch (UserModifyException ex) {
	            System.out.print(ex.getErrorMessage());
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }

	    public static void createUser(String userId) { //Function to create User
	        HashMap<String, Object> userAttributeValueMap =
	            new HashMap<String, Object>();
	        userAttributeValueMap.put("act_key", new Long(6));
	        //userAttributeValueMap.put("User Login", userId);
	        userAttributeValueMap.put("Provision", "Other");
	        userAttributeValueMap.put("First Name", "Abdul");
	        userAttributeValueMap.put("Last Name", "Hameed");
	        //userAttributeValueMap.put("Email", "wasqas25@example.com");
	        userAttributeValueMap.put("usr_password", "Pakistan@123");
	        // userAttributeValueMap.put("Manager", "muhammad Yaseen");
	        //userAttributeValueMap.put("Office", "OS-IBEX-Lahore");
	        userAttributeValueMap.put("DepartmentName", "Customer Care");
	        // USR_OFFICE_NAME, , USR_UDF_DEPARTMENTNAME, USR_EMP_NO
	        userAttributeValueMap.put("Role", "CWK");
	        userAttributeValueMap.put("Mobile", "+92-300-");

	        User user = new User("malleshk", userAttributeValueMap);
	        //user.setManagerKey(new Long(46225));
	        try {
	            userManager.create(user);


	            System.out.println("\nUser got created….");
	        } catch (ValidationFailedException e) {
	            e.printStackTrace();
	        } catch (UserAlreadyExistsException e) {
	            e.printStackTrace();
	        } catch (UserCreateException e) {
	            e.printStackTrace();
	        }
	    }

	    public void disableUser(String userId) { //Function to disable user
	    try {
	        connect();
	    } catch (Exception ex) {
	         ex.printStackTrace() ;
	    } 
	        
	        try {
	            userManager.disable(userId, true);
	            
	            System.out.println("\n Disabled user Successfully");
	        } catch (ValidationFailedException e) {
	            e.printStackTrace();
	        } catch (UserDisableException e) {
	            e.printStackTrace();
	        } catch (NoSuchUserException e) {
	            e.printStackTrace();
	        }
	    }

	    public void enableUser(String userId) { //Function to enable user
	        try {
	            userManager.enable(userId, true);
	            System.out.print("\n Enabled user Successfully");
	        } catch (ValidationFailedException e) {
	            e.printStackTrace();
	        } catch (UserEnableException e) {
	            e.printStackTrace();
	        } catch (NoSuchUserException e) {
	            e.printStackTrace();
	        }
	    }

	    public  String changePassword(String userId,String Pass) { //Function to reset user password
	    	String result="";
	    try {
	        connect();
	    } catch (Exception ex) {
	         ex.printStackTrace() ;
	    } 
	        try {
	           
	            userManager.changePassword(userId, Pass.toCharArray(), true,
	                                       true);
	            
	            System.out.println("Reset Password done…");
	            return "changed";

	           
	        } catch (NoSuchUserException e) {
	            e.printStackTrace();
	            result=e.getMessage();
	        } catch (UserManagerException e) {
	            e.printStackTrace();
	            result=e.getMessage();
	        }
	        
	        return result;
	    }
//modifyUser
	    public static void modifyUser(String userId,String username,String startDate,String endDate,String leaveType) {
	    	
	    	
	    	System.out.println(userId+" "+username.toUpperCase()+" "+startDate);
	    	
	    	
	    	
	        try {
	            connect();
	        } catch (Exception ex) {
	             ex.printStackTrace() ;
	        } 

	        try {
	            
	        	

	            
	            HashMap<String, Object> userAttributeValueMap =
	                new HashMap<String, Object>();
	         
	             userAttributeValueMap.put("Leave_Type",leaveType);
	             userAttributeValueMap.put("Leave_To",new Date(endDate));
	             userAttributeValueMap.put("Leave_From",new Date(startDate));
	            
	         
	            User retrievedUser = searchUser(username.toUpperCase());
	            User user =
	                new User(retrievedUser.getEntityId(), userAttributeValueMap);

	            //User user = new User("2157", userAttributeValueMap);

	            userManager.modify(user);
	            System.out.println("\nUpdated user datails.. \n");
	            
	        } catch (ValidationFailedException e) {
	            e.printStackTrace();
	        } catch (UserModifyException e) {
	        	
	        
	        	
	        	  System.out.println("\nUpdated user datails.. \n");
	        } catch (NoSuchUserException e) {
	            e.printStackTrace();
	        }
	        
	        
	    }
	  //modifyUser
	    
	    //modify details
	    public static void modifyAccDetails(String userlogin,String userkey,String firstname,String linemanager,String middlename,String org,String lastname,String fullname,String email,String branchcode,String branchadd,String personalnumber,String cname,String mobile) {
	    	
	    	
	    	System.out.println(userlogin+" "+userkey.toUpperCase()+" "+userkey);
	    	
	    	
	    	
	        try {
	            connect();
	        } catch (Exception ex) {
	             ex.printStackTrace() ;
	        } 

	        try {
	            
	        	

	            
	            HashMap<String, Object> userAttributeValueMap =
	                new HashMap<String, Object>();
	         
	            
		        //userAttributeValueMap.put("User Login", userId);
		        
		        userAttributeValueMap.put("First Name",firstname);
		        userAttributeValueMap.put("Last Name",lastname);
		        userAttributeValueMap.put("Email",email );
		        //userAttributeValueMap.put("usr_password", "Pakistan@123");
		        // userAttributeValueMap.put("Manager", "muhammad Yaseen");
		        //userAttributeValueMap.put("Office", "OS-IBEX-Lahore");
		        //userAttributeValueMap.put("DepartmentName", "Customer Care");
		        // USR_OFFICE_NAME, , USR_UDF_DEPARTMENTNAME, USR_EMP_NO
		       // userAttributeValueMap.put("Role", "CWK");
		        userAttributeValueMap.put("Mobile", mobile);
	         
	            User retrievedUser = searchUser(userlogin.toUpperCase());
	            User user =
	                new User(retrievedUser.getEntityId(), userAttributeValueMap);

	            //User user = new User("2157", userAttributeValueMap);

	            userManager.modify(user);
	            System.out.println("\nUpdated user datails.. \n");
	            
	        } catch (ValidationFailedException e) {
	            e.printStackTrace();
	        } catch (UserModifyException e) {
	        	
	        
	        	
	        	  System.out.println("\nUpdated user datails.. \n");
	        } catch (NoSuchUserException e) {
	            e.printStackTrace();
	        }
	        
	     
	    }
	    //modify details
	    
	    
	    public void modifyOrgnization(String userId, String act_key) {
	        try {
	            connect();
	        } catch (Exception ex) {
	             ex.printStackTrace() ;
	        } 

	        try {
	            
	                

	            
	            HashMap<String, Object> userAttributeValueMap =
	                new HashMap<String, Object>();
	            // userAttributeValueMap.put("act_key", new Long(1));
	            // userAttributeValueMap.put("User Login", userId);
	            // userAttributeValueMap.put("First Name", "mahesh");
	            userAttributeValueMap.put("act_key", new Long(act_key));
	            
	            // userAttributeValueMap.put("RoomNumber", "222");
	            User retrievedUser = searchUser(userId);
	            User user =
	                new User(retrievedUser.getEntityId(), userAttributeValueMap);

	            //User user = new User("2157", userAttributeValueMap);

	            userManager.modify(user);
	            System.out.println("\nUpdated user datails.. \n");
	            
	        } catch (ValidationFailedException e) {
	            e.printStackTrace();
	        } catch (UserModifyException e) {
	            e.printStackTrace();
	        } catch (NoSuchUserException e) {
	            e.printStackTrace();
	        }
	    }

	    public static User searchUser(String userId) {

	        Set<String> resAttrs = new HashSet<String>();
	        User user = null;
	        try {
	            user = userManager.getDetails(userId, resAttrs, true);
	            //System.out.println(user.getAccountStatus());
	        } catch (NoSuchUserException e) {
	            e.printStackTrace();
	        } catch (UserLookupException e) {
	            e.printStackTrace();
	        }
	        return user;
	    }

	    public void createBulkUser(String employeeNo, String firstName,
	                                  String lastName, String act_key,
	                                  String provision, String department,
	                                  String officeName, String empType,
	                                  String mobile, String manager, String displayName,
	                                  String title,String cell) { //Function to create User
	                       
	           try {
	               connect();
	           } catch (Exception ex) {
	                ex.printStackTrace();
	           } //Function to create User
	           
	           //System.out.println(employeeNo+" "+  firstName+" "+lastName+""+provision+" "+displayName+" "+ displayName+" ");
	        HashMap<String, Object> userAttributeValueMap =
	            new HashMap<String, Object>();
	      // userAttributeValueMap.put("act_key", new Long(126));//for Mindbridge
	       
	        userAttributeValueMap.put("act_key", new Long(124));//for Ibex
	        
	       //userAttributeValueMap.put("act_key", new Long(36));//for Guests
	        //userAttributeValueMap.put("User Login", userId);
	        
	         userAttributeValueMap.put("Title", "CCR-IBEX");
	        //userAttributeValueMap.put("Title", "CCR-MB");
	       // userAttributeValueMap.put("Title", "CCR-ABACUS");
	        //userAttributeValueMap.put("Title", "Vendor");
	                
	        userAttributeValueMap.put("Employee Number", employeeNo.toString());
	        
	        userAttributeValueMap.put("Provision", "AD");
	        //userAttributeValueMap.put("Provision", "Other");
	        
	        
	        userAttributeValueMap.put("Level", "vic");
	        
	        userAttributeValueMap.put("First Name", firstName.toString());
	        userAttributeValueMap.put("Last Name", lastName.toString());
	        userAttributeValueMap.put("Display Name", displayName.toString());
	        userAttributeValueMap.put("Company", "Jazz");
	        //userAttributeValueMap.put("Email", "wasqas25@example.com");
	        userAttributeValueMap.put("usr_password", "Hash#901");
	        // userAttributeValueMap.put("Manager", "muhammad Yaseen");
	         
	        //userAttributeValueMap.put("Office", "OS-ABACUS-LAHORE");
	        
	      //  userAttributeValueMap.put("Description","2FA");     //for abacus only
	      //  userAttributeValueMap.put("Initials","abc@exammple.com");
	          
	        //  userAttributeValueMap.put("Office", "MINDBRIDGE-Lahore");
	         userAttributeValueMap.put("Office", "OS-IBEX-Islamabad");
	      //  userAttributeValueMap.put("Office", "OS-IBEX-Karachi");
	       // userAttributeValueMap.put("Office", "OS-IBEX-Lahore");
	        //userAttributeValueMap.put("Office", "DHQ 3 F-8");
	        
	        
	        userAttributeValueMap.put("DepartmentName", "Customer Care");
	        //userAttributeValueMap.put("DepartmentName", "Technology");
	        //userAttributeValueMap.put("DepartmentName", "Digital Financial Services");
	        // USR_OFFICE_NAME, , USR_UDF_DEPARTMENTNAME, USR_EMP_NO
	        
	        userAttributeValueMap.put("Role", "CWK");
	        //  userAttributeValueMap.put("Role", "OTHER"); //for vendor
	        //
	        userAttributeValueMap.put("Mobile", cell.toString());
	       
	            
	        User user = new User("malleshk", userAttributeValueMap);
	             
	                 user.setManagerKey(new Long(10117)); //AMIR JOHN
	        //       user.setManagerKey(new Long(3822));    //Faraz Saleem
	          //     user.setManagerKey(new Long(2519)); //Khalil
	               
	        try {
	           
	             userManager.create(user);


	            System.out.println("\nUser got created….");
	        } catch (ValidationFailedException e) {
	            e.printStackTrace();
	        } catch (UserAlreadyExistsException e) {
	            e.printStackTrace();
	        } catch (UserCreateException e) {
	            e.printStackTrace();
	        }
	    }
	   


	    public void createBulkUserIsupplier(String employeeNo, String firstName,
	                                  String lastName, String email,
	                                  String USR_LOGIN, String department,
	                                  String officeName, String empType,
	                                  String mobile, String manager, String displayName,
	                                  String title,String cell) { //Function to create User
	                       
	           try {
	               connect();
	           } catch (Exception ex) {
	                ex.printStackTrace();
	           } //Function to create User
	           
	           //System.out.println(employeeNo+" "+  firstName+" "+lastName+""+provision+" "+displayName+" "+ displayName+" ");
	        HashMap<String, Object> userAttributeValueMap =
	            new HashMap<String, Object>();
	      // userAttributeValueMap.put("act_key", new Long(126));//for Mindbridge
	       
	        userAttributeValueMap.put("act_key", new Long(204));//for EBS HR-HQ
	        
	        userAttributeValueMap.put("User Login", USR_LOGIN.toString());
	        
	        //userAttributeValueMap.put("Title", "CCR-MB");
	        userAttributeValueMap.put("Title", "EBS User");
	        
	        userAttributeValueMap.put("Employee Number", employeeNo.toString());
	        userAttributeValueMap.put("Provision", "Other");
	        userAttributeValueMap.put("First Name", firstName.toString());
	        userAttributeValueMap.put("Last Name", lastName.toString());
	        userAttributeValueMap.put("Display Name", displayName.toString());
	    //    userAttributeValueMap.put("Company", "Jazz");
	        
	        userAttributeValueMap.put("Email", email.toString());
	        //userAttributeValueMap.put("usr_password", "Pakistan@123");
	        // userAttributeValueMap.put("Manager", "muhammad Yaseen");
	         
	        //   userAttributeValueMap.put("Office", "MINDBRIDGE-Lahore");
	         userAttributeValueMap.put("Office", "Franchise");
	        //userAttributeValueMap.put("Office", "OS-IBEX-Karachi");
	        //userAttributeValueMap.put("Office", "OS-IBEX-Lahore");
	        
	        userAttributeValueMap.put("DepartmentName", "Franchise");
	        // USR_OFFICE_NAME, , USR_UDF_DEPARTMENTNAME, USR_EMP_NO
	        userAttributeValueMap.put("Role", "CWK");
	        //userAttributeValueMap.put("Mobile", cell.toString());
	       
	            
	        User user = new User("malleshk", userAttributeValueMap);
	             //user.setManagerKey(new Long(6019)); //Jamshed Waheed Iqbal
	        //       user.setManagerKey(new Long(10117)); //AMIR JOHN
	              
	        try {
	           
	             userManager.create(user);


	            System.out.println("\nUser got created….");
	        } catch (ValidationFailedException e) {
	            e.printStackTrace();
	        } catch (UserAlreadyExistsException e) {
	            e.printStackTrace();
	        } catch (UserCreateException e) {
	            e.printStackTrace();
	        }
	    }
	    

	   //------------------------------------Modify Manager-------------------------
	    public void modifyManager(String userId, String ManagerKey) {
	        try {
	            connect();
	        } catch (Exception ex) {
	             ex.printStackTrace() ;
	        } 

	        try {
	            
	                

	            
	            HashMap<String, Object> userAttributeValueMap =
	                new HashMap<String, Object>();
	            // userAttributeValueMap.put("act_key", new Long(1));
	            // userAttributeValueMap.put("User Login", userId);
	            // userAttributeValueMap.put("First Name", "mahesh");
	            //userAttributeValueMap.put("Mobile",mobile);
	            
	            // userAttributeValueMap.put("RoomNumber", "222");
	            User retrievedUser = searchUser(userId);
	            User user =
	                new User(retrievedUser.getEntityId(), userAttributeValueMap);
	                user.setManagerKey(new Long(ManagerKey));
	            
	            
	            //Long m=null;
	            //user.setManagerKey(m);
	            
	            
	            //User user = new User("2157", userAttributeValueMap);

	            userManager.modify(user);
	            System.out.println("\nUpdated user datails.. \n");
	            
	        } catch (ValidationFailedException e) {
	            e.printStackTrace();
	        } catch (UserModifyException e) {
	            e.printStackTrace();
	        } catch (NoSuchUserException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    //----------------------------------Modify Title--------------------------------
	    public void modifyUserTitle(String userId, String title) {
	        try {
	            connect();
	        } catch (Exception ex) {
	             ex.printStackTrace() ;
	        } 

	        try {
	            
	                

	            
	            HashMap<String, Object> userAttributeValueMap =
	                new HashMap<String, Object>();
	            // userAttributeValueMap.put("act_key", new Long(1));
	            // userAttributeValueMap.put("User Login", userId);
	            // userAttributeValueMap.put("First Name", "mahesh");
	            userAttributeValueMap.put("Description",title);
	            
	            // userAttributeValueMap.put("RoomNumber", "222");
	            User retrievedUser = searchUser(userId);
	            User user =
	                new User(retrievedUser.getEntityId(), userAttributeValueMap);

	            //User user = new User("2157", userAttributeValueMap);

	            userManager.modify(user);
	            System.out.println("\nUpdated user datails.. \n");
	            
	        } catch (ValidationFailedException e) {
	            e.printStackTrace();
	        } catch (UserModifyException e) {
	            e.printStackTrace();
	        } catch (NoSuchUserException e) {
	            e.printStackTrace();
	        }
	    }

	 //=============================================Dev==========================================================================
	    
	    public String getRole(String userKey) { //Function to disable user
	    
	    	System.out.println("Start");
	    String status="N";
	    try {
			connect();
		} catch (ValidationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserCreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println("calling method");
	    
	    AdminRoleService adminRoleService=oimClient.getService(AdminRoleService.class);
	    
	   
	    List <AdminRole> userAdminrole=adminRoleService.getAdminRolesForUser(userKey,null);
	   for(int i=0;i<userAdminrole.size();i++)
	   {
		   
		   System.out.println(userAdminrole.get(i).getRoleName());
		   if(userAdminrole.get(i).getRoleName().contains("OrclOIMSystemAdministrator"))
		   {
			status = "Y";   
		   }
	   }
	   
//	    if(userAdminrole.size()>0)
//	    {
//	    	System.out.print(userAdminrole.size());
//	    	status="Y";
//	    }
//	    else
//	    {
//	    	System.out.print(userAdminrole.size());
//	    	status="N";
//	    }
//	   
	    return status;
	    
	    }

	    //getPendingRequests
	   	    //mm
	 

	    
	 /*public void AttributesSearchUser(String Userid){
	         try {
	             Devconnect();
	             System.out.println("Connected");
	         } catch (Exception ex) {
	              ex.printStackTrace() ;
	         } 
	       ProvisioningService ps = oimClient.getService(ProvisioningService.class);
	          tcUserOperationsIntf userintf = oimClient.getService(tcUserOperationsIntf.class);
	          System.out.println("inside getUserDetails method");
	          System.out.println("User id --->"+Userid);
	         
	         
	          HashMap userMap = new HashMap();
	            try {
	           //Fetching the User Profile attribute details
	             userMap.put("Users.User ID", Userid);
	             tcResultSet moResultSet = userintf.findUsers(userMap);
	             String[] CName=moResultSet.getColumnNames();
	         
	             for(String cname :CName){ 
	              System.out.println(cname+"--->"+moResultSet.getStringValue(cname));
	           // System.out.println("Manager login is --->"+moResultSet.getStringValue("USERS.MANAGER LOGIN"));
	           
	             }

	                }
	            catch (Exception e) {
	             e.printStackTrace();
	            }

	     }*/
	    
	    
	    //
	    public String changeUserPassword(OIMClient oimClient) throws Exception {
	        
//	    	
			AuthenticatedSelfService m_authselfservice = oimClient.getService(AuthenticatedSelfService.class);
			System.out.print(m_authselfservice);
		//	 UserManager userManager =null;
		        try {
		        	
		        	char [] oldPass = "Hello@123".toCharArray();
		        	char [] newPass = "Hello@1234".toCharArray();
		        	char [] confPass = "Hello@1234".toCharArray();
		        	
		        	
		        	m_authselfservice.changePassword(oldPass, newPass, confPass);
		        	
		      //  	m_authselfservice.
		   
		            System.out.println("Password Successfully");
		            return "";
		            
		        } catch (Exception e) {
		           System.out.println("Kashif: "+e.getMessage().toString());
		           
		  		 
		  		  
		          
		        }  
		        return ""; 
	    }
	    //

	    //----------------------------------------Account Provision-------------------------------------------
	    

		public static void main(String[] args) throws Exception {
			
	UserOperationModifyAcc uoma = new UserOperationModifyAcc();
	
/*	OIMClient oimClient = null;
	LoginToOIM loginToOim = new LoginToOIM();
	oimClient = loginToOim.loggedIntoOIM("MAJID","Test@123"); */
	
	//String status = uoma.changePassword("HARIS", "Test@1234");
	String role = uoma.getRole("68033");
	//String role = uoma.getRole("1");
	System.out.print(role);
	
//	System.out.print(uoma.getRole("54020"));
}
			
		}
//

//

