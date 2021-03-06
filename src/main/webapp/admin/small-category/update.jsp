<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改二级分类</title>
    <!-- 引入bootstrap样式 -->
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-3.3.1.min.js"></script>
    <link href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <!-- 自定义css -->	
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/index.css" type="text/css" />
	<!-- 引入表单校验jquery校验 -->
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/jquery/jquery.validate.min.js"></script>
	<style type="text/css">
		.error{
			color:#FF0000;
		}
	</style>
	<script type="text/javascript">
        // 页面加载完毕
        $(function(){
            // 表单校验
            $("form:eq(0)").validate({
                rules:{
                    "parentId":{
                        required:true
                    },
                    "name":{
                        required:true,
                        rangelength:[2,10],
                        remote: '${pageContext.request.contextPath}/admin/category/checkName'
                    }
                },
                messages:{
                    "parentId":{
                        required:"请选择分类"
                    },
                    "name":{
                        required:"请输入分类名称",
                        rangelength:"输入值必须介于2和10之间",
                        remote:"分类重复了"
                    }
                }
            });
        });
	</script>
</head>
<body>
<%@ include file="/admin/common/nav-bar.jsp" %>
<div class="pageContainer">
	<%@ include file="/admin/common/nav-link.jsp" %>
    <!--主界面-->
    <div class="pageContent">
    	<div class="modal-dialog" role="document"  style="width: 60%; height: 60%;margin-left: 10%;">
    		<!-- 模态框标题 -->
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel">修改二级分类</h4>
			</div>
        	<!-- 模态框body -->
			<div class="modal-body">
                <form class="form-horizontal" method="post"
                	action="${pageContext.request.contextPath }/admin/category/small/update?page=${param.page}" >
                	<!-- 设置id隐藏域 -->
                	<input type="hidden" name="id" value="${cate.id }">
                    <!-- 一级分类属性 -->
                	<div class="form-group">
                		<label class="col-sm-2 control-label">所属一级分类</label>
						<div class="col-sm-4">
							<select name="parentId" class="form-control">
                            	<c:forEach items="${list }" var="pc">
									<option value="${pc.id }"
										<c:if test="${pc.id==cate.parentId }">selected</c:if> >
									${pc.name }</option>
								</c:forEach>
	                        </select>
							<span class="help-block"></span>
						</div>
						<label class="col-sm-2 control-label">二级分类名称</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="name" value="${cate.name }" >
							<span class="help-block"></span>
						</div>
					</div>
                    <!-- 模态框操作 -->
					<div class="modal-footer">
						<button type="button" class="btn btn-default" onclick="history.go(-1)"
							data-dismiss="modal" >返回</button>
						<input type="submit" class="btn btn-primary" value="保存" />
					</div>
                </form>
       		</div>
        </div>
    </div>
</div>
</body>
</html>