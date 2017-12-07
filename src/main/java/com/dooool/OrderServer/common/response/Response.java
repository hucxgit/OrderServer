package com.dooool.OrderServer.common.response;

import lombok.ToString;

import java.io.Serializable;


@ToString
public class Response<T> implements Serializable {
	private String message;
	private Integer status;
	private T data;
	
	
	public Response() {
	}
	
	
	
	public Response(String message, Integer status, T data) {
		super();
		this.message = message;
		this.status = status;
		this.data = data;
	}




	public Response(String message, Integer status) {
		super();
		this.message = message;
		this.status = status;
	}


	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
	
}
