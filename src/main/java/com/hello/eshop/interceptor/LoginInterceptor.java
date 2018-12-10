package com.hello.eshop.interceptor;

import com.hello.eshop.bean.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前台登录拦截器
 * @author Hello
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	/** 
     * 在DispatcherServlet之前执行 
     * 在业务处理器处理请求之前被调用
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	        
	        User user =  (User)request.getSession().getAttribute("user");   
	        if(user == null){  
	        	// Interceptor：跳转到login页面！
	            response.sendRedirect(request.getContextPath()+"/user/login.html");  
	            return false;  
	        }else{
	        	return true;    
	        }
	}
	
	 /** 
     * 在controller执行之后的DispatcherServlet之后执行 
     * 在业务处理器处理请求执行完成后,生成视图之前执行
     */ 
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}
	
	/** 
     * 在页面渲染完成返回给客户端之前执行 
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等 
     */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
