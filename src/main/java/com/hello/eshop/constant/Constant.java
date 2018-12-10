package com.hello.eshop.constant;

public interface Constant {
	// 前台常量
	/**
	 * 用户未激活
	 */
	Integer USER_IS_NOT_ACTIVE = 0;
	
	/**
	 * 用户已激活
	 */
	Integer USER_IS_ACTIVE = 1;
	
	/**
	 * 记住用户名
	 */
	String SAVE_NAME = "ok";
	
	/**
	 * 自动登录
	 */
	String AUTO_LOGIN = "ok";
	
	/**
	 * 商品正常上架
	 */
	Integer PRODUCT_IS_UP = 1;
	/**
	 * 商品下架
	 */
	Integer PRODUCT_IS_DOWN = 2;
	/**
	 * 是热门商品
	 */
	Integer PRODUCT_IS_HOT = 1;
	/**
	 * 分页默认起始页
	 */
	Integer DEAFULT_START_PAGE = 1;
	/**
	 * 热门商品和最新商品前台显示条数
	 */
	Integer PRODUCT_HOT_NEW_PAGE = 12;
	/**
	 * 分类商品分页展示的条数
	 */
	Integer PRODUCT_BY_CATEGORY_PAGE = 12;
	/**
	 * 历史记录的条数
	 */
	Integer PRODUCT_HISTORY_RECODE = 7;

	/**
	 * Cookie容纳最大购物项
	 */
	Integer CART_COOKIE_MAX_SPACE = 7;
	
	
	/**
	 * 1、未付款
	 */
	Integer ORDER_IS_NOT_PAY = 1;
	/**
	 * 2、已付款
	 */
	Integer ORDER_IS_PAY = 2;
	/**
	 * 3、未发货
	 */
	Integer ORDER_IS_NOT_SHIP = 3;
	/**
	 * 4、已发货
	 */
	Integer ORDER_IS_SHIP = 4;
	/**
	 * 5、交易成功
	 */
	Integer ORDER_IS_FINISH = 5;
	/**
	 * 6、交易关闭
	 */
	Integer ORDER_IS_CLOSE = 6;
	
	/**
	 * 通用类的成功状态码
	 */
	Integer RESULT_SUCCESS = 100;
	
	/**
	 * 通用类的失败状态码
	 */
	Integer RESULT_FAIL = 200;
	
	/**
	 * 我的订单中：每页显示的订单数
	 */
	Integer ORDER_SHOW_PAGE = 3;
	
	/**
	 * 前台搜索列表，逆序排列
	 */
	String SEARCH_SORT_DESC = "desc";
	
	// ==========后台常量===========
	/**
	 * 所有图书的父节点编号
	 */
	Integer CATEGORY_BOOK_PID = 1;
	/**
	 * 分类为父节点
	 */
	Integer CATEGORY_IS_PARENT = 1;
	/**
	 * 分类为叶子节点
	 */
	Integer CATEGORY_IS_LEAF = 0;
	/**
	 * 二级分类列表每页显示的条数
	 */
	Integer CATEGORY_SHOW_PAGE = 10;

	/**
	 * 商品列表每页显示的条数
	 */
	Integer PRODUCT_SHOW_PAGE = 5;
	/**
	 * 订单列表每页显示的条数
	 */
	Integer ORDER_LIST_PAGE = 5;

	/**
	 * 会员列表每页显示的条数
	 */
	Integer USER_LIST_PAGE = 5;
	
}
