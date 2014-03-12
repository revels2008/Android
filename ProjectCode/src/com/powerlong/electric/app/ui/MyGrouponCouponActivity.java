/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * MyCashCouponActivity.java
 * 
 * 2013-9-6-下午02:11:55
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.GrouponCouponEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.adapter.AdapterGroupBuyCoupons;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.LogUtil;

/**
 * 
 * MyCashCouponActivity
 * 
 * @author: Liang Wang
 * 2013-9-6-下午02:11:55
 * 
 * @version 1.0.0
 * 
 */
public class MyGrouponCouponActivity extends BaseActivity implements OnClickListener {
	ImageView ivBack;
	TextView tvTitle;
	
	PullToRefreshListView mListView = null;
	AdapterGroupBuyCoupons<GrouponCouponEntity> mAdapter = null;
	Button btnSortNotUsed = null;
	Button btnSortUsed = null;
	Button btnSortExpired= null;
	
	private int mCurPage = 1;
	private int mSortType = Constants.SortType.NOT_USED;
	TextView  tvTitleTips = null;
	RelativeLayout rlNoList = null;
	public static boolean isUsed = false;
	
	private List<GrouponCouponEntity> list_data = new ArrayList<GrouponCouponEntity>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_groupon_coupon);
		
		ivBack = (ImageView) findViewById(R.id.ivBack); 
		ivBack.setOnClickListener(this);
		
		tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(R.string.str_groupon);
		
		btnSortNotUsed = (Button)findViewById(R.id.sort_not_used);
		btnSortNotUsed.setOnClickListener(this);
		btnSortNotUsed.setSelected(true);
		
		btnSortUsed = (Button)findViewById(R.id.sort_used);
		btnSortUsed.setOnClickListener(this);
		
		btnSortExpired = (Button)findViewById(R.id.sort_expired);
		btnSortExpired.setOnClickListener(this);
		
		
		tvTitleTips = (TextView)findViewById(R.id.nomatchedtips);
		rlNoList = (RelativeLayout)findViewById(R.id.rl_no_list);
		tvTitleTips.setText(String.format(Html.fromHtml(getResources().getString(R.string.str_blank_list)).toString(),"团购券"));
		tvTitleTips.setVisibility(View.GONE);
		rlNoList.setVisibility(View.GONE);
		
		mListView = (PullToRefreshListView)findViewById(R.id.lvCashcoupon);
		mListView.setMode(Mode.BOTH);
		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				mCurPage = 1;
				mAdapter.clearData();
				getData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				mCurPage++;
				getData();
			}
		});
		mAdapter = new AdapterGroupBuyCoupons(this);
		mListView.setAdapter(mAdapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				GrouponCouponEntity entity = DataCache.MyGrouponCouponListCache.get(arg2-1);
				Intent intent = new Intent(MyGrouponCouponActivity.this, GrouponDetailActivity.class);
				intent.putExtra("groupId", entity.getId());
				startActivity(intent);
			}
		});
		
		getData();
	}
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("MyMessageActivity", "msg.what = " + msg.what);
			LogUtil.d("MyMessageActivity", "msg.arg1 = " + msg.arg1);
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
			mListView.onRefreshComplete();
		}
	};

	/**
	 * 从服务器拉取数据
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void getData() {
		showLoadingDialog(null);
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("page", mCurPage + "");
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
			obj.put("type", mSortType+"");
			DataUtil.queryGrouponCouponListData(getBaseContext(),
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
		list_data.addAll(DataCache.MyGrouponCouponListCache);
		if(list_data.size()<1){
			tvTitleTips.setVisibility(View.VISIBLE);
			rlNoList.setVisibility(View.VISIBLE);
		}else{
			rlNoList.setVisibility(View.GONE);
			tvTitleTips.setVisibility(View.GONE);
			mAdapter.setList(list_data);
			mAdapter.notifyDataSetChanged();
		}
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.ivBack:
			finish();
			break;
		case R.id.sort_expired:
			if(mSortType!=Constants.SortType.EXPIRED){
				mSortType=Constants.SortType.EXPIRED;
				mAdapter.clearData();
				list_data.clear();
				updateSortBar(Constants.SortType.EXPIRED);
				
			}
			break;
		case R.id.sort_used:
			if(mSortType!=Constants.SortType.USED){
				isUsed = true;
				mSortType=Constants.SortType.USED;
				mAdapter.clearData();
				list_data.clear();
				updateSortBar(Constants.SortType.USED);
				
			}
			break;
		case R.id.sort_not_used:
			if(mSortType!=Constants.SortType.NOT_USED){
				isUsed = false;
				mSortType=Constants.SortType.NOT_USED;
				mAdapter.clearData();
				list_data.clear();
				updateSortBar(Constants.SortType.NOT_USED);
				
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
		btnSortExpired.setSelected((status==Constants.SortType.EXPIRED)?true:false);
		btnSortUsed.setSelected((status==Constants.SortType.USED)?true:false);
		btnSortNotUsed.setSelected((status==Constants.SortType.NOT_USED)?true:false);
		getData();
	}
}
