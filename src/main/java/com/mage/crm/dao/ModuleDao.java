package com.mage.crm.dao;

import com.mage.crm.dto.ModuleDto;
import com.mage.crm.query.ModuleQuery;
import com.mage.crm.vo.Module;
import com.mage.crm.vo.Tree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleDao {
    List<Tree> queryAllModules();

    List<ModuleDto> queryModulesByParams(ModuleQuery moduleQuery);

    List<Module> queryModuleByGrade(Integer grade);

    String queryPOptValueByPid(Integer parentId);

    int addModule(Module module);

    List<Module> queryModuleByModuleName(@Param("moduleName") String moduleName);

    Module queryModuleByOptValue(String optValue);

    int updateModule(Module module);

    Module queryModuleById(Integer pid);

    List<Module> queryModuleByPId(Integer pid);

    int deleteModule(List<Integer> deletes);
}
