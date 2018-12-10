<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<!-- bootstrap -->
<script src="${pageContext.request.contextPath }/static/jquery/jquery-3.3.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
<script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/style.css" type="text/css" />
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
	.container .row div {
		/* position:relative;
			float:left; */
	}
	font {
		color: #666;
		font-size: 22px;
		font-weight: normal;
		padding-right: 17px;
	}
	.error{
		color:#FF0000;
		padding-left: 15px;
	}
</style>
<!-- 自定义jq -->
<script type="text/javascript">
    // 页面加载完毕
	$(function(){
		// 记住用户名，从cookie中解码
		var val = "${cookie.saveName.value}";
		$("#username").val(decodeURIComponent(val));

        $("#user_form").validate({
            rules:{
                "username":{
                    required:true
                },
                "password":{
                    required:true
                },
                "verifyCode":{
                    required:true,
                    remote: "${pageContext.request.contextPath}/user/checkVerify"
                }
            },
            messages:{
                "username":{
                    required:"请输入邮箱或用户名"
                },
                "password":{
                    required:"密码不能为空"
                },
                "verifyCode":{
                    required:"验证码不能为空",
                    remote:"验证码不正确"
                }
            }
        });
		
	});
	
	//获取验证码
	function getVerify(img){
		$("#imgVerify").attr("src","${pageContext.request.contextPath }/user/getVerify?"+Math.random());
	}

</script>
</head>
<body>
	<!-- 引入header.jsp -->
	<jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>
	
	<div class="container"
		style="width: 100%; height: 460px; background: #F0F8FF url('${pageContext.request.contextPath }/image/login_bg.jpeg') no-repeat;">
		<div class="row">
			<div class="col-md-7">
				<!-- <img src="" width="500" height="330" title="会员登录"> -->
			</div>

			<div class="col-md-5">
				<div style="width: 440px; border: 1px solid #E7E7E7; padding: 20px 0 20px 30px; border-radius: 5px; margin-top: 60px; background: #fff;">
					<font>会员登录</font>USER LOGIN
					<div>&nbsp;</div>
					<form action="${pageContext.request.contextPath }/user/login" id="user_form"
						  method="post" class="form-horizontal">
						<div class="form-group">
							<%--<label for="username" class="col-sm-2 control-label">邮箱或用户名</label>--%>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="username" name="username"
									value="${requestScope.username or cookie.saveName.value }"	placeholder="请输入邮箱或用户名">
							</div>
						</div>
						<div class="form-group">
							<%--<label for="" class="col-sm-2 control-label">密码</label>--%>
							<div class="col-sm-8">
								<input type="password" class="form-control" id="password" name="password"
									placeholder="请输入密码">
							</div>
						</div>
						<div class="form-group">
							<%--<label for="" class="col-sm-2 control-label">验证码</label>--%>
							<div class="col-sm-4">
								<input type="text" class="form-control" id="verifyCode" name="verifyCode"
									placeholder="请输入验证码">
							</div>
							<div class="col-sm-4">
								<img src="${pageContext.request.contextPath }/user/getVerify"
									id="imgVerify" onclick="getVerify(this)" />
							</div>
						</div>
						<div class="form-group" >
							<!-- 提示信息 -->
							<label class="col-sm-6 control-label">${msg }</label>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<div class="checkbox">
									<label> 
										<input type="checkbox" name="autoLogin" value="ok" <c:if test="${not empty cookie.autoLogin.value}">checked</c:if>> 自动登录
									</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
									<label> 
										<input type="checkbox" name="saveName" value="ok" <c:if test="${not empty cookie.saveName.value}">checked</c:if> > 记住用户名
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" width="100" value="登录" name="submit"
									   class="btn btn-success">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="${pageContext.request.contextPath }/user/regist.html" class="btn btn-primary">注册</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/WEB-INF/common/footer.jsp"></jsp:include>
</body>
</html>