package com.wxx.orderRabbitmqProducer.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @Author wxx
 * @time 2022/1/17*/

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @Author wxx
     * @time 2022/1/17
     * @Description 模拟用户下单*/
    public void makeOrder(String userId,String productId, int num){
        //1. 根据商品Id查询库存是否充足
        //2. 保存订单
        String orderId= UUID.randomUUID().toString();
        System.out.println("订单产生成功："+orderId);
        //3. 通过MQ来完成消息的分发
        /**
         * @Parame1 交换机
         * @Parame2 路由key/queue队列名称
         * @Parame3 消息内容*/
        String exchangeName="fanout_order_exchange";
        String routingKey="";
        rabbitTemplate.convertAndSend(exchangeName,routingKey,productId);

    }

    public void makeOrderDirect(String userId,String productId, int num){
        //1. 根据商品Id查询库存是否充足
        //2. 保存订单
        String orderId= UUID.randomUUID().toString();
        System.out.println("订单产生成功："+orderId);
        //3. 通过MQ来完成消息的分发
        /**
         * @Parame1 交换机
         * @Parame2 路由key/queue队列名称
         * @Parame3 消息内容*/
        String exchangeName="direct_order_exchange";
        String routingKey="sms";
        rabbitTemplate.convertAndSend(exchangeName,"sms",productId);
        rabbitTemplate.convertAndSend(exchangeName,"email",productId);

    }

    public void makeOrderTopict(String userId,String productId, int num){
        //1. 根据商品Id查询库存是否充足
        //2. 保存订单
        String orderId= UUID.randomUUID().toString();
        System.out.println("订单产生成功："+orderId);
        //3. 通过MQ来完成消息的分发
        /**
         * @Parame1 交换机
         * @Parame2 路由key/queue队列名称
         * @Parame3 消息内容*/
        String exchangeName="topic_order_exchange";
        String routingKey="com.email.duanxin";
        // duanxin  #.duanxin.#
        // email    *.email.#
        //  sms      com.#
        rabbitTemplate.convertAndSend(exchangeName,routingKey,productId);

    }

}
