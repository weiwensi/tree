package com.demo.tree.service;

import com.demo.tree.bean.Permission;
import com.demo.tree.bean.Student;

import java.util.List;

public interface MongoDBService {
    List<Student> queryAll();
    //保存许可
    int saveStudent();
    //根据id查询许可
    Permission getPermissionByID(Integer id);
    //更新许可
    void doUpdate(Student  student);
    //删除许可
    int deletePermission(Integer id);
}
