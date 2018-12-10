package com.hello.eshop.controller;

import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.Order;
import com.hello.eshop.constant.Constant;
import com.hello.eshop.service.OrderService;
import com.hello.eshop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台订单Controller
 * @author Hello
 *
 */
@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {
	
	@Autowired
	private OrderService orderService;

	/**
	 * 根据id发货
	 */
	@RequestMapping("/ship/{id}")
	public String ship(@PathVariable("id") String id){
		orderService.ship(id);
		return "redirect:/admin/order/list?page=1&status="+Constant.ORDER_IS_PAY;
		
	}
	
	/**
	 * 根据id查询订单及其订单项
	 * @param id
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public Result getById(@RequestParam("id") String id){
		
		Order order = orderService.getById(id);
		return Result.success().add("items", order.getItems());
	}
	
	/**
	 * 根据状态获取分页订单(不包括订单项)
	 * @param pageNum
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=true,defaultValue="1") Integer pageNum,
			@RequestParam(value="status", required = false) Integer status,Model model){
		// 调用service分页查询
		PageInfo<Order> pageInfo = orderService.list(status,pageNum, Constant.ORDER_LIST_PAGE);
		model.addAttribute("pageInfo", pageInfo);
		// 将状态不需要带过去回显，可以可以从param中取
		return "forward:/admin/order/list.jsp";
	}
}
