package com.mage.crm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CusLinkmanDao;
import com.mage.crm.dao.CustomerDao;
import com.mage.crm.dao.CustomerLossDao;
import com.mage.crm.query.CustomerContributeQuery;
import com.mage.crm.query.CustomerQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.Customer;
import com.mage.crm.vo.CustomerContribute;
import com.mage.crm.vo.CustomerGc;
import com.mage.crm.vo.CustomerLoss;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CustomerService {
    @Resource
    private CustomerDao customerDao;
    @Resource
    private CustomerLossDao customerLossDao;
    public List<Customer> queryAllCustomers(){
        List<Customer> customers = customerDao.queryAllCustomers();
        return  customers;
    }

    public Map<String,Object> queryCustomersByParams(CustomerQuery customerQuery) {
        PageHelper.startPage(customerQuery.getPage(), customerQuery.getRows());
        List<Customer> customers = customerDao.queryCustomersByParams(customerQuery);
        PageInfo<Customer> pageInfo = new PageInfo<>(customers);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;

    }

    public void addCustomer(Customer customer) {
        customer.setIsValid(1);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        customer.setState(0);
        customer.setKhno("KH"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        AssertUtil.isTrue(customerDao.addCustomer(customer)<1,"添加失败!");
    }

    public void updateCustomer(Customer customer) {
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(customerDao.updateCustomer(customer)<1,"修改失败!");

    }

    public void deleteCustomer(int[] id) {
        AssertUtil.isTrue(customerDao.deleteCustomer(id)<1,"删除失败!");
    }

    public Customer queryCustomerById(Integer id) {
        Customer customer = customerDao.queryCustomerById(id);
        AssertUtil.isTrue(customer==null,"操作失败!");
        return customer;
    }

    public void updateCustomerLossState() {
        List<CustomerLoss> customerLosss=customerDao.queryCustomerLoss();
        List<Integer> id= customerDao.queryCustomerLossId();
        if(customerLosss.size()>0&&customerLosss!=null){
            for(CustomerLoss c:customerLosss){
                c.setState(0);
                c.setIsValid(1);
                c.setCreateDate(new Date());
                c.setUpdateDate(new Date());
            }
        }
        if (id.size()>0){
            customerDao.updateCustomerById(id);
        }
        AssertUtil.isTrue(customerLossDao.insertBatch(customerLosss)<1,"客户流失数据添加失败!");
    }

    public Map<String,Object> queryCustomersContribution(CustomerContributeQuery customerContributeQuery) {
        PageHelper.startPage(customerContributeQuery.getPage(),customerContributeQuery.getRows());
        List<CustomerContribute> customerContributeList=customerDao.queryCustomersContribution(customerContributeQuery);
        PageInfo<CustomerContribute> pageInfo = new PageInfo<>(customerContributeList);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }

    public Map<String,Object> queryCustomerGc() {
        Map<String, Object> map = new HashMap<>();
        int code=300;
        List<CustomerGc> list=customerDao.queryCustomerGc();
        if(list.size()>0&&list!=null){
            int[] total=new int[list.size()];
            String[] levels=new String[list.size()];
            for(int i=0;i<list.size();i++){
                total[i]= Integer.parseInt(list.get(i).getCount());
                levels[i]=list.get(i).getLevel();
            }
            map.put("total",total);
            map.put("levels",levels);
            code=200;
        }
        map.put("code",code);
        return map;
    }
}
