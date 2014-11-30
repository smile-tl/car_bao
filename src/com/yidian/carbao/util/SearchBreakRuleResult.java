package com.yidian.carbao.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SearchBreakRuleResult {
	private final String citiesName [] ={"北京市","天津市","上海市","重庆市","河北省","陕西省","辽宁省",
			"吉林省","黑龙江省","江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省",
			"广东省","海南省","四川省","贵州省","云南省","陕西省","甘肃省","青海省","广西","内蒙古",
			"西藏","宁夏","新疆","香港","澳门","台湾"};
	private final String citiesEasy [] = {"BJ","TJ","SH","CQ","HB","SX","LN","JL","HLJ","JS","ZJ","AH",
			"FJ","JX","SD","HN","FB","HUN","GD","HAN","SC","GZ","YN","SX","GS","QH","GX","NMG",
			"XZ","NX","XJ","XG","AM","TW"};
	public static final String key = "97e059c8643faab2a3981abfa4a46ed8";
	public static final Map<String,String> cities = new HashMap<String, String>();
	protected static final int MSG_LOGIN_RESULT = 0;
	
	public void search(final String city, final String carNumber,final String engineNumber,final Handler mHandler) {
		
		for (int i = 0; i < citiesName.length; i++) {  
			cities.put(citiesName[i], citiesEasy[i]);
		}
		
		
		System.out.println("HEllo,,,,,,,,,,,,,,,,,");
		new Thread(new Runnable() {
			public void run() {
				System.out.println("HELLO!!!!Kitty.....");
				String uri = "http://v.juhe.cn/wz/query";
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(uri);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("city", city));//记得替换成city
				params.add(new BasicNameValuePair("hphm", carNumber));
				params.add(new BasicNameValuePair("engineno", engineNumber));
				params.add(new BasicNameValuePair("key", key));
				System.out.println("hphm~~~~~~~~~~"+carNumber);
				System.out.println("engineno~~~~~~~~~~~~"+engineNumber);
				HttpResponse httpResponse = null;
				try {
					httpPost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					httpResponse = httpClient.execute(httpPost);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						Log.d("yanghongbing", "network OK!");
						HttpEntity entity = httpResponse.getEntity();
						String entityString = EntityUtils.toString(entity);
						String jsonString = entityString.substring(entityString
								.indexOf("{"));
						Log.d("yanghongbing", "entity = " + jsonString);
						JSONObject json = new JSONObject(jsonString);
						sendMessage(MSG_LOGIN_RESULT, json,mHandler);
						Log.d("yanghongbing", "json = " + json);
					}
				} catch (Exception e) {

				}

			}
		}).start();
		
	}
	
	
	private void sendMessage(int what, Object obj,Handler mHandler) {
		Message msg = Message.obtain();
		msg.what = what;
		msg.obj = obj;
		mHandler.sendMessage(msg);
	}

}
