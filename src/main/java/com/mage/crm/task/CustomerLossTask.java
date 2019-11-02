package com.mage.crm.task;

import com.mage.crm.service.CustomerLossService;
import com.mage.crm.service.CustomerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CustomerLossTask {
    @Resource
    private CustomerService customerService;
    @Scheduled(cron="0 0 0 * * ?")
    public void updateCustomerLossState(){
        customerService.updateCustomerLossState();
        System.out.println("我执行了");
    }
}
