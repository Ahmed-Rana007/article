package com.hbl.approvalrequests;


import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Date;
import com.hbl.selfservice.LoginToOIM;

import oracle.iam.api.OIMService;
import oracle.iam.catalog.vo.Catalog;
import oracle.iam.exception.OIMServiceException;
import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.api.UserManagerConstants;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.utils.vo.OIMType;
import oracle.iam.request.api.RequestService;
import oracle.iam.request.exception.BulkBeneficiariesAddException;
import oracle.iam.request.exception.BulkEntitiesAddException;
import oracle.iam.request.exception.InvalidRequestDataException;
import oracle.iam.request.exception.InvalidRequestException;
import oracle.iam.request.exception.RequestServiceException;
import oracle.iam.request.vo.Beneficiary;
import oracle.iam.request.vo.RequestBeneficiaryEntity;
import oracle.iam.request.vo.RequestBeneficiaryEntityAttribute;
import oracle.iam.request.vo.RequestConstants;
import oracle.iam.request.vo.RequestData;
import oracle.iam.request.vo.RequestEntity;
import oracle.iam.request.vo.RequestEntityAttribute;
import oracle.iam.vo.OperationResult;

public class CustomRequest {
//1 
	
	public static void main(String arg[]) {
		CustomRequest cr = new CustomRequest();
		// CustomRequest.submitCustomRequest("1","36020","LAM TEAM","95");
		LoginToOIM logOim = new LoginToOIM();
		try {
			OIMClient oimClient = logOim.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
			//cr.createApplicationRequest(oimClient,"ActiveDirectory","KASHIF.ARIF3",1);
			
			//cr.revokeEntitlement(oimClient);
			//cr.requestWithChildForm(oimClient);
			//cr.createEquationLimitsRequest(oimClient);
			//cr.disableAccountOfUser(oimClient);
			//cr.createEntRequest(oimClient);
			//cr.requestWithChildFormEquation(oimClient);
		//	cr.modifyUserRequest(oimClient, "30022", "akhter", "03/11/2021", "03/12/2021", "Casual");
		//	cr.modifyAccountRequest(oimClient);
		} catch (Exception e) {
			e.getMessage();
		}

	}
	public static boolean submitCustomRequest(String requesterUserLogin, String beneficiaryUserKey, String roleName,
			String roleKey) {

		boolean processedStatus = false;
		LoginToOIM loginOim = new LoginToOIM();
		OIMClient client = null;
		try {
			client = loginOim.loggedIntoOIM("xelsysadm", "Hblpoc_1234");// check my another post for establishing
																		// connection using local code

			int count = 0;
			RequestService requestAPI = (RequestService) client.getService(RequestService.class);

			RequestBeneficiaryEntity reqBenefEntity = new RequestBeneficiaryEntity();
			reqBenefEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.Role);
			reqBenefEntity.setEntitySubType(roleName);
			reqBenefEntity.setEntityKey(roleKey);

			reqBenefEntity.setOperation(RequestConstants.MODEL_ASSIGN_ROLES_OPERATION);
			// reqBenefEntity.setOperation(RequestConstants.MODEL_REMOVE_ROLES_OPERATION);
			System.out.println("Operation set to " + RequestConstants.MODEL_ASSIGN_ROLES_OPERATION);

			List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
			List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
			entities.add(reqBenefEntity);

			Beneficiary beneficiary = new Beneficiary();
			beneficiary.setBeneficiaryKey(beneficiaryUserKey);
			beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY);
			beneficiary.setTargetEntities(entities);
			beneficiaries.add(beneficiary);
			System.out.println(beneficiaryUserKey + " is added as beneficiary");

			RequestData reqData = new RequestData();
			reqData.setBeneficiaries(beneficiaries);

			System.out.println("Beneficiaries are set to: " + beneficiaries);

			reqData.setJustification("Justification");

			System.out.println("Initializing request service object");

			System.out.println("sbumitting the request");
			String requestId = null;
			try {

				requestId = requestAPI.submitRequest(reqData);
				System.out.println("Request Id created: " + requestId);
				if (requestId != null) {
					System.out.println("Request created successfully , Request Id: " + requestId);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return processedStatus;
	}

	public void createApplicationRequest(OIMClient oimClient, String entityName, String userLogin, int operationType) {
		 
		String userKey =null;
		RequestService requestAPI = (RequestService) oimClient.getService(RequestService.class);
		try {
			userKey = getUserKey(userLogin, oimClient);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RequestBeneficiaryEntity requestEntity = createRequestEntity(entityName, "1", userKey, operationType);
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
			String requestId = requestAPI.submitRequest(requestData);
			System.out.println("requestId: " + requestId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

/*	public static void main(String arg[]) {
		CustomRequest cr = new CustomRequest();
		// CustomRequest.submitCustomRequest("1","36020","LAM TEAM","95");
		LoginToOIM logOim = new LoginToOIM();
		try {
			OIMClient oimClient = logOim.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
			//cr.createApplicationRequest(oimClient,"ActiveDirectory","KASHIF.ARIF3",1);
			
			//cr.revokeEntitlement(oimClient);
			cr.requestWithChildForm(oimClient);
			//cr.disableAccountOfUser(oimClient);
		} catch (Exception e) {
			e.getMessage();
		}

	} */

	private RequestBeneficiaryEntity createRequestEntity(String EntityName, String EntityNameKey, String userLogin,
			int requestType) {
		String entitySubType = "";
		String entityKey = null;
		String userKey = null;
		RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
		if (requestType == 1) {
			requestEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.ApplicationInstance); // Type of the
																											// Request
			requestEntity.setEntitySubType(EntityName); // Name of the Application Instance
			requestEntity.setEntityKey(EntityNameKey); // Application instance key
			requestEntity.setOperation(RequestConstants.MODEL_PROVISION_APPLICATION_INSTANCE_OPERATION); // Request
																											// operation																								// type.
			requestEntity.setEntityData(getRequestEntityData(userLogin));
			System.out.println("Operation set to " + RequestConstants.MODEL_PROVISION_APPLICATION_INSTANCE_OPERATION);
		}
		if (requestType == 2) {
			requestEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.ApplicationInstance); // Type of the
																											// Request
			requestEntity.setEntitySubType(EntityName); // Name of the Application Instance
			requestEntity.setEntityKey(EntityNameKey); // Application instance key
			requestEntity.setOperation(RequestConstants.MODEL_PROVISION_APPLICATION_INSTANCE_OPERATION); // Request
																									// operation																								// type.
			//requestEntity.setEntityData(getRequestEntityData(userLogin));
			
			System.out.println("Operation set to " + RequestConstants.MODEL_PROVISION_APPLICATION_INSTANCE_OPERATION);
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
	public void disableAccountOfUser(OIMClient oimClient)
	{
		OIMService unifiedService = oimClient.getService(OIMService.class);

		RequestData requestData = new RequestData();

		requestData.setJustification("Provision Entitlement Request");

		RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();

		requestEntity.setRequestEntityType(OIMType.ApplicationInstance);

		requestEntity.setEntitySubType("ActiveDirectory");

		requestEntity.setEntityKey("1130");
		requestEntity.setOperation(RequestConstants.MODEL_DISABLE_ACCOUNT_OPERATION);

		
		
		List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();

		entities.add(requestEntity);


		Beneficiary beneficiary = new Beneficiary();

		String userKey = "20020";

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
			 
		} catch (OIMServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createEntRequest(OIMClient oimClient)
	{
		OIMService unifiedService = oimClient.getService(OIMService.class);

		RequestData requestData = new RequestData();

		requestData.setJustification("Provision Entitlement Request");
		RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
		List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
		
		for(int i=1 ; i< 4;i++)
		{
			requestEntity = new RequestBeneficiaryEntity();

			requestEntity.setRequestEntityType(OIMType.Entitlement);
			/*if(i==1)
			{
				requestEntity.setEntitySubType("170~BRBOMA");

				requestEntity.setEntityKey("4410");
			}
			else { */
				requestEntity.setEntitySubType("170~COPCU2");

				requestEntity.setEntityKey("3803");
			//}
			
			requestEntity.setOperation(RequestConstants.MODEL_PROVISION_ENTITLEMENT_OPERATION);
			requestEntity.setEntityData(getRequestEntityDataEquation("AR01",1009+i));
			
			entities.add(requestEntity);
		}
		


		Beneficiary beneficiary = new Beneficiary();

		String userKey = "40022";

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
			 
		} catch (OIMServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void revokeEntitlement(OIMClient oimClient)
	{
		OIMService unifiedService = oimClient.getService(OIMService.class);

		RequestData requestData = new RequestData();

		requestData.setJustification("Provision Entitlement Request");

		RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();

		requestEntity.setRequestEntityType(OIMType.Role);

		requestEntity.setEntitySubType("Head Branch Ops");

		requestEntity.setEntityKey("116");


		requestEntity.setOperation(RequestConstants.MODEL_ASSIGN_ROLES_OPERATION);


		List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();

		entities.add(requestEntity);


		Beneficiary beneficiary = new Beneficiary();

		String userKey = "30022";

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
			 
		} catch (OIMServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void childRecordRequest(OIMClient oimClient)
	{
		OIMService unifiedService = oimClient.getService(OIMService.class);

		RequestData requestData = new RequestData();

		requestData.setJustification("Provision Entitlement Request");

		RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();

		requestEntity.setRequestEntityType(OIMType.Catalog);

		requestEntity.setEntitySubType("ActiveDirectory");

		requestEntity.setEntityKey("1130");
		requestEntity.setOperation(RequestConstants.REQUEST_CHILD_INFO);


		List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();

		entities.add(requestEntity);


		Beneficiary beneficiary = new Beneficiary();

		String userKey = "20020";

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
			 
		} catch (OIMServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void getCatalogDetails()
	{
		Catalog catalog;
		 
	}
	
	void requestWithChildForm(OIMClient oimClient)
	{
		RequestService reqSvc = oimClient.getService(RequestService.class);
		RequestData rd = new RequestData();

		//setting the request template name we want to use
		rd.setRequestTemplateName("Modify Provisioned Resource");

		//creating a list that will store the beneficiaries
		List <Beneficiary> benef_list = new ArrayList<Beneficiary>();

		//create the first beneficiary
		Beneficiary b = new Beneficiary();
		b.setBeneficiaryKey("40021"); //set it to USR_KEY
		b.setBeneficiaryType(Beneficiary.USER_BENEFICIARY);

		//defining a list to store the entities
		List <RequestBeneficiaryEntity> rbe_list = new ArrayList<RequestBeneficiaryEntity>();

		//creating an entity of type AD User
		RequestBeneficiaryEntity rbe = new RequestBeneficiaryEntity();
		rbe.setEntityType("Resource");
		rbe.setEntitySubType("AD User");//Resource Object name
		rbe.setEntityKey("4781");//entity instance key, ORC_KEY

		//defining the attributes
		List <RequestBeneficiaryEntityAttribute> rbe_attr_list = new ArrayList<RequestBeneficiaryEntityAttribute>();

		//the mandatory attributes need to be filled in
		RequestBeneficiaryEntityAttribute attr0 = new RequestBeneficiaryEntityAttribute();
		attr0.setName("AD Server");
		attr0.setType(RequestBeneficiaryEntityAttribute.TYPE.Long);
		attr0.setValue(new Long(1));

		//in this example we modify also Pager attribute
		RequestBeneficiaryEntityAttribute attr1 = new RequestBeneficiaryEntityAttribute();
		attr1.setName("Pager");
		attr1.setType(RequestBeneficiaryEntityAttribute.TYPE.String);
		attr1.setValue("1234567890");

		//this example shows how to manipulate child data
		RequestBeneficiaryEntityAttribute attr2 = new RequestBeneficiaryEntityAttribute();
		attr2.setName("AD User Group Details");
		attr2.setAction(RequestBeneficiaryEntityAttribute.ACTION.Add);
		attr2.setHasChild(true);
		List <RequestBeneficiaryEntityAttribute> attr2_child_list = new ArrayList<RequestBeneficiaryEntityAttribute>();
		RequestBeneficiaryEntityAttribute attr2_child1 = new RequestBeneficiaryEntityAttribute();
		attr2_child1.setName("Active Directory~CN=Domain Users,CN=Users,DC=hblpoc,DC=com");
		attr2_child1.setType(RequestBeneficiaryEntityAttribute.TYPE.String);
		attr2_child1.setValue("4~CN=Domain Users,CN=Users,DC=hblpoc,DC=com"); //value from lookup (code)
		attr2_child_list.add(attr2_child1);
		attr2.setChildAttributes(attr2_child_list);


		//adding attributes to the list
		rbe_attr_list.add(attr0);
		rbe_attr_list.add(attr1);
		rbe_attr_list.add(attr2);
		rbe.setEntityData(rbe_attr_list);

		//adding entity to the list
		rbe_list.add(rbe);

		//setting the list to the beneficiary
		b.setTargetEntities(rbe_list);

		//adding the beneficiary
		benef_list.add(b);
		rd.setBeneficiaries(benef_list);

		try{
		  //submiting the request
		  String key = reqSvc.submitRequest(rd);
		  System.out.println("Req key:" + key);
		}catch(Exception e){
		  //handle exceptions here
		  e.printStackTrace();
		}
	}
	
	
	void requestWithChildFormEquation(OIMClient oimClient)
	{
		RequestService reqSvc = oimClient.getService(RequestService.class);
		RequestData rd = new RequestData();

		//setting the request template name we want to use
		rd.setRequestTemplateName("Modify Provisioned Resource");

		//creating a list that will store the beneficiaries
		List <Beneficiary> benef_list = new ArrayList<Beneficiary>();

		//create the first beneficiary
		Beneficiary b = new Beneficiary();
		b.setBeneficiaryKey("40022"); //set it to USR_KEY
		b.setBeneficiaryType(Beneficiary.USER_BENEFICIARY);

		//defining a list to store the entities
		List <RequestBeneficiaryEntity> rbe_list = new ArrayList<RequestBeneficiaryEntity>();

		//creating an entity of type AD User
		RequestBeneficiaryEntity rbe = new RequestBeneficiaryEntity();
		rbe.setEntityType("Resource");
		rbe.setEntitySubType("Equation");//Resource Object name
		rbe.setEntityKey("4999");//entity instance key, ORC_KEY

		//defining the attributes
		List <RequestBeneficiaryEntityAttribute> rbe_attr_list = new ArrayList<RequestBeneficiaryEntityAttribute>();

		//the mandatory attributes need to be filled in
		RequestBeneficiaryEntityAttribute attr0 = new RequestBeneficiaryEntityAttribute();
		attr0.setName("Extension");
		attr0.setType(RequestBeneficiaryEntityAttribute.TYPE.String);
		attr0.setValue("123");
		
		//in this example we modify also Pager attribute
		RequestBeneficiaryEntityAttribute attr1 = new RequestBeneficiaryEntityAttribute();
		attr1.setName("Phone Number");
		attr1.setType(RequestBeneficiaryEntityAttribute.TYPE.String);
		attr1.setValue("123456789");

		//this example shows how to manipulate child data
		RequestBeneficiaryEntityAttribute attr2 = new RequestBeneficiaryEntityAttribute();
		attr2.setName("V_WDPF for Equation");
		attr2.setAction(RequestBeneficiaryEntityAttribute.ACTION.Add);
		attr2.setHasChild(true);
		
		List <RequestBeneficiaryEntityAttribute> attr2_child_list = new ArrayList<RequestBeneficiaryEntityAttribute>();
		RequestBeneficiaryEntityAttribute attr2_child1 = new RequestBeneficiaryEntityAttribute();
		attr2_child1.setName("Group");
		attr2_child1.setType(RequestBeneficiaryEntityAttribute.TYPE.String);
		attr2_child1.setValue("170~COPCU2"); //value from lookup (code)
		attr2_child_list.add(attr2_child1);
		RequestBeneficiaryEntityAttribute attr2_child2 = new RequestBeneficiaryEntityAttribute();
		attr2_child2.setName("Branch Number");
		attr2_child2.setType(RequestBeneficiaryEntityAttribute.TYPE.String);
		attr2_child2.setValue("3000"); //value from lookup (code)
		attr2_child_list.add(attr2_child2);
		
		attr2.setChildAttributes(attr2_child_list);


		//adding attributes to the list
		rbe_attr_list.add(attr0);
		rbe_attr_list.add(attr1);
		rbe_attr_list.add(attr2);
		rbe.setEntityData(rbe_attr_list);

		//adding entity to the list
		rbe_list.add(rbe);

		//setting the list to the beneficiary
		b.setTargetEntities(rbe_list);

		//adding the beneficiary
		benef_list.add(b);
		rd.setBeneficiaries(benef_list);

		try{
		  //submiting the request
		  String key = reqSvc.submitRequest(rd);
		  System.out.println("Req key:" + key);
		}catch(Exception e){
		  //handle exceptions here
		  e.printStackTrace();
		  System.out.println(e.getMessage());
		}
	}
	
	
	public void createEquationLimitsRequest(OIMClient oimClient)
	{
		OIMService unifiedService = oimClient.getService(OIMService.class);

		RequestData requestData = new RequestData();

		requestData.setJustification("Provision Entitlement Request");
		RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
		List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
		for(int i=1041 ; i< 1043;i++)
		{
			requestEntity = new RequestBeneficiaryEntity();
			requestEntity.setRequestEntityType(OIMType.ApplicationInstance);
			requestEntity.setEntitySubType("FinancialLimits");
			requestEntity.setEntityKey("204");
			requestEntity.setOperation(RequestConstants.MODEL_PROVISION_APPLICATION_INSTANCE_OPERATION);
			requestEntity.setEntityData(getRequestEntityDataEquation("AR01",i));
			entities.add(requestEntity);
			
		}

		Beneficiary beneficiary = new Beneficiary();

		String userKey = "48021";

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
			 
		} catch (OIMServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private List<RequestBeneficiaryEntityAttribute> getRequestEntityDataEquation(String userLogin, int residentBranch) {
		List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
		RequestBeneficiaryEntityAttribute attr;
		attr = new RequestBeneficiaryEntityAttribute("Process Inter Branch Transaction", "N",
				RequestBeneficiaryEntityAttribute.TYPE.String);
		attrs.add(attr);
		attr = new RequestBeneficiaryEntityAttribute("Inter Branch Debit Transaction Limit", 0,
				RequestBeneficiaryEntityAttribute.TYPE.Integer);
		attrs.add(attr);
		attr = new RequestBeneficiaryEntityAttribute("Inter Branch Credit Transaction Limit",
				1, RequestBeneficiaryEntityAttribute.TYPE.Integer);
		attrs.add(attr);
		attr = new RequestBeneficiaryEntityAttribute("Branch Number", "1006",
				RequestBeneficiaryEntityAttribute.TYPE.String);
		attrs.add(attr);
		
		//Resident Branch
		attr = new RequestBeneficiaryEntityAttribute("Resident Branch", residentBranch+"",
				RequestBeneficiaryEntityAttribute.TYPE.String);
		attrs.add(attr);
		
		attr = new RequestBeneficiaryEntityAttribute("Local Credit Amount Transaction Limit",0,
				RequestBeneficiaryEntityAttribute.TYPE.Integer);
		attrs.add(attr);
		attr = new RequestBeneficiaryEntityAttribute("Debit Authorization Amount",
				0, RequestBeneficiaryEntityAttribute.TYPE.Integer);
		attrs.add(attr);
		
//////
		attr = new RequestBeneficiaryEntityAttribute("Credit Authorization Amount", 0,
				RequestBeneficiaryEntityAttribute.TYPE.Integer);
		attrs.add(attr);
		attr = new RequestBeneficiaryEntityAttribute("Local Debit Amount Transaction Limit", 0,
				RequestBeneficiaryEntityAttribute.TYPE.Integer);
		attrs.add(attr);
		attr = new RequestBeneficiaryEntityAttribute("Group",
				"170~ATMCCU", RequestBeneficiaryEntityAttribute.TYPE.String);
		attrs.add(attr);
		 
		return attrs;
	}
	
	public String modifyUserRequest(OIMClient oimClient, String userKey, String userLogin, String startDate,String endDate,String leaveType) {
		String requestId = null;
		boolean processedStatus = false;
	System.out.print(startDate+" "+endDate);
		
		try {
			
		//	Date startdate = java.sql.Date.valueOf(startDate);
		//	Date enddate = java.sql.Date.valueOf(endDate);

			int count = 0;
			RequestService requestAPI = (RequestService) oimClient.getService(RequestService.class);
			List<RequestEntityAttribute> attrs = new ArrayList<RequestEntityAttribute>();
			RequestEntityAttribute attr;
			
			
			RequestEntity reqEntity = new RequestEntity();
			reqEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.User);
			reqEntity.setEntityKey(userKey);
			reqEntity.setOperation(RequestConstants.MODEL_MODIFY_OPERATION);
			
			  attr = new RequestEntityAttribute("Leave_Type",leaveType,
					RequestEntityAttribute.TYPE.String);
			attrs.add(attr);
			
			attr = new RequestEntityAttribute("Leave_To",new Date(endDate) ,
					RequestEntityAttribute.TYPE.Date);
			attrs.add(attr);
			
			attr = new RequestEntityAttribute("Leave_From",  new Date(startDate),
					RequestEntityAttribute.TYPE.Date);
			attrs.add(attr);
			reqEntity.setEntityData(attrs);
			//reqBenefEntity.setOperation(RequestConstants.MODEL_MODIFY_USER_PROFILE);
			// reqBenefEntity.setOperation(RequestConstants.MODEL_REMOVE_ROLES_OPERATION);
			System.out.println("Operation set to " + RequestConstants.MODEL_MODIFY_USER_PROFILE);

			List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
			List<RequestEntity> entities = new ArrayList<RequestEntity>();
			entities.add(reqEntity);

			
			System.out.println("1234" + " is added as beneficiary");

			RequestData reqData = new RequestData();
			reqData.setTargetEntities(entities);

			System.out.println("Beneficiaries are set to: " + beneficiaries);

			reqData.setJustification("Leave Apply<br>Leave Type: "+leaveType+"<br>Start Date: "+startDate+"<br>End Date: "+endDate);

			System.out.println("Initializing request service object");

			System.out.println("sbumitting the request");
			
			try {
				
				requestId = requestAPI.submitRequest(reqData);
				System.out.println("Request Id created: " + requestId);
				if (requestId != null) {
					System.out.println("Request created successfully , Request Id: " + requestId);
					return requestId;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return requestId;

	}
	//
	public String modifySuperPassword(OIMClient oimClient, String userKey, String userLogin, String superPasword) {
		String requestId = null;
		boolean processedStatus = false;
	
		
		try {
			
		//	Date startdate = java.sql.Date.valueOf(startDate);
		//	Date enddate = java.sql.Date.valueOf(endDate);

			int count = 0;
			RequestService requestAPI = (RequestService) oimClient.getService(RequestService.class);
			List<RequestEntityAttribute> attrs = new ArrayList<RequestEntityAttribute>();
			RequestEntityAttribute attr;
			
			
			RequestEntity reqEntity = new RequestEntity();
			reqEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.User);
			reqEntity.setEntityKey(userKey);
			reqEntity.setOperation(RequestConstants.MODEL_MODIFY_OPERATION);
			
			  attr = new RequestEntityAttribute("Fax",superPasword,
					RequestEntityAttribute.TYPE.String);
			attrs.add(attr);
			
			reqEntity.setEntityData(attrs);
			//reqBenefEntity.setOperation(RequestConstants.MODEL_MODIFY_USER_PROFILE);
			// reqBenefEntity.setOperation(RequestConstants.MODEL_REMOVE_ROLES_OPERATION);
			System.out.println("Operation set to " + RequestConstants.MODEL_MODIFY_USER_PROFILE);

			List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
			List<RequestEntity> entities = new ArrayList<RequestEntity>();
			entities.add(reqEntity);

			
			System.out.println("1234" + " is added as beneficiary");

			RequestData reqData = new RequestData();
			reqData.setTargetEntities(entities);

			System.out.println("Beneficiaries are set to: " + beneficiaries);

			reqData.setJustification("Reset Super User Password: "+superPasword);

			System.out.println("Initializing request service object");

			System.out.println("sbumitting the request");
			
			try {
				
				requestId = requestAPI.submitRequest(reqData);
				System.out.println("Request Id created: " + requestId);
				if (requestId != null) {
					System.out.println("Request created successfully , Request Id: " + requestId);
					return requestId;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return requestId;

	}
	//modifySuperUser
	public String modifySuperUserPass(OIMClient oimClient, String userKey, String userLogin, String superUsername) {
		String requestId = null;
		boolean processedStatus = false;
	System.out.print("----->>>"+superUsername+"");
		
		try {
			
		//	Date startdate = java.sql.Date.valueOf(startDate);
		//	Date enddate = java.sql.Date.valueOf(endDate);

			int count = 0;
			RequestService requestAPI = (RequestService) oimClient.getService(RequestService.class);
			List<RequestEntityAttribute> attrs = new ArrayList<RequestEntityAttribute>();
			RequestEntityAttribute attr;
			
			
			RequestEntity reqEntity = new RequestEntity();
			reqEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.User);
			reqEntity.setEntityKey(userKey);
			reqEntity.setOperation(RequestConstants.MODEL_MODIFY_OPERATION);
			
			  attr = new RequestEntityAttribute("Fax",superUsername,
					RequestEntityAttribute.TYPE.String);
			attrs.add(attr);
			
			  
			 
			
			
			reqEntity.setEntityData(attrs);
			//reqBenefEntity.setOperation(RequestConstants.MODEL_MODIFY_USER_PROFILE);
			// reqBenefEntity.setOperation(RequestConstants.MODEL_REMOVE_ROLES_OPERATION);
			System.out.println("Operation set to " + RequestConstants.MODEL_MODIFY_USER_PROFILE);

			List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
			List<RequestEntity> entities = new ArrayList<RequestEntity>();
			entities.add(reqEntity);

			
			System.out.println("1234" + " is added as beneficiary");

			RequestData reqData = new RequestData();
			reqData.setTargetEntities(entities);

			System.out.println("Beneficiaries are set to: " + beneficiaries);

			reqData.setJustification("Reset Super User Password");
			//System.out.println(reqData.getJustification());
			
			System.out.println("Reset Super User Password: "+superUsername);
			System.out.println("Initializing request service object");

			System.out.println("sbumitting the request");
			
			try {
				
				requestId = requestAPI.submitRequest(reqData);
				System.out.println("Request Id created: " + requestId);
				if (requestId != null) {
					System.out.println("Request created successfully , Request Id: " + requestId);
					return requestId;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return requestId;

	}
	//
	
	public String modifyADAccountRequest(OIMClient oimCleint, String fields,String userKey,String oiu_key) throws ParseException
	{
		String Unique_Id = null;
		String Mobile	= null;
		String Password	= null;
		String User_Id	= null;
		String Fax= null;
		String User_Principal_Name= null;
		String Pager=null;
		String IP_Phone	 = null;
		String First_Name	= null;
		String Title	= null;
		String Middle_Name	= null;
		String Department= null;
		String Last_Name=null;
		String Company=null;
		String Full_Name=null;
		String Common_Name	 = null;
		String Office	= null;
		String Organization_Name = null;
		String Country	= null;
		String Street= null;
		String Zip=null;
		String Terminal_Home_Directory	 = null;
		String Home_Phone	= null;
		String City	= null;
		String Telephone_Number	= null;
		String Terminal_Profile_Path= null;
		String RedirectionMailID=null;
		String E_Mail=null;
		String ExtensionNumber	 = null;
		String Post_Office_Box	= null;
		String State	= null;
		String Home_Directory	= null;
		String Account_Expiration_Date	 = null;
		String AccountIsLockedOut	 = null;
		String PasswordNeverExpries	= null;
		String UserMustChangePasswordAtNextLogon	= null;
		String ServiceAccount	= null;
		String PasswordNotRequired= null;
		
		
		 
		

		
		
		
		String[] field = fields.split(",");
		for(int i=0;i<field.length;i++)
		{
			if((field[i]).contains("FaxAD"))
			{
				
				Fax = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Fax---->"+Fax);
			}
			else if((field[i]).contains("PagerAD"))
			{
				
				Pager = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Pager---->"+Pager);
			}
			else if((field[i]).contains("Unique_IdAD"))
			{
				
				Unique_Id = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Unique_Id---->"+Unique_Id);
			}
			else if((field[i]).contains("MobileAD"))
			{
				
				Mobile = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Mobile---->"+Mobile);
			}
			else if((field[i]).contains("Passowrd"))
			{
				
				Password = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Password---->"+Password);
			}
			else if((field[i]).contains("User_IdAD"))
			{
				
				User_Id = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("User_Id---->"+User_Id);
			}
			
			else if((field[i]).contains("IP_PhoneAD"))
			{
				
				IP_Phone = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("IP_Phone---->"+IP_Phone);
			}
			else if((field[i]).contains("First_NameAD"))
			{
				
				First_Name = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("First_Name---->"+First_Name);
			}
			else if((field[i]).contains("TitleAD"))
			{
				
				Title = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Title---->"+Title);
			}
			else if((field[i]).contains("Middle_NameAD"))
			{
				
				Middle_Name = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Middle_Name---->"+Middle_Name);
			}
			else if((field[i]).contains("DepartmentAD"))
			{
				
				Department = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Department---->"+Department);
			}
			else if((field[i]).contains("Last_NameAD"))
			{
				
				Last_Name = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Last_Name---->"+Last_Name);
			}
			else if((field[i]).contains("CompanyAD"))
			{
				
				Company = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Company---->"+Company);
			}
			else if((field[i]).contains("User_Principal_NameAD"))
			{
				
				User_Principal_Name = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("User_Principal_Name---->"+User_Principal_Name);
			}
			else if((field[i]).contains("Full_NameAD"))
			{
				
				Full_Name = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Full_Name---->"+Full_Name);
			}
			else if((field[i]).contains("Common_NameAD"))
			{
				
				Common_Name = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Common_Name---->"+Common_Name);
			}
			else if((field[i]).contains("OfficeAD"))
			{
				
				Office = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Office---->"+Office);
			}
			else if((field[i]).contains("Organization_NameAD"))
			{
				
				Organization_Name = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Organization_Name---->"+Organization_Name);
			}
			else if((field[i]).contains("CountryAD"))
			{
				
				Country = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Country---->"+Country);
			}
			else if((field[i]).contains("StreetAD"))
			{
				
				Street = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Street---->"+Street);
			}
			else if((field[i]).contains("ZipAD"))
			{
				
				Zip = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Zip---->"+Zip);
			}
			else if((field[i]).contains("Terminal_Home_DirectoryAD"))
			{
				
				Terminal_Home_Directory = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Terminal_Home_Directory---->"+Terminal_Home_Directory);
			}
			else if((field[i]).contains("Home_PhoneAD"))
			{
				
				Home_Phone = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Home_Phone---->"+Home_Phone);
			}
			else if((field[i]).contains("CityAD"))
			{
				
				City = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("City---->"+City);
			}
			else if((field[i]).contains("Telephone_NumberAD"))
			{
				
				Telephone_Number = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Telephone_Number---->"+Telephone_Number);
			}
			else if((field[i]).contains("Terminal_Profile_PathAD"))
			{
				
				Terminal_Profile_Path = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Terminal_Profile_Path---->"+Terminal_Profile_Path);
			}
			else if((field[i]).contains("RedirectionMailID"))
			{
				
				RedirectionMailID = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("RedirectionMailID---->"+RedirectionMailID);
			}
			else if((field[i]).contains("E_MailAD"))
			{
				
				E_Mail = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("E_Mail---->"+E_Mail);
			}
			else if((field[i]).contains("ExtensionNumber"))
			{
				
				ExtensionNumber = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("ExtensionNumber---->"+ExtensionNumber);
			}
			else if((field[i]).contains("Post_Office_BoxAD"))
			{
				
				Post_Office_Box = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Post_Office_Box---->"+Post_Office_Box);
			}
			else if((field[i]).contains("StateAD"))
			{
				
				State = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("State---->"+State);
			}
			else if((field[i]).contains("Home_DirectoryAD"))
			{
				
				Home_Directory = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Home_Directory---->"+Home_Directory);
			}
			else if((field[i]).contains("AccountIsLockedOut"))
			{
				
				AccountIsLockedOut = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("AccountIsLockedOut---->"+AccountIsLockedOut);
			}
			else if((field[i]).contains("PasswordNeverExpries"))
			{
				
				PasswordNeverExpries = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("PasswordNeverExpries---->"+PasswordNeverExpries);
			}
			else if((field[i]).contains("UserMustChangePasswordAtNextLogon"))
			{
				
				UserMustChangePasswordAtNextLogon = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("UserMustChangePasswordAtNextLogon---->"+UserMustChangePasswordAtNextLogon);
			}
			else if((field[i]).contains("PasswordNotRequired"))
			{
				
				PasswordNotRequired = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("PasswordNotRequired---->"+PasswordNotRequired);
			}
			else if((field[i]).contains("ServiceAccountAD"))
			{
				
				ServiceAccount = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("ServiceAccount---->"+ServiceAccount);
			}
			else if((field[i]).contains("Account_Expiration_DateAD"))
			{
				
				Account_Expiration_Date = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("Account_Expiration_Date---->"+Account_Expiration_Date);
			}
			System.out.println("Updated Fields");
			System.out.println(field[i]);
		}
		
		String requestId = null;
	
		try {
			RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
			requestEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.ApplicationInstance); //Type of the Request
			requestEntity.setEntitySubType("ActiveDirectory"); //Name of the Application Instance
			requestEntity.setEntityKey(oiu_key); //OIU_KEY of user for Active Directory
			requestEntity.setOperation(RequestConstants.MODEL_MODIFY_ACCOUNT_OPERATION); //Request operation type.
			/**
			* here in each RequestBeneficiaryEntityAttribute object we will
			* be setting the request data
			* FIELD_1,FIELD_2,FIELD_3 are the request data set filed label name and the "value" is the corresponding value.
			*/
			List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
			RequestBeneficiaryEntityAttribute attr;
			if(Unique_Id !=null)
			{attr = new RequestBeneficiaryEntityAttribute("Unique Id", Unique_Id, RequestBeneficiaryEntityAttribute.TYPE.String);
			attrs.add(attr);}
			if(Mobile !=null)
			{attr = new RequestBeneficiaryEntityAttribute("Mobile", Mobile, RequestBeneficiaryEntityAttribute.TYPE.String);
			attrs.add(attr);}
			if(Password !=null)
			{attr = new RequestBeneficiaryEntityAttribute("Password", Password, RequestBeneficiaryEntityAttribute.TYPE.String);
			attrs.add(attr);}
			if(User_Id !=null)
			{attr = new RequestBeneficiaryEntityAttribute("User Id", User_Id, RequestBeneficiaryEntityAttribute.TYPE.String);
			attrs.add(attr);}
			if(Fax !=null)
			{attr = new RequestBeneficiaryEntityAttribute("Fax", Fax, RequestBeneficiaryEntityAttribute.TYPE.String);
			attrs.add(attr);}
			if(Pager !=null) {
			attr = new RequestBeneficiaryEntityAttribute("Pager", Pager, RequestBeneficiaryEntityAttribute.TYPE.String);
			attrs.add(attr);
			}
			if(User_Principal_Name !=null) {
				attr = new RequestBeneficiaryEntityAttribute("User Principal Name", User_Principal_Name, RequestBeneficiaryEntityAttribute.TYPE.String);
				attrs.add(attr);
				}
			if(IP_Phone !=null) {
				attr = new RequestBeneficiaryEntityAttribute("IP Phone", IP_Phone, RequestBeneficiaryEntityAttribute.TYPE.String);
				attrs.add(attr);
				}
			if(First_Name !=null) {
				attr = new RequestBeneficiaryEntityAttribute("First Name", First_Name, RequestBeneficiaryEntityAttribute.TYPE.String);
				attrs.add(attr);
				}
			if(Title !=null) {
				attr = new RequestBeneficiaryEntityAttribute("Title", Title, RequestBeneficiaryEntityAttribute.TYPE.String);
				attrs.add(attr);
				}
			if(Middle_Name !=null) {
				attr = new RequestBeneficiaryEntityAttribute("Middle Name", Middle_Name, RequestBeneficiaryEntityAttribute.TYPE.String);
				attrs.add(attr);
				}
			if(Department !=null) {
				attr = new RequestBeneficiaryEntityAttribute("Department", Department, RequestBeneficiaryEntityAttribute.TYPE.String);
				attrs.add(attr);
				}
			if(Last_Name !=null) {
				attr = new RequestBeneficiaryEntityAttribute("Last Name", Last_Name, RequestBeneficiaryEntityAttribute.TYPE.String);
				attrs.add(attr);
				}
			if(Company !=null) {
				attr = new RequestBeneficiaryEntityAttribute("Company", Company, RequestBeneficiaryEntityAttribute.TYPE.String);
				attrs.add(attr);
				}
			if(Full_Name !=null) {
				attr = new RequestBeneficiaryEntityAttribute("Full Name", Full_Name, RequestBeneficiaryEntityAttribute.TYPE.String);
				attrs.add(attr);
				}
			if(Common_Name !=null) {
				attr = new RequestBeneficiaryEntityAttribute("Common Name", Common_Name, RequestBeneficiaryEntityAttribute.TYPE.String);
				attrs.add(attr);
				}
			if(Office !=null) {
				attr = new RequestBeneficiaryEntityAttribute("Office", Office, RequestBeneficiaryEntityAttribute.TYPE.String);
				attrs.add(attr);
				}
			 if(Organization_Name !=null) {
					attr = new RequestBeneficiaryEntityAttribute("Organization Name", Organization_Name, RequestBeneficiaryEntityAttribute.TYPE.String);
					attrs.add(attr);
					}
				if(Country !=null) {
					attr = new RequestBeneficiaryEntityAttribute("Country", Country, RequestBeneficiaryEntityAttribute.TYPE.String);
					attrs.add(attr);
					}
				if(Street !=null) {
					attr = new RequestBeneficiaryEntityAttribute("Street", Street, RequestBeneficiaryEntityAttribute.TYPE.String);
					attrs.add(attr);
					}
				if(Zip !=null) {
					attr = new RequestBeneficiaryEntityAttribute("Zip", Zip, RequestBeneficiaryEntityAttribute.TYPE.String);
					attrs.add(attr);
					}
				if(Terminal_Home_Directory !=null) {
					attr = new RequestBeneficiaryEntityAttribute("Terminal Home Directory", Terminal_Home_Directory, RequestBeneficiaryEntityAttribute.TYPE.String);
					attrs.add(attr);
					}
				if(Home_Phone !=null) {
					attr = new RequestBeneficiaryEntityAttribute("Home Phone", Home_Phone, RequestBeneficiaryEntityAttribute.TYPE.String);
					attrs.add(attr);
					}
				if(City !=null) {
					attr = new RequestBeneficiaryEntityAttribute("City", City, RequestBeneficiaryEntityAttribute.TYPE.String);
					attrs.add(attr);
					}
				if(Telephone_Number !=null) {
					attr = new RequestBeneficiaryEntityAttribute("Telephone Number", Telephone_Number, RequestBeneficiaryEntityAttribute.TYPE.String);
					attrs.add(attr);
					}
				if(Terminal_Profile_Path !=null) {
					attr = new RequestBeneficiaryEntityAttribute("Terminal Profile Path", Terminal_Profile_Path, RequestBeneficiaryEntityAttribute.TYPE.String);
					attrs.add(attr);
					}
				if(RedirectionMailID !=null) {
					attr = new RequestBeneficiaryEntityAttribute("Redirection Mail Id", RedirectionMailID, RequestBeneficiaryEntityAttribute.TYPE.String);
					attrs.add(attr);
					}
				if(E_Mail !=null) {
					attr = new RequestBeneficiaryEntityAttribute("E Mail", E_Mail, RequestBeneficiaryEntityAttribute.TYPE.String);
					attrs.add(attr);
					}
				if(Post_Office_Box !=null) {
					attr = new RequestBeneficiaryEntityAttribute("Post Office Box", Post_Office_Box, RequestBeneficiaryEntityAttribute.TYPE.String);
					attrs.add(attr);
					}
				if(State !=null) {
					attr = new RequestBeneficiaryEntityAttribute("State", State, RequestBeneficiaryEntityAttribute.TYPE.String);
					attrs.add(attr);
					}
					if(Home_Directory !=null) {
						attr = new RequestBeneficiaryEntityAttribute("Homedirectory", Home_Directory, RequestBeneficiaryEntityAttribute.TYPE.String);
						attrs.add(attr);
						}
					if(AccountIsLockedOut !=null) {
						attr = new RequestBeneficiaryEntityAttribute("Account is Locked out", AccountIsLockedOut, RequestBeneficiaryEntityAttribute.TYPE.Boolean);
						attrs.add(attr);
						}
					if(PasswordNeverExpries !=null) {
						attr = new RequestBeneficiaryEntityAttribute("Password Never Expires", PasswordNeverExpries, RequestBeneficiaryEntityAttribute.TYPE.Boolean);
						attrs.add(attr);
						}
					if(UserMustChangePasswordAtNextLogon !=null) {
						attr = new RequestBeneficiaryEntityAttribute("User Must Change Password At Next Logon", UserMustChangePasswordAtNextLogon, RequestBeneficiaryEntityAttribute.TYPE.Boolean);
						attrs.add(attr);
						}
					if(ServiceAccount !=null) {
						attr = new RequestBeneficiaryEntityAttribute("Service Account", ServiceAccount, RequestBeneficiaryEntityAttribute.TYPE.Boolean);
						attrs.add(attr);
						}
					if(PasswordNotRequired !=null) {
						attr = new RequestBeneficiaryEntityAttribute("Password Not Required", PasswordNotRequired, RequestBeneficiaryEntityAttribute.TYPE.Boolean);
						attrs.add(attr);
						}
					if(Account_Expiration_Date !=null) {
						Date date=new SimpleDateFormat("dd/MM/yyyy").parse(Account_Expiration_Date);
						attr = new RequestBeneficiaryEntityAttribute("Account Expiration Date" ,date, RequestBeneficiaryEntityAttribute.TYPE.Date);
						attrs.add(attr);
						}
					
					
					
					
		//			attr = new RequestBeneficiaryEntityAttribute("Password Not Required", check, RequestBeneficiaryEntityAttribute.TYPE.Boolean);
			//		attrs.add(attr);
					
		
			//Continue setting RequestBeneficiaryEntity
			requestEntity.setEntityData(attrs);
			//Adding RequestBeneficiaryEntity to List
			List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
			entities.add(requestEntity);
			//creating new Beneficiary
			Beneficiary beneficiary = new Beneficiary();
			beneficiary.setBeneficiaryKey(userKey); //set BeneficiaryKey as User key
			System.out.println("User Key:"+userKey);
			beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY); //set the type as user
			beneficiary.setTargetEntities(entities); //set target entities as list of RequestBeneficiaryEntity
			System.out.println("Entities:"+entities.size());
			//Adding Beneficiary to List
			List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
			beneficiaries.add(beneficiary);
			//Creating new RequestData and set the Beneficiaries with List of Beneficiaries
			RequestData requestData = new RequestData();
			requestData.setBeneficiaries(beneficiaries);
			/**
			* getRequesterConnection() is a seperate method to create OIM connection.
			*/
			
			 
			RequestService requestAPI = (RequestService)oimCleint.getService(RequestService.class);
			
			requestId = requestAPI.submitRequest(requestData);
			System.out.println("requestId: "+requestId);
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RequestServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BulkBeneficiariesAddException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BulkEntitiesAddException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return requestId;
	}
	
	
	
	//////////////////////////////////////Modify Cash Portal Request////////////////////
	
	public String modifyCPAccountRequest(OIMClient oimCleint, String fields,String userKey,String oiu_key) throws ParseException
	{
		String departmentId = null;
		String userTypeId	= null;
		String regionId	= null;
		
		 
		

		
		
		
		String[] field = fields.split(",");
		for(int i=0;i<field.length;i++)
		{
			if((field[i]).contains("DepartmentidCP"))
			{
				
				departmentId = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("departmentId---->"+departmentId);
			}
			else if((field[i]).contains("UsertypeidCP"))
			{
				
				userTypeId = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("userTypeId---->"+userTypeId);
			}
			else if((field[i]).contains("RegionidCP"))
			{
				
				regionId = field[i].substring(field[i].lastIndexOf(":") + 1);
				System.out.println("regionId---->"+regionId);
			}
			
			System.out.println("Updated Fields");
			System.out.println(field[i]);
		}
		
		String requestId = null;
	
		try {
			RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
			requestEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.ApplicationInstance); //Type of the Request
			requestEntity.setEntitySubType("CP1"); //Name of the Application Instance
			requestEntity.setEntityKey(oiu_key); //OIU_KEY of user for Cash Portal
			requestEntity.setOperation(RequestConstants.MODEL_MODIFY_ACCOUNT_OPERATION); //Request operation type.
			/**
			* here in each RequestBeneficiaryEntityAttribute object we will
			* be setting the request data
			* FIELD_1,FIELD_2,FIELD_3 are the request data set filed label name and the "value" is the corresponding value.
			*/
			List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
			RequestBeneficiaryEntityAttribute attr;
			if(departmentId !=null)
			{attr = new RequestBeneficiaryEntityAttribute("Departmentid", departmentId, RequestBeneficiaryEntityAttribute.TYPE.String);
			attrs.add(attr);}
			if(userTypeId !=null)
			{attr = new RequestBeneficiaryEntityAttribute("Usertypeid", userTypeId, RequestBeneficiaryEntityAttribute.TYPE.String);
			attrs.add(attr);}
			if(regionId !=null)
			{attr = new RequestBeneficiaryEntityAttribute("Regionid", regionId, RequestBeneficiaryEntityAttribute.TYPE.String);
			attrs.add(attr);}
			
					
					
		
			//Continue setting RequestBeneficiaryEntity
			requestEntity.setEntityData(attrs);
			//Adding RequestBeneficiaryEntity to List
			List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
			entities.add(requestEntity);
			//creating new Beneficiary
			Beneficiary beneficiary = new Beneficiary();
			beneficiary.setBeneficiaryKey(userKey); //set BeneficiaryKey as User key
			System.out.println("User Key:"+userKey);
			beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY); //set the type as user
			beneficiary.setTargetEntities(entities); //set target entities as list of RequestBeneficiaryEntity
			System.out.println("Entities:"+entities.size());
			//Adding Beneficiary to List
			List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
			beneficiaries.add(beneficiary);
			//Creating new RequestData and set the Beneficiaries with List of Beneficiaries
			RequestData requestData = new RequestData();
			requestData.setBeneficiaries(beneficiaries);
			/**
			* getRequesterConnection() is a seperate method to create OIM connection.
			*/
			
			 
			RequestService requestAPI = (RequestService)oimCleint.getService(RequestService.class);
			
			requestId = requestAPI.submitRequest(requestData);
			System.out.println("requestId: "+requestId);
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RequestServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BulkBeneficiariesAddException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BulkEntitiesAddException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return requestId;
	}
	///////////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////Modify AS400 form data Request////////////////////
	
public String modifyAS400AccountRequest(OIMClient oimCleint, String fields,String userKey,String oiu_key) throws ParseException
{
String GroupProfile = null;
String InitialMenu	= null;
String LimitCapabilities	= null;
String InitialProgram	= null;
String UserClass	= null;







String[] field = fields.split(",");
for(int i=0;i<field.length;i++)
{
if((field[i]).contains("Group_ProfileAS"))
{

	GroupProfile = field[i].substring(field[i].lastIndexOf(":") + 1);
System.out.println("GroupProfile---->"+GroupProfile);
}
else if((field[i]).contains("Initial_MenuAS"))
{

	InitialMenu = field[i].substring(field[i].lastIndexOf(":") + 1);
System.out.println("InitialMenu---->"+InitialMenu);
}
else if((field[i]).contains("Limit_CapabilitiesAS"))
{

	LimitCapabilities = field[i].substring(field[i].lastIndexOf(":") + 1);
System.out.println("LimitCapabilities---->"+LimitCapabilities);
}
else if((field[i]).contains("Initial_ProgramAS"))
{

	InitialProgram = field[i].substring(field[i].lastIndexOf(":") + 1);
System.out.println("InitialProgram---->"+InitialProgram);
}
else if((field[i]).contains("User_ClassAS"))
{

	UserClass = field[i].substring(field[i].lastIndexOf(":") + 1);
System.out.println("UserClass---->"+UserClass);
}

System.out.println("Updated Fields");
System.out.println(field[i]);
}

String requestId = null;

try {
RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
requestEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.ApplicationInstance); //Type of the Request
requestEntity.setEntitySubType("AS400"); //Name of the Application Instance
requestEntity.setEntityKey(oiu_key); //OIU_KEY of user for Active Directory
requestEntity.setOperation(RequestConstants.MODEL_MODIFY_ACCOUNT_OPERATION); //Request operation type.
/**
* here in each RequestBeneficiaryEntityAttribute object we will
* be setting the request data
* FIELD_1,FIELD_2,FIELD_3 are the request data set filed label name and the "value" is the corresponding value.
*/
List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
RequestBeneficiaryEntityAttribute attr;
if(GroupProfile !=null)
{attr = new RequestBeneficiaryEntityAttribute("Group Profile", GroupProfile, RequestBeneficiaryEntityAttribute.TYPE.String);
attrs.add(attr);}
if(LimitCapabilities !=null)
{attr = new RequestBeneficiaryEntityAttribute("Limit Capabilities", LimitCapabilities, RequestBeneficiaryEntityAttribute.TYPE.String);
attrs.add(attr);}
if(InitialMenu !=null)
{attr = new RequestBeneficiaryEntityAttribute("Initial Menu", InitialMenu, RequestBeneficiaryEntityAttribute.TYPE.String);
attrs.add(attr);}
if(InitialProgram !=null)
{attr = new RequestBeneficiaryEntityAttribute("Initial Program", InitialProgram, RequestBeneficiaryEntityAttribute.TYPE.String);
attrs.add(attr);}
if(UserClass !=null)
{attr = new RequestBeneficiaryEntityAttribute("User Class", UserClass, RequestBeneficiaryEntityAttribute.TYPE.String);
attrs.add(attr);}




//Continue setting RequestBeneficiaryEntity
requestEntity.setEntityData(attrs);
//Adding RequestBeneficiaryEntity to List
List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
entities.add(requestEntity);
//creating new Beneficiary
Beneficiary beneficiary = new Beneficiary();
beneficiary.setBeneficiaryKey(userKey); //set BeneficiaryKey as User key
System.out.println("User Key:"+userKey);
beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY); //set the type as user
beneficiary.setTargetEntities(entities); //set target entities as list of RequestBeneficiaryEntity
System.out.println("Entities:"+entities.size());
//Adding Beneficiary to List
List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
beneficiaries.add(beneficiary);
//Creating new RequestData and set the Beneficiaries with List of Beneficiaries
RequestData requestData = new RequestData();
requestData.setBeneficiaries(beneficiaries);
/**
* getRequesterConnection() is a seperate method to create OIM connection.
*/


RequestService requestAPI = (RequestService)oimCleint.getService(RequestService.class);

requestId = requestAPI.submitRequest(requestData);
System.out.println("requestId: "+requestId);
} catch (InvalidRequestException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (InvalidRequestDataException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (RequestServiceException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (BulkBeneficiariesAddException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (BulkEntitiesAddException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}

return requestId;
}
///////////////////////////////////////////////////////////////////////////////////


//////////////////////////////////////Modify Equation Group form data Request////////////////////

public String modifyEquationGroupAccountRequest(OIMClient oimCleint, String fields,String userKey,String oiu_key,String UD_EQGROUP_AUTH_AMOUNT_FOR_LOC
		,String UD_EQGROUP_INTER_BRANCH_DEBIT,String UD_EQGROUP_INTER_BRANCH_CREDIT,String UD_EQGROUP_USER_TYPE
		,String UD_EQGROUP_AUTH_AMOUNT_FOR_L90,String UD_EQGROUP_AUTH_SPECIFIED_BRAN,String UD_EQGROUP_ALL_BRANCHES,
		String UD_EQGROUP_AUTH_AMOUNT_FOR_INT,String UD_EQGROUP_AUTH_AMOUNT_FOR_I52,String UD_EQGROUP_LIMIT_FOR_LOCAL_CRD,String UD_EQGROUP_SPECIFIED_BRANCHES
		,String UD_EQGROUP_AUTH_ALL_BRANCHES,String UD_EQGROUP_LIMIT_FOR_LOCAL_DEB) throws ParseException
{
String UD_EQGROUP_USER_GROUP	= null;
String UD_EQGROUP_GROUP_TYPE	= null;
String UD_EQGROUP_LIMIT	= null;







String[] field = fields.split(",");
for(int i=0;i<field.length;i++)
{




 if((field[i]).contains("User_GroupEQG"))
{

UD_EQGROUP_USER_GROUP = field[i].substring(field[i].lastIndexOf(":") + 1);
System.out.println("UserClass---->"+UD_EQGROUP_USER_GROUP);
}
 else if((field[i]).contains("Group_TypeEQG"))
 {

	 UD_EQGROUP_GROUP_TYPE = field[i].substring(field[i].lastIndexOf(":") + 1);
 System.out.println("UserClass---->"+UD_EQGROUP_GROUP_TYPE);
 }
 else if((field[i]).contains("LimitEQG"))
 {

	 UD_EQGROUP_LIMIT = field[i].substring(field[i].lastIndexOf(":") + 1);
 System.out.println("UserClass---->"+UD_EQGROUP_LIMIT);
 }

System.out.println("Updated Fields");
System.out.println(field[i]);
}

String requestId = null;

try {
RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
requestEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.ApplicationInstance); //Type of the Request
requestEntity.setEntitySubType("EQGroup"); //Name of the Application Instance
requestEntity.setEntityKey(oiu_key); //OIU_KEY of user for Active Directory
requestEntity.setOperation(RequestConstants.MODEL_MODIFY_ACCOUNT_OPERATION); //Request operation type.
/**
* here in each RequestBeneficiaryEntityAttribute object we will
* be setting the request data
* FIELD_1,FIELD_2,FIELD_3 are the request data set filed label name and the "value" is the corresponding value.
*/
List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
RequestBeneficiaryEntityAttribute attr;

if(!UD_EQGROUP_INTER_BRANCH_DEBIT.isEmpty())
{
	
	attr = new RequestBeneficiaryEntityAttribute("Inter Branch Debit", Integer.parseInt(UD_EQGROUP_INTER_BRANCH_DEBIT.toString().trim()), RequestBeneficiaryEntityAttribute.TYPE.Integer);	
	attrs.add(attr);
}
if(!UD_EQGROUP_INTER_BRANCH_CREDIT.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("Inter Branch Credit",Integer.parseInt( UD_EQGROUP_INTER_BRANCH_CREDIT.toString().trim()), RequestBeneficiaryEntityAttribute.TYPE.Integer);	
	attrs.add(attr);
}
if(!UD_EQGROUP_USER_TYPE.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("User Type", UD_EQGROUP_USER_TYPE, RequestBeneficiaryEntityAttribute.TYPE.String);	
	attrs.add(attr);
}
if(!UD_EQGROUP_AUTH_AMOUNT_FOR_L90.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("Auth Amount for Local Credit",Integer.parseInt( UD_EQGROUP_AUTH_AMOUNT_FOR_L90.toString().trim()), RequestBeneficiaryEntityAttribute.TYPE.Integer);	
	attrs.add(attr);
}
if(!UD_EQGROUP_AUTH_SPECIFIED_BRAN.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("Auth Specified Branch", UD_EQGROUP_AUTH_SPECIFIED_BRAN, RequestBeneficiaryEntityAttribute.TYPE.String);	
	attrs.add(attr);
}
/*	if(!UD_EQGROUP_GROUP_DESCRIPTION.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("Group Description", UD_EQGROUP_GROUP_DESCRIPTION, RequestBeneficiaryEntityAttribute.TYPE.String);	
	attrs.add(attr);
}
*/
if(!UD_EQGROUP_ALL_BRANCHES.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("All Branches", UD_EQGROUP_ALL_BRANCHES, RequestBeneficiaryEntityAttribute.TYPE.String);	
	attrs.add(attr);
}
if(!UD_EQGROUP_AUTH_AMOUNT_FOR_INT.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("Auth Amount for Inter Branch Credit",Integer.parseInt(UD_EQGROUP_AUTH_AMOUNT_FOR_INT.toString().trim()), RequestBeneficiaryEntityAttribute.TYPE.Integer);	
	attrs.add(attr);
} 
if(!UD_EQGROUP_AUTH_AMOUNT_FOR_I52.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("Auth Amount for Inter Branch Debit",Integer.parseInt( UD_EQGROUP_AUTH_AMOUNT_FOR_I52.toString().trim()), RequestBeneficiaryEntityAttribute.TYPE.Integer);	
	attrs.add(attr);
}
if(!UD_EQGROUP_USER_GROUP.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("User Group", UD_EQGROUP_USER_GROUP, RequestBeneficiaryEntityAttribute.TYPE.String);	
	attrs.add(attr);
}
if(!UD_EQGROUP_SPECIFIED_BRANCHES.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("Specified Branches", UD_EQGROUP_SPECIFIED_BRANCHES, RequestBeneficiaryEntityAttribute.TYPE.String);	
	attrs.add(attr);
}
if(!UD_EQGROUP_LIMIT_FOR_LOCAL_CRD.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("Limit for Local Crdeit",Integer.parseInt( UD_EQGROUP_LIMIT_FOR_LOCAL_CRD.toString().trim()), RequestBeneficiaryEntityAttribute.TYPE.Integer);	
	attrs.add(attr);
}
if(!UD_EQGROUP_LIMIT_FOR_LOCAL_CRD.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("Limit for Local Debit",Integer.parseInt( UD_EQGROUP_LIMIT_FOR_LOCAL_DEB.toString().trim()), RequestBeneficiaryEntityAttribute.TYPE.Integer);	
	attrs.add(attr);
}
//
if(!UD_EQGROUP_AUTH_ALL_BRANCHES.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("Auth All Branches", UD_EQGROUP_AUTH_ALL_BRANCHES, RequestBeneficiaryEntityAttribute.TYPE.String);	
	attrs.add(attr);
}
if(!UD_EQGROUP_GROUP_TYPE.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("Group Type", UD_EQGROUP_GROUP_TYPE, RequestBeneficiaryEntityAttribute.TYPE.String);	
	attrs.add(attr);
}
/*	if(!UD_EQGROUP_JUSTIFICATION.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("Justification", UD_EQGROUP_JUSTIFICATION, RequestBeneficiaryEntityAttribute.TYPE.String);	
	attrs.add(attr);
}*/
if(!UD_EQGROUP_LIMIT.isEmpty())
{
	attr = new RequestBeneficiaryEntityAttribute("Limit", UD_EQGROUP_LIMIT, RequestBeneficiaryEntityAttribute.TYPE.String);	
	attrs.add(attr);
}



//Continue setting RequestBeneficiaryEntity
requestEntity.setEntityData(attrs);
//Adding RequestBeneficiaryEntity to List
List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
entities.add(requestEntity);
//creating new Beneficiary
Beneficiary beneficiary = new Beneficiary();
beneficiary.setBeneficiaryKey(userKey); //set BeneficiaryKey as User key
System.out.println("User Key:"+userKey);
beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY); //set the type as user
beneficiary.setTargetEntities(entities); //set target entities as list of RequestBeneficiaryEntity
System.out.println("Entities:"+entities.size());
//Adding Beneficiary to List
List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
beneficiaries.add(beneficiary);
//Creating new RequestData and set the Beneficiaries with List of Beneficiaries
RequestData requestData = new RequestData();
requestData.setBeneficiaries(beneficiaries);
/**
* getRequesterConnection() is a seperate method to create OIM connection.
*/


RequestService requestAPI = (RequestService)oimCleint.getService(RequestService.class);

requestId = requestAPI.submitRequest(requestData);
System.out.println("requestId: "+requestId);
} catch (InvalidRequestException e) {
//TODO Auto-generated catch block
e.printStackTrace();
} catch (InvalidRequestDataException e) {
//TODO Auto-generated catch block
e.printStackTrace();
} catch (RequestServiceException e) {
//TODO Auto-generated catch block
e.printStackTrace();
} catch (BulkBeneficiariesAddException e) {
//TODO Auto-generated catch block
e.printStackTrace();
} catch (BulkEntitiesAddException e) {
//TODO Auto-generated catch block
e.printStackTrace();
}

return requestId;
}
///////////////////////////////////////////////////////////////////////////////////



//////////////////////////////////////Modify Equation form data Request////////////////////

public String modifyEquationAccountRequest(OIMClient oimCleint, String fields,String userKey,String oiu_key) throws ParseException
{
String UserClass	= null;







String[] field = fields.split(",");
for(int i=0;i<field.length;i++)
{




if((field[i]).contains("User_ClassEQ"))
{

UserClass = field[i].substring(field[i].lastIndexOf(":") + 1);
System.out.println("UserClass---->"+UserClass);
}

System.out.println("Updated Fields");
System.out.println(field[i]);
}

String requestId = null;

try {
RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
requestEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.ApplicationInstance); //Type of the Request
requestEntity.setEntitySubType("Equation"); //Name of the Application Instance
requestEntity.setEntityKey(oiu_key); //OIU_KEY of user for Active Directory
requestEntity.setOperation(RequestConstants.MODEL_MODIFY_ACCOUNT_OPERATION); //Request operation type.
/**
* here in each RequestBeneficiaryEntityAttribute object we will
* be setting the request data
* FIELD_1,FIELD_2,FIELD_3 are the request data set filed label name and the "value" is the corresponding value.
*/
List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
RequestBeneficiaryEntityAttribute attr;
if(UserClass !=null)
{attr = new RequestBeneficiaryEntityAttribute("User Class", UserClass, RequestBeneficiaryEntityAttribute.TYPE.String);
attrs.add(attr);}




//Continue setting RequestBeneficiaryEntity
requestEntity.setEntityData(attrs);
//Adding RequestBeneficiaryEntity to List
List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
entities.add(requestEntity);
//creating new Beneficiary
Beneficiary beneficiary = new Beneficiary();
beneficiary.setBeneficiaryKey(userKey); //set BeneficiaryKey as User key
System.out.println("User Key:"+userKey);
beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY); //set the type as user
beneficiary.setTargetEntities(entities); //set target entities as list of RequestBeneficiaryEntity
System.out.println("Entities:"+entities.size());
//Adding Beneficiary to List
List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
beneficiaries.add(beneficiary);
//Creating new RequestData and set the Beneficiaries with List of Beneficiaries
RequestData requestData = new RequestData();
requestData.setBeneficiaries(beneficiaries);
/**
* getRequesterConnection() is a seperate method to create OIM connection.
*/


RequestService requestAPI = (RequestService)oimCleint.getService(RequestService.class);

requestId = requestAPI.submitRequest(requestData);
System.out.println("requestId: "+requestId);
} catch (InvalidRequestException e) {
//TODO Auto-generated catch block
e.printStackTrace();
} catch (InvalidRequestDataException e) {
//TODO Auto-generated catch block
e.printStackTrace();
} catch (RequestServiceException e) {
//TODO Auto-generated catch block
e.printStackTrace();
} catch (BulkBeneficiariesAddException e) {
//TODO Auto-generated catch block
e.printStackTrace();
} catch (BulkEntitiesAddException e) {
//TODO Auto-generated catch block
e.printStackTrace();
}

return requestId;
}
///////////////////////////////////////////////////////////////////////////////////


public String modifyAS400ChildDataRequest(OIMClient oimCleint,String userKey,String oiu_key) throws ParseException
{








String requestId = null;

try {
RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
requestEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.ApplicationInstance); //Type of the Request
requestEntity.setEntitySubType("AS400"); //Name of the Application Instance
requestEntity.setEntityKey(oiu_key); //OIU_KEY of user for AS400
requestEntity.setOperation(RequestConstants.MODEL_MODIFY_ACCOUNT_OPERATION); //Request operation type.
/**
* here in each RequestBeneficiaryEntityAttribute object we will
* be setting the request data
* FIELD_1,FIELD_2,FIELD_3 are the request data set filed label name and the "value" is the corresponding value.
*/
List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
RequestBeneficiaryEntityAttribute attr;

attr = new RequestBeneficiaryEntityAttribute("Service Account", false, RequestBeneficiaryEntityAttribute.TYPE.Boolean);
attrs.add(attr);





//Continue setting RequestBeneficiaryEntity
requestEntity.setEntityData(attrs);
//Adding RequestBeneficiaryEntity to List
List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
entities.add(requestEntity);
//creating new Beneficiary
Beneficiary beneficiary = new Beneficiary();
beneficiary.setBeneficiaryKey(userKey); //set BeneficiaryKey as User key
System.out.println("User Key:"+userKey);
beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY); //set the type as user
beneficiary.setTargetEntities(entities); //set target entities as list of RequestBeneficiaryEntity
System.out.println("Entities:"+entities.size());
//Adding Beneficiary to List
List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
beneficiaries.add(beneficiary);
//Creating new RequestData and set the Beneficiaries with List of Beneficiaries
RequestData requestData = new RequestData();
requestData.setBeneficiaries(beneficiaries);
/**
* getRequesterConnection() is a seperate method to create OIM connection.
*/


RequestService requestAPI = (RequestService)oimCleint.getService(RequestService.class);

requestId = requestAPI.submitRequest(requestData);
System.out.println("requestId: "+requestId);
} catch (InvalidRequestException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (InvalidRequestDataException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (RequestServiceException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (BulkBeneficiariesAddException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (BulkEntitiesAddException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}

return requestId;
}
//////////////////////////////////////Equation Group Child/////////////////////////////////////////////

public String modifyEquationGroupChildDataRequest(OIMClient oimCleint,String userKey,String oiu_key) throws ParseException
{








String requestId = null;

try {
RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
requestEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.ApplicationInstance); //Type of the Request
requestEntity.setEntitySubType("EQGroup"); //Name of the Application Instance
requestEntity.setEntityKey(oiu_key); //OIU_KEY of user for AS400
requestEntity.setOperation(RequestConstants.MODEL_MODIFY_ACCOUNT_OPERATION); //Request operation type.
/**
* here in each RequestBeneficiaryEntityAttribute object we will
* be setting the request data
* FIELD_1,FIELD_2,FIELD_3 are the request data set filed label name and the "value" is the corresponding value.
*/
List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
RequestBeneficiaryEntityAttribute attr;

attr = new RequestBeneficiaryEntityAttribute("Service Account", false, RequestBeneficiaryEntityAttribute.TYPE.Boolean);
attrs.add(attr);





//Continue setting RequestBeneficiaryEntity
requestEntity.setEntityData(attrs);
//Adding RequestBeneficiaryEntity to List
List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
entities.add(requestEntity);
//creating new Beneficiary
Beneficiary beneficiary = new Beneficiary();
beneficiary.setBeneficiaryKey(userKey); //set BeneficiaryKey as User key
System.out.println("User Key:"+userKey);
beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY); //set the type as user
beneficiary.setTargetEntities(entities); //set target entities as list of RequestBeneficiaryEntity
System.out.println("Entities:"+entities.size());
//Adding Beneficiary to List
List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
beneficiaries.add(beneficiary);
//Creating new RequestData and set the Beneficiaries with List of Beneficiaries
RequestData requestData = new RequestData();
requestData.setBeneficiaries(beneficiaries);
/**
* getRequesterConnection() is a seperate method to create OIM connection.
*/


RequestService requestAPI = (RequestService)oimCleint.getService(RequestService.class);

requestId = requestAPI.submitRequest(requestData);
System.out.println("requestId: "+requestId);
} catch (InvalidRequestException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (InvalidRequestDataException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (RequestServiceException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (BulkBeneficiariesAddException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (BulkEntitiesAddException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}

return requestId;
}
///////////////////////////////////////////////////////////////////////////////////


public String modifyEquationChildDataRequest(OIMClient oimCleint,String userKey,String oiu_key) throws ParseException
{








String requestId = null;

try {
RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
requestEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.ApplicationInstance); //Type of the Request
requestEntity.setEntitySubType("Equation"); //Name of the Application Instance
requestEntity.setEntityKey(oiu_key); //OIU_KEY of user for AS400
requestEntity.setOperation(RequestConstants.MODEL_MODIFY_ACCOUNT_OPERATION); //Request operation type.
/**
* here in each RequestBeneficiaryEntityAttribute object we will
* be setting the request data
* FIELD_1,FIELD_2,FIELD_3 are the request data set filed label name and the "value" is the corresponding value.
*/
List<RequestBeneficiaryEntityAttribute> attrs = new ArrayList<RequestBeneficiaryEntityAttribute>();
RequestBeneficiaryEntityAttribute attr;

attr = new RequestBeneficiaryEntityAttribute("Service Account", false, RequestBeneficiaryEntityAttribute.TYPE.Boolean);
attrs.add(attr);





//Continue setting RequestBeneficiaryEntity
requestEntity.setEntityData(attrs);
//Adding RequestBeneficiaryEntity to List
List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
entities.add(requestEntity);
//creating new Beneficiary
Beneficiary beneficiary = new Beneficiary();
beneficiary.setBeneficiaryKey(userKey); //set BeneficiaryKey as User key
System.out.println("User Key:"+userKey);
beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY); //set the type as user
beneficiary.setTargetEntities(entities); //set target entities as list of RequestBeneficiaryEntity
System.out.println("Entities:"+entities.size());
//Adding Beneficiary to List
List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
beneficiaries.add(beneficiary);
//Creating new RequestData and set the Beneficiaries with List of Beneficiaries
RequestData requestData = new RequestData();
requestData.setBeneficiaries(beneficiaries);
/**
* getRequesterConnection() is a seperate method to create OIM connection.
*/


RequestService requestAPI = (RequestService)oimCleint.getService(RequestService.class);

requestId = requestAPI.submitRequest(requestData);
System.out.println("requestId: "+requestId);
} catch (InvalidRequestException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (InvalidRequestDataException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (RequestServiceException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (BulkBeneficiariesAddException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (BulkEntitiesAddException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}

return requestId;
}
///////////////////////////////////////////////////////////////////////////////////






	//Enable User Request
	
	public String enableUser(OIMClient oimClient, String userKey, String userLogin, String comments) {
		String requestId = null;
		boolean processedStatus = false;
	
		
		try {
			
		//	Date startdate = java.sql.Date.valueOf(startDate);
		//	Date enddate = java.sql.Date.valueOf(endDate);

			int count = 0;
			RequestService requestAPI = (RequestService) oimClient.getService(RequestService.class);
			List<RequestEntityAttribute> attrs = new ArrayList<RequestEntityAttribute>();
			RequestEntityAttribute attr;
			
			
			RequestEntity reqEntity = new RequestEntity();
			reqEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.User);
			reqEntity.setEntityKey(userKey);
			reqEntity.setOperation(RequestConstants.MODEL_ENABLE_OPERATION);
			
			
			
			
			reqEntity.setEntityData(attrs);
			//reqBenefEntity.setOperation(RequestConstants.MODEL_MODIFY_USER_PROFILE);
			// reqBenefEntity.setOperation(RequestConstants.MODEL_REMOVE_ROLES_OPERATION);
			System.out.println("Operation set to " + RequestConstants.MODEL_ENABLE_OPERATION);

			List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
			List<RequestEntity> entities = new ArrayList<RequestEntity>();
			entities.add(reqEntity);

			
			System.out.println("1234" + " is added as beneficiary");

			RequestData reqData = new RequestData();
			reqData.setTargetEntities(entities);

			System.out.println("Beneficiaries are set to: " + beneficiaries);

			reqData.setJustification("Reason: "+comments);

			System.out.println("Initializing request service object");

			System.out.println("sbumitting the request");
			
			try {
				
				requestId = requestAPI.submitRequest(reqData);
				System.out.println("Request Id created: " + requestId);
				if (requestId != null) {
					System.out.println("Request created successfully , Request Id: " + requestId);
					return requestId.concat(" Request has been Submitted!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				requestId = e.getMessage();
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			requestId = e.getMessage();
		}
		return requestId;

	}

//Disable User Request	

	public String disableUser(OIMClient oimClient, String userKey, String userLogin, String comments) {
		String requestId = null;
		boolean processedStatus = false;
	
		
		try {
			
		//	Date startdate = java.sql.Date.valueOf(startDate);
		//	Date enddate = java.sql.Date.valueOf(endDate);

			int count = 0;
			RequestService requestAPI = (RequestService) oimClient.getService(RequestService.class);
			List<RequestEntityAttribute> attrs = new ArrayList<RequestEntityAttribute>();
			RequestEntityAttribute attr;
			
			
			RequestEntity reqEntity = new RequestEntity();
			reqEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.User);
			reqEntity.setEntityKey(userKey);
			reqEntity.setOperation(RequestConstants.MODEL_DISABLE_OPERATION);
			
			
			
			
			reqEntity.setEntityData(attrs);
			//reqBenefEntity.setOperation(RequestConstants.MODEL_MODIFY_USER_PROFILE);
			// reqBenefEntity.setOperation(RequestConstants.MODEL_REMOVE_ROLES_OPERATION);
			System.out.println("Operation set to " + RequestConstants.MODEL_DISABLE_OPERATION);

			List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
			List<RequestEntity> entities = new ArrayList<RequestEntity>();
			entities.add(reqEntity);

			
		
			RequestData reqData = new RequestData();
			reqData.setTargetEntities(entities);

			System.out.println("Beneficiaries are set to: " + beneficiaries);

			reqData.setJustification("Reason: "+comments);

			System.out.println("Initializing request service object");

			System.out.println("sbumitting the request");
			
			try {
				
				requestId = requestAPI.submitRequest(reqData);
				System.out.println("Request Id created: " + requestId);
				if (requestId != null) {
					System.out.println("Request created successfully , Request Id: " + requestId);
					return requestId="User has been Disabled Successfull";
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				requestId=e.getMessage();
				System.out.println("requestId:"+requestId);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			requestId=e.getMessage();
		
			System.out.println("requestId:"+requestId);
		}
		return requestId;

	}

	
}