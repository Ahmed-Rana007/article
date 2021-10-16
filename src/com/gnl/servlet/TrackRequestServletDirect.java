package com.gnl.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.login.LoginException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.bea.httppubsub.json.JSONArray;
import com.bea.httppubsub.json.JSONException;
import com.bea.httppubsub.json.JSONObject;
import com.google.gson.JsonObject;
import com.hbl.approvalrequests.PredefinedConst;
import com.hbl.approvalrequests.SOADBConnection;
import com.hbl.approvalrequests.TrackingRequest;
import com.hbl.equation.limits.DatabaseConnection;
import com.hbl.selfservice.LoginToOIM;
import com.hbl.selfservice.OIMUtils;

import oracle.iam.catalog.api.CatalogService;
import oracle.iam.catalog.exception.CatalogException;
import oracle.iam.catalog.vo.Catalog;
import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.entitymgr.vo.SearchCriteria;
import oracle.iam.platform.entitymgr.vo.SearchCriteria.Operator;
import oracle.iam.platform.utils.vo.OIMType;
import oracle.iam.request.api.RequestService;
import oracle.iam.request.exception.NoRequestPermissionException;
import oracle.iam.request.exception.RequestServiceException;
import oracle.iam.request.vo.Beneficiary;
import oracle.iam.request.vo.Request;
import oracle.iam.request.vo.RequestBeneficiaryEntity;
//import parseXML.tbody;
import weblogic.net.http.HttpURLConnection;
import weblogic.wsee.wstx.wsat.v10.types.Outcome;

/**
 * Servlet implementation class TrackRequestServlet
 */
@WebServlet("/TrackRequestServletDirect")
public class TrackRequestServletDirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrackRequestServletDirect() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("TrackRequest.jsp").forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			
	     response.setContentType("application/json");
         response.setCharacterEncoding("utf-8");
	
				
				String trackID=request.getParameter("requestId");
				System.out.print("TID:"+trackID);
				String action = request.getParameter("action");
				String userLogin = request.getParameter("userLogin");
				HttpSession  session=request.getSession();
				PrintWriter out = response.getWriter();
				userLogin = (String) session.getAttribute("username");
				request.setAttribute("TrackID", trackID);

				JSONObject jsonResponse = new JSONObject();
			//	if(action.equals("details"))
				//{
				System.out.println();
				String track=null;
	
				
				//////////////////////////////////////////////////////
				DatabaseConnection connection = new DatabaseConnection();
				int isExist = connection.checkXML(trackID);
				String childDataTable="";
				if(isExist ==1) {
				
			 	childDataTable = XMLParser(convertStringToXMLDocument(getChildDetailXML(trackID)));
				
			 	jsonResponse.put("childDataTable",childDataTable);
				}
				else {jsonResponse.put("childDataTable",childDataTable);}
				
				if(action.equals("trackRequest"))
				{
					if(trackID.toUpperCase().contains("LMT")) {
						System.out.println();
						track = getPendingApprovalsdetails(trackID,out);
						jsonResponse.put("Track",track);
						String EQU_Request_Data="";
						EQU_Request_Data = get_EQU_Request_Data_details(trackID,out);
						if(EQU_Request_Data.length()>1)
						{jsonResponse.put("EQU_Request_Data",EQU_Request_Data);}
						else {
						jsonResponse.put("EQU_Request_Data",EQU_Request_Data);}
					}
					else {
						System.out.println();
						track = getPendingApprovals(trackID,out);
						jsonResponse.put("Track",track);
						jsonResponse.put("EQU_Request_Data","");
					}
					
				}
				else if(action.equals("withdrawRequest"))
				{
					System.out.println("WithDraw Called");
					withdrawRequest(trackID,out);
					
				}
				else if(action.equals("getDetailRequest"))
				{
					System.out.println("GetRequestDetailed Called");

					try {
						requestCatalog(trackID,out);
					} catch (CatalogException | RequestServiceException | NoRequestPermissionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
				//////////////////////////////////////////////////////
				try {
					
					String catalog=requestCatalog(trackID,out);
			if(catalog.length() > 1) {
				jsonResponse.put("Catalog", catalog);
			}
			
				} catch (CatalogException | RequestServiceException | NoRequestPermissionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					jsonResponse.put("Catalog","");
				}
				System.out.println("------------------Reponse Stated------------------------------");
				out.write(jsonResponse.toString());
					
					System.out.println("TrackDetailscalled");
					//System.out.println(jsonResponse.get("Track"));
					System.out.println("JSONOBJECT");
					//System.out.println(jsonResponse.get("Track"));
				//	System.out.println(jsonResponse.get("Catalog"));
					
				
					
			//	}
				
			}
	
			public String getPendingApprovalsdetails(String trackID,PrintWriter out) {
				String approvalDetails ="";
				DatabaseConnection dbobj = new DatabaseConnection();
				Connection con = dbobj.connectMSCDB();
				PreparedStatement stmt= null;
				ResultSet rs =null;
				try { 
					String str = trackID;
					if(str.contains("LMT") || str.contains("L") || str.contains("lmt")) {
						String array[] = str.split("-",2);
						System.out.println(array[1]);
						str = array[1];
					}
					else {
						System.out.println(str);
					}
					String sql = "select * from equ_req_apprv_hist t1 inner join equ_request t2 on t1.request_id = t2.request_id  where t1.request_id='"+str+"'";
					stmt = con.prepareStatement(sql);
					rs = stmt.executeQuery();
					OIMUtils oim = new OIMUtils();
					OIMClient oimClient= new OIMClient();
					oimClient = LoginToOIM.loggedIntoOIM("XELSYSADM","Hblpoc_1234");
					while(rs.next()) {
						approvalDetails+="<tr>"
								+"<td>"+oim.getUserLoginName(rs.getString("REQUESTER"), oimClient)+" </td>"	
								+"<td>"+rs.getString("REQUEST_TYPE")+" </td>"
					            +"<td>Equation</td>" 
					            +"<td>-</td>"
					            +"<td>-</td>";
					            if(rs.getString("CURRENT_APPROVER").contains("LAM"))
					            {
					            	approvalDetails+="<td>"+rs.getString("CURRENT_APPROVER")+" </td>"
							            +"<td>"+rs.getString("CURRENT_APPROVER")+"</td>"
							            +"<td>"+rs.getString("REQUEST_STATUS")+" </td>"
							               //+ "<a href='"+rs.getString(1)+"' id='reAsgnBtn' name='lnkViews' data-toggle='modal' data-target='#MyPopu'>Withdraw </a></td>" 
							            +"</tr>";
							     }
					            else {
					            	approvalDetails+="<td>"+oim.getUserLoginName(rs.getString("CURRENT_APPROVER"), oimClient) +" </td>"
					            +"<td>"+oim.getUserLoginName(rs.getString("CURRENT_APPROVER"), oimClient)+"</td>" 
					            +"<td>"+rs.getString("REQUEST_STATUS")+" </td>"
					               //+ "<a href='"+rs.getString(1)+"' id='reAsgnBtn' name='lnkViews' data-toggle='modal' data-target='#MyPopu'>Withdraw </a></td>" 
					            +"</tr>";
					            	}
					}
					
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				finally {
				    if (stmt != null) {
				        try {
				        	stmt.close();
				        	System.out.println("Statement close: " + stmt.isClosed());
				        } catch (SQLException e) { /* Ignored */}
				    }
				    if (con != null) {
				        try {
				            con.close();
				            System.out.println("Statement close: " + con.isClosed());
				        } catch (SQLException e) { /* Ignored */}
				    }
				}
			
				return approvalDetails;
				
			}
			
			public String get_EQU_Request_Data_details(String trackID,PrintWriter out) {
				String approvalDetails ="<h4>Equation Request</h4>"
						+ "<table id='example' class='table table-striped table-bordered' style='width:100%'>"
						+ "<thead>"
						+ "<tr>"
						+ "<th>Action</th>"
						+ "<th>Resident Brance</th>"
						+ "<th>Group</th>"
						+ "<th>Branch Number</th>"
						+ "<th>Debit Authorization Amount</th>"
						+ "<th>Credit Authorization Amoun</th>"
						+ "</tr>"
						+ "</thead>"
						+ "<tbody style='font-size: 12px;'>";
				DatabaseConnection dbobj = new DatabaseConnection();
				Connection con = dbobj.connectMSCDB();
				PreparedStatement stmt= null;
				ResultSet rs =null;
				try { 
					String str = trackID;
					if(str.contains("LMT") || str.contains("L") || str.contains("lmt")) {
						String array[] = str.split("-",2);
						System.out.println(array[1]);
						str = array[1];
					}
					else {
						System.out.println(str);
					}
					String sql = "select * from EQU_Request_Data where request_id ='"+str+"'";
					stmt = con.prepareStatement(sql);
					rs = stmt.executeQuery();
					while(rs.next()) {
						approvalDetails+="<tr>"
								+"<td>"+rs.getString("ACTION")+" </td>"	
								+"<td>"+rs.getString("UD_KFILPK34_RESIDENT_BRANCH")+" </td>"
					            +"<td>"+rs.getString("UD_KFILPK34_GROUP")+" </td>" 
					            +"<td>"+rs.getString("UD_KFILPK34_BRANCH_NUMBER")+"</td>"
					            +"<td>"+rs.getString("UD_KFILPK34_DEBIT_AUTHORIZATIO")+" </td>"
					            +"<td>"+rs.getString("UD_KFILPK34_CREDIT_AUTHORIZATI")+"</td>" 
					               //+ "<a href='"+rs.getString(1)+"' id='reAsgnBtn' name='lnkViews' data-toggle='modal' data-target='#MyPopu'>Withdraw </a></td>" 
					            +"</tr>";
					}
					approvalDetails+="</tbody>"
									+"</table>";
					
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				finally {
				    if (stmt != null) {
				        try {
				        	stmt.close();
				        	System.out.println("Statement close: " + stmt.isClosed());
				        } catch (SQLException e) { /* Ignored */}
				    }
				    if (con != null) {
				        try {
				            con.close();
				            System.out.println("Statement close: " + con.isClosed());
				        } catch (SQLException e) { /* Ignored */}
				    }
				}
			
				return approvalDetails;
				
			}
	
	
			private String getPendingApprovals(String trackID,PrintWriter out)
			{
				
				String approvalDetails ="";
				String Justification="-";
				
				System.out.println("Track request Controller Called");
				SOADBConnection soaDB ;
				Connection con =  SOADBConnection.connectSOADB();
				 
				// step4 execute query
				//like 'kashif.arif%' and STATE='ASSIGNED'"
				try {
					PreparedStatement pstmt = con.prepareStatement(PredefinedConst.TRACK_REQUEST_QUERY);
					pstmt.setString(1, trackID);
					//pstmt.setString(2, "ASSIGNED");
					//ResultSet rs = stmt.executeQuery(sqlPendingApproval);
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						String assignees=rs.getString("ASSIGNEES");
						if (assignees!=null) {
						assignees= assignees.split(",")[0].toUpperCase();
						}
						String approver=rs.getString("APPROVERS");
						if (approver!=null) {
							approver= (approver.substring(approver.lastIndexOf(",") + 1).toUpperCase());
						}
						String reqType=rs.getString("REQUEST_TYPE");
						if (reqType.contains("Modify User Profile") ||
								reqType.contains("Disable User") ||
								reqType.contains("Enable User") ||
								reqType.contains("Enable Account") ||
								reqType.contains("Disable Account") ||
								reqType.contains("Provision ApplicationInstance")) {
							TrackingRequest tr1 = new TrackingRequest();
							try {
								OIMUtils oim = new OIMUtils();
								OIMClient oimClient= new OIMClient();
								oimClient = LoginToOIM.loggedIntoOIM("XELSYSADM","Hblpoc_1234");
								Justification=tr1.getJustification(oimClient,trackID);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						String outcome=rs.getString("STATE");
						if(outcome==null)
						{
							outcome=rs.getString("OUTCOME");
							System.out.println(outcome);
						}
						if(outcome.contains("APPROVE") ||outcome.contains("WITHDRAW"))
						{
							
							System.out.println("DISABLE WITHDRAW");
						}		
						else 
						{
							
							System.out.println("NOT Called DISABLE WITHDRAW");
						}
						approvalDetails+="<tr>"
								+"<td>"+rs.getString("IDENTIFICATIONKEY").toUpperCase()+" </td>"
								+"<td>"+rs.getString("CREATOR").toUpperCase()+" </td>"	
								+"<td>"+reqType+" </td>"
					               +"<td>"+rs.getString("ITEM_NAME")+" </td>" 
					            		   +"<td>"+Justification+" </td>"
					               +"<td>"+approver+" </td>"
					            		   +"<td>"+assignees+" </td>" 
					               +"<td>"+outcome+" </td>"
					             //  +"<td>"
					               //+ "<a href='"+rs.getString(1)+"' id='reAsgnBtn' name='lnkViews' data-toggle='modal' data-target='#MyPopu'>Withdraw </a></td>" 
				
					               //+"</td>
					               +"</tr>";
						
						System.out.print(rs.getString("ASSIGNEES") + "  " + rs.getString("ASSIGNEEGROUPSDISPLAYNAME") + "  "
								+ rs.getString("ASSIGNEEGROUPS") + " ");
						System.out.print(rs.getString("ASSIGNEEUSERS") + "  " + rs.getString("CREATOR") + "  "
								+ rs.getString("APPROVERS") + " ");
						System.out.print(rs.getString("REQUEST_TYPE") + "  " + rs.getString("ITEM_NAME") + "  "
								+ rs.getString("TITLE") + " ");
						System.out.print(rs.getString("IDENTIFICATIONKEY") + "  " + rs.getString("REQ_ID") + "  "
								+ rs.getString("TASKID") + " ");
						System.out.println(); 
					}
				}catch (SQLException e) {
					e.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				}
				return approvalDetails;	
			}
			
			
			private void withdrawRequest(String trackID,PrintWriter out)
		     {
				LoginToOIM connection=new LoginToOIM();
				OIMClient oimClient = null;
		 		try {
		 			//oimClient = connection.loggedIntoOIM("XELSYSADM", "New@12345");
		 			oimClient = connection.loggedIntoOIM("XELSYSADM", "Hblpoc_1234");
		 		} catch (LoginException e1) {
		 			// TODO Auto-generated catch block
		 			e1.printStackTrace();
		 		} catch (Exception e1) {
		 			// TODO Auto-generated catch block
		 			e1.printStackTrace();
		 		}
		    	
		      	System.out.print("WithdrawID"+trackID);
		 //        RequestService req = oimClient.getService(RequestService.class);
		 		// Gets the list of request template names that are available for logged in user for request creation.
		 	      
		 	    		  
		 	/*     try {
		 	    	RequestEntity reqEntity = new RequestEntity();
		 	        reqEntity.setRequestEntityType(OIMType.User);
		 	        reqEntity.setEntityKey("1");
		 	        
		 	       List<RequestEntity>  entities = new ArrayList<RequestEntity>();
		 	        entities.add(reqEntity);
		 	        
		 	       RequestData reqData = new RequestData();
		 	        reqData.setTargetEntities(entities);
		 	  */      
		 	        
		 	    	
		 	    	 System.out.println("Start");
		 	    	
		 	   /* 	RequestSearchCriteria searchCriteria = new RequestSearchCriteria();
		 	    	searchCriteria.setConjunctionOp(RequestSearchCriteria.Operator.OR);
		 	    	//searchCriteria.addExpression(RequestConstants.REQUESTER_USER_KEY,"*",RequestSearchCriteria.Operator.CONTAIN);
		 	    	searchCriteria.addExpression(RequestConstants.REQUESTER_KEY,"*",RequestSearchCriteria.Operator.CONTAIN);
		 	    	Map <String, Object> config = new HashMap <String,Object>();
		 	    	config.put(RequestConstants.SEARCH_SORTORDER,RequestConstants.SortOrder.ASCENDING);
		 	    	config.put(RequestConstants.SEARCH_SORTBY, RequestConstants.REQUEST_ID);
		 	    	
		 	    	//Search the requests based on the above criteria and config
		 	    	//returns an Request service instance*/
		 	    	RequestService reqsrvc = oimClient.getService(RequestService.class);
		 	    /*	//gets a list of request
		 	    	// 		"Total number of request created by User with user key"
		 	    	Long numberOfRequests = reqsrvc.getNumberOfRequestsCreatedByUser(1,searchCriteria);	
		 	    	System.out.println(numberOfRequests);
		 	    	//Get user for whom request is generated
		 	    	Long numberOfRequest = reqsrvc.getNumberOfRequestsCreatedForUser(1,searchCriteria);	
		 	    	System.out.println("for User:: "+numberOfRequest);
		 	    	Set retAttrs = null;
		 	    	
		 	    	*/
		 	   
		 	    	
		 	    //	List<Request> request = reqsrvc.getRequestsForUser("1",searchCriteria, (HashMap<String, Object>) config);
		 	    	
		 	    	//System.out.println("Raised by ME:"+reqsrvc.getNumberOfRequestsRaisedByMe(searchCriteria));
		 	    	//System.out.println(request.size()
		 	    		//	);
		 	    /*	for(int i=0 ; i< request.size();i++)
		 	    	{
		 	    		System.out.println("Requester Key:: "+request.get(i).getRequesterKey());
		 	    		System.out.println("Justif:: "+request.get(i).getJustification());
		 	    		System.out.println("Request ID:: "+request.get(i).getRequestID());
		 	    		System.out.println("Status:: "+request.get(i).getRequestStatus());
		 	    		System.out.println("Benif Ty[e:: "+request.get(i).getBeneficiaryType());
		 	    		//System.out.println("Kashif:: "+request.get(i).getBeneficiaries().get(0).getBeneficiaryKey());
		 	    		for(int j=0;j<request.get(i).getBeneficiaries().size();j++)
		 	    		{
		 	    			System.out.println("Benificiary: "+request.get(i).getBeneficiaries().get(j).getBeneficiaryKey());
		 	    		}
		 	    		System.out.println("Entty Type:: "+request.get(i).getTargetEntities().get(0).getRequestEntityType());
		 	    		 
		 	    	}*/
		 	    	
		 	    //	List forMe=reqsrvc.getRequestsRaisedForMe(searchCriteria,retAttrs,(HashMap<String, Object>) config);
		             
		 	    	
		 	  //  	System.out.println(forMe);
		 	    //	for(Request s:request)
		 			//{
		 			//	System.out.println(s.getCreationDate());
		 			/*	System.out.println(s.getRequesterKey()+" 	: "+s.getRequestID()+" 	: "+s.getRequestStatus()+"		:" +s.getCreationDate()+"		:"+s.getJustification()+"	:"+s.getAdditionalAttributes()
		 				
		 						+"	:"+s.getBeneficiaries()+"	:"+s.getRequestContext());
		 				System.out.println(reqsrvc.getRequestStatusSummary(s.getRequestID()));
		 				System.out.println(reqsrvc.getRequestHistory(s.getRequestID()));
		 			//Get All information about request*/
		 				//String reqData1=s.getRequestStatus();
		 				//System.out.println(s.getRequestKey());
		 				
		 				//System.out.println(reqData1);
		 				//System.out.println(s.getRequestID()+":"+s.getRequesterKey()+":"+s.getCreationDate()+":"+s.getRequestModelName()
		 				//+":"+s.getJustification());
		 				//System.out.println(s.getRequestModelName());
		 				//System.out.println(s.getTargetEntities());
		 				//List<Beneficiary> a=s.getBeneficiaries();
		 				
		 				
		 				//List a=reqData1.getBeneficiaries();
		 				//System.out.println(a);
		 			//}
		 	    	
		 	    	//List<String> templateName = reqsrvc.getRequestStageNames();
		 	    	
		 	    	
		 	    //	List<Request> templateNames = (List<Request>) reqsrvc.getBasicRequestData("29007");
		 	    	//List<RequestHistory> templateNames = reqsrvc.getRequestHistory("1002");
		 	    	try {
						reqsrvc.withdrawRequest(trackID);
						System.out.print("Success");
					} catch (oracle.iam.request.exception.AccessDeniedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RequestServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		 	    	
		 	    	
		 	 
		 	    	System.out.println("End");
		 	    	//List<String> templateNames = req.getModelNames();
		 	    	//List<String> templateNames = req.getTemplateNames();
		 	    	//Long a  = req.getNumberOfRequestsCreatedByUser(1,rc);
		 	    	//System.out.print(a);
		 	/*		for(String s:templateName)
		 			{
		 				System.out.println(s);
		 			}*/
		 		
		 		
		     
				
			}

			public String requestCatalog(String reqID,PrintWriter out) throws RequestServiceException, NoRequestPermissionException, CatalogException
		     {
				String catalogDetails="";
				String entityName;
				String status;
				String description;
				String benefic;
				
				
				LoginToOIM connection=new LoginToOIM();
				OIMClient oimClient = null;
		 		try {
		 			//oimClient = connection.loggedIntoOIM("XELSYSADM", "New@12345");
		 			oimClient = connection.loggedIntoOIM("XELSYSADM", "Hblpoc_1234");
		 		} catch (LoginException e1) {
		 			// TODO Auto-generated catch block
		 			e1.printStackTrace();
		 		} catch (Exception e1) {
		 			// TODO Auto-generated catch block
		 			e1.printStackTrace();
		 		}
		    	
		      	System.out.print("RequestID"+reqID);
		    	 
		    	 RequestService rs= oimClient.getService(RequestService.class);
		    	 CatalogService cs=oimClient.getService(CatalogService.class);
		    	 
		    	 Request request=rs.getBasicRequestData(reqID);
		    	 List <Beneficiary>reqBeneficiries=request.getBeneficiaries();
		    	 
		    	 for (Beneficiary beneficiary:reqBeneficiries )
		    	 {
		    		 List <RequestBeneficiaryEntity> reqBeneficiaryEntityList=beneficiary.getTargetEntities();
		    		 for(RequestBeneficiaryEntity requestBeneficiaryEntity: reqBeneficiaryEntityList)
		    		 {
		    			 String entityKey=requestBeneficiaryEntity.getEntityKey();
		    			 OIMType entityType= requestBeneficiaryEntity.getRequestEntityType();
		    			 
		    			 Catalog catalog= cs.getCatalogItemDetails(null, entityKey, entityType, null);
		    			 //System.out.println("Approver Role::"+catalog.getApproverRole() );
		    			 //System.out.println("Approver NAME::"+catalog.getApproverRoleDisplayName() );
		    	
		    			 entityName=catalog.getCategoryName();
		    			 benefic=getUserLogin(beneficiary.getBeneficiaryKey(),oimClient); 
		    			status =request.getRequestStatus();
		 				 description=catalog.getEntityDisplayName();
		    			 
		 				 
		 				catalogDetails +="<h4>Request Catalog</h4>"
							   +" <table id=\"example\" class=\"table table-striped table-bordered\" style=\"width:100%\">"
							   +"<thead>"
							   +"<tr>"
							   +"<th>Entity Name</th>"
							   +"<th>Beneficiary</th>"
							   +"<th>Status</th>"
							   +"<th>Description</th>"
							   +"</tr>"
							   +"</thead>"
							   +"<tbody style='font-size: 12px;'>";
					
				catalogDetails += "<tr>" + "<td>" + entityName + " </td>" + "<td>" + benefic + " </td>" + "<td>"
						+ status + " </td>" + "<td>" + description + " </td>"

						+ "</tr>";
				catalogDetails +="</tbody>"
							   +"</table>";
					
		    			 System.out.println("entity Name::"+catalog.getCategoryName());
		    			 System.out.println("Request Status::"+request.getRequestStatus());
		    			 System.out.println("Entity description::"+catalog.getEntityDisplayName());
		    			 
		    			 
//		    			 
		    		 }
		    		 
		    	 }
		    	 
		    	 return catalogDetails;
		     }
			
			private void requestDetials(String trackID,PrintWriter out,String userLogin,String password) throws IOException {
				// TODO Auto-generated method stub
				URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/requests/"+trackID);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				//con.setRequestProperty("X-Requested-By", userName);
				//con.setRequestProperty("uuid", userName);
				con.setRequestProperty("Authorization", "Basic "+base64Encoder(userLogin,password));
				/* Payload support */
				int status = con.getResponseCode();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer content = new StringBuffer();
				while((inputLine = in.readLine()) != null) {
					content.append(inputLine);
				}
				in.close();
				con.disconnect();
				System.out.println("Response status: " + status);
				System.out.println(content.toString());
				JSONObject obj = new JSONObject(content.toString());
				
				JSONArray array = obj.getJSONArray("reqTargetEntities");
				
				
				for(int i=0;i<array.length();i++)
				{
					System.out.println(array.getJSONObject(i));
					String appFormData=array.getJSONObject(i).getString("appFormData");
					String entityType=array.getJSONObject(i).getString("entityType");
					String entityId=array.getJSONObject(i).getString("entityId");
					System.out.println("entityType :"+entityType);
					System.out.println("entityId :"+entityId);
					System.out.println(appFormData);
					String formValues = "";
					if(entityType.equals("ApplicationInstance")) {
					String appFormData1= appFormData.replace("[", "")
							.replace("]", "")
							.replace("\"", "")
							.replace("{", "")
							.replace("}", "");
					Map<String, String> hashMap1
		            = new HashMap<String, String>();
					Map<String, String> hashMap2
		            = new HashMap<String, String>();
					System.out.println(appFormData1);
					String  appFormData2[]=appFormData1.split(",");
					for(int k=0;k<appFormData2.length;k++)
					{
						String appFormData3[] = appFormData2[k].split(":");
					//	String stuRollNo = appFormData3[0].trim();
			            String stuName = appFormData3[1].trim();
					//	hashMap1.put(stuRollNo, stuName);
					//	hashMap1.put(stuRollNo, stuName);
						k++;
						 appFormData3 = appFormData2[k].split(":");
						String stuRollNo1 = appFormData3[0].trim();
			             String stuName1 = appFormData3[1].trim();
					//	hashMap2.put(stuRollNo1, stuName);
						hashMap2.put(stuName,stuName1);
					}
					System.out.println("HashMap1"+hashMap1);
					System.out.println("HashMap2"+hashMap2);
					
					
					for (Map.Entry<String, String> set : hashMap2.entrySet()) {
						 //   System.out.println(set.getKey() + " = " + set.getValue());
						    formValues+=set.getKey()+" = "+set.getValue()+"<br>";
					}
					System.out.println(hashMap2.get("Owner"));
					System.out.print(formValues);
					}
					String requestDetails = null;
					requestDetails+="<tr>"
								
								+"<td >"+entityType+"</td>"
								+"<td >"+entityId+"</td>"
								+"<td >"+formValues+"</td>"
				         + "</tr>";
					if(requestDetails.isEmpty())
					{
						requestDetails="";
						out.print(requestDetails);
					}
					else {
					out.print(requestDetails);
					}
				}
					
				}
				
			
			
			public String base64Encoder(String username, String password)
			{
				Base64.Encoder encoder = Base64.getEncoder();
				// Creating byte array
				byte byteArr[] = { 1, 2 };
				// encoding byte array
				byte byteArr2[] = encoder.encode(byteArr);
				System.out.println("Encoded byte array: " + byteArr2);
				byte byteArr3[] = new byte[5]; // Make sure it has enough size to store copied bytes
				int x = encoder.encode(byteArr, byteArr3); // Returns number of bytes written
				System.out.println("Encoded byte array written to another array: " + byteArr3);
				System.out.println("Number of bytes written: " + x);

				// Encoding string
				String str = encoder.encodeToString((username+":"+password).getBytes());
				System.out.println("Encoded string: " + str);
				return str;

			}

			 public String getUserLogin(String userKey,OIMClient oimClient) {
		        
				 String methodName = "getUserLogin";
		         String userLogin = null;   
		         SearchCriteria sc = new SearchCriteria("usr_key", userKey,Operator.EQUAL); 
		         try {
		             Set retAttrs = new HashSet();
		             retAttrs.add("User Login");// add whatever fields you want get
		            
		      UserManager usrMgr = oimClient.getService(UserManager.class);
		     //UserManager usrMgr = OIMClientFactory.getUserManager(); in ADF
		             List<oracle.iam.identity.usermgmt.vo.User> userList;
		             userList = usrMgr.search(sc, retAttrs, null);
		             for(oracle.iam.identity.usermgmt.vo.User user: userList){
		                 userLogin =  user.getLogin();//get user fields
		             }
		          
		          } catch (Exception e) {
		             e.printStackTrace();
		         }
		         return userLogin;
		     }
			 
				private void GetRequestJustification(String trackID,String userLogin,String password) throws IOException {
					// TODO Auto-generated method stub
					URL url = new URL("http://10.9.166.59:14000/iam/governance/selfservice/api/v1/requests/"+trackID);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					//con.setRequestProperty("X-Requested-By", userName);
					//con.setRequestProperty("uuid", userName);
					con.setRequestProperty("Authorization", "Basic "+base64Encoder(userLogin,password));
					/* Payload support */
					int status = con.getResponseCode();
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer content = new StringBuffer();
					while((inputLine = in.readLine()) != null) {
						content.append(inputLine);
					}
					in.close();
					con.disconnect();
					System.out.println("Response status: " + status);
					System.out.println(content.toString());
					JSONObject obj = new JSONObject(content.toString());
					
					JSONArray array = obj.getJSONArray("reqJustification");
					
					System.out.println(content.toString());
										
					}
				
				
				
				
				
				
/////////////////////////Child Data Detail//////////////////////
				
				
		public static String getChildDetailXML(String Req_Id)
		{
			DatabaseConnection db = new DatabaseConnection();
			Connection con = db.connectMSCDB();
			PreparedStatement stmt;
			ResultSet rs;
			String xml=null;
			
			try {
				stmt = con.prepareStatement("Select REQ_XML_DATA FROM OIM_CHILD_REQ_DATA where REQUEST_ID=?");
				stmt.setString(1,Req_Id); 
				
				rs = stmt.executeQuery();         
				while (rs.next()) { 
				 xml = rs.getString(1);
				 }
				rs.close();                       
				stmt.close();        
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			System.out.println("XML: "+xml);
			return xml;
		}
		
		
		///////////////////////Convert String to XML////////////////////////////
		
		
	    private static Document convertStringToXMLDocument(String xmlString) 
	    {
	        //Parser that produces DOM object trees from XML content
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	         
	        //API to obtain DOM Document instance
	        DocumentBuilder builder = null;
	        try
	        {
	            //Create DocumentBuilder with default configuration
	            builder = factory.newDocumentBuilder();
	             
	            //Parse the content to Document object
	            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
	            return doc;
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	        return null;
	    }
		
		
	////////////////////////////Parse XML DOCUMENT//////////////////////
	    
	    
	    
	    public static String XMLParser(Document  document) {

	        //Get the Document Builder
	    	
	    	String xmlTable="";

	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        try {
	            DocumentBuilder builder = factory.newDocumentBuilder();

	            // Get Document
	          //  Document document = builder.parse(new File("C:\\Users\\Admin\\Desktop\\XML1.xml"));
	         //   Document document1 = builder.parse(new File("C:\\Users\\Admin\\eclipse-workspace\\parseXML\\file1.xml"));
	            

	            // Normalize the xml structure
	            document.getDocumentElement().normalize();
	   
	            // Get all the element by the tag name

	            NodeList laptopList = document.getElementsByTagName("Application");
	            for(int i = 0; i <laptopList.getLength(); i++) {
	            	System.out.println("Applicatin length:"+laptopList.getLength());
	                Node laptop = laptopList.item(i);
	               
	                if(laptop.getNodeType() == Node.ELEMENT_NODE) {

	                    Element laptopElement = (Element) laptop;
	                    System.out.println("Application Name: " + laptopElement.getAttribute("name"));
	                    String AppName = laptopElement.getAttribute("name");
	                    
	                    xmlTable+="<h4>Application Name: "+AppName+"</h4>";
	                    
	                    NodeList laptopDetails =  laptop.getChildNodes();
	                    for(int j = 0; j < laptopDetails.getLength(); j++){
	                        Node detail = laptopDetails.item(j);
	                        if(detail.getNodeType() == Node.ELEMENT_NODE) {
	                            Element detailElement = (Element) detail;
	                            System.out.println(" Table Name    " + detailElement.getTagName() + ": " + detailElement.getAttribute("table_name"));
	                            String TableName = detailElement.getAttribute("table_name");
	                            xmlTable+="<h4>Table Name: "+TableName+"</h4>";
	                            NodeList userDetail = detailElement.getChildNodes();
	                           
	                      
	                            String  targetUser="";
	                            
	                            Node Login = userDetail.item(0);
	                            
	                        	if(Login.getNodeType() == Node.ELEMENT_NODE)
	                        	{
	                        		
	                        		Element userAtt =(Element) Login;
	                        		
	                        	System.out.println( "User Login: " + userAtt.getAttribute("login"));
	                        	System.out.println( "User Login: " + userAtt.getTagName());
	                        	targetUser = userAtt.getAttribute("login");
	                        	xmlTable+="<h4>User: "+targetUser+"</h4>";
	                        	
	                        		//System.out.println("  "		+ ""+userAtt.getTagName()+" : "+userAtt.getTextContent());
	                        	}
	                            for(int k =1;k <userDetail.getLength();k++)
	                            {
	                            	
	                            	Node user = userDetail.item(k);
	                            	String Record;
	                            
	                            	if(user.getNodeType() == Node.ELEMENT_NODE)
	                            	{
	                            		Element userAtt =(Element) user;
	                            		System.out.println( userAtt.getTagName()+":" + userAtt.getAttribute("id"));
	                            		Record = userAtt.getAttribute("id");
	                            		xmlTable+="<h4>Record:"+Record+"</h4>";
	                            		NodeList values = userAtt.getChildNodes();
	                            		
	                            		xmlTable+="	<table id='example' class='table table-striped table-bordered' style='width:100%'>"+
                             	       
                                      	
                                        "<tbody   style='font-size: 12px;'>";
                                       	
                                        
                                		
	                            		
	                            	//	System.out.println(userAtt.getTagName()+" : "+userAtt.getTextContent());
	                            		
	                            		for(int m =0;m <values.getLength();m++)
	                                    {
	                                    	
	                                    	Node user1 = values.item(m);
	                                    
	                                    	if(user1.getNodeType() == Node.ELEMENT_NODE)
	                                    	{
	                                    		Element userAtt1 =(Element) user1;
	                                    		//System.out.println( "Record ID: " + userAtt.getAttribute("id"));
	                                    		
	                     
	                                    		xmlTable+="<tr>"
	                    								
								+"<td >"+userAtt1.getTagName()+"</td>"
								+"<td >"+userAtt1.getTextContent()+"</td>";
	                                    		
	                                    		
	                                    		System.out.println(userAtt1.getTagName()+" : "+userAtt1.getTextContent());
	                                    	}
	                                    }
	                            		xmlTable+="</tbody></table>";
	                            	}
	                            }
	                        }

	                    }

	                }
	            }


	        } catch (ParserConfigurationException e) {
	            e.printStackTrace();
	       
	        }
	        	return xmlTable;
	    }
	    
	    
	    
	    
	    
	    
		
		
		
		
////////////////////////////////////////////////////////////////
						
				
									}
					

