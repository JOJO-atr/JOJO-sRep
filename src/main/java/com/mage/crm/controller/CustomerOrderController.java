package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.query.CustomerOrderQuery;
import com.mage.crm.service.CustomerOrderService;
import com.mage.crm.vo.CustomerOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/customer_order")
public class CustomerOrderController extends BaseController{
    @Resource
    private CustomerOrderService customerOrderService;
    @RequestMapping("/queryOrdersByCid")
    @ResponseBody
    public Map<String,Object> queryOrdersByCid(CustomerOrderQuery customerOrderQuery){
        Map<String, Object> stringObjectMap = customerOrderService.queryOrdersByCid(customerOrderQuery);
        return stringObjectMap;
    }
    @RequestMapping("/queryOrderInfoById")
    @ResponseBody
    public CustomerOrder queryOrderInfoById(Integer id){
        CustomerOrder customerOrder = customerOrderService.queryOrderInfoById(id);
        return customerOrder;
    }
}
