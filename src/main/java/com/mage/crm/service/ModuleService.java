package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.ModuleDao;
import com.mage.crm.dao.PermissionDao;
import com.mage.crm.dto.ModuleDto;
import com.mage.crm.query.ModuleQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.EmptyUtil;
import com.mage.crm.vo.Module;
import com.mage.crm.vo.Tree;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ModuleService {
    @Resource
    private ModuleDao moduleDao;
    @Resource
    private PermissionDao permissionDao;
    public List<Tree> queryAllModules(Integer rid) {
        List<Tree> trees=moduleDao.queryAllModules();
        //查询该角色拥有的权限
        List<Integer> checks=permissionDao.queryCheckedModuleByRid(rid);
        for(int i=0;i<trees.size();i++){
            trees.get(i).setChecked(false);
            if(checks.contains(trees.get(i).getId())){
                trees.get(i).setChecked(true);
            }
        }
        return trees;
    }

    public Map<String,Object> queryModulesByParams(ModuleQuery moduleQuery) {
        PageHelper.startPage(moduleQuery.getPage(),moduleQuery.getRows());
        List<ModuleDto> moduleDtos=moduleDao.queryModulesByParams(moduleQuery);
        PageInfo<ModuleDto> pageInfo = new PageInfo<>(moduleDtos);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }

    public List<Module> queryModuleByGrade(Integer grade) {
        return moduleDao.queryModuleByGrade(grade   );
    }

    public void addModule(Module module) {
        checkIsEmpty(module.getModuleName(),module.getUrl(),module.getModuleStyle(),module.getOptValue(),module.getGrade());
        List<Module> modules=moduleDao.queryModuleByModuleName(module.getModuleName());
        for(Module m:modules){
            AssertUtil.isTrue(m!=null&&m.getOptValue()==module.getOptValue(),"模块名称已存在!");
        }
            AssertUtil.isTrue(moduleDao.queryModuleByOptValue(module.getOptValue())!=null,"权限值重复!");
            if(module.getGrade()!=0){
            //通过pid查出optValue
            String parentOptValue=moduleDao.queryPOptValueByPid(module.getParentId());
            module.setParentOptValue(parentOptValue);
        }
        module.setIsValid(1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(moduleDao.addModule(module)<1,"添加失败!");
    }

    private void checkIsEmpty(String moduleName,String url,String moduleStyle,String optValue,Integer grade) {
        AssertUtil.isTrue(EmptyUtil.isEmpty(moduleName),"模块名称不能为空!");
        AssertUtil.isTrue(EmptyUtil.isEmpty(url),"url不能为空!");
        AssertUtil.isTrue(EmptyUtil.isEmpty(moduleStyle),"模块样式不能为空!");
        AssertUtil.isTrue(EmptyUtil.isEmpty(optValue),"权限值不能为空!");
        AssertUtil.isTrue(grade==null,"请选择层级!");
    }

    public void updateModule(Module module) {
        checkIsEmpty(module.getModuleName(),module.getUrl(),module.getModuleStyle(),module.getOptValue(),module.getGrade());
        List<Module> modules=moduleDao.queryModuleByModuleName(module.getModuleName());
        if(modules!=null&&modules.size()>0){
            for(Module m:modules){
                AssertUtil.isTrue(m.getId()!=module.getId()&&m.getOptValue()==module.getOptValue(),"模块名称已存在!");
            }
        }

        Module m1=moduleDao.queryModuleByOptValue(module.getOptValue());
        AssertUtil.isTrue(m1!=null&&m1.getId()!=module.getId(),"权限值重复!");
        if(module.getGrade()!=0){
            //通过pid查出optValue
            String parentOptValue=moduleDao.queryPOptValueByPid(module.getParentId());
            module.setParentOptValue(parentOptValue);
        }
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(moduleDao.updateModule(module)<1,"修改失败!");
    }

    public void deleteModule(Integer id) {
        AssertUtil.isTrue(moduleDao.queryModuleById(id)==null||null==id,"待删除记录不存在！");
        List<Integer> deletes = new ArrayList<>();
        deletes=deletes(id,deletes);
        AssertUtil.isTrue(moduleDao.deleteModule(deletes)<deletes.size(),"删除失败!");
        //查询permission中的数量
        int count=permissionDao.queryPermissionByMid(deletes);
        if(count>0){
            //批量删除
            AssertUtil.isTrue(permissionDao.deleteBatch(deletes)<count,"删除失败!");
        }
    }

    private List<Integer> deletes(Integer pid,List<Integer> deletes) {
        Module module=moduleDao.queryModuleById(pid);
        if(module!=null){
            deletes.add(pid);
            //查询到所有子模块
            List<Module> modules=moduleDao.queryModuleByPId(pid);
           if(modules.size()>0&&modules!=null){
               for(Module m:modules){
                   deletes=deletes(m.getId(),deletes);
               }
           }
        }
        return deletes;
    }
}
