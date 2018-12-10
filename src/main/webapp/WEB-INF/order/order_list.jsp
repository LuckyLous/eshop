<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>	
<!DOCTYPE html>
<html>

<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>订单列表</title>
<script src="${pageContext.request.contextPath }/static/jquery/jquery-3.3.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
<script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
<script type="text/javascript">
    // 点击事件=>弹出确认模态框，并将url赋值到隐藏域中
    // 确认框中确认按钮的点击事件
    function confirmDialog(url) {
        $("#confirmModal").modal({
            backdrop:"static"
        });
        // 更新提示信息
        if(url.indexOf("receive") >= 0){
            $("#myModalBody").text("确认收到货了吗？");
        }else if(url.indexOf("cancel") >= 0){
            $("#myModalBody").text("确定要取消订单吗？");
		}
        $("#url").val(url);
    }
    function urlSubmit() {
        var url=$.trim($("#url").val());//获取会话中的隐藏属性URL
        window.location.href=url;
    }

    // 页面加载完毕
    $(function(){
        // 按照订单状态查询不同的订单
        $("#search_order").change(function(){
            $(location).attr("href","${pageContext.request.contextPath}/order/list?page=1&status="+$("#search_order").val());
        });
    });

</script>
</head>
<body>
<!--确认框div>
<%@ include file="/admin/common/confirm.jsp" %>
<!-- 引入header.jsp -->
<%@ include file="/WEB-INF/common/header.jsp" %>

	<!--主界面开始-->
	<div class="container" style="margin: 0 auto; margin-top: 10px; width: 950px;">
		<c:if test="${empty pageInfo.list }">
			<h3>该状态下暂时没有订单，请更改状态或者先去下单吧~~~~</h3>
		</c:if>
		<div class="row">
			<div class="col-sm-4">
				<div class="form-inline">
					<label >订单状态</label>
					<select id="search_order" name="status" class="form-control">
						<option value="" <c:if test="${empty param.status }">selected</c:if> >所有订单</option>
						<option value="1" <c:if test="${param.status == 1 }">selected</c:if> >待付款</option>
						<option value="2" <c:if test="${param.status == 2 }">selected</c:if> >未发货</option>
						<option value="4" <c:if test="${param.status == 4 }">selected</c:if> >已发货</option>
						<option value="5" <c:if test="${param.status == 5 }">selected</c:if> >已完成</option>
						<option value="6" <c:if test="${param.status == 6 }">selected</c:if> >已关闭</option>
					</select>
				</div>
			</div>
			<div class="col-sm-8">
				<strong>我的订单(已发货的订单，会在10天后自动确认)</strong>
			</div>
		</div>

		<table class="table table-bordered">
			<c:forEach items="${pageInfo.list }" var="order">
				<tbody>
					<tr class="success">
						<th colspan="2">订单编号:${order.id }</th>
						<th colspan="1">
							<c:if test="${order.status == 1 }">
								<a href="${pageContext.request.contextPath }/order/${order.id }" class="btn btn-link">去付款</a>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<a onclick="confirmDialog('${pageContext.request.contextPath }/order/cancel/${order.id }')"
								   class="btn btn-link">取消</a>
							</c:if>
							<c:if test="${order.status == 2 }">等待发货中...</c:if>
							<c:if test="${order.status == 4 }">
								<a onclick="confirmDialog('${pageContext.request.contextPath }/order/receive/${order.id}')"
										class="btn btn-link">
									确认收货
								</a>
							</c:if>
							<c:if test="${order.status == 5 }">交易成功</c:if>
							<c:if test="${order.status == 6 }">交易关闭</c:if>
						</th>
						<th colspan="2">
							<c:if test="${order.status == 2}">
								付款时间:
							</c:if>
							<c:if test="${order.status == 4}">
								发货时间:
							</c:if>
							<c:if test="${order.status == 5}">
								完成时间:
							</c:if>
							<c:if test="${order.status == 6}">
								关闭时间:
							</c:if>
							<c:if test="${order.status != 1}">
								<fmt:formatDate value="${order.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</c:if>
						</th>
					</tr>
					<tr class="warning">
						<th>图片</th>
						<th>商品</th>
						<th>价格</th>
						<th>数量</th>
						<th>小计</th>
					</tr>
					<c:forEach items="${order.items }" var="item">
						<tr class="active">
							<td width="60" width="40%">
								<input type="hidden" name="id" value="22">
								<img src="${pageContext.request.contextPath }${item.image }" width="70" height="60">
							</td>
							<td width="30%"><a target="_blank"> ${item.prodName }...</a></td>
							<td width="20%">￥${item.price }</td>
							<td width="10%">${item.quantity }</td>
							<td width="15%"><span class="subtotal">￥${item.subTotal }</span></td>
						</tr>
					</c:forEach>
					<tr class="active">
						<td colspan="3">
							<strong>下单时间：<fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></strong>
						</td>
						<td colspan="2">
							<strong>总金额: ￥<fmt:formatNumber value="${order.total }" maxFractionDigits="2"></fmt:formatNumber>元</strong>
						</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>

		<!-- 导入分页jsp -->
		<%@ include file="/WEB-INF/common/page.jsp" %>
	</div>
	<!--主界面结束-->
	<!-- 引入footer.jsp -->
	<%@ include file="/WEB-INF/common/footer.jsp" %>
</body>
</html>