<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <!-- 左侧导航栏 -->
     <div class="pageSidebar">
         <ul class="nav nav-stacked nav-pills">
             <li role="presentation">
                 <a href="${pageContext.request.contextPath}/admin/product/list" >
                     <span class="glyphicon glyphicon-th-list"></span>商品管理</a>
             </li>
             <li class="dropdown">
                 <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    	分类管理<span class="caret"></span>
                 </a>
                 <ul class="dropdown-menu">
                     <li>
                         <a href="${pageContext.request.contextPath}/admin/category/list" >一级分类管理</a>
                     </li>
                     <li>
                         <a href="${pageContext.request.contextPath}/admin/category/small/list" >二级分类管理</a>
                     </li>
                 </ul>
             </li>
             <li role="presentation">
                 <a href="${pageContext.request.contextPath}/admin/order/list?page=1">
                     <span class="glyphicon glyphicon-th-list"></span>订单管理</a>
             </li>
             <li role="presentation">
                 <a href="${pageContext.request.contextPath}/admin/search/search.jsp">
                     <span class="glyphicon glyphicon-send"></span>索引库管理</a>
             </li>
             <li role="presentation">
                 <a href="${pageContext.request.contextPath}/admin/user/list">
                     <span class="glyphicon glyphicon-th-list"></span>会员管理</a>
             </li>
             <li role="presentation">
                 <a href="${pageContext.request.contextPath}/admin/chart/bar.jsp">
                     <span class="glyphicon glyphicon-picture"></span>数据统计</a>
             </li>
             <%--<li role="presentation">
                 <a href="${pageContext.request.contextPath}/admin/product/import.jsp">数据导入</a>
             </li>--%>
         </ul>
     </div>
     <!-- 左侧导航和正文内容的分隔线 -->
     <div class="splitter"></div>