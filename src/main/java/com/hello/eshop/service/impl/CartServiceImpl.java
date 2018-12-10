package com.hello.eshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hello.eshop.bean.Product;
import com.hello.eshop.dao.ProductMapper;
import com.hello.eshop.service.CartService;
import com.hello.eshop.utils.JedisClient;
import com.hello.eshop.utils.JsonUtils;
import com.hello.eshop.utils.Result;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${redis.REDIS_CART_PRE}")
	private String REDIS_CART_PRE;// 当前购物车功能的前缀
	
	@Autowired
	private ProductMapper productMapper;
	
	/**
	 * 在redis新增购物车的cartitem
	 */
	@Override
	public Result addCart(Integer userId, String prodId, Integer num){
		//向redis中添加购物车。
		//数据类型是hash key：用户id field：商品id value：商品信息
		//判断商品是否存在
		Boolean hexists = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, prodId + "");
		//如果存在数量相加
		if (hexists) {
			String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, prodId + "");
			//把json转换成Product
			Product prod = JsonUtils.jsonToPojo(json, Product.class);
			prod.setNum(prod.getNum() + num);
			//写回redis
			jedisClient.hset(REDIS_CART_PRE + ":" + userId, prodId + "", JsonUtils.objectToJson(prod));
			return Result.success();
		}
		//如果不存在，根据商品id取商品信息
		Product prod = productMapper.selectByPrimaryKey(prodId);
		//设置购物车数量
		prod.setNum(num);
		//添加到购物车列表
		jedisClient.hset(REDIS_CART_PRE + ":" + userId, prodId + "", JsonUtils.objectToJson(prod));
		return Result.success();
	}
	
	/**
	 * 合并Cookie和Redis的购物车列表
	 */
	@Override
	public Result mergeCart(Integer userId, List<Product> prodList) {
		//遍历商品列表
		//把列表添加到购物车。
		//判断购物车中是否有此商品
		//如果有，数量相加
		//如果没有添加新的商品
		for (Product product : prodList) {
			addCart(userId, product.getId(), product.getNum());
		}
		//返回成功
		return Result.success();
	}
	
	/**
	 * 从redis取到购物车列表
	 */
	@Override
	public List<Product> getCartList(Integer userId) {
		//根据用户id查询购车列表
		List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
		List<Product> prodList = new ArrayList<>();
		for (String string : jsonList) {
			//创建一个Product对象
			Product prod = JsonUtils.jsonToPojo(string, Product.class);
			//添加到列表
			prodList.add(prod);
		}
		return prodList;
	}
	
	/**
	 * 在redis更新购物车的cartitem
	 */
	@Override
	public Result updateCartNum(Integer userId, String prodId, Integer num) {
		//从redis中取商品信息
		String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, prodId + "");
		//更新商品数量
		Product product = JsonUtils.jsonToPojo(json, Product.class);
		product.setNum(num);
		//写入redis
		jedisClient.hset(REDIS_CART_PRE + ":" + userId, prodId + "", JsonUtils.objectToJson(product));
		return Result.success();
	}
	
	/**
	 * 在redis删除购物车的cartitem
	 */
	@Override
	public Result deleteCartItem(Integer userId, String prodId) {
		// 删除购物车商品
		jedisClient.hdel(REDIS_CART_PRE + ":" + userId, prodId + "");
		return Result.success();
	}
	
	/**
	 * 在redis清空购物车
	 */
	@Override
	public Result clearCartItem(Integer userId){
		//删除购物车信息
		jedisClient.del(REDIS_CART_PRE + ":" + userId);
		return Result.success();
	}
}
