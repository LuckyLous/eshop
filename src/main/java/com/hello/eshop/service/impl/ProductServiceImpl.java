package com.hello.eshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.*;
import com.hello.eshop.bean.ProductExample.Criteria;
import com.hello.eshop.constant.Constant;
import com.hello.eshop.dao.*;
import com.hello.eshop.exception.MessageException;
import com.hello.eshop.service.ProductService;
import com.hello.eshop.utils.IDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private BookMapper bookMapper;

	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderMapper orderMapper;


	// =======后台操作==========
	/**
	 * 按条件查询商品
	 * 分页查,每页5条
	 */
	@Override
	public PageInfo<Product> list(SearchProduct search, Integer page, Integer pageSize) {
		// 设置分页起始页，每页数
		PageHelper.startPage(page, pageSize);
		ProductExample example = new ProductExample();
		Criteria criteria = example.createCriteria();
		// 判断查询条件是否为空
		if(search != null){
			// 设置查询条件
			if(search.getStatus()!=null){
				criteria.andStatusEqualTo(search.getStatus());
			}
			if(search.getBigCateId()!=null){
				criteria.andBigCateIdEqualTo(search.getBigCateId());
			}
			if(search.getCateId()!=null){
				criteria.andCateIdEqualTo(search.getCateId());
			}
			// 一定要%%号，其次去掉空格
			if(StringUtils.isNotBlank(search.getProdName())){
				criteria.andProdNameLike("%"+search.getProdName().trim()+"%");
			}
		}
		// 先查询所有商品信息，返回list集合
		List<Product> list = productMapper.selectByExample(example);

		// 不需要遍历book属性，查询详情才做
		for (Product product : list) {
			Category bigCate = categoryMapper.selectByPrimaryKey(product.getBigCateId());// 一级分类
			product.setBigCate(bigCate);
			Category cate = categoryMapper.selectByPrimaryKey(product.getCateId());//二级分类
			product.setCate(cate);
		}
		// list经过PageInfo加工
		PageInfo<Product> pageInfo = new PageInfo<Product>(list,5);
		return pageInfo;
	}

	/**
	 * 保存商品和图书信息
	 */
	@Override
	public void save(Product product) {
		//生成商品id
		String itemId = IDUtils.genItemId();
		//拿到图书表对应的bean对象
		Book book = product.getBook();
		book.setId(itemId);
		//补全属性(id、discount、add_time)
		product.setId(itemId);
		product.setDiscount(product.getShopPrice() / book.getMarketPrice() * 10);
		product.setAddTime(new Date());
		// 往商品表保存记录
		productMapper.insertSelective(product);
		// 向图书表插入数据
		bookMapper.insertSelective(book);
	}

	/**
	 * 根据id查询商品信息(包括分类、图书信息)
	 */
	@Override
	public Product getById(String id) {
		// 根据id查询商品
		Product product = productMapper.selectByPrimaryKey(id);
		// 补全图书属性
		Book book = bookMapper.selectByPrimaryKey(id);
		product.setBook(book);
		// 补全分类属性
		Category cate = categoryMapper.selectByPrimaryKey(product.getCateId());//分类
		product.setCate(cate);
		Category pCate = categoryMapper.selectByPrimaryKey(product.getBigCateId());//父类
		product.setBigCate(pCate);
		// 其余写法，现在改有风险
//		Category pCate = categoryMapper.selectByPrimaryKey(cate.getParentId());// 父类
//		cate.setpCate(pCate);
//		product.setCate(cate);

		return product;
	}

	@Override
	public void update(Product product) {
		Book book = product.getBook();
		// 重新计算折扣
		product.setDiscount(product.getShopPrice() / book.getMarketPrice() * 10);
		// 更新商品表
		productMapper.updateByPrimaryKeySelective(product);
		// 同时更新图书表
		bookMapper.updateByPrimaryKeySelective(book);
	}

	/**
	 * 根据id删除商品,订单未结束[未付款、已付款、未发货、已发货]的商品不能删
	 * 订单已结束可以删
	 * @param id
	 */
	@Override
	public void delete(String id) {
		OrderItemExample example = new OrderItemExample();
		OrderItemExample.Criteria criteria = example.createCriteria();
		// 设置查询条件
		criteria.andProdIdEqualTo(id);
		List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
		for (OrderItem orderItem : orderItems) {
			Order order = orderMapper.selectByPrimaryKey(orderItem.getOrderId());
			if(!Order.isCompleted(order)){
				throw new MessageException("这个商品有未完成的订单，请处理完订单再删除!");
			}
		}
		// 如果没有异常，同时删去两个表的记录
		productMapper.deleteByPrimaryKey(id);
		bookMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	@Override
	public void deleteBatch(List<String> ids) {
		/*ProductExample example =  new ProductExample();
		Criteria criteria = example.createCriteria();
		// delete from xxx where emp_id in(1,2,3);
		criteria.andIdIn(ids);
		// 这里同时删去两个表的记录
		productMapper.deleteByExample(example);*/

		for (String id:ids) {
			try {
				this.delete(id);
			}catch (MessageException e){
				throw new MessageException("序号["+id+"]的商品有未完成的订单，请处理完再删除!");
			}
		}


	}

	/**
	 * 根据id下架商品
	 * @param id
	 */
	@Override
	public void down(String id) {
		/*OrderItemExample example = new OrderItemExample();
		OrderItemExample.Criteria criteria = example.createCriteria();
		// 判断商品是否有未处理的订单
		criteria.andProdIdEqualTo(id);
		List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
		for (OrderItem orderItem : orderItems) {
			Order order = orderMapper.selectByPrimaryKey(orderItem.getOrderId());
			if(!Order.isCompleted(order)){
				throw new MessageException("这个商品有未完成的订单，请处理完订单再下架!");
			}
		}*/
		// 如果没有异常，更改商品状态
		Product product = productMapper.selectByPrimaryKey(id);
		product.setStatus(Constant.PRODUCT_IS_DOWN);
		productMapper.updateByPrimaryKeySelective(product);
	}

	/**
	 * 根据id上架商品
	 * @param id
	 */
	@Override
	public void up(String id) {
		Product product = productMapper.selectByPrimaryKey(id);
		// 更新状态和上架时间
		product.setStatus(Constant.PRODUCT_IS_UP);
		product.setAddTime(new Date());
		productMapper.updateByPrimaryKeySelective(product);
	}

	/**
	 * 查询所有商品数据，提供给excel
	 * @return
	 */
	@Override
	public List<Product> list() {
		List<Product> list = productMapper.selectByExample(null);
		for (Product prod:list) {
			prod.setBook(bookMapper.selectByPrimaryKey(prod.getId()));
			prod.setBigCate(categoryMapper.selectByPrimaryKey(prod.getBigCateId()));
			prod.setCate(categoryMapper.selectByPrimaryKey(prod.getCateId()));
		}
		return list;
	}


	// =======前台操作==========
	
	/**
	 * 前台查询热门商品,显示9条
	 */
	@Override
	public List<Product> findHot() {
		// 设置分页
		PageHelper.startPage(Constant.DEAFULT_START_PAGE,Constant.PRODUCT_HOT_NEW_PAGE);
		ProductExample example = new ProductExample();
		// 设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(Constant.PRODUCT_IS_UP);
		criteria.andIsHotEqualTo(Constant.PRODUCT_IS_HOT);
		// 设置排序条件
		example.setOrderByClause("add_time DESC");
		return productMapper.selectByExample(example);
	}
	
	/**
	 * 前台查询最新商品，显示9条
	 */
	@Override
	public List<Product> findNew() {
		// 设置分页
		PageHelper.startPage(Constant.DEAFULT_START_PAGE,Constant.PRODUCT_HOT_NEW_PAGE);
		ProductExample example = new ProductExample();
		// 设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(Constant.PRODUCT_IS_UP);
		// 设置排序条件
		example.setOrderByClause("add_time DESC");
		return productMapper.selectByExample(example);
	}
	

	/**
	 * 前台分页查询分类商品,如果cid为空，就查询为空
	 */
	@Override
	public PageInfo<Product> find(Integer page,Integer pageSize,Integer bigCid, Integer cid) {
		PageHelper.startPage(page,pageSize);
		// 先查询上架商品中符合分类，再按照上架时间逆序，得到list集合
		ProductExample example = new ProductExample();
		Criteria criteria = example.createCriteria();
		if(bigCid != null ){	// 一级分类不为空
			criteria.andBigCateIdEqualTo(bigCid);
		}
		if(cid != null ){	// 二级分类不为空
			criteria.andCateIdEqualTo(cid);
		}
		// 设置查询条件
		criteria.andStatusEqualTo(Constant.PRODUCT_IS_UP);
		example.setOrderByClause("add_time DESC");
		List<Product> list = productMapper.selectByExample(example);

		PageInfo<Product> pageInfo = new PageInfo<Product>(list,5);
		return pageInfo;
	}

}
