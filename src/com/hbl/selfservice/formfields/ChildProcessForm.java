package com.hbl.selfservice.formfields;


import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.hbl.equation.limits.DatabaseConnection;
import com.hbl.selfservice.LoginToOIM;
import com.hbl.selfservice.OIMUtils;

//import oracle.core.ojdl.logging.ODLLevel;
//import oracle.core.ojdl.logging.ODLLogger;
import oracle.iam.identity.exception.NoSuchUserException;
import oracle.iam.identity.exception.UserLookupException;
import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.api.UserManagerConstants;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.entitymgr.vo.SearchCriteria;
import oracle.iam.provisioning.api.ProvisioningConstants;
import oracle.iam.provisioning.api.ProvisioningService;
import oracle.iam.provisioning.exception.AccountNotFoundException;
import oracle.iam.provisioning.exception.GenericProvisioningException;
import oracle.iam.provisioning.exception.UserNotFoundException;
import oracle.iam.provisioning.vo.Account;
import oracle.iam.provisioning.vo.AccountData;
import oracle.iam.provisioning.vo.ChildTableRecord;
import oracle.iam.provisioning.vo.ChildTableRecord.ACTION;
 
/**
 * Update a child process form for a speicifc resource account.
 * @author rayedchan
 * @version 1.0
 */
public class ChildProcessForm
{
    // Environment specfic constants
    public static final String OIM_HOSTNAME = "localhost";
    public static final String OIM_PORT = "14000";
    public static final String OIM_PROVIDER_URL ="t3://" + OIM_HOSTNAME + ":" + OIM_PORT;
    public static final String OIM_USERNAME = "xelsysadm";
    public static final String OIM_PASSWORD = "Password1";
    public static final String OIM_CLIENT_HOME ="/home/oracle/jdeveloper/mywork/OracleIdentityManager/Resources/oimclient";
    public static final String AUTHWL_PATH =OIM_CLIENT_HOME + "/conf/authwl.conf";
     
    // Constants for testing
  /*  public static final String USER_LOGIN = "Almir";
    public static final String RESOURCE_OBJECT_NAME = "EQUATION";
    public static final String CHILD_PROCESS_FORM_NAME = "UD_KFILPK26";
    public static final String CHILD_ATTRIBUTE_NAME1 = "UD_KFILPK34_BRANCH_NUMBER";
    public static final String CHILD_ATTRIBUTE_NAME2 = "UD_KFILPK26_USER_GROUP";
    //////////////////
    */
   
    //////////////////
    /*public static final String UD_KFILPK34_PROCESS_INTER_BRAN="UD_KFILPK34_PROCESS_INTER_BRAN";
    public static final String UD_KFILPK34_GROUP="UD_KFILPK34_GROUP";          
    public static final String UD_KFILPK34_INTER_BRANCH_DEBIT="UD_KFILPK34_INTER_BRANCH_DEBIT";
    public static final String UD_KFILPK34_RESIDENT_BRANCH="UD_KFILPK34_RESIDENT_BRANCH"; 
    public static final String UD_KFILPK34_INTER_BRANCH_CREDI="UD_KFILPK34_INTER_BRANCH_CREDI";
    public static final String UD_KFILPK34_BRANCH_NUMBER="UD_KFILPK34_BRANCH_NUMBER";
    public static final String UD_KFILPK34_LOCAL_CREDIT_AMOUN="UD_KFILPK34_LOCAL_CREDIT_AMOUN";
    public static final String UD_KFILPK34_LOCAL_DEBIT_AMOUNT="UD_KFILPK34_LOCAL_DEBIT_AMOUNT";
    public static final String UD_KFILPK34_DEBIT_AUTHORIZATIO="UD_KFILPK34_DEBIT_AUTHORIZATIO";
    public static final String UD_KFILPK34_CREDIT_AUTHORIZATI="UD_KFILPK34_CREDIT_AUTHORIZATI";
    *///public static final ODLLogger logger = ODLLogger.getODLLogger(UpdateResoureProcessForm.class.getName());
    public static ProvisioningService provOps = null;
    public static UserManager usrMgrOps = null;
     
    public static void main (String[] args) throws Exception 
    
    
    {
    	String childDataTable = XMLParser(convertStringToXMLDocument(getChildDetailXML("wwww")));
    }
   /* 	
    	
        String dataArray[] = new String[10];
    	OIMClient oimClient = null;
 
        try
        {
        	LoginToOIM login= new LoginToOIM();
        	oimClient = login.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
 
        	/* // Set system properties required for OIMClient
            System.setProperty("java.security.auth.login.config", AUTHWL_PATH);
            System.setProperty("APPSERVER_TYPE", "wls");
            // Create an instance of OIMClient with OIM environment information
            Hashtable env = new Hashtable();
            env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL,"weblogic.jndi.WLInitialContextFactory");
            env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, OIM_PROVIDER_URL);
            oimClient = new OIMClient(env);
 
            // Login to OIM with the approriate credentials
            oimClient.login(OIM_USERNAME, OIM_PASSWORD.toCharArray());
 */            
            // Get OIM services
    /*        provOps = oimClient.getService(ProvisioningService.class);
            usrMgrOps = oimClient.getService(UserManager.class);
             
            // Get usr_key
            String userKey = getUserKeyByUserLogin(USER_LOGIN);
             
            // Get user's resource account
            Account resourceAccount = getUserResourceAccount(userKey, RESOURCE_OBJECT_NAME);
             
            // Get account's child data
            Map<String, ArrayList<ChildTableRecord>> childData = resourceAccount.getAccountData().getChildData();
           // logger.log(ODLLevel.NOTIFICATION, "Resource Account Child Data: {0}", new Object[]{childData});
            printResourceAccountChildData(childData);
             
            // Staging objects
            HashMap<String, Object> modParentData = new HashMap<String, Object>();
            Map<String, ArrayList<ChildTableRecord>> modChildData = new HashMap<String, ArrayList<ChildTableRecord>>();
            ArrayList<ChildTableRecord> modRecords = new ArrayList<ChildTableRecord>(); 
            
            
            HashMap<String,Object> addRecordData = new HashMap<String,Object>();
             //////////////////////////////DB CONNECTION///////////////////////////
            
    	*/	/*
    			DatabaseConnection db = new DatabaseConnection();
    			Connection con = db.connectMSCDB();
    			PreparedStatement stmt;
    			ResultSet rs;
    			
    			
    			try {
    				stmt = con.prepareStatement("Select UD_KFILPK34_PROCESS_INTER_BRAN,UD_KFILPK34_GROUP,\r\n" + 
    						"UD_KFILPK34_INTER_BRANCH_DEBIT,UD_KFILPK34_RESIDENT_BRANCH,\r\n" + 
    						"UD_KFILPK34_INTER_BRANCH_CREDI,\r\n" + 
    						"UD_KFILPK34_BRANCH_NUMBER,\r\n" + 
    						"UD_KFILPK34_LOCAL_CREDIT_AMOUN,\r\n" + 
    						"UD_KFILPK34_LOCAL_DEBIT_AMOUNT,\r\n" + 
    						"UD_KFILPK34_DEBIT_AUTHORIZATIO,\r\n" + 
    						"UD_KFILPK34_CREDIT_AUTHORIZATI\r\n" + 
    						"\r\n" + 
    						"from EQU_REQUEST_DATA  where Group_NAME =?");
    				
    				stmt.setString(1,"170~ATMCCU"); 
    				int i=1;
    				rs = stmt.executeQuery();         
    				while (rs.next()) { 
    					
    					addRecordData.put("UD_KFILPK34_PROCESS_INTER_BRAN", rs.getString(1));
    					addRecordData.put("UD_KFILPK34_GROUP", rs.getString(2));
    					addRecordData.put("UD_KFILPK34_INTER_BRANCH_DEBIT", rs.getString(3));
    					addRecordData.put("UD_KFILPK34_RESIDENT_BRANCH", rs.getString(4));
    					addRecordData.put("UD_KFILPK34_INTER_BRANCH_CREDI", rs.getString(5));
    					addRecordData.put("UD_KFILPK34_BRANCH_NUMBER", rs.getString(6));
    					addRecordData.put("UD_KFILPK34_LOCAL_CREDIT_AMOUN", rs.getString(7));
    					addRecordData.put("UD_KFILPK34_LOCAL_DEBIT_AMOUNT", rs.getString(8));
    					addRecordData.put("UD_KFILPK34_DEBIT_AUTHORIZATIO", rs.getString(9));
    					addRecordData.put("UD_KFILPK34_CREDIT_AUTHORIZATI", rs.getString(10));
    					System.out.println( "------------------------------"+(++i));
    					ChildTableRecord addRecord = new ChildTableRecord();
    		            addRecord.setAction(ACTION.Add);
    		            addRecord.setChildData(addRecordData);
    		            modRecords.add(addRecord);
    		            modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
    		            modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData); // Modify resource account
    		            modChildData.clear();
    		            addRecordData.clear();
    		            modParentData.clear();
    		            modRecords.clear();
    		            System.out.println(Arrays.asList(userKey));
    		            System.out.println(Arrays.asList(resourceAccount));
    		            System.out.println(Arrays.asList(modChildData)); // method 1
    		            System.out.println(Arrays.asList(modParentData));
    		            break;
    				 }
    				rs.close();                       
    				stmt.close();        
    				
    				
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}  
    			
    			
    		
    		
            
            
            */
          /*  
            ///////////////////////////////////////////////////////////////////////
            // Stage Add Child Record
            //-----HashMap<String,Object> addRecordData = new HashMap<String,Object>();
            addRecordData.put(CHILD_ATTRIBUTE_NAME2, "170~ATMCCU");
            ChildTableRecord addRecord = new ChildTableRecord();
            addRecord.setAction(ACTION.Add);
            addRecord.setChildData(addRecordData);
            modRecords.add(addRecord);
             
            // Stage Modify Child Record
            //HashMap<String,Object> modifyRecordData = new HashMap<String,Object>();
            //modifyRecordData.put(CHILD_ATTRIBUTE_NAME, "Engineer II");
            //ChildTableRecord modifyRecord = new ChildTableRecord();
            //modifyRecord.setChildData(modifyRecordData);
            //modifyRecord.setAction(ACTION.Modify);
            //modifyRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
            //modRecords.add(modifyRecord);
             
            // Stage Remove Child Record
            //HashMap<String,Object> removeRecordData = new HashMap<String,Object>();
            //ChildTableRecord removeRecord = new ChildTableRecord();
            //removeRecord.setChildData(removeRecordData);
            //removeRecord.setAction(ACTION.Delete);
            //removeRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
            //modRecords.add(removeRecord);
             
                     
           modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
            modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData); // Modify resource account                
        }
 
      //  catch (Exception ex) {logger.log(ODLLevel.ERROR, "", ex);} 
 
        finally
        {
            // Logout user from OIMClient
            if (oimClient != null) { oimClient.logout();} 
        }
    }
     
    /**
     * Get the row key of a child record
     * @param childFormName         Name of the Child Form to inspect
     * @param childAttributeName    Name of the child attrribute used as a criteria
     * @param childAttributeValue   Value of the childAttributeName to search agaimst
     * @param resourceAccount       The user's resource account
     * @return  Child Record Key
     * @throws Exception
     */
    public static String getChildRecordKeyByValue(String childFormName, String childAttributeName, String childAttributeValue, Account resourceAccount) throws Exception 
    {
        Map<String, ArrayList<ChildTableRecord>> childData = resourceAccount.getAccountData().getChildData();
        //logger.log(ODLLevel.NOTIFICATION, "Resource Account Child Data: {0}", new Object[]{childData});
         
        // Child Data Iterator 
        Iterator iter = childData.entrySet().iterator();
         
        // Iterator each child form
        while(iter.hasNext()) 
        {
            Map.Entry pairs = (Map.Entry) iter.next();
            String currentChildFormName = (String) pairs.getKey();
            ArrayList<ChildTableRecord> childFormData = (ArrayList<ChildTableRecord>) pairs.getValue();
          //  logger.log(ODLLevel.NOTIFICATION, "[Child Form Name: {0}], [Child Form Data: {1}]", new Object[]{childFormName, childFormData});
             
            if (currentChildFormName.equals(childFormName))
            {
                // Iterate records in a child form
                for (ChildTableRecord record : childFormData) 
                {
                    ACTION action = record.getAction();
                    Map<String, Object> childRecordData = record.getChildData();
                    String rowKey = record.getRowKey();
                    String attributeValue = (String)childRecordData.get(childAttributeName);
                     
                    if (attributeValue.equals(childAttributeValue)) 
                    {
            //            logger.log(ODLLevel.NOTIFICATION, "[Action: {0}], [Child Record Data: {1}], [Row Key: {2}]", new Object[]{action, childRecordData, rowKey});
                        return rowKey;
                    }
                }
            }
        }
         
        // Child Value does not exist
        throw new Exception("Child Value does not exist");
    }
     
     
    /**
     * Get a specific user's resource account 
     * @param userKey       `       OIM user's usr_key
     * @param resourceObjectName    Name of the resource object
     * @return  Resource account 
     * @throws UserNotFoundException
     * @throws GenericProvisioningException
     */
    public static Account getUserResourceAccount(String userKey, String resourceObjectName) throws UserNotFoundException, GenericProvisioningException
    {
        boolean populateAccountData = true;
        HashMap<String,Object> configParams = new HashMap<String,Object>();
        SearchCriteria criteria =  new SearchCriteria(ProvisioningConstants.AccountSearchAttribute.OBJ_NAME.getId(), resourceObjectName, SearchCriteria.Operator.EQUAL);
        List<Account> accounts = provOps.getAccountsProvisionedToUser(userKey, criteria , configParams , populateAccountData);
         
        for (Account account : accounts) 
        {
            String accountId = account.getAccountID();
            String appInstName = account.getAppInstance().getApplicationInstanceName();
            Map<String, Object> accountData = account.getAccountData().getData();
            String accountStatus = account.getAccountStatus();
           // logger.log(ODLLevel.NOTIFICATION, "Account Id: [{0}], Application Instance Name: [{1}], Account Status: [{2}], Account Data:[{3}]", new Object[]{accountId, appInstName, accountStatus, accountData});
             
            // Only return enabled, provisioned, or disabled account
            if(ProvisioningConstants.ObjectStatus.PROVISIONED.getId().equals(accountStatus) || ProvisioningConstants.ObjectStatus.ENABLED.getId().equals(accountStatus) || ProvisioningConstants.ObjectStatus.DISABLED.getId().equals(accountStatus)) 
            {
             //   logger.log(ODLLevel.NOTIFICATION, "Return Account Id: [{0}]", new Object[]{accountId});
                return account;
            }
        }
         
        return null;
    }
     
    /**
     * Get the user's usr_key 
     * @param userLogin OIM.User Login      (USR_LOGIN)
     * @return value of usr_key
     * @throws NoSuchUserException
     * @throws UserLookupException
     */
    public static String getUserKeyByUserLogin(String userLogin) throws NoSuchUserException, UserLookupException
    {
        boolean userLoginUsed = true;
        HashSet<String> attrsToFetch = new HashSet<String>();
        attrsToFetch.add(UserManagerConstants.AttributeName.USER_KEY.getId());
        attrsToFetch.add(UserManagerConstants.AttributeName.USER_LOGIN.getId());
        User user = usrMgrOps.getDetails(userLogin, attrsToFetch, userLoginUsed);
      //  logger.log(ODLLevel.NOTIFICATION, "User Details: {0}", new Object[]{user});
        return user.getEntityId();
    }
     
    /**
     * Modifies a resource account on an OIM user
     * @param userKey           OIM usr_key
     * @param resourceAccount   Existing resource account to modify
     * @param modAttrs          Attributes to modify on the paraent form
     * @throws AccountNotFoundException
     * @throws GenericProvisioningException
     */
    public static void modifyUserResourceAccountParentData(String userKey, Account resourceAccount, HashMap<String, Object> modAttrs, Map<String, ArrayList<ChildTableRecord>> modChildData) throws AccountNotFoundException, GenericProvisioningException 
    {
        // Stage resource account modifcations
        String accountId  = resourceAccount.getAccountID();
        System.out.println("accountId: "+accountId);
        String processFormInstanceKey = resourceAccount.getProcessInstanceKey();
        System.out.println("processFormInstanceKey: "+processFormInstanceKey);
        Account modAccount = new Account(accountId, processFormInstanceKey, userKey);
      //  logger.log(ODLLevel.NOTIFICATION, "Account Id: [{0}], Process Form Instance Key: [{1}]", new Object[]{accountId, processFormInstanceKey});
 
        // Setup account data object
        String formKey = resourceAccount.getAccountData().getFormKey();
        System.out.println("formKey: "+formKey);
        String udTablePrimaryKey = resourceAccount.getAccountData().getUdTablePrimaryKey();
        
        System.out.println("udTablePrimaryKey: "+udTablePrimaryKey);
        AccountData accountData = new AccountData(formKey, udTablePrimaryKey , modAttrs);
        //logger.log(ODLLevel.NOTIFICATION, "Form Key: [{0}], UD Table Primary Key: [{1}]", new Object[]{formKey, udTablePrimaryKey});
        accountData.setChildData(modChildData); // set child data
         
        // Set necessary information to modified account
        modAccount.setAccountData(accountData);
        modAccount.setAppInstance(resourceAccount.getAppInstance());
 
        // Modify resource account
        provOps.modify(modAccount);
       // logger.log(ODLLevel.NOTIFICATION, "Modification successful.");
    }
     
    /**
     * Prints the child data in each child form of a resource account.
     * @param childData   Child data of user's resource account
     */
    public static void printResourceAccountChildData(Map<String, ArrayList<ChildTableRecord>> childData) 
    {
        // Child Data Iterator 
        Iterator iter = childData.entrySet().iterator();
         
        // Iterator each child form
        while(iter.hasNext()) 
        {
            Map.Entry pairs = (Map.Entry) iter.next();
            String childFormName = (String) pairs.getKey();
            System.out.println("=============================================");
            System.out.println("childFormName:  "+childFormName);
            
           
            ArrayList<ChildTableRecord> childFormData = (ArrayList<ChildTableRecord>) pairs.getValue();
         //   logger.log(ODLLevel.NOTIFICATION, "[Child Form Name: {0}], [Child Form Data: {1}]", new Object[]{childFormName, childFormData});
            System.out.println("childFormData:  "+childFormData); 
            // Iterate records in a child form
            
            for (ChildTableRecord record : childFormData) 
            {
                ACTION action = record.getAction();
                Map<String, Object> childRecordData = record.getChildData();
                String rowKey = record.getRowKey();
                System.out.println("action:  "+action);
                System.out.println("rowKey:  "+rowKey);
                System.out.println("childRecordData:  "+childRecordData.get(childFormName.concat("_ID")));
           //     logger.log(ODLLevel.NOTIFICATION, "[Action: {0}], [Child Record Data: {1}], [Row Key: {2}]", new Object[]{action, childRecordData, rowKey});
            }
            System.out.println("=============================================");
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
	
/////////////////////////Child Data Detail//////////////////////


    public static String getChildDetailXML(String Req_Id)
    {
    	DatabaseConnection db = new DatabaseConnection();
    	Connection con = db.connectMSCDB();
    	PreparedStatement stmt;
    	ResultSet rs;
    	String xml=null;

    	try {
    		stmt = con.prepareStatement("Select REQ_XML_DATA FROM OIM_CHILD_REQ_DATA where REQUEST_ID=?");
    		stmt.setString(1,"134098"); 

    		rs = stmt.executeQuery();         
    		while (rs.next()) { 
    			xml = rs.getString(1);
    		}
    		rs.close();                       
    		stmt.close();        


    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}  
    	System.out.println("XML: "+xml);
    	return xml;
    }


    ///////////////////////Convert String to XML////////////////////////////


    private static Document convertStringToXMLDocument(String xmlString) 
    {
    	//Parser that produces DOM object trees from XML content
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    	//API to obtain DOM Document instance
    	DocumentBuilder builder = null;
    	try
    	{
    		//Create DocumentBuilder with default configuration
    		builder = factory.newDocumentBuilder();

    		//Parse the content to Document object
    		Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
    		return doc;
    	} 
    	catch (Exception e) 
    	{
    		e.printStackTrace();
    	}
    	return null;
    }


    ////////////////////////////Parse XML DOCUMENT//////////////////////



    public static String XMLParser(Document  document) throws LoginException, Exception {

    	//Get the Document Builder

    	String xmlTable="";
    	HashMap<Integer, String> childData = new HashMap <Integer,String>();
    	int childInt=0;

    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	try {
    		DocumentBuilder builder = factory.newDocumentBuilder();

    		// Get Document
    		//  Document document = builder.parse(new File("C:\\Users\\Admin\\Desktop\\XML1.xml"));
    	//	Document document1 = builder.parse(new File("C:\\Users\\Admin\\eclipse-workspace\\parseXML\\file1.xml"));


    		// Normalize the xml structure
    		document.getDocumentElement().normalize();

    		// Get all the element by the tag name

    		NodeList laptopList = document.getElementsByTagName("Application");
    		for(int i = 0; i <laptopList.getLength(); i++) {
    			//--System.out.println("Applicatin length:"+laptopList.getLength());
    			Node laptop = laptopList.item(i);

    			if(laptop.getNodeType() == Node.ELEMENT_NODE) {

    				Element laptopElement = (Element) laptop;
    				//--System.out.println("Application Name: " + laptopElement.getAttribute("name"));
    				String AppName = laptopElement.getAttribute("name");
    				
    				childData.put(childInt, AppName);
    				childInt++;
    				
    				xmlTable+="<h4>Application Name: "+AppName+"</h4>";

    				NodeList laptopDetails =  laptop.getChildNodes();
    				for(int j = 0; j < laptopDetails.getLength(); j++){
    					Node detail = laptopDetails.item(j);
    					if(detail.getNodeType() == Node.ELEMENT_NODE) {
    						Element detailElement = (Element) detail;
    						System.out.println(" Table Name    " + detailElement.getTagName() + ": " + detailElement.getAttribute("table_name"));
    						String TableName = detailElement.getAttribute("table_name");
    						
    						childData.put(childInt, TableName);
    						childInt++;
    						
    						xmlTable+="<h4>Table Name: "+TableName+"</h4>";
    						NodeList userDetail = detailElement.getChildNodes();


    						String  targetUser="";

    						Node Login = userDetail.item(0);

    						if(Login.getNodeType() == Node.ELEMENT_NODE)
    						{

    							Element userAtt =(Element) Login;

    							//--System.out.println( "User Login: " + userAtt.getAttribute("login"));
    							//--System.out.println( "User Login: " + userAtt.getTagName());
    							
    							
    							targetUser = userAtt.getAttribute("login");
    							
    					
    							childData.put(childInt, targetUser);
        						childInt++;
    					
        						xmlTable+="<h4>Target User: "+targetUser+"</h4>";

    							//System.out.println("  "		+ ""+userAtt.getTagName()+" : "+userAtt.getTextContent());
    						}
    						for(int k =1;k <userDetail.getLength();k++)
    						{

    							Node user = userDetail.item(k);
    							String Record;

    							if(user.getNodeType() == Node.ELEMENT_NODE)
    							{
    								Element userAtt =(Element) user;
    								System.out.println( userAtt.getTagName()+":" + userAtt.getAttribute("id"));
    								Record = userAtt.getAttribute("id");
    								xmlTable+="<h4>Record:"+Record+"</h4>";
    								NodeList values = userAtt.getChildNodes();

    								xmlTable+="	<table id='example' class='table table-striped table-bordered' style='width:100%'>"+


                    "<tbody   style='font-size: 12px;'>";




    								//	System.out.println(userAtt.getTagName()+" : "+userAtt.getTextContent());

    								for(int m =0;m <values.getLength();m++)
    								{

    									Node user1 = values.item(m);

    									if(user1.getNodeType() == Node.ELEMENT_NODE)
    									{
    										Element userAtt1 =(Element) user1;
    										//System.out.println( "Record ID: " + userAtt.getAttribute("id"));


    										xmlTable+="<tr>"

			+"<td >"+userAtt1.getTagName()+"</td>"
			+"<td >"+userAtt1.getTextContent()+"</td>";
    											
    										childData.put(childInt, userAtt1.getTextContent());
    					    				childInt++;

    										//---System.out.println(userAtt1.getTagName()+" : "+userAtt1.getTextContent());
    									}
    								}
    								xmlTable+="</tbody></table>";
    							}
    						}
    					}

  ///////////////////////Add Child Data After Getting info/////////////////
        				
        				if(AppName.equals("EQUATION")) {
        				
        				ChildProcessForm childProcessForm = new ChildProcessForm();
        				System.out.println("Getting tables");
        					String table = childData.get(1);
        					String name = childData.get(2);
        					
        					if(table.equals("table26Equation"))
        					{
        						childData.entrySet().forEach( entry -> {
                				    System.out.println( entry.getKey() + " => " + entry.getValue() );
                				    
                				    
                				});
        						System.out.println("table26Equation function called");
        						childProcessForm.Equation26(childData, name);
        						childData.clear();
        						childInt=1;
        					}
        					else if(table.equals("table27Equation"))
        					{
        						childData.entrySet().forEach( entry -> {
                				    System.out.println( entry.getKey() + " => " + entry.getValue() );
                				});
        						System.out.println("table27Equation function called");
        						childProcessForm.Equation27(childData, name);
        						childData.clear();
        						childInt=1;
        						
        					}
        					else if(table.equals("table7Equation"))
        					{
        						childData.entrySet().forEach( entry -> {
                				    System.out.println( entry.getKey() + " => " + entry.getValue() );
                				});
        						System.out.println("table7Equation function called");
        						childProcessForm.Equation7(childData, name);
        						childData.clear();
        						childInt=1;
        						
        					}
        				
        				}
        				///////////Start Work From Here////////////
        				
        				else if(AppName.equals("AS400")) {
        					ChildProcessForm childProcessForm = new ChildProcessForm();
        					System.out.println("AS400 Called");
        					System.out.println("------------------------");
        					String table = childData.get(1);
        					String name = childData.get(2);
        					if(table.equals("tableCSP"))
        					{	
        						System.out.println("tableCSP function called");
        						childData.entrySet().forEach( entry -> {
                				    System.out.println( entry.getKey() + " => " + entry.getValue() );
                				});
        						for(int k=3;k<childData.size();k++)
        						{	
        							System.out.println("CSP DATA: "+childData.get(k));
        						childProcessForm.As400CSP(childData.get(k),name);
        						}
        						childData.clear();
        						childInt=1;
        						
        					}
        					if(table.equals("tableCSG"))
        					{
        						System.out.println("tableCSG function called");
        						childData.entrySet().forEach( entry -> {
                				    System.out.println( entry.getKey() + " => " + entry.getValue() );
                				});
        						childProcessForm.As400CSG(childData,name);
        						
        						childData.clear();
        						childInt=1;
        						
        					}
        				}
        				else if(AppName.equals("EquationGroup")) {
        					ChildProcessForm childProcessForm = new ChildProcessForm();
        					System.out.println("EquationGroup Called");
        					System.out.println("------------------------");
        					String table = childData.get(1);
        					String name = childData.get(2);
        					
        					if(table.equals("FormData"))
        					{	
        						
        						childData.clear();
        						childInt=1;
        						
        					}
        					if(table.equals("tableEQGROUP"))
        					{	
        						System.out.println("tableEQGROUP function called");
        						childData.entrySet().forEach( entry -> {
                				    System.out.println( entry.getKey() + " => " + entry.getValue() );
                				});
        						
        						childProcessForm.equationGroup("EQU",name);
        						for(int k=3;k<=childData.size();k++)
        						{	
        							if(childData.get(k).equals("EQU"))
        							{continue;}
        							System.out.println("tableEQGROUP DATA: "+childData.get(k));
        						childProcessForm.equationGroup(childData.get(k),name);
        						}
        						childData.clear();
        						childInt=1;
        						
        					}
        					
        				}
        			
        				/////////////////////////////////////////////////////////////////////////////
        				
    				}
    				
    				

    			}
    		}


    	} catch (ParserConfigurationException e) {
    		e.printStackTrace();
    	} catch (SAXException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	return xmlTable;
    }
    
    
    public void Equation26(HashMap<Integer,String> mapData, String UserName) throws LoginException, Exception
    {
    	//final String USER_LOGIN = "Almir";
        final String RESOURCE_OBJECT_NAME = "EQUATION";
        final String CHILD_PROCESS_FORM_NAME = "UD_KFILPK26";
        
        //////////////////
        
        final String UD_KFILPK26_USER_BRANCH = "UD_KFILPK26_USER_BRANCH";
        final String UD_KFILPK26_BRANCH_NO = "UD_KFILPK26_BRANCH_NO";
        final String UD_KFILPK26_USR_CLASS = "UD_KFILPK26_USR_CLASS";
        final String UD_KFILPK26_LANGUAGE_CODE = "UD_KFILPK26_LANGUAGE_CODE";
        final String UD_KFILPK26_DATE_LAST_MAINTAINED = "UD_KFILPK26_DATE_LAST_MAINTAIN";
        final String UD_KFILPK26_USER_GROUP = "UD_KFILPK26_USER_GROUP";
        //////////////////
        
        
        	  String User_Branch = mapData.get(3);
              String Branch_Number = mapData.get(4);
        final String User_Class = mapData.get(5);
        final String Language_Code = mapData.get(6);
        final String Date_Last_Maintained = mapData.get(7);
        final String User_Group = mapData.get(8);
        
        
        ///////////////////
        
        
        
    	OIMClient oimClient = null;
    	 
        try
        {
        	LoginToOIM login= new LoginToOIM();
        	try {
				oimClient = login.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
			} catch (LoginException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
        
        	OIMUtils oimUtils = new OIMUtils();
            HashMap userDetail =  oimUtils.getUserDetailByLogin(UserName, oimClient);
            User_Branch=(String) userDetail.get("Branch_Code");
            Branch_Number=(String) userDetail.get("Branch_Code");
            System.out.println("Branched with N");
            if(User_Branch==null)
            {User_Branch="-";
            Branch_Number="-";}
 
        	/* // Set system properties required for OIMClient
            System.setProperty("java.security.auth.login.config", AUTHWL_PATH);
            System.setProperty("APPSERVER_TYPE", "wls");
            // Create an instance of OIMClient with OIM environment information
            Hashtable env = new Hashtable();
            env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL,"weblogic.jndi.WLInitialContextFactory");
            env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, OIM_PROVIDER_URL);
            oimClient = new OIMClient(env);
 
            // Login to OIM with the approriate credentials
            oimClient.login(OIM_USERNAME, OIM_PASSWORD.toCharArray());
 */            
            // Get OIM services
            provOps = oimClient.getService(ProvisioningService.class);
            usrMgrOps = oimClient.getService(UserManager.class);
             
            // Get usr_key
            String userKey = null;
			try {
				userKey = getUserKeyByUserLogin(UserName);
			} catch (NoSuchUserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UserLookupException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
             
            // Get user's resource account
            Account resourceAccount = null;
			try {
				resourceAccount = getUserResourceAccount(userKey, RESOURCE_OBJECT_NAME);
			} catch (UserNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (GenericProvisioningException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
             
            // Get account's child data
            Map<String, ArrayList<ChildTableRecord>> childData = resourceAccount.getAccountData().getChildData();
           // logger.log(ODLLevel.NOTIFICATION, "Resource Account Child Data: {0}", new Object[]{childData});
            printResourceAccountChildData(childData);
             
            // Staging objects
            HashMap<String, Object> modParentData = new HashMap<String, Object>();
            Map<String, ArrayList<ChildTableRecord>> modChildData = new HashMap<String, ArrayList<ChildTableRecord>>();
            ArrayList<ChildTableRecord> modRecords = new ArrayList<ChildTableRecord>(); 
            
            
            HashMap<String,Object> addRecordData = new HashMap<String,Object>();
             //////////////////////////////DB CONNECTION///////////////////////////
            
    		/*
    			DatabaseConnection db = new DatabaseConnection();
    			Connection con = db.connectMSCDB();
    			PreparedStatement stmt;
    			ResultSet rs;
    			
    			
    			try {
    				stmt = con.prepareStatement("Select UD_KFILPK34_PROCESS_INTER_BRAN,UD_KFILPK34_GROUP,\r\n" + 
    						"UD_KFILPK34_INTER_BRANCH_DEBIT,UD_KFILPK34_RESIDENT_BRANCH,\r\n" + 
    						"UD_KFILPK34_INTER_BRANCH_CREDI,\r\n" + 
    						"UD_KFILPK34_BRANCH_NUMBER,\r\n" + 
    						"UD_KFILPK34_LOCAL_CREDIT_AMOUN,\r\n" + 
    						"UD_KFILPK34_LOCAL_DEBIT_AMOUNT,\r\n" + 
    						"UD_KFILPK34_DEBIT_AUTHORIZATIO,\r\n" + 
    						"UD_KFILPK34_CREDIT_AUTHORIZATI\r\n" + 
    						"\r\n" + 
    						"from EQU_REQUEST_DATA  where Group_NAME =?");
    				
    				stmt.setString(1,"170~ATMCCU"); 
    				int i=1;
    				rs = stmt.executeQuery();         
    				while (rs.next()) { 
    					
    					addRecordData.put("UD_KFILPK34_PROCESS_INTER_BRAN", rs.getString(1));
    					addRecordData.put("UD_KFILPK34_GROUP", rs.getString(2));
    					addRecordData.put("UD_KFILPK34_INTER_BRANCH_DEBIT", rs.getString(3));
    					addRecordData.put("UD_KFILPK34_RESIDENT_BRANCH", rs.getString(4));
    					addRecordData.put("UD_KFILPK34_INTER_BRANCH_CREDI", rs.getString(5));
    					addRecordData.put("UD_KFILPK34_BRANCH_NUMBER", rs.getString(6));
    					addRecordData.put("UD_KFILPK34_LOCAL_CREDIT_AMOUN", rs.getString(7));
    					addRecordData.put("UD_KFILPK34_LOCAL_DEBIT_AMOUNT", rs.getString(8));
    					addRecordData.put("UD_KFILPK34_DEBIT_AUTHORIZATIO", rs.getString(9));
    					addRecordData.put("UD_KFILPK34_CREDIT_AUTHORIZATI", rs.getString(10));
    					System.out.println( "------------------------------"+(++i));
    					ChildTableRecord addRecord = new ChildTableRecord();
    		            addRecord.setAction(ACTION.Add);
    		            addRecord.setChildData(addRecordData);
    		            modRecords.add(addRecord);
    		            modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
    		            modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData); // Modify resource account
    		            modChildData.clear();
    		            addRecordData.clear();
    		            modParentData.clear();
    		            modRecords.clear();
    		            System.out.println(Arrays.asList(userKey));
    		            System.out.println(Arrays.asList(resourceAccount));
    		            System.out.println(Arrays.asList(modChildData)); // method 1
    		            System.out.println(Arrays.asList(modParentData));
    		            break;
    				 }
    				rs.close();                       
    				stmt.close();        
    				
    				
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}  
    			
    			
    		
    		
            
            
            */
            
            ///////////////////////////////////////////////////////////////////////
            // Stage Add Child Record
            //-----HashMap<String,Object> addRecordData = new HashMap<String,Object>();
            addRecordData.put(UD_KFILPK26_USER_BRANCH, User_Branch);
            addRecordData.put(UD_KFILPK26_BRANCH_NO, Branch_Number);
            addRecordData.put(UD_KFILPK26_USR_CLASS, User_Class);
            addRecordData.put(UD_KFILPK26_LANGUAGE_CODE, Language_Code);
            addRecordData.put(UD_KFILPK26_DATE_LAST_MAINTAINED, Date_Last_Maintained);
            addRecordData.put(UD_KFILPK26_USER_GROUP, User_Group);
            
            ChildTableRecord addRecord = new ChildTableRecord();
            addRecord.setAction(ACTION.Add);
            addRecord.setChildData(addRecordData);
            modRecords.add(addRecord);
             
            // Stage Modify Child Record
            //HashMap<String,Object> modifyRecordData = new HashMap<String,Object>();
            //modifyRecordData.put(CHILD_ATTRIBUTE_NAME, "Engineer II");
            //ChildTableRecord modifyRecord = new ChildTableRecord();
            //modifyRecord.setChildData(modifyRecordData);
            //modifyRecord.setAction(ACTION.Modify);
            //modifyRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
            //modRecords.add(modifyRecord);
             
            // Stage Remove Child Record
            //HashMap<String,Object> removeRecordData = new HashMap<String,Object>();
            //ChildTableRecord removeRecord = new ChildTableRecord();
            //removeRecord.setChildData(removeRecordData);
            //removeRecord.setAction(ACTION.Delete);
            //removeRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
            //modRecords.add(removeRecord);
             
                     
           modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
            try {
				modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData);
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GenericProvisioningException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Modify resource account                
        }
 
      //  catch (Exception ex) {logger.log(ODLLevel.ERROR, "", ex);} 
 
        finally
        {
            // Logout user from OIMClient
            if (oimClient != null) { oimClient.logout();} 
        }
        ChildProcessForm childProcessForm =new ChildProcessForm();
        ChildProcessForm.Equation34(User_Group, UserName);
    }
 
 /////////////////////////////////////Add Table BAse 7///////////////////////////
    
    
    public void Equation27(HashMap<Integer,String> mapData, String UserName)
    {
    	//final String USER_LOGIN = "Almir";
        final String RESOURCE_OBJECT_NAME = "EQUATION";
        final String CHILD_PROCESS_FORM_NAME = "UD_KFILPK27";
        
        //////////////////
        
        final String UD_KFILPK27_DEFAULT_USER_LIMIT = "UD_KFILPK27_DEFAULT_USER_LIMIT";
        final String UD_KFILPK27_CURRENCY_MNEMONIC = "UD_KFILPK27_CURRENCY_MNEMONIC";
        final String UD_KFILPK27_DATE_LAST_MAINTAINED = "UD_KFILPK27_DATE_LAST_MAINTAIN";
        final String UD_KFILPK27_APPLICATION_CODE = "UD_KFILPK27_APPLICATION_CODE";
        //////////////////
        
        final String DEFAULT_USER_LIMIT = mapData.get(3);
        final String CURRENCY_MNEMONIC = mapData.get(4);
        final String DATE_LAST_MAINTAINED = mapData.get(5);
        final String APPLICATION_CODE = mapData.get(6);
        
        
        
        ///////////////////
        
        
        
    	OIMClient oimClient = null;
    	 
        try
        {
        	LoginToOIM login= new LoginToOIM();
        	try {
				oimClient = login.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
			} catch (LoginException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
 
        	/* // Set system properties required for OIMClient
            System.setProperty("java.security.auth.login.config", AUTHWL_PATH);
            System.setProperty("APPSERVER_TYPE", "wls");
            // Create an instance of OIMClient with OIM environment information
            Hashtable env = new Hashtable();
            env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL,"weblogic.jndi.WLInitialContextFactory");
            env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, OIM_PROVIDER_URL);
            oimClient = new OIMClient(env);
 
            // Login to OIM with the approriate credentials
            oimClient.login(OIM_USERNAME, OIM_PASSWORD.toCharArray());
 */            
            // Get OIM services
            provOps = oimClient.getService(ProvisioningService.class);
            usrMgrOps = oimClient.getService(UserManager.class);
             
            // Get usr_key
            String userKey = null;
			try {
				userKey = getUserKeyByUserLogin(UserName);
			} catch (NoSuchUserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UserLookupException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
             
            // Get user's resource account
            Account resourceAccount = null;
			try {
				resourceAccount = getUserResourceAccount(userKey, RESOURCE_OBJECT_NAME);
			} catch (UserNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (GenericProvisioningException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
             
            // Get account's child data
            Map<String, ArrayList<ChildTableRecord>> childData = resourceAccount.getAccountData().getChildData();
           // logger.log(ODLLevel.NOTIFICATION, "Resource Account Child Data: {0}", new Object[]{childData});
            printResourceAccountChildData(childData);
             
            // Staging objects
            HashMap<String, Object> modParentData = new HashMap<String, Object>();
            Map<String, ArrayList<ChildTableRecord>> modChildData = new HashMap<String, ArrayList<ChildTableRecord>>();
            ArrayList<ChildTableRecord> modRecords = new ArrayList<ChildTableRecord>(); 
            
            
            HashMap<String,Object> addRecordData = new HashMap<String,Object>();
             //////////////////////////////DB CONNECTION///////////////////////////
            
    		/*
    			DatabaseConnection db = new DatabaseConnection();
    			Connection con = db.connectMSCDB();
    			PreparedStatement stmt;
    			ResultSet rs;
    			
    			
    			try {
    				stmt = con.prepareStatement("Select UD_KFILPK34_PROCESS_INTER_BRAN,UD_KFILPK34_GROUP,\r\n" + 
    						"UD_KFILPK34_INTER_BRANCH_DEBIT,UD_KFILPK34_RESIDENT_BRANCH,\r\n" + 
    						"UD_KFILPK34_INTER_BRANCH_CREDI,\r\n" + 
    						"UD_KFILPK34_BRANCH_NUMBER,\r\n" + 
    						"UD_KFILPK34_LOCAL_CREDIT_AMOUN,\r\n" + 
    						"UD_KFILPK34_LOCAL_DEBIT_AMOUNT,\r\n" + 
    						"UD_KFILPK34_DEBIT_AUTHORIZATIO,\r\n" + 
    						"UD_KFILPK34_CREDIT_AUTHORIZATI\r\n" + 
    						"\r\n" + 
    						"from EQU_REQUEST_DATA  where Group_NAME =?");
    				
    				stmt.setString(1,"170~ATMCCU"); 
    				int i=1;
    				rs = stmt.executeQuery();         
    				while (rs.next()) { 
    					
    					addRecordData.put("UD_KFILPK34_PROCESS_INTER_BRAN", rs.getString(1));
    					addRecordData.put("UD_KFILPK34_GROUP", rs.getString(2));
    					addRecordData.put("UD_KFILPK34_INTER_BRANCH_DEBIT", rs.getString(3));
    					addRecordData.put("UD_KFILPK34_RESIDENT_BRANCH", rs.getString(4));
    					addRecordData.put("UD_KFILPK34_INTER_BRANCH_CREDI", rs.getString(5));
    					addRecordData.put("UD_KFILPK34_BRANCH_NUMBER", rs.getString(6));
    					addRecordData.put("UD_KFILPK34_LOCAL_CREDIT_AMOUN", rs.getString(7));
    					addRecordData.put("UD_KFILPK34_LOCAL_DEBIT_AMOUNT", rs.getString(8));
    					addRecordData.put("UD_KFILPK34_DEBIT_AUTHORIZATIO", rs.getString(9));
    					addRecordData.put("UD_KFILPK34_CREDIT_AUTHORIZATI", rs.getString(10));
    					System.out.println( "------------------------------"+(++i));
    					ChildTableRecord addRecord = new ChildTableRecord();
    		            addRecord.setAction(ACTION.Add);
    		            addRecord.setChildData(addRecordData);
    		            modRecords.add(addRecord);
    		            modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
    		            modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData); // Modify resource account
    		            modChildData.clear();
    		            addRecordData.clear();
    		            modParentData.clear();
    		            modRecords.clear();
    		            System.out.println(Arrays.asList(userKey));
    		            System.out.println(Arrays.asList(resourceAccount));
    		            System.out.println(Arrays.asList(modChildData)); // method 1
    		            System.out.println(Arrays.asList(modParentData));
    		            break;
    				 }
    				rs.close();                       
    				stmt.close();        
    				
    				
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}  
    			
    			
    		
    		
            
            
            */
            
            ///////////////////////////////////////////////////////////////////////
            // Stage Add Child Record
            //-----HashMap<String,Object> addRecordData = new HashMap<String,Object>();
            addRecordData.put(UD_KFILPK27_DEFAULT_USER_LIMIT, DEFAULT_USER_LIMIT);
            addRecordData.put(UD_KFILPK27_CURRENCY_MNEMONIC, CURRENCY_MNEMONIC);
            addRecordData.put(UD_KFILPK27_DATE_LAST_MAINTAINED, DATE_LAST_MAINTAINED);
            addRecordData.put(UD_KFILPK27_APPLICATION_CODE, APPLICATION_CODE);
            
            ChildTableRecord addRecord = new ChildTableRecord();
            addRecord.setAction(ACTION.Add);
            addRecord.setChildData(addRecordData);
            modRecords.add(addRecord);
             
            // Stage Modify Child Record
            //HashMap<String,Object> modifyRecordData = new HashMap<String,Object>();
            //modifyRecordData.put(CHILD_ATTRIBUTE_NAME, "Engineer II");
            //ChildTableRecord modifyRecord = new ChildTableRecord();
            //modifyRecord.setChildData(modifyRecordData);
            //modifyRecord.setAction(ACTION.Modify);
            //modifyRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
            //modRecords.add(modifyRecord);
             
            // Stage Remove Child Record
            //HashMap<String,Object> removeRecordData = new HashMap<String,Object>();
            //ChildTableRecord removeRecord = new ChildTableRecord();
            //removeRecord.setChildData(removeRecordData);
            //removeRecord.setAction(ACTION.Delete);
            //removeRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
            //modRecords.add(removeRecord);
             
                     
           modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
            try {
				modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData);
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GenericProvisioningException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Modify resource account                
        }
 
      //  catch (Exception ex) {logger.log(ODLLevel.ERROR, "", ex);} 
 
        finally
        {
            // Logout user from OIMClient
            if (oimClient != null) { oimClient.logout();} 
        }
    }
    public void Equation7(HashMap<Integer,String> mapData, String UserName)
    {
    	//final String USER_LOGIN = "Almir";
        final String RESOURCE_OBJECT_NAME = "EQUATION";
        final String CHILD_PROCESS_FORM_NAME = "UD_KAPBASE7";
        
        //////////////////
        
        final String UD_KAPBASE7_UNIT_SERVER = "UD_KAPBASE7_UNIT_SERVER";
        final String UD_KAPBASE7_INITIAL_MENU = "UD_KAPBASE7_INITIAL_MENU";
        final String UD_KAPBASE7_AUTHORISED_UNIT = "UD_KAPBASE7_AUTHORISED_UNIT";
        
        //////////////////
        
        final String UNIT_SERVER = mapData.get(3);
        final String INITIAL_MENU = mapData.get(4);
        final String AUTHORISED_UNIT = mapData.get(5);
        
        
        ///////////////////
        
        
        
    	OIMClient oimClient = null;
    	 
        try
        {
        	LoginToOIM login= new LoginToOIM();
        	try {
				oimClient = login.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
			} catch (LoginException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
 
        	/* // Set system properties required for OIMClient
            System.setProperty("java.security.auth.login.config", AUTHWL_PATH);
            System.setProperty("APPSERVER_TYPE", "wls");
            // Create an instance of OIMClient with OIM environment information
            Hashtable env = new Hashtable();
            env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL,"weblogic.jndi.WLInitialContextFactory");
            env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, OIM_PROVIDER_URL);
            oimClient = new OIMClient(env);
 
            // Login to OIM with the approriate credentials
            oimClient.login(OIM_USERNAME, OIM_PASSWORD.toCharArray());
 */            
            // Get OIM services
            provOps = oimClient.getService(ProvisioningService.class);
            usrMgrOps = oimClient.getService(UserManager.class);
             
            // Get usr_key
            String userKey = null;
			try {
				userKey = getUserKeyByUserLogin(UserName);
			} catch (NoSuchUserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UserLookupException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
             
            // Get user's resource account
            Account resourceAccount = null;
			try {
				resourceAccount = getUserResourceAccount(userKey, RESOURCE_OBJECT_NAME);
			} catch (UserNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (GenericProvisioningException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
             
            // Get account's child data
            Map<String, ArrayList<ChildTableRecord>> childData = resourceAccount.getAccountData().getChildData();
           // logger.log(ODLLevel.NOTIFICATION, "Resource Account Child Data: {0}", new Object[]{childData});
            printResourceAccountChildData(childData);
             
            // Staging objects
            HashMap<String, Object> modParentData = new HashMap<String, Object>();
            Map<String, ArrayList<ChildTableRecord>> modChildData = new HashMap<String, ArrayList<ChildTableRecord>>();
            ArrayList<ChildTableRecord> modRecords = new ArrayList<ChildTableRecord>(); 
            
            
            HashMap<String,Object> addRecordData = new HashMap<String,Object>();
             //////////////////////////////DB CONNECTION///////////////////////////
            
    		/*
    			DatabaseConnection db = new DatabaseConnection();
    			Connection con = db.connectMSCDB();
    			PreparedStatement stmt;
    			ResultSet rs;
    			
    			
    			try {
    				stmt = con.prepareStatement("Select UD_KFILPK34_PROCESS_INTER_BRAN,UD_KFILPK34_GROUP,\r\n" + 
    						"UD_KFILPK34_INTER_BRANCH_DEBIT,UD_KFILPK34_RESIDENT_BRANCH,\r\n" + 
    						"UD_KFILPK34_INTER_BRANCH_CREDI,\r\n" + 
    						"UD_KFILPK34_BRANCH_NUMBER,\r\n" + 
    						"UD_KFILPK34_LOCAL_CREDIT_AMOUN,\r\n" + 
    						"UD_KFILPK34_LOCAL_DEBIT_AMOUNT,\r\n" + 
    						"UD_KFILPK34_DEBIT_AUTHORIZATIO,\r\n" + 
    						"UD_KFILPK34_CREDIT_AUTHORIZATI\r\n" + 
    						"\r\n" + 
    						"from EQU_REQUEST_DATA  where Group_NAME =?");
    				
    				stmt.setString(1,"170~ATMCCU"); 
    				int i=1;
    				rs = stmt.executeQuery();         
    				while (rs.next()) { 
    					
    					addRecordData.put("UD_KFILPK34_PROCESS_INTER_BRAN", rs.getString(1));
    					addRecordData.put("UD_KFILPK34_GROUP", rs.getString(2));
    					addRecordData.put("UD_KFILPK34_INTER_BRANCH_DEBIT", rs.getString(3));
    					addRecordData.put("UD_KFILPK34_RESIDENT_BRANCH", rs.getString(4));
    					addRecordData.put("UD_KFILPK34_INTER_BRANCH_CREDI", rs.getString(5));
    					addRecordData.put("UD_KFILPK34_BRANCH_NUMBER", rs.getString(6));
    					addRecordData.put("UD_KFILPK34_LOCAL_CREDIT_AMOUN", rs.getString(7));
    					addRecordData.put("UD_KFILPK34_LOCAL_DEBIT_AMOUNT", rs.getString(8));
    					addRecordData.put("UD_KFILPK34_DEBIT_AUTHORIZATIO", rs.getString(9));
    					addRecordData.put("UD_KFILPK34_CREDIT_AUTHORIZATI", rs.getString(10));
    					System.out.println( "------------------------------"+(++i));
    					ChildTableRecord addRecord = new ChildTableRecord();
    		            addRecord.setAction(ACTION.Add);
    		            addRecord.setChildData(addRecordData);
    		            modRecords.add(addRecord);
    		            modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
    		            modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData); // Modify resource account
    		            modChildData.clear();
    		            addRecordData.clear();
    		            modParentData.clear();
    		            modRecords.clear();
    		            System.out.println(Arrays.asList(userKey));
    		            System.out.println(Arrays.asList(resourceAccount));
    		            System.out.println(Arrays.asList(modChildData)); // method 1
    		            System.out.println(Arrays.asList(modParentData));
    		            break;
    				 }
    				rs.close();                       
    				stmt.close();        
    				
    				
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}  
    			
    			
    		
    		
            
            
            */
            
            ///////////////////////////////////////////////////////////////////////
            // Stage Add Child Record
            //-----HashMap<String,Object> addRecordData = new HashMap<String,Object>();
            addRecordData.put(UD_KAPBASE7_UNIT_SERVER, UNIT_SERVER);
            addRecordData.put(UD_KAPBASE7_INITIAL_MENU, INITIAL_MENU);
            addRecordData.put(UD_KAPBASE7_AUTHORISED_UNIT, AUTHORISED_UNIT);
            
            
            ChildTableRecord addRecord = new ChildTableRecord();
            addRecord.setAction(ACTION.Add);
            addRecord.setChildData(addRecordData);
            modRecords.add(addRecord);
             
            // Stage Modify Child Record
            //HashMap<String,Object> modifyRecordData = new HashMap<String,Object>();
            //modifyRecordData.put(CHILD_ATTRIBUTE_NAME, "Engineer II");
            //ChildTableRecord modifyRecord = new ChildTableRecord();
            //modifyRecord.setChildData(modifyRecordData);
            //modifyRecord.setAction(ACTION.Modify);
            //modifyRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
            //modRecords.add(modifyRecord);
             
            // Stage Remove Child Record
            //HashMap<String,Object> removeRecordData = new HashMap<String,Object>();
            //ChildTableRecord removeRecord = new ChildTableRecord();
            //removeRecord.setChildData(removeRecordData);
            //removeRecord.setAction(ACTION.Delete);
            //removeRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
            //modRecords.add(removeRecord);
             
                     
           modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
            try {
				modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData);
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GenericProvisioningException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Modify resource account                
        }
 
      //  catch (Exception ex) {logger.log(ODLLevel.ERROR, "", ex);} 
 
        finally
        {
            // Logout user from OIMClient
            if (oimClient != null) { oimClient.logout();} 
        }
    }

    
    ////////////////////////////Add Data to Child table 34///////////////////////
    
    ///////////////////////////////////////////////////////////////////////////
 public static void Equation34(String group, String UserName) throws LoginException, Exception 
    
    
    {
    	
    	
	 final String RESOURCE_OBJECT_NAME = "EQUATION";
	    final String CHILD_PROCESS_FORM_NAME = "UD_KFILPK34";
	   
    
    	OIMClient oimClient = null;
 
        try
        {
        	LoginToOIM login= new LoginToOIM();
        	oimClient = login.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
 
        	/* // Set system properties required for OIMClient
            System.setProperty("java.security.auth.login.config", AUTHWL_PATH);
            System.setProperty("APPSERVER_TYPE", "wls");
            // Create an instance of OIMClient with OIM environment information
            Hashtable env = new Hashtable();
            env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL,"weblogic.jndi.WLInitialContextFactory");
            env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, OIM_PROVIDER_URL);
            oimClient = new OIMClient(env);
 
            // Login to OIM with the approriate credentials
            oimClient.login(OIM_USERNAME, OIM_PASSWORD.toCharArray());
 */            
            // Get OIM services
            provOps = oimClient.getService(ProvisioningService.class);
            usrMgrOps = oimClient.getService(UserManager.class);
             
            // Get usr_key
            String userKey = getUserKeyByUserLogin(UserName);
             
            // Get user's resource account
            Account resourceAccount = getUserResourceAccount(userKey, RESOURCE_OBJECT_NAME);
             
            // Get account's child data
            Map<String, ArrayList<ChildTableRecord>> childData = resourceAccount.getAccountData().getChildData();
           // logger.log(ODLLevel.NOTIFICATION, "Resource Account Child Data: {0}", new Object[]{childData});
            printResourceAccountChildData(childData);
             
            // Staging objects
            HashMap<String, Object> modParentData = new HashMap<String, Object>();
            Map<String, ArrayList<ChildTableRecord>> modChildData = new HashMap<String, ArrayList<ChildTableRecord>>();
            ArrayList<ChildTableRecord> modRecords = new ArrayList<ChildTableRecord>(); 
            
            
            HashMap<String,Object> addRecordData = new HashMap<String,Object>();
             //////////////////////////////DB CONNECTION///////////////////////////
           
            OIMUtils oimUtils = new OIMUtils();
            HashMap userDetail =  oimUtils.getUserDetailByLogin(UserName, oimClient);
            String BranchNo=(String) userDetail.get("Branch_Code");
            if(BranchNo==null)
            {BranchNo="-";}
    		
			 String UD_KFILPK34_PROCESS_INTER_BRAN = null;
			 String UD_KFILPK34_GROUP=null;
			 String UD_KFILPK34_INTER_BRANCH_DEBIT=null;
			 String UD_KFILPK34_RESIDENT_BRANCH=null;
			 String UD_KFILPK34_INTER_BRANCH_CREDI=null;
			 String UD_KFILPK34_BRANCH_NO=null;
			 String UD_KFILPK34_LOCAL_CREDIT_AMOUN=null;
			 String UD_KFILPK34_LOCAL_DEBIT_AMOUNT=null;
			 String UD_KFILPK34_DEBIT_AUTHORIZATIO=null;
			 String UD_KFILPK34_CREDIT_AUTHORIZATI=null;
            
    			DatabaseConnection db = new DatabaseConnection();
    			Connection con = db.connectMSCDB();
    			PreparedStatement stmt;
    			ResultSet rs;
    			
    			
    			try {
    				stmt = con.prepareStatement("Select UD_KFILPK34_PROCESS_INTER_BRAN,UD_KFILPK34_GROUP,\r\n" + 
    						"UD_KFILPK34_INTER_BRANCH_DEBIT,UD_KFILPK34_RESIDENT_BRANCH,\r\n" + 
    						"UD_KFILPK34_INTER_BRANCH_CREDI,\r\n" + 
    						"UD_KFILPK34_BRANCH_NUMBER,\r\n" + 
    						"UD_KFILPK34_LOCAL_CREDIT_AMOUN,\r\n" + 
    						"UD_KFILPK34_LOCAL_DEBIT_AMOUNT,\r\n" + 
    						"UD_KFILPK34_DEBIT_AUTHORIZATIO,\r\n" + 
    						"UD_KFILPK34_CREDIT_AUTHORIZATI\r\n" + 
    						"\r\n" + 
    						"from EQU_GROUP_DATA_WDPF  where Group_NAME =?");
    				
    				stmt.setString(1,group); 
    				int i=1;
    				rs = stmt.executeQuery();         
    				while (rs.next()) { 
    					
    							 UD_KFILPK34_PROCESS_INTER_BRAN = rs.getString(1);
    	                         UD_KFILPK34_GROUP = rs.getString(2);
    	                         UD_KFILPK34_INTER_BRANCH_DEBIT = rs.getString(3);
    	                         UD_KFILPK34_RESIDENT_BRANCH = BranchNo;
    	                         UD_KFILPK34_INTER_BRANCH_CREDI =rs.getString(5);
    	                         UD_KFILPK34_BRANCH_NO =BranchNo;
    	                         UD_KFILPK34_LOCAL_CREDIT_AMOUN =rs.getString(7);
    	                         UD_KFILPK34_LOCAL_DEBIT_AMOUNT =rs.getString(8);
    	                         UD_KFILPK34_DEBIT_AUTHORIZATIO =rs.getString(9);
    	                         UD_KFILPK34_CREDIT_AUTHORIZATI =rs.getString(10);
    					
    					addRecordData.put("UD_KFILPK34_PROCESS_INTER_BRAN", rs.getString(1));
    					addRecordData.put("UD_KFILPK34_GROUP", rs.getString(2));
    					addRecordData.put("UD_KFILPK34_INTER_BRANCH_DEBIT", rs.getString(3));
    					addRecordData.put("UD_KFILPK34_RESIDENT_BRANCH",BranchNo);
    					addRecordData.put("UD_KFILPK34_INTER_BRANCH_CREDI", rs.getString(5));
    					addRecordData.put("UD_KFILPK34_BRANCH_NO",  rs.getString(6));
    					addRecordData.put("UD_KFILPK34_LOCAL_CREDIT_AMOUN", rs.getString(7));
    					addRecordData.put("UD_KFILPK34_LOCAL_DEBIT_AMOUNT", rs.getString(8));
    					addRecordData.put("UD_KFILPK34_DEBIT_AUTHORIZATIO", rs.getString(9));
    					addRecordData.put("UD_KFILPK34_CREDIT_AUTHORIZATI", rs.getString(10));
    					System.out.println( "------------------------------"+(++i));
    					ChildTableRecord addRecord = new ChildTableRecord();
    		            addRecord.setAction(ACTION.Add);
    		            addRecord.setChildData(addRecordData);
    		            modRecords.add(addRecord);
    		            modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
    		            modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData); // Modify resource account
    		            modChildData.clear();
    		            addRecordData.clear();
    		            modParentData.clear();
    		            modRecords.clear();
    		            System.out.println("UD_KFILPK34_RESIDENT_BRANCH :"+BranchNo);
    		            System.out.println("UD_KFILPK34_BRANCH_NO"+rs.getString(6));
    		            System.out.println(Arrays.asList(userKey));
    		            System.out.println(Arrays.asList(resourceAccount));
    		            System.out.println(Arrays.asList(modChildData)); // method 1
    		            System.out.println(Arrays.asList(modParentData));
    		            
    				 }
    				
    				////////////////////////For Default Branch////////////////////
    				
    				addRecordData.put("UD_KFILPK34_PROCESS_INTER_BRAN", UD_KFILPK34_PROCESS_INTER_BRAN);
					addRecordData.put("UD_KFILPK34_GROUP", UD_KFILPK34_GROUP);
					addRecordData.put("UD_KFILPK34_INTER_BRANCH_DEBIT", UD_KFILPK34_INTER_BRANCH_DEBIT);
					addRecordData.put("UD_KFILPK34_RESIDENT_BRANCH",UD_KFILPK34_RESIDENT_BRANCH);
					addRecordData.put("UD_KFILPK34_INTER_BRANCH_CREDI", UD_KFILPK34_INTER_BRANCH_CREDI);
					addRecordData.put("UD_KFILPK34_BRANCH_NO",  UD_KFILPK34_BRANCH_NO);
					addRecordData.put("UD_KFILPK34_LOCAL_CREDIT_AMOUN", UD_KFILPK34_LOCAL_CREDIT_AMOUN);
					addRecordData.put("UD_KFILPK34_LOCAL_DEBIT_AMOUNT", UD_KFILPK34_LOCAL_DEBIT_AMOUNT);
					addRecordData.put("UD_KFILPK34_DEBIT_AUTHORIZATIO",UD_KFILPK34_DEBIT_AUTHORIZATIO);
					addRecordData.put("UD_KFILPK34_CREDIT_AUTHORIZATI", UD_KFILPK34_CREDIT_AUTHORIZATI);
					System.out.println( "------------------------------"+(++i));
					ChildTableRecord addRecord = new ChildTableRecord();
		            addRecord.setAction(ACTION.Add);
		            addRecord.setChildData(addRecordData);
		            modRecords.add(addRecord);
		            modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
		            modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData); // Modify resource account
		            modChildData.clear();
		            addRecordData.clear();
		            modParentData.clear();
		            modRecords.clear();
		            System.out.println(Arrays.asList(userKey));
		            System.out.println(Arrays.asList(resourceAccount));
		            System.out.println(Arrays.asList(modChildData)); // method 1
		            System.out.println(Arrays.asList(modParentData));
		            /////////////////////////////////////Default Branch End///////////////////////////
    				rs.close();                       
    				stmt.close();        
    				
    				
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}  
    			
    			
    		
    		
            
            
            
            
            ///////////////////////////////////////////////////////////////////////
            // Stage Add Child Record
            //-----HashMap<String,Object> addRecordData = new HashMap<String,Object>();
           //-- addRecordData.put(CHILD_ATTRIBUTE_NAME2, "170~ATMCCU");
           //-- ChildTableRecord addRecord = new ChildTableRecord();
           //-- addRecord.setAction(ACTION.Add);
           //-- addRecord.setChildData(addRecordData);
           //-- modRecords.add(addRecord);
             
            // Stage Modify Child Record
            //HashMap<String,Object> modifyRecordData = new HashMap<String,Object>();
            //modifyRecordData.put(CHILD_ATTRIBUTE_NAME, "Engineer II");
            //ChildTableRecord modifyRecord = new ChildTableRecord();
            //modifyRecord.setChildData(modifyRecordData);
            //modifyRecord.setAction(ACTION.Modify);
            //modifyRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
            //modRecords.add(modifyRecord);
             
            // Stage Remove Child Record
            //HashMap<String,Object> removeRecordData = new HashMap<String,Object>();
            //ChildTableRecord removeRecord = new ChildTableRecord();
            //removeRecord.setChildData(removeRecordData);
            //removeRecord.setAction(ACTION.Delete);
            //removeRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
            //modRecords.add(removeRecord);
             
                     
           modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
            modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData); // Modify resource account                
        }
 
      //  catch (Exception ex) {logger.log(ODLLevel.ERROR, "", ex);} 
 
        finally
        {
            // Logout user from OIMClient
            if (oimClient != null) { oimClient.logout();} 
        }
    }
 
    
    
    
 /////////////////////////////////ADD Data to AS400 CSP//////////////////////////
 
 
 
 //public void As400CSG(HashMap<Integer,String> mapData, String UserName)
 public void As400CSG(HashMap<Integer,String> mapData, String UserName)
 {
 	//final String UserName = "HARIS";
     final String RESOURCE_OBJECT_NAME = "AS400 User";
     final String CHILD_PROCESS_FORM_NAME = "UD_AS400CSG";
     
     //////////////////
     
     final String UD_AS400CSG_ID = "UD_AS400CSG_ID";
     
     //////////////////
     
     final String AS400CSG_ID = mapData.get(3);
     
     
     ///////////////////
     
     
     
 	OIMClient oimClient = null;
 	 
     try
     {
     	LoginToOIM login= new LoginToOIM();
     	try {
				oimClient = login.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
			} catch (LoginException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

     	/* // Set system properties required for OIMClient
         System.setProperty("java.security.auth.login.config", AUTHWL_PATH);
         System.setProperty("APPSERVER_TYPE", "wls");
         // Create an instance of OIMClient with OIM environment information
         Hashtable env = new Hashtable();
         env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL,"weblogic.jndi.WLInitialContextFactory");
         env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, OIM_PROVIDER_URL);
         oimClient = new OIMClient(env);

         // Login to OIM with the approriate credentials
         oimClient.login(OIM_USERNAME, OIM_PASSWORD.toCharArray());
*/            
         // Get OIM services
         provOps = oimClient.getService(ProvisioningService.class);
         usrMgrOps = oimClient.getService(UserManager.class);
          
         // Get usr_key
         String userKey = null;
			try {
				userKey = getUserKeyByUserLogin(UserName);
			} catch (NoSuchUserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UserLookupException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          
         // Get user's resource account
         Account resourceAccount = null;
			try {
				resourceAccount = getUserResourceAccount(userKey, RESOURCE_OBJECT_NAME);
			} catch (UserNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (GenericProvisioningException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          
         // Get account's child data
         Map<String, ArrayList<ChildTableRecord>> childData = resourceAccount.getAccountData().getChildData();
        // logger.log(ODLLevel.NOTIFICATION, "Resource Account Child Data: {0}", new Object[]{childData});
         printResourceAccountChildData(childData);
          
         // Staging objects
         HashMap<String, Object> modParentData = new HashMap<String, Object>();
         Map<String, ArrayList<ChildTableRecord>> modChildData = new HashMap<String, ArrayList<ChildTableRecord>>();
         ArrayList<ChildTableRecord> modRecords = new ArrayList<ChildTableRecord>(); 
         
         
         HashMap<String,Object> addRecordData = new HashMap<String,Object>();
          //////////////////////////////DB CONNECTION///////////////////////////
         
 		/*
 			DatabaseConnection db = new DatabaseConnection();
 			Connection con = db.connectMSCDB();
 			PreparedStatement stmt;
 			ResultSet rs;
 			
 			
 			try {
 				stmt = con.prepareStatement("Select UD_KFILPK34_PROCESS_INTER_BRAN,UD_KFILPK34_GROUP,\r\n" + 
 						"UD_KFILPK34_INTER_BRANCH_DEBIT,UD_KFILPK34_RESIDENT_BRANCH,\r\n" + 
 						"UD_KFILPK34_INTER_BRANCH_CREDI,\r\n" + 
 						"UD_KFILPK34_BRANCH_NUMBER,\r\n" + 
 						"UD_KFILPK34_LOCAL_CREDIT_AMOUN,\r\n" + 
 						"UD_KFILPK34_LOCAL_DEBIT_AMOUNT,\r\n" + 
 						"UD_KFILPK34_DEBIT_AUTHORIZATIO,\r\n" + 
 						"UD_KFILPK34_CREDIT_AUTHORIZATI\r\n" + 
 						"\r\n" + 
 						"from EQU_REQUEST_DATA  where Group_NAME =?");
 				
 				stmt.setString(1,"170~ATMCCU"); 
 				int i=1;
 				rs = stmt.executeQuery();         
 				while (rs.next()) { 
 					
 					addRecordData.put("UD_KFILPK34_PROCESS_INTER_BRAN", rs.getString(1));
 					addRecordData.put("UD_KFILPK34_GROUP", rs.getString(2));
 					addRecordData.put("UD_KFILPK34_INTER_BRANCH_DEBIT", rs.getString(3));
 					addRecordData.put("UD_KFILPK34_RESIDENT_BRANCH", rs.getString(4));
 					addRecordData.put("UD_KFILPK34_INTER_BRANCH_CREDI", rs.getString(5));
 					addRecordData.put("UD_KFILPK34_BRANCH_NUMBER", rs.getString(6));
 					addRecordData.put("UD_KFILPK34_LOCAL_CREDIT_AMOUN", rs.getString(7));
 					addRecordData.put("UD_KFILPK34_LOCAL_DEBIT_AMOUNT", rs.getString(8));
 					addRecordData.put("UD_KFILPK34_DEBIT_AUTHORIZATIO", rs.getString(9));
 					addRecordData.put("UD_KFILPK34_CREDIT_AUTHORIZATI", rs.getString(10));
 					System.out.println( "------------------------------"+(++i));
 					ChildTableRecord addRecord = new ChildTableRecord();
 		            addRecord.setAction(ACTION.Add);
 		            addRecord.setChildData(addRecordData);
 		            modRecords.add(addRecord);
 		            modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
 		            modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData); // Modify resource account
 		            modChildData.clear();
 		            addRecordData.clear();
 		            modParentData.clear();
 		            modRecords.clear();
 		            System.out.println(Arrays.asList(userKey));
 		            System.out.println(Arrays.asList(resourceAccount));
 		            System.out.println(Arrays.asList(modChildData)); // method 1
 		            System.out.println(Arrays.asList(modParentData));
 		            break;
 				 }
 				rs.close();                       
 				stmt.close();        
 				
 				
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}  
 			
 			
 		
 		
         
         
         */
         
         ///////////////////////////////////////////////////////////////////////
         // Stage Add Child Record
         //-----HashMap<String,Object> addRecordData = new HashMap<String,Object>();
         addRecordData.put(UD_AS400CSG_ID,AS400CSG_ID);
         
         
         ChildTableRecord addRecord = new ChildTableRecord();
         addRecord.setAction(ACTION.Add);
         addRecord.setChildData(addRecordData);
         modRecords.add(addRecord);
          
         // Stage Modify Child Record
         //HashMap<String,Object> modifyRecordData = new HashMap<String,Object>();
         //modifyRecordData.put(CHILD_ATTRIBUTE_NAME, "Engineer II");
         //ChildTableRecord modifyRecord = new ChildTableRecord();
         //modifyRecord.setChildData(modifyRecordData);
         //modifyRecord.setAction(ACTION.Modify);
         //modifyRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
         //modRecords.add(modifyRecord);
          
         // Stage Remove Child Record
         //HashMap<String,Object> removeRecordData = new HashMap<String,Object>();
         //ChildTableRecord removeRecord = new ChildTableRecord();
         //removeRecord.setChildData(removeRecordData);
         //removeRecord.setAction(ACTION.Delete);
         //removeRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
         //modRecords.add(removeRecord);
          
                  
        modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
         try {
				modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData);
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GenericProvisioningException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Modify resource account                
     }

   //  catch (Exception ex) {logger.log(ODLLevel.ERROR, "", ex);} 

     finally
     {
         // Logout user from OIMClient
         if (oimClient != null) { oimClient.logout();} 
     }
 }

//////////////////////////////////////AS400 CSP//////////////////////////////////
 public void As400CSP(String CSP_ID, String UserName)
 {
 	//final String USER_LOGIN = "HARIS";
     final String RESOURCE_OBJECT_NAME = "AS400 User";
     final String CHILD_PROCESS_FORM_NAME = "UD_AS400CSP";
     
     //////////////////
     
     final String UD_AS400CSP_ID = "UD_AS400CSP_ID";
     
     //////////////////
     
     //final String AS400CSP_ID = "*AUDIT";
     final String AS400CSP_ID = CSP_ID;
     
     
     ///////////////////
     
     
     
 	OIMClient oimClient = null;
 	 
     try
     {
     	LoginToOIM login= new LoginToOIM();
     	try {
				oimClient = login.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
			} catch (LoginException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

     	/* // Set system properties required for OIMClient
         System.setProperty("java.security.auth.login.config", AUTHWL_PATH);
         System.setProperty("APPSERVER_TYPE", "wls");
         // Create an instance of OIMClient with OIM environment information
         Hashtable env = new Hashtable();
         env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL,"weblogic.jndi.WLInitialContextFactory");
         env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, OIM_PROVIDER_URL);
         oimClient = new OIMClient(env);

         // Login to OIM with the approriate credentials
         oimClient.login(OIM_USERNAME, OIM_PASSWORD.toCharArray());
*/            
         // Get OIM services
         provOps = oimClient.getService(ProvisioningService.class);
         usrMgrOps = oimClient.getService(UserManager.class);
          
         // Get usr_key
         String userKey = null;
			try {
				userKey = getUserKeyByUserLogin(UserName);
			} catch (NoSuchUserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UserLookupException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          
         // Get user's resource account
         Account resourceAccount = null;
			try {
				resourceAccount = getUserResourceAccount(userKey, RESOURCE_OBJECT_NAME);
			} catch (UserNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (GenericProvisioningException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          
         // Get account's child data
         Map<String, ArrayList<ChildTableRecord>> childData = resourceAccount.getAccountData().getChildData();
        // logger.log(ODLLevel.NOTIFICATION, "Resource Account Child Data: {0}", new Object[]{childData});
         printResourceAccountChildData(childData);
          
         // Staging objects
         HashMap<String, Object> modParentData = new HashMap<String, Object>();
         Map<String, ArrayList<ChildTableRecord>> modChildData = new HashMap<String, ArrayList<ChildTableRecord>>();
         ArrayList<ChildTableRecord> modRecords = new ArrayList<ChildTableRecord>(); 
         
         
         HashMap<String,Object> addRecordData = new HashMap<String,Object>();
          //////////////////////////////DB CONNECTION///////////////////////////
         
 		/*
 			DatabaseConnection db = new DatabaseConnection();
 			Connection con = db.connectMSCDB();
 			PreparedStatement stmt;
 			ResultSet rs;
 			
 			
 			try {
 				stmt = con.prepareStatement("Select UD_KFILPK34_PROCESS_INTER_BRAN,UD_KFILPK34_GROUP,\r\n" + 
 						"UD_KFILPK34_INTER_BRANCH_DEBIT,UD_KFILPK34_RESIDENT_BRANCH,\r\n" + 
 						"UD_KFILPK34_INTER_BRANCH_CREDI,\r\n" + 
 						"UD_KFILPK34_BRANCH_NUMBER,\r\n" + 
 						"UD_KFILPK34_LOCAL_CREDIT_AMOUN,\r\n" + 
 						"UD_KFILPK34_LOCAL_DEBIT_AMOUNT,\r\n" + 
 						"UD_KFILPK34_DEBIT_AUTHORIZATIO,\r\n" + 
 						"UD_KFILPK34_CREDIT_AUTHORIZATI\r\n" + 
 						"\r\n" + 
 						"from EQU_REQUEST_DATA  where Group_NAME =?");
 				
 				stmt.setString(1,"170~ATMCCU"); 
 				int i=1;
 				rs = stmt.executeQuery();         
 				while (rs.next()) { 
 					
 					addRecordData.put("UD_KFILPK34_PROCESS_INTER_BRAN", rs.getString(1));
 					addRecordData.put("UD_KFILPK34_GROUP", rs.getString(2));
 					addRecordData.put("UD_KFILPK34_INTER_BRANCH_DEBIT", rs.getString(3));
 					addRecordData.put("UD_KFILPK34_RESIDENT_BRANCH", rs.getString(4));
 					addRecordData.put("UD_KFILPK34_INTER_BRANCH_CREDI", rs.getString(5));
 					addRecordData.put("UD_KFILPK34_BRANCH_NUMBER", rs.getString(6));
 					addRecordData.put("UD_KFILPK34_LOCAL_CREDIT_AMOUN", rs.getString(7));
 					addRecordData.put("UD_KFILPK34_LOCAL_DEBIT_AMOUNT", rs.getString(8));
 					addRecordData.put("UD_KFILPK34_DEBIT_AUTHORIZATIO", rs.getString(9));
 					addRecordData.put("UD_KFILPK34_CREDIT_AUTHORIZATI", rs.getString(10));
 					System.out.println( "------------------------------"+(++i));
 					ChildTableRecord addRecord = new ChildTableRecord();
 		            addRecord.setAction(ACTION.Add);
 		            addRecord.setChildData(addRecordData);
 		            modRecords.add(addRecord);
 		            modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
 		            modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData); // Modify resource account
 		            modChildData.clear();
 		            addRecordData.clear();
 		            modParentData.clear();
 		            modRecords.clear();
 		            System.out.println(Arrays.asList(userKey));
 		            System.out.println(Arrays.asList(resourceAccount));
 		            System.out.println(Arrays.asList(modChildData)); // method 1
 		            System.out.println(Arrays.asList(modParentData));
 		            break;
 				 }
 				rs.close();                       
 				stmt.close();        
 				
 				
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}  
 			
 			
 		
 		
         
         
         */
         
         ///////////////////////////////////////////////////////////////////////
         // Stage Add Child Record
         //-----HashMap<String,Object> addRecordData = new HashMap<String,Object>();
         addRecordData.put(UD_AS400CSP_ID,AS400CSP_ID);
         
         
         ChildTableRecord addRecord = new ChildTableRecord();
         addRecord.setAction(ACTION.Add);
         addRecord.setChildData(addRecordData);
         modRecords.add(addRecord);
          
         // Stage Modify Child Record
         //HashMap<String,Object> modifyRecordData = new HashMap<String,Object>();
         //modifyRecordData.put(CHILD_ATTRIBUTE_NAME, "Engineer II");
         //ChildTableRecord modifyRecord = new ChildTableRecord();
         //modifyRecord.setChildData(modifyRecordData);
         //modifyRecord.setAction(ACTION.Modify);
         //modifyRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
         //modRecords.add(modifyRecord);
          
         // Stage Remove Child Record
         //HashMap<String,Object> removeRecordData = new HashMap<String,Object>();
         //ChildTableRecord removeRecord = new ChildTableRecord();
         //removeRecord.setChildData(removeRecordData);
         //removeRecord.setAction(ACTION.Delete);
         //removeRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
         //modRecords.add(removeRecord);
          
                  
        modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
         try {
				modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData);
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GenericProvisioningException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Modify resource account                
     }

   //  catch (Exception ex) {logger.log(ODLLevel.ERROR, "", ex);} 

     finally
     {
         // Logout user from OIMClient
         if (oimClient != null) { oimClient.logout();} 
     }
 }

//////////////////////////////////Equation Group/////////////////////////////////////////////////
 
 public void equationGroup(String option, String UserName)
 {
 	//final String USER_LOGIN = "HARIS";
     final String RESOURCE_OBJECT_NAME = "EQGROUP";
     final String CHILD_PROCESS_FORM_NAME = "UD_KFILPK25";
     
     //////////////////
     
     final String UD_OPTION_ID = "UD_KFILPK25_OPTION_ID";
     
     final String UD_KFILPK25_DATE_LAST_MAINTAINED = "UD_KFILPK25_DATE_LAST_MAINTAIN";
     												 
     //////////////////
     
     //final String AS400CSP_ID = "*AUDIT";
     final String OPTION_ID = option;
     final String DATE_LAST_MAINTAINED = "0";
     
     
     ///////////////////
     
     
     
 	OIMClient oimClient = null;
 	 
     try
     {
     	LoginToOIM login= new LoginToOIM();
     	try {
				oimClient = login.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
			} catch (LoginException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

     	/* // Set system properties required for OIMClient
         System.setProperty("java.security.auth.login.config", AUTHWL_PATH);
         System.setProperty("APPSERVER_TYPE", "wls");
         // Create an instance of OIMClient with OIM environment information
         Hashtable env = new Hashtable();
         env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL,"weblogic.jndi.WLInitialContextFactory");
         env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, OIM_PROVIDER_URL);
         oimClient = new OIMClient(env);

         // Login to OIM with the approriate credentials
         oimClient.login(OIM_USERNAME, OIM_PASSWORD.toCharArray());
*/            
         // Get OIM services
         provOps = oimClient.getService(ProvisioningService.class);
         usrMgrOps = oimClient.getService(UserManager.class);
          
         // Get usr_key
         String userKey = null;
			try {
				userKey = getUserKeyByUserLogin(UserName);
			} catch (NoSuchUserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UserLookupException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          
         // Get user's resource account
         Account resourceAccount = null;
			try {
				resourceAccount = getUserResourceAccount(userKey, RESOURCE_OBJECT_NAME);
			} catch (UserNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (GenericProvisioningException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          
         // Get account's child data
         Map<String, ArrayList<ChildTableRecord>> childData = resourceAccount.getAccountData().getChildData();
        // logger.log(ODLLevel.NOTIFICATION, "Resource Account Child Data: {0}", new Object[]{childData});
         printResourceAccountChildData(childData);
          
         // Staging objects
         HashMap<String, Object> modParentData = new HashMap<String, Object>();
         Map<String, ArrayList<ChildTableRecord>> modChildData = new HashMap<String, ArrayList<ChildTableRecord>>();
         ArrayList<ChildTableRecord> modRecords = new ArrayList<ChildTableRecord>(); 
         
         
         HashMap<String,Object> addRecordData = new HashMap<String,Object>();
          //////////////////////////////DB CONNECTION///////////////////////////
         
 		/*
 			DatabaseConnection db = new DatabaseConnection();
 			Connection con = db.connectMSCDB();
 			PreparedStatement stmt;
 			ResultSet rs;
 			
 			
 			try {
 				stmt = con.prepareStatement("Select UD_KFILPK34_PROCESS_INTER_BRAN,UD_KFILPK34_GROUP,\r\n" + 
 						"UD_KFILPK34_INTER_BRANCH_DEBIT,UD_KFILPK34_RESIDENT_BRANCH,\r\n" + 
 						"UD_KFILPK34_INTER_BRANCH_CREDI,\r\n" + 
 						"UD_KFILPK34_BRANCH_NUMBER,\r\n" + 
 						"UD_KFILPK34_LOCAL_CREDIT_AMOUN,\r\n" + 
 						"UD_KFILPK34_LOCAL_DEBIT_AMOUNT,\r\n" + 
 						"UD_KFILPK34_DEBIT_AUTHORIZATIO,\r\n" + 
 						"UD_KFILPK34_CREDIT_AUTHORIZATI\r\n" + 
 						"\r\n" + 
 						"from EQU_REQUEST_DATA  where Group_NAME =?");
 				
 				stmt.setString(1,"170~ATMCCU"); 
 				int i=1;
 				rs = stmt.executeQuery();         
 				while (rs.next()) { 
 					
 					addRecordData.put("UD_KFILPK34_PROCESS_INTER_BRAN", rs.getString(1));
 					addRecordData.put("UD_KFILPK34_GROUP", rs.getString(2));
 					addRecordData.put("UD_KFILPK34_INTER_BRANCH_DEBIT", rs.getString(3));
 					addRecordData.put("UD_KFILPK34_RESIDENT_BRANCH", rs.getString(4));
 					addRecordData.put("UD_KFILPK34_INTER_BRANCH_CREDI", rs.getString(5));
 					addRecordData.put("UD_KFILPK34_BRANCH_NUMBER", rs.getString(6));
 					addRecordData.put("UD_KFILPK34_LOCAL_CREDIT_AMOUN", rs.getString(7));
 					addRecordData.put("UD_KFILPK34_LOCAL_DEBIT_AMOUNT", rs.getString(8));
 					addRecordData.put("UD_KFILPK34_DEBIT_AUTHORIZATIO", rs.getString(9));
 					addRecordData.put("UD_KFILPK34_CREDIT_AUTHORIZATI", rs.getString(10));
 					System.out.println( "------------------------------"+(++i));
 					ChildTableRecord addRecord = new ChildTableRecord();
 		            addRecord.setAction(ACTION.Add);
 		            addRecord.setChildData(addRecordData);
 		            modRecords.add(addRecord);
 		            modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
 		            modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData); // Modify resource account
 		            modChildData.clear();
 		            addRecordData.clear();
 		            modParentData.clear();
 		            modRecords.clear();
 		            System.out.println(Arrays.asList(userKey));
 		            System.out.println(Arrays.asList(resourceAccount));
 		            System.out.println(Arrays.asList(modChildData)); // method 1
 		            System.out.println(Arrays.asList(modParentData));
 		            break;
 				 }
 				rs.close();                       
 				stmt.close();        
 				
 				
 			} catch (SQLException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}  
 			
 			
 		
 		
         
         
         */
         
         ///////////////////////////////////////////////////////////////////////
         // Stage Add Child Record
         //-----HashMap<String,Object> addRecordData = new HashMap<String,Object>();
         
         addRecordData.put(UD_OPTION_ID,OPTION_ID);
         addRecordData.put(UD_KFILPK25_DATE_LAST_MAINTAINED,DATE_LAST_MAINTAINED);
         
         
         ChildTableRecord addRecord = new ChildTableRecord();
         addRecord.setAction(ACTION.Add);
         addRecord.setChildData(addRecordData);
         modRecords.add(addRecord);
          
         // Stage Modify Child Record
         //HashMap<String,Object> modifyRecordData = new HashMap<String,Object>();
         //modifyRecordData.put(CHILD_ATTRIBUTE_NAME, "Engineer II");
         //ChildTableRecord modifyRecord = new ChildTableRecord();
         //modifyRecord.setChildData(modifyRecordData);
         //modifyRecord.setAction(ACTION.Modify);
         //modifyRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
         //modRecords.add(modifyRecord);
          
         // Stage Remove Child Record
         //HashMap<String,Object> removeRecordData = new HashMap<String,Object>();
         //ChildTableRecord removeRecord = new ChildTableRecord();
         //removeRecord.setChildData(removeRecordData);
         //removeRecord.setAction(ACTION.Delete);
         //removeRecord.setRowKey(getChildRecordKeyByValue(CHILD_PROCESS_FORM_NAME, CHILD_ATTRIBUTE_NAME,  "Engineer", resourceAccount)); // <UD_TABLE>_KEY (Child Record Key)
         //modRecords.add(removeRecord);
          
                  
        modChildData.put(CHILD_PROCESS_FORM_NAME, modRecords); // Put Child Form Name and its modified child data
         try {
				modifyUserResourceAccountParentData(userKey, resourceAccount, modParentData, modChildData);
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GenericProvisioningException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Modify resource account                
     }

   //  catch (Exception ex) {logger.log(ODLLevel.ERROR, "", ex);} 

     finally
     {
         // Logout user from OIMClient
         if (oimClient != null) { oimClient.logout();} 
     }
 }

 ////////////////////////////////////////////////////////////////////////////////
    
    
    
    
}