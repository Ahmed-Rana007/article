<%@page import="oracle.iam.identity.rolemgmt.vo.Role"%>
<%@page import="com.hbl.selfservice.UsersList"%>
<%@page import="com.hbl.provisioning.AccountCart"%>
<%@page import="java.util.List"%>
<%@page import="com.hbl.selfservice.objects.EntitlementObj"%>
<%@page import="com.hbl.provisioning.ProvisioningEntitlements"%>
<%@page import="com.hbl.provisioning.ProvisioningAccounts"%>
<%@page import="oracle.iam.platform.OIMClient"%>
<%@page import="java.util.Map"%>
<%@page import="com.hbl.selfservice.objects.AppInstanceObj"%>
<%@page import="com.hbl.selfservice.OIMUtils"%>
<%@page import="com.hbl.selfservice.OIMUser"%>
<%@page import="com.hbl.selfservice.UserSessaion"%>
<%@page import="com.hbl.provisioning.AccountCart"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">

<head>
	<title>Dashboard | HBL-SelfService</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<!-- VENDOR CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/linearicons/style.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/chartist/css/chartist-custom.css">
	<!-- MAIN CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">
	<!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
	<link rel="stylesheet" href="assets/css/demo.css">
	<!-- GOOGLE FONTS -->
	<link rel="stylesheet" href="assets/css/gfonts.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/gfonts.css">
	<!-- ICONS -->
	<link rel="apple-touch-icon" sizes="76x76" href="${pageContext.request.contextPath}/assets/img/apple-icon.png">
	<link rel="icon" type="image/png" sizes="96x96" href="${pageContext.request.contextPath}/assets/img/favicon.png">
	<style>
	.highlighted {
    background: red;
    background-color:red;
}
</style>
	<script type="text/javascript" src="js/jquery-1.12.4.js" ></script>
	<script>
		$(function() { 
		/// Add Entitlments to Cart code
		  $(document ).on("click","#acctsTbl button.addCart",function() {
			 var cartTotal =  $("#cartTotal").text();
			 var logintoCheckapps = $("#selfuserLogin").val();
		
			 
		    let tr = $(this).closest('tr');
		    let appInstanceKey = tr.find('td:eq(0)').text();
		    let appInstanceName = tr.find('td:eq(1)').text();
		    console.log("appInstanceKey:: "+ appInstanceKey +" = appInstanceName::"+appInstanceName );
			
		     let b = tr.find('td:eq(1)').text(); 
		     let btn = tr.find('td:eq(1)'); 
		     tr.find('td:eq(3) button').removeProp( "enabled")
		     tr.find('td:eq(3) button').prop('disabled', true);
		     
		     
		     var cartItems =  $("#cartitems").val();
		     console.log("Cart Items:  "+cartItems);
		     
		     var applicationCounter =  $("#applicationCounter").val();
		     
		     
		     console.log("total in cart before:"+applicationCounter);
		   //   $("#applicationCounter").html(parseInt(applicationCounter)+1);
		   applicationCounter++;
		   $("#applicationCounter").val(applicationCounter);
		     console.log("total in cart after:"+applicationCounter);
		     console.log($("#applicationCounter").val());
		     
		     var myRadio = $("input[name=optradio]");
			 var checkedValue = myRadio.filter(":checked").val();
			 console.log(checkedValue + " "+ cartTotal); 
			 if(checkedValue==null || checkedValue=="")
			 {
			 tr.find('td:eq(3) button').prop('disabled', false);
			 	alert("Please select request type");
			 	applicationCounter--;
		   $("#applicationCounter").val(applicationCounter);
			 }
			 else if(cartItems.includes(","+appInstanceKey))
			 {
			 alert("Application is already in cart");
			 }
			 else if(applicationCounter >=2)
			 {
			 alert("You can not add more than one application at Same time");
			 alert(applicationCounter);
			 tr.find('td:eq(3) button').prop('disabled', false);
			 }
			 else if(checkedValue =="1" ||checkedValue==1 )
			 {
			 	var selfLogin = $("#selfuserLogin").val();
			 	console.log(appInstanceName + " " + appInstanceKey + "  "+selfLogin );
			
			/////////////////////Check Applications//////////////////////////////////
			 	$.ajax({
						type: 'POST',
						data:{appInstanceName:appInstanceName,appInstanceKey:appInstanceKey,logintoCheckapps:logintoCheckapps,action:"checkapps"},
						url: 'AS400Check',
						success: function(result){
						 
						console.log(result);
						
						if(result=='true'){
						
						alert("Application already Provisioned");
						tr.find('td:eq(3) button').prop('disabled', false);
						applicationCounter--;
						$("#applicationCounter").val(applicationCounter);
						
						}
						else{
			
			
			///////////////////////////////////////
			
			if(appInstanceKey == '64' || appInstanceKey == '165')
			{
			 	$.ajax({
						type: 'POST',
						data:{appInstanceName:appInstanceName,appInstanceKey:appInstanceKey,selfLogin:selfLogin,action:"AddToCart"},
						url: 'AS400Check',
						success: function(result){
						 
						console.log(result);
						
						if(result=='true'){
						
						$.ajax({
						type: 'POST',
						data:{appInstanceName:appInstanceName,appInstanceKey:appInstanceKey,selfLogin:selfLogin,action:"AddToCart"},
						url: 'AccountProvisioningController',
						success: function(result){
						 $("#cartTotal").html(parseInt(cartTotal)+1);
						console.log(appInstanceName + " Added in cart" + result);
						alert(appInstanceName + " Added in cart");
						//tr.prop('disabled', true);
						// $(this).closest('tr').prop('disabled', true);
						tr.addClass('highlight');
						}
					});
						}
						else
						{
						alert("Please Provisioned AS400 First");
						 applicationCounter--;
		   $("#applicationCounter").val(applicationCounter);
		   tr.find('td:eq(3) button').prop('disabled', false);
		  
						}
						
						}
					});
					}
					else
					{
						$.ajax({
						type: 'POST',
						data:{appInstanceName:appInstanceName,appInstanceKey:appInstanceKey,selfLogin:selfLogin,action:"AddToCart"},
						url: 'AccountProvisioningController',
						success: function(result){
						 $("#cartTotal").html(parseInt(cartTotal)+1);
						console.log(appInstanceName + " Added in cart" + result);
						//tr.prop('disabled', true);
						// $(this).closest('tr').prop('disabled', true);
						tr.addClass('highlight');
						}
					});
					
					}  
			 	  ///////////////////////////////
			      
			 }}});}
			 else if(checkedValue =="2" ||checkedValue==2 )
			 {
			 	//var  = $("#selfuserLogin").val();
			 	var othersLogin = $('[name="userLoginToProv"]').val();
			 	console.log(appInstanceName + " " + b + "  "+othersLogin );
			 	if (othersLogin==null || othersLogin=='')
			 	{alert("Please Select Sub-Ordinate ID First");
			 	applicationCounter--;
		   $("#applicationCounter").val(applicationCounter);
		   
			 	tr.find('td:eq(3) button').prop('disabled', false);
			 	}
			 	else
			 	{
			 	var val = $("#userLoginToProv").val();
				console.log("LOGIN CHECKED");
	var obj = $("#userLoginProv").find("option[value='" + val + "']");

		if(obj != null && obj.length > 0)
	    	
	    	{
	    	
	    	//alert("valid");  // allow form submission
	    	if(appInstanceKey == '64' || appInstanceKey == '165')
			{
			 	$.ajax({
						type: 'POST',
						data:{appInstanceName:appInstanceName,appInstanceKey:appInstanceKey,othersLogin:othersLogin,action:"otherLogin"},
						url: 'AS400Check',
						success: function(result){
						 
						console.log(result);
						
						if(result=='true'){
			
	    				 $.ajax({
						type: 'POST',
						data:{appInstanceName:appInstanceName,appInstanceKey:appInstanceKey,othersLogin:othersLogin,action:"A2COthersLogin"},
						url: 'AccountProvisioningController',
						success: function(result){
						 $("#cartTotal").html(parseInt(cartTotal)+1);
						console.log(appInstanceName + " Added in cart" + result);
						}
					});
					}
						else
						{
						alert("Please Provisioned AS400 First");
						 applicationCounter--;
		   $("#applicationCounter").val(applicationCounter);
		   tr.find('td:eq(3) button').prop('disabled', false);
		  
						}
						
						}
					});
					}
			
			else{
			
						 $.ajax({
						type: 'POST',
						data:{appInstanceName:appInstanceName,appInstanceKey:appInstanceKey,othersLogin:othersLogin,action:"A2COthersLogin"},
						url: 'AccountProvisioningController',
						success: function(result){
						 $("#cartTotal").html(parseInt(cartTotal)+1);
						console.log(appInstanceName + " Added in cart" + result);
						}
					});
			
			}
					 
	    	}
		else
    	alert("invalid Beneficiary login id");
    	tr.find('td:eq(3) button').prop('disabled', false);
			 	
			   
					} 
			 }
		   
		    
		  });
		  /// Add Entitlments to Cart code
		  $(document ).on("click","#entsTbl button.addEntToCart",function() {
		 var cartTotal =  $("#cartTotal").text();
		    let tr = $(this).closest('tr');
		    let entitleListKey = tr.find('td:eq(0)').text();
		    let entName = tr.find('td:eq(1) a').text();
		     let b = tr.find('td:eq(1) a').text(); 
		     let btn = tr.find('td:eq(1) a'); 
		     var myRadio = $("input[name=optradio]");
			 var checkedValue = myRadio.filter(":checked").val();
			 console.log(checkedValue + " "+ cartTotal); 
			 if(checkedValue==null || checkedValue=="")
			 {
			 	alert("Please select request type");
			 }
			 else if(checkedValue =="1" ||checkedValue==1 )
			 {
			 	var selfLogin = $("#selfuserLogin").val();
			 	console.log(entitleListKey + " " + entName + "  "+selfLogin );  
			    $.ajax({
						type: 'POST',
						data:{entitleListKey:entitleListKey,entName:entName,selfLogin:selfLogin,action:"AddEntToCart"},
						url: 'AccountProvisioningController',
						success: function(result){
						 $("#cartTotal").html(parseInt(cartTotal)+1);
						console.log(entitleListKey + " -  " + entName +" Added in cart" + result);
						//tr.prop('disabled', true);
						// $(this).closest('tr').prop('disabled', true);
						tr.addClass('highlight');
						}
					});  
			 }
			 else if(checkedValue =="2" ||checkedValue==2 )
			 {
			 	//var  = $("#selfuserLogin").val();
			 	var othersLogin = $('[name="userLoginToProv"]').val();
			 	 
			 	console.log(entitleListKey + " " + entName + "  "+othersLogin );
			 	if(othersLogin ==null || othersLogin =='')
			 	{
			 	alert("Please Select Sub-Ordinate ID First")
			 	}  
			 	else{
			    $.ajax({
						type: 'POST',
						data:{entitleListKey:entitleListKey,entName:entName,othersLogin:othersLogin,action:"AddOthrEntToCart"},
						url: 'AccountProvisioningController',
						success: function(result){
						 $("#cartTotal").html(parseInt(cartTotal)+1);
						console.log(entName + " Added in cart" + result);
						}
					});
					}  
			 }
		   
		    
		  });
		  
		  //add roles to cart
		  $(document ).on("click","#roleLstTbl button.addRoleToCart",function() {
		 var cartTotal =  $("#cartTotal").text();
		    let tr = $(this).closest('tr');
		    let roleKey = tr.find('td:eq(0)').text();
		    let roleName = tr.find('td:eq(1) a').text();
		     let b = tr.find('td:eq(1) a').text(); 
		     let btn = tr.find('td:eq(1) a'); 
		     var myRadio = $("input[name=optradio]");
			 var checkedValue = myRadio.filter(":checked").val();
			 console.log(checkedValue + " "+ cartTotal); 
			 if(checkedValue==null || checkedValue=="")
			 {
			 	alert("Please select request type");
			 }
			 else if(checkedValue =="1" ||checkedValue==1 )
			 {
			 	var selfLogin = $("#selfuserLogin").val();
			 	console.log(roleKey + " " + roleName + "  "+selfLogin );  
			    $.ajax({
						type: 'POST',
						data:{roleKey:roleKey,roleName:roleName,selfLogin:selfLogin,action:"AddRoleToCart"},
						url: 'AccountProvisioningController',
						success: function(result){
						 $("#cartTotal").html(parseInt(cartTotal)+1);
						console.log(roleKey + " -  " + roleName +" Added in cart" + result);
						//tr.prop('disabled', true);
						// $(this).closest('tr').prop('disabled', true);
						tr.addClass('highlight');
						}
					});  
			 }
			 else if(checkedValue =="2" ||checkedValue==2 )
			 {
			 	//var  = $("#selfuserLogin").val();
			 	var othersLogin = $('[name="userLoginToProv"]').val();
			 	 
			 	console.log(roleKey + " " + roleName + "  "+othersLogin );
			 	if(othersLogin==null || othersLogin=='null')
			 	{alert("Please Select Sub-Ordinate ID First")}
			 	else{  
			    $.ajax({
						type: 'POST',
						data:{roleKey:roleKey,roleName:roleName,othersLogin:othersLogin,action:"AddOthrRoleToCart"},
						url: 'AccountProvisioningController',
						success: function(result){
						 $("#cartTotal").html(parseInt(cartTotal)+1);
						console.log(roleName + " Added in cart" + result);
						}
					});
					}  
			 }
		   
		    
		  });
		  //end roles to cart
		});
		
		$(document).ready(function(){
			$('#optradio1').click(function(){
				  
				 var myRadio = $("input[name=optradio]");
				 var checkedValue = myRadio.filter(":checked").val();
				 $('#userLoginToProv').attr("disabled", true);
				 
				 $('#optradio2').prop('disabled', false);
				 //alert(checkedValue);
			});
			$('#optradio2').click(function(){
				  
				 var myRadio = $("input[name=optradio]");
				 var checkedValue = myRadio.filter(":checked").val();
				 $('#userLoginToProv').attr("disabled", false);
				$('#optradio1').prop('disabled', false);
				//$('#reqForOthrs').show();
				
				// alert(checkedValue);
			});
			 $('#userLoginToProv').change(function(){
			 	$('#userLoginToProv').prop("disabled",true);
 			   });
		});
		
		function addCartItem(value)
		{
			return value+1;
		}
		
		
		//superuser
		           $(document).ready(function(){
			$('#restSuperPass').click(function(){
					
					var superUser = $('#superUser').val();
					if(superUser.length!=6)
					{
					alert("Please enter Mysis ID");
					return;
					}
				
					console.log(superUser);
				
				//alert("ajax call");
				$.ajax({
					type: 'POST',
					data:{superUsername:superUser,action:"superPasswordReset"},
					url: 'ResetSuperUserPass',
					success: function(result){
				
						console.log(result);
						
						
						alert(result.ResultID+" Request has been Submitted!");
						$('.resetSupPass').modal('hide');
					
				
					},
                  error: function (error) {
                  alert("Internal Error Occured!");
                   }
				}); 
				
				
				
				 
			});
			
			});
		//
</script>

</head>

<body>
<%
	String name =(String) session.getAttribute("username");
	System.out.println("Name->>>>>>>>"+name);
	if(name!=null && name.length() !=0)
	{
		session.setAttribute("username", name);
	}
	else
		response.sendRedirect("index.jsp");
	
	
	
	List<AccountCart> acctCartList = (List<AccountCart>)session.getAttribute("acctCatList");
	int cartNumber;
	String cartString=",";
	try{
	 cartNumber = acctCartList.size();
	 
	 for (AccountCart acctList : acctCartList) {
	/* if(acctList.getAppInstacneKey().contains("2") ||acctList.getAppInstacneKey().contains("165"))
	 {
	 	cartString+=(new StringBuilder()).append("Mysis").append(",").toString();
	 }
	 else{*/
	 cartString+=(new StringBuilder()).append(acctList.getAppInstacneKey()).append(",").toString();  
	 System.out.println("Cart String->>>>>"+cartString);
	 //}
	 
	 }
	 System.out.println("Cart String final->>>>>"+cartString);
	}
	catch(NullPointerException e){
	
		 cartNumber=0;
		  cartString=null;	
	}
	System.out.println("CartNumber- >>>>>>>"+cartNumber);


	
 %>
	<!-- WRAPPER -->
	<div id="wrapper">
		<!-- NAVBAR -->
		<nav class="navbar navbar-default navbar-fixed-top">
			<div class="brand">
				<a href="index.html"><img src="${pageContext.request.contextPath}/assets/img/hbl-logo.png" alt="HBL Logo" class="img-responsive logo"></a>
			</div>
			<div class="container-fluid">
				<div class="navbar-btn">
					<button type="button" class="btn-toggle-fullwidth"><i class="lnr lnr-arrow-left-circle"></i></button>
				</div>
				
				<div class="navbar-btn navbar-btn-right">
					
				</div>
				<div id="navbar-menu">
					<ul class="nav navbar-nav navbar-right">
						
						
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="assets/img/user.png" class="img-circle" alt="Avatar"> <span><%= session.getAttribute("username") %></span> <i class="icon-submenu lnr lnr-chevron-down"></i></a>
							<ul class="dropdown-menu">
								<li><a href="#"><i class="lnr lnr-user"></i> <span>My Profile</span></a></li>
								<li><a href="#"><i class="lnr lnr-envelope"></i> <span>Message</span></a></li>
								<li><a href="LogoutServlet"><i class="lnr lnr-exit"></i> <span>Logout</span></a></li>
							</ul>
						</li>
						 <li>
							<a href="ViewCart.jsp"><i class="fa fa-shopping-cart"></i> <span class="badge" id="cartTotal" ><%=cartNumber %></span></a>
							 <input type="hidden" id="applicationCounter" name="applicationCounter" value='<%=cartNumber %>'>
						</li> 
					</ul>
				</div>
			</div>
		</nav>
		<!-- END NAVBAR -->
		<!-- LEFT SIDEBAR -->
		<div id="sidebar-nav" class="sidebar">
			<div class="sidebar-scroll">
				<nav>
					<ul class="nav">
						<li></li>
						<li><a href="HomePage.jsp" class=""><i class="fa fa-home"></i> <span>Dashboard</span></a></li>
						<li><a href="myaccess.jsp" class=""><i class="fa fa-info-circle"></i> <span>My Access</span></a></li>
						<li><a href="certDashboard.jsp" class=""><i class="lnr lnr-chart-bars"></i> <span>Certifications</span></a></li>
						<li><a href="PendingApproval.jsp" class=""><i class="fa fa-check-circle"></i> <span>Pending Approvals</span></a></li>
						<li><a href="PendingRequest.jsp" class=""><i class="lnr lnr-dice"></i> <span>Pending Request</span></a></li>
						<li><a href="ManageUsers.jsp" class=""><i class="fa fa-user-o"></i> <span>Manage User</span></a></li>
						 <li><a href="TrackRequest.jsp" class=""><i class="fa fa-clock-o"></i> <span>Track Request</span></a></li>
						<li><a href="makerequest.jsp" class="active"><i class="fa fa-plus-circle"></i> <span>Make a Request</span></a></li>
									</ul>
				</nav>
			</div>
		</div>
		
		<!-- END LEFT SIDEBAR -->
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content">
				<div class="container-fluid">
					<div class="panel panel-profile">
						<div class="clearfix">
							<!-- LEFT COLUMN -->
							<div class="profile-left" style="width:25% !Important;">
								<div class="tabs-line-bottom left-aligned"><br />
									<h4 style="padding-left:15px;">Catalog</h4><br />
									<ul class="nav" role="tablist">
										<li class="active"><a href="#tab-bottom-left1" role="tab" data-toggle="tab">Applications</a></li>
										
										<li><a href="#tab-bottom-left2" role="tab" data-toggle="tab">Entitlements <!--span class="badge">7</span--></a></li>
										<li><a href="#tab-bottom-left3" role="tab" data-toggle="tab">Roles <!--span class="badge">7</span--></a></li>
										<li><a href="#" data-toggle="modal" data-target= ".resetSupPass">Reset Super Password <!--span class="badge">7</span--></a></li>
									</ul>
									<br />
									<br />
									
									<div class="table-responsive" id="reqForOthrs" hidden>
									<table class="table project-table" id="selectedUsers">
									<thead>
									<h3>Selected users</h3>
									</thead>
									<tbody>
													
									
									</tbody>
									</table>
									</div>
								</div>
								<!-- END PROFILE DETAIL -->
							</div>
							<!-- END LEFT COLUMN -->
							<!-- RIGHT COLUMN -->
							<div class="profile-right" style="width:75% !Important;">
								<h3>Request Access</h3>  
								<div class="radio">
								  <label><input type="radio" name="optradio" id="optradio1" value="1" >Request for Self</label>
								  <label><input type="radio" name="optradio" id="optradio2" value="2" >On Behalf Request</label>
								  <span style="margin-left:40px;"> Beneficiary login id </span><input list="userLoginProv" name="userLoginToProv" id="userLoginToProv" disabled/>
									<datalist id="userLoginProv">
										 <%
	 					OIMUtils oimUtils = new OIMUtils();
	 	 
						OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
							 	//Map<String,OrganizationObj> lineManagerList = oimUtils.getAllOrganizations (UserSessaion.getOimClient()) ;
							 	Map<Long,UsersList> lineManagerList = oimUtils.getAllUsers(oimClient)  ;
							 	
							 	//Map<String,OrganizationObj> orgList =  oimUtils.getAllOrganizations(UserSessaion.getOimClient());
							 	for(Map.Entry<Long, UsersList> userListMap: lineManagerList.entrySet()) {
							 	if(lineManagerList.get(userListMap.getKey()).getUserLogin().equals(name.toUpperCase().trim()))
							 	{continue;}
							 		//out.write("<option value='"+orgList.get(orgListMap.getKey()).getOrganiztionActkey() +"'>"+orgList.get(orgListMap.getValue()).getOrganizationName()+"</option>"); 
							 		out.write("<option value='"+ lineManagerList.get(userListMap.getKey()).getUserLogin()+"'/>");
							 	}
							  %>
									</datalist>
								</div>
								<!-- END AWARDS -->
								<!-- TABBED CONTENT -->
								<div class="tab-content">
								<!--  
									<div class="input-group">
										<input type="text" value="" class="form-control" placeholder="Search dashboard...">
										<span class="input-group-btn"><button type="button" class="btn btn-primary" style="background-color:#009591;">Go</button></span>
									</div>
									!-->
									<div class="tab-pane fade in active" id="tab-bottom-left1">

										<div class="table-responsive">

											<table class="table project-table" id="acctsTbl">
												<thead>
													<tr>
														<th>Application Display Name</th>
														 
														<th>Add</th>
													</tr>
												</thead>
												<tbody>
												<%  
													ProvisioningAccounts provAccts = new ProvisioningAccounts();
													 oimClient = (OIMClient) session.getAttribute("oimClient"); 
													OIMUser oimUser = (OIMUser) session.getAttribute("oimUser");
													%> 
														<input type="hidden" name="selfuserLogin" id="selfuserLogin" name="selfuserLogin" value="<%= session.getAttribute("username") %>"/>
														<input type="hidden" name="cartitems" id="cartitems" name="cartitems" value="<%= cartString %>"/>
													<%
													Map<Long, AppInstanceObj> appsListMap = provAccts.getAllAppInstance(oimClient);
													boolean mysis = false;
													 for(Map.Entry<Long, AppInstanceObj> accountMap: appsListMap.entrySet() ) {
													 if(appsListMap.get(accountMap.getKey()).getAppInstanceDisplayName().equals("SSOTarget4")||
													 appsListMap.get(accountMap.getKey()).getAppInstanceDisplayName().equals("SSOTrusted-for-SSOTarget4"))
													 {continue;}
													/* if(appsListMap.get(accountMap.getKey()).getAccountName().toString().trim().equals("AS400") || appsListMap.get(accountMap.getKey()).getAccountName().toString().trim().equals("Equation"))
													 {
													 	if(!mysis){
													 	out.write(
													 		"<tr>"
													 	 
													 	+"<td style='display:none;'>Mysis</td>"
														+"<td> Mysis/Equation</td>"
														 +"<td>Mysis/Equation</td>"
														+"<td><button  id='addCart' class='label label-success addCart' enabled>Add to Cart</button></td>"
														 
														+"</tr>"); mysis=true;}
														else continue;
													 }
													 else{*/
													 		out.write(
													 		"<tr>"
													 	 
													 	+"<td style='display:none;'>"+appsListMap.get(accountMap.getKey()).getAppInstanceKey()+"</td>"
														+"<td>"+appsListMap.get(accountMap.getKey()).getAccountName()+"</td>"
														 +"<td>"+appsListMap.get(accountMap.getKey()).getAppInstanceDisplayName()+"</td>"
														+"<td><button  id='addCart' class='label label-success addCart' enabled>Add to Cart</button></td>"
														 
														+"</tr>");
													//	}
													 }
													
												 %>
													
												</tbody>
											</table>
										</div>
										<div class="margin-top-30 text-center"><a href="LimitEnhancementSrvlt" class="btn btn-default">Equation Limit Enhancment</a></div>
									</div>
									<div class="tab-pane fade" id="tab-bottom-left2">
										<div class="table-responsive">

											<table class="table project-table" id="entsTbl" name="entsTbl">
												<thead>
													<tr>
														<th>Entitlement Name</th>
														<th>Account Name</th>
														 
														
													</tr>
												</thead>
												<tbody>
													<%  
													 
													ProvisioningEntitlements provEntObj = new ProvisioningEntitlements();
													//OIMClient oimClient = (OIMClient) session.getAttribute("oimClient"); 
													//OIMUser oimUser = (OIMUser) session.getAttribute("oimUser");
													Map<Long, EntitlementObj> entsListMap = provEntObj.getAllEntitlements(oimClient);
													 for(Map.Entry<Long, EntitlementObj> entListMap: entsListMap.entrySet() ) {
													 		out.write(
													 		"<tr>"
													 		+"<td style='display:none;'>"+entsListMap.get(entListMap.getKey()).getEntitlementKey() +" </td>"
														+"<td><a href='#'>"+entsListMap.get(entListMap.getKey()).getEntitlementName()+"</a></td>"
														 +"<td><a href='#'>"+entsListMap.get(entListMap.getKey()).getAppInstanceName()+"</a></td>"
														 
														+"<td><button  id='addEntToCart' class='label label-success addEntToCart' enabled>Add to Cart</button></td>"
														+"</tr>");
													 }
													
												 %>
												</tbody>
											</table>
										</div>
									</div>








									<div class="tab-pane fade" id="tab-bottom-left3">
										<div class="table-responsive">

											<table class="table project-table" id ="roleLstTbl">
												<thead>
													<tr>
														<th>Role Name</th>
														<th>Description</th>
														
													</tr>
												</thead>
												<tbody>
													<%  
													 
													 
													OIMUtils oimutils = new OIMUtils();
													 oimClient = (OIMClient) session.getAttribute("oimClient"); 
													//OIMUser oimUser = (OIMUser) session.getAttribute("oimUser");
													List<Role> roleList = oimUtils.getRole(oimClient);
													 for(Role roles : roleList ) {
													 		out.write(
													 		"<tr>"
													 		+"<td style='display:none;'>"+ roles.getEntityId().toString() +" </td>"
														+"<td><a href='#'>"+roles.getName()+"</a></td>"
														 +"<td><a href='#'>"+roles.getDisplayName()+"</a></td>"
														 
														+"<td><button  id='addRoleToCart' class='label label-success addRoleToCart' enabled>Add to Cart</button></td>"
														+"</tr>");
													 }
													
												 %>
												</tbody>
											</table>
										</div>
									</div>
								</div>
									
								<!-- END TABBED CONTENT -->
							</div>
							<!-- END RIGHT COLUMN -->
						</div>
					</div>
				</div>
			</div>
			<!-- END MAIN CONTENT -->
		</div>
		<!-- END MAIN -->
		<div class="clearfix"></div>
		<footer>
			<div class="container-fluid">
				<p class="copyright">&copy; 2021 <a href="#" target="_blank">HBL Pvt Ltd</a>. All Rights Reserved.</p>
			</div>
		</footer>
	</div>
	
			<!-- Reset SuperPassword  -->
		<div class="modal fade resetSupPass" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">Reset Super Password</h4>
            </div>
            <div class="modal-body">
                        <div class="form-row">
            <div class="col-md-4"><label for="ApplicationName">Enter Mysis ID: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="superUser" placeholder="Enter Super Username" required></div>
                        </div>
                    
            
                        <div class="clearfix"></div>
                            
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="restSuperPass" class="btn btn-primary" style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div>
	<!-- Reset SuperPassword  -->
	<!-- END WRAPPER -->
	<!-- Javascript -->
	<script src="assets/vendor/jquery/jquery.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script src="assets/scripts/klorofil-common.js"></script>
</body>

</html>
