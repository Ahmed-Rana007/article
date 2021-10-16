package com.hbl.selfservice;

import java.util.Map;

import oracle.iam.platform.OIMClient;

public class UserSessaion {

	static OIMClient oimClient = null;
	static Map<Long, UsersList> userList = null;
	 
	public static Map<Long, UsersList> getUserList() {
		return userList;
	}
	public static void setUserList(Map<Long, UsersList> userList) {
		UserSessaion.userList = userList;
	}
	public static OIMClient getOimClient() {
		return oimClient;
	}
	public static void setOimClient(OIMClient oimClient) {
		UserSessaion.oimClient = oimClient;
	}
	public static String getLoggedInUserKey() {
		return loggedInUserKey;
	}
	public static void setLoggedInUserKey(String loggedInUserKey) {
		UserSessaion.loggedInUserKey = loggedInUserKey;
	}
	public static String getLoggedInUserLogin() {
		return loggedInUserLogin;
	}
	public static void setLoggedInUserLogin(String loggedInUserLogin) {
		UserSessaion.loggedInUserLogin = loggedInUserLogin;
	}
	static String loggedInUserKey = null;
	static String loggedInUserLogin = null;
	static OIMUser oimUser = null;
	public static OIMUser getOimUser() {
		return oimUser;
	}
	public static void setOimUser(OIMUser oimUser) {
		UserSessaion.oimUser = oimUser;
	}
	
}
