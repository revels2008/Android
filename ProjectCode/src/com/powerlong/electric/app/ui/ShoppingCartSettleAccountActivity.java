/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ShoppingCartSettleAccountActivity.java
 * 
 * 2013年8月29日-下午7:40:10
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.android.app.IAlixPay;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.ItemBargainAdapter;
import com.powerlong.electric.app.adapter.ShoppingCartSettleAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.config.CustomerHttpClient;
import com.powerlong.electric.app.dao.NavigationActivityDao;
import com.powerlong.electric.app.dao.NavigationBaseDao;
import com.powerlong.electric.app.domain.DomainChannelMsg;
import com.powerlong.electric.app.domain.DomainOrderMsg;
import com.powerlong.electric.app.entity.AddOrderEntity;
import com.powerlong.electric.app.entity.BarginListEntity;
import com.powerlong.electric.app.entity.CartCountEntity;
import com.powerlong.electric.app.entity.CartShopListEntity;
import com.powerlong.electric.app.entity.DateTimeEntity;
import com.powerlong.electric.app.entity.ItemBargainListEntity;
import com.powerlong.electric.app.entity.ItemListEntity;
import com.powerlong.electric.app.entity.NavigationBaseEntity;
import com.powerlong.electric.app.entity.ShoppingCartSettleEntity;
import com.powerlong.electric.app.entity.UserAddressEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.CommonUtils;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.DoubleUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.JSONParser;
import com.powerlong.electric.app.utils.JSONUtil;
import com.powerlong.electric.app.utils.ListViewUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.MD5Utils;
import com.powerlong.electric.app.utils.NotificatinUtils;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.ToastUtil;
import com.powerlong.electric.app.widget.PlTableView;
import com.powerlong.electric.app.widget.PopWindowPayList;

/**
 * 
 * ShoppingCartSettleAccountActivity
 * 
 * @author: YangCheng Miao 2013年8月29日-下午7:40:10
 * 
 * @version 1.0.0
 * 
 */
public class ShoppingCartSettleAccountActivity extends BaseActivity implements
		OnClickListener {
	private static final String TAG = "ShoppingAddOrder";
	private TextView shopName;
	private ListView listView;
	private ListView lvBargain;
	private ScrollView svBargain;
	private TextView tvBargainName;
	private TextView tvBargainContent;
	private EditText etMessage;
	private ArrayAdapter<ShoppingCartSettleEntity> adapter;
	private ScrollView sv;
	private RelativeLayout rlCoupon;
	private RelativeLayout rlSend;
	private RelativeLayout rlPay;
	private RelativeLayout rlDate;
	private RelativeLayout rlShopCoupon;
	private LinearLayout ll;
	private TextView tvCoupon;
	private TextView tvSend;
	private TextView tvPay;
	private TextView tvTime;
	private HashMap<Integer, RelativeLayout> hashMapCashCoupon = new HashMap<Integer, RelativeLayout>();
	private TextView tvShopCoupon;
	public int shopIndex;
	private TextView usrName;
	private TextView usrPhoneNum;
	private TextView usrAddress;
	private ImageView ivReturn;
	private Map<Integer, Integer> positionShop = new HashMap<Integer, Integer>();
	private int positionAddress;
	private int positionCoupon = -1;
	private int positionSend = 0;
	private int positionTime = -1;
	private static Button btnPay;
	private String receiveTime = "";
	private String receiveDate = "";
	private TextView shopPrice;
	private TextView shopCount;
	private TextView tvTotalPrice;
	private boolean isPay = false;
	private double totalPrice;
	private double shopCouponPrice;
	
	private Context context;
	private ProgressDialog mProgress = null;
	boolean mbPaying = false;
	private Activity mActivity = null;
	private Integer lock = 0;
	private IAlixPay mAlixPay = null;
	private PopWindowPayList popWindowPayList;
	private View root;
	private double sendPrice;
	DomainOrderMsg orderMsg;
	String paymentNo;
	private String payId = "0";
	private int useAddresId;
	private String selectLogisticsId = "0";
	private String payPrice;
	private String totalName="";
	private int view;
	private int selectCommunity;
	private boolean isTooLong = false;
	private int type = 0;
	private Calendar c = null;
	private static boolean isAllGroupon = true;
	String dateDefault;
	String timeDefault;
	private Map<String, String> shopMessage = new HashMap<String, String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_cart_settle);
		
		findView();
		getData();
		

	}

	/**
	*/
	private void initService() {
		context = this;
		popWindowPayList = new PopWindowPayList(ShoppingCartSettleAccountActivity.this,totalName);
	}

	private void findView() {
		root = findViewById(R.id.root);
		sv = (ScrollView) findViewById(R.id.sv_settle_account);
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(this);
		btnPay = (Button) findViewById(R.id.ivPay);
		btnPay.setOnClickListener(this);
		tvTotalPrice = (TextView) findViewById(R.id.txTitle);
	}

	// {"mall":1,"TGC":"9B883D4AFB314458BB9E6447BF39F71D","shopList":{"id":"77","itemList":{"id":"205","type":"0","buyNum":"1"}}}
	private String getParam() {
		List<CartShopListEntity> shopEntities = DataCache.CartShopListCache;
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("mall", Constants.mallId);
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
			JSONArray shopArray = new JSONArray();
			for (int i = 0; i < shopEntities.size(); i++) {
				CartShopListEntity entity = shopEntities.get(i);
				JSONObject shopObject = new JSONObject();
				int shopId = entity.getShopId();
				boolean isAddShop = false;
//				shopObject.put("id", shopId);
				JSONArray itemArray = new JSONArray();
				ArrayList<ItemListEntity> itemEntities = DataCache.CartItemListCache
						.get(shopId);
				for (int j = 0; j < itemEntities.size(); j++) {
					ItemListEntity iEntity = itemEntities.get(j);
					if (iEntity.isChecked() == true) {						
						JSONObject itemObject = new JSONObject();
						itemObject.put("id", iEntity.getItemId());
						itemObject.put("type", iEntity.getIsGroupon());
						itemObject.put("buyNum", iEntity.getBuyNum());
						itemArray.put(itemObject);
						isAddShop = true;
					}
				}
				if (!isAddShop) {
					continue;
				}
				shopObject.put("id", shopId);
				shopObject.put("itemList", itemArray);
				shopArray.put(shopObject);
			}
			obj.put("shopList", shopArray);
//			Log.e("<<<<<<<<<", obj.toString());
			return obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			btnPay.setClickable(true);
			return null;
		}
	}

	private void getData() {
		// setProgressBarVisibility(true);
		DataUtil.getCartCountData(mServerConnectionHandler,getParam());
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("ShoppingCartSettleAccountActivity", "msg.what = "
					+ msg.what);
			LogUtil.d("ShoppingCartSettleAccountActivity", "msg.arg1 = "
					+ msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				updateView();
				initService();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
				Toast.makeText(ShoppingCartSettleAccountActivity.this, fail,
						Toast.LENGTH_LONG).show();
				break;
			case Constants.ORDER_SUCCESS:
//				xx
				break;
			}
			// mpProgressBar.setVisibility(View.GONE);
		}

	};

	private void updateView() {
		List<CartShopListEntity> shopEntities = DataCache.CartCountShopListCache;
		List<CartCountEntity> countEntities = DataCache.CartCountEntityCache;
		ArrayList<DateTimeEntity> dateListCache = DataCache.dateListCache;
		dateDefault = dateListCache.get(0).getDate();
		HashMap<String, ArrayList<DateTimeEntity>> timeListCache = DataCache.timeListCache;
		timeDefault = timeListCache.get(dateDefault).get(0).getTime();
		
		HashMap<Integer, ArrayList<BarginListEntity>> entities = DataCache.CartCountBagainListCache;
		List<BarginListEntity> bargainEntities;
		ItemListEntity entity;
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

		LayoutInflater mInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		RelativeLayout rl = new RelativeLayout(getBaseContext());
		rl = (RelativeLayout) mInflater.inflate(R.layout.default_address, null);
		usrName = (TextView) rl.findViewById(R.id.tv_username);
		usrPhoneNum = (TextView) rl.findViewById(R.id.tv_userphone);
		usrAddress = (TextView) rl.findViewById(R.id.tv_useraddr);
		
//		setDefaultAddress();

		LinearLayout llOut = new LinearLayout(sv.getContext());
		llOut.setOrientation(LinearLayout.VERTICAL);
		llOut.addView(rl);

		LinearLayout llBottom = new LinearLayout(getBaseContext());
		llBottom = (LinearLayout) mInflater.inflate(
				R.layout.shopping_cart_settle_bottom, null);

		tvCoupon = (TextView) llBottom.findViewById(R.id.tv_coupon_powerlong);
		tvSend = (TextView) llBottom.findViewById(R.id.tv_send);
		tvPay = (TextView) llBottom.findViewById(R.id.tv_pay);
		tvTime = (TextView) llBottom.findViewById(R.id.tv_time);
		receiveTime = timeDefault;
		try {
			if(!StringUtil.isNullOrEmpty(dateDefault)) {
				receiveDate = new String(dateDefault.getBytes(), "utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tvTime.setText(dateDefault+ "\n" + timeDefault +""); 
		CartCountEntity cartEntity = countEntities.get(0);
		totalPrice = cartEntity.getAllPrice();
		sendPrice = cartEntity.getFreight();
		view = cartEntity.getView();
		selectCommunity = cartEntity.getIsOtherCommunity();
		tvTotalPrice.setText("总计："+ DoubleUtil.subPrice(totalPrice) +"元,包含快递费" + sendPrice + "元");
		payPrice = DoubleUtil.subPrice(DoubleUtil.round((totalPrice),3)) +"";

		LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layout.setMargins(0, 20, 0, 20);

		rlCoupon = (RelativeLayout) llBottom.findViewById(R.id.rl_coupon);
		rlSend = (RelativeLayout) llBottom.findViewById(R.id.rl_send);
		rlPay = (RelativeLayout) llBottom.findViewById(R.id.rl_pay);
		rlDate = (RelativeLayout) llBottom.findViewById(R.id.rl_date); 
		
		if(view == 1) {
			rlSend.setVisibility(View.GONE);
			rlDate.setVisibility(View.GONE);
		}
		if(selectCommunity == 0) {
			tvSend.setText("宝龙物流");
			tvSend.setTextColor(getResources().getColor(R.color.grey));
			rlSend.setClickable(false);			
		} else {
			tvSend.setText("第三方物流");
			tvSend.setTextColor(getResources().getColor(R.color.grey));
			rlSend.setClickable(false);	
		}
		usrName.setText(cartEntity.getReceiver());
		usrPhoneNum.setText(cartEntity.getTel());
		usrAddress.setText(cartEntity.getAddress());
		useAddresId = cartEntity.getAddressId();

		for (int i = 0; i < shopEntities.size(); i++) {
			final int positionIndex = i;
			positionShop.put(i, -1);
			CartShopListEntity shopEntity = shopEntities.get(i);
//			CartCountEntity cartEntity = countEntities.get(i);
			ll = new LinearLayout(getBaseContext());
			ll = (LinearLayout) mInflater.inflate(
					R.layout.shopping_cart_settle_account_item, null);
			ll.setLayoutParams(layout);

			List<ShoppingCartSettleEntity> list = new ArrayList<ShoppingCartSettleEntity>();
			final List<ItemListEntity> itemEntities = DataCache.CarCountItemListCache.get(shopEntities.get(i)
					.getShopId());
			
			final int shopId = shopEntity.getShopId();
			// final int index = i;
			for (int j = 0; j < itemEntities.size(); j++) {
				entity = itemEntities.get(j);
				if(entity.getIsGroupon().equals("0")) {
					isAllGroupon = false;
				}
//				totalPrice += Float.parseFloat(entity.getplPrice());
				list.add(new ShoppingCartSettleEntity(entity.getImage(), entity
						.getItemName(), entity.getplPrice(), entity
						.getBuyNum() + ""));	
				if((totalName+entity.getItemName()).length() <= 50 ){
					totalName += entity.getItemName()+" ";
				}else{
					if(totalName.length() == 0){
						totalName = entity.getItemName().substring(0,49);
					}
					isTooLong = true;
				}
			}
			if(!totalName.contains("等等") && isTooLong && totalName.length() != 50){
					totalName += "等等";
					isTooLong = false;
			}
			shopName = (TextView) ll.findViewById(R.id.tv_item);
			shopName.setText(shopEntities.get(i).getShopName());
			shopPrice = (TextView) ll.findViewById(R.id.tv_shop_settle_price);
			  
			shopPrice.setText(DoubleUtil.subPrice(shopEntity.getTotalPrice()));
			shopCount = (TextView) ll.findViewById(R.id.tv_shop_count);
			shopCount.setText(shopEntity.getTotalNum()+"");
			listView = (ListView) ll.findViewById(R.id.lv_settle);
			listView.setDivider(getResources().getDrawable(
					R.drawable.line_dotted));
			adapter = new ShoppingCartSettleAdapter(this, list, listView, itemEntities);
			adapter.notifyDataSetChanged();
			listView.setAdapter(adapter);
			ListViewUtil.setListViewHeightBasedOnChildren(listView);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					ItemListEntity entity = itemEntities.get(arg2);
//					Intent intent = new Intent(ShoppingCartSettleAccountActivity.this, ItemDetailActivity.class);
//					intent.putExtra("itemId", entity.getItemId());
//					startActivity(intent);
					if(entity.getIsGroupon().equals("0")) {
						Intent intent = new Intent(ShoppingCartSettleAccountActivity.this, ItemDetailActivity.class);
						intent.putExtra("itemId", entity.getItemId());
						intent.putExtra("itemName",entity.getItemName());
						startActivity(intent);
					} else {
						Intent intent = new Intent(ShoppingCartSettleAccountActivity.this, GrouponDetailActivity.class);
						intent.putExtra("groupId", entity.getItemId());
						startActivity(intent);
					}
				}
			});

			bargainEntities = entities.get(shopEntities.get(i).getShopId());

			svBargain = (ScrollView) ll.findViewById(R.id.sv_settle_bargain);
			if (bargainEntities != null) {
				LinearLayout llSv = new PlTableView(svBargain.getContext(),
						null);
				llSv.setOrientation(LinearLayout.VERTICAL);
				llSv.setTag(i);
				for (int k = 0; k < bargainEntities.size(); k++) {
					LinearLayout rlbargain = new LinearLayout(getBaseContext());
					rlbargain = (LinearLayout) mInflater.inflate(
							R.layout.settle_account_bargain_item, null);
					tvBargainName = (TextView) rlbargain
							.findViewById(R.id.tv_bargin_title);
					tvBargainContent = (TextView) rlbargain
							.findViewById(R.id.tv_bargin_name);
					tvBargainName.setText("优惠活动" + (k + 1));
					tvBargainContent.setText(bargainEntities.get(k)
							.getBagainName());
					llSv.addView(rlbargain);
				}
				svBargain.addView(llSv);

			}
			etMessage = (EditText) ll.findViewById(R.id.et_message);
			etMessage.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					String str;
					try {
						if(!StringUtil.isNullOrEmpty(s.toString())){
							LogUtil.d("shoppingcartsettle.. s.toString", s.toString());
							
							str = new String(s.toString().getBytes(), "utf-8");
							LogUtil.d("shoppingcartsettle.. str", str);
							shopMessage.put(shopId+"", str);
							LogUtil.d("shopMessage.. str", shopMessage.get(shopId+"").toString());
						}
						
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			});

			rlShopCoupon = (RelativeLayout) ll
					.findViewById(R.id.rl_shop_coupon);
			rlShopCoupon.setTag(i);
			hashMapCashCoupon.put(i, rlShopCoupon);
			rlShopCoupon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					shopIndex = (Integer) v.getTag();
					Bundle bundle = new Bundle();
					bundle.putInt("shopId", shopId);
					bundle.putInt("itemIndex", positionShop.get(positionIndex));
					Intent intent = new Intent(
							ShoppingCartSettleAccountActivity.this,
							ShopCouponSelectActivity.class);
					intent.putExtras(bundle);

					startActivityForResult(intent,
							Constants.ResultType.RESULT_FROM_SHOP_COUPON);
					;
				}
			});

			llOut.addView(ll);

		}
		llOut.addView(llBottom);
		sv.addView(llOut);

		rl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						ShoppingCartSettleAccountActivity.this,
						AddressManageActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("positionAddress", positionAddress);
				intent.putExtras(bundle);
				startActivityForResult(intent,
						Constants.ResultType.RESULT_FROM_ADDRESS);
			}
		});

		rlCoupon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 Intent intent = new
				 Intent(ShoppingCartSettleAccountActivity.this,
				 CouponSelectActivity.class);
				 Bundle bundle = new Bundle();
				 bundle.putInt("positionCoupon", positionCoupon);
				 bundle.putDouble("price", totalPrice);
				 intent.putExtras(bundle);
				 startActivityForResult(intent,
				 Constants.ResultType.RESULT_FROM_COUPON);
//				ToastUtil.showExceptionTips(getBaseContext(), "功能正在完善中，敬请期待！");
			}
		});
/*		rlSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						ShoppingCartSettleAccountActivity.this,
						SendSelectActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("positionSend", positionSend);
				intent.putExtras(bundle);
				startActivityForResult(intent,
						Constants.ResultType.RESULT_FROM_SEND);
			}
		});*/
		rlPay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						ShoppingCartSettleAccountActivity.this,
						PaySelectActivity.class);
				startActivityForResult(intent,
						Constants.ResultType.RESULT_FROM_PAY);
			}
		});
		rlDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						ShoppingCartSettleAccountActivity.this,
						DateTimeAcitivityNew.class);
				Bundle bundle = new Bundle();
				bundle.putInt("positionTime", positionTime);
				intent.putExtras(bundle);
				startActivityForResult(intent,
						Constants.ResultType.RESULT_FROM_TIME);
			}
		});
	}

	private void setDefaultAddress() {
		getAddressData();
	}
	
	private String getCurrentdate() {
    	c= Calendar.getInstance();
    	return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" +(c.get(Calendar.DAY_OF_MONTH)+1);
    }
    
    private String getCurrentTime() {
    	c= Calendar.getInstance();
    	return c.get(Calendar.HOUR_OF_DAY)+":"+ c.get(Calendar.MINUTE);
    }
	
	private void getAddressData() {
		showLoadingDialog(null);
		HttpUtil.getUserAddressListJson(mGetAddressHandler);
	}
	
	private ServerConnectionHandler mGetAddressHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("ShoppingCartSettleAccountActivity", "msg.what = "
					+ msg.what);
			LogUtil.d("ShoppingCartSettleAccountActivity", "msg.arg1 = "
					+ msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				List<UserAddressEntity> entities = DataCache.UserAddressListCache;
				for (int i=0; i< entities.size(); i++) {
					UserAddressEntity entity = entities.get(0);
//					if (entity.getIsDefault() == 1) {
						usrName.setText(entity.getConsignee());
						usrPhoneNum.setText(entity.getMobile());
						usrAddress.setText(entity.getProvince()+ " " + entity.getCommunityName() + " " + entity.getAddress());						
//					}
				}
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case Constants.ResultType.RESULT_FROM_ADDRESS:
			String name = data.getExtras().getString("name");
			String phoneNum = data.getExtras().getString("phoneNum");
			String address = data.getExtras().getString("address");
			String city = data.getExtras().getString("city");
			useAddresId = data.getExtras().getInt("addressId");
			selectCommunity = data.getExtras().getInt("selectCommunity");
			usrName.setText(name);
			usrPhoneNum.setText(phoneNum);
			usrAddress.setText(city + "  " + address);
			
			positionAddress = data.getExtras().getInt("positionAddress");
			if(selectCommunity == 0) {
				tvSend.setText("宝龙物流");
				tvSend.setTextColor(getResources().getColor(R.color.grey));
				rlSend.setClickable(false);			
			} else {
				tvSend.setText("第三方物流");
				tvSend.setTextColor(getResources().getColor(R.color.grey));
				rlSend.setClickable(false);	
			}
//			setDefaultAddress();
			break;
		case Constants.ResultType.RESULT_FROM_COUPON:
			double price = data.getExtras().getDouble("price");
			String description = data.getExtras().getString("description");
			positionCoupon = data.getExtras().getInt("positionCoupon");
			tvCoupon.setText(description + " " + (price == 0.0?"":price));
			totalPrice -= price;
			tvTotalPrice.setText("总计："+ DoubleUtil.subPrice(totalPrice) +"元,包含快递费" + sendPrice + "元");
			payPrice = DoubleUtil.subPrice(DoubleUtil.round((totalPrice),3)) +"";
			
			// sv.post(new Runnable() {
			// public void run() {
			// sv.fullScroll(ScrollView.FOCUS_DOWN);
			// }
			// });

			break;
		case Constants.ResultType.RESULT_FROM_SEND:
			String send = data.getExtras().getString("send");
			tvSend.setText(send);
			positionSend = data.getExtras().getInt("positionSend");
			selectLogisticsId = data.getExtras().getString("logisticsId");
			tvTotalPrice.setText("总计："+ DoubleUtil.subPrice(totalPrice) +"元,包含快递费" + sendPrice + "元");
			payPrice = DoubleUtil.subPrice(DoubleUtil.round((totalPrice),3)) +"";
			// sv.post(new Runnable() {
			// public void run() {
			// sv.fullScroll(ScrollView.FOCUS_DOWN);
			// }
			// });
			break;
		case Constants.ResultType.RESULT_FROM_PAY:
			String pay = data.getExtras().getString("pay");
			payId = data.getExtras().getInt("payId")+"";
			tvPay.setText(pay);
			if(payId.equals("3")) {
				tvTotalPrice.setText("总计："+ DoubleUtil.subPrice(DoubleUtil.round((totalPrice - sendPrice),3)) +"元");
				payPrice = DoubleUtil.subPrice(DoubleUtil.round((totalPrice - sendPrice),3));
				rlSend.setVisibility(View.GONE);
			} else {
				tvTotalPrice.setText("总计："+ DoubleUtil.subPrice(totalPrice) +"元,包含快递费" + sendPrice + "元");
				payPrice = DoubleUtil.subPrice(DoubleUtil.round((totalPrice),3)) +"";
				rlSend.setVisibility(View.VISIBLE);
			}
			
//			if(pay.equals("货到付款")) {
//				logisticsId = "1";
//			} else {
//				logisticsId = "0";
//			}
			// sv.post(new Runnable() {
			// public void run() {
			// sv.fullScroll(ScrollView.FOCUS_DOWN);
			// }
			// });
			break;
		case Constants.ResultType.RESULT_FROM_TIME:
			String time = data.getExtras().getString("receiveTime");
			String date = data.getExtras().getString("receiveDate");
			tvTime.setText(date + "\n" + time);
			receiveTime = time;
			try {
				if(!StringUtil.isNullOrEmpty(dateDefault)) {
					receiveDate = new String(dateDefault.getBytes(), "utf-8");
					if(!StringUtil.isNullOrEmpty(date)) {
						receiveDate = date;
					}
				}
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// sv.post(new Runnable() {
			// public void run() {
			// sv.fullScroll(ScrollView.FOCUS_DOWN);
			// }
			// });
			break;
		case Constants.ResultType.RESULT_FROM_SHOP_COUPON:
			String coupon = data.getExtras().getString("shopCoupon");
			shopCouponPrice = data.getExtras().getDouble("price");
			totalPrice -= shopCouponPrice;
//			if(logisticsId.equals("3")) {
//				tvTotalPrice.setText("总计："+ totalPrice +"元");
//			} else {
				tvTotalPrice.setText("总计："+ DoubleUtil.subPrice(totalPrice) +"元,包含快递费" + sendPrice + "元");
				payPrice = DoubleUtil.subPrice(DoubleUtil.round((totalPrice),3)) +"";
//			}
			
			tvShopCoupon = (TextView) hashMapCashCoupon.get(shopIndex)
					.getChildAt(1);
			tvShopCoupon.setText(coupon.startsWith("0.0")?"不使用":coupon);
			positionShop
					.put(shopIndex, data.getExtras().getInt("positionShop"));
			break;
		case Constants.ResultType.RESULT_FROM_UPOMP:
			if (data != null) {
				Bundle bundle = data.getExtras();
				byte[] xml = bundle.getByteArray("xml");
				// 自行解析并输出
				String Sxml;
				Intent intent = new Intent(ShoppingCartSettleAccountActivity.this, MyOrderActivityNew.class);
				try {
					Sxml = new String(xml, "utf-8");
					if(Sxml.contains("<respCode>0000</respCode>")){
						if(isAllGroupon){
							Intent mIntent = new Intent(ShoppingCartSettleAccountActivity.this,MyGrouponCouponActivity.class);
							startActivity(mIntent);
						}else{
							intent.putExtra("stat", 1);
							intent.putExtra("from", 1);
							startActivity(intent);
						}
					}else{
						intent.putExtra("stat", 0);
						intent.putExtra("from", 1);
						startActivity(intent);
					}
					finish();
//					ToastUtil.showExceptionTips(ShoppingCartSettleAccountActivity.this,"这是支付成功后，回调返回的报文，需自行解析" + Sxml);
//					LogUtil.d("这是支付成功后，回调返回的报文，需自行解析", Sxml+"");
				} catch (Exception e) {
//					ToastUtil.showExceptionTips(ShoppingCartSettleAccountActivity.this,"Exception is " + e);
					ToastUtil.showExceptionTips(ShoppingCartSettleAccountActivity.this,"支付失败");
				}

			} else {
				Intent intent = new Intent(ShoppingCartSettleAccountActivity.this, MyOrderActivityNew.class);
				intent.putExtra("stat", 0);
				intent.putExtra("from", 1);
				startActivity(intent);
			}
			break;
		}
	}

	/**
	 * getOrder(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void getOrder() {
		String param = getOrderParam();
		LogUtil.d("addorder param", param);
		HttpUtil.enterSettle(mOrderHandler, param);
	}
	
	private ServerConnectionHandler mOrderHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("ShoppingCartSettleAccountActivity", "msg.what = "
					+ msg.what);
			LogUtil.d("ShoppingCartSettleAccountActivity", "msg.arg1 = "
					+ msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
//				ToastUtil.showExceptionTips(getBaseContext(), msg.what+ msg.arg1+"");
//				getOrderList();
				Intent intent = new Intent(ShoppingCartSettleAccountActivity.this, MyOrderActivityNew.class);
				intent.putExtra("stat", 1);
				startActivity(intent);
				finish();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
				Toast.makeText(ShoppingCartSettleAccountActivity.this, fail,
						Toast.LENGTH_LONG).show();
				break;
			}
			// mpProgressBar.setVisibility(View.GONE);
		}

	};
	
	private void getOrderList(int type) {
		List<AddOrderEntity> entities = DataCache.ADDORDEREntityCache;
		String result = "";
		if (entities.size() <=0) {
			return;
		}
//		for (int i=0; i<entities.size(); i++) {
//			AddOrderEntity entity = entities.get(i);
//			String str = "订单号：" + entity.getOrderNo() + "商品数量：" +entity.getItemNum() 
//					+ "包裹数：" + entity.getPakageNum() + "金额" +entity.getPrice();
//			result += (str + "\n");
// 		}
//		showCustomToast(result);
//		getChannelMsg(entities, type);
	}
	
	private String getOrderParam() {
		List<CartShopListEntity> shopEntities = DataCache.CartCountShopListCache;
		List<ItemListEntity> itemEntities ;
		ArrayList<ItemBargainListEntity> bargainEntities;
		List<ShoppingCartSettleEntity> list = new ArrayList<ShoppingCartSettleEntity>();		
		List<UserAddressEntity> addressEntity = DataCache.UserAddressListCache;
		int addressId = addressEntity.get(0).getAddressId();
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
			obj.put("addressId", useAddresId);
			obj.put("logisticsId", selectLogisticsId);
			obj.put("payId", payId);			
			obj.put("price", totalPrice+"");
			obj.put("freight", 0+"");
			obj.put("receiveTime", receiveTime+"");
			obj.put("receiveDate", receiveDate+"");
			obj.put("plCashCouponId", 0+"");
			JSONArray shopArray = new JSONArray();
			for (int i = 0; i < shopEntities.size(); i++) {
				CartShopListEntity entity = shopEntities.get(i);
				JSONObject shopObject = new JSONObject();
				int shopId = entity.getShopId();
				boolean isAddShop = false;
//				shopObject.put("id", shopId);
				JSONArray itemArray = new JSONArray();
//				itemEntities = DataCache.CartItemListCache.get(shopId);
				itemEntities = DataCache.CarCountItemListCache.get(shopEntities.get(i)
						.getShopId());
				
				for (int j = 0; j < itemEntities.size(); j++) {
					ItemListEntity iEntity = itemEntities.get(j);	
					int itemId = iEntity.getId();
						JSONObject itemObject = new JSONObject();
						itemObject.put("id", iEntity.getItemId()+"");
						itemObject.put("type", iEntity.getIsGroupon());
						itemObject.put("plPrice", iEntity.getPlPrice());
						itemObject.put("buyNum", iEntity.getBuyNum()+"");
						JSONArray bargainArray = new JSONArray();
						bargainEntities = DataCache.UserOrderDetailBagainListCache.get(itemId);
						if (bargainEntities == null) {
//							itemObject.put("itemBargainList", "");
						}else {
							for(int k=0; k<bargainEntities.size(); k++) {
								JSONObject bargainObject = new JSONObject();
								ItemBargainListEntity bargainEntity = bargainEntities.get(k);
								bargainObject.put("name", bargainEntity.getName());
								bargainObject.put("type", bargainEntity.getType());							
							}	
							itemObject.put("itemBargainList", bargainArray);
						}
						
						itemArray.put(itemObject);
				}
				String messageSend = "";
				if(!(StringUtil.isEmpty(shopMessage.get(shopId+"")))) {
					messageSend = shopMessage.get(shopId+"");
				}
				shopObject.put("id", shopId+"");
				shopObject.put("itemList", itemArray);
				shopObject.put("cashCouponId", 0+"");
				shopObject.put("message", messageSend);
				shopArray.put(shopObject);
			}
			obj.put("shopList", shopArray);
			LogUtil.d("ShoppingCartSettleAccountActivity params = ", obj.toString());
			return obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
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
			if(isPay) {
				Intent intent3 = new Intent(ShoppingCartSettleAccountActivity.this, HomeActivityNew.class);
				intent3.putExtra("mTabId", 3);
				startActivity(intent3);
			} else {
				finish();
			}
			break;
		case R.id.ivPay:
			if(selectLogisticsId == null) {
				ToastUtil.showExceptionTips(getBaseContext(), "请选择物流方式");
				return;
			}
			isPay = true;
			if(!CommonUtils.isFastDoubleClick()) {
				if(payId.equals("0")) {
					getOrderFromServer(0);
				} else if(payId.equals("1")){
					getOrder();			
				} else if(payId.equals("2")) {
					getOrder();
				} else {
					getOrderFromServer(1);
				}
			}
			
			break;
		}
	}
	
	private void getOrderFromServer(final int type){
		
		this.type = type;
		showLoadingDialog(null);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data",
							getOrderParam()));
					httpPost = new HttpPost(Constants.ServerUrl.ADD_ORDER_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, 10000);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));

					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getGroupListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						JSONObject jsObj = new JSONObject(result);
						Message msg = Message.obtain(mHandler, 0, result);
						msg.sendToTarget();
						HttpUtil.checkResultCode(result, mHandler);
					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}

					// DataUtil.getGrouponListData(getBaseContext(),
					// mServerConnectionHandler, obj.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}
	
	private void getChannelMsg(final String jsObj, final int type) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {
//					original = new JSONObject(jsObj);
					List<DomainOrderMsg> listOrderMsg = new ArrayList<DomainOrderMsg>();
//					List<DomainOrderMsg> listOrderMsg = DomainOrderMsg
//							.convertJsToOrderMsg(context, jsObj.getJSONObject("data")
//									.getString("orderList"));
					JSONObject listData = JSONUtil.getJSONObject(jsObj,"data", null);
					if (listData == null)
						return;
					JSONArray array = JSONUtil.getJSONArray(listData,"orderList", null);
					
					if (array != null) {
						
						for (int index = 0; index < array.length(); index++) {
						
								JSONObject obj = array.getJSONObject(index);
								
								DomainOrderMsg orderMsg = new DomainOrderMsg();
								String orderNo = JSONUtil.getString(obj,"orderNo", "");
								String pakageNum = JSONUtil.getString(obj,"pakageNum", "");
								String itemNum = JSONUtil.getString(obj,"itemNum", "");
								String price = JSONUtil.getString(obj,"price", "");
								
								orderMsg.setOrderNo(orderNo);
								orderMsg.setPakageNum(pakageNum);
								orderMsg.setItemNum(itemNum);
								orderMsg.setPrice(price);
								listOrderMsg.add(orderMsg);
							}
						}
					
					
					paymentNo = JSONUtil.getString(listData,"paymentNo",null);
					orderMsg = listOrderMsg.get(0);
					JSONObject jsParam = new JSONObject();
					jsParam.put("appCode", Constants.APPCODE);
					jsParam.put("bizCode", paymentNo);
					jsParam.put("bizType", "1");
					jsParam.put("bizDescription", totalName);
					if (type == 0) {
						jsParam.put("amount", payPrice);
					} else {
						jsParam.put("amount", payPrice);
						LogUtil.v("price", orderMsg.getPrice());
						LogUtil.v("sendPrice", sendPrice + "");

					}

					jsParam.put("type", "1");
					jsParam.put("userId", Constants.userId);
					jsParam.put("userName", DataCache.UserDataCache.get(0)
							.getUsername());
					jsParam.put("sign", makeSignString(jsParam));
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data",
							jsParam.toString()));
					
					URI uri = URI
							.create(Constants.ServerUrl.GET_ACTIVE_CHANNEL);
					httpPost = new HttpPost(uri);
					LogUtil.v("getChannelMsg param", jsParam.toString());
					LogUtil.v("getChannelMsg url", Constants.ServerUrl.GET_ACTIVE_CHANNEL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, 10000);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));

					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getGroupListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						// LogUtil.d("HttpUtil", "getGroupListJson result= "
						// + result);
						// if (!checkResultCode(result, handler)) {
						// return;
						// }
						// boolean ret = JSONParser.parseGroupList(result);
						// checkParserStatus(ret, handler);
						JSONObject jsObj = new JSONObject(result);
						LogUtil.v("getChannelMsg result", result);
						Message msg = Message.obtain(mHandler, 3, result);
						msg.sendToTarget();

					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}

					// DataUtil.getGrouponListData(getBaseContext(),
					// mServerConnectionHandler, obj.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	
	private void payRequest(final String str) {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				JSONObject original = null;
				try {
					original = new JSONObject(str);
					
		
//					List<DomainOrderMsg> listOrderMsg = DomainOrderMsg
//							.convertJsToOrderMsg(context, jsObj.getJSONObject("data")
//									.getString("orderList"));
//					paymentNo = jsObj.getJSONObject("data").getString("paymentNo");
//					orderMsg = listOrderMsg.get(0);
					JSONObject jsParam = new JSONObject();
					jsParam = new JSONObject();
					jsParam.put("appCode", Constants.APPCODE);
					jsParam.put("bizCode", paymentNo);
					jsParam.put("bizType", "1");
					jsParam.put("bizDescription", totalName);
					jsParam.put("amount", payPrice);
					jsParam.put("type", "1");
					jsParam.put("userId", Constants.userId);
					jsParam.put("userName", DataCache.UserDataCache.get(0).getUsername());
					jsParam.put("responseType", "json");
					jsParam.put("serialNum", original.get("serialNum"));
					jsParam.put("sign", makeSecondSignString(jsParam));
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data",
							jsParam.toString()));
					httpPost = new HttpPost(Constants.ServerUrl.GET_REQUEST_PAY);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							"utf-8"));
					httpPost.setHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						httpPost.setHeader("TGC", DataCache.UserDataCache
								.get(0).getTGC());
					}
					httpPost.setHeader("mac", Constants.mac);
					httpPost.setHeader("uid", Constants.userId + "");
					httpPost.setHeader("client", "android");
					httpPost.setHeader("version", Constants.version);
					SchemeRegistry schReg = new SchemeRegistry();
					HttpParams params = new BasicHttpParams();
					HttpClient httpClient = CustomerHttpClient.getHttpClient(
							params, schReg);
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams
							.setUserAgent(
									params,
									"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
											+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
					ConnManagerParams.setTimeout(params, 2000);
					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 4000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, 10000);
					// 设置我们的HttpClient支持HTTP和HTTPS两种模式
					schReg.register(new Scheme("http", PlainSocketFactory
							.getSocketFactory(), 80));
					schReg.register(new Scheme("https", SSLSocketFactory
							.getSocketFactory(), 443));

					HttpResponse response = httpClient.execute(httpPost);
					int status = response.getStatusLine().getStatusCode();
					LogUtil.d("HttpUtil", "getGroupListJson= " + status);
					if (status == 200) {
						String result = EntityUtils.toString(response
								.getEntity());
						LogUtil.v("payRequest result", result);
						// LogUtil.d("HttpUtil", "getGroupListJson result= "
						// + result);
						// if (!checkResultCode(result, handler)) {
						// return;
						// }
						// boolean ret = JSONParser.parseGroupList(result);
						// checkParserStatus(ret, handler);
						JSONObject jsObj = new JSONObject(result);
						Message msg = Message.obtain(mHandler, 2, jsObj);
						msg.sendToTarget();

					} else {
						/*
						 * handler.obtainMessage(
						 * Constants.HttpStatus.CONNECTION_EXCEPTION, "" +
						 * status).sendToTarget();
						 */
					}

					// DataUtil.getGrouponListData(getBaseContext(),
					// mServerConnectionHandler, obj.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	
}
	
	private String makeSignString(JSONObject jsParam) {
		
		StringBuilder sb = new StringBuilder("");
		sb.append("amount=");
		sb.append(jsParam.optString("amount", ""));
		sb.append("&appCode=");
		sb.append(jsParam.optString("appCode", ""));
		sb.append("&bizCode=");
		sb.append(jsParam.optString("bizCode", ""));
		sb.append("&bizDescription=");
		sb.append(jsParam.optString("bizDescription", ""));
		sb.append("&bizType=");
		sb.append(jsParam.optString("bizType", ""));
		sb.append("&type=");
		sb.append(jsParam.optString("type", ""));
		sb.append("&userId=");
		sb.append(jsParam.optString("userId", ""));
		sb.append("&userName=");
		sb.append(jsParam.optString("userName", ""));
		sb.append("&");
		sb.append(Constants.KEY);
		LogUtil.v("getChannelMsg befor Md5 = ", sb.toString());
		return MD5Utils.getMd5Str(sb.toString());
	}
	
	private String makeSecondSignString(JSONObject jsParam) {
		
		StringBuilder sb = new StringBuilder("");
		sb.append("amount=");
		sb.append(jsParam.optString("amount", ""));
		sb.append("&appCode=");
		sb.append(jsParam.optString("appCode", ""));
		sb.append("&bizCode=");
		sb.append(jsParam.optString("bizCode", ""));
		sb.append("&bizDescription=");
		sb.append(jsParam.optString("bizDescription", ""));
		sb.append("&bizType=");
		sb.append(jsParam.optString("bizType", ""));
		sb.append("&responseType=");
		sb.append(jsParam.optString("responseType", ""));
		sb.append("&serialNum=");
		sb.append(jsParam.optString("serialNum", ""));
		sb.append("&type=");
		sb.append(jsParam.optString("type", ""));
		sb.append("&userId=");
		sb.append(jsParam.optString("userId", ""));
		sb.append("&userName=");
		sb.append(jsParam.optString("userName", ""));
		sb.append("&");
		sb.append(Constants.KEY);
		LogUtil.v("getChannelMsg befor Md5 = ", sb.toString());
		return MD5Utils.getMd5Str(sb.toString());
	}
	
	@Override  
	protected void onResume() {  
		super.onResume();  
		btnPay.setClickable(true);
		if(StringUtil.isNullOrEmpty(receiveTime)) {
			receiveTime = timeDefault;
		}
		 
		try {
			if(!StringUtil.isNullOrEmpty(dateDefault)) {
				if(StringUtil.isNullOrEmpty(receiveDate)) {
					receiveDate = new String(dateDefault.getBytes(), "utf-8");
				}
				
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	ServerConnectionHandler mHandler = new ServerConnectionHandler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				//JSONObject jsObj = (JSONObject) msg.obj;
				String result = (String)msg.obj;
				getChannelMsg(result, type);
				dismissLoadingDialog();
				break;
			case 3:
				//JSONObject jsObj1 = (JSONObject) msg.obj;
				String result1 = (String)msg.obj;
				payRequest(result1);
				break;
			case 2:
				JSONObject jsObj2 = (JSONObject) msg.obj;
				try {
					DomainChannelMsg channelMsg = DomainChannelMsg.convertJsonToChannelMsg(context, jsObj2.getString("data"));
					LogUtil.v("payRequest data =", jsObj2.getString("data"));
					popWindowPayList.clearData();
					popWindowPayList.setChannelMsg(channelMsg);
					popWindowPayList.addData(channelMsg.getChannels());
					popWindowPayList.isAllGroupon(isAllGroupon);
					popWindowPayList.show(root);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
				ToastUtil.showExceptionTips(getBaseContext(), msg.obj.toString());
				break;
			}
		};
	};

}
