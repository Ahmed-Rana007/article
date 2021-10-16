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
	<style type="text/css">
        body
        {
            padding: 10pt !important;
        }
    </style>
	<style type="text/css">
		h4 {
		  margin: 2rem 0rem 1rem;
		}
		
		.table-image {
		  td, th {
		    vertical-align: middle;
		  }
		}
	</style>
	<script type="text/javascript" src="js/jquery-1.12.4.js" ></script>
	<script type="text/javascript">
	 $(document).ready(function(){
			$('#btnPendingApprovals').click(function(){
			 console.log("jquery called");
				$.ajax({
					type: 'POST',
					data:{userLogin:"kashif.arif",action:"approvals"},
					url: 'PendingRequestServlet',
					success: function(result){
					 
					$('#tblPendingApprovals').html(result);
					console.log("success" + result);
					 
					}
				});
				 
			});
			 
		});
		$('#tblPendingApprovals tr').click(function(){
       $(this).html();
       console.log("Row Clicked");
 });
 	 
   $(window).ready(function () {
      $.ajax({
					type: 'POST',
					data:{userLogin:"kashif.arif",action:"approvals"},
					url: 'PendingRequestServlet',
					success: function(result){
					 
					$('#tblPendingApprovals').html(result);
					console.log("success" + result);
					 
					}
				});
   });
   
   
   //withdraw start
   
   	$(document).ready(function(){
			$('#btnWithdraw').click(function(){
			var trackID = $('#searchid').val();
			if(trackID==null || trackID =='')
			{ alert("Please Enter Request ID");
			return;}
			 console.log("jquery called"+trackID);
				$.ajax({
					type: 'POST',
					data:{trackID:trackID,action:"withdrawRequest"},
					url: 'TrackRequestServlet',
					success: function(result){
					 console.log(result.STATUS);
	
					    alert(result.STATUS);
					    location.reload();
				 
					},
					  error: function (error) {
                      alert("Internal Error Occured!");
                      location.reload();
                   }
					
					
				}); 
				 
			});
			});
   //withdraw end
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
						<li><a href="HomePage.jsp" class=""><i class="fa fa-home"></i> <span>Dashboard</span></a></li>
						<li><a href="myaccess.jsp" class=""><i class="fa fa-info-circle"></i> <span>My Access</span></a></li>
						<li><a href="certDashboard.jsp" class=""><i class="lnr lnr-chart-bars"></i> <span>Certifications</span></a></li>
						<li><a href="PendingApproval.jsp" class=""><i class="fa fa-check-circle"></i> <span>Pending Approvals</span></a></li>
						<li><a href="PendingRequest.jsp" class="active"><i class="lnr lnr-dice"></i> <span>Pending Request</span></a></li>
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
							<div class="form-row"><div class="col-md-4"><h4 style="font-weight:600;">Pending Requests</h4></div><div class="col-md-2"></div><div class="col-md-6" style="text-align:right;font-size: 12px;padding-top: 14px;">
	
</div></div>

							<div class="clearfix"></div>
							
							<br />
							<form class="form-inline" action="#">
							<div class="form-group">
											<label for="TI" style="padding-right:20px;">Search :</label>
											<input type="text" style="width:150px;margin-right:20px;color:black;font-size:14px;"  onkeyup="myFunction()" id="searchid" placeholder="Enter Request ID" name="searchid"></input>
											<input type="button" class="btn hblred btn-sm" id="btnWithdraw" style="width:86px;font-weight:bold" value="Withdraw"/>
										</div>
									
					<button type="button" class="btn btn-primary btn-sm" onClick="refreshPage()" style="background-color:#009591;float: right;">Refresh</button>
					</form>
						</div>
		<div class="panel-body">
		
			<div class="table-responsive">
		<br />
<table id="example" class="table table-striped table-bordered" style="width:100%">
       
      	<thead>
		<tr>
		<th>Request ID</th>
	 	<th>Created Date</th>
		<th>Request Type</th>
		<th>Request For</th>
		<th>Assign</th>
		<th>Status</th>
	
		</tr>
		</thead>
        <tbody id="tblPendingApprovals" name="tblPendingApprovals" style="font-size: 12px;">
       	
        </tbody>
        
    </table>
    </div>
    </div>
				</div>
			</div>					 
				</div>
	</div>
    
    
	
	
	
	
		<!-- END MAIN -->
		<div class="clearfix"></div>
		<footer>
			<div class="container-fluid">
				<p class="copyright">&copy; 2021 <a href="#" target="_blank">HBL Pvt Ltd</a>. All Rights Reserved.</p>
			</div>
		</footer>
	</div>
	
	<!-- Bootstrap -->
    
    <!-- Modal Popup -->
    <div id="MyPopup" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        &times;</button>
                    <h4 class="modal-title">REQUEST DETAIL
                        <span id="reuqetKey"></span>
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="card-body">
                    
                    <div class="table-responsive">
											<table class="table project-table" id="acctTable">
												<thead>
													<tr>
														<th>Title </th>
														<th>Request Type </th>
														<th>Approver </th>
														<th>Assignee </th>
														<th>Status </th>
													</tr>
												</thead>
												
											  <tbody id="reuqetKey1">	
													
													
												</tbody>
											</table>
										</div>
                    
           <!-- <h6><span id="reuqetKey1"></span></h6>-->
         <!--    <article class="card">
                <div class="card-body row">
                    <div class="col"> <strong>Estimated Delivery time:</strong> <br>29 nov 2019 </div>
                    <div class="col"> <strong>Shipping BY:</strong> <br> BLUEDART, | <i class="fa fa-phone"></i> +1598675986 </div>
                    <div class="col"> <strong>Status:</strong> <br> Picked by the courier </div>
                    <div class="col"> <strong>Tracking #:</strong> <br> BD045903594059 </div>
                </div>
            </article> -->
                </div>
                <div class="modal-footer">
                    <input type="button" id="btnClosePopup" data-dismiss="modal" value="Close" class="btn btn-danger" />
                </div>
            </div>
        </div>
    </div>
    //
    
    //
    <!-- Modal Popup -->
	<!-- END WRAPPER -->
	<!-- Javascript -->
	<script src="assets/vendor/jquery/jquery.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script src="assets/scripts/klorofil-common.js"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
	    <script type="text/javascript">
        $(function () {
            $("#btnClosePopup").click(function () {
                $("#MyPopup").model("hide");
            });
        });
	
	$(document).ready(function () {
	$('[data-toggle="tooltip"]').tooltip();
	});
        
        $(document).on("click","a[name='lnkViews']", function (e) {
    		console.log($(this).attr('href'));
    		$('#reuqetKey').html($(this).attr('href'));
    		var requestId = $(this).attr('href');
    		//$('#reuqetKey1').html($(this).attr('href'));
    		$.ajax({
					type: 'POST',
					data:{requestId:requestId,action:"approvalsDetails"},
					url: 'PendingRequestServlet',
					success: function(result){
					 
					$('#reuqetKey1').html(result);
					console.log("success" + result);
					 
					}
				});
		});
		$(document).on("click","a[name='aprvbtn']", function (e) {
    		console.log($(this).attr('href'));
    		$('#reuqetKey').html($(this).attr('href'));
    		var requestId = $(this).attr('href');
    					console.log("success CLICKED");
    		//$('#reuqetKey1').html($(this).attr('href'));
    		$.ajax({
					type: 'POST',
					data:{requestId:requestId,action:"approve"},
					url: 'PendingRequestServlet',
					success: function(result){
					 
					$('#reuqetKey1').html(result);
					console.log("success" + result);
					alert(result);
					 
					}
				});
		});
		$(document).on("click","a[name='rjctBtn']", function (e) {
    		console.log($(this).attr('href'));
    		$('#reuqetKey').html($(this).attr('href'));
    		var requestId = $(this).attr('href');
    		//$('#reuqetKey1').html($(this).attr('href'));
    		$.ajax({
					type: 'POST',
					data:{requestId:requestId,action:"reject"},
					url: 'PendingRequestServlet',
					success: function(result){
					 
					$('#reuqetKey1').html(result);
					console.log("success" + result);
					 alert(result);
					}
				});
		});
		$(document).on("click","a[name='claimBtn']", function (e) {
    		console.log($(this).attr('href'));
    		$('#reuqetKey').html($(this).attr('href'));
    		var requestId = $(this).attr('href');
    		//$('#reuqetKey1').html($(this).attr('href'));
    		$.ajax({
					type: 'POST',
					data:{requestId:requestId,action:"claimReq"},
					url: 'PendingRequestServlet',
					success: function(result){
					 
					$('#reuqetKey1').html(result);
					console.log("success" + result);
					 alert(result);
					}
				});
		});
    </script>
	
	<script>
function myFunction() {
  // Declare variables
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById("searchid");
  filter = input.value.toUpperCase();
  table = document.getElementById("example");
  tr = table.getElementsByTagName("tr");

  // Loop through all table rows, and hide those who don't match the search query
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[0];
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }
  }
}
</script>
<script>
function refreshPage(){
    window.location.reload();
} 
</script>
</body>

</html>
