package com.hello.eshop.service;

import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.User;
import com.hello.eshop.exception.MessageException;

public interface UserService {
	/**
	 * 用户注册
	 */
	public void regist(User user) throws MessageException;
	/**
	 * 用户激活
	 */
	public User active(String activeCode);
	/**
	 * 用户登录
	 */
	public User login(String username,String password);
	/**
	 * 校验用户名是否存在
	 * @param username
	 * @return
	 */
	public User checkUsername(String username);
	/**
	 * 校验邮箱是否存在
	 * @param email
	 * @return
	 */
	public User checkEmail(String email);

	/**
	 * 更新用户信息
	 * @param user
	 */
    void update(User user);

	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 */
	User getById(Integer id);

	/**
	 * 根据状态查询会员列表
	 * @param status
	 */
    PageInfo<User> list(Integer status, Integer pageNum, Integer pageSize);

	/**
	 * 根据id删除会员
	 * @param id
	 */
	void delete(Integer id);
}
