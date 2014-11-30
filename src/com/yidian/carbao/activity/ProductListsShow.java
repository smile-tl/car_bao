package com.yidian.carbao.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.yidian.carbao.R;
import com.yidian.carbao.adapter.ShowProductListAdapter;
import com.yidian.carbao.util.TitleView;

public class ProductListsShow extends ActivityBase {

	private int screenHeightLeft = 0, screenHeightRight = 0, screenWidth = 0,
			halfWidth = 0, halfHeigth = 0;
	private ListView listViewLeft, listViewRight;
	private int[] pic = { R.drawable.mmm, R.drawable.nnn, R.drawable.ppp,
			R.drawable.xxx, R.drawable.yyy, R.drawable.ooo };
	private TitleView titleView;
	private ImageView phone;

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setContentView(R.layout.product_lists_show);
		//

		String businessName = "商家名称(动态)";// 获取商家名称
		titleView = new TitleView(ProductListsShow.this);
		titleView.LeftButton(R.drawable.icon_title_left_select, 0);
		titleView.MidTextView(businessName, 0);
		//

		listViewLeft =  (ListView) findViewById(R.id.productlistshow_listview_left);
		listViewRight = (ListView) findViewById(R.id.productlistshow_listview_right);
		phone = (ImageView) findViewById(R.id.productlistshow_phnoe);

		loadImage();

		titleView.getImageButton_left().setOnClickListener(this);
		phone.setOnClickListener(this);

	}

	private void loadImage() {
		// TODO Auto-generated method stub
		View view = FrameLayout.inflate(ProductListsShow.this,
				R.layout.productlist_row,  null);
		FrameLayout frameLayout = (FrameLayout) view
				.findViewById(R.id.productlist_framelayout);
		LinearLayout linearLayout = (LinearLayout) view
				.findViewById(R.id.productlist_linearlayout);
		ImageView imgView = (ImageView) view
				.findViewById(R.id.imageview_detail_row);

		int screenWidth = getWindowManager().getDefaultDisplay().getWidth() / 2;
		int x=pic.length/2;
		int y = pic.length/2+1;
		
		ShowProductListAdapter adapter = new ShowProductListAdapter(ProductListsShow.this);
		listViewLeft.setAdapter(adapter);
		listViewRight.setAdapter(adapter);
		

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.imagebutton_title_left:
			finish();
			break;
		case R.id.productlistshow_phnoe:
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
				Toast.makeText(ProductListsShow.this, "对不起，您拨打电话失败",
						Toast.LENGTH_SHORT).show();
				;
			}

			System.out.println("this is the phonenumber..." + phone_number);
			break;

		default:
			break;
		}

	}

}
