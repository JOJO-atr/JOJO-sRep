package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModle;
import com.mage.crm.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@RequestMapping("permission")
@Controller
public class PermissionConreoller extends BaseController {
    @Resource
    private PermissionService permissionService;
    @RequestMapping("addPermission")
    @ResponseBody
    public MessageModle addPermission(Integer rid,int[] moduleIds){
        permissionService.addPermission(rid,moduleIds);
        return createMessage("授权成功!");
    }
}
