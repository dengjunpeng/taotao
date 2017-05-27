package com.taotao.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.my10000h.common.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;

/**
 * 订单确认页面controller
 * 
 * @author Administrator
 *
 */
@Controller
public class OrderCartController {

	@Value("${TT_CART}")
	private String TT_CART;

	@Value("${CART_EXPIRE}")
	private Integer CART_EXPIRE;

	@Autowired
	private OrderService orderService;

	/**
	 * 展示订单确认页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request) {
		// 用户必须是登录状态
		// 取用户id
		TbUser user = (TbUser) request.getAttribute("user");
		System.out.println(user.getUsername());
		// 根据用户信息取收货地址列表,使用静态数据。
		// 把收货地址取出传递到页面
		// 从cookie中取购物车列表展示到页面
		List<TbItem> cartItemList = getCartItemList(request);
		// 返回逻辑视图
		request.setAttribute("cartList", cartItemList);
		return "order-cart";
	}

	/**
	 * 取购物车商品列表
	 * 
	 * @param request
	 * @return
	 */
	private List<TbItem> getCartItemList(HttpServletRequest request) {
		// 从cookie中取购物车列表
		String json = CookieUtils.getCookieValue(request, TT_CART, true);
		if (StringUtils.isBlank(json)) {
			// 没有内容
			return new ArrayList<>();
		}
		List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
		return list;
	}

	/**
	 * 生成订单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/order/create", method = RequestMethod.POST)
	public String orderCreate(OrderInfo orderInfo, Model model) {
		TaotaoResult result = orderService.createOrder(orderInfo);
		model.addAttribute("orderId", result.getData().toString());
		model.addAttribute("payment", orderInfo.getPayment());
		// 预计三天之后送达,joda-time
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(3);// 3天之后的日期
		model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		return "success";
	}
}
