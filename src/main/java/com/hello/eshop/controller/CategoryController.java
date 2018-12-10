package com.hello.eshop.controller;

import com.hello.eshop.bean.Category;
import com.hello.eshop.service.CategoryService;
import com.hello.eshop.utils.ListUtils;
import com.hello.eshop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 前台分类Controller
 * @author Hello
 *
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;


	/**
	 * ajax根据分类id查询对应子类
	 * 商品按分类查询会调用
	 * */
	@RequestMapping("/list/{cid}")
	@ResponseBody
	public Result listByCid(@PathVariable("cid")Integer cid){
		// 拿到相应的子类
		List<Category> list = categoryService.listByCid(cid);
		if(ListUtils.isNotEmpty(list)){
			return  Result.success().add("cList", list);
		}else{
			return Result.fail();
		}
	}
	
	/**
	 * ajax查询分类列表(包含一级和二级)
	 * 不推荐
	 */
	@RequestMapping("/findAll")
	@ResponseBody
	public Result findAll(){
		// 1.调用service,查询所有分类，返回值 list
		// 从mysql中获取列表
		List<Category> list = categoryService.findAll();
		// 从redis中获取列表(缓存，数据不经常更新用)
//		List<Category> list = categoryService.findAllFromRedis();
		if(null != list){
			// 2.将字符串写回浏览器，如果直接放list，那边不需要eval转换，如果直接是字符串，则不需要
			return Result.success().add("list", list);
		}else{
			return Result.fail();
		}
	}
}
