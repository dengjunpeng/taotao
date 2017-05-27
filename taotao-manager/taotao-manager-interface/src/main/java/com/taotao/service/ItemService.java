package com.taotao.service;

import com.my10000h.common.pojo.EasyUIDataGridResult;
import com.my10000h.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {
	TbItem getItemById(long itemId);
	EasyUIDataGridResult getItemList(int page, int rows);
	TaotaoResult addItem(TbItem item, String des);
	TbItemDesc geTbItemDescById(long itemId);
}
