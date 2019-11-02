package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModle;
import com.mage.crm.query.RoleQuery;
import com.mage.crm.service.RoleService;
import com.mage.crm.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;
    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Role> queryAllRoles(){
        return roleService.queryAllRoles();
    }
    @RequestMapping("index")
    public String index(){
        return "role";
    }
    @RequestMapping("queryRolesByParams")
    @ResponseBody
    public Map<String,Object> queryRolesByParams(RoleQuery roleQuery){
        return roleService.queryRolesByParams(roleQuery);
    }
    @RequestMapping("addRole")
    @ResponseBody
    public MessageModle addRole(Role role){
        roleService.addRole(role);
        return createMessage("添加成功!");
    }
    @RequestMapping("updateRole")
    @ResponseBody
    public MessageModle updateRole(Role role){
        roleService.updateRole(role);
        return createMessage("修改成功!");
    }
    @RequestMapping("deleteRole")
    @ResponseBody
    public MessageModle deleteRole(Integer id){
        roleService.deleteRole(id);
        return createMessage("删除成功!");
    }
}
