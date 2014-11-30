package com.yidian.carbao.net;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;

import com.yidian.carbao.entity.EntityProduct;
import com.yidian.carbao.entity.EntityUserInfo;
import com.yidian.carbao.entity.EntityUserList;

@SuppressWarnings("rawtypes")
@SuppressLint("UseSparseArrays")
public class JApi extends JApiServe {
	// ���������Ķ���
	private static JApi jApi;
	// �洢�첽�����map
	private static Map<Long, AsyncPost> AsyncMap;
	// �̳߳�
	private static ExecutorService executorService;
	// �������ͬһ�������ڵ�Ψһ��ʶ
	private static Map<String, ArrayList<Long>> FlagMap;
	// �����������ַ
	private final static String url = "http://115.28.86.228/car_treasure/Interface.php";

	/**
	 * �޲ι��죬��Ҫ����ʵ����һ���̳߳�
	 */
	public JApi() {
		executorService = Executors.newCachedThreadPool();
		FlagMap = new HashMap<String, ArrayList<Long>>();
		AsyncMap = new HashMap<Long, AsyncPost>();
	}

	/**
	 * �õ�japi��ʵ��,���ұ�֤����
	 * 
	 * @return ����japi������ʵ��
	 */
	public static JApi getInstance() {
		if (null == jApi) {
			jApi = new JApi();
		}
		return jApi;
	}

	/**
	 * ͨ��long���͵�flag��ȡ���첽����
	 * 
	 * @param ͬһ���������е�
	 *            Ψһ��ʶ
	 */
	public void AsyncCancel(String tag) {
		ArrayList<Long> cTags = FlagMap.get(tag);
		if (null != cTags) {
			for (int i = 0; i < cTags.size(); i++) {
				// ȡ���첽����
				AsyncMap.get(cTags.get(i)).cancel(true);
				// ��map���Ƴ�������߳�
				AsyncMap.remove(cTags.get(i));
			}
			// �ӱ��map���Ƴ�����һ����
			FlagMap.remove(tag);
		}
	}

	/**
	 * ע��
	 * 
	 * @param uname
	 *            �û���
	 * @param pwd
	 *            ����
	 * @param onPostRequest
	 *            �ص�
	 * @param tag
	 *            Ψһ���
	 */
	public void Register(final String uname, final String pwd,
			OnPostRequest<EntityUserInfo> onPostRequest, String tag) {
		AsyncPost<EntityUserInfo> asyncPost = new AsyncPost<EntityUserInfo>(
				onPostRequest, new DoInBackground() {
					@Override
					public EntityJsonParse parseJson(EntityJsonParse temp,
							String json) throws NoSuchFieldException,
							IllegalArgumentException, IllegalAccessException,
							JSONException {
						JSONObject jsonObject = new JSONObject(json);

						if (0 == jsonObject.getInt("s")) {
							EntityUserInfo entityUserInfo = new EntityUserInfo();
							entityUserInfo.setUid(jsonObject.getString("uid"));
							temp.setResult(entityUserInfo);
							temp.setCode(EntityJsonParse.STATUS_CODE_OK);
						} else {
							temp.setMsg(jsonObject.getString("e"));
							temp.setCode(EntityJsonParse.STATUS_CODE_FAIL);
						}
						return temp;
					}

					@Override
					public MultipartEntity getMultipart()
							throws UnsupportedEncodingException {
						MultiPackaging multiPackaging = new MultiPackaging();
						multiPackaging.MultipartString("api", "user_reg")
								.MultipartString("uname", uname)
								.MultipartString("pwd", pwd);
						return multiPackaging.getMultipartEntity();
					}
				});
		// ���Զ�����̳߳������У����������url
		asyncPost.executeOnExecutor(executorService, url);
		// ��ȡ��ǰϵͳʱ�䣬��ΪΨһlong���͵ı�ʶ
		Long cTag = System.currentTimeMillis();
		if (null == FlagMap.get(tag)) {
			ArrayList<Long> cTags = new ArrayList<Long>();
			cTags.add(cTag);
			FlagMap.put(tag, cTags);
		} else {
			FlagMap.get(tag).add(cTag);
		}
		// ������̳߳ؼ��뵽map��
		AsyncMap.put(cTag, asyncPost);
	}

	
	
	
	/**
	 * ��¼
	 * 
	 * @param uname
	 *            �û���
	 * @param pwd
	 *            ����
	 * @param onPostRequest
	 *            �ص�
	 * @param tag
	 *            Ψһ���
	 */
	public void Login(final String uname, final String pwd,
			OnPostRequest<EntityUserInfo> onPostRequest, String tag) {
		AsyncPost<EntityUserInfo> asyncPost = new AsyncPost<EntityUserInfo>(
				onPostRequest, new DoInBackground() {
					@Override
					public EntityJsonParse parseJson(EntityJsonParse temp,
							String json) throws NoSuchFieldException,
							IllegalArgumentException, IllegalAccessException,
							JSONException {
						JSONObject jsonObject = new JSONObject(json);

						if (0 == jsonObject.getInt("s")) {
							EntityUserInfo entityUserInfo = new EntityUserInfo();
							entityUserInfo.setUid(jsonObject.getString("uid"));
							temp.setResult(entityUserInfo);
							temp.setCode(EntityJsonParse.STATUS_CODE_OK);
						} else {
							temp.setMsg(jsonObject.getString("e"));
							temp.setCode(EntityJsonParse.STATUS_CODE_FAIL);
						}
						return temp;
					}

					@Override
					public MultipartEntity getMultipart()
							throws UnsupportedEncodingException {
						MultiPackaging multiPackaging = new MultiPackaging();
						multiPackaging.MultipartString("api", "user_login")
								.MultipartString("uname", uname)
								.MultipartString("pwd", pwd);
						return multiPackaging.getMultipartEntity();
					}
				});
		// ���Զ�����̳߳������У����������url
		asyncPost.executeOnExecutor(executorService, url);
		// ��ȡ��ǰϵͳʱ�䣬��ΪΨһlong���͵ı�ʶ
		Long cTag = System.currentTimeMillis();
		if (null == FlagMap.get(tag)) {
			ArrayList<Long> cTags = new ArrayList<Long>();
			cTags.add(cTag);
			FlagMap.put(tag, cTags);
		} else {
			FlagMap.get(tag).add(cTag);
		}
		// ������̳߳ؼ��뵽map��
		AsyncMap.put(cTag, asyncPost);
	}
	
	/**
	 * ��ȡ�û���Ϣ
	 * 
	 * @param uid
	 *            �û���uid
	 * @param onPostRequest
	 *            �ӿڻص�
	 * @param tag
	 */
	public void UserInfo(final String uid,
			OnPostRequest<EntityUserInfo> onPostRequest, String tag) {
		AsyncPost<EntityUserInfo> asyncPost = new AsyncPost<EntityUserInfo>(
				onPostRequest, new DoInBackground() {
					@Override
					public EntityJsonParse parseJson(EntityJsonParse temp,
							String json) throws NoSuchFieldException,
							IllegalArgumentException, IllegalAccessException,
							JSONException {
						JSONObject jsonObject = new JSONObject(json);

						if (0 == jsonObject.getInt("s")) {

							JSONObject jsonObjectChild = jsonObject
									.getJSONObject("data");
							EntityUserInfo entityUserInfo = new EntityUserInfo();
							entityUserInfo.setUid(jsonObjectChild
									.getString("uid"));
							entityUserInfo.setUname(jsonObjectChild
									.getString("uname"));
							entityUserInfo.setPdw(jsonObjectChild
									.getString("pwd"));
							entityUserInfo.setClasss(jsonObjectChild
									.getString("class"));
							entityUserInfo.setCname(jsonObjectChild
									.getString("cname"));
							entityUserInfo.setMark(jsonObjectChild
									.getString("mark"));
							entityUserInfo.setIntro(jsonObjectChild
									.getString("intro"));
							entityUserInfo.setProvince(jsonObjectChild
									.getString("province"));
							entityUserInfo.setAddress(jsonObjectChild
									.getString("address"));
							entityUserInfo.setWork_time(jsonObjectChild
									.getString("work_time"));
							entityUserInfo.setContact_p(jsonObjectChild
									.getString("contact_p"));
							entityUserInfo.setContact_phone(jsonObjectChild
									.getString("contact_phone"));
							entityUserInfo.setLongitude(jsonObjectChild
									.getString("long"));
							entityUserInfo.setLatitude(jsonObjectChild
									.getString("lat"));
							entityUserInfo.setReg_time(jsonObjectChild
									.getString("reg_time"));
							entityUserInfo.setLogin_time(jsonObjectChild
									.getString("login_time"));
							entityUserInfo.setStar(jsonObjectChild
									.getString("star"));
							entityUserInfo.setTop(jsonObjectChild
									.getString("top"));
							entityUserInfo.setTop_time(jsonObjectChild
									.getString("top_time"));
							temp.setResult(entityUserInfo);
							temp.setCode(EntityJsonParse.STATUS_CODE_OK);
						} else {
							temp.setMsg(jsonObject.getString("e"));
							temp.setCode(EntityJsonParse.STATUS_CODE_FAIL);
						}
						return temp;
					}

					@Override
					public MultipartEntity getMultipart()
							throws UnsupportedEncodingException {
						MultiPackaging multiPackaging = new MultiPackaging();
						multiPackaging.MultipartString("api", "user_info")
								.MultipartString("uid", uid);
						return multiPackaging.getMultipartEntity();
					}
				});
		// ���Զ�����̳߳������У����������url
		asyncPost.executeOnExecutor(executorService, url);
		// ��ȡ��ǰϵͳʱ�䣬��ΪΨһlong���͵ı�ʶ
		Long cTag = System.currentTimeMillis();
		if (null == FlagMap.get(tag)) {
			ArrayList<Long> cTags = new ArrayList<Long>();
			cTags.add(cTag);
			FlagMap.put(tag, cTags);
		} else {
			FlagMap.get(tag).add(cTag);
		}
		// ������̳߳ؼ��뵽map��
		AsyncMap.put(cTag, asyncPost);
	}

	/**�޸Ĺ�˾��Ϣ
	 * 
	 * @param entityUserInfo
	 *            �޸ĺ��ʵ��
	 * @param img
	 *            ��˾mark��ͼƬ����
	 * @param onPostRequest
	 *            �ӿڻص�
	 * @param tag
	 */
	public void updateUserInfo(final EntityUserInfo entityUserInfo,
			final File img, OnPostRequest<EntityUserInfo> onPostRequest,
			String tag) {
		AsyncPost<EntityUserInfo> asyncPost = new AsyncPost<EntityUserInfo>(
				onPostRequest, new DoInBackground() {
					@Override
					public EntityJsonParse parseJson(EntityJsonParse temp,
							String json) throws NoSuchFieldException,
							IllegalArgumentException, IllegalAccessException,
							JSONException {
						JSONObject jsonObject = new JSONObject(json);

						if (0 == jsonObject.getInt("s")) {

							temp.setCode(EntityJsonParse.STATUS_CODE_OK);
							temp.setResult(entityUserInfo);
						} else {
							temp.setMsg(jsonObject.getString("e"));
							temp.setCode(EntityJsonParse.STATUS_CODE_FAIL);
						}
						return temp;
					}

					@Override
					public MultipartEntity getMultipart()
							throws UnsupportedEncodingException {
						MultiPackaging multiPackaging = new MultiPackaging();
						multiPackaging
								.MultipartString("api", "user_update")
								.MultipartString("uid", entityUserInfo.getUid())
								.MultipartString("class",
										entityUserInfo.getClasss())
								.MultipartString("cname",
										entityUserInfo.getCname())
								.MultipartString("intro",
										entityUserInfo.getIntro())
								.MultipartString("province",
										entityUserInfo.getProvince())
								.MultipartString("address",
										entityUserInfo.getAddress())
								.MultipartString("work_time",
										entityUserInfo.getWork_time())
								.MultipartString("contact_p",
										entityUserInfo.getContact_p())
								.MultipartString("contact_phone",
										entityUserInfo.getContact_phone())
								.MultipartString("long",
										entityUserInfo.getLongitude())
								.MultipartString("lat",
										entityUserInfo.getLatitude())
								.MultipartImage("upfile", img);
						return multiPackaging.getMultipartEntity();
					}
				});
		// ���Զ�����̳߳������У����������url
		asyncPost.executeOnExecutor(executorService, url);
		// ��ȡ��ǰϵͳʱ�䣬��ΪΨһlong���͵ı�ʶ
		Long cTag = System.currentTimeMillis();
		if (null == FlagMap.get(tag)) {
			ArrayList<Long> cTags = new ArrayList<Long>();
			cTags.add(cTag);
			FlagMap.put(tag, cTags);
		} else {
			FlagMap.get(tag).add(cTag);
		}
		// ������̳߳ؼ��뵽map��
		AsyncMap.put(cTag, asyncPost);
	}

	/**
	 * ������Ʒ
	 * 
	 * @param uid
	 *            �û�id
	 * @param img
	 *            ��ƷͼƬ
	 * @param classes
	 *            ��Ʒ���
	 * @param gname
	 *            ��Ʒ����
	 * @param brand
	 *            Ʒ��
	 * @param price
	 *            �۸�
	 * @param standard
	 *            ���
	 * @param producing
	 *            ����
	 * @param introduce
	 *            ����
	 * @param onPostRequest
	 * @param tag
	 */
	public void publishProduct(final String uid, final File img,
			final String classes, final String gname, final String brand,
			final String price, final String standard, final String producing,
			final String introduce, OnPostRequest<EntityProduct> onPostRequest,
			String tag) {
		AsyncPost<EntityProduct> asyncPost = new AsyncPost<EntityProduct>(
				onPostRequest, new DoInBackground() {
					@Override
					public EntityJsonParse parseJson(EntityJsonParse temp,
							String json) throws NoSuchFieldException,
							IllegalArgumentException, IllegalAccessException,
							JSONException {
						JSONObject jsonObject = new JSONObject(json);

						if (0 == jsonObject.getInt("s")) {

							temp.setCode(EntityJsonParse.STATUS_CODE_OK);
						} else {
							temp.setMsg(jsonObject.getString("e"));
							temp.setCode(EntityJsonParse.STATUS_CODE_FAIL);
						}
						return temp;
					}

					@Override
					public MultipartEntity getMultipart()
							throws UnsupportedEncodingException {
						MultiPackaging multiPackaging = new MultiPackaging();
						multiPackaging.MultipartString("api", "insert_goods")
								.MultipartString("uid", uid)
								.MultipartImage("upfile", img)
								.MultipartString("class", classes)
								.MultipartString("gname", gname)
								.MultipartString("brand", brand)
								.MultipartString("price", price)
								.MultipartString("standard", standard)
								.MultipartString("producing", producing)
								.MultipartString("introduce", introduce);
						return multiPackaging.getMultipartEntity();
					}
				});
		// ���Զ�����̳߳������У����������url
		asyncPost.executeOnExecutor(executorService, url);
		// ��ȡ��ǰϵͳʱ�䣬��ΪΨһlong���͵ı�ʶ
		Long cTag = System.currentTimeMillis();
		if (null == FlagMap.get(tag)) {
			ArrayList<Long> cTags = new ArrayList<Long>();
			cTags.add(cTag);
			FlagMap.put(tag, cTags);
		} else {
			FlagMap.get(tag).add(cTag);
		}
		// ������̳߳ؼ��뵽map��
		AsyncMap.put(cTag, asyncPost);

	}

	/**
	 * �޸Ĳ�Ʒ
	 * 
	 * @param id
	 *            ��Ʒid
	 * @param img
	 * @param classes
	 * @param gname
	 * @param brand
	 * @param price
	 * @param standard
	 * @param producing
	 * @param introduce
	 * @param onPostRequest
	 * @param tag
	 */
	public void updateProduct(final String id, final File img,
			final String classes, final String gname, final String brand,
			final String price, final String standard, final String producing,
			final String introduce, OnPostRequest<EntityProduct> onPostRequest,
			String tag) {

		AsyncPost<EntityProduct> asyncPost = new AsyncPost<EntityProduct>(
				onPostRequest, new DoInBackground() {
					@Override
					public EntityJsonParse parseJson(EntityJsonParse temp,
							String json) throws NoSuchFieldException,
							IllegalArgumentException, IllegalAccessException,
							JSONException {
						JSONObject jsonObject = new JSONObject(json);

						if (0 == jsonObject.getInt("s")) {
							temp.setCode(EntityJsonParse.STATUS_CODE_OK);
						} else {
							temp.setMsg(jsonObject.getString("e"));
							temp.setCode(EntityJsonParse.STATUS_CODE_FAIL);
						}
						return temp;
					}

					@Override
					public MultipartEntity getMultipart()
							throws UnsupportedEncodingException {
						MultiPackaging multiPackaging = new MultiPackaging();
						multiPackaging.MultipartString("api", "update_goods")
								.MultipartString("id", id)
								.MultipartImage("upfile", img)
								.MultipartString("class", classes)
								.MultipartString("gname", gname)
								.MultipartString("brand", brand)
								.MultipartString("price", price)
								.MultipartString("standard", standard)
								.MultipartString("producing", producing)
								.MultipartString("introduce", introduce);
						return multiPackaging.getMultipartEntity();
					}
				});
		// ���Զ�����̳߳������У����������url
		asyncPost.executeOnExecutor(executorService, url);
		// ��ȡ��ǰϵͳʱ�䣬��ΪΨһlong���͵ı�ʶ
		Long cTag = System.currentTimeMillis();
		if (null == FlagMap.get(tag)) {
			ArrayList<Long> cTags = new ArrayList<Long>();
			cTags.add(cTag);
			FlagMap.put(tag, cTags);
		} else {
			FlagMap.get(tag).add(cTag);
		}
		// ������̳߳ؼ��뵽map��
		AsyncMap.put(cTag, asyncPost);

	}

	/**
	 * ɾ����Ʒ
	 * 
	 * @param id
	 *            ��Ʒid
	 * @param onPostRequest
	 * @param tag
	 */
	public void deleteProduct(final String id,
			OnPostRequest<EntityProduct> onPostRequest, String tag) {
		AsyncPost<EntityProduct> asyncPost = new AsyncPost<EntityProduct>(
				onPostRequest, new DoInBackground() {
					@Override
					public EntityJsonParse parseJson(EntityJsonParse temp,
							String json) throws NoSuchFieldException,
							IllegalArgumentException, IllegalAccessException,
							JSONException {
						JSONObject jsonObject = new JSONObject(json);

						if (0 == jsonObject.getInt("s")) {
							temp.setCode(EntityJsonParse.STATUS_CODE_OK);
						} else {
							temp.setMsg(jsonObject.getString("e"));
							temp.setCode(EntityJsonParse.STATUS_CODE_FAIL);
						}
						return temp;
					}

					@Override
					public MultipartEntity getMultipart()
							throws UnsupportedEncodingException {
						MultiPackaging multiPackaging = new MultiPackaging();
						multiPackaging.MultipartString("api", "del_goods")
								.MultipartString("id", id);
						return multiPackaging.getMultipartEntity();
					}
				});
		// ���Զ�����̳߳������У����������url
		asyncPost.executeOnExecutor(executorService, url);
		// ��ȡ��ǰϵͳʱ�䣬��ΪΨһlong���͵ı�ʶ
		Long cTag = System.currentTimeMillis();
		if (null == FlagMap.get(tag)) {
			ArrayList<Long> cTags = new ArrayList<Long>();
			cTags.add(cTag);
			FlagMap.put(tag, cTags);
		} else {
			FlagMap.get(tag).add(cTag);
		}
		// ������̳߳ؼ��뵽map��
		AsyncMap.put(cTag, asyncPost);
	}

	/**
	 * ��Ʒ�б�
	 * 
	 * @param uid
	 * @param pageIndex
	 * @param displayNumber
	 * @param onPostRequest
	 * @param tag
	 */
	public void productList(final String uid, final String pageIndex,
			final String displayNumber,
			OnPostRequest<ArrayList<EntityProduct>> onPostRequest, String tag) {
		AsyncPost<ArrayList<EntityProduct>> asyncPost = new AsyncPost<ArrayList<EntityProduct>>(
				onPostRequest, new DoInBackground() {
					@Override
					public EntityJsonParse parseJson(EntityJsonParse temp,
							String json) throws NoSuchFieldException,
							IllegalArgumentException, IllegalAccessException,
							JSONException {
						JSONObject jsonObject = new JSONObject(json);
						ArrayList<EntityProduct> entityProducts = new ArrayList<EntityProduct>();
						if (0 == jsonObject.getInt("s")) {
							JSONArray jsonArray = jsonObject
									.getJSONArray("data");
							for (int i = 0; i < jsonArray.length(); i++) {
								EntityProduct entityProduct = new EntityProduct();
								entityProduct.setId(jsonArray.getJSONObject(i)
										.getString("id"));
								entityProduct.setGname(jsonArray.getJSONObject(
										i).getString("gname"));
								entityProduct.setPrice(jsonArray.getJSONObject(
										i).getString("price"));
								entityProduct.setImg(jsonArray.getJSONObject(i)
										.getString("img"));
								entityProducts.add(entityProduct);
							}

							String total = jsonObject.getString("total");
							temp.setResult(entityProducts);

							temp.setCode(EntityJsonParse.STATUS_CODE_OK);
						} else {
							temp.setMsg(jsonObject.getString("e"));
							temp.setCode(EntityJsonParse.STATUS_CODE_FAIL);
						}
						return temp;
					}

					@Override
					public MultipartEntity getMultipart()
							throws UnsupportedEncodingException {
						MultiPackaging multiPackaging = new MultiPackaging();
						multiPackaging
								.MultipartString("api", "goods_list")
								.MultipartString("uid", uid)
								.MultipartString("pageIndex", pageIndex)
								.MultipartString("displayNumber", displayNumber);
						return multiPackaging.getMultipartEntity();
					}
				});
		// ���Զ�����̳߳������У����������url
		asyncPost.executeOnExecutor(executorService, url);
		// ��ȡ��ǰϵͳʱ�䣬��ΪΨһlong���͵ı�ʶ
		Long cTag = System.currentTimeMillis();
		if (null == FlagMap.get(tag)) {
			ArrayList<Long> cTags = new ArrayList<Long>();
			cTags.add(cTag);
			FlagMap.put(tag, cTags);
		} else {
			FlagMap.get(tag).add(cTag);
		}
		// ������̳߳ؼ��뵽map��
		AsyncMap.put(cTag, asyncPost);

	}

	/**
	 * ��Ʒ����
	 * 
	 * @param id
	 * @param onPostRequest
	 * @param tag
	 */

	public void productDetail(final String id,
			OnPostRequest<EntityProduct> onPostRequest, String tag) {

		AsyncPost<EntityProduct> asyncPost = new AsyncPost<EntityProduct>(
				onPostRequest, new DoInBackground() {
					@Override
					public EntityJsonParse parseJson(EntityJsonParse temp,
							String json) throws NoSuchFieldException,
							IllegalArgumentException, IllegalAccessException,
							JSONException {
						JSONObject jsonObject = new JSONObject(json);
						EntityProduct entityProduct = new EntityProduct();
						if (0 == jsonObject.getInt("s")) {
							entityProduct.setId(jsonObject
									.getJSONObject("data").getString("id"));
							entityProduct.setUid(jsonObject.getJSONObject(
									"data").getString("uid"));
							entityProduct.setClasses(jsonObject.getJSONObject(
									"data").getString("class"));
							entityProduct.setGname(jsonObject.getJSONObject(
									"data").getString("gname"));
							entityProduct.setImg(jsonObject.getJSONObject(
									"data").getString("img"));
							entityProduct.setBrand(jsonObject.getJSONObject(
									"data").getString("brand"));
							entityProduct.setPrice(jsonObject.getJSONObject(
									"data").getString("price"));
							entityProduct.setStandard(jsonObject.getJSONObject(
									"data").getString("standard"));
							entityProduct.setProducing(jsonObject
									.getJSONObject("data").getString(
											"producing"));
							entityProduct.setIntroduce(jsonObject
									.getJSONObject("data").getString(
											"introduce"));
							entityProduct.setDateline(jsonObject.getJSONObject(
									"data").getString("dateline"));
							temp.setResult(entityProduct);
							temp.setCode(EntityJsonParse.STATUS_CODE_OK);
						} else {
							temp.setMsg(jsonObject.getString("e"));
							temp.setCode(EntityJsonParse.STATUS_CODE_FAIL);
						}
						return temp;
					}

					@Override
					public MultipartEntity getMultipart()
							throws UnsupportedEncodingException {
						MultiPackaging multiPackaging = new MultiPackaging();
						multiPackaging.MultipartString("api", "goods_details")
								.MultipartString("id", id);
						return multiPackaging.getMultipartEntity();
					}
				});
		// ���Զ�����̳߳������У����������url
		asyncPost.executeOnExecutor(executorService, url);
		// ��ȡ��ǰϵͳʱ�䣬��ΪΨһlong���͵ı�ʶ
		Long cTag = System.currentTimeMillis();
		if (null == FlagMap.get(tag)) {
			ArrayList<Long> cTags = new ArrayList<Long>();
			cTags.add(cTag);
			FlagMap.put(tag, cTags);
		} else {
			FlagMap.get(tag).add(cTag);
		}
		// ������̳߳ؼ��뵽map��
		AsyncMap.put(cTag, asyncPost);

	}

	/**
	 * �û��б�
	 * @param uclass
	 * @param pclass
	 * @param longs
	 * @param lat
	 * @param pageIndex
	 * @param displayNumber
	 * @param onPostRequest
	 * @param tag
	 */
	public void userList(final String uclass, final String pclass,
			final String longs, final String lat, final String pageIndex,
			final String displayNumber,
			OnPostRequest<EntityUserList> onPostRequest, String tag) {

		AsyncPost<EntityUserList> asyncPost = new AsyncPost<EntityUserList>(
				onPostRequest, new DoInBackground() {
					@Override
					public EntityJsonParse parseJson(EntityJsonParse temp,
							String json) throws NoSuchFieldException,
							IllegalArgumentException, IllegalAccessException,
							JSONException {
						JSONObject jsonObject = new JSONObject(json);

						if (0 == jsonObject.getInt("s")) {
							ArrayList<EntityUserList> entityUserLists = new ArrayList<EntityUserList>();
							JSONArray jsonArray = jsonObject.getJSONArray("data");
							for (int i = 0; i < jsonArray.length(); i++) {
								EntityUserList entityUserList = new EntityUserList();
								entityUserList.setUid(jsonArray.getJSONObject(i).getString("uid"));
								entityUserList.setCname(jsonArray.getJSONObject(i).getString("cname"));
								entityUserList.setMark(jsonArray.getJSONObject(i).getString("mark"));
								entityUserList.setIntro(jsonArray.getJSONObject(i).getString("intro"));
								entityUserList.setLongitude(jsonArray.getJSONObject(i).getString("long"));
								entityUserList.setLatitude(jsonArray.getJSONObject(i).getString("lat"));
								entityUserList.setMeter(jsonArray.getJSONObject(i).getString("meter"));
								
								entityUserLists.add(entityUserList);
							}
							
							
							temp.setResult(entityUserLists);
							temp.setCode(EntityJsonParse.STATUS_CODE_OK);
						} else {
							temp.setMsg(jsonObject.getString("e"));
							temp.setCode(EntityJsonParse.STATUS_CODE_FAIL);
						}
						return temp;
					}

					@Override
					public MultipartEntity getMultipart()
							throws UnsupportedEncodingException {
						MultiPackaging multiPackaging = new MultiPackaging();
						multiPackaging
								.MultipartString("api", "user_list")
								.MultipartString("uclass", uclass)
								.MultipartString("pclass", pclass)
								.MultipartString("long", longs)
								.MultipartString("lat", lat)
								.MultipartString("pageIndex", pageIndex)
								.MultipartString("displayNumber", displayNumber);
						return multiPackaging.getMultipartEntity();
					}
				});
		// ���Զ�����̳߳������У����������url
		asyncPost.executeOnExecutor(executorService, url);
		// ��ȡ��ǰϵͳʱ�䣬��ΪΨһlong���͵ı�ʶ
		Long cTag = System.currentTimeMillis();
		if (null == FlagMap.get(tag)) {
			ArrayList<Long> cTags = new ArrayList<Long>();
			cTags.add(cTag);
			FlagMap.put(tag, cTags);
		} else {
			FlagMap.get(tag).add(cTag);
		}
		// ������̳߳ؼ��뵽map��
		AsyncMap.put(cTag, asyncPost);

	}

}