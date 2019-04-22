package com.demo.tree.aop;

import com.demo.tree.annotaion.ArchivesLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Description   测试spring的aop
 * @Author DJZ-WWS
 * @Date 2019/4/22 16:56
 */
public class AopAspectJ {



    public static final String EDP="execution(* com.demo.tree.service.impl.AopServiceImpl..*(..))";

    /**
     * 前置通知，获取方法执行的时间
     * @param jp
     */
    @Before(EDP)
    public void doBefore(JoinPoint joinPoint) throws ClassNotFoundException {
        System.out.println("=========执行前置通知==========");
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
            for (Method method:methods){
    if(method.getName().equals(methodName)){
        //进入到了这个方法  记录用户  假设每个方法里面都有用户名
        Parameter[] parameters = method.getParameters();
              for (Parameter parameter:parameters){
                  if(parameter.getName().equals("userName")){
                      ArchivesLog annotation = method.getAnnotation(ArchivesLog.class);
                      System.out.println("用户名： "+parameter.getName()+method.getName()+"action"+annotation);
                  }

              }


    }
}

    }


    @AfterReturning(value=EDP,returning="result")
    public void doAfterReturning(JoinPoint jp,String result){
        System.out.println("===========执行后置通知============");
    }

    /**
     * 最终通知：目标方法调用之后执行的通知（无论目标方法是否出现异常均执行）
     * @param jp
     */
    @After(value=EDP)
    public void doAfter(JoinPoint jp){
        System.out.println("===========执行最终通知============");
    }

    /**
     * 环绕通知：目标方法调用前后执行的通知，可以在方法调用前后完成自定义的行为。
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(EDP)
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable{

        System.out.println("======执行环绕通知开始=========");
        // 调用方法的参数
        Object[] args = pjp.getArgs();
        // 调用的方法名
        String method = pjp.getSignature().getName();
        // 获取目标对象
        Object target = pjp.getTarget();
        // 执行完方法的返回值
        // 调用proceed()方法，就会触发切入点方法执行
        Object result=pjp.proceed();
        System.out.println("输出,方法名：" + method + ";目标对象：" + target + ";返回值：" + result);
        System.out.println("======执行环绕通知结束=========");
        return result;
    }

    /**
     * 在目标方法非正常执行完成, 抛出异常的时候会走此方法
     * @param jp
     * @param ex
     */
    @AfterThrowing(value=EDP,throwing="ex")
    public void doAfterThrowing(JoinPoint jp,Exception ex) {
        System.out.println("===========执行异常通知============");
    }
}
