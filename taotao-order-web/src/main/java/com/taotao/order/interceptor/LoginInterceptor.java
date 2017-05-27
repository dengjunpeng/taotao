package com.taotao.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.terracotta.quartz.TerracottaToolkitBuilder;

import com.my10000h.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;

/**
 * 判断用户登录的拦截器
 * 
 * @author Administrator
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Value("${TT_TOKEN}")
	private String TT_TOKEN;
	@Value("${SSO_URL}")
	private String SSO_URL;

	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 执行handler之前，执行
		// 1、从cookie中取token
		String token = CookieUtils.getCookieValue(request, TT_TOKEN);
		// 2、取不到token跳转到sso的登录页面，需要把当前请求的url作为参数传递味道额sso，登录成功后天跳转url页面
		if (StringUtils.isBlank(token)) {
			// 取当前请求的url
			String url = request.getRequestURL().toString();
			// 跳转到登录页面
			response.sendRedirect(SSO_URL + "/page/login?url=" + url);
			// 拦截
			return false;
		}
		// 3、取到token，调用sso的服务，判断用户是否登录
		TaotaoResult taotaoResult = userService.getUserByToken(token);
		// 4、如果没有登录，即没取到用户信息
		if (taotaoResult.getStatus() != 200) {
			// 取当前请求的url
			String url = request.getRequestURL().toString();
			// 跳转到登录页面
			response.sendRedirect(SSO_URL + "/page/login?url=" + url);
			// 拦截
			return false;
		}
		// 5、如果取到用户信息，放行
		// 把用户信息，放到request中
		request.setAttribute("user", taotaoResult.getData());
		// 返回true放行，false拦截
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// handler执行之后，modelAndView返回之前，可以对modelandView进行操作
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// ModelAndView 返回之后，可以处理一下异常
	}

}
