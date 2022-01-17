package com.wxx.rabbitmqTest.routing;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {

    private static Runnable runnable=new Runnable() {
        @Override
        public void run() {
            //1.创建连接工厂
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("192.168.198.128");  //连接服务器
            connectionFactory.setPort(5672); //设置端口
            connectionFactory.setUsername("guest");
            connectionFactory.setPassword("guest");
            connectionFactory.setVirtualHost("/"); //虚拟访问节点
            //获取队列名称
            final String queueName = Thread.currentThread().getName();
            Connection connection = null;
            Channel channel = null;
            try {
                //2.从工厂中获取连接
                connection = connectionFactory.newConnection("生产者");
                //3.通过连接获取通道channel
                channel = connection.createChannel();
                //4. 消费者监听队列
                channel.basicConsume(queueName, true, new DeliverCallback() {
                    @Override
                    public void handle(String s, Delivery message) throws IOException {
                        System.out.println(message.getEnvelope().getDeliveryTag());
                        System.out.println(queueName+" 收到消息是：" + new String(message.getBody(), "UTF-8"));
                    }
                }, new CancelCallback() {
                    @Override
                    public void handle(String s) throws IOException {
                        System.out.printf("接受消息失败");
                    }
                });
                System.out.printf(queueName+" 开始接受消息->");
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
    };


    public static void main(String[] args) {
        //启动线程
        for (int i = 1; i <= 3; i++) {
            new Thread(runnable,"queue"+i).start();
        }
    }


}
