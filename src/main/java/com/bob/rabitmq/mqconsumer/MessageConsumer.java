package com.bob.rabitmq.mqconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

/**
 * @ClassName:MessageConsumer
 * @Author: Bob Simon
 * @Date: 2018/5/10 0010 下午 6:13
 * @Description:
 * @Version:1.0
 */
public class MessageConsumer implements ChannelAwareMessageListener {

    private Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @Override
    public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
        byte[] body = message.getBody();
        System.out.println("收到消息1 : " + new String(body));
        //确认消息成功消费
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

    }
}
