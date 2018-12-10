<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>异常状况</title>
	<script src="${pageContext.request.contextPath }/static/jquery/jquery-3.3.1.min.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
	<script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
</head>

<body>
	<div class="container-fluid">
		<div style="text-align: center;">
			<h3>亲，不好意思，发生了一些异常状况!</h3>
			<h3>请进行稍后再试或进行其他操作</h3>
			<button class="btn btn-info" onclick="history.go(-1)">返回</button>
		</div>
	</div>
</body>
</html>