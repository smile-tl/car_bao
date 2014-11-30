package com.yidian.carbao.adapter;

import com.yidian.carbao.R;
import com.yidian.carbao.activity.Detail;
import com.yidian.carbao.activity.ProductListsShow;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowServiceListAdapter extends AdapterBase {
	private int ScreenWidth = 0;
	private int[] pic = { R.drawable.xxx, R.drawable.yyy, R.drawable.mmm,
			R.drawable.nnn, R.drawable.ooo, R.drawable.ppp };

	public ShowServiceListAdapter(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		ScreenWidth = getActivity().getWindowManager().getDefaultDisplay()
				.getWidth();
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pic.length;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder  viewHolder=null;
		if(null == convertView){
			convertView = LinearLayout.inflate(getActivity(),
					R.layout.fragment_row_view, null);
		    viewHolder = new ViewHolder();
			viewHolder.carPic = (ImageView) convertView.findViewById(R.id.fragment_img);
			viewHolder.fragmentHead = (TextView) convertView.findViewById(R.id.fragment_head);
			viewHolder.fragmentDetail = (TextView) convertView.findViewById(R.id.fragment_detail);
			viewHolder.fragmentMeters = (TextView) convertView.findViewById(R.id.fragment_meters);
			viewHolder.fragmentBusiness = (TextView) convertView.findViewById(R.id.fragment_buniness);
			viewHolder.carPic.setImageResource(pic[position]);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		final int  p =position;
		convertView.findViewById(R.id.fragmentview_row).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("pic", pic[p]);
				intent.setClass(getActivity(), ProductListsShow.class);
				getActivity().startActivity(intent);
			}
		});
		
		return convertView;
		
	}
	
	
	class ViewHolder{
		public  ImageView carPic;
		public TextView fragmentHead,fragmentDetail,fragmentBusiness,fragmentMeters;
		
	}
}
