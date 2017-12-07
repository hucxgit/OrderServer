package com.dooool.OrderServer.common.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.jdbc.BadSqlGrammarException;

import java.io.Serializable;

/**
 * Created by thinkpad on 2016/5/4.
 */
@Data
public class SystemException extends RuntimeException implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
    //异常提示消息编号
    @Setter(AccessLevel.NONE)
	private Integer status=3; // 默认异常状态值
	private String messageNo;
    private String message;
    // module no
    @Setter(AccessLevel.NONE)
    private String moduleNo;
    // module name
    @Setter(AccessLevel.NONE)
    private String moduleName;

    public SystemException() {
    }
    
    public SystemException(String message) {
    	this.messageNo="000";
    	this.message=message;
    }

    public SystemException(Exception e) {
        super(e);
        this.messageNo = genExceptionNo(e);
        this.message = e.toString();
        this.setStackTrace(e.getStackTrace());
    }
    
    public SystemException(String message, Exception e) {
    	this.messageNo = genExceptionNo(e);
    	this.message = message;
        this.setStackTrace(e.getStackTrace());
    }
    
    public SystemException wrapModule(ModuleEnum me) {
    	this.moduleNo = me.getModuleNo();
    	this.moduleName = me.getModuleName();
    	return this;
    }
    
    public SystemException setStatus(Integer status) {
    	this.status = status;
    	return this;
    }

    public String genExceptionNo(Exception e) {
        if (e instanceof NullPointerException) {
            return "001";
        } else if (e instanceof java.net.SocketTimeoutException) {
            return "002";
//        } else if (e instanceof org.apache.commons.httpclient.ConnectTimeoutException) {
//            return "003";
//        } else if (e instanceof com.alibaba.dubbo.rpc.RpcException) {
//            return "004";
        } else if (e instanceof java.sql.SQLException) {
            return "005";
        } else if (e instanceof java.io.FileNotFoundException) {
            return "006";
        } else if (e instanceof java.io.IOException) {
            return "007";
        } else if (e instanceof ClassCastException) {
            return "008";
        } else if (e instanceof NumberFormatException) {
            return "009";
        } else if (e instanceof ClassNotFoundException) {
            return "010";
        } else if (e instanceof ArrayIndexOutOfBoundsException) {
            return "011";
        } else if (e instanceof IndexOutOfBoundsException) {
            return "012";
        } else if (e instanceof NegativeArraySizeException) {
            return "013";
        } else if (e instanceof StringIndexOutOfBoundsException) {
            return "014";
        } else if (e instanceof ArrayStoreException) {
            return "015";
        } else if (e instanceof CloneNotSupportedException) {
            return "016";
        } else if (e instanceof java.io.EOFException) {
            return "017";
        } else if (e instanceof org.mybatis.spring.MyBatisSystemException) {
            return "018";
        } else if (e instanceof org.springframework.dao.DeadlockLoserDataAccessException) {
            return "019";
        } else if (e instanceof NoSuchFieldException) {
            return "020";
        } else if (e instanceof NoSuchMethodException) {
            return "021";
        } else if (e instanceof TypeNotPresentException) {
            return "022";
        } else if (e instanceof InterruptedException) {
            return "023";
        } else if(e instanceof BadSqlGrammarException){
            return "024";
        } else if(e instanceof RuntimeException) {
            return "997";
        } else if(e instanceof Exception){
            return "998";
        } else {
            return "999";
        }
    }
}
