package com.hbl.selfservice;
import com.hbl.selfservice.objects.AppInstanceObj;
import com.hbl.selfservice.objects.EntitlementObj;
import com.hbl.selfservice.objects.OrganizationObj;
import com.hbl.selfservice.objects.RoleUser;
import com.thortech.xl.crypto.Base64;
import com.thortech.xl.crypto.tcCryptoUtil;
import com.thortech.xl.crypto.tcSignatureMessage;

import Thor.API.tcResultSet;
import Thor.API.Operations.tcLookupOperationsIntf;
import Thor.API.Operations.tcUserOperationsIntf;
import Thor.API.Security.XLClientSecurityAssociation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import oracle.iam.catalog.vo.CatalogSearchCriteria.Operator;
import oracle.iam.identity.exception.UserSearchException;
import oracle.iam.identity.orgmgmt.api.OrganizationManager;
import oracle.iam.identity.orgmgmt.api.OrganizationManagerConstants;
import oracle.iam.identity.orgmgmt.vo.Organization;
import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.api.UserManagerConstants;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.identity.usermgmt.vo.UserManagerResult;
import oracle.iam.passwordmgmt.api.PasswordMgmtService;
import oracle.iam.passwordmgmt.vo.ValidationResult;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.entitymgr.vo.SearchCriteria;
import oracle.iam.provisioning.api.EntitlementService;
import oracle.iam.provisioning.api.ProvisioningConstants;
import oracle.iam.provisioning.api.ProvisioningConstants.EntitlementInstanceSearchAttribute;
import oracle.iam.provisioning.api.ProvisioningService;
import oracle.iam.provisioning.vo.Account;
import oracle.iam.provisioning.vo.Entitlement;
import oracle.iam.provisioning.vo.EntitlementInstance;
import oracle.iam.identity.rolemgmt.api.RoleManager;
import oracle.iam.identity.rolemgmt.api.RoleManagerConstants.RoleAttributeName;
import oracle.iam.identity.rolemgmt.vo.Role;
import oracle.iam.identity.rolemgmt.vo.RoleManagerResult;
public class OIMUtils {
    public OIMUtils() {
        super();
    }

    public OIMClient getOIMClient(String adminUser) {
        final String methodName = ":::getOIMClient:::";

        OIMClient oimClient = null;
        tcSignatureMessage message = null;
        try {
            Hashtable env = new Hashtable();
            env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL,
                    "weblogic.jndi.WLInitialContextFactory");
            message = tcCryptoUtil.sign(adminUser, "PrivateKey");
            String xelsysadmUser =
                (String)message.getSignedObject().getObject();
            byte[] serialzedMessage =
                tcCryptoUtil.getSerializedMessage(message);
            String xelsysPassword =
                "xlSigned::" + Base64.getEncoded(serialzedMessage);
            oimClient = new OIMClient(env);
            oimClient.login(xelsysadmUser, xelsysPassword.toCharArray());
            System.out.println(methodName +
                               " - Action Connection Obtained...");

        } catch (Exception ex) {
            System.out.println(methodName + " " + ex.getMessage());
        }


        return oimClient;
    }

    public String createUser(String firstName, String lastName, String role,
                               String country, String manager) throws Exception{
        UserManager userAPI =
            getOIMClient("XELSYSADM").getService(UserManager.class);
        HashMap<String, Object> attr = new HashMap<String, Object>();
        attr.put("First Name", firstName);
        attr.put("Last Name", lastName);
        attr.put("Role", role);
        attr.put("act_key", Long.parseLong("202"));
       // attr.put(UserManagerConstants.AttributeName.MANAGER_KEY.getId(), Long.parseLong(getUserKey(manager)));
        attr.put("Country", country);
        User user = new User(null, attr);
        
            UserManagerResult result = userAPI.create(user);
            System.out.println(result.getEntityId() + " " + result.getClass());
            java.util.Set retAttrs = new HashSet();
            retAttrs.add(UserManagerConstants.AttributeName.USER_LOGIN.getId());
            User createUser =
                userAPI.getDetails(UserManagerConstants.AttributeName.USER_KEY.getId(),
                                   result.getEntityId(), retAttrs);
            System.out.println(createUser.getLogin());
        
        return createUser.getLogin();
    }
    public String getUserKey(String userLogin, OIMClient oimClient) throws Exception
    {
        Set retAttr = new HashSet();
        retAttr.add(UserManagerConstants.AttributeName.USER_KEY.getId());
        UserManager userAPI = oimClient.getService(UserManager.class);
        User user = userAPI.getDetails(userLogin, retAttr, true);
        String userKey = user.getEntityId();
         
        return userKey;
    }
public String getManagerKey(String userLogin, OIMClient oimClient) throws Exception {
		Set retAttr = new HashSet();
		retAttr.add(UserManagerConstants.AttributeName.MANAGER_KEY.getId());
		UserManager userAPI = oimClient.getService(UserManager.class);
		User user = userAPI.getDetails(userLogin, retAttr, true);
		String userKey = user.getManagerKey();
		

		return userKey;
	}
	public String getUserLogin(String userKey, OIMClient oimClient) throws Exception {
		Set retAttr = new HashSet();
		retAttr.add(UserManagerConstants.AttributeName.USER_LOGIN.getId());
		UserManager userAPI = oimClient.getService(UserManager.class);
		User user = userAPI.getDetails(userKey, retAttr, false);
		String userLogin = user.getLogin();
		return userLogin;
	}

	public OIMUser getUserDetails(String userLogin, OIMClient oimClient) {

		OIMUser oimUser = new OIMUser();
		try {
        UserManager usrMgr = oimClient.getService(UserManager.class);

        SearchCriteria criteria = new SearchCriteria("User Login",
            userLogin, SearchCriteria.Operator.EQUAL);
        Set retSet = new HashSet();
        retSet.add("usr_key");
        retSet.add("User Login");
        retSet.add("First Name");
        retSet.add("Last Name");
        retSet.add("Email");
        retSet.add("Mobile");
        retSet.add("Branch_Code");
        retSet.add("Branch_Name");
        retSet.add("Display Name");
        retSet.add("Employee Number");
        retSet.add("Status");
        //
        retSet.add("Department Number");
           
        List<User> users = usrMgr.search(criteria, retSet, null);
        
        for(User user : users) {
            System.out.println("********LIST********"+user.getAttributeNames());
            System.out.println("********LIST********"+user.getStatus());
            Long usrKey = (Long)user.getAttribute("usr_key");
            String usrLogin = (String)user.getAttribute("User Login");
            String fn = (String)user.getAttribute("First Name");
            String ln = (String)user.getAttribute("Last Name");
            String email = (String)user.getAttribute("Email");
            String custom1 = (String)user.getAttribute("custom1");
            String mobileNumber = (String)user.getAttribute("Mobile");
            String Branch_Code = (String)user.getAttribute("Branch_Code");
            String Branch_Name = (String)user.getAttribute("Branch_Name");
            String display_Name = (String)user.getAttribute("Display Name");
            String employee_number = (String)user.getAttribute("Employee Number");
            String department_number = (String)user.getAttribute("Department Number");
            System.out.println(usrKey + " " + usrLogin + " " + fn + " " + ln + " DepN:" +  department_number+ " " + custom1);
            oimUser.setUserFirstName(fn);
            oimUser.setUserLastName(ln);
            oimUser.setUserKey(usrKey);
            oimUser.setUserEmail(email);
            oimUser.setMobileNumber(mobileNumber);
            oimUser.setBranch_Code(Branch_Code);
            oimUser.setBranchName(Branch_Name);
            oimUser.setUserlogin(usrLogin);
            oimUser.setUserDisplayName(display_Name);
            oimUser.setPersonalNumber(employee_number);
            oimUser.setDepartmentNumber(department_number);
        }

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return oimUser;
    }
    
    public Map<Long,UsersList>  getUserAllUsers(OIMClient oimClient)
	{
        
    	
    	
    	UsersList oimUserList = null;
    	ArrayList<UsersList> userList = new ArrayList<UsersList>();
    	Map<Long,UsersList> userHashMapList=new Hashtable<Long,UsersList>(); 
		try {
			LoginToOIM lo = new LoginToOIM();
	    	 
	    	
        UserManager usrMgr = oimClient.getService(UserManager.class);

        SearchCriteria criteria1 = new SearchCriteria("User Login",
            "*", SearchCriteria.Operator.EQUAL);
        Set retSet = new HashSet();
        retSet.add("usr_key");
        retSet.add("User Login");
        retSet.add("First Name");
        retSet.add("Last Name");
        retSet.add("Email");
        retSet.add("Mobile");
        retSet.add("Employee Number");
        retSet.add("Branch_Code");
        retSet.add("Status");
        List<User> users = usrMgr.search(criteria1, retSet, null);
        
        for(User user : users) {
            System.out.println("********LIST********"+user.getAttributeNames());
            
            Long usrKey = (Long)user.getAttribute("usr_key");
            String usrLogin = (String)user.getAttribute("User Login");
            String fn = (String)user.getAttribute("First Name");
            String ln = (String)user.getAttribute("Last Name");
            String email = (String)user.getAttribute("Email");
            String custom1 = (String)user.getAttribute("custom1");
            String mobileNumber = (String)user.getAttribute("Mobile");
            String Employee = (String)user.getAttribute("Employee");
            String Branch_Code = (String)user.getAttribute("Branch_Code");
            String Status = (String)user.getAttribute("Status");
            
            oimUserList = new UsersList();
            //System.out.println("Kashif:: "+usrKey + " " + usrLogin + " " + fn + " " + ln + " " + email + " " + mobileNumber);
           oimUserList.setUserKey(usrKey);
           oimUserList.setUserDisplayName(fn+" " + ln);
           oimUserList.setUserFirstName(fn);
           oimUserList.setUserFirstName(ln);
           oimUserList.setUserMobileNumber(mobileNumber);
           oimUserList.setUserEmail(email);
           oimUserList.setUserLogin(usrLogin);
           oimUserList.setUserManager("Kashif.Ali");
           oimUserList.setuserStatus(Status);
           //userList.add(oimUserList);
           userHashMapList.put(usrKey, oimUserList);
             
        }

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return userHashMapList;
    }
    
    public Map<Long,UsersList> getAllUsers(OIMClient oimClient) throws UserSearchException{
    	
    	UsersList oimUserList = null;
    	ArrayList<UsersList> userList = new ArrayList<UsersList>();
    	Map<Long,UsersList> userHashMapList=new Hashtable<Long,UsersList>();
    	 
    	 UserManager  userManager = oimClient.getService(UserManager.class);
    	  SearchCriteria criteria = new 
    	    SearchCriteria("User Login", "*",
    	            SearchCriteria.Operator.EQUAL);  
    	  
    	  List<User> users = userManager.search(criteria, null, null); 
    	  System.out.println("Size of users -> "+users.size());
    	  for(User user: users){
    		  oimUserList = new UsersList();
    	      System.out.println( user.getStatus());
    	      String firstName = user.getFirstName();
    	     // System.out.print(firstName);
    	      oimUserList.setUserFirstName(user.getFirstName());
    		  oimUserList.setUserFirstName(user.getFirstName());
    	      oimUserList.setUserLastName(user.getLastName());
    		  oimUserList.setUserDisplayName(user.getDisplayName());
    		  oimUserList.setPersonalNumber(user.getEmployeeNumber());
    		  oimUserList.setUserEmail(user.getEmail());
    		  oimUserList.setUserManager(user.getManagerKey());
    		  oimUserList.setUserLogin(user.getLogin());
    		  oimUserList.setUserKey(Long.parseLong(user.getEntityId()));
    		  oimUserList.setuserStatus(user.getStatus());
    		  userHashMapList.put(Long.parseLong(user.getEntityId()), oimUserList); 
    	  }
    	  UserSessaion.setUserList(userHashMapList);
    	  return userHashMapList;
    	 }
    
 public static OIMUser getUsersDetailsByUserKey(OIMClient oimClient, String userKey){
    	
	 OIMUser oimUser = new OIMUser();
		try {
     UserManager usrMgr = oimClient.getService(UserManager.class);
     OrganizationManager organizationManager =oimClient.getService(OrganizationManager.class);

     SearchCriteria criteria = new SearchCriteria("usr_key",
    		 userKey, SearchCriteria.Operator.EQUAL);
     Set retSet = new HashSet();
     retSet.add("usr_key");
     retSet.add("User Login");
     retSet.add("First Name");
     retSet.add("Last Name");
     retSet.add("Email");
     retSet.add("act_key");
     retSet.add("Mobile");
     retSet.add("Employee Number");
     retSet.add("Department");
     retSet.add("Branch_Code");
     retSet.add("Branch_Name");
     retSet.add("Role");
     retSet.add("usr_manager_key");
     
     List<User> users = usrMgr.search(criteria, retSet, null);
     System.out.println("User Details Called");     
     for(User user : users) {
         System.out.println("********LIST********"+user.getAttributeNames());
         if((Long)user.getAttribute("usr_key")!=null)
         {
        	 Long usrKey = (Long)user.getAttribute("usr_key");
        	 oimUser.setUserKey(usrKey);
         }
         if((String)user.getAttribute("User Login")!=null)
         {
        	 
        	 oimUser.setUserlogin( (String)user.getAttribute("User Login"));
         }
         if((String)user.getAttribute("First Name")!=null)
         {
        	 oimUser.setUserFirstName((String)user.getAttribute("First Name"));
         }
         if((String)user.getAttribute("Last Name")!=null)
         {
        	 oimUser.setUserLastName((String)user.getAttribute("Last Name"));
         }
         if((String)user.getAttribute("Email")!=null)
         {
        	 oimUser.setUserEmail((String)user.getAttribute("Email"));
         }
         if((String)user.getAttribute("Mobile")!=null)
         {
        	 oimUser.setMobileNumber((String)user.getAttribute("Mobile"));
         }
         else 
         { oimUser.setMobileNumber("+92-300");}
         if((String)user.getAttribute("Employee Number")!=null)
         {
        	 oimUser.setPersonalNumber((String)user.getAttribute("Employee Number"));
         }
         if((String)user.getAttribute("Department")!=null)
         {
        	 oimUser.setDepartment((String)user.getAttribute("Department"));
         }
         if((String)user.getAttribute("Branch_Code")!=null)
         {
        	 oimUser.setBranch_Code((String)user.getAttribute("Branch_Code"));
         }
         if((String)user.getAttribute("Branch_Name")!=null)
         {
        	 oimUser.setBranchName((String)user.getAttribute("Branch_Name"));
         }
         if(user.getAttribute("act_key")!=null)
         {
        	 oimUser.setOrganization((String)organizationManager.getDetails(user.getAttribute("act_key").toString(),null,false).getAttribute(OrganizationManagerConstants.AttributeName.ORG_NAME.getId()));
         }
         if((String)user.getAttribute("Role")!=null)
         {
        	 oimUser.setUserRole((String)user.getAttribute("Role"));
         }
         System.out.println("SET"+retSet);
         System.out.println("user.getAttribute(User Type)"+user.getAttribute("Role"));
         System.out.println("user.getAttribute(Organization)"+organizationManager.getDetails(user.getAttribute("act_key").toString(),null,false).getAttribute(OrganizationManagerConstants.AttributeName.ORG_NAME.getId()));
         System.out.println("user.getAttribute(Branch_Name)"+user.getAttribute("Branch_Name"));
         System.out.println("user.getAttribute(Branch_Code)"+user.getAttribute("Branch_Code"));
         System.out.println("user.getAttribute(Department)"+user.getAttribute("Department"));
         System.out.println("user.getAttribute(Employee Number)"+user.getAttribute("Employee Number"));
         System.out.println("user.getAttribute(User Type)"+user.getAttribute("User Type"));
         System.out.println("user.getAttribute(Manager)"+usrMgr.getDetails(user.getAttribute("usr_manager_key").toString(),null,false).getDisplayName());
         System.out.println("user.getAttribute(User Type)"+user.getAttributeNames());
         
         /*
         String fn = (String)user.getAttribute("First Name");
         String ln = (String)user.getAttribute("Last Name");
         String email = (String)user.getAttribute("Email");
         String custom1 = (String)user.getAttribute("custom1");
         String mobileNumber = (String)user.getAttribute("Mobile");
         String employeeNumber = (String)user.getAttribute("Employee Number");
         
        
         oimUser.setUserLastName(ln);
         oimUser.setUserDisplayName(fn+" "+ ln);
         oimUser.setUserKey(usrKey);
         oimUser.setUserEmail(email);
         oimUser.setMobileNumber(mobileNumber);
         oimUser.setPersonalNumber(employeeNumber);
         oimUser.setUserlogin(usrLogin);*/
       //  System.out.println(oimUser.getMobileNumber() + " " + oimUser.getUserLastName() + " "+ oimUser.getPersonalNumber());
          
     }

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return oimUser;
    	 }
 
 
 
 ////////// Get User Status By User Key
 
 
 public String getUserStatusByKey(String userKey, OIMClient oimClient) throws Exception {

		OIMUser oimUser = new OIMUser();
		String status="Diabled";
		OIMUtils oimUtils=new OIMUtils();
		String userLogin= oimUtils.getUserLogin(userKey, oimClient);
		System.out.println("userLogin"+userLogin);
		try {
     UserManager usrMgr = oimClient.getService(UserManager.class);

     SearchCriteria criteria = new SearchCriteria("User Login",
         userLogin, SearchCriteria.Operator.EQUAL);
     Set retSet = new HashSet();
     retSet.add("usr_key");
     retSet.add("User Login");
     retSet.add("First Name");
     retSet.add("Last Name");
     retSet.add("Email");
     retSet.add("Mobile");
     retSet.add("Branch_Code");
     retSet.add("Branch_Name");
     retSet.add("Display Name");
     retSet.add("Employee Number");
     retSet.add("Status");
     //
     retSet.add("Department Number");
        
     List<User> users = usrMgr.search(criteria, retSet, null);
     
     for(User user : users) {
         System.out.println("********LIST********"+user.getAttributeNames());
         status=user.getStatus();
         Long usrKey = (Long)user.getAttribute("usr_key");
         String usrLogin = (String)user.getAttribute("User Login");
         String fn = (String)user.getAttribute("First Name");
         String ln = (String)user.getAttribute("Last Name");
         String email = (String)user.getAttribute("Email");
         String custom1 = (String)user.getAttribute("custom1");
         String mobileNumber = (String)user.getAttribute("Mobile");
         String Branch_Code = (String)user.getAttribute("Branch_Code");
         String Branch_Name = (String)user.getAttribute("Branch_Name");
         String display_Name = (String)user.getAttribute("Display Name");
         String employee_number = (String)user.getAttribute("Employee Number");
         System.out.println("EMPLOYEE NUMBER->>>>>>>>"+employee_number);
         String department_number = (String)user.getAttribute("Department Number");
         System.out.println(usrKey + " " + usrLogin + " " + fn + " " + ln + " DepN:" +  department_number+ " " + custom1);
         oimUser.setUserFirstName(fn);
         oimUser.setUserLastName(ln);
         oimUser.setUserKey(usrKey);
         oimUser.setUserEmail(email);
         oimUser.setMobileNumber(mobileNumber);
         oimUser.setBranch_Code(Branch_Code);
         oimUser.setBranchName(Branch_Name);
         oimUser.setUserlogin(usrLogin);
         oimUser.setUserDisplayName(display_Name);
         oimUser.setPersonalNumber(employee_number);
         oimUser.setDepartmentNumber(department_number);
     }

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return status;
 }
  
//////////

 public String getUserEMPByLogin(String userLogin, OIMClient oimClient) throws Exception {

		OIMUser oimUser = new OIMUser();
		String employee_number="";
		
		
		System.out.println("userLogin"+userLogin);
		try {
  UserManager usrMgr = oimClient.getService(UserManager.class);

  SearchCriteria criteria = new SearchCriteria("User Login",
      userLogin, SearchCriteria.Operator.EQUAL);
  Set retSet = new HashSet();
  retSet.add("usr_key");
  retSet.add("User Login");
  retSet.add("First Name");
  retSet.add("Last Name");
  retSet.add("Email");
  retSet.add("Mobile");
  retSet.add("Branch_Code");
  retSet.add("Branch_Name");
  retSet.add("Display Name");
  retSet.add("Employee Number");
  retSet.add("Status");
  //
  retSet.add("Department Number");
     
  List<User> users = usrMgr.search(criteria, retSet, null);
  
  for(User user : users) {
      System.out.println("********LIST********"+user.getAttributeNames());
      System.out.println("********LIST********"+user.getAttribute("Branch_Code"));
      System.out.println("********LIST********"+user.getAttribute("Mobile"));
      System.out.println("********LIST********"+user.getAttribute("Email"));
      System.out.println("********LIST********"+user.getAttribute("Branch_Name"));
      
       employee_number = (String)user.getAttribute("Employee Number");
      
      
  }

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return employee_number;
}

 
//////////////////
    public String createUser(
            
            String ACT_KEY                
            ,String USR_LAST_NAME          
            ,String USR_FIRST_NAME         
            ,String USR_MIDDLE_NAME        
            ,String USR_MANAGER            
            ,String USR_TYPE               
            ,String USR_EMP_TYPE           
            ,String USR_LOGIN              
            //,String USR_MANAGER_KEY        
            ,String USR_EMAIL              
            ,String USR_COUNTRY            
            ,String USR_EMP_NO             
            ,String USR_HOME_PHONE         
            ,String USR_MOBILE             
            ,String USR_STATE              
            ,String USR_STREET             
            ,String USR_TELEPHONE_NUMBER   
            ,String USR_TITLE              
            ,String USR_OFFICE_NAME        
            ,String USR_UDF_DEPARTMENT_NAME
            ,String USR_UDF_CITY           
            ,String USR_UDF_BRANCH_CODE    
            ,String USR_UDF_REGION_NAME    
            ,String USR_UDF_BRANCH_NAME    
            ,String USR_UDF_REGION_CODE    
            ,String USR_UDF_DEPARTMENT, 
            OIMClient oimClient
    		) throws Exception{
    	LoginToOIM logOIM = new LoginToOIM();
    	//OIMClient oimClient = logOIM.loggedIntoOIM("XELSYSADM", "Hblpoc_1234");
    	
        UserManager userAPI =
        		oimClient.getService(UserManager.class);
HashMap<String, Object> attr = new HashMap<String, Object>();
attr.put("First Name", USR_FIRST_NAME);
attr.put("Last Name", USR_LAST_NAME);
attr.put("Role", "EMP");

attr.put("Email", USR_EMAIL);
attr.put("Middle Name", USR_MIDDLE_NAME);
//attr.put("User Type", USR_TYPE);
attr.put("Country",USR_COUNTRY);        
attr.put("Employee Number",USR_EMP_NO);
attr.put("Home Phone",USR_HOME_PHONE);         
attr.put("Mobile",USR_MOBILE);             
attr.put("State",USR_STATE);              
attr.put("Street",USR_STREET);             
attr.put("Telephone Number",USR_TELEPHONE_NUMBER);   
attr.put("Title",USR_TITLE);    
attr.put("usr_password","Hblpoc_123");
//attr.put("Office_Name",USR_OFFICE_NAME);        
attr.put("Department",USR_UDF_DEPARTMENT_NAME);
attr.put("City",USR_UDF_CITY);           
attr.put("Branch_Code",USR_UDF_BRANCH_CODE);    
attr.put("Region_Name",USR_UDF_REGION_NAME);    
attr.put("Branch_Name",USR_UDF_BRANCH_NAME);    
//attr.put("REGION_CODE",USR_UDF_REGION_CODE);    
attr.put("Department",USR_UDF_DEPARTMENT);
if(USR_MANAGER!=null)
	attr.put(UserManagerConstants.AttributeName.MANAGER_KEY.getId(), Long.parseLong(getUserKey(USR_MANAGER,oimClient)));
	//System.out.print("Act Key     "+Long.parseLong(getOrganizationKey(ACT_KEY.toString().trim(), oimClient)));
	System.out.print("Act Key     "+ACT_KEY.toString());
   System.out.print("Manager key   ----"+Long.parseLong(getUserKey(USR_MANAGER,oimClient)));
attr.put("act_key", Long.parseLong(getOrganizationKey(ACT_KEY.toString().trim(), oimClient)));
	//attr.put("act_key", new Long(1));
User user = new User(null, attr);
user.setManagerKey(Long.parseLong(getUserKey(USR_MANAGER,oimClient)));
UserManagerResult result = userAPI.create(user);
System.out.println(result.getEntityId() + " " + result.getClass());
java.util.Set retAttrs = new HashSet();
retAttrs.add(UserManagerConstants.AttributeName.USER_LOGIN.getId());
User createUser =
userAPI.getDetails(UserManagerConstants.AttributeName.USER_KEY.getId(),
                result.getEntityId(), retAttrs);
System.out.println(createUser.getLogin());

return createUser.getLogin();
}
    public void changeUserPassword(String userLogin,String newPassword) { //Function to reset user password
        try {
            
            LoginToOIM logOIM = new LoginToOIM();
           // OIMClient oimClient = logOIM.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
            
            UserManager userManager =
            		UserSessaion.oimClient.getService(UserManager.class);
           
            
            userManager.changePassword(userLogin.toString().trim(), newPassword.trim().toCharArray(), true,
                                       null, false);
           
            System.out.println("Reset Password done…");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Map<Long,AppInstanceObj> fetchProvisionedAccountsOfUser(String usrKey, OIMClient oimClient){
    	String CN = "Test";
        final String logp = CN + " :: fetchProvisionedAccountsOfUser - ";
        System.out.println("usrKey:: "+ usrKey);
        List<Long> appInstanceKeys = new ArrayList<Long>();
        AppInstanceObj appInstanceObj =null;
        Map<Long,AppInstanceObj> acctProviosnedList=new Hashtable<Long,AppInstanceObj>();
        try{          
         
            ProvisioningService provService = oimClient.getService(ProvisioningService.class);
            List<Account> provAccounts = provService.getAccountsProvisionedToUser(usrKey);
           // System.out.println(provAccounts.size() + " KEY: "+ usrKey);
            for(Account act : provAccounts){
            	if(act.getAppInstance().getApplicationInstanceName().equals("SSOTarget4"))
            		continue;
                if (((act.getAccountStatus().equalsIgnoreCase("Provisioned"))
                                || (act.getAccountStatus().equalsIgnoreCase("Enabled")) ||   (act.getAccountStatus().equalsIgnoreCase("Disabled"))
                                	)){   
                	appInstanceObj = new AppInstanceObj();
                    Long appInstanceKey = act.getAppInstance().getApplicationInstanceKey();
                    
                    System.out.println("getApplicationInstanceName:: "+act.getAppInstance().getApplicationInstanceName());
                    //System.out.print(act.getAccountID() +" "+  +appInstanceKey+"  App Name: "+act.getAppInstance().getApplicationInstanceName() +" Display Name:  "+ act.getAppInstance().getDisplayName());
                    //System.out.println( act.getAppInstance().getAccountForm().getFormFields());
                   // System.out.println("  "+ act.getAppInstance().getItResourceName() + " Status"+ act.getAccountStatus() + "  "+ act.getProvisionedOnDate() +" " + act.getAccountType());
                    
                    System.out.println(act.getAppInstance().getApplicationInstanceName());
                    appInstanceObj.setAccountName(act.getAccountID().toString());
                    appInstanceObj.setAccountStatus(act.getAccountStatus().toString());
                    appInstanceObj.setAppInstanceDisplayName(act.getAppInstance().getDisplayName().toString());
                    appInstanceObj.setAppInstanceName(act.getAppInstance().getApplicationInstanceName());
                    appInstanceObj.setAccountOIUKey(act.getAccountID().toString());
                    appInstanceObj.setUserKey(act.getUserKey().toString());
                    appInstanceObj.setActStartDate(act.getProvisionedOnDate());
                    appInstanceObj.setAccountType(act.getAccountType().name());
                    appInstanceObj.setAccountId(act.getAccountDescriptiveField());
                    
                    acctProviosnedList.put(appInstanceKey, appInstanceObj);             
                    if (!appInstanceKeys.contains(appInstanceKey)){
                            appInstanceKeys.add(appInstanceKey);
                    }
                }
                System.out.println("Total: "+acctProviosnedList.size());
            }
        } catch (Exception e) {
               e.printStackTrace();
        }
       
        return acctProviosnedList;
}
    
    public  Map<Long,EntitlementObj> getUserEntitlementInstances(String userKey, OIMClient oimClient)  {
    	
    	EntitlementObj appEntitlementObj =null;
         Map<Long,EntitlementObj> entitlementProviosnedList=new Hashtable<Long,EntitlementObj>();
        // Get user's key
         
        try {
        	ProvisioningService provServOps = oimClient.getService(ProvisioningService.class);
            // Get user's entitlements
            List<EntitlementInstance> userEntitlements =  provServOps.getEntitlementsForUser(userKey);
           
            // Iterate each entitlement and print to logs
            for(EntitlementInstance userEntitlement : userEntitlements )
            {
            //	if(userEntitlement.getStatus().equals("In Progress"));
        		//continue;
        	
            	
            	appEntitlementObj = new EntitlementObj();
            	
          
            	
                Long accountId = userEntitlement.getAccountKey(); // OIU_KEY
                appEntitlementObj.setEntitlementKey(userEntitlement.getEntitlementInstanceKey());
                appEntitlementObj.setAccountKey(userEntitlement.getAccountKey());
                appEntitlementObj.setAccountName(userEntitlement.getAccountName());
                appEntitlementObj.setStatus(userEntitlement.getStatus());
                appEntitlementObj.setEntitlementName(userEntitlement.getEntitlement().getDisplayName());
                appEntitlementObj.setFormName(userEntitlement.getEntitlement().getFormName());
                appEntitlementObj.setAppInstanceName(userEntitlement.getEntitlement().getAppInstance().toString().split(":")[0]);
                entitlementProviosnedList.put(userEntitlement.getChildTablePrimaryKey(), appEntitlementObj);
                System.out.println( accountId.toString()+" ==  "+userEntitlement.getChildTablePrimaryKey()+" == " + userEntitlement.getEntitlement().getDisplayName()
                		+" == " + userEntitlement.getEntitlement().getFormName()
                		+" == " + userEntitlement.getEntitlement().getAppInstance().toString().split(":")[0]);
            }
            System.out.println("Total "+entitlementProviosnedList.size());
        	
        }catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        
    	return entitlementProviosnedList;
    }
    
	//
	public String getProcessInstanceKey(String userLogin, OIMClient oimClient) {
		String CN = "Test";
		final String logp = CN + " :: fetchProvisionedAccountsOfUser - ";
		
		// System.out.println(logp);
		List<Long> appInstanceKeys = new ArrayList<Long>();
		AppInstanceObj appInstanceObj = null;
		String processInstanceKey = null;
		Map<Long, AppInstanceObj> acctProviosnedList = new Hashtable<Long, AppInstanceObj>();
		try {
			String usrKey = getUserKey(userLogin, oimClient);
			ProvisioningService provService = oimClient.getService(ProvisioningService.class);
			List<Account> provAccounts = provService.getAccountsProvisionedToUser(usrKey);
			
			// System.out.println(provAccounts.size() + " KEY: "+ usrKey);
			for (Account act : provAccounts) {
				if ((act.getAccountStatus().equalsIgnoreCase("Provisioned"))
						|| (act.getAccountStatus().equalsIgnoreCase("Enabled"))
						|| (act.getAccountStatus().equalsIgnoreCase("Disabled")) ) {
					Long appInstanceKey = act.getAppInstance().getApplicationInstanceKey();
					 if(act.getAppInstance().getApplicationInstanceName().startsWith("Equation")  &&  act.getAccountType().toString().equals("Primary"))
					 {
						 	/*System.out.println(act.getAppInstance().getApplicationInstanceName());
							System.out.println(appInstanceKey);
							System.out.println(act.getProcessInstanceKey());
							System.out.println(act.getAccountType());
							System.out.println(act.getAppInstance());  */
						 processInstanceKey = act.getProcessInstanceKey();
						 System.out.println("userLogin:"+userLogin);
						 System.out.println("processInstanceKey:"+processInstanceKey);
						 break;
					 }
					
					 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return processInstanceKey;
	}
	//
    public  Map<String,OrganizationObj> getAllOrganizations(OIMClient oimClient)
    {
    	Organization org= null;
    	List orgList =null;
    	OrganizationObj orgObj = null;
    	Map<String,OrganizationObj> organizationDetails = new HashMap<String,OrganizationObj>();
    	Set<String> retAttrs = new HashSet<String>();
        retAttrs.add(OrganizationManagerConstants.AttributeName.ID_FIELD.getId());
        retAttrs.add(OrganizationManagerConstants.AttributeName.ORG_NAME.getId());
        //retAttrs.add(OrganizationManagerConstants.AttributeName.ORG_STATUS.getId());
        HashMap<String, Object> configParams = new HashMap<String, Object>();
        String orgNameStr= null;
        String act_keyStr = null;
    	try {
    		OrganizationManager orgMgr = oimClient.getService(OrganizationManager.class);
    		org = orgMgr.getDetails("Top", null, true);
    		
    		
    		 orgList = orgMgr.getChildOrganizations("3", retAttrs, configParams);

    		for(int i = 0 ; i < orgList.size() ; i++)
    		{
    			orgObj = new OrganizationObj();
    			//System.out.println(orgList.get(i).toString().substring(orgList.get(i).toString().lastIndexOf("Name=")+5, orgList.get(i).toString().indexOf(',')) );
    			orgNameStr = orgList.get(i).toString().substring(orgList.get(i).toString().lastIndexOf("Name=")+5, orgList.get(i).toString().indexOf(','));
    			
    			 act_keyStr = orgList.get(i).toString().substring(orgList.get(i).toString().lastIndexOf("act_key=")+8, orgList.get(i).toString().indexOf('}'));
    			//System.out.println(act_keyStr );
    			orgObj.setOrganizationName(orgNameStr.toString().trim());
    			orgObj.setOrganiztionActkey(act_keyStr.toString().trim());
    			organizationDetails.put(act_keyStr,orgObj);
    			//String orgName = orgList.get(i)+"".substring(orgList.get(i).toString().indexOf("Name="));
    			
    		}
    		/*System.out.println(organizationDetails);
    		for(Map.Entry<String, OrganizationObj> orgListMap: organizationDetails.entrySet() ) {
    			
    		  System.out.println(organizationDetails.get(orgListMap.getKey()).getOrganizationName());
    		  System.out.println(organizationDetails.get(orgListMap.getKey()).getOrganiztionActkey());
    		 //System.out.println(organizationDetails.get(orgListMap.getValue())+"\n\n");
    		}*/
    		//System.out.println(org.getEntityId());
    		//System.out.println(org.getAttribute(OrganizationManagerConstants.AttributeName.ORG_NAME.getId()));
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return organizationDetails;
    }
    public String getOrganizationKey(String orgName, OIMClient oimClient)
    {
    	String orginaztionKey=null;
    	Organization org =null;
    	try {
    		OrganizationManager orgMgr = oimClient.getService(OrganizationManager.class);
    		 org = orgMgr.getDetails(orgName, null, true);
    		//String orgKey = org.getEntityId();
    		 orginaztionKey = org.getEntityId();
    		 return orginaztionKey;
    		
    	}catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	//return orginaztionKey;
    	return orginaztionKey;
    }
    public List<RoleUser> fetchUserRole(String userLogin, OIMClient oimClient)
	{

		List<RoleUser> listRole = new ArrayList<RoleUser>();
		RoleUser roleUserObj = new RoleUser();
		RoleManager rm = oimClient.getService(RoleManager.class);
		List<Role> roleLst=new ArrayList<Role>();
		ArrayList sRoles = new ArrayList();
		try {
			 
			//String userKey = getUserKey(userLogin, oimClient);
			String userKey = userLogin.toString().trim();
			roleLst=rm.getUserMemberships(userKey, false);
			 for(Role role : roleLst){
				 if(role.getDisplayName().equals("ALL USERS"))
					 continue;
				 roleUserObj = new RoleUser();
				 roleUserObj.setRoleDisplayName((String)role.getDisplayName());
				 roleUserObj.setRoleKey((String)role.getEntityId());
				 roleUserObj.setRoleName(role.getName());
				 roleUserObj.setRoleDescription( role.getDescription());
				  
		         //sRoles.add((String)role.getEntityId());
		         //sRoles.add((String)role.getDisplayName());
				 listRole.add(roleUserObj);
		        }
			for(RoleUser rlusr :listRole )
			{
				System.out.println(rlusr.getRoleDisplayName() + " " +rlusr.getRoleKey() +" " + rlusr.getRoleName() + " " + rlusr.getRoleDescription() );
			}
			return listRole;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	
		///System.out.println("Role List is:::::"+"\n"+sRoles);
		 
		
		return null;
	}
    
    public List<Role> getRole(OIMClient oimClient){
    	 List<Role> roleList = null;
        try {
            //get role manager service
            RoleManager roleManager = oimClient.getService(RoleManager.class);
            SearchCriteria criteria = new SearchCriteria(RoleAttributeName.NAME.getId(), "*", SearchCriteria.Operator.EQUAL);
            roleList = roleManager.search(criteria, null, null);  
            for(Role role: roleList)
            {
            	System.out.println(role.getDisplayName() + " : "+ role.getEntityId());
            }
        }catch(Exception e){
              System.out.println(e.getMessage());
        }

         
        return roleList;
 }
public String getRoleKey(OIMClient oimClient, String roleName) {
		List<Role> roleList = null;
		String roleKey =null;
		try {
			// get role manager service
			RoleManager roleManager = oimClient.getService(RoleManager.class);
			SearchCriteria criteria = new SearchCriteria(RoleAttributeName.KEY,roleName,
					SearchCriteria.Operator.EQUAL);
			roleList = roleManager.search(criteria, null, null);
			for (Role role : roleList) {
				System.out.println(role.getDisplayName() + " : " + role.getEntityId());
				roleKey = role.getEntityId();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return roleKey;
	}
    
    
    //
    public String getUserLoginName(String userKey, OIMClient oimClient) throws Exception {
    	if(userKey==null) {
        
    		return "-";
    	}else {
    		
    		Set retAttr = new HashSet();
            retAttr.add(UserManagerConstants.AttributeName.USER_LOGIN.getId());
            UserManager userAPI = oimClient.getService(UserManager.class);
            User user = userAPI.getDetails(userKey, retAttr, false);
            String userLogin = user.getLogin();
            return userLogin;
    	}
    }
    
    
    public boolean checkAS400App(String usrKey, OIMClient oimClient){
    	
    	boolean isAs400= false;  
    	
    	String CN = "Test";
        final String logp = CN + " :: fetchProvisionedAccountsOfUser - ";
        System.out.println("usrKey:: "+ usrKey);
        List<Long> appInstanceKeys = new ArrayList<Long>();
        AppInstanceObj appInstanceObj =null;
        Map<Long,AppInstanceObj> acctProviosnedList=new Hashtable<Long,AppInstanceObj>();
        try{          
         
            ProvisioningService provService = oimClient.getService(ProvisioningService.class);
            List<Account> provAccounts = provService.getAccountsProvisionedToUser(usrKey);
           // System.out.println(provAccounts.size() + " KEY: "+ usrKey);
            for(Account act : provAccounts){
            	if(act.getAppInstance().getApplicationInstanceName().equals("AS400"))
            		
            	{
            		
                if (((act.getAccountStatus().equalsIgnoreCase("Provisioned")) ||
                     (act.getAccountStatus().equalsIgnoreCase("Enabled"))     ||  
                     (act.getAccountStatus().equalsIgnoreCase("Disabled"))))
                {
                	isAs400 = true;
                	break;
                }
                	
                }
              
            }
        } catch (Exception e) {
               e.printStackTrace();
        }
       
        return isAs400;
}
    
    
    ////////////////////////////////////get User Detail by User Login///////////////////////////
    
//////////

public HashMap getUserDetailByLogin(String userLogin, OIMClient oimClient) throws Exception {

	OIMUser oimUser = new OIMUser();
	HashMap	userDetail= new HashMap();
	
	
	System.out.println("userLogin"+userLogin);
	try {
UserManager usrMgr = oimClient.getService(UserManager.class);
	
SearchCriteria criteria = new SearchCriteria("User Login",
  userLogin, SearchCriteria.Operator.EQUAL);
Set retSet = new HashSet();
retSet.add("usr_key");
retSet.add("User Login");
retSet.add("First Name");
retSet.add("Last Name");
retSet.add("Email");
retSet.add("Mobile");
retSet.add("Branch_Code");
retSet.add("Branch_Name");
retSet.add("Display Name");
retSet.add("Employee Number");
retSet.add("Status");
//
retSet.add("Department Number");
 
List<User> users = usrMgr.search(criteria, retSet, null);


for(User user : users) {
	if(user.getAttribute("Branch_Code")!=null) {userDetail.put("Branch_Code", user.getAttribute("Branch_Code").toString());}
	if(user.getAttribute("Mobile")!=null) {userDetail.put("Mobile", user.getAttribute("Mobile").toString());}
	if(user.getAttribute("Email")!=null) {userDetail.put("Email", user.getAttribute("Email").toString());}
	if(user.getAttribute("Branch_Name")!=null) {userDetail.put("Branch_Name", user.getAttribute("Branch_Name").toString());}

  System.out.println("********LIST********"+user.getAttributeNames());
  System.out.println("********LIST********"+user.getAttribute("Branch_Code"));
  System.out.println("********LIST********"+user.getAttribute("Mobile"));
  System.out.println("********LIST********"+user.getAttribute("Email"));
  System.out.println("********LIST********"+user.getAttribute("Branch_Name"));
  
   
  
}

	}catch(Exception ex)
	{
		ex.printStackTrace();
	}
	return userDetail;
}


///////////////////////////////////validate password against policy/////////////////

public static boolean validatePassword(String UserLogin, OIMClient oimClient){
	 // User user = new User(String.valueOf(51045)); 
	  PasswordMgmtService passwordMgmtService = 
			  oimClient.getService(PasswordMgmtService.class);
	  
	
	  
	  ValidationResult validationResult = 
	                passwordMgmtService.validatePasswordAgainstPolicy("HARIS".toCharArray(), "HARIS",Locale.ENGLISH); 
	  System.out.println("Valid password was validated by Password Validation " + "API "+ validationResult.getPasswordPolicyInfo());
	  System.out.println("Valid password was validated by Password Validation " + "API "+ validationResult.isPasswordValid());
	  System.out.println("Valid password was validated by Password Validation " + "API "+ validationResult.getPasswordPolicyDescription().getHeaderDisplayValue());
	  
	      
	    /*try { 
	    validationResult =  passwordMgmtService.validatePasswordAgainstPolicy("Welcome1".toCharArray(), user, "InavlidAppInstance",Locale.ENGLISH); 
	    }catch (Exception e) { 
	    System.out.println("! Inavlid Account with Password Validation via Change Account Password method API" +
	    e.getMessage().contains("oracle.iam.provisioning.exception.ApplicationInstanc eNotFoundException")); 
	    } */
	      return true;
	 }
	

    
    public  void main(String arg[])
    {
 	   try {
 		   
 		   OIMUtils oimUtils = new OIMUtils();
 		  LoginToOIM log1 = new LoginToOIM();
		 OIMClient oimClient = log1.loggedIntoOIM("XELSYSADM", "Hblpoc_1234");
 		  // oimUtils.createUser("Xellerate Users", "Arif", "Kashif", "Ali", "kashif.arif", "EMP", "EMP", "kashif.ali", "kashif2@test1.hl.com", "Pakistan", "12345", "03485255956",
 			 	 //  "03443328539", "North", "Street123", "12345", "Branch Manager", "Gulberg Lahore", "Department1", "Lahore", "12331", "Lahore Region", "Lahore branch", "1232", "Dep1",oimClient);
 		   
		//String userlogin= oimUtils.getUserLoginName(null, oimClient);
		// System.out.print(userlogin);
		// System.out.println(oimUtils.checkAS400App("1", oimClient));
		 
//		 oimUtils.getAllUsers(oimClient);
 //		oimUtils.getUserDetails("TESTER2", oimClient);
 	//	System.out.print("Status->>>>>>>>"+oimUtils.getUserStatusByKey("5012", oimClient));
 		//System.out.print("LOGIN CPPP->>>>>>>>"+oimUtils.getUserEMPByLogin("KAMRAN.MUTTAQI", oimClient));
		 
		// oimUtils.getUserEntitlementInstances("56026", oimClient);
 		  // oimUtils.fetchProvisionedAccountsOfUser(oimUtils.getUserKey("MUSSAB.KHAKWANI", oimClient).toString(),oimClient);
	//	HashMap userDetail= oimUtils.getUserDetailByLogin("MIRZA.BAIG", oimClient); 
		//System.out.println(userDetail.get("Branch_Code"));
		//validatePassword("UserLogin", oimClient);
		//getUsersDetailsByUserKey(oimClient,"29167");
		getProcessInstanceKey("MUDASAR.BUTT",oimClient);
 		  //oimUtils.getUserEntitlementInstances(oimUtils.getUserKey("AKHTER", oimClient).toString(), oimClient);
 		 // oimUtils.getAllOrganizations(oimClient);
 		   //oimUtils.getUsersDetailsByUserKey(oimClient, "34024");
 		  // oimUtils.fetchUserRole("MUSSAB.UAT", oimClient);
		  //oimUtils.getRole(oimClient);
 	   }catch(Exception ex)
 	   {
 		   ex.printStackTrace();
 	   }
 	  
    }
    
    
}
