package com.yidian.carbao.net;

/**
 * postÇëÇó·µ»Ø·â×°
 * 
 * @author Administrator
 *
 */
public class EntityHttpResponse {
	public static final int STATUS_CODE_OK = 200;

	public int StatusCode = 0;
	public String Message = "";

	public int getStatusCode() {
		return StatusCode;
	}

	public void setStatusCode(int statusCode) {
		StatusCode = statusCode;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}
}