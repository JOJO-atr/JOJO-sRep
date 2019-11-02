package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CustomerRepriDao;
import com.mage.crm.query.CustomerReprieveQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.CustomerReprieve;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerRepriService {
    @Resource
    private CustomerRepriDao customerRepriDao;
    public Map<String,Object> customerReprieveByLossId(CustomerReprieveQuery customerReprieveQuery) {
        PageHelper.startPage(customerReprieveQuery.getPage(),customerReprieveQuery.getRows());
        List<CustomerReprieve> customerReprieves=customerRepriDao.customerReprieveByLossId(customerReprieveQuery);
        PageInfo<CustomerReprieve> pageInfo = new PageInfo<>(customerReprieves);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("rows",pageInfo.getList());
        return hashMap;
    }

    public void insertReprive(CustomerReprieve customerReprieve) {
        customerReprieve.setIsValid(1);
        customerReprieve.setCreateDate(new Date());
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(customerRepriDao.insertReprive(customerReprieve)<1,"添加暂缓措施失败!");
    }

    public void updateReprive(CustomerReprieve customerReprieve) {
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(customerRepriDao.updateReprive(customerReprieve)<1,"修改暂缓措施失败!");
    }

    public void deleteReprive(Integer id) {
        AssertUtil.isTrue(customerRepriDao.deleteReprive(id)<1,"删除暂缓措施失败!");
    }
}
