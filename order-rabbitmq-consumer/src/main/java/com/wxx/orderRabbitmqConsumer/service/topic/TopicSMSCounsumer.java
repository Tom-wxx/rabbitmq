package com.wxx.orderRabbitmqConsumer.service.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@RabbitListener(bindings = @QueueBinding(         //  持久化         删除
        value = @Queue(value = "sms.topic.queue",durable = "true",autoDelete = "false"),   //绑定队列
        exchange = @Exchange(value = "topic_order_exchange",type = ExchangeTypes.TOPIC),   //绑定交换机
        key = "com.#"                                                                //设置模糊查询路由key
))
public class TopicSMSCounsumer {

    @RabbitHandler  //自动监听到消息，并注入到此方法中
    public void reviceMessage(String message){
        System.out.println("SMS  topic消费者模式===>接收到的订单信息是："+message);
    }
}
