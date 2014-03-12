/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * HomeFragment.java
 * 
 * 2013-7-27-下午04:35:42
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.CartCollectionAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.dialog.NormalDialog;
import com.powerlong.electric.app.entity.CartShopListEntity;
import com.powerlong.electric.app.entity.ItemFavourListEntity;
import com.powerlong.electric.app.entity.ItemListEntity;
import com.powerlong.electric.app.entity.UserAddressEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseFragment;
import com.powerlong.electric.app.update.UpdateManager;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.DoubleUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.ToastUtil;
import com.powerlong.electric.app.widget.TableViewLayout;

/**
 * 
 * HomeFragment
 * 
 * @author: Liang Wang 2013-7-27-下午04:35:42
 * 
 * @version 1.0.0
 * 
 */
@SuppressLint("ValidFragment")
public class ShoppingCartFragment extends BaseFragment implements
		OnClickListener {
	// private PlTableView tableView;
	// private PlTableView tableView2;
	// private ProgressBar mpProgressBar = null;
	private PullToRefreshScrollView sv;
	// 底部总合计
	// 总和
	private TextView sumPrice;
	private Button btn_total;
//	private LinearLayout ll_select_all = null;
//	private Button btnSelectAll = null;
	// private boolean bSelectAll = false;
	private LinearLayout llCart;
	private LinearLayout llFavour;
	private LinearLayout rlBottomcart;
	private RelativeLayout rlBottomFavort;
	private TableViewLayout tableLayout = null;
	private PullToRefreshListView lvFavour;
	private Button btnDelete;
	private Button btnAdd;
	private TextView tvCartTitle;
	private TextView tvFavourTitle;
	private TextView tvCartTips;
	CartCollectionAdapter adapter;
	ArrayList<ItemFavourListEntity> itemEntities;
	private boolean isAllSelected = false;
	private boolean isAddFavourToCart = false; // 用来判断从收藏切到购物车是否刷新界面
	private boolean isFreshFavour = true;
	private int curPage = 1;
	private boolean isRefresh = true;
	private int numOfPage = 1;
	
	private Context context;
	UpdateManager mUpdateManager;
	private String curVersionName;
	private RelativeLayout rlTopLyt;
	private ImageView mImgDelete;
	private ImageView mImgFavour;
	private LinearLayout mLinearDelete;
	private LinearLayout mLinearFavour;
	private CheckBox mCheckSelectAll; //全选CheckBox
	private NormalDialog mDialog;
	public ShoppingCartFragment() {
		super();
	}

	public ShoppingCartFragment(Application application, Activity activity,
			Context context) {
		super(application, activity, context);
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.shopping_cart, container, false);
		isAllSelected = false;
		LogUtil.e("ShoppingCartFragment", "onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#initViews()
	 */
	@Override
	protected void initViews() {
		LogUtil.e("ShoppingCartFragment", "initViews");
		llCart = (LinearLayout) getActivity().findViewById(R.id.ll_tab_shopping_cart);
		llFavour = (LinearLayout) getActivity().findViewById(R.id.ll_tab_favour);
		rlBottomcart = (LinearLayout) findViewById(R.id.bottom_layout);
		rlBottomFavort = (RelativeLayout) findViewById(R.id.bottom_layout_favour);
		tvCartTitle = (TextView) getActivity().findViewById(R.id.tv_tab_shopping_cart);
		tvFavourTitle = (TextView) getActivity().findViewById(R.id.tv_tab_favour);
		sumPrice = (TextView) findViewById(R.id.sum_price);
		tvCartTips = (TextView) findViewById(R.id.shopping_cart_tips);
		tvCartTips.setVisibility(View.INVISIBLE);
		sv = (PullToRefreshScrollView) findViewById(R.id.sv_shopping_cart);
		sv.setMode(Mode.PULL_DOWN_TO_REFRESH);
		btn_total = (Button) findViewById(R.id.btn_total);
		btn_total.setEnabled(false);
		tableLayout = (TableViewLayout) findViewById(R.id.table_layout);

		lvFavour = (PullToRefreshListView) findViewById(R.id.lv_shopping_cart);
		lvFavour.setMode(Mode.BOTH);
		btnDelete = (Button) findViewById(R.id.btn_delete_favour);
		btnAdd = (Button) findViewById(R.id.btn_add);
		rlTopLyt = (RelativeLayout)findViewById(R.id.top_layout);
		mCheckSelectAll = (CheckBox) findViewById(R.id.ck_selectAll);
		sv.setVisibility(View.VISIBLE);
		lvFavour.setVisibility(View.GONE);
		lvFavour.getRefreshableView().setVisibility(View.GONE);
		rlBottomFavort.setVisibility(View.GONE);
//		rlBottomcart.setVisibility(View.VISIBLE);
		// llCart.setBackgroundResource(R.drawable.tab_brown1_press);
		llCart.setSelected(true);
		llFavour.setSelected(false);
		// llFavour.setBackgroundResource(R.drawable.tab_common3_nor);
		tvCartTitle.setTextColor(getResources().getColor(R.color.black));
		tvFavourTitle.setTextColor(getResources().getColor(R.color.black));
		isFreshFavour = true;

		// mpProgressBar = (ProgressBar)findViewById(R.id.progressbar);
		mImgDelete = (ImageView) findViewById(R.id.img_delete);
		mImgFavour = (ImageView) findViewById(R.id.img_favour);
		mLinearDelete = (LinearLayout) findViewById(R.id.linear_delete);
		mLinearFavour = (LinearLayout) findViewById(R.id.linear_favour);
		
		tableLayout.setmImgDelete(mImgDelete);
		tableLayout.setmImgFavour(mImgFavour);
//		tableLayout.setmBtnSelectAll(ll_select_all);
	}

	private void selectAll(){
		LogUtil.e("ShoppingCartFragment", "isAllSelected original = "+isAllSelected);
		isAllSelected = isSelectedAll();
		isAllSelected = !isAllSelected;
		LogUtil.e("ShoppingCartFragment", "isAllSelected = "+isAllSelected);
		final Drawable leftDrawble = getResources().getDrawable(
				isAllSelected ? R.drawable.confirm : R.drawable.all);
		leftDrawble.setBounds(0, 0, leftDrawble.getIntrinsicWidth(),
				leftDrawble.getIntrinsicHeight());
//		btnSelectAll
//				.setCompoundDrawables(leftDrawble, null, null, null);
		tableLayout.updateViewToSelectAll(isAllSelected);
		if (!isAllSelected) {
			btn_total.setEnabled(true);
		} else {
			btn_total.setEnabled(false);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#initEvents()
	 */
	@Override
	protected void initEvents() {
		btnDelete.setOnClickListener(this);
		btnAdd.setOnClickListener(this);
		tableLayout.setHandler(mFooterViewHandler);
		btn_total.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//检查//1. 检测安全支付服务是否安装
				if(context == null) {
					context = getActivity().getBaseContext();
				}
//				MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(context);
//				boolean isMobile_spExist = mspHelper.detectMobile_sp();
//				if (!isMobile_spExist){
//					return;
//				}
				getAddressData();
			}
		});

		mCheckSelectAll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectAll();
			}
		});
		
		llCart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(DataUtil.isUserDataExisted(context))
				{
					Log.i("ShoppingCartNew", "isAddFavourToCart = " + isAddFavourToCart +
							"DataCache.CartShopListCache.size() = " + DataCache.CartShopListCache.size()
							+ "DataCache.CartShopListCache == null ? " + (DataCache.CartShopListCache == null));
					if (isAddFavourToCart
							|| DataCache.CartShopListCache.size() == 0
							|| DataCache.CartShopListCache == null) {
						tableLayout.removeAllViews();
						getData();
						isAddFavourToCart = false;
					} else {
						if(DataCache.CartShopListCache.size()!=0){
							rlTopLyt.setVisibility(View.VISIBLE);
						} else {
							rlTopLyt.setVisibility(View.INVISIBLE);
							mCheckSelectAll.setChecked(false);
						}
						
						if (tableLayout.isAllNotSelected()) {
							rlBottomcart.setVisibility(View.GONE);
						} else {
							rlBottomcart.setVisibility(View.VISIBLE);
						}
					}
				}
					sv.setVisibility(View.VISIBLE);
					lvFavour.getRefreshableView().setVisibility(View.GONE);
					lvFavour.setVisibility(View.GONE);
					rlBottomFavort.setVisibility(View.GONE);
					llCart.setSelected(true);
					llFavour.setSelected(false);
//					tvCartTitle.setTextColor(getResources().getColor(R.color.white));
//					tvCartTitle.setTextColor(getResources().getColor(R.color.black));
//					tvFavourTitle.setTextColor(getResources().getColor(R.color.black));
				}
		});

		llFavour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rlTopLyt.setVisibility(View.GONE);
				sv.setVisibility(View.GONE);
				lvFavour.setVisibility(View.VISIBLE);
				lvFavour.getRefreshableView().setVisibility(View.VISIBLE);
				rlBottomFavort.setVisibility(View.VISIBLE);
				rlBottomcart.setVisibility(View.GONE);
				llCart.setSelected(false);
				llFavour.setSelected(true);
//				tvCartTitle.setTextColor(getResources().getColor(R.color.black));
//				tvFavourTitle.setTextColor(getResources().getColor(R.color.black));
//				tvFavourTitle.setTextColor(getResources().getColor(R.color.white));

				if(DataUtil.isUserDataExisted(context))
				{
					if (isFreshFavour || DataCache.ItemFavourListCache.size() == 0
							|| DataCache.ItemFavourListCache == null) {
						getFavourData();
					}
				}
				isFreshFavour = false;
			}

		});
		
		mLinearDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				mDialog = new NormalDialog(mContext);
				mDialog.setTitle("提示");
				mDialog.setInfo("确定要删除这些商品吗?");
				mDialog.setButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mDialog.cancel();
							}
						},"确认", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mDialog.cancel();
								tableLayout.deleteCartItem();
							}
						});
				mDialog.show();
			}
		});
		
		mLinearFavour.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mDialog = new NormalDialog(mContext);
				mDialog.setTitle("提示");
				mDialog.setInfo("确定要收藏这些商品吗?");
				mDialog.setButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mDialog.cancel();
							}
						},"确认", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mDialog.cancel();
								tableLayout.favorCartItem();
							}
						});
				mDialog.show();
			}
		});
	}

	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.powerlong.electric.app.ui.base.BaseFragment#init()
	 */
	@Override
	protected void init() {
		
		if (DataUtil.isUserDataExisted(getActivity())) {
			getData();
		} else {
//			showCustomToast("请先登录您的账户方可查看购物车内容.");
			ToastUtil.showExceptionTips(getActivity().getBaseContext(), "请先登录您的账户方可查看购物车内容.");
			 IntentUtil.start_activity(getActivity(), LoginActivity.class);
		}
		sv.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
//				tableLayout.removeAllViewsInLayout();
				tableLayout.removeAllViews();
				tableLayout.refreshCart();
				getData();
			}
		});
		
		lvFavour.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity().getBaseContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				curPage = 1;
				isRefresh = true;
				itemEntities.clear();
				getFavourData();
			}
			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity().getBaseContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				isRefresh = false;
				curPage++;
				getFavourData();
			}
		});
		lvFavour.setOnPullEventListener(onPullEventListener);
		rlTopLyt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				tvCartTips.setVisibility((tvCartTips.getVisibility() == View.INVISIBLE) ? 
//						View.VISIBLE : View.INVISIBLE);
			}
		});
		
	}

	private OnPullEventListener onPullEventListener = new OnPullEventListener(){
		@Override
		public void onPullEvent(PullToRefreshBase refreshView, State state,
				Mode direction) {
			if(state == State.REFRESHING || state == State.PULL_TO_REFRESH ||state == State.RELEASE_TO_REFRESH){
//				llmessage.setVisibility(View.GONE);
			}
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		isFreshFavour = true;
	}

	@Override
	public void onPause() {
		super.onPause();
		
	}
	
	public boolean isSelectedAll(){
		List<CartShopListEntity> shopEntities = DataCache.CartShopListCache;
		int itemNum = 0;
		int checkedNum = 0;
		for (int i = 0; i < shopEntities.size(); i++) {
			CartShopListEntity entity = shopEntities.get(i);
			int shopId = entity.getShopId();
			ArrayList<ItemListEntity> itemEntities = DataCache.CartItemListCache
					.get(shopId);
			itemNum += itemEntities.size();
			for (int j = 0; j < itemEntities.size(); j++) {
				ItemListEntity iEntity = itemEntities.get(j);
				if(iEntity.isChecked()){
					checkedNum++;
				}
			}
		}
		return (checkedNum == itemNum)&&itemNum!=0;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		List<CartShopListEntity> shopEntities = DataCache.CartShopListCache;

		for (int i = 0; i < shopEntities.size(); i++) {
			CartShopListEntity entity = shopEntities.get(i);
			int shopId = entity.getShopId();
			ArrayList<ItemListEntity> itemEntities = DataCache.CartItemListCache
					.get(shopId);
			for (int j = 0; j < itemEntities.size(); j++) {
				ItemListEntity iEntity = itemEntities.get(j);
				iEntity.setChecked(false);
			}
		}
	}

	private void getFavourData() {
		showLoadingDialog(null);
		DataUtil.getMyItemFavourList(getActivity().getBaseContext(), mHandler, curPage);
	}

	private Handler mFooterViewHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0: // 计算总金额
				LogUtil.d("ShoppingCartFragment",
						"mFooterViewHandler msg.what = " + msg.what);
				LogUtil.d("ShoppingCartFragment", "msg.arg1 = " + msg.arg1);
				double totalPrice;
				int totalNum = -1;
				btn_total.setEnabled(true);
				Bundle bundle = msg.getData();
				totalPrice = bundle.getDouble("totalPrice");
				totalNum = bundle.getInt("totalNum", 0);
				sumPrice.setText("      合计：" + DoubleUtil.subPrice(totalPrice));
				if (totalPrice == 0) {
					btn_total.setEnabled(false);
				}
				rlBottomcart.setVisibility(totalPrice == 0 ? View.GONE : View.VISIBLE);
				btn_total.setText("结算( " + tableLayout.waitExecuteNum + " )");
				break;
			case 1: // 删除商品
				tableLayout.removeAllViews();
				tableLayout.refreshCart();
				getData();
				rlBottomcart.setVisibility(View.GONE);
				break;
			case 2: // 全选按状态
				Log.i("ShoppingCartNew", "全选状态开启");
				mCheckSelectAll.setChecked(true);
				rlBottomcart.setVisibility(View.VISIBLE);
				break;
			case 3:
				mCheckSelectAll.setChecked(false);
				rlBottomcart.setVisibility(View.VISIBLE);
				break;
			case 4: //全不选
				mCheckSelectAll.setChecked(false);
				rlBottomcart.setVisibility(View.GONE);
				break;
			case 5: // 是否刷新收藏商品
				isFreshFavour = true;
				break;
			case 6: // 跳转至商品详情界面
				Bundle bundle2 = msg.getData();
				String type = bundle2.getString("type");
				if(type.equals("0")) {
					Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
					int i = bundle2.getInt("shopId");
					long shopId = new Long((long) i);
					intent.putExtra("itemId", bundle2.getLong("itemId"));
					intent.putExtra("shopId", shopId);
					intent.putExtra("itemName", bundle2.getString("itemName"));
					startActivity(intent);
				} else {
					Intent intent = new Intent(getActivity(), GrouponDetailActivity.class);
					intent.putExtra("groupId", bundle2.getLong("itemId"));
					startActivity(intent);
				}
				break;
			case 7: //更新购物车数量显示
				if (msg.obj != null && Long.parseLong(msg.obj.toString()) > 0) {
					tvCartTitle.setText("购物车( " + msg.obj.toString() + " )");
				} else {
					tvCartTitle.setText("购物车");
				}
				break;
			case 8:
				showLoadingDialog(null);
				break;
			case 9:
				dismissLoadingDialog();
				break;
			}
		}
	};

	private ServerConnectionHandler mHandler = new ServerConnectionHandler() {
		@Override
		public void handleMessage(Message msg) {
			dismissLoadingDialog();
			LogUtil.d("ShoppingCartFragment", "msg.what = " + msg.what);
			LogUtil.d("ShoppingCartFragment", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				updateFavourView();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
//				showCustomToast(fail);
				ToastUtil.showExceptionTips(mContext, fail);
				break;
			}
			lvFavour.onRefreshComplete();
			// mpProgressBar.setVisibility(View.GONE);
		}
	};

	private void updateFavourView() {
		itemEntities = DataCache.ItemFavourListCache;

		if(itemEntities != null && itemEntities.size() > 0){
			adapter = new CartCollectionAdapter(getActivity(), itemEntities, lvFavour.getRefreshableView());
			lvFavour.setAdapter(adapter);
			adapter.setNotifyOnChange(true);
			if(isRefresh){
				numOfPage = lvFavour.getRefreshableView().getAdapter().getCount();
				lvFavour.getRefreshableView().setSelection(0);
			}else{
				lvFavour.getRefreshableView().setSelection(numOfPage*(curPage-1)-2);
			}
			lvFavour.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					long itemId = itemEntities.get(arg2-1).getItemId();
					Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
					intent.putExtra("itemId", itemId);
					startActivity(intent);
					
				}
			});
			
		}else{
			
		}
	}

	ItemFavourListEntity entitySelected = null;
	List<Integer> rmIndexList = new ArrayList<Integer>();

	private List<Long> getRemoveFavourId() {
		//null pointer
		rmIndexList.clear();
		List<Long> idList = new ArrayList<Long>();
		if(adapter != null) {
			Map<Integer, Boolean> isSelected = adapter.isSelected;
			
			for (int i = 0; i < itemEntities.size(); i++) {
				if (isSelected.get(i)) {
					rmIndexList.add(i);
					entitySelected = itemEntities.get(i);
					idList.add(entitySelected.getItemId());
					Log.e("remove id = ", i + "");
					Log.e("remove itemId = ", entitySelected.getFavourId() + "");
				}
			}
		}
		
		return idList;
	}
	
	private List<Long> getToCartFavourId() {
		//null pointer
		rmIndexList.clear();
		Map<Integer, Boolean> isSelected = adapter.isSelected;
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < itemEntities.size(); i++) {
			if (isSelected.get(i)) {
				rmIndexList.add(i);
				entitySelected = itemEntities.get(i);
				idList.add(entitySelected.getFavourId());
				Log.e("remove id = ", i + "");
				Log.e("remove itemId = ", entitySelected.getFavourId() + "");
			}
		}
		return idList;
	}

	private String getRemoveFavourPrama() {

		List<Long> idList = new ArrayList<Long>();
		idList = getToCartFavourId();
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
			JSONArray itemArray = new JSONArray();
			for (int i = 0; i < idList.size(); i++) {
				JSONObject itemObject = new JSONObject();
				itemObject.put("id", idList.get(i) + "");
				itemArray.put(itemObject);
			}
			obj.put("favourList", itemArray);
			return obj.toString();

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String AddFavourToCart() {

		List<Long> idList = new ArrayList<Long>();
		idList = getRemoveFavourId();
		if(idList.size() > 0) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("mall", Constants.mallId);
				obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
				JSONArray itemArray = new JSONArray();
				for (int i = 0; i < idList.size(); i++) {
					JSONObject itemObject = new JSONObject();
					String itemId = idList.get(i) + "";
					itemObject.put("id", itemId);
					itemObject.put("buyNum", 1);
					itemObject.put("type", "0");
					itemArray.put(itemObject);
				}
				obj.put("itemList", itemArray);
				return obj.toString();
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			btnAdd.setClickable(false);
		}
		return null;
	}

	/*
	 * private ChangeItemEntity AddFavourToCart() { List<Long> idList = new
	 * ArrayList<Long>(); idList = getRemoveFavourId(); ChangeItemEntity
	 * entities = new ChangeItemEntity(); for (int i = 0; i < idList.size();
	 * i++) { entities.setItemId(idList.get(i)); entities.setItemNum(1);
	 * entities.setType(0+""); } return entities; }
	 */

	private void getData() {
		mCheckSelectAll.setChecked(false);
		showLoadingDialog(null);
		rlBottomcart.setVisibility(View.GONE);
		DataUtil.getCartListData(mServerConnectionHandler);
		HttpUtil.getCartTotalCount(mContext, mFooterViewHandler);
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {
			LogUtil.d("ShoppingCartFragment", "msg.what = " + msg.what);
			LogUtil.d("ShoppingCartFragment", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				Log.i("ShoppingCartNew", "getData() DataCache.CartShopListCache.size() = " 
						+ DataCache.CartShopListCache.size());
				if(DataCache.CartShopListCache.size()!=0){
					rlTopLyt.setVisibility(View.VISIBLE);
				} else {
					rlTopLyt.setVisibility(View.INVISIBLE);
				}
				mCheckSelectAll.setChecked(false);
				tableLayout.createTableView();
				
				mUpdateManager = new UpdateManager(ShoppingCartFragment.this.context);
				if(Constants.isUpdate) {
					HttpUtil.getVersionCode(ShoppingCartFragment.this.context, mServerConnectionHandlerNew);
				}
				tableLayout.clearAllMap();
//				selectAll();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
//				showCustomToast(fail);
//				ToastUtil.showExceptionTips(mContext, fail);
				if(DataCache.CartShopListCache.size()!=0){
					rlTopLyt.setVisibility(View.VISIBLE);
				} else {
					rlTopLyt.setVisibility(View.INVISIBLE);
					mCheckSelectAll.setChecked(false);
				}
				break;
			}
			sv.onRefreshComplete();
			// mpProgressBar.setVisibility(View.GONE);
		}

	};

	private ServerConnectionHandler mAddOrRemoveFavourHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {
			LogUtil.d("ShoppingCartFragment", "msg.what = " + msg.what);
			LogUtil.d("ShoppingCartFragment", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			String msg1 = (String) msg.obj;
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				RemoveFromFavour();
				updateFavourView();
				DataUtil.getMyItemFavourList(getActivity().getBaseContext(), mHandler,
						1);
//				showCustomToast("移出收藏夹成功.");
				ToastUtil.showExceptionTips(mContext, "移出收藏夹成功.");
				
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
//				showCustomToast(msg1);
				ToastUtil.showExceptionTips(mContext, msg1);
				break;
			}
			// mpProgressBar.setVisibility(View.GONE);
		}

	};

	private ServerConnectionHandler mAddFavourToCartHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {
			LogUtil.d("ShoppingCartFragment", "msg.what = " + msg.what);
			LogUtil.d("ShoppingCartFragment", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				RemoveFromFavour();
				updateFavourView();
				DataUtil.getMyItemFavourList(getActivity().getBaseContext(), mHandler,1);
				if (DataCache.UserDataCache.size() > 0) {
					JSONObject obj = new JSONObject();
					try {
						obj.put("mall", Constants.mallId);
						obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
						DataUtil.queryMyMessageListData(mContext,
								HomeActivityNew.mServerConnectionHandlerNew2, obj.toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}
					HttpUtil.queryUnReadMsgJson(mContext,
							HomeActivityNew.mServerConnectionHandlerNew2, obj.toString());
					HttpUtil.getCartItemCount(mContext,
							HomeActivityNew.mServerConnectionHandlerNewCart);
					HttpUtil.getCartTotalCount(mContext, mFooterViewHandler);
				}
//				showCustomToast("移入购物车成功.");
				ToastUtil.showExceptionTips(mContext, "移入购物车成功.");
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
//				showCustomToast(fail);
				ToastUtil.showExceptionTips(mContext, fail);
				break;
			}
		}

	};

	private void RemoveFromFavour() {
//		itemEntities = DataCache.ItemFavourListCache;
//		if (rmIndexList != null)
//			for (int i=0; i<rmIndexList.size(); i++) {
//				DataCache.ItemFavourListCache.remove(rmIndexList.get(i));
//				Log.e("<<<<<<<<<<<<<<<<<<", "1");
//			}
			
		DataCache.ItemFavourListCache.clear();
	}

	private void getAddressData() {
		showLoadingDialog(null);
		DataUtil.getUserAddressListData(mAddHandler);
	}

	// 结算按钮
	private ServerConnectionHandler mAddHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {
			LogUtil.d("AddressManageActivity", "msg.what = " + msg.what);
			LogUtil.d("AddressManageActivity", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				List<UserAddressEntity> entities = DataCache.UserAddressListCache;
				if (entities.size() == 0) {
//					IntentUtil.start_activity(getActivity(),
//							AddressAddActivity.class);
					Intent intent = new Intent(getActivity(), AddressAddActivity.class);
					intent.putExtra("from", 2);
					startActivity(intent);
				} else {
					IntentUtil.start_activity(getActivity(),
							ShoppingCartSettleAccountActivity.class);
				}
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
//				showCustomToast(fail);
				ToastUtil.showExceptionTips(mContext, fail);
				break;
			}
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_delete_favour:
			if(adapter==null)
				break;
			showLoadingDialog(null);
			String param = getRemoveFavourPrama();
			HttpUtil.requestAddOrDeleteItemToFavour(mAddOrRemoveFavourHandler,
					param, 1);

//			getFavourData();
//			adapter.notifyDataSetChanged();
			break;
		case R.id.btn_add:
			showLoadingDialog(null);
			String param2 = AddFavourToCart();
			HttpUtil.AddFavoirToCart(mAddFavourToCartHandler, param2);
			isAddFavourToCart = true;
			getFavourData();
		default:
			break;
		}

	}
	
	private ServerConnectionHandler mServerConnectionHandlerNew = new ServerConnectionHandler() {
		@Override
		public void handleMessage(Message msg) {
			// .d("HomeActivity", "msg.what = " + msg.what);
			// .d("HomeActivity", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				//long versionName = Long.parseLong(Integer.toString(msg.arg1));
				LogUtil.d("cur version", curVersionName + "");
				LogUtil.d("cur version", DataCache.versionCode + "");
				try {
					PackageManager packageManager = getActivity().getPackageManager();
					PackageInfo packageInfo = packageManager.getPackageInfo(
							"com.powerlong.electric.app", 0);
					curVersionName = packageInfo.versionName;
					if (!TextUtils.isEmpty(curVersionName)) {

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (DataCache.versionCode!= null && curVersionName != null && !DataCache.versionCode.equals(curVersionName)) {
					mUpdateManager.checkUpdateInfo(ShoppingCartFragment.this.context);
				}
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				//String fail = (String) msg.obj;
				//showCustomToast(fail);
				break;
			}
		}

	};
}
