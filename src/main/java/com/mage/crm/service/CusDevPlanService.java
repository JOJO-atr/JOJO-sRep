package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CusDevPlanDao;
import com.mage.crm.dao.SaleChanceDao;
import com.mage.crm.query.CusDevPlanQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.CusDevPlan;
import com.mage.crm.vo.SaleChance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CusDevPlanService {
    @Resource
    private SaleChanceDao saleChanceDao;
    @Resource
    private CusDevPlanDao cusDevPlanDao;
    public Map<String,Object> queryCusDevPlan(CusDevPlanQuery  cusDevPlanQuery){
        PageHelper.startPage(cusDevPlanQuery.getPage(),cusDevPlanQuery.getRows());
        List<CusDevPlan> cusDevPlans = cusDevPlanDao.queryCusDevPlan(cusDevPlanQuery);
        AssertUtil.isTrue(cusDevPlans==null,"操作异常!");
        PageInfo<CusDevPlan> cusDevPlanPageInfo = new PageInfo<>(cusDevPlans);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",cusDevPlanPageInfo.getList());
        map.put("total",cusDevPlanPageInfo.getTotal());
        return map;
    }

    public void addCusDevPlan(CusDevPlan cusDevPlan) {
        SaleChance saleChance=saleChanceDao.querySaleChancesById(cusDevPlan.getSaleChanceId());
        AssertUtil.isTrue(null==saleChance,"营销机会不存在!");
        AssertUtil.isTrue(saleChance.getDevResult()==3,"营销机会已经完成了");
        AssertUtil.isTrue(saleChance.getDevResult()==4,"营销机会已经失败了");
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        cusDevPlan.setIsValid(1);
        AssertUtil.isTrue(cusDevPlanDao.addCusDevPlan(cusDevPlan)<1,"添加开发计划失败!");
    }

    public void updateCusDevPlan(CusDevPlan cusDevPlan) {
        SaleChance saleChance=saleChanceDao.querySaleChancesById(cusDevPlan.getSaleChanceId());
        AssertUtil.isTrue(null==saleChance,"营销机会不存在!");
        AssertUtil.isTrue(saleChance.getDevResult()==3,"营销机会已经完成了");
        AssertUtil.isTrue(saleChance.getDevResult()==4,"营销机会已经失败了");
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanDao.updateCusDevPlan(cusDevPlan)<1,"修改开发计划失败!");
    }

    public void deleteCusDevPlan(Integer id) {
        AssertUtil.isTrue(cusDevPlanDao.deleteCusDevPlan(id)<1,"删除开发计划失败!");
    }

    public void updateDevResult(Integer saleChanceId, Integer devResult) {
        AssertUtil.isTrue(cusDevPlanDao.updateDevResult(saleChanceId,devResult)<1,"操作失败!");
    }
}
