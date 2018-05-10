package com.bob.rabitmq.macallback;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * @ClassName:MsgSendConfirmCallBack
 * @Author: Bob Simon
 * @Date: 2018/5/10 0010 下午 6:11
 * @Description:
 * @Version:1.0
 */
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback{
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(" 回调id:" + correlationData);
        if (ack) {
            System.out.println("消息成功消费");
        } else {
            System.out.println("消息消费失败:" + cause+"\n重新发送");

        }
    }
}
