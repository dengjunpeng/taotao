package com.taotao.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class SpringActiveMQ {

	/**
	 * 使用jsmTemplate 发送消息
	 * 
	 * @throws Exception
	 */
	@Test
	public void testJmsTemplate() throws Exception {
		// 初始化spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-activemq.xml");
		// 从容器中获取jsmTemplate对象
		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
		// 从容器中获取Destination对象
		Destination destination = (Destination) applicationContext.getBean("test-queue");
		// 发送消息
		jmsTemplate.send(destination, new MessageCreator() {
			// 产生Message
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage("spring activemq send queue");
				return message;
			}
		});

	}
}
