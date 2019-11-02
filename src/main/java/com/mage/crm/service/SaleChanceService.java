package com.mage.crm.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.SaleChanceDao;
import com.mage.crm.model.MessageModle;
import com.mage.crm.query.SaleChanceQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.EmptyUtil;
import com.mage.crm.vo.SaleChance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleChanceService {
    @Resource
    private SaleChanceDao saleChanceDao;
    public Map<String,Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        PageHelper.startPage(saleChanceQuery.getPage(), saleChanceQuery.getRows());
        List<SaleChance> saleChances = saleChanceDao.querySaleChancesByParams(saleChanceQuery);
        PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChances);
        List<SaleChance> list = pageInfo.getList();
        Map<String, Object> map = new HashMap<>();
        map.put("rows",list);
        map.put("total",pageInfo.getTotal());
        return map;
    }
    public void addSaleChance(SaleChance saleChance){
        //非空判断
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        saleChance.setIsValid(1);
        saleChance.setDevResult(0);
        if (EmptyUtil.isEmpty(saleChance.getAssignMan())){//未分配
            saleChance.setState(0);
        }else {//已分配
            saleChance.setState(1);
            saleChance.setAssignTime(new Date());
        }
        int i = saleChanceDao.addSaleChance(saleChance);
        AssertUtil.isTrue(i<1,"添加失败!");
    }
    public void checkParams(String customerName,String linkMan,String phone){
        AssertUtil.isTrue(EmptyUtil.isEmpty(customerName),"客户名称不能为空!");
        AssertUtil.isTrue(EmptyUtil.isEmpty(linkMan),"联系人不能为空!");
        AssertUtil.isTrue(EmptyUtil.isEmpty(phone),"联系电话不能为空!");
    }
    public void updateSaleChance(SaleChance saleChance){
        //非空判断
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        saleChance.setUpdateDate(new Date());
        saleChance.setDevResult(0);
        if (EmptyUtil.isEmpty(saleChance.getAssignMan())){//未分配
            saleChance.setState(0);
        }else {//已分配
            saleChance.setState(1);
            saleChance.setAssignTime(new Date());
        }
        int i = saleChanceDao.updateSaleChance(saleChance);
        AssertUtil.isTrue(i<1,"修改失败!");
    }

    public void deleteSaleChance(int[] id) {
        //非空判断
        AssertUtil.isTrue(id.length==0,"删除异常!");
        int i = saleChanceDao.deleteSaleChance(id);
        AssertUtil.isTrue(i<1,"删除失败!");
    }

    public SaleChance querySaleChancesById(int id) {
        SaleChance saleChance = saleChanceDao.querySaleChancesById(id);
        AssertUtil.isTrue(saleChance==null,"操作异常!");
        return  saleChance;
    }
}
