package com.taotao.content.service;

import java.util.List;

import com.my10000h.common.pojo.EasyUITreeNode;
import com.my10000h.common.pojo.TaotaoResult;

public interface ContentCategoryService {

	List<EasyUITreeNode> getContentCategoryList(long parentId);

	TaotaoResult addContentCategory(long parentId, String name);
}
