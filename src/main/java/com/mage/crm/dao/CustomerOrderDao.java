package com.mage.crm.dao;

import com.mage.crm.query.CustomerOrderQuery;
import com.mage.crm.vo.CustomerOrder;

import java.util.List;

public interface CustomerOrderDao {
    public List<CustomerOrder> queryOrdersByCid(CustomerOrderQuery customerOrderQuery);

    public CustomerOrder queryOrderInfoById(Integer id);
}
