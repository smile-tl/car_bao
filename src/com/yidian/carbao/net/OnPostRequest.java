package com.yidian.carbao.net;

/**
 * post�����ui��ص�
 * 
 * @author Administrator
 *
 * @param <T>
 */
public interface OnPostRequest<T> {
	/**
	 * ����ص���ui
	 * 
	 * @param temp
	 */
	void onOk(T temp);

	/**
	 * ����ʧ�ܵĻص�
	 * 
	 * @param msg
	 */
	void onFail(String msg);
}