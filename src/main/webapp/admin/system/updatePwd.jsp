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
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/index.css" type="text/css" />
    <!-- 引入表单校验jquery校验 -->
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/jquery/jquery.validate.min.js"></script>
    <style type="text/css">
        .error{
            color:#FF0000;
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
            $("form:eq(0)").submit();
        }

        // 页面加载完毕
        $(function () {
            // 表单校验
            $("form:eq(0)").validate({
                rules:{
                    "oldPwd":{
                        "required":true
                    },
                    "newPwd":{
                        "required":true,
                        "rangelength":[6,12]
                    },
                    "confirm_pwd":{
                        "required":true,
                        "rangelength":[6,12],
                        "equalTo":"input[name='newPwd']"
                    }
                },
                messages:{
                    "oldPwd":{
                        "required":"请输入旧密码"
                    },
                    "newPwd":{
                        "required":"请输入新密码",
                        "rangelength":"密码长度6-12位"
                    },
                    "confirm_pwd":{
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
<%@ include file="/admin/common/confirm.jsp" %>
<%@ include file="/admin/common/nav-bar.jsp" %>
<div class="pageContainer">
    <%@ include file="/admin/common/nav-link.jsp" %>
    <!-- 正文内容部分 -->
    <div class="pageContent" style="margin: 0px 10px;">
        <!--模态框开始-->
        <div class="modal-dialog" role="document"  style="width: 600px; height: 500px;text-align: center;">
            <!-- 模态框标题 -->
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">修改密码</h4>
            </div>
            <!-- 模态框body -->
            <div class="modal-body">
                <form class="form-horizontal" method="post" action="${pageContext.request.contextPath }/admin/update/pwd">
                    <!-- 设置id隐藏域 -->
                    <input type="hidden" name="username" value="${admin.username }">
                    <!-- 个人信息属性 -->
                    <div class="form-group">
                        <div class="col-sm-5">
                            <input type="password" class="form-control" name="oldPwd" placeholder="请输入旧密码" />
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-5">
                            <input type="password" class="form-control" name="newPwd" placeholder="请输入新密码" />
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-5">
                            <input type="password" class="form-control" name="confirm_pwd" placeholder="请再次输入新密码" />
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
</div>
<!-- 引入footer.jsp -->
<%@ include file="/admin/common/footer.jsp" %>

</body>
</html>
