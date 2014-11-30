package com.yidian.carbao.pullToRefresh;

import com.yidian.carbao.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * ������װ������ˢ�µĲ���
 * 
 * @author Li Hong
 * @since 2013-7-30
 */
@SuppressLint("InflateParams")
public class RotateLoadingLayout extends LoadingLayout {
	/** ��ת������ʱ�� */
	static final int ROTATION_ANIMATION_DURATION = 1200;
	/** ������ֵ */
	static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
	/** Header������ */
	private RelativeLayout mHeaderContainer;
	/** ��ͷͼƬ */
	private ImageView mArrowImageView;
	/** ״̬��ʾTextView */
	private TextView mHintTextView;
	// /** ������ʱ���TextView */
	// private TextView mHeaderTimeView;
	// /** ������ʱ��ı��� */
	// private TextView mHeaderTimeViewTitle;
	/** ��ת�Ķ��� */
	private Animation mRotateAnimation;

	/**
	 * ���췽��
	 * 
	 * @param context
	 *            context
	 */
	public RotateLoadingLayout(Context context) {
		super(context);
		init(context);
	}

	/**
	 * ���췽��
	 * 
	 * @param context
	 *            context
	 * @param attrs
	 *            attrs
	 */
	public RotateLoadingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * ��ʼ��
	 * 
	 * @param context
	 *            context
	 */
	private void init(Context context) {
		mHeaderContainer = (RelativeLayout) findViewById(R.id.pull_to_refresh_header_content);
		mArrowImageView = (ImageView) findViewById(R.id.pull_to_refresh_header_arrow);
		mHintTextView = (TextView) findViewById(R.id.pull_to_refresh_header_hint_textview);
		// mHeaderTimeView = (TextView)
		// findViewById(R.id.pull_to_refresh_header_time);
		// mHeaderTimeViewTitle = (TextView)
		// findViewById(R.id.pull_to_refresh_last_update_time_text);

		mArrowImageView.setScaleType(ScaleType.CENTER);
		mArrowImageView.setImageResource(R.drawable.default_ptr_rotate);

		float pivotValue = 0.5f; // SUPPRESS CHECKSTYLE
		float toDegree = 720.0f; // SUPPRESS CHECKSTYLE
		mRotateAnimation = new RotateAnimation(0.0f, toDegree,
				Animation.RELATIVE_TO_SELF, pivotValue,
				Animation.RELATIVE_TO_SELF, pivotValue);
		mRotateAnimation.setFillAfter(true);
		mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
		mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
		mRotateAnimation.setRepeatCount(Animation.INFINITE);
		mRotateAnimation.setRepeatMode(Animation.RESTART);
	}

	@Override
	protected View createLoadingView(Context context, AttributeSet attrs) {
		View container = LayoutInflater.from(context).inflate(
				R.layout.pull_to_refresh_header2, null);
		return container;
	}

	@Override
	public void setLastUpdatedLabel(CharSequence label) {
		// ��������µ�ʱ����ı��ǿյĻ�������ǰ��ı���
		// mHeaderTimeViewTitle
		// .setVisibility(TextUtils.isEmpty(label) ? View.INVISIBLE
		// : View.VISIBLE);
		// mHeaderTimeView.setText(label);
	}

	@Override
	public int getContentSize() {
		if (null != mHeaderContainer) {
			return mHeaderContainer.getHeight();
		}

		return (int) (getResources().getDisplayMetrics().density * 60);
	}

	@Override
	protected void onStateChanged(State curState, State oldState) {
		super.onStateChanged(curState, oldState);
	}

	@Override
	protected void onReset() {
		resetRotation();
		mHintTextView.setText("����ˢ��");
	}

	@Override
	protected void onReleaseToRefresh() {
		mHintTextView.setText("�ɿ���ˢ��");
	}

	@Override
	protected void onPullToRefresh() {
		mHintTextView.setText("����ˢ��");
	}

	@Override
	protected void onRefreshing() {
		resetRotation();
		mArrowImageView.startAnimation(mRotateAnimation);
		mHintTextView.setText("���ڼ�����");
	}

	@Override
	@SuppressLint("NewApi")
	public void onPull(float scale) {
		float angle = scale * 180f; // SUPPRESS CHECKSTYLE
		mArrowImageView.setRotation(angle);
	}

	/**
	 * ���ö���
	 */
	@SuppressLint("NewApi")
	private void resetRotation() {
		mArrowImageView.clearAnimation();
		mArrowImageView.setRotation(0);
	}
}
