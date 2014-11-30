package com.yidian.carbao.net;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONException;

/**
 * 异步请求的数据回调
 * 
 * @author Administrator
 *
 */
public interface DoInBackground {
	/**
	 * 获取需要传递的参数的封装
	 * 
	 * @return 返回封装对象
	 * @throws UnsupportedEncodingException
	 */
	MultipartEntity getMultipart() throws UnsupportedEncodingException;

	/**
	 * 解析回调请求的json，解析完成后返回
	 * 
	 * @param temp
	 *            装解析结果的对象
	 * @param json
	 *            请求的json
	 * @return 返回解析之后的封装
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JSONException
	 */
	EntityJsonParse parseJson(EntityJsonParse temp, String json)
			throws NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException, JSONException;
}