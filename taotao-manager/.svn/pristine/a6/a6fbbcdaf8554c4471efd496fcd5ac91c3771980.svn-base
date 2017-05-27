package com.taotao.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.xml.soap.Text;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.springframework.jms.connection.SingleConnectionFactory;

public class TestActiveMQ {

	/**
	 * queue
	 */
	@Test
	public void testQueueProducer() throws Exception {
		// 1、创建连接工厂ConnectionFactory对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		// 2、创建一个连接对象
		Connection connection = connectionFactory.createConnection();
		// 3、开启连接
		connection.start();
		// 4、创建会话,arg0是否开启事务，一般不使用事务，
		// 如果arg0为ture，自动忽略arg1，如果为false ，arg1为消息的应答模式， 一般为自动应答
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5、创建Destination对象，使用queue方式
		Queue queue = session.createQueue("test-queue");
		// 6、创建生产者
		MessageProducer producer = session.createProducer(queue);
		// 7、创建TextMessage对象，消息对象
		// 方式一：
		/*
		 * TextMessage textMessage = new ActiveMQTextMessage();
		 * textMessage.setText("hello activemq");
		 */
		// 方式二：
		TextMessage textMessage = session.createTextMessage("hello activemq");
		// 8、发送消息
		producer.send(textMessage);
		// 9、关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	@Test
	public void testQueueConsumer() throws Exception {
		// 创建一个连接工厂对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		// 使用连接工厂创建连接
		Connection connection = connectionFactory.createConnection();
		// 开启连接
		connection.start();
		// 使用连接对象创建一个Session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 使用Session创建一个Destination,Destination应该和消息的发送端一致
		Queue queue = session.createQueue("test-queue");
		// Session创建一个Consumer对象
		MessageConsumer consumer = session.createConsumer(queue);
		// 向Consumer对象设置MessageListener对象，用于接受消息
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				// 获取消息内容
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						String text = textMessage.getText();
						// 打印
						System.out.println(text);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}

			}
		});
		// 等待接受消息
		/*
		 * while (true) { Thread.sleep(100); }
		 */
		System.out.println("按任意键退出");
		// 等待输入
		System.in.read();
		// 关闭资源
		consumer.close();
		session.close();
		connection.close();
	}

	/**
	 * Topic
	 */
	@Test
	public void testTopicProducer() throws Exception {
		// 创建一个连接工厂对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		// 创建连接
		Connection connection = connectionFactory.createConnection();
		// 开启连接
		connection.start();
		// 创建Session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 创建Destination，使用topic
		Topic topic = session.createTopic("test-topic");
		// 创建一个Producer对象
		MessageProducer producer = session.createProducer(topic);
		// 创建一个TextMessage对象
		TextMessage textMessage = session.createTextMessage("hello topic activemq");
		// 发送消息
		producer.send(textMessage);
		// 关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	@Test
	public void testTopicConsumer() throws Exception {
		// 创建一个连接工厂对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		// 创建连接
		Connection connection = connectionFactory.createConnection();
		// 开启连接
		connection.start();
		// 创建Session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("test-topic");
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				// 获取消息内容
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						String text = textMessage.getText();
						// 打印
						System.out.println(text);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		System.out.println("消费者2：");
		System.out.println("按任意键退出");
		// 等待输入
		System.in.read();
		// 关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
}
