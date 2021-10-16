package com.hbl.provisioning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bea.core.repackaged.springframework.dao.DataAccessException;
import com.hbl.selfservice.LoginToOIM;
import com.hbl.selfservice.objects.EntitlementObj;

import oracle.iam.exception.OIMServiceException;
import oracle.iam.identity.exception.NoSuchUserException;
import oracle.iam.identity.exception.UserLookupException;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.entitymgr.vo.SearchCriteria;
import oracle.iam.provisioning.api.EntitlementService;
import oracle.iam.provisioning.plugins.EntitlementDatasetPlugin;
import oracle.iam.provisioning.vo.Entitlement;
import oracle.iam.reconciliation.utils.Sys;
import oracle.iam.request.api.RequestService;
import oracle.iam.request.vo.Beneficiary;
import oracle.iam.request.vo.RequestBeneficiaryEntity;
import oracle.iam.request.vo.RequestConstants;
import oracle.iam.request.vo.RequestData;

public class ProvisioningEntitlements {

	public static void main(String[] args) {
		OIMClient oimClient = null;
        LoginToOIM loginToOim = new LoginToOIM();
        ProvisioningEntitlements pe = new ProvisioningEntitlements();
        try {
        	 oimClient = loginToOim.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
        	 pe.getAllEntitlements(oimClient);
        	// pe.createEntitlementRequest(oimClient);
        }catch (NoSuchUserException e) {
			e.printStackTrace();
		}catch (UserLookupException e) {
			e.printStackTrace();
		}
        catch (OIMServiceException e) {
			e.printStackTrace();
		}catch (DataAccessException e) {
			e.printStackTrace();
		}
        catch (Exception e) {
			e.printStackTrace();
		}
         

	}
	public Map<Long, EntitlementObj> getAllEntitlements(OIMClient oimClient){
        final String logp = ":: getEntitlementDetails - ";
        System.out.println(logp + "START");

         
        Map<Long, EntitlementObj> entListMap = new HashMap<Long,EntitlementObj>();
        EntitlementObj entObj = null;
        Entitlement ent = null;
        try{
            SearchCriteria criteria = new SearchCriteria(Entitlement.ENTITLEMENT_NAME, "*", SearchCriteria.Operator.BEGINS_WITH);
           
            //get entitlement service
            EntitlementService entServ = oimClient.getService(EntitlementService.class);
            List<Entitlement> entList = entServ.findEntitlements(criteria, null);
            
            if (entList.size() == 0 ) {
               System.out.println(logp + "Improper number of entitlements found for entitlement name" + entList.size());
            }else{
            		for(int i=0 ; i < entList.size() ; i++)
            		{
            			entObj = new EntitlementObj();
            			ent = entList.get(i);
                        //System.out.println(logp + "Successfully obtained entitlement - " + ent);
                        entObj.setEntitlementKey(ent.getEntitlementKey());
                        entObj.setEntitlementName(ent.getDisplayName());
                        entObj.setAccountKey(ent.getAppInstance().getApplicationInstanceKey());
                        entObj.setAppInstanceName(ent.getAppInstance().getApplicationInstanceName());
                        entListMap.put(ent.getEntitlementKey(), entObj);
                       
            			System.out.print(logp + "Entitlement Key - " + ent.getEntitlementKey());
                        System.out.print(logp + "Entitlement Display Name  - " + ent.getDisplayName() +"\n");
                        //System.out.print(logp + "IT Resource Key  - " + ent.getItResourceKey());
                        //System.out.println(logp + "IT Resource Key  - " + ent.getAppInstance().getApplicationInstanceName() +"\n");
            		}
            		return entListMap;
                    
            }
        }catch (Exception e){
        	System.out.println( e.getMessage());
        }

      
        return null;
 }
	private String createEntitlementRequest(OIMClient oimClient ) throws Exception{
		/**
		* Creating a new RequestBeneficiaryEntity object which
		* will hold all the requested entitlement related data.
		*/
		RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
		requestEntity.setRequestEntityType(oracle.iam.platform.utils.vo.OIMType.Entitlement); //Type of the Request
		requestEntity.setEntitySubType("GRPNYK"); //Name of the Entitlement
		requestEntity.setEntityKey("2863"); //Entitlement key
		
		requestEntity.setOperation(RequestConstants.MODEL_PROVISION_ENTITLEMENT_OPERATION); //Request operation type.
		System.out.println("Operation set to " + RequestConstants.MODEL_PROVISION_ENTITLEMENT_OPERATION);
		//Adding RequestBeneficiaryEntity to List
		List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
		entities.add(requestEntity);
		//creating new Beneficiary
		Beneficiary beneficiary = new Beneficiary();
		beneficiary.setBeneficiaryKey("30022"); //set BeneficiaryKey as User key
		beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY); //set the type as user
		beneficiary.setTargetEntities(entities); //set target entities as list of RequestBeneficiaryEntity
		//Adding Beneficiary to List
		List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
		beneficiaries.add(beneficiary);
		System.out.println("Beneficiaries are set to: " + beneficiaries);
		//Creating new RequestData and set the Beneficiaries with List of Beneficiaries
		RequestData requestData = new RequestData();
		requestData.setBeneficiaries(beneficiaries);
		/**
		* getRequesterConnection() is a seperate method to create OIM connection.
		*/
		  //create an OIM connection with requester's login
		RequestService requestAPI = oimClient.getService(RequestService.class);
		String requestId = requestAPI.submitRequest(requestData); //Return request ID
		System.out.println("requestId: "+requestId);
		return requestId;
		}
	 

		

}
