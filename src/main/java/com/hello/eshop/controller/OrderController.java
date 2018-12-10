package com.hello.eshop.controller;

import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.Order;
import com.hello.eshop.bean.OrderItem;
import com.hello.eshop.bean.Product;
import com.hello.eshop.bean.User;
import com.hello.eshop.constant.Constant;
import com.hello.eshop.service.CartService;
import com.hello.eshop.service.OrderService;
import com.hello.eshop.utils.DateUtils;
import com.hello.eshop.utils.IDUtils;
import com.hello.eshop.utils.PaymentUtil;
import com.hello.eshop.utils.Result;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 前台订单Controller
 * @author Hello
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService cartService;


	/**
	 * ajax查询后台订单支付剩余时间
	 */
	@RequestMapping("/countdown/{id}")
	@ResponseBody
	public Result countDownTime (@PathVariable("id")String id) throws ParseException {

		//("2017-05-26 12:50:12");//这里的时间自己去定义，应该是从数据库中取
		Order order = orderService.getById(id);
		Date orderTime = order.getOrderTime();

		Date overDate = new DateTime(orderTime).plusHours(1).toDate();// 计算1小时后
		Date nowDate = new Date();// 现在时间

		long seconds = (overDate.getTime() - nowDate.getTime())/1000;
		if(seconds >= 0){
			return Result.success().add("seconds",new Long(seconds));
		}else{
			return  Result.fail();
		}

	}
	
	/**
	 * 确认收货
	 */
	@RequestMapping("/receive/{id}")
	public String receive(@PathVariable("id") String orderId){
		
		orderService.receive(orderId);
		return "redirect:/order/list";
	}
	
	/**
	 * 易宝支付成功之后的回调
	 * 该方法会在支付成功后 进行调用----- 支付公司 、客户
	 * @author seawind
	 * @throws IOException 
	 * 
	 */
	@RequestMapping("/callback")
	public String callback(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// 获得回调所有数据
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// 身份校验 --- 判断是不是支付公司通知你
		String hmac = request.getParameter("hmac");
		String keyValue = ResourceBundle.getBundle("conf/payment").getString(
				"keyValue");

		// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 响应数据有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向
				// 获取订单和订单项信息,修改订单状态以及库存数量
				Order order = orderService.getById(r6_Order);
				// 如果订单已经付款过，就不要再更新库存了
				if(order.getStatus() == Constant.ORDER_IS_PAY){
					request.setAttribute("msg", "兄弟，不要捣乱~~");
				}else{
					Result result = orderService.update(order);
					if(result.getCode() == Constant.RESULT_SUCCESS){
						request.setAttribute("msg", "您的订单号为："+r6_Order+",金额为："+r3_Amt+"已经支付成功，等待发货~~");
					}else{
						request.setAttribute("msg", "支付失败，请重新再试");
					}
				}

			} else if (r9_BType.equals("2")) {
				// 服务器点对点 --- 支付公司通知你
				System.out.println("付款成功！222");
				// 修改订单状态 为已付款
				// 回复支付公司
				response.getWriter().print("success");
			}
			
		} else {
			// 数据无效
			System.out.println("数据被篡改！");
			request.setAttribute("msg", "支付失败");
		}
		
		return "common/msg";
	}
	
	/**
	 * 易宝在线支付
	 * @param request
	 * @return
	 */
	@RequestMapping("/pay")
	public String pay(Order order,HttpServletRequest request){
		// 调用service更新订单，更新收货信息
		orderService.updateAddr(order);
		order = orderService.getById(order.getId());
		
		// 获得 支付必须基本数据
		String orderId = order.getId();
//		String money = order.getTotal()+"";真正发布时才用，测试只能用1分
		String money = "0.01";
		// 银行
		String pd_FrpId = request.getParameter("pd_FrpId");

		// 发给支付公司需要哪些数据
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("conf/payment").getString("p1_MerId");
		String p2_Order = orderId;
		String p3_Amt = money;
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		// 第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("conf/payment").getString("p8_Url");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 加密hmac 需要密钥
		String keyValue = ResourceBundle.getBundle("conf/payment").getString("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);
		
		// 生成url --- url?
		String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId="+pd_FrpId+
				"&p0_Cmd="+p0_Cmd+
				"&p1_MerId="+p1_MerId+
				"&p2_Order="+p2_Order+
				"&p3_Amt="+p3_Amt+
				"&p4_Cur="+p4_Cur+
				"&p5_Pid="+p5_Pid+
				"&p6_Pcat="+p6_Pcat+
				"&p7_Pdesc="+p7_Pdesc+
				"&p8_Url="+p8_Url+
				"&p9_SAF="+p9_SAF+
				"&pa_MP="+pa_MP+
				"&pr_NeedResponse="+pr_NeedResponse+
				"&hmac="+hmac;
		//带着数据重定向到支付页面
		return "redirect:"+url;
	}
	
	/**
	 * 取消订单
	 * @param id
	 * @return
	 */
	@RequestMapping("/cancel/{orderId}")
	public String getById(@PathVariable("orderId") String id){
		// LoginInterceptor处理
		
		orderService.cancel(id);
		return "redirect:/order/list";
	}
	
	
	/**
	 * 根据id查询订单
	 * 重定向到订单支付页面，避免重复创建订单
	 * 
	 */
	@RequestMapping("/{orderId}")
	public String getById(@PathVariable("orderId") String id, Model model){
		// LoginInterceptor处理
		
		Order order = orderService.getById(id);
		model.addAttribute("order", order);
		return "order/order_info";
	}
	
	/**
	 * 分页根据状态查询用户的订单(包括订单项)
	 * @return
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=true,defaultValue="1")Integer page,
					   @RequestParam(value="status", required = false) Integer status,
					   HttpServletRequest request){
		// LoginInterceptor处理
		User user = (User) request.getSession().getAttribute("user");
		
		PageInfo<Order> pageInfo = orderService.find(status,user.getId(),page,Constant.ORDER_SHOW_PAGE);
		
		request.setAttribute("pageInfo", pageInfo);
		return "order/order_list";
	}
	
	
	/**
	 * 保存用户的订单
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request){
		// LoginInterceptor处理
		// 从session拿到user对象
		User user = (User) request.getSession().getAttribute("user");
		
		// 调用CartService，从redis拿到用户对应的购物车列表
		List<Product> cartList = cartService.getCartList(user.getId());
		
		// ======封装对象开始==========
		Order order = new Order();
		order.setId(DateUtils.getCurrentDateStr());// 设置一个当前时间或者UUID
		// 遍历计算总金额，同时生成存储的OrderItem集合
		Double total = 0.00;
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		
		for (Product product : cartList) {
			Double subTotal = product.getNum() * product.getShopPrice();
			total += subTotal;
			
			OrderItem orderItem = new OrderItem();
			orderItem.setId(IDUtils.genItemId());// 设置一个时间生成的id
			orderItem.setProdName(product.getProdName());//对应的商品名称
			orderItem.setQuantity(product.getNum());// 前台取的数据不可靠
			orderItem.setPrice(product.getShopPrice());// 前台取的数据不可靠
			orderItem.setSubTotal(subTotal);// 前台取的数据不可靠，小计也要计算
			orderItem.setImage(product.getImage());//对应的商品图片
			orderItem.setProdId(product.getId());//插入数据库，只需要FK
			orderItem.setOrderId(order.getId());//插入数据库，只需要FK
			
			orderItems.add(orderItem);
		}
		// 补全属性
		order.setOrderTime(new Date());//设置下单时间
		order.setTotal(total);//设置订单总金额
		
		order.setStatus(Constant.ORDER_IS_NOT_PAY);//设置订单状态
		order.setUserId(user.getId());//插入数据库，只需要FK
		order.setItems(orderItems);//方便调用service层传参，只需要传一个
		
		// ====封装对象结束，调用service保存=====
		Result result = orderService.save(order);
		//如果订单生成成功,清空redis中用户对应的购物车
		if(result.getCode() == Constant.RESULT_SUCCESS){
			cartService.clearCartItem(user.getId());
		}
		
		// 重定向到查询controller，避免重新生成无意义订单
		return "redirect:/order/"+order.getId();
	}
	
}
