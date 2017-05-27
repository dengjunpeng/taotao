package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.H2Utils;
import com.my10000h.common.pojo.EasyUITreeNode;
import com.my10000h.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分类Service
 * 
 * @author Administrator
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper categoryMapper;

	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = categoryMapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			node.setText(tbContentCategory.getName());
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TaotaoResult addContentCategory(long parentId, String name) {
		// 创建pojo对象
		TbContentCategory category = new TbContentCategory();

		// 补全对象属性
		category.setParentId(parentId);
		category.setName(name);
		// 1-正常，2-删除
		category.setStatus(1);
		// 1-默认排序
		category.setSortOrder(1);
		category.setIsParent(false);
		category.setCreated(new Date());
		category.setUpdated(new Date());
		// 插入到数据库
		categoryMapper.insert(category);
		// 判断父节点状态
		TbContentCategory parent = categoryMapper.selectByPrimaryKey(parentId);
		if (!parent.getIsParent()) {
			// 如果父节点是叶子节点，应该为父节点
			parent.setIsParent(true);
			// 更新父节点
			categoryMapper.updateByPrimaryKey(parent);
		}
		// 返回结果
		return TaotaoResult.ok(category);
	}
}
