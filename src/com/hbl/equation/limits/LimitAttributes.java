package com.hbl.equation.limits;

public class LimitAttributes {
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setResidentBranch(String residentBranch) {
        this.residentBranch = residentBranch;
    }

    public String getResidentBranch() {
        return residentBranch;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public void setLStringtCCDate(String lStringtCCDate) {
        this.lStringtCCDate = lStringtCCDate;
    }

    public String getLStringtCCDate() {
        return lStringtCCDate;
    }

    public void setGroupName1(String groupName1) {
        this.groupName1 = groupName1;
    }

    public String getGroupName1() {
        return groupName1;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setAllBranches(String allBranches) {
        this.allBranches = allBranches;
    }

    public String getAllBranches() {
        return allBranches;
    }

    public void setSps_all_brnch(String sps_all_brnch) {
        this.sps_all_brnch = sps_all_brnch;
    }

    public String getSps_all_brnch() {
        return sps_all_brnch;
    }

    public void setLmtFrLclCrdt(String lmtFrLclCrdt) {
        this.lmtFrLclCrdt = lmtFrLclCrdt;
    }

    public String getLmtFrLclCrdt() {
        return lmtFrLclCrdt;
    }

    public void setLmtFrLclDbt(String lmtFrLclDbt) {
        this.lmtFrLclDbt = lmtFrLclDbt;
    }

    public String getLmtFrLclDbt() {
        return lmtFrLclDbt;
    }

    public void setIntrBrnCrLmt(String intrBrnCrLmt) {
        this.intrBrnCrLmt = intrBrnCrLmt;
    }

    public String getIntrBrnCrLmt() {
        return intrBrnCrLmt;
    }

    public void setIntrBrnDbtLmt(String intrBrnDbtLmt) {
        this.intrBrnDbtLmt = intrBrnDbtLmt;
    }

    public String getIntrBrnDbtLmt() {
        return intrBrnDbtLmt;
    }

    public void setAuthBrnch(String authBrnch) {
        this.authBrnch = authBrnch;
    }

    public String getAuthBrnch() {
        return authBrnch;
    }

    public void setAuthSpsfdBrn(String authSpsfdBrn) {
        this.authSpsfdBrn = authSpsfdBrn;
    }

    public String getAuthSpsfdBrn() {
        return authSpsfdBrn;
    }

    public void setAuthAmtFrLclCrdt(String authAmtFrLclCrdt) {
        this.authAmtFrLclCrdt = authAmtFrLclCrdt;
    }

    public String getAuthAmtFrLclCrdt() {
        return authAmtFrLclCrdt;
    }

    public void setAuthAmtFrLclDbt(String authAmtFrLclDbt) {
        this.authAmtFrLclDbt = authAmtFrLclDbt;
    }

    public String getAuthAmtFrLclDbt() {
        return authAmtFrLclDbt;
    }

    public void setAuthAmtFrIntrBrnCrdt(String authAmtFrIntrBrnCrdt) {
        this.authAmtFrIntrBrnCrdt = authAmtFrIntrBrnCrdt;
    }

    public String getAuthAmtFrIntrBrnCrdt() {
        return authAmtFrIntrBrnCrdt;
    }

    public void setAuthAmtFrIntrBrnDbt(String authAmtFrIntrBrnDbt) {
        this.authAmtFrIntrBrnDbt = authAmtFrIntrBrnDbt;
    }

    public String getAuthAmtFrIntrBrnDbt() {
        return authAmtFrIntrBrnDbt;
    }

    public void setLStringtXXDate(String lStringtXXDate) {
        this.lStringtXXDate = lStringtXXDate;
    }

    public String getLStringtXXDate() {
        return lStringtXXDate;
    }
    String groupName= null;
    String residentBranch= null; 
    String branchNumber= null;
    String lStringtCCDate= null;
    String groupName1= null; 
    String userType= null;
    String allBranches= null; 
    String sps_all_brnch= null; 
    String lmtFrLclCrdt= null;
    String lmtFrLclDbt= null; 
    String intrBrnCrLmt= null;
    String intrBrnDbtLmt= null;
    String authBrnch= null;
    String authSpsfdBrn= null;
    String authAmtFrLclCrdt= null;
    String authAmtFrLclDbt= null;
    String authAmtFrIntrBrnCrdt = null;
    String authAmtFrIntrBrnDbt= null;
    String lStringtXXDate =null;
    String rowNumberToUpdate =null;
    String requestId = null;
    
    public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRowNumberToUpdate() {
		return rowNumberToUpdate;
	}

	public void setRowNumberToUpdate(String rowNumberToUpdate) {
		this.rowNumberToUpdate = rowNumberToUpdate;
	}
	
	public LimitAttributes() {
        super();
    }
}
