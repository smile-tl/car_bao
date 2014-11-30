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

	private HashMap<String, Integer> selector;// 存放含有索引字母的位置
	private LinearLayout layoutIndex;
	private TextView tv_show;
	private ListViewAdapter adapter;
	private String[] indexStr = { "#", "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z" };
	private List<City> citiChanger = new ArrayList<City>();
	private HashMap<String, String> map = new HashMap<String, String>();
	private List<City> newCities = new ArrayList<City>();
	private int height;// 字体高度
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
					for (int j = 0; j < indexStr.length; j++) {// 循环字母表，找出newPersons中对应字母的位置
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
				Toast.makeText(BreakRuleCitySearchActivity.this, "查询失败",
						Toast.LENGTH_SHORT).show();
				// finish();
				break;
			}
		}

		// 增加数据
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
		titleView.MidTextView("选择城市", 0);
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
	 * 重新排序获得一个新的List集合
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
		// 在oncreate里面执行下面的代码没反应，因为oncreate里面得到的getHeight=0
		if (!flag) {// 这里为什么要设置个flag进行标记，我这里不先告诉你们，请读者研究，因为这对你们以后的开发有好处
			height = layoutIndex.getMeasuredHeight() / indexStr.length;
			getIndexView();
			flag = true;
		}
	}

	/**
	 * 获取排序后的新数据
	 * 
	 * @param persons
	 * @return
	 */
	public String[] sortIndex(List<City> persons) {
		TreeSet<String> set = new TreeSet<String>();
		// 获取初始化数据源中的首字母，添加到set中
		for (City person : persons) {
			set.add(StringHelper.getPinYinHeadChar(person.getName()).substring(
					0, 1));
		}
		// 新数组的长度为原数据加上set的大小
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
		// 将原数据拷贝到新数据中
		System.arraycopy(pinYinNames, 0, names, set.size(), pinYinNames.length);
		// 自动按照首字母排序
		Arrays.sort(names, String.CASE_INSENSITIVE_ORDER);
		return names;
	}

	/**
	 * 绘制索引列表
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
					if (index > -1 && index < indexStr.length) {// 防止越界
						String key = indexStr[index];
						if (selector.containsKey(key)) {
							int pos = selector.get(key);
							if (cityList.getHeaderViewsCount() > 0) {// 防止ListView有标题栏，本例中没有。
								cityList.setSelectionFromTop(
										pos + cityList.getHeaderViewsCount(), 0);
							} else {
								cityList.setSelectionFromTop(pos, 0);// 滑动到第一项
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
			System.out.println("city 个数~~~~~" + cities.size());
			Log.d("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",
					"city 个数~~~~~" + cities.size());
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
					System.out.println("city 个数~~~~~" + cities.size());
					finish();

				}
			});

			return convertView;
		}

	}

}
