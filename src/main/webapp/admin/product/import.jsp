<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据导入</title>
	<!-- bootstrap -->
	<script src="${pageContext.request.contextPath }/static/jquery/jquery-3.3.1.min.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
	<script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
	<!-- 自定义css -->	
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/index.css" type="text/css" />
	<script type="text/javascript">
		function downloadTemplate() {
			$(location).attr("href","${pageContext.request.contextPath }/admin/product/download");
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
		 <!--模态框开始-->
		 <div class="modal-dialog" role="document"  style="width: 600px; height: 500px;text-align: center;">
			 <!-- 模态框标题 -->
			 <div class="modal-header">
				 <h4 class="modal-title" id="myModalLabel">数据导入</h4>
			 </div>
			 <!-- 模态框body -->
			 <div class="modal-body">
				 <form class="form-horizontal" method="post" enctype="multipart/form-data"
					   action="${pageContext.request.contextPath }/admin/product/import ">
					 <!-- 操作 -->
					 <div class="form-group">
						 <div class="col-sm-6">
							 <label >下载模板</label>
							 <input type="button" class="form-control col-md-4" onclick="downloadTemplate()" value="导入模板" />
						 </div>
						 <div class="col-sm-6">
							 <label >上传文件</label>
							 <input type="file" class="form-control" name="userUploadFile" />
						 </div>
					 </div>
					 <!-- 模态框操作 -->
					 <div class="modal-footer">
						 <button type="button" class="btn btn-default" onclick="history.go(-1)" data-dismiss="modal">
							 返回
						 </button>
						 <button onclick="confirmDialog()" type="button" class="btn btn-primary" >上传</button>
					 </div>
				 </form>
			 </div>
		 </div>
     </div>
 </div>
	<%@ include file="/admin/common/footer.jsp" %>
</body>
</html>