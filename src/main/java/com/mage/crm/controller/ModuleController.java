package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModle;
import com.mage.crm.query.ModuleQuery;
import com.mage.crm.service.ModuleService;
import com.mage.crm.vo.Module;
import com.mage.crm.vo.Tree;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {
    @Resource
    private ModuleService moduleService;
    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<Tree> queryAllModules(Integer rid){
        return moduleService.queryAllModules(rid);
    }
    @RequestMapping("index")
    public String index(){
        return "module";
    }
    @RequestMapping("queryModulesByParams")
    @ResponseBody
    public Map<String,Object> queryModulesByParams(ModuleQuery moduleQuery){
        return moduleService.queryModulesByParams(moduleQuery);
    }
    @RequestMapping("queryModuleByGrade")
    @ResponseBody
    public List<Module> queryModuleByGrade(Integer grade){
        return moduleService.queryModuleByGrade(grade);
    }
    @RequestMapping("addModule")
    @ResponseBody
    public MessageModle addModule(Module module){
        moduleService.addModule(module);
        return createMessage("添加成功!");
    }
    @RequestMapping("updateModule")
    @ResponseBody
    public MessageModle updateModule(Module module){
        moduleService.updateModule(module);
        return createMessage("修改成功!");
    }
    @RequestMapping("deleteModule")
    @ResponseBody
    public MessageModle deleteModule(Integer id){
        moduleService.deleteModule(id);
        return createMessage("删除成功!");
    }
}
