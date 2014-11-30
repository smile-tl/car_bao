package com.yidian.carbao.net;

/**
 * post请求的ui层回调
 * 
 * @author Administrator
 *
 * @param <T>
 */
public interface OnPostRequest<T> {
	/**
	 * 请求回调到ui
	 * 
	 * @param temp
	 */
	void onOk(T temp);

	/**
	 * 请求失败的回调
	 * 
	 * @param msg
	 */
	void onFail(String msg);
}