<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   	
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>	
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>商品分类列表</title>
<script src="${pageContext.request.contextPath }/static/jquery/jquery-3.3.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
<script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
	width: 100%;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
<script type="text/javascript">
	// 页面加载完毕，回显一下二级分类
	$(function () {
		//cid如果是根节点,直接查自己，如果是子节点，查父节点
		//is_sync都是异步查，只有导航栏那里是同步查
	   	if(${cate.isParent == 1 }){
            listByCid(${cate.id},false);
        }else if(${cate.isParent == 0} ){
            listByCid(${cate.parentId},false);
        }
    });
</script>
</head>
<body>
	<!-- 引入header.jsp -->
	<%@ include file="/WEB-INF/common/header.jsp" %>
	<!--主界面开始-->
<div class="container-fluid">
	<!-- 显示左侧分类列表，将下侧div浮动调整为右侧 -->
	<div class="nav" id="menu" style="width: 186px;float: left">

	</div>

	<!--中间界面-->
	<div class="container" style="width: 1210px; margin: 0 auto;">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li><a href="${pageContext.request.contextPath }/">首页</a></li>
				<!--如果是父节点-->
				<c:if test="${cate.isParent == 1}">
					<li><a href="${pageContext.request.contextPath }/product/list?bigCid=${cate.id}">${cate.name}</a></li>
				</c:if>
				<!--如果是子节点-->
				<c:if test="${cate.isParent == 0}">
					<li><a href="${pageContext.request.contextPath }/product/list?bigCid=${cate.parentId}">${cate.pCate.name}</a></li>
					<li><a href="${pageContext.request.contextPath }/product/list?cid=${cate.id}">${cate.name}</a></li>
				</c:if>
			</ol>
		</div>
		<!--遍历商品列表-->
		<c:forEach items="${pageInfo.list }" var="prod">
			<div class="col-md-2">
				<!-- 为了能返回列表，还需带上分页，prod_info有判断 -->
				<a href="${pageContext.request.contextPath }/product/${prod.id}?page=${pageInfo.pageNum}">
					<img src="${pageContext.request.contextPath }${prod.image }" style="width:170px; height:170px; display:inline-block;">
				</a>
				<a href="${pageContext.request.contextPath }/product/${prod.id}?page=${pageInfo.pageNum}" style='color: green'>
						${fn:substring(prod.prodName,0,10)}...
				</a>
				<p>
					<font color="#FF0000">现价：&yen;${prod.shopPrice }</font>
				</p>
			</div>
		</c:forEach>

		<!--分页 -->
		<div class="col-md-12">
			<!-- 分页文字信息 -->
			<div class="col-md-6">
				当前${pageInfo.pageNum }页,
				总${pageInfo.pages }页,
				共${pageInfo.total }条记录
			</div>
			<!-- 分页条信息 -->
			<div class="col-md-6">
				<nav aria-label="Page navigation">
					<ul class="pagination" >
						<!-- 判断是否是第一页 -->
						<c:if test="${not pageInfo.isFirstPage and pageInfo.pageNum > 0}">
							<li>
								<a href="${pageContext.request.contextPath}/product/list?page=1
									<c:if test="${cate.isParent == 1}">&bigCid=${param.bigCid}</c:if>
									<c:if test="${cate.isParent == 0}">&cid=${param.cid}</c:if>
								">首页</a>
							</li>
						</c:if>
						<!-- 判断是否有上一页 -->
						<c:if test="${pageInfo.hasPreviousPage}">
							<li>
								<a href="${pageContext.request.contextPath}/product/list?page=${pageInfo.prePage }
									<c:if test="${cate.isParent == 1}">&bigCid=${param.bigCid}</c:if>
									<c:if test="${cate.isParent == 0}">&cid=${param.cid}</c:if>
								" aria-label="Previous">
									<span aria-hidden="true">上一页</span>
								</a>
							</li>
						</c:if>
						<!-- 展示连续5条页码 -->
						<c:forEach items="${pageInfo.navigatepageNums }" var="pageNum">
							<c:if test="${pageNum ==  pageInfo.pageNum }">
								<li class="active"><a href="javascript:void(0)">${pageNum }</a></li>
							</c:if>
							<c:if test="${pageNum !=  pageInfo.pageNum }">
								<li>
									<a href="${pageContext.request.contextPath}/product/list?page=${pageNum }
										<c:if test="${cate.isParent == 1}">&bigCid=${param.bigCid}</c:if>
										<c:if test="${cate.isParent == 0}">&cid=${param.cid}</c:if>
									">${pageNum }</a>
								</li>
							</c:if>
						</c:forEach>
						<!-- 判断是否有下一页 -->
						<c:if test="${pageInfo.hasNextPage }">
							<li>
								<a href="${pageContext.request.contextPath}/product/list?page=${pageInfo.nextPage }
									<c:if test="${cate.isParent == 1}">&bigCid=${param.bigCid}</c:if>
									<c:if test="${cate.isParent == 0}">&cid=${param.cid}</c:if>
								" aria-label="Next">
									<span aria-hidden="true">下一页</span>
								</a>
							</li>
						</c:if>
						<!-- 判断是否为最后一页 -->
						<c:if test="${not pageInfo.isLastPage }">
							<li>
								<a href="${pageContext.request.contextPath}/product/list?page=${pageInfo.pages}
									<c:if test="${cate.isParent == 1}">&bigCid=${param.bigCid}</c:if>
									<c:if test="${cate.isParent == 0}">&cid=${param.cid}</c:if>
								"><span aria-hidden="true">末页</span>
								</a>
							</li>
						</c:if>
					</ul>
				</nav>
			</div>
		</div>
		<!--分页结束-->
	</div>

</div>
	<!--主界面结束-->
	<!-- 引入footer.jsp -->
	<%@ include file="/WEB-INF/common/footer.jsp" %>
</body>

</html>