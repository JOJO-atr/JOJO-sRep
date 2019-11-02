package com.mage.crm.dao;

import com.mage.crm.query.SaleChanceQuery;
import com.mage.crm.vo.SaleChance;

import java.util.List;

public interface SaleChanceDao {
    public List<SaleChance> querySaleChancesByParams(SaleChanceQuery saleChanceQuery);
    public int addSaleChance(SaleChance saleChance);
    public int updateSaleChance(SaleChance saleChance);
    public int deleteSaleChance(int[] id);
    public SaleChance querySaleChancesById(int id);
}
