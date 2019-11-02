package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.dao.CustomerDao;
import com.mage.crm.model.MessageModle;
import com.mage.crm.query.CustomerLossQuery;
import com.mage.crm.service.CustomerLossService;
import com.mage.crm.service.CustomerService;
import com.mage.crm.vo.CustomerLoss;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_loss")
public class CustomerLossController extends BaseController {
    @Resource
    private CustomerLossService customerLossService;
    @RequestMapping("index")
    public String index(){
        return "customer_loss";
    }
    @RequestMapping("queryCustomerLossesByParams")
    @ResponseBody
    public Map<String,Object> queryCustomerLossesByParams(CustomerLossQuery customerLossQuery){
        return customerLossService.queryCustomerLossesByParams(customerLossQuery);
    }
    @RequestMapping("/toRepreivePage/{lossId}")
    public String toRepreivePage(@PathVariable("lossId") Integer lossId, Model model){
        Map<String,Object> map=customerLossService.queryCustomerLossByLossId(lossId);
        model.addAttribute("customerLoss",map);
        return "customer_repri";
    }
    @RequestMapping("/updateCustomerLossState")
    @ResponseBody
    public MessageModle updateCustomerLossState(int lossId,String lossReason){
        customerLossService.updateCustomerLossState(lossId,lossReason);
        return createMessage("操作成功!");
    }
    @RequestMapping("queryServeType")
    @ResponseBody
    public Map<String,Object> queryServeType(){
        return customerLossService.queryServeType();
    }
}
