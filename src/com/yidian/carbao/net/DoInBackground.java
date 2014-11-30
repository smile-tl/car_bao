package com.yidian.carbao.net;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONException;

/**
 * �첽��������ݻص�
 * 
 * @author Administrator
 *
 */
public interface DoInBackground {
	/**
	 * ��ȡ��Ҫ���ݵĲ����ķ�װ
	 * 
	 * @return ���ط�װ����
	 * @throws UnsupportedEncodingException
	 */
	MultipartEntity getMultipart() throws UnsupportedEncodingException;

	/**
	 * �����ص������json��������ɺ󷵻�
	 * 
	 * @param temp
	 *            װ��������Ķ���
	 * @param json
	 *            �����json
	 * @return ���ؽ���֮��ķ�װ
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JSONException
	 */
	EntityJsonParse parseJson(EntityJsonParse temp, String json)
			throws NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException, JSONException;
}