<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="refresh" content="3;url=${pageContext.request.contextPath }/admin/${newUrl}" />
<title>系统提示</title>
	<!-- bootstrap -->
	<script src="${pageContext.request.contextPath }/static/jquery/jquery-3.3.1.min.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
	<script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
	<!-- 自定义css -->	
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/index.css" type="text/css" />
	<script type="text/javascript">
		$(".nav li").click(function() {
		    $(".active").removeClass('active');
		    $(this).addClass("active");
		});

	</script>
</head>
<body>
	<%@ include file="/admin/common/nav-bar.jsp" %>
<!-- 中间主体内容部分 -->
<div class="pageContainer">
	<%@ include file="/admin/common/nav-link.jsp" %>
     <!-- 正文内容部分 -->
     <div class="pageContent">
		 <div style="text-align: center;">
			 <h3>${msg }</h3>
			 <h3>3秒钟后自动跳转到新页面...</h3>
			 <a class="btn btn-info" href="${pageContext.request.contextPath }/admin/${newUrl}">确定</a>
		 </div>
     </div>
 </div>
	<%@ include file="/admin/common/footer.jsp" %>
</body>
</html>