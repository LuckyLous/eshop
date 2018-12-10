<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 登录 注册 购物车... -->
<div class="container-fluid">
	<div class="col-md-4">
		<!-- 此处可放logo -->
		<img src=""  />
	</div>
	<div class="col-md-5">
		<!-- 此处可放宣传优势 -->
		<img src="" />
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
			<!-- 使用静态包含，这里不用立刻解析，等到了被导入时，再结合index页面一并运行，所以只需在index导入标签库即可
				但是这样有弊端，其他页面，product_info页面也需要导入，这样容易漏掉，索性这里直接导入，这样不容易漏掉
			 -->
			<c:if test="${empty sessionScope.user }">
				<li><a href="${pageContext.request.contextPath }/user/login.html">登录</a></li>
				<li><a href="${pageContext.request.contextPath }/user/regist.html">注册</a></li>
			</c:if>
			<c:if test="${not empty sessionScope.user }">
				${user.nickname}：
				<li><a href="${pageContext.request.contextPath }/user/info">个人信息</a></li>
				<li><a href="${pageContext.request.contextPath }/order/list">我的订单</a></li>
				<li><a href="${pageContext.request.contextPath }/user/logout">退出</a></li>
			</c:if>
			<li><a href="${pageContext.request.contextPath }/cart/cart.html">我的购物车</a></li>
			<li><a href="${pageContext.request.contextPath }/product/list/history">浏览历史</a></li>
		</ol>
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath }/">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<!-- 分类下拉列表 -->
				<ul class="nav navbar-nav" id="c_ul">
				
				</ul>
				<!-- 查询关键字 -->
				<form action="${pageContext.request.contextPath}/search"
					id="search_form" class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" name="keyword" value="${query }" class="form-control" placeholder="Search">
						<input type="hidden" name="cateName" id="cate_name" value="${cateName }"/> 
						<input type="hidden" name="price" id="price" value="${price }"/> 
						<input type="hidden" name="sort" id="sort" value="${sort }"/> 
					</div>
					<button onclick="query()" type="button" class="btn btn-default">搜索</button>
				</form>
			</div>
		</div>
	</nav>
</div>
<script type="text/javascript">
	function query() {
		//执行关键词查询时清空过滤条件
		document.getElementById("cate_name").value="";
		document.getElementById("price").value="";
		//执行查询
		queryList();
	}

    //提交表单
    function queryList() {
		document.getElementById("search_form").submit();
	}
	//页面加载完毕
	$(function(){
		//发送ajax，根据图书的cid查询所有一级分类
		$.ajax({
			url:"${pageContext.request.contextPath}/category/list/1",
			type:"GET",
			success:function(result){
				// 1.遍历json列表，获取每一个分类，包装成li标签，插入到ul内部
				$.each(result.data.cList,function(){
					//2、DOM操作li元素
					$("<li></li>").attr("role","presentation")
						.append($("<a></a>").attr("href","javascript:listByCid("+this.id+",true)")
									.append(this.name)
						).appendTo("#c_ul");

				});
			}
		});
		
	});
	// 根据一级分类ajax查询二级分类
    function listByCid(cid,is_sync) {
        // 只有导航条那里同步，其余都是异步
        if(is_sync){
		    $(location).attr("href","${pageContext.request.contextPath}/product/list?bigCid="+cid);
		}else{
            // 如果是product页面，异步加载
            // 清空之前的ul li
            $("#menu").empty();
            var ulEle = $("<ul class='nav nav-pills nav-stacked'></ul>");
            $.ajax({
                url:"${pageContext.request.contextPath}/category/list/"+cid,
                type:"GET",
                success:function(result){
                    $.each(result.data.cList,function () {
                        $("<li></li>").attr("role","presentation")
                            .append($("<a></a>").append(this.name)
                                .attr("href","${pageContext.request.contextPath}/product/list?cid="+this.id)
                            ).appendTo(ulEle);

                        // 遍历完添加到ul
                        $("#menu").append(ulEle);
                    })

                }
            });
        }

    }
</script>