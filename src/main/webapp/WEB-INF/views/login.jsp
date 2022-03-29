<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="../../resources/font.css" rel="stylesheet" type="text/css" />
	<style>
		.login{
			text-align: center;
			font-family: "Helvetica";
		}
		h1{
			font-family: "Helvetica";
			font-size: 40px;
		}
		h2{
			font-size: 30px;
		}
		.loginBtn{
			font-family: "Helvetica";
			font-size: 15px;
			padding: 5px 5px;
			color: white;
			background-color: #424343 !important;
			border: none;
			margin: 5px 8px;
			border-radius: 5px;
			cursor: pointer;
			width: 70px;
			height: 40px;
		}
	</style>
	<title>Login</title>
</head>

<body>
<div class="login">
	<form action="${pageContext.request.contextPath}/loginProcess.do" method="post">
		<h1> To Do </h1>
		<hr>
		<h2> Login </h2>
		<label>Username&ensp;&ensp;</label>
		<input type='text' style="width:100px; margin:5px 0px 5px 0px;" name='userId'>
		<br>
		<label>Password&nbsp;</label>
		<input type='password' style="width:100px; margin:5px 0px 5px 0px;" name='password'>
		<br><br>
		<input type="submit" value="Login" class="loginBtn">&emsp;
	</form>
</div>
</body>
</html>