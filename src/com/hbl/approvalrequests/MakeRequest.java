package com.hbl.approvalrequests;

import java.util.ArrayList;
import java.util.List;

import com.hbl.provisioning.AccountCart;
import com.hbl.selfservice.OIMUtils;

import oracle.iam.api.OIMService;
import oracle.iam.exception.OIMServiceException;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.utils.vo.OIMType;
import oracle.iam.request.vo.Beneficiary;
import oracle.iam.request.vo.RequestBeneficiaryEntity;
import oracle.iam.request.vo.RequestBeneficiaryEntityAttribute;
import oracle.iam.request.vo.RequestConstants;
import oracle.iam.request.vo.RequestData;
import oracle.iam.vo.OperationResult;

public class MakeRequest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private RequestBeneficiaryEntity createEntity(String entitySubType, String entityKey, OIMType OimType,
			String ModelOperation) {
		System.out.println(entitySubType + " :=:  " + entityKey);
		RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
		// requestEntity.setRequestEntityType(OIMType.ApplicationInstance);
		requestEntity.setRequestEntityType(OimType);
		requestEntity.setEntitySubType(entitySubType);
		requestEntity.setEntityKey(entityKey);
		// requestEntity.setOperation(RequestConstants.MODEL_ENABLE_ACCOUNT_OPERATION);
		requestEntity.setOperation(ModelOperation);
		System.out.println("Return RBE");
		return requestEntity;
	}
	
	private RequestBeneficiaryEntity createEntityWithData(String entitySubType, String entityKey, OIMType OimType,
			String ModelOperation, List<RequestBeneficiaryEntityAttribute> attrs ) {
		System.out.println(entitySubType + " :=:  " + entityKey);
		RequestBeneficiaryEntity requestEntity = new RequestBeneficiaryEntity();
		// requestEntity.setRequestEntityType(OIMType.ApplicationInstance);
		requestEntity.setRequestEntityType(OimType);
		requestEntity.setEntitySubType(entitySubType);
		requestEntity.setEntityKey(entityKey);
		if(attrs!=null)
			requestEntity.setEntityData(attrs);
		// requestEntity.setOperation(RequestConstants.MODEL_ENABLE_ACCOUNT_OPERATION);
		requestEntity.setOperation(ModelOperation);
		System.out.println("Return RBE");
		return requestEntity;
	}

	/// provision (AER) Account Entitlement Role Request method
	public String provisionAERRequest(OIMClient oimClient, List<AccountCart> acctInCart, String userKey,String justification) {
		System.out.println("IN provisionAERRequest:::.. " + userKey);
		OIMService unifiedService = oimClient.getService(OIMService.class);
		List<RequestBeneficiaryEntity> entities = new ArrayList<RequestBeneficiaryEntity>();
		if (acctInCart.size() > 0) {
			RequestBeneficiaryEntity requestEntity = null;
			for (AccountCart acctCrt : acctInCart) {
				System.out.println("Kashif- Name:: " + acctCrt.getAppInstacneName() + " KEY:: " + acctCrt.getAppInstacneKey());
				 if (acctCrt.getItemType() == 1 && acctCrt!=null && acctCrt.getAttrs()!=null) {
						requestEntity = createEntityWithData(acctCrt.getAppInstacneName(), acctCrt.getAppInstacneKey(),
								OIMType.ApplicationInstance,
								RequestConstants.MODEL_PROVISION_APPLICATION_INSTANCE_OPERATION,acctCrt.getAttrs());
					}
				else if (acctCrt.getItemType() == 1) {
					requestEntity = createEntity(acctCrt.getAppInstacneName(), acctCrt.getAppInstacneKey(),
							OIMType.ApplicationInstance,
							RequestConstants.MODEL_PROVISION_APPLICATION_INSTANCE_OPERATION);
				} 
				else if (acctCrt.getItemType() == 2) {
					requestEntity = createEntity("6~"+acctCrt.getAppInstacneName().toString().trim(),
							acctCrt.getAppInstacneKey().toString().trim(), OIMType.Entitlement,
							RequestConstants.MODEL_PROVISION_ENTITLEMENT_OPERATION);
				} else if (acctCrt.getItemType() == 3) {
					requestEntity = createEntity(acctCrt.getAppInstacneName().toString().trim(), acctCrt.getAppInstacneKey().toString().trim(),
							OIMType.Role, RequestConstants.MODEL_ASSIGN_ROLES_OPERATION);
				}
				entities.add(requestEntity);
			}
		}
		if (entities.size() > 0) {
			try {
				Beneficiary beneficiary = new Beneficiary();
				beneficiary.setBeneficiaryKey(userKey);
				beneficiary.setBeneficiaryType(Beneficiary.USER_BENEFICIARY);
				beneficiary.setTargetEntities(entities);
				List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
				beneficiaries.add(beneficiary);
				RequestData requestData = new RequestData();
				requestData.setJustification(justification);
				requestData.setBeneficiaries(beneficiaries);
				OperationResult result = unifiedService.doOperation(requestData, OIMService.Intent.REQUEST);
				System.out.print(result.getRequestID());
				System.out.print(result.getEntityId());
				System.out.print(result.getOperationStatus());
				return result.getRequestID().toString().trim();

			} catch (OIMServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

}
