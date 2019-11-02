package com.mage.crm.interceptors;


import com.mage.crm.service.UserService;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.Base64Util;
import com.mage.crm.util.CookieUtil;
import com.mage.crm.util.EmptyUtil;
import com.mage.crm.vo.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取userId
        String userId = CookieUtil.getCookies(request, "userId");
        //判断是否为空
        AssertUtil.isTrue(EmptyUtil.isEmpty(userId),309,"用户未登录!");
        //对userId进行解密
        String decode = Base64Util.decode(userId);
        //查询是否有该id的用户
        User user = userService.queryUserById(Integer.parseInt(decode));
        //判断查询到的用户是否为空
        AssertUtil.isTrue(null==user,206,"用户不存在!");
        //判断用户是否注销
        AssertUtil.isTrue("0".equals(user.getIsValid()),"用户已注销!");
        return true;
    }
}
