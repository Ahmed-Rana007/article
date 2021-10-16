<!doctype html>
<html lang="en">

<head>
<title>Dashboard | HBL-SelfService</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<!-- VENDOR CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/vendor/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/vendor/linearicons/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/vendor/chartist/css/chartist-custom.css">
<!-- MAIN CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/main.css">
<!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
<link rel="stylesheet" href="assets/css/demo.css">
<!-- GOOGLE FONTS -->
<link rel="stylesheet" href="assets/css/gfonts.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/gfonts.css">
<!-- ICONS -->
<link rel="apple-touch-icon" sizes="76x76"
	href="${pageContext.request.contextPath}/assets/img/apple-icon.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="${pageContext.request.contextPath}/assets/img/favicon.png">
<style>
.rt {
	text-align: right !important;
}

.lf {
	text-align: left !important;
}
</style>


</head>

<body>


	<!-- MAIN -->
	<div class="main">
		<!-- MAIN CONTENT -->
		<div class="main-content">
			<div class="container-fluid">
				<h3>Request Details Equation</h3>
				<div class="panel panel-headline">
					<div class="panel-body">
						<!--  Accout form start -->
						<form id="adDetailsForm" style="display: none">

							<div class="form-group row">
								<h4>Details</h4>
								<br />
								<div class="form-group col-md-6 ">
									<div class="form-group col-md-3 rt">
										<label for="uniqueid">Unique ID</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="uniqueid"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt ">
										<label for="Mobile">Mobile</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="MobileAD"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="Passowrd">Passowrd</label>
									</div>
									<div class="form-group col-md-8">
										<input type="password" class="form-control" id="Passowrd"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="Pager">Pager</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="PagerAD"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="userid">User ID</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="userid"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="Fax">Fax</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="Fax"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="userprincipalname">User Principal Name</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="userprincipalname"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="IPPhone">IP Phone</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="IPPhone"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="FirstName">Firs Name</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="First_NameAD"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="Title">Title</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="TitleAD"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="MiddleName">Middle Name</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="Middle_NameAD"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="Department">Department</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="DepartmentAD"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="LastName">Last Name</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="Last NameAD"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="Company">Company</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="CompanyAD"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="FullName">Full Name</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="Full_NameAD"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="ManagerName">Manager Name</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="ManagerName"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="CommonName">Common Name</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="Common_NameAD"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="Office">Office</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="OfficeAD"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="OrganizationName">Organization Name</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="OrganizationName"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="Country">Country</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="Country"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="PasswordNeverExpries">Password Never
											Expires</label>
									</div>
									<div class="form-group col-md-8">

										<input type="checkbox" id="Password_Never_ExpiresAD">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="Street">Street</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="StreetAD"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="UserMustChangePasswordAtNextLogon">User
											Must Change Password At Next Logon</label>
									</div>
									<div class="form-group col-md-8">
										<input type="checkbox"
											id="User_Must_Change_Password_At_Next_Logon">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="HomeDirectory">Home Directory</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="HomeDirectory"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="PasswordNotRequired">Password Not Required</label>
									</div>
									<div class="form-group col-md-8">
										<input type="checkbox" id="Password_Not_RequiredAD">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="TerminalHomeDirectory">Terminal Home
											Directory</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control"
											id="Terminal_Home_DirectoryAD" placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="AccountIsLockedOut">Account Is Locked Out</label>
									</div>
									<div class="form-group col-md-8">
										<input type="checkbox" id="AccountIsLockedOut">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="TerminalAllowLogin">Terminal Allow Login</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control"
											id="Terminal_Allow_LoginAD" placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="TelephoneNumber">Telephone Number</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="TelephoneNumber"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="TerminalProfilePath">Terminal Profile Path</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control"
											id="Terminal_Profile_PathAD" placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="AccountExpirationDate">Account Expiration
											Date</label>
									</div>
									<div class="form-group col-md-8">
										<input type="date" class="form-control"
											id="Account_Expiration_DateAD">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="RedirectionMailID">Redirection Mail ID</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control"
											id="Redirection_Mail_IdAD" placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="EMail">E Mail</label>
									</div>
									<div class="form-group col-md-8">
										<input type="email" class="form-control" id="EMail"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="ExtensionNumber">Extension Number</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="ExtensionNumber"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="PostOfficeBox">Post Office Box</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="Post_OfficeAD"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="EmployeeNumber">Employee Number</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="EmployeeNumber"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="City">City</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="City"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="CountryCode">Country Code</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="CountryCode"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="State">State</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="StateAD"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="CountryName">Country Name</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="CountryName"
											placeholder="Enter Here">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="Zip">Zip</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="ZipAD"
											placeholder="Enter Here">
									</div>

								</div>

								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="ServiceAccount">Service Account</label>
									</div>
									<div class="form-group col-md-8">
										<input type="checkbox" id="ServiceAccount">
									</div>

								</div>
							</div>
							<div class="form-group row">
								<div class="form-group col-md-6">
									<div class="form-group col-md-3 rt">
										<label for="HomePhone">Home Phone</label>
									</div>
									<div class="form-group col-md-8">
										<input type="text" class="form-control" id="Home_PhoneAD"
											placeholder="Enter Here">
									</div>

								</div>

							</div>
							<div class="custom-tabs-line tabs-line-bottom left-aligned">
								<ul class="nav" role="tablist">

									<li><a href="#tab-bottom-left1" role="tab"
										data-toggle="tab">ADUserCountryAttrUD_ADUSRC</a></li>
									<li><a href="#tab-bottom-left2" role="tab"
										data-toggle="tab">TAB 2</a></li>
								</ul>
							</div>
							<div class="tab-content">

								<div class="tab-pane active" id="tab-bottom-left1">
									<div class="form-row">
										<div class="col-md-1 rt"
											style="border-left: 3px solid; width: 3%; color: #009591">
											<a href="#"><i class="glyphicon glyphicon-ok"></i></a>
										</div>
										<div class="col-md-1 lf"
											style="border-right: 3px solid; width: 4%; color: #009591">
											<a href="#"><i class="glyphicon glyphicon-remove"></i></a>
										</div>
										<div class="col-md-1" style="color: #009591">
											<a href="#"><i class="glyphicon glyphicon-filter"></i></a>
										</div>
									</div>
									<div class="clearfix"></div>
									<br />
									<div class="col-md-2 panel" style="border: 2px solid #009591;">
										<br /> <input type="text" class="form-control" /> <label
											for="City">Group Name</label>

									</div>

								</div>
								<div class="tab-pane fade in" id="tab-bottom-left2">
									<h5>This is tab 2</h5>
									<div class="form-row">
										<div class="col-md-1 rt"
											style="border-left: 3px solid; width: 3%; color: #009591">
											<a href="#"><i class="glyphicon glyphicon-ok"></i></a>
										</div>
										<div class="col-md-1 lf"
											style="border-right: 3px solid; width: 4%; color: #009591">
											<a href="#"><i class="glyphicon glyphicon-remove"></i></a>
										</div>
										<div class="col-md-1" style="color: #009591">
											<a href="#"><i class="glyphicon glyphicon-filter"></i></a>
										</div>
									</div>
									<div class="clearfix"></div>
									<br />
									<div class="col-md-2 panel" style="border: 2px solid #009591;">
										<br /> <input type="text" class="form-control" /> <label
											for="City">Group Name</label>

									</div>

								</div>

							</div>

						</form>

						<!--  Account form End -->
						<!-- Start Equation Form -->
						<form id="EquationDetailsForm">
							<h4>Details</h4>
							<br />
							<div class='form-group row'>
								<div class='form-group col-md-6 '>
									<div class='form-group col-md-3 rt'>
										<label for='UD_EQUATION_DATE'>Date</label>
									</div>
									<div class='form-group col-md-8'>
										<input type='text' class='form-control' id='UD_EQUATION_DATE'
											name='UD_EQUATION_DATE'>
									</div>
								</div>
								<div class='form-group col-md-6 '>
									<div class='form-group col-md-3 rt'>
										<label for='UD_EQUATION_BRANCH_NUMBER'>Branch Number</label>
									</div>
									<div class='form-group col-md-8'>
										<input type='text' class='form-control'
											id='UD_EQUATION_BRANCH_NUMBER'
											name='UD_EQUATION_BRANCH_NUMBER'>
									</div>
								</div>
							</div>
							<div class='form-group row'>
								<div class='form-group col-md-6 '>
									<div class='form-group col-md-3 rt'>
										<label for='UD_EQUATION_LANGUAGE'>Language</label>
									</div>
									<div class='form-group col-md-8'>
										<input type='text' class='form-control'
											id='UD_EQUATION_LANGUAGE' name='UD_EQUATION_LANGUAGE'>
									</div>
								</div>
								<div class='form-group col-md-6 '>
									<div class='form-group col-md-3 rt'>
										<label for='UD_EQUATION_SHOW_LCL_TIME_ON_E'>Show Lcl
											Time On Enquiry</label>
									</div>
									<div class='form-group col-md-8'>
										<input type='text' class='form-control'
											id='UD_EQUATION_SHOW_LCL_TIME_ON_E'
											name='UD_EQUATION_SHOW_LCL_TIME_ON_E'>
									</div>
								</div>
							</div>
							<div class='form-group row'>
								<div class='form-group col-md-6 '>
									<div class='form-group col-md-3 rt'>
										<label for='UD_EQUATION_SYSTEM_OPTION_AUTH'>System
											Option Auth Level</label>
									</div>
									<div class='form-group col-md-8'>
										<input type='text' class='form-control'
											id='UD_EQUATION_SYSTEM_OPTION_AUTH'
											name='UD_EQUATION_SYSTEM_OPTION_AUTH'>
									</div>
								</div>
								<div class='form-group col-md-6 '>
									<div class='form-group col-md-3 rt'>
										<label for='UD_EQUATION_USER_ID'>User ID</label>
									</div>
									<div class='form-group col-md-8'>
										<input type='text' class='form-control'
											id='UD_EQUATION_USER_ID' name='UD_EQUATION_USER_ID'>
									</div>
								</div>
							</div>
							<div class='form-group row'>
								<div class='form-group col-md-6 '>
									<div class='form-group col-md-3 rt'>
										<label for='UD_EQUATION_LIMIT_VIOLATION_AU'>Limit
											Violation Authority</label>
									</div>
									<div class='form-group col-md-8'>
										<input list='UD_EQUATION_LIMIT_VIOLATION_AU1'
											class='form-control' id='UD_EQUATION_LIMIT_VIOLATION_AU'
											name='UD_EQUATION_LIMIT_VIOLATION_AU' />
										<datalist id='UD_EQUATION_LIMIT_VIOLATION_AU1'>
											<option value='S'>S</option>
											<option value='E'>E</option>
											<option value='Y'>Y</option>
											<option value='N'>N</option>
										</datalist>
									</div>
								</div>
								<div class='form-group col-md-6 '>
									<div class='form-group col-md-3 rt'>
										<label for='UD_EQUATION_EXTENSION'>Extension</label>
									</div>
									<div class='form-group col-md-8'>
										<input type='text' class='form-control'
											id='UD_EQUATION_EXTENSION' name='UD_EQUATION_EXTENSION'>
									</div>
								</div>
							</div>
							<div class='form-group row'>
								<div class='form-group col-md-6 '>
									<div class='form-group col-md-3 rt'>
										<label for='UD_EQUATION_AVAILABILITY_CODE'>Availability
											Code</label>
									</div>
									<div class='form-group col-md-8'>
										<input type='text' class='form-control'
											id='UD_EQUATION_AVAILABILITY_CODE'
											name='UD_EQUATION_AVAILABILITY_CODE'>
									</div>
								</div>
								<div class='form-group col-md-6 '>
									<div class='form-group col-md-3 rt'>
										<label for='UD_EQUATION_CAS_USER_ID'>CAS User ID</label>
									</div>
									<div class='form-group col-md-8'>
										<input type='text' class='form-control'
											id='UD_EQUATION_CAS_USER_ID' name='UD_EQUATION_CAS_USER_ID'>
									</div>
								</div>
							</div>
							<div class='form-group row'>
								<div class='form-group col-md-6 '>
									<div class='form-group col-md-3 rt'>
										<label for='UD_EQUATION_PRIME_MENU_ID'>Prime Menu Id</label>
									</div>
									<div class='form-group col-md-8'>
										<input type='text' class='form-control'
											id='UD_EQUATION_PRIME_MENU_ID'
											name='UD_EQUATION_PRIME_MENU_ID'>
									</div>
								</div>
								<div class='form-group col-md-6 '>
									<div class='form-group col-md-3 rt'>
										<label for='UD_EQUATION_CHARACTER_GENERAL_'>Character
											General Text Display</label>
									</div>
									<div class='form-group col-md-8'>
										<input type='text' class='form-control'
											id='UD_EQUATION_CHARACTER_GENERAL_'
											name='UD_EQUATION_CHARACTER_GENERAL_'>
									</div>
								</div>
							</div>
							<div class='form-group row'>
								<div class='form-group col-md-6 '>
									<div class='form-group col-md-3 rt'>
										<label for='UD_EQUATION_SYSTEM_OPTION_AU44'>System
											Option Auth Class</label>
									</div>
									<div class='form-group col-md-8'>
										<input type='text' class='form-control'
											id='UD_EQUATION_SYSTEM_OPTION_AU44'
											name='UD_EQUATION_SYSTEM_OPTION_AU44'>
									</div>
								</div>
								<div class='form-group col-md-6 '>
									<div class='form-group col-md-3 rt'>
										<label for='UD_EQUATION_AUTH_ANY_BRANCH'>Auth Any
											Branch</label>
									</div>
									<div class='form-group col-md-8'>
										<input list='UD_EQUATION_AUTH_ANY_BRANCH1'
											class='form-control' id='UD_EQUATION_AUTH_ANY_BRANCH'
											name='UD_EQUATION_AUTH_ANY_BRANCH' />
										<datalist id='UD_EQUATION_AUTH_ANY_BRANCH1'>
											<option value='Y'>Y</option>
											<option value='N'>N</option>
										</datalist>
									</div>
								</div>
							</div>
						</form>


						<!-- END Equation Form -->

					</div>
				</div>
			</div>
		</div>
		<!-- END MAIN CONTENT -->
	</div>


	<script src="assets/vendor/jquery/jquery.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script src="assets/scripts/klorofil-common.js"></script>
	<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="js/ajaxcode.js"></script>
</body>

</html>