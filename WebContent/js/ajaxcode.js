console.log("Test Ajx Calls");
$(document).ready(function(){
	$('#btnCreateUser').click(function(){
		console.log("Clicked");
		var userFirstName = $('#userFirstName').val();
		var userMiddleName = $('#userMiddleName').val();
		var userlastName = $('#userlastName').val();
		var userEmail = $('#userEmail').val();
		var userLogin = $('#userLogin').val();
		var userPassword = $('#userPassword').val();
		var confirmPassword = $('#confirmPassword').val();
		var userFullName = $('#userFullName').val();
		var ManagerEmpNum = $('#ManagerEmpNum').val();
		var userMobileNumber = $('#userMobileNumber').val();
		var userState = $('#userState').val();
		var userStreet = $('#userStreet').val();
		var userCity = $('#userCity').val();
		var userCountry = $('#userCountry').val();
		var userPersonalNumber = $('#userPersonalNumber').val();
		var userTitle = $('#userTitle').val();
		var userRegionCode = $('#userRegionCode').val();
		var userRegionName = $('#userRegionName').val();
		var userDepartment = $('#userDepartment').val();
		var userBranchCode = $('#userBranchCode').val();
		//var userLineManager = $("#userLineManager :selected").val();
		var userLineManager = $('[name="userLineManager"]').val(); 
		var userOrganization = $('[name="userOrganizations"]').val();
		//var userOrganization = $('#userOrganizations').val();
		//var userLineManager = $('#userLineManager').find(":selected").text();
		var Descrption = $('#Descrption').val();
		console.log(userOrganization);
		 $.ajax({
			type: 'POST',
			data:{userFirstName:userFirstName,
				userMiddleName:userMiddleName,
				userlastName:userlastName,
				userEmail:userEmail,
				userLogin:userLogin,
				userPassword:userPassword,
				confirmPassword:confirmPassword,
				userFullName:userFullName,
				ManagerEmpNum:ManagerEmpNum,
				userMobileNumber:userMobileNumber, 
				userState:userState,
				userStreet:userStreet,
				userCity:userCity,
				userCountry:userCountry,
				userPersonalNumber:userPersonalNumber,
				userTitle:userTitle,
				userRegionCode:userRegionCode,
				userRegionName:userRegionName,
				userDepartment:userDepartment,
				userBranchCode:userBranchCode,
				userLineManager:userLineManager,
				Descrption:Descrption,
				userOrganization:userOrganization,
				action:"crateUser"},
				url: 'UserCreateController',
				success: function(result){
					//console.log(result.firstName);
					//console.log(result.lastName);
					//console.log(result.userLogin);
					console.log(result);
					
				},
			    error: function(result)
			    {
			    	console.log(result);
			    }  
		}); 
		});
		 
});
