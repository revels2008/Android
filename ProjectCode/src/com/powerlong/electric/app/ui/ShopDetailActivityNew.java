/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ShopDetailNew.java
 * 
 * 2013年10月11日-上午11:19:05
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.BrandDeatilGridAdapter;
import com.powerlong.electric.app.adapter.BrandDetailListAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.domain.DomainShopDetailCategory;
import com.powerlong.electric.app.entity.ShopDetailsEntity;
import com.powerlong.electric.app.entity.ShopItemListEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.indicator.TitlePageIndicator;
import com.powerlong.electric.app.slidingmenu.SlidingMenu;
import com.powerlong.electric.app.ui.adapter.AdapterShopDetailCategory;
import com.powerlong.electric.app.ui.base.BaseSlidingFragmentActivity;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil.ILoadImageCallback;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.JSONParser;
import com.powerlong.electric.app.utils.JSONUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.RoundCornerUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.widget.PlRangeSeekBar;

/**
 * 
 * ShopDetailNew
 * 
 * @author: YangCheng Miao
 * 2013年10月11日-上午11:19:05
 * 
 * @version 1.0.0
 * 
 */
public class ShopDetailActivityNew extends BaseSlidingFragmentActivity implements OnClickListener {
	private SlidingMenu sm = null;
	private ImageView ivBack;
	private ImageView ivFilter;
	private PullToRefreshGridView gvDetail;
	private PullToRefreshListView lvDetail;
	private Boolean isGrid = true;
	private ImageView ivReturn;
//	private ProgressBar mpProgressBar = null;
	private ImageView ivShop;
	private TextView tvShopName;
//	private TextView tvInstroduction;
//	private RatingBar rb;
	private TextView rb;
	private TextView tvClassification;
//	private TextView tvItemNum;
	private TextView tvFavourNum;
	private long itemId;
	private String itemName;
	private int type;
//	private long itemIdL;
	private String from;
	private ImageView ivGrid;
	private ImageView ivList;
	private ImageView ivMessage;
//	private ImageView ivAddShopToFavour;
	private RelativeLayout rlAddShopToFavour,rlContactUs;
	private String method;
	private String methodParams ="";
	private String orderType = "0";
	private long shopId;
	private RelativeLayout rlTitile, rlItem;
	private LinearLayout llTabItem1, llTabItem2, llTabItem3, llTabItem4;
	private TextView tvSortItem1, tvSortItem2, tvSortItem3, tvSortItem4;
	
	private ListView listview_category;
	private AdapterShopDetailCategory<DomainShopDetailCategory> adapterShopDetailCategory;
	
	private static final String ORDERTYPE_LATEST = "0";//新品
	private static final String ORDERTYPE_SALES = "1";//销量
	private static final String ORDERTYPE_POPULAR = "2";//人气/收藏人数
	private static final String ORDERTYPE_PRICE_DESC = "3";//价格降序
	private static final String ORDERTYPE_PRICE_ASC = "4";//价格升序
	
	//设置默认的价格
	private PlRangeSeekBar<Number> priceRangeBar;
	private Number price_start = 0;
	private Number price_end = Integer.MAX_VALUE;
	private TextView tv_price;
	private ImageView btn_ensure;
	private String shopName;
	
	private boolean  isFirstLoading = true;
	
	private Context context;
	
	private boolean isCategoryData = false;
	
	private DomainShopDetailCategory curShopDetailCategory;
	private int curPage = 1;
	private boolean isRefresh = true;
	private int numOfPage = 0;
	private boolean isGridBack;
	private EditText etSearch;
	private int gvDetailIndex;
	private boolean isBusy = false;
	private RelativeLayout rlTitleSearch;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initSlidingMenu();
		setContentView(R.layout.brand_detail);
		context = this;
		Intent intent = getIntent();
		shopId = intent.getLongExtra("shopId", shopId);
		method = intent.getStringExtra("method");
		methodParams = intent.getStringExtra("methodParams");
		if(!StringUtil.isEmpty(methodParams)){
			try {
				JSONObject obj = new JSONObject(methodParams);
				shopId = obj.getInt("shopId");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		LogUtil.d("ShopDetailActivityNew", "shopId = "+shopId);
		LogUtil.d("ShopDetailActivityNew", "methodParams = "+methodParams);
		orderType = intent.getStringExtra("orderType");
		if(StringUtil.isEmpty(orderType)){
			orderType = "0";
		}
		findView();
		isFirstLoading = true;
		getData();
		setLeftView();
	}



	/**
	 * findView(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void findView() {
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(this);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		ivFilter = (ImageView) findViewById(R.id.ivFilter);
		ivFilter.setVisibility(View.VISIBLE);
		ivFilter.setOnClickListener(this);
		gvDetail = (PullToRefreshGridView) findViewById(R.id.gv_brand_detail);
		lvDetail = (PullToRefreshListView) findViewById(R.id.lv_brand_detail);
		ivShop = (ImageView) findViewById(R.id.floor_shopimage);
		tvShopName = (TextView) findViewById(R.id.floor_title_shop);
		tvShopName.requestFocus();
//		tvInstroduction = (TextView) findViewById(R.id.floor_shop_details);
		rb = (TextView) findViewById(R.id.tv_shop_list_evaluate);
		tvClassification = (TextView) findViewById(R.id.floor_products_categories);
//		tvItemNum = (TextView) findViewById(R.id.tv_item_num);
		tvFavourNum = (TextView) findViewById(R.id.floor_favour_num);
		ivGrid = (ImageView) findViewById(R.id.ivGrid);
		ivGrid.setOnClickListener(this);
		ivList = (ImageView) findViewById(R.id.ivList);
		ivList.setOnClickListener(this);
		rlTitile = (RelativeLayout) findViewById(R.id.brand_detail_title);
		tvSortItem1 = (TextView) findViewById(R.id.tv_sort_item1);
		tvSortItem1.setText("新品");
		tvSortItem2 = (TextView) findViewById(R.id.tv_sort_item2);
		tvSortItem2.setText("销量");
		tvSortItem3 = (TextView) findViewById(R.id.tv_sort_item3);
		tvSortItem3.setText("价格");
		tvSortItem4 = (TextView) findViewById(R.id.tv_sort_item4);
		tvSortItem4.setText("收藏人数");
		llTabItem1 = (LinearLayout) findViewById(R.id.ll_tab_item1);
		llTabItem1.setOnClickListener(this);
		llTabItem2 = (LinearLayout) findViewById(R.id.ll_tab_item2);
		llTabItem2.setOnClickListener(this);
		llTabItem3 = (LinearLayout) findViewById(R.id.ll_tab_item3);
		llTabItem3.setOnClickListener(this);
		llTabItem4 = (LinearLayout) findViewById(R.id.ll_tab_item4);
		llTabItem4.setOnClickListener(this);
		tvSortItem3.setCompoundDrawables(null, null, null, null);
		updateSortBar(orderType);
		ivMessage = (ImageView) findViewById(R.id.iv_message);
		ivMessage.setOnClickListener(this);
//		ivAddShopToFavour = (ImageView) findViewById(R.id.iv_add_shop_to_favour);
//		ivAddShopToFavour.setOnClickListener(this);
		rlAddShopToFavour = (RelativeLayout) findViewById(R.id.favour_layout);
		rlContactUs = (RelativeLayout)findViewById(R.id.message_layout);
		rlAddShopToFavour.setOnClickListener(this);
		rlContactUs.setOnClickListener(this);
		rlItem = (RelativeLayout) findViewById(R.id.item);
		rlItem.setOnClickListener(this);
		rlTitleSearch = (RelativeLayout)findViewById(R.id.brand_search);
		etSearch = (EditText) findViewById(R.id.evSearch);
		etSearch.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_ENTER){
					getCategoryListData(curShopDetailCategory);
				}
				return false;
			}
		});
		
		adapterShopDetailCategory = new AdapterShopDetailCategory<DomainShopDetailCategory>(context);
		listview_category = (ListView) findViewById(R.id.lv_shop_detail_filter);
		listview_category.setAdapter(adapterShopDetailCategory);
		listview_category.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				showContent();
				DomainShopDetailCategory shopDetailCategory = adapterShopDetailCategory.getDomain(arg2);
				adapterShopDetailCategory.clearSelected();
				shopDetailCategory.setSelect(true);
				adapterShopDetailCategory.notifyDataSetChanged();
				curShopDetailCategory = shopDetailCategory;
				getCategoryListData(shopDetailCategory);
				isCategoryData = true;
			}
		});
		
		priceRangeBar = (PlRangeSeekBar<Number>) findViewById(R.id.priceRangebar);
		tv_price = (TextView) findViewById(R.id.tv_price);
		btn_ensure = (ImageView) findViewById(R.id.btn_ensure);
		btn_ensure.setOnClickListener(this);
		priceRangeBar.setOnRangeSeekBarChangeListener(new PlRangeSeekBar.OnRangeSeekBarChangeListener<Number>() {
			@Override
			public void onRangeSeekBarValuesChanged(PlRangeSeekBar<?> bar,
					Number minValue, Number maxValue) {
				price_start = minValue;
				price_end = maxValue;
				String max_price = price_end.intValue()>1000000 ? "∞" : price_end.toString();
				tv_price.setText(price_start+" - "+max_price);
			}
		});
		lvDetail.setMode(Mode.BOTH);
		lvDetail.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				isRefresh=true;
				curPage = 1;
				getData();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				isRefresh=false;
				curPage++;
				getData();
			}
		});
//		lvDetail.setOnRefreshListener(new OnRefreshListener<ListView>() {
//			@Override
//			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
//						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//					getData();
//			}
//		});
		gvDetail.setMode(Mode.BOTH);
		
		gvDetail.setOnRefreshListener(new OnRefreshListener2<GridView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				isRefresh=true;
				curPage = 1;
				getData();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				isRefresh=false;
				curPage++;
				getData();
			}
		});
		gvDetail.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				gvDetailIndex = view.getFirstVisiblePosition();
				switch(scrollState){
				case SCROLL_STATE_FLING:
					if(gvDetailIndex > 3 && rlTitile.getVisibility()==View.VISIBLE && !isBusy){
//						rlTitile.setVisibility(View.GONE);
						isBusy = true;
						gvCloseTitleBarAnimation();
//						view.setSelection(gvDetailIndex);
					}
					break;
				case SCROLL_STATE_IDLE:
					if(gvDetailIndex==0 && rlTitile.getVisibility()==View.GONE && !isBusy){
//						rlTitile.setVisibility(View.VISIBLE);
						isBusy = true;
						gvOpenTitleBarAnimation();
//						view.setSelection(gvDetailIndex);
					}
					if(gvDetailIndex > 3 && rlTitile.getVisibility()==View.VISIBLE && !isBusy){
//						rlTitile.setVisibility(View.GONE);
						isBusy = true;
						gvCloseTitleBarAnimation();
//						view.setSelection(gvDetailIndex);
					}
					break;
				case SCROLL_STATE_TOUCH_SCROLL:
					if(gvDetailIndex > 3 && rlTitile.getVisibility()==View.VISIBLE && !isBusy){
//						rlTitile.setVisibility(View.GONE);
						isBusy = true;
						gvCloseTitleBarAnimation();
//						view.setSelection(gvDetailIndex);
					}
					break;
				}
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		lvDetail.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				gvDetailIndex = view.getFirstVisiblePosition();
				switch(scrollState){
				case SCROLL_STATE_FLING:
					if(gvDetailIndex > 3 && rlTitile.getVisibility()==View.VISIBLE && !isBusy){
//						rlTitile.setVisibility(View.GONE);
						isBusy = true;
						lvCloseTitleBarAnimation();
//						view.setSelection(gvDetailIndex);
					}
					break;
				case SCROLL_STATE_IDLE:
					if(gvDetailIndex==0 && rlTitile.getVisibility()==View.GONE && !isBusy){
//						rlTitile.setVisibility(View.VISIBLE);
						isBusy = true;
						lvOpenTitleBarAnimation();
//						view.setSelection(gvDetailIndex);
					}
					if(gvDetailIndex > 3 && rlTitile.getVisibility()==View.VISIBLE && !isBusy){
//						rlTitile.setVisibility(View.GONE);
						isBusy = true;
						lvCloseTitleBarAnimation();
//						view.setSelection(gvDetailIndex);
					}
					break;
				case SCROLL_STATE_TOUCH_SCROLL:
					if(gvDetailIndex > 3 && rlTitile.getVisibility()==View.VISIBLE && !isBusy){
//						rlTitile.setVisibility(View.GONE);
						isBusy = true;
						lvCloseTitleBarAnimation();
//						view.setSelection(gvDetailIndex);
					}
					break;
				}
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
		});
//		gvDetail.setOnRefreshListener(new OnRefreshListener<GridView>() {
//			@Override
//			public void onRefresh(PullToRefreshBase<GridView> refreshView) {
//				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
//						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//				getData();
//			}
//		});
	}
	
	
	private void gvCloseTitleBarAnimation(){
		Animation closeAnimation = new TranslateAnimation(0, 0, 0, -rlTitile.getHeight()-rlTitleSearch.getHeight());
		closeAnimation.setFillAfter(true);
		closeAnimation.setDuration(400);
		closeAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				isBusy = false;
				rlTitile.setVisibility(View.GONE);
				gvDetail.getRefreshableView().setSelection(gvDetailIndex);
			}
		});
		
		rlTitile.startAnimation(closeAnimation);
	}
	
	private void gvOpenTitleBarAnimation(){
		Animation openAnimation = new TranslateAnimation(0, 0, -rlTitile.getHeight()-rlTitleSearch.getHeight(), 0);
		openAnimation.setFillAfter(true);
		openAnimation.setDuration(400);
		openAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				isBusy = false;
			}
		});
		rlTitile.setVisibility(View.VISIBLE);
		gvDetail.getRefreshableView().setSelection(gvDetailIndex);
		rlTitile.startAnimation(openAnimation);
	}
	
	
	private void lvCloseTitleBarAnimation(){
		Animation closeAnimation = new TranslateAnimation(0, 0, 0, -rlTitile.getHeight()-rlTitleSearch.getHeight());
		closeAnimation.setFillAfter(true);
		closeAnimation.setDuration(400);
		closeAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				isBusy = false;
				rlTitile.setVisibility(View.GONE);
				lvDetail.getRefreshableView().setSelection(gvDetailIndex);
			}
		});
		
		rlTitile.startAnimation(closeAnimation);
	}
	
	private void lvOpenTitleBarAnimation(){
		Animation openAnimation = new TranslateAnimation(0, 0, -rlTitile.getHeight()-rlTitleSearch.getHeight(), 0);
		openAnimation.setFillAfter(true);
		openAnimation.setDuration(400);
		openAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				isBusy = false;
			}
		});
		rlTitile.setVisibility(View.VISIBLE);
		lvDetail.getRefreshableView().setSelection(gvDetailIndex);
		rlTitile.startAnimation(openAnimation);
	}
	
	
	
	private void initSlidingMenu() {

		setBehindContentView(R.layout.shop_detail_behind_left);
		setSecondaryMenu(R.layout.shop_detail_behind_right);

		// customize the SlidingMenu
		sm = getSlidingMenu();
//		sm.setSecondaryShadowDrawable(R.drawable.slidingmenu_shadow);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setShadowDrawable(R.drawable.slidingmenu_shadow);
		sm.setShadowWidth(10);
		sm.setMode(SlidingMenu.LEFT_RIGHT);
//		sm.setMode(SlidingMenu.RIGHT);
		sm.setBehindScrollScale(0.333f);		
	}
	
	/**
	 * setLeftView(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void setLeftView() {
		
		
	}
	/**
	 * getData(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void getData() {
		showLoadingDialog(null);
		JSONObject obj = null;
		if(StringUtil.isEmpty(methodParams)){
			obj = new JSONObject();
			try {
				obj.put("shopId", shopId);
				obj.put("page", curPage);
				obj.put("orderType", orderType);
				obj.put("mall", Constants.mallId);
				obj.put("priceFrom", price_start);
				obj.put("priceTo", price_end);
				if(DataCache.UserDataCache.size()>0){
					obj.put("TGC",DataCache.UserDataCache.get(0).getTGC());
				} else {
					obj.put("TGC","");
				}
				DataUtil.getShopDetails(getBaseContext(), mServerConnectionHandler, obj.toString(),isRefresh);
			} catch (JSONException e) {
				dismissLoadingDialog();
				e.printStackTrace();
			}
		}
		else{
			try {
				obj = new JSONObject(methodParams);
				obj.put("mall", Constants.mallId);
				obj.put("page", curPage);
				obj.put("orderType", orderType);
				obj.put("priceFrom", price_start);
				obj.put("priceTo", price_end);
				if(DataCache.UserDataCache.size()>0){
					obj.put("TGC",DataCache.UserDataCache.get(0).getTGC());
				} else {
					obj.put("TGC","");
				}
				DataUtil.getShopDetails(getBaseContext(), mServerConnectionHandler, obj.toString(),isRefresh);
			} catch (JSONException e) {
				dismissLoadingDialog();
				e.printStackTrace();
			}
		}
	}
	
	public void getCategoryListData(DomainShopDetailCategory shopDetailCategory){
//		categoryId
//		priceFrom
//		priceTo
//		shopId
//		orderType
//		mall
//		TGC
		showLoadingDialog(null);
		DataCache.ShopItemListCache.clear();
		FinalHttp finalHttp = new FinalHttp();
		JSONObject jsonObject = null;
		AjaxParams ajaxParams = new AjaxParams();
		try {
			jsonObject = new JSONObject();
			if(shopDetailCategory != null) {
				jsonObject.put("categoryId", shopDetailCategory.getId());
			}
			
			jsonObject.put("priceFrom", price_start);
			jsonObject.put("page", curPage);
			jsonObject.put("priceTo", price_end);
			jsonObject.put("shopId", shopId);
			jsonObject.put("orderType", orderType);
			jsonObject.put("mall", Constants.mallId);
			jsonObject.put("keyword", etSearch.getText().toString());
			if(DataCache.UserDataCache.size()>0){
				jsonObject.put("TGC",DataCache.UserDataCache.get(0).getTGC());
			} else {
				jsonObject.put("TGC","");
			}
			ajaxParams.put("data", jsonObject.toString());
			String url = Constants.ServerUrl.GET_SHOP_ITEM_DETAIL_URL;
			finalHttp.get(url.substring(0, url.length()-1), ajaxParams, new AjaxCallBack<String>() {
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					if(t!=null && strMsg != null){
						Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
						dismissLoadingDialog();
					}
				}
				@Override
				public void onSuccess(String t) {
					try {
//						{"code":0,"data":{"itemList":[]},"msg":"成功"}
						JSONObject jsonObject = new JSONObject(t);
						if(0 == jsonObject.getInt("code")){
							String itemlist = jsonObject.getJSONObject("data").getString("itemList");
							JSONParser.addShopItemToCache(new JSONArray(itemlist));
							if(isGrid){
								gvDetail.getRefreshableView().setVisibility(View.VISIBLE);
								lvDetail.getRefreshableView().setVisibility(View.GONE);
								setBrandDetailGridView();
							}else{
								lvDetail.getRefreshableView().setVisibility(View.VISIBLE);
								gvDetail.getRefreshableView().setVisibility(View.GONE);
								setBrandDetailListView();
							}
						}else{
							Toast.makeText(context, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						throw new RuntimeException("ShopDetailActivity",e);
					}finally{
						dismissLoadingDialog();
					}
				}
			});
		} catch (JSONException e) {
			throw new RuntimeException("shopdetailactivitynew getcategoryListData", e);
		}
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("ShopDetailActivity", "msg.what = "+msg.what);
			LogUtil.d("ShopDetailActivity", "msg.arg1 = "+msg.arg1);
			dismissLoadingDialog();
			switch(msg.what){
			case Constants.HttpStatus.SUCCESS:
				updateView(Integer.toString(msg.arg1));
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				if(!Constants.isShowDefaultUi)
					finish();
				break;
			}
			if(isGrid){
				gvDetail.onRefreshComplete();
			}else{
				lvDetail.onRefreshComplete();
			}
		}
		
	};
	
	protected void updateView(String navId) {
		setBrandDetailHead();
		setBrandDetailGridView();
		setBrandDetailListView();
		updateLeftCategory();
	}	
		
	private void updateLeftCategory() {
		String categoryList = DataCache.SHOP_DETAIL_ALL;
		if(categoryList!=null){
			try {
				JSONObject jsonObject = new JSONObject(categoryList);
				if(0==jsonObject.getInt("code")){
		
//					List<DomainShopDetailCategory> list = DomainShopDetailCategory.convertJsonToCategory(jsonObject.getJSONObject("data").optString("categoryList",""));
					List<DomainShopDetailCategory> list = DataCache.CategoryListCache;
					if(list==null || list.size()==0 ){
						return;
					}
					adapterShopDetailCategory.setList(list);
				}
			} catch (Exception e) {
				throw new RuntimeException("shopdetailActiivty updateLeftCategory", e);
			}
		}
//		adapterShopDetailCategory.setList(list);
	}

	private void setBrandDetailHead() {
		ArrayList<ShopDetailsEntity> shopEntities = DataCache.ShopDetailsCache;
				
		ShopDetailsEntity shoEntity = shopEntities.get(0);
		String imageUrl = shoEntity.getImage();
		AsyncImageLoaderUtil imageLoader = new AsyncImageLoaderUtil();
		
		Drawable cachedImage = imageLoader.loadDrawable(imageUrl,ivShop, new ILoadImageCallback() {

			@Override
			public void onObtainDrawable(Drawable drawable,ImageView imageView) {
				if(drawable != null) {
					imageView.setImageDrawable(RoundCornerUtil.toRoundCorner(drawable, 35));
				}
			}
			
		});
		if (cachedImage == null) {
			ivShop.setImageResource(R.drawable.pic_56);
//			Log.e("Adapter", "null");
		} else {
			Drawable d = RoundCornerUtil.toRoundCorner(cachedImage, 15);
			ivShop.setImageDrawable(d);
		}
		shopName = shoEntity.getName();
		tvShopName.setText(shopName);
//		tvInstroduction.setText(shoEntity.getInstroduction());
		float f = Float.parseFloat(shoEntity.getEvaluation()+"");
		rb.setText(f+"");
		tvClassification.setText(shoEntity.getClassification());
//		tvItemNum.setText(shoEntity.getItemNum()+"");
		tvFavourNum.setText(shoEntity.getFavourNum()+"");
	}
	
	private void setBrandDetailGridView() {
		final ArrayList<ShopItemListEntity> itemEntities = DataCache.ShopItemListCache;
		BrandDeatilGridAdapter adapterGrid = null;
		if (itemEntities != null) {	
			if(adapterGrid == null){
				adapterGrid = new BrandDeatilGridAdapter(this, itemEntities,gvDetail.getRefreshableView());
				gvDetail.setAdapter(adapterGrid);
			}
			adapterGrid.notifyDataSetChanged();
			if(isRefresh){
				numOfPage = gvDetail.getRefreshableView().getAdapter().getCount();
				gvDetail.getRefreshableView().setSelection(0);
			}else{
				gvDetail.getRefreshableView().setSelection((numOfPage-2)*(curPage-1));
			}
			gvDetail.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					final ShopItemListEntity entity = itemEntities.get(arg2);
					itemId = entity.getId();
					itemName = entity.getName();
					//getItemData();
					Intent intent = new Intent(ShopDetailActivityNew.this, ItemDetailActivity.class);
					intent.putExtra("itemId", itemId);
					intent.putExtra("shopId", shopId);
					intent.putExtra("itemName", itemName);
					startActivity(intent);
					
					LinearLayout llFavour = (LinearLayout) arg1.findViewById(R.id.ll_brand_detail_favour);
					LinearLayout llCart = (LinearLayout) arg1.findViewById(R.id.ll_brand_detail_cart);
					
					llFavour.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							if (entity.getIsFavour() == 1) {
								showCustomToast("商品已加入收藏");
							} else {
								AddToFavour(itemId, itemName);
								entity.setIsFavour(1);
							}
						}
					});
					
					llCart.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							if (entity.getIsCart() == 1) {
								showCustomToast("商品已在购物车中");
							} else {
								AddToCart(itemId, entity.getType());
								entity.setIsCart(1);
							}
						}
					});
				}
			});
		}
	}
	
	private void AddToCart(long mItemId, int type) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("TGC",DataCache.UserDataCache.get(0).getTGC());
			obj.put("buyNum", "1");
			obj.put("id", mItemId);
			obj.put("type", type);
			HttpUtil.requestAddOrDeleteItemTocart(getBaseContext(), obj.toString(), mCartHandler, 0);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void AddToFavour(long mItemId, String mItemName) {
		if(DataUtil.isUserDataExisted(getBaseContext()))
		{	
			JSONObject obj = new JSONObject();
			try {
				obj.put("mall", Constants.mallId);
				obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
				
				//JSONArray itemArray = new JSONArray();
				//JSONObject item = new JSONObject();
				//item.put("buyNum", tvCount.getText()+"");
				obj.put("id", mItemId);
				obj.put("itemName", mItemName);
				//item.put("type", itemType+"");
				
				//itemArray.put(item);
				
				//obj.put("itemList",itemArray);
				
				DataUtil.sendUserFavourOperation(mFavourHandler, obj.toString(), 0);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}else{
			showCustomToast("请先登录您的账号方可进行操作.");
			 IntentUtil.start_activity(this, LoginActivity.class);
		}
	}
	
	private ServerConnectionHandler mCartHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("ItemDetailActivity", "msg.what = " + msg.what);
			LogUtil.d("ItemDetailActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				showCustomToast("加入购物车成功！");
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
		}

	};
	
	private ServerConnectionHandler mFavourHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("ItemDetailActivity", "msg.what = " + msg.what);
			LogUtil.d("ItemDetailActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				showCustomToast("收藏商品成功！");
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
		}

	};
	
	private void setBrandDetailListView() {
		lvDetail.setVisibility(View.VISIBLE);
		final ArrayList<ShopItemListEntity> itemEntities = DataCache.ShopItemListCache;
		BrandDetailListAdapter adapter;
		if (itemEntities != null) {		
			adapter = new BrandDetailListAdapter(ShopDetailActivityNew.this, itemEntities, lvDetail.getRefreshableView());
			lvDetail.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			if(isRefresh){
				numOfPage = gvDetail.getRefreshableView().getAdapter().getCount();
				lvDetail.getRefreshableView().setSelection(0);
			}else{
				lvDetail.getRefreshableView().setSelection((numOfPage-2)*(curPage-1));
			}
			lvDetail.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					
					ShopItemListEntity itemEntity= itemEntities.get(arg2-1);
					itemId = itemEntity.getId();
					type = itemEntity.getType();
					itemName = itemEntity.getName();
					//getItemData();
					
					Intent intent = new Intent(ShopDetailActivityNew.this, ItemDetailActivity.class);
					intent.putExtra("itemId", itemId);
					intent.putExtra("shopId", shopId);
					intent.putExtra("itemName", itemName);
					startActivity(intent);
					
				}
			});
		}
	}

	private ServerConnectionHandler mAddShopToFavourHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("ItemDetailActivity", "msg.what = " + msg.what);
			LogUtil.d("ItemDetailActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				showCustomToast("收藏店铺成功");
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
			// mpProgressBar.setVisibility(View.GONE);
		}

	};
	

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
			finish();
			break;
		case R.id.ivReturn:
			showMenu();
			break;
		case R.id.ivFilter:
			showSecondaryMenu();
			isGridBack = isGrid;
			break;
		case R.id.ivGrid:
			if (!isGrid) {
				lvDetail.getRefreshableView().setVisibility(View.GONE);
				gvDetail.getRefreshableView().setVisibility(View.VISIBLE);
				setBrandDetailGridView();
				isGrid = true;
			}
	
			break;
		case R.id.ivList:
			if (isGrid) {
				gvDetail.getRefreshableView().setVisibility(View.GONE);
				lvDetail.getRefreshableView().setVisibility(View.VISIBLE);
				setBrandDetailListView();
				isGrid = false;
			}

			break;
		case R.id.ll_tab_item1:
			if(orderType.equals(ORDERTYPE_LATEST)){
				return;
			}else{
				orderType = ORDERTYPE_LATEST;
				tvSortItem3.setCompoundDrawables(null, null, null, null);
				updateSortBar(orderType);
				if(isCategoryData){
					getCategoryListData(curShopDetailCategory);
				}else{
					getData();
				}
			}
			break;
		case R.id.ll_tab_item2:
			if(orderType.equals(ORDERTYPE_SALES)){
				return;
			}else{
				orderType = ORDERTYPE_SALES;
				tvSortItem3.setCompoundDrawables(null, null, null, null);
				updateSortBar(orderType);
				if(isCategoryData){
					getCategoryListData(curShopDetailCategory);
				}else{
					getData();
				}
			}
			break;
		case R.id.ll_tab_item3:
			if(orderType.equals(ORDERTYPE_PRICE_ASC)){
				orderType = ORDERTYPE_PRICE_DESC;
				updatePriceIndicator(orderType);
				updateSortBar(orderType);
			}else if(orderType.equals(ORDERTYPE_PRICE_DESC)){
				orderType = ORDERTYPE_PRICE_ASC;
				updatePriceIndicator(orderType);
				updateSortBar(orderType);
			}
			else{
				orderType = ORDERTYPE_PRICE_DESC;
				updatePriceIndicator(orderType);
				updateSortBar(orderType);
			}
			if(isCategoryData){
				getCategoryListData(curShopDetailCategory);
			}else{
				getData();
			}
			break;
		case R.id.ll_tab_item4:
			if(orderType.equals(ORDERTYPE_POPULAR)){
				return;
			}else{
				orderType = ORDERTYPE_POPULAR;
				tvSortItem3.setCompoundDrawables(null, null, null, null);
				updateSortBar(orderType);
				if(isCategoryData){
					getCategoryListData(curShopDetailCategory);
				}else{
					getData();
				}
			}
			break;
		case R.id.btn_ensure:
			getSlidingMenu().showContent();
			getCategoryListData(curShopDetailCategory);
			isGrid = isGridBack;
//			getData();
			break;
		case R.id.ll_brand_detail_favour:
			break;
		case R.id.ll_brand_detail_cart:
			break;
		case R.id.iv_message:
			IntentUtil.start_activity(this, MyMessageActivity.class);
			break;
//		case R.id.iv_add_shop_to_favour:
//			if(DataUtil.isUserDataExisted(getBaseContext()))
//			{	
//				JSONObject obj = new JSONObject();
//				try {
//					obj.put("shopId", shopId);
//					obj.put("shopName", shopName);
//					obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
//					obj.put("mall", Constants.mallId);
//					HttpUtil.AddShopToFavourUrl(getBaseContext(), mAddShopToFavourHandler, obj.toString());
//				} catch (JSONException e) {
//					dismissLoadingDialog();
//					e.printStackTrace();
//				}
//			}else{
//				showCustomToast("请先登录您的账号方可进行操作.");
//				 IntentUtil.start_activity(this, LoginActivity.class);
//			}
//			break;
		case R.id.favour_layout:
			if(DataUtil.isUserDataExisted(getBaseContext()))
			{	
				JSONObject obj = new JSONObject();
				try {
					obj.put("shopId", shopId);
					obj.put("shopName", shopName);
					obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
					obj.put("mall", Constants.mallId);
					HttpUtil.AddShopToFavourUrl(getBaseContext(), mAddShopToFavourHandler, obj.toString());
				} catch (JSONException e) {
					dismissLoadingDialog();
					e.printStackTrace();
				}
			}else{
				showCustomToast("请先登录您的账号方可进行操作.");
				 IntentUtil.start_activity(this, LoginActivity.class);
			}
			break;
		case R.id.message_layout:
			Intent intent2 = new Intent(ShopDetailActivityNew.this, ChatActivity.class);
			intent2.putExtra("shopId", shopId+"");
			startActivity(intent2);
			break;
		default:
			break;
		}
		
	}



	/**
	 * 更新价格降序/升序指示器
	 * @param type
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void updatePriceIndicator(String type) {
		Drawable right = null;
		right = getResources().getDrawable(
				type == ORDERTYPE_PRICE_DESC ? R.drawable.selector_sort_down
						: R.drawable.selector_sort_up);
		right.setBounds(0, 0, right.getIntrinsicWidth(),
				right.getIntrinsicHeight());
		tvSortItem3.setCompoundDrawables(null, null, right, null);
	}



	/**
	 * 更新sortbar视图
	 * (这里描述这个方法适用条件 – 可选)
	 * @param type 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void updateSortBar(String type) {
		LogUtil.d("ShopDetailActivityNew", "updateSortBar  type = "+type);
		llTabItem1.setSelected(type.equals(ORDERTYPE_LATEST) ? true : false);
		llTabItem2.setSelected(type.equals(ORDERTYPE_SALES) ? true : false);
		llTabItem3.setSelected(type.equals(ORDERTYPE_PRICE_DESC)
				|| type.equals(ORDERTYPE_PRICE_ASC) ? true : false);
		llTabItem4.setSelected(type.equals(ORDERTYPE_POPULAR) ? true : false);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_SEARCH && event.getRepeatCount() == 0) {
//			getCategoryListData(curShopDetailCategory);

		}
			 return false;
		
	}

}
