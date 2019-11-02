package com.mage.crm.dao;

import com.mage.crm.query.RoleQuery;
import com.mage.crm.vo.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleDao {
    @Select("select id ,role_name 'roleName' from t_role where is_valid=1")
    List<Role> queryAllRoles();

    List<Role> queryRolesByParams(RoleQuery roleQuery);

    int addRole(Role role);

    Role queryRoleByRoleName(String roleName);

    Role queryRoleById(String id);

    int updateRole(Role role);

    int deleteRole(Integer id);
}
