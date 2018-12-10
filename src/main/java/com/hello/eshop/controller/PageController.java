package com.hello.eshop.controller;

import com.hello.eshop.bean.Product;
import com.hello.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 页面Controller，取代了直接访问Tomcat下的webapp下的静态资源,即index.jsp也通过controller来访问
 * @author Hello
 *
 */
@Controller
public class PageController {
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 转发到前台界面
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model){
		// 1、调用productService查询最新商品和热门商品
		List<Product> hotList = productService.findHot();
		List<Product> newList = productService.findNew();
		
		model.addAttribute("hotList", hotList);
		model.addAttribute("newList", newList);
		return "index";
	}
	
}
