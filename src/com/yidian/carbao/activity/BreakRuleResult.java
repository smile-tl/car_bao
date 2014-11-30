package com.yidian.carbao.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yidian.carbao.R;
import com.yidian.carbao.entity.BreakRuleEntity;
import com.yidian.carbao.util.SearchBreakRuleResult;
import com.yidian.carbao.util.TitleView;

public class BreakRuleResult extends ActivityBase {

	private TitleView titleView;
	private String carNumber, city, engineno,cartypeKey;
	private TextView carNOTextView,cartypTextView;
	private FrameLayout noResultFrameLayout, breakResultFrameLayout;
	private ListView resultList;
	private ArrayList<BreakRuleEntity> listJson = new ArrayList<BreakRuleEntity>();
	public static final String key = "986125c32068c2055c8a3b20e7e5be91";
	protected static final int MSG_LOGIN_RESULT = 0;
	private SearchBreakRuleResult searchBreakRuleResult = new SearchBreakRuleResult();
	public static  Map<String,String> carType = new HashMap<String, String>();
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
					String result = json.getString("result");
					System.out.println("resutlt~~~~" + result);
					String jsonRestultString = result.substring(result
							.indexOf("{"));
					Log.d("tttttttt", "entity = " + jsonRestultString);
					JSONObject resultJson = new JSONObject(jsonRestultString);

					String list = resultJson.getString("lists");
					cartypeKey = resultJson.getString("hpzl");
					System.out.println("list~~~~" + list);

					JSONArray jsonArray = new JSONArray(list);
					// JSONArray jsonArr = JSONArray.fromObject(list);

					if (jsonArray.length() == 0) {
						noResultFrameLayout.setVisibility(View.VISIBLE);
						breakResultFrameLayout.setVisibility(View.GONE);
					} else {
						breakResultFrameLayout.setVisibility(View.VISIBLE);
						noResultFrameLayout.setVisibility(View.GONE);
						for (int i = 0; i < jsonArray.length(); i++) {
							BreakRuleEntity breakRuleEntity = new BreakRuleEntity();

							breakRuleEntity.setMoney(jsonArray.getJSONObject(i)
									.getString("money"));
							breakRuleEntity.setFen(jsonArray.getJSONObject(i)
									.getString("fen"));
							breakRuleEntity.setArea(jsonArray.getJSONObject(i)
									.getString("area"));
							breakRuleEntity.setAct(jsonArray.getJSONObject(i)
									.getString("act"));
							breakRuleEntity.setDate(jsonArray.getJSONObject(i)
									.getString("date"));
							listJson.add(breakRuleEntity);
							System.out.println("this is the number" + i);

						}
						MyAdapter myAdapter = new MyAdapter();
						resultList.setAdapter(myAdapter);
						cartypTextView.setText(carType.get(cartypeKey));
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Toast.makeText(BreakRuleResult.this, "sucess!!!!!",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				Toast.makeText(BreakRuleResult.this, "查询失败", Toast.LENGTH_SHORT)
						.show();
//				finish();
				break;
			}
		};
	};

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setContentView(R.layout.breakrule_result);
		initCatyType();
		titleView = new TitleView(BreakRuleResult.this);
		carNOTextView = (TextView) findViewById(R.id.breakruleresult_carno);
		cartypTextView = (TextView) findViewById(R.id.breakruleresult_cartype);
		noResultFrameLayout = (FrameLayout) findViewById(R.id.noresult);
		breakResultFrameLayout = (FrameLayout) findViewById(R.id.breakrule_results);
		resultList = (ListView) findViewById(R.id.breakruleresult_list);
		
		Intent intent = getIntent();
		carNumber = intent.getStringExtra("province")
				+ intent.getStringExtra("carNumber");
		city = intent.getStringExtra("city");
		engineno = intent.getStringExtra("engineNumber");
		
		titleView.LeftButton(R.drawable.icon_title_left_select, 0);
		titleView.RightButton(R.drawable.icon_breakrule_fresh, 0);
		titleView.MidTextView("违章查询", 0);
		carNOTextView.setText(carNumber);

		titleView.getImageButton_left().setOnClickListener(this);
		titleView.getImageButton_right().setOnClickListener(this);
		
		
		searchBreakRuleResult.search(city, carNumber, engineno, mHandler);

		
		
		
		
		
		
		
	}

	private void initCatyType() {
		// TODO Auto-generated method stub
		carType.put("01", "大型车");
		carType.put("02", "小型车");
		carType.put("03", "使馆汽车");
		carType.put("04", "领馆汽车");
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listJson.size();
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
			convertView = LinearLayout.inflate(BreakRuleResult.this,
					R.layout.breakrule_result_row, null);

			TextView time = (TextView) convertView
					.findViewById(R.id.breakrule_time);
			TextView location = (TextView) convertView
					.findViewById(R.id.breakrule_location);
			TextView act = (TextView) convertView
					.findViewById(R.id.breakrule_act);
			TextView money = (TextView) convertView
					.findViewById(R.id.breakrule_money);
			TextView fen = (TextView) convertView
					.findViewById(R.id.breakrule_fen);

			time.setText(listJson.get(position).getDate());
			location.setText(listJson.get(position).getArea());
			act.setText(listJson.get(position).getAct());
			money.setText(listJson.get(position).getMoney());
			fen.setText(listJson.get(position).getFen());

			return convertView;
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
		case R.id.imagebutton_title_right:
			searchBreakRuleResult.search(city, carNumber, engineno, mHandler);
			break;
		default:
			break;
		}
	}

}
