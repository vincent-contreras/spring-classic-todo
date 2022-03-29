<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="../../resources/font.css" rel="stylesheet" type="text/css" />
	<style>
		.write{
			margin: 30px;
			text-align: center;
			font-family: "Helvetica";
		}
		h1{
			font-family: "Helvetica" !important;
			font-size: 40px !important;
			font-weight: bold !important; 
		}
		h2{
			font-size: 30px;
			font-family: "Helvetica" !important; 
		}
		.note-frame card{
			postion: realtive;
			margin: 0 auto;
		}
		.note-editor{
			postion: realtive;
			margin: 0 auto;
		}
		.p{
			padding: 5px 0px 5px 0px;
			font-weight: bold;
			font-size: 18px;
			color: #337AB7;
		}
		.button{
			font-family: "Helvetica";
			font-size: 18px;
			padding: 5px 5px;
			color: white;
			background-color: #424343 !important;
			border: none;
			margin: 5px 5px;
			border-radius: 5px;
			cursor: pointer;
			width: 70px;
			height: 40px;
		}
		#cancelBtn{
			background-color: #CA3E3E !important;
		}
	</style>
	
	<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 
	<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote-bs4.css" rel="stylesheet">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote-bs4.js"></script>

	
	<title>Write</title>
</head>
<body>
	<div class="write">
		<form action="${pageContext.request.contextPath}/writeProcess.do" onsubmit="return doAlert()" method="post" enctype="multipart/form-data">
			<h1> To Do Item </h1>
			<hr>
			<h2> Write To Do Item </h2>
			<br><br>
			<a style="font-size:17px; font-weight:bold;">Author |&emsp;</a> <b>${sessionScope.userId}</b>&emsp;
			<br>
			<input type='text' style="width:500px; margin:10px 0px 10px 0px;" placeholder="Title" name='boardTitle'>
			<br>
			<div>
			<textarea id='summernote' name='boardContent'></textarea>
			</div>
			<br>
			<input type="submit" value="Save" class="button">&emsp;
			<input type="button" value="Discard" class="button" id="cancelBtn" onclick="location.href='${pageContext.request.contextPath}/main.do'">
		</form>
	</div>

<script>
	function doAlert(){
		alert("Created new item successfully");
	}
	
	$(function() {
		$('#summernote').summernote({
		        height: 300,
		        width: 800,
			    minHeight: 370,
			    maxHeight: null,
			    focus: true, 
		        lang: 'ko-KR',
		        toolbar: [
				    ['fontname', ['fontname']],
				    ['fontsize', ['fontsize']],
				    ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
				    ['color', ['forecolor','color']],
				    ['table', ['table']],
				    ['para', ['ul', 'ol', 'paragraph']],
				    ['height', ['height']],
				    ['insert',['picture','link','video']],
				    ['view', ['fullscreen', 'help']]
				  ],
				fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋음체','바탕체'],
				fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72'],
		        callbacks: {
		        	onImageUpload: function(files, editor, welEditable) {
		        		for(var i = files.length -1; i>=0; i--) {
		        			sendFile(files[i], this);
		        		}
		        	}
		        }
		 });
	});
	
</script>
</body>
</html>