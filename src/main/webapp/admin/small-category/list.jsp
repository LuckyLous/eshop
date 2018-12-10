<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<html>
<head>
    <meta charset="UTF-8">
    <title>二级分类列表</title>
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
        // 页面加载完毕
        $(function () {
            // 按照激活状态查询不同的会员
            $("#search_cate").change(function(){
                $(location).attr("href","${pageContext.request.contextPath}/admin/category/small/list?page=1&parentId="+$("#search_cate").val());
            });
        });

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
	 			<h2>二级分类列表</h2>
	 		</div>
	 	</div>
	 	<!-- 基础操作按钮 -->
	 	<div class="row">
			<form class="form-inline" action="#">
				<div class="form-group">
					<a href="${pageContext.request.contextPath }/admin/category/small/add" class="btn btn-primary">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
						新增
					</a>
					<label>一级分类</label>
					<select id="search_cate" name="parentId" class="form-control">
						<option value="" <c:if test="${empty param.parentId }">selected</c:if>>所有</option>
						<c:forEach items="${pList }" var="pc">
							<option value="${pc.id }"<c:if test="${param.parentId == pc.id}">selected</c:if>  >
									${pc.name }</option>
						</c:forEach>
					</select>
				</div>
			</form>

	 	</div>
       <!-- 显示数据列表  -->
        <div class="row">
        	<div class="col-md-12">
	            <table class="table table-hover">
	                <tr>
	                    <th>序号</th>
	                    <th>二级分类名称</th>
	                    <th>操作</th>
	                </tr>
	                <c:forEach items="${pageInfo.list }" var="cate" varStatus="status">
	                	<tr>
		                	<td>${status.count }</td>
		                    <td>${cate.name }</td>
		                    <td>
		                        <a href="${pageContext.request.contextPath }/admin/category/small/update/${cate.id}?page=${param.page}" class="btn btn-primary btn-sm">
			 						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
			 						修改
			 					</a>
		                        <button onclick="confirmDialog('${pageContext.request.contextPath }/admin/category/small/delete/${cate.id}?page=${param.page}')"
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
						<li><a href="${pageContext.request.contextPath }/admin/category/small/list?parentId=${param.parentId}">首页</a></li>
					</c:if>
					<c:if test="${pageInfo.hasPreviousPage }">
						<li>
							<a href="${pageContext.request.contextPath }/admin/category/small/list?page=${pageInfo.prePage }&parentId=${param.parentId}" aria-label="Previous">
								<span aria-hidden="true">&laquo;</span>
							</a>
						</li>						   
					</c:if>
					
					<c:forEach items="${pageInfo.navigatepageNums }" var="pageNum">
						<c:if test="${pageNum == pageInfo.pageNum }">
							<li class="active"><a href="#">${pageNum }</a></li>
						</c:if>
						<c:if test="${pageNum != pageInfo.pageNum }">
							<li><a href="${pageContext.request.contextPath }/admin/category/small/list?page=${pageNum }&parentId=${param.parentId}">${pageNum }</a></li>
						</c:if>
					</c:forEach>
					
					<c:if test="${pageInfo.hasNextPage }">
						<li>
							<a href="${pageContext.request.contextPath }/admin/category/small/list?page=${pageInfo.nextPage }&parentId=${param.parentId}" aria-label="Next">
								<span aria-hidden="true">&raquo;</span>
							</a>
						</li>						   
					</c:if>
					<c:if test="${not pageInfo.isLastPage }">
						<li><a href="${pageContext.request.contextPath }/admin/category/small/list?page=${pageInfo.pages }&parentId=${param.parentId}">末页</a></li>
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