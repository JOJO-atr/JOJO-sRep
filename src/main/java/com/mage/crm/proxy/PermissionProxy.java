package com.mage.crm.proxy;

import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.RequestPermission;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.List;

@Component
@Aspect
public class PermissionProxy {
    @Resource
    private HttpSession session;
    @Pointcut("@annotation(com.mage.crm.vo.RequestPermission)")
    public void cut(){
    }
    @Before(value = "cut()")
    public void before(JoinPoint jp){
        MethodSignature methodSignature= (MethodSignature) jp.getSignature();
        Method method=methodSignature.getMethod();
        RequestPermission permission = method.getAnnotation(RequestPermission.class);
        if(permission!=null){
           List<String> userPermission = (List<String>) session.getAttribute("userPermission");
            AssertUtil.isTrue(userPermission==null||userPermission.size()<1,"没有权限执行该操作!");
            AssertUtil.isTrue(!userPermission.contains(permission.aclValue()),"没有权限执行该操作!");
        }
    }
}
