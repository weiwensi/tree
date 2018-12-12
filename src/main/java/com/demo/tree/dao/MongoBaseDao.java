package com.demo.tree.dao;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import com.mongodb.WriteResult;

/**
 * 对mongodb CRUD的简单封装
 * @author jelly
 */
@Component
public class MongoBaseDao {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 根据id查询
     * @param _id  主键id
     * @param entityClass 实体类
     * @return T
     * @author jelly
     *
     */
    public <T> T findById( String _id,Class<T> entityClass) {
        return  mongoTemplate.findById(_id, entityClass);
    }
    /**
     * 根据id查询
     * @param _id  主键id
     * @param entityClass 实体类
     * @param collectionName  集合名称
     * @return T
     * @author jelly
     *
     */
    public <T> T findById( String _id,Class<T> entityClass,String collectionName) {
        return  mongoTemplate.findById(_id, entityClass,collectionName);
    }


    /**
     * 保存一个实体对象
     * @param t
     * @return T
     * @author jelly
     *
     */
    public  <T> T save(T t){
        mongoTemplate.save(t);
        return  t;//自动主键返回
    }
    /**
     *
     * @param t
     * @param collectionName
     * @return
     * @return T
     * @author jelly
     *
     */
    public  <T> T save(T t,String collectionName){
        mongoTemplate.save(t,collectionName);
        return  t;//自动主键返回
    }

    /**
     * 查询所有实体对象
     * @param entityClass
     * @return List<T>
     * @author jelly
     *
     */
    public  <T>  List<T> findAll(Class<T> entityClass){
        return    mongoTemplate.findAll(entityClass);
    }
    /**
     * 查询所有实体对象
     * @param entityClass
     * @param collectionName
     * @return List<T>
     * @author jelly
     *
     */
    public  <T>  List<T> findAll(Class<T> entityClass,String collectionName){
        return  mongoTemplate.findAll(entityClass,collectionName);
    }

    /**
     * 根据id删除
     * @param _id
     * @param entityClass
     * @return WriteResult
     * @author jelly
     *
     */
    public  <T> WriteResult  deleteById(String _id,Class<T> entityClass){
        Query query =Query.query(Criteria.where("_id").is(new ObjectId(_id)));

        return     mongoTemplate.remove(query, entityClass);

    }
    /**
     * 根据id删除
     * @param _id
     * @param entityClass
     * @param collectionName
     * @return WriteResult
     * @author jelly
     *
     */
    public  <T> WriteResult  deleteById(String _id,Class<T> entityClass,String collectionName){
        Query query =Query.query(Criteria.where("_id").is(new ObjectId(_id)));

        return     mongoTemplate.remove(query, entityClass,collectionName);

    }


    /**
     * 更新一个实体对象 ，根据_id主键update
     * @param t
     * @throws Exception
     * @return WriteResult
     * @author jelly
     *
     */
    public  <T> WriteResult updateOne(T t) throws Exception{

        //where条件， 根据id 更新
        //Query query =Query.query(Criteria.where("_id").is(new ObjectId(_id)));
        Update  update=new Update();

        Class<? extends Object> clazz=t.getClass(); //clazz 对象

        Field field =   clazz.getDeclaredField("_id");

        PropertyDescriptor idDescriptor = new PropertyDescriptor(field.getName(), clazz);
        String _id = (String) idDescriptor.getReadMethod().invoke(t);

        Query query =Query.query(Criteria.where("_id").is(new ObjectId(_id)));//查询条件  id


        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

        PropertyDescriptor[] propertyDescriptors=  beanInfo.getPropertyDescriptors();//属性描述器数组

        for(int i=0;i<propertyDescriptors.length;i++){
            //属性描述器
            PropertyDescriptor descriptor  = propertyDescriptors[i];

            String propertyName = descriptor.getName(); //获得属性名

            if(propertyName.equals("class") || propertyName.equals("_id") || propertyName.equals("serialVersionUID")){
                continue;
            }
            PropertyDescriptor pd = new PropertyDescriptor(propertyName, clazz);
            Method writeMethod= pd.getWriteMethod();
            if(writeMethod==null){
                continue;
            }
            Method readMethod=pd.getReadMethod();
            String retType= readMethod.getReturnType().getName();
            if(retType.equals(String.class.getName())){//"java.lang.String"

                String  value = (String) readMethod.invoke(t);

                if(StringUtils.isNotEmpty(value)){
                    update.set(propertyName, value);
                }
            }else {
                if(readMethod.invoke(t)!=null){
                    update.set(propertyName, readMethod.invoke(t));
                }
            }
        }

        return  mongoTemplate.updateFirst(query, update, clazz);

    }

    /**
     *  更新一个实体对象 ，根据_id主键update
     * @param t
     * @param collectionName
     * @throws Exception
     * @return WriteResult
     * @author jelly
     *
     */
    public  <T> WriteResult updateOne(T t,String collectionName) throws Exception{

        //where条件， 根据id 更新
        //Query query =Query.query(Criteria.where("_id").is(new ObjectId(_id)));
        Update  update=new Update();

        Class<? extends Object> clazz=t.getClass(); //clazz 对象

        Field field =   clazz.getDeclaredField("_id");

        PropertyDescriptor idDescriptor = new PropertyDescriptor(field.getName(), clazz);
        String _id = (String) idDescriptor.getReadMethod().invoke(t);

        Query query =Query.query(Criteria.where("_id").is(new ObjectId(_id)));//查询条件  id


        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

        PropertyDescriptor[] propertyDescriptors=  beanInfo.getPropertyDescriptors();//属性描述器数组

        for(int i=0;i<propertyDescriptors.length;i++){
            //属性描述器
            PropertyDescriptor descriptor  = propertyDescriptors[i];

            String propertyName = descriptor.getName(); //获得属性名

            if(propertyName.equals("class") || propertyName.equals("_id") || propertyName.equals("serialVersionUID")){
                continue;
            }
            PropertyDescriptor pd = new PropertyDescriptor(propertyName, clazz);
            Method writeMethod= pd.getWriteMethod();
            if(writeMethod==null){
                continue;
            }
            Method readMethod=pd.getReadMethod();
            String retType= readMethod.getReturnType().getName();
            if(retType.equals(String.class.getName())){//"java.lang.String"

                String  value = (String) readMethod.invoke(t);
                if(StringUtils.isNotEmpty(value)){
                    update.set(propertyName, value);
                }
            }else {
                if(readMethod.invoke(t)!=null){
                    update.set(propertyName, readMethod.invoke(t));
                }
            }
        }

        return  mongoTemplate.updateFirst(query, update, clazz,collectionName);

    }



}
