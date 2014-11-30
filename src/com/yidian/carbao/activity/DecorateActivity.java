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

public class DecorateActivity extends ActivityBase implements
		OnPageChangeListener {

	private TitleView titleView;
	private String city;
	private Button decorateInnerButton, decorateCarbodyButton,
			decorateWashButton;
	private ViewPager viewPager;
	private ArrayList<Button> buttons = new ArrayList<Button>();

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setContentView(R.layout.decorate);
		city = ActivityMain.city;

		titleView = new TitleView(DecorateActivity.this);
		titleView.LeftButton(R.drawable.icon_title_left_select, 0);
		titleView.MidTextView(getResources().getString(R.string.heading), 0);
		titleView.RightTextViewSingle(city, 0);
		titleView.RightButton(R.drawable.icon_title_right_select, 0);

		decorateInnerButton = (Button) findViewById(R.id.button_decorate_inner);
		decorateCarbodyButton = (Button) findViewById(R.id.button_decorate_carbody);
		decorateWashButton = (Button) findViewById(R.id.button_decorate_wash);
		viewPager = (ViewPager) findViewById(R.id.viewpager_decorate);
		viewPager.setOnPageChangeListener(this);
		setAdaptor();
		buttons.add(decorateInnerButton);
		buttons.add(decorateCarbodyButton);
		buttons.add(decorateWashButton);


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
		
		titleView.getImageButton_left().setOnClickListener(this);
		titleView.getTextView_right_single().setOnClickListener(this);
		titleView.getImageButton_right().setOnClickListener(this);
		

	}

	private void setAdaptor() {
		// TODO Auto-generated method stub
		ArrayList<FragmentBase> fragmentBases = new ArrayList<FragmentBase>();
		FragmentServiceList fragmentService = new FragmentServiceList(
				R.drawable.icon_car);
		FragmentServiceList fragmentService2 = new FragmentServiceList(
				R.drawable.icon_car);
		FragmentServiceList fragmentService3 = new FragmentServiceList(
				R.drawable.icon_car);
		fragmentBases.add(fragmentService);
		fragmentBases.add(fragmentService2);
		fragmentBases.add(fragmentService3);
		AdapterViewPager viewPagerAdapter = new AdapterViewPager(
				getSupportFragmentManager(), fragmentBases);
		viewPager.setAdapter(viewPagerAdapter);
		
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
			intent.setClass(DecorateActivity.this, CityActivity.class);
			intent.putExtra("city", ActivityMain.city);
			startActivityForResult(intent, 0);
			break;
		case R.id.textview_title_right_single:
			intent.setClass(DecorateActivity.this, CityActivity.class);
			intent.putExtra("city", ActivityMain.city);
			startActivityForResult(intent, 1);
			break;

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

	@Override
	public void onPageScrollStateChanged(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int id) {
		// TODO Auto-generated method stub
		selectButton(id);
	}

	public void selectButton(int id) {
		for (int i = 0; i < buttons.size(); i++) {
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
