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
	// ���ݼ������/�ɹ���ʧ�ܻص�
	private OnLoadResult onLoadResult;
	// activity
	private Activity activity;
	// ��ҳ����
	private static String totle = "1";
	// ��ҳҳ��
	private static String pageIndex = "1";
	// ��ҳ��ҳ����
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
	 * �вι���
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
	 * ����ˢ��
	 */
	public void PullDown() {
		// ҳ��ָ�Ĭ��ֵ
		setPageIndex("1");
		onLoadNew();
	}

	/**
	 * ��������
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
			Toast.makeText(activity, "�����Ѽ������", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * ���ظ���
	 */
	public void onLoadMore() {
		onLoadData();
	}

	/**
	 * ����ˢ��
	 */
	public void onLoadNew() {
		onLoadData();
	}

	/**
	 * ��������
	 */
	public void onLoadData() {

	}

	/**
	 * ���ݼ�����ɣ��ɹ�/ʧ�ܻص�
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