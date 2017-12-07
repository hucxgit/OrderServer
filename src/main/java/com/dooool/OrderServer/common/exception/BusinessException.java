package com.dooool.OrderServer.common.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Liushanchao on 2016/4/28.
 */
@Data
public class BusinessException extends RuntimeException implements Serializable {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
    //异常提示消息编号
    @Setter(AccessLevel.NONE)
	private Integer status=2; // 默认异常状态值
    //异常提示消息内容
    private String message;
    // data数据
    private Object data;
    
    // module no
    @Setter(AccessLevel.NONE)
    private String moduleNo;
    // module name
    @Setter(AccessLevel.NONE)
    private String moduleName; 

    public BusinessException() {
    }

    public BusinessException(String message) {
    	this.message = message;
    }

    public BusinessException(String message, Object data) {
    	this.message = message;
    	this.data = data;
    }
    
    public BusinessException(IMessage msg){
    	this.status = msg.getStatus()==null?2:msg.getStatus();
    	this.message = msg.getMessage();
    	this.data = msg.getData();
    }
    
    public BusinessException wrapModule(ModuleEnum me) {
    	this.moduleNo = me.getModuleNo();
    	this.moduleName = me.getModuleName();
    	return this;
    }
    
    public BusinessException setStatus(Integer status) {
    	this.status = status;
    	return this;
    }

	@Override
	public synchronized Throwable fillInStackTrace() {
		// override null implementation,prevent fills in the execution stack trace
		return null;
	}
}
