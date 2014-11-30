package com.yidian.carbao.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.yidian.carbao.R;
import com.yidian.carbao.util.TitleView;

public class ActivityMain extends ActivityBase {

	private TitleView titleView;
	private ViewPager viewPager;
	private LinearLayout linearLayout_detection, linearLayout_breakrule,
			linearLayout_decorate, linearLayout_part, linearLayout_helper;

	public LocationClient mLocationClient = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	public static String city;

	@Override
	public void initView() {
		// TODO Auto-generated method stub

		super.initView();
		setContentView(R.layout.main);
		initCity();// 获取当前所在地
		titleView = new TitleView(ActivityMain.this);
		titleView.show();
		titleView.LeftButton(R.drawable.icon_main_login, 0);
		titleView.MidTextView(getResources().getString(R.string.heading), 0);

		linearLayout_detection = (LinearLayout) findViewById(R.id.main_detection);
		linearLayout_breakrule = (LinearLayout) findViewById(R.id.main_breakrule);
		linearLayout_decorate = (LinearLayout) findViewById(R.id.main_decorate);
		linearLayout_part = (LinearLayout) findViewById(R.id.main_part);
		linearLayout_helper = (LinearLayout) findViewById(R.id.main_helper);

		linearLayout_detection.setOnClickListener(this);
		linearLayout_breakrule.setOnClickListener(this);
		linearLayout_decorate.setOnClickListener(this);
		linearLayout_part.setOnClickListener(this);
		linearLayout_helper.setOnClickListener(this);

		titleView.getImageButton_left().setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.imagebutton_title_left:
			intent.setClass(ActivityMain.this, LoginActivity.class);
			startActivity(intent);
			break;

		case R.id.main_detection:
			intent.setClass(ActivityMain.this, DetectionActivity.class);
			startActivity(intent);
			break;

		case R.id.main_breakrule:
			intent.setClass(ActivityMain.this, BreakRule.class);
			startActivity(intent);
			break;

		case R.id.main_decorate:
			intent.setClass(ActivityMain.this, DecorateActivity.class);
			startActivity(intent);
			break;

		case R.id.main_part:
			intent.setClass(ActivityMain.this, PartActivity.class);
			startActivity(intent);
			break;

		case R.id.main_helper:
			intent.setClass(ActivityMain.this, HelperActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	private void initCity() {
		System.out.println("this is the city");
		// TODO Auto-generated method stub
		mLocationClient = new LocationClient(ActivityMain.this);
		mLocationClient.registerLocationListener(myListener);
		setLocationOption();
		mLocationClient.start();
	}

	class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			city = location.getCity();
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
		option.setPoiNumber(10);
		option.disableCache(true);
		mLocationClient.setLocOption(option);
	}

	@Override
	public void onDestroy() {
		mLocationClient.stop();
		super.onDestroy();
	}

}
