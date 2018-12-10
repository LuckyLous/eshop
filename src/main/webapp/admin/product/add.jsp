<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <meta content="charset=UTF-8">
    <title>商品添加</title>	
   	 <!-- bootstrap -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.3.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
	<script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
	<!-- 自定义css -->	
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/index.css" type="text/css" />
	<!-- 在线时间插件 -->
	<script src="https://cdn.bootcss.com/moment.js/2.18.1/moment-with-locales.min.js"></script>  
	<link href="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet">  
	<script src="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
	<!-- 引入表单校验jquery校验 -->
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/jquery/jquery.validate.min.js"></script>
	<style type="text/css">
		.error{
			color:#FF0000;
			padding-left: 12px;
		}
	</style>
	<script type="text/javascript">
		// 页面加载完毕
		$(function(){
			// 一级分类的二级联动
			$("#pCate").change(function(){
				// 清空二级分类option
				$("#cate").empty();
				var cid = $("#pCate").val();
				if(cid!=""){// 请选择时，cid=""
					fillCate(cid);
				}				
			});
			
			// ajax查询二级分类
			function fillCate(cid){
				$.ajax({
					url:"${pageContext.request.contextPath}/admin/category/list/by/"+cid,
					type:"GET",
					success:function(result){
						if(result.code == 100){
							// 遍历二级分类
							$.each(result.data.cList,function(){
								// dom组合元素
				    			var opEle = $("<option></option>").attr("value",this.id)
				    						.append(this.name)
				    						.appendTo("#cate");
							});
						}
					}
				});
			}
			
			// 时间控件的配置
	 		$('#datetimepicker1').datetimepicker({  
	 	        format: 'YYYY-MM',
	 	        locale: moment.locale('zh-cn')  
	 	    });
	 		$('#datetimepicker2').datetimepicker({  
	 	        format: 'YYYY-MM-DD',  
	 	        locale: moment.locale('zh-cn')  
	 	    });

            // 隐藏预览图片
            $("#preview").css("display","none");

	 		// 表单校验
            $("form:eq(0)").validate({
                rules:{
                    "prodName":{
                        required:true
                    },
                    "shopPrice":{
                        required:true,
                        number:true,
                        range:[1,1000]
                    },
                    "num":{
                        required:true,
                        number:true,
                        range:[1,9999]
                    },
                    "file":{
                        required:true
                    },
                    "cateId":{
                        required:true
                    },
                    "book.bookName":{
                        required:true
                    },
                    "book.author":{
                        required:true
                    },
                    "book.press":{
                        required:true
                    },
                    "book.marketPrice":{
                        required:true,
                        number:true,
                        range:[1,1000]
                    },
                    "book.publishDate":{
                        required:true
                    },
                    "book.printDate":{
                        dateISO:true
                    }
                },
                messages:{
                    "prodName":{
                        required:"请输入商品名称"
                    },
                    "shopPrice":{
                        required:"请输入售价",
                        number:"请输入合法的数字",
                        range:"输入值必须介于1和1000之间"
                    },
                    "num":{
                        required:"请输入库存数量",
                        number:"请输入合法的数字",
                        range:"输入值必须介于1和9999之间"
                    },
                    "file":{
                        required:""
                    },
                    "cateId":{
                        required:"请选择分类"
                    },
                    "book.bookName":{
                        required:"请输入图书名称"
                    },
                    "book.author":{
                        required:"请输入作者"
                    },
                    "book.press":{
                        required:"请输入出版社"
                    },
                    "book.marketPrice":{
                        required:"请输入定价",
                        number:"请输入合法的数字",
                        range:"输入值必须介于1和1000之间"
                    },
                    "book.publishDate":{
                        required:"请选择日期"
                    },
                    "book.printDate":{
                        dateISO:"请输入正确格式的日期"
                    }
                }
            });

		});

        // 预览图片
        function imgPreview(fileDom) {
            //判断是否支持FileReader
            if (window.FileReader) {
                var reader = new FileReader();
            } else {
                alert("您的设备不支持图片预览功能，如需该功能请升级您的设备！");
            }

            //获取文件
            var file = fileDom.files[0];
            var imageType = /^image\//;
            //是否是图片
            if (!imageType.test(file.type)) {
                alert("请选择图片！");
                return;
            }
            //读取完成
            reader.onload = function(e) {
                //获取图片dom
                var img = document.getElementById("preview");
                //图片路径设置为读取的图片
                img.src = e.target.result;
            };
            reader.readAsDataURL(file);
			// 显示预览图片
            $("#preview").css("display","block");
        }
	</script>
</head>	
<body>
<!-- 顶部导航栏 -->
<%@ include file="/admin/common/nav-bar.jsp" %>
<div class="pageContainer">
	<!-- 左侧导航栏  -->
    <%@ include file="/admin/common/nav-link.jsp" %>
    <!-- 正文内容部分 -->
		<div class="pageContent" style="margin: 0px 10px;">
			<!-- 模态框，可设置长宽，位置 -->
			<div class="modal-dialog" role="document"  style="width: 60%; height: 60%;margin-left: 10%;">
				<!-- 模态框标题 -->
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">商品添加</h4>
				</div>
				<!-- 模态框body -->
				<div class="modal-body">
					<form class="form-horizontal" method="post" enctype="multipart/form-data"
					 	action="${pageContext.request.contextPath}/admin/product/save" >
						<!-- 商品属性 -->
						<div class="form-group">
							<label class="col-sm-2 control-label">商品名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="prodName" placeholder="请输入名称">
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">价格</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="shopPrice">
								<span class="help-block"></span>
							</div>
							<label class="col-sm-2 control-label">库存数量</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="num"> 
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">上传图片</label>
							<div class="col-sm-4">
								<input type="file" name="file" onchange="imgPreview(this)">
								<img id="preview" src="" style="width: 100px;height: 100px;" />
							</div>
							<label class="col-sm-2 control-label">是否热门</label>
							<div class="col-sm-4">
								<select name="isHot" class="form-control">
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">一级分类</label>
							<div class="col-sm-4">
								<select class="form-control" name="bigCateId" id="pCate">
									<option value="">请选择...</option>
									<c:forEach items="${list }" var="pc">
										<option value="${pc.id }">${pc.name }</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-2 control-label">二级分类</label>
							<div class="col-sm-4">
								<select class="form-control" name="cateId" id="cate">
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">商品描述</label>
							<div class="col-sm-10">
								<textarea class="form-control" name="description" rows="4"></textarea>
							</div>
						</div>

						<!-- 图书属性 -->
						<div class="form-group">
							<label class="col-sm-2 control-label">图书名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="book.bookName">
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">作者</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="book.author">
								<span class="help-block"></span>
							</div>
							<label class="col-sm-2 control-label">出版社</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="book.press">
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">市场定价</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="book.marketPrice">
							</div>
							<label class="col-sm-2 control-label">版次</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="book.edition"
									value="1">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">出版时间</label>
							<div class="col-sm-4">
								<div class="input-group date" id='datetimepicker1'>
									<input type="text" class="form-control" name="book.publishDate">
									<span class="input-group-addon"> <i class="glyphicon glyphicon-calendar"></i></span>
								</div>
							</div>
							<label class="col-sm-2 control-label">印刷时间</label>
							<div class="col-sm-4">
								<div class='input-group date' id='datetimepicker2'>
									<input type="text" class="form-control" name="book.printDate">
									<span class="input-group-addon"> <i class="glyphicon glyphicon-calendar"></i></span>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">纸张</label>
							<div class="col-sm-4">
								<input type="text" class="form-control" name="book.paper"
									value="胶版纸">
							</div>
							<label class="col-sm-2 control-label">开本</label>
							<div class="col-sm-4">
								<select name="book.size" class="form-control">
									<option value="16">16开</option>
									<option value="32" selected="selected">32开</option>
									<option value="64">64开</option>
								</select>
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