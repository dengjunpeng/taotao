package com.taotao.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.jute.compiler.JInt;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.FactoryBeanNotInitializedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my10000h.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;

/**
 * 购物车管理Controller
 * 
 * @author Administrator
 *
 */
@Controller
public class CartController {

	@Value("${TT_CART}")
	private String TT_CART;

	@Value("${CART_EXPIRE}")
	private Integer CART_EXPIRE;

	@Autowired
	private ItemService itemService;
	

	@RequestMapping("/cart/add/{itemId}")
	public String addItemCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		// 取购物车商品列表
		List<TbItem> cartItemList = getCartItemList(request);
		// 判断商品在购物车中是否存在
		// 有，数量+1
		boolean flag = false;
		for (TbItem tbItem : cartItemList) {
			// 比对数据
			if (tbItem.getId() == itemId.longValue()) {
				tbItem.setNum(tbItem.getNum() + num);
				flag = true;
				break;
			}
		}
		// 没有，添加
		if (!flag) {
			// 调用服务取商品信息
			TbItem tbItem = itemService.getItemById(itemId);
			// 设置购买商品数量
			tbItem.setNum(num);
			String image = tbItem.getImage();
			if (StringUtils.isNotBlank(image)) {
				String[] images = image.split(",");
				tbItem.setImage(images[0]);
			}
			// 商品添加到购物车
			cartItemList.add(tbItem);
		}
		// 购物车列表输入cookie
		CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cartItemList), CART_EXPIRE, true);
		// 返回添加成功的页面
		return "cartSuccess";
	}

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

	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request) {
		// 从cookie中取购物车列表
		List<TbItem> cartItemList = getCartItemList(request);
		// 把数据传递到页面
		request.setAttribute("cartList", cartItemList);
		return "cart";
	}

	@RequestMapping(value = "/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public TaotaoResult updateItmemNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 从cookie中取商品列表
		List<TbItem> cartItemList = getCartItemList(request);
		// 查询到对应的商品
		// 更新商品数量
		for (TbItem tbItem : cartItemList) {
			if (tbItem.getId() == itemId.longValue()) {
				tbItem.setNum(num);
				break;
			}
		}
		// 购物车列表写入cookie
		CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cartItemList), CART_EXPIRE, true);
		// 返回结果
		return TaotaoResult.ok();
	}

	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		// Cookie中获取列表
		List<TbItem> cartItemList = getCartItemList(request);
		// 找到对应的商品
		for (TbItem tbItem : cartItemList) {
			if (tbItem.getId() == itemId.longValue()) {
				// 删除商品
				cartItemList.remove(tbItem);
				break;
			}
		}
		// 列表写入购物车
		CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cartItemList), CART_EXPIRE, true);
		// 重定向
		return "redirect:/cart/cart.html";
	}

}
