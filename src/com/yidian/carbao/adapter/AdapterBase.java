package com.yidian.carbao.adapter;

import java.util.HashMap;

import com.yidian.carbao.pullToRefresh.PullToRefreshListView;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;


@SuppressWarnings("static-access")
public class AdapterBase extends BaseAdapter {
	// 数据加载完成/成功或失败回调
	private OnLoadResult onLoadResult;
	// activity
	private Activity activity;
	// 分页总数
	private static String totle = "1";
	// 分页页码
	private static String pageIndex = "1";
	// 分页单页总数
	private static String displayNumber = "15";
	// viewmap
	private HashMap<String, View> viewMap = new HashMap<String, View>();

	public OnLoadResult getOnLoadResult() {
		return onLoadResult;
	}

	public void setOnLoadResult(OnLoadResult onLoadResult) {
		this.onLoadResult = onLoadResult;
	}

	/**
	 * 有参构造
	 * 
	 * @param activity
	 */
	public AdapterBase(Activity activity) {
		this.activity = activity;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public HashMap<String, View> getViewMap() {
		return viewMap;
	}

	public void setViewMap(HashMap<String, View> viewMap) {
		this.viewMap = viewMap;
	}

	public String getTotle() {
		return totle;
	}

	public void setTotle(String totle) {
		this.totle = totle;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getDisplayNumber() {
		return displayNumber;
	}

	public void setDisplayNumber(String displayNumber) {
		this.displayNumber = displayNumber;
	}

	/**
	 * 下拉刷新
	 */
	public void PullDown() {
		// 页码恢复默认值
		setPageIndex("1");
		onLoadNew();
	}

	/**
	 * 上拉加载
	 */
	public void PullUp(Activity activity,
			PullToRefreshListView pullToRefreshListView) {
		int totle = Integer.parseInt(getTotle());
		int pageIndex = Integer.parseInt(getPageIndex()) + 1;
		if (totle >= pageIndex) {
			setPageIndex(pageIndex + "");
			onLoadMore();
		} else {
			pullToRefreshListView.onPullUpRefreshComplete();
			Toast.makeText(activity, "数据已加载完成", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 加载更多
	 */
	public void onLoadMore() {
		onLoadData();
	}

	/**
	 * 下拉刷新
	 */
	public void onLoadNew() {
		onLoadData();
	}

	/**
	 * 加载数据
	 */
	public void onLoadData() {

	}

	/**
	 * 数据加载完成，成功/失败回调
	 * 
	 * @author Administrator
	 *
	 */
	public interface OnLoadResult {
		void LoadOk();

		void LoadFail();
	}

	@Override
	public int getCount() {
		return 0;
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
		return null;
	}
}