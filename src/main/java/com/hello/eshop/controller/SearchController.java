package com.hello.eshop.controller;


import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.SearchItem;
import com.hello.eshop.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 前台搜索Controller
 * 后台索引库
 * @author Hello
 *
 */
@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	//搜索结果每页显示的记录数
	private Integer SEARCH_RESULT_ROWS = Integer.parseInt(
			ResourceBundle.getBundle("conf/solr").getString("solr.SEARCH_RESULT_ROWS"));
	
	/**
	 * 通过上面四个条件查询对象商品结果集
	 * @param keyword
	 * @param cateName
	 * @param price
	 * @param sort
	 */
	@RequestMapping("/search")
	public String searchItemList(String keyword, String cateName, String price, String sort,
			@RequestParam(value="page",required=true,defaultValue="1")Integer page,
			Model model) throws Exception {
		// GET过滤器解决URL的乱码或tomcat uri配置都能解决
		// 个人项目建议改配置，因为无风险还快
		// 部署项目到别人的服务器就用过滤器

		//查询商品列表
		PageInfo<SearchItem> pageInfo = searchService.search(keyword, cateName, price, sort, page, SEARCH_RESULT_ROWS);
		// 把数据回显给页面
		model.addAttribute("query", keyword);
		model.addAttribute("cateName", cateName);
		model.addAttribute("price", price);
		model.addAttribute("sort", sort);
		//把结果传递给页面
		/*model.addAttribute("page", page);
		model.addAttribute("itemList", pageInfo.getList());
		model.addAttribute("totalPages", pageInfo.getPages());
		model.addAttribute("recourdCount", pageInfo.getTotal());*/

		model.addAttribute("pageInfo",pageInfo);

		// 去除重复的分类名称
		Set<String> cateSet = new HashSet<>();
		for (SearchItem item: pageInfo.getList()) {
			cateSet.add(item.getCateName());
		}
		model.addAttribute("cateSet", cateSet);
		//返回逻辑视图
		return "serach/search_list";
	}
}
