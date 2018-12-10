<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>浏览历史</title>
    <script src="${pageContext.request.contextPath }/static/jquery/jquery-3.3.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
    <script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/style.css" type="text/css" />
</head>
<body>
<!-- 引入header.jsp -->
<%@ include file="/WEB-INF/common/header.jsp" %>
<!--商品浏览记录-->
<div class="container-fluid">
    <!-- 显示左侧分类列表，将下侧div浮动调整为右侧 -->
    <div class="nav" id="menu" style="width: 186px;float: left">

    </div>
    <!--中间界面-->
    <div class="container" style="width: 1210px; margin: 0 auto;">
        <div class="col-md-12">
            <div class="col-md-2">
                <h4 style="font: 14px/30px 微软雅黑">浏览记录</h4>
            </div>
            <div class="col-md-2" style="float: right; text-align: right;">
                <a href="javascript:void(0)">more</a>
            </div>
        </div>
        <!--多出的元素隐藏-->
        <div class="col-md-12" style="overflow: hidden;">
            <ul style="list-style: none;">
                <c:forEach items="${historyList }" var="prod">
                    <li style="width: 150px; height: 216px; float: left; padding: 0 18px 15px; text-align: center;">
                        <a href="${pageContext.request.contextPath}/product/${prod.id}">
                            <img src="${pageContext.request.contextPath}${prod.image}" width="130px" height="130px" />
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
<!-- 引入footer.jsp -->
<%@ include file="/WEB-INF/common/footer.jsp" %>

</body>
</html>
