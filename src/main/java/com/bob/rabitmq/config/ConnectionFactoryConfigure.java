package com.bob.rabitmq.config;

import com.bob.rabitmq.macallback.MsgSendConfirmCallBack;
import com.bob.rabitmq.mqconsumer.MessageConsumer;
import com.bob.rabitmq.mqconsumer.MessageConsumer2;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName:ConnectionFactoryConfigure
 * @Author: Bob Simon
 * @Date: 2018/5/10 0010 下午 6:09
 * @Description:
 * @Version:1.0
 */
@Configuration
public class ConnectionFactoryConfigure {

    @Value("${spring.rabbitmq.host}")
    public String host;
    @Value("${spring.rabbitmq.port}")
    public int port;
    @Value("${spring.rabbitmq.username}")
    public String username;
    @Value("${spring.rabbitmq.password}")
    public String password;
    @Value("${spring.rabbitmq.virtual-host}")
    public String virtualHost;

    /** 消息交换机的名字*/
    public static final String EXCHANGE = "exchangeTest";
    /** 队列key1*/
    public static final String ROUTINGKEY1 = "queue_one_key1";
    /** 队列key2*/
    public static final String ROUTINGKEY2 = "queue_one_key2";

    /**
     创建连接工厂
     * @date:2017/8/31
     * @className:ConnectionFactory
     * @author:Administrator
     * @description:
     */
    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(this.host, this.port);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setVirtualHost(this.virtualHost);
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    /**
     1.定义direct exchange，绑定queueTest
     2.durable="true" rabbitmq重启的时候不需要创建新的交换机
     3.direct交换器相对来说比较简单，匹配规则为：如果路由键匹配，消息就被投送到相关的队列
     fanout交换器中没有路由键的概念，他会把消息发送到所有绑定在此交换器上面的队列中。
     topic交换器你采用模糊匹配路由键的原则进行转发消息到队列中
     key: queue在该direct-exchange中的key值，当消息发送给direct-exchange中指定key为设置值时，消息将会转发给queue参数指定
     的消息队列
     * @date:2017/9/1
     * @className:ConnectionFactory
     * @author:Administrator
     * @description:
     */
    @Bean
    public DirectExchange directExchange(){
        DirectExchange directExchange = new DirectExchange(ConnectionFactoryConfigure.EXCHANGE,true,false);
        return directExchange;
    }


    /**
     * --------------------定义queue_one------------------------------------------------
     */

    @Bean
    public Queue queue_one(){
        /**
         *   durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列
         auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
         exclusive  表示该消息队列是否只在当前connection生效,默认是false*/
        Queue queue = new Queue("queue_one",true,false,false);
        return queue;
    }

    /**
     将消息队列1和交换机进行绑定
     * @date:2017/9/1
     * @className:ConnectionFactoryConfigure
     * @author:Administrator
     * @description:
     */
    @Bean
    public Binding binding_one() {
        return BindingBuilder.bind(queue_one()).to(directExchange()).with(ConnectionFactoryConfigure.ROUTINGKEY1);
    }




    /**
     * --------------------定义queue_two------------------------------------------------
     */

    @Bean
    public Queue queue_two(){
        /**
         *   durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列
         auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
         exclusive  表示该消息队列是否只在当前connection生效,默认是false*/
        Queue queue = new Queue("queue_two",true,false,false);
        return queue;
    }

    /**
     将消息队列2和交换机进行绑定
     * @date:2017/9/1
     * @className:ConnectionFactoryConfigure
     * @author:Administrator
     * @description:
     */
    @Bean
    public Binding binding_two() {
        return BindingBuilder.bind(queue_two()).to(directExchange()).with(ConnectionFactoryConfigure.ROUTINGKEY2);
    }



    /**
     queue litener 观察 监听模式 当有消息到达时会通知监听在对应的队列上的监听对象
     * @date:2017/9/1
     * @className:ConnectionFactory
     * @author:Administrator
     * @description:
     */
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer_one(){
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory());
        simpleMessageListenerContainer.addQueues(queue_one());
        simpleMessageListenerContainer.setExposeListenerChannel(true);
        simpleMessageListenerContainer.setMaxConcurrentConsumers(1);
        simpleMessageListenerContainer.setConcurrentConsumers(1);

        //设置确认模式手工确认
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        simpleMessageListenerContainer.setMessageListener(messageConsumer());
        return simpleMessageListenerContainer;
    }

    /**
     定义消费者
     * @date:2017/9/1
     * @className:ConnectionFactory
     * @author:Administrator
     * @description:
     */
    @Bean
    public MessageConsumer messageConsumer(){
        return new MessageConsumer();
    }

    /**
     queue litener  观察 监听模式 当有消息到达时会通知监听在对应的队列上的监听对象
     * @date:2017/9/1
     * @className:ConnectionFactory
     * @author:Administrator
     * @description:
     */
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer_two(){
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory());
        simpleMessageListenerContainer.addQueues(queue_two());
        simpleMessageListenerContainer.setExposeListenerChannel(true);
        simpleMessageListenerContainer.setMaxConcurrentConsumers(1);
        simpleMessageListenerContainer.setConcurrentConsumers(1);

        //设置确认模式手工确认
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        simpleMessageListenerContainer.setMessageListener(messageConsumer2());
        return simpleMessageListenerContainer;
    }

    /**
     定义消费者
     * @date:2017/9/1
     * @className:ConnectionFactory
     * @author:Administrator
     * @description:
     */
    @Bean
    public MessageConsumer2 messageConsumer2(){
        return new MessageConsumer2();
    }

    /**
     定义rabbit template用于数据的接收和发送
     * @date:2017/8/31
     * @className:ConnectionFactory
     * @author:Administrator
     * @description:*/
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        /**若使用confirm-callback或return-callback，必须要配置publisherConfirms或publisherReturns为true
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback*/

        template.setConfirmCallback(msgSendConfirmCallBack());
        //template.setReturnCallback(msgSendReturnCallback());
        /**
         * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true，可针对每次请求的消息去确定’mandatory’的boolean值，
         * 只能在提供’return -callback’时使用，与mandatory互斥*/
        //  template.setMandatory(true);
        return template;
    }

    /**
     消息确认机制
     Confirms给客户端一种轻量级的方式，能够跟踪哪些消息被broker处理，哪些可能因为broker宕掉或者网络失败的情况而重新发布。
     确认并且保证消息被送达，提供了两种方式：发布确认和事务。(两者不可同时使用)在channel为事务时，
     不可引入确认模式；同样channel为确认模式下，不可使用事务。
     * @date:2017/8/31
     * @className:ConnectionFactory
     * @author:Administrator
     * @description:
     */
    @Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack(){
        return new MsgSendConfirmCallBack();
    }

}
