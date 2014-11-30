package com.yidian.carbao.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yidian.carbao.R;
import com.yidian.carbao.util.TitleView;

public class ProductLists extends ActivityBase {

	private int screenHeightLeft = 0, screenHeightRight = 0, screenWidth = 0,
			halfWidth = 0, halfHeigth = 0;
	private LinearLayout linearlayoutLeft, linearlayoutRight;
	private TitleView titleView;
	private ImageView phone;
	private boolean isDelete;

	private final static String imgs[] = {
			"http://d.hiphotos.baidu.com/image/pic/item/95eef01f3a292df593a0bb0abe315c6034a87349.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/42a98226cffc1e1786194a384890f603738de911.jpg",
			"http://d.hiphotos.baidu.com/image/pic/item/d788d43f8794a4c2ce07ce350cf41bd5ad6e3983.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/fc1f4134970a304e294c3de4d3c8a786c9175c45.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/42a98226cffc1e1786194a384890f603738de911.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/42a98226cffc1e1786194a384890f603738de911.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/42a98226cffc1e1786194a384890f603738de911.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/42a98226cffc1e1786194a384890f603738de911.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/42a98226cffc1e1786194a384890f603738de911.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/42a98226cffc1e1786194a384890f603738de911.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/42a98226cffc1e1786194a384890f603738de911.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/42a98226cffc1e1786194a384890f603738de911.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/42a98226cffc1e1786194a384890f603738de911.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/42a98226cffc1e1786194a384890f603738de911.jpg" };

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setContentView(R.layout.product_lists);

		String businessName = "商家名称(动态)";// 获取商家名称
		titleView = new TitleView(ProductLists.this);
		titleView.LeftButton(R.drawable.icon_title_left_select, 0);
		titleView.MidTextView(businessName, 0);
		titleView.RightTextView("删除", 0);

		linearlayoutLeft = (LinearLayout) findViewById(R.id.productlist_linearlayout_left);
		linearlayoutRight = (LinearLayout) findViewById(R.id.productlist_linearlayout_right);
		phone = (ImageView) findViewById(R.id.productlist_phnoe);

		titleView.getImageButton_left().setOnClickListener(this);
		titleView.getTextView_right().setOnClickListener(this);
		phone.setOnClickListener(this);
		loadImage();
	}

	private void loadImage() {
		// TODO Auto-generated method stub

		screenWidth = getWindowManager().getDefaultDisplay().getWidth();// 得到屏幕的宽度
		halfWidth = screenWidth / 2;
		for (int i = 0; i < imgs.length; i++) {
			final ImageView imageView = new ImageView(ProductLists.this);
			ImageLoader.getInstance().displayImage(imgs[i], imageView,
					new ImageLoadingListener() {

						@Override
						public void onLoadingStarted(String imageUri, View view) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							View detail = LinearLayout.inflate(
									ProductLists.this,
									R.layout.productlist_price, null);
							
							FrameLayout imgrow = (FrameLayout) detail.findViewById(R.id.productlist_row);
							
							// TODO Auto-generated method stub
							int imgWidth = loadedImage.getWidth();
							int imgHeight = loadedImage.getHeight();
							double radio = (1.0 * halfWidth) / imgWidth;
							halfHeigth = (int) (imgHeight * radio);
							imageView.setScaleType(ScaleType.FIT_XY);

							imageView.setLayoutParams(new LayoutParams(
									halfWidth, halfHeigth));

							imageView.setImageBitmap(loadedImage);
							if (screenHeightLeft <= screenHeightRight) {
								screenHeightLeft += imgHeight;
								imgrow.addView(imageView);
//								linearlayoutLeft.addView(imgrow);
//								linearlayoutLeft.addView(detail);
//								linearlayoutLeft.addView(imageView);
//								
//								linearlayoutLeft.addView(detail);

							} else {
								screenHeightRight += imgHeight;
								imgrow.addView(imageView);
//								linearlayoutRight.addView(imgrow);
//								linearlayoutRight.addView(detail);
								
//								linearlayoutRight.addView(imageView);
//								linearlayoutRight.addView(detail);
							}

						}

						@Override
						public void onLoadingCancelled(String imageUri,
								View view) {
							// TODO Auto-generated method stub

						}
					});

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.imagebutton_title_left:
			finish();
			break;
		case R.id.productlist_phnoe:
			// 调用系统的拨号服务实现电话拨打功能
			String phone_number = null;// 获取系统需要拨打的电话号。。。
			System.out.println("please call your number ~~~~~~~~~~~~~~~~~");

			try {
				phone_number = phone_number.trim();// 删除字符串首部和尾部的空格
				if (phone_number != null && !"".equals(phone_number)) {

					// 调用系统的拨号服务实现电话拨打功能
					// 封装一个拨打电话的intent，并且将电话号码包装成一个Uri对象传入
					Intent intent = new Intent(Intent.ACTION_CALL,
							Uri.parse("tel:" + phone_number));
					startActivity(intent);// 内部类
				}
			} catch (Exception e) {
				Toast.makeText(ProductLists.this, "对不起，您拨打电话失败",
						Toast.LENGTH_SHORT).show();
				;
			}

			System.out.println("this is the phonenumber..." + phone_number);
			break;

		case R.id.textview_title_right:
			Intent intent = new Intent();
			intent.setClass(ProductLists.this, ProductPublishActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

}
