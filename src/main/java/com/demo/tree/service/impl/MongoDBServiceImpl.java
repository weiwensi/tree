package com.demo.tree.service.impl;


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
    //加载tree，查询所有
    public List<Permission> queryAllPermission() {

        List<Permission> allpermission = mongoTemplate.findAll(Permission.class);
        return allpermission;
    }
   //保存
    public int savePermission(Permission permission) {
        //mongoTemplate.save(permission);
       /* private Integer id;
        private String icon;*/
      //  String s = UUID.randomUUID().toString();
        double random = Math.random();
        int i= (int) random;
        permission.setId(i);
        permission.setIcon(null);
        mongoTemplate.insert(permission);
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
