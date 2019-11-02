package com.mage.crm.dao;

import com.mage.crm.vo.DataDic;

import java.util.List;

public interface DataDicDao {
    public List<DataDic> queryDataDicValueByDataDicName(String dataDicName);
}
