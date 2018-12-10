package com.hello.eshop.service;

import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.Order;
import com.hello.eshop.utils.Result;

public interface OrderService {
	/**
	 * 保存订单和订单项
	 * @param order
	 * @return
	 */
	Result save(Order order);
	
	/**
	 * 根据用户id分页查询用户的订单
	 * @param status
	 */
	PageInfo<Order> find(Integer status, Integer userId, Integer page, Integer pageSize);
	
	/**
	 * 根据id查询订单及其订单项
	 * @param id
	 * @return
	 */
	Order getById(String id);
	
	/**
	 * 更新收货信息
	 * @param order
	 */
	void updateAddr(Order order);
	
	/**
	 * 更新订单的支付状态，成功后返回提示信息
	 * @param order 
	 * @return 
	 */
	Result update(Order order);
	
	/**
	 * 确认收货，将订单状态改为完成
	 * @param orderId
	 */
	void receive(String orderId);
	
	/**
	 * 取消订单，将订单状态改为结束
	 * @param id
	 */
	void cancel(String id);

	// 后台操作
	/**
	 * 根据订单状态查询订单分页
	 */
	PageInfo<Order> list(Integer status, Integer pageNum, Integer pageSize);
	/**
	 * 发货，修改订单状态
	 * @param id
	 */
	void ship(String id);

}
