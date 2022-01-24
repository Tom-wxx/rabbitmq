package com.wxx.orderRabbitmqConsumer.service.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RabbitListener(bindings = @QueueBinding(         //  持久化         删除
        value = @Queue(value = "email.topic.queue",durable = "true",autoDelete = "false"),   //绑定队列
        exchange = @Exchange(value = "topic_order_exchange",type = ExchangeTypes.TOPIC),   //绑定交换机
        key = "*.email.#"                                                                //设置模糊查询路由key
))
public class TopicEmailConsumer {

    @RabbitHandler
    public void reviceMessage(String message){
        System.out.println("email topic消费者模式===>接收到的订单信息是："+message);
    }
}
