/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * SearchBaseActivity.java
 * 
 * 2013-8-12-下午04:26:31
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.GrouponListAdapter;
import com.powerlong.electric.app.adapter.IdeasExpandableListAdapter;
import com.powerlong.electric.app.adapter.IdeasExpandableListAdapter.GroupViewHolder;
import com.powerlong.electric.app.adapter.ShopListAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.config.Constants.FilterType;
import com.powerlong.electric.app.config.Constants.SortType;
import com.powerlong.electric.app.domain.DomainSearchCategorySub;
import com.powerlong.electric.app.domain.DomainSearchHistory;
import com.powerlong.electric.app.entity.FilterEntity;
import com.powerlong.electric.app.entity.FloorDetailEntity;
import com.powerlong.electric.app.entity.GrouponItemEntity;
import com.powerlong.electric.app.entity.NavigationFloorDetailsEntity;
import com.powerlong.electric.app.entity.SearchCategoryDetailEntity;
import com.powerlong.electric.app.entity.SearchCategoryEntity;
import com.powerlong.electric.app.entity.SearchResultEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.slidingmenu.SlidingMenu;
import com.powerlong.electric.app.ui.base.BaseSlidingFragmentActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;

/**
 * 
 * SearchBaseActivity:搜索后进入的详情页面视图
 * 
 * @author: Liang Wang 2013-8-12-下午04:26:31
 * 
 * @version 1.0.0
 * 
 */
public class SearchBaseActivityNew extends BaseSlidingFragmentActivity implements
		OnClickListener, OnGroupClickListener, OnChildClickListener,
		OnFocusChangeListener, TextWatcher, OnEditorActionListener {

	private SlidingMenu sm = null;
	private Button btnGoods = null;
	private Button btnShops = null;
	private ImageView ivReturn = null;
	private ImageView ivFilter = null;
	private String itemName;
	private LinearLayout llPopular = null;
	private LinearLayout llSales = null;
	private LinearLayout llPrice = null;
	private LinearLayout llTypeBar = null;
	private int iCurSortType = SortType.POPULAR;
	private int iPreSortType = SortType.POPULAR;
	private TextView tvPopular = null;
	private TextView tvSales = null;
	private TextView tvPrice = null;

	//private ImageView ivPopular = null;
	//private ImageView ivSales = null;
	//private ImageView ivPrice = null;

	private boolean bDesend = true;

	private EditText ivSearch = null;

	int STATUS_BAR_HEIGHT = 0;

	private RelativeLayout rlButtonBar = null;
	private Button btnBarBack = null;
	private ImageView ivBarHome = null;

	private ImageView ivHome = null;

	private ExpandableListView mExpandableListView = null;
	private IdeasExpandableListAdapter mExpandableListAdapter = null;

	private int mFilterType = Constants.FilterType.ALL;
	private String mFilter = "";
	private String rootFilter = "";
	private PullToRefreshListView mListView = null;
	private GrouponListAdapter mItemAdapter = null;
	private ShopListAdapter mShopAdapter = null;
	private EditText evSearch = null;
	private ImageView ivClose = null;
	InputMethodManager imm = null;

	TextView tvTips = null;
	
	private View vDivder1 = null;
	private View vDivder2 = null;
	private View vDivder3 = null;
	private boolean onlyItem = false;
	
	
	private static final int ORDERTYPRE_POPULAR = 0;//人气降序
	private static final int ORDERTYPRE_SALES = 1;//销量降序
	private static final int ORDERTYPRE_PRICE_ASC = 2;//价格升序
	private static final int ORDERTYPRE_PRICE_DESC = 3;//价格降序
	
	private int orderType = ORDERTYPRE_POPULAR;
	
	private static Object entityObj;
	
	private Number price_start = 0;
	private Number price_end = Integer.MAX_VALUE;
	
	private List<GrouponItemEntity> list_all_groupItem = new ArrayList<GrouponItemEntity>();
	private List<FloorDetailEntity> list_all_floor_detail = new ArrayList<FloorDetailEntity>();
	
	private int curSelectPosition =0;
	private int numOfPage = 0;
	
	private Handler mRefreshListViewHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			LogUtil.e(SearchBaseActivityNew.class.getName(), "handleMessage...");
			updateView(msg.what, msg.arg1, msg.arg2);
		}
	};

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
	
	@Override
	protected void onRestart() {
		super.onRestart();
		curPage = 1;
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {
		@Override
		public void handleMessage(Message msg) {
			LogUtil.d("SearchBaseActivity", "msg.what = " + msg.what);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				if (mFilterType == Constants.FilterType.GOODS) {
					if (DataCache.NavGrouponListCache.size() == 0 && list_all_groupItem.size()<1){
						showHasNoMatchedTips(true, mFilter, mFilterType);
					}
					list_all_groupItem.addAll(DataCache.NavGrouponListCache); 
					mItemAdapter = new GrouponListAdapter(
							SearchBaseActivityNew.this,
							list_all_groupItem, mListView.getRefreshableView());
					mListView.setAdapter(mItemAdapter);
					mListView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
//							ArrayList<GrouponItemEntity> entities = DataCache.NavGrouponListCache;
							GrouponItemEntity entity = list_all_groupItem.get(arg2-1);
							long itemId = entity.getId();
							itemName = entity.getName();
							Intent intent = new Intent(SearchBaseActivityNew.this, ItemDetailActivity.class);
							intent.putExtra("itemId", itemId);
							intent.putExtra("itemName", itemName);
							startActivity(intent);
						}
					});
					if(curPage > 1 && list_all_groupItem.size()>1){
							mListView.getRefreshableView().setSelection((numOfPage-2)*(curPage-1));
						
					}else{
						mListView.getRefreshableView().setSelection(0);
						numOfPage = mListView.getRefreshableView().getAdapter().getCount();
					}
				} else if (mFilterType == Constants.FilterType.SHOPS) {
					final ArrayList<NavigationFloorDetailsEntity> entities = DataCache.SearchShopResultCache;
					if(entities.size()==0 && list_all_floor_detail.size()<1){
						showHasNoMatchedTips(true, mFilter, mFilterType);
					}
					NavigationFloorDetailsEntity entity;
//					List<FloorDetailEntity> list = new ArrayList<FloorDetailEntity>();
					for (int i = 0; i < entities.size(); i++) {
						entity = entities.get(i);
						list_all_floor_detail.add(new FloorDetailEntity(entity.getLogo(), entity
								.getShopName(), entity.getBrief(), entity
								.getfScore(), entity.getClassification(),
								entity.getFavourNum() + "",entity.getSelfId()));
					}
					mShopAdapter = new ShopListAdapter(SearchBaseActivityNew.this,
							list_all_floor_detail, mListView.getRefreshableView());
					mListView.setAdapter(mShopAdapter);
					mListView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
//							NavigationFloorDetailsEntity entity = ;
							long shopId = list_all_floor_detail.get(arg2-1).getSelfId();
//							itemName = entity.								
							Intent intent = new Intent(SearchBaseActivityNew.this, ShopDetailActivityNew.class);
//							intent.putExtra("itemId", itemId);
							intent.putExtra("shopId", shopId);
							startActivity(intent);
						}
					});
					if(curPage > 1 && list_all_groupItem.size()>1){
						mListView.getRefreshableView().setSelection((numOfPage-2)*(curPage-1));
					}else{
						mListView.getRefreshableView().setSelection(0);
						numOfPage = mListView.getRefreshableView().getAdapter().getCount();
					}
				}

				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
			mListView.onRefreshComplete();
			dismissLoadingDialog();
		}
	};
	
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.powerlong.electric.app.ui.base.BaseActivity#onCreate(android.os.Bundle
	 * )
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initSlidingMenu();
		setContentView(R.layout.basic_listactivity_layout_new);

		DisplayMetrics metrics = getResources().getDisplayMetrics();
		STATUS_BAR_HEIGHT = (int) Math.ceil(25 * metrics.density);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("params");
		entityObj = bundle.get("params");
		curPage = 1;
		btnGoods = (Button) findViewById(R.id.type_good);
		btnGoods.setOnClickListener(this);
		// btnGoods.setSelected(true);
		btnShops = (Button) findViewById(R.id.type_shop);
		btnShops.setOnClickListener(this);
		
		llTypeBar = (LinearLayout)findViewById(R.id.type);
		vDivder1 = (View)findViewById(R.id.divider1);

		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		evSearch = (EditText) findViewById(R.id.evSearch);
//		evSearch.setOnFocusChangeListener(this);
//		evSearch.setOnEditorActionListener(this);
//		evSearch.addTextChangedListener(this);
//		evSearch.setOnEditorActionListener(new OnEditorActionListener() {
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				if(KeyEvent.KEYCODE_ENTER == event.getKeyCode()){
//					getData();
//				}
//				return true;
//			}
//		});
		evSearch.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
					list_all_groupItem.clear();
					curPage = 1;
					getData();
					return true;
				}
				return false;
			}
		});
		ivClose = (ImageView) findViewById(R.id.ivClose);
		ivClose.setOnClickListener(this);

		tvTips = (TextView) findViewById(R.id.nomatchedtips);
		tvTips.setVisibility(View.GONE);

		
		if (entityObj instanceof SearchResultEntity) {
			LogUtil.d("SearchBaseActivity",
					"obj instance of SearchResultEntity");
			rootFilter = ((SearchResultEntity) entityObj).getResultText();
			mFilterType = ((SearchResultEntity) entityObj).getResultType();
			btnShops.setVisibility(View.VISIBLE);
			btnShops.setSelected(mFilterType == Constants.FilterType.SHOPS ? true
					: false);
			btnGoods.setVisibility(View.VISIBLE);
			btnGoods.setSelected(mFilterType == Constants.FilterType.GOODS ? true
					: false);
			vDivder1.setVisibility(View.VISIBLE);
			llTypeBar.setVisibility(View.VISIBLE);
//			if(mFilterType == Constants.FilterType.SHOPS){
//				onlyItem = true;
//			}else{
//				onlyItem = false;
//			}
		} else if (entityObj instanceof SearchCategoryDetailEntity) {
			LogUtil.d("SearchBaseActivity", "obj instance of String");
			rootFilter = ((SearchCategoryDetailEntity) entityObj).getName();
			mFilterType = Constants.FilterType.GOODS;
			btnShops.setVisibility(View.GONE);
			btnShops.setSelected(mFilterType == Constants.FilterType.SHOPS ? true
					: false);
			btnGoods.setVisibility(View.GONE);
			btnGoods.setSelected(mFilterType == Constants.FilterType.GOODS ? true
					: false);
			evSearch.setHint("在\"" + rootFilter + "\"下搜索");
			vDivder1.setVisibility(View.GONE);
			llTypeBar.setVisibility(View.GONE);
			onlyItem = false;
		} else if (entityObj instanceof SearchCategoryEntity) {
			LogUtil.d("SearchBaseActivity", "obj instance of String");
			rootFilter = ((SearchCategoryEntity) entityObj).getName();
			mFilterType = Constants.FilterType.GOODS;
			btnShops.setVisibility(View.GONE);
			btnShops.setSelected(mFilterType == Constants.FilterType.SHOPS ? true
					: false);
			btnGoods.setVisibility(View.GONE);
			btnGoods.setSelected(mFilterType == Constants.FilterType.GOODS ? true
					: false);
			evSearch.setHint("在\"" + rootFilter + "\"下搜索");
			vDivder1.setVisibility(View.GONE);
			llTypeBar.setVisibility(View.GONE);
			onlyItem = false;
		}else if(entityObj instanceof DomainSearchCategorySub){
			rootFilter = ((DomainSearchCategorySub) entityObj).getName();
			mFilterType = Constants.FilterType.GOODS;
			btnShops.setVisibility(View.GONE);
			btnShops.setSelected(mFilterType == Constants.FilterType.SHOPS ? true
					: false);
			btnGoods.setVisibility(View.GONE);
			btnGoods.setSelected(mFilterType == Constants.FilterType.GOODS ? true
					: false);
			evSearch.setHint("在\"" + rootFilter + "\"下搜索");
			vDivder1.setVisibility(View.GONE);
			llTypeBar.setVisibility(View.GONE);
			onlyItem = false;
		}else if(entityObj instanceof DomainSearchHistory){
			rootFilter = ((DomainSearchHistory) entityObj).getSearchWord();
			mFilterType = ((DomainSearchHistory) entityObj).getType();
			btnShops.setVisibility(View.VISIBLE);
			btnShops.setSelected(mFilterType == Constants.FilterType.SHOPS ? true
					: false);
			btnGoods.setVisibility(View.VISIBLE);
			btnGoods.setSelected(mFilterType == Constants.FilterType.GOODS ? true
					: false);
			vDivder1.setVisibility(View.VISIBLE);
			llTypeBar.setVisibility(View.VISIBLE);
//			if(mFilterType == Constants.FilterType.SHOPS){
//				onlyItem = true;
//			}else{
//				onlyItem = false;
//			}
		}
		
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(this);
		ivFilter = (ImageView) findViewById(R.id.ivFilter);
		ivFilter.setOnClickListener(this);

		llPopular = (LinearLayout) findViewById(R.id.ll_popular);
		llPopular.setOnClickListener(this);
		llSales = (LinearLayout) findViewById(R.id.ll_sales);
		llSales.setOnClickListener(this);
		llPrice = (LinearLayout) findViewById(R.id.ll_price);
		llPrice.setOnClickListener(this);

		tvPopular = (TextView) findViewById(R.id.popular);
		tvPopular.setCompoundDrawables(null, null, null, null);
		tvSales = (TextView) findViewById(R.id.sales);
		tvSales.setCompoundDrawables(null, null, null, null);
		tvPrice = (TextView) findViewById(R.id.price);

		//ivPopular = (ImageView) findViewById(R.id.arrow1);
		//ivSales = (ImageView) findViewById(R.id.arrow2);
		//ivPrice = (ImageView) findViewById(R.id.arrow3);

		ivSearch = (EditText) findViewById(R.id.evSearch);
//		ivSearch.setOnEditorActionListener(new OnEditorActionListener() {
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				getData();
//				return true;
//			}
//		});
		rlButtonBar = (RelativeLayout) findViewById(R.id.rlButton);
		btnBarBack = (Button) findViewById(R.id.filterBack);
		btnBarBack.setOnClickListener(this);
		ivBarHome = (ImageView) findViewById(R.id.filterHome);
		ivBarHome.setOnClickListener(this);
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
		mListView = (PullToRefreshListView) findViewById(R.id.basic_list);
		mListView.setMode(Mode.BOTH);
		mListView.setOnPullEventListener(new OnPullEventListener<ListView>() {
			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView,
					State state, Mode direction) {
				if(state == State.REFRESHING || state == State.PULL_TO_REFRESH ||state == State.RELEASE_TO_REFRESH){
					showHasNoMatchedTips(false, mFilter, mFilterType);
				}
			}
		});
		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>(){
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				curPage = 1;
				switch (mFilterType) {
					case Constants.FilterType.GOODS:
						list_all_groupItem.clear();
						break;
					case Constants.FilterType.SHOPS:
						list_all_floor_detail.clear();
						break;
	
					default:
						break;
				}
				getData();
			}
			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				curPage++;
				getData();
			}} );
			switch (mFilterType) {
				case Constants.FilterType.GOODS:
					tvPrice.setText("价格");
					tvSales.setText("销量");
					break;
				case Constants.FilterType.SHOPS:
					tvPrice.setText("等级");
					tvSales.setText("类别");
					break;
			}
		getData();
	}
	
	private void getData(){
		try {
			showHasNoMatchedTips(false,mFilter,mFilterType);
			DataUtil.getMatchedObjByParams(makeParams(entityObj),mFilterType,
					mServerConnectionHandler);
		} catch (Exception e) {
			dismissLoadingDialog();
			showCustomToast("获取参数失败!");
			e.printStackTrace();
		}
	}
	
	private static int curPage = 1;
	
	private String makeParams(Object obj) throws Exception{
		showLoadingDialog(null);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("mall", Constants.mallId);
		jsonObj.put("page", curPage);
		LogUtil.d("SearchBaseActivityNew ", "makeParams orderType ="+orderType);
		jsonObj.put("orderby", orderType);
		jsonObj.put("priceFrom", price_start);
		jsonObj.put("priceTo", price_end);
		if (mFilterType == Constants.KEYWORDS_TYPE_HOTWORDS) {
			jsonObj.put("classification", "keyword");
		} else if (mFilterType == Constants.KEYWORDS_TYPE_GOODS) {
			jsonObj.put("classification", "item");
		} else if (mFilterType == Constants.KEYWORDS_TYPE_SHOP) {
			jsonObj.put("classification", "shop");
		} else if (mFilterType == Constants.KEYWORDS_TYPE_GROUPON) {
			jsonObj.put("classification", "groupon");
		}
		if (obj instanceof SearchResultEntity) {
			jsonObj.put("keyword", "");
		}else if(obj instanceof SearchCategoryDetailEntity){
			jsonObj.put("keyword", "");
			jsonObj.put("categoryId", ((SearchCategoryDetailEntity) obj).getAppItemCategoryId());
		}else if(obj instanceof SearchCategoryEntity){
			jsonObj.put("keyword", "");
			jsonObj.put("categoryId", ((SearchCategoryEntity) obj).getAppItemCategoryId());
		}else if(obj instanceof DomainSearchCategorySub){
			jsonObj.put("keyword", "");
			jsonObj.put("categoryId", ((DomainSearchCategorySub)obj).getAppItemCategoryId());
		}else if(obj instanceof DomainSearchHistory){
			jsonObj.put("keyword", ((DomainSearchHistory)obj).getSearchWord());
		}
		String searchKey = evSearch.getText().toString();
		if(searchKey!=null && !"".equals(searchKey)){
			jsonObj.put("filterKey", evSearch.getText().toString());
			mFilter = searchKey;
		}else{
			jsonObj.put("filterKey", evSearch.getText().toString());
			mFilter = rootFilter;
		}
		evSearch.setHint("在\"" + mFilter + "\"下搜索");
		return jsonObj.toString();
	}

	/**
	 * 是否显示tips
	 * 
	 * @param filterType
	 *            搜索类型
	 * @param filter
	 *            关键字
	 * @param show
	 *            是否显示 void
	 * @exception
	 * @since 1.0.0
	 */
	private void showHasNoMatchedTips(boolean show, String filter,
			int filterType) {
		LogUtil.d("SearchBaseActivity", "showHasNoMatchedTips + show ="+show);
		String tips = String.format(
				getResources().getString(R.string.str_search_no_match_result),
				filter,
				filterType == Constants.FilterType.GOODS ? getResources()
						.getString(R.string.goods) : getResources().getString(
						R.string.shop));
		;
		LogUtil.d("SearchBaseActivity", "showHasNoMatchedTips + tips ="+tips);
		tvTips.setText(Html.fromHtml(tips));
		tvTips.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.powerlong.electric.app.ui.base.BaseFragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		updateSortView(getSortType(), bDesend);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Rect rect = new Rect();
		ivSearch.getHitRect(rect);

		Rect realRect = new Rect(rect.left, rect.top, rect.right, rect.bottom
				+ STATUS_BAR_HEIGHT);

		boolean isHit = realRect.contains((int) ev.getRawX(),
				(int) ev.getRawY());
		LogUtil.w(SearchBaseActivityNew.class.getName(),
				"dispatchTouchEvent.. ev.getRawX = " + ev.getRawX());
		LogUtil.w(SearchBaseActivityNew.class.getName(),
				"dispatchTouchEvent.. ev.getRawY = " + ev.getRawY());

		boolean isOpen = ivSearch.isFocused();

		if (!isHit && isOpen) {
			ivSearch.clearFocus();
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			// 输入法是否弹出
			inputMethodManager.hideSoftInputFromWindow(SearchBaseActivityNew.this
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			return true;
		}
		return super.dispatchTouchEvent(ev);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		LogUtil.w(SearchBaseActivityNew.class.getName(),
				"onClick.. id = " + view.getId());
		switch (view.getId()) {
		case R.id.ivReturn:
			finish();
			break;
		case R.id.ivFilter:
			showMenu();
			break;
		case R.id.type_good:
			if (mFilterType == Constants.FilterType.GOODS)
				return;
			else {
				tvPrice.setText("价格");
				tvSales.setText("销量");
				mFilterType = Constants.FilterType.GOODS;
				updateSortBar(mFilterType);
				list_all_groupItem.clear();
				getData();
			}
			break;
		case R.id.type_shop:
			if (mFilterType == Constants.FilterType.SHOPS)
				return;
			else {
				tvPrice.setText("等级");
				tvSales.setText("类别");
				mFilterType = Constants.FilterType.SHOPS;
				updateSortBar(mFilterType);
				list_all_floor_detail.clear();
				getData();
			}
			break;
		case R.id.ll_popular:
/*			iCurSortType = SortType.POPULAR;
			if (iPreSortType != iCurSortType) {
				iPreSortType = iCurSortType;
				bDesend = true;
			} else
				bDesend = !bDesend;*/
			orderType = ORDERTYPRE_POPULAR;
			updateSortView(orderType, true);
			LogUtil.d("SearchBaseActivityNew ", "onclick orderType ="+orderType);
			switch (mFilterType) {
				case Constants.FilterType.GOODS:
					list_all_groupItem.clear();
					break;
				case Constants.FilterType.SHOPS:
					list_all_floor_detail.clear();
					break;
			}
			curPage = 1;
			getData();
			break;
		case R.id.ll_sales:
			/*iCurSortType = SortType.SALES;
			if (iPreSortType != iCurSortType) {
				iPreSortType = iCurSortType;
				bDesend = true;
			} else
				bDesend = !bDesend;*/
			orderType = ORDERTYPRE_SALES;
			updateSortView(orderType, true);
			LogUtil.d("SearchBaseActivityNew ", "onclick orderType ="+orderType);
			switch (mFilterType) {
				case Constants.FilterType.GOODS:
					list_all_groupItem.clear();
					break;
				case Constants.FilterType.SHOPS:
					list_all_floor_detail.clear();
					break;
			}
			curPage = 1;
			getData();
			break;
		case R.id.ll_price:
			/*iCurSortType = SortType.PRICE;
			if (iPreSortType != iCurSortType) {
				iPreSortType = iCurSortType;
				bDesend = true;
			} else
				bDesend = !bDesend;*/
			if(orderType == ORDERTYPRE_PRICE_ASC){
				orderType = ORDERTYPRE_PRICE_DESC;
				bDesend = true;
			}else if(orderType == ORDERTYPRE_PRICE_DESC){
				orderType = ORDERTYPRE_PRICE_ASC;
				bDesend = false;
			}
			else{
				orderType = ORDERTYPRE_PRICE_DESC;
				bDesend = true;
			}
			updateSortView(orderType, bDesend);
			LogUtil.d("SearchBaseActivityNew ", "onclick orderType ="+orderType);
			switch (mFilterType) {
				case Constants.FilterType.GOODS:
					list_all_groupItem.clear();
					break;
				case Constants.FilterType.SHOPS:
					list_all_floor_detail.clear();
					break;
			}
			curPage = 1;
			getData();
			break;
		case R.id.filterBack:
			break;
		case R.id.filterHome:
		case R.id.rfilterHome:
			switch (mFilterType) {
			case Constants.FilterType.GOODS:
				list_all_groupItem.clear();
				break;
			case Constants.FilterType.SHOPS:
				list_all_floor_detail.clear();
				break;

			default:
				break;
		}
			showContent();
			getData();
			break;
		case R.id.close:
			evSearch.setText("");
			ivClose.setVisibility(View.VISIBLE);
			break;
		}
	}

	/**
	 * 更新sortbar视图
	 * 
	 * @param mFilterType2
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void updateSortBar(int filterType) {
		showHasNoMatchedTips(false,"",0);
		// btnShops.setVisibility(View.VISIBLE);
		btnShops.setSelected(filterType == Constants.FilterType.SHOPS ? true
				: false);
		// btnGoods.setVisibility(View.VISIBLE);
		btnGoods.setSelected(filterType == Constants.FilterType.GOODS ? true
				: false);
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

	private int getSortType() {
		return iCurSortType;
	}

	private void updateSortView(int type, boolean desc) {
		showHasNoMatchedTips(false,"",0);
		Drawable right = null;
		right = getResources().getDrawable(
				desc ? R.drawable.selector_sort_down
						: R.drawable.selector_sort_up);
		right.setBounds(0, 0, right.getIntrinsicWidth(),
				right.getIntrinsicHeight());

		if (type == ORDERTYPRE_POPULAR) {
			tvPopular.setCompoundDrawables(null, null, null, null);
			//ivPopular.setVisibility(View.VISIBLE);
			llPopular.setSelected(true);
			llSales.setSelected(false);
			//ivSales.setVisibility(View.INVISIBLE);
			tvSales.setCompoundDrawables(null, null, null, null);
			llPrice.setSelected(false);
			tvPrice.setCompoundDrawables(null, null, null, null);
			//ivPrice.setVisibility(View.INVISIBLE);
		} else if (type == ORDERTYPRE_SALES) {
			tvPopular.setCompoundDrawables(null, null, null, null);
			//ivPopular.setVisibility(View.INVISIBLE);
			llPopular.setSelected(false);
			//ivSales.setVisibility(View.VISIBLE);
			tvSales.setCompoundDrawables(null, null, null, null);
			llSales.setSelected(true);
			llPrice.setSelected(false);
			tvPrice.setCompoundDrawables(null, null, null, null);
			//ivPrice.setVisibility(View.INVISIBLE);
		} else if (type == ORDERTYPRE_PRICE_DESC || type == ORDERTYPRE_PRICE_ASC) {
			tvPopular.setCompoundDrawables(null, null, null, null);
			//ivPopular.setVisibility(View.INVISIBLE);
			llPopular.setSelected(false);
			llSales.setSelected(false);
			//ivSales.setVisibility(View.INVISIBLE);
			tvSales.setCompoundDrawables(null, null, null, null);
			tvPrice.setCompoundDrawables(null, null, right, null);
			//ivPrice.setVisibility(View.VISIBLE);
			llPrice.setSelected(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.ExpandableListView.OnChildClickListener#onChildClick(android
	 * .widget.ExpandableListView, android.view.View, int, int, long)
	 */
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		String child = (String) mExpandableListAdapter.getChild(groupPosition,
				childPosition);
		showCustomToast(child);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.ExpandableListView.OnGroupClickListener#onGroupClick(android
	 * .widget.ExpandableListView, android.view.View, int, long)
	 */
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.TextView.OnEditorActionListener#onEditorAction(android
	 * .widget.TextView, int, android.view.KeyEvent)
	 */
//	@Override
//	public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
//		// TODO Auto-generated method stub
//		if (actionId == EditorInfo.IME_ACTION_SEARCH
//				|| actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
//			imm.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_SHOWN,
//					InputMethodManager.HIDE_NOT_ALWAYS);
//			evSearch.clearFocus();
//		} else {
//
//		}
//		SearchHistoryEntity enitity = new SearchHistoryEntity(FilterType.GOODS,
//				evSearch.getText().toString().trim());
//		DataCache.SearchHistroyEntity.add(enitity);
//		return true;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.View.OnFocusChangeListener#onFocusChange(android.view.View,
	 * boolean)
	 */
	@Override
	public void onFocusChange(View view, boolean flag) {
		if (flag) {

		} else {

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
	 */
	@Override
	public void afterTextChanged(Editable editable) {
		LogUtil.d(SearchBaseActivityNew.class.getName(),
				"afterTextChanged.. + editable = " + editable);
		if (TextUtils.isEmpty(editable)) {
			ivClose.setVisibility(View.GONE);
		} else {
			ivClose.setVisibility(View.VISIBLE);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence,
	 * int, int, int)
	 */
	@Override
	public void beforeTextChanged(CharSequence charsequence, int i, int j, int k) {
		LogUtil.d(SearchBaseActivityNew.class.getName(),
				"beforeTextChanged.. + charsequence = " + charsequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int,
	 * int, int)
	 */
	@Override
	public void onTextChanged(CharSequence charsequence, int i, int j, int k) {
		LogUtil.d(SearchBaseActivityNew.class.getName(),
				"onTextChanged.. + charsequence = " + charsequence);
		if (TextUtils.isEmpty(charsequence)) {
			ivClose.setVisibility(View.GONE);
		} else {
			showHasNoMatchedTips(false,"",0);
			ivClose.setVisibility(View.VISIBLE);
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.TextView.OnEditorActionListener#onEditorAction(android.widget.TextView, int, android.view.KeyEvent)
	 */
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
