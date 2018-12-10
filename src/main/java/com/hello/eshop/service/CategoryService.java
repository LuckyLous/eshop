package com.hello.eshop.service;

import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.Category;

import java.util.List;

public interface CategoryService {

	// =======后台操作==========
	/**
	 * 查询所有分类，包括子类(mysql)
	 */
	List<Category> findAll();
	/**
	 * 根据id查询分类信息
	 */
	Category getById(Integer id);
	/**
	 * 保存一级分类
	 */
	void save(Category category);
	/**
	 * 根据id更新分类
	 */
	void update(Category category);
	/**
	 * 根据id删除一级分类
	 */
	void delete(Integer id);

	/**
	 * 查询二级分类列表，分页
	 */
	PageInfo<Category> list(Integer pid, Integer page, Integer pageSize);
	/**
	 * 保存图书的二级分类
	 * @param category
	 */
	void saveSmall(Category category);
	/**
	 * 修改图书的二级分类
	 */
	void updateSmall(Category category);
	/**
	 * 根据id删除二级分类
	 * 如果有商品使用该分类，会抛出MessageException
	 */
	void deleteSmall(Integer id);

	// =======前台操作==========
	/**
	 * 根据分类id查询直接子类(非递归)
	 * @param cid
	 * @return
	 */
	List<Category> listByCid(Integer cid);
	/**
	 * 根据id查询二级分类，及其父分类
	 * @param id
	 * @return
	 */
	Category getByCid(Integer id);
	/**
	 * 查询所有一级分类(redis)
	 * @return
	 */
	List<Category> findAllFromRedis();

	/**
	 * 检验分类名称是否相同
	 * @param name
	 * @return
	 */
	Category checkName(String name);
}
