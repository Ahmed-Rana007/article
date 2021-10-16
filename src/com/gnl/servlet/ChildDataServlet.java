package com.gnl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bea.httppubsub.json.JSONObject;
import com.hbl.provisioning.SearchAccountsForUser;
import com.hbl.selfservice.objects.GSonResponseFields;

import oracle.iam.platform.OIMClient;

/**
 * Servlet implementation class ChildDataServlet
 */
@WebServlet("/ChildDataServlet")
public class ChildDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChildDataServlet() {
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
	System.out.println("ChildData Called");

	     response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userLogin = request.getParameter("userLogin").trim().toString();
	        String action = request.getParameter("action").trim().toString();
	      //  String oiu_key = request.getParameter("oiu_key").trim().toString();
	        
	        GetChildData childData = new GetChildData();
	        OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
	        
	        if(action.equals("getAS400ChildData"))
	        {
	        	List <String> AS400CSP = new ArrayList<>();
		        List <String> AS400CSG = new ArrayList<>();
		        
		        HashMap<String, String> AS400CSPMap = new HashMap<>();
		        HashMap<String, String> AS400CSGMap = new HashMap<>();
		        
	        
	         	AS400CSP = childData.getAS400CSPTable(2, userLogin);
	        	AS400CSG = childData.getAS400CSGTable(2, userLogin);
	        	AS400CSPMap = childData.getAS400CSPMap(2, userLogin);
	        	
	        
	        
	        String tableCSP ="";
	        String tableCSG="";
	        
	        for(Map.Entry m :AS400CSPMap.entrySet())
			{
				
	        	tableCSP+="<tr>"
	        			 +"<td>"+m.getValue()+"</td>"
	        			+"<td>"+m.getKey()+"</td>"
	        			+"<td><input type='checkbox' name='AS400CSPCB' id='AS400CSPCB' />&nbsp;</td>"
			     +"</tr>";
	        }
	        	
	        
	        for(String a:AS400CSG)
			{
				System.out.println(a);

	        	tableCSG+="<tr>"
			               +"<td style='width=500px' id='popdetails' name='popdetails' class='popdetails'><a href='"+a+"' name='lnkViews' data-toggle='modal' data-target='#MyPopup' class='popdetails'>"+a+ "</a></td>"
			     +"</tr>";
	        }
	      
	        
	        
	        String AS400Tables = "<div class='custom-tabs-line tabs-line-bottom left-aligned'>\r\n" + 
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
					"											<div class='col-md-1 lf' style='border-right:3px solid;width:4%;color:#009591'><i class='glyphicon glyphicon-remove' name='tableCSPRemove' id='tableCSPRemove'></i></div>\r\n" + 
																 
					"										</div>\r\n" + 
					"										<div class='clearfix'></div><br />\r\n" + 
					"										<table class='table table-bordered' id='tableCSP'>\r\n" + 
					"											<thead>\r\n" + 
					"												<tr>\r\n" + 
					"													<th scope='col'>Special_Authority</th>\r\n" + 
					"													<th scope='col' >ID</th>\r\n" +
					"													<th scope='col' >Action</th>\r\n" +
					
					"												</tr>\r\n" + 
					"											</thead>\r\n" + 
					"											<tbody>\r\n" + 
					tableCSP+
					 
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
																tableCSG+
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
			
	        
	        
	        
	        
	        JSONObject jsonResponse = new JSONObject();
	        jsonResponse.put("AS400Tables", AS400Tables);
			// jsonResponse.put("AS400CSG", AS400CSG);
			 
			 out.print(jsonResponse.toString());
			
	
	}

	        
	        
	        
	else if(action.equals("getEQUATIONChildData"))
    {
    
		
	     
	     HashMap<String, String> Equation26Map = new HashMap<>();
	     HashMap<String, String> Equation7Map = new HashMap<>();
	     HashMap<String, String> Equation27Map = new HashMap<>();
	     
	     List<HashMap<String,String>> Equation34Map = new ArrayList<HashMap<String,String>>();
		
     	
     	Equation26Map = childData.getEquation26Map(165, userLogin);
     	Equation7Map = childData.getEquation7Map(165, userLogin);
     	Equation27Map = childData.getEquation27Map(165, userLogin);
     	Equation34Map = childData.getEquation34Map(165, userLogin);
    
    
    String table26 ="";
    String table7="";
    String table27 ="";
    String table34="";
    
    table26+="<tr>";
        	
    for(Map.Entry m :Equation26Map.entrySet())
	{
    	table26+=
    			 "<td>"+m.getValue()+"</td>";
    }
    table26+="<td><input type='checkbox' name='EquationTable26' id='EquationTable26' checked disabled/>&nbsp;</td></tr>";
    
    table7+="<tr>";
    for(Map.Entry m :Equation7Map.entrySet())
	{
		
    	table7+=
    			 "<td>"+m.getValue()+"</td>";
	     
    }
    table7+="<td><input type='checkbox' name='EquationTable7' id='EquationTable7' />&nbsp;</td></tr>";
    
    table27+="<tr>";
    for(Map.Entry m :Equation27Map.entrySet())
	{
		
    	table27+=
    			 "<td>"+m.getValue()+"</td>";
	     
    }
    table27+="<td><input type='checkbox' name='EquationTable27' id='EquationTable27' />&nbsp;</td></tr>";
  
    for(HashMap<String, String> EquationMap :Equation34Map)
	{
		//System.out.println(m.get("UD_KFILPK34_PROCESS_INTER_BRAN"));
		
	
    
    table34+="<tr>";
    for(Map.Entry m :EquationMap.entrySet())
	{
		
    	table34+=
    			 "<td>"+m.getValue()+"</td>";
    			
	     System.out.println("Table34:"+m.getValue());
    }
    table34+="<td><input type='checkbox' name='EquationTable34' id='EquationTable34' />&nbsp;</td></tr>";
    System.out.println("Table34:"+table34);
	}    
    String EQUATIONTables = "<div class='custom-tabs-line tabs-line-bottom left-aligned'>\r\n" + 
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
			table26+	
			 
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
			"\r\n" + table7+
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
			"																					<th scope='col'>ID</th>\r\n" +
			"																					<th scope='col'>ID2</th>\r\n" +

			"\r\n" + 
			"																				</tr>\r\n" + 
			"																			</thead>\r\n" + 
			"																			<tbody>\r\n" + 
			"\r\n" + 
							table34 + 
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
	
    
    
    
    
    JSONObject jsonResponse = new JSONObject();
    jsonResponse.put("EQUATIONTables", EQUATIONTables);
	// jsonResponse.put("AS400CSG", AS400CSG);
	 
	 out.print(jsonResponse.toString());
	

}
	else if(action.equals("getEQUATIONGroupChildData"))
	        {
	        	
		        HashMap<String, String> EquationGroupOption = new HashMap<>();
		        
	        
		        EquationGroupOption = childData.getEquationGroupOptionMap(164, userLogin);
	        	
	        
	        
	        String tableEquationGroup ="";
	      
	        
	        for(Map.Entry<String,String> m :EquationGroupOption.entrySet())
			{
				
	        	tableEquationGroup+="<tr>"
	        			 +"<td>"+m.getValue()+"</td>"
	        			+"<td>"+m.getKey()+"</td>"
	        			+"<td><input type='checkbox' name='EuationGroupId' id='EuationGroupId' />&nbsp;</td>"
			     +"</tr>";
	        }
	        	
	        
	       	      
	        
	        
	        String EquationGroupTables = "<div class='custom-tabs-line tabs-line-bottom left-aligned'>\r\n" + 
					"									<ul class='nav' role='tablist'>\r\n" + 
					"\r\n" + 
					"										<li><a href='#tab-bottom-left6' role='tab' data-toggle='tab'>ADD Option</a></li>\r\n" + 
														
					"									</ul>\r\n" + 
					"								</div>\r\n" + 
					"								<div class='tab-content'>\r\n" + 
					"\r\n" + 
					"									<div class='tab-pane' id='tab-bottom-left6'>\r\n" + 
					"										<div class='form-row'>\r\n" +
		
					"											<div class='col-md-1 rt' style='border-left:3px solid;width:3%;color:#009591'><a data-toggle='modal' data-target= '.addEQUATION_GROUP'><i class='glyphicon glyphicon-plus'></i></a></div>\r\n" + 
					"											<div class='col-md-1 lf' style='border-right:3px solid;width:4%;color:#009591'><i class='glyphicon glyphicon-remove' name='tableCSPRemove' id='tableCSPRemove'></i></div>\r\n" + 
																 
					"										</div>\r\n" + 
					"										<div class='clearfix'></div><br />\r\n" + 
					"										<table class='table table-bordered' id='tableEQGroup'>\r\n" + 
					"											<thead>\r\n" + 
					"												<tr>\r\n" + 
					"													<th scope='col'>OPTION_ID</th>\r\n" + 
					"													<th scope='col' >ID</th>\r\n" +
					"													<th scope='col' >Action</th>\r\n" +
					
					"												</tr>\r\n" + 
					"											</thead>\r\n" + 
					"											<tbody>\r\n" + 
					tableEquationGroup+
					 
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
			
	        
	        
	        
	        
	        JSONObject jsonResponse = new JSONObject();
	        jsonResponse.put("EQUATIONGroupTables", EquationGroupTables);
			// jsonResponse.put("AS400CSG", AS400CSG);
			 
			 out.print(jsonResponse.toString());
			
	
	}

}
}
