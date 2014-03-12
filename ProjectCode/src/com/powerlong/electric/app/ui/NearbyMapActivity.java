/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * NearbyMapActivity.java
 * 
 * 2013年9月26日-下午8:10:43
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.FloatMath;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.AbstractSpinerAdapter;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.NearbySearchEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.listener.PlWebViewLoadingListener;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.widget.PlWebView;
import com.powerlong.electric.app.widget.SpinerPopWindow;

/**
 * 
 * NearbyMapActivity
 * 
 * @author: YangCheng Miao
 * 2013年9月26日-下午8:10:43
 * 
 * @version 1.0.0
 * 
 */
public class NearbyMapActivity extends BaseActivity implements OnClickListener, AbstractSpinerAdapter.IOnItemSelectListener{
	private Context context;
	private PlWebView mPlWebview = null;
	private ImageView ivBack;
	private TextView tvTitle;
	private LinearLayout ll1, ll2, ll3, ll4, ll5, ll6;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6;
	private LinearLayout llShow;
	private LinearLayout llHide;
	private RelativeLayout rlNearBy;
	private RelativeLayout rlTitle;
	private SpinerPopWindow mSpinerPopWindow;
	private AutoCompleteTextView tvSearch;
	private List<String> list = new ArrayList<String>();
	
	private ArrayList<NearbySearchEntity> curentList;
	private int cur_floor = 2;
	private ImageView ivFilter;
	private ImageView ivClose;
	private String strSearch;
	private boolean isSearchAll = false;
	private String flagInfo;
	private enum MODE {
		NONE, DRAG, ZOOM

	};

	private MODE mode = MODE.NONE;// 默认模式
	private float beforeLenght, afterLenght;// 两触点距离
	private float scale_temp;// 缩放比例
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearby_map);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		context = this;
		findView();	
		init();
		mPlWebview.setWebLoadingListener(new PlWebViewLoadingListener() {
			
			@Override
			public void onLoadingSucced() {
				dismissLoadingDialog();
//				showCustomToast("加载成功");
			}
			
			@Override
			public void onLoadingFailed() {
				dismissLoadingDialog();
//				showCustomToast("加载失败");
			}
		});
		//设置js调用java监听。
		mPlWebview.setOnJsInterfaceInvokeListener(new PlWebView.OnJsInterfaceInvokeListener(){
			@Override
			public void onJsInvoked(String data) {
//				Toast.makeText(context, ""+data, Toast.LENGTH_SHORT).show();
//				startActivity(new Intent());
				//根据店铺id跳转到店铺详情
				Intent intent = new Intent(context,ShopDetailActivityNew.class);
				intent.putExtra("shopId", Long.parseLong(data));
				startActivity(intent);
			}});
		
		mPlWebview.setWebChromeClient(new WebChromeClient() {
			   public void onProgressChanged(WebView view, int progress) {
			     // Activities and WebViews measure progress with different scales.
			     // The progress meter will automatically disappear when we reach 100%
			     NearbyMapActivity.this.setProgress(progress * 1000);
			   }
			 });

//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list); 
//		tvSearch.setAdapter(adapter);
		tvSearch.addTextChangedListener(new TextWatcher() {
			String beforeChange = "";
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				beforeChange = s.toString();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String str = s.toString();
				if(str.length()>0 && !beforeChange.equals(str)){
					getData(str);
					strSearch = str;
				}
			}
		});
		
		tvSearch.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId ==EditorInfo.IME_ACTION_SEARCH /*&& event.getRepeatCount() == 0 */){
				// 先隐藏键盘
				((InputMethodManager) tvSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
/*				String url = Constants.ServerUrl.URL_PRFIX_NEARBY+"locate_web/location/searchByInfo.htm?";
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("mall", Constants.mallId+"");
					jsonObject.put("info", strSearch);
					jsonObject.put("currfloor", cur_floor);
					jsonObject.put("x", "-1");
					jsonObject.put("y", "-1");
					jsonObject.put("mac", Constants.mac);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				mPlWebview.loadUrl(url+"data="+jsonObject.toString());*/
				getData(strSearch);
				}
				return false;
			}
		});

	}
	
	private void findView() {
		mPlWebview = (PlWebView) findViewById(R.id.webview);
//		WebSettings webSettings= mPlWebview.getSettings(); // webView: 类WebView的实例 
		
		ivBack = (ImageView) findViewById(R.id.iv_Return);
		ivBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText("地图");
		ll1 = (LinearLayout) findViewById(R.id.ll_nearby_b1);
		ll1.setOnClickListener(this);
		ll2 = (LinearLayout) findViewById(R.id.ll_nearby_1f);
		ll2.setOnClickListener(this);
		ll3 = (LinearLayout) findViewById(R.id.ll_nearby_2f);
		ll3.setOnClickListener(this);
		ll4 = (LinearLayout) findViewById(R.id.ll_nearby_3f);
		ll4.setOnClickListener(this);
		ll5 = (LinearLayout) findViewById(R.id.ll_nearby_4f);
		ll5.setOnClickListener(this);
		ll6 = (LinearLayout) findViewById(R.id.ll_nearby_5f);
		ll6.setOnClickListener(this);
		tv1 = (TextView) findViewById(R.id.tv_nearby_1);
		tv2 = (TextView) findViewById(R.id.tv_nearby_2);
		tv3 = (TextView) findViewById(R.id.tv_nearby_3);
		tv4 = (TextView) findViewById(R.id.tv_nearby_4);
		tv5 = (TextView) findViewById(R.id.tv_nearby_5);
		tv6 = (TextView) findViewById(R.id.tv_nearby_6);
		llShow = (LinearLayout) findViewById(R.id.ll_nearby_show);
		llShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				llShow.setVisibility(View.GONE);
				rlNearBy.setVisibility(View.VISIBLE);
				rlTitle.setVisibility(View.VISIBLE);
			}
		});
		llHide = (LinearLayout) findViewById(R.id.ll_nearby_hide);
		llHide.setOnClickListener(this);
		rlNearBy = (RelativeLayout) findViewById(R.id.rl_nearby);
		rlTitle = (RelativeLayout) findViewById(R.id.title);
		mSpinerPopWindow = new SpinerPopWindow(this);
//		mSpinerPopWindow.refreshData(nameList, 0);
		mSpinerPopWindow.setItemListener(this);
		tvSearch = (AutoCompleteTextView) findViewById(R.id.evSearch);
		ivFilter = (ImageView) findViewById(R.id.ivFilter);
		ivClose = (ImageView) findViewById(R.id.ivClose);
	}
	
	protected void init() {
//		showLoadingDialog("正在加载,请稍后...");
//		mPlWebview.loadUrl(Constants.ServerUrl.URL_PRFIX_NEARBY_NEARBY + "locate_web/shopInfo/getShopInfo.htm?");
		mPlWebview.loadUrl(Constants.ServerUrl.URL_PRFIX_NEARBY+"locate_web/location/search.htm?data={%22mall%22:%22"+Constants.mallId+"%22,%22mac%22:%22"+ Constants.mac + "%22}");
//		mPlWebview.loadUrl("http://192.168.171.112:8080/locate_web/location/search.htm?data={%22mall22%:%221%22,%22mac%22:%22EC78B2914CB24DA89736323ADE6ED812%22}");
		//mPlWebview.setFitsSystemWindows(true);
//		mPlWebview.loadUrl("http://192.168.171.39/OCC_SSO_Web/mobile.html");
		
		Log.v("NearbyMap", Constants.ServerUrl.URL_PRFIX_NEARBY + "locate_web/location/search.htm?data={%22mall22%:%22"+Constants.mallId+"%22,%22mac%22:%22"+ Constants.mac + "%22}");
		
		
		
		
		tvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				try {
					String url = Constants.ServerUrl.URL_PRFIX_NEARBY+"locate_web/location/searchByInfo.htm?";
					NearbySearchEntity nearbySearchEntity = curentList.get(arg2);
					if(arg2 == 0) {
						isSearchAll = true;
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("mall", Constants.mallId);
						flagInfo = nearbySearchEntity.getLocate();
						jsonObject.put("info", flagInfo);
						jsonObject.put("currfloor", cur_floor);
						jsonObject.put("x", "-1");
						jsonObject.put("y", "-1");
						jsonObject.put("mac", Constants.mac);
						mPlWebview.loadUrl(url+"data="+jsonObject.toString());
						tvSearch.setText("全部");
						
					} else {
						isSearchAll = false;
	//				Toast.makeText(context, ""+nearbySearchEntity.getShopid(), Toast.LENGTH_SHORT).show();
	//					String url = "http://192.168.171.112:8080/locate_web/location/searchByInfo.htm?";
						
	//				data={"mall":"1","info":"川菜","currfloor":"1","x":"1","y":"1","floor":"1"}
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("mall", Constants.mallId);
						jsonObject.put("info", nearbySearchEntity.getShopname());
						jsonObject.put("currfloor", cur_floor);
						jsonObject.put("x", nearbySearchEntity.getLocx());
						jsonObject.put("y", nearbySearchEntity.getLocy());
						jsonObject.put("floor", nearbySearchEntity.getFloorid());
						jsonObject.put("shopId", nearbySearchEntity.getShopid());
						jsonObject.put("mac", Constants.mac);
						mPlWebview.loadUrl(url+"data="+jsonObject.toString());
					}
					
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
			           imm.hideSoftInputFromWindow(tvSearch.getWindowToken(),0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		ivFilter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				mPlWebview.loadUrl("javascript: testAndroid ()");
				try {
//					String url = "http://192.168.171.112:8080/locate_web/location/searchByInfo.htm?";
/*					String url = Constants.ServerUrl.URL_PRFIX+"locate_web/location/searchByInfo.htm?";
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("mall", Constants.mallId+"");
					jsonObject.put("info", tvSearch.getText().toString() );
					jsonObject.put("currfloor", cur_floor);
					jsonObject.put("x", "");
					jsonObject.put("y", "");
					jsonObject.put("floor", "");
					mPlWebview.loadUrl(url+"data="+jsonObject.toString());*/
					mPlWebview.loadUrl(Constants.ServerUrl.URL_PRFIX_NEARBY+"locate_web/location/search.htm?data={%22mall%22:%221%22,%22mac%22:%22"+ Constants.mac + "%22}");
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
		ivClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tvSearch.setText("");
			}
		});
	}

	@Override  
	protected void onResume() {  
	 /** 
	  * 设置为横屏 
	  */  
//	 if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){  
//	  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);  
//	 }  
	 super.onResume();  

	 mPlWebview.loadUrl("javascript: unlockAutoLocate ()");
	}


	
	
	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mPlWebview.loadUrl("javascript: lockAutoLocate ()");
	}

	private void getData(String str) {
		HttpUtil.searchNearbyJson(getBaseContext(), mServerConnectionHandler, 1, str);
	}
	
	private void updateData() {
		ArrayList<NearbySearchEntity> entities = DataCache.NearbySearchCache;
		list.clear();
		for (int i=0; i<entities.size(); i++) {
			NearbySearchEntity entity = entities.get(i);
			list.add(entity.getShopname());
			Log.v("NearbyMapActivity shopName = ", entity.getShopname());
		}
		curentList = entities;
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list); 
		tvSearch.setThreshold(1);
		tvSearch.setFocusable(true);
		tvSearch.requestFocus();
		tvSearch.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("ItemPicViewFlipperActivity", "msg.what = " + msg.what);
			LogUtil.d("ItemPicViewFlipperActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
//				ToastUtil.showExceptionTips(getBaseContext(), "加载成功");
				updateData();
				
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
	@SuppressLint("ResourceAsColor")
	private void setChanged(int floor) {
		switch (floor) {
		case 1:
			ll1.setBackgroundResource(R.drawable.nearby_map_bottom_press);
			ll2.setBackgroundResource(R.color.transparent);
			ll3.setBackgroundResource(R.color.transparent);
			ll4.setBackgroundResource(R.color.transparent);
			ll5.setBackgroundResource(R.color.transparent);
			ll6.setBackgroundResource(R.color.transparent);
			tv1.setTextColor(getResources().getColor(R.color.navigation_text_press));
			tv2.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv3.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv4.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv5.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv6.setTextColor(getResources().getColor(R.color.navigation_text_nor));			
			break;
		case 2:
			ll2.setBackgroundResource(R.drawable.nearby_map_bottom_press);
			ll1.setBackgroundResource(R.color.transparent);
			ll3.setBackgroundResource(R.color.transparent);
			ll4.setBackgroundResource(R.color.transparent);
			ll5.setBackgroundResource(R.color.transparent);
			ll6.setBackgroundResource(R.color.transparent);
			tv2.setTextColor(getResources().getColor(R.color.navigation_text_press));
			tv1.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv3.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv4.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv5.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv6.setTextColor(getResources().getColor(R.color.navigation_text_nor));			
			break;
		case 3:
			ll3.setBackgroundResource(R.drawable.nearby_map_bottom_press);
			ll2.setBackgroundResource(R.color.transparent);
			ll1.setBackgroundResource(R.color.transparent);
			ll4.setBackgroundResource(R.color.transparent);
			ll5.setBackgroundResource(R.color.transparent);
			ll6.setBackgroundResource(R.color.transparent);
			tv3.setTextColor(getResources().getColor(R.color.navigation_text_press));
			tv2.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv1.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv4.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv5.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv6.setTextColor(getResources().getColor(R.color.navigation_text_nor));	
			break;
		case 4:
			ll4.setBackgroundResource(R.drawable.nearby_map_bottom_press);
			ll2.setBackgroundResource(R.color.transparent);
			ll3.setBackgroundResource(R.color.transparent);
			ll1.setBackgroundResource(R.color.transparent);
			ll5.setBackgroundResource(R.color.transparent);
			ll6.setBackgroundResource(R.color.transparent);
			tv4.setTextColor(getResources().getColor(R.color.navigation_text_press));
			tv2.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv3.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv1.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv5.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv6.setTextColor(getResources().getColor(R.color.navigation_text_nor));	
			break;
		case 5:
			ll5.setBackgroundResource(R.drawable.nearby_map_bottom_press);
			ll2.setBackgroundResource(R.color.transparent);
			ll3.setBackgroundResource(R.color.transparent);
			ll4.setBackgroundResource(R.color.transparent);
			ll1.setBackgroundResource(R.color.transparent);
			ll6.setBackgroundResource(R.color.transparent);
			tv5.setTextColor(getResources().getColor(R.color.navigation_text_press));
			tv2.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv3.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv4.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv1.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv6.setTextColor(getResources().getColor(R.color.navigation_text_nor));	
			break;
		case 6:
			ll6.setBackgroundResource(R.drawable.nearby_map_bottom_press);
			ll2.setBackgroundResource(R.color.transparent);
			ll3.setBackgroundResource(R.color.transparent);
			ll4.setBackgroundResource(R.color.transparent);
			ll5.setBackgroundResource(R.color.transparent);
			ll1.setBackgroundResource(R.color.transparent);
			tv6.setTextColor(getResources().getColor(R.color.navigation_text_press));
			tv2.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv3.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv4.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv5.setTextColor(getResources().getColor(R.color.navigation_text_nor));
			tv1.setTextColor(getResources().getColor(R.color.navigation_text_nor));	
			break;

		default:
			break;
		}
	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.ivBack:
			finish();
			break;
		case R.id.ll_nearby_b1:
//			getData(1);
			if(isSearchAll) {
				mPlWebview.loadUrl(Constants.ServerUrl.URL_PRFIX+
						"locate_web/location/searchByInfo.htm?data={%22mall%22:%22"+Constants.mallId+"%22,%22currfloor%22:%221%22,%22mac%22:%22" +Constants.mac +"%22,%22info%22:"
						+ "%22" + flagInfo +"%22" + "}");
				LogUtil.v("<<<<<<<", Constants.ServerUrl.URL_PRFIX_NEARBY+
						"locate_web/location/searchByInfo.htm?data={%22mall%22:%221%22,%22currfloor%22:%221%22,%22mac%22:%22" +Constants.mac +"%22,%22info%22:"
						+ "%22" + flagInfo +"%22" + "}");
			} else {
				mPlWebview.loadUrl(Constants.ServerUrl.URL_PRFIX_NEARBY+"locate_web/location/search.htm?data={%22mall%22:%22"+Constants.mallId+"%22,%22floor%22:%221%22,%22mac%22:%22" +Constants.mac +"%22}");
			}
			setChanged(1);
			cur_floor = 1;
			break;
		case R.id.ll_nearby_1f:
//			getData(2);
			if(isSearchAll) {
				mPlWebview.loadUrl(Constants.ServerUrl.URL_PRFIX_NEARBY+
						"locate_web/location/searchByInfo.htm?data={%22mall%22:%22"+Constants.mallId+"%22,%22currfloor%22:%222%22,%22mac%22:%22" +Constants.mac +"%22,%22info%22:"
						+ "%22" + flagInfo +"%22" + "}");
			} else {
				mPlWebview.loadUrl(Constants.ServerUrl.URL_PRFIX_NEARBY+"locate_web/location/search.htm?data={%22mall%22:%22"+Constants.mallId+"%22,%22floor%22:%222%22,%22mac%22:%22" +Constants.mac +"%22}");			
			}
			setChanged(2);
			cur_floor = 2;
			break;
		case R.id.ll_nearby_2f:
//			getData(3);
			if(isSearchAll) {
				mPlWebview.loadUrl(Constants.ServerUrl.URL_PRFIX_NEARBY+
						"locate_web/location/searchByInfo.htm?data={%22mall%22:%22"+Constants.mallId+"%22,%22currfloor%22:%223%22,%22mac%22:%22" +Constants.mac +"%22,%22info%22:"
						+ "%22" + flagInfo +"%22" + "}");
			} else {
				mPlWebview.loadUrl(Constants.ServerUrl.URL_PRFIX_NEARBY+"locate_web/location/search.htm?data={%22mall%22:%22"+Constants.mallId+"%22,%22floor%22:%223%22,%22mac%22:%22" +Constants.mac +"%22}");			
			}
			setChanged(3);
			cur_floor = 3;
			break;
		case R.id.ll_nearby_3f:
			if(isSearchAll) {
				mPlWebview.loadUrl(Constants.ServerUrl.URL_PRFIX_NEARBY+
						"locate_web/location/searchByInfo.htm?data={%22mall%22:%22"+Constants.mallId+"%22,%22currfloor%22:%224%22,%22mac%22:%22" +Constants.mac +"%22,%22info%22:"
						+ "%22" + flagInfo +"%22" + "}");
			} else {
				mPlWebview.loadUrl(Constants.ServerUrl.URL_PRFIX_NEARBY+"locate_web/location/search.htm?data={%22mall%22:%22"+Constants.mallId+"%22,%22floor%22:%224%22,%22mac%22:%22" +Constants.mac +"%22}");//			
			}
//				getData(4);
			setChanged(4);
			cur_floor = 4;
			break;
		case R.id.ll_nearby_4f:
			if(isSearchAll) {
				mPlWebview.loadUrl(Constants.ServerUrl.URL_PRFIX_NEARBY+
						"locate_web/location/searchByInfo.htm?data={%22mall%22:%22"+Constants.mallId+"%22,%22currfloor%22:%225%22,%22mac%22:%22" +Constants.mac +"%22,%22info%22:"
						+ "%22" + flagInfo +"%22" + "}");
			} else {
				mPlWebview.loadUrl(Constants.ServerUrl.URL_PRFIX_NEARBY+"locate_web/location/search.htm?data={%22mall%22:%22"+Constants.mallId+"%22,%22floor%22:%225%22,%22mac%22:%22" +Constants.mac +"%22}");//			
			}
				//			getData(5);
			setChanged(5);
			cur_floor = 5;
			break;
		case R.id.ll_nearby_5f:
			if(isSearchAll) {
				mPlWebview.loadUrl(Constants.ServerUrl.URL_PRFIX_NEARBY+
						"locate_web/location/searchByInfo.htm?data={%22mall%22:%22"+Constants.mallId+"%22,%22currfloor%22:%226%22,%22mac%22:%22" +Constants.mac +"%22,%22info%22:"
						+ "%22" + flagInfo +"%22" + "}");
			} else {
				mPlWebview.loadUrl(Constants.ServerUrl.URL_PRFIX_NEARBY+"locate_web/location/search.htm?data={%22mall%22:%22"+Constants.mallId+"%22,%22floor%22:%226%22,%22mac%22:%22" +Constants.mac +"%22}");//			
			}
				//			getData(6);
			setChanged(6);
			cur_floor = 6;
			break;
		case R.id.ll_nearby_show:
//			llShow.setVisibility(View.GONE);
//			hsv.setVisibility(View.VISIBLE);
//			Log.v("<<<<<<<<<<", "click");
		case R.id.ll_nearby_hide:
			llShow.setVisibility(View.VISIBLE);
			rlNearBy.setVisibility(View.GONE);
			rlTitle.setVisibility(View.GONE);
		default:
			break;
		}
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
			finish();
			return true;
		} 
/*		if (keyCode == KeyEvent.KEYCODE_SEARCH && event.getRepeatCount() == 0) {
			String url = Constants.ServerUrl.URL_PRFIX_NEARBY+"locate_web/location/searchByInfo.htm?";
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("mall", Constants.mallId+"");
				jsonObject.put("info", strSearch);
				jsonObject.put("currfloor", cur_floor);
				jsonObject.put("x", "");
				jsonObject.put("y", "");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			mPlWebview.loadUrl(url+"data="+jsonObject.toString());
		}*/
			 return true;
		
	}

	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.adapter.AbstractSpinerAdapter.IOnItemSelectListener#onItemClick(int)
	 */
	@Override
	public void onItemClick(int pos) {
		Toast.makeText(context, ""+pos, Toast.LENGTH_SHORT).show();
	}
	
	/** 两个手指 只能放大缩小 **/
	void onPointerDown(MotionEvent event) {
		if (event.getPointerCount() == 2) {
			mode = MODE.ZOOM;
			beforeLenght = getDistance(event);// 获取两点的距离
		}
	}
	
	/** 获取两点的距离 **/
	float getDistance(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);

		return FloatMath.sqrt(x * x + y * y);
	}
	
	/** 移动的处理 **/
	void onTouchMove(MotionEvent event) {
		int left = 0, top = 0, right = 0, bottom = 0;
		
		/** 处理缩放 **/
		if (mode == MODE.ZOOM) {

			afterLenght = getDistance(event);// 获取两点的距离

			float gapLenght = afterLenght - beforeLenght;// 变化的长度

			if (Math.abs(gapLenght) > 5f) {
				scale_temp = afterLenght / beforeLenght;// 求的缩放的比例

				this.setScale(scale_temp);

				beforeLenght = afterLenght;
			}
		}

	}
	
	/** 处理缩放 **/
	void setScale(float scale) {

		// 放大
		if (scale > 1) {
			llShow.setVisibility(View.VISIBLE);
			rlNearBy.setVisibility(View.GONE);
			rlTitle.setVisibility(View.GONE);
		}
		// 缩小
		else if (scale < 1) {
			llShow.setVisibility(View.GONE);
			rlNearBy.setVisibility(View.VISIBLE);
			rlTitle.setVisibility(View.VISIBLE);
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		mPlWebview.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				// 多点触摸
				case MotionEvent.ACTION_POINTER_DOWN:
					onPointerDown(event);
					break;

				case MotionEvent.ACTION_MOVE:
					onTouchMove(event);
					break;
				// 多点松开
				case MotionEvent.ACTION_POINTER_UP:
					mode = MODE.NONE;
					/** 执行缩放还原 **/
//					if (isScaleAnim) {
//						doScaleAnim();
//					}
					break;
				}
				return false;
			}
		});
		return super.dispatchTouchEvent(ev);
	}
}
