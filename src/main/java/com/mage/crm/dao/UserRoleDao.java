package com.mage.crm.dao;

import com.mage.crm.vo.UserRole;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface UserRoleDao {

    int insertBatch(List<UserRole> userRoles);

    int deleteUserRoleByUserId(String userId);

    int queryRoleCountsByUserId(String id);

    int queryRoleCountsByRoleId(Integer id);

    int deleteUserRoleByRoleId(Integer id);
}
