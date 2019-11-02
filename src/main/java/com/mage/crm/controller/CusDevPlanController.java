package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModle;
import com.mage.crm.query.CusDevPlanQuery;
import com.mage.crm.service.CusDevPlanService;
import com.mage.crm.service.SaleChanceService;
import com.mage.crm.vo.CusDevPlan;
import com.mage.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/cus_dev_plan")
public class CusDevPlanController extends BaseController {
    @Resource
    private CusDevPlanService cusDevPlanService;
    @Resource
    private SaleChanceService saleChanceService;
    @RequestMapping("/index")
    public String  index(int id,Model model){
        SaleChance saleChance = saleChanceService.querySaleChancesById(id);
        //springMVC中使用@ModelAttribute注解将 查询的数据放到org.springframework.ui.model中就能直接在jsp页面取出来
        model.addAttribute(saleChance);
        return "cus_dev_plan_detail";
    }
    @RequestMapping("/queryCusDevPlan")
    @ResponseBody
    public Map<String,Object> queryCusDevPlan(CusDevPlanQuery cusDevPlanQuery){
        Map<String, Object> map = cusDevPlanService.queryCusDevPlan(cusDevPlanQuery);
        return  map;
    }
    @RequestMapping("/addCusDevPlan")
    @ResponseBody
    public MessageModle  addCusDevPlan(CusDevPlan cusDevPlan){
        MessageModle messageModle = new MessageModle();
        cusDevPlanService.addCusDevPlan(cusDevPlan);
        return messageModle;
    }
    @RequestMapping("/updateCusDevPlan")
    @ResponseBody
    public MessageModle  updateCusDevPlan(CusDevPlan cusDevPlan){
        MessageModle messageModle = new MessageModle();
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return messageModle;
    }
    @RequestMapping("/deleteCusDevPlan")
    @ResponseBody
    public MessageModle  deleteCusDevPlan(Integer id){
        MessageModle messageModle = new MessageModle();
        cusDevPlanService.deleteCusDevPlan(id);
        return messageModle;
    }
    @RequestMapping("/updateDevResult")
    @ResponseBody
    public MessageModle  updateDevResult(Integer saleChanceId,Integer devResult){
        MessageModle messageModle = new MessageModle();
        cusDevPlanService.updateDevResult(saleChanceId,devResult);
        return messageModle;
    }
}
