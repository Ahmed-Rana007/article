<%@page import="com.hbl.provisioning.ProvisioningAccounts"%>
<%@page import="oracle.iam.platform.OIMClient"%>
<%@page import="com.hbl.selfservice.formfields.AcctFormFields"%>
<%@page import="java.util.List"%>
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
		$(document).ready(function(){
			$('#btnActiveDirectory').click(function(){
				 var appInstanceName = $('#btnActiveDirectory').val();
				$.ajax({
					type: 'POST',
					data:{appInstanceName:appInstanceName},
					url: 'AccountFormController',
					success: function(result){
					$('#actFromRes').html(result);
					console.log(result);
					}
				});
				 
			});
			$('#btnAS400').click(function(){
				 var appInstanceName = $('#btnAS400').val();
				$.ajax({
					type: 'POST',
					data:{appInstanceName:appInstanceName},
					url: 'AccountFormController',
					success: function(result){
					$('#actFromRes').html(result);
					console.log(result);
					}
				});
				 
			});
			$('#btnCashPortal').click(function(){
				 var appInstanceName = $('#btnCashPortal').val();
				$.ajax({
					type: 'POST',
					data:{appInstanceName:appInstanceName},
					url: 'AccountFormController',
					success: function(result){
					$('#actFromRes').html(result);
					console.log(result);
					}
				});
				 
			});
			$('#btnCP1').click(function(){
				 var appInstanceName = $('#btnCP1').val();
				$.ajax({
					type: 'POST',
					data:{appInstanceName:appInstanceName},
					url: 'AccountFormController',
					success: function(result){
					$('#actFromRes').html(result);
					console.log(result);
					}
				});
				 
			});
		});
		
	 
	</script>
	<style>
		.rt{
			text-align:right !important;
		}
        .lf {
            text-align: left !important;
        }
	</style>
</head>

<body>
<%
	/*String name =(String) session.getAttribute("username");
	
	if(name!=null && name.length() !=0)
	{
		session.setAttribute("username", name);
	}
	else
		response.sendRedirect("index.jsp");*/

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
								<li><a href="ChangePassword.jsp"><i class="lnr lnr-cog"></i> <span>Change Password</span></a></li>
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
						<li><a href="myaccess.jsp" class=""><i class="fa fa-info-circle"></i> <span>My Access</span></a></li>
						<li><a href="certDashboard.jsp" class=""><i class="lnr lnr-chart-bars"></i> <span>Certifications</span></a></li>
						<li><a href="PendingApproval.jsp" class=""><i class="fa fa-check-circle"></i> <span>Pending Approvals</span></a></li>
						<li><a href="ManageUsers.jsp" class=""><i class="fa fa-user-o"></i> <span>Manage User</span></a></li>
						 <li><a href="TrackRequest.jsp" class="active"><i class="fa fa-clock-o"></i> <span>Track Request</span></a></li>
						<li><a href="makerequest.jsp" class=""><i class="fa fa-plus-circle"></i> <span>Make a Request</span></a></li>
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
					<input type="submit" id="btnActiveDirectory" name="btnActiveDirectory" value="ActiveDirectory" class="btn btn-primary" style="Background-color:#009591"/>
					<input type="submit" id="btnAS400" name="btnAS400" value="AS400" class="btn btn-primary" style="Background-color:#009591"/>
					<input type="submit" id="btnCashPortal" name="btnCashPortal" value="CashPortal" class="btn btn-primary" style="Background-color:#009591"/>
					<input type="submit" id="btnCP1" name="btnCP1" value="CP1" class="btn btn-primary" style="Background-color:#009591"/>
					
					
					<div class="panel panel-headline">
					
						<div class="panel-body">
							<form id="actFromRes" name="actFromRes">
								<!--  Form Fields Start -->
								
								
					<!--  Form Fields End -->
								<div class="custom-tabs-line tabs-line-bottom left-aligned">
									<ul class="nav" role="tablist">

										<li><a href="#tab-bottom-left1" role="tab" data-toggle="tab">ADUserCountryAttrUD_ADUSRC</a></li>
										<li><a href="#tab-bottom-left2" role="tab" data-toggle="tab">TAB 2</a></li>
									</ul>
								</div>
								<div class="tab-content">

									<div class="tab-pane active" id="tab-bottom-left1">
										<div class="form-row">
											<div class="col-md-1 rt" style="border-left:3px solid;width:3%;color:#009591"><a href="#"><i class="glyphicon glyphicon-plus"></i></a></div>
											<div class="col-md-1 lf" style="border-right:3px solid;width:4%;color:#009591"><a href="#"><i class="glyphicon glyphicon-remove"></i></a></div>
											<div class="col-md-1" style="color:#009591"><a href="#"><i class="glyphicon glyphicon-filter"></i></a></div>
										</div>
										<div class="clearfix"></div><br />
										<div class="col-md-2 panel" style="border:2px solid #009591;">
											<br />
											<input type="text" class="form-control" />
											<label for="City">Group Name</label>

										</div>

									</div>
									<div class="tab-pane fade in" id="tab-bottom-left2">
										<h5>This is tab 2</h5>
										<div class="form-row">
											<div class="col-md-1 rt" style="border-left:3px solid;width:3%;color:#009591"><a href="#"><i class="glyphicon glyphicon-plus"></i></a></div>
											<div class="col-md-1 lf" style="border-right:3px solid;width:4%;color:#009591"><a href="#"><i class="glyphicon glyphicon-remove"></i></a></div>
											<div class="col-md-1" style="color:#009591"><a href="#"><i class="glyphicon glyphicon-filter"></i></a></div>
										</div>
										<div class="clearfix"></div><br />
										<div class="col-md-2 panel" style="border:2px solid #009591;">
											<br />
											<input type="text" class="form-control" />
											<label for="City">Group Name</label>

										</div>

									</div>

										</div>
									</div>
</form>
							
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
	<!-- END WRAPPER -->
	<!-- Javascript -->
	<script src="assets/vendor/jquery/jquery.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script src="assets/scripts/klorofil-common.js"></script>
	
</body>

</html>
