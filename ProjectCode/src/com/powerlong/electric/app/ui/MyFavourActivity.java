/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * MyFavourActivity.java
 * 
 * 2013年9月16日-下午5:58:50
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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.CollectionAdapter;
import com.powerlong.electric.app.adapter.MyFavourItemAdapter;
import com.powerlong.electric.app.adapter.MyFavourShopAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.ItemFavourListEntity;
import com.powerlong.electric.app.entity.ShopFavourListEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.LogUtil;

/**
 * 
 * MyFavourActivity
 * 
 * @author: YangCheng Miao 2013年9月16日-下午5:58:50
 * 
 * @version 1.0.0
 * 
 */
public class MyFavourActivity extends BaseActivity implements OnClickListener {

	private ImageView iv_back;
    private PullToRefreshListView lv_shop,lv_item;
    private LinearLayout llmessage;
    private TextView tvMsgTips;
    private ImageView ivMsgTips;
    private Button btn_shop,btn_item;
    private Button btn_delete,btn_putcart;
    private static boolean isShop=true;
    private  RelativeLayout rel_bottom;
    List<Integer> rmIndexList = new ArrayList<Integer>();
    Map<Integer, Boolean> isShopSelected;
    Map<Integer, Boolean> isItemSelected;
    List<ShopFavourListEntity> shopEntities = new ArrayList<ShopFavourListEntity>();
    ArrayList<ItemFavourListEntity> itemEntities = new ArrayList<ItemFavourListEntity>();
    CollectionAdapter adapter;
    private boolean isFreshItem = true;
    private boolean isShopDel = true;
	private int curPage = 1;
    @Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_favour);
		InitView();
		btn_shop.setSelected(true);
		curPage = 1;
		getData();
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
		DataUtil.getMyShopFavourList(getApplicationContext(), shophandle, curPage );
	}
	private ServerConnectionHandler shophandle=new ServerConnectionHandler(){
		public void handleMessage(Message msg) {		
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				updateView(Integer.toString(msg.arg1));
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
			dismissLoadingDialog();
			lv_shop.onRefreshComplete();
		}
	};
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				//显示底部
				rel_bottom.setVisibility(View.VISIBLE);
				break;
			case 1:
				//隐藏底部
				rel_bottom.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		};
	};
	private void updateView(String string) {
		lv_shop.getRefreshableView().setVisibility(View.VISIBLE);
		lv_shop.setVisibility(View.VISIBLE);
		shopEntities.clear();
		shopEntities.addAll(DataCache.ShopFavourListCache);
		LogUtil.d("===", String.valueOf(shopEntities.size()));
		if(shopEntities.size()>0 && shopEntities != null ){
			MyFavourShopAdapter adapter=new MyFavourShopAdapter(getApplicationContext(), shopEntities, handler);
			isShopSelected = adapter.isSelected;
			lv_shop.setAdapter(adapter);
			llmessage.setVisibility(View.GONE);
			lv_shop.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					long shopId = shopEntities.get(arg2-1).getShopId();
					Intent intent = new Intent(MyFavourActivity.this, ShopDetailActivityNew.class);
					intent.putExtra("shopId", shopEntities.get(arg2-1).getShopId());

					startActivity(intent);
				}
			});
		}else {
			llmessage.setVisibility(View.VISIBLE);
//			lv_shop.setVisibility(View.GONE);
		};
		
	};
	private void updateItemView(String string) {
			List<ItemFavourListEntity> entities = DataCache.ItemFavourListCache;
			LogUtil.d("===", String.valueOf(entities.size()));
		
			if(entities.size()>0){
				MyFavourItemAdapter adapter=new MyFavourItemAdapter(getApplicationContext(), entities,handler);
				
				lv_item.setAdapter(adapter);
				lv_item.setVisibility(View.VISIBLE);
				llmessage.setVisibility(View.GONE);
			}else {
				llmessage.setVisibility(View.VISIBLE);
//				lv_item.setVisibility(View.GONE);
			};
			
		};

	protected void InitView() {
		iv_back = (ImageView) this.findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		
		lv_shop=(PullToRefreshListView) findViewById(R.id.lv_shop);
		lv_item=(PullToRefreshListView) findViewById(R.id.lv_item);
		
		lv_shop.setMode(Mode.BOTH);
		lv_item.setMode(Mode.BOTH);
		
		lv_shop.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				shopEntities.clear();
				curPage = 1;
				getData();
			}
			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				curPage++;
				getUpdateData();
			}
		});
		lv_shop.setOnPullEventListener(onPullEventListener);
		
		lv_item.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				curPage = 1;
				itemEntities.clear();
				getFavourData();
			}
			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				curPage++;
				getFavourData();
			}
		});
		lv_item.setOnPullEventListener(onPullEventListener);
		llmessage=(LinearLayout)findViewById(R.id.ll_message);
		tvMsgTips = (TextView)findViewById(R.id.tv_msg_tips);
		ivMsgTips =(ImageView)findViewById(R.id.iv_msg_tips);
		
		
		btn_shop=(Button) findViewById(R.id.btn_shop);
		btn_shop.setOnClickListener(this);
		btn_item=(Button) findViewById(R.id.btn_item);
		btn_item.setOnClickListener(this);
		
		rel_bottom=(RelativeLayout) this.findViewById(R.id.rel_bottom);
		
		btn_delete=(Button) rel_bottom.findViewById(R.id.btn_delete);
		btn_delete.setOnClickListener(this);
		btn_putcart=(Button) rel_bottom.findViewById(R.id.btn_putcart);
		btn_putcart.setOnClickListener(this);
	}

	private List<Long> getRemoveFavourShopId() {
		//null pointer
		rmIndexList.clear();
		
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < shopEntities.size(); i++) {
			if (isShopSelected.get(i)) {
				ShopFavourListEntity entity = shopEntities.get(i);
				rmIndexList.add(i);
				idList.add(entity.getFavourId());
				Log.e("remove id = ", i + "");
			}
		}
		return idList;
	}
	
	private OnPullEventListener onPullEventListener = new OnPullEventListener(){
		@Override
		public void onPullEvent(PullToRefreshBase refreshView, State state,
				Mode direction) {
			if(state == State.REFRESHING || state == State.PULL_TO_REFRESH ||state == State.RELEASE_TO_REFRESH){
				llmessage.setVisibility(View.GONE);
			}
		}
	};

	private String getRemoveFavourShopPrama() {

		List<Long> idList = new ArrayList<Long>();
		idList = getRemoveFavourShopId();
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
			JSONArray shopArray = new JSONArray();
			for (int i = 0; i < idList.size(); i++) {
				JSONObject shopObject = new JSONObject();
				shopObject.put("id", idList.get(i));
				shopArray.put(shopObject);
			}
			obj.put("favourList", shopArray);
			Log.e("remove prama", obj.toString());
			return obj.toString();

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void getDeleteShopData() {
		HttpUtil.delShopFromFavour(mDelShopHandler, getRemoveFavourShopPrama());
	}
	
	private ServerConnectionHandler mDelShopHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {
			LogUtil.d("ShoppingCartFragment", "msg.what = " + msg.what);
			LogUtil.d("ShoppingCartFragment", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				showCustomToast((String) msg.obj);
				getData();
				rel_bottom.setVisibility(View.GONE);
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
				showCustomToast(fail);
				break;
			}
			// mpProgressBar.setVisibility(View.GONE);
		}

	};

	private List<Long> getRemoveFavourItemId() {
	List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < itemEntities.size(); i++) {
			if (isItemSelected.get(i)) {
				ItemFavourListEntity entity = itemEntities.get(i);
//				idList.add(entity.getItemId());
				idList.add(entity.getFavourId());
				Log.e("remove id = ", i + "");
			}
		}
		return idList;
	}

	private String getRemoveFavourItemPrama() {

		List<Long> idList = new ArrayList<Long>();
		idList = getRemoveFavourItemId();
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
			JSONArray shopArray = new JSONArray();
			for (int i = 0; i < idList.size(); i++) {
				JSONObject shopObject = new JSONObject();
				shopObject.put("id", idList.get(i));
				shopArray.put(shopObject);
			}
			obj.put("favourList", shopArray);
			Log.e("remove prama", obj.toString());
			return obj.toString();

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void getDeleteItemData() {
		HttpUtil.requestAddOrDeleteItemToFavour(mDelItemHandler, getRemoveFavourItemPrama(), 1);
	}
	
	private ServerConnectionHandler mDelItemHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {
			LogUtil.d("ShoppingCartFragment", "msg.what = " + msg.what);
			LogUtil.d("ShoppingCartFragment", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				showCustomToast((String) msg.obj);
//				getData();
				getFavourData();
				rel_bottom.setVisibility(View.GONE);
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
				showCustomToast(fail);
				break;
			}
			// mpProgressBar.setVisibility(View.GONE);
		}

	};
	
	private void getFavourData() {
		showLoadingDialog(null);
		DataUtil.getMyItemFavourList(getBaseContext(), mHandler, curPage);
	}
	
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
				isFreshItem = false;
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
				showCustomToast(fail);
				break;
			}
			// mpProgressBar.setVisibility(View.GONE);
			lv_item.onRefreshComplete();
		}
	};
	
	private void updateFavourView() {
		itemEntities.clear();
		itemEntities.addAll(DataCache.ItemFavourListCache);

		if(itemEntities != null && itemEntities.size() > 0){
			llmessage.setVisibility(View.GONE);
			adapter = new CollectionAdapter(this, itemEntities, lv_item.getRefreshableView(), handler);
			isItemSelected = adapter.isSelected;
			lv_item.setAdapter(adapter);
			adapter.setNotifyOnChange(true);
			lv_item.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					long itemId = itemEntities.get(arg2-1).getItemId();
					Intent intent = new Intent(MyFavourActivity.this, ItemDetailActivity.class);
					intent.putExtra("itemId", itemId);
					startActivity(intent);
				}
			});
		}else{
			lv_item.setVisibility(View.VISIBLE);
			llmessage.setVisibility(View.VISIBLE);
		}
	}
	
	private String AddFavourToCartParam() {

		List<Long> idList = new ArrayList<Long>();
		idList = getFavourItemIdToCart();
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

	}
	
	private List<Long> getFavourItemIdToCart() {
		List<Long> idList = new ArrayList<Long>();
			for (int i = 0; i < itemEntities.size(); i++) {
				if (isItemSelected.get(i)) {
					ItemFavourListEntity entity = itemEntities.get(i);
					idList.add(entity.getItemId());
//					idList.add(entity.getFavourId());
					Log.e("remove id = ", i + "");
				}
			}
			return idList;
		}
	private ServerConnectionHandler mAddFavourToCartHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {
			LogUtil.d("ShoppingCartFragment", "msg.what = " + msg.what);
			LogUtil.d("ShoppingCartFragment", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
//				RemoveFromFavour();
				getFavourData();
				showCustomToast("移入购物车成功.");
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
				showCustomToast(fail);
				break;
			}
		}

	};

	@Override
	protected void onResume() {
		super.onResume();
//		isShop=true;
//		btn_shop.setSelected(true);
//		btn_item.setSelected(false);
//		lv_item.getRefreshableView().setVisibility(View.GONE);
//		lv_shop.getRefreshableView().setVisibility(View.VISIBLE);
//		btn_putcart.setVisibility(View.GONE);
//		llmessage.setVisibility(View.GONE);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.btn_shop:
			isShop=true;
			btn_shop.setSelected(true);
			btn_item.setSelected(false);
			tvMsgTips.setText("您还未收藏店铺");
			ivMsgTips.setImageResource(R.drawable.no_shop);
			lv_item.getRefreshableView().setVisibility(View.GONE);
			lv_item.setVisibility(View.GONE);
			lv_shop.getRefreshableView().setVisibility(View.VISIBLE);
			lv_shop.setVisibility(View.VISIBLE);
			btn_putcart.setVisibility(View.GONE);
			llmessage.setVisibility(View.GONE);
			isShopDel = true;
			curPage = 1;
			shopEntities.clear();
			getData();
			break;
		case R.id.btn_item:
			btn_shop.setSelected(false);
			btn_item.setSelected(true);
			isShop=false ;
			tvMsgTips.setText("您还未收藏商品");
			ivMsgTips.setImageResource(R.drawable.no_commodity);
			lv_shop.getRefreshableView().setVisibility(View.GONE);
			lv_shop.setVisibility(View.GONE);
			lv_item.getRefreshableView().setVisibility(View.VISIBLE);
			lv_item.setVisibility(View.VISIBLE);
			llmessage.setVisibility(View.GONE);
			btn_putcart.setVisibility(View.VISIBLE);
			isShopDel = false;
			curPage = 1;
			itemEntities.clear();
//			if (DataCache.ItemFavourListCache.size() == 0 || isFreshItem) {
				getFavourData();
//			}
			
			break;
		case R.id.btn_delete:
			if(isShopDel) {
				getDeleteShopData();
			} else {
				
				getDeleteItemData();				
			}
			
			
			break;
		case R.id.btn_putcart:
			HttpUtil.AddFavoirToCart(mAddFavourToCartHandler, AddFavourToCartParam());
			break;
		default:
			break;
		}

	}
	
	private void getUpdateData() {
		showLoadingDialog(null);
		DataUtil.getMyShopFavourList(getApplicationContext(), shopUpdatehandle, curPage );
	}
	private ServerConnectionHandler shopUpdatehandle=new ServerConnectionHandler(){
		public void handleMessage(Message msg) {		
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				updateShopView(Integer.toString(msg.arg1));
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
			dismissLoadingDialog();
			lv_shop.onRefreshComplete();
		}
	};
	private void updateShopView(String string) {
		lv_shop.getRefreshableView().setVisibility(View.VISIBLE);
		lv_shop.setVisibility(View.VISIBLE);
		shopEntities.addAll(DataCache.ShopFavourListCache);
		LogUtil.d("===", String.valueOf(shopEntities.size()));
		if(shopEntities.size()>0 && shopEntities != null ){
			MyFavourShopAdapter adapter=new MyFavourShopAdapter(getApplicationContext(), shopEntities, handler);
			isShopSelected = adapter.isSelected;
			lv_shop.setAdapter(adapter);
			llmessage.setVisibility(View.GONE);
			lv_shop.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					long shopId = shopEntities.get(arg2-1).getShopId();
					Intent intent = new Intent(MyFavourActivity.this, ShopDetailActivityNew.class);
					intent.putExtra("shopId", shopEntities.get(arg2-1).getShopId());

					startActivity(intent);
				}
			});
		}else {
			llmessage.setVisibility(View.VISIBLE);
//			lv_shop.setVisibility(View.GONE);
		};
		
	};
}
