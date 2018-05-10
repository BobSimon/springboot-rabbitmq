package com.bob.rabitmq.macallback;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @ClassName:MsgSendReturnCallback
 * @Author: Bob Simon
 * @Date: 2018/5/10 0010 下午 6:12
 * @Description:
 * @Version:1.0
 */
public class MsgSendReturnCallback implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String msgJson  = new String(message.getBody());
        System.out.println("回馈消息："+msgJson);
    }


}
