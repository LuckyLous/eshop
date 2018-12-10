package com.hello.eshop.interceptor;

import com.hello.eshop.bean.User;
import com.hello.eshop.service.UserService;
import com.hello.eshop.utils.CookieUtils;
import com.hello.eshop.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 自动登录拦截器，访问任何资源都会拦截
 * @author Hello
 *
 */
public class AutoLoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserService userService;
	
	@Override
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 查询Session中是否存在user对象
		HttpSession session = request.getSession();
		// 如果有，则直接放行，否则，解析Cookie
		if(session.getAttribute("user") != null){
			return true;
		}else{
			String cookieValue = CookieUtils.getCookieValue(request, "autoLogin", true);
			if(StringUtils.isNotBlank(cookieValue)){ // 如果这个Cookie存在
				// 调用service,看cookie是否与最新的密码一致,若一致则存到session,并且放行
				User userC = JsonUtils.jsonToPojo(cookieValue, User.class);
				User user_exist = userService.login(userC.getUsername(),userC.getPassword());
				if(user_exist != null){
					request.getSession().setAttribute("user", user_exist);
				}
			}
			// 无论是否能自动登录，这里都放行，其它拦截器会根据其访问的URL进行精准拦截
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
