<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="UTF-8">
	<link href="../../resources/font.css" rel="stylesheet" type="text/css" />
	<style>
		.form{
			margin: 30px;
			text-align: center;
			font-family: "Helvetica";
		}
		h1{
			font-family: "Helvetica";
			font-size: 40px;
		}
		#button{
			font-family: "Helvetica";
			font-size: 20px;
			padding: 15px 25px;
			color: white;
			border: none;
			background-color: 424343;
			margin: 150px 30px;
			border-radius: 5px;
			cursor: pointer;
		}
		#button:hover{
			color: 424343;
			background-color: white;
		}
	</style>
	<title>Home</title>
</head>

<body>
<div class="form">
	<h1>To Do</h1>
	<P> Current Time : ${serverTime} </P>
	<hr>
	<c:if test="${request.getSession()==null}">
		<input type="button" id="button" value="Login" onclick="location.href='${pageContext.request.contextPath}/login.do'">
		<input type="button" id="button" value="Register" onclick="location.href='${pageContext.request.contextPath}/join.do'">
	</c:if>
	<hr>
</div>
</body>
</html>
