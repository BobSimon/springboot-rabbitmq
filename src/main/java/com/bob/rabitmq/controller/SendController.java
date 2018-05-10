package com.bob.rabitmq.controller;

import com.bob.rabitmq.config.ConnectionFactoryConfigure;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @ClassName:SendController
 * @Author: Bob Simon
 * @Date: 2018/5/10 0010 下午 6:31
 * @Description:
 * @Version:1.0
 */

@RestController
public class SendController  {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 生产者
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/send")
    public String send3() throws UnsupportedEncodingException {
        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(ConnectionFactoryConfigure.EXCHANGE, ConnectionFactoryConfigure.ROUTINGKEY2,"哈哈", correlationId);
        return uuid;
    }



}
