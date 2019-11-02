package com.mage.crm.dao;

import com.mage.crm.query.CusDevPlanQuery;
import com.mage.crm.vo.CusDevPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CusDevPlanDao {
    public List<CusDevPlan> queryCusDevPlan(CusDevPlanQuery cusDevPlanQuery);

    public int addCusDevPlan(CusDevPlan cusDevPlan);

    public  int updateCusDevPlan(CusDevPlan cusDevPlan);

    public int deleteCusDevPlan(Integer id);

    public int updateDevResult(@Param("saleChanceId") Integer saleChanceId,@Param("devResult") Integer devResult);
}
