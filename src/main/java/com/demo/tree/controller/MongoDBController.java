package com.demo.tree.controller;

import com.demo.tree.bean.Permission;
import com.demo.tree.service.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public  Permission loadData(){
        Permission root = null ;
        List<Permission> allPermission = mongoDBService.queryAllPermission();
        Map<Integer,Permission> allMap = new HashMap<Integer,Permission>();
        for (Permission permission : allPermission) {
            allMap.put(permission.getId(), permission);
        }
        for(Permission permission: allPermission) {
            if(permission.getPid() == null) {
                root = permission;
            }else {
                //组合父与子之间的关系
                Integer pid = permission.getPid();
                Permission parent = allMap.get(pid);
                parent.getChildren().add(permission);
            }
        }

        return root;
    }
    //执行添加
    @RequestMapping("/permission/doAdd")
    @ResponseBody
    public void  doAdd(Permission permission){
        mongoDBService.savePermission( permission);
    }
    //跳到修改页面，进行数据的回显
    @RequestMapping("/permission/toUpdate.do")
    public Permission  toUpdate(Integer id){
        Permission permission =  mongoDBService.getPermissionByID(id);
        return  permission;
    }
    //执行修改操作
    @RequestMapping("permission/doUpdate")
    public void  doUpdate(Permission permission){
        mongoDBService.doUpdate(permission);
    }
    //删除
    @ResponseBody
    @RequestMapping("permission/doDelete.do")
    public void  doDelete( @RequestParam Integer id){
        int date=mongoDBService.deletePermission(id);
    }


    //以下为测试数据----------------------------------------------------------------


    //查询mongo中的permission
    @ResponseBody
    @RequestMapping("showMoData")
    public List<Permission>  showMoData(){
        List<Permission> list = mongoDBService.queryAllPermission();
        return list;
    }

    //测试mongotemplate的添加    ok
    @ResponseBody
    @RequestMapping("addMoData")
    public String  addData(){

        Permission permission = new Permission( 1,null,"系统权限菜单","glyphicon glyphicon-th-list","ffff");
        Permission permission1 = new Permission(2,1,"控制面板","glyphicon glyphicon-dashboard","ffff");
        Permission permission2 = new Permission(3,1,"权限管理","glyphicon glyphicon glyphicon-tasks","ffff");
        Permission permission3 = new Permission(4,3,"用户维护","glyphicon glyphicon glyphicon-tasks","ffff");
        Permission permission4 = new Permission(5,3,"角色维护","glyphicon glyphicon-king","ffff");
        Permission permission5 = new Permission(6,3,"许可维护","glyphicon glyphicon-king","ffff");




        List<Permission> pems=new ArrayList<Permission>();
        pems.add(permission1);
        pems.add(permission2);
        pems.add(permission3);
        pems.add(permission4);
        pems.add(permission5);
        for (Permission pem : pems) {
            mongoDBService.savePermission( pem);
        }
            return "ok";
    }

    @ResponseBody
    @RequestMapping("doUpdate")
    public  String udate(){
        Permission permission = new Permission(6,3,"许可维护","glyphicon glyphicon-king","ffff");
        mongoDBService.doUpdate(permission);
        return "ok";
    }



}
