package com.wxx.rabbitmqTest.work.fair;

import com.rabbitmq.client.*;

import java.io.IOException;

public class WorkTwo {


    public static void main(String[] args) {
        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.198.128");  //连接服务器
        connectionFactory.setPort(5672); //设置端口
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/"); //虚拟访问节点
        Connection connection = null;
        Channel channel = null;
        try {
            //2.创建连接connection
            connection = connectionFactory.newConnection("消费者--workOne");
            //3.通过连接获取通道channel
            channel = connection.createChannel();
            /**如果 queue已经被创建过一次，可以不需要定义
             *  channel.queueDeclare("queue1",true,false,false,null);
             * 同一时刻，服务器追推送一条消息给消费者*/
            //4.定义接受 消息的回调
            channel.basicQos(1);
            Channel finalChannel = channel;
            finalChannel.basicConsume("queue1", false, new DeliverCallback() {  //false 为手动应答  公平分发一定要改为手动应答
                @Override
                public void handle(String s, Delivery message) throws IOException {
                    try {
                        System.out.println("workOne 收到消息是：" + new String(message.getBody(), "UTF-8"));
                        // Thread.sleep(2000);
                        //手动应答
                        finalChannel.basicAck(message.getEnvelope().getDeliveryTag(),false); //单条消费
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }, new CancelCallback() {
                @Override
                public void handle(String s) throws IOException {
                    System.out.printf("接受消息失败");
                }
            });
            System.out.println("开始接受消息->");
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
