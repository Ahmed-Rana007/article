package com.hbl.selfservice.objects;

import java.util.Date;

public class EntitlementObj {

	private Long entitlementKey = null;
	private String entitlementName =null;
	
	public String getEntitlementName() {
		return entitlementName;
	}
	public void setEntitlementName(String entitlementName) {
		this.entitlementName = entitlementName;
	}
	public Long getEntitlementKey() {
		return entitlementKey;
	}
	public void setEntitlementKey(Long entitlementKey) {
		this.entitlementKey = entitlementKey;
	}
	public Long getAccountKey() {
		return accountKey;
	}
	public void setAccountKey(Long accountKey) {
		this.accountKey = accountKey;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getAppInstanceName() {
		return appInstanceName;
	}
	public void setAppInstanceName(String appInstanceName) {
		this.appInstanceName = appInstanceName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getProvisionedOn() {
		return provisionedOn;
	}
	public void setProvisionedOn(Date provisionedOn) {
		this.provisionedOn = provisionedOn;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	private Long accountKey = null;
	private String resource = null;
	private String appInstanceName = null;
	private String accountName = null;
	private String status = null;
	private Date provisionedOn = null;
	private String requestId = null;
	private Date startDate = null;
	private Date endDate = null;
	private String formName =null;

	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
}
