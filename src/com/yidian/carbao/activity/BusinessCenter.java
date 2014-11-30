package com.yidian.carbao.activity;

import java.security.PublicKey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yidian.carbao.R;
import com.yidian.carbao.entity.EntityUserInfo;
import com.yidian.carbao.util.TitleView;
import com.yidian.carbao.util.Util;

public class BusinessCenter extends ActivityBase {

	private TitleView titleView;
	private LinearLayout businessData, productLists, productPublish;

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setContentView(R.layout.business_center);

		titleView = new TitleView(BusinessCenter.this);
		titleView.LeftButton(R.drawable.icon_title_left_select, 0);
		titleView.MidTextView("商家中心", 0);

		businessData = (LinearLayout) findViewById(R.id.business_data);
		productLists = (LinearLayout) findViewById(R.id.product_list);
		productPublish = (LinearLayout) findViewById(R.id.product_publish);

		businessData.setOnClickListener(this);
		productLists.setOnClickListener(this);
		productPublish.setOnClickListener(this);
		titleView.getImageButton_left().setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		Intent intent = new Intent();

		switch (v.getId()) {
		case R.id.business_data:
			EntityUserInfo getUserInfo = Util.GetUserInfo(BusinessCenter.this);
			String classes = getUserInfo.getClasss();
			String name = getUserInfo.getCname();
			String mark = getUserInfo.getMark();
			String intro = getUserInfo.getIntro();
			String province = getUserInfo.getProvince();
			String address = getUserInfo.getAddress();
			String worktime= getUserInfo.getWork_time();
			String contactP = getUserInfo.getContact_p();
			String contactM = getUserInfo.getContact_phone();
			String longitude = getUserInfo.getLongitude();
			String latitude = getUserInfo.getLatitude();
			
			Bundle bundle = new Bundle();
			bundle.putString("classes", classes);
			bundle.putString("name", name);
			bundle.putString("mark", mark);
			bundle.putString("intro", intro);
			bundle.putString("province", province);
			bundle.putString("address", address);
			bundle.putString("worktime", worktime);
			bundle.putString("contactP", contactP);
			bundle.putString("contactM", contactM);
			bundle.putString("longitude", longitude);
			bundle.putString("latitude", latitude);
			intent.setClass(BusinessCenter.this, BusinessData.class);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case R.id.product_list:
			intent.setClass(BusinessCenter.this, ProductLists.class);
			startActivity(intent);
			break;
		case R.id.product_publish:
			intent.setClass(BusinessCenter.this, ProductPublishActivity.class);
			startActivity(intent);
			break;
		case R.id.imagebutton_title_left:
			finish();
			break;	
		default:
			break;
		}

	}

}
