package com.my.zx.net;

/**
 * 结果类
 *
 */
public class ResultObject {
	private Boolean success;
    private String message;
    private String username;
    private int code;
    private int total;
    private Object object;
    
    public ResultObject() {
    	super();
    }
    public ResultObject(String message, int code) {
    	super();
    	this.message = message;
    	this.code = code;
    }
    public ResultObject(String message,Boolean success) {
    	super();
    	this.message = message;
    	this.success = success;
    }
    
    public Boolean getSuccess() {
        return success;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getObject() {
        return object;
    }
    public void setObject(Object object) {
        this.object = object;
    }
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
