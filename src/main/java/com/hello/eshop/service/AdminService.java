package com.hello.eshop.service;

import com.hello.eshop.bean.Admin;

public interface AdminService {
	/**
	 * 管理员登录
	 */
	Admin login(String username, String password);

	/**
	 * 更新管理员信息
	 */
	void update(Admin admin);
}
