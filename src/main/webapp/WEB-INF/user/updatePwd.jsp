<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>修改登录密码</title>
    <script src="${pageContext.request.contextPath }/static/jquery/jquery-3.3.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
    <script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/style.css" type="text/css" />
    <!-- 引入表单校验jquery校验 -->
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/jquery/jquery.validate.min.js"></script>
    <style type="text/css">
        .error{
            color:#FF0000;
            padding-left: 12px;
        }
    </style>
    <script type="text/javascript">
        // 保存按钮的点击事件=>弹出确认模态框
        function confirmDialog() {
            $("#confirmModal").modal({
                backdrop:"static"
            });

            $("#myModalBody").text("确认修改吗？");
        }
        // 确认框中确认按钮的点击事件
        function urlSubmit() {
            // 不用获取url，直接提交form
            $("#pwd_form").submit();
        }

        // 表单校验
        $(function () {
            $("#pwd_form").validate({
                rules:{
                    "oldPwd":{
                        "required":true
                    },
                    "password":{
                        "required":true,
                        "rangelength":[6,12]
                    },
                    "confirmpwd":{
                        "required":true,
                        "rangelength":[6,12],
                        "equalTo":"input[name='password']"
                    }
                },
                messages:{
                    "oldPwd":{
                        "required":"请输入旧密码"
                    },
                    "password":{
                        "required":"请输入新密码",
                        "rangelength":"密码长度6-12位"
                    },
                    "confirmpwd":{
                        "required":"请再次输入新密码",
                        "rangelength":"密码长度6-12位",
                        "equalTo":"两次密码不一致"
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
        <div class="modal-dialog" role="document"  style="width: 600px; height: 500px;text-align: center;">
            <!-- 模态框标题 -->
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">修改密码</h4>
            </div>
            <!-- 模态框body -->
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="pwd_form"
                      action="${pageContext.request.contextPath }/user/update/pwd ">
                    <!-- 设置username隐藏域 -->
                    <input type="hidden" name="username" value="${sessionScope.user.username }">
                    <!-- 个人信息属性 -->
                    <div class="form-group">
                        <div class="col-sm-5">
                            <input type="password" class="form-control" name="oldPwd" placeholder="请输入旧密码" />
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-5">
                            <input type="password" class="form-control" name="password" placeholder="请输入新密码" />
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-5">
                            <input type="password" class="form-control" name="confirmpwd" placeholder="请再次输入新密码" />
                            <span class="help-block"></span>
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
