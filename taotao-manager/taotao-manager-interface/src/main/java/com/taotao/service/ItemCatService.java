package com.taotao.service;

import java.util.List;

import com.my10000h.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	List<EasyUITreeNode> getItemCatList(long parentId);
}
