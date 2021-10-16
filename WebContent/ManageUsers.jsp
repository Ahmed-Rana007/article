<%@page import="java.util.Map"%>
<%@page import="com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.Hashtable"%>
<%@page import="com.hbl.selfservice.OIMUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="antlr.collections.List"%>
<%@page import="com.hbl.selfservice.UsersList"%>
<%@page import="com.hbl.selfservice.OIMUser"%>
<%@page import="com.hbl.selfservice.UserSessaion"%>
<%@page import="oracle.iam.platform.OIMClient"%>
<%@page import="com.hbl.selfservice.OIMUtils"%>
<%@page import="com.hbl.selfservice.OIMUser"%>
<%@page import="com.hbl.selfservice.UserSessaion"%>
<%@page import="com.hbl.provisioning.UserOperationModifyAcc"%>
<%@page import="com.hbl.provisioning.ProvisioningAccounts"%>

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
	<!--<link rel="apple-touch-icon" sizes="76x76" href="js/https_maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">-->
	<!--<link rel="icon" type="image/png" sizes="96x96" href="js/https_cdn.datatables.net/1.10.23/css/dataTables.bootstrap.min.css">-->
	
	<link rel="apple-touch-icon" sizes="76x76" href="js/https _maxcdn.bootstrapcdn.com_bootstrap_3.3.7_css_bootstrap.min.css">
	<link rel="icon" type="image/png" sizes="96x96" href="js/https _cdn.datatables.net_1.10.23_css_dataTables.bootstrap.min.css">
	
	 <%
	 					
	 	 
						OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
							 	
							 	
	 %>
		<style>
#myInput {
  background-image: url('/css/searchicon.png'); /* Add a search icon to input */
  background-position: 10px 12px; /* Position the search icon */
  background-repeat: no-repeat; /* Do not repeat the icon image */
  width: 100%; /* Full-width */
  font-size: 16px; /* Increase font-size */
  padding: 12px 20px 12px 40px; /* Add some padding */
  border: 1px solid #ddd; /* Add a grey border */
  margin-bottom: 12px; /* Add some space below the input */
}

#myTable {
  border-collapse: collapse; /* Collapse borders */
  width: 100%; /* Full-width */
  border: 1px solid #ddd; /* Add a grey border */
  font-size: 18px; /* Increase font-size */
}

#myTable th, #myTable td {
  text-align: left; /* Left-align text */
  padding: 12px; /* Add padding */
}

#myTable tr {
  /* Add a bottom border to all table rows */
  border-bottom: 1px solid #ddd;
}

#myTable tr.header, #myTable tr:hover {
  /* Add a grey background color to the table header and on hover */
  background-color: #f1f1f1;
}
</style>
		
		<style>
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
	
	
	<script type="text/javascript" src="js/jquery-1.12.4.js" ></script>
	<script>

		$(function() { 
		  $(document ).on("click","#example button.action",function() {
		    let tr = $(this).closest('tr');
		    let userkeytodisbale = tr.find('td:eq(0) a').text();
		    let b = tr.find('td:eq(6) button').text(); 
		    console.log(userkeytodisbale +" " + b);
		    var action =null;
		    if(b=="Disable")
		    	action = "disable"
		    else
		    	action = "enable"
		    $.ajax({
					type: 'POST',
					data:{userkeytodisbale:userkeytodisbale,action:action},
					url: 'UserOperationsController',
					success: function(result){
					 
					 
					 console.log("User "+action+"d Successfully");
					}
				});    
		  });
		});
</script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#btnDisable').click(function(){
			console.log("Cliked Disbale");
				var userkeytodisbale = $('#userLoginToAction').val();
				$.ajax({
					type: 'POST',
					data:{userkeytodisbale:userkeytodisbale,action:"disable"},
					url: 'UserOperationsController',
					success: function(result){
					 
					 
					alert("User Disabled Successfully");
					}
				});
				 
			});
			//enable ajax
			$('#btnEnable').click(function(){
				console.log("Cliked Enable");
				var userkeytodisbale = $('#userLoginToAction').val();
				$.ajax({
					type: 'POST',
					data:{userkeytodisbale:userkeytodisbale,action:"enable"},
					url: 'UserOperationsController',
					success: function(result){
					 
					alert("User Enabled Successfully");
					}
				});
				 
			});
			/// end enable ajax
			/// start lock ajax
			 
			/// end lock ajax
			/// start unlock ajax id="btnDisable"
			 
		});
			
			$(document).ready(function(){
			$('#createUser').click(function(){
			console.log("Cliked Disbale");
				
				window.location = 'CreateUser.jsp'
				 
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
						<li><a href="PendingRequest.jsp" class=""><i class="lnr lnr-dice"></i> <span>Pending Request</span></a></li>
						<li><a href="ManageUsers.jsp" class="active"><i class="fa fa-user-o"></i> <span>Manage User</span></a></li>
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
							
								<h3 style="font-weight:600;">Manage User</h3>
									<br />
						<!-- 			<form class="form-inline"  action="#">
										<div class="form-group">
											<label for="TI" style="padding-right:20px;">Search :</label>
											<input type="text" style="width:150px;margin-right:20px;color:black;font-size:14px;"  onkeyup="myFunction()" id="searchid" placeholder="Enter User ID" name="searchid"></input>
										</div>
										
										
										<input type="button" class="btn hblbtn btn-sm" id="btnTrackRequest" style="width:86px;font-weight:bold;margin-right: 1%;" value="Submit"/>
<%

String userLogin  = (String)session.getAttribute("username");
ProvisioningAccounts pa = new ProvisioningAccounts();
String userkey1 = pa.getUserKey(userLogin, oimClient);
System.out.print(userkey1+"->>>>>>>>>>>>>>>>>>>>>>");
UserOperationModifyAcc uomc = new UserOperationModifyAcc();
if(uomc.getRole(userkey1).equals("Y")){
out.write("<input type='button' class='btn hblbtn btn-sm' id='createUser' style='width:86px;font-weight:bold' value='Create'/>");

}else{
out.write("<input type='button' class='btn hblbtn btn-sm' id='createUser' style='width:86px;font-weight:bold' value='Create' disabled/>");

}
%>

									</form>    -->
  



						</div>

								</div>
								
								
								<div class="panel-body" style="padding: 0px;">
								<div class="table-responsive">
											<table id="example" class="table table-striped table-bordered" style="width:100%" >
        <thead style="font-size:14px">
            <tr>
                <th>User Login</th>
                <th>Display Name</th>
                <th>Email</th>
                <th>Mobile</th>
                <th>Status</th>
              <!--   <th>Manager</th> -->
                <th>Personal Number</th>
            </tr>
        </thead>
        <tbody style="font-size:12px">
        <% 
        	OIMUtils oimUtils = new OIMUtils();
        	String actionBtn =null;
        	String btnAjax = null;
        	String btnClass = null;
        	String btnColor = null;
        	String btnLabel =null;
        	Map<Long,UsersList> userList =oimUtils.getAllUsers(oimClient);
        for(Map.Entry<Long, UsersList> userMap: userList.entrySet() ) {
        	if(userList.get(userMap.getKey()).getuserStatus().equalsIgnoreCase("Active"))
        	{
        		actionBtn = "Disable";
        		btnAjax = "btnDisable";	
        		btnClass = "label label-success";
        		btnColor = "c9302c";
        		btnLabel ="Disable";
        	}
        	else 
        	{
        		actionBtn = "Active";
        		btnAjax = "btnEnable";	
        		btnClass = "label label-remove";
        		btnColor = "009591";
        		btnLabel ="Enable";
        	}
        	out.write("<tr>"
               +"<td><a href='UserDetailsServlet?userKey="+userList.get(userMap.getKey()).getUserKey()+"'>" + userList.get(userMap.getKey()).getUserLogin()+"</a></td>"
               +"<td>" + userList.get(userMap.getKey()).getUserFirstName() +" "+ userList.get(userMap.getKey()).getUserLastName() +"</td>" 
               +"<td>" +userList.get(userMap.getKey()).getUserEmail()+"</td>" 
               +"<td>" + userList.get(userMap.getKey()).getUserMobileNumber()+"</td>" 
               +"<td>" + userList.get(userMap.getKey()).getuserStatus()+"</td>"
               +"<td>" + userList.get(userMap.getKey()).getPersonalNumber()+"</td></tr>" );}
              //   +"<td>" +oimUtils.getUserLoginName( userList.get(userMap.getKey()).getUserManager(), oimClient)+"</td>" 
           // +"<td><button type='button' id='action' class='"+btnClass+" action' style=background-color:#"+btnColor+";>"+btnLabel+"</button></td></tr>");}
            
        %>
            
        </tbody>
        
    </table>
    
										</div>
								
								</div>
								 
								
							
							
						
					
				</div>
			</div>
			<!-- END MAIN CONTENT -->
		</div>
		<!-- END MAIN -->
		</div>
		<!-- END Wrapper -->
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
	<script src="js/jquery-3.5.1.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/dataTables.bootstrap.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
    $('#example').DataTable();
} );
	</script>


</body>

</html>
