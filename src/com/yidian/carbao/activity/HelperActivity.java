package com.yidian.carbao.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.yidian.carbao.R;
import com.yidian.carbao.adapter.AdapterViewPager;
import com.yidian.carbao.fragment.FragmentBase;
import com.yidian.carbao.fragment.FragmentService;
import com.yidian.carbao.fragment.FragmentServiceList;
import com.yidian.carbao.util.TitleView;

public class HelperActivity extends ActivityBase implements
		 OnPageChangeListener {

	private TitleView titleView;
	private String city;
	private ViewPager viewPager;
	private ArrayList<Button> buttons = new ArrayList<Button>();
	private Button helperFactoryButton, helperServiceButton, helperWashButton;

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setContentView(R.layout.helper);
		city = ActivityMain.city;
		
		titleView = new TitleView(HelperActivity.this);
		titleView.LeftButton(R.drawable.icon_title_left_select, 0);
		titleView.MidTextView(getResources().getString(R.string.heading), 0);
		titleView.RightTextViewSingle(city, 0);
		titleView.RightButton(R.drawable.icon_title_right_select, 0);
		helperFactoryButton = (Button) findViewById(R.id.button_helper_factory);
		helperServiceButton = (Button) findViewById(R.id.button_helper_service);
		helperWashButton = (Button) findViewById(R.id.button_helper_wash);
		viewPager = (ViewPager) findViewById(R.id.viewpager_helper);

		setViewPager();

		titleView.getImageButton_left().setOnClickListener(this);
		titleView.getTextView_right_single().setOnClickListener(this);
		titleView.getImageButton_right().setOnClickListener(this);
		
		buttons.add(helperFactoryButton);
		buttons.add(helperServiceButton);
		buttons.add(helperWashButton);
		
		for (int i = 0; i < buttons.size(); i++) {
			final int j = i;
			buttons.get(i).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					selectButton(j);
					viewPager.setCurrentItem(j);
				}
			});
		}
		selectButton(0);

	}

	private void setViewPager() {
		// TODO Auto-generated method stub
		ArrayList<FragmentBase> fragmentBases = new ArrayList<FragmentBase>();
		FragmentServiceList fragmentService = new FragmentServiceList(R.drawable.a);
		FragmentServiceList fragmentService2 = new FragmentServiceList(
				R.drawable.icon_fen);
		FragmentServiceList fragmentService3 = new FragmentServiceList(
				R.drawable.icon_money);
		fragmentBases.add(fragmentService);
		fragmentBases.add(fragmentService2);
		fragmentBases.add(fragmentService3);
		viewPager.setAdapter(new AdapterViewPager(getSupportFragmentManager(),
				fragmentBases));

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int id) {
				selectButton(id);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

	

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		super.onClick(v);
		switch (v.getId()) {

		case R.id.imagebutton_title_left:
			System.out.println("left -------");
			finish();
			break;
		case R.id.imagebutton_title_right:
			intent.setClass(HelperActivity.this, CityActivity.class);
			intent.putExtra("city", ActivityMain.city);
			startActivityForResult(intent, 0);
			break;
		case R.id.textview_title_right_single:
			intent.setClass(HelperActivity.this, CityActivity.class);
			intent.putExtra("city", ActivityMain.city);
			startActivityForResult(intent, 1);
			break;
//		case R.id.viewpager_helper:
//			intent.setClass(HelperActivity.this, ProductListsShow.class);
//			startActivity(intent);
//			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		String city = arg2.getStringExtra("cityChoice");
		titleView.RightTextViewSingle(city, 0);
	}

	private void selectButton(int id) {
		for (int i = 0; i < buttons.size(); i++) {
			// Toast.makeText(HelperActivity.this, id + "", Toast.LENGTH_LONG)
			// .show();
			if (id == i) {
				buttons.get(i).setTextColor(
						getResources().getColor(R.color.button_text_blue));
			} else {
				buttons.get(i).setTextColor(
						getResources().getColor(R.color.button_text_gray));
			}
		}
	}
}
