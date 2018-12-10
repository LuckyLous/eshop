<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<html>
<head>
    <meta charset="UTF-8">
    <title>一级分类列表</title>
	<!-- 引入bootstrap样式 -->
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-3.3.1.min.js"></script>
    <link href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <!-- 自定义css -->	
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/index.css" type="text/css" />

	<script type="text/javascript">
        // 删除按钮的点击事件=>弹出确认模态框，并将url赋值到隐藏域中
        function confirmDialog(url) {
            $("#confirmModal").modal({
                backdrop:"static"
            });
            $("#url").val(url);
        }
        // 确认框中确认按钮的点击事件
        function urlSubmit() {
            var url=$.trim($("#url").val());//获取会话中的隐藏属性URL
            window.location.href=url;
        }

	</script>
</head>
<body>
<!--确认框div>
<%@ include file="/admin/common/confirm.jsp" %>
<%@ include file="/admin/common/nav-bar.jsp" %>
<div class="pageContainer">
	<%@ include file="/admin/common/nav-link.jsp" %>
	<!--右侧主界面-->
	<div class="pageContent" style="margin: 0px 20px ;">
    	<!-- 标题 -->
	 	<div class="row">
	 		<div class="col-md-12">
	 			<h2>一级分类列表</h2>
	 		</div>
	 	</div>
	 	<!-- 基础操作按钮 -->
	 	<div class="row">
	 		<div class="col-md-12">
	 			<a href="${pageContext.request.contextPath }/admin/category/add.jsp" class="btn btn-primary">
        			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
		 			新增
        		</a>
	 		</div>
	 	</div>
       <!-- 显示数据列表  -->
        <div class="row">
        	<div class="col-md-12">
	            <table class="table table-hover">
	                <tr>
	                    <th>序号</th>
	                    <th>一级分类名称</th>
	                    <th>操作</th>
	                </tr>
	                <c:forEach items="${list }" var="cate" varStatus="status">
	                	<tr>
		                	<td>${status.count }</td>
		                    <td>${cate.name }</td>
		                    <td>
		                        <a href="${pageContext.request.contextPath }/admin/category/update/${cate.id}" class="btn btn-primary btn-sm">
			 						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
			 						修改
			 					</a>
		                        <button onclick="confirmDialog('${pageContext.request.contextPath }/admin/category/delete/${cate.id}')"
										class="btn btn-warning btn-sm">
				 					<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
				 					删除
				 				</button>
		                  	</td>
	                	</tr>
	                </c:forEach>
	            </table>
            </div>
        </div>
    </div>
</div>
<%@ include file="/admin/common/footer.jsp" %>
</body>
</html>