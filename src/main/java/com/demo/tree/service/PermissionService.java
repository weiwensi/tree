package com.demo.tree.service;

import com.demo.tree.bean.Permission;

import java.util.List;

public interface PermissionService {

    List<Permission> queryAllPermission();
      //保存许可
    int savePermission(Permission permission);
     //根据id查询许可
    Permission getPermissionByID(Integer id);
     //更新许可
    void doUpdate(Permission permission);
     //删除许可
    int deletePermission(Integer id);
}
