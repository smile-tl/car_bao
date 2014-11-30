package com.yidian.carbao.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yidian.carbao.R;
import com.yidian.carbao.util.TitleView;

public class CityActivity extends ActivityBase {

	private final String cities [] ={"北京市","天津市","上海市","重庆市","河北省","陕西省","辽宁省",
			"吉林省","黑龙江省","江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省",
			"广东省","海南省","四川省","贵州省","云南省","陕西省","甘肃省","青海省","广西","内蒙古",
			"西藏","宁夏","新疆","香港","澳门","台湾"};
	private TextView location;
	private ListView cityList;
	private TitleView titleView;
	private String currentLocation;
	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setContentView(R.layout.citiy_list);
		location = (TextView) findViewById(R.id.location);
		cityList = (ListView) findViewById(R.id.city_list);
		titleView = new TitleView(CityActivity.this);
		titleView.LeftButton(R.drawable.icon_title_left_select, 0);
		titleView.MidTextView("选择城市", 0);
		
		
		Intent intent = getIntent();
		currentLocation = intent.getStringExtra("city");
		location.setText(currentLocation);
		System.out.println("currentLocation...................."+currentLocation);
		MyAdapter myAdapter =new MyAdapter();
		cityList.setAdapter(myAdapter);
		
		location.setOnClickListener(this);
		titleView.getImageButton_left().setOnClickListener(this);
	
	}
	
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return cities.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = LinearLayout.inflate(CityActivity.this, R.layout.citiy_row, null);;
			TextView cityTextView =(TextView) convertView.findViewById(R.id.city);
			
			String city= cities[position];
			System.out.println("position is "+position);
			cityTextView.setText(city);
			
			cityList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					
					Intent intent = new Intent();
					intent.putExtra("cityChoice", cities[arg2]);
					setResult(3, intent);
					System.out.println("----------getCity"+cities[arg2]);
					finish();
				
				}
			});
			return convertView;
		}
		
	}
	
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		super.onClick(v);
		switch (v.getId()) {
		case R.id.location:
			System.out.println("this is the back");
			
			intent.putExtra("cityChoice", currentLocation);
			setResult(0, intent);
			System.out.println("----------getCity"+currentLocation);
			finish();
			break;
		case R.id.imagebutton_title_left:
			intent.putExtra("cityChoice", currentLocation);
			setResult(0, intent);
			System.out.println("----------getCity"+currentLocation);
			finish();
			break;
		default:
			break;
		}
		
	}




	
}
