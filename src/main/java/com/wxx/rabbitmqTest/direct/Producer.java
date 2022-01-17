package com.wxx.rabbitmqTest.direct;

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
            String message="王祥祥===》创建交换机";
            //6. 准备交换机，创建
            String exchangeName="direct_message_exchange";   //fanout-exchange-> ->fanout   topic_exchange->com.course.order->topic    交换机-路由key-类型 是关联起来的
            //7. 指定交换机类型  direct/topic/fanout/headers
            String exchangeType="direct";  //fanout
            //8.  定义路由key
            String routeKey="order"; //com.course.order
            /**
             * 如果已经用界面把queue exchange 关系先绑定的话，代码就不需要编写
             * */
            //9. 声明交换机   所谓的持久化就是指，交换机不会随着服务器重启造成丢失 ， true 代表不丢失
             channel.exchangeDeclare(exchangeName, exchangeType,true);
            for (int i = 5; i <=7 ; i++) {
                //10. 声明队列
                channel.queueDeclare("queue"+i,true,false,false,null);
                //11. 绑定队列和加环己的关系
                if (i==7)  routeKey="course";
                channel.queueBind("queue"+i,exchangeName,routeKey);
            }
            channel.basicPublish(exchangeName,"course",null,message.getBytes());
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
