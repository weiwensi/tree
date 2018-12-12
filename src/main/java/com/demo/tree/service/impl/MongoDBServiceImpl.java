package com.demo.tree.service.impl;


import com.alibaba.fastjson.JSON;
import com.demo.tree.bean.Permission;
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
    //加载tree
    public List<Permission> queryAllPermission() {
        List<Permission> all = mongoTemplate.findAll(Permission.class);
        return all;
    }
   //保存
    public int savePermission(Permission permission) {
      //将permission转换成json
        String s = JSON.toJSONString(permission);

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
      /*  Query query = new Query();
        Integer id = permission.getId();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = Update.update("teacher", "Mr.wang");
        mongoTemplate.upsert(query, update, "class");*/

        Query query = Query.query(Criteria.where("classId").is("1"));
        Update update = new Update();
//update.push("Students", student);
        update.addToSet("Permission", permission);
        mongoTemplate.upsert(query, update, "class");
    }
    //删除
    public int deletePermission(Integer id) {
        return 0;
    }
}
