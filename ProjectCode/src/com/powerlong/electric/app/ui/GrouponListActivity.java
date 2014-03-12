/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * FloorDetailActivity.java
 * 
 * 2013年8月19日-上午10:24:19
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.GrouponListAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.GrouponItemEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;

/**
 * 
 * GrouponListActivity  团购列表
 * 
 * @author: YangCheng Miao 2013年8月19日-上午10:24:19
 * 
 * @version 1.0.0
 * 
 */
public class GrouponListActivity extends BaseActivity implements OnClickListener {
	private PullToRefreshListView listView;
	private String method;
	private String methodParams;
	private ImageView ivReturn;
	private TextView txTitle;
	private ArrayAdapter<GrouponItemEntity> adapter;
	private LinearLayout llTabItem1, llTabItem2, llTabItem3, llTabItem4;
	private TextView tvSortItem1, tvSortItem2, tvSortItem3, tvSortItem4;

	private List<GrouponItemEntity> list_all_group = new ArrayList<GrouponItemEntity>();
	private int curPage = 0;
	private int curType = 0;//0：最新；1：美食；2：娱乐；3：购物；
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_groupon);
		
		Intent intent = getIntent();
		method = intent.getStringExtra("method");
		methodParams = intent.getStringExtra("methodParams");
        
		findView();
		getData();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void findView() {
		listView = (PullToRefreshListView) findViewById(R.id.lv_groupon_detail);
		listView.setMode(Mode.BOTH);
		txTitle = (TextView) findViewById(R.id.txTitle);
		txTitle.setText("团购");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(this);
//		ivFilter = (ImageView) findViewById(R.id.ivFilter);
//		ivFilter.setOnClickListener(this);
		tvSortItem1 = (TextView) findViewById(R.id.tv_sort_item1);
		tvSortItem1.setText("最新");
		tvSortItem2 = (TextView) findViewById(R.id.tv_sort_item2);
		tvSortItem2.setText("美食");
		tvSortItem3 = (TextView) findViewById(R.id.tv_sort_item3);
		tvSortItem3.setText("娱乐");
		tvSortItem4 = (TextView) findViewById(R.id.tv_sort_item4);
		tvSortItem4.setText("购物");
		llTabItem1 = (LinearLayout) findViewById(R.id.ll_tab_item1);
		llTabItem1.setOnClickListener(this);
		llTabItem2 = (LinearLayout) findViewById(R.id.ll_tab_item2);
		llTabItem2.setOnClickListener(this);
		llTabItem3 = (LinearLayout) findViewById(R.id.ll_tab_item3);
		llTabItem3.setOnClickListener(this);
		llTabItem4 = (LinearLayout) findViewById(R.id.ll_tab_item4);
		llTabItem4.setOnClickListener(this);
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				curPage = 1;
				list_all_group.clear();
				getData();
//				try {
//					if(StringUtil.isEmpty(methodParams)){
//							JSONObject obj = new JSONObject();
//							curPage = 1;
//							list_all_group.clear();
//							obj.put("page",curPage+"");
//							obj.put("mall",Constants.mallId+"");
//							obj.put("type",curType+"");
//							DataUtil.getGrouponListData(getBaseContext(), mServerConnectionHandler, obj.toString());
//					}else{
//						JSONObject obj = new JSONObject(methodParams);
//						obj.put(name, value)
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				try {
					if(StringUtil.isEmpty(methodParams)){
							JSONObject obj = new JSONObject();
							obj.put("page",++curPage);
							obj.put("mall",Constants.mallId+"");
							obj.put("type",curType+"");
							DataUtil.getGrouponListData(getBaseContext(), mServerConnectionHandler, obj.toString());
					}else{
						JSONObject obj = new JSONObject(methodParams);
						obj.put("page", ++curPage);
						obj.put("type",curType+"");
						DataUtil.getMatchedObjByParams(obj.toString(), Constants.KEYWORDS_TYPE_GOODS, mServerConnectionHandler);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	private void getData() {
		showLoadingDialog(null);
		if(StringUtil.isEmpty(methodParams)){
		JSONObject obj = new JSONObject();
		try {
			obj.put("page",curPage+"");
			obj.put("mall",Constants.mallId+"");
			obj.put("type",curType+"");
			DataUtil.getGrouponListData(getBaseContext(), mServerConnectionHandler, obj.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		}else{
			//DataUtil.getGrouponListData(getBaseContext(), mServerConnectionHandler,methodParams);
			DataUtil.getMatchedObjByParams(methodParams, Constants.KEYWORDS_TYPE_GOODS, mServerConnectionHandler);
		}
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("GrouponDetailActivity", "msg.what = " + msg.what);
			LogUtil.d("GrouponDetailActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				updateView(Integer.toString(msg.arg1));
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				finish();
				break;
			}
			listView.onRefreshComplete();
			dismissLoadingDialog();
		}

	};

	/**
	 * updateView(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @param navId
	 * @exception
	 * @since 1.0.0
	 */
	protected void updateView(String navId) {
		final ArrayList<GrouponItemEntity> entities = DataCache.NavGrouponListCache;
		GrouponItemEntity entity;
		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);
//			list.add(new NavigationGrouponDetailsEntity(entity.getLogo(), entity.getShopName(), entity.getBrief(), 
//					entity.getfScore(), entity.getClassification(), entity.getFavourNum()+""));
			list_all_group.add(entity);
		}
		LogUtil.d("BrandActivity", "list szie = " + list_all_group.size());
//		FloorDetailAdapterOrg adapter = new FloorDetailAdapterOrg(list,
//				FloorDetailActivity.this);
		adapter = new GrouponListAdapter(this, entities, listView.getRefreshableView());
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (entities != null) {
					long groupId;
					GrouponItemEntity entity = entities.get(arg2-1);
					groupId = entity.getId();
					Intent intent = new Intent(GrouponListActivity.this, GrouponDetailActivity.class);
					intent.putExtra("groupId", groupId);
					startActivity(intent);
				}
			}
		});
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivReturn:
			finish();
			break;
		case R.id.ll_tab_item1:
			llTabItem1.setBackgroundResource(R.drawable.tab_green1_press);
			tvSortItem1.setTextColor(getResources().getColor(R.color.white));
			llTabItem2.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem2.setTextColor(getResources().getColor(R.color.black));
			llTabItem3.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem3.setTextColor(getResources().getColor(R.color.black));
			llTabItem4.setBackgroundResource(R.drawable.tab_common3_nor);
			tvSortItem4.setTextColor(getResources().getColor(R.color.black));
			break;
		case R.id.ll_tab_item2:
			llTabItem1.setBackgroundResource(R.drawable.tab_common1_nor);
			tvSortItem1.setTextColor(getResources().getColor(R.color.black));
			llTabItem2.setBackgroundResource(R.drawable.tab_green2_press);
			tvSortItem2.setTextColor(getResources().getColor(R.color.white));
			llTabItem3.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem3.setTextColor(getResources().getColor(R.color.black));
			llTabItem4.setBackgroundResource(R.drawable.tab_common3_nor);
			tvSortItem4.setTextColor(getResources().getColor(R.color.black));
			break;
		case R.id.ll_tab_item3:
			llTabItem1.setBackgroundResource(R.drawable.tab_common1_nor);
			tvSortItem1.setTextColor(getResources().getColor(R.color.black));
			llTabItem2.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem2.setTextColor(getResources().getColor(R.color.black));
			llTabItem3.setBackgroundResource(R.drawable.tab_green2_press);
			tvSortItem3.setTextColor(getResources().getColor(R.color.white));
			llTabItem4.setBackgroundResource(R.drawable.tab_common3_nor);
			tvSortItem4.setTextColor(getResources().getColor(R.color.black));
			break;
		case R.id.ll_tab_item4:
			llTabItem1.setBackgroundResource(R.drawable.tab_common1_nor);
			tvSortItem1.setTextColor(getResources().getColor(R.color.black));
			llTabItem2.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem2.setTextColor(getResources().getColor(R.color.black));
			llTabItem3.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem3.setTextColor(getResources().getColor(R.color.black));
			llTabItem4.setBackgroundResource(R.drawable.tab_green3_press);
			tvSortItem4.setTextColor(getResources().getColor(R.color.white));
			break;
		}

	}

}
