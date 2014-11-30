package com.yidian.carbao.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yidian.carbao.R;
import com.yidian.carbao.entity.EntityUserInfo;
import com.yidian.carbao.net.JApi;
import com.yidian.carbao.net.OnPostRequest;
import com.yidian.carbao.util.FrameLoading;
import com.yidian.carbao.util.TitleView;
import com.yidian.carbao.util.Util;

public class RegisterActivity extends ActivityBase {

	private FrameLoading frameLoading;
	private TitleView titleView;
	private EditText editName, editPassword;
	private LinearLayout register;
	private InputMethodManager inputMethodManager;

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setContentView(R.layout.register);

		titleView = new TitleView(RegisterActivity.this);
		titleView.LeftButton(R.drawable.icon_title_left_select, 0);
		titleView.MidTextView("注册", 0);
		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		editName = (EditText) findViewById(R.id.register_username);
		editPassword = (EditText) findViewById(R.id.register_password);
		register = (LinearLayout) findViewById(R.id.register);
//		frameLoading = new FrameLoading(this);
		
		titleView.getImageButton_left().setOnClickListener(this);
		register.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.imagebutton_title_left:
			finish();
			break;

		case R.id.register:
			inputMethodManager.hideSoftInputFromWindow(
					editPassword.getWindowToken(), 0);
			String username = editName.getText().toString();
			String password = editPassword.getText().toString();
//			frameLoading.showFrame();
//			frameLoading.setMessage("正在注册");
//			new JApi().Register(username, password, new RegisterPostRequest());
			
			
			
			
			new JApi().getInstance().Register(username, password, new RegisterPost(), TAG);

		default:
			break;
		}

	}

	
	class RegisterPost implements OnPostRequest<EntityUserInfo>{

		@Override
		public void onOk(EntityUserInfo temp) {
			// TODO Auto-generated method stub
			Toast.makeText(RegisterActivity.this, "正在请求用户信息", Toast.LENGTH_SHORT).show();
			JApi.getInstance().UserInfo(temp.getUid(), new UserInfoPost(), TAG);
		}

		@Override
		public void onFail(String msg) {
			// TODO Auto-generated method stub
			Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	class UserInfoPost implements OnPostRequest<EntityUserInfo>{

		@Override
		public void onOk(EntityUserInfo temp) {
			// TODO Auto-generated method stub
			Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
			Util.InsertUserInfo(RegisterActivity.this, temp);
			
			Intent intent  = new Intent();
			intent.setClass(RegisterActivity.this, BusinessCenter.class);
			startActivity(intent);
		}

		@Override
		public void onFail(String msg) {
			// TODO Auto-generated method stub
			Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
		}
		
	}

}
