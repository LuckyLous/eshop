<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>我的购物车</title>
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
		
		font {
			color: #3164af;
			font-size: 18px;
			font-weight: normal;
			padding: 0 10px;
		}
		/*数量css*/
		.quantity{
			text-align: center;
		}
	</style>
	<script type="text/javascript">
		//将prodId，购买数量，遍历cartList的索引传递过来
		function addNum(prodId,buyNum,index){
			// 递增前查询商品的最大库存
			$.ajax({
				url:"${pageContext.request.contextPath}/product/getNum/"+prodId,
				type:"GET",
				success:function(result){
					var $quantity = $(".quantity").eq(index); // 购买数量的jq对象
					var buy_num = parseInt($quantity.val());
					// 只要不超过库存数，就可以递增
					if(buy_num >= result.data.prodNum){
						alert("超过库存数");
						return;
					}
					$quantity.val(buy_num + 1);
					window.location.href = "${pageContext.request.contextPath }/cart/update/num/"+prodId+"/"+$quantity.val();
				}
			});
		}
		
		function delNum(prodId,buyNum,index){
			var $quantity = $(".quantity").eq(index);
			var buy_num = parseInt($quantity.val());
			// 只要大于1，就可以递减
			if(buy_num == 1){
			    // 确认操作
                confirmDialog("${pageContext.request.contextPath }/cart/delete/"+prodId);
            }else{
				$quantity.val(buy_num - 1);
				window.location.href = "${pageContext.request.contextPath }/cart/update/num/"+prodId+"/"+$quantity.val();
			}
			
		}
		
		// 约束自定义数量的范围
		function changeNum(prodId,buyNum,index){
			// 改变前查询商品的最大库存
			$.ajax({
				url:"${pageContext.request.contextPath}/product/getNum/"+prodId,
				type:"GET",
				success:function(result){
					var $quantity = $(".quantity").eq(index);
					var newNum = parseInt($quantity.val());
					if(!/^[1-9]\d*$/.test(newNum) || newNum > result.data.prodNum){
						$quantity.val(buyNum);
						return;
					}
					window.location.href = "${pageContext.request.contextPath }/cart/update/num/"+prodId+"/"+newNum;
				}
			});
		}

        // 点击事件=>弹出确认模态框，并将url赋值到隐藏域中
        // 确认框中确认按钮的点击事件
        function confirmDialog(url) {
            $("#confirmModal").modal({
                backdrop:"static"
            });
            // 更新提示信息
			if(url.indexOf("delete") >= 0){
                $("#myModalBody").text("确定要删除这个商品吗？");
            }else if(url.indexOf("clear") >= 0){
                $("#myModalBody").text("确定清空购物车吗？");
			}else if(url.indexOf("save") >= 0){
                $("#myModalBody").text("确定提交订单吗？");
			}
            $("#url").val(url);
        }
        function urlSubmit() {
            var url=$.trim($("#url").val());//获取会话中的隐藏属性URL
            window.location.href=url;
        }
		
	</script>
</head>
<body>
<!--确认框div>
<%@ include file="/admin/common/confirm.jsp" %>
<!-- 引入header.jsp -->
<jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<c:if test="${empty cartList }">
				<h3>购物车空空如也，请先去逛逛去吧~~~~~~~~~</h3>
			</c:if>
			<c:if test="${not empty cartList }">
			<div style="margin:0 auto; margin-top:10px;width:950px;">
				<strong style="font-size:16px;margin:5px 0;">购物车详情</strong>
				<table class="table table-bordered">
					<tbody>
						<tr class="warning">
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
							<th>操作</th>
						</tr>
						<!-- 遍历购物车之前 -->
						<c:set var="total" value="0" />
						<c:forEach items="${cartList }" var="prod" varStatus="status" >
							<tr class="active">
								<td width="60" width="40%">
									<img src="${pageContext.request.contextPath }${prod.image}" width="70" height="60">
								</td>
								<td width="30%">
									<a href="${pageContext.request.contextPath }/product/${prod.id}"> ${prod.prodName }...</a>
								</td>
								<td width="20%">
									￥${prod.shopPrice }
								</td>
								<td width="15%">
									<a href="javascript:delNum(${prod.id },${prod.num },${status.index });" class="glyphicon glyphicon-minus" ></a>
									<input type="text" name="quantity" class="quantity" value="${prod.num }" maxlength="4" size="3"
										onchange="changeNum(${prod.id },${prod.num },${status.index })" >
									<a href="javascript:addNum(${prod.id },${prod.num },${status.index });" class="glyphicon glyphicon-plus" ></a>
								</td>
								<td width="15%">
									<span class="subtotal">
										￥<fmt:formatNumber value="${prod.shopPrice * prod.num }" maxFractionDigits="2"></fmt:formatNumber>
									</span>
									<c:set var="total" value="${total + prod.shopPrice * prod.num }"></c:set>
								</td>
								<td>
									<button onclick="confirmDialog('${pageContext.request.contextPath }/cart/delete/${prod.id}')"
											class="btn btn-warning">
										删除
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 结算 -->			
		<div style="margin-right:130px;">
			<div style="text-align:right;">
				 商品金额: <strong style="color:#ff6600;">￥<fmt:formatNumber value="${total }" maxFractionDigits="2"></fmt:formatNumber>元</strong>
			</div>
			<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
				<button onclick="confirmDialog('${pageContext.request.contextPath }/cart/clear')" class="btn btn-danger">
					清空购物车
				</button>
				<button onclick="confirmDialog('${pageContext.request.contextPath }/order/save')" class="btn btn-primary">
					提交订单
				</button>
			</div>
		</div>
		</c:if>
	</div>
	<!-- 引入footer.jsp -->
	<jsp:include page="/WEB-INF/common/footer.jsp"></jsp:include>
	</body>
</html>