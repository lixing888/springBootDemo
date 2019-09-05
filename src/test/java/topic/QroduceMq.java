package topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class QroduceMq {
    private static final String SUBJECT = "test-activemq-queue";
    //连接账号
    private static String userName = "admin";
    //连接密码
    private static String password = "admin";
    //连接地址
    private static String brokerURL = "tcp://localhost:61616";
    public static void main(String[] args) throws JMSException {
        //初始化连接工厂 
        ConnectionFactory connectionFactory = new
                ActiveMQConnectionFactory(userName,password,brokerURL);
        //获得连接
        Connection conn = connectionFactory.createConnection();
        //创建Session，此方法第一个参数表示会话是否在事务中执行，第二个参数设定会话的应答模式
        Session session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
        //创建队列
        Destination dest = session.createTopic("MyTopic");
        //通过session可以创建消息的生产者
        MessageProducer producer = session.createProducer(dest);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);//持久化
        //启动连接 ，连接的启动位置和queue略有不同，需要把配置配完，在启动连接
        conn.start();
        for (int i=0;i<3;i++) {
            //初始化一个mq消息
            TextMessage message = session.createTextMessage("topic 持久化66" + i);
            //发送消息
            producer.send(message);//
        }
        session.commit();
        session.close();
        //关闭mq连接
        conn.close();
    }

}
