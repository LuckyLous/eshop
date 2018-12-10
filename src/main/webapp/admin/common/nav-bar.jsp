<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--顶部导航栏部分-->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" title="logoTitle" href="${pageContext.request.contextPath}/admin">后台管理</a>
            <a class="navbar-brand" title="logoTitle" href="${pageContext.request.contextPath}/" target="_blank">网站首页</a>
       </div>
       <div class="collapse navbar-collapse">
           <ul class="nav navbar-nav navbar-right">
               <li role="presentation">
                   <a href="#">当前用户：<span class="badge">${admin.username }</span></a>
               </li>
               <c:if test="${empty sessionScope.admin}">
                   <li>
                       <a href="${pageContext.request.contextPath}/admin/system/login.jsp">
                           <span class="glyphicon glyphicon-lock"></span>登录</a>
                   </li>
               </c:if>
               <c:if test="${not empty sessionScope.admin}">
                   <li>
                       <a href="${pageContext.request.contextPath}/admin/system/updatePwd.jsp">
                           <span class="glyphicon glyphicon-cog"></span>修改密码</a>
                   </li>
                   <li>
                       <a href="${pageContext.request.contextPath}/admin/logout">
                           <span class="glyphicon glyphicon-log-out"></span>退出登录</a>
                   </li>
               </c:if>
            </ul>
       </div>
    </div>      
</nav>