package com.demo.tree.dao;

import com.demo.tree.bean.Permission;

import java.util.List;

public interface PermissionDao {
    List<Permission> selectAll();

    int savePermission(Permission permission);

    Permission getPermissionByID(Integer id);

    void doUpdate(Permission permission);

    int deletePermission(Integer id);
}
