package com.mage.crm.service;

import com.mage.crm.dao.PermissionDao;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PermissionService {
    @Resource
    private PermissionDao permissionDao;
    public void addPermission(Integer rid, int[] moduleIds) {
        //先删除
        //查询是否有权限
        int count=permissionDao.queryPermissionByRid(rid);
        if(count>0){
            AssertUtil.isTrue(permissionDao.deletePermission(rid)<count,"授权失败!");
        }
        List<Permission> list=new ArrayList<>();
        //再添加
       for(int i=0;i<moduleIds.length;i++){
           Permission permission = new Permission();
           //查询aclValue
          String aclValue=permissionDao.queryAcValueByMid(moduleIds[i]);
          permission.setAclValue(aclValue);
          permission.setRoleId(rid);
          permission.setModuleId(moduleIds[i]);
          permission.setCreateDate(new Date());
          permission.setUpdateDate(new Date());
          list.add(permission);
       }
       AssertUtil.isTrue(permissionDao.insertBatch(list)<list.size(),"授权失败!");
}
}
