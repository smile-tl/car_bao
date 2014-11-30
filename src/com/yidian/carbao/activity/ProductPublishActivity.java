package com.yidian.carbao.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidian.carbao.R;
import com.yidian.carbao.util.TitleView;

public class ProductPublishActivity extends ActivityBase {

	private TitleView titleView;
	private ImageView cameraImg;
	private LinearLayout linearLayoutClassifySelected,
			linearLayoutClassifyList, linearLayoutInputList;
	private String classes[] = { "轿车配件", "火车配件", "特种机械配件", "轮胎批发", "内饰装饰",
			"车身装饰", "美容洗车", "修配厂", "4S店服务站", "火速电瓶勾车" };
	private ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
	private String inputs[] = { "名称", "品牌", "价格", "规格", "产地", "介绍" };
	private int count = 0;// 记录分类按钮的点击次数
	private final static int REQUEST_CODE_IMAGE = 0;
	private final static int REQUEST_CODE_CAMERA = 1;
	private final static int REQUEST_CODE_HEADER_IMAGE = 2;
	private File headerFile;
	private String path;

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();

		setContentView(R.layout.product_publish);

		titleView = new TitleView(ProductPublishActivity.this);
		titleView.LeftButton(R.drawable.icon_title_left_select, 0);
		titleView.MidTextView("编辑产品", 0);
		titleView.RightTextView("保存", 0);

		cameraImg = (ImageView) findViewById(R.id.productedit_camera);
		linearLayoutClassifyList = (LinearLayout) findViewById(R.id.productedit_classify_layout);
		linearLayoutClassifySelected = (LinearLayout) findViewById(R.id.productedit_classify_selected);
		linearLayoutInputList = (LinearLayout) findViewById(R.id.productedit_input_layout);

		for (int i = 0; i < classes.length; i++) {
			linearLayoutClassifyList.addView(getView(i, classes[i]));
		}

		for (int i = 0; i < inputs.length; i++) {
			linearLayoutInputList.addView(getInputView(i, inputs[i]));
		}

		linearLayoutClassifySelected.setOnClickListener(this);
		titleView.getImageButton_left().setOnClickListener(this);
		cameraImg.setOnClickListener(this);

//		new JApi().ApiMod("", "", "",null);
	}

//	class onPost implements OnPostRequest<ArrayList<EntityList>> {
//
//		@Override
//		public void onOk(ArrayList<EntityList> temp) {
//
//		}
//
//		@Override
//		public void onFail(String msg) {
//			
//		}
//	}

	private View getInputView(int i, String string) {
		// TODO Auto-generated method stub
		View view = LinearLayout.inflate(ProductPublishActivity.this,
				R.layout.product_edit_input_row, null);
		TextView textView = (TextView) view
				.findViewById(R.id.productedit_input_row_text);
		textView.setText(inputs[i]);
		return view;
	}

	private View getView(final int index, String context) {
		// TODO Auto-generated method stub
		LinearLayout view = (LinearLayout) LinearLayout.inflate(
				ProductPublishActivity.this, R.layout.product_edit_classify_row,
				null);
		TextView text = (TextView) view
				.findViewById(R.id.productedit_classify_row_text);
		ImageView img = (ImageView) view
				.findViewById(R.id.productedit_classify_row_img);
		text.setText(context);
		imageViews.add(img);
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select(index);
			}

		});
		return view;
	}

	private void select(int id) {
		// TODO Auto-generated method stub
		for (int i = 0; i < imageViews.size(); i++) {
			if (id == i) {
				imageViews.get(i).setVisibility(View.VISIBLE);
			} else {
				imageViews.get(i).setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);

		switch (v.getId()) {
		case R.id.imagebutton_title_left:
			System.out.println("left -------");
			finish();
			break;
		case R.id.productedit_classify_selected:

			if (count % 2 == 0) {
				linearLayoutClassifyList.setVisibility(FrameLayout.VISIBLE);
				count++;
			} else {
				linearLayoutClassifyList.setVisibility(FrameLayout.GONE);
				count++;
			}
			break;
		case R.id.productedit_camera:
			ImageSelector();
			break;
		default:
			break;
		}

	}

	private void ImageSelector() {
		// TODO Auto-generated method stub
		Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("公司标志");
		dialog.setMessage("选择照片来源");
		dialog.setNegativeButton("相册", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					System.out.println("相册~~~~~~~~~~~~~~~~~~~~~~");
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intent, REQUEST_CODE_IMAGE);
				} catch (ActivityNotFoundException e) {
					Toast.makeText(ProductPublishActivity.this, "媒体库启动失败",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		dialog.setPositiveButton("照相机", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(
							MediaStore.EXTRA_OUTPUT,
							Uri.fromFile(new File(FilePatchInitialize()
									+ "heading_image_cache.jpg")));
					startActivityForResult(intent, REQUEST_CODE_CAMERA);
				} catch (ActivityNotFoundException e) {
					Toast.makeText(ProductPublishActivity.this, "开启照相机失败",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		});
		dialog.show();
	}

	/**
	 * 初始化默认路径
	 */
	public static String FilePatchInitialize() {
		String sdPatch = "";
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			File sdDir = Environment.getExternalStorageDirectory();
			sdPatch = sdDir.toString() + "/carbao/";
		}

		return sdPatch;
	}

	/**
	 * 图片裁剪
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		System.out.println("PhotoZoom 裁剪~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, REQUEST_CODE_HEADER_IMAGE);
	}

	/**
	 * 获取到的图片本地保存至指定目录
	 * 
	 * @param bitmap
	 *            原始图片
	 * @param file
	 *            文件路径
	 * @return
	 * @throws IOException
	 */
	private File saveImage(Bitmap bitmap, File file) throws IOException {
		// 压缩图片

		System.out.println("保存图片~~~~~~~~~~~~~~~~~~~");

		int options = 100;// 文件压缩比例
		int fileSize = 100;// 文件大小（KB）
		ByteArrayOutputStream btyeArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, options,
				btyeArrayOutputStream);
		while (btyeArrayOutputStream.toByteArray().length / 1024 > fileSize) {
			btyeArrayOutputStream.reset();
			bitmap.compress(Bitmap.CompressFormat.JPEG, options,
					btyeArrayOutputStream);
			options -= 10;
			if (options <= 0) {
				break;
			}
		}
		// 判断缓存文件夹是否存在，不存在则创建
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		// path=file.getAbsolutePath();
		// System.out.println(path);
		// 将压缩后的图片写入到本地
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(btyeArrayOutputStream.toByteArray());
		fileOutputStream.flush();
		fileOutputStream.close();
		// 重赋值file
		return file;
	}

	/**
	 * 添加图片 处理过程
	 * 
	 * @author Administrator
	 *
	 */
	class ImageThread extends Thread {
		private Bitmap bitmap;

		public ImageThread(Bitmap bitmap) {
			this.bitmap = bitmap;
		}

		public void run() {
			if (bitmap != null) {
				System.out
						.println("添加图片 处理过程~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				String root = FilePatchInitialize();
				System.out.println("root !!!!!!!!!!!!!!!!!!!!!!!!!!" + root);
				if (!root.equals("")) {
					try {
						String fileName = System.currentTimeMillis() / 1000
								+ ".jpg";
						headerFile = saveImage(bitmap,
								new File(root + fileName));
						path = headerFile.getAbsolutePath();
						System.out.println("root is " + root);
						System.out.println("file name is " + headerFile);
						// setData(true);
						runOnUiThread(new Runnable() {
							public void run() {

								setData(true);

								// new CloudAPI()
								// .UserInfoHeadUpdate(
								// Utile.GetUserInfo(ActivitySetting.this),
								// headerFile,
								// new UpdataUserHeader());
							}
						});
					} catch (IOException e) {
						Toast.makeText(ProductPublishActivity.this, "图片获取失败",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(ProductPublishActivity.this, "SD卡不可用",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(ProductPublishActivity.this, "图片获取失败",
						Toast.LENGTH_SHORT).show();
			}
		};

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (data != null) {
			System.out.println("Intent  if ~~~~~~~~~~~~~~~~~" + data);
			switch (requestCode) {

			case REQUEST_CODE_IMAGE:
				startPhotoZoom(data.getData());
				System.out.println("11111111111111111111");
				System.out.println("path~~~~~~~~~~11" + path);
				break;
			case REQUEST_CODE_CAMERA:
				Uri uri = Uri.fromFile(new File(FilePatchInitialize()
						+ "heading_image_cache.jpg"));
				startPhotoZoom(uri);
				System.out.println("22222222222222222222222");
				break;
			case REQUEST_CODE_HEADER_IMAGE:
				Bitmap bitmap = data.getExtras().getParcelable("data");
				System.out.println("333333333333333333333");

				new Thread(new ImageThread(bitmap)).start();
				System.out.println("path~~~~~~~~~~33" + path);
				break;
			}
		} else if (requestCode == REQUEST_CODE_CAMERA) {
			System.out.println("Intent else ***8~~~~~~~~~~~~~~~~~" + data);

			Uri uri = Uri.fromFile(new File(FilePatchInitialize()
					+ "heading_image_cache.jpg"));
			startPhotoZoom(uri);
		}
		System.out.println("444444444444444444444444");

	}

	/**
	 * 根据用户是否登陆的状态进行数据设置
	 */
	private void setData(boolean isLogin) {
		if (isLogin) {
			System.out.println("login*****");
			Bitmap bm = BitmapFactory.decodeFile(path);
			cameraImg.setImageBitmap(bm);

		} else {

		}
	}

}
