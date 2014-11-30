package com.yidian.carbao.activity;

import com.yidian.carbao.net.JApi;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class ActivityBase extends FragmentActivity implements OnClickListener {
	public String TAG = "ActivityBase";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	public void initView() {

	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			KeyBack();
			break;
		}
		return true;
	}

	public void KeyBack() {
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		JApi.getInstance().AsyncCancel(TAG);
	}
}