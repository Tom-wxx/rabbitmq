package com.wxx.orderRabbitmqConsumer.service.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = {"duanxin.fanout.queue"})  //绑定队列
@Service
public class FanoutDuanXinConsumer {

    @RabbitHandler
    public void reviceMessage(String message){
        System.out.println("duanxin fanout消费者模式===>接收到的订单信息是："+message);
    }
}
