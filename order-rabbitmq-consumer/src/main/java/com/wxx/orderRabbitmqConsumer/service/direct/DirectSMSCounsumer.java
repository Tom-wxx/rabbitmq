package com.wxx.orderRabbitmqConsumer.service.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = {"sms.direct.queue"})
@Service
public class DirectSMSCounsumer {

    @RabbitHandler  //自动监听到消息，并注入到此方法中
    public void reviceMessage(String message){
        System.out.println("SMS  direct消费者模式===>接收到的订单信息是："+message);
    }
}
