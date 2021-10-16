package com.hbl.certifications;

public class CertifDetailsObj {
	String id = null;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getParentEntityId() {
		return parentEntityId;
	}
	public void setParentEntityId(String parentEntityId) {
		this.parentEntityId = parentEntityId;
	}
	public String getLastReviewerId() {
		return lastReviewerId;
	}
	public void setLastReviewerId(String lastReviewerId) {
		this.lastReviewerId = lastReviewerId;
	}
	public String getLastReviewerTaskUid() {
		return lastReviewerTaskUid;
	}
	public void setLastReviewerTaskUid(String lastReviewerTaskUid) {
		this.lastReviewerTaskUid = lastReviewerTaskUid;
	}
	public String getRiskSummary() {
		return riskSummary;
	}
	public void setRiskSummary(String riskSummary) {
		this.riskSummary = riskSummary;
	}
	public String getItemRisk() {
		return itemRisk;
	}
	public void setItemRisk(String itemRisk) {
		this.itemRisk = itemRisk;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getLastDecision() {
		return lastDecision;
	}
	public void setLastDecision(String lastDecision) {
		this.lastDecision = lastDecision;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getProvisionedBy() {
		return provisionedBy;
	}
	public void setProvisionedBy(String provisionedBy) {
		this.provisionedBy = provisionedBy;
	}
	public String getProvisioningMethod() {
		return provisioningMethod;
	}
	public void setProvisioningMethod(String provisioningMethod) {
		this.provisioningMethod = provisioningMethod;
	}
	public String getProvMechRisk() {
		return provMechRisk;
	}
	public void setProvMechRisk(String provMechRisk) {
		this.provMechRisk = provMechRisk;
	}
	public String getOpenSodViolations() {
		return openSodViolations;
	}
	public void setOpenSodViolations(String openSodViolations) {
		this.openSodViolations = openSodViolations;
	}
	public String getSodRisk() {
		return sodRisk;
	}
	public void setSodRisk(String sodRisk) {
		this.sodRisk = sodRisk;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCatalogType() {
		return catalogType;
	}
	public void setCatalogType(String catalogType) {
		this.catalogType = catalogType;
	}
	public String getEndPointId() {
		return endPointId;
	}
	public void setEndPointId(String endPointId) {
		this.endPointId = endPointId;
	}
	public String getEndPointName() {
		return endPointName;
	}
	public void setEndPointName(String endPointName) {
		this.endPointName = endPointName;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public String getIamId() {
		return iamId;
	}
	public void setIamId(String iamId) {
		this.iamId = iamId;
	}
	public String appID(String dis) {
		if(dis.contains("Active Directory")) {
			return "ActiveDirectory";
		}
		else if(dis.contains("Equation")) {
			return "Equation";
		}
		else if(dis.contains("Cash Portal")) {
			return "CP1";
		}
		else if(dis.contains("AS400")) {
			return "AS400";
		}	
		return dis;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	String entityId= null;
	String parentEntityId= null;
	String lastReviewerId = null;
	String lastReviewerTaskUid = null;
	String riskSummary = null;
	String itemRisk = null;
	String category = null;
	String lastDecision = null;
	String entityType = null;
	String provisionedBy = null;
	String provisioningMethod = null;
	String provMechRisk= null;
	String openSodViolations = null;
	String sodRisk = null;
	String userId = null;
	String accountId = null;
	String accountName = null;
	String displayName = null;
	String name = null;
	String catalogType = null;
	String endPointId = null;
	String endPointName = null;
	String definition = null;
	String iamId = null;
	String action = null;
	
}
