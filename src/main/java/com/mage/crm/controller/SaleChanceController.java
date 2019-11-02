package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModle;
import com.mage.crm.query.SaleChanceQuery;
import com.mage.crm.service.SaleChanceService;
import com.mage.crm.vo.RequestPermission;
import com.mage.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/sale_chance")
public class SaleChanceController extends BaseController {
    @Resource
    private SaleChanceService saleChanceService;
    @RequestMapping("/index/{id}")
    public String index(@PathVariable("id") String id){
        switch (id){
            case "1":return  "sale_chance";
            case "2":return  "cus_dev_plan";
            default:return "error";
        }
    }
    @RequestMapping("/querySaleChancesByParams")
    @ResponseBody
    public Map<String,Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        Map<String, Object> map = saleChanceService.querySaleChancesByParams(saleChanceQuery);
        return map;
    }
    @RequestMapping("/addSaleChance")
    @ResponseBody
    public MessageModle addSaleChance(SaleChance saleChance){
        MessageModle messageModle = new MessageModle();
        saleChanceService.addSaleChance(saleChance);
        messageModle.setMsg("添加成功!");
        return messageModle;
    }
    @RequestMapping("/updateSaleChance")
    @ResponseBody
    public MessageModle updateSaleChance(SaleChance saleChance){
        MessageModle messageModle = new MessageModle();
        saleChanceService.updateSaleChance(saleChance);
        messageModle.setMsg("修改成功!");
        return messageModle;
    }
    @RequestMapping("/deleteSaleChance")
    @ResponseBody
    @RequestPermission(aclValue = "101003")
    public MessageModle deleteSaleChance(int[] id){
        MessageModle messageModle = new MessageModle();
        saleChanceService.deleteSaleChance(id);
        return messageModle;
    }
}
