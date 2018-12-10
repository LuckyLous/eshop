<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单列表</title>
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
 			// 按照订单状态查询不同的订单
 			$("#search_order").change(function(){
 				$(location).attr("href","${pageContext.request.contextPath}/admin/order/list?page=1&status="+$("#search_order").val());
 			});

 		});

 		// 点击按钮查询某个订单的详情
		function showOrderItemById(id){
			// 弹出模态框
			$("#orderItemsModal").modal({
				backdrop:"static" 
			});
			//清理上一次显示的内容覆盖
			$("#showTable tbody:eq(1)").empty();
			$("#showId").text(id);
			//ajax异步访问数据
			$.ajax({
				url:"${pageContext.request.contextPath}/admin/order/getById",
				data:{"id":id},
				type:"GET",
				success:function(result){
					$.each(result.data.items,function(){
						var trEle = $("<tr></tr>").append("<td><img src='${pageContext.request.contextPath }"+this.image+"' width='50' height='50'></td>")
								.append("<td><a target='_blank'>"+this.prodName+"</a></td>")
								.append("<td>￥"+this.price+"</td>")
								.append("<td>"+this.quantity+"</td>")
								.append("<td>￥"+this.subTotal+"</td>");
						$("#showTable tbody:eq(1)").append(trEle);
					}); 
				}
			});
		}

		// 点击事件=>弹出确认模态框，并将url赋值到隐藏域中
		function confirmDialog(url) {
            $("#confirmModal").modal({
                backdrop:"static"
            });
            if(url.indexOf("ship") >= 0){
                $("#myModalBody").text("确认发货吗？");
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
<!-- 弹出层div -->
<%@ include file="/admin/order/showItems.jsp" %>
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
	 			<h2>订单列表</h2>
	 		</div>
	 	</div>
	 	<!-- 基础操作按钮 -->
	 	<div class="row">
			<div class="col-sm-2">
				<form class="form-inline" action="#">
					  <div class="form-group">
						<label>订单状态</label>
						<select id="search_order" name="status" class="form-control">
							<option value="" <c:if test="${empty param.status }">selected</c:if> >所有订单</option>
						  	<option value="1" <c:if test="${param.status == 1 }">selected</c:if> >未付款</option>
						  	<option value="2" <c:if test="${param.status == 2 }">selected</c:if> >待发货</option>
						  	<option value="4" <c:if test="${param.status == 4 }">selected</c:if> >已发货</option>
						  	<option value="5" <c:if test="${param.status == 5 }">selected</c:if> >已完成</option>
							<option value="6" <c:if test="${param.status == 6 }">selected</c:if> >已关闭</option>
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
	 					<th>下单时间</th>
	 					<th>总金额</th>
	 					<th>订单状态</th>
	 					<th>收货地址</th>
	 					<th>收货人</th>
	 					<th>联系号码</th>
	 					<th>会员名</th>
	 					<th>操作</th>
	 				</tr>
	 				
	 				<c:forEach items="${pageInfo.list }" var="order" varStatus="status">
	 					<tr>
		 					<td>${status.count }</td>
		 					<td><fmt:formatDate value="${order.orderTime }" pattern="yyyy-MM-dd" /></td>
		 					<td>${order.total }</td>
		 					<td>
		 						<c:if test="${order.status == 1 }">
		 							未付款
		 						</c:if>
		 						<c:if test="${order.status == 2 }">
		 							已付款
		 						</c:if>
		 						<c:if test="${order.status == 3 }">
		 							未发货
		 						</c:if>
		 						<c:if test="${order.status == 4 }">
		 							已发货
		 						</c:if>
		 						<c:if test="${order.status == 5 }">
		 							交易成功
		 						</c:if>
		 						<c:if test="${order.status == 6 }">
		 							交易关闭
		 						</c:if>
		 					</td>
		 					<td>${order.address}</td>
		 					<td>${order.receiverName}</td>
		 					<td>${order.receiverPhone}</td>
		 					<td>${order.user.username}</td>
		 					<td>
		 						<c:if test="${order.status == 2 }">
			 						<button onclick="confirmDialog('${pageContext.request.contextPath}/admin/order/ship/${order.id}')"
											class="btn btn-primary btn-sm">
										<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
			 							发货
			 						</button>
		 						</c:if>
		 						<button class="btn btn-primary btn-sm " onclick="showOrderItemById('${order.id}')">
		 							<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
		 							 订单详情
		 						</button>
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
						<li><a href="${pageContext.request.contextPath }/admin/order/list?page=1&status=${param.status}">首页</a></li>
					</c:if>
					<c:if test="${pageInfo.hasPreviousPage }">
						<li>
							<a href="${pageContext.request.contextPath }/admin/order/list?page=${pageInfo.prePage }&status=${param.status}" aria-label="Previous">
								<span aria-hidden="true">&laquo;</span>
							</a>
						</li>
					</c:if>

					<c:forEach items="${pageInfo.navigatepageNums }" var="pageNum">
						<c:if test="${pageNum == pageInfo.pageNum }">
							<li class="active"><a href="#">${pageNum }</a></li>
						</c:if>
						<c:if test="${pageNum != pageInfo.pageNum }">
							<li><a href="${pageContext.request.contextPath }/admin/order/list?page=${pageNum }&status=${param.status}">${pageNum }</a></li>
						</c:if>
					</c:forEach>

					<c:if test="${pageInfo.hasNextPage }">
						<li>
							<a href="${pageContext.request.contextPath }/admin/order/list?page=${pageInfo.nextPage }&status=${param.status}" aria-label="Next">
								<span aria-hidden="true">&raquo;</span>
							</a>
						</li>
					</c:if>
					<c:if test="${not pageInfo.isLastPage }">
						<li><a href="${pageContext.request.contextPath }/admin/order/list?page=${pageInfo.pages }&status=${param.status}">末页</a></li>
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