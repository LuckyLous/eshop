<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>索引库管理</title>
	<!-- bootstrap -->
	<script src="${pageContext.request.contextPath }/static/jquery/jquery-3.3.1.min.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
	<script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
	<!-- 自定义css -->	
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/index.css" type="text/css" />
	<script type="text/javascript">
		function importItems(){
	    	$.ajax({
				url:"${pageContext.request.contextPath }/admin/search/import",
				type:"POST",
				success:function(result){
					if(result.code == 100){
						alert("提示，导入索引库成功");
					}else{
						alert("提示，导入索引库失败");
					}
				}
			});
	    }
	</script>
</head>
<body>
	<%@ include file="/admin/common/nav-bar.jsp" %>
<!-- 中间主体内容部分 -->
<div class="pageContainer">
	<%@ include file="/admin/common/nav-link.jsp" %>
     <!-- 正文内容部分 -->
     <div class="pageContent">
     	<button onclick="importItems()" class="btn btn-primary">
      		<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
 			一键导入商品数据到索引库
        </button>
     </div>
 </div>
	<%@ include file="/admin/common/footer.jsp" %>
</body>
</html>