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
	 * post�����첽����
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
			// ȡ����Ҫ�����url
			String url = (String) params[0];
			// ����T����
			EntityJsonParse temp = new EntityJsonParse();
			// ���������
			try {
				EntityHttpResponse entityHttpResponse = Post(url,
						doInBackground.getMultipart());
				// �ж��Ƿ��Ѿ�ȡ��������,���ȡ���ˣ��򲻻����json
				if (!isCancelled()) {
					if (entityHttpResponse.StatusCode == 200) {
						temp = doInBackground.parseJson(temp,
								entityHttpResponse.getMessage());
					} else {
						temp.setMsg("�ӿ�����ʧ��");
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
			Log.d("AsyncPost", "������ȡ��");
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
	 * ����װ
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
	 * post���󷽷�
	 * 
	 * @param url
	 *            ����url
	 * @param multipartEntity
	 *            ����װ
	 * @return ����һ���������
	 * @throws ParseException
	 *             �쳣
	 * @throws IOException
	 *             �쳣
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