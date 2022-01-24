package com.rabbitmq.producter;

import com.wxx.rabbitmq.RabbitmqApplication;
import com.wxx.rabbitmq.service.Service;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RabbitmqApplication.class)
public class Test {
    @Autowired
    private Service service;

    @org.junit.jupiter.api.Test
    public void contextLoads(){
        service.makeOrder("1","fanout生产者模式==》发送消息",12);
    }
}
