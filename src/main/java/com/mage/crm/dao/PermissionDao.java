package com.mage.crm.dao;

import com.mage.crm.vo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionDao {
    List<Integer> queryCheckedModuleByRid(Integer rid);

    int queryPermissionByRid(Integer rid);

    int deletePermission(Integer rid);

    String queryAcValueByMid(@Param("moduleId") int moduleId);

    int insertBatch(List<Permission> list);

    List<String> queryPermissionByUid(String id);

    int queryPermissionByMid(List<Integer> deletes);

    int deleteBatch(List<Integer> deletes);
}
