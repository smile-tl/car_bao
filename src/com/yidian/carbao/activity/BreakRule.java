package com.yidian.carbao.activity;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidian.carbao.R;
import com.yidian.carbao.entity.BreakRuleEntity;
import com.yidian.carbao.util.KeyboardUtil;
import com.yidian.carbao.util.TitleView;

public class BreakRule extends ActivityBase {

	protected static final int MSG_LOGIN_RESULT = 0;
	private TextView cityShow, showBelong;
	private LinearLayout linearLayoutLocation, linearLayoutCarBelong,
			linearLayoutSearch;
	private EditText carNo, engineNo;
	private TitleView titleView;
	private String getCityCode, currentCity, hphmProvince, hphmNumber,
			engineNumber;
	KeyboardUtil util;

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setContentView(R.layout.breakrule);
		titleView = new TitleView(BreakRule.this);
		titleView.LeftButton(R.drawable.icon_title_left_select, 0);
		titleView.MidTextView("违章查询", 0);

		showBelong = (TextView) findViewById(R.id.belong_show);
		cityShow = (TextView) findViewById(R.id.breakrule_city);
		linearLayoutLocation = (LinearLayout) findViewById(R.id.breakrule_location);
		linearLayoutCarBelong = (LinearLayout) findViewById(R.id.car_belong);
		linearLayoutSearch = (LinearLayout) findViewById(R.id.breakrule_search);

		carNo = (EditText) this.findViewById(R.id.car_num);
		engineNo = (EditText) findViewById(R.id.engine_num);
//		cityShow.setText(ActivityMain.city);

		linearLayoutLocation.setOnClickListener(this);
		linearLayoutSearch.setOnClickListener(this);
		showBelong.setOnClickListener(this);
		titleView.getImageButton_left().setOnClickListener(this);
		showKey();

	}

	private void showKey() {
		// // TODO Auto-generated method stub

		//
		if (android.os.Build.VERSION.SDK_INT <= 10) {
		} else {
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			try {
				Class<EditText> cls = EditText.class;
				Method setShowSoftInputOnFocus;
				setShowSoftInputOnFocus = cls.getMethod(
						"setShowSoftInputOnFocus", boolean.class);
				setShowSoftInputOnFocus = cls.getMethod(
						"setSoftInputShownOnFocus", boolean.class);

				// 4.0的是setShowSoftInputOnFocus,4.2的是setSoftInputOnFocus
				setShowSoftInputOnFocus.setAccessible(false);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		//
		util = new KeyboardUtil(BreakRule.this, BreakRule.this, showBelong);

	}
 
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		super.onClick(v);
		switch (v.getId()) {
		case R.id.imagebutton_title_left:
			finish();
			break;
		case R.id.car_belong:

			break;
		case R.id.breakrule_location:
//			intent.putExtra("city", getCity);
			intent.setClass(BreakRule.this, BreakRuleCitySearchActivity.class);
			int requestCode = 0;
			startActivityForResult(intent, requestCode);
			break;
		case R.id.belong_show:
			InputMethodManager imm = (InputMethodManager) getSystemService(BreakRule.this.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(carNo.getWindowToken(), 0);
			imm.hideSoftInputFromInputMethod(carNo.getWindowToken(), 0);
			util.showKeyboard();
			break;

		case R.id.breakrule_search:
			currentCity = cityShow.getText().toString();
			hphmProvince = showBelong.getText().toString();
			hphmNumber = carNo.getText().toString();
			engineNumber = engineNo.getText().toString();
			
			intent.putExtra("city", getCityCode);
			intent.putExtra("province", hphmProvince);
			intent.putExtra("carNumber", hphmNumber);
			intent.putExtra("engineNumber", engineNumber);
			intent.setClass(BreakRule.this, BreakRuleResult.class);
			startActivity(intent);
			
			
			System.out.println(currentCity + "--" + hphmProvince + "--"
					+ hphmNumber + "--" + engineNumber);
		default:
			break;
		}

	}


	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		String city = arg2.getStringExtra("cityname");
		cityShow.setText(city);
		getCityCode = arg2.getStringExtra("citycode");
		
	}
}
