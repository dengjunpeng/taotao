package com.taotao.search.controller;

import java.io.UnsupportedEncodingException;

import javax.jws.WebParam.Mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.my10000h.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;

/**
 * 搜索服务Controller
 * 
 * @author Administrator
 *
 */
@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;

	@Value("${SEARCH_RESULT_ROWS}")
	private Integer SEARCH_RESULT_ROWS;

	@RequestMapping("/search")
	public String search(@RequestParam("q") String queryString, @RequestParam(defaultValue = "1") Integer page,
			Model model) throws Exception {
		// int a = 1/0;//测试全局异常处理
		// 调用服务执行查询
		// 把查询条件转码，解决乱码问题
		queryString = new String(queryString.getBytes("ISO-8859-1"), "UTF-8");
		SearchResult search = searchService.search(queryString, page, SEARCH_RESULT_ROWS);
		// 结果传递给页面
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", search.getTotalPages());
		model.addAttribute("itemList", search.getItemList());
		model.addAttribute("page", page);
		return "search";

	}
}
