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
	<style>
.hblbtn {
			background-color: #009591;
			color: white;
			
			border:none;
			
			transition-duration: 0.3s;
		}

			.hblbtn:hover {
				background-color: white;
				color: #009591;
			}
			
			.hblred {
			background-color: #a02030;
			color: white;
			
			border:none;
			
			transition-duration: 0.3s;
		}

			.hblred:hover {
				background-color: #F9354C;
				color: white;
			}
	</style>
	<script type="text/javascript" src="js/jquery-1.12.4.js" ></script>
	<script type="text/javascript">
	 $(document).ready(function(){
			$('#btnTrackRequest').click(function(){
			var trackID = $('#trackID').val();
			 console.log("jquery called"+trackID);
				$.ajax({
					type: 'POST',
					data:{trackID:trackID,action:"trackRequest"},
					url: 'TrackRequestServlet',
					success: function(result){
					 console.log("success" + result);
					
					$('#tbltrackRequest').html(result);
					$('#requestResultID').val(trackID);
					//$('#tbldetailRequest').html(result);
					
					 
					}
				});
				 
			});
			
			$('#btnWithdraw').click(function(){
			var trackID = $('#requestResultID').val();
			 console.log("jquery called"+trackID);
				$.ajax({
					type: 'POST',
					data:{trackID:trackID,action:"withdrawRequest"},
					url: 'TrackRequestServlet',
					success: function(result){
					 console.log("success" + result);
					$('#tbltrackRequest').html(result);
					
					 
					}
				});
				 
			});
			
			$('#btnDetailRequest').click(function(){
			var trackID = $('#trackID').val();
			 console.log("jquery called"+trackID);
				$.ajax({
					type: 'POST',
					data:{trackID:trackID,action:"getDetailRequest"},
					url: 'TrackRequestServlet',
					success: function(result){
					 console.log("success" + result);
					$('#tblDetailRequest').html(result);
					
					 
					}
				});
				 
			});
			 
		});

 </script>
</head>

<body>
<%
	String name =(String) session.getAttribute("username");
	
	if(name!=null && name.length() !=0)
	{
		session.setAttribute("username", name);
	}
	else
		response.sendRedirect("index.jsp");

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
						<li><a href="Compliance.jsp" class=""><i class="lnr lnr-chart-bars"></i> <span>Certifications</span></a></li>
						<li><a href="PendingApproval.jsp" class=""><i class="fa fa-check-circle"></i> <span>Pending Approvals</span></a></li>
						<li><a href="PendingRequest.jsp" class=""><i class="lnr lnr-dice"></i> <span>Pending Request</span></a></li>
						<li><a href="ManageUsers.jsp" class=""><i class="fa fa-user-o"></i> <span>Manage User</span></a></li>
						 <li><a href="TrackRequest.jsp" class=""><i class="fa fa-clock-o"></i> <span>Track Request</span></a></li>
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
						<div class="panel panel-headline">
						
							<!-- LEFT COLUMN -->
							<div class="panel-body" style="background-color:darkcyan;color:white;">
							
								<h3 style="font-weight:600;">Track Request</h3>
									<br />
									<form class="form-inline"  action="#">
										<div class="form-group">
											<label for="TI" style="padding-right:20px;">Tracking ID :</label>
											<input type="text" style="width:150px;margin-right:20px;color:black"  id="trackID" placeholder="Enter ID" name="trackID"></input>
										</div>
										
										
										<input type="button" class="btn hblbtn btn-sm" id="btnTrackRequest" style="width:86px;font-weight:bold;margin-right: 1%;" value="Submit"/>
<input type="button" class="btn hblred btn-sm" id="btnWithdraw" style="width:86px;font-weight:bold" value="Withdraw"/>
									</form>




						</div>


								<!-- END AWARDS -->
								<!-- TABBED CONTENT -->
								<div class="panel-body">
								
										<div class="table-responsive">
											<h3>Request Detail</h3>
											<table class="table project-table" > 
												<thead>
													<tr>
													<th>Request Creator</th>
														<th>Request Type</th>
														<th>Request Item</th>
														<th>Approver</th>
														<th>Assigned</th>
														<th>Status</th>
													</tr>
												</thead>
												  <tbody id="tbltrackRequest" name="tbltrackRequest">
												 

													
												</tbody>
											</table>
											</div>
											</div>
											</div>
											
											
											
											
											
											
											
											
											
						<div class="panel panel-headline">
						<div class="panel-body">
							<div class="form-row"><div class="col-md-4" style="padding-left:0px;"><h3>Request Catalog Detail</h3></div><div class="col-md-6"></div><div class="col-md-2"><button  class="btn hblbtn btn-sm" id="btnDetailRequest" style="margin-top:9px;">Get Catalog</button></div></div>
							<div class="clearfix"></div>
							<br />
							<div class="table-responsive">
								<table class="table project-table">
									<thead>
										<tr>
											<th>Entity Name</th>
											<th>Beneficiary</th>
											<th>Status</th>
											<th>Description</th>


										</tr>
									</thead>
									<tbody id="tblDetailRequest" name="tblDetailRequest">
												</tbody>
											</table>
											
										</div>
							</div>
							</div>
				<!-- END TABBED CONTENT -->
							</div>
				<!-- END RIGHT COLUMN -->
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
	<script>
		$(document).ready(function () {
			$('[data-toggle="tooltip"]').tooltip();
		});
	</script>
	<script src="assets/vendor/jquery/jquery.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script src="assets/scripts/klorofil-common.js"></script>
</body>

</html>
