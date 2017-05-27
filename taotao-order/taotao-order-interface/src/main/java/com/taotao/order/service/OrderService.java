package com.taotao.order.service;

import com.my10000h.common.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;

public interface OrderService {

	TaotaoResult createOrder(OrderInfo orderInfo);
}
