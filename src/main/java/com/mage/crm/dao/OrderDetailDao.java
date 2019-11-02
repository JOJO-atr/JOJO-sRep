package com.mage.crm.dao;

import com.mage.crm.query.OrderDetailQuery;
import com.mage.crm.vo.OrderDetail;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderDetailDao {
    @Select("select id,goods_Name 'goodsName',goods_Num 'goodsNum',unit,price,sum from t_order_details where order_id=#{orderId}")
    public List<OrderDetail> queryOrderDetailsByOrderId(OrderDetailQuery orderDetailQuery);
}
