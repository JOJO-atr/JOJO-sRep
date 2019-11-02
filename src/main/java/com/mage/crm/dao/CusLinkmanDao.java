package com.mage.crm.dao;

import com.mage.crm.query.CusLinkmanQuery;
import com.mage.crm.vo.CusLinkman;

import java.util.List;

public interface CusLinkmanDao {
    public List<CusLinkman> queryCusLinkman(CusLinkmanQuery cusLinkmanQuery);

    public int addCusLinkman(CusLinkman cusLinkman);

    public int updateCusLinkman(CusLinkman cusLinkman);
}
