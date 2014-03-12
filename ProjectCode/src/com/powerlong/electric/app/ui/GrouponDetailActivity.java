/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * GrouponDetailActivity.java
 * 
 * 2013年9月11日-上午11:34:00
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.GoodsDetailEntity;
import com.powerlong.electric.app.entity.GrouponDetailShopEntity;
import com.powerlong.electric.app.entity.GrouponDetailsEntity;
import com.powerlong.electric.app.entity.ImageListEntity;
import com.powerlong.electric.app.entity.ItemGroupItemListEntity;
import com.powerlong.electric.app.entity.ItemGroupListEntity;
import com.powerlong.electric.app.entity.NavigationGrouponDetailsEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.listener.PlWebViewLoadingListener;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.DoubleUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.ToastUtil;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil.ILoadImageCallback;
import com.powerlong.electric.app.widget.PlScrollView;
import com.powerlong.electric.app.widget.PlWebView;
import com.powerlong.electric.app.widget.PlWebViewNew;

/**
 * 
 * GrouponDetailActivity
 * 
 * @author: YangCheng Miao
 * 2013年9月11日-上午11:34:00
 * 
 * @version 1.0.0
 * 
 */
public class GrouponDetailActivity extends BaseActivity implements OnClickListener {
	private long groupId;
	private PlScrollView mScrollView;
	private ImageView ivTitle;
	private TextView tvPriceNow;
	private TextView tvPriceBefore;
	private TextView tvDiscount;
	private TextView tvTitle;
	private TextView tvNotice;
	//private PlWebViewNew webView;
	private TextView tvGrouponNum;
	private ImageView ivBack;
	private ImageView ivGoCart;
	private LinearLayout llLeft;
	private LinearLayout llMid;
	private LinearLayout llRight;
	private LinearLayout llOut;
	private TextView tvShopName;
	private RatingBar rbShop;
	private TextView tvShopCategory;	
	private TextView tvLaveTime;
	private TextView tvBuyNotice;
	private RelativeLayout rlMore,rlShowNew;
	private Button btnBuy;
	private String methodParams ="";
	private long startTime;
	private long endTime;
	private boolean isStart;
	private String groupItemId;
	private long itemId;
	private RelativeLayout rlOut;
	private boolean isSelected = false;
	private TextView tvEvaluate;
	long seconds;
	private String content="";
	ArrayList<ItemGroupItemListEntity> itemGroupListEntities;
	LayoutInflater mInflater;
	private String defaulProp;
	private int grouponState = 3;
	private String leftTime = ""; 
	private ImageView iv_shop_logo;
	private TextView tv_shop_name;
	private TextView tv_shop_prop;
	private TextView tv_shop_evaluation;
	private TextView tv_connection;
	private long shopId;
	private String shopName;
	private RelativeLayout rlShop;
	private ImageView ivGoToCart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupon_detail);
		
		Intent intent = getIntent();
		groupId = intent.getLongExtra("groupId", groupId);
		methodParams = intent.getStringExtra("methodParams");
		if(!StringUtil.isEmpty(methodParams)){
			try {
				JSONObject obj = new JSONObject(methodParams);
				groupId = obj.getInt("grouponId");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		LogUtil.d("GrouponDetailActivity", "groupId = "+groupId);
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
		mScrollView = (PlScrollView)findViewById(R.id.groupon_scrollview);
		ivTitle = (ImageView) findViewById(R.id.iv_groupon_detail_pic);
		tvPriceNow = (TextView) findViewById(R.id.tv_groupon_detail_price_now);
		tvPriceBefore = (TextView) findViewById(R.id.tv_groupon_detail_price_before);
		tvDiscount = (TextView) findViewById(R.id.tv_groupon_detail_discount);
		tvTitle = (TextView) findViewById(R.id.tv_groupon_detail_title);
//		tvNotice = (TextView) findViewById(R.id.tv_groupon_detail_content);
		//webView = (PlWebViewNew) findViewById(R.id.tv_groupon_detail_content);
		ivBack = (ImageView) findViewById(R.id.ivReturn);
		ivBack.setOnClickListener(this);
		ivGoCart = (ImageView) findViewById(R.id.ivGoCart);
		ivGoCart.setOnClickListener(this);
		tvGrouponNum = (TextView) findViewById(R.id.iv_groupon_detail_count);
		llLeft = (LinearLayout) findViewById(R.id.ll_groupon_detail_left);
		llMid = (LinearLayout) findViewById(R.id.ll_groupon_detail_mid);
		llRight= (LinearLayout) findViewById(R.id.ll_groupon_detail_right);
		rlOut = (RelativeLayout) findViewById(R.id.rl_groupon_detail_outer);
		tvLaveTime = (TextView) findViewById(R.id.tv_groupon_detail_lave_time);
		tvBuyNotice = (TextView) findViewById(R.id.tv_groupon_detail_deadline);
		rlMore = (RelativeLayout) findViewById(R.id.rl_groupon_detail_more);
		rlShowNew = (RelativeLayout)findViewById(R.id.rl_groupon_detail_show_new);
		rlMore.setOnClickListener(this);
		rlShowNew.setOnClickListener(this);
		btnBuy = (Button) findViewById(R.id.btn_groupon_detail_buy);
		btnBuy.setOnClickListener(this);
		llOut = (LinearLayout) findViewById(R.id.ll_groupon_out);
		iv_shop_logo = (ImageView) findViewById(R.id.img_shop);
		iv_shop_logo.setOnClickListener(this);
		tv_shop_name = (TextView) findViewById(R.id.tv_shop_name);
		tv_shop_prop = (TextView) findViewById(R.id.tv_shop_type);
		tv_connection = (TextView) findViewById(R.id.tv_contact);
		tv_connection.setOnClickListener(this);
		ivGoToCart = (ImageView) findViewById(R.id.ivGoToShopCart);
		ivGoToCart.setOnClickListener(this);
		rlShop = (RelativeLayout)findViewById(R.id.rl_shop);
		rlShop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				rlOut.setVisibility(rlOut.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
			}
		});
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
		DataUtil.getGrouponDetailsData(this, groupId, mServerConnectionHandler);
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("GrouponDetailActivity", "msg.what = " + msg.what);
			LogUtil.d("GrouponDetailActivity", "msg.arg1 = " + msg.arg1);
			
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				updateView();
				dismissLoadingDialog();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
				showCustomToast(fail);
				if(!Constants.isShowDefaultUi)
					finish();
				break;
			}
		}

	};
	
	private void updateView() {
		ArrayList<ImageListEntity> imageEntities = DataCache.GrouponShopImgCache;
		ArrayList<GrouponDetailsEntity> grouponEntities = DataCache.GrouponDetailCache;
		ArrayList<GrouponDetailShopEntity> shopEntities = DataCache.GrouponShopCache;
		ArrayList<ItemGroupListEntity> groupListEntities = DataCache.GrouponItemListCache;
		itemGroupListEntities = DataCache.itemGrouponItemListCache;
		mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		GrouponDetailsEntity entity = grouponEntities.get(0);
		if(entity.getIsReturnItem().equals("1")) {
			llLeft.setVisibility(View.VISIBLE);
		} 
		if(entity.getIsReturnMoney().equals("1")) {
			llMid.setVisibility(View.VISIBLE);
		} 
		if(entity.getIsPaidfor().equals("1")) {
			llRight.setVisibility(View.VISIBLE);
		} 
		
		tv_shop_name.setText(entity.getShopName());
		tv_shop_prop.setText(entity.getShopProp());
		String shopImageUrl = entity.getShopImage();
		shopId = entity.getShopId();
		shopName = entity.getShopName();
		AsyncImageLoaderUtil imageShopLoader = new AsyncImageLoaderUtil();
		
		Drawable cachedShopImage = imageShopLoader.loadDrawable(shopImageUrl, iv_shop_logo, new ILoadImageCallback() {
			@Override
			public void onObtainDrawable(Drawable drawable,ImageView imageView) {
				imageView.setImageDrawable(drawable);
			}
			
		});
		if (cachedShopImage == null) {
			ivTitle.setImageResource(R.drawable.pic_130);
		} else {
			ivTitle.setImageDrawable(cachedShopImage);
		}
		if (imageEntities.size() > 0) {
			String imageUrl = imageEntities.get(0).getImageName();
			AsyncImageLoaderUtil imageLoader = new AsyncImageLoaderUtil();
			
			Drawable cachedImage = imageLoader.loadDrawable(imageUrl,ivTitle, new ILoadImageCallback() {
				@Override
				public void onObtainDrawable(Drawable drawable,ImageView imageView) {
					imageView.setImageDrawable(drawable);
				}
				
			});
			if (cachedImage == null) {
			ivTitle.setImageResource(R.drawable.pic_130);
			} else {
				ivTitle.setImageDrawable(cachedImage);
			}
		}		

		tvPriceNow.setText("￥"+entity.getPlPrice());
		tvPriceBefore.setText("￥"+entity.getListPrice());
		tvPriceBefore.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		tvDiscount.setText(DoubleUtil.subPrice(DoubleUtil.round((entity.getPlPrice()/(entity.getListPrice() == 0.0?1:entity.getListPrice())),2) * 10) +"折  ");
		tvTitle.setText(entity.getName());
//		tvNotice.setText(Html.fromHtml(entity.getContent()));
		content = entity.getContent();

		tvGrouponNum.setText(entity.getBuyNum()+"人已团购");
		endTime =entity.getCountDownTime();
		startTime = entity.getStartDate();
		groupItemId = entity.getItemGroupList();
		Log.v("getCountDownTime", entity.getCountDownTime()+"");
		handler.postDelayed(runnable, 1000);  

		Date now = new Date();
		// 获取当前时间点对应的毫秒数
		long currentTime = now.getTime();
		int stat = entity.getStat();
		// 计算两个时间点相差的秒数
		//seconds = (endTime - currentTime) / 1000;
		if(stat == 0) {
			isStart = false;
			btnBuy.setText("即将开始");
			btnBuy.setClickable(false);
			seconds = (startTime - currentTime)/1000;
			//tvLaveTime.setText("开始时间：" + StringUtil.formatDuring((startTime-currentTime)*1000));
			grouponState = Constants.GROUPON_STATE_WAIT;
		} else if(stat==1){
			isStart = true;
			seconds = (endTime - currentTime)/1000;
			btnBuy.setText("立即抢购");
			btnBuy.setClickable(true);
			grouponState = Constants.GROUPON_STATE_START;
		}else{
			btnBuy.setText("已结束");
			btnBuy.setClickable(false);
			grouponState = Constants.GROUPON_STATE_END;
		}
//		if((startTime - currentTime) > 0) {
//			isStart = false;
//			btnBuy.setText("即将开始");
//			btnBuy.setClickable(false);
//			seconds = (startTime - currentTime)/1000;
//			//tvLaveTime.setText("开始时间：" + StringUtil.formatDuring((startTime-currentTime)*1000));
//			grouponState = Constants.GROUPON_STATE_WAIT;
//		} else if(endTime - currentTime > 0 && startTime - currentTime < 0){
//			isStart = true;
//			seconds = (endTime - currentTime)/1000;
//			btnBuy.setText("立即抢购");
//			btnBuy.setClickable(true);
//			grouponState = Constants.GROUPON_STATE_START;
//		}else{
//			btnBuy.setText("已结束");
//			btnBuy.setClickable(false);
//			grouponState = Constants.GROUPON_STATE_END;
//		}
		Log.v("currentTime", currentTime+"");
		Log.v("endTime", endTime+"");
		tvBuyNotice.setText(entity.getBuyNotify());
		
		
		
		for (int i=0; i<shopEntities.size(); i++) {
			GrouponDetailShopEntity shopEntity = shopEntities.get(i);
			RelativeLayout rl = new RelativeLayout(getBaseContext());
			rl = (RelativeLayout) mInflater.inflate(R.layout.groupon_detail_shop_item, null);
			tvShopName = (TextView) rl.findViewById(R.id.tv_groupon_detail_item);
			tvShopName.setText(shopEntity.getName());
//			rbShop = (RatingBar) rl.findViewById(R.id.rb_groupon_detail);
			float f = Float.parseFloat(shopEntity.getEvaluation()+"");
//			rbShop.setRating(f);
			tvEvaluate = (TextView) rl.findViewById(R.id.tv_groupon_evaluate);
			tvEvaluate.setText(f+"");
			tvShopCategory = (TextView) rl.findViewById(R.id.tv_groupon_detail_category);
			tvShopCategory.setText(shopEntity.getProp());
			
			rlOut.addView(rl);
		}
		
		for(int j=0; j<groupListEntities.size(); j++) {
			ItemGroupListEntity entityGroup = groupListEntities.get(j);
			groupItemId = entityGroup.getItemGroupId() + ",";
		}
		
//		updatePropView();
		for(int k=0; k<itemGroupListEntities.size(); k++) {
			
			final ItemGroupItemListEntity entityItem = itemGroupListEntities.get(k);
			final Button btnProp;
			LinearLayout ll;
			
			if(k == 0) {
				ll = new LinearLayout(getBaseContext());
				ll = (LinearLayout) mInflater.inflate(R.layout.item_detail_button_item, null);
				btnProp = (Button) ll.findViewById(R.id.btn_item_detail_color);
				btnProp.setText("  "+ entityItem.getItemName() + "  ");		
				btnProp.setBackgroundResource(R.drawable.btn_select_press);
				btnProp.setTag(0);
				itemId = entityItem.getId();
			} else {
				ll = new LinearLayout(getBaseContext());
				ll = (LinearLayout) mInflater.inflate(R.layout.item_detail_button_item, null);
				btnProp = (Button) ll.findViewById(R.id.btn_item_detail_color);
				btnProp.setText("  "+ entityItem.getItemName() + "  ");		
				btnProp.setBackgroundResource(R.drawable.btn_select_normal);
				btnProp.setTag(k);
			}
			btnProp.setLayoutParams(new LinearLayout.LayoutParams(llOut.getWidth()/50*49, 75));
			
			
			btnProp.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					btnProp.setTag("0");
					itemId = entityItem.getId();
					isSelected = true;
//					updatePropView();
					btnProp.setBackgroundResource(R.drawable.btn_select_press);
					
				}
			});
			llOut.addView(ll);
		}
			
		
	}
	
	private void updatePropView() {
		llOut.removeAllViews();
		for(int k=0; k<itemGroupListEntities.size(); k++) {
			final ItemGroupItemListEntity entityItem = itemGroupListEntities.get(k);
			
			LinearLayout ll = new LinearLayout(getBaseContext());
			ll = (LinearLayout) mInflater.inflate(R.layout.item_detail_button_item, null);
			final Button btnProp = (Button) ll.findViewById(R.id.btn_item_detail_color);
			btnProp.setText("  "+ entityItem.getItemName() + "  ");		
			btnProp.setBackgroundResource(R.drawable.btn_select_normal);
			btnProp.setTag(k);
			
			
			btnProp.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					btnProp.setTag("0");
					itemId = entityItem.getId();
					isSelected = true;
					updatePropView();
					btnProp.setBackgroundResource(R.drawable.btn_select_press);
					
				}
			});
			llOut.addView(ll);
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

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivReturn:
			finish();
			break;
		case R.id.rl_groupon_detail_more:
//			showCustomToast( "More Details");
			Intent intent = new Intent(GrouponDetailActivity.this,ActivityGroupMoreDetail.class);
			intent.putExtra("grouponId", groupId+"");
			startActivity(intent);
			break;
		case R.id.rl_groupon_detail_show_new:
			Intent mIntent = new Intent(GrouponDetailActivity.this,GrouponDetailShowActivity.class);
			mIntent.putExtra("content", content);
			mIntent.putExtra("groupId", groupId);
			startActivity(mIntent);
			break;
		case R.id.btn_groupon_detail_buy:
/*			if(!isSelected) {
				ToastUtil.showExceptionTips(getBaseContext(), "请选择规格!");
				return;
			}*/
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
	//				itemArray.put(item);
					
					obj.put("buyNum", "1");
					obj.put("id", groupId);
					obj.put("type", "1");
					obj.put("grouponItemId", itemId);
					HttpUtil.requestAddOrDeleteItemTocart(getBaseContext(), obj.toString(), mCartHandler, 0);
				}catch (Exception e) {
					e.printStackTrace();
				
				}
			}else{
					showCustomToast("请先登录您的账号方可进行操作.");
					IntentUtil.start_activity(this, LoginActivity.class);
		}
			
			break;
		case R.id.ivGoCart:
//			Intent intent3 = new Intent(GrouponDetailActivity.this, HomeActivityNew.class);
//			intent3.putExtra("mTabId", 3);
//			startActivity(intent3);
			Intent intent3 = new Intent(GrouponDetailActivity.this, ChatActivity.class);
			intent3.putExtra("shopId", shopId+"");
			intent3.putExtra("shopName", shopName+"");
			startActivity(intent3);
			break;
		case R.id.tv_contact:
			Intent intent4 = new Intent(GrouponDetailActivity.this, ChatActivity.class);
			intent4.putExtra("shopName", shopName+"");
			startActivity(intent4);
			break;
		case R.id.img_shop:
			Intent intent5 = new Intent(GrouponDetailActivity.this, ShopDetailActivityNew.class);
			intent5.putExtra("shopId", shopId);
			startActivity(intent5);
			break;
		case R.id.ivGoToShopCart:
			Intent intent6 = new Intent(GrouponDetailActivity.this, HomeActivityNew.class);
			intent6.putExtra("mTabId", 3);
			startActivity(intent6);
		default:
			break;
		}
		
	}

	Handler handler = new Handler();
	Runnable runnable = new Runnable() {  
        @Override  
        public void run() {  
        	
           seconds--;
           switch(grouponState){
           case Constants.GROUPON_STATE_END:
        	   tvLaveTime.setText("团购已过期");
        	   break;
           case Constants.GROUPON_STATE_START:
        	   leftTime = StringUtil.formatDuring(seconds*1000);
        	   tvLaveTime.setText("距离结束："+ leftTime);
        	   handler.postDelayed(this, 1000);
        	   break;
           case Constants.GROUPON_STATE_WAIT:
        	   leftTime = StringUtil.formatDuring(seconds*1000);
        	   tvLaveTime.setText("距离开始："+ leftTime);
        	   handler.postDelayed(this, 1000);
        	   break;
           }
           
           
           
           
//           if (seconds < 0) {
//        	   tvLaveTime.setText("团购已过期");
//           } else {
//	          StringUtil.formatDuring(seconds*1000);
//	           tvLaveTime.setText("倒计时："+ time);
//	            handler.postDelayed(this, 1000);  
//           }
        }  
    };  
    

}
