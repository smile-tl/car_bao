package com.yidian.carbao.net;

/**
 * json解析完成返回的封装
 * 
 * @author Administrator
 *
 */
public class EntityJsonParse {
	public final static int STATUS_CODE_OK = 0;
	public final static int STATUS_CODE_FAIL = -1;

	private Object result;
	private int Code = -1;
	private String msg;

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}