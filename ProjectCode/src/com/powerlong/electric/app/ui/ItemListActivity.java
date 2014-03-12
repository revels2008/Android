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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
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
import com.powerlong.electric.app.adapter.IdeasExpandableListAdapter;
import com.powerlong.electric.app.adapter.IdeasExpandableListAdapter.GroupViewHolder;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.FilterEntity;
import com.powerlong.electric.app.entity.GrouponItemEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.slidingmenu.SlidingMenu;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.ui.base.BaseSlidingFragmentActivity;
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
public class ItemListActivity extends BaseSlidingFragmentActivity implements OnClickListener, OnGroupClickListener, OnChildClickListener {
	private PullToRefreshListView listView;
	private String method;
	private String methodParams;
	private String itemName;
	private ImageView ivReturn;
	private TextView txTitle;
	private ArrayAdapter<GrouponItemEntity> adapter;
	private LinearLayout llTabItem1, llTabItem2, llTabItem3,llTabItem4;
	private TextView tvSortItem1, tvSortItem2, tvSortItem3;

	private List<GrouponItemEntity> list_allData = new ArrayList<GrouponItemEntity>();
	private int curPage = 1;
	private int curType = 0;//0：最新；1：美食；2：娱乐；3：购物；
	private SlidingMenu sm;
	private ImageView ivFilter;
	private ImageView ivHome;
	private Number price_start = 0;
	private Number price_end = Integer.MAX_VALUE;
	private ExpandableListView mExpandableListView;
	private IdeasExpandableListAdapter mExpandableListAdapter;
	private boolean onlyItem = false;
	private boolean isRaise = true;
	private boolean isRefresh = true;
	private int numOfPage = 0;
	
	private Handler mRefreshListViewHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			LogUtil.e(SearchBaseActivityNew.class.getName(), "handleMessage...");
			updateView(msg.what, msg.arg1, msg.arg2);
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_list);
		initSlidingMenu();
		Intent intent = getIntent();
		method = intent.getStringExtra("method");
		methodParams = intent.getStringExtra("methodParams");
		itemName = intent.getStringExtra("itemName");
        
		findView();
		getData();
	}
	
	private void initSlidingMenu() {
		setBehindContentView(R.layout.filter_behind_slidingmenu);

		// customize the SlidingMenu
		sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setShadowDrawable(R.drawable.slidingmenu_shadow);
		sm.setShadowWidth(10);
		sm.setMode(SlidingMenu.RIGHT);
		// sm.setBehindScrollScale(0.333f);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	private void findView() {
		listView = (PullToRefreshListView) findViewById(R.id.lv_groupon_detail);
		listView.setMode(Mode.BOTH);
		txTitle = (TextView) findViewById(R.id.txTitle);
		txTitle.setText(itemName);
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(this);
//		ivFilter = (ImageView) findViewById(R.id.ivFilter);
//		ivFilter.setOnClickListener(this);
		tvSortItem1 = (TextView) findViewById(R.id.tv_sort_item1);
		tvSortItem1.setText("人气");
		tvSortItem2 = (TextView) findViewById(R.id.tv_sort_item2);
		tvSortItem3 = (TextView) findViewById(R.id.tv_sort_item3);
		llTabItem1 = (LinearLayout) findViewById(R.id.ll_tab_item1);
		llTabItem1.setOnClickListener(this);
		llTabItem2 = (LinearLayout) findViewById(R.id.ll_tab_item2);
		llTabItem2.setOnClickListener(this);
		llTabItem3 = (LinearLayout) findViewById(R.id.ll_tab_item3);
		llTabItem3.setOnClickListener(this);
		llTabItem4 = (LinearLayout) findViewById(R.id.ll_tab_item4);
		llTabItem4.setVisibility(View.GONE);
		ivFilter = (ImageView) findViewById(R.id.ivFilter);
		ivFilter.setOnClickListener(this);
		ivHome = (ImageView) findViewById(R.id.rfilterHome);
		ivHome.setOnClickListener(this);
		mExpandableListAdapter = new IdeasExpandableListAdapter(this,
				mRefreshListViewHandler,onlyItem);
		mExpandableListView = (ExpandableListView) findViewById(R.id.filterExList);
		mExpandableListView.setAdapter(mExpandableListAdapter);
		mExpandableListView.setOnGroupClickListener(this);
		mExpandableListView.setOnChildClickListener(this);
		mExpandableListAdapter.setOnSeekBarRangeListener(new IdeasExpandableListAdapter.OnSeekBarRangeListener() {
			
			@Override
			public void onSeekBarRanged(Number min, Number max) {
				price_start = min;
				price_end = max;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (list_allData != null) {
					long groupId;
					GrouponItemEntity entity = list_allData.get(arg2-1);
					groupId = entity.getId();
					Intent intent = new Intent(ItemListActivity.this, ItemDetailActivity.class);
					intent.putExtra("itemId", groupId);
					startActivity(intent);
				}
			}
		});
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				try {
					if(methodParams!=null){
						JSONObject jsObj = new JSONObject(methodParams);
						curPage = 1;
						jsObj.put("page",curPage);
						jsObj.put("priceFrom", price_start);
						jsObj.put("priceTo", price_end);
						list_allData.clear();
						methodParams = jsObj.toString();
					}else{
						curPage = 1;
						
					}
					isRefresh = true;
					getData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				try {
					if(methodParams!=null){
						JSONObject jsObj = new JSONObject(methodParams);
						jsObj.put("page",++curPage);
						jsObj.put("priceFrom", price_start);
						jsObj.put("priceTo", price_end);
						methodParams = jsObj.toString();
					}else{
						curPage++;
						
					}
					isRefresh = false;
					getData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void getData() {
		showLoadingDialog(null);
		if (StringUtil.isEmpty(methodParams)) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("page", curPage );
				obj.put("mall", Constants.mallId );
				obj.put("type", curType);
				DataUtil.getGrouponListData(getBaseContext(),
						mServerConnectionHandler, obj.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			DataUtil.getMatchedObjByParams(methodParams,
					Constants.KEYWORDS_TYPE_GOODS, mServerConnectionHandler);
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
			dismissLoadingDialog();
			listView.onRefreshComplete();
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
			list_allData.add(entity);
		}
		adapter = new GrouponListAdapter(this, list_allData, listView.getRefreshableView());
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		if(isRefresh){
			numOfPage = listView.getRefreshableView().getAdapter().getCount();
			listView.getRefreshableView().setSelection(0);
		}else{
			listView.getRefreshableView().setSelection(listView.getRefreshableView().getAdapter().getCount()-numOfPage);
		}
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
			curType = Constants.SortType.POPULAR;
			llTabItem1.setBackgroundResource(R.drawable.tab_green1_press);
			tvSortItem1.setTextColor(getResources().getColor(R.color.white));
			llTabItem2.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem2.setTextColor(getResources().getColor(R.color.black));
			llTabItem3.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem3.setTextColor(getResources().getColor(R.color.black));
			try {
				JSONObject jsObj = new JSONObject(methodParams);
				curPage = 1;
				jsObj.put("page",curPage);
				jsObj.put("orderby", curType);
				jsObj.put("priceFrom", price_start);
				jsObj.put("priceTo", price_end);
				list_allData.clear();
				methodParams = jsObj.toString();
				getData();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.ll_tab_item2:
			curType = Constants.SortType.SALES;
			llTabItem1.setBackgroundResource(R.drawable.tab_common1_nor);
			tvSortItem1.setTextColor(getResources().getColor(R.color.black));
			llTabItem2.setBackgroundResource(R.drawable.tab_green2_press);
			tvSortItem2.setTextColor(getResources().getColor(R.color.white));
			llTabItem3.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem3.setTextColor(getResources().getColor(R.color.black));
			try {
				JSONObject jsObj = new JSONObject(methodParams);
				curPage = 1;
				jsObj.put("page",curPage);
				jsObj.put("orderby", curType);
				jsObj.put("priceFrom", price_start);
				jsObj.put("priceTo", price_end);
				list_allData.clear();
				methodParams = jsObj.toString();
				getData();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.ll_tab_item3:
			if(isRaise) {
				isRaise = false;
				curType = Constants.SortType.PRICE;
				try {
					JSONObject jsObj = new JSONObject(methodParams);
					curPage = 1;
					jsObj.put("page",curPage);
					jsObj.put("orderby", curType);
					jsObj.put("priceFrom", price_start);
					jsObj.put("priceTo", price_end);
					list_allData.clear();
					methodParams = jsObj.toString();
					updatePriceIndicator("2");
					getData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				isRaise = true;
				curType = 3;
				try {
					JSONObject jsObj = new JSONObject(methodParams);
					curPage = 1;
					jsObj.put("page",curPage);
					jsObj.put("orderby", curType);
					jsObj.put("priceFrom", price_start);
					jsObj.put("priceTo", price_end);
					list_allData.clear();
					methodParams = jsObj.toString();
					updatePriceIndicator("3");
					getData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			llTabItem1.setBackgroundResource(R.drawable.tab_common1_nor);
			tvSortItem1.setTextColor(getResources().getColor(R.color.black));
			llTabItem2.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem2.setTextColor(getResources().getColor(R.color.black));
			llTabItem3.setBackgroundResource(R.drawable.tab_green2_press);
			tvSortItem3.setTextColor(getResources().getColor(R.color.white));
			
			break;
		case R.id.ivFilter:
			showMenu();
			break;
		case R.id.rfilterHome:
			try {
				JSONObject jsObj = new JSONObject(methodParams);
				curPage = 1;
				jsObj.put("page",curPage);
				jsObj.put("orderby", curType);
				jsObj.put("priceFrom", price_start);
				jsObj.put("priceTo", price_end);
				list_allData.clear();
				methodParams = jsObj.toString();
				getData();
			} catch (Exception e) {
				e.printStackTrace();
			}
			showContent();
		}
		}

	private void updatePriceIndicator(String type) {
		Drawable right = null;
		right = getResources().getDrawable(
				type == "3" ? R.drawable.selector_sort_down
						: R.drawable.selector_sort_up);
		right.setBounds(0, 0, right.getIntrinsicWidth(),
				right.getIntrinsicHeight());
		tvSortItem3.setCompoundDrawables(null, null, right, null);
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListView.OnChildClickListener#onChildClick(android.widget.ExpandableListView, android.view.View, int, int, long)
	 */
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListView.OnGroupClickListener#onGroupClick(android.widget.ExpandableListView, android.view.View, int, long)
	 */
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		// TODO Auto-generated method stub
		return false;
	}
	private void updateView(int msgType, int groupPosition, int childPosition) {
		// 得到第1个可显示控件的位置,记住是第1个可显示控件噢。而不是第1个控件
		int visiblePosition = mExpandableListView.getFirstVisiblePosition();
		LogUtil.e(SearchBaseActivityNew.class.getName(), "visiblePosition ="
				+ visiblePosition);
		LogUtil.e(SearchBaseActivityNew.class.getName(), "groupPosition ="
				+ groupPosition);
		int itemIndex = 0;
		if(!onlyItem){
			if (groupPosition == 1 && msgType == 1) {
				for (int i = 0; i < groupPosition; i++) {
					boolean expanded = mExpandableListView.isGroupExpanded(i);
					if (expanded)
						itemIndex += mExpandableListAdapter.getChildrenCount(i);
					else
						itemIndex += i;
				}
	
				itemIndex += groupPosition;
				LogUtil.e(SearchBaseActivityNew.class.getName(), "itemIndex ="
						+ itemIndex);
				// 得到你需要更新item的View
				View view = mExpandableListView.getChildAt(itemIndex
						- visiblePosition);
				GroupViewHolder holder = new GroupViewHolder();
				holder.tvGroupValue = (TextView) view.findViewById(R.id.exValue);
				String content = null;
				int childCount = mExpandableListAdapter
						.getChildrenCount(groupPosition);
	
				for (int i = 0; i < childCount; i++) {
					FilterEntity childEntity = DataCache.ExShopServiceChecked
							.get(Integer.valueOf(i));
					if (childEntity != null && childEntity.getChildId() == i) {
						if (StringUtil.isEmpty(content)) {
							content = childEntity.getValue();
						} else {
							content += "," + childEntity.getValue();
						}
					}
				}
				holder.tvGroupValue.setText(content);
			} else if (groupPosition == 0 && msgType == 2) {
				itemIndex = visiblePosition;
				View view = mExpandableListView.getChildAt(itemIndex
						- visiblePosition);
				GroupViewHolder holder = new GroupViewHolder();
				holder.tvGroupValue = (TextView) view.findViewById(R.id.exValue);
				FilterEntity childEntity = DataCache.ExPriceChecked
						.get(groupPosition);
				String content = null;
				if (childEntity != null) {
					content = ""
							+ childEntity.getMinium()
							+ "-"
							+ ""
							+ (childEntity.getMaxium().intValue() < 6000 ? childEntity
									.getMaxium() : "∞");
				}
				holder.tvGroupValue.setText(content);
			}
		}else{
			if (groupPosition == 0 && msgType == 1) {
				for (int i = 0; i < groupPosition; i++) {
					boolean expanded = mExpandableListView.isGroupExpanded(i);
					if (expanded)
						itemIndex += mExpandableListAdapter.getChildrenCount(i);
					else
						itemIndex += i;
				}
	
				itemIndex += groupPosition;
				LogUtil.e(SearchBaseActivityNew.class.getName(), "itemIndex ="
						+ itemIndex);
				// 得到你需要更新item的View
				View view = mExpandableListView.getChildAt(itemIndex
						- visiblePosition);
				GroupViewHolder holder = new GroupViewHolder();
				holder.tvGroupValue = (TextView) view.findViewById(R.id.exValue);
				String content = null;
				int childCount = mExpandableListAdapter
						.getChildrenCount(groupPosition);
	
				for (int i = 0; i < childCount; i++) {
					FilterEntity childEntity = DataCache.ExBarginChecked
							.get(Integer.valueOf(i));
					if (childEntity != null && childEntity.getChildId() == i) {
						if (StringUtil.isEmpty(content)) {
							content = childEntity.getValue();
						} else {
							content += "," + childEntity.getValue();
						}
					}
				}
				holder.tvGroupValue.setText(content);
			} 
		}
	}

}
