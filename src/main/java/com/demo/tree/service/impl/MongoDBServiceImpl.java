package com.demo.tree.service.impl;


import com.demo.tree.bean.Permission;
import com.demo.tree.bean.Student;
import com.demo.tree.service.MongoDBService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MongoDBServiceImpl implements MongoDBService {
    @Resource
    private MongoTemplate mongoTemplate;
    public List<Student> queryAllPermission() {

        List<Student> allStudent = mongoTemplate.findAll(Student.class);
        return allStudent;
    }
   //保存
    public int savePermission(Student student) {
        mongoTemplate.insert(student);
        return 0;
    }
    //根据id查找
    public Permission getPermissionByID(Integer id) {
        List<Permission> permissionList = mongoTemplate.find(new Query(Criteria.where("id").is(id)), Permission.class);
        Permission permission = permissionList.get(0);
        return permission;
    }



    //更新
    public void doUpdate(Permission permission) {
        Query query=new Query(Criteria.where("_id").is(permission.getId()));
        Update update = Update.update("name", permission.getName()).set("url",permission.getUrl());
        mongoTemplate.updateFirst(query, update, Permission.class);
    }
    //删除
    public int deletePermission(Integer id) {
        Query query=new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query,Permission.class);
        return 0;
    }


}
