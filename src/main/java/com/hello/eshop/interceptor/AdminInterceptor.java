package com.hello.eshop.interceptor;

import com.hello.eshop.bean.Admin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台权限拦截器
 * @author Hello	
 *
 */
public class AdminInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Admin admin =  (Admin)request.getSession().getAttribute("admin");   
        if(admin == null){  
        	// 重定向到login页面！
        	response.sendRedirect(request.getContextPath() + "/admin/system/login.jsp");
            return false;  
        }else{
        	return true;  
        }
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
