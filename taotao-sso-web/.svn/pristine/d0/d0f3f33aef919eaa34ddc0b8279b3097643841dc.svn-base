package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 展示登录注册页面Controller
 * @author Administrator
 *
 */
@Controller
public class PageController {
	
	@RequestMapping("/page/register")
	public String showRegister() {
		return "register";
	}
	
	@RequestMapping("/page/login")
	public String showLogin(Model model,String url) {
		model.addAttribute("redirect", url);
		return "login";
	}
}
