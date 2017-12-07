package com.dooool.OrderServer.common.exception;

/**
 * Created by thinkpad on 2016/5/4.
 */
public interface IMessage {
    public String getMessage();
    public Object getData();
    public Integer getStatus();
}
