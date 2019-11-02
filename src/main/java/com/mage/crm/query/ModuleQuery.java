package com.mage.crm.query;

public class ModuleQuery extends BaseQuery {
    private String moduleName;
    private Integer optValue;
    private String  parentModuleName;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getOptValue() {
        return optValue;
    }

    public void setOptValue(Integer optValue) {
        this.optValue = optValue;
    }

    public String getParentModuleName() {
        return parentModuleName;
    }

    public void setParentModuleName(String parentModuleName) {
        this.parentModuleName = parentModuleName;
    }
}
