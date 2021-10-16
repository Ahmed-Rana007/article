<%@page import="com.hbl.provisioning.AccountCart"%>
<%@page import="oracle.iam.platform.OIMClient"%>
<%@page import="com.hbl.provisioning.ProvisioningAccounts"%>
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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/gfonts.css">
<!-- ICONS -->
<link rel="apple-touch-icon" sizes="76x76"
	href="${pageContext.request.contextPath}/assets/img/apple-icon.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="${pageContext.request.contextPath}/assets/img/favicon.png">

<style>
		.hblbtn {
			background-color: #009591;
			color: white;
			border: 2px solid #4CAF50;
			transition-duration: 0.3s;
		}

			.hblbtn:hover {
				background-color: white;
				color: #009591;
			}
	</style>
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>

<script>
 
 var application =null;
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
	$(document).ready(function() {
	
	//var xml = "<?xml version=\"1.0\" standalone=\"yes\" ?> <ChildData>";
	var xml="";
	var affected_user="";
	
		$('#submitRequest').click(function() {
			
			
			var TableRow = document.getElementById("cartTable").rows[0];
			var RowCells = TableRow.getElementsByTagName("td");
			var appName = RowCells[1].innerText;
			
		
		if((appName == 'CP1')&&
		((($('[name="UD_CP1USR_REGION_ID"]').val()=='') 	|| ($('[name="UD_CP1USR_REGION_ID"]').val()==null))||
		 (($('[name="UD_CP1USR_DEPARTMENT_ID"]').val()=='') || ($('[name="UD_CP1USR_DEPARTMENT_ID"]').val()==null))||
		 (($('[name="UD_CP1USR_USER_TYPE_ID"]').val()=='')  || ($('[name="UD_CP1USR_USER_TYPE_ID"]').val()==null))))
		{
		console.log("$('[name='UD_CP1USR_REGION_ID']').val():"+$('[name="UD_CP1USR_REGION_ID"]').val());
		console.log("$('[name='UD_CP1USR_DEPARTMENT_ID']').val():"+$('[name="UD_CP1USR_DEPARTMENT_ID"]').val());
		console.log("$('[name='UD_CP1USR_USER_TYPE_ID']').val():"+$('[name="UD_CP1USR_USER_TYPE_ID"]').val());
		alert("Please enter all value in Request Details Cash Portal and click Update");
		return;
		
		}
		if((appName == 'EQGroup')&&
		((($('[name="UD_EQGROUP_GROUP_DESCRIPTION"]').val()=='') 	|| ($('[name="UD_EQGROUP_GROUP_DESCRIPTION"]').val()==null))||
		 (($('[name="UD_EQGROUP_GROUP_TYPE"]').val()=='') 			|| ($('[name="UD_EQGROUP_GROUP_TYPE"]').val()==null))||
		 (($('[name="UD_EQGROUP_USER_GROUP"]').val()=='') 			|| ($('[name="UD_EQGROUP_USER_GROUP"]').val()==null))))
		{
		alert("Please enter all value in Request Details EQ Group and click Update");
		return;
		
		}		
	
			
			 var myRadio = $("input[name=submitDisclaimer]");
			 var checkedValue = myRadio.filter(":checked").val();
			  var accountJustification   = document.getElementById('accountJustification').value;
			  if(accountJustification=null || accountJustification=='')
			  {
			  alert("Please Add Justification");
			  return;
			  }
			
			 accountJustification = document.getElementById('accountJustification').value;
			  

			//alert(checkedValue);
			
			if(checkedValue=="1"){
			
			
			
			 $.ajax({
				type : 'POST',
				data : {
					action : "submitRequest",
					accountJustification:accountJustification
				},
				url : 'AccountProvisioningController',
				success : function(result) {
					//$('#result1').html(result);

					alert(result);
					window.location = 'makerequest.jsp';
				}
			}); 
			}else{
			alert("Please Select Disclaimer Checkbox to Submit Request!");
			}
		});
		$('#btnActiveDirectory').click(function(){
				 //var appInstanceName = $('#btnActiveDirectory').val();
				 let tr = $(this).closest('tr');
			affected_user = tr.find('td:eq(2)').text();
				 $('#divAS400').hide();
				// $('#divAD').show();
				  $('#divEQGRP').hide();
				 $('#divEQ').hide();
				  $('#divCP1').hide();
				   $('#divAD').hide();
				    $('#actFromRes').hide();
				  
				 $( "div.updateButton" ).html( "" );
				 
				 
				 console.log("Active Directory Clicked");
			/*	$.ajax({
					type: 'POST',
					data:{appInstanceName:"ActiveDirectory"},
					url: 'AccountFormController',
					success: function(result){
					$('#actFromRes').html(result);
					console.log(result);
					}
				});*/
				 
			}); 
			$('#btnAS400').click(function(){
				 //var appInstanceName = $('#btnActiveDirectory').val();
				  let tr = $(this).closest('tr');
			affected_user = tr.find('td:eq(2)').text();
				 $('#divAS400').hide();
				 $('#divAD').hide();
				  $('#divEQGRP').hide();
				 $('#divEQ').hide();
				 $('#divCP1').hide();
   				 $('#actFromRes').hide();
				 console.log("AS400 Clicked");
				$.ajax({
					type: 'POST',
					data:{appInstanceName:"AS400"},
					url: 'AccountFormController',
					success: function(result){
					$('#actFromRes').html(result);
					console.log(result);
					}
				});
				 
			});
			$('#btnCP1').click(function(){
				 //var appInstanceName = $('#btnActiveDirectory').val();
				  let tr = $(this).closest('tr');
			affected_user = tr.find('td:eq(2)').text();
	
				  $('#divAS400').hide();
				 $('#divAD').hide();
				  $('#divEQGRP').hide();
				 $('#divEQ').hide();
				 $('#divCP1').show();
				  $('#actFromRes').show();
				 console.log("CP1 Clicked");
				$.ajax({
					type: 'POST',
					data:{appInstanceName:"CP1"},
					url: 'AccountFormController',
					success: function(result){
					$('#actFromRes').html(result);
					console.log(result);
					} 
				});
				 
			});
			$('#btnEquation').click(function(){
			 let tr = $(this).closest('tr');
			affected_user = tr.find('td:eq(2)').text();
				 //var appInstanceName = $('#btnActiveDirectory').val();
				  $('#divAS400').hide();
				 $('#divAD').hide();
				  $('#divEQGRP').hide();
				 $('#divEQ').show();
				 $('#divCP1').hide();
				 $('#actFromRes').show();
				 console.log("Equation Clicked");
				$.ajax({
					type: 'POST',
					data:{appInstanceName:"Equation"},
					url: 'AccountFormController',
					success: function(result){
					 
					$('#actFromRes').html(result);
					console.log(result);
					$('#UD_EQUATION_BRANCH_NUMBER').prop("disabled", true);
        			$('#UD_EQUATION_USER_ID').prop("disabled", true);
        			
        			
					}
				});
				 
			});
			$('#btnEQGroup').click(function(){
			 let tr = $(this).closest('tr');
			affected_user = tr.find('td:eq(2)').text();
				 //var appInstanceName = $('#btnActiveDirectory').val();
				  $('#divAS400').hide();
				 $('#divAD').hide();
				  $('#divEQGRP').show();
				 $('#divEQ').hide();
				 $('#divCP1').hide();
				 $('#actFromRes').show();
				 
				 
				 console.log("EQ Group Clicked");
				$.ajax({
					type: 'POST',
					data:{appInstanceName:"EQGroup"},
					url: 'AccountFormController',
					success: function(result){
					$('#actFromRes').html(result);
					console.log(result);
					
					$('#UD_EQGROUP_AUTH_AMOUNT_FOR_LOC').prop("disabled", true);
        			$('#UD_EQGROUP_INTER_BRANCH_DEBIT').prop("disabled", true);
        			$('#UD_EQGROUP_INTER_BRANCH_CREDIT').prop("disabled", true);
        			$('#UD_EQGROUP_USER_TYPE').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_AMOUNT_FOR_L90').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_SPECIFIED_BRAN').prop("disabled", true);
        			$('#UD_EQGROUP_GROUP_DESCRIPTION').prop("disabled", true);
        			$('#UD_EQGROUP_ALL_BRANCHES').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_AMOUNT_FOR_INT').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_AMOUNT_FOR_I52').prop("disabled", true);
        			$('#UD_EQGROUP_USER_GROUP').prop("disabled", true);
        			$('#UD_EQGROUP_LIMIT_FOR_LOCAL_CRD').prop("disabled", true);
        			$('#UD_EQGROUP_SPECIFIED_BRANCHES').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_ALL_BRANCHES').prop("disabled", true);
        			$('#UD_EQGROUP_JUSTIFICATION').prop("disabled", true);
        			$('#UD_EQGROUP_LIMIT').prop("disabled", true);
        			$('#UD_EQGROUP_DATE_LAST_MAINTAINE').prop("disabled", true);
        			$('#UD_EQGROUP_LIMIT_FOR_LOCAL_DEB').prop("disabled", true);
        			$('#UD_EQGROUP_UNIQUE_ID').prop("disabled", true);
        			$('#UD_EQGROUP_IT_RESOURCE_NAME').prop("disabled", true);
        			    	
					}
				});
				
			});
			
			 
			
			$(document.body).on('change','#UD_EQGROUP_GROUP_TYPE',function(){
			
			$('#UD_EQGROUP_LIMIT').val("");
			$('#UD_EQGROUP_USER_GROUP').val("");
			var UD_EQGROUP_GROUP_TYPE =  $('[name="UD_EQGROUP_GROUP_TYPE"]').val();
			console.log("UD_EQGROUP_GROUP_TYPE:"+UD_EQGROUP_GROUP_TYPE);
			
			if(UD_EQGROUP_GROUP_TYPE == 'Teller' || UD_EQGROUP_GROUP_TYPE == 'Supervisor')
			{
					$('#UD_EQGROUP_AUTH_AMOUNT_FOR_LOC').prop("disabled", true);
        			$('#UD_EQGROUP_INTER_BRANCH_DEBIT').prop("disabled", true);
        			$('#UD_EQGROUP_INTER_BRANCH_CREDIT').prop("disabled", true);
        			$('#UD_EQGROUP_USER_TYPE').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_AMOUNT_FOR_L90').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_SPECIFIED_BRAN').prop("disabled", true);
        			$('#UD_EQGROUP_GROUP_DESCRIPTION').prop("disabled", false);
        			$('#UD_EQGROUP_ALL_BRANCHES').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_AMOUNT_FOR_INT').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_AMOUNT_FOR_I52').prop("disabled", true);
        			$('#UD_EQGROUP_USER_GROUP').prop("disabled", false);
        			$('#UD_EQGROUP_LIMIT_FOR_LOCAL_CRD').prop("disabled", true);
        			$('#UD_EQGROUP_SPECIFIED_BRANCHES').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_ALL_BRANCHES').prop("disabled", true);
        			$('#UD_EQGROUP_JUSTIFICATION').prop("disabled", true);
        			$('#UD_EQGROUP_LIMIT').prop("disabled", false);
        			$('#UD_EQGROUP_DATE_LAST_MAINTAINE').prop("disabled", true);
        			$('#UD_EQGROUP_LIMIT_FOR_LOCAL_DEB').prop("disabled", true);
        			$('#UD_EQGROUP_UNIQUE_ID').prop("disabled", true);
        			$('#UD_EQGROUP_IT_RESOURCE_NAME').prop("disabled", true);    	
			
			
					
		//	alert('Teller/Supervisor Change Happened');
			}
			
			
			
			else if(UD_EQGROUP_GROUP_TYPE == 'Enquiry')
			{
			
					$('#UD_EQGROUP_AUTH_AMOUNT_FOR_LOC').prop("disabled", true);
        			$('#UD_EQGROUP_INTER_BRANCH_DEBIT').prop("disabled", true);
        			$('#UD_EQGROUP_INTER_BRANCH_CREDIT').prop("disabled", true);
        			$('#UD_EQGROUP_USER_TYPE').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_AMOUNT_FOR_L90').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_SPECIFIED_BRAN').prop("disabled", true);
        			$('#UD_EQGROUP_GROUP_DESCRIPTION').prop("disabled", false);
        			$('#UD_EQGROUP_ALL_BRANCHES').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_AMOUNT_FOR_INT').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_AMOUNT_FOR_I52').prop("disabled", true);
        			$('#UD_EQGROUP_USER_GROUP').prop("disabled", false);
        			$('#UD_EQGROUP_LIMIT_FOR_LOCAL_CRD').prop("disabled", true);
        			$('#UD_EQGROUP_SPECIFIED_BRANCHES').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_ALL_BRANCHES').prop("disabled", true);
        			$('#UD_EQGROUP_JUSTIFICATION').prop("disabled", true);
        			$('#UD_EQGROUP_LIMIT').prop("disabled", true);
        			$('#UD_EQGROUP_DATE_LAST_MAINTAINE').prop("disabled", true);
        			$('#UD_EQGROUP_LIMIT_FOR_LOCAL_DEB').prop("disabled", true);
        			$('#UD_EQGROUP_UNIQUE_ID').prop("disabled", true);
        			$('#UD_EQGROUP_IT_RESOURCE_NAME').prop("disabled", true);    	
			
	//alert('Enquiry Change Happened');
			}
			else
			{
			
					$('#UD_EQGROUP_AUTH_AMOUNT_FOR_LOC').prop("disabled", true);
        			$('#UD_EQGROUP_INTER_BRANCH_DEBIT').prop("disabled", true);
        			$('#UD_EQGROUP_INTER_BRANCH_CREDIT').prop("disabled", true);
        			$('#UD_EQGROUP_USER_TYPE').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_AMOUNT_FOR_L90').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_SPECIFIED_BRAN').prop("disabled", true);
        			$('#UD_EQGROUP_GROUP_DESCRIPTION').prop("disabled", true);
        			$('#UD_EQGROUP_ALL_BRANCHES').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_AMOUNT_FOR_INT').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_AMOUNT_FOR_I52').prop("disabled", true);
        			$('#UD_EQGROUP_USER_GROUP').prop("disabled", true);
        			$('#UD_EQGROUP_LIMIT_FOR_LOCAL_CRD').prop("disabled", true);
        			$('#UD_EQGROUP_SPECIFIED_BRANCHES').prop("disabled", true);
        			$('#UD_EQGROUP_AUTH_ALL_BRANCHES').prop("disabled", true);
        			$('#UD_EQGROUP_JUSTIFICATION').prop("disabled", true);
        			$('#UD_EQGROUP_LIMIT').prop("disabled", true);
        			$('#UD_EQGROUP_DATE_LAST_MAINTAINE').prop("disabled", true);
        			$('#UD_EQGROUP_LIMIT_FOR_LOCAL_DEB').prop("disabled", true);
        			$('#UD_EQGROUP_UNIQUE_ID').prop("disabled", true);
        			$('#UD_EQGROUP_IT_RESOURCE_NAME').prop("disabled", true);
        			alert("Please Select Correct User Type");    	
			}
    
});
			
			$('#updateAD').click(function(){
			console.log('updateAD clicked');
				 var UD_ADUSER_FULLNAME = $('#UD_ADUSER_FULLNAME').val();
				 var UD_ADUSER_ORGNAME =  $('[name="UD_ADUSER_ORGNAME"]').val();
				 console.log(UD_ADUSER_FULLNAME);
				 //UD_ADUSER_FULLNAME
				 
				$.ajax({
					type: 'POST',
					data:{UD_ADUSER_FULLNAME:UD_ADUSER_FULLNAME,UD_ADUSER_ORGNAME:UD_ADUSER_ORGNAME,appInstanceType:"1",action:"updateAD"},
					url: 'AccountProvisioningController',
					success: function(result){
					//$('#actFromRes').html(result);
					console.log(result);
					alert("Active Directory Details has been Updated");
					}
				});
				 
			});
				$('#updateAS400').click(function(){
				// UD_AS400CON_INITIALMENU
				// UD_AS400CON_USERCONTROLS
				// UD_AS400CON_GROUP_PROFILE
				// UD_AS400CON_INITIALPROGRAM
				// UD_AS400CON_UNITNAME
				// UD_AS400CON_UID
				
				
				console.log("AS400 Updated Called");
				
				 var UD_AS400CON_CURRENT_LIBRARY;// = $('#UD_AS400CON_CURRENT_LIBRARY').val();
				 var UD_AS400CON_USERCONTROLS;// = $('#UD_AS400CON_USERCONTROLS').val();
				 var UD_AS400CON_GROUP_PROFILE;// =  $('[name="UD_AS400CON_GROUP_PROFILE"]').val();
				 var UD_AS400CON_INITIALMENU;// =  $('[name="UD_AS400CON_INITIALMENU"]').val();
				 var UD_AS400CON_INITIALPROGRAM;// =  $('[name="UD_AS400CON_INITIALPROGRAM"]').val();
				 var UD_AS400CON_UID;// =  $('#UD_AS400CON_UID').val();
				 var UD_AS400CON_LIMITCAP;// =  $('[name="UD_AS400CON_LIMITCAP"]').val();
				
				
//var UD_AS400CON_GROUP_PROFILE1 = ($('#UD_AS400CON_GROUP_PROFILE1 [value="' + UD_AS400CON_GROUP_PROFILE + '"]').attr('label'));
 
xml = "<Application name=\"AS400\">";

/*	
	xml = xml + "<table table_name=\"FormData\">";
 	xml = xml + "<userLogin login=\""+affected_user+"\"/>";
 	
xml =xml+  "<Record id=\""+1+"\">";
xml =xml+ "<CURRENT_LIBRARY>"+UD_AS400CON_CURRENT_LIBRARY+"</CURRENT_LIBRARY>";
xml =xml+ "<INITIAL_PROGRAM>"+UD_AS400CON_INITIALPROGRAM+"</INITIAL_PROGRAM>";
xml =xml+ "<USER_CLASS>"+UD_AS400CON_USERCONTROLS+"</USER_CLASS>";
//xml =xml+ "<GROUP_PROFILE>"+UD_AS400CON_GROUP_PROFILE+"</GROUP_PROFILE>";
xml =xml+ "<GROUP_PROFILE>"+UD_AS400CON_GROUP_PROFILE1+"</GROUP_PROFILE>";
xml =xml+ "<INITIAL_MENU>"+UD_AS400CON_INITIALMENU+"</INITIAL_MENU>";
xml =xml+ "<LIMIT_CAPABILITIES>"+UD_AS400CON_LIMITCAP+"</LIMIT_CAPABILITIES>";



xml =xml+ "</Record>";
xml =xml+ "</table>";			
*/
							////////////////////////////////////////////////
			
					//AS400 Table CSP Child Data
			
			////////////////////////////////////////////////

var n1 = document.getElementById("tableCSP").rows.length;
var i=0,j=0;
var str="";
 
 var tableCSP="";
 var row="";
 var col="";
 
 

 xml = xml + "<table table_name=\"tableCSP\">";
 xml = xml + "<userLogin login=\""+affected_user+"\"/>";
 
for(i=1; i<n1;i++){
console.log("i::"+i);
 row =  "<Record id=\""+i+"\">";
 
var n2 = document.getElementById("tableCSP").rows[i].cells.length;
  
for(j=0; j<n2-1;j++){
 
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

 xml = xml + "</table>";
 
 
				 ////////////////////////////////////////////////
			
					//AS400 TableCSG Child Data
			
				 ////////////////////////////////////////////////
 
n1 = document.getElementById("tableCSG").rows.length;
 i=0;
 j=0;
var str="";
 
 var tableCSG="";
  row="";
  col="";


  xml = xml + "<table table_name=\"tableCSG\">";
 xml = xml + "<userLogin login=\""+affected_user+"\"/>";
 
for(i=1; i<n1;i++){
console.log("i::"+i);
 row =  "<Record id=\""+i+"\">";
 
var n2 = document.getElementById("tableCSG").rows[i].cells.length;
  
for(j=0; j<n2-1;j++){
 
var x=document.getElementById("tableCSG").rows[i].cells.item(j).innerHTML;
var header=document.getElementById("tableCSG").rows[0].cells.item(j).innerHTML;
 col = col + "<"+header+">"+x+"</"+header+">";
   // tableCSP[j] = x;
   
 
    str=str+x+":";
 
 
}
tableCSG=tableCSG+row+col+"</Record>";
row="";
col="";
str=str+"#";

}
xml=xml+tableCSG;
				
 xml = xml + "</table></Application>";
				
				 
				 
				 console.log(UD_AS400CON_GROUP_PROFILE);
				 //UD_ADUSER_FULLNAME
				 
				$.ajax({
					type: 'POST',
					data:{UD_AS400CON_LIMITCAP:UD_AS400CON_LIMITCAP,UD_AS400CON_CURRENT_LIBRARY:UD_AS400CON_CURRENT_LIBRARY,UD_AS400CON_USERCONTROLS:UD_AS400CON_USERCONTROLS,UD_AS400CON_GROUP_PROFILE:UD_AS400CON_GROUP_PROFILE1,UD_AS400CON_INITIALMENU:UD_AS400CON_INITIALMENU
					,UD_AS400CON_INITIALPROGRAM:UD_AS400CON_INITIALPROGRAM,UD_AS400CON_UID:UD_AS400CON_UID,appInstanceType:"1",action:"updateAS400",xml:JSON.stringify(xml)},
					url: 'AccountProvisioningController',
					success: function(result){
					//$('#actFromRes').html(result);
					console.log(result);
					alert("As400 Details has been Updated");
					}
				});
				 xml ="";
			});
			$('#updateEquation').click(function(){
				console.log("updateEquation called");

	var val = $("#UD_EQUATION_USER_CLASS").val();
				console.log("UserGroup11:"+val);
				if(val==null || val=='')
				{alert("Please Enter User class");
				return;}
                 var obj = $("#UD_EQUATION_USER_CLASS1").find("option[value='" + val + "']");
		
		if(obj != null && obj.length > 0)
	    	
	    	{}else{alert("Please enter correct User class");
		return;}
	

console.log("Table26"+document.getElementById("table26").rows.length);
		console.log("Table7"+document.getElementById("table7").rows.length);
if((document.getElementById("table26").rows.length < 2) || (document.getElementById("table7").rows.length < 2))
{
		console.log(document.getElementById("table26").rows.length);
		console.log(document.getElementById("table7").rows.length);
		alert("Please Add Data in Table 26 and Table 7");
		return;

}

			////////////////////////////////////////////////
			
					//Equation Table 26 Child Data
			
			////////////////////////////////////////////////
xml = "<Application name=\"EQUATION\">";
var n1 = document.getElementById("table26").rows.length;
var i=0,j=0;
var str="";
 
 var table26="";
 var row="";
 var col="";
 
 

 xml = xml + "<table table_name=\"table26Equation\">";
 xml = xml + "<userLogin login=\""+affected_user+"\"/>";
 
for(i=1; i<n1;i++){
console.log("i::"+i);
 row =  "<Record id=\""+i+"\">";
 
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

 xml = xml + "</table>";
 
 
				 ////////////////////////////////////////////////
			
					//Equation Tablre 27 Child Data
			
				 ////////////////////////////////////////////////
 
n1 = document.getElementById("table27").rows.length;
 i=0;
 j=0;
var str="";
 
 var table27="";
  row="";
  col="";


  xml = xml + "<table table_name=\"table27Equation\">";
 xml = xml + "<userLogin login=\""+affected_user+"\"/>";
 
for(i=1; i<n1;i++){
console.log("i::"+i);
 row =  "<Record id=\""+i+"\">";
 
var n2 = document.getElementById("table27").rows[i].cells.length;
  
for(j=0; j<n2;j++){
 
var x=document.getElementById("table27").rows[i].cells.item(j).innerHTML;
var header=document.getElementById("table27").rows[0].cells.item(j).innerHTML;
 col = col + "<"+header+">"+x+"</"+header+">";
   // table26[j] = x;
   
 
    str=str+x+":";
 
 
}
table27=table27+row+col+"</Record>";
row="";
col="";
str=str+"#";

}
xml=xml+table27;

 xml = xml + "</table>";

 				////////////////////////////////////////////////
			
					//Equation Tablre 7 Child Data
			
				 ////////////////////////////////////////////////
 
n1 = document.getElementById("table7").rows.length;
 i=0;
 j=0;
var str="";
 
 var table7="";
  row="";
  col="";


  xml = xml + "<table table_name=\"table7Equation\">";
 xml = xml + "<userLogin login=\""+affected_user+"\"/>";
 
for(i=1; i<n1;i++){
console.log("i::"+i);
 row =  "<Record id=\""+i+"\">";
 
var n2 = document.getElementById("table7").rows[i].cells.length;
  
for(j=0; j<n2-1;j++){
 
var x=document.getElementById("table7").rows[i].cells.item(j).innerHTML;
var header=document.getElementById("table7").rows[0].cells.item(j).innerHTML;
 col = col + "<"+header+">"+x+"</"+header+">";
   // table26[j] = x;
   
 
    str=str+x+":";
 
 
}
table7=table7+row+col+"</Record>";
row="";
col="";
str=str+"#";

}
xml=xml+table7;

 xml = xml + "</table>";



					////////////////////////////////////////////////
			
					//Equation Tablre 34 Child Data
			
				 ////////////////////////////////////////////////
 
n1 = document.getElementById("table34").rows.length;
 i=0;
 j=0;
var str="";
 
 var table34="";
  row="";
  col="";


  xml = xml + "<table table_name=\"table34Equation\">";
 xml = xml + "<userLogin login=\""+affected_user+"\"/>";
 
for(i=1; i<n1;i++){
console.log("i::"+i);
 row =  "<Record id=\""+i+"\">";
 
var n2 = document.getElementById("table34").rows[i].cells.length;
  
for(j=0; j<n2-1;j++){
 
var x=document.getElementById("table34").rows[i].cells.item(j).innerHTML;
var header=document.getElementById("table34").rows[0].cells.item(j).innerHTML;
 col = col + "<"+header+">"+x+"</"+header+">";
   // table26[j] = x;
   
 
    str=str+x+":";
 
 
}
table34=table34+row+col+"</Record>";
row="";
col="";
str=str+"#";

}
xml=xml+table34;

 xml = xml + "</table></Application>";


 
				 console.log("....."+xml);
				 //console.log("String"+str);
				 
				 var UD_EQUATION_AUTH_ANY_BRANCH =  $('[name="UD_EQUATION_AUTH_ANY_BRANCH"]').val();
				 var UD_EQUATION_USER_CLASS =  $('[name="UD_EQUATION_USER_CLASS"]').val();
				 var UD_EQUATION_LIMIT_VIOLATION_AU =  $('[name="UD_EQUATION_LIMIT_VIOLATION_AU"]').val();
				// console.log(UD_EQUATION_LIMIT_VIOLATION_AU);
				 
				 // UD_EQUATION_AUTH_ANY_BRANCH
				 // UD_EQUATION_USER_CLASS
				 // UD_EQUATION_LIMIT_VIOLATION_AU
				$.ajax({
					type: 'POST',
					data:{UD_EQUATION_AUTH_ANY_BRANCH:UD_EQUATION_AUTH_ANY_BRANCH,UD_EQUATION_USER_CLASS:UD_EQUATION_USER_CLASS,
					UD_EQUATION_LIMIT_VIOLATION_AU:UD_EQUATION_LIMIT_VIOLATION_AU
					,appInstanceType:"1",action:"updateEquation",xml:JSON.stringify(xml)},
					url: 'AccountProvisioningController',
					success: function(result){
					//$('#actFromRes').html(result);
					console.log(result);
					
					alert("Equation Details has been Updated");
					}
				});
				 xml ="";
				 
			}); 
			$('#updateEQGRP').click(function(){

				console.log("Update Equation Group clicked");
				var UD_EQGROUP_AUTH_AMOUNT_FOR_LOC; //= $('#UD_EQGROUP_AUTH_AMOUNT_FOR_LOC').val();
				var UD_EQGROUP_INTER_BRANCH_DEBIT; //= $('#UD_EQGROUP_INTER_BRANCH_DEBIT').val();
				var UD_EQGROUP_INTER_BRANCH_CREDIT; //= $('#UD_EQGROUP_INTER_BRANCH_CREDIT').val();
				var UD_EQGROUP_USER_TYPE;//=  $('[name="UD_EQGROUP_USER_TYPE"]').val();
				var UD_EQGROUP_AUTH_AMOUNT_FOR_L90; //=  $('#UD_EQGROUP_AUTH_AMOUNT_FOR_L90').val();
				var UD_EQGROUP_AUTH_SPECIFIED_BRAN; //=  $('#UD_EQGROUP_AUTH_SPECIFIED_BRAN').val();
				var UD_EQGROUP_GROUP_DESCRIPTION =  $('[name="UD_EQGROUP_GROUP_DESCRIPTION"]').val();
				var UD_EQGROUP_ALL_BRANCHES; //=  $('[name="UD_EQGROUP_ALL_BRANCHES"]').val();
				var UD_EQGROUP_AUTH_AMOUNT_FOR_INT; //=  $('[name="UD_EQGROUP_AUTH_AMOUNT_FOR_INT"]').val();
				var UD_EQGROUP_AUTH_AMOUNT_FOR_I52; //=  $('#UD_EQGROUP_AUTH_AMOUNT_FOR_I52').val();
				var UD_EQGROUP_USER_GROUP =  $('#UD_EQGROUP_USER_GROUP').val();
				var UD_EQGROUP_LIMIT_FOR_LOCAL_CRD; //=  $('#UD_EQGROUP_LIMIT_FOR_LOCAL_CRD').val();
				var UD_EQGROUP_SPECIFIED_BRANCHES; //=  $('[name="UD_EQGROUP_SPECIFIED_BRANCHES"]').val();
				var UD_EQGROUP_AUTH_ALL_BRANCHES; //=  $('[name="UD_EQGROUP_AUTH_ALL_BRANCHES"]').val();
				var UD_EQGROUP_JUSTIFICATION; //=  $('#UD_EQGROUP_JUSTIFICATION').val();
				var UD_EQGROUP_LIMIT =  $('#UD_EQGROUP_LIMIT').val();
				var UD_EQGROUP_GROUP_TYPE =  $('[name="UD_EQGROUP_GROUP_TYPE"]').val();
				var UD_EQGROUP_LIMIT_FOR_LOCAL_DEB; //=  $('#UD_EQGROUP_LIMIT_FOR_LOCAL_DEB').val();
														  
				console.log("UD_EQGROUP_INTER_BRANCH_DEBIT: "+ UD_EQGROUP_INTER_BRANCH_DEBIT);
				 //UD_ADUSER_FULLNAME
					 
							
				 if(UD_EQGROUP_GROUP_TYPE=='' || UD_EQGROUP_GROUP_TYPE==null)
				 {
				 		alert("Please enter Group Type");
				 		return;
				 }
				
				if(UD_EQGROUP_USER_GROUP.length !=6)
				{alert("USER_GROUP should be 6 Characters")
				return;} 
				
			console.log("UD_EQGROUP_GROUP_TYPE:"+UD_EQGROUP_GROUP_TYPE);
			
			if(UD_EQGROUP_GROUP_TYPE == 'Teller'){
	
			if(UD_EQGROUP_LIMIT=='' || UD_EQGROUP_LIMIT == null)
			{
			alert("Please Enter Limit")
			return;
			}
			if(UD_EQGROUP_LIMIT<0 || UD_EQGROUP_LIMIT>10000000)
			{
			alert("Please Enter Valid Limit")
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
				
	xml = "<Application name=\"EquationGroup\">";
	xml = xml + "<table table_name=\"FormData\">";
 	xml = xml + "<userLogin login=\""+affected_user+"\"/>";
 	
 xml =xml+  "<Record id=\""+1+"\">";
 xml =xml+ "<GROUP_TYPE>"+UD_EQGROUP_GROUP_TYPE+"</GROUP_TYPE>";
 xml =xml+ "<GROUP_NAME>"+UD_EQGROUP_USER_GROUP+"</GROUP_NAME>";
xml =xml+ "<LIMIT>"+UD_EQGROUP_LIMIT+"</LIMIT>";

xml =xml+ "</Record>";
xml =xml+ "</table>";			
			}
			else if(UD_EQGROUP_GROUP_TYPE == 'Supervisor'){
			if(UD_EQGROUP_LIMIT=='' || UD_EQGROUP_LIMIT==null)
			{
			alert("Please Enter Limit")
			return;
			}
			if(UD_EQGROUP_LIMIT<0 || UD_EQGROUP_LIMIT>10000000)
			{
			alert("Please Enter Valid Limit")
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
				
			xml = "<Application name=\"EquationGroup\">";
	xml = xml + "<table table_name=\"FormData\">";
 	xml = xml + "<userLogin login=\""+affected_user+"\"/>";
 	
 xml =xml+  "<Record id=\""+1+"\">";
 xml =xml+ "<GROUP_TYPE>"+UD_EQGROUP_GROUP_TYPE+"</GROUP_TYPE>";
 xml =xml+ "<GROUP_NAME>"+UD_EQGROUP_USER_GROUP+"</GROUP_NAME>";
xml =xml+ "<LIMIT>"+UD_EQGROUP_LIMIT+"</LIMIT>";
xml =xml+ "</Record>";
xml =xml+ "</table>";
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
				
			
				xml = "<Application name=\"EquationGroup\">";
	xml = xml + "<table table_name=\"FormData\">";
 	xml = xml + "<userLogin login=\""+affected_user+"\"/>";
 	
 xml =xml+  "<Record id=\""+1+"\">";
xml =xml+ "<GROUP_TYPE>"+UD_EQGROUP_GROUP_TYPE+"</GROUP_TYPE>";
xml =xml+ "<GROUP_NAME>"+UD_EQGROUP_USER_GROUP+"</GROUP_NAME>";
xml =xml+ "</Record>";
xml =xml+ "</table>";
			
			}
				 
				  
/////////////////////////////////////////////////////////////////
			//Equation Group Table
			
/////////////////////////////////////////////////////////////////

var n1 = document.getElementById("tableEQGROUP").rows.length;
var i=0,j=0;
var str="";
 
 var tableEQGROUP="";
 var row="";
 var col="";
 
 

 xml = xml + "<table table_name=\"tableEQGROUP\">";
 xml = xml + "<userLogin login=\""+affected_user+"\"/>";
 
for(i=1; i<n1;i++){
console.log("i::"+i);
 row =  "<Record id=\""+i+"\">";
 
var n2 = document.getElementById("tableEQGROUP").rows[i].cells.length;
  
for(j=0; j<n2-1;j++){
 
var x=document.getElementById("tableEQGROUP").rows[i].cells.item(j).innerHTML;
var header=document.getElementById("tableEQGROUP").rows[0].cells.item(j).innerHTML;
 col = col + "<"+header+">"+x+"</"+header+">";
   // tableCSP[j] = x;
   
 
    str=str+x+":";
 
 
}
tableEQGROUP=tableEQGROUP+row+col+"</Record>";
row="";
col="";
str=str+"#";

}
xml=xml+tableEQGROUP;

 xml = xml + "</table></Application>";

console.log("XML: "+xml);
				$.ajax({
					type: 'POST',
					data:{UD_EQGROUP_AUTH_AMOUNT_FOR_LOC:UD_EQGROUP_AUTH_AMOUNT_FOR_LOC,
					UD_EQGROUP_INTER_BRANCH_DEBIT:UD_EQGROUP_INTER_BRANCH_DEBIT,
					UD_EQGROUP_INTER_BRANCH_CREDIT:UD_EQGROUP_INTER_BRANCH_CREDIT,
					 UD_EQGROUP_USER_TYPE:UD_EQGROUP_USER_TYPE,
					 UD_EQGROUP_AUTH_AMOUNT_FOR_L90:UD_EQGROUP_AUTH_AMOUNT_FOR_L90,
					 UD_EQGROUP_AUTH_SPECIFIED_BRAN:UD_EQGROUP_AUTH_SPECIFIED_BRAN,
					 UD_EQGROUP_GROUP_DESCRIPTION:UD_EQGROUP_GROUP_DESCRIPTION,
					 UD_EQGROUP_ALL_BRANCHES:UD_EQGROUP_ALL_BRANCHES,
					 UD_EQGROUP_AUTH_AMOUNT_FOR_INT:UD_EQGROUP_AUTH_AMOUNT_FOR_INT,
					 UD_EQGROUP_AUTH_AMOUNT_FOR_I52:UD_EQGROUP_AUTH_AMOUNT_FOR_I52,
					 UD_EQGROUP_USER_GROUP:UD_EQGROUP_USER_GROUP,
					 UD_EQGROUP_LIMIT_FOR_LOCAL_CRD:UD_EQGROUP_LIMIT_FOR_LOCAL_CRD,
					 UD_EQGROUP_SPECIFIED_BRANCHES:UD_EQGROUP_SPECIFIED_BRANCHES,
					 UD_EQGROUP_AUTH_ALL_BRANCHES:UD_EQGROUP_AUTH_ALL_BRANCHES,
					 UD_EQGROUP_JUSTIFICATION:UD_EQGROUP_JUSTIFICATION,
					 UD_EQGROUP_LIMIT:UD_EQGROUP_LIMIT,
					 UD_EQGROUP_LIMIT_FOR_LOCAL_DEB:UD_EQGROUP_LIMIT_FOR_LOCAL_DEB,
					 UD_EQGROUP_GROUP_TYPE:UD_EQGROUP_GROUP_TYPE,appInstanceType:"1",action:"updateEQGRP",xml:JSON.stringify(xml)},
					url: 'AccountProvisioningController',
					success: function(result){
					//$('#actFromRes').html(result);
					console.log("result: "+ result);
					alert("Equation Group Details has been Updated");
					}
				});
				 xml = "";
			}); 
			$('#updateCP1').click(function(){
				
				 var UD_CP1USR_COMMENT = $('#UD_CP1USR_COMMENT').val();
				 var UD_CP1USR_REGION_ID =  $('[name="UD_CP1USR_REGION_ID"]').val();
				 var UD_CP1USR_REGION_ID =  $('[name="UD_CP1USR_REGION_ID"]').val();
				  var UD_CP1USR_USER_ID =  $('#UD_CP1USR_USER_ID').val();
				  var UD_CP1USR_DEPARTMENT_ID =  $('[name="UD_CP1USR_DEPARTMENT_ID"]').val();
				  var UD_CP1USR_USER_TYPE_ID =  $('[name="UD_CP1USR_USER_TYPE_ID"]').val();
				 

				 


    var CP_REGION = ($('#UD_CP1USR_REGION_ID1 [value="' + UD_CP1USR_REGION_ID + '"]').attr('label'));
    var CP_DEPARTMENT = ($('#UD_CP1USR_DEPARTMENT_ID1 [value="' + UD_CP1USR_DEPARTMENT_ID + '"]').attr('label'));
    var CP_USER_TYPE = ($('#UD_CP1USR_USER_TYPE_ID1 [value="' + UD_CP1USR_USER_TYPE_ID + '"]').attr('label'));

	
	///////////////////////////////create XML of Form//////////////////////////
	
	xml = "<Application name=\"CashPortal\">";
	xml = xml + "<table table_name=\"FormData\">";
 	xml = xml + "<userLogin login=\""+affected_user+"\"/>";
 	
 var row="";
 var col="";
 xml =xml+  "<Record id=\""+1+"\">";
xml =xml+ "<REGION_ID>"+CP_REGION+"</REGION_ID>";
xml =xml+ "<DEPARTMENT_ID>"+CP_DEPARTMENT+"</DEPARTMENT_ID>";
xml =xml+ "<USER_TYPE>"+CP_USER_TYPE+"</USER_TYPE>";
xml =xml+ "</Record>";
xml =xml+ "</table>";
xml =xml+ "</Application>";
				 
				$.ajax({
					type: 'POST',
					data:{UD_CP1USR_COMMENT:UD_CP1USR_COMMENT,UD_CP1USR_REGION_ID:UD_CP1USR_REGION_ID,UD_CP1USR_USER_ID:UD_CP1USR_USER_ID,
					UD_CP1USR_DEPARTMENT_ID:UD_CP1USR_DEPARTMENT_ID,UD_CP1USR_USER_TYPE_ID:UD_CP1USR_USER_TYPE_ID,appInstanceType:"1",action:"updateCP1",xml:JSON.stringify(xml)},
					url: 'AccountProvisioningController',
					success: function(result){
					//$('#actFromRes').html(result);
					console.log(result);
					alert("Cash Portal Details has been Updated");
					}
				});
				xml = "";				 
			});
			/*$('#updateEQGRP').click(function(){
				
				 var UD_AS400CON_USERCONTROLS = $('#UD_AS400CON_USERCONTROLS').val();
				 var UD_AS400CON_GROUP_PROFILE =  $('[name="UD_AS400CON_GROUP_PROFILE"]').val();
				  var UD_AS400CON_INITIALPROGRAM =  $('#UD_AS400CON_INITIALPROGRAM').val();
				 console.log(UD_AS400CON_GROUP_PROFILE);
				 //UD_ADUSER_FULLNAME
				 
				$.ajax({
					type: 'POST',
					data:{UD_AS400CON_GROUP_PROFILE:UD_AS400CON_GROUP_PROFILE,UD_AS400CON_USERCONTROLS:UD_AS400CON_USERCONTROLS,
					UD_AS400CON_INITIALPROGRAM:UD_AS400CON_INITIALPROGRAM,appInstanceType:"1",action:"updateAS400"},
					url: 'AccountProvisioningController',
					success: function(result){
					//$('#actFromRes').html(result);
					console.log(result);
					}
				});
				 
			}); */
	});
</script>
</head>

<body>
	<%
		String name = (String) session.getAttribute("username");

		if (name != null && name.length() != 0) {
			session.setAttribute("username", name);
		} else
			response.sendRedirect("index.jsp");

		List<AccountCart> acctCartList = (List<AccountCart>) session.getAttribute("acctCatList");
		 //get number of items in cart
		 int cartNumber = acctCartList.size();
		String CpUserId = acctCartList.get(0).getUserLogin();
		session.setAttribute("CpUID", CpUserId);
		OIMClient oimClient = (OIMClient) session.getAttribute("oimClient");
  		ProvisioningAccounts pa = new ProvisioningAccounts();
  							
  							
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
						 <li>
							
							
							<a href="ViewCart.jsp"><i class="fa fa-shopping-cart"></i> <span class="badge" id="cartTotal" ><%=cartNumber %></span></a>
							
							
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
							<div class="profile-left" style="width: 25% !Important;">
								<div class="tabs-line-bottom left-aligned">
									<br />
									 
									<br />
									<ul class="nav" role="tablist">
										<li class="active"><a href="#tab-bottom-left1" role="tab"
											data-toggle="tab"><i class="glyphicon glyphicon-ok-sign"
												style="color: #009591;"> </i> Review Items in your cart</a></li>

										<li><a href="#tab-bottom-left2" role="tab"
											data-toggle="tab"><i class="glyphicon glyphicon-ok-sign"
												style="color: #009591;"> </i> Explain Access Needs</a></li>
										<li><a href="#tab-bottom-left3" role="tab"
											data-toggle="tab"><i class="glyphicon glyphicon-ok-sign"
												style="color: #009591;"> </i> Submit Your Request</a></li>
									</ul>
								</div>
								<!-- END PROFILE DETAIL -->
							</div>
							<!-- END LEFT COLUMN -->
							<!-- RIGHT COLUMN -->
							<div class="profile-right" style="width: 75% !Important;">
								<h3>Items in your Request Cart</h3>
								<!-- END AWARDS -->
								<!-- TABBED CONTENT -->
								<div class="tab-content">



									<div class="tab-pane fade in active" id="tab-bottom-left1">

										<div class="table-responsive">

											<table class="table project-table" id="cartTable">

												<tbody>
													<%
														for (AccountCart acctList : acctCartList) {
															if(acctList.getItemType()==1)
														{
															out.write("<tr>" + "<td class='col-md-1'> <i class='glyphicon glyphicon-cog'> App  </i></td>" + "<td>"
																	+ "<a href='#' id='btn"+acctList.getAppInstacneName()+"' name='btn"+acctList.getAppInstacneName()+"'>   " + acctList.getAppInstacneName() + "</a>" + "</td>" + "<td>"
																	+ acctList.getUserLogin() + "</td>"
																	+ "<td><button class='btn btn-primary btn-sm removeCart' id='removeCart'>Remove</button></td>"
																	+ "</tr>");
														}
														}
														for (AccountCart acctList : acctCartList) {
														if(acctList.getItemType()==2)
														{
															out.write("<tr>" + "<td class='col-md-1'> <i class='glyphicon glyphicon-cog'> Ent  </i></td>" + "<td>"
																	+ "<a href='#' id='btn"+acctList.getAppInstacneName()+"' name='btn"+acctList.getAppInstacneName()+"'>   " + acctList.getAppInstacneName() + "</a>" + "</td>" + "<td>"
																	+ acctList.getUserLogin() + "</td>"
																	+ "<td><button class='btn btn-primary btn-sm removeCart' id='removeCart'>Remove</button></td>"
																	+ "</tr>");
														}
															
														}
														for (AccountCart acctList : acctCartList) {
														if(acctList.getItemType()==3)
														{
															out.write("<tr>" + "<td class='col-md-1'> <i class='glyphicon glyphicon-cog'> Role  </i></td>" + "<td>"
																	+ "<a href='#' id='btn"+acctList.getAppInstacneName()+"' name='btn"+acctList.getAppInstacneName()+"'>   " + acctList.getAppInstacneName() + "</a>" + "</td>" + "<td>"
																	+ acctList.getUserLogin() + "</td>"
																	+ "<td><button class='btn btn-primary btn-sm removeCart' id='removeCart'>Remove</button></td>"
																	+ "</tr>");
														}
															
														}
													%>


													<tr>
														<td></td>
														<td></td>
														<td><label>Justification</label> <textarea
																class="form-control"
																placeholder="Please Grant me Access" id=accountJustification></textarea></td>
														<td></td>
													</tr>

												</tbody>


											</table>
											<hr />
											<div>
												<div class="col-md-4"></div>

												<div class="col-md-8">
													<button type="button" id="previousButton"
														class="btn btn-default btn-lg"
														style="background-color: white; color: #009591; height: 40px; font-size: 15px">
														<i class="glyphicon glyphicon-arrow-left"> </i> Continue
														Add Items
													</button>
													<span style="color: white">aa</span>
													<button type="button" id="submitRequest"
														class="btn btn-primary btn-lg"
														style="background-color: #009591; height: 40px; font-size: 15px">
														Submit Your Request <i
															class="glyphicon glyphicon-arrow-right"> </i>
													</button>
												</div>
												<!-- <label><input type="radio" name="submitDisclaimer" id="submitDisclaimer" value="1" >Dsiaclaimer text</label> -->
												
											</div>
											<br />
											<br />
											<br />

<label style="font-weight:100"><input type="radio" name="submitDisclaimer" id="submitDisclaimer" value="1" ><span> </span> I will use this access only to perform my official duties. I will not misuse it and disclose the credentials to any bank's employee or outsider.</label>
										</div>

									</div>
									<!--<div class="tab-pane fade" id="tab-bottom-left2">
										<div class="table-responsive">

											<table class="table project-table">
												<thead>
													<tr>
														<th>Title</th>
														<th>Progress</th>
														<th>Leader</th>
														<th>Status</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td><a href="#">Spot Media</a></td>
														<td>
															<div class="progress">
																<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
																	<span>60% Complete</span>
																</div>
															</div>
														</td>
														<td><img src="assets/img/user2.png" alt="Avatar" class="avatar img-circle"> <a href="#">Michael</a></td>
														<td><span class="label label-success">ACTIVE</span></td>
													</tr>
													<tr>
														<td><a href="#">E-Commerce Site</a></td>
														<td>
															<div class="progress">
																<div class="progress-bar" role="progressbar" aria-valuenow="33" aria-valuemin="0" aria-valuemax="100" style="width: 33%;">
																	<span>33% Complete</span>
																</div>
															</div>
														</td>
														<td><img src="assets/img/user1.png" alt="Avatar" class="avatar img-circle"> <a href="#">Antonius</a></td>
														<td><span class="label label-warning">PENDING</span></td>
													</tr>
													<tr>
														<td><a href="#">Project 123GO</a></td>
														<td>
															<div class="progress">
																<div class="progress-bar" role="progressbar" aria-valuenow="68" aria-valuemin="0" aria-valuemax="100" style="width: 68%;">
																	<span>68% Complete</span>
																</div>
															</div>
														</td>
														<td><img src="assets/img/user1.png" alt="Avatar" class="avatar img-circle"> <a href="#">Antonius</a></td>
														<td><span class="label label-success">ACTIVE</span></td>
													</tr>
													<tr>
														<td><a href="#">Wordpress Theme</a></td>
														<td>
															<div class="progress">
																<div class="progress-bar" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 75%;">
																	<span>75%</span>
																</div>
															</div>
														</td>
														<td><img src="assets/img/user2.png" alt="Avatar" class="avatar img-circle"> <a href="#">Michael</a></td>
														<td><span class="label label-success">ACTIVE</span></td>
													</tr>
													<tr>
														<td><a href="#">Project 123GO</a></td>
														<td>
															<div class="progress">
																<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
																	<span>100%</span>
																</div>
															</div>
														</td>
														<td><img src="assets/img/user1.png" alt="Avatar" class="avatar img-circle" /> <a href="#">Antonius</a></td>
														<td><span class="label label-default">CLOSED</span></td>
													</tr>
													<tr>
														<td><a href="#">Redesign Landing Page</a></td>
														<td>
															<div class="progress">
																<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
																	<span>100%</span>
																</div>
															</div>
														</td>
														<td><img src="assets/img/user5.png" alt="Avatar" class="avatar img-circle" /> <a href="#">Jason</a></td>
														<td><span class="label label-default">CLOSED</span></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>-->








									<!--<div class="tab-pane fade" id="tab-bottom-left3">
										<div class="table-responsive">

											<table class="table project-table">
												<thead>
													<tr>
														<th>Hash</th>
														<th>Hash</th>
														<th>Hash</th>
														<th>Hash</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td><a href="#">Spot Media</a></td>
														<td>
															<div class="progress">
																<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
																	<span>60% Complete</span>
																</div>
															</div>
														</td>
														<td><img src="assets/img/user2.png" alt="Avatar" class="avatar img-circle"> <a href="#">Michael</a></td>
														<td><span class="label label-success">ACTIVE</span></td>
													</tr>
													<tr>
														<td><a href="#">E-Commerce Site</a></td>
														<td>
															<div class="progress">
																<div class="progress-bar" role="progressbar" aria-valuenow="33" aria-valuemin="0" aria-valuemax="100" style="width: 33%;">
																	<span>33% Complete</span>
																</div>
															</div>
														</td>
														<td><img src="assets/img/user1.png" alt="Avatar" class="avatar img-circle"> <a href="#">Antonius</a></td>
														<td><span class="label label-warning">PENDING</span></td>
													</tr>
													<tr>
														<td><a href="#">Project 123GO</a></td>
														<td>
															<div class="progress">
																<div class="progress-bar" role="progressbar" aria-valuenow="68" aria-valuemin="0" aria-valuemax="100" style="width: 68%;">
																	<span>68% Complete</span>
																</div>
															</div>
														</td>
														<td><img src="assets/img/user1.png" alt="Avatar" class="avatar img-circle"> <a href="#">Antonius</a></td>
														<td><span class="label label-success">ACTIVE</span></td>
													</tr>
													<tr>
														<td><a href="#">Wordpress Theme</a></td>
														<td>
															<div class="progress">
																<div class="progress-bar" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 75%;">
																	<span>75%</span>
																</div>
															</div>
														</td>
														<td><img src="assets/img/user2.png" alt="Avatar" class="avatar img-circle"> <a href="#">Michael</a></td>
														<td><span class="label label-success">ACTIVE</span></td>
													</tr>
													<tr>
														<td><a href="#">Project 123GO</a></td>
														<td>
															<div class="progress">
																<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
																	<span>100%</span>
																</div>
															</div>
														</td>
														<td><img src="assets/img/user1.png" alt="Avatar" class="avatar img-circle" /> <a href="#">Antonius</a></td>
														<td><span class="label label-default">CLOSED</span></td>
													</tr>
													<tr>
														<td><a href="#">Redesign Landing Page</a></td>
														<td>
															<div class="progress">
																<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
																	<span>100%</span>
																</div>
															</div>
														</td>
														<td><img src="assets/img/user5.png" alt="Avatar" class="avatar img-circle" /> <a href="#">Jason</a></td>
														<td><span class="label label-default">CLOSED</span></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>-->
								</div>

								<!-- END TABBED CONTENT -->
							</div>
							<!-- END RIGHT COLUMN -->
						</div>
						 
							
						 
					</div>

				</div>
				<!--  START Account Forms -->
				<div class="main-content">

						 
									
				<div class="container-fluid">
								<div class="panel panel-profile">
									
				<div class="form-row" style="display: none; margin-left:20px; margin-top:15px;" id="divAS400" name="divAS400">
					<div class="col-md-4">
						<h4 style="font-size:18px;">Request Details AS400</h4></div>
					<div class="col-md-6">
				</div>
					<div class="col-md-2">
						<button class="btn hblbtn" id="updateAS400" name="updateAS400" >Update</button>
					</div>
				</div>	
				<div class="form-row" style="display: none;" id="divAD" class="divAD">
					<div class="col-md-4">
						<h4 style="font-size:18px; margin-left:20px; margin-top:15px;">Request Details Active Directory</h4></div>
					<div class="col-md-6">
				</div>
					<div class="col-md-2">
						<button class="btn hblbtn" id="updateAD" name="updateAD">Update</button>
					</div>
				</div>
				<div class="form-row" style="display: none;" id="divEQ" class="divEQ">
					<div class="col-md-4">
						<h4 style="font-size:18px; margin-left:20px; margin-top:15px; margin-top:15px;">Request Details Equation</h4></div>
					<div class="col-md-6">
				</div>
					<div class="col-md-2">
						<button class="btn hblbtn" id="updateEquation" name="updateEquation">Update</button>
					</div>
				</div>
				<div class="form-row" style="display: none;" id="divEQGRP" class="divEQGRP">
					<div class="col-md-4">
						<h4 style="font-size:18px; margin-left:20px; margin-top:15px;">Request Details EQ Group</h4></div>
					<div class="col-md-6">
				</div>
					<div class="col-md-2">
						<button class="btn hblbtn" id="updateEQGRP" name="updateEQGRP">Update</button>
					</div>
				</div>
				<div class="form-row" style="display: none;" id="divCP1" class="divCP1">
					<div class="col-md-4">
						<h4 style="font-size:18px; margin-left:20px; margin-top:15px;">Request Details Cash Portal</h4></div>
					<div class="col-md-6">
				</div>
					<div class="col-md-2">
						<button class="btn hblbtn" id="updateCP1" name="updateCP1">Update</button>
					</div>
				</div>

									<div class='clearfix'></div>
									<br />
									<form id="actFromRes" name="actFromRes" style="margin-left:20px; margin-right:20px">
									
									
									<!-- Start Child Form Fields  -->
										
									
									<!-- END Child Form Fields  -->
									</form>
								</div>
							</div>
						</div>
				<!--  END ACCOUNT FORMS -->
				<!-- END MAIN CONTENT -->



			</div>






			<!-- END MAIN -->
			<div class="clearfix"></div>
			<footer>
				<div class="container-fluid">
					<p class="copyright">
						&copy; 2021 <a href="#" target="_blank">HBL Pvt Ltd</a>. All
						Rights Reserved.
					</p>
				</div>
			</footer>
		</div>
		
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
		


		<!-- Table AS400CSG -->
	
	
		<div class="modal fade AS400CSG tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">AS400CSG</h4>
            </div>
            <div class="modal-body">
                        
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Supplemental Group: </label></div>
                        <div class="col-md-4"> 
                        
                
                
                 <input list="UserGroup2" name="UserGroup" id="SupplementalGroup">
							<datalist id="UserGroup2">
<% 							
								Map<String, String> lookupValues3 = pa.getLookupMap("Lookup.AS400.Groups", oimClient);
			
			for(Map.Entry<String, String> lookupEntrySet: lookupValues3.entrySet() )
			{
				out.write("<option value='"+lookupEntrySet.getValue().trim()+"' label='"+lookupEntrySet.getKey().trim()+"'>"+lookupEntrySet.getKey().trim()+"</option>"); 
				
			}
  		%>	       
</datalist>
<!-- 
						<select name="UserGroup" id="SupplementalGroup">
  								<option value="170~ATMCCU">Supp0</option>
  								<option value="170~ATMCCU">supp1</option>
  								<option value="170~ATMCCU">supp2</option>
  								<option value="170~ATMCCU">supp3</option>
						</select>
						
						 -->
                         <!-- input type="text" class="form-control" id="UserGroup" placeholder="User Group" required!--></div>
                        
                        
                        
                        </div>
                    
            
                        <div class="clearfix"></div>
                            
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="addRowCSG" class="btn btn-primary"  style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div>
	<!-- Table AS400CSG->


		
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
								Map<String, String> lookupValues1 = pa.getLookupMap("Lookup.EQAOB.Groups", oimClient);
			
			for(Map.Entry<String, String> lookupEntrySet: lookupValues1.entrySet() )
			{
				out.write("<option value='"+lookupEntrySet.getKey().trim()+"' label='"+lookupEntrySet.getValue().trim()+"'>"+lookupEntrySet.getValue().trim()+"</option>"); 
				
			}
  		%>	
</datalist>
						<!--
						<select name="UserGroup" id="UserGroup">
  								<option value="170~ATMCCU">ATMCCU</option>
  								<option value="170~ATMCCU">171~ATMCCU</option>
  								<option value="170~ATMCCU">172~ATMCCU</option>
  								<option value="170~ATMCCU">173~ATMCCU</option>
						</select>
						-->
                         <!-- input type="text" class="form-control" id="UserGroup" placeholder="User Group" required!--></div>
                        
                        
                        
                        </div>
                    
            
                        <div class="clearfix"></div>
                            
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="addRow26" class="btn btn-primary"  style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div>
	<!-- table26 Equation->
	
	
	
	<!-- table27 Equation -->
	
	
	
		<div class="modal fade table27 tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">Equation Table 27</h4>
            </div>
            <div class="modal-body">
                        <div class="form-row">
            <div class="col-md-4"><label for="ApplicationName">Default User Limit: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="DefaultUserLimit" placeholder="Default User Limit" required></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Currency Mnemonic: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="CurrencyMnemonic" placeholder="Currency Mnemonic" required></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Date Last Maintained: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="DateLastMaintained27" placeholder="Date Last Maintained" required></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Application Code: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="ApplicationCode" placeholder="Application Code" required></div>
                        </div>
                                
            
                        <div class="clearfix"></div>
                            
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="addRow27" class="btn btn-primary"  style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div>

	<!-- table27 Equation->
	


<!-- table7 Equation -->
	
	
	
		<div class="modal fade table7 tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">Equation Table 7</h4>
            </div>
            <div class="modal-body">
                        <div class="form-row">
            <div class="col-md-4"><label for="ApplicationName">Unite Server: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="UniteServer" placeholder="Unite Server" value='HBLDOM' Disabled></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Initial Menu: </label></div>
                        <div class="col-md-4"> 
                        
                        <select name="UserGroup" id="InitialMenu">
  								<option value="WMENU1">WMENU1</option>
  								<option value="CCMENU1">CCMENU1</option>
  								
						</select>
                        
                        
                         <!--  input type="text" class="form-control" id="InitialMenu" placeholder="Initial Menu" required--></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Authorised Unit: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="AuthorisedUnit" placeholder="Authorised Unit" value ="PKC" Disabled></div>
                        </div>
                                            
            
                        <div class="clearfix"></div>
                            
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="addRow7" class="btn btn-primary"  style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div>

	<!-- table7 Equation->


<!-- table34 Equation -->
	
	
	
		<div class="modal fade table34 tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">Equation Table 34</h4>
            </div>
            <div class="modal-body">
                        <div class="form-row">
            <div class="col-md-4"><label for="ApplicationName">Process Inter Branch Transaction: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="ProcessInterBranch34" placeholder="Process Inter Branch Transaction" required></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Inter Branch Debit Transaction Limit: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="InterBranchDebit34" placeholder="Inter Branch Debit Transaction Limit" required></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Resident Branch: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="ResidentBranch34" placeholder="Resident Branch" required></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Inter Branch Credit Transaction Limit: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="InterBranchCredit34" placeholder="Inter Branch Credit Transaction Limit" required></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Branch Number: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="BranchNumber34" placeholder="Branch Number" required></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Local Credit Amount Transaction Limit: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="LocalCreditAmount34" placeholder="Local Credit Amount Transaction Limit" required></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Local Debit Amount Transaction Limit: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="LocalDebitAmount34" placeholder="Local Debit Amount Transaction Limit" required></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Debit Authorization Amount: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="DebitAuthorizationAmount34" placeholder="Debit Authorization Amount" required></div>
                        </div>
                        <br>
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Credit Authorization Amount: </label></div>
                        <div class="col-md-4">  <input type="text" class="form-control" id="CreditAuthorizationAmount34" placeholder="Credit Authorization Amount" required></div>
                        </div>
                                            
            
                        <div class="clearfix"></div>
                            
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="addRow34" class="btn btn-primary"  style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div>

	<!-- table7 Equation->



<!-- Table tableEQGROUP -->
	
	
		<div class="modal fade EQGROUP tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="font-weight:600;">Equation Group</h4>
            </div>
            <div class="modal-body">
                        
                        <div class="clearfix"></div>
                        <div class="form-row">
                        <div class="col-md-4"><label for="ApplicationName">Option ID: </label></div>
                        <div class="col-md-4"> 
                        
                    
							<input list="option1" name="OptionId" id="OptionId">
							<datalist id="option1">
<% 							
								Map<String, String> lookupValues = pa.getLookupMap("Lookup.EquationGrp.Options", oimClient);
			
			for(Map.Entry<String, String> lookupEntrySet: lookupValues.entrySet() )
			{
				out.write("<option value='"+lookupEntrySet.getKey()+"' label='"+lookupEntrySet.getValue()+"'>"+lookupEntrySet.getValue()+"</option>"); 
				
			}
  		%>					
				
								
							</datalist>

				         <!-- input type="text" class="form-control" id="UserGroup" placeholder="User Group" required!--></div>
                        
                        
                        
                        </div>
                    
            
                        <div class="clearfix"></div>
                            
            </div>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               <input type="button" id="addRowEQGROUP" class="btn btn-primary"  style="Background-color:#009591" value ="Submit"></input>
            </div>
        </div>
    </div>
</div>
	<!-- Table tableEQGROUP->


	
		<!-- END WRAPPER -->
		<!-- Javascript -->
		<script src="assets/vendor/jquery/jquery.min.js"></script>
		<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
		<script src="assets/scripts/klorofil-common.js"></script>
		
		<script type="text/javascript">
    document.getElementById("previousButton").onclick = function () {
        window.location = 'makerequest.jsp';
    };
    function addRow()
            {
            console.log("add row clicked");
                 // get input values
                var UserBranch  = document.getElementById('UserBranch').value;
                 var BranchNumber = document.getElementById('BranchNumber').value;
                  var UserClass = document.getElementById('UserClass').value;
                    var LanguageCode = document.getElementById('LanguageCode').value;
                      var DateLastMaintained = document.getElementById('DateLastMaintained').value;
                        var UserGroup = document.getElementById('UserGroup').value;
                  // get the html table
                  // 0 = the first table
                  
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
                  // add values to the cells
                  cel1.innerHTML = UserBranch;
                  cel2.innerHTML = BranchNumber;
                  cel3.innerHTML = UserClass; 
                  cel4.innerHTML = LanguageCode;
                  cel5.innerHTML = DateLastMaintained;
                  cel6.innerHTML = UserGroup;
                  alert("Group Added Successfully");
            }
    
    
    $(document).ready(function() {
    
    	$("#addRow26").click(function() {
              //var table = document.getElementById("table");
              console.log("table clicked")
            console.log("add row clicked");
                 var rows11 = document.getElementById('table26').rows.length;
					
					if(rows11 > 1){
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
                  var UserClass = document.getElementById('UD_EQUATION_USER_CLASS').value;
                    var LanguageCode = document.getElementById('LanguageCode').value;
                      var DateLastMaintained = document.getElementById('DateLastMaintained').value;
                        var UserGroup = document.getElementById('UserGroup').value;
                  // get the html table
                  // 0 = the first table
                  var rows11 = document.getElementById('table26').rows.length;
					
					if(UserClass=='' || UserClass==null)
						{
						alert("Please Add user First class in Request Details Equation");
						return;
						}
					if(rows11 > 1){
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
                  cel7.innerHTML = "<button class='label label-danger' ><i class='fa fa-remove'></i></button>";
                  
                  				
                  alert("Group Added Successfully");
              
              
              //$('#table tr:last').after('<tr>aa</tr><tr>aa</tr>');
          //    var x=document.getElementById("#table").tBodies[0];  //get the table
      //var node=t.rows[0].cloneNode(true);   //clone the previous node or row
      //x.appendChild(node);   //add the node or row to the t
 
	    	  
	    	}
		else
    	alert("Invalid Group Name");
                 
                 
            
 	});
 	
 	$("#addRow27").click(function() {
              //var table = document.getElementById("table");
              console.log("table clicked")
            console.log("add row 27 clicked");
                 // get input values
                var DefaultUserLimit   = document.getElementById('DefaultUserLimit').value;
                 var CurrencyMnemonic = document.getElementById('CurrencyMnemonic').value;
                  var DateLastMaintained27 = document.getElementById('DateLastMaintained27').value;
                    var ApplicationCode = document.getElementById('ApplicationCode').value;
                  // get the html table
                  // 0 = the first table
                  
                  //var table = document.getElementsByTagName('table')[1];
                  var table = document.getElementById('table27');
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
                  // add values to the cells
                  cel1.innerHTML = DefaultUserLimit;
                  cel2.innerHTML = CurrencyMnemonic;
                  cel3.innerHTML = DateLastMaintained27; 
                  cel4.innerHTML = ApplicationCode;
                  cel5.innerHTML = "<button class='label label-danger' ><i class='fa fa-remove'></i></button>";
                  
                  				
                  alert("Data Added Successfully");
              
              
              //$('#table tr:last').after('<tr>aa</tr><tr>aa</tr>');
          //    var x=document.getElementById("#table").tBodies[0];  //get the table
      //var node=t.rows[0].cloneNode(true);    //clone the previous node or row
      //x.appendChild(node);   //add the node or row to the t
 
 	});
 	
 		$("#addRow7").click(function() {
              //var table = document.getElementById("table");
              console.log("table clicked")
            console.log("add row 7 clicked");
            
            console.log("table clicked")
            console.log("add row clicked");
                 var rows11 = document.getElementById('table7').rows.length;
					
					if(rows11 > 1){
 					  alert("Maximun 1 Row can be added");
 					  return;
						}
                 // get input values
                var UniteServer   = document.getElementById('UniteServer').value;
                 var InitialMenu = document.getElementById('InitialMenu').value;
                  var AuthorisedUnit = document.getElementById('AuthorisedUnit').value;
                   
                  // get the html table
                  // 0 = the first table
                  
                  //var table = document.getElementsByTagName('table')[1];
                  var table = document.getElementById('table7');
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
                  
                  // add values to the cells
                  cel1.innerHTML = UniteServer;
                  cel2.innerHTML = InitialMenu;
                  cel3.innerHTML = AuthorisedUnit; 
                  cel4.innerHTML = "<button class='label label-danger' ><i class='fa fa-remove'></i></button>";
                  
                  				
                  alert("Data Added Successfully");
              
              
              //$('#table tr:last').after('<tr>aa</tr><tr>aa</tr>');
          //    var x=document.getElementById("#table").tBodies[0];  //get the table
      //var node=t.rows[0].cloneNode(true);    //clone the previous node or row
      //x.appendChild(node);   //add the node or row to the t
 
 	});
 	
 	$("#addRow34").click(function() {
              //var table = document.getElementById("table");
              console.log("table clicked")
            console.log("add row 34 clicked");
                 // get input values
                var ProcessInterBranch34   = document.getElementById('ProcessInterBranch34').value;
                 var InterBranchDebit34 = document.getElementById('InterBranchDebit34').value;
                  var ResidentBranch34 = document.getElementById('ResidentBranch34').value;
                   var InterBranchCredit34   = document.getElementById('InterBranchCredit34').value;
                 var BranchNumber34 = document.getElementById('BranchNumber34').value;
                  var LocalCreditAmount34 = document.getElementById('LocalCreditAmount34').value;
                  var LocalDebitAmount34   = document.getElementById('LocalDebitAmount34').value;
                 var DebitAuthorizationAmount34 = document.getElementById('DebitAuthorizationAmount34').value;
                  var CreditAuthorizationAmount34 = document.getElementById('CreditAuthorizationAmount34').value;
                  // get the html table
                  // 0 = the first table
                  
                  //var table = document.getElementsByTagName('table')[1];
                  var table = document.getElementById('table34');
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
                  var cel8 = newRow.insertCell(7);
                  var cel9 = newRow.insertCell(8);
                  var cel10 = newRow.insertCell(9);
                  // add values to the cells
                  cel1.innerHTML = ProcessInterBranch34;
                  cel2.innerHTML = InterBranchDebit34;
                  cel3.innerHTML = ResidentBranch34;
                  cel4.innerHTML = InterBranchCredit34;
                  cel5.innerHTML = BranchNumber34;
                  cel6.innerHTML = LocalCreditAmount34;
                  cel7.innerHTML = LocalDebitAmount34;
                  cel8.innerHTML = DebitAuthorizationAmount34;
                  cel9.innerHTML = CreditAuthorizationAmount34; 
                  cel10.innerHTML = "<button class='label label-danger' ><i class='fa fa-remove'></i></button>";
                  
                  				
                  alert("Data Added Successfully");
              
              
              //$('#table tr:last').after('<tr>aa</tr><tr>aa</tr>');
          //    var x=document.getElementById("#table").tBodies[0];  //get the table
      //var node=t.rows[0].cloneNode(true);    //clone the previous node or row
      //x.appendChild(node);   //add the node or row to the t
 
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
                  // add values to the cells
                  cel1.innerHTML = SpecialAuthority;
                  cel2.innerHTML = "<button class='label label-danger' ><i class='fa fa-remove'></i></button>";
                  
                  				
                  alert("Data Added Successfully");
              
              
              //$('#table tr:last').after('<tr>aa</tr><tr>aa</tr>');
          //    var x=document.getElementById("#table").tBodies[0];  //get the table
      //var node=t.rows[0].cloneNode(true);    //clone the previous node or row
      //x.appendChild(node);   //add the node or row to the t
 
 	});
 
 	
 		$("#addRowCSG").click(function() {
              //var table = document.getElementById("table");
              console.log("table clicked")
            console.log("add row clicked");
                 var rows11 = document.getElementById('tableCSG').rows.length;
					
					if(rows11 > 1){
 					  alert("Maximun 1 Row can be added");
 					  return;
						}
                var SupplementalGroup  = document.getElementById('SupplementalGroup').value;
                  // get the html table
                  // 0 = the first table
                  var rows11 = document.getElementById('tableCSG').rows.length;
					
					if(rows11 > 1){
 					  alert("Maximun 1 Row can be added");
 					  return;
						}
						
                var val = $("#SupplementalGroup").val();
				console.log("UserGroup11:"+val);
                 var obj = $("#UserGroup2").find("option[value='" + val + "']");

		if(obj != null && obj.length > 0)
	    	
	    	{  
                  //var table = document.getElementsByTagName('table')[1];
                  var table = document.getElementById('tableCSG');
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
                  // add values to the cells
                  cel1.innerHTML = SupplementalGroup;
                  cel2.innerHTML = "<button class='label label-danger' ><i class='fa fa-remove'></i></button>";
                  
                  				
                  alert("Supplemetal Group Added Successfully");
              
             }
             else{
             alert("Invalid Group Name");
             } 
              //$('#table tr:last').after('<tr>aa</tr><tr>aa</tr>');
          //    var x=document.getElementById("#table").tBodies[0];  //get the table
      //var node=t.rows[0].cloneNode(true);    //clone the previous node or row
      //x.appendChild(node);   //add the node or row to the t
 
 	});
 
 	$("#addRowEQGROUP").click(function() {
              //var table = document.getElementById("table");
              console.log("table clicked")
            console.log("add row clicked");
                 
                 
                 // get input values
               
            	 
                //var OptionId  = document.getElementById('OptionId').val();
                 var OptionId =  $('[name="OptionId"]').val();		
                  
                  
                  var allCells = $('#tableEQGROUP tr');
                  //alert(allCells);
var textMapping = {};
allCells.each(function() {
    textMapping[$(this).text()] = true;
    
});

var count = 1;
for (var text in textMapping){
    count++;
   // alert(text)
    
if(text ==OptionId ){
alert("Duplicate Group can not be added");

return;}}
/*
if (count !== allCells.length) {
    alert("Duplicate Group can not be added");
    return;
} else {
    alert("no duplicates found");
}
  */                
                  
                  
                  
               var val = $("#OptionId").val();
				console.log("OptionId:"+val);
                 var obj = $("#option1").find("option[value='" + val + "']");

		if(obj != null && obj.length > 0){   
                  
                  //var table = document.getElementsByTagName('table')[1];
                  var table = document.getElementById('tableEQGROUP');
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
                  // add values to the cells
                  cel1.innerHTML = OptionId;
                  
                  cel2.innerHTML = "<button class='label label-danger'><i class='fa fa-remove'></i></button>";
                  
                  				
                  alert("Option ID Added");
              
              
              //$('#table tr:last').after('<tr>aa</tr><tr>aa</tr>');
          //    var x=document.getElementById("#table").tBodies[0];  //get the table
      //var node=t.rows[0].cloneNode(true);    //clone the previous node or row
      //x.appendChild(node);   //add the node or row to the t
 }
 else{alert("Please enter valid Option ID");}
 	});
 
 	
 	
 	});
 				
</script>
<script>
    		 //$(document).ready(function() {
    		 $(document).on("click","[name='table26Remove']", function (e) {
            var index;
            var table = document.getElementById('table26');
            console.log("index:"+index);
            console.log("table:"+table);
           
            for(var i = 1; i < table.rows.length; i++)
            {
            
                table.rows[i].cells[6].onclick = function()
                {
                    var c = confirm("do you want to delete this row");
                    if(c === true)
                    {
                        index = this.parentElement.rowIndex;
                        table.deleteRow(index);
                    }
                    
                    //console.log(index);
                };
                
                
            }
    });
    
     $(document).on("click","[name='table27Remove']", function (e) {
            var index;
            var table = document.getElementById('table27');
            console.log("index:"+index);
            console.log("table:"+table);
           
            for(var i = 1; i < table.rows.length; i++)
            {
            
                table.rows[i].cells[4].onclick = function()
                {
                    var c = confirm("do you want to delete this row");
                    if(c === true)
                    {
                        index = this.parentElement.rowIndex;
                        table.deleteRow(index);
                    }
                    
                    //console.log(index);
                };
                
                
            }
    });
    
    $(document).on("click","[name='table7Remove']", function (e) {
            var index;
            var table = document.getElementById('table7');
            console.log("index:"+index);
            console.log("table:"+table);
           
            for(var i = 1; i < table.rows.length; i++)
            {
            
                table.rows[i].cells[3].onclick = function()
                {
                    var c = confirm("do you want to delete this row");
                    if(c === true)
                    {
                        index = this.parentElement.rowIndex;
                        table.deleteRow(index);
                    }
                    
                    //console.log(index);
                };
                
                
            }
    });
    
    $(document).on("click","[name='table34Remove']", function (e) {
            var index;
            var table = document.getElementById('table34');
            console.log("index:"+index);
            console.log("table:"+table);
           
            for(var i = 1; i < table.rows.length; i++)
            {
            
                table.rows[i].cells[9].onclick = function()
                {
                    var c = confirm("do you want to delete this row");
                    if(c === true)
                    {
                        index = this.parentElement.rowIndex;
                        table.deleteRow(index);
                    }
                    
                    //console.log(index);
                };
                
                
            }
    });
    
     $(document).on("click","[name='tableCSPRemove']", function (e) {
            var index;
            var table = document.getElementById('tableCSP');
            console.log("index:"+index);
            console.log("table:"+table);
           
            for(var i = 1; i < table.rows.length; i++)
            {
            
                table.rows[i].cells[1].onclick = function()
                {
                    var c = confirm("do you want to delete this row");
                    if(c === true)
                    {
                        index = this.parentElement.rowIndex;
                        table.deleteRow(index);
                    }
                    
                    //console.log(index);
                };
                
                
            }
    });
     $(document).on("click","[name='tableCSGRemove']", function (e) {
            var index;
            var table = document.getElementById('tableCSG');
            console.log("index:"+index);
            console.log("table:"+table);
           
            for(var i = 1; i < table.rows.length; i++)
            {
            
                table.rows[i].cells[1].onclick = function()
                {
                    var c = confirm("do you want to delete this row");
                    if(c === true)
                    {
                        index = this.parentElement.rowIndex;
                        table.deleteRow(index);
                    }
                    
                    //console.log(index);
                };
                
                
            }
    });
    
    
    $(document).on("click","[name='tableEQGROUPRemove']", function (e) {
            var index;
            var table = document.getElementById('tableEQGROUP');
            console.log("index:"+index);
            console.log("table:"+table);
           
            for(var i = 1; i < table.rows.length; i++)
            {
            
                table.rows[i].cells[1].onclick = function()
                {
                    var c = confirm("do you want to delete this row");
                    if(c === true)
                    {
                        index = this.parentElement.rowIndex;
                        table.deleteRow(index);
                    }
                    
                    //console.log(index);
                };
                
                
            }
    });
    
        </script>
</body>

</html>
