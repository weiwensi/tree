package com.demo.tree.aop;

import com.demo.tree.service.AopService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestAop1 {
    public static void main(String[] args) throws Exception{

        ApplicationContext act =  new ClassPathXmlApplicationContext("spring/application-aop.xml");
        AopService aopService = (AopService) act.getBean("aopService");
        String  userName="zhhh";
        aopService.add(userName);
    }
}