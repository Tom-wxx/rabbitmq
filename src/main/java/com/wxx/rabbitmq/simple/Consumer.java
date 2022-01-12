package com.wxx.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * rabbitmq为什么基于channel处理而不是连接*/

public class Consumer {

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
           channel.basicConsume("队列一", true, new DeliverCallback() {
               @Override
               public void handle(String s, Delivery message) throws IOException {
                   System.out.println("收到消息是：" + new String(message.getBody(), "UTF-8"));
               }
           }, new CancelCallback() {
               @Override
               public void handle(String s) throws IOException {
                   System.out.printf("接受消息失败");
               }
           });
            System.out.printf("开始接受消息->");
            System.in.read();

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
