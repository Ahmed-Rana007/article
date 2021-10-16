<%@page import="com.bea.core.repackaged.springframework.context.annotation.Role"%>
<%@page import="com.hbl.selfservice.objects.RoleUser"%>
<%@page import="java.util.List"%>
<%@page import="oracle.iam.platform.OIMClient"%>
<%@page import="com.hbl.selfservice.objects.EntitlementObj"%>
<%@page import="java.util.Map"%>
<%@page import="com.hbl.selfservice.objects.AppInstanceObj"%>
<%@page import="com.hbl.selfservice.OIMUtils"%>
<%@page import="com.hbl.selfservice.OIMUser"%>
<%@page import="com.hbl.selfservice.UserSessaion"%>
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
	<!-- Password Reset CSS -->
	 <style>
        #dialog-overlay {
            background: #000;
            bottom: 0;
            display: none;
            height: 100%;
            left: 0;
            margin-left: auto;
            margin-right: auto;
            opacity: 0.75;
            position: fixed;
            right: 0;
            top: 0;
            width: 100%;
            z-index: 99998;
        }

        .dialog-popup {
            background: none repeat scroll 0 0 #CCCCCC;
            border: 2px solid #222222;
            border-radius: 3px 3px 3px 3px;
            font-family: Verdana,Geneva,sans-serif;
            padding: 10px;
            display: none;
            left: 50%;
            position: fixed;
            top: 50%;
            z-index: 99999; 
        }

        .dialog-popup .close-button {
            float: right;
            margin: -26px -26px 0 0;
        }

        .dialog-popup form fieldset {
            border: medium none;
            padding: 0;
        }

        .dialog-popup form fieldset label {
            display: block;
            font-size: 1.2em;
        }

        .dialog-popup form fieldset label span,
        .dialog-popup form fieldset label input,
        .dialog-popup form button,
        .dialog-popup form a {
            display: block;
            margin-bottom: 10px;
        }

        .dialog-popup form fieldset label input {
            background: none repeat scroll 0 0 #EEEEEE;
            border-color: #FFFFFF #DDDDDD #DDDDDD #FFFFFF;
            border-radius: 3px 3px 3px 3px;
            border-style: solid;
            border-width: 1px;
            color: #000000;
            padding: 6px 6px 4px;
            width: 200px;
        }

        .dialog-popup form button.submit {
            border-color: #000000;
            border-radius: 3px 3px 3px 3px;
            border-width: 1px;
            cursor: pointer;
            margin-top: 10px;
            padding: 6px 6px 4px;
            width: 200px;
        }

        .dialog-popup form button.submit:hover {
            background-color: #E0E0E0;
        }    

        #forgot-password-link {
            color: #00AE00;
            font-size: 0.7em;
            text-align: center;
            text-decoration: none;
            width: 200px;
        }
        
        .hblbtn {
			background-color: #009591;
			color: white;

			transition-duration: 0.3s;
		}

			.hblbtn:hover {
				background-color: white;
				color: #009591;
			}
			
			.hblred {
			background-color: #a02030;
			color: white;
			
			transition-duration: 0.3s;
		}

			.hblred:hover {
				background-color: #F9354C;
				color: white;
			}
    </style>
	<!--  -->
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
	<script type="text/javascript" src="js/jquery-1.12.4.js" ></script>
	<script type="text/javascript">
	//
		$(document).ready(function(){
			$('#btnChangePassword').click(function(){
			console.log("btn clicked");
				var newPassword = $('#newPassword').val();
				var confirmPassword = $('#confirmPassword').val();
				if (newPassword !='' && confirmPassword !=''){
				$.ajax({
					type: 'POST',
					data:{newPassword:newPassword,confirmPassword:confirmPassword,action:"changePass"},
					url: 'UserOperationsController',
					success: function(result){
					console.log(result);
					
					//alert("Password Changed Successfully");
					}
				});
				
				}else{
				
				alert("Please Enter Valid New/Confirm Passwords!");
				}
				 
			}); 
			
			});
			//endsubmitButton
			
			//
			$(document ).on("click","#acctTable button.acctDisable",function() {
		    let tr = $(this).closest('tr');
		    let a = tr.find('td:eq(0)').text();
		    let b = tr.find('td:eq(1)').text(); 
		    console.log(a +" " + b);
		    //console.log("Hello");
		   
		    //var action =null;
		    
		    $.ajax({
					type: 'POST',
					data:{appName:b,appKey:a,action:"acctDisable"},
					url: 'AccountDisableEnable',
					success: function(result){
				    alert("Request Initiated!");
					 console.log(result);
					 
					}
					 
				});   
		  });
	//repair
		//Enable Button Start
		$(document ).on("click","#acctTable button.acctEnable",function() {
		    let tr = $(this).closest('tr');
		    let a = tr.find('td:eq(0)').text();
		    let b = tr.find('td:eq(1)').text(); 
		    console.log(a +" " + b);
		    //console.log("Hello");
		   
		    //var action =null;
		    
		    $.ajax({
					type: 'POST',
					data:{appName:b,appKey:a,action:"acctEnable"},
					url: 'AccountDisableEnable',
					success: function(result){
				    alert("Request Initiated!");
					 console.log(result);
					 
					}
					 
				});   
		  });
		//Enable Button End
	 
		//Revoke Entitlement Start
		
	$(document ).on("click","#EntTable button.acctREVO",function() {
		    let tr = $(this).closest('tr');
		    let a = tr.find('td:eq(0)').text();
		    let b = tr.find('td:eq(1)', a).text();
		    console.log(a+ ""+ b);
		   
		    
		    $.ajax({
					type: 'POST',
					data:{entName:b,entKey:a},
					url: 'EntitlementRevoke',
					
					success: function(result){
					
					 	 if (result.ID=="notProvisioned")
					 alert("The "+result.EntName+" is already Not Provisioned!");
					 else
					 alert("Revoke Request is initiated againt Entitlement "+result.EntName);
					 
					}
					 
				});   
		  });
		//Revoke Entitlement End
		
		
		//
		//Account Disbale Dropdown start
	
   $(document).on("click","a[name='acctDisable']", function (e) {
    		console.log($(this).attr('href'));
    		
    	let tr = $(this).closest('tr');
		    let a = tr.find('td:eq(0)').text();
		    let b = tr.find('td:eq(1)').text(); 
		    console.log(a +" " + b);
    
    		
    	$.ajax({
					type: 'POST',
					data:{appName:b,appKey:a,action:"acctDisable"},
					url: 'AccountDisableEnable',
					success: function(result){
				    alert(result.ID+" Request Initiated!");
					 console.log(result);
					 
					} 
				}); 
		}); 
		//Disable dropdow end
		
		//Enable dropdown Start
		$(document).on("click","a[name='acctEnable']", function (e) {
    		console.log($(this).attr('href'));
    		
    	let tr = $(this).closest('tr');
		    let a = tr.find('td:eq(0)').text();
		    let b = tr.find('td:eq(1)').text(); 
		    console.log(a +" " + b);
    
    		
    	$.ajax({
					type: 'POST',
					data:{appName:b,appKey:a,action:"acctEnable"},
					url: 'AccountDisableEnable',
					success: function(result){
				    alert(result.ID+" Request Initiated!");
					 console.log(result);
					 
					} 
				
		}); 
		});
		//Enable dropdown end
		//
		$(document).on("click","a[name='resetPWD']", function (e) {
		
	
		
		console.log($(this).attr('href'));
    		
    	    var tr = $(this).closest('tr');
		    
		    var b = tr.find('td:eq(1)').text(); 
		    var a = tr.find('td:eq(0)').text();
		    console.log(b+""+a);
		  	 $("#otp").html('');
		    $("#AppHead1").html(b);
		    $("#appName1").html(a);
		      $("#AppHead").html(b);
		    $("#appName").html(a);
			$.ajax({
			async: false,
				type: 'POST',
				data:{action: "OTPchangepasswordAPP"},
				url: 'OTP_Application_Servlet',
				success: function (result){
					var status = result.Status;
					console.log(status);
					alert(status);
					$('#myModalotp').modal('show');
				}
			
			
			});
		    
		
		}); 
		
		 $(document).ready(function(){
			$('#btnotp').click(function(){
			//console.log("btn clicked");
				var otp = $('#otp').val();
				//alert("OTP: "+ otp);
				$.ajax({
					type: 'POST',
					data:{otp:otp,action:"otpRedirect"},
					url: 'OTP_Validation',
					
					success: function(result){
					//alert("Result: "+result);
					console.log(result);
					//alert(result.qq);
					if (result.qq=="True"){
					$("body").css("cursor", "default");
					alert("Otp Matched");
					$('#myModalotp').modal('hide');
					$('#myModal').modal('show');
					//window.location.href = "ChangePassword.jsp";
					}
					else{
					$("body").css("cursor", "default");
					alert("Please Enter Valid OTP");
				    $('#myModalotp').modal('show');
					}
					
				}  
			}); 
			}); 
			});
		//resetPassword end
		//
		/*  $(document).on("click","a[name='resetPWD']", function (e) {
		
	
		
		console.log($(this).attr('href'));
    		
    	    var tr = $(this).closest('tr');
		    
		    var b = tr.find('td:eq(1)').text(); 
		    var a = tr.find('td:eq(0)').text();
		    console.log(b+""+a);
		    $("#AppHead").html(b);
		    $("#appName").html(a);
		
		    $('#myModal').modal('show');
		
		}); 
		//resetPassword end */
		
		//
		$(document).ready(function(){
			$('#restAppPassword').click(function(){
			
			console.log("Salahuddin:: here");
				var appName = $('#AppHead').text();
				var appKey = $('#appKey').text();
			    var newPasword = $('#newRPass').val();
				var confPasword = $('#confRPass').val();
				
				//console.log(newPasword);	
				console.log(appKey);
				
				if (newPasword!='' && confPasword !='' ){
				if(appName=='AS400')
				{
				var As400Policy=/^[a-zA-Z0-9]{9,10}$/;
				var As400Seq=/(?:(?=01|12|23|34|45|56|67|78|89)\d)+\d/g;
	if (document.getElementById("newRPass").value.search(As400Policy)==-1 || document.getElementById("confRPass").value.search(As400Policy)==-1)
	{
		alert("Please Folow the Password Policy for AS400 \n Minimum 9 Char Max 10 Characters \nPassword Must be Alpha Numeric \nPassword not Contains Special Character \nNumber Should not be in Sequence");
		return;
	}
	if(!(document.getElementById("newRPass").value.search(As400Seq)==-1) || !(document.getElementById("confRPass").value.search(As400Seq)==-1))
	{	alert("Number Should not be in Sequence");
		return;
	}
	
	}
	else if(appName=='ActiveDirectory' || appName=='CP1')
				{
				var Policy=/^([A-Z])(?=.*\d)(?=.*[a-z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,9}$/;
				
	if (document.getElementById("newRPass").value.search(Policy)==-1 || document.getElementById("confRPass").value.search(Policy)==-1)
	{
		alert("Please Folow the Password Policy \n Minimum 9 Characters Max 10 Characters \nStart with Upper letter \nAt-least one Special Char \nAt-leaset one upper letter \nAt-leaset one lower letter");
		return;
	}
	}
				
				$.ajax({
					type: 'POST',
					data:{appName:appName,appKey:appKey,newPass:newPasword,confPass:confPasword},
					url: 'changeApplicationPassword',
					success: function(result){
				   
					console.log(result.status);
					
					if (result.status =="true"){
					
					$('#myModal').modal('hide');
					
					alert("Password has been Changed!");
					$('#newRPass').val("");
					$('#confRPass').val("");
					
					}else{
					
					alert("New Password MISMATCHED Confirm Password! ");
					
					}
					
				
					 
				 }
				 
				 
				
				});
				
				
				
					}else{
					
					   alert("Please Enter Valid New/Confirm Passwords!");
					}
				
				});
				});
		//
		$(document).ready(function(){
			$('#submitLeave').click(function(){
				var startDate = $('#startdate').val();
				var dateAs = startDate.split('-');
				var newStartDate = dateAs[1] + '/' + dateAs[2] + '/' + dateAs[0];
				
				var endDate = $('#enddate').val();
				var dateAe = endDate.split('-');
				var newEndDate = dateAe[1] + '/' + dateAe[2] + '/' + dateAe[0];
				
				var leaveType = $("#leave").val();
				console.log(newStartDate+" "+newEndDate+" "+leaveType);
				console.log(dateAs[2]+" "+ dateAe[2]);
				
				if(newEndDate >= newStartDate){
				
				//alert("ajax call");
				$.ajax({
					type: 'POST',
					data:{startDate:newStartDate,endDate:newEndDate,leaveType:leaveType},
					url: 'LeaveManagement',
					success: function(result){
				
						console.log(result);
						
						
						alert(result.ResultID+" Leave Application Successfully Applied!");
						$('#startdate').val("");
						$('#enddate').val("");
						
					
				
					},
                  error: function (error) {
                  alert("Internal Error Occured!");
                   }
				}); 
				
				}else{
				
					alert("END DATE must be GREATER than START DATE");
				}
				
				 
			});
			
			});
	
		
		//
	</script>

	
</head>

<body>
<%@page import="com.hbl.selfservice.objects.AppInstanceObj"%><%
	String name =(String) session.getAttribute("username");
	
	if(name!=null && name.length() !=0)
	{
		session.setAttribute("username", name);
	}
	else
		response.sendRedirect("index.jsp");

OIMUtils oimutils = new OIMUtils();
OIMClient oimClient = (OIMClient) session.getAttribute("oimClient"); 
OIMUser oimUser = (OIMUser) session.getAttribute("oimUser");
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
					<button type="button" class="btn hbl btn-sm"><i class="lnr lnr-arrow-left-circle"></i></button>
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
						<li><a href="myaccess.jsp" class="active"><i class="fa fa-info-circle"></i> <span>My Access</span></a></li>
						<li><a href="certDashboard.jsp" class=""><i class="lnr lnr-chart-bars"></i> <span>Certifications</span></a></li>
						<li><a href="PendingApproval.jsp" class=""><i class="fa fa-check-circle"></i> <span>Pending Approvals</span></a></li>
						<li><a href="PendingRequest.jsp" class=""><i class="lnr lnr-dice"></i> <span>Pending Request</span></a></li>
						<li><a href="ManageUsers.jsp" class=""><i class="fa fa-user-o"></i> <span>Manage User</span></a></li>
						 <li><a href="TrackRequest.jsp" class=""><i class="fa fa-clock-o"></i> <span>Track Request</span></a></li>
						<li><a href="makerequest.jsp" class=""><i class="fa fa-plus-circle"></i> <span>Make a Request</span></a></li>
									</ul>
				</nav>
			</div>
		</div>
		
		//
			
		<!-- END LEFT SIDEBAR -->
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content" style="padding-top:0px;">
				<div class="container-fluid">
					<!--  New Access Dashboard start -->
						<h3>My Access</h3><br />
					<div class='row'>
					<%
										
												 	
													Map<Long,AppInstanceObj> acctProviosnedList = oimutils.fetchProvisionedAccountsOfUser(oimUser.getUserKey().toString(),oimClient);
													int totalAppsPerUser = acctProviosnedList.size();
													String totalAppsPerUserStr = totalAppsPerUser +"";
													Map<Long,EntitlementObj> entProviosnedList = oimutils.getUserEntitlementInstances(oimUser.getUserKey().toString(),oimClient) ;
													List<RoleUser> rolesList = oimutils.fetchUserRole(oimUser.getUserKey().toString(), oimClient);
													//int totalEntPeruser = entProviosnedList.size();
										 %>
						<div class='col-md-4'>
							<div class='panel' >
								<div class='panel-heading' style='background-color:#44505D;color:white;'>
									<h4 class='heading' style='font-weight:600'>Application</h4>
									<p style='font-weight:100'>Applications that I have access to</p>
								</div>
								<div class='panel-body'>
									<div class='table-responsive'>
										<table class=' table-borderless' style='width:100%;'>
											 
											<tbody>
												<%
													
													 for(Map.Entry<Long, AppInstanceObj> accountsMap: acctProviosnedList.entrySet() ) 
													 {
        												out.write("<tr>"
															+"<td>"
														+"<h5 class='group card-title inner list-group-item-heading' style='font-weight:600;font-size:14px;'>"
															+acctProviosnedList.get(accountsMap.getKey()).getAppInstanceDisplayName()
														+"</h5>"
													+"</td>"
													+"<td>"
													+"</td>"+
													
													"<td style='text-align:center'>");
													if(acctProviosnedList.get(accountsMap.getKey()).getAccountStatus().equals("Disabled"))
													{
														out.write("<span class='label label-danger' style='border-radius:3.25em;'>Disabled</span>");
													}
													else{
														out.write("<span class='label label-success' style='border-radius:3.25em;'>Enabled</span>");
														}
												out.write("</td>"
												+"</tr>"
												+"<tr>"
													+"<td>"
														+"<p class='group inner list-group-item-text' style='font-weight:100;font-size:13px;'>"
															+acctProviosnedList.get(accountsMap.getKey()).getAccountId()
														+"</p>"
													+"</td>"
												+"</tr>"
												
												+"<tr>"
													+"<td style='visibility:hidden'>"+acctProviosnedList.get(accountsMap.getKey()).getAccountOIUKey()+"</td>"
													+"<td style='visibility:hidden'>"+acctProviosnedList.get(accountsMap.getKey()).getAppInstanceName()+"</td>");
													if((acctProviosnedList.get(accountsMap.getKey()).getAppInstanceDisplayName().equals("Equation") ||
													 (acctProviosnedList.get(accountsMap.getKey()).getAppInstanceDisplayName().equals("EQGroup"))))
													{
													out.write("<td style='visibility:hidden'>"
														+"<br />"
														+"<ul class='list-inline' style='text-align:center'>"
															+"<li><a href='#' data-toggle='tooltip' name ='resetPWD' id ='resetPWD' data-placement='top' title='Password Reset'><span class='label label-warning'><i class='fa fa-key '></i></span></a></li>"
														+"</ul>"
													+"</td>"
												+"</tr> ");
													}
													else
													{
													out.write("<td>"
														+"<br />"
														+"<ul class='list-inline' style='text-align:center'>"
															+"<li><a href='#' data-toggle='tooltip' name ='resetPWD' id ='resetPWD' data-placement='top' title='Password Reset'><span class='label label-warning'><i class='fa fa-key '></i></span></a></li>"
														+"</ul>"
													+"</td>"
												+"</tr> ");
												}
												}
												 %>
											</tbody>
											 
										</table>
									</div>

								</div>
							</div>
						</div>
						<div class='col-md-4'>
							<div class='panel' >
								<div class='panel-heading' style='background-color:#458CD5;color:white'>
									<h4 class='heading' style='font-weight:600'>Entitlements</h4>
									<p style='font-weight:100'>My Entitlements within the company</p>
								</div>
								<div class='panel-body'>
								<div class='caption card-body'>
								<% 
									//Map<Long,EntitlementObj> entProviosnedList = oimutils.getUserEntitlementInstances(UserSessaion.getOimUser().getUserKey().toString(),UserSessaion.getOimClient()) ;
													 
									 for(Map.Entry<Long, EntitlementObj> entsMap: entProviosnedList.entrySet() ) {
								if(entProviosnedList.get(entsMap.getKey()).getStatus().equals("In Progress"))
								{continue;}
								 	%>
									
								<div class='col-md-6'>
									<h5 class='group card-title inner list-group-item-heading' style='font-weight:600;font-size:14px;'>
										<% out.write(entProviosnedList.get(entsMap.getKey()).getEntitlementName()); %>
									</h5>
									<p class='group inner list-group-item-text' style='font-weight:100;font-size:13px;'>
										<% out.write(entProviosnedList.get(entsMap.getKey()).getAccountName()); %>
									</p>
								</div>
								<div class='col-md-6' style='text-align:right;padding-right:0px;'>
										<a href='#'><span class='label label-success' style='border-radius:3.25em;'>Provisioned</span></a>
									</div>
								
								
									 <div class='clearfix'></div>
									 <hr />
									 
									<%}
									
								%>
								</div>
								</div>
							</div>
						</div>
						<div class='col-md-4'>
							<div class='panel'>
								<div class='panel-heading' style='background-color:#259774;color:white'>
									<h4 class='heading' style='font-weight:600'>Roles</h4>
									<p style='font-weight:100'>These are my roles within the organziation</p>
								</div>
								<div class='panel-body'>
								<%
													
										for(int i=0 ; i < rolesList.size();i++) {
        								out.write("<div class='caption card-body'>"
										+"<h5 class='group card-title inner list-group-item-heading' style='font-weight:600;font-size:14px;'>"
											+rolesList.get(i).getRoleDisplayName()
										+"</h5>"
										+"<p class='group inner list-group-item-text' style='font-weight:600;font-size:13px;'>Description"
											
										+"</p>"
										+"<p class='group inner list-group-item-text' style='font-weight:100;font-size:13px;'>"
											+rolesList.get(i).getRoleDisplayName() +" for OIM. "
										+"</p>"

									+"</div>"
									+"<hr />" );
									}
								%>

								</div>
							</div>
						</div>
					</div>
					
					<!--  New Access dashboard End  -->
				</div>
			<!-- END MAIN CONTENT -->
			</div>
		</div>
		<!-- END MAIN -->
	
		<!-- Reset Password Popup -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">Reset Password</h4>
            </div>
            <div class="modal-body">
                        <div class="form-row">
            <div class="col-md-4"><label for="ApplicationName">Application Name: </label></div>
                        <div class="col-md-4"> <span class ="AppHead" id = "AppHead" >appName</span>  <span id = "appKey" style="display:none">appKey</span></div>
                        </div>
                        <div class="clearfix"></div>
                 <div class="form-row">
            <div class="col-md-4"><label for="newPassword">New Password</label></div>
                        <div class="col-md-4"><input type="password" class="form-control" id="newRPass" placeholder="Enter New Password" required></div>
                        </div>
                        <div class="clearfix"></div>
                           <div class="form-row">
            <div class="col-md-4"><label for="confirm Password">Confirm Password</label></div>
                        <div class="col-md-4">  <input type="password" class="form-control" id="confRPass" placeholder="Enter Confirm Password" required></div>
                        </div>
                        <div class="clearfix"></div>
                            
            </div>
            
             
 
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="restAppPassword" class="btn btn-primary" style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div>
		<!--  -->
		
		<!-- OTP Application Password Popup -->
		<div class="modal fade" id="myModalotp" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">Please Enter OTP</h4>
            </div>
            <div class="modal-body">
                        <div class="form-row">
            <div class="col-md-4"><label for="ApplicationName">Application Name: </label></div>
                        <div class="col-md-4"> <span class ="AppHead" id ="AppHead1" >appName</span>  <span id = "appKey" style="display:none">appKey</span></div>
                        </div>
                        <div class="clearfix"></div>
                 <div class="form-row">
            <div class="col-md-4"><label for="otp">OTP</label></div>
                        <div class="col-md-4"><input type="text" class="form-control" id="otp" placeholder="Enter OTP" required></div>
                        </div>      
                        <div class="clearfix"></div>       
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="btnotp" class="btn btn-primary" style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div>
		<!--  -->
		
		
		
	<!-- END WRAPPER -->
	<!-- Javascript -->
	<script src="assets/vendor/jquery/jquery.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script src="assets/scripts/klorofil-common.js"></script>
</body>

</html>
