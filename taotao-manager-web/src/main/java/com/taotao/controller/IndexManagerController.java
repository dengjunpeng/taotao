package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my10000h.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;

/**
 * 导入索引库
 * @author Administrator
 *
 */
@Controller
public class IndexManagerController {

	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("/index/import")
	@ResponseBody
	public TaotaoResult importIndex() {
		TaotaoResult taotaoResult = searchItemService.importItemsToIndex();
		return taotaoResult;
	}
}
