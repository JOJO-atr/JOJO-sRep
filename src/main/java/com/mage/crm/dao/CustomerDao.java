package com.mage.crm.dao;

import com.mage.crm.query.CustomerContributeQuery;
import com.mage.crm.query.CustomerQuery;
import com.mage.crm.vo.Customer;
import com.mage.crm.vo.CustomerContribute;
import com.mage.crm.vo.CustomerGc;
import com.mage.crm.vo.CustomerLoss;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CustomerDao {
    @Select("select name from t_customer where is_valid =1 and state=0")
    public List<Customer> queryAllCustomers();

    public List<Customer> queryCustomersByParams(CustomerQuery customerQuery);

    public int addCustomer(Customer customer);

    public int updateCustomer(Customer customer);

    public int deleteCustomer(@Param("id") int[] id);

    public Customer queryCustomerById(Integer id);

    List<CustomerLoss> queryCustomerLoss();

    int updateCustomerById(List id);

    List<Integer> queryCustomerLossId();

    List<CustomerContribute> queryCustomersContribution(CustomerContributeQuery customerContributeQuery);

    List<CustomerGc> queryCustomerGc();
}
