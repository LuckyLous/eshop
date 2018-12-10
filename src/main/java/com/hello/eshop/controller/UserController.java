package com.hello.eshop.controller;

import com.hello.eshop.bean.User;
import com.hello.eshop.constant.Constant;
import com.hello.eshop.exception.MessageException;
import com.hello.eshop.service.UserService;
import com.hello.eshop.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * 前台用户控制器
 * @author Hello
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// 自动登录的保存时间
	private Integer AUTO_LOGIN_TIME = Integer.parseInt(
			ResourceBundle.getBundle("conf/cookie").getString("cookie.AUTO_LOGIN_TIME"));

	/**
	 * 更新登录密码
	 */
	@RequestMapping("/update/pwd")
	public String updatePwd(String username,String oldPwd,String password,
					HttpServletRequest request) throws IOException {
		// 验证旧密码的正确性
		User user_exist = userService.login(username,oldPwd);
		// 如果旧密码不为空，而且输入的正确
		if(user_exist!= null){
			user_exist.setPassword(MD5Utils.md5(password));
			userService.update(user_exist);
			// 添加成功信息
			request.setAttribute("msg","修改密码成功!");
			// 注销登录状态
			request.setAttribute("newUrl","user/logout");
		}else{
			request.setAttribute("msg","修改密码失败~");
		}
		return "common/msg";
	}

	/**
	 * 更新用户信息
	 */
	@RequestMapping("/update")
	public String update(User user,HttpServletRequest request){
		// 调用service更新用户
		userService.update(user);
		// 更新session域中的user
		User new_user = userService.getById(user.getId());

		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.setAttribute("user",new_user);

		request.setAttribute("msg","更新个人信息成功！");
		request.setAttribute("newUrl","user/info");
		return "common/msg";
	}

	/**
	 * 跳转到修改密码页面
	 */
	@RequestMapping("/updatePwd")
	public String changePwdUI(){
		return "user/updatePwd";
	}

	/**
	 * 跳转到个人信息页面
	 */
	@RequestMapping("/info")
	public String infoUI(){
		return "user/info";
	}


	/**
	 * 用户退出
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		// 将session销毁
		request.getSession().invalidate();
		
		// 将存储客户端的cookie删掉，不然重定向首页后，自动登录拦截器就会拦截，获取cookie，重复登录
		CookieUtils.deleteCookie(request, response, "autoLogin");
		// 重定向到首页
		request.setAttribute("msg","你已经成功注销！");
		return "common/msg";
	}
	
	/**
	 * 用户登录(自动登录，记住用户名为辅助)
	 * @return
	 */
	@RequestMapping("/login")
	public String login(User user,String verifyCode,HttpServletRequest request,HttpServletResponse response,
			HttpSession session,String saveName,String autoLogin){
		
		// 校验验证码
		if("FALSE".equals(checkVerify(verifyCode, session))){
			request.setAttribute("msg", "校验码不正确");
			return "user/login";
		}
		// 调用service完成登录
		User user_exist = userService.login(user.getUsername(),user.getPassword());
		
		// 判断user 根据结果生成提示
		if(user_exist == null){
			request.setAttribute("msg", "邮箱或用户名和密码不匹配");
			request.setAttribute("username", user.getUsername());
			return "user/login";
		}
		
		// 若用户不为空，继续判断是否激活
		if(Constant.USER_IS_ACTIVE != user_exist.getStatus()){
			// 未激活
			request.setAttribute("msg", "请先去邮箱激活，再登录!");
			return "common/msg";
		}
		
		// ======记住用户名=======
		saveName(user, request, response, saveName);
		// ======自动登录=========
		autoLogin(user, request, response, autoLogin);

		// 登录成功，保存用户登录状态,重定向到index.jsp
		session.setAttribute("user", user_exist);
		return "redirect:/";
		
	}

	private void autoLogin(User user, HttpServletRequest request, HttpServletResponse response, String autoLogin) {
		if(Constant.AUTO_LOGIN.equals(autoLogin)){
			// 设置一个Cookie，保存用户登录信息在客户端
			User userInfo = new User();
			userInfo.setUsername(user.getUsername());
			userInfo.setPassword(user.getPassword());// 保存非加密过

			CookieUtils.setCookie(request, response, "autoLogin", JsonUtils.objectToJson(userInfo), AUTO_LOGIN_TIME , true);
		}else{
			CookieUtils.deleteCookie(request, response, "autoLogin");
		}
	}

	private void saveName(User user, HttpServletRequest request, HttpServletResponse response, String saveName) {
		if(Constant.SAVE_NAME.equals(saveName)){
			CookieUtils.setCookie(request, response, "saveName", user.getUsername(), Integer.MAX_VALUE, true);
		}else{
			CookieUtils.deleteCookie(request, response, "saveName");
		}
	}

	/**
     * 登录页面校验验证码
     */
    @RequestMapping(value = "checkVerify")
    @ResponseBody
    public Boolean checkVerify(String verifyCode, HttpSession session){
        //从session中获取随机数
        String random = (String) session.getAttribute("RANDOM_VALIDATE_CODE_KEY");
        if(random.equalsIgnoreCase(verifyCode)){ // 忽略大小写
            return true;//验证码正确
        }else{
            return false;//验证码错误
        }
    } 
	
	/**
     * 登录页面生成验证码
     */
    @RequestMapping(value = "getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片  
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容  
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expire", 0); 
        try { 
        	ValidateCodeUtil.getRandcode(request, response);//输出验证码图片方法  
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
	
	/**
	 * 跳转到登录页面
	 * @return
	 */
	@RequestMapping("/login.html")
	public String loginUI(){
		return "user/login";
	}

	
	/**
	 * 用户激活
	 * @param activeCode
	 * @return
	 */
	@RequestMapping("/active")
	public String active(@RequestParam("activeCode") String activeCode,Model model){
		// 调用service完成激活
		User user = userService.active(activeCode);
		// 判断user 生成不同的提示信息
		if(user == null){
			// 没有找到这个用户，激活失败
			model.addAttribute("msg", "激活失败，请重新激活或者重新注册~");
		}else{
			// 激活成功
			model.addAttribute("msg", "恭喜你，激活成功了，可以登录了");
		}
		return "common/msg";
	}
	
	@RequestMapping("/checkEmail")
	@ResponseBody
	public Boolean checkEmail(String email){
		User user = userService.checkEmail(email);
		if(user != null){
			return false;//重复了，返回false
		}else{
			return true;//不重复，返回true
		}
	}
	
	@RequestMapping("/checkUsername")
	@ResponseBody
	public Boolean checkUsername(String username){
		User user = userService.checkUsername(username);
		if(user != null){
			return false;//重复了,返回false,返回false代表该校验器不通过
		}else{
			return true;//不重复,返回true
		}
	}

	/**
	 * 用户注册
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("/regist")
	public String regist(User user,Model model,String verifyCode,HttpSession session){
		// 校验验证码
		if("FALSE".equals(checkVerify(verifyCode, session))){
			model.addAttribute("msg", "校验码不正确");
			return "common/msg";
		}
		// 1、处理封装数据
		user.setStatus(Constant.USER_IS_NOT_ACTIVE);// 1.1 激活状态
		user.setActiveCode(IDUtils.getActiveCode());// 1.2激活码
		// 2.调用service完成注册
		try {
			userService.regist(user);
			model.addAttribute("msg", "恭喜你，注册成功，请登录邮箱完成激活！");
		} catch (MessageException e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
		}
		// 3.页面转发提示信息， 转发到msg.jsp
		return "common/msg";
	}
	
	/**
	 * 跳转到注册页面
	 * @return
	 */
	@RequestMapping("/regist.html")
	public String registUI(){
		return "user/register";
	}

}
