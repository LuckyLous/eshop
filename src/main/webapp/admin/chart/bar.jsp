<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
    <!-- 引入jquery -->
    <script src="${pageContext.request.contextPath}/static/jquery/jquery-3.3.1.min.js"></script>
    <!-- 引入bootstrap样式 -->
    <link href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <!-- 自定义css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/index.css" type="text/css" />
    <!-- 在线时间插件 -->
    <script src="https://cdn.bootcss.com/moment.js/2.18.1/moment-with-locales.min.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript">
        // 页面加载完毕
        $(function(){
            // 时间控件的配置
            $('#datetimepicker1').datetimepicker({
                format: 'YYYY-MM',
                locale: moment.locale('zh-cn'),
                defaultDate: "2018-03"
            });
            // 如果没有生成图片地址
            if(${empty filename}){
                // 隐藏图片
                $("img:eq(0)").css("display","none");
            }
        });

        // 点击事件=>弹出确认模态框，并将url赋值到隐藏域中
        function confirmDialog(url) {
            $("#confirmModal").modal({
                backdrop:"static"
            });
            if(url.indexOf("export") >= 0){
                $("#myModalBody").text("确认导出数据吗？");
            }else if(url.indexOf("bar") >= 0){
                $("#myModalBody").text("确认显示柱状图吗？");
            }
            // url补充日期
            $("#url").val(url+"?date="+$("input[name='date']").val());
        }
        // 确认框中确认按钮的点击事件
        function urlSubmit() {
            var url=$.trim($("#url").val());//获取会话中的隐藏属性URL
            $(location).attr("href",url);

            // 显示图片
            if(url.indexOf("bar") >= 0){
                $("img:eq(0)").css("display","block");
            }

        }
    </script>
    <style>
        img{
            display: block;
            width:1000px;
            height:600px;
            border:0px;
        }
    </style>
</head>
<body>
<%@ include file="/admin/common/confirm.jsp" %>
<%@ include file="/admin/common/nav-bar.jsp" %>
<div class="pageContainer">
    <%@ include file="/admin/common/nav-link.jsp" %>
        <div class="pageContent" style="margin: 0px 20px ;">
            <!-- 标题 -->
            <div class="row">
                <div class="col-md-12">
                    <h2>数据统计</h2>
                </div>
            </div>
            <!-- 基础操作按钮 -->
            <div class="row">
                <div class="col-sm-10">
                    <label class="col-sm-2 control-label">选择年月：</label>
                    <div class="col-sm-4">
                        <div class="input-group date" id='datetimepicker1'>
                            <input type="text" class="form-control" name="date" value="${param.date }" >
                            <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <button class="btn btn-primary" onclick="confirmDialog('${pageContext.request.contextPath }/chart/export')" >
                            <span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
                            导出销售数据
                        </button>
                    </div>
                    <div class="col-sm-2">
                        <button class="btn btn-primary" onclick="confirmDialog('${pageContext.request.contextPath }/chart/bar')" >
                            <span class="glyphicon glyphicon-export" aria-hidden="true"></span>
                            显示销量Top5
                        </button>
                    </div>
                </div>
            </div>
            <!-- 显示图表 -->
            <div class="row">
                <div class="col-md-12">
                    <img src="<c:if test="${not empty filename}">${pageContext.request.contextPath}/displayChart?filename=${filename}</c:if>"/>
                </div>
            </div>
        </div>
<%@ include file="/admin/common/footer.jsp" %>
</body>
</html>
