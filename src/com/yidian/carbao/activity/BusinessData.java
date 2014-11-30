package com.yidian.carbao.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.yidian.carbao.R;
import com.yidian.carbao.R.string;
import com.yidian.carbao.entity.EntityUserInfo;
import com.yidian.carbao.net.JApi;
import com.yidian.carbao.net.OnPostRequest;
import com.yidian.carbao.util.TitleView;
import com.yidian.carbao.util.Util;

public class BusinessData extends ActivityBase {

	private final static int REQUEST_CODE_IMAGE = 0;
	private final static int REQUEST_CODE_CAMERA = 1;
	private final static int REQUEST_CODE_HEADER_IMAGE = 2;
	private final static int REQUEST_CODE_GET_CITY=3;
	
	
	private File headerFile;
	private  String path;

	private double latitude, longitude;// 经度、纬度
	private String la,lo;
	private String address;
	private EntityUserInfo entityUserInfo = new EntityUserInfo();
	private LinearLayout linearLayout_classify, linearLayout_classify_img,
			linearLayout_part, linearLayout_detection, linearLayout_decorate,
			linearLayout_helper, linearLayout_cityselected;
	private ImageView partImg, detectionImg, decorateImg, helperImg;
	private EditText companyNameEdit,introEdit,addreEdit,worktimeEdit,contacPEdit,contacMEdit;
	private TextView textView = null, companyAddress,classPart,classHelper,classDetection,classDecorate;
	private String strPart="汽车配件",strHelper="汽车救援",strDetection="汽车检测线",strDecorate="汽车装饰",classes="";
	private String strName,strIntro,strProv,strAddre,strWorktime,strContaP,strContaM;
	private boolean bsign [] ={false,false,false,false};
	public LocationClient mLocationClient = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	private TitleView titleView;
	private ImageButton businessSign;
	private String getCity;
	private DisplayImageOptions displayImageOptions;
	private int count = 0;// 记录分类按钮的点击次数
	private int count_part = 0, count_detection = 0, count_decorate = 0,
			count_helper = 0;// 记录图片的点击次数

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		
		

		
		setContentView(R.layout.business_data);
		titleView = new TitleView(BusinessData.this);
		titleView.LeftButton(R.drawable.icon_title_left_select, 0);
		titleView.MidTextView("商家中心", 0);
		titleView.RightTextView("保存", 0);
		
		strPart =getResources().getString(R.string.part);
		strHelper = getResources().getString(R.string.hlper);
		strDecorate = getResources().getString(R.string.decorate);
		strDetection = getResources().getString(R.string.detection);
		
		classPart = (TextView) findViewById(R.id.class_part);
		classHelper = (TextView) findViewById(R.id.class_helper);
		classDetection = (TextView) findViewById(R.id.class_detection);
		classDecorate = (TextView) findViewById(R.id.class_decorate);
		
		classPart.setText(strPart);
		classHelper.setText(strHelper);
		classDecorate.setText(strDecorate);
		classDetection.setText(strDetection);
		
		
		
		companyAddress = (TextView) findViewById(R.id.company_address);
		textView = (TextView) findViewById(R.id.business_data_city);
		linearLayout_classify = (LinearLayout) findViewById(R.id.classify);
		linearLayout_classify_img = (LinearLayout) findViewById(R.id.classify_img);
		linearLayout_decorate = (LinearLayout) findViewById(R.id.business_data_decorate);
		linearLayout_detection = (LinearLayout) findViewById(R.id.business_data_detection);
		linearLayout_helper = (LinearLayout) findViewById(R.id.business_data_helper);
		linearLayout_part = (LinearLayout) findViewById(R.id.business_data_part);
		linearLayout_cityselected = (LinearLayout) findViewById(R.id.business_data_city_selected);
		
		companyNameEdit = (EditText) findViewById(R.id.company_name);
		introEdit = (EditText) findViewById(R.id.company_info);
		addreEdit = (EditText) findViewById(R.id.company_address);
		worktimeEdit = (EditText) findViewById(R.id.job_time);
		contacPEdit = (EditText) findViewById(R.id.contacts);
		contacMEdit = (EditText) findViewById(R.id.contact_phone);
		
		
		
		
		partImg = (ImageView) findViewById(R.id.business_data_part_img);
		decorateImg = (ImageView) findViewById(R.id.business_data_decorate_img);
		detectionImg = (ImageView) findViewById(R.id.business_data_detection_img);
		helperImg = (ImageView) findViewById(R.id.business_data_helper_img);
		businessSign = (ImageButton) findViewById(R.id.businessdata_sign);

		
		
		
		getBusinessData();
		
		
		
		
		
		initCity();// 获取当前所在地
		System.out.println("this is the city" + getCity);
		// Toast.makeText(this, "当前纬度:" + longitude, Toast.LENGTH_SHORT)
		// .show();
		System.out.println("当前纬度：" + longitude + "******************");
		linearLayout_classify.setOnClickListener(this);
		linearLayout_part.setOnClickListener(BusinessData.this);
		linearLayout_decorate.setOnClickListener(BusinessData.this);
		linearLayout_detection.setOnClickListener(BusinessData.this);
		linearLayout_helper.setOnClickListener(BusinessData.this);
		businessSign.setOnClickListener(this);
		linearLayout_cityselected.setOnClickListener(this);
		titleView.getImageButton_left().setOnClickListener(this);
		titleView.getTextView_right().setOnClickListener(this);
		// 头像加载配置文件
		displayImageOptions = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.icon_businessdata_sign)
				.showImageOnFail(R.drawable.icon_businessdata_sign)
				.showImageOnLoading(R.drawable.icon_businessdata_sign)
				.resetViewBeforeLoading(true).cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(100, 3)).build();
		
		
	}

	private void getBusinessData() {
		// TODO Auto-generated method stub
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		String classes = bundle.getString("classes");
		String name = bundle.getString("name");
		String mark = bundle.getString("mark");
		String intro = bundle.getString("intro");
		String province = bundle.getString("province");
		String address = bundle.getString("address");
		String worktime = bundle.getString("worktime");
		String contactP = bundle.getString("contactP");
		String contactM = bundle.getString("contactM");
		lo = bundle.getString("longitude");
		la = bundle.getString("latitude");
		
		String clss [] = classes.split("|");
		for(int i = 0;i<clss.length;i++){
			if("汽车配件".equals(clss[i])){
				partImg.setImageResource(R.drawable.icon_part);
			}else if("汽车装饰".equals(clss[i])){
				decorateImg.setImageResource(R.drawable.icon_decorate);
				
			}else if("汽车救援".equals(clss[i])){
				helperImg.setImageResource(R.drawable.icon_helper);
			}else if("汽车检测线".equals(clss[i])){
				detectionImg.setImageResource(R.drawable.icon_detection);
			}
		}
		
		
		companyNameEdit.setText(name);
		introEdit.setText(intro);
		textView.setText(province);
		addreEdit.setText(address);
		worktimeEdit.setText(worktime);
		contacPEdit.setText(contactP);
		contacMEdit.setText(contactM);
		
		
		
	}

	private void initCity() {

		// TODO Auto-generated method stub
		mLocationClient = new LocationClient(BusinessData.this);

		// 设置定位条件
		LocationClientOption option = new LocationClientOption();

		mLocationClient.setLocOption(option);
		// 注册位置监听器

		mLocationClient.registerLocationListener(myListener);
		setLocationOption();
		mLocationClient.start();

		System.out.println("init 经纬度" + latitude + "??????");
		System.out.println("city    " + getCity);
	}

	class MyLocationListenner implements BDLocationListener {// 要想给响应的变量赋值，只能在这里面赋值，否则会被清掉。。。
		@Override
		public void onReceiveLocation(BDLocation location) {
			textView.setText(location.getCity());
			getCity = location.getCity();
			address = location.getAddrStr();
			companyAddress.setText(address);
			latitude = location.getLatitude();// 经度
			longitude = location.getLongitude();// 纬度
			System.out.println("当前纬度：" + longitude);
			System.out.println("当前经度" + latitude);
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
		}
	}

	// 设置相关参数
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		option.setPriority(LocationClientOption.NetWorkFirst);
		option.setPriority(LocationClientOption.GpsFirst); // gps
		// option.setPoiNumber(10);
		option.disableCache(true);

		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		// option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		// option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(5); // 最多返回POI个数
		option.setPoiDistance(1000); // poi查询距离

		mLocationClient.setLocOption(option);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mLocationClient.stop();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		super.onClick(v);
		switch (v.getId()) {
		case R.id.classify:
			if (count % 2 == 0) {
				linearLayout_classify_img.setVisibility(FrameLayout.VISIBLE);
				count++;
			} else {
				linearLayout_classify_img.setVisibility(FrameLayout.GONE);
				count++;
			}

			break;
		case R.id.business_data_part:
			if (count_part % 2 == 0) {
				partImg.setImageResource(R.drawable.icon_part);
				System.out.println(count_part + "~~~~");
				bsign[0]=true;
				count_part++;
			} else {
				partImg.setImageResource(R.drawable.icon_part_gray);
				System.out.println(count_part + "~~~~");
				bsign[0]=false;
				count_part++;
			}

			break;
		case R.id.business_data_decorate:
			if (count_decorate % 2 == 0) {
				decorateImg.setImageResource(R.drawable.icon_decorate);
				System.out.println(count_decorate + "~~~~");
				bsign[1] = true;
				count_decorate++;
			} else {
				decorateImg.setImageResource(R.drawable.icon_decorate_gray);
				System.out.println(count_decorate + "~~~~");
				bsign[1] = false;
				count_decorate++;
			}

			break;
		case R.id.business_data_helper:
			if (count_helper % 2 == 0) {
				helperImg.setImageResource(R.drawable.icon_helper);
				System.out.println(count_helper + "~~~~");
				bsign[2]=true;
				count_helper++;
			} else {
				helperImg.setImageResource(R.drawable.icon_helper_gray);
				System.out.println(count_helper + "~~~~");
				bsign[2] = false;
				count_helper++;
			}

			break;
		case R.id.business_data_detection:
			if (count_detection % 2 == 0) {
				detectionImg.setImageResource(R.drawable.icon_detection);
				System.out.println(count_detection + "~~~~");
				bsign[3] = true;
				count_detection++;
			} else {
				detectionImg.setImageResource(R.drawable.icon_detection_gray);
				System.out.println(count_detection + "~~~~");
				bsign[3] = false;
				count_detection++;
			}

			break;

		case R.id.imagebutton_title_left:
			finish();
			break;
		case R.id.business_data_city_selected:
			System.out.println(getCity + "buttoncity^^^^^^^");
			intent.putExtra("city", getCity);
			intent.setClass(BusinessData.this, CityActivity.class);
			startActivityForResult(intent, REQUEST_CODE_GET_CITY);
			break;

		case R.id.businessdata_sign:

			ImageSelector();

			break;
		case R.id.textview_title_right:
//			Toast.makeText(BusinessData.this, "经度"+longitude, Toast.LENGTH_SHORT).show();
			strName = companyNameEdit.getText().toString();
			strIntro = introEdit.getText().toString();
			strProv = textView.getText().toString();
			strAddre = addreEdit.getText().toString();
			strWorktime = worktimeEdit.getText().toString();
			strContaP = contacPEdit.getText().toString();
			strContaM = contacMEdit.getText().toString();
			
			File file = this.headerFile;
			EntityUserInfo getUserInfo = Util.GetUserInfo(BusinessData.this);
			entityUserInfo.setUid(getUserInfo.getUid());
			classes=getClasses();
			entityUserInfo.setClasss(classes);
			entityUserInfo.setCname(strName);
			entityUserInfo.setIntro(strIntro);
			entityUserInfo.setProvince(strProv);
			entityUserInfo.setAddress(strAddre);
			entityUserInfo.setWork_time(strWorktime);
			entityUserInfo.setContact_p(strContaP);
			entityUserInfo.setContact_phone(strContaM);
			entityUserInfo.setLongitude(longitude+"");
			entityUserInfo.setLatitude(latitude+"");
			new JApi().updateUserInfo(entityUserInfo, file, new UpdateUserInfoPost(), TAG);
			
		default:
			break;
		}
	}

	
	
	class UpdateUserInfoPost implements OnPostRequest<EntityUserInfo>{

		@Override
		public void onOk(EntityUserInfo temp) {
			// TODO Auto-generated method stub
			Util.UpdateUserInfo(BusinessData.this, temp);
			Toast.makeText(BusinessData.this, "保存成功", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onFail(String msg) {
			// TODO Auto-generated method stub
			Toast.makeText(BusinessData.this, msg, Toast.LENGTH_SHORT).show();
		}
		
	}
	
	private String getClasses() {
		// TODO Auto-generated method stub
		StringBuffer str = new StringBuffer();
		str.append("");
		String [] strClass={"汽车配件","汽车装饰","汽车救援","汽车检测线"} ;
		for(int i =0;i<bsign.length;i++){
			if(bsign[i]){
				str.append(strClass[i]);
				str.append("|");
			}
		}
		str.deleteCharAt(str.length()-1);
		return str.toString();
	}

	private void ImageSelector() {
		// TODO Auto-generated method stub
		Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("公司标志");
		dialog.setMessage("选择照片来源");
		dialog.setNegativeButton("相册", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					System.out.println("相册~~~~~~~~~~~~~~~~~~~~~~");
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intent, REQUEST_CODE_IMAGE);
				} catch (ActivityNotFoundException e) {
					Toast.makeText(BusinessData.this, "媒体库启动失败",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		dialog.setPositiveButton("照相机", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(
							MediaStore.EXTRA_OUTPUT,
							Uri.fromFile(new File(FilePatchInitialize()
									+ "heading_image_cache.jpg")));
					startActivityForResult(intent, REQUEST_CODE_CAMERA);
				} catch (ActivityNotFoundException e) {
					Toast.makeText(BusinessData.this, "开启照相机失败",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		});
		dialog.show();
	}

	/**
	 * 初始化默认路径
	 */
	public static String FilePatchInitialize() {
		String sdPatch = "";
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			File sdDir = Environment.getExternalStorageDirectory();
			sdPatch = sdDir.toString() + "/carbao/";
		}

		return sdPatch;
	}

	/**
	 * 图片裁剪
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		System.out.println("PhotoZoom 裁剪~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, REQUEST_CODE_HEADER_IMAGE);
	}
	
	
	/**
	 * 获取到的图片本地保存至指定目录
	 * 
	 * @param bitmap
	 *            原始图片
	 * @param file
	 *            文件路径
	 * @return
	 * @throws IOException
	 */
	private File saveImage(Bitmap bitmap, File file) throws IOException {
		// 压缩图片
		
		System.out.println("保存图片~~~~~~~~~~~~~~~~~~~");
		
		int options = 70;// 文件压缩比例
		int fileSize = 70;// 文件大小（KB）
		ByteArrayOutputStream btyeArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, options,
				btyeArrayOutputStream);
		while (btyeArrayOutputStream.toByteArray().length / 1024 > fileSize) {
			btyeArrayOutputStream.reset();
			bitmap.compress(Bitmap.CompressFormat.JPEG, options,
					btyeArrayOutputStream);
			options -= 10;
			if (options <= 0) {
				break;
			}
		}
		// 判断缓存文件夹是否存在，不存在则创建
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		} 
//		path=file.getAbsolutePath();
//		System.out.println(path);
		// 将压缩后的图片写入到本地
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(btyeArrayOutputStream.toByteArray());
		fileOutputStream.flush();
		fileOutputStream.close();
		// 重赋值file
		return file;
	}


	
	
	/**
	 * 添加图片 处理过程
	 * 
	 * @author Administrator
	 *
	 */
	class ImageThread extends Thread {
		private Bitmap bitmap;

		public ImageThread(Bitmap bitmap) {
			this.bitmap = bitmap;
		}

		public void run() {
			if (bitmap != null) {
				System.out.println("添加图片 处理过程~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				String root = FilePatchInitialize();
				System.out.println("root !!!!!!!!!!!!!!!!!!!!!!!!!!"+root);
				if (!root.equals("")) {
					try {
						String fileName = System.currentTimeMillis() / 1000
								+ ".jpg";
						headerFile = saveImage(bitmap,
								new File(root + fileName));
						path = headerFile.getAbsolutePath();
						System.out.println("root is "+root);
						System.out.println("file name is "+headerFile);
//						setData(true);
						runOnUiThread(new Runnable() {
							public void run() {
								
								setData(true);
								
//								new CloudAPI()
//										.UserInfoHeadUpdate(
//												Utile.GetUserInfo(ActivitySetting.this),
//												headerFile,
//												new UpdataUserHeader());
							}
						});
					} catch (IOException e) {
						Toast.makeText(BusinessData.this, "图片获取失败",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(BusinessData.this, "SD卡不可用",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(BusinessData.this, "图片获取失败",
						Toast.LENGTH_SHORT).show();
			}
		};

	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (data != null) {
			System.out.println("Intent  if ~~~~~~~~~~~~~~~~~"+data);
			switch (requestCode) {
			case REQUEST_CODE_GET_CITY:
				String city = data.getStringExtra("cityChoice");
				System.out.println("city0000000000000000000000"+city);
				textView.setText(city);
				break;
			case REQUEST_CODE_IMAGE:
				startPhotoZoom(data.getData());
				System.out.println("11111111111111111111");
				System.out.println("path~~~~~~~~~~11"+path);
				break;
			case REQUEST_CODE_CAMERA:
				Uri uri = Uri.fromFile(new File(FilePatchInitialize()
						+ "heading_image_cache.jpg"));
				startPhotoZoom(uri);
				System.out.println("22222222222222222222222");
				break;
			case REQUEST_CODE_HEADER_IMAGE:
				Bitmap bitmap = data.getExtras().getParcelable("data");
				System.out.println("333333333333333333333");
				
				new Thread(new ImageThread(bitmap)).start();
				System.out.println("path~~~~~~~~~~33"+path);
				break;
			}
		} else if (requestCode == REQUEST_CODE_CAMERA) {
			System.out.println("Intent else ***8~~~~~~~~~~~~~~~~~"+data);
			
			Uri uri = Uri.fromFile(new File(FilePatchInitialize()
					+ "heading_image_cache.jpg"));
			startPhotoZoom(uri);
		}
		System.out.println("444444444444444444444444");

		
		
	}
	
	
	/**
	 * 根据用户是否登陆的状态进行数据设置
	 */
	private void setData(boolean isLogin) {
		if (isLogin) {
			System.out.println("login*****");
			Bitmap bm = BitmapFactory.decodeFile(path);
			businessSign.setImageBitmap(bm);
			
		} else {
			
		}
	}

	

}
