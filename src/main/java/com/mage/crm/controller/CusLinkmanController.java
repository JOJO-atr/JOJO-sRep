package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModle;
import com.mage.crm.query.CusLinkmanQuery;
import com.mage.crm.service.CusLinkmanService;
import com.mage.crm.vo.CusLinkman;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/customer_linkman")
public class CusLinkmanController extends BaseController{
    @Resource
    private CusLinkmanService cusLinkmanService;
    @RequestMapping("/queryCusLinkman")
    @ResponseBody
    public Map<String,Object> queryCusLinkman(CusLinkmanQuery cusLinkmanQuery){
        Map<String, Object> map = cusLinkmanService.queryCusLinkman(cusLinkmanQuery);
        return  map;
    }
    @RequestMapping("/addCusLinkman")
    public void addCusLinkman(CusLinkman cusLinkman){
        cusLinkmanService.addCusLinkman(cusLinkman);
    }
    @RequestMapping("/updateCusLinkman")
    public void updateCusLinkman(CusLinkman cusLinkman){
        cusLinkmanService.updateCusLinkman(cusLinkman);
    }
}
