package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CustomerServeDao;
import com.mage.crm.query.CustomerServeQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.CookieUtil;
import com.mage.crm.util.EmptyUtil;
import com.mage.crm.vo.CustomerServe;
import com.mage.crm.vo.ServeType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServeService {
    @Resource
    private CustomerServeDao customerServeDao;
    public void insert(CustomerServe customerServe) {
        cheakCustomerServeParams(customerServe.getServeType(),customerServe.getCustomer(),customerServe.getServiceRequest());
        customerServe.setState(ServeType.CREATE.getType());
        customerServe.setCreateDate(new Date());
        customerServe.setUpdateDate(new Date());
        customerServe.setIsValid(1);
        AssertUtil.isTrue(customerServeDao.insert(customerServe)<1,"服务创建失败");
    }
    private void cheakCustomerServeParams(String serveType, String customer,String serviceRequest) {
        AssertUtil.isTrue(EmptyUtil.isEmpty(serveType), "服务类型非空!");
        AssertUtil.isTrue(EmptyUtil.isEmpty(customer), "客户名称非空!");
        AssertUtil.isTrue(EmptyUtil.isEmpty(serviceRequest), "内容非空!");
    }

    public Map<String,Object> queryCustomerServesByParams(CustomerServeQuery customerServeQuery) {
        PageHelper.startPage(customerServeQuery.getPage(),customerServeQuery.getRows());
        List<CustomerServe> customerServeList=customerServeDao.queryCustomerServesByParams(customerServeQuery);
        PageInfo<CustomerServe> pageInfo = new PageInfo<>(customerServeList);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("rows",pageInfo.getList());
        return hashMap;
    }

    public void update(CustomerServe customerServe, HttpServletRequest request) {
        cheakCustomerServeParams(customerServe.getServeType(),customerServe.getCustomer(),customerServe.getServiceRequest());
        customerServe.setUpdateDate(new Date());
        if(ServeType.ASSIGN.getType().equals(customerServe.getState())){
            customerServe.setAssigner(CookieUtil.getCookies(request,"trueName"));
            customerServe.setAssignTime(new Date());
        }else if(ServeType.PROCEED.getType().equals(customerServe.getState())){
            AssertUtil.isTrue(EmptyUtil.isEmpty(customerServe.getServiceProce()),"处理内容不能为空!");
            customerServe.setServiceProcePeople(CookieUtil.getCookies(request,"trueName"));
            customerServe.setServiceProceTime(new Date());
        }else if(ServeType.FEEDBACK.getType().equals(customerServe.getState())){
            AssertUtil.isTrue(EmptyUtil.isEmpty(customerServe.getServiceProceResult()),"处理结果不能为空");
            AssertUtil.isTrue(EmptyUtil.isEmpty(customerServe.getMyd()),"满意度不能为空");
            customerServe.setState(ServeType.ARCHIVE.getType());
        }
        AssertUtil.isTrue(customerServeDao.update(customerServe)<1,"操作失败!");
    }
}
