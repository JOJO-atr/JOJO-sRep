package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CustomerLossDao;
import com.mage.crm.query.CustomerLossQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.EmptyUtil;
import com.mage.crm.vo.CustomerLoss;
import com.mage.crm.vo.Serve;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerLossService {
    @Resource
    private CustomerLossDao customerLossDao;
    public Map<String,Object> queryCustomerLossesByParams(CustomerLossQuery customerLossQuery) {
        PageHelper.startPage(customerLossQuery.getPage(),customerLossQuery.getRows());
        List<CustomerLoss> customerLosses=customerLossDao.queryCustomerLossesByParams(customerLossQuery);
        PageInfo<CustomerLoss> pageInfo = new PageInfo<>(customerLosses);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("rows",pageInfo.getList());
        return  hashMap;
    }

    public  Map<String,Object> queryCustomerLossByLossId(Integer lossId) {
        Map<String,Object> map=customerLossDao.queryCustomerLossByLossId(lossId);
        AssertUtil.isTrue(map==null,"操作失败!");
        return map;
    }


    public void updateCustomerLossState(int lossId,String lossReason) {
        AssertUtil.isTrue(EmptyUtil.isEmpty(lossReason),"流失原因不能为空");
        Map<String,Object> map = queryCustomerLossByLossId(lossId);
        AssertUtil.isTrue(map==null,"流失记录不存在");
        AssertUtil.isTrue(customerLossDao.updateCustomerLossState(lossId,lossReason) <1,"操作失败");
    }

    public Map<String,Object> queryServeType() {
        Map<String, Object> map = new HashMap<>();
        int code=300;
        List<Serve> list=customerLossDao.queryServeType();
        if(list.size()>0&&list!=null){
            Serve[] serve=new Serve[list.size()];
            for(int i=0;i<list.size();i++){
                serve[i]=list.get(i);
            }
            map.put("serve",serve);
            code=200;
        }
        map.put("code",code);
        return map;
    }
}
