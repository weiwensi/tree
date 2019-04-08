package com.gysoft.demo.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author DJZ-WWS
 * @Date 2019/1/7 15:47
 */
@Controller
public class MQController {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String QUEUE_KEY = "test_queue";

    @ResponseBody
    @RequestMapping("test/send.do")
public String  testSendMessage(){


        Map<String ,Object> map=new HashMap<String,Object>();
        map.put("id","xxx");
        map.put("name","tom");
        rabbitTemplate.convertAndSend(QUEUE_KEY,map);

        return "ok";
}
}
