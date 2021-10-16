<%@page import="com.hbl.certifications.CertUserListObj"%>
<%@page import="com.hbl.certifications.CertificationsImpl"%>
<%@page import="com.hbl.certifications.CertifDetailsObj"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
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
	$(document).on("click","a[name='certDetailsBtn']", function (e) {
    		console.log($(this).attr('href'));
    	 
    		 var certifId = $(this).attr('href');
    		 
    		 var arr = certifId.split('%');
    		 console.log(arr[0] +" -  "+ arr[1]);
    		//$('#reuqetKey1').html($(this).attr('href'));
    		
    		//var successUrl = "Home.jsp"; // might be a good idea to return this URL in the successful AJAX call
    //window.location.href = successUrl;
    		
    		var successUrl = "CertUsersList.jsp?certifId="+arr[0]+"&certTaskID="+arr[1]+"";
				 		 window.location.href = successUrl; 
		});
	 $(document).on("click","a[name='certifComplete']", function (e) {
    		console.log($(this).attr('href'));
    	 
    		 var certifId = $(this).attr('href');
    		 var arr = certifId.split('%');
    		 console.log(arr[0] +" -  "+ arr[1]);
    		  $.ajax({
					type: 'POST',
					data:{certId:arr[0],taskId:arr[1] ,action:"completeCertification"},
					url: 'CertificationsServlet',
					success: function(result){
					alert(result);
					 
					}
				});

		});
	 $(window).ready(function () {
      $.ajax({
					type: 'POST',
					data:{userLogin:"kashif.arif",action:"certifications"},
					url: 'CertificationsServlet',
					success: function(result){
					 
					$('#tblCertifications').html(result);
					console.log("success" + result);
					 
					}
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
						<li><a href="HomePage.jsp" class="active"><i class="fa fa-home"></i> <span>Dashboard</span></a></li>
						<li><a href="myaccess.jsp" class=""><i class="fa fa-info-circle"></i> <span>My Access</span></a></li>
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
		<!-- END LEFT SIDEBAR -->
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content">
				 	<div class="container-fluid">

					<div class="panel panel-headline">
						<div class="panel-body" style="background-color:darkcyan;color:white;">
							<h3 style="font-weight:600;">Certification Dashboard</h3>
							<br>
							



						</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table class=" table-borderless" style="width:100%;">
									<thead>
										<tr>
										</tr>
									</thead>
									<tbody id="tblCertifications">
								 
									</tbody>
									<tfoot>
										<tr>
										</tr>
									</tfoot>
								</table>
							</div>




						</div>
					</div>
				
					<!-- END MAIN CONTENT -->
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
	<script type="text/javascript">
		//change Password
	     $(document).ready(function(){
			$('#btnChangePassword').click(function(){
			console.log("btn clicked");
				var curPassword = $('#curpass').val();
				var newPassword = $('#newpass').val();
				var confirmPassword = $('#confpass').val();
				console.log(curPassword+" "+confirmPassword);
				if (newPassword !='' && confirmPassword !='' && curPassword!=''){
					
					console.log(newPassword);
				$.ajax({
					type: 'POST',
					data:{newPassword:newPassword,confirmPassword:confirmPassword},
					url: 'ChangeUserPassword',
					success: function(result){
					console.log(result);
					
					if (result.STATUS=="changed")
					alert("Password has been Changed!");
					else if (result.notMatched=="Y")
					alert("New/Confirm Password not Matched!");
					else
					alert("Internal Error!");
					}
				}); 
				
				}else{
				
				alert("Please Enter Valid New/Confirm Passwords!");
				}
				 
			}); 
			}); 
	//change Pasword
	
	</script>
</body>

</html>
