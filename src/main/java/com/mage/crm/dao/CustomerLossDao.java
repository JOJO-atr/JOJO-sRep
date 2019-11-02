package com.mage.crm.dao;

import com.mage.crm.query.CustomerLossQuery;
import com.mage.crm.vo.CustomerLoss;
import com.mage.crm.vo.Serve;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface CustomerLossDao {
    List<CustomerLoss> queryCustomerLossesByParams(CustomerLossQuery customerLossQuery);

    int insertBatch(List<CustomerLoss> customerLosss);

    Map<String,Object> queryCustomerLossByLossId(Integer lossId);
    @Update("update t_customer_loss set state=1 , confirm_loss_time = now(), loss_reason = #{lossReason} where id = #{lossId} ")
    int updateCustomerLossState(@Param("lossId") int lossId, @Param("lossReason") String lossReason);
    @Select("select serve_type 'name',count(serve_type) 'value' from t_customer_serve GROUP BY serve_type")
    List<Serve> queryServeType();
}
