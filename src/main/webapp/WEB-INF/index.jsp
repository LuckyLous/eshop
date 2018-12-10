<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>网上书店首页</title>
	<!-- bootstrap -->
	<script src="${pageContext.request.contextPath }/static/jquery/jquery-3.3.1.min.js" type="text/javascript"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
	<script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
	<!-- 自定义jq -->
	<script type="text/javascript">
		
	</script>
</head>

<body>
	<div class="container-fluid">
		<!-- 静态包含header.jsp -->
		<%@ include file="/WEB-INF/common/header.jsp" %>
		<!-- 轮播图 -->
		<div class="container-fluid">
			<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
				<!-- 轮播图的中的小点 -->
				<ol class="carousel-indicators">
					<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
					<li data-target="#carousel-example-generic" data-slide-to="1"></li>
					<li data-target="#carousel-example-generic" data-slide-to="2"></li>
				</ol>
				<!-- 轮播图的轮播图片 -->
				<div class="carousel-inner" role="listbox">
					<div class="item active">
						<img src="${pageContext.request.contextPath }/image/cover/coffee-cup-books-home.jpg">
						<div class="carousel-caption">
							<!-- 轮播图上的文字 -->
						</div>
					</div>
					<div class="item">
						<img src="${pageContext.request.contextPath }/image/cover/pexels-photo-261820.jpeg">
						<div class="carousel-caption">
							<!-- 轮播图上的文字 -->
						</div>
					</div>
					<div class="item">
						<img src="${pageContext.request.contextPath }/image/cover/pexels-photo-324129.jpeg">
						<div class="carousel-caption">
							<!-- 轮播图上的文字 -->
						</div>
					</div>
				</div>
				<!-- 上一张 下一张按钮 -->
				<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
					<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a>
				<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
					<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
		</div>
		
		<!-- 热门商品的遍历 -->
		<div class="container-fluid">
			<div class="col-md-12">
				<h2>热门商品&nbsp;&nbsp;<%--<img src="${pageContext.request.contextPath }/image/ad/title.jpg"/>--%></h2>
			</div>
			<div class="col-md-12">
				<c:forEach items="${hotList }" var="prod">
					<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
						<a href="${pageContext.request.contextPath }/product/${prod.id}">
							<img src="${pageContext.request.contextPath }${prod.image }" width="130" height="130" style="display: inline-block;">
						</a>
						<p><a href="${pageContext.request.contextPath }/product/${prod.id}" style='color:#666'>${fn:substring(prod.prodName,0,10)}..</a></p>
						<p><font color="#E4393C" style="font-size:16px">&yen;${prod.shopPrice }</font></p>
					</div>
				</c:forEach>
			</div>
		</div>
		
		<!-- 广告条 -->
        <div class="container-fluid">
			<img src="${pageContext.request.contextPath }/image/ad/pexels-photo-298660.jpeg" width="100%"/>
		</div>
		
		<!-- 最新商品的遍历 -->
		<div class="container-fluid">
			<div class="col-md-12">
				<h2>最新商品&nbsp;&nbsp;<%--<img src="${pageContext.request.contextPath }/image/ad/title.jpg"/>--%></h2>
			</div>
			<div class="col-md-12">
				<c:forEach items="${newList }" var="prod">
					<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
						<a href="${pageContext.request.contextPath }/product/${prod.id}">
							<img src="${pageContext.request.contextPath }${prod.image }" width="130" height="130" style="display: inline-block;">
						</a>
						<p><a href="${pageContext.request.contextPath }/product/${prod.id}" style='color:#666'>${fn:substring(prod.prodName,0,10)}..</a></p>
						<p><font color="#E4393C" style="font-size:16px">&yen;${prod.shopPrice }</font></p>
					</div>
				</c:forEach>
			</div>
		</div>			
		
		<!-- 静态引入footer.jsp -->
		<%@ include file="/WEB-INF/common/footer.jsp" %>
		
	</div>
</body>
</html>