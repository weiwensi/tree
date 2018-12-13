package com.demo.tree.controller;

import com.alibaba.fastjson.JSON;
import com.demo.tree.bean.Permission;
import com.demo.tree.service.MongoDBService;
import com.demo.tree.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class IndexController {
/*

    //这是使用mysql作为数据库的注入
    @Resource(name="permissionServiceImpl")
   PermissionService permissionService;




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
        List<Permission> allPermission = permissionService.queryAllPermission();
        //2.采用并行for循环代替嵌套for循环，大大提高了系统性能。
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
    @RequestMapping("/permission/doAdd.do")
   public Object  doAdd(Permission permission){
        int i = permissionService.savePermission(permission);
     if(i==1){
       return "redirect:/permission/loadData.do";
     }else{
         return "add";
}
   }
//跳到修改页面，进行数据的回显
    @RequestMapping("/permission/toUpdate.do")
    public String  toUpdate(Integer id,Map<String,Object> map){
       Permission permission =  permissionService.getPermissionByID(id);
        map.put("permission",permission);
        return  "update";
    }
    //对修改数据进行保存
      @RequestMapping("permission/doUpdate.do")
    public String  doUpdate(Permission permission){
          permissionService.doUpdate(permission);
        return "redirect:/permission/loadData.do";
    }
  //删除
    @ResponseBody
    @RequestMapping("permission/doDelete.do")
    public Object  doDelete( @RequestParam Integer id){
        int date=permissionService.deletePermission(id);
        return date;
    }

*/



}
