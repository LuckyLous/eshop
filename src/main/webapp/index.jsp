<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 虽然jsp的转发可以直接访问WEB-INF下面资源，但是这里选择匹配到url，由控制器转发，不由jsp转发 -->
	<jsp:forward page="/index"></jsp:forward>
</body>
</html>