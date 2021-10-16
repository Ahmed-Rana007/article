<%@page import="com.hbl.selfservice.objects.EntitlementObj"%>
<%@page import="com.hbl.provisioning.ProvisioningAccounts"%>
<%@page import="com.hbl.selfservice.objects.AppInstanceObj"%>
<%@page import="com.hbl.selfservice.objects.RoleUser"%>
<%@page import="com.hbl.selfservice.UsersList"%>
<%@page import="java.util.List"%>
<%@page import="com.hbl.selfservice.OIMUtils"%>
<%@page import="oracle.iam.platform.OIMClient"%>
<%@page import="com.hbl.selfservice.UserOperations"%>
<%@page import="com.hbl.selfservice.UsersList"%>
<%@page import="com.hbl.selfservice.objects.OrganizationObj"%>
<%@page import="java.util.Map"%>
<%@page import="com.hbl.selfservice.OIMUtils"%>
<%@page import="com.hbl.selfservice.OIMUser"%>
<%@page import="com.hbl.selfservice.UserSessaion"%>
<%@page import="com.hbl.provisioning.UserOperationModifyAcc"%>
<%@page import="com.hbl.provisioning.ProvisioningEntitlements"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">

<head>
<title>Dashboard | HBL SelfService</title>
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
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<script type="text/javascript">
/*	$(document).ready(function() {
		$('#btnDisable').click(function() {
			console.log("disable");
			var userkeytodisbale = $('#userLogin').val();
			$.ajax({
				type : 'POST',
			
	data : {userkeytodisbale : userkeytodisbale,action : "disable"},
				url : 'UserOperationServices',
				success : function(result) {

					console.log(result);
					$('.disablePop').modal('hide');
					if (result.STATUS == true)
					
					alert("DISABLED user Successfully!");
					
					else
						alert("USER Already DISABLED!");
						
				}
			});

		});
		});
		//enable ajax
		$(document).ready(function() {
			$('#btnEnable').click(function() {
				var userkeytodisbale = $('#userLogin').val();
				$.ajax({
					type : 'POST',
					data : {
						userkeytodisbale : userkeytodisbale,
						action : "enable"
					},
					url : 'UserOperationServices',
					success : function(result) {

						console.log(result);
						$('.enablePop').modal('hide');
						if (result.STATUS == true)
							alert("ENABLED user Successfully!");
						else
							alert("USER Already ENABLED!");
					}
				});

			});
		}); */
		/// end enable ajax
		/// start lock ajax
		$('#btnLock').click(function() {
			var userkeytodisbale = $('#userLogin').val();
			$.ajax({
				type : 'POST',
				data : {
					userkeytodisbale : userkeytodisbale,
					action : "lockUser"
				},
				url : 'UserOperationsController',
				success : function(result) {
					$('#resultDisable').html("");
					$('#resultUnlock').html("");
					$('#resultEnable').html("");
					$('#resultLock').html(result);
					Showabel();
					HideLabel();
				}
			});

		});
		/// end lock ajax
		/// start unlock ajax id="btnDisable"
		$('#btnUnLock').click(function() {
			var userkeytodisbale = $('#userLogin').val();
			$.ajax({
				type : 'POST',
				data : {
					userkeytodisbale : userkeytodisbale,
					action : "unLockUser"
				},
				url : 'UserOperationsController',
				success : function(result) {
					$('#resultDisable').html("");
					$('#resultEnable').html("");
					$('#resultLock').html("");
					$('#resultUnlock').html(result);
					Showabel();
					HideLabel();
				}
			});

		});
		/// end unlock ajax


	//
/*	$(document).on("click", "#acctTable button.acctDisable", function() {

		let tr = $(this).closest('tr');
		let a = tr.find('td:eq(0)').text();
		let b = tr.find('td:eq(1)').text();
		console.log(a + " " + b +" "+a);
		//console.log("Hello");

		//var action =null;

		$.ajax({
			type : 'POST',
			data : {
				appName : b,
				appKey : a,
				action : "acctDisable"
			},
			url : 'AccountDisableEnable',
			success : function(result) {

				console.log(result.ID);
				alert(result.ID + " Request Initiated!");

			}


		});
	}); */

/*	$(document).on("click", "#acctTable button.acctEnable", function() {

		let tr = $(this).closest('tr');
		let a = tr.find('td:eq(0)').text();
		let b = tr.find('td:eq(1)').text();
		console.log(a + " " + b);
		//console.log("Hello");

		//var action =null;

		$.ajax({
			type : 'POST',
			data : {
				appName : b,
				appKey : a,
				action : "acctEnable"
			},
			url : 'AccountDisableEnable',
			success : function(result) {

				console.log(result.ID);
				alert(result.ID + " Request Initiated!");

			}

		});
	}); */
	//
	
	//AppEnableDisbale
	$(document).on("click","a[name='acctDisable']", function (e) {
		
	
		
		console.log($(this).attr('href'));
    		
    	    var tr = $(this).closest('tr'); 
		    var b = tr.find('td:eq(1)').text(); 
		    var a = tr.find('td:eq(0)').text();
		    var c= 	tr.find('td:eq(5)').text();
		    console.log(c);
		    $("#appId").html(a);
		    $("#AppHead").html(b);
		     $("#Appstatus").html(c);
		    $('#EDModal').modal('show');
		
		});
	//

	//Revoke Entitlement Start

	$(document)
			.on(
					"click",
					"#MUentRevoke button.acctREVO",
					function() {
						let tr = $(this).closest('tr');
						let a = tr.find('td:eq(0)').text();
						let b = tr.find('td:eq(1)', a).text();
						console.log(a + " " + b);

						$
								.ajax({
									type : 'POST',
									data : {
										entName : b,
										entKey : a,
										action: 'revokeEntitlement'
									},
									url : 'EntitlementRevoke',

									success : function(result) {

										console.log(result.ID);
										console.log(result.EntName);
										if (result.ID == "notProvisioned")
											alert("The "
													+ result.EntName
													+ " is already Not Provisioned!");
										else
											alert("Revoke Request is initiated againt Entitlement "
													+ result.EntName);

									}

								});
					});
	//Revoke Entitlement End

	//modify button

	$(document).ready(function() {
		$('#btnmodify').click(function() {
			console.log("modifyClicked");
			$("#userFirstName").prop("disabled", false);
			$("#userOrganization").prop("disabled", false);
			$("#userLastName").prop("disabled", false);
			$("#userDisplayName").prop("disabled", false);
			$("#userEmail").prop("disabled", false);
			$("#ubranchcode").prop("disabled", false);
			$("#ubranchadd").prop("disabled", false);
			$("#personalNumber").prop("disabled", false);
			$("#ucommonname").prop("disabled", false);
			$("#mobileNumber").prop("disabled", false);
			$("#linemanager").prop("disabled", false);
		});
	});
	//end modify button

	//submit button
	$(document).ready(function() {
		$('#submitdata').click(function() {
			console.log("submitClicked");
			var ufirstname = $("#userFirstName").val();
			var uorgnization = $("#userOrganization").val();
			var ulastname = $("#userLastName").val();
			var udisplayname = $("#userDisplayName").val();
			var uemail = $("#userEmail").val();
			var ubranchcode = $("#ubranchcode").val();
			var ubranchaddress = $("#ubranchadd").val();
			var upersnalNum = $("#personalNumber").val();
			var ucomonName = $("#ucommonname").val();
			var umobile = $("#mobileNumber").val();
			var userloginName = $("#userLogin").val();
			var linemanager = $("#linemanager").val();
			var umiddlename = $("#middlename").val();
			console.log(ufirstname + " " + ubranchcode + " " + userloginName);
			$.ajax({
				type : 'POST',
				data : {
					firstName : ufirstname,
					Organization : uorgnization,
					lastName : ulastname,
					fullName : udisplayname,
					email : uemail,
					branchCode : ubranchcode,
					branchAddress : ubranchaddress,
					personalNum : upersnalNum,
					CommonName : ucomonName,
					mobileNum : umobile,
					lineManager : linemanager,
					middleName : umiddlename,
					userlogin : userloginName
				},
				url : 'ModifyAccountDetails',
				success : function(result) {

					console.log(result)

					alert("Records Updated!");

				}
			});
		});
	});

	//submit button
	//
	$(document).ready(function() {
		$('#btnpassreset').click(function() {
			//console.log("btnpassreset");
			var userlogin = $("#userLogin").val();
			var flag = "restUserPass";
			console.log(userlogin);
			$("body").css("cursor", "wait");

			$.ajax({
				type : 'POST',
				data : {
					userlogin : userlogin,
				},
				url : 'ResetUserPassword',
				success : function(result) {

					console.log(result)
					if (result.RESPONSE == "changed")
						{
						alert("Your password has been reset successfully.");
						$("body").css("cursor", "default");
						}
					else
						{
						alert(result.RESPONSE);
							$("body").css("cursor", "default");						
						}
				}
			});

		});
	});
	//
	function Showabel() {

		document.getElementById("displaydisableMsg").style.display = "block";
	};
	function HideLabel() {

		var seconds = 5;
		setTimeout(
				function() {
					document.getElementById("displaydisableMsg").style.display = "none";
				}, seconds * 1000);
	};

	$(function() {
		$(document).on("click", "#cartTable button.removeCart", function() {
			var cartTotal = $("#cartTotal").text();
			console.log("Jquery Called" + cartTotal);
			let tr = $(this).closest('tr');
			let appInstanceName = tr.find('td:eq(1) a').text();
			let b = tr.find('td:eq(2)').text();
			let btn = tr.find('td:eq(3) button').text();
			alert(appInstanceName + " = " + b + " = " + btn);
			$.ajax({
				type : 'POST',
				data : {
					appInstanceName : appInstanceName,
					action : "RemoveInCart"
				},
				url : 'AccountProvisioningController',
				success : function(result) {
					$('#cartTable').html(result);

					$("#cartTotal").html(parseInt(cartTotal) - 1);
					console.log(appInstanceName + " Removed in cart" + result);
				}
			});
		});

	});
	$(document)
			.ready(
					function() {
						$('#submitRequest').click(function() {
							//var newPassword = $('#newPassword').val();
							//var confirmPassword = $('#confirmPassword').val();
							$.ajax({
								type : 'POST',
								data : {
									action : "submitRequest"
								},
								url : 'AccountProvisioningController',
								success : function(result) {
									//$('#result1').html(result);

									alert(result);
									window.location = 'makerequest.jsp';
								}
							});

						});

						$('#btnAS400').click(function() {
							//var appInstanceName = $('#btnActiveDirectory').val();
							$('#adAcctDetailsForm').hide();
							$('#EquationDetailsForm').hide();
							$('#equationGrpForm').hide();
							$('#divEQUATION').hide();
							$('#cpAcctDetailsForm').hide();
							$('#divAS400').hide();
							
							var userLogin = $('#userLogin') .val();
							var appInstanceNameTxt = $( '#btnEquation').text();
							console.log("AS400 Clicked");
							$.ajax({
								type : 'POST',
								data : {
									appInstanceName : "AS400",
									userLogin : userLogin,
									action : "getAS400ChildData"
								},
								url : 'ChildDataServlet',
								success : function(result) {
								$('#AS400Tables').html(result.AS400Tables);
								console.log("AS400 Tables: "+result.AS400Tables);
								//alert(result.AS400Tables);
																}
							});
							
							$.ajax({
								type : 'POST',
								data : {
									appInstanceName : "AS400",
									userLogin : userLogin,
									action : "AS400AcctDetails"
								},
								url : 'AccountDetailsController',
								success : function(result) {
										var detailsJson = $
																	.parseJSON(result);
															var tagID = null;
															 
															for (var i = 0; i < detailsJson.length; i++) {
																console
																		.log("Record "
																				+ i
																				+ 1);
																console
																		.log(detailsJson[i].fieldName);
																console
																		.log(detailsJson[i].fieldValue);
																console
																		.log(detailsJson[i].fieldLabel);
																tagID = detailsJson[i].fieldLabel
																		+ "";
																$('#' + tagID)
																		.val(
																				detailsJson[i].fieldValue);

															}
								}
							});

						});
						$('#btnCP1').click(function() {
							//var appInstanceName = $('#btnActiveDirectory').val();
							$('#adAcctDetailsForm').hide();
							$('#EquationDetailsForm').hide();
							$('#equationGrpForm').hide();
							$('#divAS400').hide();
							$('#divEQUATION').hide();
							$('#cpAcctDetailsForm').show();
							
							var userLogin = $('#userLogin') .val();
							var appInstanceNameTxt = $( '#btnEquation').text();
							console.log("btnCP1 Clicked");
							$.ajax({
								type : 'POST',
								data : {
									appInstanceName : "CP1",
									userLogin : userLogin,
									action : "CP1AcctDetails"
								},
								url : 'AccountDetailsController',
								success : function(result) {
								
								console.log(result);
															var detailsJson = $
																	.parseJSON(result);
															var tagID = null;
															 
															for (var i = 0; i < detailsJson.length; i++) {
																console
																		.log("Record "
																				+ i
																				+ 1);
																console
																		.log(detailsJson[i].fieldName);
																console
																		.log(detailsJson[i].fieldValue);
																console
																		.log(detailsJson[i].fieldLabel);
																tagID = detailsJson[i].fieldLabel
																		+ "";
																$('#' + tagID)
																		.val(
																				detailsJson[i].fieldValue);

															}
									
								}
							});

						});
						$('#btnEquation').click(function() {
							//var appInstanceName = $('#btnActiveDirectory').val();
							$('#adAcctDetailsForm').hide();
							$('#divAS400').hide();
							$('#EquationDetailsForm').hide();
							$('#equationGrpForm').hide();
							$('#cpAcctDetailsForm').hide();
							$('#divEQUATION').show();
							
							var userLogin = $('#userLogin') .val();
							var appInstanceNameTxt = $( '#btnEquation').text();
							console.log("btnEQUATION Clicked");
							
							$.ajax({
								type : 'POST',
								data : {
									appInstanceName : "EQUATION",
									userLogin : userLogin,
									action : "getEQUATIONChildData"
								},
								url : 'ChildDataServlet',
								success : function(result) {
								$('#EQUATIONTables').html(result.EQUATIONTables);
								console.log("Equation Tables: "+result.EQUATIONTables);
								//alert(result.AS400Tables);
																}
							});
							
							
								$.ajax({
								type : 'POST',
								data : {
									appInstanceName : "EQUATION",
									userLogin : userLogin,
									action : "EquationAcctDetails"
								},
								url : 'AccountDetailsController',
								success : function(result) {
								//alert("equation");
										var detailsJson = $
																	.parseJSON(result);
															var tagID = null;
															 
															for (var i = 0; i < detailsJson.length; i++) {
																console
																		.log("Record "
																				+ i
																				+ 1);
																console
																		.log(detailsJson[i].fieldName);
																console
																		.log(detailsJson[i].fieldValue);
																console
																		.log(detailsJson[i].fieldLabel);
																tagID = detailsJson[i].fieldLabel
																		+ "";
																$('#' + tagID)
																		.val(
																				detailsJson[i].fieldValue);

															}
								}
							});


						});
						
						$('#btnActiveDirectory').click(function() {
							//var appInstanceName = $('#btnActiveDirectory').val();
							$('#divAS400').hide();
							$('#equationGrpForm').hide();
							$('#cpAcctDetailsForm').hide();
							$('#EquationDetailsForm').hide();
							$('#divEQUATION').hide();
							$('#adAcctDetailsForm').show();
							
							adAcctDetailsForm
								var userLogin = $('#userLogin') .val();
								var appInstanceNameTxt = $( '#btnEquation').text();
							console.log("btnActiveDirectory Clicked");
							$.ajax({
								type : 'POST',
								data : {
									appInstanceName : "ActiveDirectory",
									userLogin : userLogin,
									action : "ADAcctDetails"
								},
								url : 'AccountDetailsController',
								success : function(result) {
										console.log(result);
															var detailsJson = $
																	.parseJSON(result);
															var tagID = null;
															 
															for (var i = 0; i < detailsJson.length; i++) {
																console
																		.log("Record "
																				+ i
																				+ 1);
																console
																		.log(detailsJson[i].fieldName);
																console
																		.log(detailsJson[i].fieldValue);
																console
																		.log(detailsJson[i].fieldLabel);
																tagID = detailsJson[i].fieldLabel
																		+ "";
																$('#' + tagID)
																		.val(
																				detailsJson[i].fieldValue);

															}
								}
							});

						});
						
						//////////////////EQGroup Button///////////////////////////////
						
						
							$('#btnEQGroup').click(function() {
							//var appInstanceName = $('#btnActiveDirectory').val();
							$('#EquationDetailsForm').hide();
							$('#divEQUATION').hide();
							$('#adAcctDetailsForm').hide();
							$('#divAS400').hide();
							$('#cpAcctDetailsForm').hide();
							$('#equationGrpForm').show();
							adAcctDetailsForm
								var userLogin = $('#userLogin') .val();
								var appInstanceNameTxt = $( '#btnEQGroup').text();
									let tr = $(this).closest('tr');
								 	let oiu_key = tr.find('td:eq(0)').text();
									console.log(oiu_key);
							console.log("btnActiveDirectory Clicked");
							$.ajax({
								type : 'POST',
								data : {
									appInstanceName : "EQGroup",
									userLogin : userLogin,
									oiu_key:oiu_key,
									action : "getEQUATIONGroupChildData"
								},
								url : 'ChildDataServlet',
								success : function(result) {
								$('#EQUATIONGroupTables').html(result.EQUATIONGroupTables);
								console.log("EQUATIONGroupTables Tables: "+result.EQUATIONGroupTables);
								//alert(result.EQUATIONGroupTables);
																}
							});
	
							
							$.ajax({
								type : 'POST',
								data : {
									appInstanceName : "EQGroup",
									userLogin : userLogin,
									action : "EQGroupDetails"
								},
								url : 'AccountDetailsController',
								success : function(result) {
										console.log(result);
															var detailsJson = $
																	.parseJSON(result);
															var tagID = null;
															 
															for (var i = 0; i < detailsJson.length; i++) {
																console
																		.log("Record "
																				+ i
																				+ 1);
																console
																		.log(detailsJson[i].fieldName);
																console
																		.log(detailsJson[i].fieldValue);
																console
																		.log(detailsJson[i].fieldLabel);
																tagID = detailsJson[i].fieldLabel
																		+ "";
																$('#' + tagID)
																		.val(
																				detailsJson[i].fieldValue);

															}
								}
							});

						});
						
						//////////////////////////////////////////////////////////////
						
						
												//btnActiveDirectory
						$('#btnEQAOB')
								.click(
										function() {
											$('#adAcctDetailsForm').hide();
											$('#divAS400').show();
											$('#equationGrpForm').hide();
											var userLogin = $('#userLogin')
													.val();
											var appInstanceNameTxt = $(
													'#btnEquation').text();
											console.log("btnEquation Clicked"
													+ userLogin + " = "
													+ appInstanceNameTxt);
											$
													.ajax({
														type : 'POST',
														data : {
															appInstanceName : "EQAOB",
															userLogin : userLogin,
															action : "EQAOBDetails"
														},
														url : 'AccountDetailsController',
														success : function(
																result) {
															$('#actFromRes')
																	.html(
																			result);
															console.log(result);
															var students = $
																	.parseJSON(result);
															var tagID = null;
															 
															for (var i = 0; i < students.length; i++) {
																console
																		.log("Record "
																				+ i
																				+ 1);
																console
																		.log(students[i].fieldName);
																console
																		.log(students[i].fieldValue);
																console
																		.log(students[i].fieldLabel);
																tagID = students[i].fieldLabel
																		+ "";
																$('#' + tagID)
																		.val(
																				students[i].fieldValue);

															}
															 

														}
													});

										});
						$('#updateAD')
								.click(
										function() {
											var UD_ADUSER_FULLNAME = $(
													'#UD_ADUSER_FULLNAME')
													.val();
											var UD_ADUSER_ORGNAME = $(
													'[name="UD_ADUSER_ORGNAME"]')
													.val();
											console.log(UD_ADUSER_FULLNAME);
											//UD_ADUSER_FULLNAME

											$
													.ajax({
														type : 'POST',
														data : {
															UD_ADUSER_FULLNAME : UD_ADUSER_FULLNAME,
															UD_ADUSER_ORGNAME : UD_ADUSER_ORGNAME,
															appInstanceType : "1",
															action : "updateAD"
														},
														url : 'AccountProvisioningController',
														success : function(
																result) {
															//$('#actFromRes').html(result);
															console.log(result);
														}
													});

										});
						$('#updateAS400')
								.click(
										function() {

											var UD_AS400CON_USERCONTROLS = $(
													'#UD_AS400CON_USERCONTROLS')
													.val();
											var UD_AS400CON_GROUP_PROFILE = $(
													'[name="UD_AS400CON_GROUP_PROFILE"]')
													.val();
											var UD_AS400CON_INITIALPROGRAM = $(
													'#UD_AS400CON_INITIALPROGRAM')
													.val();
											console
													.log(UD_AS400CON_GROUP_PROFILE);
											//UD_ADUSER_FULLNAME

											$
													.ajax({
														type : 'POST',
														data : {
															UD_AS400CON_GROUP_PROFILE : UD_AS400CON_GROUP_PROFILE,
															UD_AS400CON_USERCONTROLS : UD_AS400CON_USERCONTROLS,
															UD_AS400CON_INITIALPROGRAM : UD_AS400CON_INITIALPROGRAM,
															appInstanceType : "1",
															action : "updateAS400"
														},
														url : 'AccountProvisioningController',
														success : function(
																result) {
															//$('#actFromRes').html(result);
															console.log(result);
														}
													});

										});
			
			
					});
					
					//
					 //
		/*   $(document).on("click","a[name='acctDisable']", function (e) {
    		console.log($(this).attr('href'));
    		
    	let tr = $(this).closest('tr');
		    let a = tr.find('td:eq(0)').text();
		    let b = tr.find('td:eq(1)').text(); 
		    console.log(a +" " + b+" "+a);
    
    		
    	$.ajax({
					type: 'POST',
					data:{appName:b,appKey:a,action:"acctDisable"},
					url: 'AccountDisableEnable',
					success: function(result){
				    alert(result.ID+" Request Initiated!");
					 console.log(result);
					 
					} 
				}); 
		}); */
		//Disable button end
		  //
		  
		  //
		  	   $(document).on("click","a[name='acctEnable']", function (e) {
    	console.log($(this).attr('href'));
    		
    	    var tr = $(this).closest('tr');
		    
		    var b = tr.find('td:eq(1)').text(); 
		    var a = tr.find('td:eq(0)').text();
		    var c= 	tr.find('td:eq(5)').text();
		    console.log(c);
		    $("#appId").html(a);
		    $("#AppHead").html(b);
		     $("#Appstatus").html(c);
		
		    $('#EDModal').modal('show');
		});
		
		
		
		////Modify Account/////////
		
		$(document).on("click","a[name='abcd']", function (e) {
    		console.log($(this).attr('href'));
    		
    		let tr = $(this).closest('tr');
		    let oiu_key = tr.find('td:eq(0)').text();
		    let AppName = tr.find('td:eq(1)').text(); 
		    console.log(oiu_key +" " + AppName+" Modify Called");
		
		
		
var strFields = ',';		
if(AppName == "ActiveDirectory")
{	


var PagerAD   = 'null';
var FaxAD     = 'null';
var userkeytodisbale = null;
////////////////////////AD Update Variabled////////////////////////
var Account_Expiration_DateAD='';
var PasswordNotRequired='';
var ServiceAccountAD='';
var UserMustChangePasswordAtNextLogon='';
var AccountIsLockedOut='';
var PasswordNeverExpries='';
var StateAD='';
var Post_Office_BoxAD='';
var ExtensionNumber='';
var E_MailAD='';
var RedirectionMailID='';
var Terminal_Profile_PathAD='';
var Telephone_NumberAD='';
var CityAD='';
var Home_PhoneAD='';
var Home_DirectoryAD='';
var Terminal_Home_DirectoryAD='';
var Middle_NameAD='';
var ZipAD='';
var StreetAD='';
var CountryAD='';
var Organization_NameAD='';
var OfficeAD='';
var Common_NameAD='';
var Full_NameAD='';
var CompanyAD='';
var Last_NameAD='';
var DepartmentAD='';
var Middle_NameAD='';
var TitleAD='';
var First_NameAD='';
var IP_PhoneAD='';
var FaxAD1='';
var PagerAD1='';
var User_Principal_NameAD='';
var MobileAD='';
var Unique_IdAD='';


//////////////////////////////////////////////////////////////////		    
    userkeytodisbale=$('#userLogin').val();
    
xml ="<?xml version=\"1.0\" standalone=\"yes\" ?> <ChildData>";
xml =xml+ "<Application name=\"ActiveDirectory\">";
xml =xml + "<table table_name=\"FormData\">";
xml =xml + "<userLogin login=\""+userkeytodisbale+"\"/>";
xml =xml+  "<Record id=\""+1+"\">";

 
 $("#Unique_IdAD").change(function(){
   
   Unique_IdAD=$(this).val();
   
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
 
 $("#MobileAD").change(function(){
   
   MobileAD=$(this).val();
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });

/*
$("#Passowrd").change(function(){
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
    alert(strFields);
      
  });
*/

$("#User_Principal_NameAD").change(function(){
   
   User_Principal_NameAD=$(this).val();
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });

  $("#PagerAD").change(function(){
   
   PagerAD1=$(this).val();
   
  
  PagerAD = ($("#PagerAD").val());
  strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
 //  alert($("#PagerAD").val());
  // alert(strFields);
  });
 /* 
  $("#User_IdAD").change(function(){
  
  strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  
  // alert(strFields);
  });
  */
   $("#FaxAD").change(function(){
  
   FaxAD1=$(this).val();
  
  
   FaxAD = ($("#FaxAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
      
  });
  
  $("#IP_PhoneAD").change(function(){
  
  
   IP_PhoneAD=$(this).val();
  
  
   FaxAD = ($("#IP_PhoneAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  
  $("#First_NameAD").change(function(){
   
   First_NameAD=$(this).val();
  
   
   FaxAD = ($("#First_NameAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
      
  });
  
  $("#TitleAD").change(function(){
  
   TitleAD=$(this).val();
  
   FaxAD = ($("#TitleAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
      
  });
  
  
  $("#Middle_NameAD").change(function(){
  
   Middle_NameAD=$(this).val();
  
   FaxAD = ($("#Middle_NameAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  
  
  $("#DepartmentAD").change(function(){
   
   DepartmentAD=$(this).val();
  
   
   FaxAD = ($("#DepartmentAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  
  
  $("#Last_NameAD").change(function(){
  
   Last_NameAD=$(this).val();
  
  
  
   FaxAD = ($("#Last_NameAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  
  
  
  $("#CompanyAD").change(function(){
   CompanyAD=$(this).val();
  
   
   FaxAD = ($("#CompanyAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  
  $("#Full_NameAD").change(function(){
   Full_NameAD=$(this).val();
  
  FaxAD = ($("#Full_NameAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  
  $("#Common_NameAD").change(function(){
   Common_NameAD=$(this).val();
  
   
   FaxAD = ($("#Common_NameAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  
  $("#OfficeAD").change(function(){
   OfficeAD=$(this).val();
  
   
   FaxAD = ($("#OfficeAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  
  $("#Organization_NameAD").change(function(){
   Organization_NameAD=$(this).val();
  
   
   FaxAD = ($("#Organization_NameAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
      
  });
  
  $("#CountryAD").change(function(){
   CountryAD=$(this).val();
  
   FaxAD = ($("#CountryAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
      
  });
  
  $("#StreetAD").change(function(){
   StreetAD=$(this).val();
  
   FaxAD = ($("#StreetAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
      
  });
  
  
  $("#ZipAD").change(function(){
   ZipAD=$(this).val();
  
   FaxAD = ($("#ZipAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  
  
  $("#Middle_NameAD").change(function(){
    
   Middle_NameAD=$(this).val();
  
   
   FaxAD = ($("#Middle_NameAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  
  $("#Terminal_Home_DirectoryAD").change(function(){
    
   Terminal_Home_DirectoryAD=$(this).val();
  
   FaxAD = ($("#Terminal_Home_DirectoryAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  $("#Home_DirectoryAD").change(function(){
    
   Home_DirectoryAD=$(this).val();
  
   
   FaxAD = ($("#Home_DirectoryAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  
  $("#Home_PhoneAD").change(function(){
    
   Home_PhoneAD=$(this).val();
  
   FaxAD = ($("#Home_PhoneAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  $("#CityAD").change(function(){
    

   CityAD=$(this).val();
  
   
   FaxAD = ($("#CityAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  $("#Telephone_NumberAD").change(function(){
    

   Telephone_NumberAD=$(this).val();
  
   FaxAD = ($("#Telephone_NumberAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  $("#Terminal_Profile_PathAD").change(function(){
    

   Terminal_Profile_PathAD=$(this).val();
  
   FaxAD = ($("#Terminal_Profile_PathAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  $("#RedirectionMailID").change(function(){
    

   RedirectionMailID=$(this).val();
  
   
   FaxAD = ($("#RedirectionMailID").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  $("#E_MailAD").change(function(){
    

   E_MailAD=$(this).val();
  
   FaxAD = ($("#E_MailAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  $("#ExtensionNumber").change(function(){
 
 

   ExtensionNumber=$(this).val();
   
   FaxAD = ($("#ExtensionNumber").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
      
  });
  $("#Post_Office_BoxAD").change(function(){
    

   Post_Office_BoxAD=$(this).val();
  
   FaxAD = ($("#Post_Office_BoxAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
      
  });
  $("#StateAD").change(function(){
   

   StateAD=$(this).val();
  
   FaxAD = ($("#StateAD").val());
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
      
  });
  $("#PasswordNeverExpries").change(function(){
    

   
   
   if($(this).is(":checked")) {
      //'checked' event code
      
      PasswordNeverExpries="true";
      
   strFields = strFields+ $(this).attr("id")+':'+true+',';
   // alert(strFields);
  }
  else{
   strFields = strFields+ $(this).attr("id")+':'+false+',';
  //  alert(strFields);
  PasswordNeverExpries="false";
      }
  });
  $("#AccountIsLockedOut").change(function(){
   
    

   
   
   if($(this).is(":checked")) {
      //'checked' event code
      AccountIsLockedOut="true";
   strFields = strFields+ $(this).attr("id")+':'+true+',';
  //  alert(strFields);
  }
  else{
  AccountIsLockedOut="false";
   strFields = strFields+ $(this).attr("id")+':'+false+',';
   // alert(strFields);
      }
  });
  $("#UserMustChangePasswordAtNextLogon").change(function(){

   if($(this).is(":checked")) {
      //'checked' event code
      UserMustChangePasswordAtNextLogon="true";
   strFields = strFields+ $(this).attr("id")+':'+true+',';
   // alert(strFields);
  }
  else{
  UserMustChangePasswordAtNextLogon="false";
   strFields = strFields+ $(this).attr("id")+':'+false+',';
   // alert(strFields);
      }
  });
  $("#ServiceAccountAD").change(function(){

   if($(this).is(":checked")) {
      //'checked' event code
      ServiceAccountAD="true";
   strFields = strFields+ $(this).attr("id")+':'+true+',';
  //  alert(strFields);
  }
  else{
  ServiceAccountAD="false";
   strFields = strFields+ $(this).attr("id")+':'+false+',';
  //  alert(strFields);
      }
  });
  
  $("#PasswordNotRequired").change(function(){

   
   if($(this).is(":checked")) {
      //'checked' event code
      PasswordNotRequired="true";
   strFields = strFields+ $(this).attr("id")+':'+true+',';
  //  alert(strFields);
  }
  else{
  PasswordNotRequired="false";
   strFields = strFields+ $(this).attr("id")+':'+false+',';
  //  alert(strFields);
      }
  });
  
  $("#Account_Expiration_DateAD").change(function(){
    

   Account_Expiration_DateAD=$(this).val();
  
   var dateAr = $(this).val().split('-');
var newDate = dateAr[2] + '/' + dateAr[1] + '/' + dateAr[0];
   FaxAD = ($("#Account_Expiration_DateAD").val());
  
   
   strFields = strFields+ $(this).attr("id")+':'+newDate+',';
  //  alert(strFields);
      
  });
  
		$('#submitAD').click(function() {
	
	
	if(Unique_IdAD)
		{
			xml =xml+ "<Unique_Id>"+Unique_IdAD+"</Unique_Id>";
		}
	
	if(MobileAD)
		{
			xml =xml+ "<Mobile>"+MobileAD+"</Mobile>";
		}
	
	if(User_Principal_NameAD)
		{
			xml =xml+ "<User_Principal_Name>"+User_Principal_NameAD+"</User_Principal_Name>";
		}
	
	if(PagerAD1)
		{
			xml =xml+ "<Pager>"+PagerAD1+"</Pager>";
		}
	
	if(FaxAD1)
		{
			xml =xml+ "<Fax>"+FaxAD1+"</Fax>";
		}
	
	if(IP_PhoneAD)
		{
			xml =xml+ "<IP_Phone>"+IP_PhoneAD+"</IP_Phone>";
		}
	
	if(First_NameAD)
		{
			xml =xml+ "<First_Name>"+First_NameAD+"</First_Name>";
		}
	
		if(TitleAD)
		{
			xml =xml+ "<Title>"+TitleAD+"</Title>";
		}
		
		if(Middle_NameAD)
		{
			xml =xml+ "<Middle_Name>"+Middle_NameAD+"</Middle_Name>";
		}
		
		if(DepartmentAD)
		{
			xml =xml+ "<Department>"+DepartmentAD+"</Department>";
		}
		
		if(Last_NameAD)
		{
			xml =xml+ "<Last_Name>"+Last_NameAD+"</Last_Name>";
		}
		
		if(CompanyAD)
		{
			xml =xml+ "<Company>"+CompanyAD+"</Company>";
		}
		
		if(Full_NameAD)
		{
			xml =xml+ "<Full_Name>"+Full_NameAD+"</Full_Name>";
		}
		
		if(Common_NameAD)
		{
			xml =xml+ "<Common_Name>"+Common_NameAD+"</Common_Name>";
		}
			
		if(OfficeAD)
		{
			xml =xml+ "<Office>"+OfficeAD+"</Office>";
		}
		
		if(Organization_NameAD)
		{
			xml =xml+ "<Organization_Name>"+Organization_NameAD+"</Organization_Name>";
		}
		
		if(CountryAD)
		{
			xml =xml+ "<Country>"+CountryAD+"</Country>";
		}
		
		if(StreetAD)
		{
			xml =xml+ "<Street>"+StreetAD+"</Street>";
		}
		
		if(ZipAD)
		{
			xml =xml+ "<Zip>"+ZipAD+"</Zip>";
		}
		
		if(Middle_NameAD)
		{
			xml =xml+ "<Middle_Name>"+Middle_NameAD+"</Middle_Name>";
		}
		
		if(Terminal_Home_DirectoryAD)
		{
			xml =xml+ "<Terminal_Home_Directory>"+Terminal_Home_DirectoryAD+"</Terminal_Home_Directory>";
		}
		
		
		if(PasswordNeverExpries)
		{
			xml =xml+ "<PasswordNeverExpires>"+PasswordNeverExpries+"</PasswordNeverExpires>";
		}
		if(Home_DirectoryAD)
		{
			xml =xml+ "<Home_Directory>"+Home_DirectoryAD+"</Home_Directory>";
		}
		
		if(Home_PhoneAD)
		{
			xml =xml+ "<Home_Phone>"+Home_PhoneAD+"</Home_Phone>";
		}
		
		if(CityAD)
		{
			xml =xml+ "<City>"+CityAD+"</City>";
		}
		
		if(Telephone_NumberAD)
		{
			xml =xml+ "<Telephone_Number>"+Telephone_NumberAD+"</Telephone_Number>";
		}
		
		if(Terminal_Profile_PathAD)
		{
			xml =xml+ "<Terminal_Profile_Path>"+Terminal_Profile_PathAD+"</Terminal_Profile_Path>";
		}
		
		if(RedirectionMailID)
		{
			xml =xml+ "<RedirectionMail>"+RedirectionMailID+"</RedirectionMail>";
		}
		
		if(E_MailAD)
		{
			xml =xml+ "<E_Mail>"+E_MailAD+"</E_Mail>";
		}
		
		if(ExtensionNumber)
		{
			xml =xml+ "<ExtensionNumber>"+ExtensionNumber+"</ExtensionNumber>";
		}
		
		if(Post_Office_BoxAD)
		{
			xml =xml+ "<Post_Office_Box>"+Post_Office_BoxAD+"</Post_Office_Box>";
		}
		
		if(StateAD)
		{
			xml =xml+ "<State>"+StateAD+"</State>";
		}
		
		if(AccountIsLockedOut)
		{
			xml =xml+ "<AccountIsLockedOut>"+AccountIsLockedOut+"</AccountIsLockedOut>";
		}

		if(Account_Expiration_DateAD)
		{
			xml =xml+ "<Account_Expiration_Date>"+Account_Expiration_DateAD+"</Account_Expiration_Date>";
		}
		if(PasswordNotRequired)
		{
			xml =xml+ "<PasswordNotRequired>"+PasswordNotRequired+"</PasswordNotRequired>";
		}
		if(ServiceAccountAD)
		{
			xml =xml+ "<ServiceAccount>"+PasswordNotRequired+"</ServiceAccount>";
		}
		if(UserMustChangePasswordAtNextLogon)
		{
			xml =xml+ "<UserMustChangePasswordAtNextLogon>"+UserMustChangePasswordAtNextLogon+"</UserMustChangePasswordAtNextLogon>";
		}
		xml =xml+ "</Record>";
		xml =xml+ "</table></Application></ChildData>";			
		
		console.log("XML ACtive Directory: "+xml);
		console.log(strFields);		
		
		$.ajax({
			type : 'POST',
			data : {
	            oiu_key:oiu_key,
			    AppName:AppName,
				PagerAD : PagerAD,
				FaxAD : FaxAD,
				strFields:strFields,
				userkeytodisbale:userkeytodisbale,
				xml:JSON.stringify(xml),
				action : 'AD'
			},
			url : 'ModifyFormServlet',
			success : function(result) {
				//alert(strFields);
				console.log(result);
	//			alert(result + " Request Initiated!");
						var r = confirm(result + " Request Initiated!");
    if (r == true){
      	window.location = window.location.href.split("#")[0];
    }
    else{
		window.location = window.location.href.split("#")[0];    
    }
		
				$('#adAcctDetailsForm').hide();
			}

		});
		 xml ="";
		 strFields="";
	//	location.reload();
		
		});  
    /*		
    	$.ajax({
					type: 'POST',
					data:{appName:b,appKey:a,action:"acctDisable"},
					url: 'AccountDisableEnable',
					success: function(result){
				    alert(result.ID+" Request Initiated!");
					 console.log(result);
					 
					} 
				});*/ 
			}
			
			

//////////////////////////////////Cash Portal Form Update////////////////////
			
			else if(AppName == "CP1")
			{	
			
strFields = ',';
var userkeytodisbale = null;
////////////////////////CashPortal Update Variabled////////////////////////

var DepartmentidCP='';
var UsertypeidCP='';
var RegionidCP='';


//////////////////////////////////////////////////////////////////		    
    userkeytodisbale=$('#userLogin').val();
    
xml ="<?xml version=\"1.0\" standalone=\"yes\" ?> <ChildData>";
xml =xml+ "<Application name=\"CashPortal\">";
xml =xml + "<table table_name=\"FormData\">";
xml =xml + "<userLogin login=\""+userkeytodisbale+"\"/>";
xml =xml+  "<Record id=\""+1+"\">";

 
 $("#DepartmentidCP").change(function(){
   
       var val = $("#DepartmentidCP").val();

                 var obj = $("#DepartmentidCP1").find("option[value='" + val + "']");

		if(obj != null && obj.length > 0)
	    	
	    	{
   
   DepartmentidCP=$(this).val();
   
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
    //alert(strFields);
   }
   else{
   alert("Please enter Valid Department ID");
   }
      
  });
 
 $("#UsertypeidCP").change(function(){

				var val = $("#UsertypeidCP").val();

                 var obj = $("#UsertypeidCP1").find("option[value='" + val + "']");

		if(obj != null && obj.length > 0)
	    	
	    	{
   
   UsertypeidCP=$(this).val();
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
      }
      else 
      {
      alert("Please enter Valid User Type ID");
      
      }
  });


$("#RegionidCP").change(function(){
   
				var val = $("#RegionidCP").val();

                 var obj = $("#RegionidCP1").find("option[value='" + val + "']");

		if(obj != null && obj.length > 0)
	    	
	    	{

   RegionidCP=$(this).val();
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
  //  alert(strFields);
  }
     else 
      {
      alert("Please enter Valid Region ID");
      }    
  });
  
  

		$('#submitCP').click(function() {
		
				var val = $("#RegionidCP").val();
				 var obj = $("#RegionidCP1").find("option[value='" + val + "']");
			if(obj != null && obj.length > 0){}	
			else{alert("Please enter Valid Region ID");return;}
				
				var val = $("#UsertypeidCP").val();
				 var obj = $("#UsertypeidCP1").find("option[value='" + val + "']");
			if(obj != null && obj.length > 0){}	
			else{alert("Please enter Valid User Type ID");return;}

				var val = $("#DepartmentidCP").val();
				 var obj = $("#DepartmentidCP1").find("option[value='" + val + "']");
			if(obj != null && obj.length > 0){}	
			else{alert("Please enter Valid Department ID");return;}
	
	if(DepartmentidCP)
		{
			var Departmentid = ($('#DepartmentidCP1 [value="' + DepartmentidCP + '"]').attr('label'));
    
			xml =xml+ "<Department_ID>"+Departmentid+"</Department_ID>";
		}
	
	if(UsertypeidCP)
		{
			var Usertypeid = ($('#UsertypeidCP1 [value="' + UsertypeidCP + '"]').attr('label'));
			xml =xml+ "<Usertype_ID>"+Usertypeid+"</Usertype_ID>";
		}
	
	if(RegionidCP)
		{
			var Regionid = ($('#RegionidCP1 [value="' + RegionidCP + '"]').attr('label'));
			xml =xml+ "<Region_ID>"+Regionid+"</Region_ID>";
		}
	
		xml =xml+ "</Record>";
		xml =xml+ "</table></Application></ChildData>";			
		
		console.log("XML CP: "+xml);
		console.log(strFields);		
		
		$.ajax({
			type : 'POST',
			data : {
	            oiu_key:oiu_key,
			    AppName:AppName,
					strFields:strFields,
				userkeytodisbale:userkeytodisbale,
				xml:JSON.stringify(xml),
				action : 'CP'
			},
			url : 'ModifyFormServlet',
			success : function(result) {
				//alert(strFields);
				console.log(result);
//				alert(result + " Request Initiated!");
				
				var r = confirm(result + " Request Initiated!");
    if (r == true){
      window.location = window.location.href.split("#")[0];
    }
    else{
window.location = window.location.href.split("#")[0];    
    }
				$('#cpAcctDetailsForm').hide();
			}

		});
		 xml ="";
		strFields="";
		
//		location.reload();
//window.location.href.substr(0, window.location.href.indexOf('#'));
console.log("reload");


console.log("strFields"+strFields);
	DepartmentidCP='';
	RegionidCP='';
	UsertypeidCP='';

		}); 
	
			}
			
			
			//////////////////////////////////AS400 Form Update////////////////////
			
			else if(AppName == "AS400")
			{	
			
strFields = ',';
var userkeytodisbale = null;
////////////////////////AS400 Update Variabled////////////////////////

var GroupProfileAS='';
var InitialMenuAS='';
var LimitCapabilitiesAS='';
var InitialProgramAS='';
var UserClassAS='';


//////////////////////////////////////////////////////////////////		    
    userkeytodisbale=$('#userLogin').val();
    
xml ="<?xml version=\"1.0\" standalone=\"yes\" ?> <ChildData>";
xml =xml+ "<Application name=\"AS400\">";
xml =xml + "<table table_name=\"FormData\">";
xml =xml + "<userLogin login=\""+userkeytodisbale+"\"/>";
xml =xml+  "<Record id=\""+1+"\">";

 
 $("#Group_ProfileAS").change(function(){
   
       var val = $("#Group_ProfileAS").val();

                 var obj = $("#Group_ProfileAS1").find("option[value='" + val + "']");

		if(obj != null && obj.length > 0)
	    	
	    	{
   
   GroupProfileAS=$(this).val();
   
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
   }
   else{
   alert("Please enter Valid Group Profile");
   }
      
  });
 
 $("#Initial_MenuAS").change(function(){

				var val = $("#Initial_MenuAS").val();

                 var obj = $("#Initial_MenuAS1").find("option[value='" + val + "']");

		if(obj != null && obj.length > 0)
	    	
	    	{
   
   InitialMenuAS=$(this).val();
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
    //alert(strFields);
      }
      else 
      {
      alert("Please enter Valid Initial Menu");
      
      }
  });

 $("#Initial_ProgramAS").change(function(){

				var val = $("#Initial_ProgramAS").val();

                 var obj = $("#Initial_ProgramAS1").find("option[value='" + val + "']");

		if(obj != null && obj.length > 0)
	    	
	    	{
   
   InitialProgramAS=$(this).val();
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
      }
      else 
      {
      alert("Please enter Valid Initial Program");
      
      }
  });


$("#Limit_CapabilitiesAS").change(function(){
   
			
   LimitCapabilitiesAS=$(this).val();
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
      
  });
  
$("#User_ClassAS").change(function(){
   
			
   UserClassAS=$(this).val();
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
      
  });
  

		$('#submitAS400').click(function() {
		
				
				var val = $("#Initial_MenuAS").val();
				 var obj = $("#Initial_MenuAS1").find("option[value='" + val + "']");
			if(obj != null && obj.length > 0){}	
			else{alert("Please enter Valid Initail Menu");return;}

				var val = $("#Initial_ProgramAS").val();
				 var obj = $("#Initial_ProgramAS1").find("option[value='" + val + "']");
			if(obj != null && obj.length > 0){}	
			else{alert("Please enter Valid Initail Program");return;}


				var val = $("#Group_ProfileAS").val();
				 var obj = $("#Group_ProfileAS1").find("option[value='" + val + "']");
			if(obj != null && obj.length > 0){}	
			else{alert("Please enter Valid Group Profile");return;}
	
	if(GroupProfileAS)
		{
				xml =xml+ "<Group_Profile>"+GroupProfileAS+"</Group_Profile>";
		}
	
	if(InitialMenuAS)
		{
			xml =xml+ "<Initial_Menu>"+InitialMenuAS+"</Initial_Menu>";
		}
	if(InitialProgramAS)
		{
			xml =xml+ "<Initial_Program>"+InitialProgramAS+"</Initial_Program>";
		}
	
	if(LimitCapabilitiesAS)
		{
			xml =xml+ "<Limit_Capabilities>"+LimitCapabilitiesAS+"</Limit_Capabilities>";
		}
	if(UserClassAS)
		{
			xml =xml+ "<User_Class>"+UserClassAS+"</User_Class>";
		}
	
		xml =xml+ "</Record>";
		xml =xml+ "</table></Application></ChildData>";			
		
		console.log("XML AS400: "+xml);
		console.log(strFields);		
		
		$.ajax({
			type : 'POST',
			data : {
	            oiu_key:oiu_key,
			    AppName:AppName,
					strFields:strFields,
				userkeytodisbale:userkeytodisbale,
				xml:JSON.stringify(xml),
				action : 'AS400'
			},
			url : 'ModifyFormServlet',
			success : function(result) {
				//alert(strFields);
				console.log(result);
//				alert(result + " Request Initiated!");
				
				var r = confirm(result + " Request Initiated!");
    if (r == true){
      window.location = window.location.href.split("#")[0];
    }
    else{
window.location = window.location.href.split("#")[0];    
    }
				$('#cpAcctDetailsForm').hide();
			}

		});
		 xml ="";
		strFields="";
		
//		location.reload();
//window.location.href.substr(0, window.location.href.indexOf('#'));
console.log("reload");


console.log("strFields"+strFields);
 GroupProfileAS='';
 InitialMenuAS='';
 LimitCapabilitiesAS='';
 InitialProgramAS='';
 UserClassAS='';

		}); 
	
			}
			
//////////////////////////////////Equation Form Form Update////////////////////
			
			else if(AppName == "EQGroup")
			{	
			
strFields = ',';
var userkeytodisbale = null;
////////////////////////Equatio Form Update Variabled////////////////////////

var UD_EQGROUP_USER_GROUP='';
var UD_EQGROUP_LIMIT='';
var UD_EQGROUP_GROUP_TYPE='';


//////////////////////////////////////////////////////////////////		    
    userkeytodisbale=$('#userLogin').val();
    
xml ="<?xml version=\"1.0\" standalone=\"yes\" ?> <ChildData>";
xml =xml+ "<Application name=\"EquationGroup\">";
xml =xml + "<table table_name=\"FormData\">";
xml =xml + "<userLogin login=\""+userkeytodisbale+"\"/>";
xml =xml+  "<Record id=\""+1+"\">";

 
   
$("#User_GroupEQG").change(function(){
   
			
   UD_EQGROUP_USER_GROUP=$(this).val();
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
      
  });
  
$("#LimitEQG").change(function(){
   
			
   UD_EQGROUP_LIMIT=$(this).val();
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
      
  });
$("#Group_TypeEQG").change(function(){
   
			
   UD_EQGROUP_GROUP_TYPE=$(this).val();
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
      
  });
  

		$('#submitEQUATIONGroup').click(function() {

	if(UD_EQGROUP_USER_GROUP)
		{
				xml =xml+ "<USER_GROUP>"+UD_EQGROUP_USER_GROUP+"</USER_GROUP>";
		}
	
	if(UD_EQGROUP_LIMIT)
		{
			xml =xml+ "<LIMIT>"+UD_EQGROUP_LIMIT+"</LIMIT>";
		}
	if(UD_EQGROUP_GROUP_TYPE)
		{
			xml =xml+ "<GROUP_TYPE>"+UD_EQGROUP_GROUP_TYPE+"</GROUP_TYPE>";
		}
	
	
		xml =xml+ "</Record>";
		xml =xml+ "</table></Application></ChildData>";			
		
		console.log("XML Equation Group: "+xml);
		console.log(strFields);		
		
				var UD_EQGROUP_AUTH_AMOUNT_FOR_LOC;
				var UD_EQGROUP_INTER_BRANCH_DEBIT;				
				var UD_EQGROUP_INTER_BRANCH_CREDIT;
				var UD_EQGROUP_USER_TYPE;
				var UD_EQGROUP_AUTH_AMOUNT_FOR_L90;
				var UD_EQGROUP_AUTH_SPECIFIED_BRAN;
				var UD_EQGROUP_GROUP_DESCRIPTION;
				var UD_EQGROUP_ALL_BRANCHES;
				var UD_EQGROUP_AUTH_AMOUNT_FOR_INT;
				var UD_EQGROUP_AUTH_AMOUNT_FOR_I52;
				var UD_EQGROUP_LIMIT_FOR_LOCAL_CRD;
				var UD_EQGROUP_SPECIFIED_BRANCHES;
				var UD_EQGROUP_AUTH_ALL_BRANCHES;
				var UD_EQGROUP_JUSTIFICATION;
				var UD_EQGROUP_LIMIT_FOR_LOCAL_DEB;
				
				
				console.log("UD_EQGROUP_GROUP_TYPE : "+ UD_EQGROUP_GROUP_TYPE);
				 
				 
				
				 if(UD_EQGROUP_GROUP_TYPE=='' || UD_EQGROUP_GROUP_TYPE==null)
				 {
				 		alert("Please enter Group Type");
				 		return;
				 }
				 
				
			console.log("UD_EQGROUP_GROUP_TYPE:"+UD_EQGROUP_GROUP_TYPE);
			
			if(UD_EQGROUP_GROUP_TYPE == 'Teller'){
	
			if(UD_EQGROUP_LIMIT=='' || UD_EQGROUP_LIMIT == null)
			{
			alert("Please Enter Limit")
			return;
			}
			else if(UD_EQGROUP_USER_GROUP=='' || UD_EQGROUP_USER_GROUP==null)
			{
			alert("Please Enter Group Name")
			return;
			}
				
				UD_EQGROUP_USER_TYPE='U';
				UD_EQGROUP_ALL_BRANCHES='Y';
				UD_EQGROUP_SPECIFIED_BRANCHES='N';
				UD_EQGROUP_AUTH_ALL_BRANCHES='N';
				UD_EQGROUP_AUTH_SPECIFIED_BRAN='N';
				UD_EQGROUP_INTER_BRANCH_DEBIT=UD_EQGROUP_LIMIT;
				UD_EQGROUP_INTER_BRANCH_CREDIT=UD_EQGROUP_LIMIT;
				UD_EQGROUP_LIMIT_FOR_LOCAL_CRD=UD_EQGROUP_LIMIT;
				UD_EQGROUP_LIMIT_FOR_LOCAL_DEB=UD_EQGROUP_LIMIT;
				
				UD_EQGROUP_AUTH_AMOUNT_FOR_L90='';
				UD_EQGROUP_AUTH_AMOUNT_FOR_LOC='';
				UD_EQGROUP_AUTH_AMOUNT_FOR_INT='';
				UD_EQGROUP_AUTH_AMOUNT_FOR_I52='';
				
			}
			else if(UD_EQGROUP_GROUP_TYPE == 'Supervisor'){
			if(UD_EQGROUP_LIMIT=='' || UD_EQGROUP_LIMIT==null)
			{
			alert("Please Enter Limit")
			return;
			}
			else if(UD_EQGROUP_USER_GROUP=='' || UD_EQGROUP_USER_GROUP==null)
			{
			alert("Please Enter Group Name")
			return;
			}
			
				UD_EQGROUP_USER_TYPE='A';
				UD_EQGROUP_ALL_BRANCHES='N';
				UD_EQGROUP_SPECIFIED_BRANCHES='N';
				UD_EQGROUP_AUTH_ALL_BRANCHES='Y';
				UD_EQGROUP_AUTH_SPECIFIED_BRAN='N';
				UD_EQGROUP_AUTH_AMOUNT_FOR_L90=UD_EQGROUP_LIMIT;
				UD_EQGROUP_AUTH_AMOUNT_FOR_LOC=UD_EQGROUP_LIMIT;
				UD_EQGROUP_AUTH_AMOUNT_FOR_INT=UD_EQGROUP_LIMIT;
				UD_EQGROUP_AUTH_AMOUNT_FOR_I52=UD_EQGROUP_LIMIT;
				
				UD_EQGROUP_INTER_BRANCH_DEBIT='';
				UD_EQGROUP_INTER_BRANCH_CREDIT='';
				UD_EQGROUP_LIMIT_FOR_LOCAL_CRD='';
				UD_EQGROUP_LIMIT_FOR_LOCAL_DEB='';
				
			}
			else if(UD_EQGROUP_GROUP_TYPE == 'Enquiry'){
			
			if(UD_EQGROUP_USER_GROUP=='' || UD_EQGROUP_USER_GROUP==null)
			{
			alert("Please Enter Group Name")
			return;
			}
			
				UD_EQGROUP_USER_TYPE='U';
				UD_EQGROUP_ALL_BRANCHES='Y';
				UD_EQGROUP_SPECIFIED_BRANCHES='N';
				UD_EQGROUP_AUTH_ALL_BRANCHES='N';
				UD_EQGROUP_AUTH_SPECIFIED_BRAN='N';
				UD_EQGROUP_LIMIT='';
				
				UD_EQGROUP_AUTH_AMOUNT_FOR_L90='';
				UD_EQGROUP_AUTH_AMOUNT_FOR_LOC='';
				UD_EQGROUP_AUTH_AMOUNT_FOR_INT='';
				UD_EQGROUP_AUTH_AMOUNT_FOR_I52='';
				
				UD_EQGROUP_INTER_BRANCH_DEBIT='';
				UD_EQGROUP_INTER_BRANCH_CREDIT='';
				UD_EQGROUP_LIMIT_FOR_LOCAL_CRD='';
				UD_EQGROUP_LIMIT_FOR_LOCAL_DEB='';
				
			
			
			}
	
		
		$.ajax({
			type : 'POST',
			data : {
	            oiu_key:oiu_key,
			    AppName:AppName,
					strFields:strFields,
				userkeytodisbale:userkeytodisbale,
				UD_EQGROUP_AUTH_AMOUNT_FOR_LOC:UD_EQGROUP_AUTH_AMOUNT_FOR_LOC,
				UD_EQGROUP_INTER_BRANCH_DEBIT:UD_EQGROUP_INTER_BRANCH_DEBIT,				
				UD_EQGROUP_INTER_BRANCH_CREDIT:UD_EQGROUP_INTER_BRANCH_CREDIT,
				UD_EQGROUP_USER_TYPE:UD_EQGROUP_USER_TYPE,
				UD_EQGROUP_AUTH_AMOUNT_FOR_L90:UD_EQGROUP_AUTH_AMOUNT_FOR_L90,
				UD_EQGROUP_AUTH_SPECIFIED_BRAN:UD_EQGROUP_AUTH_SPECIFIED_BRAN,
				UD_EQGROUP_GROUP_DESCRIPTION:UD_EQGROUP_GROUP_DESCRIPTION,
				UD_EQGROUP_ALL_BRANCHES:UD_EQGROUP_ALL_BRANCHES,
				UD_EQGROUP_AUTH_AMOUNT_FOR_INT:UD_EQGROUP_AUTH_AMOUNT_FOR_INT,
				UD_EQGROUP_AUTH_AMOUNT_FOR_I52:UD_EQGROUP_AUTH_AMOUNT_FOR_I52,
				UD_EQGROUP_LIMIT_FOR_LOCAL_CRD:UD_EQGROUP_LIMIT_FOR_LOCAL_CRD,
				UD_EQGROUP_SPECIFIED_BRANCHES:UD_EQGROUP_SPECIFIED_BRANCHES,
				UD_EQGROUP_AUTH_ALL_BRANCHES:UD_EQGROUP_AUTH_ALL_BRANCHES,
				UD_EQGROUP_JUSTIFICATION:UD_EQGROUP_JUSTIFICATION,
				UD_EQGROUP_LIMIT_FOR_LOCAL_DEB:UD_EQGROUP_LIMIT_FOR_LOCAL_DEB,
				UD_EQGROUP_USER_GROUP:UD_EQGROUP_USER_GROUP,
				UD_EQGROUP_LIMIT:UD_EQGROUP_LIMIT,
				UD_EQGROUP_GROUP_TYPE:UD_EQGROUP_GROUP_TYPE,
				xml:JSON.stringify(xml),
				action : 'EQGroup'
			},
			url : 'ModifyFormServlet',
			success : function(result) {
				//alert(strFields);
				console.log(result);
//				alert(result + " Request Initiated!");
				
				var r = confirm(result + " Request Initiated!");
    if (r == true){
      window.location = window.location.href.split("#")[0];
    }
    else{
window.location = window.location.href.split("#")[0];    
    }
				$('#equationGrpForm').hide();
			}

		});
		 xml ="";
		strFields="";
		
//		location.reload();
//window.location.href.substr(0, window.location.href.indexOf('#'));
console.log("reload");


console.log("strFields"+strFields);
				UD_EQGROUP_AUTH_AMOUNT_FOR_LOC='';
				UD_EQGROUP_INTER_BRANCH_DEBIT='';				
				UD_EQGROUP_INTER_BRANCH_CREDIT='';
				UD_EQGROUP_USER_TYPE='';
				UD_EQGROUP_AUTH_AMOUNT_FOR_L90='';
				UD_EQGROUP_AUTH_SPECIFIED_BRAN='';
				UD_EQGROUP_GROUP_DESCRIPTION='';
				UD_EQGROUP_ALL_BRANCHES='';
				UD_EQGROUP_AUTH_AMOUNT_FOR_INT='';
				UD_EQGROUP_AUTH_AMOUNT_FOR_I52='';
				UD_EQGROUP_LIMIT_FOR_LOCAL_CRD='';
				UD_EQGROUP_SPECIFIED_BRANCHES='';
				UD_EQGROUP_AUTH_ALL_BRANCHES='';
				UD_EQGROUP_JUSTIFICATION='';
				UD_EQGROUP_LIMIT_FOR_LOCAL_DEB='';
				UD_EQGROUP_USER_GROUP='';
				UD_EQGROUP_LIMIT='';
				UD_EQGROUP_GROUP_TYPE='';
		}); 
	
			}
/////////////////////////////////Equation Group Form End/////////////////////////			
			else if(AppName == "Equation")
			{
//alert("equation form Called");			
			
strFields = ',';
var userkeytodisbale = null;
////////////////////////CashPortal Update Variabled////////////////////////

var UserClassEquation='';


//////////////////////////////////////////////////////////////////		    
    userkeytodisbale=$('#userLogin').val();
    
xml ="<?xml version=\"1.0\" standalone=\"yes\" ?> <ChildData>";
xml =xml+ "<Application name=\"Equation\">";
xml =xml + "<table table_name=\"FormData\">";
xml =xml + "<userLogin login=\""+userkeytodisbale+"\"/>";
xml =xml+  "<Record id=\""+1+"\">";

 
 $("#User_ClassEQ").change(function(){
   
       var val = $("#User_ClassEQ").val();

                 var obj = $("#User_ClassEQ1").find("option[value='" + val + "']");

		if(obj != null && obj.length > 0)
	    	
	    	{
   
   UserClassEquation=$(this).val();
   
   
   strFields = strFields+ $(this).attr("id")+':'+$(this).val()+',';
   // alert(strFields);
   }
   else{
   alert("Please enter Valid User Class");
   }
      
  });
 
 

		$('#submitEQUATION').click(function() {
		
				
				var val = $("#User_ClassEQ").val();
				 var obj = $("#User_ClassEQ1").find("option[value='" + val + "']");
			if(obj != null && obj.length > 0){}	
			else{alert("Please enter Valid User Class");return;}

				

				
	
	if(UserClassEquation)
		{
				xml =xml+ "<User_Class>"+UserClassEquation+"</User_Class>";
		}
	
	
		xml =xml+ "</Record>";
		xml =xml+ "</table></Application></ChildData>";			
		
		console.log("XML Equation: "+xml);
		console.log(strFields);		
		
		$.ajax({
			type : 'POST',
			data : {
	            oiu_key:oiu_key,
			    AppName:AppName,
					strFields:strFields,
				userkeytodisbale:userkeytodisbale,
				xml:JSON.stringify(xml),
				action : 'Equation'
			},
			url : 'ModifyFormServlet',
			success : function(result) {
				//alert(strFields);
				console.log(result);
//				alert(result + " Request Initiated!");
				
				var r = confirm(result + " Request Initiated!");
    if (r == true){
      window.location = window.location.href.split("#")[0];
    }
    else{
window.location = window.location.href.split("#")[0];    
    }
				$('#divEQUATION').hide();
			}

		});
		 xml ="";
		strFields="";
		
//		location.reload();
//window.location.href.substr(0, window.location.href.indexOf('#'));
console.log("reload");


console.log("strFields"+strFields);
 UserClassEquation='';

		}); 
	
			}
			
			
			///////////////////////////////AS400 child data Ammmendment///////////////////////
			
			
		$('#submitAS400Child').click(function(){
				

var userkeytodisbale = $('#userLogin').val();
xml = "<?xml version=\"1.0\" standalone=\"yes\" ?>";
xml = xml +"<ChildData>";
xml = xml +"<Application name=\"AS400\">";
xml = xml + "<table table_name=\"Special_Authority_REMOVE\">";
xml = xml + "<userLogin login=\""+userkeytodisbale+"\"/>";


var n1 = document.getElementById("tableCSP").rows.length;
var grid = document.getElementById("tableCSP");
var checkBoxes = grid.getElementsByTagName("INPUT");
 console.log("checkBoxes:"+checkBoxes);
 console.log("checkBoxes.length:"+checkBoxes.length);
 
var i=0,j=0;
var str="";
 
 var tableCSP="";
 var row="";
 var col="";
 
 


for (var z = 0; z < checkBoxes.length; z++) {
            if (checkBoxes[z].checked) {
           // alert("checked");
            var row = checkBoxes[z].parentNode.parentNode;
            tableCSP=tableCSP+ "<Record id=\""+(z+1)+"\">";
            tableCSP=tableCSP+ "<"+row.cells[0].innerHTML.replace(/[^a-zA-Z ]/g, "")+">"+row.cells[1].innerHTML+"</"+row.cells[0].innerHTML.replace(/[^a-zA-Z ]/g, "")+">";
            tableCSP=tableCSP+ "</Record>";
            }}
      tableCSP=tableCSP+"</table>";
tableCSP = tableCSP + "<table table_name=\"Special_Authority_ADD\">";
tableCSP = tableCSP + "<userLogin login=\""+userkeytodisbale+"\"/>";
      
      
for(i=checkBoxes.length+1; i<n1;i++){
console.log("i::"+i);
 row =  "<Record id=\""+(i-(checkBoxes.length))+"\">";
 
var n2 = document.getElementById("tableCSP").rows[i].cells.length;
  
for(j=0; j<n2-2;j++){
 
var x=document.getElementById("tableCSP").rows[i].cells.item(j).innerHTML;
var header=document.getElementById("tableCSP").rows[0].cells.item(j).innerHTML;
 col = col + "<"+header+">"+x+"</"+header+">";
   // tableCSP[j] = x;
   
 
    str=str+x+":";
 
 
}
tableCSP=tableCSP+row+col+"</Record>";
row="";
col="";
str=str+"#";

}
xml=xml+tableCSP;

 xml = xml + "</table></Application></ChildData>";
 
	console.log(xml);
	
	$.ajax({
					type: 'POST',
					data:{AppName:"",userkeytodisbale:userkeytodisbale,oiu_key:oiu_key,action:"AS400ChildAmmendment",xml:JSON.stringify(xml)},
					url: 'ModifyFormServlet',
					success: function(result){
					//$('#actFromRes').html(result);
					console.log(result);
					var r = confirm(result + " Request Initiated!");
					if (r == true){
      	window.location = window.location.href.split("#")[0];
    }
    else{
		window.location = window.location.href.split("#")[0];    
    }
					
					}
				});
				 xml ="";
	
	});
	
	///////////////////////////////AS400 child data Ammmendment end///////////////////////		

///////////////////////////////EQG child data Ammmendment///////////////////////
			
			
		$('#submitEQUATIONGroupChild').click(function(){
				
//alert();
var userkeytodisbale = $('#userLogin').val();
xml = "<?xml version=\"1.0\" standalone=\"yes\" ?>";
xml = xml +"<ChildData>";
xml = xml +"<Application name=\"Equation_Group\">";
xml = xml + "<table table_name=\"Option_ID_REMOVE\">";
xml = xml + "<userLogin login=\""+userkeytodisbale+"\"/>";


var n1 = document.getElementById("tableEQGroup").rows.length;
var grid = document.getElementById("tableEQGroup");
var checkBoxes = grid.getElementsByTagName("INPUT");
 console.log("checkBoxes:"+checkBoxes);
 console.log("checkBoxes.length:"+checkBoxes.length);
 
var i=0,j=0;
var str="";
 
 var tableEQGroup="";
 var row="";
 var col="";
 
 


for (var z = 0; z < checkBoxes.length; z++) {
            if (checkBoxes[z].checked) {
           // alert("checked");
            var row = checkBoxes[z].parentNode.parentNode;
            tableEQGroup=tableEQGroup+ "<Record id=\""+(z+1)+"\">";
            tableEQGroup=tableEQGroup+ "<"+row.cells[0].innerHTML.replace(/[^a-zA-Z ]/g, "")+">"+row.cells[1].innerHTML+"</"+row.cells[0].innerHTML.replace(/[^a-zA-Z ]/g, "")+">";
            tableEQGroup=tableEQGroup+ "</Record>";
            }}
      tableEQGroup=tableEQGroup+"</table>";
tableEQGroup = tableEQGroup + "<table table_name=\"Option_ID_ADD\">";
tableEQGroup = tableEQGroup + "<userLogin login=\""+userkeytodisbale+"\"/>";
      
      
for(i=checkBoxes.length+1; i<n1;i++){
console.log("i::"+i);
 row =  "<Record id=\""+(i-(checkBoxes.length))+"\">";
 
var n2 = document.getElementById("tableEQGroup").rows[i].cells.length;
  
for(j=0; j<n2-2;j++){
 
var x=document.getElementById("tableEQGroup").rows[i].cells.item(j).innerHTML;
var header=document.getElementById("tableEQGroup").rows[0].cells.item(j).innerHTML;
 col = col + "<"+header+">"+x+"</"+header+">";
   // tableEQGroup[j] = x;
   
 
    str=str+x+":";
 
 
}
tableEQGroup=tableEQGroup+row+col+"</Record>";
row="";
col="";
str=str+"#";

}
xml=xml+tableEQGroup;

 xml = xml + "</table></Application></ChildData>";
 
	console.log(xml);
	
	$.ajax({
					type: 'POST',
					data:{AppName:"",userkeytodisbale:userkeytodisbale,oiu_key:oiu_key,action:"EquationGroupChildAmmendment",xml:JSON.stringify(xml)},
					url: 'ModifyFormServlet',
					success: function(result){
					//$('#actFromRes').html(result);
					console.log(result);
					var r = confirm(result + " Request Initiated!");
					if (r == true){
      	window.location = window.location.href.split("#")[0];
    }
    else{
		window.location = window.location.href.split("#")[0];    
    }
					
					}
				});
				 xml ="";
	
	});
	
	///////////////////////////////EQG child data Ammmendment end///////////////////////		



	///////////////////////////////EQAUTION child data Ammmendment///////////////////////
						
			
			

$('#submitEQUATIONChild').click(function(){
				

var userkeytodisbale = $('#userLogin').val();
xml = "<?xml version=\"1.0\" standalone=\"yes\" ?>";
xml = xml +"<ChildData>";
xml = xml +"<Application name=\"EQUATION\">";
xml = xml + "<table table_name=\"EQAUTION_TABLE26_REMOVE\">";
xml = xml + "<userLogin login=\""+userkeytodisbale+"\"/>";


var n1 = document.getElementById("table26").rows.length;
var grid = document.getElementById("table26");
var checkBoxes = grid.getElementsByTagName("INPUT");
 console.log("checkBoxes:"+checkBoxes);
 console.log("checkBoxes.length:"+checkBoxes.length);
 
var i=0,j=0;
var str="";
 
 var table26="";
 var row="";
 var col="";
 
 


for (var z = 0; z < checkBoxes.length; z++) {
            if (checkBoxes[z].checked) {
           // alert("checked");
            var row = checkBoxes[z].parentNode.parentNode;
            table26=table26+ "<Record id=\""+(z+1)+"\">";
            table26=table26+ "<"+row.cells[5].innerHTML.replace(/[^a-zA-Z ]/g, "")+">"+row.cells[6].innerHTML+"</"+row.cells[5].innerHTML.replace(/[^a-zA-Z ]/g, "")+">";
            table26=table26+ "</Record>";
            }}
      table26=table26+"</table>";
table26 = table26 + "<table table_name=\"EQAUTION_TABLE26_ADD\">";
table26 = table26 + "<userLogin login=\""+userkeytodisbale+"\"/>";
      
      
for(i=checkBoxes.length+1; i<n1;i++){
console.log("i::"+i);
 row =  "<Record id=\""+(i-(checkBoxes.length))+"\">";
 
var n2 = document.getElementById("table26").rows[i].cells.length;
  
for(j=0; j<n2-1;j++){
 
var x=document.getElementById("table26").rows[i].cells.item(j).innerHTML;
var header=document.getElementById("table26").rows[0].cells.item(j).innerHTML;
 col = col + "<"+header+">"+x+"</"+header+">";
   // table26[j] = x;
   
 
    str=str+x+":";
 
 
}
table26=table26+row+col+"</Record>";
row="";
col="";
str=str+"#";

}
xml=xml+table26;

 xml = xml + "</table></Application></ChildData>";
 
	console.log(xml);
	
	$.ajax({
					type: 'POST',
					data:{AppName:"",userkeytodisbale:userkeytodisbale,oiu_key:oiu_key,action:"EQUATIONChildAmmendment",xml:JSON.stringify(xml)},
					url: 'ModifyFormServlet',
					success: function(result){
					//$('#actFromRes').html(result);
					console.log(result);
					var r = confirm(result + " Request Initiated!");
					if (r == true){
      	window.location = window.location.href.split("#")[0];
    }
    else{
		window.location = window.location.href.split("#")[0];    
    }
					
					}
				});
				 xml ="";
	
	});
	
	///////////////////////////////EQUATION child data Ammmendment end///////////////////////		


		
		}); 
	
		
		////Modify Cash Portal End//////
		 
		//Enable Button
		
		/*
		$(document).ready(function() {		//ADmodification
		$('#submitAD').click(function() {
		
		console.log("AD submit");		
		$("#Unique_IdAD, #MobileAD, #Passowrd, #PagerAD").change(function() { 
		
		
		var uid = $('#Unique_IdAD').val();
		var ADmobile = $('#MobileAD').val();
		var ADpass = $('#Passowrd').val();
		var ADPager = $('#PagerAD').val();
		
		console.log(uid+" "+ADmobile+" "+ADpass+" "+ADPager);
		
		});
		});
	});
	*/
	//EnableDisableApplication
	$(document).ready(function(){
			$('#edApp').click(function(){
			
			//console.log("Salahuddin:: here");
				var appName = $('#AppHead').text();
				var appKey = $('#appId').text();
				var status =	$('#Appstatus').text();
				var commReason = $('#EDReason').val();
				var userlogin = $('#userLogin').val();
				console.log(status);
				console.log(commReason);
				console.log(appKey);
				console.log(appName);
				console.log(userlogin);
				
				if(status =='Enabled'){
				
				alert(status);
		
					$.ajax({
					type : 'POST',
			data : {
				appName :appName,
				appKey : appKey,
				action : "acctDisable",
				justification:commReason,
				userlogin:userlogin
			},
			url : 'AccountDisableEnable',
			success : function(result) {

				console.log(result.ID);
				$('#EDModal').modal('hide');
				alert(result.ID);

			}


		}); 
				
				
				
	}else{
		
	
				$.ajax({
			type : 'POST',
			data : {
				appName :appName,
				appKey : appKey,
				action : "acctEnable",
				justification:commReason,
				userlogin:userlogin
			},
			url : 'AccountDisableEnable',
			success : function(result) {

				console.log(result.ID);
				$('#EDModal').modal('hide');
				alert(result.ID + " Request Initiated!");

			}


		}); 
				
	
	
	
	
	} 
				
				
				
			
				
				});
				});				
					
					//EnableDisableApplication
</script>

</head>

<body>
	<%
//	OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
		String name = (String) session.getAttribute("username");
		//	OIMUtils oimUtils = new OIMUtils();
		//	Map<Long,UsersList> userList =oimUtils.getAllUsers(oimClient);
			
			//oimUtils.getUserLoginName( userList.get(userMap.getKey()).getUserManager(), oimClient);

		if (name != null && name.length() != 0) {
			session.setAttribute("username", name);
		} else
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
					<div class="panel panel-profile">
						<div class="clearfix">
							<!--  CENTER START -->

							<div class="container">
							<h3 class="heading" style="margin-left: 10px">User
											Details</h3>
								<div class="row" style="padding-top: 20px;">

									<div class="col-md-12">

										<%
OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
String userLogin  = (String)session.getAttribute("username");
OIMUtils oimUtils1 = new OIMUtils();

ProvisioningAccounts pa = new ProvisioningAccounts();
String userkey1 = pa.getUserKey(userLogin, oimClient);
String userKey2=request.getParameter("userKey");
System.out.print("User State Key: "+userKey2+"->>>>>>>>>>>>>>>>>>>>>>");
String targetUserLogin = oimUtils1.getUserLogin(userKey2, oimClient);

String userState=oimUtils1.getUserStatusByKey(userKey2, oimClient);
UserOperationModifyAcc uomc = new UserOperationModifyAcc();
System.out.print(uomc.getRole(userkey1)+"->>>>>>>>>>>>>>>>>>>>>>");

if(uomc.getRole(userkey1).equals("Y")){

//out.write("<button class='btn btn-sm btn-primary' id ='btnmodify' style='background-color:#009591'><i class='glyphicon glyphicon-pencil'></i> Modify</button>");
if(userState.equalsIgnoreCase("Active"))
        	{
out.write("<a data-toggle='modal' data-target= '.enablePop'><button class='btn btn-sm btn-primary' id='btnEnable' style='background-color:#009591' disabled><i class='glyphicon glyphicon-ok'></i> Enable</button>");

out.write("<a data-toggle='modal' data-target= '.disablePop'><button class='btn btn-sm btn-primary' id='btnDisable' style='background-color:#009591'><i class='glyphicon glyphicon-minus-sign'></i> Disable</button>");
out.write("<a><button class='btn btn-sm btn-primary'  style='background-color:#009591' id='btnDeleteUser'><i class='glyphicon glyphicon-remove'></i> Delete</button>");

}
else{
out.write("<a data-toggle='modal' data-target= '.enablePop'><button class='btn btn-sm btn-primary' id='btnEnable' style='background-color:#009591'><i class='glyphicon glyphicon-ok'></i> Enable</button>");

out.write("<a data-toggle='modal' data-target= '.disablePop'><button class='btn btn-sm btn-primary' id='btnDisable' style='background-color:#009591' disabled><i class='glyphicon glyphicon-minus-sign'></i> Disable</button>");
out.write("<a><button class='btn btn-sm btn-primary'  style='background-color:#009591' id='btnDeleteUser'><i class='glyphicon glyphicon-remove'></i> Delete</button>");

}

//out.write("<a><button class='btn btn-sm btn-primary' id='btnLock' style='background-color:#009591'><i class='glyphicon glyphicon-lock'></i> Lock Account</button>");
//out.write("<a><button class='btn btn- sm btn-primary' id='btnUnLock' style='background-color:#009591'><i class='fa fa-unlock'></i> Unlock</button>");
//out.write("<a href='ChangePasswordManual.jsp?userKey="+userKey2+"'><button class='btn btn-sm btn-primary' id='btnPassword' style='background-color:#009591' enable><i class='glyphicon glyphicon-repeat'></i> Manual Password</button>");
out.write("<a data-toggle='modal' data-target= '.passwordPop'><button class='btn btn-sm btn-primary' id='btnPassword' style='background-color:#009591' enable><i class='glyphicon glyphicon-repeat'></i> Manual Password</button>");
out.write("<a><button class='btn btn-sm btn-primary' id ='btnpassreset'  style='background-color:#009591'><i class='glyphicon glyphicon-repeat'> </i> Reset Password</button>");

}else{
if(userState.equalsIgnoreCase("Active"))
        	{
out.write("<a data-toggle='modal' data-target= '.enablePop'><button class='btn btn-sm btn-primary' id='btnEnable' style='background-color:#009591' disabled><i class='glyphicon glyphicon-ok'></i> Enable</button>");

out.write("<a data-toggle='modal' data-target= '.disablePop'><button class='btn btn-sm btn-primary' id='btnDisable' style='background-color:#009591'><i class='glyphicon glyphicon-minus-sign'></i> Disable</button>");
}
else{
out.write("<a data-toggle='modal' data-target= '.enablePop'><button class='btn btn-sm btn-primary' id='btnEnable' style='background-color:#009591'><i class='glyphicon glyphicon-ok'></i> Enable</button>");

out.write("<a data-toggle='modal' data-target= '.disablePop'><button class='btn btn-sm btn-primary' id='btnDisable' style='background-color:#009591' disabled><i class='glyphicon glyphicon-minus-sign'></i> Disable</button>");
}
//out.write("<button class='btn btn-sm btn-primary' id ='btnmodify' style='background-color:#009591' disabled><i class='glyphicon glyphicon-pencil'></i> Modify</button>");
//out.write("<a data-toggle='modal' data-target= '.enablePop'><button class='btn btn-sm btn-primary' id='btnEnable' style='background-color:#009591'><i class='glyphicon glyphicon-ok'></i> Enable</button>");
//out.write("<a data-toggle='modal' data-target= '.disablePop'><button class='btn btn-sm btn-primary' id='btnDisable' style='background-color:#009591'><i class='glyphicon glyphicon-minus-sign'></i> Disable</button>");
//out.write("<button class='btn btn-sm btn-primary' id='btnEnable' style='background-color:#009591' disabled><i class='glyphicon glyphicon-ok'></i> Enable</button>");
//out.write("<button class='btn btn-sm btn-primary' id='btnDisable' style='background-color:#009591' disabled><i class='glyphicon glyphicon-minus-sign'></i> Disable</button>");
//out.write("<a><button class='btn btn-sm btn-primary'  style='background-color:#009591' disabled><i class='glyphicon glyphicon-remove'></i> Delete</button>");
//out.write("<a><button class='btn btn-sm btn-primary' id='btnLock' style='background-color:#009591' disabled><i class='glyphicon glyphicon-lock'></i> Lock Account</button>");
//out.write("<a><button class='btn btn-sm btn-primary' id='btnUnLock' style='background-color:#009591' disabled><i class='fa fa-unlock'></i> Unlock</button>");
//out.write("<a><button class='btn btn-sm btn-primary' id ='btnpassreset'  style='background-color:#009591' disabled><i class='glyphicon glyphicon-repeat'> </i> Reset Password</button>");

}
	 %>


									</div>

								</div>
								<div class="row" id="displaydisableMsg">
									<span id="resultEnable"></span> <span id="resultDisable"></span>
									<span id="resultLock"></span> <span id="resultUnlock"></span>
									 <span
										id="resultModify"></span>
								</div>
								<div class="row">


									<form role="form" action="CreateUser" method="post">
										
										<br>
										<div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="exampleInputEmail1">First Name</label> <input
												type="text" class="form-control" id="userFirstName"
												value="<%=request.getAttribute("userFirstName")%>"
												placeholder="Enter First Name" required="required"
												name="userFirstName" disabled />
										</div>
										<div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="exampleInputEmail1">Line Manager</label> <input
												list="browsers" class="form-control" id="linemanager"
												
												name="userLineManager" placeholder=""
												disabled>

											<datalist id="browsers">
												<%
													OIMUtils oimUtils = new OIMUtils();

													//Map<String,OrganizationObj> lineManagerList = oimUtils.getAllOrganizations (UserSessaion.getOimClient()) ;
													Map<Long, UsersList> lineManagerList = oimUtils.getAllUsers(UserSessaion.getOimClient());

													//Map<String,OrganizationObj> orgList =  oimUtils.getAllOrganizations(UserSessaion.getOimClient());
													for (Map.Entry<Long, UsersList> userListMap : lineManagerList.entrySet()) {
														//out.write("<option value='"+orgList.get(orgListMap.getKey()).getOrganiztionActkey() +"'>"+orgList.get(orgListMap.getValue()).getOrganizationName()+"</option>"); 
														out.write("<option value='" + lineManagerList.get(userListMap.getKey()).getUserLogin() + "'>"
																+ lineManagerList.get(userListMap.getKey()).getUserDisplayName() + " </option>");
													}
												%>


											</datalist>

										</div>
										<div class="clearfix"></div>
										<div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="exampleInputPassword1">Middle Name</label> <input
												type="text" class="form-control" id="middlename"
												placeholder="Middle Name" name="userMiddleName" disabled>
										</div>
									<!-- 	<div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="exampleFormControlSelect1">Select
												Organization</label> <input list="organizations"
												class="form-control" id="userOrganization"
												name="userOrganization" disabled>

											<datalist id="organizations">
												<%
													oimUtils = new OIMUtils();

													Map<String, OrganizationObj> orgList = oimUtils.getAllOrganizations(UserSessaion.getOimClient());
													//Map<String,OrganizationObj> orgList =  oimUtils.getAllOrganizations(UserSessaion.getOimClient());
													for (Map.Entry<String, OrganizationObj> orgListMap : orgList.entrySet()) {
														//out.write("<option value='"+orgList.get(orgListMap.getKey()).getOrganiztionActkey() +"'>"+orgList.get(orgListMap.getValue()).getOrganizationName()+"</option>"); 
														out.write("<option value='" + orgList.get(orgListMap.getKey()).getOrganizationName() + "'/>");
													}
												%>


											</datalist>
										</div> -->
										<div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="exampleInputPassword1">Organization</label> <input
												type="text" class="form-control" id="userOrganization"
												value="<%=request.getAttribute("organization")%>"
												placeholder="Last Name" required="required"
												name="userlastName" disabled />
										</div>
										
										<div class="clearfix"></div>
										<div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="exampleInputPassword1">Last Name</label> <input
												type="text" class="form-control" id="userLastName"
												value="<%=request.getAttribute("userLastName")%>"
												placeholder="Last Name" required="required"
												name="userlastName" disabled />
										</div>
										<!-- <div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="exampleFormControlSelect1">Select Role</label> <select
												class="form-control" id="exampleFormControlSelect1"
												name="userRole" disabled>
												<option>Employee</option>
												<option>Vendor</option>
												<option>Consultant</option>

											</select>
										</div> -->
										<div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="userDisplayName">User Type</label> <input
												type="text" class="form-control" id="userDisplayName"
												value="<%=request.getAttribute("userRole")%>"
												placeholder="Full Name" name="userFullName" disabled>
										</div>
										
										<div class="clearfix"></div>
										<div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="userDisplayName">Full Name</label> <input
												type="text" class="form-control" id="userDisplayName"
												value="<%=request.getAttribute("userDisplayName")%>"
												placeholder="Full Name" name="userFullName" disabled>
										</div>
										<div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="userEmail">Email</label> <input type="text"
												class="form-control" id="userEmail"
												value="<%=request.getAttribute("userEmail")%>"
												placeholder="Enter Email" required="required"
												name="userEmail" disabled>
										</div>
										<div class="clearfix"></div>
										<div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="userLogin">User Login</label> <input type="text"
												class="form-control" id="userLogin"
												value="<%=request.getAttribute("userLogin")%>"
												placeholder="Enter User Login" name="userLogin" value=""
												disabled>
										</div>
										<div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="mobileNumber">Mobile</label> <input type="text"
												class="form-control" id="mobileNumber"
												value="<%=request.getAttribute("mobileNumber")%>"
												placeholder="Enter Mobile Number" name="userMobileNumber"
												disabled>
										</div>
										<div class="clearfix"></div>
									
										<div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="exampleFormControlSelect1">Department</label> <input
												type="text" class="form-control" id="exampleFormControlSelect1"
												value="<%=request.getAttribute("department")%>"
												placeholder="Enter Branch Code" required="required"
												name="userBranchCode" disabled>
										</div>
										
																<div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="personalNumber">Personal Number</label> <input
												type="text" class="form-control" id="personalNumber"
												value="<%=request.getAttribute("personalNumber")%>"
												placeholder="Enter personal number" required="required"
												name="userPersonalNumber" disabled>
										</div>
										<div class="clearfix"></div>
										<div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="exampleInputPassword1">Branch Name</label> <input
												type="text" class="form-control" id="ubranchadd"
												value="<%=request.getAttribute("branchName")%>"
												placeholder="Enter Branch Address" name="branchAddress"
												disabled>
										</div>
										
										<div class="form-group col-xs-10 col-sm-4 col-md-4 col-lg-4">
											<label for="exampleInputPassword1">Branch Code</label> <input
												type="text" class="form-control" id="ubranchcode"
												value="<%=request.getAttribute("branch_Code")%>"
												placeholder="Enter Branch Code" required="required"
												name="userBranchCode" disabled>
										</div>
				
										<div class="clearfix"></div>
										<div class="clearfix"></div>
									<!-- 	<div class="col-xs-10 col-sm-4 col-md-4 col-lg-4">
											 <button type="submit" id ="Submit" class="btn btn-default" style="background-color:#009591">Submit</button> 
											<input type="button" id="submitdata" class="btn btn-default"
												style="background-color: #009591; color: white"
												value="Submit"></input>
											<button type="reset" class="btn btn-default"
												style="background-color: #cc3d3d; color: white">Cancel</button>
										</div>  -->
									</form>

									<div class="clearfix"></div>

									<br /> <br />
								</div>
							</div>
							<!--  CENTER END -->
						</div>
						<!-- START Account Access Details -->
						<!-- TABBED CONTENT -->
						<div class="custom-tabs-line tabs-line-bottom left-aligned">
							<ul class="nav" role="tablist">
								<li class="active"><a href="#tab-bottom-left11" role="tab"
									data-toggle="tab">Applications</a></li>

								<li><a href="#tab-bottom-left12" role="tab"
									data-toggle="tab">Entitlements <!--  span class="badge">7</span--></a></li>
								<li><a href="#tab-bottom-left13" role="tab"
									data-toggle="tab">Roles </a></li>

							</ul>
						</div>
						<div class="tab-content">
							<div class="tab-pane fade in active" id="tab-bottom-left11">
								<div class="table-responsive">
									<table class="table project-table" id="acctTable">
										<thead>
											<tr>
												<th>Application Name</th>
												<th>User ID</th>
												<th>Account ID</th>
												<th>Status</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>

											<%
												OIMUtils oimutils = new OIMUtils();
											//	OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
												OIMUser oimUser = (OIMUser) session.getAttribute("oimUser");
											//	String userLogin = request.getAttribute("userLogin").toString().trim();
												String userKey = oimUtils.getUserKey(userLogin, oimClient);
												String userKeyDetails =(String) request.getAttribute("userKeyDetails");
												Map<Long, AppInstanceObj> acctProviosnedList = oimutils
							 							.fetchProvisionedAccountsOfUser(userKeyDetails.trim().toString(), oimClient);

												for(Map.Entry<Long, AppInstanceObj> accountsMap: acctProviosnedList.entrySet() ) {
												System.out.println("getAppInstanceName:: "+ acctProviosnedList.get(accountsMap.getKey()).getAppInstanceName());
						out.write(" <tr>"
        														+"<td style='display:none;'>"+acctProviosnedList.get(accountsMap.getKey()).getAccountOIUKey() +"</td>"
        																+"<td style='display:none;'>"+acctProviosnedList.get(accountsMap.getKey()).getAppInstanceName() +"</td>"
        												
        														+"<td><a href='#' id='btn"+acctProviosnedList.get(accountsMap.getKey()).getAppInstanceName()+"'name='abcd' class='abcd'>"+ acctProviosnedList.get(accountsMap.getKey()).getAppInstanceDisplayName()+"</a></td>"
        														
        												+ "<td><a href='#'>" + acctProviosnedList.get(accountsMap.getKey()).getAccountName() +" </a></td>"
        												+ "<td><a href='#'>" + acctProviosnedList.get(accountsMap.getKey()).getAccountId() +" </a></td>");
        											
        												if(acctProviosnedList.get(accountsMap.getKey()).getAccountStatus().equals("Disabled"))
													{
														out.write("<td><span class='label label-danger' style='border-radius:3.25em;'>Disabled</span></td>");
													}
													else{
														out.write("<td><span class='label label-success' style='border-radius:3.25em;'>Enabled</span></td>");
														}
		
						//+"<td><button type='button' id='acctDisable' class='acctDisable'> Disable</button></tr>"
						if(acctProviosnedList.get(accountsMap.getKey()).getAppInstanceName().equals("EQGroup") || acctProviosnedList.get(accountsMap.getKey()).getAppInstanceName().equals("Equation")) 
						
						{System.out.println("appName:: "+ acctProviosnedList.get(accountsMap.getKey()).getAppInstanceName());
						 out.write("<td style='display:none;'><ul class='list-inline'>");
						if(acctProviosnedList.get(accountsMap.getKey()).getAccountStatus().equals("Disabled"))
						{
							out.write("<li ><a href='"+acctProviosnedList.get(accountsMap.getKey()).getAccountOIUKey() +"' data-toggle='modal' data-placement='top' title='Enable' id='acctEnable' name='acctEnable' class='acctEnable' data-target='#MyPopup'><span class='label label-success'><i class='fa fa-check'></i></span></a></li>");
						}else
							out.write("<li><a href='"+acctProviosnedList.get(accountsMap.getKey()).getAccountOIUKey() +"' data-toggle='modal' data-placement='top' title='Disable'  id='acctDisable' name='acctDisable' class='acctDisable' data-target='#MyPopup'><span class='label label-danger'><i class='fa fa-ban'></i></span></a></li>");
						}
						else{
						 out.write("<td><ul class='list-inline'>");
						if(acctProviosnedList.get(accountsMap.getKey()).getAccountStatus().equals("Disabled"))
						{
							out.write("<li ><a href='"+acctProviosnedList.get(accountsMap.getKey()).getAccountOIUKey() +"' data-toggle='modal' data-placement='top' title='Enable' id='acctEnable' name='acctEnable' class='acctEnable' data-target='#MyPopup'><span class='label label-success'><i class='fa fa-check'></i></span></a></li>");
						}else
							out.write("<li><a href='"+acctProviosnedList.get(accountsMap.getKey()).getAccountOIUKey() +"' data-toggle='modal' data-placement='top' title='Disable'  id='acctDisable' name='acctDisable' class='acctDisable' data-target='#MyPopup'><span class='label label-danger'><i class='fa fa-ban'></i></span></a></li>");
						
						}
				}									
					%>



										</tbody>
									</table>
								</div>
								<!--  Account Details Form START -->
								<!--  AS400 and Equation merged Form Start -->
								<div class="container-fluid" style="display: none" id="divAS400">
									<div class="form-row">
										<div class="col-md-4">
											<h4 style="font-size: 24px;">Account Details AS400</h4>
										</div>
										<div class="col-md-6"></div>
										<div class="col-md-2">
											<button id="submitAS400"class="btn hblbtn">Update</button>
										</div>
									</div>

									<div class="clearfix"></div>
									<br />

									<div class="panel panel-headline">
										<div class="panel-body">
											<h4>Details</h4>
											<br />
											<form>

													<div class="form-row">
										<!-- 			<div class="form-group col-md-4">
														<label for="User_IdAS">User ID</label> <input type="text"
															class="form-control" id="User_IdAS" name="User_IdAS" placeholder="Enter Here" Disabled>
													</div>
												<div class="form-group col-md-4">
														<label for="Group_ProfileAS">Group Profile</label> 
															<input list="Group_ProfileAS1" name="Group_ProfileAS" id="Group_ProfileAS" class="form-control" placeholder="Enter Here">
												<datalist id="Group_ProfileAS1">
<% 							
								Map<String, String> lookupValues11 = pa.getLookupMap("Lookup.AS400.Groups", oimClient);
			
			for(Map.Entry<String, String> lookupEntrySet: lookupValues11.entrySet() )
			{
				out.write("<option value='"+lookupEntrySet.getValue().trim()+"' label='"+lookupEntrySet.getKey().trim()+"'>"+lookupEntrySet.getKey().trim()+"</option>"); 
				
			}
  		%>	
</datalist>
												</div>  -->
										 
								<!-- 				<div class="form-group col-md-4">
														<label for="Account_NameAS">Account Name</label> <input
															type="text" class="form-control" id="Account_NameAS" name="Account_NameAS"
															placeholder="Enter Here" Disabled>
													</div>      -->
													
												</div>
									 			<div class="clearfix"></div>
											<!-- 	<div class="form-row">

										  		<div class="form-group col-md-4">
														<label for="Group_ProfileAS">Initial Menu</label> 
															<input list="Initial_MenuAS1" name="Initial_MenuAS" id="Initial_MenuAS" class="form-control" placeholder="Enter Here">
												<datalist id="Initial_MenuAS1">
<% 							
								Map<String, String> lookupValues13 = pa.getLookupMap("Lookup.AS400.InitialMenu", oimClient);
			
			for(Map.Entry<String, String> lookupEntrySet: lookupValues13.entrySet() )
			{
				out.write("<option value='"+lookupEntrySet.getKey().trim()+"' label='"+lookupEntrySet.getValue().trim()+"'>"+lookupEntrySet.getValue().trim()+"</option>"); 
				
			}
  		%>	
</datalist>
												</div>      
												
										 		<div class="form-group col-md-4">
														<label for="Password">Password</label> <input
															type="password" class="form-control" id="Password"
															placeholder="Enter Here" Disabled>
													</div>  
													
													<div class="form-group col-md-4">
														<label for="Job_DescriptionAS">Job Description</label> <input
															type="text" class="form-control" id="Job_DescriptionAS" name="Job_DescriptionAS"
															placeholder="Enter Here" Disabled>
													</div> 
												</div>  

												<div class="clearfix"></div> -->

										<!--  		<div class="form-row">

													<div class="form-group col-md-4">
														<label for="OwnerAS">Owner</label> <input type="text"
															class="form-control" id="OwnerAS"  name="OwnerAS" placeholder="Enter Here" Disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="Limit_CapabilitiesAS">Limit Capabilities</label>
														<input type="text" class="form-control"
															id="Limit_CapabilitiesAS" name ="Limit_CapabilitiesAS" placeholder="Enter Here">
													</div>
													<div class="form-group col-md-4">
														<label for="Description_TextAS">Description Text</label> <input
															type="text" class="form-control" id="Description_TextAS" name="Description_TextAS"
															placeholder="Enter Here" Disabled>
													</div>
												</div>
												<div class="clearfix"></div>  -->
												<div class="form-row">
													<div class="form-group col-md-4">
														<label for="First_NameAS">First Name</label> <input
															type="text" class="form-control" id="First_NameAS" name="First_NameAS"
															placeholder="Enter Here" Disabled>
													</div>
													
													<div class="form-group col-md-4">
														<label for="Last_NameAS">Last Name</label> <input type="text"
															class="form-control" id="Last_NameAS" name="Last_NameAS"
															placeholder="Enter Here" Disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="User_IdAS">User ID</label> <input type="text"
															class="form-control" id="User_IdAS" name="User_IdAS" placeholder="Enter Here" Disabled>
													</div>
											<!-- 		<div class="form-group col-md-4">
														<label for="Group_ProfileAS">Initial Program</label> 
															<input list="Initial_ProgramAS1" name="Initial_ProgramAS" id="Initial_ProgramAS" class="form-control" placeholder="Enter Here">
												<datalist id="Initial_ProgramAS1">
<% 							
								Map<String, String> lookupValues12 = pa.getLookupMap("Lookup.AS400.InitialProgram", oimClient);
			
			for(Map.Entry<String, String> lookupEntrySet: lookupValues12.entrySet() )
			{
				out.write("<option value='"+lookupEntrySet.getKey().trim()+"' label='"+lookupEntrySet.getValue().trim()+"'>"+lookupEntrySet.getValue().trim()+"</option>"); 
				
			}
  		%>	
</datalist>
												</div>    -->

													
												</div>
												<div class="clearfix"></div>

												<div class="form-row">

												<!--	<div class="form-group col-md-4">
														<label for="User_ClassAS">User Class</label> <input
															type="text" class="form-control" id="User_ClassAS" name="User_ClassAS"
															placeholder="Enter Here">
													</div>
													  <div class="form-group col-md-4">
														<label for="ServiceAccount">Service Account</label> <input
															type="checkbox" id="ServiceAccount">
													</div>
													<div class="form-group col-md-4">
														<label for="PasswordExpries">Password Expires</label> <input
															type="checkbox" id="PasswordExpries">

													</div>-->
												</div>
												<div class="clearfix"></div>
												
												<div class="form-row">
										<div class="col-md-4">
											<h4 style="font-size: 24px;">AS400 Child Detail</h4>
										</div>
										<div class="col-md-6"></div>
										<div class="col-md-2">
											<input type ="button" id ="submitAS400Child" class="btn hblbtn" value = "Update"></input>
										</div>
									</div>

									<div class="clearfix"></div>
												
												
												<span id=AS400Tables></span>
											<!--	<div class="custom-tabs-line tabs-line-bottom left-aligned">
													
													
													<ul class="nav" role="tablist">

														<li><a href="#tab-bottom-left5" role="tab"
															data-toggle="tab">AS4001</a></li>
														<li><a href="#tab-bottom-left6" role="tab"
															data-toggle="tab">AS4002</a></li>

													</ul>
												</div>
												<div class="tab-content">

													<div class="tab-pane active" id="tab-bottom-left5">

														TAB 1 AS400</div>
													<div class="tab-pane " id="tab-bottom-left6">TAB 2
														AS4002</div>


												</div>-->
												<div class="clearfix"></div>


											</form>

										</div>
									</div>
									</div>


									<!--  Eqaution Form Start  -->
									<div class="container-fluid" style="display: none" id="divEQUATION">
									<div class="form-row">
										<div class="col-md-4">
											<h4 style="font-size: 24px;">Account Details EQUATION</h4>
										</div>
										<div class="col-md-6"></div>
										<div class="col-md-2">
											<button id="submitEQUATION"class="btn hblbtn">Update</button>
										</div>
									</div>

									<div class="clearfix"></div>
									<br />

									<div class="panel panel-headline">
										<div class="panel-body">
											<h4>Details</h4>
											<br />
											<form>

												<div class="form-row">
													<div class="form-group col-md-4">
														<label for="User_IDEQ">User ID</label> <input type="text"
															class="form-control" id="User_IDEQ"
															placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="Branch_NumberEQ">Branch Number</label> <input
															type="text" class="form-control" id="Branch_NumberEQ"
															placeholder="Enter Here" disabled>
													</div>
															<div class="form-group col-md-4">
														<label for="User_ClassEQ">User Class</label> 
															<input list="User_ClassEQ1" name="User_ClassEQ" id="User_ClassEQ" class="form-control" placeholder="Enter Here">
												<datalist id="User_ClassEQ1">
<% 							
								Map<String, String> lookupValues14 = pa.getLookupMap("Lookup.Equation.UserClass", oimClient);
			
			for(Map.Entry<String, String> lookupEntrySet: lookupValues14.entrySet() )
			{
				out.write("<option value='"+lookupEntrySet.getValue().trim()+"' label='"+lookupEntrySet.getValue().trim()+"'>"+lookupEntrySet.getValue().trim()+"</option>"); 
				
			}
  		%>	
</datalist>
												</div>
			
										<!--  			<div class="form-group col-md-4">
														<label for="CAS_User_IDEQ">CAS User ID</label> <input
															type="text" class="form-control" id="CAS_User_IDEQ" name="CAS_User_IDEQ"
															placeholder="Enter Here" disabled>
													</div>  -->
												</div>
												<div class="clearfix"></div>
			<!--  									<div class="form-row">

													<div class="form-group col-md-4">
														<label for="LanguageEQ">Language</label> <input type="text" class="form-control"
															id="LanguageEQ" placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="Show_Lcl_Time_On_EnquiryEQ">Show Lcl Time On Enquiry</label> <input type="text"
															class="form-control" id="Show_Lcl_Time_On_EnquiryEQ" name="Show_Lcl_Time_On_EnquiryEQ"
															placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="System_Option_Auth_ClassEQ">System Option Auth Class</label> <input type="text"
															id="System_Option_Auth_ClassEQ" class="form-control" disabled>
												
													</div>
												</div>
												<div class="clearfix"></div>
												<div class="form-row">

													
													<div class="form-group col-md-4">
														<label for="User_ClassEQ">User Class</label> 
															<input list="User_ClassEQ1" name="User_ClassEQ" id="User_ClassEQ" class="form-control" placeholder="Enter Here">
												<datalist id="User_ClassEQ1">
<% 							
								Map<String, String> lookupValues115 = pa.getLookupMap("Lookup.Equation.UserClass", oimClient);
			
			for(Map.Entry<String, String> lookupEntrySet: lookupValues14.entrySet() )
			{
				out.write("<option value='"+lookupEntrySet.getValue().trim()+"' label='"+lookupEntrySet.getValue().trim()+"'>"+lookupEntrySet.getValue().trim()+"</option>"); 
				
			}
  		%>	
</datalist>
												</div>
													<div class="form-group col-md-4">
														<label for="Prime_Menu_IdEQ">Prime Menu Id</label> <input
															type="text" class="form-control" id="Prime_Menu_IdEQ"
															placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for='Branch_MnemonicEQ'>Branch Mnemonic Number</label>
														 <input type='text' class='form-control'
																id='Branch_MnemonicEQ' name='Branch_MnemonicEQ' disabled>
													</div>
												</div>
												<div class="clearfix"></div>
												<div class="form-row">

												 
													<div class="form-group col-md-4">
														<label for="iSeries_OS_User_IdEQ">iSeries OS User Id</label> <input
															type="text" class="form-control" id="iSeries_OS_User_IdEQ" name="iSeries_OS_User_IdEQ"
															placeholder="Enter Here" disabled>
													</div>
												 
													<div class="form-group col-md-4">
														<label for="UserNameEQ">UserName</label> <input type="text"
															class="form-control" id="UserNameEQ" name="UserNameEQ" placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="Auth_Any_BranchEQ">Auth Any Branch</label> <input
															type="text" class="form-control" id="Auth_Any_BranchEQ" name="Auth_Any_BranchEQ"
															placeholder="Enter Here" disabled>
													</div>
												</div>
												<div class="clearfix"></div>
												<div class="form-row">

													<div class="form-group col-md-4">
														<label for="Character_General_Text_DisplayEQ">Character General Text Display</label>
														 <input type="text" id="Character_General_Text_DisplayEQ"
															class="form-control" disabled>
															
													</div>
													<div class="form-group col-md-4">
														<label for="DateEQ">Date</label> <input
															type="text" class="form-control" id="DateEQ" name="DateEQ"
															placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="System_Option_Auth_LevelEQ">System Option Auth Level</label> <input
															type="text" class="form-control" id="System_Option_Auth_LevelEQ"
															placeholder="Enter Here" disabled>
													</div>
												</div>
												<div class="clearfix"></div>
												<div class="form-row">

													<div class="form-group col-md-4">
														<label for="ExtensionEQ">Extension</label> <input type="text"
															class="form-control" id="ExtensionEQ" name="ExtensionEQ"
															placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="Unique_IDEQ">Unique ID</label> <input
															type="text" class="form-control" id="Unique_IDEQ"
															placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="Availability_CodeEQ">Availability Code</label>
														 <input type="text" class="form-control" id="Availability_CodeEQ"
															placeholder="Enter Here" disabled>
														 
													</div>
												</div>

												<div class="clearfix"></div>
												<div class="form-row">

													<div class="form-group col-md-4">
														<label for="Limit_Violation_AuthorityEQ">Limit Violation Authority</label> <input
															type="text" class="form-control" id="Limit_Violation_AuthorityEQ"
															placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="Phone_NumberEQ">Phone Number</label> <input
															type="text" class="form-control" id="Phone_NumberEQ"
															placeholder="Enter Here" disabled>
													</div>
													</div>
												<div class="clearfix"></div>
												<div class="clearfix"></div>
												-->
												<div class="form-row">
										<div class="col-md-4">
											<h4 style="font-size: 24px;">Equation Child Detail</h4>
										</div>
										<div class="col-md-6"></div>
										<div class="col-md-2">
											<input type ="button" id ="submitEQUATIONChild" class="btn hblbtn" value = "Update"></input>
										</div>
									</div>

									<div class="clearfix"></div>
												

														<span id=EQUATIONTables></span>
													
									<div class="clearfix"></div>
						



											</form>

										</div>
									</div> 
								
									</div>
								<!--  Equation Form End-->




									<!--  Eqaution Group Form Start  -->


									<div class="container-fluid" style="display: none" id="equationGrpForm">
									<div class="form-row">
										<div class="col-md-4">
											<h4 style="font-size: 24px;">Account Details EQUATION Group</h4>
										</div>
										<div class="col-md-6"></div>
										<div class="col-md-2">
											<button id="submitEQUATIONGroup"class="btn hblbtn">Update</button>
										</div>
									</div>

									<div class="clearfix"></div>
									<br />

									<div class="panel panel-headline">
										<div class="panel-body">
											<h4>Details</h4>
											<br />
											<form>

												<div class="form-row">
													<!-- <div class="form-group col-md-4">
														<label for="User_GroupEQG">User Group</label> <input type="text"
															class="form-control" id="User_GroupEQG" name="User_GroupEQG"
															placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="Group_TypeEQG">Group Type</label> <input
															type="text" class="form-control" id="Group_TypeEQG" name="Group_TypeEQG"
															placeholder="Enter Here">
													</div> -->
													<div class="form-group col-md-4">
														<label for="LimitEQG">Limit</label> <input
															type="text" class="form-control" id="LimitEQG" name="LimitEQG"
															placeholder="Enter Here"> 
													</div>
														<div class="form-group col-md-4">
														<label for="JustificationEQG">Justification</label> <input
															type="text" class="form-control" id="JustificationEQG"
															placeholder="Enter Here">
													</div>
													
												</div>
												<!-- <div class="clearfix"></div>
												<div class="form-row">

													<div class="form-group col-md-4">
														<label for="Auth_Amount_for_Inter_Branch_DebitEQG">Auth Amount for Inter Branch Debit</label> <input type="text" class="form-control"
															id="Auth_Amount_for_Inter_Branch_DebitEQG" placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="Group_DescriptionEQG">Group Description</label> <input type="text"
															class="form-control" id="Group_DescriptionEQG" name="Group_DescriptionEQG"
															placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="Auth_Amount_for_Inter_Branch_CreditEQG">Auth Amount for Inter Branch Credit</label> <input type="text"
															id="Auth_Amount_for_Inter_Branch_CreditEQG" class="form-control" disabled>
												
													</div>
												</div> -->
												<!-- <div class="clearfix"></div>
												<div class="form-row">

													
													<div class="form-group col-md-4">
														<label for="Inter_Branch_CreditEQG">Inter Branch Credit</label> 
															<input type="text" name="Inter_Branch_CreditEQG " id="Inter_Branch_CreditEQG " class="form-control" placeholder="Enter Here" disabled>
			
												</div>
													<div class="form-group col-md-4">
														<label for="Auth_Amount_for_Local_DebitEQG">Auth Amount for Local Debit</label> <input
															type="text" class="form-control" id="Auth_Amount_for_Local_DebitEQG"
															placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for='User_TypeEQG'>User Type</label>
														 <input type='text' class='form-control'
																id='User_TypeEQG' name='User_TypeEQG' disabled>
													</div>
												</div> -->
												<!-- <div class="clearfix"></div>
												<div class="form-row">

												 
													<div class="form-group col-md-4">
														<label for="Specified_BranchesEQG">Specified Branches</label> <input
															type="text" class="form-control" id="Specified_BranchesEQG" name="Specified_BranchesEQG"
															placeholder="Enter Here" disabled>
													</div>
												 
													<div class="form-group col-md-4">
														<label for="Auth_Specified_BranchEQG">Auth Specified Branch</label> <input type="text"
															class="form-control" id="Auth_Specified_BranchEQG" name="Auth_Specified_BranchEQG" placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="Limit_for_Local_DebitEQG">Limit for Local Debit</label> <input
															type="text" class="form-control" id="Limit_for_Local_DebitEQG" name="Limit_for_Local_DebitEQG"
															placeholder="Enter Here" disabled>
													</div>
												</div> -->
												<!-- <div class="clearfix"></div>
												<div class="form-row">

													<div class="form-group col-md-4">
														<label for="Limit_for_Local_CrdeitEQG">Limit for Local Debit</label>
														 <input type="text" id="Limit for Local Crdeit"
															class="form-control" disabled>
															
													</div>
													<div class="form-group col-md-4">
														<label for="Limit_for_Local_CrdeitEQG">Limit for Local Crdeit</label> <input
															type="text" class="form-control" id="Limit_for_Local_CrdeitEQG" name="Limit_for_Local_CrdeitEQG"
															placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="Auth_All_BranchesEQG">Auth All Branches</label> <input
															type="text" class="form-control" id="Auth_All_BranchesEQG"
															placeholder="Enter Here" disabled>
													</div>
												</div> -->
												<!-- <div class="clearfix"></div>
												<div class="form-row">

													<div class="form-group col-md-4">
														<label for="Auth_Amount_for_Local_CreditEQG">Auth Amount for Local Credit</label> <input type="text"
															class="form-control" id="Auth_Amount_for_Local_CreditEQG" name="Auth_Amount_for_Local_CreditEQG"
															placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="Date_Last_MaintainedEQG">Date Last Maintained</label> <input
															type="text" class="form-control" id="Date_Last_MaintainedEQG"
															placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="Unique_IDEQG">Unique ID</label>
														 <input type="text" class="form-control" id="Unique_IDEQG"
															placeholder="Enter Here" disabled>
														 
													</div>
												</div> -->

												<!-- <div class="clearfix"></div>
												<div class="form-row">

													 <div class="form-group col-md-4">
														<label for="All_BranchesEQG">All Branches</label> <input
															type="text" class="form-control" id="All_BranchesEQG"
															placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="JustificationEQG">Justification</label> <input
															type="text" class="form-control" id="JustificationEQG"
															placeholder="Enter Here" disabled>
													</div>
													<div class="form-group col-md-4">
														<label for="Inter_Branch_DebitEQG">Inter Branch Debit</label> <input
															type="text" class="form-control" id="Inter_Branch_DebitEQG"
															placeholder="Enter Here" disabled>
													</div>
													</div> -->
													
												<div class="clearfix"></div>
												<div class="clearfix"></div>
												
												<div class="form-row">
										<div class="col-md-4">
											<h4 style="font-size: 24px;">Equation Group Detail</h4>
										</div>
										<div class="col-md-6"></div>
										<div class="col-md-2">
											<input type ="button" id ="submitEQUATIONGroupChild" class="btn hblbtn" value = "Update"></input>
										</div>
									</div>

									<div class="clearfix"></div>
												

														<span id=EQUATIONGroupTables></span>
													
									<div class="clearfix"></div>
						



											</form>

										</div>
									</div> 
								
									</div>
								<!--  Equation Group Form END -->



								<!--  Active Directory Form START -->
								<div class="container-fluid" id="adAcctDetailsForm" style="display:none">
									<div class="form-row">
										<div class="col-md-4">
											<h4 style="font-size: 24px;">User Information</h4>
										</div>
										<div class="col-md-6"></div>
										<div class="col-md-2">
											<input type ="button" id ="submitAD" class="btn hblbtn" value = "Update"></input>
										</div>
									</div>

									<div class="clearfix"></div>
									<br />
									<div class="panel panel-headline">
										<div class="panel-body">
											<form>

												<div class="form-row">

										<!-- 			<div class="form-group col-md-4">
														<label for="Unique_IdAD">Unique ID</label> <input type="text"
															class="form-control" id="Unique_IdAD" name="Unique_IdAD"
															placeholder="Enter Here">
													</div>  -->
													<div class="form-group col-md-4">
														<label for="MobileAD">Mobile</label> <input type="text"
															class="form-control" id="MobileAD" name="MobileAD" placeholder="Enter Here">
													</div>
											<!-- 		<div class="form-group col-md-4">
														<label for="Passowrd">Passowrd</label> <input
															type="password" class="form-control" id="Passowrd"
															placeholder="Enter Here">
													</div> -->
													<div class="form-group col-md-4">
														<label for="User_IdAD">User ID</label> <input type="text"
															class="form-control" id="User_IdAD" name="User_IdAD" placeholder="Enter Here">
													</div>
													
													<div class="form-group col-md-4">
														<label for="First_NameAD">First Name</label> <input
															type="text" class="form-control" id="First_NameAD" name="First_NameAD"
															placeholder="Enter Here">
													</div>
													
													
												</div>

												<div class="clearfix"></div>

												<div class="form-row">

													<!-- <div class="form-group col-md-4">
														<label for="PagerAD">Pager</label> <input type="text"
															class="form-control" id="PagerAD" name="PagerAD" placeholder="Enter Here">
													</div> -->
													
													<!-- <div class="form-group col-md-4">
														<label for="FaxAD">Fax</label> <input type="text"
															class="form-control" id="FaxAD" name="FaxAD" placeholder="Enter Here">
													</div> -->
												</div>

												<div class="clearfix"></div>

												<div class="form-row">

													<!-- <div class="form-group col-md-4">
														<label for="User_Principal_NameAD">User Principal Name</label>
														<input type="text" class="form-control"
															id="User_Principal_NameAD" name="User_Principal_NameAD" placeholder="Enter Here">
													</div> -->
													<!-- <div class="form-group col-md-4">
														<label for="IP_PhoneAD">IP Phone</label> <input type="text"
															class="form-control" id="IP_PhoneAD" name="IP_PhoneAD"
															placeholder="Enter Here">
													</div> -->
													
												</div>

												<div class="clearfix"></div>

												<div class="form-row">
													<div class="form-group col-md-4">
														<label for=TitleAD>Title</label> <input 
															type="text" class="form-control" id="TitleAD" name="TitleAD" 
															placeholder="Enter Here">
													</div>
													<div class="form-group col-md-4">
														<label for="Middle_NameAD">Middle Name</label> <input
															type="text" class="form-control" id="Middle_NameAD" name="Middle_NameAD"
															placeholder="Enter Here">
													</div>
													<div class="form-group col-md-4">
														<label for="DepartmentAD">Department</label> <input
															type="text" class="form-control" id="DepartmentAD" name="DepartmentAD"
															placeholder="Enter Here">
													</div>
												</div>

												<div class="clearfix"></div>

												<div class="form-row">

													<div class="form-group col-md-4">
														<label for="Last_NameAD">Last Name</label> <input type="text"
															class="form-control" id="Last_NameAD" name="Last_NameAD"
															placeholder="Enter Here">
													</div>
													<div class="form-group col-md-4">
														<label for="CompanyAD">Company</label> <input type="text"
															class="form-control" id="CompanyAD" name="CompanyAD"
															placeholder="Enter Here">
													</div>
													<div class="form-group col-md-4">
														<label for="Full_NameAD">Full Name</label> <input type="text"
															class="form-control" id="Full_NameAD" name="Full_NameAD"
															placeholder="Enter Here">
													</div>
												</div>

												<div class="clearfix"></div>

												<div class="form-row">
													<div class="form-group col-md-4">
														<label for="Manager_NameAD">Manager Name</label> <input
															type="text" class="form-control" id="Manager_NameAD" name="Manager_NameAD"
															placeholder="Enter Here">
													</div>
													<div class="form-group col-md-4">
														<label for="CountryName">Country Name</label> <input
															type="text" class="form-control" id="CountryName"
															placeholder="Enter Here">
													</div>
													<div class="form-group col-md-4">
														<label for="CityAD">City</label> <input type="text"
															class="form-control" id="CityAD" name="CityAD" placeholder="Enter Here">
													</div>
													<!-- <div class="form-group col-md-4">
														<label for="Common_NameAD">Common Name</label> <input
															type="text" class="form-control" id="Common_NameAD" name="Common_NameAD"
															placeholder="Enter Here">
													</div> -->
													<!-- <div class="form-group col-md-4">
														<label for="OfficeAD">Office</label> <input type="text"
															class="form-control" id="OfficeAD"  name="OfficeAD" placeholder="Enter Here">
													</div> -->
												</div>

												<div class="clearfix"></div>

												<div class="form-row">

													<!-- <div class="form-group col-md-4">
														<label for="Organization_NameAD">Organization Name</label> <input
															type="text" class="form-control" id="Organization_NameAD" name="Organization_NameAD"
															placeholder="Enter Here">
													</div> -->
													<!-- <div class="form-group col-md-4">
														<label for="CountryAD">Country</label> <input type="text"
															class="form-control" id="CountryAD" name="CountryAD"
															placeholder="Enter Here">
													</div> -->
													
												</div>

												<div class="clearfix"></div>

												<div class="form-row">
													<div class="form-group col-md-4">
														<label for="StreetAD">Street</label> <input type="text"
															class="form-control" id="StreetAD" name="StreetAD" placeholder="Enter Here">
													</div>
													<div class="form-group col-md-4">
														<label for="ZipAD">Zip</label> <input type="text"
															class="form-control" id="ZipAD" name="ZipAD" placeholder="Enter Here">
													</div>
													<div class="form-group col-md-4">
														<label for="E_MailAD">E Mail</label> <input type="email"
															class="form-control" id="E_MailAD" name="E_MailAD" placeholder="Enter Here">
													</div>
													<!-- <div class="form-group col-md-4">
														<label for="Home_DirectoryAD">Home Directory</label> <input
															type="text" class="form-control" id="Home_DirectoryAD" name="Home_DirectoryAD"
															placeholder="Enter Here">
													</div> -->

												</div>

												<div class="clearfix"></div>
												<div class="form-row">

													<!-- <div class="form-group col-md-4  ">

														<label for="Home_PhoneAD">Home Phone</label> <input
															type="text" class="form-control" id="Home_PhoneAD" name="Home_PhoneAD"
															placeholder="Enter Here">

													</div> -->

													<!-- <div class="form-group col-md-4">
														<label for="Terminal_Home_DirectoryAD">Terminal Home
															Directory</label> <input type="text" class="form-control"
															id="Terminal_Home_DirectoryAD" name="Terminal_Home_DirectoryAD" placeholder="Enter Here">
													</div> -->

													
												</div>
												<div class="clearfix"></div>
												<div class="form-row">



													<!-- <div class="form-group col-md-4">
														<label for="Terminal_Allow_LoginAD">Terminal Allow
															Login</label> <input type="text" class="form-control"
															id="Terminal_Allow_LoginAD" name="Terminal_Allow_LoginAD" placeholder="Enter Here">
													</div> -->

													<!-- <div class="form-group col-md-4">
														<label for="Telephone_NumberAD">Telephone Number</label> <input
															type="text" class="form-control" id="Telephone_NumberAD" name="Telephone_NumberAD"
															placeholder="Enter Here">
													</div> -->

													<!-- <div class="form-group col-md-4">
														<label for="Terminal_Profile_PathAD">Terminal Profile
															Path</label> <input type="text" class="form-control"
															id="Terminal_Profile_PathAD" name="Terminal_Profile_PathAD" placeholder="Enter Here">
													</div> -->
												</div>

												<div class="clearfix"></div>

												<div class="form-row">


													<!-- <div class="form-group col-md-4">
														<label for="Account_Expiration_DateAD">Account
															Expiration Date</label> <input type="date" class="form-control"
															id="Account_Expiration_DateAD" name="Account_Expiration_DateAD">
													</div> -->

													<!-- <div class="form-group col-md-4">
														<label for="RedirectionMailID">Redirection Mail ID</label>
														<input type="text" class="form-control"
															id="RedirectionMailID" placeholder="Enter Here">
													</div> -->

													

												</div>


												<div class="clearfix"></div>
												<div class="form-row">


													<div class="form-group col-md-4">
														<label for="ExtensionNumber">Extension Number</label> <input
															type="text" class="form-control" id="ExtensionNumber"
															placeholder="Enter Here">
													</div>

													<!-- <div class="form-group col-md-4">
														<label for="Post_Office_BoxAD">Post Office Box</label> <input
															type="text" class="form-control" id="Post_Office_BoxAD" name="Post_Office_BoxAD"
															placeholder="Enter Here">
													</div> -->

													<div class="form-group col-md-4">
														<label for="EmployeeNumber">Employee Number</label> <input
															type="text" class="form-control" id="EmployeeNumber"
															placeholder="Enter Here">
													</div>
												</div>

												<div class="clearfix"></div>

												<div class="form-row">


													<!-- <div class="form-group col-md-4">
														<label for="StateAD">State</label> <input type="text"
															class="form-control" id="StateAD" name="StateAD" placeholder="Enter Here">

													</div> -->

													<!-- <div class="form-group col-md-4">
														<label for="CountryCode">Country Code</label> <input
															type="text" class="form-control" id="CountryCode"
															placeholder="Enter Here">
													</div> -->

													<!-- <div class="form-group col-md-4">
														<label for="AccountIsLockedOut">Account Is Locked
															Out</label> <input type="checkbox" id="AccountIsLockedOut">

													</div> -->

												</div>
												<div class="clearfix"></div>


												<div class="form-row">


													<!-- <div class="form-group col-md-4">
														<label for="PasswordNeverExpries">Password Never
															Expires</label> <input type="checkbox" id="PasswordNeverExpries">

													</div>

													<div class="form-group col-md-4">

														<label for="UserMustChangePasswordAtNextLogon">User
															Must Change Password At Next Logon</label> <input type="checkbox"
															id="UserMustChangePasswordAtNextLogon">

													</div> -->

													<!-- <div class="form-group col-md-4">
														<label for="ServiceAccount">Service Account</label> <input
															type="checkbox" id="ServiceAccountAD">
													</div> -->

												</div>

												<div class="clearfix"></div>

												<div class="form-row">


													<!-- <div class="form-group col-md-4">
														<label for="PasswordNotRequired">Password Not
															Required</label> <input type="checkbox" id="PasswordNotRequired">

													</div> -->
												</div>

										<div class="clearfix"></div>
														<div class="form-row">
										<div class="col-md-4">
											<h4 style="font-size: 24px;">Gropus</h4>
										</div>
										<div class="col-md-6"></div>
										<div class="col-md-2">
									<!-- 		<input type ="button" id ="submitADGroup" class="btn hblbtn" value = "Update"></input> -->
										</div>
									</div>
										
												<div class="clearfix"></div>

												<div class="custom-tabs-line tabs-line-bottom left-aligned">
													<ul class="nav" role="tablist">

														<li><a href="#tab-bottom-left17" role="tab"
															data-toggle="tab">Active Directory Groups</a></li>
														<li><a href="#tab-bottom-left18" role="tab"
															data-toggle="tab">TAB 2</a></li>
													</ul>
												</div>
												
												
												
												
												<div class="tab-content">

												<div class="tab-pane fade" id="tab-bottom-left17">
												
												<div class="form-row">
															<div class='col-md-1 rt' style='border-left:3px solid;width:3%;color:#009591'><a data-toggle='modal' data-target= '.adADGroup'><i class='glyphicon glyphicon-plus'></i></a></div>
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
														
								<div class="table-responsive">
									<table class="table project-table" id="ADGroups">
										<thead>
											<tr>
												<th>Group Name</th>
												<th>Application</th>
												<th>Status</th>
												
											</tr>
										</thead>
										<tbody>


											<%
												Map<Long, EntitlementObj> entProviosnedListAD = oimutils
														.getUserEntitlementInstances(userKey2.toString().trim(), oimClient);

												 for(Map.Entry<Long, EntitlementObj> entsMap: entProviosnedListAD.entrySet() ) {
												 if(!entProviosnedListAD.get(entsMap.getKey()).getFormName().equals("UD_ADUSRC"))
												 continue;
						out.write(" <tr>"
						+"<td style='display:none;'>"+entProviosnedListAD.get(entsMap.getKey()).getEntitlementKey()+"</td>"
        				+"<td><a href='#'>"+ entProviosnedListAD.get(entsMap.getKey()).getEntitlementName()+"</a></td>"
        				+ "<td><a href='#'>" + entProviosnedListAD.get(entsMap.getKey()).getAppInstanceName() +" </a></td>" 
        			//	+"<td><span class='label label-success' style='border-radius:3.25em;'>Provisioned</span></td>")
        				+"<td><span class='label label-success' style='border-radius:3.25em;'>"+ entProviosnedListAD.get(entsMap.getKey()).getStatus() +"</span></td>"
        				+"<td><a href='#'><span class='label label-danger'><i class='fa fa-remove'></i></span></a>");
        												
				}
											%>
										</tbody>
									</table>
								</div>
							</div>													<div class="tab-pane fade in" id="tab-bottom-left18">
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
														<div class="col-md-2 panel"
															style="border: 2px solid #009591;">
															<br /> <input type="text" class="form-control" /> <label
																for="City">Group Name</label>

														</div>

													</div>

												</div>




											</form>

										</div>
									</div>
								</div>
								<!--  Active Directory Form END -->
								<!--  Account Details Form END -->


							</div>
							<div class="tab-pane fade in" id="tab-bottom-left13">
								<div class="table-responsive">
									<table class="table project-table">
										<thead>
											<tr>
												<th>Role Name</th>
												<th>Description</th>
												<th>ID</th>

											</tr>
										</thead>
										<tbody>
											<%
												List<RoleUser> rolesList = oimutils.fetchUserRole(userKey, oimClient);

												for (int i = 0; i < rolesList.size(); i++) {
													out.write(" <tr>" + "<tr><td><a href='#'>" + rolesList.get(i).getRoleDisplayName() + "</a></td>"
															+ "<td><a href='#'>" + rolesList.get(i).getRoleDescription() + " </a></td>" + "<td>"
															+ rolesList.get(i).getRoleKey() + "</td>");

												}
											%>



										</tbody>
									</table>
								</div>

							</div>

							<div class="tab-pane fade" id="tab-bottom-left12">
								<div class="table-responsive">
									<table class="table project-table" id="MUentRevoke">
										<thead>
											<tr>
												<th>Entitlement Name</th>
												<th>Account Name</th>
												<th>Status</th>
												
											</tr>
										</thead>
										<tbody>


											<%
												Map<Long, EntitlementObj> entProviosnedList = oimutils
														.getUserEntitlementInstances(userKey.toString().trim(), oimClient);

												 for(Map.Entry<Long, EntitlementObj> entsMap: entProviosnedList.entrySet() ) {
						out.write(" <tr>"
						+"<td style='display:none;'>"+entProviosnedList.get(entsMap.getKey()).getEntitlementKey()+"</td>"
        				+"<td><a href='#'>"+ entProviosnedList.get(entsMap.getKey()).getEntitlementName()+"</a></td>"
        				+ "<td><a href='#'>" + entProviosnedList.get(entsMap.getKey()).getAppInstanceName() +" </a></td>" 
        				+"<td><span class='label label-success' style='border-radius:3.25em;'>Provisioned</span></td>");
        				
        												
				}
											%>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<!-- END TABBED CONTENT -->
						<!--  End Account Access Details -->

					</div>
				</div>

			</div>
			
									<!--  Cash Portal Form START -->
								<div class="container-fluid" id="cpAcctDetailsForm" style="display:none">
									<div class="form-row">
										<div class="col-md-4">
											<h4 style="font-size: 24px;">Account Detail Cash Portal</h4>
										</div>
										<div class="col-md-6"></div>
										<div class="col-md-2">
											<input type ="button" id ="submitCP" class="btn hblbtn" value = "Update"></input>
										</div>
									</div>

									<div class="clearfix"></div>
									<br />
									<div class="panel panel-headline">
										<div class="panel-body">
											<form>

												
												<div class="clearfix"></div>

												<div class="form-row">

													<div class="form-group col-md-4">
														<label for="DepartmentidCP">Department ID</label> 
															<input list="DepartmentidCP1" name="DepartmentidCP" id="DepartmentidCP" class="form-control" placeholder="Enter Here">
												<datalist id="DepartmentidCP1">
<% 							
								Map<String, String> lookupValues1 = pa.getLookupMap("Department ID", oimClient);
			
			for(Map.Entry<String, String> lookupEntrySet: lookupValues1.entrySet() )
			{
				out.write("<option value='"+lookupEntrySet.getKey().trim()+"' label='"+lookupEntrySet.getValue().trim()+"'>"+lookupEntrySet.getValue().trim()+"</option>"); 
				
			}
  		%>	
</datalist>
												</div>
			
													<div class="form-group col-md-4">
														<label for="UsertypeidCP">User Type ID</label> 
														
													<input list="UsertypeidCP1" name="UsertypeidCP" id="UsertypeidCP" class="form-control" placeholder="Enter Here">
												<datalist id="UsertypeidCP1">
<% 							
								Map<String, String> lookupValues2 = pa.getLookupMap("Lookup.CashPortal.UserType", oimClient);
			
			for(Map.Entry<String, String> lookupEntrySet: lookupValues2.entrySet() )
			{
				out.write("<option value='"+lookupEntrySet.getKey().trim()+"' label='"+lookupEntrySet.getValue().trim()+"'>"+lookupEntrySet.getValue().trim()+"</option>"); 
				
			}
  		%>	
</datalist>
												</div>
													
													<div class="form-group col-md-4">
														<label for="RegionidCP">Region ID</label> 
													<!--  X	<input type="text" class="form-control" id="RegionidCP" name="RegionidCP" placeholder="Enter Here">-->
												<input list="RegionidCP1" name="RegionidCP" id="RegionidCP" class="form-control" placeholder="Enter Here">
												<datalist id="RegionidCP1">
<% 							
								Map<String, String> lookupValues3 = pa.getLookupMap("Lookup.CashPortal.Region", oimClient);
			
			for(Map.Entry<String, String> lookupEntrySet: lookupValues3.entrySet() )
			{
				out.write("<option value='"+lookupEntrySet.getKey().trim()+"' label='"+lookupEntrySet.getValue().trim()+"'>"+lookupEntrySet.getValue().trim()+"</option>"); 
				
			}
  		%>	
</datalist>
												</div>


														</div>
                        
											</form>

										</div>
									</div>
								</div>
								<!--  Cash Portal Form END -->
			
			<!-- END MAIN CONTENT -->
		</div>
		
		
						
		<!-- END MAIN -->
		<div class="clearfix"></div>
		<footer>
			<div class="container-fluid">
				<p class="copyright">
					&copy; 2021 <a href="#" target="_blank">HBL Pvt Ltd</a>. All Rights
					Reserved.
				</p>
			</div>
		</footer>
	</div>
	
	
<!-- Enble User PopUp  -->
		<div class="modal fade enablePop" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">Reason For Enable User</h4>
            </div>
            <div class="modal-body">
                        <div class="form-row">
            <div class="col-md-4"><label for="ApplicationName">Enter Reason: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="superUser" placeholder="Reason For Enable" required></div>
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
	<!-- Enable User PopUp  -->
	
	
	
	
<!-- Disable User PopUp  -->
		<div class="modal fade disablePop" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">Reason For Disable User</h4>
            </div>
            <div class="modal-body">
                        <div class="form-row">
            <div class="col-md-4"><label for="ApplicationName">Enter Reason: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="disableUserReason" placeholder="Reason For Disable" required></div>
                        </div>
                    
            
                        <div class="clearfix"></div>
                            
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="disableUser" class="btn btn-primary" style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div>
	<!-- Disable User PopUp END -->
	
	<!-- Manual  Reset Password PopUp  -->
		<div class="modal fade passwordPop" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">Change <%=targetUserLogin %> Password</h4>
            </div>
            <div class="modal-body">
                        <div class="form-row">
            <div class="col-md-4"><label for="ApplicationName">New Password: </label></div>
                        <div class="col-md-4">  <input type="password" class="form-control" id="newPassword" placeholder="New Password" required></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Confirm Password: </label></div>
                        <div class="col-md-4">  <input type="password" class="form-control" id="confirmPassword" placeholder="Confirm Password" required></div>
                        </div>
                    
            
                        <div class="clearfix"></div>
                            
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="changeManualPassword" class="btn btn-primary" style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div>
	<!-- Manual  Reset Password PopUp  END-->
	
	<!-- Disbale/Enable Popup -->
	<div class="modal fade" id="EDModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">Application Management</h4>
            </div>
            <div class="modal-body">
                        <div class="form-row">
            <div class="col-md-4"><label for="ApplicationName">Application Name: </label></div>
                        <div class="col-md-4"> <span class ="AppHead" id = "AppHead" >appName</span>
                          <span  id = "appId" style="display:none">appId</span>
                          <span id = "Appstatus" style="display:none">Appstatus</span>
                          </div>
                        </div>
                        <div class="clearfix"></div>
                 <div class="form-row">
            <div class="col-md-4"><label for="newPassword">Reason: </label></div>
                        <div class="col-md-4"><input type="text" class="form-control" id="EDReason" placeholder="Enter Reason" required></div>
                        </div>
                        <div class="clearfix"></div>
                           <div class="form-row">
          
                        </div>
                        <div class="clearfix"></div>
                            
            </div>
            
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="edApp" class="btn btn-primary" style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div>
<!-- Disable/Enable Popup -->
	
	<!-- ADD AD GROUP  -->
		<div class="modal fade adADGroup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">Request Active Directory Group</h4>
            </div>
            <div class="modal-body">
                      
            <div class="col-md-4"><label for="ApplicationName">Select Group: </label></div>
            
            <div class="col-md-4"> 
                 <input list="ADGroup2" name="ADGroup1" id="ADGroup1">
							<datalist id="ADGroup2">
<% 							
							ProvisioningEntitlements provEntObj = new ProvisioningEntitlements();
													//OIMClient oimClient = (OIMClient) session.getAttribute("oimClient"); 
													//OIMUser oimUser = (OIMUser) session.getAttribute("oimUser");
													Map<Long, EntitlementObj> entsListMap = provEntObj.getAllEntitlements(oimClient);
													 for(Map.Entry<Long, EntitlementObj> entListMap: entsListMap.entrySet() ) {
				out.write("<option value='"+entsListMap.get(entListMap.getKey()).getEntitlementName().trim()+"' label='"+entsListMap.get(entListMap.getKey()).getEntitlementKey().toString()+"'>"+entsListMap.get(entListMap.getKey()).getAppInstanceName().trim()+"</option>"); 
				
			}
  		%>	       
</datalist>

                      <!--   <div class="col-md-4">  <input type="text" class="form-control" id="AdGroup" name="AdGroup" placeholder="Select Group" required></div> -->
                   
                        </div>
                    &nbsp;&nbsp;<input type="button" id="singleGruopAD" class="btn btn-primary" style="Background-color:#009591" value ="ADD"></input>
            
                        <div class="clearfix"></div>
                        
                        <div class="table-responsive">
									<table class="table project-table" id="ReqAdGroup">
										<thead>
											<tr>
												<th>Group Name</th>	
												<th>Group ID</th>	
											</tr>
										</thead>
										<tbody>


						
										</tbody>
									</table>
								</div>
                        
                            
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="addADGroup" class="btn btn-primary" style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div>
	<!-- ADD AD Group  -->



<!-- Table AS400CSP -->
	
	
		<div class="modal fade AS400CSP tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">AS400CSP</h4>
            </div>
            <div class="modal-body">
                        
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Special Authority: </label></div>
                        <div class="col-md-4"> 
                        
                       

						<select name="UserGroup" id="SpecialAuthority">
  								<option value="170~ATMCCU">ATMCCU</option>
  								<option value="170~ATMCCU">171~ATMCCU</option>
  								<option value="170~ATMCCU">172~ATMCCU</option>
  								<option value="170~ATMCCU">173~ATMCCU</option>
						</select>
                         <!-- input type="text" class="form-control" id="UserGroup" placeholder="User Group" required!--></div>
                        
                        
                        
                        </div>
                    
            
                        <div class="clearfix"></div>
                            
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="addRowCSP" class="btn btn-primary"  style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div>
	<!-- Table AS400CSP->

		<!-- table26 Equation -->
	
	
		<div class="modal fade table26 tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">Equation Table26</h4>
            </div>
            <div class="modal-body">
                        <div class="form-row">
            <div class="col-md-4"><label for="ApplicationName">User Branch: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="UserBranch" placeholder="User Branch" value='-' Disabled></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Branch Number: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="BranchNumber" placeholder="Branch Number" value='-' Disabled></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">User Class: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="UserClass" placeholder="User Class" value='-' Disabled></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Language Code: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="LanguageCode" placeholder="Language Code" value='GB' Disabled></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Date Last Maintained: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="DateLastMaintained" placeholder="Date Last Maintained" value='0' Disabled></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">User Group: </label></div>
                        <div class="col-md-4"> 
                        
                       
                       <input list="UserGroup1" name="UserGroup" id="UserGroup">
							<datalist id="UserGroup1">
<% 							
								Map<String, String> lookupValues15 = pa.getLookupMap("Lookup.EQAOB.Groups", oimClient);
			
			for(Map.Entry<String, String> lookupEntrySet: lookupValues15.entrySet() )
			{
				out.write("<option value='"+lookupEntrySet.getKey().trim()+"' label='"+lookupEntrySet.getValue().trim()+"'>"+lookupEntrySet.getValue().trim()+"</option>"); 
				
			}
  		%>	
</datalist>
                        
                        </div>
                    
            
                        <div class="clearfix"></div>
                            
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="addRow26" class="btn btn-primary"  style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div></div>
	<!-- table26 Equation->	
	

<!-- Table Equation Group -->
	
	
		<div class="modal fade addEQUATION_GROUP tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">EQUATION_GROUP</h4>
            </div>
            <div class="modal-body">
                        
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Option ID:</label></div>
                        <div class="col-md-4"> 
                        
                       
             <input list="EQG1" name="EQG" id="EQG">
							<datalist id="EQG1">
<% 							
								Map<String, String> lookupValues16 = pa.getLookupMap("Lookup.EquationGrp.Options", oimClient);
			
			for(Map.Entry<String, String> lookupEntrySet: lookupValues16.entrySet() )
			{
				out.write("<option value='"+lookupEntrySet.getKey().trim()+"' label='"+lookupEntrySet.getValue().trim()+"'>"+lookupEntrySet.getValue().trim()+"</option>"); 
				
			}
  		%>	
</datalist>
                         
                         
                         </div>
                        
                        
                        
                        </div>
                    
            
                        <div class="clearfix"></div>
                            
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="addRowEQGroup" class="btn btn-primary"  style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div>
	<!-- Table Equation Group->
	
	
	
	<!-- END WRAPPER -->
	<!-- Javascript -->
	<script src="assets/vendor/jquery/jquery.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script src="assets/scripts/klorofil-common.js"></script>

</body>

<script>
	function methodJS() {
		//var myclass = Java.type("com.hbl.selfservice.UserOperations");
		//alert(myclass.methodJS(input1));

		alert("Clicked")
	}
</script>

<script>



$(document).ready(function(){
			$('#restSuperPass').click(function(){
					
					var superUser = $('#superUser').val();
					var userLogintoEnable = null;
					userLogintoEnable=$('#userLogin').val();
					if(superUser==null || superUser=='')
					{
					alert("Please enter Reason");
					return;
					}
					
				
					console.log(superUser);
					console.log(userLogintoEnable);
				
				//alert("ajax call");
				$.ajax({
					type: 'POST',
					data:{superUsername:superUser,userLogintoEnable:userLogintoEnable,action:"UserEnable"},
					url: 'ResetSuperUserPass',
					success: function(result){
				
						console.log(result);
						
						$('.enablePop').modal('hide');
						alert(result.ResultID.split(":").pop());
						location.reload();
					
				
					},
                  error: function (error) {
                  alert("Internal Error Occured!");
                   }
				}); 
				
				
				
				 
			});
			
			});
		//



//Disable User///

$(document).ready(function(){
			$('#disableUser').click(function(){
					
					var disableUserReason = $('#disableUserReason').val();
					var userLogintoDisable = null;
					userLogintoDisable=$('#userLogin').val();
					if(disableUserReason ==null || disableUserReason=='')
					{
					alert("Please Enter Reason");
					return;
					}
				
					console.log(disableUserReason);
					console.log(userLogintoDisable);
					
				
				
				//alert("ajax call");
				$.ajax({
					type: 'POST',
					data:{disableUserReason:disableUserReason,userLogintoDisable:userLogintoDisable,action:"UserDisable"},
					url: 'ResetSuperUserPass',
					success: function(result){
				
						console.log(result);
						
						
						alert(result.ResultID.split(":").pop());
						location.reload();
					
				
					},
                  error: function (error) {
                  alert("Internal Error Occured!");
                   }
				}); 
				 
			});
			
			});
			$(document).ready(function(){
			$('#btnDeleteUser').click(function(){
					
					var userLogintoDisable = null;
					userLogintoDelete=$('#userLogin').val();
					
				
					console.log(userLogintoDelete);
					$("body").css("cursor", "wait");
				
				//alert("ajax call");
				$.ajax({
					type: 'POST',
					data:{userLogintoDelete:userLogintoDelete,action:"UserDelete"},
					url: 'ResetSuperUserPass',
					success: function(result){
				
						console.log(result.Result);
						$("body").css("cursor", "default");
						alert("Delete Operation has been "+result.Result);
						location.reload();
					window.location.href = "ManageUsers.jsp";
				
					},
                  error: function (error) {
                  $("body").css("cursor", "default");
                  alert("Internal Error Occured!");
                  location.reload();
                  
                   }
				}); 
				 
			});
			
			});
			
			 $(document).ready(function(){
			$('#changeManualPassword').click(function(){
			console.log("Manual password submit clicked");

				var newPassword = $('#newPassword').val();
				var confirmPassword = $('#confirmPassword').val();
				targetUser=$('#userLogin').val();
				console.log(newPassword+" "+confirmPassword+" "+targetUser)
				if (newPassword !='' && confirmPassword !=''){
					
					console.log(newPassword);
					$("body").css("cursor", "progress");
				$.ajax({
					type: 'POST',
					data:{newPassword:newPassword,confirmPassword:confirmPassword,targetUser:targetUser,action:"manualPassword"},
					url: 'ChangeUserPassword',
					success: function(result){
					console.log(result);
					
					if (result.STATUS=="changed")
					{
					$("body").css("cursor", "default");
					alert("Password has been Changed!");
					document.getElementById('newPassword').value = '';
					document.getElementById('confirmPassword').value = '';
					}
					else if (result.notMatched=="Y")
					alert("New/Confirm Password not Matched!");
					else
					alert(result.STATUS);
					$("body").css("cursor", "default");
					}
				}); 
				
				}else{
				
				alert("Please Enter Valid New/Confirm Passwords!");
				}
				 
			}); 
			
					$("#addRowCSP").click(function() {
              //var table = document.getElementById("table");
              console.log("table clicked")
            console.log("add row clicked");
                 
                 
                 // get input values
               
            	 
                var SpecialAuthority  = document.getElementById('SpecialAuthority').value;
                 		
                  
                  //var table = document.getElementsByTagName('table')[1];
                  var table = document.getElementById('tableCSP');
                  // add new empty row to the table
                  // 0 = in the top 
                  // table.rows.length = the end
                  // table.rows.length/2+1 = the center
                  //var newRow = table.insertRow(table.rows.length/2+1);
                   var newRow = table.insertRow(table.rows.length);
                  //var newRow = table.insertRow(0);
                  // add cells to the row
                  var cel1 = newRow.insertCell(0);
                  var cel2 = newRow.insertCell(1);
                  var cel3 = newRow.insertCell(2);
                  // add values to the cells
                  cel1.innerHTML = SpecialAuthority;
                  cel2.innerHTML = "";
                 // cel3.innerHTML = "<button class='label label-danger'><i class='fa fa-remove'></i></button>";
                   cel3.innerHTML = "";
                  
                  				
                  alert("Row Added");
              
              
              //$('#table tr:last').after('<tr>aa</tr><tr>aa</tr>');
          //    var x=document.getElementById("#table").tBodies[0];  //get the table
      //var node=t.rows[0].cloneNode(true);    //clone the previous node or row
      //x.appendChild(node);   //add the node or row to the t
   			

  	});
  		$("#addRowEQGroup").click(function() {
              //var table = document.getElementById("table");
              console.log("table clicked")
            console.log("add row clicked");
                 
                 
                 // get input values
               
            	  var val = $("#EQG").val();
				
                 var obj = $("#EQG1").find("option[value='" + val + "']");

		if(obj != null && obj.length > 0)
	    	
	    	{
            	 
                var OptionID  = document.getElementById('EQG').value;
                 		
                  
                  //var table = document.getElementsByTagName('table')[1];
                  var table = document.getElementById('tableEQGroup');
                  // add new empty row to the table
                  // 0 = in the top 
                  // table.rows.length = the end
                  // table.rows.length/2+1 = the center
                  //var newRow = table.insertRow(table.rows.length/2+1);
                   var newRow = table.insertRow(table.rows.length);
                  //var newRow = table.insertRow(0);
                  // add cells to the row
                  var cel1 = newRow.insertCell(0);
                  var cel2 = newRow.insertCell(1);
                  var cel3 = newRow.insertCell(2);
                  // add values to the cells
                  cel1.innerHTML = OptionID;
                  cel2.innerHTML = "";
                 // cel3.innerHTML = "<button class='label label-danger'><i class='fa fa-remove'></i></button>";
                   cel3.innerHTML = "";
                  
                  				
                  alert("Row Added");
              }
              
              //$('#table tr:last').after('<tr>aa</tr><tr>aa</tr>');
          //    var x=document.getElementById("#table").tBodies[0];  //get the table
      //var node=t.rows[0].cloneNode(true);    //clone the previous node or row
      //x.appendChild(node);   //add the node or row to the t
   			else{alert("Please enter Correct Option ID");}

  	});
  	
			
			$("#addRow26").click(function() {
              //var table = document.getElementById("table");
              console.log("table clicked")
            console.log("add row clicked");
                 var rows11 = document.getElementById('table26').rows.length;
					
					if(rows11 > 2){
 					  alert("Maximum 1 Row can be added");
 					  return;
						}
                 
                 var val = $("#UserGroup").val();
				console.log("UserGroup11:"+val);
                 var obj = $("#UserGroup1").find("option[value='" + val + "']");

		if(obj != null && obj.length > 0)
	    	
	    	{
	    	
	    	     
                 // get input values
               
              //  var UserBranch =  $('[name="UD_EQUATION_USER_BRANCH"]').val();
		/*		 var UserClass =  $('[name="UD_EQUATION_USER_CLASS"]').val();
				// var BranchNumber =  $('[name="UD_EQUATION_BRANCH_NUMBER"]').val();
				 if(UserClass == "")
				 {
				 UserClass ="ENQ";
				 }
*/				 
                var UserBranch  = document.getElementById('UserBranch').value;
                 var BranchNumber = document.getElementById('BranchNumber').value;
                  var UserClass = document.getElementById('UserClass').value;
                    var LanguageCode = document.getElementById('LanguageCode').value;
                      var DateLastMaintained = document.getElementById('DateLastMaintained').value;
                        var UserGroup = document.getElementById('UserGroup').value;
                  // get the html table
                  // 0 = the first table
                  var rows11 = document.getElementById('table26').rows.length;
					
					if(rows11 > 2){
 					  alert("Maximun 1 Row can be added");
 					  return;
						}
						
                  
                  //var table = document.getElementsByTagName('table')[1];
                  var table = document.getElementById('table26');
                  // add new empty row to the table
                  // 0 = in the top 
                  // table.rows.length = the end
                  // table.rows.length/2+1 = the center
                  //var newRow = table.insertRow(table.rows.length/2+1);
                   var newRow = table.insertRow(table.rows.length);
                  //var newRow = table.insertRow(0);
                  // add cells to the row
                  var cel1 = newRow.insertCell(0);
                  var cel2 = newRow.insertCell(1);
                  var cel3 = newRow.insertCell(2);
                  var cel4 = newRow.insertCell(3);
                  var cel5 = newRow.insertCell(4);
                  var cel6 = newRow.insertCell(5);
                  var cel7 = newRow.insertCell(6);
                  // add values to the cells
                  cel1.innerHTML = UserBranch;
                  cel2.innerHTML = BranchNumber;
                  cel3.innerHTML = UserClass; 
                  cel4.innerHTML = LanguageCode;
                  cel5.innerHTML = DateLastMaintained;
                  cel6.innerHTML = UserGroup;
                //  cel7.innerHTML = "<button class='label label-danger' ><i class='fa fa-remove'></i></button>";
                  
                  				
                  alert("Row Added");
              
              
              //$('#table tr:last').after('<tr>aa</tr><tr>aa</tr>');
          //    var x=document.getElementById("#table").tBodies[0];  //get the table
      //var node=t.rows[0].cloneNode(true);   //clone the previous node or row
      //x.appendChild(node);   //add the node or row to the t
 
	    	  
	    	}
		else
    	alert("Invalid Group Name");
                 
                 
            
 	});
			
			
			 	$("#singleGruopAD").click(function() {
              //var table = document.getElementById("table");
              console.log("table clicked")
            console.log("add row clicked");
                 
                 var rows11 = document.getElementById('ReqAdGroup').rows.length;
					
					console.log("number of Rows"+rows11);
					if(rows11 > 1){
 					  alert("Maximum 1 Row can be added");
 					  return;
						}
                 
                 // get input values
               
            	 var val = $("#ADGroup1").val();
				
 var obj = $("#ADGroup2").find("option[value='" + val + "']");

console.log("val:"+val);
		if(obj != null && obj.length > 0)
	    	
	    	{
            	 
                //var OptionId  = document.getElementById('OptionId').val();
                 var group =  $('[name="ADGroup1"]').val();		
                 var eId = ($('#ADGroup2 [value="' + group+ '"]').attr('label'));
                  
                  console.log("eId:"+eId);
                  //var table = document.getElementsByTagName('table')[1];
                  var table = document.getElementById('ReqAdGroup');
                  // add new empty row to the table
                  // 0 = in the top 
                  // table.rows.length = the end
                  // table.rows.length/2+1 = the center
                  //var newRow = table.insertRow(table.rows.length/2+1);
                   var newRow = table.insertRow(table.rows.length);
                  //var newRow = table.insertRow(0);
                  // add cells to the row
                  var cel1 = newRow.insertCell(0);
                 
                  var cel2 = newRow.insertCell(1);
                   var cel3 = newRow.insertCell(2);
                  // add values to the cells
                  cel1.innerHTML = group;
                  cel2.innerHTML = eId;
                  cel3.innerHTML = "<button class='label label-danger'><i class='fa fa-remove'></i></button>";
                  
                  				
                  alert("Group Added");
              }
              else{
                  alert("Please enter Valid Group");
              }
              //$('#table tr:last').after('<tr>aa</tr><tr>aa</tr>');
          //    var x=document.getElementById("#table").tBodies[0];  //get the table
      //var node=t.rows[0].cloneNode(true);    //clone the previous node or row
      //x.appendChild(node);   //add the node or row to the t
 
 	});
 	
 	$("#ReqAdGroup").on('click', '.label-danger', function () {
    $(this).closest('tr').remove();
});
	$("#tableCSP").on('click', '.label-danger', function () {
    $(this).closest('tr').remove();
});



$('#addADGroup').click(function(){
				console.log("Add AD Group  Called");


var xml="";
var affected_user = $('#userLogin').val();
xml = "<Application name=\"ActiveDirectory\">";
var n1 = document.getElementById("ReqAdGroup").rows.length;
var i=0,j=0;
var str="";
 
 var group="";
 var row="";
 var col="";
 var entName="";
 var entKey="";
 
 

 xml = xml + "<table table_name=\"AddADGroups\">";
 xml = xml + "<userLogin login=\""+affected_user+"\"/>";
 
for(i=1; i<n1;i++){
console.log("i::"+i);
 row =  "<Record id=\""+i+"\">";
 
var n2 = document.getElementById("ReqAdGroup").rows[i].cells.length;
  
for(j=0; j<n2-1;j++){
 
var x=document.getElementById("ReqAdGroup").rows[i].cells.item(j).innerHTML;
var header=document.getElementById("ReqAdGroup").rows[0].cells.item(j).innerHTML;
 col = col + "<"+header+">"+x+"</"+header+">";
 
   // table26[j] = x;
   
 
    str=str+x+":";
 
 
}
group=group+row+col+"</Record>";
row="";
col="";
str=str+"#";

}
xml=xml+group;

 xml = xml + "</table></Application>";

entName=document.getElementById("ReqAdGroup").rows[1].cells.item(0).innerHTML;
entKey=document.getElementById("ReqAdGroup").rows[1].cells.item(1).innerHTML;

console.log(affected_user);





$.ajax({
					type: 'POST',
					data:{entName:entName,entKey:entKey,affected_user:affected_user,action:"AddADGroup",xml:JSON.stringify(xml)},
					url: 'EntitlementRevoke',
					success: function(result){
					//$('#actFromRes').html(result);
					console.log(result);
					alert("Request ID: " +result.ID +" has been created for Active Directoy Group Addition");
					}
				});
				xml = "";				 
			
			
			
			});
			
			
	/////////////////////////////////AS400 Child Data/////////////////////////
	
}); 

	$("#ADGroups").on('click', '.label-danger', function () {
   var tr = $(this).closest('tr');
		    
		    var entKey = tr.find('td:eq(0)').text(); 
		    var entName = tr.find('td:eq(1)').text();
		    var affected_user = $('#userLogin').val();
		 //  console.log(a+" "+b);
		   var c = confirm("Do you want to Remove active directory Group:\r\n"+entName);
                    if(c === true)
                    {
                     
                     
		    $.ajax({
					type: 'POST',
					data:{entName:entName,entKey:entKey,affected_user:affected_user,action:"RemoveADGroup"},
					url: 'EntitlementRevoke',
					success: function(result){
					//$('#actFromRes').html(result);
					console.log(result);
					alert("Request ID: " +result.ID +" has been created for Active Directoy Group Removal");
					}
				});
		    }else{
		    return;
		    }
});	//

/*
 $(document).on("click","[name='tableCSPRemove']", function (e) {
            var index;
            var table = document.getElementById('tableCSP');
            console.log("index:"+index);
            console.log("table:"+table);
           
            for(var i = 1; i < table.rows.length; i++)
            {
            
                table.rows[i].cells[2].onclick = function()
                {
                    var c = confirm("do you want to delete this row");
                    if(c === true)
                    {
                        index = this.parentElement.rowIndex;
                        table.deleteRow(index);
                    }
                    else
                    {
                    return;
                    }
                    //console.log(index);
                };
                
                
            }
    });
*/


		$(document.body).on('change','#Group_TypeEQG',function(){
			
			$('#LimitEQG').val("");
			$('#User_GroupEQG').val("");
			var UD_EQGROUP_GROUP_TYPE =  $('[name="Group_TypeEQG"]').val();
			console.log("Group_TypeEQG:"+UD_EQGROUP_GROUP_TYPE);
			
			if(UD_EQGROUP_GROUP_TYPE == 'Teller' || UD_EQGROUP_GROUP_TYPE == 'Supervisor')
			{
					$('#User_GroupEQG').prop("disabled", false);
        			$('#LimitEQG').prop("disabled", false);
        			
			
					
			alert('Teller/Supervisor Change Happened');
			}
			
			
			
			else if(UD_EQGROUP_GROUP_TYPE == 'Enquiry')
			{
			
					$('#User_GroupEQG').prop("disabled", false);
        			$('#LimitEQG').prop("disabled", true);
	alert('Enquiry Change Happened');
			}
			else
			{
					$('#User_GroupEQG').prop("disabled", true);
        			$('#LimitEQG').prop("disabled", true);
        	
	    			alert("Please Select Correct User Type");    	
			}
    
});
</script>




</html>
