package com.yidian.carbao.adapter;

import java.util.HashMap;

import com.yidian.carbao.R;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowProductListAdapter extends AdapterBase {



	private int ScreenWidth = 0;
	private HashMap<String, View> viewMap = new HashMap<String, View>();
	private int[] pic = { R.drawable.mmm, R.drawable.nnn, R.drawable.ppp,
			R.drawable.xxx, R.drawable.yyy, R.drawable.ooo };
	
	public ShowProductListAdapter(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		ScreenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
	}
	
	@Override
	public int getCount() {
		return pic.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		
		ViewHolder viewHolder = null;
//		convertView = viewMap.get(position + "");
		if (null == convertView) {
			convertView = FrameLayout.inflate(getActivity(),
					R.layout.productlist_row, null);	
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageview_detail_row);
			viewHolder.imageView.setImageResource(pic[position]);
			convertView.setTag(viewHolder);
			
			viewMap.put(position + "", convertView);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}

	class ViewHolder {
		public ImageView imageView, imageView_delete;
		public TextView textView_name, textView_money;
	}
}