package com.hbl.provisioning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hbl.selfservice.LoginToOIM;

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
public class UpdateChildProcessForm
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
    public static final String USER_LOGIN = "adeel";
    public static final String RESOURCE_OBJECT_NAME = "Equation";
    public static final String CHILD_PROCESS_FORM_NAME = "UD_KFILPK34";
    public static final String CHILD_ATTRIBUTE_NAME1 = "UD_KFILPK34_BRANCH_NUMBER";
    public static final String CHILD_ATTRIBUTE_NAME2 = "UD_KFILPK34_GROUP";
    
    //// CUstom Fields
    public static final String UD_KFILPK34_PROCESS_INTER_BRAN="UD_KFILPK34_PROCESS_INTER_BRAN";
    public static final String UD_KFILPK34_GROUP="UD_KFILPK34_GROUP";          
    public static final String UD_KFILPK34_INTER_BRANCH_DEBIT="UD_KFILPK34_INTER_BRANCH_DEBIT";
    public static final String UD_KFILPK34_RESIDENT_BRANCH="UD_KFILPK34_RESIDENT_BRANCH"; 
    public static final String UD_KFILPK34_INTER_BRANCH_CREDI="UD_KFILPK34_INTER_BRANCH_CREDI";
    public static final String UD_KFILPK34_BRANCH_NUMBER="UD_KFILPK34_BRANCH_NUMBER";
    public static final String UD_KFILPK34_LOCAL_CREDIT_AMOUN="UD_KFILPK34_LOCAL_CREDIT_AMOUN";
    public static final String UD_KFILPK34_LOCAL_DEBIT_AMOUNT="UD_KFILPK34_LOCAL_DEBIT_AMOUNT";
    public static final String UD_KFILPK34_DEBIT_AUTHORIZATIO="UD_KFILPK34_DEBIT_AUTHORIZATIO";
    public static final String UD_KFILPK34_CREDIT_AUTHORIZATI="UD_KFILPK34_CREDIT_AUTHORIZATI";
    
    /////
    
    
    //public static final ODLLogger logger = ODLLogger.getODLLogger(UpdateResoureProcessForm.class.getName());
    public static ProvisioningService provOps = null;
    public static UserManager usrMgrOps = null;
     
    public static void main (String[] args) 
    {
        OIMClient oimClient = null;
 
        try
        {
            // Set system properties required for OIMClient
            LoginToOIM logOim = new LoginToOIM();
 
            // Login to OIM with the approriate credentials
            oimClient = logOim.loggedIntoOIM("XELSYSADM", "Hblpoc_1234");
             
            // Get OIM services
            provOps = oimClient.getService(ProvisioningService.class);
            usrMgrOps = oimClient.getService(UserManager.class);
             
            // Get usr_key
            String userKey = "42021";//getUserKeyByUserLogin(USER_LOGIN);
             
            // Get user's resource account
            Account resourceAccount = getUserResourceAccount(userKey, RESOURCE_OBJECT_NAME);
             
            // Get account's child data
            Map<String, ArrayList<ChildTableRecord>> childData = resourceAccount.getAccountData().getChildData();
            //logger.log(ODLLevel.NOTIFICATION, "Resource Account Child Data: {0}", new Object[]{childData});
            printResourceAccountChildData(childData);
             
            // Staging objects
            HashMap<String, Object> modParentData = new HashMap<String, Object>();
            Map<String, ArrayList<ChildTableRecord>> modChildData = new HashMap<String, ArrayList<ChildTableRecord>>();
            ArrayList<ChildTableRecord> modRecords = new ArrayList<ChildTableRecord>(); 
             
            // Stage Add Child Record
            HashMap<String,Object> addRecordData = new HashMap<String,Object>();
            addRecordData.put(UD_KFILPK34_GROUP, "170~CONLON");
            addRecordData.put(UD_KFILPK34_BRANCH_NUMBER, "1000");
            addRecordData.put(UD_KFILPK34_PROCESS_INTER_BRAN, "N");
            addRecordData.put(UD_KFILPK34_INTER_BRANCH_DEBIT, 10000);
            addRecordData.put(UD_KFILPK34_RESIDENT_BRANCH, "1000");
            addRecordData.put(UD_KFILPK34_INTER_BRANCH_CREDI, 22222);
            addRecordData.put(UD_KFILPK34_LOCAL_CREDIT_AMOUN, 33333);
            addRecordData.put(UD_KFILPK34_LOCAL_CREDIT_AMOUN, 20000);
            addRecordData.put(UD_KFILPK34_LOCAL_DEBIT_AMOUNT, 40000);
            addRecordData.put(UD_KFILPK34_DEBIT_AUTHORIZATIO, 10000);
            addRecordData.put(UD_KFILPK34_CREDIT_AUTHORIZATI, 30000);
  
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
            System.out.println("Added");
        }
 
        catch (Exception ex) { ex.printStackTrace();} 
 
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
            //logger.log(ODLLevel.NOTIFICATION, "[Child Form Name: {0}], [Child Form Data: {1}]", new Object[]{childFormName, childFormData});
             
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
                       // logger.log(ODLLevel.NOTIFICATION, "[Action: {0}], [Child Record Data: {1}], [Row Key: {2}]", new Object[]{action, childRecordData, rowKey});
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
            //logger.log(ODLLevel.NOTIFICATION, "Account Id: [{0}], Application Instance Name: [{1}], Account Status: [{2}], Account Data:[{3}]", new Object[]{accountId, appInstName, accountStatus, accountData});
             
            // Only return enabled, provisioned, or disabled account
            if(ProvisioningConstants.ObjectStatus.PROVISIONED.getId().equals(accountStatus) || ProvisioningConstants.ObjectStatus.ENABLED.getId().equals(accountStatus) || ProvisioningConstants.ObjectStatus.DISABLED.getId().equals(accountStatus)) 
            {
                //logger.log(ODLLevel.NOTIFICATION, "Return Account Id: [{0}]", new Object[]{accountId});
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
        //logger.log(ODLLevel.NOTIFICATION, "User Details: {0}", new Object[]{user});
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
        String processFormInstanceKey = resourceAccount.getProcessInstanceKey();
        Account modAccount = new Account(accountId, processFormInstanceKey, userKey);
       // logger.log(ODLLevel.NOTIFICATION, "Account Id: [{0}], Process Form Instance Key: [{1}]", new Object[]{accountId, processFormInstanceKey});
 
        // Setup account data object
        String formKey = resourceAccount.getAccountData().getFormKey();
        String udTablePrimaryKey = resourceAccount.getAccountData().getUdTablePrimaryKey();
        AccountData accountData = new AccountData(formKey, udTablePrimaryKey , modAttrs);
        //logger.log(ODLLevel.NOTIFICATION, "Form Key: [{0}], UD Table Primary Key: [{1}]", new Object[]{formKey, udTablePrimaryKey});
        accountData.setChildData(modChildData); // set child data
         
        // Set necessary information to modified account
        modAccount.setAccountData(accountData);
        modAccount.setAppInstance(resourceAccount.getAppInstance());
 
        // Modify resource account
        provOps.modify(modAccount);
        //logger.log(ODLLevel.NOTIFICATION, "Modification successful.");
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
            ArrayList<ChildTableRecord> childFormData = (ArrayList<ChildTableRecord>) pairs.getValue();
            //logger.log(ODLLevel.NOTIFICATION, "[Child Form Name: {0}], [Child Form Data: {1}]", new Object[]{childFormName, childFormData});
             
            // Iterate records in a child form
            for (ChildTableRecord record : childFormData) 
            {
                ACTION action = record.getAction();
                Map<String, Object> childRecordData = record.getChildData();
                String rowKey = record.getRowKey();
                //logger.log(ODLLevel.NOTIFICATION, "[Action: {0}], [Child Record Data: {1}], [Row Key: {2}]", new Object[]{action, childRecordData, rowKey});
            }
        }
    }   
}
