/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * MyOrderActivityNew.java
 * 
 * 2013-11-11-下午03:45:30
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.List;

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
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.config.CustomerHttpClient;
import com.powerlong.electric.app.domain.DomainGroupBuyItem;
import com.powerlong.electric.app.domain.OrderMsgParent;
import com.powerlong.electric.app.ui.adapter.AdapterOrders;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.ToastUtil;

/**
 * 
 * MyOrderActivityNew
 * 
 * @author: He Gao 2013-11-11-下午03:45:30
 * 
 * @version 1.0.0
 * 
 */
public class MyOrderActivityNew extends BaseActivity implements OnClickListener {
	private Context context;
	private AdapterOrders<OrderMsgParent> adapterOrders;
	private PullToRefreshListView listView;
	private ImageView btn_back;
	private LinearLayout llmessage;
	private EditText et_search;
	private ImageView iv_delete;
	private String url = null;
	private String keyword = "";
	private int curPage = 1;
	private int stat = 0;
	private View root;
	private TextView tvTitle;
	private int fromWhere;
	private boolean isRefresh = true;
	private int numOfPage = 1;
	private int orderStatus;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.activity_my_order);
		Intent intent = getIntent();
		stat = intent.getIntExtra("stat", 0);
		fromWhere = intent.getIntExtra("from", 0);

		initService();
		initView();

		getData(stat);
	}

	/**
	 * getData(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void getData(final int stat) {

		showLoadingDialog(null);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {
					JSONObject obj = new JSONObject();
					obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
					obj.put("page", curPage);
					obj.put("mall", Constants.mallId);
					obj.put("keyword", keyword);
					if (stat != 0) {
						obj.put("stat", stat);
						url = Constants.ServerUrl.GET_ORDER_LIST_STAT_URL;
						url = url.substring(0, url.length() - 1);
					} else {
						url = Constants.ServerUrl.GET_ORDER_LIST_URL;
						url = url.substring(0, url.length() - 1);
					}
					String param = obj.toString();
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param));
					httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
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
						Message msg = Message.obtain(mHandler, 0, jsObj);
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

//		FinalHttp finalHttp = new FinalHttp();
//		JSONObject jsonObject = new JSONObject();
//		try {
//			jsonObject.put("TGC", DataCache.UserDataCache.get(0).getTGC());
//			jsonObject.put("page", curPage);
//			jsonObject.put("mall", Constants.mallId);
//			jsonObject.put("keyword", keyword);
//			if (stat != 0) {
//				jsonObject.put("stat", stat);
//				url = Constants.ServerUrl.GET_ORDER_LIST_STAT_URL;
//				url = url.substring(0, url.length() - 1);
//			} else {
//				url = Constants.ServerUrl.GET_ORDER_LIST_URL;
//				url = url.substring(0, url.length() - 1);
//			}
//
//		} catch (JSONException e) {
//			Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT)
//					.show();
//		}
//		AjaxParams ajaxParams = new AjaxParams();
//		ajaxParams.put("data", jsonObject.toString());
//		LogUtil.v("MyOrderActivityNew url", url);
//		LogUtil.v("MyOrderActivityNew ajaxParams", ajaxParams.toString());
//		finalHttp.get(url, ajaxParams, new AjaxCallBack<String>() {
//
//			@Override
//			public void onFailure(Throwable t, int errorNo, String strMsg) {
//				if (t != null && strMsg != null) {
//					Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
//					listView.onRefreshComplete();
//					dismissLoadingDialog();
//				}
//			}
//
//			@Override
//			public void onSuccess(String t) {
//				try {
//					JSONObject jsonObject = new JSONObject(t);
//					// "code": 0,
//					// "msg": "回复消息",
//					// "data": {
//					// "parentOrderList": [
//					if (0 == jsonObject.getInt("code")) {
//						List<OrderMsgParent> list;
//						if (stat == 0) {
//							list = OrderMsgParent
//									.convertJsonToOrderMsgParent(
//											context,
//											jsonObject.getJSONObject("data")
//													.getString(
//															"parentOrderList"),
//											stat);
//						} else {
//							list = OrderMsgParent.convertJsonToOrderMsgParent(
//									context, jsonObject.getJSONObject("data")
//											.getString("OrderList"), stat);
//						}
//
//						adapterOrders.addData(list);
//					}
//				} catch (JSONException e) {
//					Toast.makeText(context, e.getLocalizedMessage(),
//							Toast.LENGTH_SHORT).show();
//				} finally {
//					listView.onRefreshComplete();
//					dismissLoadingDialog();
//				}
//			}
//		});
	}

	private void initView() {
		root = (RelativeLayout) findViewById(R.id.rl_order_root);
		btn_back = (ImageView) findViewById(R.id.iv_back);
		btn_back.setOnClickListener(this);
		llmessage = (LinearLayout) findViewById(R.id.ll_message);

		tvTitle = (TextView) findViewById(R.id.txTitle);
		if (stat == 1) {
			tvTitle.setText("待发货");
		} else if (stat == 2) {
			tvTitle.setText("待收货");
		} else if (stat == 3) {
			tvTitle.setText("已完成");
		} else if(stat == 0) {
			tvTitle.setText("待付款");
		}
		listView = (PullToRefreshListView) findViewById(R.id.lv_order_list);
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				curPage = 1;
				isRefresh = true;
				adapterOrders.clearData();
				getData(stat);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				curPage++;
				isRefresh = false;
				getData(stat);
			}
		});
		listView.setAdapter(adapterOrders);

		et_search = (EditText) findViewById(R.id.et_search);
		et_search.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER
						&& event.getAction() == KeyEvent.ACTION_UP) {
					keyword = et_search.getText().toString();
					// Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
					curPage = 1;
					adapterOrders.clearData();
					showLoadingDialog(null);
					getData(stat);
					return true;
				}
				return false;
			}
		});
		// et_search.seton
		iv_delete = (ImageView) findViewById(R.id.iv_delete);
		iv_delete.setOnClickListener(this);
	}

	private void initService() {
		context = this;
		adapterOrders = new AdapterOrders<OrderMsgParent>(context, root);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			if (fromWhere == 1) {
				finish();
			} else {
				Intent intent = new Intent(MyOrderActivityNew.this,
						HomeActivityNew.class);
				intent.putExtra("mTabId", 1);
				startActivity(intent);
			}
			//

			break;
		case R.id.iv_delete:
			et_search.setText("");
			break;

		default:
			break;
		}
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			JSONObject jsObj = (JSONObject) msg.obj;
			List<OrderMsgParent> list = null;

			try {
				if (stat == 0) {
					LogUtil.d("MyOrderActivityNew ", jsObj.toString());
					list = OrderMsgParent.convertJsonToOrderMsgParent(
							context,
							jsObj.getJSONObject("data").getString(
									"parentOrderList"), stat);
					LogUtil.d("MyOrderActivityNew  result", jsObj.toString());
				} else {
					list = OrderMsgParent.convertJsonToOrderMsgParent(context,
							jsObj.getJSONObject("data").getString("OrderList"),
							stat);
					LogUtil.d("MyOrderActivityNew  result", jsObj.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(list != null){
				adapterOrders.addData(list);
			}else{
				adapterOrders.addData(new ArrayList<OrderMsgParent>());
			}
			if(isRefresh){
				numOfPage = listView.getRefreshableView().getAdapter().getCount();
				listView.getRefreshableView().setSelection(0);
			}else{
				listView.getRefreshableView().setSelection(numOfPage*(curPage-1)-2);
			}
			dismissLoadingDialog();
			listView.onRefreshComplete();
		};
	};
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
        if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getRepeatCount() == 0) {
        	if (fromWhere == 1) {
				finish();
			} else {
				Intent intent = new Intent(MyOrderActivityNew.this,
						HomeActivityNew.class);
				intent.putExtra("mTabId", 1);
				startActivity(intent);
			}
             return true;
         }
         return super.onKeyDown(keyCode, event);
     }
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(resultCode){
		case Constants.ResultType.RESULT_FROM_UPOMP:
			if (data != null) {
				Bundle bundle = data.getExtras();
				byte[] xml = bundle.getByteArray("xml");
				// 自行解析并输出
				String Sxml;
				Intent intent = new Intent(MyOrderActivityNew.this, MyOrderActivityNew.class);
				try {
					Sxml = new String(xml, "utf-8");
					if(Sxml.contains("<respCode>0000</respCode>")){
						if(AdapterOrders.isAllGroupon){
							Intent mIntent = new Intent(MyOrderActivityNew.this,MyGrouponCouponActivity.class);
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
					ToastUtil.showExceptionTips(MyOrderActivityNew.this,"支付失败");
				}

			} else {
				Intent intent = new Intent(MyOrderActivityNew.this, MyOrderActivityNew.class);
				intent.putExtra("stat", 0);
				intent.putExtra("from", 1);
				startActivity(intent);
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
