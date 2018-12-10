<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>订单支付</title>
<script src="${pageContext.request.contextPath }/static/jquery/jquery-3.3.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
<script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/style.css" type="text/css" />
<!--引入城市联动的js-->
<script src="${pageContext.request.contextPath }/static/js/distpicker.data.js"></script>
<script src="${pageContext.request.contextPath }/static/js/distpicker.js"></script>
<script src="${pageContext.request.contextPath }/static/js/main.js"></script>
<!--引入倒计时的css-->
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/pay-time.css" type="text/css" />
<!-- 引入表单校验jquery校验 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/static/jquery/jquery.validate.min.js"></script>
<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
.logo{
	width: 154px;
	height: 33px;
}
/*给自定义表单校验加css*/
.error{
	color:#FF0000;
	padding-left: 12px;
}
</style>
<script type="text/javascript">
	// 页面加载完毕
	$(function () {
        // 省市取消自动选择
        /*$("#distpicker3").distpicker({
            province: "浙江省",
            city: "杭州市",
            district: "西湖区"
        });*/
        // 回显收货地址
        showAddress();

        // 手机号码验证
        jQuery.validator.addMethod("isMobile", function(value, element) {
            var length = value.length;
            var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
            return this.optional(element) || (length == 11 && mobile.test(value));
        }, "请正确填写您的手机号码");

        // 表单校验
        $("#order_form").validate({
            rules:{
                "detail_address":{
                    "required":true
                },
                "receiverName":{
                    "required":true
                },
                "receiverPhone":{
                    required:true,
                    minlength : 11,
                    isMobile : true
                }
            },
            messages:{
                "detail_address":{
                    "required":"详细地址不能为空"
                },
                "receiverName":{
                    "required":"联系人不能为空"
                },
                "receiverPhone":{
                    required:"请输入手机号码",
					minlength : "确认手机不能小于11个字符",
                    isMobile : "请正确填写您的手机号码"
                }
            }
        });
    });

	// 回显用户的收货地址
	function showAddress() {
        var adds = "${user.address}".split('-');
        if(adds.length != 0){
			// 回显是在编辑时，$(#).trigger();方法是根据当前操作的下拉框是省或是市来初始化之后的下拉列表框
			// 比如我先选择了省，则市和区就会被初始化
			$("#province").val(adds[0]);
			$('#province').trigger("change");
			$("#city").val(adds[1]);
			$('#city').trigger("change");
			$("#district").val(adds[2]);

			$("#detail_address").val(adds[3]);
        }
    }

    // 点击事件=>弹出确认模态框，并将url赋值到隐藏域中
    // 确认框中确认按钮的点击事件
    function confirmDialog() {
        $("#confirmModal").modal({
            backdrop:"static"
        });
        // 更新提示信息
		$("#myModalBody").text("确认去支付吗？");

		// 根据选择框更新url
		var radio_val = $("input:checked").val();
        if(radio_val.indexOf("支付宝") >= 0){
            $("#order_form").attr("action","${pageContext.request.contextPath }/alipay/pay");
        }

        // -切割字符,设置隐藏域
        var address = $("#province").val() + "-"
            +$("#city").val() + "-"
            + $("#district").val() + "-"
            + $("#detail_address").val();
        $("input[name='address']").val(address);
    }
    function urlSubmit() {
        // 不用获取url，直接提交form
        $("#order_form").submit();
    }
	
</script>
<script type="text/javascript">
	//var intDiff = parseInt(86400);//倒计时总秒数量--前台测试用，86400秒=24小时
	var intDiff = checkTime();// 每次页面刷新时，都从后台获取一次剩余的时间
	// 倒计时
	function timer(intDiff){
		var tid = window.setInterval(function(){
			var day=0,
				hour=0,
				minute=0,
				second=0;//时间默认值
			if(intDiff > 0){
				day = Math.floor(intDiff / (60 * 60 * 24));
				hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
				minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
				second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
			}else if(intDiff <= 0){
                alert("倒计时已经结束了，订单即将取消~~");
                clearInterval(tid);// 清除倒计时
				$(location).attr("href","${pageContext.request.contextPath }/order/cancel/${order.id }");
			}else{
				alert("倒计时发生错误--");
                clearInterval(tid);
            }
			if (minute <= 9) minute = '0' + minute;
			if (second <= 9) second = '0' + second;
			$('#day_show').html(day+"天");
			$('#hour_show').html('<s id="h"></s>'+hour+'时');
			$('#minute_show').html('<s></s>'+minute+'分');
			$('#second_show').html('<s></s>'+second+'秒');
			intDiff--;
		}, 1000);
	}

	// ajax从获取后台的剩余时间
	function checkTime() {
		$.ajax({
			"url":"${pageContext.request.contextPath }/order/countdown/${order.id}",
			"type":"GET",
			"success":function (result) {
				if (result.code == 100){
					intDiff = parseInt(result.data.seconds);
				}else {
					intDiff =  parseInt(0);
				}
			}
		})
	}
	// 页面加载完毕后，ajax赋值一次
	$(function(){
	    if(intDiff == undefined){ // 如果全局变量没有赋值到，暂时以1小时来赋值
			intDiff = parseInt(3600);
        }
        timer(intDiff);//启动倒计时
    });

</script>
</head>

<body>
<!--确认框div>
<%@ include file="/admin/common/confirm.jsp" %>
<!-- 引入header.jsp -->
<%@ include file="/WEB-INF/common/header.jsp" %>
	
	<div class="container" style="margin: 0 auto; margin-top: 10px; width: 950px;">
		<strong>订单详情</strong>
		<table class="table table-bordered">
			<tbody>
				<tr class="warning">
					<th colspan="2">订单编号:${order.id }</th>
					<th colspan="1">
						<c:if test="${order.status == 1 }">等待付款</c:if>
						<c:if test="${order.status == 2 }">等待发货中...</c:if>
						<c:if test="${order.status == 4 }">请等待收货</c:if>
						<c:if test="${order.status == 5 }">交易成功</c:if>
						<c:if test="${order.status == 6 }">交易关闭</c:if>
					</th>
					<th colspan="2">时间:<fmt:formatDate value="${order.orderTime }" pattern="yyyy-MM-dd HH:mm:ss"/></th>
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
			</tbody>
		</table>

		<div style="text-align: right; margin-right: 20px;">
			商品金额: <strong style="color: #ff6600;">￥<fmt:formatNumber value="${order.total }" maxFractionDigits="2"></fmt:formatNumber></strong>
		</div>

		<hr/>
			<div class="col-md-3">
				<label class="control-label">剩余支付时间</label>
			</div>
			<div class="time-item">
				<span id="day_show">0天</span>
				<strong id="hour_show">0时</strong>
				<strong id="minute_show">0分</strong>
				<strong id="second_show">0秒</strong>
			</div>

		<hr />
		<form id="order_form" class="form-horizontal" method = "post"
			action="${pageContext.request.contextPath }/order/pay" >
			<div class="form-group">
				<input type="hidden" name="id" value="${order.id }">
				<input type="hidden" name="address" >

				<label class="col-sm-1 control-label">地址</label>
				<div class="col-sm-11" id="distpicker3">
					<div class="col-sm-3">
						<label class="sr-only" for="province">Province</label>
						<select class="form-control" id="province"></select>
					</div>
					<div class="col-sm-3">
						<label class="sr-only" for="city">City</label>
						<select class="form-control" id="city"></select>
					</div>
					<div class="col-sm-2">
						<label class="sr-only" for="district">District</label>
						<select class="form-control" id="district"></select>
					</div>
					<div class="col-sm-4">
						<input type="text" class="form-control" id="detail_address" name="detail_address"
					   		placeholder="请输入详细地址"
							   value="<c:if test='${not empty user.address}'></c:if>
									<c:if test='${empty user.address}'>${order.address }</c:if>">

					</div>
				</div>

			</div>
			<div class="form-group">
				<label class="col-sm-1 control-label">收货人</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" name="receiverName"
						placeholder="请输入收货人" value="${order.receiverName }">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-1 control-label">手机号</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" name="receiverPhone" placeholder="请输入联系方式"
					   value="<c:if test='${not empty user.phone}'>${user.phone}</c:if>
							<c:if test='${empty user.phone}'>${order.receiverPhone }</c:if>">
				</div>
			</div>

		<hr />
		<div>
			<strong>选择银行：</strong>
			<p>
				<br />
				<input type="radio" name="pd_FrpId" value="ICBC-NET-B2C" />工商银行
				<img src="${pageContext.request.contextPath }/bank_img/icbc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="pd_FrpId" value="BOC-NET-B2C" />中国银行
				<img src="${pageContext.request.contextPath }/bank_img/bc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="pd_FrpId" value="ABC-NET-B2C" />农业银行
				<img src="${pageContext.request.contextPath }/bank_img/abc.bmp" align="middle" /> <br /> <br />

				<input type="radio" name="pd_FrpId" value="BOCO-NET-B2C" />交通银行
				<img src="${pageContext.request.contextPath }/bank_img/bcc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="pd_FrpId" value="PINGANBANK-NET" />平安银行
				<img src="${pageContext.request.contextPath }/bank_img/pingan.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="pd_FrpId" value="CCB-NET-B2C" checked="checked" />建设银行
				<img src="${pageContext.request.contextPath }/bank_img/ccb.bmp" align="middle" /> <br /> <br />

				<input type="radio" name="pd_FrpId" value="CEB-NET-B2C" />光大银行
				<img src="${pageContext.request.contextPath }/bank_img/guangda.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="pd_FrpId" value="CMBCHINA-NET-B2C" />招商银行
				<img src="${pageContext.request.contextPath }/bank_img/cmb.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="pd_FrpId" value="POST-NET-B2C" />邮政银行
				<img src="${pageContext.request.contextPath }/bank_img/post.bmp" align="middle" /><br /> <br />

			</p>
			<hr />
			<div style="text-align: right; margin-right: 20px;">
				<a href="javascript:confirmDialog()" class="btn btn-primary">
					订单支付
				</a>
			</div>
			<hr />
		</div>
		</form>
	</div>



	<!-- 引入footer.jsp -->
	<%@ include file="/WEB-INF/common/footer.jsp" %>

</body>

</html>