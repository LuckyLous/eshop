package com.hello.eshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.*;
import com.hello.eshop.bean.OrderExample.Criteria;
import com.hello.eshop.constant.Constant;
import com.hello.eshop.dao.OrderItemMapper;
import com.hello.eshop.dao.OrderMapper;
import com.hello.eshop.dao.ProductMapper;
import com.hello.eshop.dao.UserMapper;
import com.hello.eshop.service.OrderService;
import com.hello.eshop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 保存订单和订单项
	 */
	@Override
	public Result save(Order order) {
		order.setUpdateTime(new Date());// 设置更新时间
		// 向订单表插入记录
		orderMapper.insertSelective(order);
		
		List<OrderItem> list = order.getItems();
		// 向订单项表插入记录
		for (OrderItem orderItem : list) {
			orderItemMapper.insertSelective(orderItem);
		}
		
		return Result.success();
	}
	
	/**
	 * 根据用户id分页查询用户的订单
	 */
	@Override
	public PageInfo<Order> find(Integer status, Integer userId, Integer page, Integer pageSize) {
		
		PageHelper.startPage(page, pageSize);
		// 设置查询tb_order条件
		OrderExample example = new OrderExample();
		Criteria criteria = example.createCriteria();
		// 如果状态不为空，则增加查询条件
		if(status != null){
			criteria.andStatusEqualTo(status);
		}
		// 按照用户id查询
		criteria.andUserIdEqualTo(userId);
		// 按最新处理的订单排序
		example.setOrderByClause("update_time DESC");
		List<Order> list = orderMapper.selectByExample(example);

		// 遍历Order集合
		for (Order order : list) {
			String orderId = order.getId();
			// 设置查询tb_orderitem的查询条件
			OrderItemExample nextExample = new OrderItemExample();
			OrderItemExample.Criteria nextCriteria = nextExample.createCriteria();
			nextCriteria.andOrderIdEqualTo(orderId);
			List<OrderItem> items = orderItemMapper.selectByExample(nextExample);
			// 设置每个order的items
			order.setItems(items);
		}
		
		PageInfo<Order> pageInfo = new PageInfo<Order>(list,5);
		return pageInfo;
	}
	
	/**
	 * 根据id查询订单，将订单项一并封装
	 */
	/**
	 * 根据id查询订单及其订单项
	 * @param id
	 * @return
	 */
	@Override
	public Order getById(String id) {
		// 根据id查询订单
		Order order = orderMapper.selectByPrimaryKey(id);
		// 查询订单对应的订单项
		OrderItemExample example = new OrderItemExample();
		OrderItemExample.Criteria criteria = example.createCriteria();
		criteria.andOrderIdEqualTo(id);
		List<OrderItem> items = orderItemMapper.selectByExample(example);
		// 设置订单项集合
		order.setItems(items);
		return order;
	}
	
	/**
	 * 更新收货人信息
	 */
	@Override
	public void updateAddr(Order order) {
		order.setUpdateTime(new Date());// 更新时间
		orderMapper.updateByPrimaryKeySelective(order);
		
	}
	
	/**
	 * 支付成功后修改订单状态、商品的库存数量
	 */
	@Override
	public Result update(Order order) {
		// 设置订单状态为已付款
		order.setStatus(Constant.ORDER_IS_PAY);//更新状态
		order.setUpdateTime(new Date());// 更新时间
		orderMapper.updateByPrimaryKeySelective(order);
		
		// 根据订单项表，更新商品表的库存记录
		List<OrderItem> items = order.getItems();
		for (OrderItem orderItem : items) {
			Product product = productMapper.selectByPrimaryKey(orderItem.getProdId());
			product.setNum(product.getNum() - orderItem.getQuantity());// 减少库存量
			productMapper.updateByPrimaryKeySelective(product);
		}
		return Result.success();
	}
	
	/**
	 * 确认收货，将订单状态改为成功
	 */
	@Override
	public void receive(String orderId) {
		Order order = orderMapper.selectByPrimaryKey(orderId);
		order.setStatus(Constant.ORDER_IS_FINISH);//更新状态
		order.setUpdateTime(new Date());// 更新时间
		orderMapper.updateByPrimaryKeySelective(order);
	}
	
	/**
	 * 取消订单，将订单状态改为结束
	 * @param id
	 */
	@Override
	public void cancel(String id) {
		Order order = orderMapper.selectByPrimaryKey(id);
		order.setStatus(Constant.ORDER_IS_CLOSE);//更新状态
		order.setUpdateTime(new Date());// 更新时间
		orderMapper.updateByPrimaryKeySelective(order);
		
	}

	// ======后台操作



	/**
	 * 根据订单状态分页查询订单(不查询订单项)，每页5条
	 */
	@Override
	public PageInfo<Order> list(Integer status, Integer pageNum, Integer pageSize) {

		PageHelper.startPage(pageNum,pageSize);
		OrderExample example = new OrderExample();
		Criteria criteria = example.createCriteria();
		// 如果状态不为空，则增加查询条件
		if(status != null){
			criteria.andStatusEqualTo(status);
		}
		// 按最新生成订单时间排序
		example.setOrderByClause("order_time DESC");
		List<Order> list = orderMapper.selectByExample(example);

		for (Order order : list) {
			User user = userMapper.selectByPrimaryKey(order.getUserId());
			order.setUser(user);
		}
		PageInfo<Order> pageInfo = new PageInfo<Order>(list);
		return pageInfo;
	}

	/**
	 * 更新订单状态
	 */
	@Override
	public void ship(String id) {
		Order order = orderMapper.selectByPrimaryKey(id);
		order.setStatus(Constant.ORDER_IS_SHIP);
		order.setUpdateTime(new Date());

		orderMapper.updateByPrimaryKeySelective(order);
	}
	
}
