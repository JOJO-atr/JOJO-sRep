package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModle;
import com.mage.crm.query.CustomerServeQuery;
import com.mage.crm.service.CustomerServeService;
import com.mage.crm.vo.CustomerServe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("customer_serve")
public class CustomerServeController extends BaseController {
    @Resource
    private CustomerServeService customerServeService;
    @RequestMapping("/index/{state}")
    public String index(@PathVariable("state") String state){
        if ("1".equals(state)) {
            return "customer_serve_create";
        }
        if ("2".equals(state)) {
            return "customer_serve_assign";
        }
        if ("3".equals(state)) {
            return "customer_serve_proceed";
        }
        if ("4".equals(state)) {
            return "customer_serve_feed_back";
        }
        if ("5".equals(state)) {
            return "customer_serve_archive";
        }
        return  "error";
    }
    @ResponseBody
    @RequestMapping("insert")
    public MessageModle insert(CustomerServe customerServe){
        customerServeService.insert(customerServe);
        return createMessage("添加服务成功!");
    }
    @ResponseBody
    @RequestMapping("queryCustomerServesByParams")
    public Map<String,Object> queryCustomerServesByParams(CustomerServeQuery customerServeQuery){
        return customerServeService.queryCustomerServesByParams(customerServeQuery);
    }
    @ResponseBody
    @RequestMapping("update")
    public MessageModle update(CustomerServe customerServe, HttpServletRequest request){
         customerServeService.update(customerServe,request);
        return createMessage("分配成功!");
    }
}
