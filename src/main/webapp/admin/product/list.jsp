<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <meta content="charset=UTF-8">	
    <title>商品管理</title>
    <!-- bootstrap -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-3.3.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css" />
	<script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript"></script>
	<!-- 自定义css -->	
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/index.css" type="text/css" />
    <script type="text/javascript">
    	// 页面加载完，如果一级分类不为空，就查询一次二级分类
    	$(function(){
    		var cid = $("#pCate").val();
    		if(cid != ""){
    			fillCate(cid);
    		}
    	});
    	// 一级分类的二级联动
	    $(function(){
	    	// 当一级分类改变时
			$("#pCate").change(function(){
				// 1.1清空二级分类option
				$("#cate").empty();
				// 1.2获取一级分类的id
				var cid = $("#pCate").val();
				// 1.3如果一级分类id不为空，则查询二级分类
				if(cid!=""){
					fillCate(cid);
				}else{
					$("#cate").append('<option value="">请选择...</option>');
				}
			});
		});
	    
	    // ajax根据一级分类id查询二级分类
	    function fillCate (cid){
	    	$.ajax({
				url:"${pageContext.request.contextPath}/admin/category/list/by/"+cid,
				type:"GET",
				success:function(result){
					if(result.code == 100){
						// 遍历二级分类
						$.each(result.data.cList,function(){
							// dom组合元素
			    			var opEle = $("<option></option>").attr("value",this.id)
			    						.append(this.name);

			    			// 回显选项
							if("${search.cateId}" == this.id){
								opEle.attr("selected","selected");
							}
			    			
							opEle.appendTo("#cate");
						});
					}
				}
			});
	    }

	    $(function () {
			//完成全选/全不选功能
            $("#check_all").click(function(){
                //attr获取checked是undefined;
                //我们这些dom原生的属性；attr获取自定义属性的值；
                //prop修改和读取dom原生属性的值
                $(".check_item").prop("checked",$(this).prop("checked"));
            });

            //check_item，选中后的回调函数
            $(document).on("click",".check_item",function(){
                //判断当前选择中的元素是否5个
                var flag = $(".check_item:checked").length == $(".check_item").length;
                $("#check_all").prop("checked",flag);
            });

            //点击全部删除，就批量删除
            $("#del_all_btn").click(function(){
                // 拼串
                var prodNames = "";
                var del_idstr = "";
                $.each($(".check_item:checked"),function(){
                    //this
                    prodNames += $(this).parents("tr").find("td:eq(2)").text()+",";
                    //组装id字符串
                    del_idstr += $(this).parents("tr").find("td:eq(1)").text()+"-";

                });
                // 去除prodNames多余的','
                prodNames = prodNames.substring(0, prodNames.length-1);
                //去除删除的id多余的'-'
                del_idstr = del_idstr.substring(0, prodNames.length-1);

                var del_url = "${pageContext.request.contextPath }/admin/product/delete/"+del_idstr+"?page=${param.page}";
                confirmDialog(del_url);
            });
        });

        // 删除按钮的点击事件=>弹出确认模态框，并将url赋值到隐藏域中
        function confirmDialog(url) {
            $("#confirmModal").modal({
                backdrop:"static"
            });
			if(url.indexOf("export") >= 0){
                $("#myModalBody").text("确认导出数据吗？");
			}else if(url.indexOf("delete") >= 0){
                $("#myModalBody").text("确认删除吗？");
            }else if(url.indexOf("down") >= 0){
                $("#myModalBody").text("确认下架吗？");
            }else if(url.indexOf("up") >= 0){
                $("#myModalBody").text("确认上架吗？");
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
<!--确认框div>
<%@ include file="/admin/common/confirm.jsp" %>
<!-- 顶部导航栏 -->
<%@ include file="/admin/common/nav-bar.jsp" %>
<!-- 中间主体内容部分 -->	
<div class="pageContainer">
    <!-- 左侧导航栏  -->
    <%@ include file="/admin/common/nav-link.jsp" %>
     <!-- main正文内容部分 -->
    <div class="pageContent" style="margin: 0px 10px ;">
    	<!-- 标题 -->
	 	<div class="row">
	 		<div class="col-md-12">
	 			<h2>商品列表</h2>
	 		</div>
	 	</div>
        <!-- 查询查询条件 -->
        <div class="row">
        	<form action="${pageContext.request.contextPath }/admin/product/list" method="post" class="form-inline">
        		<div class="form-group">
        			<label >状态</label>
        			<select id="search_order" name="status" class="form-control">
				   		<option value="" <c:if test="${empty search.status }">selected</c:if> >所有</option>
					  	<option value="1" <c:if test="${search.status == 1 }">selected</c:if> >正常</option>
					  	<option value="2" <c:if test="${search.status == 2 }">selected</c:if> >下架</option>
					</select>
					<label >所属一级分类:</label>
					<select name="bigCateId" id="pCate" class="form-control">
                    	<option value="">请选择...</option>
                    	<c:forEach items="${pList }" var="pc">
                       		<option value="${pc.id }"<c:if test="${search.bigCateId == pc.id}">selected</c:if>  >
                       			${pc.name }</option>
                    	</c:forEach>
                    	
                    </select>
                    <label >所属二级分类:</label>
                    <select name="cateId" id="cate" class="form-control">
                    	<option value="">请选择...</option>
                    </select>
                    <label >商品名称:</label>
                    <input type="text" class="form-control" name="prodName" placeholder="商品名称" value="${search.prodName} ">
                    <input type="submit" class="btn btn-primary" value="查询">
					<a href="${pageContext.request.contextPath}/admin/product/list" class="btn btn-link" >刷新</a>
            	</div>
        	</form>
        </div>
        <!-- 基础操作 -->
        <div class="row">
        	<div class="col-md-12">
        		<a href="${pageContext.request.contextPath }/admin/product/add" class="btn btn-primary">
        			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
		 			新增
        		</a>
        		<button id="del_all_btn" class="btn btn-danger">
        			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
        			批量删除
        		</button>
				<!--备份和还原-->
				<button class="btn btn-primary" onclick="confirmDialog('${pageContext.request.contextPath }/admin/product/export')" >
					<span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>
					导出数据
				</button>
				<%--<a href="${pageContext.request.contextPath }/admin/product/import.jsp" class="btn btn-primary">
					<span class="glyphicon glyphicon-import" aria-hidden="true"></span>
					用模版批量导入数据
				</a>--%>
	 		</div>
        </div>
        <!-- 显示数据列表 -->
        <div class="row">
            <div class="col-md-12">
	            <table class="table table-hover">
	            	<tr>
	                    <th><input type="checkbox" id="check_all" ></th>
	                    <th>序号</th>
	                    <th>名称</th>
	                    <th>图片</th>
	                    <th>库存数量</th>
	                    <th>折扣</th>
	                    <th>售价</th>
	                    <th>上架时间</th>
	                    <th>热门</th>
	                    <th>所属类别</th>
	                    <th>操作</th>
	                </tr>
	                <c:forEach items="${pageInfo.list }" var="prod" varStatus="status">
	                	<tr>
		                	<td><input type="checkbox" class="check_item" ></td>
							<td>${prod.id }</td>
		                    <td>${prod.prodName }</td>
		                    <td><img style="width:40px;height:45px;"  src="${pageContext.request.contextPath }${prod.image }"></td>
		                    <td>${prod.num }</td>
		                    <td>${prod.discount }折</td>
		                    <td>${prod.shopPrice }</td>
		                    <td><fmt:formatDate value="${prod.addTime }" pattern="yyyy-MM-dd"></fmt:formatDate></td>
		                    <td>
	                    		<c:if test="${prod.isHot == 1}">是</c:if>
	                    		<c:if test="${prod.isHot == 0}">否</c:if>
		                    </td>
		                    <td>
		                     	${prod.bigCate.name }->${prod.cate.name }
		                    </td>
		                    <td>
		                    	<a href="${pageContext.request.contextPath }/admin/product/update/${prod.id}?page=${param.page}"
								   class="btn btn-primary btn-sm">
		 							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
		 							修改
		 						</a>
		 						<c:if test="${prod.status == 1}">
		                     		<button onclick="confirmDialog('${pageContext.request.contextPath }/admin/product/down/${prod.id}')"
											class="btn btn-link btn-sm">
		 								<span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span>
		 								下架
		 							</button>
		                     	</c:if>
		                    	<c:if test="${prod.status == 2}">
		                    		<button onclick="confirmDialog('${pageContext.request.contextPath }/admin/product/up/${prod.id}')"
											class="btn btn-link btn-sm">
		 								<span class="glyphicon glyphicon-menu-up" aria-hidden="true"></span>
		 								上架
		 							</button>
		                    	</c:if>
		 						<button onclick="confirmDialog('${pageContext.request.contextPath }/admin/product/delete/${prod.id}?page=${param.page}')"
										class="btn btn-warning btn-sm">
			 						<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
			 						删除
			 					</button>
		                    </td>
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
        	<div class="col-md-6">
				<nav aria-label="Page navigation">
				<ul class="pagination">
					<c:if test="${pageInfo.isFirstPage  }">
						<li class="disabled"><a href="javascript:void(0)" aria-label="Previous">首页</a></li>
					</c:if>
					<c:if test="${not pageInfo.isFirstPage }">
						<li><a href="${pageContext.request.contextPath }/admin/product/list?page=1
							<c:if test="${not empty search.status }">&status=${search.status }</c:if>
							<c:if test="${not empty search.bigCateId }">&bigCateId=${search.bigCateId }</c:if>
							<c:if test="${not empty search.cateId }">&cateId=${search.cateId }</c:if>
							<c:if test="${not empty search.prodName }">&prodName=${search.prodName }</c:if>" >首页</a></li>
					</c:if>
					<c:if test="${pageInfo.hasPreviousPage }">
						<li>
							<a href="${pageContext.request.contextPath }/admin/product/list?page=${pageInfo.prePage}
								<c:if test="${not empty search.status }">&status=${search.status }</c:if>
								<c:if test="${not empty search.bigCateId }">&bigCateId=${search.bigCateId }</c:if>
								<c:if test="${not empty search.cateId }">&cateId=${search.cateId }</c:if>
								<c:if test="${not empty search.prodName }">&prodName=${search.prodName }</c:if>" aria-label="Previous"> 
								<span aria-hidden="true">&laquo;</span>
								
							</a>
						</li>						   
					</c:if>
					<c:forEach items="${pageInfo.navigatepageNums }" var="pageNum">
						<c:if test="${pageNum == pageInfo.pageNum }">
							<li class="active"><a href="#">${pageNum }</a></li>
						</c:if>
						<c:if test="${pageNum != pageInfo.pageNum }">
							<li><a href="${pageContext.request.contextPath }/admin/product/list?page=${pageNum }
								<c:if test="${not empty search.status }">&status=${search.status }</c:if>
								<c:if test="${not empty search.bigCateId }">&bigCateId=${search.bigCateId }</c:if>
								<c:if test="${not empty search.cateId }">&cateId=${search.cateId }</c:if>
								<c:if test="${not empty search.prodName }">&prodName=${search.prodName }</c:if>" >${pageNum }</a></li>
						</c:if>
					</c:forEach>
					<c:if test="${pageInfo.hasNextPage }">
						<li>
							<a href="${pageContext.request.contextPath }/admin/product/list?page=${pageInfo.nextPage }
								<c:if test="${not empty search.status }">&status=${search.status }</c:if>
								<c:if test="${not empty search.bigCateId }">&bigCateId=${search.bigCateId }</c:if>
								<c:if test="${not empty search.cateId }">&cateId=${search.cateId }</c:if>
								<c:if test="${not empty search.prodName }">&prodName=${search.prodName }</c:if>" aria-label="Next"> 
								<span aria-hidden="true">&raquo;</span>
							</a>
						</li>						   
					</c:if>
					<c:if test="${not pageInfo.isLastPage }">
						<li><a href="${pageContext.request.contextPath }/admin/product/list?page=${pageInfo.pages }
							<c:if test="${not empty search.status }">&status=${search.status }</c:if>
							<c:if test="${not empty search.bigCateId }">&bigCateId=${search.bigCateId }</c:if>
							<c:if test="${not empty search.cateId }">&cateId=${search.cateId }</c:if>
							<c:if test="${not empty search.prodName }">&prodName=${search.prodName }</c:if>" >末页</a></li>
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
<!-- 底部页脚部分 -->
<%@ include file="/admin/common/footer.jsp" %>
</body>
</html>