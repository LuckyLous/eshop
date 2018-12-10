package com.hello.eshop.service.impl;

import java.util.List;

import com.hello.eshop.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hello.eshop.bean.Admin;
import com.hello.eshop.bean.AdminExample;
import com.hello.eshop.bean.AdminExample.Criteria;
import com.hello.eshop.dao.AdminMapper;
import com.hello.eshop.service.AdminService;
import com.hello.eshop.utils.ListUtils;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminMapper adminMapper;
	
	/**
	 * 管理员登录
	 */
	@Override
	public Admin login(String username, String password) {
		AdminExample example = new AdminExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		criteria.andPasswordEqualTo(MD5Utils.md5(password));
		List<Admin> list = adminMapper.selectByExample(example);
		Admin admin = ListUtils.listToBean(list);
		return admin;
	}

	/**
	 * 更新管理员信息
	 * @param admin
	 */
	@Override
	public void update(Admin admin) {
		adminMapper.updateByPrimaryKeySelective(admin);
	}


}
