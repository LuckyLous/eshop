<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>个人信息</title>
    <script src="${pageContext.request.contextPath }/static/jquery/jquery-3.3.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
    <script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/style.css" type="text/css" />
    <!--引入城市联动的js-->
    <script src="${pageContext.request.contextPath }/static/js/distpicker.data.js"></script>
    <script src="${pageContext.request.contextPath }/static/js/distpicker.js"></script>
    <script src="${pageContext.request.contextPath }/static/js/main.js"></script>
    <style type="text/css">
        .error{
            color:#FF0000;
            padding-left: 15px;
        }
    </style>
    <!-- 引入表单校验jquery校验 -->
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/jquery/jquery.validate.min.js"></script>
    <script type="text/javascript">
        // 保存按钮的点击事件=>弹出确认模态框
        function confirmDialog() {
            $("#confirmModal").modal({
                backdrop:"static"
            });
            // 更新提示信息
            $("#myModalBody").text("确认更新信息吗？");
            // -切割字符,设置隐藏域
            var address = $("#province").val() + "-"
                            +$("#city").val() + "-"
                            + $("#district").val() + "-"
                            + $("#detail_address").val();
            $("input[name='address']").val(address);
        }
        // 确认框中确认按钮的点击事件
        function urlSubmit() {
            // 不用获取url，直接提交form
            $("#user_form").submit();
        }

        // 回显收货地址
        function showAddress() {
            var adds = "${user.address}".split('-');
            // 回显是在编辑时，$(#).trigger();方法是根据当前操作的下拉框是省或是市来初始化之后的下拉列表框
            // 比如我先选择了省，则市和区就会被初始化
            $("#province").val(adds[0]);
            $('#province').trigger("change");
            $("#city").val(adds[1]);
            $('#city').trigger("change");
            $("#district").val(adds[2]);

            $("#detail_address").val(adds[3]);
        }

        $(function () {
            // 回显收货地址
            showAddress();

            // 手机号码验证
            jQuery.validator.addMethod("isMobile", function(value, element) {
                var length = value.length;
                var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
                return this.optional(element) || (length == 11 && mobile.test(value));
            }, "请正确填写您的手机号码");

            // 表单校验
            $("#user_form").validate({
                rules:{
                    "nickname":{
                        "required":true
                    },
                    "phone":{
                        minlength : 11,
                        isMobile : true
                    }
                },
                messages:{
                    "nickname":{
                        "required":"昵称不能为空"
                    },
                    "phone":{
                        minlength : "确认手机不能小于11个字符",
                        isMobile : "请正确填写您的手机号码"
                    }
                }
            });

        });

    </script>
</head>
<body>
<!--确认框div>
<%@ include file="/admin/common/confirm.jsp" %>
<!-- 引入header.jsp -->
<%@ include file="/WEB-INF/common/header.jsp" %>

    <!--中间界面-->
    <div class="container" style="width: 1210px; margin: 0 auto;">
        <!--模态框开始-->
        <div class="modal-dialog" role="document"  style="width: 700px; height: 500px;text-align: center;">
            <!-- 模态框标题 -->
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">个人信息</h4>
            </div>
            <!-- 模态框body -->
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="user_form"
                      action="${pageContext.request.contextPath }/user/update" >
                    <!-- 设置id、address隐藏域 -->
                    <input type="hidden" name="id" value="${user.id }">
                    <input type="hidden" name="address" >
                    <!-- 个人信息属性 -->
                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户名</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="username" value="${user.username}" readonly />
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">昵称</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="nickname" value="${user.nickname}" />
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">邮箱</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="email" value="${user.email}" readonly />
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">手机号码</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" name="phone" value="${user.phone}" />
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group" style="width: 900px;">
                        <label class="col-sm-2 control-label">收货地址</label>
                        <div class="col-sm-10" id="distpicker3">
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
                                       placeholder="请输入详细地址" />
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">其他</label>
                        <div class="col-sm-5">
                            <a href="${pageContext.request.contextPath }/user/updatePwd" class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                修改密码
                            </a>
                        </div>
                    </div>

                    <!-- 模态框操作 -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" onclick="history.go(-1)" data-dismiss="modal">
                            返回
                        </button>
                        <button onclick="confirmDialog()" type="button" class="btn btn-primary" >保存</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

<!-- 引入footer.jsp -->
<%@ include file="/WEB-INF/common/footer.jsp" %>

</body>
</html>
