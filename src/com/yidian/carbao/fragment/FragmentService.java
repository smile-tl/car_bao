package com.yidian.carbao.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yidian.carbao.R;
import com.yidian.carbao.activity.ProductListsShow;
import com.yidian.carbao.adapter.AdapterFragmentPager;
import com.yidian.carbao.adapter.AdapterViewPager;
import com.yidian.carbao.pullToRefresh.PullToRefreshBase;
import com.yidian.carbao.pullToRefresh.PullToRefreshListView;
import com.yidian.carbao.pullToRefresh.PullToRefreshScrollView;
import com.yidian.carbao.pullToRefresh.PullToRefreshBase.OnRefreshListener;
public class FragmentService extends FragmentBase implements
OnRefreshListener<ScrollView>, OnPageChangeListener{

	private PullToRefreshScrollView pullToRefreshScrollView;
	private ScrollView scrollView;
	ArrayList<FragmentBase> fragmentBases = new ArrayList<FragmentBase>();
	private ImageView carPic;
	private TextView fragmentHead,fragmentDetail,fragmentBusiness,fragmentMeters;
	private int pid;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public FragmentService(int pid){
		this.pid = pid;
	}
	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setContentView(R.layout.view_pull_scroll);
		 pullToRefreshScrollView = (PullToRefreshScrollView)
				 contentView.findViewById(R.id.pulltorefresh_helper);
		 scrollView = pullToRefreshScrollView.getRefreshableView();
		 pullToRefreshScrollView.setPullRefreshEnabled(true);
		 pullToRefreshScrollView.setOnRefreshListener(this);
		 pullToRefreshScrollView.setPullLoadEnabled(true);
		 scrollView.setFadingEdgeLength(0);
//		 对view_helper的布局进行实例化
		 View view = LinearLayout.inflate(getActivity(),
		 R.layout.fragment_row_view, null);
		 
		 view.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), ProductListsShow.class);
				startActivity(intent);
			}
		});
		 
		 
		 
		 carPic = (ImageView) view.findViewById(R.id.fragment_img);
		 fragmentHead = (TextView) view.findViewById(R.id.fragment_head);
		 fragmentDetail = (TextView) view.findViewById(R.id.fragment_detail);
		 fragmentMeters = (TextView) view.findViewById(R.id.fragment_meters);
		 fragmentBusiness = (TextView) view.findViewById(R.id.fragment_buniness);
		 scrollView.addView(view);
	}

	
	
	class m extends FragmentPagerAdapter  {

		public m(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		
		
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
	public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
		// TODO Auto-generated method stub
		
	}
	
}
