package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.query.BaseQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("report")
public class ReportController extends BaseController {
    @RequestMapping("{id}")
    public String index(@PathVariable("id") String id) {
        if ("0".equals(id)) {
            return "customer_contribution";
        }
        if("1".equals(id)){
            return "customer_gc";
        }
        if("2".equals(id)){
            return "customer_serve";
        }
        if("3".equals(id)){
            return "customer_loss";
        }
        return "error";
    }
}
