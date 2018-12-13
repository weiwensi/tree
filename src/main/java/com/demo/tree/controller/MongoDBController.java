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

    //许可首页
    @RequestMapping("index.do")
    public String index(){

        return "index";
    }

    @RequestMapping("toTree.do")
    public  String toTree(){

        return "tree";
    }
    //去往添加页面
    @RequestMapping("permission/toAdd.do")
    public  String toTree3(){
        return "add";
    }

    //加载许可树
    @ResponseBody
    @RequestMapping("permission/loadData.do")
    public  Object loadData(){
        Permission root = null ;
        //1.一次性查询数据库，避免频繁与数据库交互，提高系统性能。
        List<Permission> allPermission = mongoDBService.queryAllPermission();
        //2.采用并行for循环代替嵌套for循环，大大提高了系统性能。
        Map<String,Permission> allMap = new HashMap<String,Permission>();
        for (Permission permission : allPermission) {
            allMap.put(permission.getId(), permission);
        }
        for(Permission permission: allPermission) {
            if(permission.getPid() == null) {
                root = permission;
            }else {
                //组合父与子之间的关系
                String pid = permission.getPid();
                Permission parent = allMap.get(pid);
                parent.getChildren().add(permission);
            }
        }

        return root;
    }
    //执行添加
    @RequestMapping("/permission/doAdd.do")
    public Object  doAdd(Permission permission){
        mongoDBService.savePermission( permission);
            return "redirect:/permission/loadData.do";
    }
    //跳到修改页面，进行数据的回显
    @RequestMapping("/permission/toUpdate.do")
    public String  toUpdate(String id,Map<String,Object> map){
        Permission permission =  mongoDBService.getPermissionByID(id);
        map.put("permission",permission);
        return  "update";
    }
    //执行修改操作
    @RequestMapping("permission/doUpdate.do")
    public String  doUpdate(Permission permission){
        mongoDBService.doUpdate(permission);
        return "redirect:/permission/loadData.do";
    }
    //删除
    @ResponseBody
    @RequestMapping("permission/doDelete.do")
    public Object  doDelete( @RequestParam String id){
           mongoDBService.deletePermission(id);
        return "redirect:/permission/loadData.do";
    }


    //以下为测试数据----------------------------------------------------------------


    //查询mongo中的permission
    @ResponseBody
    @RequestMapping("showMoData.do")
    public List<Permission>  showMoData(){
        List<Permission> list = mongoDBService.queryAllPermission();
        return list;
    }

    //测试mongotemplate的添加    ok
    @ResponseBody
    @RequestMapping("addMoData.do")
    public String  addData(){
        //Integer id, Integer pid, String name, String icon, String url
       // {"id":1,"pid":null,"name":"系统权限菜单","icon":"glyphicon glyphicon-th-list","url":null}
     //   {"id":2,"pid":1,"name":"控制面板","icon":"glyphicon glyphicon-dashboard","url":"main.htm"}
       /* db.permission.save({"id":2,"pid":1,"name":"控制面板","icon":"glyphicon glyphicon-dashboard","url":"main.htm"});
        db.permission.save({"id":3,"pid":1,"name":"权限管理","icon":"glyphicon glyphicon glyphicon-tasks","url":null});
        db.permission.save({"id":4,"pid":3,"name":"用户维护","icon":"glyphicon glyphicon-user","url":"user/index.htm"});
        db.permission.save({"id":5,"pid":3,"name":"角色维护","icon":"glyphicon glyphicon-king","url":"role/index.htm"});
        db.permission.save({"id":6,"pid":3,"name":"许可维护","icon":"glyphicon glyphicon-lock","url":"permission/index.htm"});*/

        Permission permission0 = new Permission("1",null,"系统权限菜单","glyphicon glyphicon-th-list","ffff");
        Permission permission1 = new Permission("2","1","控制面板","glyphicon glyphicon-dashboard","ffff");
        Permission permission2 = new Permission("3","1","权限管理","glyphicon glyphicon glyphicon-tasks","ffff");
        Permission permission3 = new Permission("4","3","用户维护","glyphicon glyphicon glyphicon-tasks","ffff");
        Permission permission4 = new Permission("5","3","角色维护","glyphicon glyphicon-king","ffff");
        Permission permission5 = new Permission("6","3","许可维护","glyphicon glyphicon-king","ffff");
        List<Permission> pems=new ArrayList<Permission>();
        pems.add(permission0);
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
    @RequestMapping("doUpdate.do")
    public  String udate(){
       /* Permission permission = new Permission(6,3,"许可维护","glyphicon glyphicon-king","ffff");
        mongoDBService.doUpdate(permission);*/
        return "ok";
    }


    //查询所有许可
    @ResponseBody
    @RequestMapping("getAllPermission.do")
    public  List<Permission>  getAllPeermissoion(){
        List<Permission> permissions = mongoDBService.queryAllPermission();
        return permissions;
    }

    //测试删除
    @ResponseBody
    @RequestMapping("dolete.do")
    public  String  delete(@RequestParam String id){
        mongoDBService.deletePermission(id);
        return "ok";
    }


}
