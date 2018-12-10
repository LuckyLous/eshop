package com.hello.eshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.*;
import com.hello.eshop.bean.CategoryExample.Criteria;
import com.hello.eshop.constant.Constant;
import com.hello.eshop.dao.CategoryMapper;
import com.hello.eshop.dao.ProductMapper;
import com.hello.eshop.exception.MessageException;
import com.hello.eshop.service.CategoryService;
import com.hello.eshop.utils.JedisClient;
import com.hello.eshop.utils.JsonUtils;
import com.hello.eshop.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${redis.SHOP_CATEGORY_LIST}")
	private String SHOP_CATEGORY_LIST;

	// =======后台操作==========
	/**
	 * 后台查询所有一级分类，包括二级分类(mysql)
	 */
	@Override
	public List<Category> findAll() {
		CategoryExample example = new CategoryExample();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		criteria.andParentIdEqualTo(Constant.CATEGORY_BOOK_PID);
		categoryMapper.selectByExample(example);
		List<Category> list = categoryMapper.selectByExample(example);
		
		// 遍历list
		for (Category category : list) {
			// 根据一级分类的id查询子类列表
			CategoryExample exampleNew = new CategoryExample();
			Criteria criteriaNew = exampleNew.createCriteria();
			// 设置查询条件
			criteriaNew.andParentIdEqualTo(category.getId());
			List<Category> cList = categoryMapper.selectByExample(exampleNew);
			// 补全一级分类下的二级分类
			category.setcList(cList);
		}
		return list;
		
	}

	/**
	 * 根据id查询分类信息
	 */
	@Override
	public Category getById(Integer id) {
		return categoryMapper.selectByPrimaryKey(id);
	}

	/**
	 * 保存图书一级分类
	 */
	@Override
	public void save(Category category) {
		// 将其parent_id设为图书总类
		category.setParentId(Constant.CATEGORY_BOOK_PID);
		// 设置为父节点
		category.setIsParent(Constant.CATEGORY_IS_PARENT);
		categoryMapper.insertSelective(category);
	}

	/**
	 * 更新一级分类
	 */
	@Override
	public void update(Category category) {
		categoryMapper.updateByPrimaryKeySelective(category);
	}

	/**
	 * 删除一级分类
	 */
	@Override
	public void delete(Integer id) {
		// 先查询到某个一级分类下的所有二级分类
		CategoryExample example = new CategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		List<Category> list = categoryMapper.selectByExample(example);
		// 如果没有子类，才将一级分类删除
		if(ListUtils.isNotEmpty(list)){
			throw new MessageException("该分类还有子类，不可以删除~");
		}else{
			categoryMapper.deleteByPrimaryKey(id);
		}
	}

	/**
	 * 分页查询二级分类信息，每页10条
	 */
	@Override
	public PageInfo<Category> list(Integer pid, Integer page, Integer pageSize) {
		// 在查询之前设置页码，以及每页的大小
		PageHelper.startPage(page, pageSize);

		CategoryExample example = new CategoryExample();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		if(pid != null){
			criteria.andParentIdEqualTo(pid); // 如果选择一级分类，则加上
		}
		criteria.andIsParentEqualTo(Constant.CATEGORY_IS_LEAF);// 选择子节点
		List<Category> list = categoryMapper.selectByExample(example);

		PageInfo<Category> pageInfo = new PageInfo<Category>(list,5);// 封装了详细的分页信息，包括有我们查询出来的数据，传入连续显示的页数
		return pageInfo;
	}

	/**
	 * 保存图书的二级分类
	 */
	@Override
	public void saveSmall(Category category) {
		categoryMapper.insertSelective(category);
	}

	/**
	 * 修改图书的二级分类
	 */
	@Override
	public void updateSmall(Category category) {
		categoryMapper.updateByPrimaryKeySelective(category);
	}

	/**
	 * 删除二级分类,如果有商品使用该分类，会抛出MessageException
	 */
	@Override
	public void deleteSmall(Integer id) {
		// 如果商品中仍然有该分类ID，就抛出异常
		ProductExample example = new ProductExample();
		ProductExample.Criteria criteria = example.createCriteria();
		criteria.andCateIdEqualTo(id);
		List<Product> list = productMapper.selectByExample(example);

		// 如果查询商品列表中没有该分类，才可以删除分类
		if(ListUtils.isNotEmpty(list)){
			throw new MessageException("该分类还有对应的商品，不可以删除~");
		}else{
			categoryMapper.deleteByPrimaryKey(id);
		}
	}

	// =======前台操作==========
	
	/**
	 * 从redis中查询图书的所有一级分类信息
	 */
	@Override
	public List<Category> findAllFromRedis() {
		//1.从redis中获取数据
		String value = jedisClient.get(SHOP_CATEGORY_LIST);
		//2.判断数据为空
		if(value == null){
			//2.1若为空，调用findAll() 将查询的结果放入redis return 
			List<Category> list = findAll();
			jedisClient.set(SHOP_CATEGORY_LIST, JsonUtils.objectToJson(list));
			return list;
		}else{
			//2.2若不为空，return 
			//若没有判断value是否为空就进行转换，第一次就会有NullPointer
			List<Category> list = JsonUtils.jsonToList(value, Category.class);
			return list;
		}
		
	}

	/**
	 * 根据cid查询分类,及其父节点
	 * @param id
	 * @return
	 */
	@Override
	public Category getByCid(Integer id) {
		Category category = categoryMapper.selectByPrimaryKey(id);
		Category pCate = categoryMapper.selectByPrimaryKey(category.getParentId());
		category.setpCate(pCate);
		return category;
	}

	/**
	 * 根据分类id查询直接子类(非递归)
	 * @param cid
	 * @return
	 */
	@Override
	public List<Category> listByCid(Integer cid) {
		CategoryExample example = new CategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(cid);
		return categoryMapper.selectByExample(example);
	}

	/**
	 * 根据名称查询分类
	 */
	@Override
	public Category checkName(String name) {
		CategoryExample example = new CategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(name);
		List<Category> list = categoryMapper.selectByExample(example);
		return ListUtils.listToBean(list);
	}

}
