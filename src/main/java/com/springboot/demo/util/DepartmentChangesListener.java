package com.springboot.demo.util;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class DepartmentChangesListener {
    @Value("${spring.rocketmq.producer.group}")
    private String producerGroup;
    @Value("${spring.rocketmq.nameServer}")
    private String nameServer;

    private final static String DEPARTMENT_CHANGES_TOPIC = "departmentChanges";

    @PostConstruct
    public void init() throws MQClientException {
        log.info("准备监听：{}", DEPARTMENT_CHANGES_TOPIC);
        DefaultMQPushConsumer consumerOfWhole = new DefaultMQPushConsumer(producerGroup);
        consumerOfWhole.setNamesrvAddr(nameServer);
        consumerOfWhole.subscribe(DEPARTMENT_CHANGES_TOPIC, "*");
        consumerOfWhole.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                try {
                    if (CollUtil.isEmpty(messages)) {
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    for (MessageExt msg : messages) {
                        String str = new String(msg.getBody());
                        log.info("{} Receive New Messages: {}", Thread.currentThread().getName(), str);
                        //onMessage(str);
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        });
        consumerOfWhole.start();
    }
}
