package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModle;
import com.mage.crm.query.CustomerReprieveQuery;
import com.mage.crm.service.CustomerRepriService;
import com.mage.crm.vo.CustomerReprieve;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/customer_repri")
public class CustomerRepriController extends BaseController {
    @Resource
    private CustomerRepriService customerRepriService;
    @RequestMapping("/customerReprieveByLossId")
    @ResponseBody
    public Map<String,Object> customerReprieveByLossId(CustomerReprieveQuery customerReprieveQuery){
        return customerRepriService.customerReprieveByLossId(customerReprieveQuery);
    }
    @RequestMapping("/insertReprive")
    public void insertReprive(CustomerReprieve customerReprieve){
        customerRepriService.insertReprive(customerReprieve);
    }
    @RequestMapping("/updateReprive")
    public void updateReprive(CustomerReprieve customerReprieve){
        customerRepriService.updateReprive(customerReprieve);
    }
    @ResponseBody
    @RequestMapping("deleteReprive")
    public MessageModle deleteReprive(Integer  id){
        customerRepriService.deleteReprive(id);
        return createMessage("删除暂缓措施成功!");
    }
}
