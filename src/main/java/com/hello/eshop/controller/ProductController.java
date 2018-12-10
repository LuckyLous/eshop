package com.hello.eshop.controller;

import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.Category;
import com.hello.eshop.bean.Product;
import com.hello.eshop.constant.Constant;
import com.hello.eshop.service.CategoryService;
import com.hello.eshop.service.ProductService;
import com.hello.eshop.utils.CookieUtils;
import com.hello.eshop.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 前台商品控制器
 * @author Hello
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	/**
	 * 查询浏览记录
	 * =========历史记录的回显==========
	 */
	@RequestMapping("/list/history")
	public String historyList(HttpServletRequest request){
		// 获得客户端携带名字叫history的cookie
		String historyC = CookieUtils.getCookieValue(request, "history", true);
		if(StringUtils.isNotBlank(historyC)){
			// 定义一个历史记录商品信息的集合
			List<Product> list = new ArrayList<Product>();
			String[] ids = historyC.split("-");
			for (String prodId : ids) {
				Product product = productService.getById(prodId);
				list.add(product);
			}
			// 将历史记录的集合放到域中
			request.setAttribute("historyList", list);
		}

		return "product/history";
	}
	
	/**
	 * 根据分类的id来查商品列表
	 * 分页显示
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=true,defaultValue="1")Integer page, Model model,
		   @RequestParam(value="bigCid",required=false)Integer bigCid,
		   @RequestParam(value="cid",required=false)Integer cid){
		
		PageInfo<Product> pageInfo = productService.find(page,Constant.PRODUCT_BY_CATEGORY_PAGE,bigCid,cid);
		model.addAttribute("pageInfo", pageInfo);

		// 查询二级分类及其父分类,// 如果查询不到，前台应该只传了一级分类，没传入二级分类
		Category cate = null;
		if(cid != null){
			cate = categoryService.getByCid(cid);
		}else{
			cate = categoryService.getByCid(bigCid);
		}
		model.addAttribute("cate",cate);

		return "product/product_list";
	}
	
	/**
	 * 根据id查询商品，并保存历史记录
	 */
	@RequestMapping("/{id}")
	public String getById(@PathVariable("id")String id,
			HttpServletRequest request,HttpServletResponse response){
		
		Product product = productService.getById(id);
		request.setAttribute("prod", product);

		// 记录历史数据
		historyRecord(id, request, response);

		return "product/product_info";
	}

	/**
	 * =========历史记录的记录==========
	 * @param id
	 * @param request
	 * @param response
	 */
	private void historyRecord(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
		// 获得客户端携带的cookie----获得名字是history的cookie
		String historyC = CookieUtils.getCookieValue(request, "history", true);
		// 如果指定的cookie不为空，就按步骤存储
		if(StringUtils.isNotBlank(historyC)){
			// 1-3-2	本次prodId是8		8-1-3-2
			// 1-3-2	本次prodId是3		3-1-2
			// 1-3-2	本次prodId是2		2-1-3
			String[] split = historyC.split("-");//{3,1,2}
			LinkedList<String> list = new LinkedList<String>(Arrays.asList(split));//[3,1,2]

			// 判断集合中是否存在当前的prodId,包含当前查看商品的prodId，先删再放头上
			if(list.contains(id)){
				list.remove(id);
			}
			// 不包含当前查看商品的prodId,直接将id放头上
			list.addFirst(id);
			// 将[3,1,2]->3-1-2字符串
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i<list.size() && i< Constant.PRODUCT_HISTORY_RECODE; i++){
				sb.append(list.get(i));
				sb.append("-");
			}
			//去掉最后一个-
			historyC = sb.substring(0, sb.length()-1);

			// 当转发之前，创建cookie存储prodId
			CookieUtils.setCookie(request, response, "history", historyC, Integer.MAX_VALUE, true);
		}else{
			CookieUtils.setCookie(request, response, "history", id, Integer.MAX_VALUE, true);
		}
	}


	/**
	 * ajax根据id查询库存数量
	 */
	@RequestMapping("/getNum/{id}")
	@ResponseBody
	public Result getNum(@PathVariable("id") String id){
		Product product = productService.getById(id);
		return Result.success().add("prodNum", product.getNum());
	}
}
