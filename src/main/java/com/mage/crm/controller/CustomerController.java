package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModle;
import com.mage.crm.query.CustomerContributeQuery;
import com.mage.crm.query.CustomerQuery;
import com.mage.crm.service.CustomerService;
import com.mage.crm.vo.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {
    @Resource
    private CustomerService customerService;
    @RequestMapping("/index")
    public String index(){
        return "customer";
    }
    @RequestMapping("/queryAllCustomers")
    @ResponseBody
    public List<Customer> queryAllCustomers(){
        List<Customer> customers = customerService.queryAllCustomers();
        return  customers;
    }
    @RequestMapping("/queryCustomersByParams")
    @ResponseBody
    public Map<String,Object> queryCustomersByParams(CustomerQuery customerQuery){
        return customerService.queryCustomersByParams(customerQuery);
    }
    @RequestMapping("/addCustomer")
    @ResponseBody
    public MessageModle addCustomer(Customer customer){
        MessageModle messageModle = new MessageModle();
        customerService.addCustomer(customer);
        messageModle.setMsg("添加成功!");
        return messageModle;
    }
    @RequestMapping("/updateCustomer")
    @ResponseBody
    public MessageModle updateCustomer(Customer customer){
        MessageModle messageModle = new MessageModle();
        customerService.updateCustomer(customer);
        messageModle.setMsg("修改成功!");
        return messageModle;
    }
    @RequestMapping("/deleteCustomer")
    @ResponseBody
    public MessageModle deleteCustomer(int[] id){
        MessageModle messageModle = new MessageModle();
        customerService.deleteCustomer(id);
        messageModle.setMsg("修改成功!");
        return messageModle;
    }
    @RequestMapping("/openCustomerOtherInfo/{type}/{id}")
    public String openCustomerOtherInfo(@PathVariable("type")String type, @PathVariable("id")Integer id, Model model){
        Customer customer = customerService.queryCustomerById(id);
        model.addAttribute("customer",customer);
        switch (type){
            case "1":return "customer_linkMan";
            case "2":return "customer_concat";
            case "3":return "customer_order";
            default:return "error";
        }
    }
    @RequestMapping("queryCustomersContribution")
    @ResponseBody
    public Map<String,Object> queryCustomersContribution(CustomerContributeQuery customerContributeQuery){
        return customerService.queryCustomersContribution(customerContributeQuery);
    }
    @ResponseBody
    @RequestMapping("queryCustomerGc")
    public Map<String,Object> queryCustomerGc(){
        return customerService.queryCustomerGc();
    }

}
