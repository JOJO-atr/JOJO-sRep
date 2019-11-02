package com.mage.crm.dao;


import com.mage.crm.dto.UserDto;
import com.mage.crm.query.UserQuery;
import com.mage.crm.vo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    public User queryUserByName(String userName);
    public int updatePwd(@Param("id") int id, @Param("newPwd") String newPwd);
    public User queryUserById(int id);
    public List<User> queryAllCustomerManager();
    List<UserDto> queryUsersByParams(UserQuery userQuery);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(Integer id);
}
