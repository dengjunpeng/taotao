package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AD1Node;
import com.taotao.utils.JsonUtils;

/**
 * 首页展示Controller
 * 
 * @author Administrator
 *
 */
@Controller
public class IndexController {

	@Value("${AD1_CATEGORY_ID}")
	private Long AD1_CATEGORY_ID;

	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_WIDTHB}")
	private Integer AD1_WIDTHB;
	@Value("${AD1_HEIGHTB}")
	private Integer AD1_HEIGHTB;

	@Autowired
	private ContentService contentService;

	@RequestMapping("/index")
	public String showIndex(Model model) {
		// 根据cid查询轮播图内容列表cid=89
		List<TbContent> contentList = contentService.getContentsByCid(AD1_CATEGORY_ID);
		// 把列表转成AD1Node列表
		List<AD1Node> ad1Nodes = new ArrayList<>();
		for (TbContent tbContent : contentList) {
			AD1Node node = new AD1Node();
			node.setAlt(tbContent.getTitle());
			node.setWidth(AD1_WIDTH);
			node.setHeight(AD1_HEIGHT);
			node.setWidthB(AD1_WIDTHB);
			node.setHeightB(AD1_HEIGHTB);
			node.setSrc(tbContent.getPic());
			node.setSrcB(tbContent.getPic2());
			node.setHref(tbContent.getUrl());
			ad1Nodes.add(node);
		}
		// 列表转成json数据
		String ad1Json = JsonUtils.objectToJson(ad1Nodes);

		// 传递到页面
		model.addAttribute("ad1", ad1Json);

		return "index";
	}
}