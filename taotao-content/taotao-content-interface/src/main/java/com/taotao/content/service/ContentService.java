package com.taotao.content.service;

import java.util.List;

import org.apache.log4j.spi.LoggingEvent;

import com.my10000h.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {

	TaotaoResult addContent(TbContent content);
	List<TbContent> getContentsByCid(long cid);
}
