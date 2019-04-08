package com.gysoft.demo.componet;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @Description
 * @Author DJZ-WWS
 * @Date 2019/1/7 15:19
 */

//消息监听  用于接受消息
@Component
public class QueueListenter  implements MessageListener {


    public void onMessage(Message message) {
        String  str="";
        try {

            str=new String(message.getBody(),"UTF-8");
            System.out.println("监听QuenListen消息是"+message);
            System.out.println("获取到的消息是"+str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
