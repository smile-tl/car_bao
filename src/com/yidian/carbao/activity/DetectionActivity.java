package com.yidian.carbao.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yidian.carbao.R;
import com.yidian.carbao.util.TitleView;

public class DetectionActivity extends ActivityBase {

	private TitleView titleView;
	private ListView listView;
	private List<Integer> imgs = new ArrayList<Integer>();
	private List<String> headings = new ArrayList<String>();
	private List<String> details = new ArrayList<String>();
	private List<String> meters = new ArrayList<String>();

	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setContentView(R.layout.detection_list);
		listView = (ListView) findViewById(R.id.detection_list);
		titleView = new TitleView(DetectionActivity.this);
		titleView.show();
		titleView.LeftButton(R.drawable.icon_title_left_select, 0);
		titleView.MidTextView("汽车检测线信息", 0);
		titleView.RightButton(R.drawable.icon_title_right_select, 0);
		titleView.RightTextViewSingle(ActivityMain.city, 0);//获得当前location
		
		
		titleView.getImageButton_left().setOnClickListener(this);
		titleView.getImageButton_right().setOnClickListener(this);
		titleView.getTextView_right_single().setOnClickListener(this);
		
		for(int i=0;i<10;i++){
			imgs.add(R.drawable.mmm);
			headings.add("晚车报");
			details.add("zheshiyige.....999999999999999999999999999999999999999999999999999999999999999990000000000000");
			meters.add("0.5公里");
		}
		
		MyAdaptor myAdaptor = new MyAdaptor();
		listView.setAdapter(myAdaptor);
	}
	
	
	
	
	class MyAdaptor extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgs.size();
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = LinearLayout.inflate(DetectionActivity.this, R.layout.detection_row_view, null);
			ImageView img = (ImageView) convertView.findViewById(R.id.detection_img);
			TextView heading = (TextView) convertView.findViewById(R.id.detection_head);
			TextView detail = (TextView) convertView.findViewById(R.id.detection_detail);
			TextView meter = (TextView) convertView.findViewById(R.id.detection_meters);
			
			img.setImageResource(imgs.get(position));
			heading.setText(headings.get(position));
			detail.setText(details.get(position));
			meter.setText(meters.get(position));
			
			return convertView;
		}
		
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
			intent.setClass(DetectionActivity.this, CityActivity.class);
			intent.putExtra("city", ActivityMain.city);
			startActivityForResult(intent, 0);
			break;
		case R.id.textview_title_right_single:
			intent.setClass(DetectionActivity.this, CityActivity.class);
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
	
}
