package com.gysoft.demo.service;

/**
 * Create by wws on 2019/1/7
 */
public interface MQProducer {

//发送消息到指定生产者
    public void sendDataToQueue(String queueKey, Object object);
}
