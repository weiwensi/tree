package com.demo.tree.aop;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.demo.tree.annotaion.ArchivesLog;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



public class ArchivesLogAspect {
    private final Logger logger = Logger.getLogger(this.getClass());
    // 请求地址
    private String requestPath = null ;
    // 用户名
    private String userName = "" ;
    // 传入参数
    private Map<?,?> inputParamMap = null ;
    private Map<String, Object> outputParamMap = null; // 存放输出结果
    private long startTimeMillis = 0; // 开始时间
    private long endTimeMillis = 0; // 结束时间
    private HttpServletRequest request = null;

    /**
     *
     * @Description: 方法调用前触发   记录开始时间
     * @author fei.lei
     * @date 2016年11月23日 下午5:10
     * @param joinPoint
     */
    public void before(JoinPoint joinPoint){
        //System.out.println("被拦截方法调用之后调用此方法，输出此语句");
        request = getHttpServletRequest();
        //fileName  为例子
        Object obj =request.getParameter("fileName");
        System.out.println("方法调用前: " + obj);

    }

    /**
     *
     * @Description: 方法调用后触发   记录结束时间
     * @author fei.lei
     * @date 2016年11月23日 下午5:10
     * @param joinPoint
     */
    public  void after(JoinPoint joinPoint) {
        request = getHttpServletRequest();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = null;
        try {
            targetClass = Class.forName(targetName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Method[] methods = targetClass.getMethods();
        String operationName = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs!=null&&clazzs.length == arguments.length&&method.getAnnotation(ArchivesLog.class)!=null) {
                    operationName = method.getAnnotation(ArchivesLog.class).operationName();
                    break;
                }
            }
        }
        endTimeMillis = System.currentTimeMillis();
        //格式化开始时间
        String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTimeMillis);
        //格式化结束时间
        String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTimeMillis);

        Object obj =request.getParameter("fileName");
        System.out.println("方法调用后: " + obj);
        //System.out.println(" 操作人: "+user.getName()+" 操作方法: "+operationName+" 操作开始时间: "+startTime +" 操作结束时间: "+endTime);

    }
    /**
     * @Description: 获取request
     * @author fei.lei
     * @date 2016年11月23日 下午5:10
     * @param
     * @return HttpServletRequest
     */
    public HttpServletRequest getHttpServletRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }

    /**
     *
     * @Title：around
     * @Description: 环绕触发
     * @author fei.lei
     * @date 2016年11月23日 下午5:10
     * @param joinPoint
     * @return Object
     * @throws Throwable
     */
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        return null;
    }

    /**
     *
     * @Title：printOptLog
     * @Description: 输出日志
     * @author fei.lei
     * @date 2016年11月23日 下午5:10
     */
    /*private void printOptLog() {
        Gson gson = new Gson(); // 需要用到google的gson解析包
        String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTimeMillis);
        String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTimeMillis);
        logger.info("user :" +user.getName()+ " start_time: " +  startTime +" end_time: "+endTime);
    }  */


}