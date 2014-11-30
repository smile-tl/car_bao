package com.yidian.carbao.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yidian.carbao.R;
import com.yidian.carbao.activity.RegisterActivity.UserInfoPost;
import com.yidian.carbao.entity.EntityUserInfo;
import com.yidian.carbao.net.JApi;
import com.yidian.carbao.net.OnPostRequest;
import com.yidian.carbao.util.TitleView;
import com.yidian.carbao.util.Util;

public class LoginActivity extends ActivityBase {

	private TitleView titleView;
	private LinearLayout linearLayout_login,linearLayout_login_register;
	private String strUsername,strPassword;
	private EditText editName,editPassword;
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setContentView(R.layout.login);
		
		titleView = new TitleView(LoginActivity.this);
		titleView.LeftButton(R.drawable.icon_title_left_select, 0);
		titleView.MidTextView("登录", 0);
		titleView.getImageButton_left().setOnClickListener(this);
		
		linearLayout_login = (LinearLayout) findViewById(R.id.login);
		linearLayout_login_register = (LinearLayout) findViewById(R.id.login_register);
		
		editName = (EditText) findViewById(R.id.login_username);
		editPassword = (EditText) findViewById(R.id.login_password);
		
		linearLayout_login.setOnClickListener(this);
		linearLayout_login_register.setOnClickListener(this);
		
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

		case R.id.login:
			strUsername = editName.getText().toString();
			strPassword = editPassword.getText().toString();
			if(null==strUsername||"".equals(strUsername)){
				Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
			}else if(null==strPassword||"".equals(strPassword)){
				Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
			}else{
				new JApi().Login(strUsername, strPassword, new LoginPost(), TAG);
			}
			
			break;
		case R.id.login_register :
			
			intent.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		
	}
	
	
	class LoginPost implements OnPostRequest<EntityUserInfo>{

		@Override
		public void onOk(EntityUserInfo temp) {
			// TODO Auto-generated method stub
			Toast.makeText(LoginActivity.this, "正在获取用户数据", Toast.LENGTH_SHORT).show();
			new JApi().getInstance().UserInfo(temp.getUid(), new UserInfoPost(), TAG);
			
		}

		@Override
		public void onFail(String msg) {
			// TODO Auto-generated method stub
			Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	
	class UserInfoPost implements OnPostRequest<EntityUserInfo>{

		@Override
		public void onOk(EntityUserInfo temp) {
			// TODO Auto-generated method stub
			Util.InsertUserInfo(LoginActivity.this, temp);
			Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, BusinessCenter.class);
			startActivity(intent);
		}

		@Override
		public void onFail(String msg) {
			// TODO Auto-generated method stub
			Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
		}
		
	}
	
}
