package com.hbl.selfservice.objects;

public class OrganizationObj {
	private String organiztionActkey = null;
	public String getOrganiztionActkey() {
		return organiztionActkey;
	}
	public void setOrganiztionActkey(String organiztionActkey) {
		this.organiztionActkey = organiztionActkey;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getOrginizationDisplayName() {
		return orginizationDisplayName;
	}
	public void setOrginizationDisplayName(String orginizationDisplayName) {
		this.orginizationDisplayName = orginizationDisplayName;
	}
	public String getOrgparentkey() {
		return orgparentkey;
	}
	public void setOrgparentkey(String orgparentkey) {
		this.orgparentkey = orgparentkey;
	}
	public String getOrgParentName() {
		return orgParentName;
	}
	public void setOrgParentName(String orgParentName) {
		this.orgParentName = orgParentName;
	}
	private String organizationName =null;
	private String orginizationDisplayName = null;
	private String orgparentkey =null;
	private String orgParentName = null;
}
