<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form style="width:97%;">
  <div class="form-group row">
    <label for="currentPassword" class="col-sm-3 col-form-label">Current Password</label>
    <div class="col-sm-9">
      <input type="password" class="form-control" id="currentPassword" placeholder="Enter Current Password" required>
    </div>
  </div>
  <div class="form-group row">
    <label for="newPassword" class="col-sm-3 col-form-label">New Password</label>
    <div class="col-sm-9">
      <input type="password" class="form-control" id="newPassword" placeholder="Enter New Password" required>
    </div>
  </div>
  <div class="form-group row">
    <label for="confirm Password" class="col-sm-3 col-form-label">Confirm Password</label>
    <div class="col-sm-9">
      <input type="password" class="form-control" id="confirmPassword" placeholder="Enter Confirm Password" required>
    </div>
  </div>
  <div class="form-group row">
    <div class="col-sm-9">
      <button type="submit" id="btnChangePassword" class="btn btn-primary" style="Background-color:#009591">Submit</button>
    </div>
  </div>
  <div class="row">
  <span id="result1"></span>
  </div>
</form>

</body>
</html>