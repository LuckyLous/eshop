package com.hello.eshop.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 * @author Hello
 *
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver  {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class); 

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		/* 发生异常的地方 Serivce层  方法  包名+类名+方法名（形参） 字符串
			写日志    
			1.发布 tomcat war  Eclipse 
			2.发布Tomcat  服务器上  Linux  Log4j
		*/
		// 打印到控制台
		//ex.printStackTrace();
		//写日志
		logger.debug("测试输出的日志。。。。。。。");
		logger.info("系统发生异常了。。。。。。。");
		logger.error("系统发生异常", ex);
		
		//显示错误页面
		ModelAndView mav = new ModelAndView();
		//判断异常为类型
		if(ex instanceof MessageException){
			// 自定义异常,转发到fail页面显示
			MessageException me = (MessageException)ex;
			mav.addObject("fail", me.getMessage());
			mav.setViewName("error/fail");
		}else{
			// 未知异常，转发到error页面显示
			mav.setViewName("error/error");
		}
		return mav;
	}

}
