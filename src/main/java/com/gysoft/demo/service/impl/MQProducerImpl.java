package com.gysoft.demo.service.impl;

import com.gysoft.demo.service.MQProducer;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author DJZ-WWS
 * @Date 2019/1/7 15:14
 */
@Service
public class MQProducerImpl implements MQProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private final static Logger LOGGER = Logger.getLogger(MQProducerImpl.class);
    //发送消息
    public void sendDataToQueue(String queueKey, Object object) {

        try
        {
            LOGGER.info("=========发送消息开始=============消息："+object.toString());
            amqpTemplate.convertAndSend(queueKey, object);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }
}
