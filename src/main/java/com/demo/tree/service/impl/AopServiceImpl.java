package com.demo.tree.service.impl;

import com.demo.tree.annotaion.ArchivesLog;
import com.demo.tree.service.AopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description   测试通过切面实现日志的记录
 * @Author DJZ-WWS
 * @Date 2019/4/22 16:44
 */

@Service
public class AopServiceImpl implements AopService {


    @Autowired
    AopServiceImpl aopService;




    @Override
    @ArchivesLog(operationType = "add",operationName = "添加用户信息")
    public void add(String operauser) {

        System.out.println("进入到add");

    }
}
