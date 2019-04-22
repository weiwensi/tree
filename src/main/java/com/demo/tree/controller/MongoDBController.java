package com.demo.tree.controller;

import com.demo.tree.bean.Permission;
import com.demo.tree.bean.Student;
import com.demo.tree.service.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
public class MongoDBController {
   //这是使用MongoDB作为数据库的注入
    @Autowired
    MongoDBService mongoDBService;



    //加载许可树
    @ResponseBody
    @RequestMapping("/permission/loadData")
    public  List<Student> loadData(){
        List<Student> permissions = mongoDBService.queryAll();

        return permissions;
    }
    //执行添加
    @RequestMapping("/permission/doAdd")
    @ResponseBody
    public String  doAdd(){
        mongoDBService.saveStudent( );
        return  "add success";
    }
    //跳到修改页面，进行数据的回显
    @RequestMapping("/permission/toUpdate.do")
    public Permission  toUpdate(Integer id){
        Permission permission =  mongoDBService.getPermissionByID(id);
        return  permission;
    }
    //执行修改操作
    @RequestMapping("permission/doUpdate")
    public void  doUpdate(Student student){
        mongoDBService.doUpdate(student);
    }
    //删除
    @ResponseBody
    @RequestMapping("permission/doDelete.do")
    public void  doDelete( @RequestParam Integer id){
        int date=mongoDBService.deletePermission(id);
    }





}
