package com.mage.crm.dto;

import com.mage.crm.vo.Module;

public class ModuleDto extends Module {
    private String ParentModuleName;

    public String getParentModuleName() {
        return ParentModuleName;
    }

    public void setParentModuleName(String parentModuleName) {
        ParentModuleName = parentModuleName;
    }
}
