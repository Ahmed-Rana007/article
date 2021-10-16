package com.hbl.selfservice.objects;

import java.util.Date;

public class RoleUser {

	private String roleDescription = null;
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescriptio) {
		this.roleDescription = roleDescriptio;
	}
	public String getRolememberShipType() {
		return rolememberShipType;
	}
	public void setRolememberShipType(String rolememberShipType) {
		this.rolememberShipType = rolememberShipType;
	}
	public Date getRoleAssigOn() {
		return roleAssigOn;
	}
	public void setRoleAssigOn(Date roleAssigOn) {
		this.roleAssigOn = roleAssigOn;
	}
	public String getRoleRequestId() {
		return roleRequestId;
	}
	public void setRoleRequestId(String roleRequestId) {
		this.roleRequestId = roleRequestId;
	}
	private String rolememberShipType = null;
	private Date roleAssigOn = null;
	private String roleRequestId = null;
	private String roleKey =null;
	public String getRoleKey() {
		return roleKey;
	}
	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}
	public String getRoleDisplayName() {
		return roleDisplayName;
	}
	public void setRoleDisplayName(String roleDisplayName) {
		this.roleDisplayName = roleDisplayName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	private String roleDisplayName = null;
	private String roleName = null;
}
