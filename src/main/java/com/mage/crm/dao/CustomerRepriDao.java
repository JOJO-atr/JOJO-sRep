package com.mage.crm.dao;

import com.mage.crm.query.CustomerReprieveQuery;
import com.mage.crm.vo.CustomerReprieve;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface CustomerRepriDao {
    List<CustomerReprieve> customerReprieveByLossId(CustomerReprieveQuery customerReprieveQuery);

    int insertReprive(CustomerReprieve customerReprieve);

    int updateReprive(CustomerReprieve customerReprieve);

    int deleteReprive(Integer id);
}
