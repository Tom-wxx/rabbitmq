package com.wxx.orderRabbitmqConsumer.service.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = {"email.direct.queue"})
@Service
public class DirectEmailConsumer {

    @RabbitHandler
    public void reviceMessage(String message){
        System.out.println("email direct消费者模式===>接收到的订单信息是："+message);
    }
}
