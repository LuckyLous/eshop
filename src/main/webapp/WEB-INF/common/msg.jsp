<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="refresh" content="3;url=${pageContext.request.contextPath }/${newUrl}" />
		<title>前台提示</title>
		<script src="${pageContext.request.contextPath }/static/jquery/jquery-3.3.1.min.js" type="text/javascript"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
	</head>

	<body>
	<!-- 引入header.jsp -->
	<jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>

	<div class="container">
		<div style="text-align: center;">
			<h3>${msg }</h3>
			<h3>3秒钟后自动跳转到新页面...</h3>
		</div>
		<div style="text-align: center;">
			<a href="${pageContext.request.contextPath }/${newUrl}" class="btn btn-info" >确定</a>
			<button class="btn btn-success" onclick="history.go(-1)">返回</button>
		</div>
	</div>
	<!-- 引入footer.jsp -->
	<jsp:include page="/WEB-INF/common/footer.jsp"></jsp:include>

	</body>

</html>