package com.yidian.carbao.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yidian.carbao.R;
import com.yidian.carbao.adapter.ListViewAdapter;
import com.yidian.carbao.entity.City;
import com.yidian.carbao.entity.CityEntity;
import com.yidian.carbao.util.BreakRuleGetCityUtil;
import com.yidian.carbao.util.StringHelper;
import com.yidian.carbao.util.TitleView;

public class BreakRuleCitySearchActivity extends ActivityBase {

	private HashMap<String, Integer> selector;// ��ź���������ĸ��λ��
	private LinearLayout layoutIndex;
	private TextView tv_show;
	private ListViewAdapter adapter;
	private String[] indexStr = { "#", "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z" };
	private List<City> citiChanger = new ArrayList<City>();
	private HashMap<String, String> map = new HashMap<String, String>();
	private List<City> newCities = new ArrayList<City>();
	private int height;// ����߶�
	private boolean flag = false;

	private ListView cityList;
	private TitleView titleView;
	private String city;
	private BreakRuleGetCityUtil breakruleGetcityUtil = new BreakRuleGetCityUtil();
	protected static final int MSG_LOGIN_RESULT = 0;
	private ArrayList<CityEntity> cities = new ArrayList<CityEntity>();
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_LOGIN_RESULT:
				JSONObject json = (JSONObject) msg.obj;
				handleLoginResult(json);
				break;
			}
		}

		private void handleLoginResult(JSONObject json) {
			// TODO Auto-generated method stub
			int resultCode = -1;
			try {
				resultCode = json.getInt("resultcode");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch (resultCode) {
			case 200:
				try {

					JSONObject result = json.getJSONObject("result");
					Iterator<String> it = result.keys();
					while (it.hasNext()) {
						String key = it.next();
						JSONObject city = result.getJSONObject(key);
						JSONArray citys = city.getJSONArray("citys");
						for (int i = 0; i < citys.length(); i++) {
							CityEntity cityEntity = new CityEntity();
							cityEntity.setCityCode(citys.getJSONObject(i)
									.getString("city_code"));
							cityEntity.setCityName(citys.getJSONObject(i)
									.getString("city_name"));
							map.put(citys.getJSONObject(i).getString(
									"city_name"), citys.getJSONObject(i)
									.getString("city_code"));
							cities.add(cityEntity);

						}
					} 
//					Toast.makeText(BreakRuleCitySearchActivity.this,
//							cities.size() + "", Toast.LENGTH_LONG).show();

					// MyAdapter myAdapter =new MyAdapter();
					// cityList.setAdapter(myAdapter);

					setData();
					String[] allNames = sortIndex(citiChanger);
					sortList(allNames);

					selector = new HashMap<String, Integer>();
					for (int j = 0; j < indexStr.length; j++) {// ѭ����ĸ���ҳ�newPersons�ж�Ӧ��ĸ��λ��
						for (int i = 0; i < newCities.size(); i++) {
							if (newCities.get(i).getName().equals(indexStr[j])) {
								selector.put(indexStr[j], i);
							}
						}

					}
					adapter = new ListViewAdapter(
							BreakRuleCitySearchActivity.this, newCities);
					cityList.setAdapter(adapter);

					System.out.println("Hello~~");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Toast.makeText(BreakRuleCitySearchActivity.this, "sucess!!!!!",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				Toast.makeText(BreakRuleCitySearchActivity.this, "��ѯʧ��",
						Toast.LENGTH_SHORT).show();
				// finish();
				break;
			}
		}

		// ��������
		private void setData() {
			// TODO Auto-generated method stub
			for (int i = 0; i < cities.size(); i++) {
				City city = new City(cities.get(i).getCityName());
				citiChanger.add(city);
			}
		};
	};

	@Override
	public void initView() {
		setContentView(R.layout.breakrule_city_list);
		titleView = new TitleView(BreakRuleCitySearchActivity.this);
		cityList = (ListView) findViewById(R.id.breakrule_city_list);
		layoutIndex = (LinearLayout) BreakRuleCitySearchActivity.this
				.findViewById(R.id.layout);
		layoutIndex.setBackgroundColor(Color.parseColor("#00ffffff"));
		tv_show = (TextView) findViewById(R.id.tv);
		tv_show.setVisibility(View.GONE);

		titleView.LeftButton(R.drawable.icon_title_left_select, 0);
		titleView.MidTextView("ѡ�����", 0);
		titleView.getImageButton_left().setOnClickListener(this);
		breakruleGetcityUtil.searchCity(mHandler);

		cityList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();

				String cityname = newCities.get(arg2).getName();
				String citycode = map.get(cityname);

				intent.putExtra("cityname", cityname);
				intent.putExtra("citycode", citycode);

				setResult(0, intent);
				System.out.println("----------getCity" + cityname + "~~~~~~~"
						+ citycode);
				finish();

			}
		});

	}

	/**
	 * ����������һ���µ�List����
	 * 
	 * @param allNames
	 */
	private void sortList(String[] allNames) {
		for (int i = 0; i < allNames.length; i++) {
			if (allNames[i].length() != 1) {
				for (int j = 0; j < citiChanger.size(); j++) {
					if (allNames[i].equals(citiChanger.get(j).getPinYinName())) {
						City p = new City(citiChanger.get(j).getName(),
								citiChanger.get(j).getPinYinName());
						newCities.add(p);
					}
				}
			} else {
				newCities.add(new City(allNames[i]));
			}
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// ��oncreate����ִ������Ĵ���û��Ӧ����Ϊoncreate����õ���getHeight=0
		if (!flag) {// ����ΪʲôҪ���ø�flag���б�ǣ������ﲻ�ȸ������ǣ�������о�����Ϊ��������Ժ�Ŀ����кô�
			height = layoutIndex.getMeasuredHeight() / indexStr.length;
			getIndexView();
			flag = true;
		}
	}

	/**
	 * ��ȡ������������
	 * 
	 * @param persons
	 * @return
	 */
	public String[] sortIndex(List<City> persons) {
		TreeSet<String> set = new TreeSet<String>();
		// ��ȡ��ʼ������Դ�е�����ĸ����ӵ�set��
		for (City person : persons) {
			set.add(StringHelper.getPinYinHeadChar(person.getName()).substring(
					0, 1));
		}
		// ������ĳ���Ϊԭ���ݼ���set�Ĵ�С
		String[] names = new String[persons.size() + set.size()];
		int i = 0;
		for (String string : set) {
			names[i] = string;
			i++;
		}
		String[] pinYinNames = new String[persons.size()];
		for (int j = 0; j < persons.size(); j++) {
			persons.get(j).setPinYinName(
					StringHelper
							.getPingYin(persons.get(j).getName().toString()));
			pinYinNames[j] = StringHelper.getPingYin(persons.get(j).getName()
					.toString());
		}
		// ��ԭ���ݿ�������������
		System.arraycopy(pinYinNames, 0, names, set.size(), pinYinNames.length);
		// �Զ���������ĸ����
		Arrays.sort(names, String.CASE_INSENSITIVE_ORDER);
		return names;
	}

	/**
	 * ���������б�
	 */
	public void getIndexView() {
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.WRAP_CONTENT, height);
		for (int i = 0; i < indexStr.length; i++) {
			final TextView tv = new TextView(this);
			tv.setLayoutParams(params);
			tv.setText(indexStr[i]);
			tv.setPadding(10, 0, 10, 0);
			layoutIndex.addView(tv);
			layoutIndex.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event)

				{
					float y = event.getY();
					int index = (int) (y / height);
					if (index > -1 && index < indexStr.length) {// ��ֹԽ��
						String key = indexStr[index];
						if (selector.containsKey(key)) {
							int pos = selector.get(key);
							if (cityList.getHeaderViewsCount() > 0) {// ��ֹListView�б�������������û�С�
								cityList.setSelectionFromTop(
										pos + cityList.getHeaderViewsCount(), 0);
							} else {
								cityList.setSelectionFromTop(pos, 0);// ��������һ��
							}
							tv_show.setVisibility(View.VISIBLE);
							tv_show.setText(indexStr[index]);
						}
					}
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						layoutIndex.setBackgroundColor(Color
								.parseColor("#606060"));
						break;

					case MotionEvent.ACTION_MOVE:

						break;
					case MotionEvent.ACTION_UP:
						layoutIndex.setBackgroundColor(Color
								.parseColor("#00ffffff"));
						tv_show.setVisibility(View.GONE);
						break;
					}
					return true;
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		super.onClick(v);
		switch (v.getId()) {
		case R.id.imagebutton_title_left:
			intent.putExtra("city", city);
			setResult(0, intent);
			System.out.println("----------getCity" + city);
			finish();
			break;

		default:
			break;
		}
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			System.out.println("city ����~~~~~" + cities.size());
			Log.d("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",
					"city ����~~~~~" + cities.size());
			return cities.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = LinearLayout.inflate(
					BreakRuleCitySearchActivity.this, R.layout.citiy_row, null);
			TextView cityTextView = (TextView) convertView
					.findViewById(R.id.city);
			cityTextView.setText(cities.get(position).getCityName());

			cityList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub

					Intent intent = new Intent();
					intent.putExtra("cityname", cities.get(arg2).getCityName());
					intent.putExtra("citycode", cities.get(arg2).getCityCode());
					setResult(0, intent);
					System.out.println("----------getCity"
							+ cities.get(arg2).getCityCode() + "~~~~~~~"
							+ cities.get(arg2).getCityName());
					System.out.println("city ����~~~~~" + cities.size());
					finish();

				}
			});

			return convertView;
		}

	}

}
