package com.mage.crm.base;


import com.mage.crm.model.MessageModle;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @ModelAttribute
    public void preMethod(HttpServletRequest request){

        request.setAttribute("ctx",request.getContextPath());
    }
    public MessageModle createMessage(String msg){
        MessageModle messageModle = new MessageModle();
        messageModle.setMsg(msg);
        return  messageModle;
    }
}
