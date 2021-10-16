package com.hbl.equation.limits.servlet;

import java.util.Date;

public class LimitRequestObj {
	int requestId =0;
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public String getREQUEST_TYPE() {
		return REQUEST_TYPE;
	}
	public void setREQUEST_TYPE(String rEQUEST_TYPE) {
		REQUEST_TYPE = rEQUEST_TYPE;
	}
	public String getREQUEST_STATUS() {
		return REQUEST_STATUS;
	}
	public void setREQUEST_STATUS(String rEQUEST_STATUS) {
		REQUEST_STATUS = rEQUEST_STATUS;
	}
	public String getAPPROVER_LEVEL() {
		return APPROVER_LEVEL;
	}
	public void setAPPROVER_LEVEL(String aPPROVER_LEVEL) {
		APPROVER_LEVEL = aPPROVER_LEVEL;
	}
	public String getGROUP_NAME() {
		return GROUP_NAME;
	}
	public void setGROUP_NAME(String gROUP_NAME) {
		GROUP_NAME = gROUP_NAME;
	}
	public String getSTATE() {
		return STATE;
	}
	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getUSER_KEY() {
		return USER_KEY;
	}
	public void setUSER_KEY(String uSER_KEY) {
		USER_KEY = uSER_KEY;
	}
	public String getREQUESTER() {
		return REQUESTER;
	}
	public void setREQUESTER(String rEQUESTER) {
		REQUESTER = rEQUESTER;
	}
	public String getFIRST_LEV_APPRVER() {
		return FIRST_LEV_APPRVER;
	}
	public void setFIRST_LEV_APPRVER(String fIRST_LEV_APPRVER) {
		FIRST_LEV_APPRVER = fIRST_LEV_APPRVER;
	}
	public String getSEC_LEV_APPRVER() {
		return SEC_LEV_APPRVER;
	}
	public void setSEC_LEV_APPRVER(String sEC_LEV_APPRVER) {
		SEC_LEV_APPRVER = sEC_LEV_APPRVER;
	}
	public String getTHIRD_LEV_APPRVER() {
		return THIRD_LEV_APPRVER;
	}
	public void setTHIRD_LEV_APPRVER(String tHIRD_LEV_APPRVER) {
		THIRD_LEV_APPRVER = tHIRD_LEV_APPRVER;
	}
	public String getCURRENT_APPROVER() {
		return CURRENT_APPROVER;
	}
	public void setCURRENT_APPROVER(String cURRENT_APPROVER) {
		CURRENT_APPROVER = cURRENT_APPROVER;
	}
	String REQUEST_TYPE = null;
	String REQUEST_STATUS =null;
	String APPROVER_LEVEL = null;
	String GROUP_NAME = null;
	String STATE = null;
	String STATUS = null;
	String USER_KEY = null;
	String REQUESTER = null;
	String FIRST_LEV_APPRVER = null;
	String SEC_LEV_APPRVER = null;
	String THIRD_LEV_APPRVER = null;
	String CURRENT_APPROVER = null;
	Date CREATE_DATE = null;
	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public Date getUPDATE_DATE() {
		return UPDATE_DATE;
	}
	public void setUPDATE_DATE(Date uPDATE_DATE) {
		UPDATE_DATE = uPDATE_DATE;
	}
	Date UPDATE_DATE = null;
	
}
