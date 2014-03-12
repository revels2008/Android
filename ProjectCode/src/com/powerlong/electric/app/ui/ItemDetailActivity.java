/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ItemDetailActivity.java
 * 
 * 2013年9月9日-上午10:36:29
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.f;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.ViewPagerAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.cache.FileCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.BarginListEntity;
import com.powerlong.electric.app.entity.ImageListEntity;
import com.powerlong.electric.app.entity.ItemDetailEntity;
import com.powerlong.electric.app.entity.PropEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil.ILoadImageCallback;
import com.powerlong.electric.app.utils.ToastUtil;

/**
 * 
 * ItemDetailActivity
 * 
 * @author: YangCheng Miao
 * 2013年9月9日-上午10:36:29
 * 
 * @version 1.0.0
 * 
 */
public class ItemDetailActivity extends BaseActivity implements OnClickListener {	 
	private LinearLayout llActivities;
	private long itemId;
	private long shopId;
	private String shopName;
	private String itemName;
	private int  itemType = 0;
	private TextView tvPlPrice;
	private TextView tvPriceBefore;
	private TextView tvFreight; //运费
	private TextView tvSellNum30; //30天售出
	private TextView tvFavourNum;//收藏数
	private TextView tvCommentNum; //评论数
	private TextView tvShopName;
	private RatingBar rbShopEvaluation; //店铺评级
	private ImageView ivShopImage;
	private String tvDetailUrl; //商品详情url
	private HorizontalScrollView hsv;	
	private TextView tvColor;
	private TextView tvSize;
	private LinearLayout llColor;
	private LinearLayout llSize;
	private ImageView ivArrow;
	private LinearLayout llExpand;
	private Boolean isExpand = true; //填写属性界面是否收起   true:展开 false:收起
	private ImageView ivReduce;
	private ImageView ivRaise;
	private TextView tvCount;
	private TextView tvShopProp;
	private ImageView ivBack;
	private ImageView ivGrid;
	private ImageView ivList;
	private boolean spec1IsRefresh = false;
	private boolean spec2IsRefresh = false;
	ArrayList<ItemDetailEntity> itemDetailEntities;
	ArrayList<BarginListEntity> itemBarginEntities;
	ArrayList<ImageListEntity> itemImageEntiies;
	ArrayList<PropEntity> ItemsPropList1;
	HashMap<String,ArrayList<PropEntity>> ItemsProp;
	ArrayList<PropEntity> ItemsPropList2;
	LayoutInflater mInflater;
	private RelativeLayout rlDetail;
	private RelativeLayout llEvaluate;
	private RelativeLayout rlProp;
	private LinearLayout llAddToFavour;
	private LinearLayout llAddToCart;
	private LinearLayout llChat;
	private ImageView ivChat;
	private boolean userOper = false;
	private boolean userOper2 = false;
	private TextView tvItemName;
	private LinearLayout llEnterShop;
	private TextView tvStockNum;
	private Boolean tvNoStock = false;
	private Button btnBuyNow;
	private String methodParams ="";
	private ImageView ivGotoCart;
	private Button mBtnBuy;
	private ArrayList<String> urlList  = new ArrayList<String>();
	private int selectId;
	private boolean isGroupon = false;
	private boolean isProp2Existed = false;
	private TextView tvAddToCart;
	private int type; //0实体 1展示 2虚拟
	private RelativeLayout rlGrouponItem;
	private long grouponId;
	private ImageView ivGrouponDiliver;
	private int specIndexFirst = 0;
	private int specIndexSec = 0;
	private List<Button> btnList;
	private List<Button> btnList2;
	private Map<String,List<Button>> btnMap;
	private boolean isFirstIn = true;
	private ViewPager mViewPager;
	private List<View> mViewList;
	private ImageView ivMessageID;
	private TextView tvMessageID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detail);
		
		Intent intent = getIntent();
		itemId = intent.getLongExtra("itemId", itemId);
		shopId = intent.getLongExtra("shopId", shopId);
		itemName = intent.getStringExtra("itemName");
		itemType = intent.getIntExtra("type", 0);
		methodParams = intent.getStringExtra("methodParams");
		if(!StringUtil.isEmpty(methodParams)){
			try {
				JSONObject obj = new JSONObject(methodParams);
				itemId = obj.getInt("itemId");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		btnMap = new HashMap<String, List<Button>>();
		btnList = new ArrayList<Button>();
		LogUtil.d("ItemDetailActivity", "itemId = "+itemId);
		findView();
		getData(itemId);		 
	}
	
	private void findView() {
		ivMessageID = (ImageView)findViewById(R.id.iv_message_item_detail);
		tvMessageID = (TextView)findViewById(R.id.tv_messag_item_detail);
		ivMessageID.setOnClickListener(this);
		tvMessageID.setOnClickListener(this);
		mBtnBuy = (Button)findViewById(R.id.btn_groupon_detail_buy);
		mBtnBuy.setOnClickListener(this);
		llActivities = (LinearLayout) findViewById(R.id.ll_item_detail_activities);
		tvPlPrice = (TextView) findViewById(R.id.tv_item_detail_price_now);
		tvPriceBefore = (TextView) findViewById(R.id.tv_item_detail_price_before);
//		tvFreight = (TextView) findViewById(R.id.tvitem_detail_freight);
		tvSellNum30 = (TextView) findViewById(R.id.tv_item_detail_sell_num); 
		tvFavourNum = (TextView) findViewById(R.id.tv_item_collection_count);
		tvCommentNum = (TextView) findViewById(R.id.tv_item_detail_comment); 
		tvShopName = (TextView) findViewById(R.id.tv_item_detail_shop_name);
		rbShopEvaluation = (RatingBar) findViewById(R.id.rb_item_evaluation); //店铺评级
		ivShopImage = (ImageView) findViewById(R.id.iv_item_detail_icon);
		hsv = (HorizontalScrollView) findViewById(R.id.hsv);
		tvColor = (TextView) findViewById(R.id.tv_item_detail_color);
		tvSize = (TextView) findViewById(R.id.tv_item_detail_size);
		llColor = (LinearLayout) findViewById(R.id.ll_item_detail_color);
		llSize = (LinearLayout) findViewById(R.id.ll_item_detail_size);
		llColor.setOrientation(LinearLayout.VERTICAL);
		llSize.setOrientation(LinearLayout.HORIZONTAL);
		ivArrow = (ImageView) findViewById(R.id.iv_arrow);
		ivArrow.setOnClickListener(this);
		rlProp = (RelativeLayout) findViewById(R.id.rl_prop_open);
		rlProp.setOnClickListener(this);
		llExpand = (LinearLayout) findViewById(R.id.ll_expand);
		ivReduce = (ImageView) findViewById(R.id.iv_item_detail_reduce);
		ivReduce.setOnClickListener(this);
		ivRaise = (ImageView) findViewById(R.id.iv_item_detail_raise);
		ivRaise.setOnClickListener(this);
		tvCount = (TextView) findViewById(R.id.tv_item_detail_count);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		ivGrid = (ImageView) findViewById(R.id.ivGrid);
		ivGrid.setVisibility(View.GONE);
		ivList = (ImageView) findViewById(R.id.ivList);
		ivList.setVisibility(View.GONE);
//		ivList.setImageDrawable(drawable);
		llEvaluate = (RelativeLayout) findViewById(R.id.rl_item_detail_evaluate);
		llEvaluate.setOnClickListener(this);
		rlDetail = (RelativeLayout)findViewById(R.id.rl_item_detail);
		rlDetail.setOnClickListener(this);
		rlGrouponItem = (RelativeLayout) findViewById(R.id.rl_item_detail_groupon);
		rlGrouponItem.setOnClickListener(this);
		ivGrouponDiliver = (ImageView) findViewById(R.id.iv_groupon_diliver);
		btnBuyNow = (Button) findViewById(R.id.btn_buy_now);
//		btnBuyNow.setVisibility(View.VISIBLE);
		btnBuyNow.setOnClickListener(this);
		llAddToFavour = (LinearLayout) findViewById(R.id.ll_tab_item_favour);
		llAddToFavour.setOnClickListener(this);
		llAddToCart = (LinearLayout) findViewById(R.id.ll_tab_item_cart);
		llAddToCart.setOnClickListener(this);
		tvAddToCart = (TextView) findViewById(R.id.tv_sort_item2);
		tvItemName = (TextView) findViewById(R.id.tv_item_detail_title);
		tvItemName.setText(itemName);
		llEnterShop = (LinearLayout) findViewById(R.id.ll_enter_shop);
		llEnterShop.setOnClickListener(this);
		llChat = (LinearLayout) findViewById(R.id.ll_chat);
		ivChat = (ImageView)findViewById(R.id.iv_chat);
		llChat.setOnClickListener(this);
		ivChat.setOnClickListener(this);
		tvShopProp = (TextView)findViewById(R.id.rb_item_prop);
		tvStockNum = (TextView) findViewById(R.id.tv_item_detail_stock_num);
		ivGotoCart = (ImageView) findViewById(R.id.ivGoToCart);
		ivGotoCart.setVisibility(View.VISIBLE);
		ivGotoCart.setOnClickListener(this);
		mViewPager = (ViewPager)findViewById(R.id.viewpager);
	}

	private void getData(long itemId) {
		isFirstIn=true;
		showLoadingDialog(null);
		DataUtil.getItemDetails(this,mServerConnectionHandler,itemId);
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("ItemDetailActivity", "msg.what = " + msg.what);
			LogUtil.d("ItemDetailActivity", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
//				if(!userOper)
					updateView(userOper);
//				else{
//					userOper = false;
//					updatePropChangedView();
//				}
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
				String exception = (String)msg.obj;
				showCustomToast(exception);
				if(!Constants.isShowDefaultUi)
					finish();
				break;
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				if(!Constants.isShowDefaultUi)
					finish();
				break;
			}
		}

	};
	
	private void updateView(boolean isPropChanged) {
		itemDetailEntities = DataCache.ItemsDetailsCache;
		itemBarginEntities = DataCache.ItemsBarginListCache;
		itemImageEntiies = DataCache.ItemsImageListCache;
		ItemsPropList1 = DataCache.ItemsPropList1Cache;
		ItemsProp = DataCache.ItemsPropList2Cache;
		ItemsPropList2 = new ArrayList<PropEntity>();
		mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		List<View> mViewList = new ArrayList<View>();
		LinearLayout llOut = new LinearLayout(hsv.getContext());
		llOut.setOrientation(LinearLayout.HORIZONTAL);
		
		for (int i=0; i<itemDetailEntities.size(); i++) {
			ItemDetailEntity entity = itemDetailEntities.get(i);
			shopId = entity.getShopId();
			shopName = entity.getShopName();
			grouponId = entity.getGrouponId();
			if(grouponId == 0) {
				rlGrouponItem.setVisibility(View.GONE);
				ivGrouponDiliver.setVisibility(View.GONE);
			}
			type = entity.getType();
			if(type == 1) {
				llAddToCart.setClickable(false);
				llAddToCart.setFocusable(false);
				tvAddToCart.setText("展示商品");
				mBtnBuy.setText("展示商品");
				mBtnBuy.setClickable(false);
				mBtnBuy.setFocusable(false);
			}else if(type == 2){
				llAddToCart.setClickable(false);
				llAddToCart.setFocusable(false);
				tvAddToCart.setText("虚拟商品");
				mBtnBuy.setText("虚拟商品");
				mBtnBuy.setClickable(false);
				mBtnBuy.setFocusable(false);
			}else if(type == 3){
				llAddToCart.setClickable(false);
				llAddToCart.setFocusable(false);
				tvAddToCart.setText("即将推出");
				mBtnBuy.setText("即将推出");
				mBtnBuy.setClickable(false);
				mBtnBuy.setFocusable(false);
			}
			int stat = entity.getStat();
			if(stat == 0) {
				llAddToCart.setClickable(false);
				llAddToCart.setFocusable(false);
				tvAddToCart.setText("商品已下架");
				mBtnBuy.setText("商品已下架");
				mBtnBuy.setClickable(false);
				mBtnBuy.setFocusable(false);
			}
			tvPlPrice.setText(Constants.moneyTag + entity.getPlPrice());
			tvPriceBefore.setText(Constants.moneyTag + entity.getListPrice());
			tvPriceBefore.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//			tvFreight.setText(entity.getFreight()+"");
			tvSellNum30.setText(entity.getSellNum30()+"");
			tvFavourNum.setText(entity.getFavourNum()+"");
			tvCommentNum.setText(entity.getCommentNum()+"");
			
			
			LogUtil.d("ItemDetailActivity", "shopName= "
					+ entity.getShopName());
			tvShopName.setText(entity.getShopName());
			tvItemName.setText(entity.getName());
			tvShopProp.setText(entity.getShopProp());
			rbShopEvaluation.setRating(Float.parseFloat(entity.getShopEvaluation()+""));
			if(!spec1IsRefresh){
				if (isExpand) {
					tvColor.setText(entity.getProp1Name());
				} else {
					tvColor.setText(entity.getProp1Name());
				}
				tvSize.setText(entity.getProp2Name());
			}
			
			if(!isPropChanged) {
				String imageUrl = entity.getShopImage();
				if (imageUrl != null) {
					AsyncImageLoaderUtil imageLoader = new AsyncImageLoaderUtil();
					Drawable cachedImage = imageLoader.loadDrawable(imageUrl,
							ivShopImage, new ILoadImageCallback() {
								@Override
								public void onObtainDrawable(Drawable drawable,
										ImageView imageView) {
									imageView.setImageDrawable(drawable);
								}

							});
					if (cachedImage == null) {
						ivShopImage.setImageResource(R.drawable.pic_56);
						// Log.e("Adapter", "null");
					} else {
						ivShopImage.setImageDrawable(cachedImage);
						
					}
				}
			}
			
			
			
		}
		
		if (itemBarginEntities == null || itemBarginEntities.size()<=0) {
			llActivities.setVisibility(View.GONE);
		}else {
			for (int j=0; j<itemBarginEntities.size(); j++) {
				LinearLayout ll;
				TextView tv;
				ll = new LinearLayout(getBaseContext());
				ll = (LinearLayout) mInflater.inflate(R.layout.item_detail_activities_item, null);
				tv = (TextView) ll.findViewById(R.id.tv_item_detail_activites);
				tv.setText(itemBarginEntities.get(j).getBagainName());
				tv.setTextColor(getResources().getColor(R.color.activity_text_grey));
				llActivities.addView(ll);
			}
		}
		
		mViewList = new ArrayList<View>();
		if (itemImageEntiies == null || itemImageEntiies.size() == 0) {
			hsv.setVisibility(View.GONE);
		} else {
			if(isPropChanged) {
				return;
			}
			for (int m=0; m<itemImageEntiies.size(); m++) {
				LinearLayout ll = new LinearLayout(getBaseContext());
				ll = (LinearLayout) mInflater.inflate(R.layout.item_detail_icon_item, null);
				final ImageView ivIcon = (ImageView) ll.findViewById(R.id.iv_item_detail_icon);
				String imgUrl = itemImageEntiies.get(m).getImageName();
				if (imgUrl != null) {
					AsyncImageLoaderUtil imageLoader = new AsyncImageLoaderUtil();
					Drawable cachedImage = imageLoader.loadDrawable(imgUrl,
							ivIcon, new ILoadImageCallback() {
								@Override
								public void onObtainDrawable(Drawable drawable,
										ImageView imageView) {
									imageView.setImageDrawable(drawable);
								}

							});
					if (cachedImage == null) {
						ivIcon.setImageResource(R.drawable.pic_56);
						// Log.e("Adapter", "null");
					} else {
						ivIcon.setImageDrawable(cachedImage);												
					}
					ivIcon.setTag(m);
					urlList.add(imgUrl);
				}
				ivIcon.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(ItemDetailActivity.this, ItemPicViewFlipperActivity.class);
						intent.putStringArrayListExtra("urlList", urlList);
						intent.putExtra("index", ivIcon.getTag()+"");
						startActivity(intent);
					}
				});
//				llOut.addView(ll);
				mViewList.add(ll);
			}
			
			hsv.addView(llOut);
		}
		MyViewPagerAdapter mVPA = new MyViewPagerAdapter(mViewList);
		mViewPager.setAdapter(mVPA);
		updateSelectView();

	}
	
	private void updateSelectView() {
		LinearLayout llOutColor = null;
		String defaulName = null;
		final int defaulIndex[] = {-1};
		int stockNum = 0;
		defaulIndex[0] = -1;
		llColor.removeAllViews();
		llSize.removeAllViews();
		if (ItemsPropList1.size() <= 0) {
			tvStockNum.setText("(库存" + stockNum + ")");
			tvCount.setText("0");
			llAddToCart.setClickable(false);
			ivRaise.setClickable(false);
			return;
		} else {
			tvCount.setText("1");
		}
		for(int n=0; n<ItemsPropList1.size(); n++) {
			final PropEntity entity1 = ItemsPropList1.get(n);	
			btnList2 = new ArrayList<Button>();
			btnMap.put(n+"", btnList2);
			stockNum = entity1.getStockNum();
			tvStockNum.setText("(库存" + stockNum + ")");
			if (stockNum <= 0) {
				tvCount.setText("0");
				llAddToCart.setClickable(false);
				ivRaise.setClickable(false);
			} else {
				ivRaise.setClickable(true);
				tvCount.setText("1");
				
			}
			
			LinearLayout ll = new LinearLayout(getBaseContext());
			ll = (LinearLayout) mInflater.inflate(R.layout.item_detail_button_item, null);
			ll.setLayoutParams(new LayoutParams(llColor.getWidth()/2, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
			Button btnProp = (Button) ll.findViewById(R.id.btn_item_detail_color);
			btnProp.setLayoutParams(new LayoutParams(llColor.getWidth()/10*9/2, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
			final TextView tv = (TextView) ll.findViewById(R.id.tv_item_detail_color);
			btnProp.setText("  "+entity1.getName()+ "  ");		
			btnProp.setTag(n+"");
			btnList.add(btnProp);
			if(llOutColor == null){
				llOutColor = new LinearLayout(this.getBaseContext());  
				llOutColor.setOrientation(LinearLayout.HORIZONTAL);  
			}
	        llOutColor.addView(ll);
	        if((n != 0 && n%2 !=0) || ItemsPropList1.size()-n==1){
	        	llColor.addView(llOutColor);
	        	llOutColor = null;
	        }
			btnProp.setBackgroundResource(R.drawable.btn_select_normal);
			btnProp.setTextColor(getResources().getColor(R.color.prop_btn_nor));
			if ((n == 0 && !userOper) || (specIndexFirst == n && userOper)) {
				defaulName = entity1.getName();
				btnProp.setBackgroundResource(R.drawable.btn_select_press);
				btnProp.setTextColor(getResources().getColor(R.color.prop_btn_press));
				defaulIndex[0] = n;
				ItemsPropList2 = ItemsProp.get(defaulName);
				selectId = entity1.getItemId();
				tvColor.setText(tvColor.getText().toString().split(":").length > 1?tvColor.getText().toString().split(":")[0]+":"+entity1.getName():tvColor.getText().toString()+":"+entity1.getName());
				spec1IsRefresh=true;
				userOper = true;
			}
			
			btnProp.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					PropEntity entity;
					for (int n=0; n<ItemsPropList1.size(); n++) {
						ItemsPropList1.get(n).setIsDefault(0);
					}
					specIndexFirst = Integer.parseInt(v.getTag().toString());
					entity1.setIsDefault(1);
					
					selectId = entity1.getItemId();
					userOper = true;
					if(!isFirstIn){
						userOper2 = false;
					}
					isFirstIn = false;
					updateSelectView();
					if(!isProp2Existed) {
						getData(selectId);
					}else{
						getData(ItemsPropList2.get(Integer.parseInt(btnMap.get(specIndexFirst+"").get(specIndexSec).getTag().toString())).getItemId());
					}
					
//					llColor.getChildAt(defaulIndex[0]).setBackground(getResources().getDrawable(R.drawable.btn_bgwhite_nor));
//					llColor.invalidate();
				}
			});
			llColor.invalidate();
		}

		llColor.getTag();
		
		if (ItemsPropList2 == null || ItemsPropList2.size() == 0) {
			tvSize.setVisibility(View.GONE);
			isProp2Existed = false;
			
		} else {
			for (int l=0; l<ItemsPropList2.size(); l++) {
				final PropEntity entity2 = ItemsPropList2.get(l);
				isProp2Existed = true;
				LinearLayout ll2 = new LinearLayout(getBaseContext());
				ll2 = (LinearLayout) mInflater.inflate(R.layout.item_detail_button_item, null);
				Button btnProp2 = (Button) ll2.findViewById(R.id.btn_item_detail_color);
				stockNum = entity2.getStockNum();
				tvStockNum.setText("(库存" + stockNum + ")");
				if (stockNum <= 0) {
					tvCount.setText("0");
					llAddToCart.setClickable(false);
					ivRaise.setClickable(false);
				} else {
					llAddToCart.setClickable(true);
					ivRaise.setClickable(true);
					tvCount.setText("1");
				}
//				TextView tv = (TextView) ll2.findViewById(R.id.tv_item_detail_color);
				btnProp2.setText("  "+ entity2.getName()+ "  ");
				btnProp2.setBackgroundResource(R.drawable.btn_select_normal);
				btnProp2.setTextColor(getResources().getColor(R.color.prop_btn_nor));
				btnProp2.setTag(l+"");
				btnMap.get(specIndexFirst+"").add(btnProp2);
				llSize.addView(ll2);
				if(specIndexSec > ItemsPropList2.size()-1){
					specIndexSec = 0;
				}
				if ((l == 0 && !userOper2) || (specIndexSec == l && userOper2)) {
					btnProp2.setBackgroundResource(R.drawable.btn_select_press);
					btnProp2.setTextColor(getResources().getColor(R.color.prop_btn_press));
					selectId = entity2.getItemId();
					userOper2=true;
					tvSize.setText(tvSize.getText().toString().split(":").length > 1?tvSize.getText().toString().split(":")[0]+":"+entity2.getName():tvSize.getText().toString()+":"+entity2.getName());
					if(!userOper2){
						getData(selectId);
					}
				}
				
				btnProp2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						for (int n=0; n<ItemsPropList2.size(); n++) {
							ItemsPropList2.get(n).setIsDefault(0);
						}
						specIndexSec = Integer.parseInt(v.getTag().toString());
						entity2.setIsDefault(1);
						updateSelectView();
						selectId = entity2.getItemId();
						userOper2 = true;
						getData(selectId);
					}
				});
			}
			llSize.invalidate();
		}	
		if(isFirstIn){
			llExpand.setVisibility(View.GONE);
		}
	}
	
	private void updatePropChangedView() {
		
	}
	
	private void AddToFavour() {
		if(DataUtil.isUserDataExisted(getBaseContext()))
		{	
			JSONObject obj = new JSONObject();
			try {
				obj.put("mall", Constants.mallId);
				obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
				
				//JSONArray itemArray = new JSONArray();
				//JSONObject item = new JSONObject();
				//item.put("buyNum", tvCount.getText()+"");
				obj.put("id", itemId);
				obj.put("shopId", shopId);
				obj.put("itemName", itemName);
				obj.put("fromType", 1);
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
			String fail = (String)msg.obj;
			
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				showCustomToast("加入购物车成功！");
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
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
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		int count;
		switch (v.getId()) {
		case R.id.rl_item_detail:
			BasicNameValuePair  nameValuePair3 = new BasicNameValuePair("detailUrl", itemDetailEntities.get(0).getDetailUrl());
			IntentUtil.start_activity(ItemDetailActivity.this, ItemDetailWebActivity.class,nameValuePair3);
			break;
		case R.id.rl_item_detail_evaluate:
			BasicNameValuePair  nameValuePair = new BasicNameValuePair("itemId", itemId+"");
			BasicNameValuePair  nameValuePair1 = new BasicNameValuePair("type", itemType+"");
			BasicNameValuePair  nameValuePair2 = new BasicNameValuePair("page", 1+"");
			IntentUtil.start_activity(ItemDetailActivity.this, EvaluateDetailActivity.class,nameValuePair,nameValuePair1,nameValuePair2);
			break;
		case R.id.rl_prop_open:
			if (isExpand) {
				llExpand.setVisibility(View.GONE);
				isExpand = false;
			} else {
				llExpand.setVisibility(View.VISIBLE);
				isExpand = true;
			}
			break;
			
		case R.id.iv_arrow:
			if (isExpand) {
				llExpand.setVisibility(View.GONE);
				isExpand = false;
			} else {
				llExpand.setVisibility(View.VISIBLE);
				isExpand = true;
			}
			break;
		case R.id.iv_item_detail_reduce:
			count = Integer.parseInt(tvCount.getText()+"");
			if (count > 1) {
				count -= 1;
			}
			tvCount.setText(count+"");
			break;
		case R.id.iv_item_detail_raise:
			count = Integer.parseInt(tvCount.getText()+"");
			count += 1;
			tvCount.setText(count+"");
			break;
		case R.id.ivBack:
			finish();
			break;
		case R.id.btn_buy_now:
//			showCustomToast("支付宝申请中，敬请期待");
			break;
		case R.id.ll_tab_item_favour:
			if(itemType == 1) {
				ToastUtil.showExceptionTips(getBaseContext(), "很抱歉，团购商品不能加入收藏");
			} else {
				AddToFavour();
			}
			
			
			break;
		case R.id.btn_groupon_detail_buy:
		case R.id.ll_tab_item_cart:
			if (!isExpand){
				llExpand.setVisibility(View.VISIBLE);
				isExpand = true;
			} else {
				if(DataUtil.isUserDataExisted(getBaseContext()))
				{	
					JSONObject obj = new JSONObject();
					try {
						obj.put("mall", Constants.mallId);
						obj.put("TGC",DataCache.UserDataCache.get(0).getTGC());
						
		//				JSONArray itemArray = new JSONArray();
		//				JSONObject item = new JSONObject();
		//				item.put("buyNum", tvCount.getText()+"");
		//				item.put("id", itemId);
		//				item.put("type", itemType+"");
		//				
		//				itemArray.put(item);
						obj.put("buyNum", tvCount.getText()+"");
						obj.put("id", selectId);
						obj.put("type", itemType+"");
						HttpUtil.requestAddOrDeleteItemTocart(getBaseContext(), obj.toString(), mCartHandler, 0);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}else{
						showCustomToast("请先登录您的账号方可进行操作.");
						 IntentUtil.start_activity(this, LoginActivity.class);
				}
			}
			break;
		case R.id.ll_enter_shop:
			Intent intent = new Intent(ItemDetailActivity.this, ShopDetailActivityNew.class);
			intent.putExtra("shopId", shopId);
			intent.putExtra("orderType", "0");
			startActivity(intent);
			break;
		case R.id.iv_message_item_detail:
		case R.id.tv_messag_item_detail:
		case R.id.iv_chat:
		case R.id.ll_chat:
			Intent intent2 = new Intent(ItemDetailActivity.this, ChatActivity.class);
			intent2.putExtra("shopId", shopId+"");
			intent2.putExtra("shopName", shopName+"");
			startActivity(intent2);
			break;
		case R.id.ivGoToCart:
//			IntentUtil.start_activity(ItemDetailActivity.this, ShoppingCartFragment.class);
			Intent intent3 = new Intent(ItemDetailActivity.this, HomeActivityNew.class);
			intent3.putExtra("mTabId", 3);
			startActivity(intent3);
			
			break;
		case R.id.rl_item_detail_groupon:
			if(grouponId != 0) {
				Intent intent4 = new Intent(ItemDetailActivity.this, GrouponDetailActivity.class);
				intent4.putExtra("groupId", grouponId);
				startActivity(intent4);
			}
			
			break;
		default:
			break;
		}
	}
	
	class MyViewPagerAdapter extends PagerAdapter{

	        private List<View> mListViews;  
	          
	        public MyViewPagerAdapter(List<View> mListViews) {  
	            this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。  
	        }  
	  
	        @Override  
	        public void destroyItem(ViewGroup container, int position, Object object)   {     
	            container.removeView(mListViews.get(position));//删除页卡  
	        }  
	  
	  
	        @Override  
	        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡         
	             container.addView(mListViews.get(position), 0);//添加页卡  
	             return mListViews.get(position);  
	        }  
	  
	        @Override  
	        public int getCount() {           
	            return  mListViews.size();//返回页卡的数量  
	        }  
	          
	        @Override  
	        public boolean isViewFromObject(View arg0, Object arg1) {             
	            return arg0==arg1;//官方提示这样写  
	        }  
	}
}
