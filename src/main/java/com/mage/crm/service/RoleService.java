package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.RoleDao;
import com.mage.crm.dao.UserRoleDao;
import com.mage.crm.query.RoleQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.EmptyUtil;
import com.mage.crm.vo.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {
    @Resource
    private RoleDao roleDao;
    @Resource
    private UserRoleDao userRoleDao;
    public List<Role> queryAllRoles() {
        return roleDao.queryAllRoles();
    }

    public  Map<String,Object> queryRolesByParams(RoleQuery roleQuery) {
        PageHelper.startPage(roleQuery.getPage(),roleQuery.getRows());
        List<Role> roles=roleDao.queryRolesByParams(roleQuery);
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("rows",pageInfo.getList());
        return hashMap;
    }

    public void addRole(Role role) {
        AssertUtil.isTrue(EmptyUtil.isEmpty(role.getRoleName()),"角色名称不能为空!");
        AssertUtil.isTrue(EmptyUtil.isEmpty(role.getRoleRemark()),"角色备注不能为空!");
        AssertUtil.isTrue(roleDao.queryRoleByRoleName(role.getRoleName())!=null,"已拥有该角色!");
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleDao.addRole(role)<1,"添加失败!");
    }

    public void updateRole(Role role) {
        AssertUtil.isTrue(EmptyUtil.isEmpty(role.getRoleName()),"角色名称不能为空!");
        AssertUtil.isTrue(EmptyUtil.isEmpty(role.getRoleRemark()),"角色备注不能为空!");
        Role r=roleDao.queryRoleById(role.getId());
        AssertUtil.isTrue(r!=null&!role.getId().equals(r.getId()),"角色名称已存在!");
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleDao.updateRole(role)<1,"修改失败!");
    }

    public void deleteRole(Integer id) {
        AssertUtil.isTrue(roleDao.deleteRole(id)<1,"删除失败!");
        int i=userRoleDao.queryRoleCountsByRoleId(id);
        if(i>0){
            AssertUtil.isTrue(userRoleDao.deleteUserRoleByRoleId(id)<i,"用户删除失败!");
        }

    }
}
