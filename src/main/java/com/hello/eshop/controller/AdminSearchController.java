package com.hello.eshop.controller;

import com.hello.eshop.service.SearchService;
import com.hello.eshop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台索引库Controller
 */
@Controller
@RequestMapping("/admin/search")
public class AdminSearchController {
	
	@Autowired
	private SearchService searchService;

	/**
	 * 导入商品数据到索引库
	 * @return
	 */
	@RequestMapping("/import")
	@ResponseBody
	public Result importItemList() {
		Result result = searchService.importAllItems();
		return result;
	}
}
