package com.dooool.OrderServer.common.exception;

/**
 * Created by thinkpad on 2016/4/28.
 */
public enum ModuleEnum {

    Default("000","Default"),

    OrderServer("001", "OrderServer");

    private String moduleNo;
    private String moduleName;

    private ModuleEnum(String moduleNo, String moduleName) {
        this.moduleNo = moduleNo;
        this.moduleName = moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleNo() {
        return moduleNo;
    }
    
    public void setModuleNo(String moduleNo) {
    	this.moduleNo = moduleNo;
    }

    public static ModuleEnum getModuleByNo(String moduleNo) {
        ModuleEnum retEnum = null;
        for (ModuleEnum module : ModuleEnum.values()) {
            if (module.getModuleNo().equals(moduleNo)) {
                retEnum = module;
            }
        }
        if(retEnum==null) {
        	retEnum = ModuleEnum.Default;
        	retEnum.setModuleNo(moduleNo);
        }
        return retEnum;
    }

    public static ModuleEnum getDefault(String moduleName){
        ModuleEnum.Default.setModuleName(moduleName);
        return ModuleEnum.Default;
    }

    @Override   
	public String toString() {
		return this.name()+":"+this.getModuleNo()+"-"+this.getModuleName();
	}
}
