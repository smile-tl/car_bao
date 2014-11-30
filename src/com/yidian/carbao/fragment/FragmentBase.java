package com.yidian.carbao.fragment;

import com.yidian.carbao.net.JApi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FragmentBase extends Fragment implements OnClickListener {
	public String TAG = "ActivityBase";
	
	public View contentView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return contentView;
	}

	public void setContentView(int res) {
		if (contentView == null) {
			contentView = LinearLayout.inflate(getActivity(), res, null);
		}
	}

	public void initView() {

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {

	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		JApi.getInstance().AsyncCancel(TAG);
	}
}