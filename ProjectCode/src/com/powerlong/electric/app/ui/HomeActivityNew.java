/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * HomeActivity.java
 * 
 * 2013-7-24-上午11:59:53
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.BrandListAdapter;
import com.powerlong.electric.app.adapter.NavigationImgAdapter;
import com.powerlong.electric.app.adapter.NavigationNumAdapter;
import com.powerlong.electric.app.adapter.NavigationTextAdapter;
import com.powerlong.electric.app.adapter.SelectCategoryAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.dao.NavigationBaseDao;
import com.powerlong.electric.app.domain.DMNavigationShopping;
import com.powerlong.electric.app.entity.NavigationBaseEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.listener.LogoutDlgListener;
import com.powerlong.electric.app.service.NewMessageReceiver;
import com.powerlong.electric.app.slidingmenu.SlidingMenu;
import com.powerlong.electric.app.ui.adapter.AdapterNavigationShopping;
import com.powerlong.electric.app.ui.base.BaseDialogFragment;
import com.powerlong.electric.app.ui.base.BaseSlidingFragmentActivity;
import com.powerlong.electric.app.ui.base.BaseTabContent;
import com.powerlong.electric.app.ui.model.Mall;
import com.powerlong.electric.app.update.UpdateManager;
import com.powerlong.electric.app.utils.Base64;
import com.powerlong.electric.app.utils.Base64Encoder;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.Des3Utility;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.MD5Utils;
import com.powerlong.electric.app.utils.PopupWindowUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.ToastUtil;
import com.powerlong.electric.app.widget.BadgeView;
import com.scanning.CaptureActivity;

/**
 * 
 * HomeActivity
 * 
 * @author: Liang Wang 2013-7-24-上午11:59:53
 * 
 * @version 1.0.0
 * 
 */
public class HomeActivityNew extends BaseSlidingFragmentActivity implements
		OnClickListener, OnCheckedChangeListener {
	// final static String TAG = "HomeActivity";
	private static final int TO_SCAN = 99;
	private SlidingMenu sm = null;
	private LinearLayout llGoHome = null;
	private LinearLayout llSearchBar = null;
	private RelativeLayout rlBubble = null;
	private RelativeLayout rlSortBar = null;
	private RelativeLayout rlMainTitle = null;
	private ImageButton imgLogin;
	private TextView titleTv = null;
	private ImageButton imgBack;
	private LinearLayout headerLogo = null;
	private Button btnToFuZhou,btnToJinJiang;
	// private ImageView imgMore;
	private TextView accountTv = null;
	private TextView tvMallChange = null;
	private ImageView ivMallChange = null;
	private SharedPreferences pref = null;
	private String username = "";
	private ImageView ivReturn = null;
	private ImageView ivScanning;
	private Button btnMap;
	private ImageView ivFresh;
	private TabHost mTabHost;
	private RelativeLayout mHomeTabIndicator = null,
			mMyAccountIndicator = null;
	private RelativeLayout mSearchTabIndicator = null,
			mShoppingCartIndicator = null;
	private RelativeLayout mNearByTabIndicator = null;// mMoreTabIndicator =
														// null;
	private LinearLayout mSelectCategory;
	private RelativeLayout mBrand;
	ListView lvBrand;
	private TextView overlay;
	BrandListAdapter brandAdapter;
	private Handler handler;
	private OverlayThread overlayThread;
	private HashMap<String, Integer> alphaIndexer = null;// 存放存在的汉语拼音首字母和与之对应的列表位置
	private String[] sections = null;// 存放存在的汉语拼音首字母
	private SelectCategoryAdapter categoryAdapter;
	public static final int navigation_floor = 1;
	public static final int navigation_food = 2;
	public static final int navigation_disport = 3;
	public static final int navigation_shopping = 4;
	public static final int navigation_groupon = 5;
	int CURRENT_TAB = 0; // 设置常量
	HomeFragment mHomeFragment = null;
	MyAccountFragment mMyAccountFragment = null;
	SearchFragmentNew mSearchFragment = null;
	ShoppingCartFragment mShoppingCartFragment = null;
	// MoreFragment mMoreFragment = null;
	NearByFragmentNew mNearByFragmentNew = null;
	NearByFragment mNearByFragment = null;

	private PullToRefreshScrollView svNavigation;
	private PullToRefreshListView lvNavigation;
	private RelativeLayout rlFloor, rlBrand, rlActivity, rlDelicacy, rlDisport,
			rlShop, rlGroupon;
	android.support.v4.app.FragmentTransaction ft;
	int mTabId = 1;

	private RadioButton rBtnHome, rBtnSearch, rBtnNear, rBtnCart, rBtnProfile;
	private int shopLevel = 2;
	ArrayList<NavigationBaseEntity> saveEntities;
	boolean isBackFromShopDetail = false;
	private int currentNav = 1;

	private AdapterNavigationShopping<DMNavigationShopping> adapterNavigationShopping;

	private List<DMNavigationShopping> dmNavigationShoppings = null;

	private Context context;
	UpdateManager mUpdateManager;
	private String curVersionName;
	private static com.powerlong.electric.app.widget.BadgeView mBvCartTips;
	private static com.powerlong.electric.app.widget.BadgeView mBvAccTips;
	public static SlidingMenu mSlidingMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// .w("HomeActivityNew", "onCreate ..");
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
//		mSlidingMenu = getSlidingMenu();

		try {
			PackageManager packageManager = HomeActivityNew.this
					.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					"com.powerlong.electric.app", 0);
			curVersionName = packageInfo.versionName;
			if (!TextUtils.isEmpty(curVersionName)) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mUpdateManager = new UpdateManager(HomeActivityNew.this);
		// HttpUtil.getVersionCode(HomeActivityNew.this,
		// mServerConnectionHandlerNew);
		Intent intent = getIntent();
		mTabId = 1;
		mTabId = intent.getIntExtra("mTabId", mTabId);
		context = this;
		initSlidingMenu();
		setContentView(R.layout.above_slidingmenu_irregular_tabbar);
		initControl();
		findTabView();
		mTabHost.setup();
		// mTabHost.setCurrentTab(3);
		adapterNavigationShopping = new AdapterNavigationShopping<DMNavigationShopping>(
				context);
		pref = HomeActivityNew.this.getSharedPreferences("account_info",
				MODE_PRIVATE);
		Constants.mallId = pref.getInt("mall", 1);
		

		rBtnHome = (RadioButton) findViewById(R.id.radio_button0);
		rBtnSearch = (RadioButton) findViewById(R.id.radio_button1);
		rBtnNear = (RadioButton) findViewById(R.id.radio_button2);
		rBtnCart = (RadioButton) findViewById(R.id.radio_button3);
		rBtnProfile = (RadioButton) findViewById(R.id.radio_button4);
		mBvCartTips = (BadgeView) findViewById(R.id.bv_cart_tips);
		mBvAccTips = (BadgeView) findViewById(R.id.bv_acc_tips);
		tvMallChange = (TextView)findViewById(R.id.mall_name);
		ivMallChange = (ImageView)findViewById(R.id.pull_icon);
		rlBubble = (RelativeLayout)findViewById(R.id.bubble_layout);
		btnToFuZhou = (Button)findViewById(R.id.change_to_fuzhou);
		btnToJinJiang = (Button) findViewById(R.id.change_to_jinjiang);
		
		btnToFuZhou.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tvMallChange.setText("福州站");
				setMallIdToPref(1);
				closeMallChangeMenu();
			}
		});
		
		btnToJinJiang.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tvMallChange.setText("晋江站");
				setMallIdToPref(2);
				closeMallChangeMenu();
			}
		});
		
		tvMallChange.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openMallChangeMenu();
			}
		});
		
		ivMallChange.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openMallChangeMenu();
			}
		});
		
		rlBubble.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if(rlBubble.getVisibility() == View.VISIBLE){
					rlBubble.setVisibility(View.GONE);
					ivMallChange.setImageResource(R.drawable.site_pull_default);
					return true;
				}
				return false;
			}
		});
		
		rBtnHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rBtnHome.setChecked(true);
				rBtnSearch.setChecked(false);
				rBtnNear.setChecked(false);
				rBtnCart.setChecked(false);
				rBtnProfile.setChecked(false);
				getNewMessageAndCartCount();
				mTabHost.setCurrentTabByTag("home");
			}
		});

		rBtnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rBtnHome.setChecked(false);
				rBtnSearch.setChecked(true);
				rBtnNear.setChecked(false);
				rBtnCart.setChecked(false);
				rBtnProfile.setChecked(false);
				getNewMessageAndCartCount();
				mTabHost.setCurrentTabByTag("search");
			}
		});

		rBtnNear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rBtnHome.setChecked(false);
				rBtnSearch.setChecked(false);
				rBtnNear.setChecked(true);
				rBtnCart.setChecked(false);
				rBtnProfile.setChecked(false);
				getNewMessageAndCartCount();
				mTabHost.setCurrentTabByTag("nearby");
			}
		});

		rBtnCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(DataUtil.isUserDataExisted(HomeActivityNew.this)){
					rBtnHome.setChecked(false);
					rBtnSearch.setChecked(false);
					rBtnNear.setChecked(false);
					rBtnCart.setChecked(true);
					rBtnProfile.setChecked(false);
					getNewMessageAndCartCount();
					mTabHost.setCurrentTabByTag("shopping_cart");
				}else{
					rBtnHome.setChecked(false);
					rBtnSearch.setChecked(false);
					rBtnNear.setChecked(false);
					rBtnCart.setChecked(false);
					rBtnProfile.setChecked(true);
					mTabHost.setCurrentTabByTag("my_account");
					ToastUtil.showExceptionTips(HomeActivityNew.this, "请先登录您的账户方可查看购物车内容");
				}
			}
		});

		rBtnProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rBtnHome.setChecked(false);
				rBtnSearch.setChecked(false);
				rBtnNear.setChecked(false);
				rBtnCart.setChecked(false);
				rBtnProfile.setChecked(true);
				mTabHost.setCurrentTabByTag("my_account");
			}
		});

		/** 监听 */
		TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				/** 碎片管理 */
				android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
				fm.popBackStackImmediate();
				ivReturn.setVisibility(View.GONE);
				mHomeFragment = (HomeFragment) fm.findFragmentByTag("home");
				mMyAccountFragment = (MyAccountFragment) fm
						.findFragmentByTag("my_account");
				mSearchFragment = (SearchFragmentNew) fm
						.findFragmentByTag("search");
				mShoppingCartFragment = (ShoppingCartFragment) fm
						.findFragmentByTag("shopping_cart");
				// mMoreFragment = (MoreFragment) fm.findFragmentByTag("more");

				// if (Constants.isShowNearGallery) {
				mNearByFragmentNew = (NearByFragmentNew) fm
						.findFragmentByTag("nearby");
				// } else {
				// mNearByFragment = (NearByFragment) fm
				//
				// .findFragmentByTag("nearby");
				// }
				ft = fm.beginTransaction();
				ft.setCustomAnimations(R.anim.push_left_in,
						R.anim.push_left_out);

				/** 如果存在Detaches掉 */
				if (mHomeFragment != null) {
					ft.detach(mHomeFragment);
				}

				if (mMyAccountFragment != null)
					ft.detach(mMyAccountFragment);

				if (mSearchFragment != null)
					ft.detach(mSearchFragment);

				if (mShoppingCartFragment != null)
					ft.detach(mShoppingCartFragment);

				/*
				 * if (mMoreFragment != null) ft.detach(mMoreFragment);
				 */

				// if (mNearByFragment != null)
				// ft.detach(mNearByFragment);
				if (mNearByFragmentNew != null) {
					ft.detach(mNearByFragmentNew);
				}

				/** 如果当前选项卡是home */
				if (tabId.equalsIgnoreCase("home")) {
					isTabHome();
					CURRENT_TAB = 1;
					showTitle(false, "宝龙广场");
//					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
				}

				/** 如果当前选项卡是searach */
				if (tabId.equalsIgnoreCase("search")) {
					isTabSearch();
					CURRENT_TAB = 2;
					showTitle(false, "搜索");
				}

				/** 如果当前选项卡是near by */
				if (tabId.equalsIgnoreCase("nearby")) {
					isTabNearBy();
					CURRENT_TAB = 3;
					showTitle(true, "附近");
				}

				/** 如果当前选项卡是shopping cart */
				if (tabId.equalsIgnoreCase("shopping_cart")) {
					isTabShoppingCart();
					CURRENT_TAB = 4;
					showTitle(false, "购物车");
				}

				/** 如果当前选项卡是my account */
				if (tabId.equalsIgnoreCase("my_account")) {
					isTabMyAccount();
					CURRENT_TAB = 5;
					showTitle(true, "我的账户");
				}

				/** 如果当前选项卡是more */
				/*
				 * if (tabId.equalsIgnoreCase("more")) { isTabMore();
				 * CURRENT_TAB = 5; }
				 */
				if (CURRENT_TAB != 1) {
					headerLogo.setVisibility(View.GONE);
					ivScanning.setVisibility(View.GONE);
				} else {
					headerLogo.setVisibility(View.VISIBLE);
					ivScanning.setVisibility(View.VISIBLE);
				}

				if (CURRENT_TAB == 4) {
					rlMainTitle.setBackgroundResource(0);
					rlSortBar.setVisibility(View.VISIBLE);
					llSearchBar.setVisibility(View.GONE);
					llGoHome.setVisibility(View.GONE);
					ivReturn.setVisibility(View.GONE);
					btnMap.setVisibility(View.GONE);
					ivFresh.setVisibility(View.GONE);
				} else if (CURRENT_TAB == 2) {
					rlMainTitle.setBackgroundResource(R.drawable.top_bg1);
					llGoHome.setVisibility(View.GONE);
					llSearchBar.setVisibility(View.VISIBLE);
					rlSortBar.setVisibility(View.GONE);
					btnMap.setVisibility(View.GONE);
					ivFresh.setVisibility(View.GONE);
				} else if (CURRENT_TAB == 3) {
					rlMainTitle.setBackgroundResource(R.drawable.top_bg1);
					llGoHome.setVisibility(View.VISIBLE);
					llSearchBar.setVisibility(View.GONE);
					rlSortBar.setVisibility(View.GONE);
					btnMap.setVisibility(View.VISIBLE);
					ivFresh.setVisibility(View.VISIBLE);
				} else {
					rlMainTitle.setBackgroundResource(R.drawable.top_bg1);
					llSearchBar.setVisibility(View.GONE);
					llGoHome.setVisibility(View.VISIBLE);
					rlSortBar.setVisibility(View.GONE);
					ivReturn.setVisibility(View.GONE);
					btnMap.setVisibility(View.GONE);
					ivFresh.setVisibility(View.GONE);
				}
				ft.commit();
			}

		};

		mTabHost.setOnTabChangedListener(tabChangeListener);
		initTab();

		// 设置初始选项卡
		mTabHost.setCurrentTabByTag("home");
		rBtnHome.setChecked(true);
		rBtnSearch.setChecked(false);
		rBtnNear.setChecked(false);
		rBtnCart.setChecked(false);
		rBtnProfile.setChecked(false);

		llGoHome.setVisibility(View.VISIBLE);

		

		Intent mIntent = new Intent(HomeActivityNew.this,NewMessageReceiver.class);
		 startService(mIntent);
		if (mReceiveMessages.getState() == Thread.State.NEW
				&& DataCache.UserDataCache.size() != 0) {
			// mReceiveMessages.run();
		}
		getMallList();
		initMallTitleName();
	}
	
	public void getMallList(){
		DataUtil.queryMallListData(HomeActivityNew.this, mServerConnectionHandlerMallList);
	}

	/**
	 * initNavigationList(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void initNavigationList() {
		lvNavigation = (PullToRefreshListView) getSlidingMenu().findViewById(
				R.id.lv_navigation);
		lvNavigation.setMode(Mode.PULL_DOWN_TO_REFRESH);
		lvNavigation.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				// public static final int navigation_floor = 1;
				// public static final int navigation_food = 2;
				// public static final int navigation_disport = 3;
				// public static final int navigation_shopping = 4;
				// public static final int navigation_groupon = 5;

				switch (currentNav) {
				case navigation_floor:
					DataCache.NavItemCache.clear();
					DataUtil.getNavFloorData(getBaseContext(),
							mServerConnectionHandler);
					break;
				case navigation_food:
					DataCache.NavItemCache.clear();
					DataUtil.getNavFoodsData(getBaseContext(),
							mServerConnectionHandler);
					break;
				case navigation_disport:
					DataCache.NavItemCache.clear();
					DataUtil.getNavEnterData(getBaseContext(),
							mServerConnectionHandler);
					break;
				case navigation_shopping:
					DataCache.NavItemCache.clear();
					DataUtil.getNavShoppingData(getBaseContext(),
							mServerConnectionHandler);
					break;

				default:
					break;
				}
			}
		});
	}

	/**
	 * 初始化选项卡
	 * 
	 * */
	public void initTab() {
		TabHost.TabSpec tSpecHome = mTabHost.newTabSpec("home");
		tSpecHome.setIndicator(mHomeTabIndicator);
		tSpecHome.setContent(new BaseTabContent(getBaseContext()));
		mTabHost.addTab(tSpecHome);

		mTabHost.addTab(mTabHost.newTabSpec("search")
				.setIndicator(mSearchTabIndicator)
				.setContent(new BaseTabContent(getBaseContext())));

		mTabHost.addTab(mTabHost.newTabSpec("nearby")
				.setIndicator(mNearByTabIndicator)
				.setContent(new BaseTabContent(getBaseContext())));

		mTabHost.addTab(mTabHost.newTabSpec("shopping_cart")
				.setIndicator(mShoppingCartIndicator)
				.setContent(new BaseTabContent(getBaseContext())));

		mTabHost.addTab(mTabHost.newTabSpec("my_account")
				.setIndicator(mMyAccountIndicator)
				.setContent(new BaseTabContent(getBaseContext())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.powerlong.electric.app.ui.base.BaseFragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();

		if (accountTv != null) {
			username = pref.getString("name", "");
			if (!TextUtils.isEmpty(username)) {
				accountTv.setText(username);
			}
		}
		if (Constants.isUpdate) {
			HttpUtil.getVersionCode(HomeActivityNew.this,
					mServerConnectionHandlerNew);
		}
		getNewMessageAndCartCount();
	}
	
	public void getNewMessageAndCartCount(){
		
		if (DataUtil.isUserDataExisted(HomeActivityNew.this)) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
				obj.put("mall", Constants.mallId);
//				DataUtil.queryMyMessageListData(getBaseContext(),
//						mServerConnectionHandlerNew2, obj.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			HttpUtil.queryUnReadMsgJson(HomeActivityNew.this,
					mServerConnectionHandlerNew2, obj.toString());
			HttpUtil.getCartItemCount(HomeActivityNew.this,
					mServerConnectionHandlerNewCart);
		}else{
			mServerConnectionHandlerNewCart.sendEmptyMessage(Constants.HttpStatus.SUCCESS);
			mServerConnectionHandlerNew2.sendEmptyMessage(Constants.HttpStatus.SUCCESS);
		}

	}

	protected void onNewIntent(Intent intent) {

		super.onNewIntent(intent);
		// .w("HomeActivityNew", "onNewIntent ..");
		setIntent(intent);// must store the new intent unless getIntent() will
							// return the old one

		showContent();
		Intent intent2 = getIntent();
		mTabId = intent2.getIntExtra("mTabId", mTabId);
		
		if(intent.getExtras() != null && intent.getExtras().getInt("mTabId") == 5){
			rBtnHome.setChecked(false);
			rBtnSearch.setChecked(false);
			rBtnNear.setChecked(false);
			rBtnCart.setChecked(false);
			rBtnProfile.setChecked(true);
			mTabHost.setCurrentTabByTag("my_account");
			return;
		}
		
		
		
		// .w("HomeActivityNew", "mTabId =" + mTabId);
		
		
		if (mTabId == 3) {
			isTabShoppingCart();
			if(DataUtil.isUserDataExisted(HomeActivityNew.this)){
				rBtnHome.setChecked(false);
				rBtnSearch.setChecked(false);
				rBtnNear.setChecked(false);
				rBtnCart.setChecked(true);
				rBtnProfile.setChecked(false);
				getNewMessageAndCartCount();
				mTabHost.setCurrentTabByTag("home");
				mTabHost.setCurrentTabByTag("shopping_cart");
			}else{
				rBtnHome.setChecked(false);
				rBtnSearch.setChecked(false);
				rBtnNear.setChecked(false);
				rBtnCart.setChecked(false);
				rBtnProfile.setChecked(true);
				mTabHost.setCurrentTabByTag("my_account");
				ToastUtil.showExceptionTips(HomeActivityNew.this, "请先登录您的账户方可查看购物车内容");
			}
		} else {
			isTabHome();
			mTabHost.setCurrentTabByTag("home");
			rBtnHome.setChecked(true);
			rBtnSearch.setChecked(false);
			rBtnNear.setChecked(false);
			rBtnCart.setChecked(false);
			rBtnProfile.setChecked(false);
		}
	}

	/**
	 * initSlidingMenu(初始化侧栏) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void initSlidingMenu() {

		setBehindView();
		// customize the SlidingMenu
		sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setShadowDrawable(R.drawable.slidingmenu_shadow);
		sm.setShadowWidth(10);
		sm.setBehindScrollScale(0.5f);
	}

	private void setBehindView() {
		setBehindContentView(R.layout.behind_slidingmenu);
		imgBack = (ImageButton) findViewById(R.id.action_back);
		imgBack.setOnClickListener(this);

		createList();
		findView();
	}

	/**
	 * findView(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void findView() {
		rlFloor = (RelativeLayout) findViewById(R.id.rl_nav_floor);
		rlFloor.setOnClickListener(this);
		rlBrand = (RelativeLayout) findViewById(R.id.rl_nav_brand);
		rlBrand.setOnClickListener(this);
		rlActivity = (RelativeLayout) findViewById(R.id.rl_nav_activity);
		rlActivity.setOnClickListener(this);
		rlDelicacy = (RelativeLayout) findViewById(R.id.rl_nav_delicacy);
		rlDelicacy.setOnClickListener(this);
		rlDisport = (RelativeLayout) findViewById(R.id.rl_nav_disport);
		rlDisport.setOnClickListener(this);
		rlShop = (RelativeLayout) findViewById(R.id.rl_nav_shop);
		rlShop.setOnClickListener(this);
		rlGroupon = (RelativeLayout) findViewById(R.id.rl_nav_groupon);
		if (!Constants.isNeedGroupon) {
			rlGroupon.setVisibility(View.GONE);
		}
		rlGroupon.setOnClickListener(this);

	}

	private void createList() {
		LayoutInflater mInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout ll = (LinearLayout) mInflater.inflate(
				R.layout.navigation_root, null);

		svNavigation = (PullToRefreshScrollView) findViewById(R.id.sv_navigation);
		svNavigation.setMode(Mode.PULL_DOWN_TO_REFRESH);
		svNavigation.addView(ll);
		svNavigation.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				// getData(method, methodParams);
				new GetDataTask().execute("scroll");
			}
		});
	}

	private class GetDataTask extends AsyncTask<String, Void, String[]> {

		@Override
		protected String[] doInBackground(String... params) {
			// Simulates a background job.
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			return params;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// Do some stuff here

			// Call onRefreshComplete when the list has been refreshed.
			String results = result[0];
			results.length();
			if ("scroll".equals(results)) {
				svNavigation.onRefreshComplete();
			} else {
				lvNavigation.onRefreshComplete();
			}
			super.onPostExecute(result);
		}
	}

	/**
	 * setBehindFloorView(设置楼层页面) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void setBehindFloorView() {
		currentNav = navigation_floor;
		setBehindContentView(R.layout.navigation_second);
		initNavigationList();
		backFunction(navigation_floor);
		DataUtil.getNavFloorData(getBaseContext(), mServerConnectionHandler);
	}

	private void setBehindFoodView() {
		currentNav = navigation_food;
		setBehindContentView(R.layout.navigation_second);
		initNavigationList();
		backFunction(navigation_food);
		DataUtil.getNavFoodsData(getBaseContext(), mServerConnectionHandler);
	}

	private void setBehindDisportView() {
		currentNav = navigation_disport;
		setBehindContentView(R.layout.navigation_second);
		initNavigationList();
		backFunction(navigation_disport);
		DataUtil.getNavEnterData(getBaseContext(), mServerConnectionHandler);
	}

	private void setBehindShoppingView() {
		currentNav = navigation_shopping;
		setBehindContentView(R.layout.navigation_second);
		initNavigationList();
		backFunction(navigation_shopping);
		DataUtil.getNavShoppingData(getBaseContext(), mServerConnectionHandler);
	}

	private void backFunction(int id) {
		final TextView tv = (TextView) findViewById(R.id.tv_nav_title);
		tv.setVisibility(View.VISIBLE);
		ImageView iv = (ImageView) findViewById(R.id.action_back);

		switch (id) {
		case navigation_floor:
			tv.setText("楼层");
			break;
		case navigation_food:
			tv.setText("美食");
			break;
		case navigation_disport:
			tv.setText("娱乐");
			break;
		case navigation_shopping:
			tv.setText("购物");
			break;
		case navigation_groupon:
			tv.setText("团购");
			break;
		}

		tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setBehindView();
			}
		});
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// showContent();
				// isBackFromShopDetail = false;
				// if (shopLevel == 3) {
				// setBehindShoppingView();
				// shopLevel = 2;
				// } else {
				// }
				if (currentNav == navigation_shopping && isBackFromShopDetail) {
					adapterNavigationShopping.setList(DMNavigationShopping
							.getNavigationShoppingFirst(dmNavigationShoppings));
					lvNavigation.setMode(Mode.PULL_DOWN_TO_REFRESH);
					isBackFromShopDetail = false;
					tv.setText("购物");
				} else {
					setBehindView();
				}
			}
		});
	}

	/**
	 * 找到Tabhost布局
	 */
	public void findTabView() {

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		RelativeLayout layout = (RelativeLayout) mTabHost
				.findViewById(R.id.tab_layout);
		TabWidget tw = (TabWidget) layout.getChildAt(2);

		mHomeTabIndicator = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		TextView tvTab1 = (TextView) mHomeTabIndicator.getChildAt(1);
		ImageView ivTab1 = (ImageView) mHomeTabIndicator.getChildAt(0);
		ivTab1.setBackgroundResource(R.drawable.selector_mood_home);
		tvTab1.setText(R.string.tab_home);

		mSearchTabIndicator = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.tab_indicator, tw, false);
		TextView tvTab2 = (TextView) mSearchTabIndicator.getChildAt(1);
		ImageView ivTab2 = (ImageView) mSearchTabIndicator.getChildAt(0);
		ivTab2.setBackgroundResource(R.drawable.selector_mood_search);
		tvTab2.setText(R.string.tab_search);

		mNearByTabIndicator = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.tab_indicator, tw, false);
		TextView tvTab3 = (TextView) mNearByTabIndicator.getChildAt(1);
		ImageView ivTab3 = (ImageView) mNearByTabIndicator.getChildAt(0);
		ivTab3.setBackgroundResource(R.drawable.selector_mood_nearby);
		tvTab3.setText(R.string.tab_nearby);

		mShoppingCartIndicator = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.tab_indicator, tw, false);
		TextView tvTab4 = (TextView) mShoppingCartIndicator.getChildAt(1);
		ImageView ivTab4 = (ImageView) mShoppingCartIndicator.getChildAt(0);
		ivTab4.setBackgroundResource(R.drawable.selector_mood_shopping_cart);
		tvTab4.setText(R.string.tab_shopping_cart);

		mMyAccountIndicator = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.tab_indicator, tw, false);
		TextView tvTab5 = (TextView) mMyAccountIndicator.getChildAt(1);
		ImageView ivTab5 = (ImageView) mMyAccountIndicator.getChildAt(0);
		ivTab5.setBackgroundResource(R.drawable.selector_mood_my_account);
		tvTab5.setText(R.string.tab_my_account);

		/*
		 * mMoreTabIndicator = (RelativeLayout)
		 * LayoutInflater.from(this).inflate( R.layout.tab_indicator, tw,
		 * false); TextView tvTab5 = (TextView) mMoreTabIndicator.getChildAt(1);
		 * ImageView ivTab5 = (ImageView) mMoreTabIndicator.getChildAt(0);
		 * ivTab5.setBackgroundResource(R.drawable.selector_mood_more);
		 * tvTab5.setText(R.string.tab_more);
		 */

	}

	// 判断当前
	public void isTabHome() {
		if (mHomeFragment == null) {
			ft.add(R.id.realtabcontent, new HomeFragment(getApplication(),
					this, this), "home");
		} else {
			ft.attach(mHomeFragment);
		}
	}

	// 判断当前
	public void isTabSearch() {
		if (mSearchFragment == null) {
			ft.add(R.id.realtabcontent, new SearchFragmentNew(), "search");
		} else {
			ft.attach(mSearchFragment);
		}
	}

	// 判断当前
	public void isTabShoppingCart() {
		if (mShoppingCartFragment == null) {
			ft.add(R.id.realtabcontent, new ShoppingCartFragment(
					getApplication(), this, this), "shopping_cart");
		} else {
			ft.attach(mShoppingCartFragment);
		}
	}

	// // 判断当前
	// public void isTabMore() {
	// if (mMoreFragment == null) {
	// ft.add(R.id.realtabcontent, new MoreFragment(), "more");
	// } else {
	// ft.attach(mMoreFragment);
	// }
	// }

	// 判断当前
	public void isTabNearBy() {
		// if (!Constants.isShowNearGallery) {
		// if (mNearByFragment == null) {
		// ft.add(R.id.realtabcontent, new NearByFragment(
		// getApplication(), this, this), "nearby");
		// } else {
		// ft.attach(mNearByFragment);
		// }
		// } else {
		if (mNearByFragmentNew == null) {
			ft.add(R.id.realtabcontent, new NearByFragmentNew(getApplication(),
					this, this), "nearby");
		} else {
			ft.attach(mNearByFragmentNew);
		}
		// }
	}

	// 判断当前
	public void isTabMyAccount() {
		if (mMyAccountFragment == null) {
			ft.add(R.id.realtabcontent, new MyAccountFragment(getApplication(),
					this, this), "my_account");
		} else {
			ft.attach(mMyAccountFragment);
		}
	}

	private void initControl() {
		llGoHome = (LinearLayout) findViewById(R.id.Linear_above_toHome);
		llGoHome.setOnClickListener(this);
		llGoHome.setVisibility(View.VISIBLE);
		ivScanning = (ImageView) findViewById(R.id.imageview_scanning);
		ivScanning.setOnClickListener(this);
		llSearchBar = (LinearLayout) findViewById(R.id.llsearch);
		rlSortBar = (RelativeLayout) findViewById(R.id.sort);

		rlMainTitle = (RelativeLayout) findViewById(R.id.main_title);
		ivReturn = (ImageView) findViewById(R.id.back);
		btnMap = (Button) findViewById(R.id.btn_map);
		ivFresh = (ImageView) findViewById(R.id.iv_fresh);
		headerLogo = (LinearLayout) findViewById(R.id.ll_header);

		imgLogin = (ImageButton) findViewById(R.id.login_login);
		imgLogin.setOnClickListener(this);
		titleTv = (TextView) findViewById(R.id.above_textview_title);
		titleTv.setText(R.string.homepage_title);
		titleTv.setVisibility(View.GONE);
		accountTv = (TextView) findViewById(R.id.account_info);
		// category = (ImageView) findViewById(R.id.category);
		// category.setOnClickListener(this);

		mSelectCategory = (LinearLayout) findViewById(R.id.select_category);

		mBrand = (RelativeLayout) findViewById(R.id.ll_brand);
		// imgMore = (ImageView) findViewById(R.id.imageview_above_more);
		// imgMore.setOnClickListener(this);
	}

	private void showTitle(boolean show, String title) {
		titleTv = (TextView) findViewById(R.id.above_textview_title);
		titleTv.setText(title);
		titleTv.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Message msg = new Message();
			String result = data.getStringExtra("RESULT");

			if (result.contains("www.") || result.contains("http")) {
				if (result.contains(Constants.ServerUrl.SERVER_IP)
						&& result.contains("OCC_QR_CODE_Web")) {
					dismissLoadingDialog();
					if (!result.endsWith("&")) {
						result = result + "&";
					}
					// LogUtil.v("<<<<<<<<<<<<<sign", Constants.userId + "|" +
					// getLocalMacAddress() + "|" + Constants.MD5_KEY);
					String sign = MD5Utils.getMd5Str(pref.getString("userId",
							"")
							+ "|"
							+ getLocalMacAddress()
							+ "|"
							+ Constants.MD5_KEY);
					// String sign =
					// MD5Utils.getMd5Str("1024|a-b-c-d|APWMFIVJDAUVCHWOQQFDVB");
					// LogUtil.v("<<<<<<<<<<<<<sign", sign);
					String str;
					String str1;
					String str2 = pref.getString("userId", "") + "|"
							+ getLocalMacAddress() + "|" + sign;
					LogUtil.v("<<<<<<<<<<<<<str2", str2);
					try {
						// str = encrypt(Constants.AES_KEY, str2);
						str = Des3Utility.encode(Constants.AES_KEY, str2);
						LogUtil.v("<<<<<<<<<<<<<str", str);
						// str1 = decrypt(Constants.AES_KEY, str);
						// LogUtil.v("<<<<<<<<<<<<<str1", str1);
						Intent intent = new Intent(HomeActivityNew.this,
								ScanningResultActivity.class);
						intent.putExtra(
								"url",
								result + "param="
										+ URLEncoder.encode(str, "utf-8"));
						LogUtil.v("scanning url", result + "&param=" + str);
						startActivity(intent);

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					// Intent intent = new Intent(HomeActivityNew.this,
					// ScanningResultActivity.class);
					// intent.putExtra("url", result);
					// startActivity(intent);
					Intent intent = new Intent(HomeActivityNew.this,
							ScanningStringResultActivity.class);
					intent.putExtra("url", result);
					startActivity(intent);
				}
			} else {
				Intent intent = new Intent(HomeActivityNew.this,
						ScanningStringResultActivity.class);
				intent.putExtra("url", result);
				startActivity(intent);
			}
		}

		if (resultCode == Activity.RESULT_CANCELED) {
			dismissLoadingDialog();
			// showCustomToast("no result");
		}
	}

	private String getLocalMacAddress() {
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		Constants.mac = info.getMacAddress();
		return info.getMacAddress();
	}

	public static final String VIPARA = "0102030405060708";
	public static final String bm = "utf-8";

	/*
	 * public static String encrypt(String dataPassword, String cleartext)throws
	 * Exception { IvParameterSpec zeroIv = new
	 * IvParameterSpec(VIPARA.getBytes()); SecretKeySpec key = new
	 * SecretKeySpec(dataPassword.getBytes(), "AES"); Cipher cipher =
	 * Cipher.getInstance("AES/CBC/PKCS5Padding");
	 * cipher.init(Cipher.ENCRYPT_MODE|0x001, key, zeroIv); byte[] encryptedData
	 * = cipher.doFinal(cleartext.getBytes(bm));
	 * 
	 * return Base64Encoder.encode(encryptedData); }
	 */

	/*
	 * public static String decrypt(String dataPassword, String encrypted)
	 * throws Exception { byte[] byteMi = Base64.decode(encrypted);
	 * IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
	 * SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");
	 * Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	 * cipher.init(Cipher.DECRYPT_MODE, key, zeroIv); byte[] decryptedData =
	 * cipher.doFinal(byteMi);
	 * 
	 * return new String(decryptedData,bm); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.action_back:
			showContent();
			break;
		case R.id.login_login:
			if (TextUtils.isEmpty(username))
				IntentUtil.start_activity(this, LoginActivity.class);
			else {
				// showDialog(DLG_LOGOUT);
				// 得到一个fragment 事务（类似sqlite的操作）

				String tag = "my_dialog";
				BaseDialogFragment myFragment = BaseDialogFragment
						.newInstance(Constants.DialogIdentity.DLG_LOGOUT);
				// Animation anim=AnimationUtils.loadAnimation(this,
				// R.anim.push_top_in);
				// myFragment.show(getSupportFragmentManager(), tag);
				getWindow().setWindowAnimations(R.anim.push_top_in);
				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				ft.add(myFragment, tag);
				ft.setCustomAnimations(R.anim.push_left_in, R.anim.push_top_out);
				ft.commit();
				myFragment.setListener(new LogoutDlgListener() {

					@Override
					public void doPositiveClick() {
						// press ok btn
						Editor editor = pref.edit();
						editor.putString("name", "");
						editor.putString("pwd", "");
						editor.commit();
						if (accountTv != null) {
							username = pref.getString("name", "");
							if (!TextUtils.isEmpty(username)) {
								accountTv.setText(username);
							} else {
								accountTv.setText(R.string.account_need_login);
							}
						}
					}

					@Override
					public void doNegativeClick() {
						// press cancel btn
					}
				});
				ft.show(myFragment);
			}
			break;
		case R.id.Linear_above_toHome:
			showMenu();
			break;
		case R.id.imageview_scanning:

			if (DataUtil.isUserDataExisted(getBaseContext())) {
				Intent intent = new Intent(HomeActivityNew.this,
						CaptureActivity.class);
				startActivityForResult(intent, TO_SCAN);
			} else {

				IntentUtil.start_activity(this, LoginActivity.class);
			}

			break;
		case R.id.imageview_above_more:
			showPopWindow(view);
			break;
		// case R.id.category:
		// mSelectCategory.setVisibility(View.VISIBLE);
		// mBrand.setVisibility(View.GONE);
		// break;
		// case R.id.brand_imageview_gohome:
		// setBehindContentView(R.layout.behind_slidingmenu);
		// break;
		case R.id.rl_nav_floor:
			setBehindFloorView();
			break;
		case R.id.rl_nav_brand:
			IntentUtil
					.start_activity(HomeActivityNew.this, BrandActivity.class);
			break;
		case R.id.rl_nav_activity:
			IntentUtil.start_activity(HomeActivityNew.this,
					LatestActivity.class);
			break;
		case R.id.rl_nav_delicacy:
			setBehindFoodView();
			break;
		case R.id.rl_nav_disport:
			setBehindDisportView();
			break;
		case R.id.rl_nav_shop:
			// setBehindShoppingView();
			currentNav = navigation_shopping;
			setBehindContentView(R.layout.navigation_second);
			backFunction(navigation_shopping);
			lvNavigation = (PullToRefreshListView) getSlidingMenu()
					.findViewById(R.id.lv_navigation);
			lvNavigation.setMode(Mode.PULL_DOWN_TO_REFRESH);
			lvNavigation
					.setOnRefreshListener(new OnRefreshListener<ListView>() {
						@Override
						public void onRefresh(
								PullToRefreshBase<ListView> refreshView) {
							String label = DateUtils.formatDateTime(
									getApplicationContext(),
									System.currentTimeMillis(),
									DateUtils.FORMAT_SHOW_TIME
											| DateUtils.FORMAT_SHOW_DATE
											| DateUtils.FORMAT_ABBREV_ALL);
							refreshView.getLoadingLayoutProxy()
									.setLastUpdatedLabel(label);
							getDMNavigationShoppingData();
						}
					});
			lvNavigation.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					DMNavigationShopping dmNavigationShopping = adapterNavigationShopping
							.getDomain(arg2 - 1);
					if ("0".equals(dmNavigationShopping.getIsParent())) {
						adapterNavigationShopping.setList(DMNavigationShopping
								.getNavigationShoppingSecond(
										dmNavigationShoppings,
										dmNavigationShopping.getNavId()));
						isBackFromShopDetail = true;
						lvNavigation.setMode(Mode.DISABLED);
						TextView tv = (TextView) findViewById(R.id.tv_nav_title);
						tv.setVisibility(View.VISIBLE);
						tv.setText(dmNavigationShopping.getName());
					} else {
						Intent intent = new Intent();
						intent.putExtra("method",
								dmNavigationShopping.getMethod());
						intent.putExtra("methodParams",
								dmNavigationShopping.getData());
						intent.putExtra("itemName",
								dmNavigationShopping.getName());
						intent.setClass(HomeActivityNew.this,
								ItemListActivity.class);
						startActivity(intent);
					}
				}
			});
			getDMNavigationShoppingData();

			break;
		case R.id.rl_nav_groupon:
			IntentUtil.start_activity(HomeActivityNew.this,
					GroupBuyListActivityNew.class);
			break;
		}
	}

	private void getDMNavigationShoppingData() {
		FinalHttp finalHttp = new FinalHttp();
		String url = Constants.ServerUrl.NAVIGATION_URL;
		JSONObject jsObj = null;
		try {
			jsObj = new JSONObject();
			jsObj.put("mall", Constants.mallId);
			jsObj.put("navId", "6");
		} catch (JSONException e) {
			throw new RuntimeException("dm", e);
		}
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("data", jsObj.toString());
		finalHttp.get(url.substring(0, url.length() - 1), ajaxParams,
				new AjaxCallBack<String>() {
					@Override
					public void onFailure(Throwable throwable, int i, String s) {
						if (throwable != null && s != null) {
							Toast.makeText(context, s, Toast.LENGTH_SHORT)
									.show();
							lvNavigation.onRefreshComplete();
						}
					}

					@Override
					public void onSuccess(String t) {
						try {
							JSONObject jsObj = new JSONObject(t);
							if (0 != jsObj.getInt("code")) {
								Toast.makeText(context, jsObj.getString("msg"),
										Toast.LENGTH_SHORT).show();
							} else {
								String naviShoppinglist = jsObj.getJSONObject(
										"data").getString("navigationList");
								dmNavigationShoppings = DMNavigationShopping
										.convertJsonToDMNavigationShopping(naviShoppinglist);
								List<DMNavigationShopping> lists = DMNavigationShopping
										.getNavigationShoppingFirst(dmNavigationShoppings);
								lvNavigation
										.setAdapter(adapterNavigationShopping);
								adapterNavigationShopping.setList(lists);
							}
						} catch (Exception e) {
							throw new RuntimeException("获取购物导航json格式有误", e);
						} finally {
							lvNavigation.onRefreshComplete();
						}
					}
				});
	}

	// 设置overlay不可见
	private class OverlayThread implements Runnable {

		@Override
		public void run() {
			overlay.setVisibility(View.GONE);

		}
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				showCustomToast("再按一次退出程序");
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showPopWindow(View attachTo) {
		PopupWindowUtil<String> mPopView = null;
		// if (mPopView == null) {
		// initPopWindow();
		// }
		// if (!mPopView.isShowing()) {
		// mPopView.update();
		// Display display = getWindowManager().getDefaultDisplay();
		// int height = display.getHeight();
		// int width = display.getWidth();
		// mPopView.showAsDropDown(attachTo,width-attachTo.getWidth()/2,attachTo.getHeight());
		// }
		// if (mPopView == null) {
		List<String> data = new ArrayList<String>();
		data.add("宝贝");
		data.add("商铺");
		mPopView = new PopupWindowUtil<String>();
		mPopView.showActionWindow(attachTo, this, data);
		// }
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {
		@Override
		public void handleMessage(Message msg) {
			// .d("HomeActivity", "msg.what = " + msg.what);
			// .d("HomeActivity", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				String navId = Integer.toString(msg.arg1);
				updateNavItemView(navId);
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
				showCustomToast(fail);
				break;
			}
			lvNavigation.onRefreshComplete();
		}

	};

	private ServerConnectionHandler mServerConnectionHandlerNew = new ServerConnectionHandler() {
		@Override
		public void handleMessage(Message msg) {
			// .d("HomeActivity", "msg.what = " + msg.what);
			// .d("HomeActivity", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				// long versionName =
				// Long.parseLong(Integer.toString(msg.arg1));
				LogUtil.d("current remote version", DataCache.versionCode + "");
				if (DataCache.versionCode != null && curVersionName != null
						&& !DataCache.versionCode.equals(curVersionName)) {
					mUpdateManager.checkUpdateInfo(HomeActivityNew.this);
				}
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				// String fail = (String) msg.obj;
				// showCustomToast(fail);
				break;
			}
		}

	};

	/**
	 * updateNavItemView(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param navId
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void updateNavItemView(String navId) {
		this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (StringUtil.isEquals(navId,
				Constants.JSONKeyName.NavItemId.FLOOR.getValue())) {

			final ArrayList<NavigationBaseEntity> entities = DataCache.NavItemCache
					.get(navId);
			NavigationTextAdapter adapter = new NavigationTextAdapter(
					getBaseContext(), entities);
			if (lvNavigation != null) {
				lvNavigation.setDividerDrawable(getResources().getDrawable(
						R.drawable.nav_diliver));
				lvNavigation.getRefreshableView().setAdapter(adapter);

				lvNavigation.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (entities != null) {
							NavigationBaseEntity entity = entities
									.get(arg2 - 1);
							String method = entity.getMethod();
							String methodParams = entity.getMethodParams();
							Intent intent = new Intent();
							intent.putExtra("method", method);
							intent.putExtra("methodParams", methodParams);
							intent.setClass(HomeActivityNew.this,
									FloorDetailActivity.class);
							startActivity(intent);
						}
					}
				});
			}

		} else if (StringUtil.isEquals(navId,
				Constants.JSONKeyName.NavItemId.BRAND.getValue())) {
			DataCache.NavItemCache.get(navId);
		} else if (StringUtil.isEquals(navId,
				Constants.JSONKeyName.NavItemId.FOODS.getValue())) {

			final ArrayList<NavigationBaseEntity> entities = DataCache.NavItemCache
					.get(navId);
			NavigationNumAdapter adapter = new NavigationNumAdapter(
					getBaseContext(), entities);
			if (lvNavigation != null) {
				lvNavigation.setDividerDrawable(getResources().getDrawable(
						R.drawable.nav_diliver));
				lvNavigation.setAdapter(adapter);
				lvNavigation.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (entities != null) {
							NavigationBaseEntity entity = entities
									.get(arg2 - 1);
							String method = entity.getMethod();
							String methodParams = entity.getMethodParams();
							Intent intent = new Intent();
							intent.putExtra("method", method);
							intent.putExtra("methodParams", methodParams);
							intent.setClass(HomeActivityNew.this,
									FoodDetailActivity.class);
							startActivity(intent);
						}
					}
				});
			}

		} else if (StringUtil.isEquals(navId,
				Constants.JSONKeyName.NavItemId.ENTERTAINMENT.getValue())) {

			final ArrayList<NavigationBaseEntity> entities = DataCache.NavItemCache
					.get(navId);

			NavigationNumAdapter adapter = new NavigationNumAdapter(
					getBaseContext(), entities);
			if (lvNavigation != null) {
				lvNavigation.setDividerDrawable(getResources().getDrawable(
						R.drawable.nav_diliver));
				lvNavigation.setAdapter(adapter);
				lvNavigation.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (entities != null) {
							NavigationBaseEntity entity = entities
									.get(arg2 - 1);
							String method = entity.getMethod();
							String methodParams = entity.getMethodParams();
							Intent intent = new Intent();
							intent.putExtra("method", method);
							intent.putExtra("methodParams", methodParams);
							intent.setClass(HomeActivityNew.this,
									EntertainmentDetailActivity.class);
							startActivity(intent);
						}
					}
				});
			}

		} else if (StringUtil.isEquals(navId,
				Constants.JSONKeyName.NavItemId.SHOPPING.getValue())) {
			final NavigationBaseDao dao = new NavigationBaseDao(
					getBaseContext());
			final ArrayList<NavigationBaseEntity> entity_mCache = DataCache.NavItemCache
					.get(navId);
			// final ArrayList<NavigationBaseEntity> entities = dao
			// .getDataByNavId(navId, shopLevel+"", "");
			final ArrayList<NavigationBaseEntity> list = new ArrayList<NavigationBaseEntity>();
			for (NavigationBaseEntity navigationBaseEntity : entity_mCache) {
				if (0 == navigationBaseEntity.getIsParent()) {
					list.add(navigationBaseEntity);
				}
			}
			NavigationImgAdapter adapter = new NavigationImgAdapter(
					getBaseContext(), list);
			if (lvNavigation != null) {
				lvNavigation.setDividerDrawable(getResources().getDrawable(
						R.drawable.nav_diliver));
				lvNavigation.setAdapter(adapter);
			}
			lvNavigation.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// NavigationBaseEntity navigationBaseEntity =
					// list.get(arg2-1);
					// if(0 == navigationBaseEntity.getIsParent()){
					// List<NavigationBaseEntity> list_second = new
					// ArrayList<NavigationBaseEntity>();
					// int pid =
					// Integer.parseInt(navigationBaseEntity.getNavId());
					// for(NavigationBaseEntity navigationBaseEntity2 :
					// entity_mCache){
					// if( pid == navigationBaseEntity2.getParentId()){
					// list_second.add(navigationBaseEntity2);
					// }
					// }
					// NavigationImgAdapter adapter = new NavigationImgAdapter(
					// getBaseContext(), list_second);
					// lvNavigation.setAdapter(adapter);
					// }else{
					// // 跳转
					// Intent intent = new Intent();
					// intent.putExtra("method",
					// navigationBaseEntity.getMethod());
					// intent.putExtra("methodParams",
					// navigationBaseEntity.getMethodParams());
					// intent.putExtra("itemName",
					// navigationBaseEntity.getName());
					// intent.setClass(HomeActivityNew.this,
					// ItemListActivity.class);
					// startActivity(intent);
					// isBackFromShopDetail = true;
					// }
					NavigationBaseEntity entityShop = null;
					ArrayList<NavigationBaseEntity> entitiesSecond = null;
					entityShop = entity_mCache.get(arg2 - 1);
					if (entityShop == null)
						return;
					if (shopLevel == 2 && entityShop.getIsParent() == 0) { // 0有下一页
						shopLevel = 3;
						Log.v("isParent", entityShop.getIsParent() + "");
						// entitiesSecond = dao.getDataByNavId("", "",
						// entityShop.getNavId() + "");
						saveEntities = entitiesSecond;
						NavigationImgAdapter adapter = new NavigationImgAdapter(
								getBaseContext(), entitiesSecond);
						if (lvNavigation != null) {
							lvNavigation.setDividerDrawable(getResources()
									.getDrawable(R.drawable.nav_diliver));
							lvNavigation.setAdapter(adapter);
							adapter.notifyDataSetChanged();
						}
					} else {
						if (!isBackFromShopDetail) {
							shopLevel = 2;
						} else {
							shopLevel = 3;
						}
						if (saveEntities != null) {
							entityShop = saveEntities.get(arg2 - 1);
							String method = entityShop.getMethod();
							String methodParams = entityShop.getMethodParams();
							Intent intent = new Intent();
							intent.putExtra("method", method);
							intent.putExtra("methodParams", methodParams);
							intent.putExtra("itemName", entityShop.getName());
							intent.setClass(HomeActivityNew.this,
									ItemListActivity.class);
							startActivity(intent);
							isBackFromShopDetail = true;
						}
					}
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.RadioGroup.OnCheckedChangeListener#onCheckedChanged(android
	 * .widget.RadioGroup, int)
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio_button0:
			rBtnHome.setChecked(true);
			mTabHost.setCurrentTabByTag("home");
			break;
		case R.id.radio_button1:
			rBtnSearch.setChecked(true);
			mTabHost.setCurrentTabByTag("search");
			break;
		case R.id.radio_button2:
			rBtnNear.setChecked(true);
			mTabHost.setCurrentTabByTag("nearby");
			break;
		case R.id.radio_button3:
			rBtnCart.setChecked(true);
			mTabHost.setCurrentTabByTag("shopping_cart");
			break;
		case R.id.radio_button4:
			rBtnProfile.setChecked(true);
			mTabHost.setCurrentTabByTag("my_account");
			break;
		}
	}

	BroadcastReceiver mMessageTips = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(
					NewMessageReceiver.BROADCAST_NEW_MESSAGES_TIPS)) {
				// ((ImageView)mMyAccountIndicator.getChildAt(0)).setBackground(HomeActivityNew.this.getResources().getDrawable(R.drawable.icon_zh_t));
				// ToastUtil.showExceptionTips(HomeActivityNew.this,
				// "Service Response");
			}

		}
	};

	protected void onStart() {
		super.onStart();
		IntentFilter intentFilter = new IntentFilter();

		intentFilter.addAction(NewMessageReceiver.BROADCAST_NEW_MESSAGES_TIPS); // 为BroadcastReceiver指定action，使之用于接收同action的广播

		registerReceiver(mMessageTips, intentFilter);
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		unregisterReceiver(mMessageTips);
	}

	Thread mReceiveMessages = new Thread(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					Thread.sleep(1000 * 60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	});

	public static ServerConnectionHandler mServerConnectionHandlerNew2 = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				if (Constants.unReadMessageNum > 0 || Constants.unReadNotifyNum > 0) {
					// ToastUtil.showExceptionTips(HomeActivityNew.this,
					// "收到新消息");
					mBvAccTips.setText((Constants.unReadNotifyNum+Constants.unReadMessageNum) + "");
					mBvAccTips.setVisibility(View.VISIBLE);
				} else {
					mBvAccTips.setVisibility(View.GONE);
				}
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				// ToastUtil.showExceptionTips(HomeActivityNew.this, "连接失败");
				break;
			}
		}
	};

	public static ServerConnectionHandler mServerConnectionHandlerNewCart = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				if (msg.obj != null && Long.parseLong(msg.obj.toString()) > 0) {
					mBvCartTips
							.setText(Long.parseLong(msg.obj.toString()) + "");
					mBvCartTips.setVisibility(View.VISIBLE);
				} else {
					mBvCartTips.setVisibility(View.GONE);
				}
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				// ToastUtil.showExceptionTips(HomeActivityNew.this, "连接失败");
				break;
			}
		}
	};
	
	public static ServerConnectionHandler mServerConnectionHandlerMallList = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				// ToastUtil.showExceptionTips(HomeActivityNew.this, "连接失败");
				break;
			}
		}
	};
	
	public void closeMallChangeMenu(){
		resetTitleView();
		clearDataCache();
	}
	
	public void resetTitleView(){
		rlBubble.setVisibility(View.GONE);
		ivMallChange.setImageResource(R.drawable.site_pull_default);
	}
	
	public void clearDataCache(){
		File dbFile = new File("/data/data/"  
                + context.getPackageName() + "/databases/powerlong.db");
		File dir = new File("/mnt/sdcard/powerlong");
		if(dbFile.exists()){
			dbFile.delete();
		}
		if(dir.exists()){
			deleteDir(dir);
		}
		
		DataCache.NavItemCache.clear();
		DataCache.NavFloorDetailsCache.clear();
		DataCache.NavGrouponDetailsCache.clear();
		DataCache.NavActivityDetaillsCache.clear();
		DataCache.NavActivityImageListCache.clear();
		DataCache.NavActivityListCache.clear();
		DataCache.NavBrandDetailsCache.clear();
		DataCache.NavGrouponListCache.clear();
		Constants.isMallChanged = true;
	}
	
	private boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
				return false;
				}
			}
		}
		// The directory is now empty so now it can be smoked
		return dir.delete();
	}
	
	private void openMallChangeMenu(){
		rlBubble.setVisibility(rlBubble.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
		ivMallChange.setImageResource(R.drawable.site_pull_hover);
		btnToFuZhou.setClickable(true);
		btnToJinJiang.setClickable(true);
		btnToFuZhou.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_bg_title_green));
		btnToJinJiang.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_bg_title_green));
		switch(Constants.mallId){
		case 1:
			btnToFuZhou.setBackgroundDrawable(getResources().getDrawable(R.drawable.site_back_hover));
			btnToFuZhou.setClickable(false);
			break;
		case 2:
			btnToJinJiang.setBackgroundDrawable(getResources().getDrawable(R.drawable.site_back_hover));
			btnToJinJiang.setClickable(false);
			break;
		}
	}
	
	private void setMallIdToPref(int mallId){
		Constants.mallId = mallId;
		Editor editor = pref.edit();
		editor.putInt("mall", mallId);
		editor.commit();
	}
	
	private void initMallTitleName(){
		switch(Constants.mallId){
		case 1:
			tvMallChange.setText("福州站");
			break;
		case 2:
			tvMallChange.setText("晋江站");
			break;
		}
	}
}
