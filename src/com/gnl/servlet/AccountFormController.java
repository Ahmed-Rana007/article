package com.gnl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hbl.provisioning.ProvisioningAccounts;
import com.hbl.selfservice.OIMUtils;
import com.hbl.selfservice.formfields.AcctFormFields;
import com.hbl.provisioning.UserOperationModifyAcc;

import oracle.iam.platform.OIMClient;

/**
 * Servlet implementation class AccountFormController
 */
@WebServlet("/AccountFormController")
public class AccountFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String appInstanceName = request.getParameter("appInstanceName");
		HttpSession  session=request.getSession();
        
        OIMUtils oimUtils = new OIMUtils();
        PrintWriter out = response.getWriter();
        
        OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
        String userLogin  = (String)session.getAttribute("username");
        OIMUtils oimUtils1 = new OIMUtils();
        ProvisioningAccounts pa = new ProvisioningAccounts();
        String userkey1 = null;
        try {
			userkey1 = pa.getUserKey(userLogin, oimClient);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        UserOperationModifyAcc uomc = new UserOperationModifyAcc();
        
        String role = uomc.getRole(userkey1);
        System.out.println("ROLE->>>>>>>>>>>>>>"+role);
		
		String cpUID = (String) session.getAttribute("CpUID");
		System.out.println("CPID->>>>>>>>>>>>>>"+cpUID);
	
		ProvisioningAccounts prvAccts = new ProvisioningAccounts();
		
		
		List<AcctFormFields>  acctList  = prvAccts.getAccountFormFields(appInstanceName, oimClient,role);
		//List<AcctFormFields>  acctList  = prvAccts.getAccountFormFields("Siebel8", oimClient);
		String accountFormHtml = "";
		
		for(int i = 0 ; i < acctList.size()-2; i++)
		{
			accountFormHtml+= "<div class='form-row'>"
			//+ acctList.get(i).makeFieldHTML(oimClient,prvAccts)
			//+ acctList.get(++i).makeFieldHTML(oimClient,prvAccts)
			//+ acctList.get(++i).makeFieldHTML(oimClient,prvAccts)
			+ acctList.get(i).makeHTMLNew(oimClient,prvAccts)
			+ acctList.get(++i).makeHTMLNew(oimClient,prvAccts)
			+ acctList.get(++i).makeHTMLNew(oimClient,prvAccts)
			+"</div><div class='clearfix'></div>";
			System.out.println("accountFormHtml: "+accountFormHtml);
		}
		if(appInstanceName.equals("Equation"))
			
			out.print("<h4>Details</h4><br/>"+accountFormHtml + CPChildForm() );
		else if(appInstanceName.equals("ActiveDirectory"))
			out.print("<h4>Details</h4><br/>"+accountFormHtml + ADChildForm() );
		else if(appInstanceName.equals("AS400"))
			out.print("<h4>Details</h4><br/>"+accountFormHtml + AS400ChildForm() );
		else if(appInstanceName.equals("EQGroup"))
			out.print("<h4>Details</h4><br/>"+accountFormHtml + EquationGruopChildForm() );
		else 
			out.print("<h4>Details</h4><br/>"+accountFormHtml);
			
		
		
	}
	private String CPChildForm()
	{
		String childhtml = "<div class='custom-tabs-line tabs-line-bottom left-aligned'>\r\n" + 
				"									<ul class='nav' role='tablist'>\r\n" + 
				"\r\n" + 
				"										<li><a href='#tab-bottom-left5' role='tab' data-toggle='tab'>Equation2UD_KFILPK26</a></li>\r\n" + 
				"										<li><a href='#tab-bottom-left2' role='tab' data-toggle='tab'>Equation2UD_KFILPK27</a></li>\r\n" + 
				"										<li><a href='#tab-bottom-left3' role='tab' data-toggle='tab'>Equation2UD_KAPBASE7</a></li>\r\n" + 
				"										<li><a href='#tab-bottom-left4' role='tab' data-toggle='tab'>Equation2UD_KFILPK34</a></li>\r\n" + 
				"									</ul>\r\n" + 
				"								</div>\r\n" + 
				"								<div class='tab-content'>\r\n" + 
				"\r\n" + 
				"									<div class='tab-pane' id='tab-bottom-left5'>\r\n" + 
				"										<div class='form-row'>\r\n" +
	
				"											<div class='col-md-1 rt' style='border-left:3px solid;width:3%;color:#009591'><a data-toggle='modal' data-target= '.table26'><i class='glyphicon glyphicon-plus'></i></a></div>\r\n" + 
				"											<div class='col-md-1 lf' style='border-right:3px solid;width:4%;color:#009591'><i class='glyphicon glyphicon-remove' name='table26Remove'></i></div>\r\n" + 
				"											<div class='col-md-1' style='color:#009591'><i class='glyphicon glyphicon-filter'></i></div>\r\n" + 
				"										</div>\r\n" + 
				"										<div class='clearfix'></div><br />\r\n" + 
				"										<table class='table table-bordered' id='table26'>\r\n" + 
				"											<thead>\r\n" + 
				"												<tr>\r\n" + 
				"													<th scope='col'>User_Branch</th>\r\n" + 
				"													<th scope='col'>Branch_Number</th>\r\n" + 
				"													<th scope='col'>User_Class</th>\r\n" + 
				"													<th scope='col'>Language_Code</th>\r\n" + 
				"													<th scope='col'>Date_Last_Maintained</th>\r\n" + 
				"													<th scope='col'>User_Group</th>\r\n" + 
				"													<th scope='col' >Action</th>\r\n" +
				"												</tr>\r\n" + 
				"											</thead>\r\n" + 
				"											<tbody>\r\n" + 
				
				 
				"											</tbody>\r\n" + 
				"										</table>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"									</div>\r\n" + 
				"									<div class='tab-pane fade in' id='tab-bottom-left2'>\r\n" + 
				"\r\n" + 
				"										<div class='form-row'>\r\n" + 
				"											<div class='col-md-1 rt' style='border-left:3px solid;width:3%;color:#009591'><i class='glyphicon glyphicon-plus'></i></a></div>\r\n" + 
				"											<div class='col-md-1 lf' style='border-right:3px solid;width:4%;color:#009591'><i class='glyphicon glyphicon-remove'></i></div>\r\n" + 
				"											<div class='col-md-1' style='color:#009591'><i class='glyphicon glyphicon-filter'></i></div>\r\n" + 
				"										</div>\r\n" + 
				"										<div class='clearfix'></div><br />\r\n" + 
				"										<table class='table table-bordered' id='table27'>\r\n" + 
				"											<thead>\r\n" + 
				"												<tr>\r\n" + 
				"													<th scope='col'>Default_User_Limit</th>\r\n" + 
				"													<th scope='col'>Currency_Mnemonic</th>\r\n" + 
				"													<th scope='col'>Date_Last_Maintained</th>\r\n" + 
				"													<th scope='col'>Application_Code</th>\r\n" + 
				 
				"												</tr>\r\n" + 
				"											</thead>\r\n" + 
				"											<tbody>\r\n" + 
				"												<tr>\r\n" + 
				"	   											<td>10000</td>\r\n" + 
				"													<td>PKC</td>\r\n" + 
				"													<td>0</td>\r\n" + 
				"													<td>CP</td>\r\n" + 
				 
				"												</tr>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"											</tbody>\r\n" + 
				"										</table>\r\n" + 
				"\r\n" + 
				"									</div>\r\n" + 
				"\r\n" + 
				"									<div class='tab-pane fade in' id='tab-bottom-left3'>\r\n" + 
				"\r\n" + 
				"										<div class='form-row'>\r\n" + 
				"											<div class='col-md-1 rt' style='border-left:3px solid;width:3%;color:#009591'><a data-toggle='modal' data-target= '.table7'><i class='glyphicon glyphicon-plus'></i></a></div>\r\n" + 
				"											<div class='col-md-1 lf' style='border-right:3px solid;width:4%;color:#009591'><i class='glyphicon glyphicon-remove' name='table7Remove'></i></div>\r\n" + 
				"											<div class='col-md-1' style='color:#009591'><i class='glyphicon glyphicon-filter'></i></div>\r\n" + 
				"										</div>\r\n" + 
				"										<div class='clearfix'></div><br />\r\n" + 
				"										<table class='table table-bordered' id='table7'>\r\n" + 
				"											<thead>\r\n" + 
				"												<tr>\r\n" + 
				"													<th scope='col'>Unite_Server</th>\r\n" + 
				"													<th scope='col'>Initial_Menu</th>\r\n" + 
				"													<th scope='col'>Authorised_Unit</th>\r\n" +
				"													<th scope='col' >Action</th>\r\n" +
				"													\r\n" + 
				"												</tr>\r\n" + 
				"											</thead>\r\n" + 
				"											<tbody>\r\n" +  
				"\r\n" + 
				"\r\n" + 
				"											</tbody>\r\n" + 
				"										</table>\r\n" + 
				"\r\n" + 
				"									</div>\r\n" + 
				"\r\n" + 
				"									<div class='tab-pane fade in' id='tab-bottom-left4'>\r\n" + 
				"\r\n" + 
				"										<div class='form-row'>\r\n" + 
				"											<div class='col-md-1 rt' style='border-left:3px solid;width:3%;color:#009591'><i class='glyphicon glyphicon-plus'></i></a></div>\r\n" + 
				"											<div class='col-md-1 lf' style='border-right:3px solid;width:4%;color:#009591'><i class='glyphicon glyphicon-remove'></i></div>\r\n" + 
				"											<div class='col-md-1' style='color:#009591'><i class='glyphicon glyphicon-filter'></i></div>\r\n" + 
				"										</div>\r\n" + 
				"										<div class='clearfix'></div><br />\r\n" + 
				"\r\n" + 
				"																	<div class='table-responsive'>\r\n" + 
				"																		<table class='table table-bordered' id='table34'>\r\n" + 
				"																			<thead>\r\n" + 
				"																				<tr>\r\n" + 
				"																					<th scope='col'>Process_Inter_Branch_Transaction</th>\r\n" + 
				"																					<th scope='col'>Inter_Branch_Debit_Transaction_Limit</th>\r\n" + 
				"																					<th scope='col'>Resident_Branch</th>\r\n" + 
				"																					<th scope='col'>Inter_Branch_Credit_Transaction_Limit</th>\r\n" + 
				"																					<th scope='col'>Branch_Number</th>\r\n" + 
				"																					<th scope='col'>Local_Credit_Amount_Transaction_Limit</th>\r\n" + 
				"																					<th scope='col'>Local_Debit_Amount_Transaction_Limit</th>\r\n" + 
				"																					<th scope='col'>Debit_Authorization_Amount</th>\r\n" + 
				"																					<th scope='col'>Credit_Authorization_Amount</th>\r\n" +

				"\r\n" + 
				"																				</tr>\r\n" + 
				"																			</thead>\r\n" + 
				"																			<tbody style='overflow:auto;'>\r\n" + 
				"\r\n" + 
				"																				</tr>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"																			</tbody>\r\n" + 
				"																		</table>\r\n" + 
				"																	</div>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"									</div>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"								</div>";
		return childhtml.toString();
	}
	private String ADChildForm()
	{
		String childhtml = "<div class='custom-tabs-line tabs-line-bottom left-aligned'>\r\n" + 
				"		<ul class='nav' role='tablist'>\r\n" + 
				"\r\n" + 
				"			<li><a href='#tab-bottom-left1' role='tab' data-toggle='tab'>ActiveDirectory Group</a></li>\r\n" + 
				"		</ul>\r\n" + 
				"	</div>\r\n" + 
				"	<div class='tab-content'>\r\n" + 
				"\r\n" + 
				"		<div class='tab-pane active' id='tab-bottom-left1'>\r\n" + 
				"			<div class='form-row'>\r\n" + 
				"				<div class='col-md-1 rt' style='border-left:3px solid;width:3%;color:#009591'><a href='#'><i class='glyphicon glyphicon-plus'></i></a></div>\r\n" + 
				"				<div class='col-md-1 lf' style='border-right:3px solid;width:4%;color:#009591'><a href='#'><i class='glyphicon glyphicon-remove'></i></a></div>\r\n" + 
				"				<div class='col-md-1' style='color:#009591'><a href='#'><i class='glyphicon glyphicon-filter'></i></a></div>\r\n" + 
				"			</div>\r\n" + 
				"			<div class='clearfix'></div><br />\r\n" + 
				"			<table class='table table-bordered'>\r\n" + 
				"				<thead>\r\n" + 
				"					<tr>\r\n" + 
				"						<th scope='col'>Group Name</th>\r\n" + 
				"					</tr>\r\n" + 
				"				</thead>\r\n" + 
				"				<tbody>\r\n" + 
				"					<tr>\r\n" + 
				"						<th scope='row'><input type='text' class='form-control' /></th>\r\n" + 
				
				"					</tr>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"				</tbody>\r\n" + 
				"			</table>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"		</div>\r\n" + 
				"		\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"	</div>";
		return childhtml.toString();
	}
	private String AS400ChildForm()
	{
		String childhtml = "<div class='custom-tabs-line tabs-line-bottom left-aligned'>\r\n" + 
				"									<ul class='nav' role='tablist'>\r\n" + 
				"\r\n" + 
				"										<li><a href='#tab-bottom-left6' role='tab' data-toggle='tab'>AS400CSP</a></li>\r\n" + 
				"										<li><a href='#tab-bottom-left7' role='tab' data-toggle='tab'>AS400CSG</a></li>\r\n" + 
	 
				"									</ul>\r\n" + 
				"								</div>\r\n" + 
				"								<div class='tab-content'>\r\n" + 
				"\r\n" + 
				"									<div class='tab-pane' id='tab-bottom-left6'>\r\n" + 
				"										<div class='form-row'>\r\n" +
	
				"											<div class='col-md-1 rt' style='border-left:3px solid;width:3%;color:#009591'><a data-toggle='modal' data-target= '.AS400CSP'><i class='glyphicon glyphicon-plus'></i></a></div>\r\n" + 
				"											<div class='col-md-1 lf' style='border-right:3px solid;width:4%;color:#009591'><i class='glyphicon glyphicon-remove' name='tableCSPRemove'></i></div>\r\n" + 
															 
				"										</div>\r\n" + 
				"										<div class='clearfix'></div><br />\r\n" + 
				"										<table class='table table-bordered' id='tableCSP'>\r\n" + 
				"											<thead>\r\n" + 
				"												<tr>\r\n" + 
				"													<th scope='col'>Special_Authority</th>\r\n" + 
				"													<th scope='col' >Action</th>\r\n" +
				"												</tr>\r\n" + 
				"											</thead>\r\n" + 
				"											<tbody>\r\n" + 
				
				 
				"											</tbody>\r\n" + 
				"										</table>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"									</div>\r\n" + 
				"									<div class='tab-pane fade in' id='tab-bottom-left7'>\r\n" + 
				"\r\n" + 
				"										<div class='form-row'>\r\n" + 
				"											<div class='col-md-1 rt' style='border-left:3px solid;width:3%;color:#009591'><a data-toggle='modal' data-target= '.AS400CSG'><i class='glyphicon glyphicon-plus'></i></a></div>\r\n" + 
				"											<div class='col-md-1 lf' style='border-right:3px solid;width:4%;color:#009591'><i class='glyphicon glyphicon-remove' name='tableCSGRemove'></i></div>\r\n" + 
				 
				"										</div>\r\n" + 
				"										<div class='clearfix'></div><br />\r\n" + 
				"										<table class='table table-bordered' id='tableCSG'>\r\n" + 
				"											<thead>\r\n" + 
				"												<tr>\r\n" + 
				"													<th scope='col'>Supplemental_Group</th>\r\n" + 
				"													<th scope='col'>Action</th>\r\n" + 
				 
				"												</tr>\r\n" + 
				"											</thead>\r\n" + 
				"											<tbody>\r\n" + 
				"												\r\n" + 
				"\r\n" + 
				"											</tbody>\r\n" + 
				"										</table>\r\n" + 
				"\r\n" + 
				"									</div>\r\n" + 
				"\r\n" + 
				 
				"\r\n" + 
				"									</div>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"								</div>";
		return childhtml.toString();
	}

	private String EquationGruopChildForm()
	{
		String childhtml = "<div class='custom-tabs-line tabs-line-bottom left-aligned'>\r\n" + 
				"									<ul class='nav' role='tablist'>\r\n" + 
				"\r\n" + 
				"										<li><a href='#tab-bottom-left8' role='tab' data-toggle='tab'>ADD Options</a></li>\r\n" + 
														 
	 
				"									</ul>\r\n" + 
				"								</div>\r\n" + 
				"								<div class='tab-content'>\r\n" + 
				"\r\n" + 
				"									<div class='tab-pane' id='tab-bottom-left8'>\r\n" + 
				"										<div class='form-row'>\r\n" +
	
				"											<div class='col-md-1 rt' style='border-left:3px solid;width:3%;color:#009591'><a data-toggle='modal' data-target= '.EQGROUP'><i class='glyphicon glyphicon-plus'></i></a></div>\r\n" + 
				"											<div class='col-md-1 lf' style='border-right:3px solid;width:4%;color:#009591'><i class='glyphicon glyphicon-remove' name='tableEQGROUPRemove'></i></div>\r\n" + 
															 
				"										</div>\r\n" + 
				"										<div class='clearfix'></div><br />\r\n" + 
				"										<table class='table table-bordered' id='tableEQGROUP'>\r\n" + 
				"											<thead>\r\n" + 
				"												<tr>\r\n" + 
				"													<th scope='col'>Option_Id</th>\r\n" +  
				"													<th scope='col' >Action</th>\r\n" +
				"												</tr>\r\n" + 
				"											</thead>\r\n" + 
				"											<tbody>\r\n" + 
				
				 
				"											</tbody>\r\n" + 
				"										</table>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"									</div>\r\n" + 
				 
				 
				"\r\n" + 
				"									</div>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"								</div>";
		return childhtml.toString();
	}
}
