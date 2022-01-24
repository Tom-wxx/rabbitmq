package com.wxx.orderRabbitmqConsumer.service.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = {"email.fanout.queue"})
@Service
public class FanoutEmailConsumer {

    @RabbitHandler
    public void reviceMessage(String message){
        System.out.println("email fanout消费者模式===>接收到的订单信息是："+message);
    }
}
