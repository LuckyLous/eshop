<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会员列表</title>
	<!-- 引入jquery -->
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-3.3.1.min.js"></script>
	<!-- 引入bootstrap样式 -->
    <link href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <!-- 自定义css -->	
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/index.css" type="text/css" />
    <!-- 自定义的js -->
    <script type="text/javascript">
		// 页面加载完毕
 		$(function(){
 			// 按照激活状态查询不同的会员
 			$("#search_user").change(function(){
 				$(location).attr("href","${pageContext.request.contextPath}/admin/user/list?page=1&status="+$("#search_user").val());
 			});

 		});

		// 点击事件=>弹出确认模态框，并将url赋值到隐藏域中
		function confirmDialog(url) {
            $("#confirmModal").modal({
                backdrop:"static"
            });
            if(url.indexOf("delete") >= 0){
                $("#myModalBody").text("确认删除该会员吗？");
            }
            $("#url").val(url);
        }
        // 确认框中确认按钮的点击事件
        function urlSubmit() {
            var url=$.trim($("#url").val());//获取会话中的隐藏属性URL
            $(location).attr("href",url);
        }

	</script>
</head>
<body>
<!--确认框div>
<%@ include file="/admin/common/confirm.jsp" %>
<%@ include file="/admin/common/nav-bar.jsp" %>
<div class="pageContainer">
	<%@ include file="/admin/common/nav-link.jsp" %>
	<!-- 搭建显示页面 -->
	<div class="pageContent" style="margin: 0px 20px ;"> 
	 	<!-- 标题 -->
	 	<div class="row">
	 		<div class="col-md-12">
	 			<h2>会员列表</h2>
	 		</div>
	 	</div>
	 	<!-- 基础操作按钮 -->
	 	<div class="row">
			<div class="col-sm-2">
				<form class="form-inline" action="#">
					  <div class="form-group">
						<label>会员状态</label>
						<select id="search_user" name="status" class="form-control">
							<option value="" <c:if test="${empty param.status }">selected</c:if> >所有</option>
						  	<option value="1" <c:if test="${param.status == 1 }">selected</c:if> >已激活</option>
						  	<option value="0" <c:if test="${not empty param.status and param.status == 0 }">selected</c:if> >未激活</option>
						</select>
					  </div>
				</form>
			</div>

	 	</div>
		<!-- 显示表格数据 -->
	 	<div class="row">
	 		<div class="col-md-12">
	 			<table class="table table-hover">
	 				<tr>
	 					<th>序号</th>
	 					<th>用户名</th>
	 					<th>邮箱</th>
	 					<th>昵称</th>
	 					<th>手机号码</th>
	 					<th>收货地址</th>
	 					<th>激活状态</th>
	 					<th>操作</th>
	 				</tr>
	 				
	 				<c:forEach items="${pageInfo.list }" var="user" varStatus="status">
	 					<tr>
		 					<td>${status.count }</td>
		 					<td>${user.username }</td>
							<td>${user.email}</td>
							<td>${user.nickname}</td>
							<td>${user.phone}</td>
							<td>${user.address}</td>
		 					<td>
		 						<c:if test="${user.status == 0 }">
		 							未激活
		 						</c:if>
		 						<c:if test="${user.status == 1 }">
		 							已激活
		 						</c:if>
		 					</td>
		 					<td>
		 						<c:if test="${user.status == 0 }">
			 						<button onclick="confirmDialog('${pageContext.request.contextPath}/admin/user/delete/${user.id}?page=${param.page}')"
											class="btn btn-warning btn-sm">
										<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
			 							删除
			 						</button>
		 						</c:if>
		 					</th>
	 					</tr>
	 				</c:forEach>
	 			</table>
	 		</div>
	 	</div>
	 	<!-- 显示分页信息 -->
	 	<div class="row">
	 		<!-- 分页文字信息 -->
	 		<div class="col-md-6">
	 			当前${pageInfo.pageNum }页,
	 			总${pageInfo.pages }页,
	 			共${pageInfo.total }条记录
	 			
	 		</div>
	 		<!-- 分页条信息 -->
	 		<div class="col-md-6">
				<nav aria-label="Page navigation">
				<ul class="pagination">
					<c:if test="${pageInfo.isFirstPage  }">
						<li class="disabled"><a href="javascript:void(0)" aria-label="Previous">首页</a></li>
					</c:if>
					<c:if test="${not pageInfo.isFirstPage }">
						<li><a href="${pageContext.request.contextPath }/admin/user/list?page=1&status=${param.status}">首页</a></li>
					</c:if>
					<c:if test="${pageInfo.hasPreviousPage }">
						<li>
							<a href="${pageContext.request.contextPath }/admin/user/list?page=${pageInfo.prePage }&status=${param.status}" aria-label="Previous">
								<span aria-hidden="true">上一页</span>
							</a>
						</li>
					</c:if>

					<c:forEach items="${pageInfo.navigatepageNums }" var="pageNum">
						<c:if test="${pageNum == pageInfo.pageNum }">
							<li class="active"><a href="#">${pageNum }</a></li>
						</c:if>
						<c:if test="${pageNum != pageInfo.pageNum }">
							<li><a href="${pageContext.request.contextPath }/admin/user/list?page=${pageNum }&status=${param.status}">${pageNum }</a></li>
						</c:if>
					</c:forEach>

					<c:if test="${pageInfo.hasNextPage }">
						<li>
							<a href="${pageContext.request.contextPath }/admin/user/list?page=${pageInfo.nextPage }&status=${param.status}" aria-label="Next">
								<span aria-hidden="true">下一页</span>
							</a>
						</li>
					</c:if>
					<c:if test="${not pageInfo.isLastPage }">
						<li><a href="${pageContext.request.contextPath }/admin/user/list?page=${pageInfo.pages }&status=${param.status}">末页</a></li>
					</c:if>
					<c:if test="${pageInfo.isLastPage  }">
						<li class="disabled"><a href="javascript:void(0)" aria-label="Previous">末页</a></li>
					</c:if>
				</ul>
				</nav>
			</div>
	 	</div>
	 </div>	
</div>
<%@ include file="/admin/common/footer.jsp" %>
</body>
</html>