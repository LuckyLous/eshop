<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head></head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
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
	font {
		color: #3164af;
		font-size: 18px;
		font-weight: normal;
		padding: 0 10px;
	}
	/*给自定义校验加css*/
	.error{
		color:#FF0000;
		padding-left: 15px;
	}
</style>
<!-- 自定义jq -->
<script type="text/javascript" >
	// 页面加载完毕
	$(function(){
        //自定义校验规则
		$("#user_form").validate({
			rules:{
				"username":{
					"required":true,
                    remote: "${pageContext.request.contextPath}/user/checkUsername"
				},
				"password":{
					"required":true,
					"rangelength":[6,12]
				},
				"confirmpwd":{
					"required":true,
					"rangelength":[6,12],
					"equalTo":"#password"
				},
				"email":{
					"required":true,
					"email":true,
                    remote: "${pageContext.request.contextPath}/user/checkEmail"
				},
				"verifyCode":{
					"required":true,
                    remote: "${pageContext.request.contextPath}/user/checkVerify"
				}
			},
			messages:{
				"username":{
					"required":"用户名不能为空",
                    remote:"用户名已存在"
				},
				"password":{
					"required":"密码不能为空",
					"rangelength":"密码长度6-12位"
				},
				"confirmpwd":{
					"required":"确认密码不能为空",
					"rangelength":"密码长度6-12位",
					"equalTo":"两次密码不一致"
				},
				"email":{
					"required":"邮箱不能为空",
					"email":"邮箱格式不正确",
                    remote:"邮箱已经被注册"
				},
				"verifyCode":{
					"required":"验证码不能为空",
                    remote:"验证码不正确"
				}
			}
		});
	});

	/*
	 * 更新校验码
	 */ 
	function getVerify(img){
		$("#imgVerify").attr("src","${pageContext.request.contextPath }/user/getVerify?"+Math.random());
	}
</script>
</head>
<body>

	<!-- 引入header.jsp -->
	<jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>

	<div class="container"
		style="width: 100%; background: url('${pageContext.request.contextPath }/image/regist_bg.jpg');">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8"
				style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
				<font>会员注册</font>USER REGISTER
				<form action="${pageContext.request.contextPath }/user/regist" id="user_form"
					  method="post" class="form-horizontal" style="margin-top: 5px;">
					<div class="form-group">
						<label for="" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="username" name="username"
								placeholder="请输入用户名">
						</div>
					</div>
					<div class="form-group">
						<label for="" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="password" name="password"
								placeholder="请输入密码">
						</div>
					</div>
					<div class="form-group">
						<label for="" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="confirmpwd" name="confirmpwd"
								placeholder="请输入确认密码">
						</div>
					</div>
					<div class="form-group">
						<label for="" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input type="email" class="form-control" id="email" name="email"
								placeholder="Email">
						</div>
					</div>
					<div class="form-group">
						<label for="" class="col-sm-2 control-label">昵称</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="nickname" name="nickname"
								placeholder="请输入昵称">
						</div>
					</div>

					<div class="form-group">
						<label for="" class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="verifyCode" name="verifyCode">
						</div>
						<div class="col-sm-2">
							<img src="${pageContext.request.contextPath }/user/getVerify" 
								id="imgVerify" onclick="getVerify(this)" />
						</div>

					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" width="100" value="注册" name="submit"
								class="btn btn-success">
						</div>
					</div>
				</form>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>
	<!-- 引入footer.jsp -->
	<jsp:include page="/WEB-INF/common/footer.jsp"></jsp:include>
	
</body>
</html>
