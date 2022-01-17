package com.wxx.rabbitmqTest.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

//amqp协议
/** 简单模式*/
public class Producer {

    public static void main(String[] args) {
        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.198.128");  //连接服务器
        connectionFactory.setPort(5672); //设置端口
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/order"); //虚拟访问节点
        Connection connection = null;
        Channel channel = null;
        try {
            //2.创建连接connection
            connection = connectionFactory.newConnection("生产者");
            //3.通过连接获取通道channel
            channel = connection.createChannel();
            //4.通过创建交换机，声明队列，绑定关系，由路由key发消息和接受消息
            String queueName="队列一";
            //是否持久化 durable  就是消息是否存盘   非持久化是否存盘？  会存盘  但是随着服务器的重启而丢失
            //排他性  是否是一个独占队列
            //是否自动删除    最后一个消费者完毕以后是否把队列删除
            //携带一些附加参数
            //                     名称    是否持久化  是不是具有排他性   是不是代表自动删除  是否有额外的参数
            channel.queueDeclare(queueName,false,false,false,null);
            //5.准备发消息的内容
            String message="wxx 发来消大学奥斯丁息";
            //6.发送消息给队列queue   params1： 交换机  实际生产中指定交换机,这里没有指定是默认的  params2:队列、路由key  params3： 消息的状态控制 是否持久化   params4：消息的主体
            /**面试题：可以存在没有交换机的队列吗?  不可能的，虽然没有指定交换机，但是会存在一个默认的交换机*/
            channel.basicPublish("",queueName,null,message.getBytes());

            System.out.printf("消息发送成功");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //8.关闭通道
            if (channel!=null && channel.isOpen()){
                try {
                    channel.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            //9.关闭连接
            if (connection !=null && connection.isOpen()){
                try {
                    connection.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }


    }
}
