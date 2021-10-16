package com.hbl.provisioning;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.security.auth.login.LoginException;

import com.bea.common.security.service.ValidationFailedException;
import com.bea.httppubsub.json.JSONObject;
import com.hbl.encrypt.maker.CallSOAPWS;
import com.hbl.selfservice.LoginToOIM;
import com.hbl.selfservice.UserSessaion;
import com.hbl.selfservice.formfields.AcctFormFields;
import com.hbl.selfservice.objects.AppInstanceObj;
import com.oracle.cie.domain.security.external.SecurityContext.Platform;

import Thor.API.tcResultSet;
import Thor.API.Operations.tcLookupOperationsIntf;
import Thor.API.Security.XLClientSecurityAssociation;
import oracle.iam.api.OIMService;
import oracle.iam.exception.OIMServiceException;
import oracle.iam.identity.exception.NoSuchUserException;
import oracle.iam.identity.exception.UserLookupException;
import oracle.iam.identity.exception.UserModifyException;
import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.api.UserManagerConstants;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.oimcommon.vo.RiskContext.AccountScanScope;
import oracle.iam.passwordmgmt.api.PasswordMgmtService;
import oracle.iam.passwordmgmt.vo.ValidationResult;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.authz.exception.AccessDeniedException;
import oracle.iam.platform.entitymgr.vo.SearchCriteria;
import oracle.iam.platform.utils.vo.OIMType;
import oracle.iam.provisioning.api.ApplicationInstanceService;
import oracle.iam.provisioning.api.ProvisioningConstants;
import oracle.iam.provisioning.api.ProvisioningService;
import oracle.iam.provisioning.exception.AccountNotFoundException;
import oracle.iam.provisioning.exception.ApplicationInstanceNotFoundException;
import oracle.iam.provisioning.exception.GenericAppInstanceServiceException;
import oracle.iam.provisioning.exception.GenericProvisioningException;
import oracle.iam.provisioning.exception.ImproperAccountStateException;
import oracle.iam.provisioning.exception.UserNotFoundException;
import oracle.iam.provisioning.vo.Account;
import oracle.iam.provisioning.vo.AccountData;
import oracle.iam.provisioning.vo.ApplicationInstance;
import oracle.iam.provisioning.vo.FormField;
import oracle.iam.provisioning.vo.FormInfo;
import oracle.iam.reconciliation.utils.Sys;
import oracle.iam.request.api.RequestService;
import oracle.iam.request.vo.Beneficiary;
import oracle.iam.request.vo.RequestBeneficiaryEntity;
import oracle.iam.request.vo.RequestBeneficiaryEntityAttribute;
import oracle.iam.request.vo.RequestConstants;
import oracle.iam.request.vo.RequestData;
import oracle.iam.selfservice.self.selfmgmt.api.AuthenticatedSelfService;
import oracle.iam.vo.OperationResult;
import utils.system;
import oracle.iam.identity.usermgmt.api.UserManagerConstants;
import oracle.iam.platform.context.ContextManager;


//import com.massiveGaze.;



public class ProvisioningAccounts {
	
	public static UserManager userManager;
	
	//change password
	OIMClient oimClient;
	//
	public static void main(String[] args) throws Exception {
		
		
		
		OIMClient oimClient = null;
		LoginToOIM loginToOim = new LoginToOIM();
		oimClient = loginToOim.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
		//System.out.print("calling method");
		ProvisioningAccounts pa = new ProvisioningAccounts();
	/*
		String ukey = pa.getUserKey("haris.khan", oimClient);
		System.out.print(ukey);

		Map<String,String> lookup = pa.getLookupMap("Lookup.EquationGrp.Options", oimClient);
		lookup.entrySet().forEach( entry -> {
		    System.out.println( entry.getKey() + " => " + entry.getValue() );
		});
		
*/	 List<Long> EquationOIU_KEYS= new ArrayList<Long>();
List<Account> account= null;
account = pa.getAllAccount(oimClient, "51042");
		for (int i=0;i<account.size();i++)
		{
			System.out.println("EquationOIU_KEYS:"+account.get(i).getAccountID());
			EquationOIU_KEYS.add(Long.valueOf(account.get(i).getAccountID()));
		}
		System.out.println("EquationOIU_KEYS:"+EquationOIU_KEYS);
		
		/*ProvisioningAccounts pa = new ProvisioningAccounts();
		// Map<Long, AppInstanceObj> appInstMap = pa.getAllAppInstance(oimClient);
		// pa.provisionAccount("KASHIF.ARIF1", oimClient,
		// "ActiveDirectory","UD_ADUSER");
		// pa.getAccountFormFields("ActiveDirectory", oimClient);
		 //pa.provisionAccount("KASHIF.ARIF1", oimClient, "ActiveDirectory","UD_AS400CON");
		 //pa.provisionAccount("KASHIF.ARIF3", oimClient, "ActiveDirectory","UD_ADUSER");
		 //pa.createApplicationRequest(oimClient,"ActiveDirectory","1","KASHIF.ARIF3",1);
		Long entKey =(long) 2875;
		pa.createEntitlementRequest(oimClient,"6~GRPATM",entKey,"MAJID",3);
		//pa.getLookupMap("Lookup.ActiveDirectory.Countries", oimClient);
		// System.out.println(appInstMap.size()); 34024
		/*
		 * for(Map.Entry<Long, AppInstanceObj> accountMap: appInstMap.entrySet() ) {
		 * System.out.println(appInstMap.get(accountMap.getKey()).getAccountName()); }
		 */

	}

	public Map<Long, AppInstanceObj> getAllAppInstance(OIMClient oimClient) {

		Map<Long, AppInstanceObj> appInstMap = new HashMap<Long, AppInstanceObj>();
		AppInstanceObj appInstObj = null;
		try {
			ApplicationInstanceService aiService = oimClient.getService(ApplicationInstanceService.class);
			SearchCriteria criteria = new SearchCriteria(ApplicationInstance.APPINST_NAME, "*",
					SearchCriteria.Operator.BEGINS_WITH);
			List<ApplicationInstance> aiList = aiService.findApplicationInstance(criteria,
					new HashMap<String, Object>());
			for (ApplicationInstance ai : aiList) {
				appInstObj = new AppInstanceObj();

				appInstObj.setAccountName(ai.getApplicationInstanceName());
				appInstObj.setAppInstanceDisplayName(ai.getDisplayName());
				appInstObj.setAppInstanceKey(ai.getApplicationInstanceKey());

				/*System.out.print(" ai name = " + ai.getApplicationInstanceName());
				System.out.print(" ai Key = " + ai.getApplicationInstanceKey());
				System.out.print(" ai display name = " + ai.getDisplayName());
				System.out.println(" ai getObjectName  = " + ai.getObjectName());
				System.out.println(ai.getObjectKey() + "\n");*/
				appInstMap.put(ai.getApplicationInstanceKey(), appInstObj);
			}

			return appInstMap;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void provisionAccount(String userLogin, OIMClient oimClient, String appInstanceName, String tableName)
			throws ApplicationInstanceNotFoundException {

		ProvisioningService provSvc = oimClient.getService(ProvisioningService.class);
		UserManager usrMgr = oimClient.getService(UserManager.class);

		// Find the user
		SearchCriteria criteria = new SearchCriteria("User Login", userLogin, SearchCriteria.Operator.EQUAL);
		Set retSet = new HashSet();
		retSet.add("usr_key");
		retSet.add("User Login");
		retSet.add("First Name");
		retSet.add("Last Name");
		List<User> users = null;
		ApplicationInstance ai = null;
		try {
			users = usrMgr.search(criteria, retSet, null);
			ai = findApplicationInstanceByName(appInstanceName, oimClient);
		

		} catch (ApplicationInstanceNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		User u = users.get(0);

		Map parentData = new HashMap();
		// parentData.put(tableName, ai.getApplicationInstanceName().toString());
		// List<FormField> fields = ai.getAccountForm().getFormFields();

		// Organization Name UD_ADUSER_ORGNAME 15827
		AccountData accountData = new AccountData(ai.getAccountForm().getFormKey() + "", null, null);

		Account account = new Account(ai, accountData);
		account.setAccountType(Account.ACCOUNT_TYPE.Primary);
		Long keyy = null;
		try {
			keyy = provSvc.provision(u.getEntityId() + "", account);
			 
			System.out.print(keyy + "request created" );

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

		//System.out.println(keyy);
		 

	}

	public void provisionAccountWithForm(String userLogin, OIMClient oimClient, String appInstanceName)
			throws ApplicationInstanceNotFoundException {

		ProvisioningService provSvc = oimClient.getService(ProvisioningService.class);
		UserManager usrMgr = oimClient.getService(UserManager.class);

		// Find the user
		SearchCriteria criteria = new SearchCriteria("User Login", userLogin, SearchCriteria.Operator.EQUAL);
		Set retSet = new HashSet();
		retSet.add("usr_key");
		retSet.add("User Login");
		retSet.add("First Name");
		retSet.add("Last Name");
		List<User> users = null;
		ApplicationInstance ai = null;
		try {
			users = usrMgr.search(criteria, retSet, null);
			ai = findApplicationInstanceByName(appInstanceName, oimClient);

		} catch (ApplicationInstanceNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		User u = users.get(0);
		HashMap<String, Object> parentData = new HashMap<String, Object>();

		List<FormField> fields = ai.getAccountForm().getFormFields();
		for (FormField f : fields) {
			System.out.println(f.getLabel() + " " + f.getName() + " " + f.getFldKey());
			if (f.getLabel().equals("itres") || f.getType().equals("DateFieldDlg")) {
				continue;
			} else if (f.getLabel().equals("First Name")) {
				parentData.put(f.getName(), u.getFirstName() + "XX");
			} else if (f.getLabel().equals("lname")) {
				parentData.put(f.getName(), u.getLastName());
			} else if (f.getLabel().equals("Organization Name")) {
				parentData.put(f.getName(), "4~OU=Users,OU=Central,DC=contoso,DC=com,DC=pk");
			}

		}
		// Organization Name UD_ADUSER_ORGNAME 15827
		AccountData accountData = new AccountData(ai.getAccountForm().getFormKey() + "", "", parentData);

		Account account = new Account(ai, accountData);
		try {
			provSvc.provision(u.getEntityId(), account);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
 

	}

	public ApplicationInstance findApplicationInstanceByName(String applicationInstanceName, OIMClient oimClient)
			throws ApplicationInstanceNotFoundException,

			GenericAppInstanceServiceException {

		ApplicationInstanceService service = oimClient.getService(ApplicationInstanceService.class);

		ApplicationInstance appInstance = service.findApplicationInstanceByName(applicationInstanceName);
		 

		return appInstance;

	}
	public Long getApplicationInstanceKey(String applicationInstanceName, OIMClient oimClient)
			throws ApplicationInstanceNotFoundException,

			GenericAppInstanceServiceException {

		ApplicationInstanceService service = oimClient.getService(ApplicationInstanceService.class);

		ApplicationInstance appInstance = service.findApplicationInstanceByName(applicationInstanceName);
		 

		return appInstance.getApplicationInstanceKey();

	}

	public List<AcctFormFields> getAccountFormFields(String appInstanceName, OIMClient oimClient,String roleid) {
		List<AcctFormFields> actfList = new ArrayList<AcctFormFields>();

		ProvisioningService provSvc = oimClient.getService(ProvisioningService.class);
		UserManager usrMgr = oimClient.getService(UserManager.class);
		UserOperationModifyAcc uomc = new UserOperationModifyAcc();
		ApplicationInstance ai = null;
		AcctFormFields acctFromFields = null;
			System.out.println("APP INSTANCE NAME:"+appInstanceName);
			
			

			if(appInstanceName.equals("Equation"))
			{
				try {

					ai = findApplicationInstanceByName(appInstanceName, oimClient);
					List<FormField> fields = ai.getAccountForm().getFormFields();
					for (FormField f : fields) {

						if(f.getLabel().equalsIgnoreCase("User Class") ||f.getLabel().equalsIgnoreCase("Branch Number")
								||f.getLabel().equalsIgnoreCase("User ID"))
						{
							acctFromFields = new AcctFormFields();
							acctFromFields.setFieldLabel(f.getLabel());
							acctFromFields.setFieldApiName(f.getName());
							acctFromFields.setFieldType(f.getType());
							acctFromFields.setFieldKey(f.getFldKey());
							acctFromFields.setDefaultValue(f.getDefaultValue());
							if (f.getProperty("Lookup Code") != null)
								acctFromFields.setFiledLookupName((String) f.getProperty("Lookup Code"));
							actfList.add(acctFromFields);
							//System.out.println(f.getProperty("Lookup Code"));
							 System.out.println("Label"+f.getLabel() + " Name: " + f.getName() + " Type: "
							 + f.getType() + " Key: " + f.getFldKey() + " Defult Value: " +
							 f.getDefaultValue()) ;
						}
						
						
					
					}
					// System.out.println(actfList.get(0).makeFieldHTML());
					return actfList;
				} catch (ApplicationInstanceNotFoundException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
			}

		if(appInstanceName.equals("EQGroup"))
		{
			try {

				ai = findApplicationInstanceByName(appInstanceName, oimClient);
				List<FormField> fields = ai.getAccountForm().getFormFields();
				for (FormField f : fields) {

					if(f.getLabel().equalsIgnoreCase("User Group") ||  f.getLabel().equalsIgnoreCase("Group Type")
							|| f.getLabel().equalsIgnoreCase("Limit") || f.getLabel().equalsIgnoreCase("Group Description")
							|| f.getLabel().equalsIgnoreCase("Unique ID") || f.getLabel().equalsIgnoreCase("Justification"))
						
					{
						acctFromFields = new AcctFormFields();
						acctFromFields.setFieldLabel(f.getLabel());
						acctFromFields.setFieldApiName(f.getName());
						acctFromFields.setFieldType(f.getType());
						acctFromFields.setFieldKey(f.getFldKey());
						acctFromFields.setDefaultValue(f.getDefaultValue());
						if (f.getProperty("Lookup Code") != null)
							acctFromFields.setFiledLookupName((String) f.getProperty("Lookup Code"));
						actfList.add(acctFromFields);
						//System.out.println(f.getProperty("Lookup Code"));
						 System.out.println("Label"+f.getLabel() + " Name: " + f.getName() + " Type: "
						 + f.getType() + " Key: " + f.getFldKey() + " Defult Value: " +
						 f.getDefaultValue()) ;
					}
					
					
				
				}
				// System.out.println(actfList.get(0).makeFieldHTML());
				return actfList;
			} catch (ApplicationInstanceNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}

		
		if(appInstanceName.equals("CP1"))
		{
			try {

				ai = findApplicationInstanceByName(appInstanceName, oimClient);
				List<FormField> fields = ai.getAccountForm().getFormFields();
				for (FormField f : fields) {
					
				if(roleid.equals("N")){
					if(f.getLabel().equalsIgnoreCase("Usertypeid") ||  f.getLabel().equalsIgnoreCase("Departmentid")
							|| f.getName().equalsIgnoreCase("UD_CP1USR_REGION_ID"))
					{
						acctFromFields = new AcctFormFields();
						acctFromFields.setFieldLabel(f.getLabel());
						acctFromFields.setFieldApiName(f.getName());
						acctFromFields.setFieldType(f.getType());
						acctFromFields.setFieldKey(f.getFldKey());
						acctFromFields.setDefaultValue(f.getDefaultValue());
						if (f.getProperty("Lookup Code") != null)
							acctFromFields.setFiledLookupName((String) f.getProperty("Lookup Code"));
						actfList.add(acctFromFields);
						//System.out.println(f.getProperty("Lookup Code"));
						 System.out.println("Label"+f.getLabel() + " Name: " + f.getName() + " Type: "
						 + f.getType() + " Key: " + f.getFldKey() + " Defult Value: " +
						 f.getDefaultValue()) ;
					}
					
					
				}else {
					
					acctFromFields = new AcctFormFields();
					acctFromFields.setFieldLabel(f.getLabel());
					acctFromFields.setFieldApiName(f.getName());
					acctFromFields.setFieldType(f.getType());
					acctFromFields.setFieldKey(f.getFldKey());
					acctFromFields.setDefaultValue(f.getDefaultValue());
					if (f.getProperty("Lookup Code") != null)
						acctFromFields.setFiledLookupName((String) f.getProperty("Lookup Code"));
					actfList.add(acctFromFields);
					//System.out.println(f.getProperty("Lookup Code"));
					 System.out.println("Label"+f.getLabel() + " Name: " + f.getName() + " Type: "
					 + f.getType() + " Key: " + f.getFldKey() + " Defult Value: " +
					 f.getDefaultValue()) ;
					
					
					
				}
				}
				// System.out.println(actfList.get(0).makeFieldHTML());
				return actfList;
			} catch (ApplicationInstanceNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		if(appInstanceName.equals("AS400"))
		{
			try {

				ai = findApplicationInstanceByName(appInstanceName, oimClient);
				List<FormField> fields = ai.getAccountForm().getFormFields();
				for (FormField f : fields) {
					if(f.getLabel().equalsIgnoreCase("Date"))
					{
						acctFromFields = new AcctFormFields();
						acctFromFields.setFieldLabel(f.getLabel());
						acctFromFields.setFieldApiName(f.getName());
						acctFromFields.setFieldType(f.getType());
						acctFromFields.setFieldKey(f.getFldKey());
						acctFromFields.setDefaultValue(f.getDefaultValue());
						if (f.getProperty("Lookup Code") != null)
							acctFromFields.setFiledLookupName((String) f.getProperty("Lookup Code"));
						actfList.add(acctFromFields);
						//System.out.println(f.getProperty("Lookup Code"));
						 System.out.println("Label"+f.getLabel() + " Name: " + f.getName() + " Type: "
						 + f.getType() + " Key: " + f.getFldKey() + " Defult Value: " +
						 f.getDefaultValue()) ;
					}
					

				}
				// System.out.println(actfList.get(0).makeFieldHTML());
				return actfList;
			} catch (ApplicationInstanceNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		else {
			try {

				ai = findApplicationInstanceByName(appInstanceName, oimClient);
				List<FormField> fields = ai.getAccountForm().getFormFields();
				for (FormField f : fields) {

					acctFromFields = new AcctFormFields();
					acctFromFields.setFieldLabel(f.getLabel());
					acctFromFields.setFieldApiName(f.getName());
					acctFromFields.setFieldType(f.getType());
					acctFromFields.setFieldKey(f.getFldKey());
					acctFromFields.setDefaultValue(f.getDefaultValue());
					if (f.getProperty("Lookup Code") != null)
						acctFromFields.setFiledLookupName((String) f.getProperty("Lookup Code"));
					actfList.add(acctFromFields);
					//System.out.println(f.getProperty("Lookup Code"));
					 System.out.println("Label"+f.getLabel() + " Name: " + f.getName() + " Type: "
					 + f.getType() + " Key: " + f.getFldKey() + " Defult Value: " +
					 f.getDefaultValue()) ;

				}
				// System.out.println(actfList.get(0).makeFieldHTML());
				return actfList;
			} catch (ApplicationInstanceNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		
		
		return actfList;
	}

	public Map<String, String> getLookupMap(String lookupName, OIMClient oimClient) {

		LoginToOIM loginto =  new LoginToOIM();
		OIMClient oimclient1=null;
		try {
			oimclient1 = loginto.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, String> lookupMap = new HashMap<String, String>();
		com.thortech.xl.dataaccess.tcDataProvider dbProvider = null;

		try {
			// Establish connection to OIM Schema
			XLClientSecurityAssociation.setClientHandle(oimclient1);
			dbProvider = new com.thortech.xl.client.dataobj.tcDataBaseClient();

			String query = "select LKV_ENCODED,LKV_DECODED from lkv where lku_key=(select lku_key from lku where LKU_TYPE_STRING_KEY='"+lookupName+"')" ;
			com.thortech.xl.dataaccess.tcDataSet usersDataSet = new com.thortech.xl.dataaccess.tcDataSet();
			usersDataSet.setQuery(dbProvider, query);
			usersDataSet.executeQuery();

			int numRecords = usersDataSet.getTotalRowCount();

			// iterate through each record
			for (int i = 0; i < numRecords; i++) {
				usersDataSet.goToRow(i);
				lookupMap.put(usersDataSet.getString("LKV_ENCODED"), usersDataSet.getString("LKV_DECODED"));
				//System.out.println( usersDataSet.getString("LKV_ENCODED")+ " " + usersDataSet.getString("LKV_DECODED"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		/*for(Map.Entry<String, String> lookupEntrySet: lookupMap.entrySet() )
		{
			 System.out.println(lookupEntrySet.getKey()+" = " + lookupEntrySet.getValue());
			
		}*/
		return lookupMap;
	}

	public void getDatabaseConnectionExample(OIMClient oimClient) {
		// get OIM Client

		// OIM Schema Database Client
		com.thortech.xl.dataaccess.tcDataProvider dbProvider = null;

		try {
			// Establish connection to OIM Schema
			XLClientSecurityAssociation.setClientHandle(oimClient);
			dbProvider = new com.thortech.xl.client.dataobj.tcDataBaseClient();

			String query = "select usr_login from usr";
			com.thortech.xl.dataaccess.tcDataSet usersDataSet = new com.thortech.xl.dataaccess.tcDataSet();
			usersDataSet.setQuery(dbProvider, query);
			usersDataSet.executeQuery();

			int numRecords = usersDataSet.getTotalRowCount();

			// iterate through each record
			for (int i = 0; i < numRecords; i++) {
				usersDataSet.goToRow(i);
				System.out.println("User Login :: " + usersDataSet.getString("USR_LOGIN"));
			}
		}

		catch (Exception e) {
			System.out.println("Exception occured while getting user details" + e);
		} finally {
			if (dbProvider != null) {
				try {
					dbProvider.close();
				} catch (Exception e) {
					System.out.println("Exception occured while closing connection" + e);
				}
			}

			XLClientSecurityAssociation.clearThreadLoginSession();
		}
	}
	public String createApplicationRequest(OIMClient oimClient, String entityName,Long appInstKey, String userLogin, int operationType) {
		 
		String userKey =null;
		String requestId = null;
		RequestService requestAPI = (RequestService) oimClient.getService(RequestService.class);
		try {
			System.out.println("userLogin"+userLogin);
			userKey = getUserKey(userLogin, oimClient);
			System.out.println("userKey "+ userKey);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RequestBeneficiaryEntity requestEntity = createRequestEntity(entityName, appInstKey, userKey, operationType);
		//RequestBeneficiaryEntity requestEntity1 = createRequestEntity("AS400", "2", userKey, 2);
		
		// Adding RequestBeneficiaryEntity to List
		List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
		entities.add(requestEntity);
		//entities.add(requestEntity1);
		// creating new Beneficiary
		Beneficiary beneficiary = new Beneficiary();
		beneficiary.setBeneficiaryKey(userKey); // set BeneficiaryKey as User key
		beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY); // set the type as user
		beneficiary.setTargetEntities(entities); // set target entities as list of RequestBeneficiaryEntity
		// Adding Beneficiary to List
		List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
		beneficiaries.add(beneficiary);
		System.out.println("Beneficiaries are set to: " + beneficiaries);
		// Creating new RequestData and set the Beneficiaries with List of Beneficiaries
		RequestData requestData = new RequestData();
		requestData.setBeneficiaries(beneficiaries);
		/**
		 * getRequesterConnection() is a seperate method to create OIM connection.
		 */
		try {
			 requestId = requestAPI.submitRequest(requestData);
			System.out.println("requestId: " + requestId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestId.toString();

	}
	public String createBulkApplicationRequest(OIMClient oimClient, List<AccountCart> entityList,String userLogin, int operationType) {
		 
		String userKey =null;
		String requestId = null;
		RequestService requestAPI = (RequestService) oimClient.getService(RequestService.class);
		try {
			userKey = getUserKey(entityList.get(0).getUserLogin(), oimClient);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<RequestBeneficiaryEntity> requestEntity =null;
		List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
		 
		Long appInstKey = null;
		for(AccountCart acctInCart : entityList)
		{
			try {
				if(acctInCart.getItemType()==1)
					appInstKey = getApplicationInstanceKey(acctInCart.getAppInstacneName(), oimClient);
			} catch (ApplicationInstanceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GenericAppInstanceServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(acctInCart.getItemType()==1)
			{
				entities.add(createRequestEntity(acctInCart.getAppInstacneName(), appInstKey, userKey, operationType));
			}
			else if(acctInCart.getItemType()==2)
			{
				;
			
				entities.add(createEntReqEntity(acctInCart.getAppInstacneKey(), acctInCart.getAppInstacneName()));
			}
			
			
		}
		    
		//RequestBeneficiaryEntity requestEntity1 = createRequestEntity("AS400", "2", userKey, 2);
		
		// Adding RequestBeneficiaryEntity to List
		
		//entities.add(requestEntity1);
		// creating new Beneficiary
		Beneficiary beneficiary = new Beneficiary();
		beneficiary.setBeneficiaryKey(userKey); // set BeneficiaryKey as User key
		beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY); // set the type as user
		beneficiary.setTargetEntities(entities); // set target entities as list of RequestBeneficiaryEntity
		// Adding Beneficiary to List
		List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
		beneficiaries.add(beneficiary);
		System.out.println("Beneficiaries are set to: " + beneficiaries);
		// Creating new RequestData and set the Beneficiaries with List of Beneficiaries
		RequestData requestData = new RequestData();
		requestData.setBeneficiaries(beneficiaries);
		/**
		 * getRequesterConnection() is a seperate method to create OIM connection.
		 */
		try {
			 requestId = requestAPI.submitRequest(requestData);
			System.out.println("requestId: " + requestId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestId.toString();

	}
	public RequestBeneficiaryEntity createEntReqEntity(String entListkey, String entCode)
	{
		RequestData requestData = new RequestData();

		requestData.setJustification("Provision Entitlement Request");

		RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();

		requestEntity.setRequestEntityType(OIMType.Entitlement);

		requestEntity.setEntitySubType(entCode.toString().trim());

		requestEntity.setEntityKey(entListkey.toString().trim());

		requestEntity.setOperation(RequestConstants.MODEL_PROVISION_ENTITLEMENT_OPERATION);

		return requestEntity;
		//List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
	}
	public String createEntitlementRequest(OIMClient oimClient, String entName,Long entKey, String userLogin, int operationType) {
		 
		String userKey =null;
		String requestId = null;
		RequestService requestAPI = (RequestService) oimClient.getService(RequestService.class);
		try {
			System.out.println("userLogin"+userLogin);
			userKey = getUserKey(userLogin, oimClient);
			System.out.println("userKey "+ userKey);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RequestBeneficiaryEntity requestEntity = createRequestEntity(entName, entKey, userKey, operationType);
		//RequestBeneficiaryEntity requestEntity1 = createRequestEntity("AS400", "2", userKey, 2);
		
		// Adding RequestBeneficiaryEntity to List
		List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
		entities.add(requestEntity);
		//entities.add(requestEntity1);
		// creating new Beneficiary
		Beneficiary beneficiary = new Beneficiary();
		beneficiary.setBeneficiaryKey(userKey); // set BeneficiaryKey as User key
		beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY); // set the type as user
		beneficiary.setTargetEntities(entities); // set target entities as list of RequestBeneficiaryEntity
		// Adding Beneficiary to List
		List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
		beneficiaries.add(beneficiary);
		System.out.println("Beneficiaries are set to: " + beneficiaries);
		// Creating new RequestData and set the Beneficiaries with List of Beneficiaries
		RequestData requestData = new RequestData();
		requestData.setBeneficiaries(beneficiaries);
		/**
		 * getRequesterConnection() is a seperate method to create OIM connection.
		 */
		try {
			 requestId = requestAPI.submitRequest(requestData);
			System.out.println("requestId: " + requestId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestId.toString();

	}
	private RequestBeneficiaryEntity createRequestEntity(String EntityName, Long EntityNameKey, String userLogin,
			int requestType) {
		String entitySubType = "";
		String entityKey = null;
		String userKey = null;
		RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
		if (requestType == 1) {
			requestEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.ApplicationInstance); // Type of the
																											// Request
			requestEntity.setEntitySubType(EntityName); // Name of the Application Instance
			requestEntity.setEntityKey(EntityNameKey+"".toString().trim()); // Application instance key
			requestEntity.setOperation(RequestConstants.MODEL_PROVISION_APPLICATION_INSTANCE_OPERATION); // Request
																											// operation																								// type.
			requestEntity.setEntityData(getRequestEntityData(userLogin));
			System.out.println("Operation set to " + RequestConstants.MODEL_PROVISION_APPLICATION_INSTANCE_OPERATION);
		}
		else if (requestType == 2) {
			requestEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.ApplicationInstance); // Type of the
																											// Request
			requestEntity.setEntitySubType(EntityName); // Name of the Application Instance
			requestEntity.setEntityKey(EntityNameKey+"".toString().trim()); // Application instance key
			requestEntity.setOperation(RequestConstants.MODEL_PROVISION_APPLICATION_INSTANCE_OPERATION); // Request
																											// operation																								// type.
			//requestEntity.setEntityData(getRequestEntityData(userLogin));
			System.out.println("Operation set to " + RequestConstants.MODEL_PROVISION_APPLICATION_INSTANCE_OPERATION);
		}
		else if (requestType == 3) {
			requestEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.Entitlement); // Type of the
																											// Request
			requestEntity.setEntitySubType(EntityName); // Name of the Application Instance
			requestEntity.setEntityKey(EntityNameKey+"".toString().trim()); // Application instance key
			requestEntity.setOperation(RequestConstants.MODEL_PROVISION_ENTITLEMENT_OPERATION); // Request
																											// operation																								// type.
			//requestEntity.setEntityData(getRequestEntityData(userLogin));
			System.out.println("Operation set to " + RequestConstants.MODEL_PROVISION_ENTITLEMENT_OPERATION);
		}
		return requestEntity;
	}

	private List<RequestBeneficiaryEntityAttribute> getRequestEntityData(String userLogin) {
		List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
		RequestBeneficiaryEntityAttribute attr;
		attr = new RequestBeneficiaryEntityAttribute("First Name", "Ammar",
				RequestBeneficiaryEntityAttribute.TYPE.String);
		attrs.add(attr);
		attr = new RequestBeneficiaryEntityAttribute("Last Name", "Kashif",
				RequestBeneficiaryEntityAttribute.TYPE.String);
		attrs.add(attr);
		attr = new RequestBeneficiaryEntityAttribute("Organization Name",
				"4~OU=Users,OU=Central,DC=contoso,DC=com,DC=pk", RequestBeneficiaryEntityAttribute.TYPE.String);
		attrs.add(attr);
		return attrs;
	}

	public String getUserKey(String userLogin, OIMClient oimClient) throws Exception {
		Set retAttr = new HashSet();
		String userKey = null;

		retAttr.add(UserManagerConstants.AttributeName.USER_KEY.getId());
		UserManager userAPI = oimClient.getService(UserManager.class);
		User user = userAPI.getDetails(userLogin, retAttr, true);
		userKey = user.getEntityId();
		return userKey;
	}

	public String disableAccountOfUser(OIMClient oimClient, String appInstanceName, String oiuKey, String userLogin,String jNotes) {
		String userKey = null;

		try {
			userKey = getUserKey(userLogin, oimClient);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OIMService unifiedService = oimClient.getService(OIMService.class);

		RequestData requestData = new RequestData();

		requestData.setJustification(jNotes);

		RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();

		requestEntity.setRequestEntityType(OIMType.ApplicationInstance);

		requestEntity.setEntitySubType(appInstanceName.toString().trim());

		requestEntity.setEntityKey(oiuKey.toString().trim());
		requestEntity.setOperation(RequestConstants.MODEL_DISABLE_ACCOUNT_OPERATION);

		List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();

		entities.add(requestEntity);

		Beneficiary beneficiary = new Beneficiary();

		beneficiary.setBeneficiaryKey(userKey);

		beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY);

		beneficiary.setTargetEntities(entities);

		List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();

		beneficiaries.add(beneficiary);

		requestData.setBeneficiaries(beneficiaries);

		try {
			OperationResult result = unifiedService.doOperation(requestData, OIMService.Intent.REQUEST);
			System.out.print(result.getRequestID());
			System.out.print(result.getEntityId());
			System.out.print(result.getOperationStatus());
			return result.getRequestID().toString().trim();

		} catch (OIMServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
//////////////////////////////////Disable User Account with OIU KEY without Request///////////////////////////////////
	
	public String disableAccount(OIMClient oimClient, String OIU_KEY, String AppName, String Login) {

		 ProvisioningService provService = oimClient.getService(ProvisioningService.class);
				
				try {
					provService.disable(Long.valueOf(OIU_KEY));
					System.out.print(AppName+" for "+Login+" has been Disable");
					return AppName+" for "+Login+" has been Disable";
				} catch (oracle.iam.platform.authopss.exception.AccessDeniedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return e.getMessage();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return e.getMessage();
				} catch (AccountNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return e.getMessage();
				} catch (ImproperAccountStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return e.getMessage();
				} catch (GenericProvisioningException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return e.getMessage();
				}
				
			
			}
			
			
		
		
		
	
	
	
	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Start Enable Account Method
	public String enableAccountOfUser(OIMClient oimClient, String appInstanceName, String oiuKey, String userLogin,String jNotes) {
		String userKey = null;
			System.out.print("enableAccountOfUser");
		try {
			userKey = getUserKey(userLogin, oimClient);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OIMService unifiedService = oimClient.getService(OIMService.class);

		RequestData requestData = new RequestData();

		requestData.setJustification(jNotes );

		RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();

		requestEntity.setRequestEntityType(OIMType.ApplicationInstance);

		requestEntity.setEntitySubType(appInstanceName.toString().trim());

		requestEntity.setEntityKey(oiuKey.toString().trim());
		requestEntity.setOperation(RequestConstants.MODEL_ENABLE_ACCOUNT_OPERATION);

		List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();

		entities.add(requestEntity);

		Beneficiary beneficiary = new Beneficiary();

		beneficiary.setBeneficiaryKey(userKey);

		beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY);

		beneficiary.setTargetEntities(entities);

		List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();

		beneficiaries.add(beneficiary);

		requestData.setBeneficiaries(beneficiaries);

		try {
			OperationResult result = unifiedService.doOperation(requestData, OIMService.Intent.REQUEST);
			System.out.print(result.getRequestID());
			System.out.print(result.getEntityId());
			System.out.print(result.getOperationStatus());
			return result.getRequestID().toString().trim();

		} catch (OIMServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	// End Enable Account Method

	// Revoke Entitlement Start
	public String revokeEntitlementOfUser(OIMClient oimClient, String appInstanceName, String oiuKey,
			String userLogin) {
		String userKey = null;

		try {
			userKey = getUserKey(userLogin, oimClient);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OIMService unifiedService = oimClient.getService(OIMService.class);

		RequestData requestData = new RequestData();

		requestData.setJustification("Remove Active Directory Group: "+appInstanceName);

		RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();

		requestEntity.setRequestEntityType(OIMType.Entitlement);

		requestEntity.setEntitySubType(appInstanceName.toString().trim());

		requestEntity.setEntityKey(oiuKey.toString().trim());
		requestEntity.setOperation(RequestConstants.MODEL_REVOKE_ENTITLEMENT_OPERATION);

		List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();

		entities.add(requestEntity);

		Beneficiary beneficiary = new Beneficiary();

		beneficiary.setBeneficiaryKey(userKey);

		beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY);

		beneficiary.setTargetEntities(entities);

		List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();

		beneficiaries.add(beneficiary);

		requestData.setBeneficiaries(beneficiaries);

		try {
			OperationResult result = unifiedService.doOperation(requestData, OIMService.Intent.REQUEST);
			System.out.print(result.getRequestID());
			System.out.print(result.getEntityId());
			System.out.print(result.getOperationStatus());
			return result.getRequestID().toString().trim();

		} catch (OIMServiceException e) {
			// TODO Auto-generated catch block
			e.getStackTrace();
			//System.out.print("already revoked");
		}
		return "notProvisioned";
	}

	// Revoke Entitlement End
	
	
	
	
	///////-----------------------------------------ADD Entitlement Method--------------------------------------
	
	
		public String AddEntitlementOfUser(OIMClient oimClient, String entName, String entKey,
				String userLogin) {
			String userKey = null;

			try {
				userKey = getUserKey(userLogin, oimClient);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			OIMService unifiedService = oimClient.getService(OIMService.class);

			RequestData requestData = new RequestData();

			requestData.setJustification("ADD Active Directory Group:"+entName);

			RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();

			requestEntity.setRequestEntityType(OIMType.Entitlement);

			requestEntity.setEntitySubType(entName.toString().trim());

			requestEntity.setEntityKey(entKey.toString().trim());
			requestEntity.setOperation(RequestConstants.MODEL_PROVISION_ENTITLEMENT_OPERATION);

			List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();

			entities.add(requestEntity);

			Beneficiary beneficiary = new Beneficiary();

			beneficiary.setBeneficiaryKey(userKey);

			beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY);

			beneficiary.setTargetEntities(entities);

			List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();

			beneficiaries.add(beneficiary);

			requestData.setBeneficiaries(beneficiaries);

			try {
				OperationResult result = unifiedService.doOperation(requestData, OIMService.Intent.REQUEST);
				System.out.print(result.getRequestID());
				System.out.print(result.getEntityId());
				System.out.print(result.getOperationStatus());
				return result.getRequestID().toString().trim();

			} catch (OIMServiceException e) {
				// TODO Auto-generated catch block
				e.getStackTrace();
				//System.out.print("already revoked");
			}
			return "Request Creation Failed";
		}

		

	
	
	
	//------------------------------------------------------------------------------------------------------------
	//
	public String resetPassword(String userId ,String password) { //Function to disable user
		String status="";
			UserOperationModifyAcc uoma = new UserOperationModifyAcc();
	        try {
	        	status = uoma.changePassword(userId, password);
				System.out.print(status);
	            System.out.println("Reset Password");
	            return status;
	        } catch (Exception e) {
	           System.out.println("Kashif: "+e.getMessage().toString());
	           return e.getMessage();
	        }  
	    }
	//
	
	
	//changeAccountPassword Start
	public void changeAccountPassword(String login, OIMClient oimClient, String appInstanceName, String newPasswordforAcc ) throws Exception {

		//String appInstanceName="ActiveDirectory";
		 
	 
		UserManager usrMgr = oimClient.getService(UserManager.class);
		ProvisioningService provService = oimClient.getService(ProvisioningService.class);
		PasswordMgmtService pwdMgmtService = oimClient.getService(PasswordMgmtService.class);
		System.out.println("user login:" + login);
		// Change variable values accordinly
		String userLogin = login; // OIM User Login
		// String resourceObjectName = "AS400 User"; // Resource Object Name
		String resourceObjectName = getResourceObjectName(appInstanceName,oimClient); // Resource Object Name
		System.out.println("App Instance:" + appInstanceName);
		char[] newPassword = newPasswordforAcc.toCharArray(); //
		System.out.print("SUCCESS3");
		// Get user's details
		boolean useUserLogin = true;
		HashSet<String> retAttrs = new HashSet<String>();
		retAttrs.add(UserManagerConstants.AttributeName.USER_KEY.getId()); // usr_key
		User user = usrMgr.getDetails(userLogin, retAttrs, useUserLogin);
		String userKey = user.getId(); // Get usr_key

		// Get user's resource accounts
		SearchCriteria criteria = new SearchCriteria(ProvisioningConstants.AccountSearchAttribute.OBJ_NAME.getId(),
				resourceObjectName, SearchCriteria.Operator.EQUAL);
		List<Account> accounts = provService.getAccountsProvisionedToUser(userKey, criteria, null, useUserLogin);
		Account resourceAcct = accounts.isEmpty() ? null : accounts.get(0); // Grab first item
		System.out.print("SUCCESS4");
		if (resourceAcct != null) {
			System.out.print("SUCCESS10");
			String accountId = resourceAcct.getAccountID(); // oiu_key
			String procInstFormKey = resourceAcct.getProcessInstanceKey(); // Process Form Instance
			String appInstName = resourceAcct.getAppInstance().getApplicationInstanceName();
			System.out.println("App Instance Name:" + appInstName);

			// Validate new password against account password policy
			ValidationResult vr = pwdMgmtService.validatePasswordAgainstPolicy(newPassword, user, appInstName,
					Locale.getDefault());
			boolean isNewPasswordValid = vr.isPasswordValid();

			// Perfrom account password change if account password policy passes
			if (isNewPasswordValid) {
				// TODO: Account Password History being bypassed in
				// validatePasswordAgainstPolicy.

				// Change resource account password
				if(appInstName.equals("CP1"))
				{
					 System.out.println("CP Encryption called"); 
					 System.out.println("appInstName:"+appInstName); 
					CallSOAPWS smsSend = new CallSOAPWS();
					String encryptedPassword = smsSend.getEncryptedValue("Hello@123");
			        System.out.println("encryptedPassword"+encryptedPassword);
			        
			        char[] PasswordCP = encryptedPassword.toCharArray();
			        System.out.println("PasswordCP"+PasswordCP.toString());     
					provService.changeAccountPassword(Long.valueOf(accountId), PasswordCP);
				}
				else
				{	
					System.out.println("CP Encryption not called");
				provService.changeAccountPassword(Long.valueOf(accountId), newPassword);
				}
			//	provService.enable(Long.valueOf(accountId));
				System.out.print("SUCCESS");
				// Confirm resource account password; This checks if the password on the process
				// form is identical to the supplied value
				boolean confirmAcctPwd = provService.confirmAccountPassword(Long.valueOf(accountId), newPassword);
			}
			
			
		}
		
		
	}
	
	//
	
	public String getResourceObjectName(String appInstName, OIMClient omClient){
        
        

		 

        ApplicationInstanceService appService = omClient.getService(ApplicationInstanceService.class);
        ApplicationInstance appInst;
        String resourceObjectName=null;
        try{
            appInst = appService.findApplicationInstanceByName(appInstName);
            resourceObjectName = appInst.getObjectName();



           System.out.println("Resource Object Name :" + resourceObjectName);
           
        }catch(ApplicationInstanceNotFoundException e){
            e.printStackTrace();
        }catch(GenericAppInstanceServiceException e){
           e.printStackTrace();
        }



        
        return resourceObjectName;
 }
	//chnage User password
	public boolean changePassowrd(String newPassword, OIMClient oimc) { 
		
		
		
	//	AuthenticatedSelfService authentcatedSelfService = null;
//		AuthenticatedSelfService authentcatedSelfService = null;
		//AuthenticatedSelfService m_authselfservice = oimc.getService(AuthenticatedSelfService.class);
		AuthenticatedSelfService m_authselfservice = oimc.getService(AuthenticatedSelfService.class);
		System.out.print(m_authselfservice);
		 UserManager userManager =null;
	        try {
	        	char [] oldPass = "Hello@1234".toCharArray();
	        	//m_authselfservice = UserSessaion.getOimClient().getService(AuthenticatedSelfService.class);
	        	
	        //	m_authselfservice.changePassword(oldPass,newPassword.toCharArray(),newPassword.toCharArray());
	        	
	        	m_authselfservice.changePassword(newPassword.toCharArray());
	        	
	        	//ContextManager.clearContext();
	        //	userManager = UserSessaion.getOimClient().getService(UserManager.class);
	        	// userManager.changePassword(UserSessaion.getLoggedInUserLogin(), newPassword.toCharArray(), true,
                //        null, false);
	            System.out.println("Password Successfully");
	            return true;
	            
	        } catch (Exception e) {
	           System.out.println("Kashif: "+e.getMessage().toString());
	           
	  		 
	  		  
	          
	        }  
	        return false; 
	    }
	public boolean enableUser(String userId) { //Function to disable user
		 UserManager userManager =null;
	        try {
	        	userManager = UserSessaion.getOimClient().getService(UserManager.class);
	            userManager.enable(userId, true);
	            System.out.println("\n Enable user Successfully");
	           
	            return true;
	            
	            
	        } catch (Exception e) {
	           System.out.println("Kashif: "+e.getMessage().toString());
	        }  
	        return false;
	    }
	
	public boolean disableUser(String userId) { //Function to disable user
		 UserManager userManager =null;
	        try {
	        	userManager = UserSessaion.getOimClient().getService(UserManager.class);
	            userManager.disable(userId, true);
	            System.out.println("\n Disabled user Successfully");
	            return true;
	            
	        } catch (Exception e) {
	           System.out.println("Kashif: "+e.getMessage().toString());
	        }  
	        return false;
	    }
	
//
	
	public String modifyUser(OIMClient oimClient,String userLogin) {
		String userKey = null;

		try {
			userKey = getUserKey(userLogin, oimClient);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OIMService unifiedService = oimClient.getService(OIMService.class);

		RequestData requestData = new RequestData();

		requestData.setJustification("Modify User Operation");

		RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();

		requestEntity.setRequestEntityType(OIMType.User);

		//requestEntity.setEntitySubType(appInstanceName.toString().trim());
		requestEntity.setEntityKey(userKey.toString().trim());
		requestEntity.setOperation(RequestConstants.MODEL_MODIFY_USER_PROFILE);
		//requestEntity.setEntityData(entityAttributes);
		List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
		
		List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
		RequestBeneficiaryEntityAttribute attr;
		
		attr = new RequestBeneficiaryEntityAttribute("Email", "test123@gmail.com",
				RequestBeneficiaryEntityAttribute.TYPE.String);
		attrs.add(attr);
		
		//attr = new RequestBeneficiaryEntityAttribute("Organization Name",
				//"4~OU=Users,OU=Central,DC=contoso,DC=com,DC=pk", RequestBeneficiaryEntityAttribute.TYPE.String);
		//attrs.add(attr);
		requestEntity.setEntityData(attrs);
		
		entities.add(requestEntity);

		Beneficiary beneficiary = new Beneficiary();

		beneficiary.setBeneficiaryKey(userKey);

		beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY);

		beneficiary.setTargetEntities(entities);

		List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();

		beneficiaries.add(beneficiary);

		requestData.setBeneficiaries(beneficiaries);

		try {
			OperationResult result = unifiedService.doOperation(requestData, OIMService.Intent.REQUEST);
			System.out.print("thereeeeeeeee");
			System.out.print(result.getRequestID());
			System.out.print(result.getEntityId());
			System.out.print(result.getOperationStatus());
			return result.getRequestID().toString().trim();

		} catch (OIMServiceException e) {
			// TODO Auto-generated catch block
			e.getStackTrace();
			//System.out.print("already revoked");
		}
		return "notProvisioned";
	}

	//
	
	 //
	///////////////////////////////////////////////Get Account Provisioned to User/////////////////////////
	public List<Account> getAllAccount(OIMClient oimClient,String userId)
	{
		ProvisioningService accounts = oimClient.getService(ProvisioningService.class);
		List<Account> account = null;
		try {
			account = accounts.getAccountsProvisionedToUser(userId);
			System.out.print("acc"+account.size());
		} catch (UserNotFoundException | GenericProvisioningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
	}
	
}
