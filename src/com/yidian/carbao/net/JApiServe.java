package com.yidian.carbao.net;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.os.AsyncTask;
import android.util.Log;

public class JApiServe {
	/**
	 * post方法异步请求
	 * 
	 * @author Administrator
	 *
	 * @param <T>
	 */
	@SuppressWarnings("unchecked")
	public class AsyncPost<T> extends
			AsyncTask<String, Integer, EntityJsonParse> {
		private OnPostRequest<T> onPostRequest;
		private DoInBackground doInBackground;

		public AsyncPost(OnPostRequest<T> onPostRequest,
				DoInBackground doInBackground) {
			this.onPostRequest = onPostRequest;
			this.doInBackground = doInBackground;
		}

		@Override
		protected EntityJsonParse doInBackground(String... params) {
			// 取出需要请求的url
			String url = (String) params[0];
			// 声明T类型
			EntityJsonParse temp = new EntityJsonParse();
			// 请求服务器
			try {
				EntityHttpResponse entityHttpResponse = Post(url,
						doInBackground.getMultipart());
				// 判断是否已经取消请求了,如果取消了，则不会解析json
				if (!isCancelled()) {
					if (entityHttpResponse.StatusCode == 200) {
						temp = doInBackground.parseJson(temp,
								entityHttpResponse.getMessage());
					} else {
						temp.setMsg("接口请求失败");
					}
				}
			} catch (ParseException e) {
				temp.setMsg(e.toString());
			} catch (IOException e) {
				temp.setMsg(e.toString());
			} catch (JSONException e) {
				temp.setMsg(e.toString());
			} catch (IllegalArgumentException e) {
				temp.setMsg(e.toString());
			} catch (IllegalAccessException e) {
				temp.setMsg(e.toString());
			} catch (NoSuchFieldException e) {
				temp.setMsg(e.toString());
			}
			return temp;
		}

		@Override
		protected void onCancelled() {
			Log.d("AsyncPost", "动作已取消");
		}

		@Override
		protected void onPostExecute(EntityJsonParse result) {
			if (null != onPostRequest) {
				if (EntityJsonParse.STATUS_CODE_OK == result.getCode()) {
					onPostRequest.onOk((T) result.getResult());
				} else if (EntityJsonParse.STATUS_CODE_FAIL == result.getCode()) {
					onPostRequest.onFail(result.getMsg());
				}
			}
		}
	}

	/**
	 * 表单封装
	 * 
	 * @author Administrator
	 *
	 */
	public class MultiPackaging {
		private MultipartEntity multipartEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);

		public MultipartEntity getMultipartEntity() {
			return multipartEntity;
		}

		public MultiPackaging MultipartFile(String key, File file) {
			multipartEntity.addPart(key, new FileBody(file));
			return this;
		}

		public MultiPackaging MultipartString(String key, String str)
				throws UnsupportedEncodingException {
			multipartEntity.addPart(key,
					new StringBody(str, Charset.forName("UTF-8")));
			return this;
		}

		public MultiPackaging MultipartImage(String key, File imgFile) {
			multipartEntity.addPart(key, new FileBody(imgFile, "image/jpeg"));
			return this;
		}
	}

	/**
	 * post请求方法
	 * 
	 * @param url
	 *            请求url
	 * @param multipartEntity
	 *            表单封装
	 * @return 返回一个结果对象
	 * @throws ParseException
	 *             异常
	 * @throws IOException
	 *             异常
	 */
	public EntityHttpResponse Post(String url, MultipartEntity multipartEntity)
			throws ParseException, IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(multipartEntity);
		HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
		EntityHttpResponse entityHttpResponse = new EntityHttpResponse();
		entityHttpResponse.setMessage(EntityUtils.toString(httpResponse
				.getEntity()));
		entityHttpResponse.setStatusCode(httpResponse.getStatusLine()
				.getStatusCode());
		return entityHttpResponse;
	}
}