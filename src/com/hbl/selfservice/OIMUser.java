package com.hbl.selfservice;

import oracle.iam.platform.OIMClient;

public class OIMUser {

	private Long userKey = null;
	public Long getUserKey() {
		return userKey;
	}
	public void setUserKey(Long userKey) {
		this.userKey = userKey;
	}
	public String getUserlogin() {
		 
		return userlogin;
	}
	public void setUserlogin(String userlogin) {
		this.userlogin = userlogin;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getUserMiddleName() {
		return userMiddleName;
	}
	public void setUserMiddleName(String userMiddleName) {
		this.userMiddleName = userMiddleName;
	}
	public String getUserDisplayName() {
		return userDisplayName;
	}
	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserOffice() {
		return userOffice;
	}
	public void setUserOffice(String userOffice) {
		this.userOffice = userOffice;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDepartmentNumber() {
		return department;
	}
	public void setDepartmentNumber(String department) {
		this.department = department;
	}
	public String getUserLineManager() {
		return userLineManager;
	}
	public void setUserLineManager(String userLineManager) {
		this.userLineManager = userLineManager;
	}
	public String getUserLineManagerEmail() {
		return userLineManagerEmail;
	}
	public void setUserLineManagerEmail(String userLineManagerEmail) {
		this.userLineManagerEmail = userLineManagerEmail;
	}
	public String getLineManagerLogin() {
		return lineManagerLogin;
	}
	public void setLineManagerLogin(String lineManagerLogin) {
		this.lineManagerLogin = lineManagerLogin;
	}
	public String getLineManagerUserkey() {
		return lineManagerUserkey;
	}
	public void setLineManagerUserkey(String lineManagerUserkey) {
		this.lineManagerUserkey = lineManagerUserkey;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		System.out.println("mobileNumber:: "+ mobileNumber);
		this.mobileNumber = mobileNumber;
	}
	public String getAct_key() {
		return act_key;
	}
	public void setAct_key(String act_key) {
		this.act_key = act_key;
	}
	public String getLineManagerKey() {
		return lineManagerKey;
	}
	public void setLineManagerKey(String lineManagerKey) {
		this.lineManagerKey = lineManagerKey;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranch_Code() {
		return branch_Code;
	}
	public void setBranch_Code(String branch_Code) {
		this.branch_Code = branch_Code;
	}
	public String getPersonalNumber() {
		return personalNumber;
	}
	public void setPersonalNumber(String personalNumber) {
		this.personalNumber = personalNumber;
	}
	public String getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	private String userlogin = null;
	public OIMUser() {
		super();
		this.userKey = null;
		this.userlogin = null;
		this.userFirstName = null;
		this.userLastName = null;
		this.userMiddleName = null;
		this.userDisplayName = null;
		this.userEmail = null;
		this.userOffice = null;
		this.department = null;
		this.userLineManager = null;
		this.userLineManagerEmail = null;
		this.lineManagerLogin = null;
		this.lineManagerUserkey = null;
		this.mobileNumber = null;
		this.act_key = null;
		this.lineManagerKey = null;
		this.userRole = null;
		this.branchName = null;
		this.branch_Code = null;
		this.personalNumber = null;
		this.branchAddress = null;
		this.organization= null;
	}
	private String userFirstName = null;
	private String userLastName = null;
	private String userMiddleName = null;
	private String userDisplayName = null;
	private String userEmail =null;
	private String userOffice = null;
	private String department=null;
	private String userLineManager =null;
	private String userLineManagerEmail =null;
	private String lineManagerLogin =null;
	private String lineManagerUserkey = null;
	private String mobileNumber = null;
	private String act_key =null;
	private String lineManagerKey =null;
	private String userRole = null;
	private String branchName = null;
	private String branch_Code = null;
	private String personalNumber = null;
	private String branchAddress =null;
	private String organization =null;
	
}
