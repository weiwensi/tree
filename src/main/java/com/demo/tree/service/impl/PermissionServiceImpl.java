package com.demo.tree.service.impl;

import com.demo.tree.bean.Permission;
import com.demo.tree.dao.PermissionDao;
import com.demo.tree.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionDao permissionDao ;
    //查询所有许可
    public List<Permission> queryAllPermission() {

        return permissionDao.selectAll();
    }

    //添加许可
    public int savePermission(Permission permission) {
       int i= permissionDao.savePermission( permission);

        return i;
    }
   //根据ID查询许可
    public Permission getPermissionByID(Integer id) {
       Permission permission= permissionDao.getPermissionByID(id);

        return permission;
    }
//执行修改
    public void doUpdate(Permission permission) {
        permissionDao.doUpdate( permission);
    }
    //删除许可
    public int deletePermission(Integer id) {
        int i=permissionDao.deletePermission(id);
        return i;
    }
}
