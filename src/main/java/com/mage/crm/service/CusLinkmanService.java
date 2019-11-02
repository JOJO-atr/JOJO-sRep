package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CusLinkmanDao;
import com.mage.crm.query.CusLinkmanQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.CusLinkman;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CusLinkmanService {
    @Resource
    private CusLinkmanDao cusLinkmanDao;
    public Map<String,Object> queryCusLinkman(CusLinkmanQuery cusLinkmanQuery) {
        PageHelper.startPage(cusLinkmanQuery.getPage(),cusLinkmanQuery.getRows());
        List<CusLinkman> cusLinkmens = cusLinkmanDao.queryCusLinkman(cusLinkmanQuery);
        PageInfo<CusLinkman> pageInfo = new PageInfo<>(cusLinkmens);
        Map<String, Object> map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }

    public void addCusLinkman(CusLinkman cusLinkman) {
        cusLinkman.setCreateDate(new Date());
        cusLinkman.setIsValid(1);
        int i = cusLinkmanDao.addCusLinkman(cusLinkman);
        AssertUtil.isTrue(i<1,"添加失败!");
    }

    public void updateCusLinkman(CusLinkman cusLinkman) {
        int i = cusLinkmanDao.updateCusLinkman(cusLinkman);
        AssertUtil.isTrue(i<1,"修改失败!");
    }
}
