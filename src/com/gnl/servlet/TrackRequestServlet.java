package com.gnl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.security.auth.login.LoginException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import oracle.iam.request.vo.RequestComment;
import weblogic.wsee.wstx.wsat.v10.types.Outcome;
import com.bea.httppubsub.json.JSONObject;
/**
 * Servlet implementation class TrackRequestServlet
 */
@WebServlet("/TrackRequestServlet")
public class TrackRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrackRequestServlet() {
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
				
		       //  response.setContentType("application/json");
	       //      response.setCharacterEncoding("utf-8");
				
				String trackID=request.getParameter("trackID");
				System.out.print("TID:"+trackID);
				String action = request.getParameter("action");
				String userLogin = request.getParameter("userLogin");
				HttpSession  session=request.getSession();
				PrintWriter out = response.getWriter();
				JSONObject jsonResponse = new JSONObject();
				userLogin = (String) session.getAttribute("username");
				//userLogin = (String) session.getAttribute("username");
				//OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
				String password = (String) session.getAttribute("password");
				
				System.out.println("TrackRequestDirect Start");
				//RequestDispatcher requestDispatcher = request
	              //      .getRequestDispatcher("/TrackRequestDirect.jsp");
	            //requestDispatcher.forward(request, response);
			//	response.sendRedirect("/RequestDetails.jsp");  
	            System.out.println("TrackRequestDirect END");
				
				if(action.equals("trackRequest"))
				{	
					if(trackID.toUpperCase().contains("LMT")) {
						getRequestDetails(trackID,out);
					}
					else {
					
						getPendingApprovals(trackID,out);
					}	
				}
				else if(action.equals("withdrawRequest"))
				{
					response.setContentType("application/json");
				      response.setCharacterEncoding("utf-8");
				         
					
					String result=null;
					System.out.println("WithDraw Called");
					result=withdrawRequest(trackID.toString().trim(),out,userLogin,password);
					
					
					jsonResponse.put("STATUS",result);
					out.write(jsonResponse.toString());
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
				
					
			}
	
	
			private void getRequestDetails(String trackID,PrintWriter out) {
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
					String sql = "select * from equ_req_apprv_hist t1 inner join equ_request t2 on t1.request_id = t2.request_id where t1.request_id='"+str+"' and t1.STAGE in (select MAX(t1.STAGE) from equ_req_apprv_hist t1 where t1.request_id='"+str+"')";
					stmt = con.prepareStatement(sql);
					rs = stmt.executeQuery();
					OIMUtils oim = new OIMUtils();
					OIMClient oimClient= new OIMClient();
					oimClient = LoginToOIM.loggedIntoOIM("XELSYSADM","Hblpoc_1234");
					while(rs.next()){
						approvalDetails+="<tr>"
								+"<td>"+oim.getUserLoginName(rs.getString("REQUESTER"), oimClient)+" </td>"	
								+"<td>"+rs.getString("REQUEST_TYPE")+" </td>"
					            +"<td>Equation</td>" 
					            +"<td>-</td>"
					            +"<td>"+rs.getString("APPROVAL_COMMENTS")+"</td>";
					            if(rs.getString("CURRENT_APPROVER").contains("LAM"))
					            {
					            	if(rs.getString("REQUEST_STATUS").equals("Rejected"))
					            	{approvalDetails+="<td>"+rs.getString("CURRENT_APPROVER")+" </td>"
								            +"<td>-</td>"
								            +"<td>"+rs.getString("REQUEST_STATUS")+" </td>"
								               //+ "<a href='"+rs.getString(1)+"' id='reAsgnBtn' name='lnkViews' data-toggle='modal' data-target='#MyPopu'>Withdraw </a></td>" 
								            +"</tr>";}
					            	else {
					            	approvalDetails+="<td>"+rs.getString("CURRENT_APPROVER")+" </td>"
							            +"<td>"+rs.getString("CURRENT_APPROVER")+"</td>"
							            +"<td>"+rs.getString("REQUEST_STATUS")+" </td>"
							               //+ "<a href='"+rs.getString(1)+"' id='reAsgnBtn' name='lnkViews' data-toggle='modal' data-target='#MyPopu'>Withdraw </a></td>" 
							            +"</tr>";
							     }
					            	}
					            else {
					            	
					            	if(rs.getString("REQUEST_STATUS").equals("Rejected"))
					            	{approvalDetails+="<td>"+oim.getUserLoginName(rs.getString("CURRENT_APPROVER"), oimClient) +" </td>"
								            +"<td>-</td>" 
								            +"<td>"+rs.getString("REQUEST_STATUS")+" </td>"
								               //+ "<a href='"+rs.getString(1)+"' id='reAsgnBtn' name='lnkViews' data-toggle='modal' data-target='#MyPopu'>Withdraw </a></td>" 
								            +"</tr>";}
					            	else {
					            	approvalDetails+="<td>"+oim.getUserLoginName(rs.getString("CURRENT_APPROVER"), oimClient) +" </td>"
					            +"<td>"+oim.getUserLoginName(rs.getString("CURRENT_APPROVER"), oimClient)+"</td>" 
					            +"<td>"+rs.getString("REQUEST_STATUS")+" </td>"
					               //+ "<a href='"+rs.getString(1)+"' id='reAsgnBtn' name='lnkViews' data-toggle='modal' data-target='#MyPopu'>Withdraw </a></td>" 
					            +"</tr>";
					            	}}
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
			
				out.print(approvalDetails);	
			}
			
			
			
			
			private void getPendingApprovals(String trackID,PrintWriter out)
			{
				
				String approvalDetails ="";
				String Justification="-";
				String comments="";
				List<String> commentsList = SOADBConnection.getRequestComments(trackID); 
			
				if(commentsList.size()>0)
				{
					for(String c:commentsList)
					{
						System.out.println(c);
						comments=comments+c;
					}
				}
				else {comments="-";}
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
								reqType.contains("Disable Account") ||
								reqType.contains("Enable Account") ||
								reqType.contains("Provision Entitlement")||
								reqType.contains("Provision ApplicationInstance")||
								reqType.contains("Revoke Entitlement")) {
							TrackingRequest tr1 = new TrackingRequest();
							try {
								LoginToOIM logoim = new LoginToOIM();
								OIMClient oimClient = logoim.loggedIntoOIM("xelsysadm", "Hblpoc_1234");
								Justification=tr1.getJustification(oimClient,trackID);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(Justification.equals(null))
							{
								Justification="-";
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
						System.out.println("JUSTIFICATION: "+Justification);
						approvalDetails+="<tr>"
								+"<td>"+rs.getString("CREATOR").toUpperCase()+" </td>"	
								+"<td>"+reqType+" </td>"
					               +"<td>"+rs.getString("ITEM_NAME")+" </td>" 
					            		   +"<td>"+Justification+" </td>"
					            		   +"<td>"+comments+" </td>"
					               +"<td>"+approver+" </td>"
					            		   +"<td>"+assignees+" </td>" 
					               +"<td>"+outcome+" </td>"
					               +"<td"
					               + ">"
					               //+ "<a href='"+rs.getString(1)+"' id='reAsgnBtn' name='lnkViews' data-toggle='modal' data-target='#MyPopu'>Withdraw </a></td>" 
				
					               +"</td></tr>";
						
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
				out.print(approvalDetails);	
			}
			
			
			private String withdrawRequest(String trackID,PrintWriter out,String userlogin,String upassword)
		     {
				String result=null;
				LoginToOIM connection=new LoginToOIM();
				OIMClient oimClient = null;
			
		 		try {
		 			//oimClient = connection.loggedIntoOIM("XELSYSADM", "New@12345");
		 			oimClient = connection.loggedIntoOIM(userlogin, upassword);
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
						reqsrvc.withdrawRequest(trackID.toString().trim());
						System.out.print("Success");
						return result="Request ID "+trackID+" has been withdrawn Successfully";
						
					} catch (oracle.iam.request.exception.AccessDeniedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return result=e.getMessage();
					} catch (RequestServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return result=e.getMessage();
					}
		 	    	
		 	    	
		 	 	//List<String> templateNames = req.getModelNames();
		 	    	//List<String> templateNames = req.getTemplateNames();
		 	    	//Long a  = req.getNumberOfRequestsCreatedByUser(1,rc);
		 	    	//System.out.print(a);
		 	/*		for(String s:templateName)
		 			{
		 				System.out.println(s);
		 			}*/
		 		
		 		
		     
				
			}

			public void requestCatalog(String reqID,PrintWriter out) throws RequestServiceException, NoRequestPermissionException, CatalogException
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
		    			 
		 				 
		 				catalogDetails+="<tr>"
								+"<td>"+entityName+" </td>"
								+"<td>"+benefic+" </td>"
				               +"<td>"+status+" </td>" 
				               +"<td>"+description+" </td>"
	
				               +"</tr>";
					
		    			 System.out.println("entity Name::"+catalog.getCategoryName());
		    			 System.out.println("Request Status::"+request.getRequestStatus());
		    			 System.out.println("Entity description::"+catalog.getEntityDisplayName());
		    			 
		    			 
//		    			 
		    		 }
		    		 
		    	 }
		    	 
		    	 out.print(catalogDetails);
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
			 
			 

}
