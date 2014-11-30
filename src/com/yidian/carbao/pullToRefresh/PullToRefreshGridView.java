package com.yidian.carbao.pullToRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.GridView;

import com.yidian.carbao.pullToRefresh.ILoadingLayout.State;

/**
 * �����ʵ����GridView����ˢ�£��ϼ��ظ���ͻ����ײ��Զ�����
 * 
 * @author Li Hong
 * @since 2013-8-15
 */
public class PullToRefreshGridView extends PullToRefreshBase<GridView>
		implements OnScrollListener {

	/** ListView */
	private GridView mGridView;
	/** ���ڻ����ײ��Զ����ص�Footer */
	private LoadingLayout mFooterLayout;
	/** �����ļ����� */
	private OnScrollListener mScrollListener;

	/**
	 * ���췽��
	 * 
	 * @param context
	 *            context
	 */
	public PullToRefreshGridView(Context context) {
		this(context, null);
	}

	/**
	 * ���췽��
	 * 
	 * @param context
	 *            context
	 * @param attrs
	 *            attrs
	 */
	public PullToRefreshGridView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * ���췽��
	 * 
	 * @param context
	 *            context
	 * @param attrs
	 *            attrs
	 * @param defStyle
	 *            defStyle
	 */
	public PullToRefreshGridView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		setPullLoadEnabled(false);
	}

	@Override
	protected GridView createRefreshableView(Context context, AttributeSet attrs) {
		GridView gridView = new GridView(context);
		mGridView = gridView;
		gridView.setOnScrollListener(this);

		return gridView;
	}

	/**
	 * �����Ƿ��и������ݵı�־
	 * 
	 * @param hasMoreData
	 *            true��ʾ���и�������ݣ�false��ʾû�и���������
	 */
	public void setHasMoreData(boolean hasMoreData) {
		if (null != mFooterLayout) {
			if (!hasMoreData) {
				mFooterLayout.setState(State.NO_MORE_DATA);
			}
		}
	}

	/**
	 * ���û����ļ�����
	 * 
	 * @param l
	 *            ������
	 */
	public void setOnScrollListener(OnScrollListener l) {
		mScrollListener = l;
	}

	@Override
	protected boolean isReadyForPullUp() {
		return isLastItemVisible();
	}

	@Override
	protected boolean isReadyForPullDown() {
		return isFirstItemVisible();
	}

	@Override
	protected void startLoading() {
		super.startLoading();

		if (null != mFooterLayout) {
			mFooterLayout.setState(State.REFRESHING);
		}
	}

	@Override
	public void onPullUpRefreshComplete() {
		super.onPullUpRefreshComplete();

		if (null != mFooterLayout) {
			mFooterLayout.setState(State.RESET);
		}
	}

	@Override
	public void setScrollLoadEnabled(boolean scrollLoadEnabled) {
		super.setScrollLoadEnabled(scrollLoadEnabled);

		if (scrollLoadEnabled) {
			// ����Footer
			if (null == mFooterLayout) {
				mFooterLayout = new FooterLoadingLayout(getContext());
			}

			// mGridView.removeFooterView(mFooterLayout);
			// mGridView.addFooterView(mFooterLayout, null, false);
		} else {
			if (null != mFooterLayout) {
				// mGridView.removeFooterView(mFooterLayout);
			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (isScrollLoadEnabled() && hasMoreData()) {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					|| scrollState == OnScrollListener.SCROLL_STATE_FLING) {
				if (isReadyForPullUp()) {
					startLoading();
				}
			}
		}

		if (null != mScrollListener) {
			mScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (null != mScrollListener) {
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
		}
	}

	/**
	 * ��ʾ�Ƿ��и�������
	 * 
	 * @return true��ʾ���и�������
	 */
	private boolean hasMoreData() {
		if ((null != mFooterLayout)
				&& (mFooterLayout.getState() == State.NO_MORE_DATA)) {
			return false;
		}

		return true;
	}

	/**
	 * �жϵ�һ��child�Ƿ���ȫ��ʾ����
	 * 
	 * @return true��ȫ��ʾ����������false
	 */
	private boolean isFirstItemVisible() {
		final Adapter adapter = mGridView.getAdapter();

		if (null == adapter || adapter.isEmpty()) {
			return true;
		}

		int mostTop = (mGridView.getChildCount() > 0) ? mGridView.getChildAt(0)
				.getTop() : 0;
		if (mostTop >= 0) {
			return true;
		}

		return false;
	}

	/**
	 * �ж����һ��child�Ƿ���ȫ��ʾ����
	 * 
	 * @return true��ȫ��ʾ����������false
	 */
	private boolean isLastItemVisible() {
		final Adapter adapter = mGridView.getAdapter();

		if (null == adapter || adapter.isEmpty()) {
			return true;
		}

		final int lastItemPosition = adapter.getCount() - 1;
		final int lastVisiblePosition = mGridView.getLastVisiblePosition();

		/**
		 * This check should really just be: lastVisiblePosition ==
		 * lastItemPosition, but ListView internally uses a FooterView which
		 * messes the positions up. For me we'll just subtract one to account
		 * for it and rely on the inner condition which checks getBottom().
		 */
		if (lastVisiblePosition >= lastItemPosition - 1) {
			final int childIndex = lastVisiblePosition
					- mGridView.getFirstVisiblePosition();
			final int childCount = mGridView.getChildCount();
			final int index = Math.min(childIndex, childCount - 1);
			final View lastVisibleChild = mGridView.getChildAt(index);
			if (lastVisibleChild != null) {
				return lastVisibleChild.getBottom() <= mGridView.getBottom();
			}
		}

		return false;
	}
}
