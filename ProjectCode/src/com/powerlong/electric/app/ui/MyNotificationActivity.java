/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * MyNotificationActivity.java
 * 
 * 2013-9-6-下午6:24:33
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.MyNotificationAdapter;
import com.powerlong.electric.app.adapter.MyNotificationAdapter.ViewHolder;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.ToastUtil;
import com.powerlong.electric.app.widget.CollapsibleTextView;

/**
 * 
 * MyNotificationActivity
 * 
 * @author: Liang Wang 2013-9-6-下午6:24:33
 * 
 * @version 1.0.0
 * 
 */
public class MyNotificationActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {
	ImageView ivBack;
	TextView tvTitle;

	ListView mListView = null;
	MyNotificationAdapter mAdapter = null;

	Button btnSortAll = null;
	Button btnSortTrade= null;
	Button btnSortSystem= null;
	
	TextView  tvTitleTips = null;
	
	private int mCurPage = 1;
	private int mSortType = 9;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_notification);

		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);

		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText(R.string.str_notification);
		
		btnSortAll = (Button)findViewById(R.id.sort_all);
		btnSortAll.setOnClickListener(this);
		btnSortAll.setSelected(true);
		
		btnSortTrade = (Button)findViewById(R.id.sort_trade);
		btnSortTrade.setOnClickListener(this);
		
		btnSortSystem = (Button)findViewById(R.id.sort_system);
		btnSortSystem.setOnClickListener(this);
		
		tvTitleTips = (TextView)findViewById(R.id.nomatchedtips);
		tvTitleTips.setText(String.format(Html.fromHtml(getResources().getString(R.string.str_blank_list)).toString(),"通知"));
		tvTitleTips.setVisibility(View.GONE);
		
		
		mListView = (ListView) findViewById(R.id.lvNotification);
		mAdapter = new MyNotificationAdapter(this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		getData();
	}

	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		
	}
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("MyNotificationActivity", "msg.what = " + msg.what);
			LogUtil.d("MyNotificationActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				updateView();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
				showCustomToast(fail);
				break;
			}
			dismissLoadingDialog();
		}
	};

	/**
	 * 从服务器拉取数据
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void getData() {
		tvTitleTips.setVisibility(View.GONE);
		showLoadingDialog(null);
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("page", mCurPage + "");
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
			obj.put("type", mSortType+"");
			DataUtil.queryMyNotifyListData(getBaseContext(),
					mServerConnectionHandler, obj.toString());
		} catch (JSONException e) {
			dismissLoadingDialog();
			e.printStackTrace();
		}
	}
	
	/**
	 * 刷新列表
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	protected void updateView() {
		if(DataCache.MyNotifyListCache.size()==0){
			tvTitleTips.setVisibility(View.VISIBLE);
		}else{
			mAdapter.notifyDataSetChanged();
			tvTitleTips.setVisibility(View.GONE);
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
		case R.id.ivBack:
			finish();
			break;
		case R.id.sort_trade:
			if(mSortType!=Constants.SortType.TRADE){
				mSortType = Constants.SortType.TRADE;
				mAdapter.clear();
				updateSortBar(Constants.SortType.TRADE);
			}
			break;
		case R.id.sort_system:
			if(mSortType!=Constants.SortType.SYSTEM){
				mSortType = Constants.SortType.SYSTEM;
				mAdapter.clear();
				updateSortBar(Constants.SortType.SYSTEM);
			}
			break;
		case R.id.sort_all:
			if(mSortType!=Constants.SortType.ALL){
				mSortType = Constants.SortType.ALL;
				mAdapter.clear();
				updateSortBar(Constants.SortType.ALL);
			}
			break;
		}
	}

	/**
	 * 更新分类按钮状态
	 * @param status 当前分类编号
	 * @exception 
	 * @since  1.0.0
	*/
	private void updateSortBar(int status) {
		btnSortAll.setSelected((status==Constants.SortType.ALL)?true:false);
		btnSortTrade.setSelected((status==Constants.SortType.TRADE)?true:false);
		btnSortSystem.setSelected((status==Constants.SortType.SYSTEM)?true:false);
		getData();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int id,
			long position) {

		int visiblePos = mListView.getFirstVisiblePosition();
		int offset = (int) (position - visiblePos);

		// 只有在可见区域才更新
		if (offset < 0)
			return;

		View child = mListView.getChildAt(offset);
		ViewHolder holder = (ViewHolder) child.getTag();

		holder.content.updateView(holder.content);
	}
}
