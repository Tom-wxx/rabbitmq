package com.wxx.rabbitmqTest.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    public static void main(String[] args) {
        //1.创建连接工厂,连接服务器
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.198.128");
        connectionFactory.setPort(5672); //设置端口
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/"); //虚拟访问节点
        //2.创建连接和通道
        Connection connection=null;
        Channel channel=null;

        try {
            //3. 从工厂中获取连接
            connection=connectionFactory.newConnection("生产者");
            //4. 从连接中获取通道
            channel=connection.createChannel();
            /**
             *  发消息给中间件rabbotmq-server
             *  params1： 交换机  实际生产中指定交换机
             *  params2:  队列名称或者路由key
             *  params3： 消息的状态控制 是否持久化
             *  params4：消息的主体
             *  */
            //5. 准备发消息
            String message="路由模式 routing";
            //6. 准备交换机
            String exchangeName="direct_exchange";   //fanout-exchange-> ->fanout   topic_exchange->com.course.order->topic    交换机-路由key-类型 是关联起来的
            //7. 定义路由key
            String routeKey="email"; //com.course.order
            //8. 指定交换机类型
            String type="direct";  //fanout
            channel.basicPublish(exchangeName,routeKey,null,message.getBytes());
            System.out.println("消息发送成功");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("消息发送异常");
        }finally {
            //9.关闭通道
            if (channel!=null && channel.isOpen()){
                try {
                    channel.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            //10.关闭连接
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
