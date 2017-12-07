/**
 * Function： TODO
 * <p>
 * ver     date      	author
 * ──────────────────────────────────
 * 2015年12月28日     Yang'ushan
 * <p>
 * Copyright (c) 2015, Lifang All Rights Reserved.
 */

package com.dooool.OrderServer.common.wkeyes;

import com.dooool.OrderServer.common.response.Response;

import com.dooool.OrderServer.common.exception.BusinessException;
import com.dooool.OrderServer.common.exception.SystemException;
import com.dooool.OrderServer.common.exception.ModuleEnum;




import com.dooool.OrderServer.common.utils.PropertyUtils;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import com.google.common.base.Strings;



/**
 * AOP 信息 方法执行顺序为： 1、AroundMethod 执行一半 2、beforeMethod 3、AroundMethod 执行另一半
 * 4、afterMethod
 *
 * @author Yang'ushan
 * @author ck
 * @Date 2015年12月28日 下午12:04:54
 * @see
 */
@Aspect
@Data
public class MonitorAOP {
    /**
     * 监控模式<br>
     * 1：CatFilter<br>
     * 2：AOP-Controller
     */
    private int monitoringMode = 2;

    private String moduleNo;

    private int recordMode = 1;

    private String isShowErrNo;

    public MonitorAOP() {
    }

    public MonitorAOP(int monitoringMode) {
        this();
        this.monitoringMode = monitoringMode;
    }


    /**
     * 功能描述:对controller层的拦截，统计业务访问数量
     * <p/>
     * <p>
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     CK:   2016年2月23日      新建
     * </pre>
     *
     * @throws Throwable
     */
    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object controllerArround(ProceedingJoinPoint point) throws Throwable {
        // 获取代理方法的参数Class
        Object result = null;
        moduleNo = PropertyUtils.getProperty("moduleNo", "000");
        isShowErrNo = PropertyUtils.getProperty("isShowErrNo", "0");

        try {
            // 执行业务代码
            result = point.proceed();
        } catch (Exception e) {
            result = handlerException( e);
        } finally {
            //t.complete();
        }
        return result;
    }

    private Object handlerException(Exception e) {

        // 统一返回json,由前端处理
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            be.getMessage();
            if(be.getModuleNo() == null) {
                be.wrapModule(ModuleEnum.getModuleByNo(moduleNo));
            }
            return  new Response(be.getMessage(), be.getStatus(), be.getData());
        } else {
            SystemException se = e instanceof SystemException ? (SystemException)e : new SystemException(e);
            if (se.getModuleNo() == null) {
                se.wrapModule(ModuleEnum.getModuleByNo(moduleNo));
            }
            String message = "服务器内部错误，请稍后重试";
            return new Response(concatErrNo(message, se.getModuleNo(), se.getMessageNo()), se.getStatus());
        }

    }

    private String concatErrNo(String msg, String moduleNo, String status) {
        if (isShowErrNo != "0" && (!Strings.isNullOrEmpty(moduleNo) || !Strings.isNullOrEmpty(status))) {
            msg += "(" + moduleNo + "-" + status + ")";
        }
        return msg;
    }
}
