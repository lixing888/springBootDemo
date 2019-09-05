package topic;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerMq {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerMq.class);
    private static final String SUBJECT = "test-activemq-queue";
    //连接账号
    private static String userName = "admin";
    //连接密码
    private static String password = "admin";
    //连接地址
    private static String brokerURL = "tcp://localhost:61616";
    public static void main(String[] args) throws JMSException {
        //初始化ConnectionFactory
        ConnectionFactory connectionFactory = new
                ActiveMQConnectionFactory(userName,password,brokerURL);

        //创建mq连接
        Connection conn = connectionFactory.createConnection();
        //参数名称随便起
        conn.setClientID("c3");



        //创建会话
        Session session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);

        //通过会话创建目标
        Topic topic = session.createTopic("MyTopic");
        //第二个参数随便起
        TopicSubscriber subscriber = session.createDurableSubscriber(topic, "t3");
        //持久化消息接收
        //启动连接
        conn.start();
        //创建mq消息的消费者
        Message msg = subscriber.receive();
        while(msg!=null){
            onMessage(msg);
            msg=subscriber.receive(1000L);
//        	session.commit();
//        	message.acknowledge();

        }
        session.commit();
        session.close();
        subscriber.close();
        conn.close();
        //初始化MessageListener
        ConsumerMq me = new ConsumerMq();

        //给消费者设定监听对象
//        consumer.setMessageListener(me);
    }

    public static void onMessage(Message message) {
        TextMessage txtMessage = (TextMessage)message;
        try {
            System.out.println("get message " + txtMessage.getText());
            LOGGER.info ("get message " + txtMessage.getText());
        } catch (JMSException e) {
            LOGGER.error("error {}", e);
        }
    }
}
