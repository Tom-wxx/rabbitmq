package com.wxx.orderRabbitmqProducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 基于配置类的方式绑定*/


@Configuration
public class DirectRabbitmqConfig {

    //1. 声明注册direct模式的交换机
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("direct_order_exchange",true,false);
    }
    //2. 声明队列sms.direct.queue  email.direct.queue duanxin.direct.queue
    @Bean
    public Queue emailQueueDirect(){
        return new Queue("email.direct.queue",true);
    }
    @Bean
    public Queue smsQueueDirect(){
        return new Queue("sms.direct.queue",true);
    }
    @Bean
    public Queue duanxinQueueDirect(){
        return new Queue("duanxin.direct.queue",true);
    }
    //3. 完成绑定关系（队列和交换机完成绑定关系）direct多了绑定路由key
    @Bean
    public Binding emailBindingDirect(){
        return BindingBuilder.bind(emailQueueDirect()).to(directExchange()).with("email");
    }
    @Bean
    public Binding smsBindingDirect(){
        return BindingBuilder.bind(smsQueueDirect()).to(directExchange()).with("sms");
    }
    @Bean
    public Binding duanxinBindingDirect(){
        return BindingBuilder.bind(duanxinQueueDirect()).to(directExchange()).with("duanxin");
    }
}
