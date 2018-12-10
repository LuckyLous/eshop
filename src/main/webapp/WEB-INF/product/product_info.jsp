<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>商品详情</title>
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
/*数量css*/
#quantity{
	text-align: center;
}
</style>

<script type="text/javascript">
	var prod_num = ${prod.num };
	$(function(){
		// 购买数量+1
		$("#num_add").click(function(){
			var buy_num = Number($("#quantity").val());
			// 只要不超过库存数，就可以递增
			if(buy_num < prod_num){
				$("#quantity").val(buy_num + 1);
			}
		});
		// 购买数量-1
		$("#num_del").click(function(){
			var buy_num = Number($("#quantity").val());
			// 只要大于1，就可以递减
			if(buy_num > 1){
				$("#quantity").val(buy_num - 1);
			}
		});
	
		// 约束自定义数量的范围
		$("#quantity").change(function(){
			var new_num = Number($("#quantity").val());
			if(!/^[1-9]\d*$/.test(new_num) || new_num > prod_num){
				$("#quantity").val(1);
			}
		});
		
	});

    // 点击事件=>弹出确认模态框，并将url赋值到隐藏域中
    // 确认框中确认按钮的点击事件
    function confirmDialog(url) {
        $("#confirmModal").modal({
            backdrop:"static"
        });
        // 更新提示信息
        $("#myModalBody").text("确定要加入购物车吗？");
        // 加入购物车url
        var add_cart_url = url+"?num="+$("#quantity").val();
        $("#url").val(add_cart_url);
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
<%@ include file="/WEB-INF/common/header.jsp" %>
<!--主界面开始-->
<div class="container-fluid">
	<!-- 显示左侧分类列表，将下侧div浮动调整为右侧 -->
	<div class="nav" id="menu" style="width: 186px;float: left">

	</div>
	<!--中间界面-->
	<div class="container">
		<div class="row">
			<div style="border: 1px solid #e4e4e4; width: 930px; margin-bottom: 10px; margin: 0 auto; padding: 10px; margin-bottom: 10px;">
				<a href="${pageContext.request.contextPath }/">首页&nbsp;&nbsp;&gt;</a>
				<a href="${pageContext.request.contextPath }/product/list?page=${param.page }&bigCid=${prod.bigCateId}">${prod.bigCate.name}&nbsp;&nbsp;&gt;</a>
				<!-- 如果分页不为空，返回分页界面 -->
				<c:if test="${not empty param.page}">
					<a href="${pageContext.request.contextPath}/product/list?page=${param.page }&cid=${prod.cateId}">${prod.cate.name }&nbsp;&nbsp;&gt;</a>
				</c:if>
				<!-- 如果分页为空，默认为第一页 -->
				<c:if test="${empty param.page }">
					<a href="${pageContext.request.contextPath}/product/list?cid=${prod.cateId}">${prod.cate.name }&nbsp;&nbsp;&gt;</a>
				</c:if>
				<a>${prod.prodName }</a>
			</div>

			<div style="margin: 0 auto; width: 950px;">
				<div class="col-md-6">
					<img src="${pageContext.request.contextPath }${prod.image } " 
						style="opacity: 1; width: 400px; height: 350px;" title="" class="medium">
				</div>

				<div class="col-md-6">
					<div>
						<strong>${prod.prodName }</strong>
					</div>
					<div style="border-bottom: 1px dotted #dddddd; width: 350px; margin: 10px 0 10px 0;">
						<div>编号：${prod.id }</div>
					</div>

					<div style="margin: 10px 0 10px 0;">
						现价: <strong style="color: #ef0101;">￥：${prod.shopPrice }元</strong>
						定价：<del>￥${prod.book.marketPrice }元/份</del>
					</div>
					
					<div style="margin: 10px 0 10px 0;">
						<%--促销: <a target="_blank" title="限时抢购 (2014-07-30 ~ 2015-01-01)"
							style="background-color: #f07373;">限时抢购</a>&nbsp;&nbsp;&nbsp;--%>
						库存数量: <strong style="color: #FF8C00;">${prod.num }份</strong>	
					</div>

					<div style="padding: 10px; border: 1px solid #e7dbb1; width: 330px; margin: 15px 0 10px 0;; background-color: #fffee6;">
						<div style="margin: 5px 0 10px 0;">白色</div>

						<div style="border-bottom: 1px solid #faeac7; margin-top: 20px; padding-left: 10px;">
						购买数量: 
							<a href="javascript:;" class="glyphicon glyphicon-minus" id="num_del" ></a>
							<input type="text" id="quantity" name="quantity" value="1" maxlength="4" size="3">
							<a href="javascript:;" class="glyphicon glyphicon-plus" id="num_add" ></a>
						</div>

						<div style="margin: 20px 0 10px 0;; text-align: center;">
							<a href="javascript:confirmDialog('${pageContext.request.contextPath }/cart/add/${prod.id}')">
								<span class="glyphicon glyphicon-shopping-cart"></span>加入购物车
							</a> &nbsp;<!-- 收藏商品 -->
						</div>
					</div>
					<div>
						<button type="button" class="btn btn-primary" onclick="history.go(-1)" data-dismiss="modal" >返回</button>
					</div>

				</div>
			</div>
			<div class="clear"></div>
			<div style="width: 950px; margin: 0 auto;">
				<div
					style="background-color: #d3d3d3; width: 930px; padding: 10px 10px; margin: 10px 0 10px 0;">
					<strong>商品介绍</strong>
				</div>

				<%--<div>
					<img src="${pageContext.request.contextPath }${prod.image }">
				</div>--%>

				<div
					style="background-color: #d3d3d3; width: 930px; padding: 10px 10px; margin: 10px 0 10px 0;">
					<strong>商品参数</strong>
				</div>
				<div style="margin-top: 10px; width: 900px;">
					<table class="table table-bordered">
						<tbody>
							<tr class="active">
								<th colspan="2">基本参数</th>
							</tr>
							<tr>
								<th width="10%">作者</th>
								<td width="30%">${prod.book.author }</td>
							</tr>
							<tr>
								<th width="10%">出版社</th>
								<td>${prod.book.press }</td>
							</tr>
							<tr>
								<th width="10%">出版时间</th>
								<td><fmt:formatDate value="${prod.book.publishDate }" pattern="yyyy-MM"></fmt:formatDate></td>
							</tr>
							<tr>
								<th width="10%">版次</th>
								<td>${prod.book.edition }</td>
							</tr>
							<tr>
								<th width="10%">印刷时间</th>
								<td><fmt:formatDate value="${prod.book.printDate }" pattern="yyyy-MM-dd"></fmt:formatDate></td>
							</tr>
							<tr>
								<th width="10%">开本</th>
								<td>${prod.book.size }开</td>
							</tr>
							<tr>
								<th width="10%">纸张</th>
								<td>${prod.book.paper }</td>
							</tr>
							<tr>
								<th width="10%">页数</th>
								<td>${prod.book.pageNum }</td>
							</tr>
							<tr>
								<th width="10%">字数</th>
								<td>${prod.book.wordNum }</td>
							</tr>
							<tr>
								<th width="10%">描述</th>
								<td>${prod.description }</td>
							</tr>
						</tbody>
					</table>
				</div>

				<%--<div style="background-color: #d3d3d3; width: 900px;">
					<table class="table table-bordered">
						<tbody>
							<tr class="active">
								<th><strong>商品评论</strong></th>
							</tr>
							<tr class="warning">
								<th>暂无商品评论信息 <a>[发表商品评论]</a></th>
							</tr>
						</tbody>
					</table>
				</div>--%>

				<%--<div style="background-color: #d3d3d3; width: 900px;">
					<table class="table table-bordered">
						<tbody>
							<tr class="active">
								<th><strong>商品咨询</strong></th>
							</tr>
							<tr class="warning">
								<th>暂无商品咨询信息 <a>[发表商品咨询]</a></th>
							</tr>
						</tbody>
					</table>
				</div>--%>
			</div>

		</div>
	</div>
</div>
	<!-- 引入footer.jsp -->
	<%@ include file="/WEB-INF/common/footer.jsp" %>
</body>

</html>