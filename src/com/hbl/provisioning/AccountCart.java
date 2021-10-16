package com.hbl.provisioning;

import java.util.List;

import oracle.iam.request.vo.RequestBeneficiaryEntityAttribute;

public class AccountCart {
	private String userLogin = null;
	private String appDisplayName= null;
	private String userKey = null;
	private String appInstacneName = null;
	private String appInstacneKey = null;
	private String tableName = null;
	private int itemType = -1;
	private List<RequestBeneficiaryEntityAttribute> attrs;
	public List<RequestBeneficiaryEntityAttribute> getAttrs() {
		return attrs;
	}
	public void setAttrs(List<RequestBeneficiaryEntityAttribute> attrs) {
		this.attrs = attrs;
	}
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	public String getAppDisplayName() {
		return appDisplayName;
	}
	public void setAppDisplayName(String appDisplayName) {
		this.appDisplayName = appDisplayName;
	}
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getAppInstacneName() {
		return appInstacneName;
	}
	public void setAppInstacneName(String appInstacneName) {
		this.appInstacneName = appInstacneName;
	}
	public String getAppInstacneKey() {
		return appInstacneKey;
	}
	public void setAppInstacneKey(String appInstacneKey) {
		this.appInstacneKey = appInstacneKey;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	
}
