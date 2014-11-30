package com.yidian.carbao.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yidian.carbao.R;
import com.yidian.carbao.util.TitleView;

public class Detail extends ActivityBase {

	private ImageView imageView;
	private LinearLayout phone;
	private TitleView titleView;

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();

		setContentView(R.layout.detail);
		imageView = (ImageView) findViewById(R.id.detail_img);
		phone = (LinearLayout) findViewById(R.id.detail_phnoe);
		titleView = new TitleView(Detail.this);
		titleView.LeftButton(R.drawable.icon_title_left_select, 0);
		titleView.MidTextView("�̼����ƣ���̬��", 0);

		Intent intent = getIntent();
		int p = intent.getIntExtra("pic", 0);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), p);// �õ�ͼƬ�������������ͼƬ��Դ�Ķ���
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();

		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		double radio = 0;
		if (width < screenWidth) {
			radio = (width * 1.0) / screenWidth;

		} else {
			radio = (screenWidth * 1.0) / width;
		}

		int imgHeight = (int) (height * radio);
		LayoutParams layoutParams = imageView.getLayoutParams();
		layoutParams.height = imgHeight;
		layoutParams.width = screenWidth;
		imageView.setLayoutParams(layoutParams);
		imageView.setImageBitmap(bitmap);

		phone.setOnClickListener(this);
		titleView.getImageButton_left().setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {

		case R.id.detail_phnoe:
			// ����ϵͳ�Ĳ��ŷ���ʵ�ֵ绰������
			String phone_number = null;// ��ȡϵͳ��Ҫ����ĵ绰�š�����
			System.out.println("please call your number ~~~~~~~~~~~~~~~~~");

			try {
				phone_number = phone_number.trim();// ɾ���ַ����ײ���β���Ŀո�
				if (phone_number != null && !"".equals(phone_number)) {

					// ����ϵͳ�Ĳ��ŷ���ʵ�ֵ绰������
					// ��װһ������绰��intent�����ҽ��绰�����װ��һ��Uri������
					Intent intent = new Intent(Intent.ACTION_CALL,
							Uri.parse("tel:" + phone_number));
					startActivity(intent);// �ڲ���
				}
			} catch (Exception e) {
				Toast.makeText(Detail.this, "�Բ���������绰ʧ��", Toast.LENGTH_SHORT)
						.show();
				;
			}
 
			System.out.println("this is the phonenumber..." + phone_number);
			break;
		case R.id.imagebutton_title_left:
			finish();
			break;

		default:
			break;
		}

	}
}
