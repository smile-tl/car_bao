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
	private String classes[] = { "�γ����", "�����", "���ֻ�е���", "��̥����", "����װ��",
			"����װ��", "����ϴ��", "���䳧", "4S�����վ", "���ٵ�ƿ����" };
	private ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
	private String inputs[] = { "����", "Ʒ��", "�۸�", "���", "����", "����" };
	private int count = 0;// ��¼���ఴť�ĵ������
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
		titleView.MidTextView("�༭��Ʒ", 0);
		titleView.RightTextView("����", 0);

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
		dialog.setTitle("��˾��־");
		dialog.setMessage("ѡ����Ƭ��Դ");
		dialog.setNegativeButton("���", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					System.out.println("���~~~~~~~~~~~~~~~~~~~~~~");
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intent, REQUEST_CODE_IMAGE);
				} catch (ActivityNotFoundException e) {
					Toast.makeText(ProductPublishActivity.this, "ý�������ʧ��",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		dialog.setPositiveButton("�����", new DialogInterface.OnClickListener() {

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
					Toast.makeText(ProductPublishActivity.this, "���������ʧ��",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		});
		dialog.show();
	}

	/**
	 * ��ʼ��Ĭ��·��
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
	 * ͼƬ�ü�
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		System.out.println("PhotoZoom �ü�~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
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
	 * ��ȡ����ͼƬ���ر�����ָ��Ŀ¼
	 * 
	 * @param bitmap
	 *            ԭʼͼƬ
	 * @param file
	 *            �ļ�·��
	 * @return
	 * @throws IOException
	 */
	private File saveImage(Bitmap bitmap, File file) throws IOException {
		// ѹ��ͼƬ

		System.out.println("����ͼƬ~~~~~~~~~~~~~~~~~~~");

		int options = 100;// �ļ�ѹ������
		int fileSize = 100;// �ļ���С��KB��
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
		// �жϻ����ļ����Ƿ���ڣ��������򴴽�
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		// path=file.getAbsolutePath();
		// System.out.println(path);
		// ��ѹ�����ͼƬд�뵽����
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(btyeArrayOutputStream.toByteArray());
		fileOutputStream.flush();
		fileOutputStream.close();
		// �ظ�ֵfile
		return file;
	}

	/**
	 * ���ͼƬ �������
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
						.println("���ͼƬ �������~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
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
						Toast.makeText(ProductPublishActivity.this, "ͼƬ��ȡʧ��",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(ProductPublishActivity.this, "SD��������",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(ProductPublishActivity.this, "ͼƬ��ȡʧ��",
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
	 * �����û��Ƿ��½��״̬������������
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
