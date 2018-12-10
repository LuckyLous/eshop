package com.hello.eshop.controller;

import com.hello.eshop.bean.Product;
import com.hello.eshop.bean.User;
import com.hello.eshop.constant.Constant;
import com.hello.eshop.service.CartService;
import com.hello.eshop.service.ProductService;
import com.hello.eshop.utils.CookieUtils;
import com.hello.eshop.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 购物车Controller
 * 未登录状态，使用Cookie保存，所以不使用LoginInterceptor，自行判断
 * 已登录状态，使用redis保存
 * @author Hello
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController {
	
	//cookie中的购物车保存时间
	private Integer COOKIE_CART_EXPIRE = Integer.parseInt(
			ResourceBundle.getBundle("conf/cookie").getString("cookie.CART_EXPIRE"));
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CartService cartService;
	
	/**
	 * 新增购物车列表
	 * @param prodId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/add/{prodId}")
	public String addCart(@PathVariable String prodId, @RequestParam(defaultValue="1")Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		//判断用户是否登录
		User user = (User) request.getSession().getAttribute("user");
		//如果是登录状态，把购物车写入redis
		if (user != null) {
			//保存到服务端
			cartService.addCart(user.getId(), prodId, num);
			//返回逻辑视图
			return "redirect:/cart/cart.html";
		}
		//如果未登录使用cookie
		//从cookie中取购物车列表
		List<Product> cartList = getCartListFromCookie(request);
		//判断商品在购物车列表中是否存在
		boolean flag = false;
		for (Product product : cartList) {
			//如果存在数量相加
			if (prodId.equals(product.getId())) {
				flag = true;
				//找到商品，数量相加
				product.setNum(product.getNum() + num);
				//跳出循环
				break;
			}
		}
		//如果不存在
		if (!flag) {
			//根据商品id查询商品信息。得到一个Product对象
			Product product = productService.getById(prodId);
			// 为了减少占用Cookie的空间，去除无关的引用，否则只能显示2个购物项
			Product prod = new Product();
			prod.setId(product.getId());
			prod.setProdName(product.getProdName());
			prod.setImage(product.getImage());
			prod.setShopPrice(product.getShopPrice());
			//设置购买数量
			prod.setNum(num);

			//把商品添加到商品列表
			cartList.add(prod);

			// 超过cookie容量，给用户提示
			if(cartList.size() > Constant.CART_COOKIE_MAX_SPACE){
				request.setAttribute("msg","超过cookie容量，请登录账号进行保存");
				request.setAttribute("newUrl","user/login.html");
				return "common/msg";
			}
		}
		//写入cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//返回添加成功页面
		return "redirect:/cart/cart.html";
	}
	
	/**
	 * 从cookie中取购物车列表的处理
	 * <p>Title: getCartListFromCookie</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 */
	private List<Product> getCartListFromCookie(HttpServletRequest request) {
		String json = CookieUtils.getCookieValue(request, "cart", true);
		//判断json是否为空
		if (StringUtils.isBlank(json)) {
			return new ArrayList<>();
		}
		//把json转换成商品列表
		List<Product> list = JsonUtils.jsonToList(json, Product.class);
		return list;
	}
	
	/**
	 * 展示购物车列表
	 * <p>Title: showCatList</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 */
	@RequestMapping("/cart.html")
	public String showCartList(HttpServletRequest request, HttpServletResponse response) {
		//从cookie中取购物车列表
		List<Product> cartList = getCartListFromCookie(request);
		//判断用户是否为登录状态
		User user = (User) request.getSession().getAttribute("user");
		//如果是登录状态
		if (user != null) {
			//从cookie中取购物车列表
			//如果不为空，把cookie中的购物车商品和服务端的购物车商品合并。
			cartService.mergeCart(user.getId(), cartList);
			//把cookie中的购物车删除
			CookieUtils.deleteCookie(request, response, "cart");
			//从服务端取购物车列表
			cartList = cartService.getCartList(user.getId());
			
		}
		//把列表传递给页面
		request.setAttribute("cartList", cartList);
		// 
		//返回逻辑视图
		return "cart/cart";
	}
	
	/**
	 * 更新购物车商品数量
	 */
	@RequestMapping("/update/num/{prodId}/{num}")
	public String updateCartNum(@PathVariable String prodId, @PathVariable Integer num
			, HttpServletRequest request ,HttpServletResponse response) {
		//判断用户是否为登录状态
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			cartService.updateCartNum(user.getId(), prodId, num);
			return "redirect:/cart/cart.html";
		}
		//从cookie中取购物车列表
		List<Product> cartList = getCartListFromCookie(request);
		//遍历商品列表找到对应的商品
		for (Product product : cartList) {
			if (prodId.equals(product.getId())) {
				//更新数量
				product.setNum(num);
				break;
			}
		}
		//把购物车列表写回cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//返回成功
		return "redirect:/cart/cart.html";
	}
	
	/**
	 * 删除购物车商品
	 */
	@RequestMapping("/delete/{prodId}")
	public String deleteCartItem(@PathVariable String prodId, HttpServletRequest request,
			HttpServletResponse response) {
		//判断用户是否为登录状态
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			cartService.deleteCartItem(user.getId(), prodId);
			return "redirect:/cart/cart.html";
		}
		//从cookie中取购物车列表
		List<Product> cartList = getCartListFromCookie(request);
		//遍历列表，找到要删除的商品
		for (Product product : cartList) {
			if (prodId.equals(product.getId())) {
				//删除商品
				cartList.remove(product);
				//跳出循环
				break;
			}
		}
		//把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//返回逻辑视图
		return "redirect:/cart/cart.html";
	}
	
	/**
	 * 清空购物车商品
	 */
	@RequestMapping("/clear")
	public String clearCartItem(HttpServletRequest request,HttpServletResponse response){
		//判断用户是否为登录状态
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			cartService.clearCartItem(user.getId());
			return "redirect:/cart/cart.html";
		}
		//把购物车列表从cookie删去
		CookieUtils.deleteCookie(request, response, "cart");
		return "redirect:/cart/cart.html";
	}
}
