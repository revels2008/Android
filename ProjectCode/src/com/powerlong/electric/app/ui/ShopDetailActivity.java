/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * BrandDetailActivity.java
 * 
 * 2013年9月7日-下午1:23:10
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
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.BrandDeatilGridAdapter;
import com.powerlong.electric.app.adapter.BrandDetailListAdapter;
import com.powerlong.electric.app.adapter.GrouponListAdapter;
import com.powerlong.electric.app.adapter.ImageAndTextListAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.BrandListEntity;
import com.powerlong.electric.app.entity.ImageAndText;
import com.powerlong.electric.app.entity.ShopDetailsEntity;
import com.powerlong.electric.app.entity.ShopItemListEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.ToastUtil;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil.ILoadImageCallback;
import com.powerlong.electric.app.utils.RoundCornerUtil;

/**
 * 
 * ShopDetailActivity 店铺详情
 * 
 * @author: YangCheng Miao
 * 2013年9月7日-下午1:23:10
 * 
 * @version 1.0.0
 * 
 */
public class ShopDetailActivity extends BaseActivity implements OnClickListener {
	private GridView gvDetail;
	private ListView lvDetail;
	private Boolean isGrid = true;
	private ImageView ivReturn;
//	private ProgressBar mpProgressBar = null;
	private ImageView ivShop;
	private TextView tvShopName;
	private TextView tvInstroduction;
	private RatingBar rb;
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
	private String method;
	private String methodParams ="";
	private String orderType = "0";
	private long shopId;
	private RelativeLayout rlTitile;
	private LinearLayout llTabItem1, llTabItem2, llTabItem3, llTabItem4;
	private TextView tvSortItem1, tvSortItem2, tvSortItem3, tvSortItem4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brand_detail);
		
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
		LogUtil.d("ShopDetailActivity", "shopId = "+shopId);
		orderType = intent.getStringExtra("orderType");
		
		findView();
		getData();
	}

	/**
	 * findView(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void findView() {
		//mpProgressBar = (ProgressBar) findViewById(R.id.progressbar);
		gvDetail = (GridView) findViewById(R.id.gv_brand_detail);
		lvDetail = (ListView) findViewById(R.id.lv_brand_detail);
		ivReturn = (ImageView) findViewById(R.id.ivBack);
		ivReturn.setOnClickListener(this);
		ivShop = (ImageView) findViewById(R.id.floor_shopimage);
		tvShopName = (TextView) findViewById(R.id.floor_title_shop);
		tvShopName.requestFocus();
		tvInstroduction = (TextView) findViewById(R.id.floor_shop_details);
		rb = (RatingBar) findViewById(R.id.floor_evaluation);
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
		ivMessage = (ImageView) findViewById(R.id.iv_message);
		ivMessage.setOnClickListener(this);
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
		if(StringUtil.isEmpty(methodParams)){
			JSONObject obj = new JSONObject();
			try {
				obj.put("mall", Constants.mallId);
				obj.put("shopId", shopId);
				obj.put("orderType", orderType);
				DataUtil.getShopDetails(getBaseContext(), mServerConnectionHandler, obj.toString(),true);
			} catch (JSONException e) {
				dismissLoadingDialog();
				e.printStackTrace();
			}
		}
		else{
			DataUtil.getShopDetails(getBaseContext(), mServerConnectionHandler, methodParams,true);
		}
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("ShopDetailActivity", "msg.what = "+msg.what);
			LogUtil.d("ShopDetailActivity", "msg.arg1 = "+msg.arg1);
			switch(msg.what){
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
		}
		
	};
	
	protected void updateView(String navId) {
		setBrandDetailHead();
		setBrandDetailGridView();		
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
		tvShopName.setText(shoEntity.getName());
		tvInstroduction.setText(shoEntity.getInstroduction());
		float f = Float.parseFloat(shoEntity.getEvaluation()+"");
		rb.setRating(f);
		tvClassification.setText(shoEntity.getClassification());
//		tvItemNum.setText(shoEntity.getItemNum()+"");
		tvFavourNum.setText(shoEntity.getFavourNum()+"");
	}
	
	private void setBrandDetailGridView() {
		final ArrayList<ShopItemListEntity> itemEntities = DataCache.ShopItemListCache;
		BrandDeatilGridAdapter adapterGrid;
		if (itemEntities != null) {			
			adapterGrid = new BrandDeatilGridAdapter(this, itemEntities,gvDetail);
			gvDetail.setAdapter(adapterGrid);
			adapterGrid.notifyDataSetChanged();
			
			gvDetail.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					final ShopItemListEntity entity = itemEntities.get(arg2);
					itemId = entity.getId();
					itemName = entity.getName();
					//getItemData();
					Intent intent = new Intent(ShopDetailActivity.this, ItemDetailActivity.class);
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
			
	//		JSONArray itemArray = new JSONArray();
	//		JSONObject item = new JSONObject();
	//		item.put("buyNum", tvCount.getText()+"");
	//		item.put("id", itemId);
	//		item.put("type", itemType+"");
	//		
	//		itemArray.put(item);
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
		final ArrayList<ShopItemListEntity> itemEntities = DataCache.ShopItemListCache;
		BrandDetailListAdapter adapter;
		if (itemEntities != null) {		
			adapter = new BrandDetailListAdapter(this, itemEntities, lvDetail);
			lvDetail.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			lvDetail.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					
					ShopItemListEntity itemEntity= itemEntities.get(arg2);
					itemId = itemEntity.getId();
					type = itemEntity.getType();
					itemName = itemEntity.getName();
					//getItemData();
					
					Intent intent = new Intent(ShopDetailActivity.this, ItemDetailActivity.class);
					intent.putExtra("itemId", itemId);
					intent.putExtra("shopId", shopId);
					intent.putExtra("itemName", itemName);
					startActivity(intent);
					
				}
			});
		}
	}
	private void getItemData() {
		showLoadingDialog(null);
		DataUtil.getItemDetails(this,mItemServerConnectionHandler,1);
	}

	private ServerConnectionHandler mItemServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("ItemDetailActivity", "msg.what = " + msg.what);
			LogUtil.d("ItemDetailActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				Intent intent = new Intent(ShopDetailActivity.this, ItemDetailActivity.class);
				intent.putExtra("itemId", itemId);
				intent.putExtra("type", type);
				intent.putExtra("shopId", shopId);
				intent.putExtra("itemName", itemName);
				startActivity(intent);
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
		case R.id.ivGrid:
			if (!isGrid) {
				lvDetail.setVisibility(View.GONE);
				gvDetail.setVisibility(View.VISIBLE);
				setBrandDetailGridView();
				isGrid = true;
			}
	
			break;
		case R.id.ivList:
			if (isGrid) {
				gvDetail.setVisibility(View.GONE);
				lvDetail.setVisibility(View.VISIBLE);
				setBrandDetailListView();
				isGrid = false;
			}

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
		case R.id.ll_brand_detail_favour:
			break;
		case R.id.ll_brand_detail_cart:
			break;
		case R.id.iv_message:
			IntentUtil.start_activity(this, MyMessageActivity.class);
			break;
		default:
			break;
		}
		
	}

}
