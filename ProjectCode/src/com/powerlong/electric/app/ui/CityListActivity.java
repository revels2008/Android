/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * CityListActivity.java
 * 
 * 2013-7-30-下午04:21:34
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.CityListAdapter;
import com.powerlong.electric.app.config.Constants.LocationStatus;
import com.powerlong.electric.app.db.DBManager;
import com.powerlong.electric.app.listener.PlLocationListener;
import com.powerlong.electric.app.location.CurrentLocation;
import com.powerlong.electric.app.location.Location;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.widget.PlLetterListView;
import com.powerlong.electric.app.widget.PlLetterListView.OnTouchingLetterChangedListener;
import com.powerlong.electric.app.utils.CityUtil;
import com.powerlong.electric.app.utils.LogUtil;

/**
 * 
 * CityListActivity：城市列表页面
 * 
 * @author: Liang Wang 2013-7-30-下午04:21:34
 * 
 * @version 1.0.0
 * 
 */
public  class CityListActivity extends BaseActivity implements OnClickListener {
	private CityListAdapter adapter;
	private ListView mCityList;
	private TextView overlay;
	private PlLetterListView letterListView;
	private Handler handler;
	private OverlayThread overlayThread;
	private SQLiteDatabase database;
	private ArrayList<CityUtil> mCityNames;
	private HashMap<String, Integer> alphaIndexer = null;// 存放存在的汉语拼音首字母和与之对应的列表位置
	private String[] sections = null;// 存放存在的汉语拼音首字母

	private ImageView ivBack = null;
	private TextView tvTitle = null;
	Location mLocation = null;

	/*
	 * private PlLocationListener mListener = new
	 * PlLocationListener(CityListActivity.this) {
	 * 
	 * @Override public void OnTimeout() { LogUtil.d("Location", "time out");
	 * CurrentLocation.status = LocationStatus.FAILED;
	 * adapter.notifyDataSetChanged(); mLocation.stop(); }
	 * 
	 * @Override public void OnGetLocationSuccess(BDLocation location) { String
	 * city = location.getCity(); double mLatitude = location.getLatitude();
	 * double mLongitude = location.getLongitude(); LogUtil.d("Location",
	 * "mLocation = "+city); LogUtil.d("Location", "mLatitude = "+mLatitude);
	 * LogUtil.d("Location", "mLongitude = "+mLongitude); CurrentLocation.status
	 * = LocationStatus.SUCCESS; CurrentLocation.curLocation = city;
	 * CurrentLocation.lat = mLatitude; CurrentLocation.lng = mLongitude;
	 * mLocation.stop(); adapter.notifyDataSetChanged(); } };
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_list);

		mLocation = Location.getInstance(this);

		ivBack = (ImageView) findViewById(R.id.custom_imageview_gohome);
		ivBack.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.custom_textview_title);
		tvTitle.setText(R.string.city_list);
		mCityList = (ListView) findViewById(R.id.city_list);
		letterListView = (PlLetterListView) findViewById(R.id.cityLetterListView);
		DBManager dbManager = new DBManager(this);
		dbManager.openDateBase();
		dbManager.closeDatabase();
		database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
				+ DBManager.DB_NAME, null);
		mCityNames = getCityNames();
		database.close();
		letterListView
				.setOnTouchingLetterChangedListener(new LetterListViewListener());
		handler = new Handler();
		overlayThread = new OverlayThread();
		initOverlay();
		setAdapter(mCityNames);
		mCityList.setOnItemClickListener(new CityListOnItemClick());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mLocation.setListener(new PlLocationListener(CityListActivity.this) {

			@Override
			public void OnTimeout() {
//				LogUtil.d("Location", "time out");
				CurrentLocation.status = LocationStatus.FAILED;
				adapter.notifyDataSetChanged();
				mLocation.stop();
			}

			@Override
			public void OnGetLocationSuccess(BDLocation location) {
				String city = location.getCity();
				double mLatitude = location.getLatitude();
				double mLongitude = location.getLongitude();
				LogUtil.d("Location", "mLocation = " + city);
				LogUtil.d("Location", "mLatitude = " + mLatitude);
				LogUtil.d("Location", "mLongitude = " + mLongitude);
				CurrentLocation.status = LocationStatus.SUCCESS;
				CurrentLocation.curLocation = city;
				CurrentLocation.lat = mLatitude;
				CurrentLocation.lng = mLongitude;
				mLocation.stop();
				adapter.notifyDataSetChanged();
			}
		});
		mLocation.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mLocation.stop();
		CurrentLocation.status = LocationStatus.LOADING;
		removeOverlay();
	}

	/**
	 * 从数据库获取城市数据
	 * 
	 * @return
	 */
	private ArrayList<CityUtil> getCityNames() {
		ArrayList<CityUtil> names = new ArrayList<CityUtil>();
		CityUtil dfCityUtil = new CityUtil();
		dfCityUtil.setCityName("");
		dfCityUtil.setNameSort("GPS");
		names.add(dfCityUtil);

		Cursor cursor = database.rawQuery(
				"SELECT * FROM T_City ORDER BY NameSort", null);
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			CityUtil CityUtil = new CityUtil();
			CityUtil.setCityName(cursor.getString(cursor
					.getColumnIndex("CityName")));
			CityUtil.setNameSort(cursor.getString(cursor
					.getColumnIndex("NameSort")));
			names.add(CityUtil);
		}
		return names;
	}

	/**
	 * 城市列表点击事件
	 * 
	 * @author sy
	 * 
	 */
	class CityListOnItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			CityUtil CityUtil = (CityUtil) mCityList.getAdapter().getItem(pos);
//			Toast.makeText(CityListActivity.this, CityUtil.getCityName(),
//					Toast.LENGTH_SHORT).show();
			CurrentLocation.myHome = CityUtil.getCityName();
			finish();
			overridePendingTransition(R.anim.push_left_in, R.anim.roll_down);
		}

	}

	/**
	 * 为ListView设置适配器
	 * 
	 * @param list
	 */
	private void setAdapter(List<CityUtil> list) {
		if (list != null) {
			adapter = new CityListAdapter(this, list);
			mCityList.setAdapter(adapter);
		}

	}

	// 初始化汉语拼音首字母弹出提示框
	private void initOverlay() {
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}
	
	private void removeOverlay(){
		LayoutInflater inflater = LayoutInflater.from(this);
		WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		windowManager.removeView(overlay);
	}

	private class LetterListViewListener implements
			OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(final String s) {
			if (alphaIndexer == null) {
				alphaIndexer = adapter.getAlphaIndexer();
			}

			if (sections == null) {
				sections = adapter.getSections();
			}

			if (alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				LogUtil.d("test", "s = " + s + ", position=" + position);
				mCityList.setSelection(position);
				overlay.setText(sections[position]);
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				// 延迟一秒后执行，让overlay为不可见
				handler.postDelayed(overlayThread, 1500);
			}
		}

	}

	// 设置overlay不可见
	private class OverlayThread implements Runnable {

		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.custom_imageview_gohome:
			finish();
			overridePendingTransition(R.anim.push_left_in, R.anim.roll_down);
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.baidu.location.BDLocationListener#onReceiveLocation(com.baidu.location
	 * .BDLocation)
	 * 
	 * @Override public void onReceiveLocation(BDLocation bdlocation) { String
	 * city = bdlocation.getCity(); double mLatitude = bdlocation.getLatitude();
	 * double mLongitude = bdlocation.getLongitude(); LogUtil.d("Location",
	 * "mLocation = "+city); LogUtil.d("Location", "mLatitude = "+mLatitude);
	 * LogUtil.d("Location", "mLongitude = "+mLongitude);
	 * CurrentLocation.curLocation = city;
	 * 
	 * adapter.notifyDataSetChanged(); }
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.baidu.location.BDLocationListener#onReceivePoi(com.baidu.location
	 * .BDLocation)
	 * 
	 * @Override public void onReceivePoi(BDLocation bdlocation) { }
	 */

}
