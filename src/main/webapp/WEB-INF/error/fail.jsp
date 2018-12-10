<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>失败信息</title>
	<script src="${pageContext.request.contextPath }/static/jquery/jquery-3.3.1.min.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
	<script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
</head>

<body>
	<div class="container-fluid">
		<div style="text-align: center;">
			<h3>${fail }</h3>
			<h3>请返回操作</h3>
			<button class="btn btn-info" onclick="history.go(-1)">返回</button>
		</div>
	</div>
</body>
</html>