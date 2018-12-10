<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   	
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>	
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>商品搜索列表</title>
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

/*ul li的样式*/
.li-style{
	list-style:none;
	padding-left: 0px;
}

.li-style li{
	float:left;
}
</style>
<script type="text/javascript">
	
	function filter(key, value) {
		document.getElementById(key).value=value;
		//执行查询
		queryList();
	}
	function sort() {
		var s = document.getElementById("sort").value; 
		if (s != "desc") {
			s = "desc";
		} else {
			s = "asc";
		}
		document.getElementById("sort").value = s;
		//执行查询
		queryList();
	}
</script>
</head>
<body>
<!-- 引入header.jsp -->
<%@ include file="/WEB-INF/common/header.jsp" %>
<!--主界面开始-->
<div class="container" style="width: 1210px; margin: 0 auto;">
	<!-- 筛选类别 -->
	<div class="col-md-12">
		<h3><strong>&nbsp;商品筛选</strong></h3>
	</div>
	<div class="col-md-12" style="margin-bottom: 10px;">
		<div class="col-md-1">商品类别</div>
		<div class="col-md-10">
			<div class="col-md-1">
				<a href="javascript:filter('cate_name','')" >不限</a>
			</div>
			<c:forEach items="${cateSet }" var="cname">
				<div class="col-md-1">
					<a href="javascript:filter('cate_name','${cname}')" >${cname}</a>
				</div>
			</c:forEach>
		</div>
	</div>
	<!-- 筛选价格 -->
	<div class="col-md-12" style="margin-bottom: 10px;">
		<div class="col-md-1">价格</div>
		<div class="col-md-10">
			<ul class="li-style">
				<div class="col-md-1">
					<li><a href="javascript:filter('price','')">不限</a></li>
				</div>
				<div class="col-md-1">
					<li><a href="javascript:filter('price','0-29')">0-29</a></li>
				</div>
				<div class="col-md-1">
					<li><a href="javascript:filter('price','30-59')">30-59</a></li>
				</div>
				<div class="col-md-1">
					<li><a href="javascript:filter('price','60-99')">60-99</a></li>
				</div>
				<div class="col-md-2">
					<li><a href="javascript:filter('price','100-199')">100-199</a></li>
				</div>
				<div class="col-md-2">
					<li><a href="javascript:filter('price','200-299')">200-299</a></li>
				</div>
				<div class="col-md-2">
					<li><a href="javascript:filter('price','300-*')">300以上</a></li>
				</div>
			</ul>
		</div>
	</div>
	<!-- 筛选排序 -->
	<div class="col-md-12" style="margin-bottom: 10px;">
		<div class="col-md-1">排序</div>
		<div class="col-md-10">
			<div class="col-md-1">
				<a href="javascript:sort()">价格</a>
			</div>
		</div>
	</div>

	<!--商品列表开始-->
	<c:if test="${empty pageInfo.list}">
		<div class="col-md-12">
			<h3><strong>好像没有搜索到相关的商品...</strong></h3>
		</div>
	</c:if>

	<c:forEach items="${pageInfo.list }" var="item">
		<div class="col-md-12">
			<a href="${pageContext.request.contextPath }/product/${item.id}">
				<img src="${pageContext.request.contextPath }${item.image }" width="170" height="170" style="display: inline-block;">
			</a>
			<p>
				<a href="#" style='color: green'>${item.prodName}</a>
			</p>
			<p>
				<font color="#FF0000">售价：&yen;${item.shopPrice }</font>
			</p>
			<p>
				<font color="#008000">作者：&yen;${item.author }</font>
			</p>
			<p>
				<font color="#008000">出版社：&yen;${item.press }</font>
			</p>
		</div>
	</c:forEach>
	<!--商品列表结束-->
	
	<!--分页开始 -->
	<div class="col-md-12">
		<div class="col-md-6">
			当前${pageInfo.pageNum }页,
			总${pageInfo.pages }页,
			共${pageInfo.total }条记录
		</div>
		<div class="col-md-6">
			<nav aria-label="Page navigation">
				<ul class="pagination">
					<!-- 判断是否是第一页 -->
					<c:if test="${not pageInfo.isFirstPage and pageInfo.pageNum > 0 }">
						<li>
							<a href="${pageContext.request.contextPath}/search?
							keyword=${param.keyword}&cateName=${param.cateName}&price=${param.price}&sort=${param.sort}" aria-label="Previous">
								<span aria-hidden="true">首页</span>
							</a>
						</li>
					</c:if>
					<!-- 不是第一页 -->
					<c:if test="${pageInfo.hasPreviousPage }">
						<li>
							<a href="${pageContext.request.contextPath}/search?
							keyword=${param.keyword}&cateName=${param.cateName}&price=${param.price}&sort=${param.sort}&page=${pageInfo.prePage}" aria-label="Previous">
								<span aria-hidden="true">上一页</span>
							</a>
						</li>
					</c:if>
					<!-- 展示所有页码 -->
					<c:forEach items="${pageInfo.navigatepageNums }" var="pageNum">
						<c:if test="${pageNum ==  pageInfo.pageNum  }">
							<li class="active"><a href="javascript:void(0)">${pageNum }</a></li>
						</c:if>
						<c:if test="${pageNum !=  pageInfo.pageNum }">
							<li><a href="${pageContext.request.contextPath}/search?
							keyword=${param.keyword}&cateName=${param.cateName}&price=${param.price}&sort=${param.sort}&page=${pageNum}">${pageNum }</a></li>
						</c:if>
					</c:forEach>
					<!-- 判断是否为最后一页 -->
					<c:if test="${pageInfo.hasNextPage }">
						<li>
							<a href="${pageContext.request.contextPath}/search?
							keyword=${param.keyword}&cateName=${param.cateName}&price=${param.price}&sort=${param.sort}&page=${pageInfo.nextPage }" aria-label="Next">
								<span aria-hidden="true">下一页</span>
							</a>
						</li>
					</c:if>
					<c:if test="${not pageInfo.isLastPage  }">
						<li>
							<a href="${pageContext.request.contextPath}/search?
							keyword=${param.keyword}&cateName=${param.cateName}&price=${param.price}&sort=${param.sort}&page=${pageInfo.pages }" aria-label="Next">
								<span aria-hidden="true">末页</span>
							</a>
						</li>
					</c:if>
				</ul>
			</nav>
		</div>
	</div>
	<!-- 分页结束 -->
</div>
<!-- 引入footer.jsp -->
<%@ include file="/WEB-INF/common/footer.jsp" %>
</body>
</html>