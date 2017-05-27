package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.my10000h.common.pojo.TaotaoResult;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
	
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper orderMapper;

	@Autowired
	private TbOrderItemMapper orderItemMapper;

	@Autowired
	private TbOrderShippingMapper orderShippingMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${ORDER_ID_GEN}")
	private String ORDER_ID_GEN;

	@Value("${ORDER_ID_BEGIN_VALUE}")
	private String ORDER_ID_BEGIN_VALUE;

	@Value("${ORDER_ITEM_ID_GEN_KEY}")
	private String ORDER_ITEM_ID_GEN_KEY;

	@Override
	public TaotaoResult createOrder(OrderInfo orderInfo) {
		// 生成订单号，redis的incr生成,订单号最好不要从0开始
		if (!jedisClient.exists(ORDER_ID_GEN)) {
			// 设置初始值
			jedisClient.set(ORDER_ID_GEN, ORDER_ID_BEGIN_VALUE);
		}
		String orderId = jedisClient.incr(ORDER_ID_GEN).toString();
		// 向订单表插入数据，补全
		orderInfo.setOrderId(orderId);
		// 免邮费
		orderInfo.setPostFee("0");
		// 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		// 向订单表插入数据
		orderMapper.insert(orderInfo);
		// 插入订单明细表插入数据
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			// 明细主键id
			String oid = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY).toString();
			tbOrderItem.setId(oid);
			tbOrderItem.setOrderId(orderId);
			orderItemMapper.insert(tbOrderItem);
		}
		// 订单物流表插入数据
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		orderShippingMapper.insert(orderShipping);
		// 返回订单号
		return TaotaoResult.ok(orderId);
	}

}
