package com.hello.eshop.service;

import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.Product;
import com.hello.eshop.bean.SearchProduct;

import java.util.List;

public interface ProductService {
	// 后台操作
	/**
	 * 按条件查询商品
	 * 分页查,每页5条
	 * @param page
	 * @return
	 */
	PageInfo<Product> list(SearchProduct search, Integer page, Integer pageSize);

	/**
	 * 保存商品和图书信息
	 */
	void save(Product product);

	/**
	 * 根据id查询商品信息(包括分类、图书信息)
	 * @param id
	 * @return
	 */
	Product getById(String id);
	/**
	 * 更新商品和图书信息
	 * @param product
	 */
	void update(Product product);
	/**
	 * 根据id删除单一商品(自己应该写SQL)
	 * @param id
	 */
	void delete(String id);
	/**
	 * 根据id批量删除商品(自己应该写SQL)
	 * @param ids
	 */
	void deleteBatch(List<String> ids);

	/**
	 * 根据id下架商品(自己应该写SQL)
	 * @param id
	 */
	void down(String id);

	/**
	 * 根据id上架商品
	 * @param id
	 */
	void up(String id);

	/**
	 * 查询所有商品数据，提供给excel
	 * @return
	 */
	List<Product> list();

	// 前台操作

	List<Product> findHot();

	List<Product> findNew();

	/**
	 * 前台分页查询分类商品
	 * @param page
	 * @param cid
	 * @return
	 */
	PageInfo<Product> find(Integer page, Integer pageSize,Integer bigCid, Integer cid);

}
