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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.R.string;
import com.powerlong.electric.app.adapter.BrandListAdapter;
import com.powerlong.electric.app.adapter.CityListAdapter;
import com.powerlong.electric.app.adapter.ShopListAdapter;
import com.powerlong.electric.app.adapter.NavigationAdapter;
import com.powerlong.electric.app.adapter.NavigationImgAdapter;
import com.powerlong.electric.app.adapter.NavigationNumAdapter;
import com.powerlong.electric.app.adapter.NavigationTextAdapter;
import com.powerlong.electric.app.adapter.PopWindowListAdapter;
import com.powerlong.electric.app.adapter.SelectCategoryAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.BrandEntity;
import com.powerlong.electric.app.entity.CategoryEntity;
import com.powerlong.electric.app.entity.FloorDetailEntity;
import com.powerlong.electric.app.entity.NavigationBaseEntity;
import com.powerlong.electric.app.entity.NavigationEntity;
import com.powerlong.electric.app.entity.NavigationFloorDetailsEntity;
import com.powerlong.electric.app.entity.NavigationFloorEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.listener.LogoutDlgListener;
import com.powerlong.electric.app.slidingmenu.SlidingMenu;
import com.powerlong.electric.app.ui.base.BaseDialogFragment;
import com.powerlong.electric.app.ui.base.BaseSlidingFragmentActivity;
import com.powerlong.electric.app.ui.base.BaseTabContent;
import com.powerlong.electric.app.ui.model.ViewItem;
import com.powerlong.electric.app.utils.CityUtil;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.PopupWindowUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.widget.PlLetterListView;
import com.powerlong.electric.app.widget.PlLetterListView.OnTouchingLetterChangedListener;
import com.powerlong.electric.app.widget.PlTableView;
import com.powerlong.electric.app.widget.PlTableView.ClickListener;

/**
 * 
 * HomeActivity
 * 
 * @author: Liang Wang 2013-7-24-上午11:59:53
 * 
 * @version 1.0.0
 * 
 */
public class HomeActivity extends BaseSlidingFragmentActivity implements
		OnClickListener {
	final static String TAG = "HomeActivity";
	private SlidingMenu sm = null;
	private LinearLayout llGoHome = null;
	private LinearLayout llSearchBar = null;
	private RelativeLayout rlSortBar = null;
	private RelativeLayout rlMainTitle = null;
	private ImageButton imgLogin;
	private TextView titleTv = null;
	private ImageButton imgBack;
	// private ImageView imgMore;
	private TextView accountTv = null;
	private SharedPreferences pref = null;
	private String username = "";
	private ImageView	ivReturn = null;
	private Button btnMap;
	private TabHost mTabHost;
	private TabWidget mTabWidget;
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
	SearchFragment mSearchFragment = null;
	ShoppingCartFragment mShoppingCartFragment = null;
	// MoreFragment mMoreFragment = null;
	NearByFragment mNearByFragment = null;
	private ScrollView svNavigation;
	private ListView lvNavigation;
	private RelativeLayout rlFloor, rlBrand, rlActivity, rlDelicacy, rlDisport, rlShop, rlGroupon;
	android.support.v4.app.FragmentTransaction ft;
	int mTabId = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		Intent intent = getIntent();
		mTabId = intent.getIntExtra("mTabId", mTabId);
		// initNavListData();
		initSlidingMenu();
		setContentView(R.layout.above_slidingmenu_tabbar);
		initControl();
		findTabView();
		mTabHost.setup();	
//		mTabHost.setCurrentTab(3);
		

		/** 监听 */
		TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {

				/** 碎片管理 */
				android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
				mHomeFragment = (HomeFragment) fm.findFragmentByTag("home");
				mMyAccountFragment = (MyAccountFragment) fm
						.findFragmentByTag("my_account");
				mSearchFragment = (SearchFragment) fm
						.findFragmentByTag("search");
				mShoppingCartFragment = (ShoppingCartFragment) fm
						.findFragmentByTag("shopping_cart");
				// mMoreFragment = (MoreFragment) fm.findFragmentByTag("more");
				mNearByFragment = (NearByFragment) fm
						.findFragmentByTag("nearby");
				ft = fm.beginTransaction();
				ft.setCustomAnimations(R.anim.push_left_in,
						R.anim.push_left_out);

				/** 如果存在Detaches掉 */
				if (mHomeFragment != null)
					ft.detach(mHomeFragment);

				if (mMyAccountFragment != null)
					ft.detach(mMyAccountFragment);

				if (mSearchFragment != null)
					ft.detach(mSearchFragment);

				if (mShoppingCartFragment != null)
					ft.detach(mShoppingCartFragment);

				/*
				 * if (mMoreFragment != null) ft.detach(mMoreFragment);
				 */

				if (mNearByFragment != null)
					ft.detach(mNearByFragment);

				/** 如果当前选项卡是home */
				if (tabId.equalsIgnoreCase("home")) {
					isTabHome();
					CURRENT_TAB = 1;
					showTitle(true,"宝龙天地");
				}

				/** 如果当前选项卡是searach */
				if (tabId.equalsIgnoreCase("search")) {
					isTabSearch();
					CURRENT_TAB = 2;
					showTitle(false,"搜索");
				}

				/** 如果当前选项卡是near by */
				if (tabId.equalsIgnoreCase("nearby")) {
					isTabNearBy();
					CURRENT_TAB = 3;
					showTitle(true,"附近");
				}

				/** 如果当前选项卡是shopping cart */
				if (tabId.equalsIgnoreCase("shopping_cart")) {
					isTabShoppingCart();
					CURRENT_TAB = 4;
					showTitle(false,"购物车");
				}

				/** 如果当前选项卡是my account */
				if (tabId.equalsIgnoreCase("my_account")) {
					isTabMyAccount();
					CURRENT_TAB = 5;
					showTitle(true,"我的账户");
				}

				/** 如果当前选项卡是more */
				/*
				 * if (tabId.equalsIgnoreCase("more")) { isTabMore();
				 * CURRENT_TAB = 5; }
				 */

				if (CURRENT_TAB == 4) {
					rlSortBar.setVisibility(View.VISIBLE);
					llSearchBar.setVisibility(View.GONE);
					llGoHome.setVisibility(View.GONE);
					ivReturn.setVisibility(View.GONE);
					btnMap.setVisibility(View.GONE);
				}else if(CURRENT_TAB == 2){
					llGoHome.setVisibility(View.GONE);
					llSearchBar.setVisibility(View.VISIBLE);
					rlSortBar.setVisibility(View.GONE);
					btnMap.setVisibility(View.GONE);
				}else if (CURRENT_TAB == 3){
					llGoHome.setVisibility(View.VISIBLE);
					llSearchBar.setVisibility(View.GONE);
					rlSortBar.setVisibility(View.GONE);
					btnMap.setVisibility(View.VISIBLE);
				}else {
					llSearchBar.setVisibility(View.GONE);
					llGoHome.setVisibility(View.VISIBLE);
					rlSortBar.setVisibility(View.GONE);
					ivReturn.setVisibility(View.GONE);
					btnMap.setVisibility(View.GONE);
				}
				ft.commit();
			}

		};
		// 设置初始选项卡
		mTabHost.setCurrentTab(3);
				
		mTabHost.setOnTabChangedListener(tabChangeListener);
		initTab();

		llGoHome.setVisibility(View.VISIBLE);

		pref = HomeActivity.this.getSharedPreferences("account_info",
				MODE_PRIVATE);
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

		try
        {
                Field idcurrent = mTabHost.getClass()
                                .getDeclaredField("mCurrentTab");
                idcurrent.setAccessible(true);
                if (mTabId == 0)
                {
                        idcurrent.setInt(mTabHost, 1);
                }
                else
                {
                        idcurrent.setInt(mTabHost, 0);
                }
        }
        catch (Exception e)
        {
                e.printStackTrace();
        }
        //进入传来的选项卡
		mTabHost.setCurrentTab(mTabId);
		/*
		 * mTabHost.addTab(mTabHost.newTabSpec("more")
		 * .setIndicator(mMoreTabIndicator) .setContent(new
		 * BaseTabContent(getBaseContext())));
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.powerlong.electric.app.ui.base.BaseFragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
//		showContent();
//		Intent intent = getIntent();
//		mTabHost.clearAllTabs();
//		mTabId = intent.getIntExtra("mTabId", mTabId);
//		Log.e("<<<<<<<<<<<<<mTabId", mTabId+"");
//		initTab();

	
		if (accountTv != null) {
			username = pref.getString("name", "");
			if (!TextUtils.isEmpty(username)) {
				accountTv.setText(username);
			}
		}
	}

	// public void initNavListData() {
	// // categoryList = new ArrayList<CategoryEntity>();
	// Vector<CategoryEntity> data = new Vector<CategoryEntity>();
	// int[] listTitle = new int[] { R.string.category_floor,
	// R.string.category_brand, R.string.category_shopping,
	// R.string.category_food, R.string.category_buy,
	// R.string.category_activity, R.string.category_recreation };
	// int[] listIcon = new int[] { R.drawable.nav_floor,
	// R.drawable.nav_brand, R.drawable.nav_activity_nor,
	// R.drawable.nav_delicacy_nor, R.drawable.nav_disport_nor,
	// R.drawable.nav_shop_nor, R.drawable.nav_groupon_nor};
	// for (int i = 0; i < listTitle.length; i++) {
	// String title = this.getResources().getString(listTitle[i]);
	// CategoryEntity entity;
	// if (i < 3) {
	// entity = new CategoryEntity(title, listIcon[i], 1);
	//
	// } else {
	// entity = new CategoryEntity(title, listIcon[i], 2);
	// }
	// data.add(entity);
	// // categoryList.add(entity);
	// }
	//
	// addAdapterItem(data);

	// List<Map<String, Object>> items = new
	// ArrayList<Map<String,Object>>();
	// for (int i = 0; i < 100; i++) {
	// Map<String, Object> item = new HashMap<String, Object>();
	// item.put("imageItem", R.drawable.ic_launcher);
	// item.put("textItem", "text" + i);
	// items.add(item);
	//
	// mDatas.add(String.valueOf(i));
	// }
	// }

	protected void onNewIntent(Intent intent) {

		   super.onNewIntent(intent);
		   setIntent(intent);//must store the new intent unless getIntent() will return the old one
		   
		   showContent();
			Intent intent2 = getIntent();
			mTabHost.clearAllTabs();
			mTabId = intent2.getIntExtra("mTabId", mTabId);
			Log.e("<<<<<<<<<<<<<mTabId", mTabId+"");
			initTab();

		
			if (accountTv != null) {
				username = pref.getString("name", "");
				if (!TextUtils.isEmpty(username)) {
					accountTv.setText(username);
				}
			}
	}

	private void addAdapterItem(Vector<CategoryEntity> data) {
		Vector<CategoryEntity> classItem = new Vector<CategoryEntity>();
		classItem.removeAllElements();

		CategoryEntity temp = null;

		categoryAdapter = new SelectCategoryAdapter(this);
		Set<Integer> set = new HashSet<Integer>();
		if (data != null && data.size() > 0) {
			for (int i = 0; i < data.size(); i++) {
				temp = data.get(i);// 获取数据
				if (set.contains(temp.partId)) {// 判断是否存在这个partid 如果存在
												// 说明此条数据是在同意个栏目下
					classItem.add(temp);
				} else {
					temp.ifTop = true;// 设置置顶 也就是显示栏目
					set.add(temp.partId);// 将此条partid 添加到set 以便后面判断
					classItem.add(temp);
				}
			}
			categoryAdapter.removeAll();
			for (CategoryEntity item : classItem) {
				categoryAdapter.addItem(item);
			}
		}
	}

	private String getFisrtLetter(String name) {
		return name.substring(0, 1);
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
	 * findView(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void findView() {
		rlFloor = (RelativeLayout) findViewById(R.id.rl_nav_floor);
		rlFloor.setOnClickListener(this);
		rlBrand = (RelativeLayout) findViewById(R.id.rl_nav_brand);
		rlBrand .setOnClickListener(this);
		rlActivity = (RelativeLayout) findViewById(R.id.rl_nav_activity);
		rlActivity.setOnClickListener(this);
		rlDelicacy = (RelativeLayout) findViewById(R.id.rl_nav_delicacy);
		rlDelicacy.setOnClickListener(this);
		rlDisport = (RelativeLayout) findViewById(R.id.rl_nav_disport);
		rlDisport.setOnClickListener(this);
		rlShop = (RelativeLayout) findViewById(R.id.rl_nav_shop);
		rlShop.setOnClickListener(this);
		rlGroupon = (RelativeLayout) findViewById(R.id.rl_nav_groupon);
		rlGroupon.setOnClickListener(this);

	}
	
	

	private void createList() {
		LayoutInflater mInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout ll = (LinearLayout) mInflater.inflate(
				R.layout.navigation_root, null);
		svNavigation = (ScrollView) findViewById(R.id.sv_navigation);		
		svNavigation.addView(ll);
	}

	/**
	 * setBehindFloorView(设置楼层页面) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void setBehindFloorView() {
		setBehindContentView(R.layout.navigation_second);
		backFunction(navigation_floor);
		DataUtil.getNavFloorData(getBaseContext(), mServerConnectionHandler);
	}

	private void setBehindFoodView() {
		setBehindContentView(R.layout.navigation_second);
		backFunction(navigation_food);
		DataUtil.getNavFoodsData(getBaseContext(), mServerConnectionHandler);
	}

	private void setBehindDisportView() {
		setBehindContentView(R.layout.navigation_second);
		backFunction(navigation_disport);
		DataUtil.getNavEnterData(getBaseContext(), mServerConnectionHandler);
	}

	private void setBehindShoppingView() {
		setBehindContentView(R.layout.navigation_second);
		backFunction(navigation_shopping);
		DataUtil.getNavShoppingData(getBaseContext(), mServerConnectionHandler);
	}

	private void setBehindGrouponView() {
		setBehindContentView(R.layout.navigation_food);
		backFunction(navigation_groupon);
		DataUtil.getNavGrouponData(getBaseContext(), mServerConnectionHandler);
	}

	private void backFunction(int id) {
		TextView tv = (TextView) findViewById(R.id.tv_nav_title);
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
//				showContent();
				setBehindView();
				
			}
		});
	}


	/**
	 * 找到Tabhost布局
	 */
	public void findTabView() {

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
		LinearLayout layout = (LinearLayout) mTabHost
				.findViewById(R.id.tab_layout);
		TabWidget tw = (TabWidget) layout.getChildAt(2);

		mHomeTabIndicator = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		TextView tvTab1 = (TextView) mHomeTabIndicator.getChildAt(1);
		ImageView ivTab1 = (ImageView) mHomeTabIndicator.getChildAt(0);
		ivTab1.setBackgroundResource(R.drawable.selector_mood_home_old);
		tvTab1.setText(R.string.tab_home);

		mSearchTabIndicator = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.tab_indicator, tw, false);
		TextView tvTab2 = (TextView) mSearchTabIndicator.getChildAt(1);
		ImageView ivTab2 = (ImageView) mSearchTabIndicator.getChildAt(0);
		ivTab2.setBackgroundResource(R.drawable.selector_mood_search_old);
		tvTab2.setText(R.string.tab_search);

		mNearByTabIndicator = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.tab_indicator, tw, false);
		TextView tvTab3 = (TextView) mNearByTabIndicator.getChildAt(1);
		ImageView ivTab3 = (ImageView) mNearByTabIndicator.getChildAt(0);
		ivTab3.setBackgroundResource(R.drawable.selector_mood_nearby_old);
		tvTab3.setText(R.string.tab_nearby);

		mShoppingCartIndicator = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.tab_indicator, tw, false);
		TextView tvTab4 = (TextView) mShoppingCartIndicator.getChildAt(1);
		ImageView ivTab4 = (ImageView) mShoppingCartIndicator.getChildAt(0);
		ivTab4.setBackgroundResource(R.drawable.selector_mood_shopping_cart_old);
		tvTab4.setText(R.string.tab_shopping_cart);

		mMyAccountIndicator = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.tab_indicator, tw, false);
		TextView tvTab5 = (TextView) mMyAccountIndicator.getChildAt(1);
		ImageView ivTab5 = (ImageView) mMyAccountIndicator.getChildAt(0);
		ivTab5.setBackgroundResource(R.drawable.selector_mood_my_account_old);
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
			ft.add(R.id.realtabcontent, new HomeFragment(getApplication(),this,this), "home");
		} else {
			ft.attach(mHomeFragment);
		}
	}

	// 判断当前
	public void isTabSearch() {
		if (mSearchFragment == null) {
			ft.add(R.id.realtabcontent, new SearchFragment(getApplication(),this,this), "search");
		} else {
			ft.attach(mSearchFragment);
		}
	}

	// 判断当前
	public void isTabShoppingCart() {
		if (mShoppingCartFragment == null) {
			ft.add(R.id.realtabcontent, new ShoppingCartFragment(getApplication(),this,this),
					"shopping_cart");
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
		if (mNearByFragment == null) {
			ft.add(R.id.realtabcontent, new NearByFragment(getApplication(),this,this), "nearby");
		} else {
			ft.attach(mNearByFragment);
		}
	}

	// 判断当前
	public void isTabMyAccount() {
		if (mMyAccountFragment == null) {
			ft.add(R.id.realtabcontent, new MyAccountFragment(getApplication(),this,this), "my_account");
		} else {
			ft.attach(mMyAccountFragment);
		}
	}

	private void initControl() {
		llGoHome = (LinearLayout) findViewById(R.id.Linear_above_toHome);
		llGoHome.setOnClickListener(this);
		llSearchBar = (LinearLayout) findViewById(R.id.llsearch);
		rlSortBar = (RelativeLayout) findViewById(R.id.sort);
		
		rlMainTitle = (RelativeLayout) findViewById(R.id.main_title);
		ivReturn = (ImageView)findViewById(R.id.back);
		btnMap = (Button)findViewById(R.id.btn_map);
		
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
	
	private void showTitle(boolean show,String title){
		titleTv = (TextView) findViewById(R.id.above_textview_title);
		titleTv.setText(title);
		titleTv.setVisibility(show?View.VISIBLE:View.GONE);
	}

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
			IntentUtil.start_activity(HomeActivity.this,BrandActivity.class);
			break;
		case R.id.rl_nav_activity:
			IntentUtil.start_activity(HomeActivity.this,LatestActivity.class);
			break;
		case R.id.rl_nav_delicacy:
			setBehindFoodView();
			break;
		case R.id.rl_nav_disport:
			setBehindDisportView();
			break;
		case R.id.rl_nav_shop:
			setBehindShoppingView();
			break;
		case R.id.rl_nav_groupon:
			IntentUtil.start_activity(HomeActivity.this, GrouponListActivity.class);
			break;
		}

	}

	private void initOverlay() {
		WindowManager windowManager = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		if (overlay == null) {
			LayoutInflater inflater = LayoutInflater.from(this);
			overlay = (TextView) inflater.inflate(R.layout.overlay, null);
			overlay.setVisibility(View.INVISIBLE);
			WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.TYPE_APPLICATION,
					WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
							| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
					PixelFormat.TRANSLUCENT);
			windowManager.addView(overlay, lp);
		}
	}

	private class LetterListViewListener implements
			OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(final String s) {
			if (alphaIndexer == null) {
				alphaIndexer = brandAdapter.getAlphaIndexer();
			}

			if (sections == null) {
				sections = brandAdapter.getSections();
			}

			if (alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				LogUtil.d("test", "s = " + s + ", position=" + position);
				lvBrand.setSelection(position);
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

	private void setAdapter(List<BrandEntity> list) {
		if (list != null) {
			brandAdapter = new BrandListAdapter(this, list);
			lvBrand.setAdapter(brandAdapter);
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

	@SuppressWarnings("rawtypes")
	private PopupWindowUtil mPopView = null;
	private void initPopWindow() {
		// if (mPopView == null) {
		// LayoutInflater lay = (LayoutInflater)
		// getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// View view = lay.inflate(R.layout.pop_window_layout, null);
		// final ListView list = (ListView) view.findViewById(R.id.popList);
		// List<String> data = new ArrayList<String>();
		// data.add("宝贝");
		// data.add("商铺");
		// mpListAdapter = new PopWindowListAdapter(getBaseContext(), data);
		// list.setAdapter(mpListAdapter);
		// list.setItemsCanFocus(false);
		// list.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// Toast.makeText(getBaseContext(),
		// (String) list.getAdapter().getItem(arg2),
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		//
		// mPopView = new PopupWindow(view,
		// WindowManager.LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT);
		// mPopView.setBackgroundDrawable(getResources().getDrawable(
		// R.drawable.tab_selected));
		// mPopView.setOutsideTouchable(true);
		// mPopView.setFocusable(true);
		// }
	}

	private void showPopWindow(View attachTo) {
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
		mPopView = new PopupWindowUtil();
		mPopView.showActionWindow(attachTo, this, data);
		// }
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {
		@Override
		public void handleMessage(Message msg) {
			LogUtil.d("HomeActivity", "msg.what = " + msg.what);
			LogUtil.d("HomeActivity", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				String navId = Integer.toString(msg.arg1);
				LogUtil.d("HomeActivity", "navId = " + navId);

				updateNavItemView(navId);
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
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
		this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		lvNavigation = (ListView) getSlidingMenu().findViewById(R.id.lv_navigation);

		
		lvNavigation = (ListView) getSlidingMenu().findViewById(R.id.lv_navigation);
		
		if (StringUtil.isEquals(navId,
				Constants.JSONKeyName.NavItemId.FLOOR.getValue())) {
			
			final ArrayList<NavigationBaseEntity> entities = DataCache.NavItemCache.get(navId);		
			NavigationTextAdapter adapter = new NavigationTextAdapter(getBaseContext(), entities);
			if(lvNavigation != null) {
				lvNavigation.setDivider(getResources().getDrawable(R.drawable.line_full_brown));
				lvNavigation.setAdapter(adapter);
							
				lvNavigation.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (entities != null) {
							NavigationBaseEntity entity = entities.get(arg2);
							String method = entity.getMethod();
							String methodParams = entity.getMethodParams();
							Intent intent = new Intent();
							intent.putExtra("method", method);
							intent.putExtra("methodParams", methodParams);
							intent.setClass(HomeActivity.this, FloorDetailActivityNew.class);
							startActivity(intent);
						}
					}
				});
			}

		} else if (StringUtil.isEquals(navId,
				Constants.JSONKeyName.NavItemId.BRAND.getValue())) {
			DataCache.NavItemCache
					.get(navId);
		} else if (StringUtil.isEquals(navId,
				Constants.JSONKeyName.NavItemId.FOODS.getValue())) {
			
			final ArrayList<NavigationBaseEntity> entities = DataCache.NavItemCache.get(navId);			
			NavigationNumAdapter adapter = new NavigationNumAdapter(getBaseContext(), entities);
			if(lvNavigation != null) {
				lvNavigation.setDivider(getResources().getDrawable(R.drawable.line_full_brown));
				lvNavigation.setAdapter(adapter);
				lvNavigation.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (entities != null) {
							NavigationBaseEntity entity = entities.get(arg2);
							String method = entity.getMethod();
							String methodParams = entity.getMethodParams();
							Intent intent = new Intent();
							intent.putExtra("method", method);
							intent.putExtra("methodParams", methodParams);							
							intent.setClass(HomeActivity.this, FoodDetailActivity.class);
							startActivity(intent);
						}
					}
				});
			}
			
			
		} else if (StringUtil.isEquals(navId,
				Constants.JSONKeyName.NavItemId.ENTERTAINMENT.getValue())) {
			
			final ArrayList<NavigationBaseEntity> entities = DataCache.NavItemCache
					.get(navId);
			
			NavigationNumAdapter adapter = new NavigationNumAdapter(getBaseContext(), entities);
			if(lvNavigation != null) {
				lvNavigation.setDivider(getResources().getDrawable(R.drawable.line_full_brown));
				lvNavigation.setAdapter(adapter);
				lvNavigation.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (entities != null) {
							NavigationBaseEntity entity = entities.get(arg2);
							String method = entity.getMethod();
							String methodParams = entity.getMethodParams();
							Intent intent = new Intent();
							intent.putExtra("method", method);
							intent.putExtra("methodParams", methodParams);
							intent.setClass(HomeActivity.this, EntertainmentDetailActivity.class);
							startActivity(intent);
						}
					}
				});
			}
			
		} else if (StringUtil.isEquals(navId,
				Constants.JSONKeyName.NavItemId.SHOPPING.getValue())) {
			
			final ArrayList<NavigationBaseEntity> entities = DataCache.NavItemCache.get(navId);
			NavigationImgAdapter adapter = new NavigationImgAdapter(getBaseContext(), entities);
			if(lvNavigation != null) {
				lvNavigation.setDivider(getResources().getDrawable(R.drawable.line_full_brown));
				lvNavigation.setAdapter(adapter);
			}
			lvNavigation.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					entities.get(arg2);
//					String method = entity.getMethod();
//					String methodParams = entity.getMethodParams();
//					getData(method, methodParams);
//					NavigationNumAdapter adapter = new NavigationNumAdapter(getBaseContext(), ShopEntities);
					NavigationBaseEntity entityShop = entities.get(arg2);
					String method = entityShop.getMethod();
					String methodParams = entityShop.getMethodParams();
					Intent intent = new Intent();
					intent.putExtra("method", method);
					intent.putExtra("methodParams", methodParams);
					intent.setClass(HomeActivity.this, ShoppingActivity.class);
					startActivity(intent);
				}
			});
			
		} else if (StringUtil.isEquals(navId,
				Constants.JSONKeyName.NavItemId.GROUPON.getValue())) {
			/*tvFloor = new PlTableView(getBaseContext(), null);

			ArrayList<NavigationBaseEntity> entity = DataCache.NavItemCache
					.get(navId);
			RelativeLayout child[] = new RelativeLayout[entity.size()];

//			CustomFloorClickListener listener = new CustomFloorClickListener();
//			tvFloor.setClickListener(listener);

			for (int i = 0; i < entity.size(); i++) {
				child[i] = (RelativeLayout) mInflater.inflate(
						R.layout.navigation_shopping_item, null);

				tvNavigationFloor = (TextView) child[i]
						.findViewById(R.id.tv_navigation_shopping);
				// tvNavigationCategory =
				// (TextView)child[i].findViewById(R.id.tv_navigation_food_amount);
				tvNavigationFloor.setText(entity.get(i).getName());
				// tvNavigationCategory.setText(floorCategory[i]);

				ViewItem viteItem = new ViewItem(child[i]);
				tvFloor.addViewItem(viteItem);
			}

			ScrollView sv = (ScrollView) findViewById(R.id.sv_navigation_food);
			sv.addView(tvFloor);

			tvFloor.commit();*/
			
		}
	}
	
	private void getData(String method, String methodParams) {
		showLoadingDialog(null);
		DataUtil.getNavFloorDetailsData(getBaseContext(), method, methodParams, mServerConnectionHandler);
	}



	/**
	 * updateView(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @param navId
	 * @exception
	 * @since 1.0.0
	 */
/*	protected void updateView(String navId) {
		ArrayList<NavigationFloorDetailsEntity> entities = DataCache.NavFloorDetailsCache;
		NavigationFloorDetailsEntity entity;
		List<FloorDetailEntity> list = new ArrayList<FloorDetailEntity>();

		for (int i = 0; i < entities.size(); i++) {
			entity = entities.get(i);
			list.add(new FloorDetailEntity(entity.getLogo(), entity.getShopName(), entity.getBrief(), 
					entity.getfScore(), entity.getClassification(), entity.getFavourNum()+""));
		}
		LogUtil.d("BrandActivity", "list szie = " + list.size());
//		FloorDetailAdapterOrg adapter = new FloorDetailAdapterOrg(list,
//				FloorDetailActivity.this);
		adapter = new FloorDetailAdapter(this, list, listView);
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
	}*/
}
