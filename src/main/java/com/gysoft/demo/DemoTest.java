package com.gysoft.demo;

import com.gysoft.demo.service.MQProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description
 * @Author DJZ-WWS
 * @Date 2019/1/7 15:26
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring.xml","classpath:/spring/applicationContext-rabbitmq.xml"})
public class DemoTest {

    @Autowired
    MQProducer mqProducer;

    private static final String QUEUE_KEY = "test_queue";


      @Test
    public void send(){
      String mesage="hello  MQ";
      mqProducer.sendDataToQueue(QUEUE_KEY,mesage);
}

}
