package com.hbl.selfservice.objects;

import java.util.Date;

public class AppInstanceObj {
	private String appInstanceName = null;
	private String appInstanceDisplayName = null;
	private Long appInstanceKey = null;
	public Long getAppInstanceKey() {
		return appInstanceKey;
	}
	public void setAppInstanceKey(Long appInstanceKey) {
		this.appInstanceKey = appInstanceKey;
	}
	private String resourceType =null;
	private String accountName = null;
	private String provisionedOn = null;
	private String accountStatus = null;
	private String accountType = null;
	private String accountOIUKey = null;
	private String actRequestID = null;
	private Date actStartDate = null;
	private Date actEndDate = null;
	private String userKey = null;
	private String accountId=null;
	
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAppInstanceDisplayName() {
		return appInstanceDisplayName;
	}
	public void setAppInstanceDisplayName(String appInstanceDisplayName) {
		this.appInstanceDisplayName = appInstanceDisplayName;
	}
	public String getAppInstanceName() {
		return appInstanceName;
	}
	public void setAppInstanceName(String appInstanceName) {
		this.appInstanceName = appInstanceName;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getProvisionedOn() {
		return provisionedOn;
	}
	public void setProvisionedOn(String provisionedOn) {
		this.provisionedOn = provisionedOn;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountOIUKey() {
		return accountOIUKey;
	}
	public void setAccountOIUKey(String accountOIUKey) {
		this.accountOIUKey = accountOIUKey;
	}
	public String getActRequestID() {
		return actRequestID;
	}
	public void setActRequestID(String actRequestID) {
		this.actRequestID = actRequestID;
	}
	public Date getActStartDate() {
		return actStartDate;
	}
	public void setActStartDate(Date actStartDate) {
		this.actStartDate = actStartDate;
	}
	public Date getActEndDate() {
		return actEndDate;
	}
	public void setActEndDate(Date actEndDate) {
		this.actEndDate = actEndDate;
	}
	
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
		
	
}
