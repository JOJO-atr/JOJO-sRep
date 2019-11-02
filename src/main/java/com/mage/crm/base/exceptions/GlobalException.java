package com.mage.crm.base.exceptions;

import com.alibaba.fastjson.JSON;
import com.mage.crm.model.MessageModle;
import com.mage.crm.util.CrmConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class GlobalException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {
        ParamsException paramsException;
        ModelAndView modelAndView = createDefaultMoudleAndView(httpServletRequest);
        if(handler instanceof HandlerMethod){
            //判断是否是自定义参数异常
            if(e instanceof  ParamsException){
                paramsException=(ParamsException) e;
                if(paramsException.getCode()==CrmConstant.LOGIN_NO_CODE){
                    modelAndView.addObject("code",CrmConstant.LOGIN_NO_CODE);
                    modelAndView.addObject("msg",CrmConstant.LOGIN_NO_MSG);
                    return modelAndView;
                }

            }
            //判断是json异常还是视图异常
            HandlerMethod handlerMethod=(HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
            //判断是否含有responseBody注解
            //不等于空为json异常
            if(responseBody!=null){
                MessageModle messageModle = new MessageModle();
                messageModle.setCode(CrmConstant.OPS_FAILED_CODE);
                messageModle.setMsg(CrmConstant.OPS_FAILED_MSG);
                if(e instanceof  ParamsException){
                    paramsException=(ParamsException)e;
                    messageModle.setCode(paramsException.getCode());
                    messageModle.setMsg(paramsException.getMsg());
                }
                //写出messageModle
                httpServletResponse.setContentType("application/json;charset=uft-8");
                httpServletResponse.setCharacterEncoding("utf-8");
                PrintWriter printWriter = null;
                try {
                    printWriter = httpServletResponse.getWriter();
                } catch (IOException ioE) {
                    ioE.printStackTrace();
                }finally {
                    if(printWriter!=null){
                        printWriter.write(JSON.toJSONString(messageModle));
                        printWriter.flush();
                        printWriter.close();
                    }
                }
                return  null;
            }else {//等于空为视图异常
                if(e instanceof  ParamsException){
                    paramsException=(ParamsException)e;
                    modelAndView.addObject("code",paramsException.getCode());
                    modelAndView.addObject("msg",paramsException.getMsg());
                }
                return modelAndView;

            }
        }
        return null;
    }
    public ModelAndView createDefaultMoudleAndView(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("code", CrmConstant.OPS_FAILED_CODE);
        modelAndView.addObject("msg",CrmConstant.OPS_FAILED_MSG);
        modelAndView.addObject("ctx",request.getContextPath());
        modelAndView.addObject("uri",request.getRequestURI());
        return modelAndView;
    }
}
