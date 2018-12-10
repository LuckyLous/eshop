package com.hello.eshop.controller;

import com.hello.eshop.bean.Admin;
import com.hello.eshop.service.AdminService;
import com.hello.eshop.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 管理员控制器
 * @author Hello
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	/**
	 * 更新登录密码
	 */
	@RequestMapping("/update/pwd")
	public String updatePwd(String username, String oldPwd, String newPwd,
							HttpServletRequest request) throws IOException {
		// 如果旧密码不为空，而且输入的正确
		Admin admin_exist = adminService.login(username, oldPwd);
		if(admin_exist != null){
			// 新密码不为空则加密
			admin_exist.setPassword(MD5Utils.md5(newPwd));
			adminService.update(admin_exist);
			// 添加成功信息
			request.setAttribute("msg","修改密码成功!");
			// 注销登录状态
			request.setAttribute("newUrl","logout");
		}else{
			request.setAttribute("msg","修改密码失败~");
		}
		return "forward:/admin/common/msg.jsp";
	}
	
	/**
	 * 用户退出
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/admin/system/login.jsp";
	}
	
	/**
	 * 后台登录
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Admin admin,HttpSession session,Model model){
		Admin admin_exist = adminService.login(admin.getUsername(), admin.getPassword());
		if(admin_exist == null){
			model.addAttribute("info", "您的用户名或者密码错误！");
			return "forward:/admin/system/login.jsp";
		}else{
			session.setAttribute("admin", admin_exist); 
			return "redirect:/admin/index.jsp";
		}
		
	}
	
}
