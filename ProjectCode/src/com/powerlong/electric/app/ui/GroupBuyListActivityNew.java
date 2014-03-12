/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * GrouponListNewActivity.java
 * 
 * 2013-10-31-下午05:10:20
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.powerlong.electric.app.entity.GrouponItemEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.adapter.AdapterGroupBuyList;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.JSONParser;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.NotificatinUtils;
import com.powerlong.electric.app.utils.StringUtil;

/**
 * 
 * GrouponListNewActivity
 * 
 * @author: hegao 2013-10-31-下午05:10:20
 * 
 * @version 1.0.0
 * 
 */
public class GroupBuyListActivityNew extends BaseActivity implements
		OnClickListener {
	private Context context;
	private PullToRefreshListView listView;
	private AdapterGroupBuyList<DomainGroupBuyItem> adapterGroupBuyList;

	private String method;
	private String methodParams;
	private ImageView ivReturn;
	private TextView txTitle;
	private LinearLayout llTabItem1, llTabItem2, llTabItem3, llTabItem4;
	private TextView tvSortItem1, tvSortItem2, tvSortItem3, tvSortItem4;
	private int curPage = 0;
	private int curType = 0;// 0：最新；1：美食；2：娱乐；3：购物；

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_groupon);
		initService();
		initView();
		initData();
	}

	/**
	 * initService(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void initService() {
		context = this;
		Intent intent = getIntent();
		method = intent.getStringExtra("method");
		methodParams = intent.getStringExtra("methodParams");
		adapterGroupBuyList = new AdapterGroupBuyList<DomainGroupBuyItem>(
				context);
	}

	/**
	 * initView(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void initView() {
		listView = (PullToRefreshListView) findViewById(R.id.lv_groupon_detail);
		listView.setAdapter(adapterGroupBuyList);
		listView.setMode(Mode.BOTH);
		txTitle = (TextView) findViewById(R.id.txTitle);
		txTitle.setText("团购");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(this);
		tvSortItem1 = (TextView) findViewById(R.id.tv_sort_item1);
		tvSortItem1.setText("最新");
		tvSortItem2 = (TextView) findViewById(R.id.tv_sort_item2);
		tvSortItem2.setText("美食");
		tvSortItem3 = (TextView) findViewById(R.id.tv_sort_item3);
		tvSortItem3.setText("娱乐");
		tvSortItem3.setCompoundDrawables(null, null, null, null);
		tvSortItem4 = (TextView) findViewById(R.id.tv_sort_item4);
		tvSortItem4.setText("购物");
		llTabItem1 = (LinearLayout) findViewById(R.id.ll_tab_item1);
		llTabItem1.setOnClickListener(this);
		llTabItem2 = (LinearLayout) findViewById(R.id.ll_tab_item2);
		llTabItem2.setOnClickListener(this);
		llTabItem3 = (LinearLayout) findViewById(R.id.ll_tab_item3);
		llTabItem3.setOnClickListener(this);
		llTabItem4 = (LinearLayout) findViewById(R.id.ll_tab_item4);
		llTabItem4.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				DomainGroupBuyItem entity = adapterGroupBuyList.getList().get(
						arg2 - 1);
				Intent intent = new Intent(GroupBuyListActivityNew.this,
						GrouponDetailActivity.class);
				intent.putExtra("groupId", Long.parseLong(entity.getId()));
				startActivity(intent);
			}
		});
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
				adapterGroupBuyList.getList().clear();
				adapterGroupBuyList.notifyDataSetChanged();
				getData();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				if(curPage <= 1){
				curPage = 2;
				}else{
					curPage++;
				}
				getNewData();
			}
		});
	}

	/**
	 * initData(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void initData() {
		getData();
	}

	/**
	 * getData(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void getData() {
		showLoadingDialog(null);
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpPost httpPost = null;
				try {
					JSONObject obj = new JSONObject();
					obj.put("page", curPage + "");
					obj.put("mall", Constants.mallId + "");
					obj.put("type", curType + "");
					String param = obj.toString();
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

					nameValuePair.add(new BasicNameValuePair("data", param));
					httpPost = new HttpPost(Constants.ServerUrl.GET_GROUPON_LIST_URL);
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
//						LogUtil.d("HttpUtil", "getGroupListJson result= "
//								+ result);
//						if (!checkResultCode(result, handler)) {
//							return;
//						}
//						boolean ret = JSONParser.parseGroupList(result);
//						checkParserStatus(ret, handler);
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
					
//					DataUtil.getGrouponListData(getBaseContext(),
//							mServerConnectionHandler, obj.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		
//		
//		try {
//			FinalHttp finalHttp = new FinalHttp();
//			JSONObject jsObj = new JSONObject();
//			jsObj.put("page", curPage + "");
//			jsObj.put("mall", Constants.mallId + "");
//			jsObj.put("type", curType + "");
//			AjaxParams params = new AjaxParams();
//			params.put("data", jsObj.toString());
//			String url = Constants.ServerUrl.GET_GROUPON_LIST_URL;
//			finalHttp.get(url.substring(0, url.length() - 1), params,
//					new AjaxCallBack<String>() {
//						@Override
//						public void onFailure(Throwable t, int errorNo,
//								String strMsg) {
//							if (t != null && strMsg != null) {
//								Toast.makeText(context, strMsg,
//										Toast.LENGTH_SHORT).show();
//							}
//						}
//
//						@Override
//						public void onSuccess(String t) {
//							try {
//								JSONObject jsObj = new JSONObject(t);
//								if (0 != jsObj.getInt("code")) {
//									NotificatinUtils.showCustomToast(context,
//											jsObj.getString("msg"));
//								} else {
//									LogUtil.v("", jsObj.toString());
//									List<DomainGroupBuyItem> list = DomainGroupBuyItem
//											.convertJsonToGroupBuyItemList(
//													context,
//													jsObj.getJSONObject("data")
//															.getString(
//																	"grouponList"));
//									adapterGroupBuyList.getList().addAll(list);
//									adapterGroupBuyList.notifyDataSetChanged();
//								}
//							} catch (Exception e) {
//								NotificatinUtils.showCustomToast(context,
//										e.getLocalizedMessage());
//							} finally {
//								dismissLoadingDialog();
//								listView.onRefreshComplete();
//							}
//						}
//					});
//		} catch (Exception e) {
//			NotificatinUtils.showCustomToast(context, e.getLocalizedMessage());
//		}
	}

	private void getNewData() {
		showLoadingDialog(null);
		try {
			FinalHttp finalHttp = new FinalHttp();
			JSONObject jsObj = new JSONObject();
			jsObj.put("page", curPage + "");
			jsObj.put("mall", Constants.mallId + "");
			jsObj.put("type", curType + "");
			AjaxParams params = new AjaxParams();
			params.put("data", jsObj.toString());
			String url = Constants.ServerUrl.GET_GROUPON_LIST_URL;
			finalHttp.get(url.substring(0, url.length() - 1), params,
					new AjaxCallBack<String>() {
						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							if (t != null && strMsg != null) {
								Toast.makeText(context, strMsg,
										Toast.LENGTH_SHORT).show();
							}
						}

						@Override
						public void onSuccess(String t) {
							try {
								JSONObject jsObj = new JSONObject(t);
								if (0 != jsObj.getInt("code")) {
									// NotificatinUtils.showCustomToast(context,
									// jsObj.getString("msg"));
								} else {
									List<DomainGroupBuyItem> list = DomainGroupBuyItem
											.convertJsonToGroupBuyItemList(
													context,
													jsObj.getJSONObject("data")
															.getString(
																	"grouponList"));
									adapterGroupBuyList.getList().addAll(list);
									adapterGroupBuyList.notifyDataSetChanged();
								}
							} catch (Exception e) {
								NotificatinUtils.showCustomToast(context,
										e.getLocalizedMessage());
							} finally {
								dismissLoadingDialog();
								listView.onRefreshComplete();
							}
						}
					});
		} catch (Exception e) {
			NotificatinUtils.showCustomToast(context, e.getLocalizedMessage());
		}
	}

	/*
	 * 处理注册到监听器上的控件的点击事件。
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ivReturn:
			finish();
			break;
		case R.id.ll_tab_item1:
			llTabItem1.setBackgroundResource(R.drawable.tab_chris1_press);
			tvSortItem1.setTextColor(getResources().getColor(R.color.chris_text_press));
			llTabItem2.setBackgroundResource(R.drawable.tab_chris_common2_nor);
			tvSortItem2.setTextColor(getResources().getColor(R.color.chris_text_default));
			llTabItem3.setBackgroundResource(R.drawable.tab_chris_common3_nor);
			tvSortItem3.setTextColor(getResources().getColor(R.color.chris_text_default));
			llTabItem4.setBackgroundResource(R.drawable.tab_chris_common4_nor);
			tvSortItem4.setTextColor(getResources().getColor(R.color.chris_text_default));
			if (curType != 0) {
				curType = 0;
				curPage = 1;
				adapterGroupBuyList.getList().clear();
				adapterGroupBuyList.notifyDataSetChanged();
				getData();
			}
			break;
		case R.id.ll_tab_item2:
			llTabItem1.setBackgroundResource(R.drawable.tab_chris_common1_nor);
			tvSortItem1.setTextColor(getResources().getColor(R.color.chris_text_default));
			llTabItem2.setBackgroundResource(R.drawable.tab_chris2_press);
			tvSortItem2.setTextColor(getResources().getColor(R.color.chris_text_press));
			llTabItem3.setBackgroundResource(R.drawable.tab_chris_common3_nor);
			tvSortItem3.setTextColor(getResources().getColor(R.color.chris_text_default));
			llTabItem4.setBackgroundResource(R.drawable.tab_chris_common4_nor);
			tvSortItem4.setTextColor(getResources().getColor(R.color.chris_text_default));
			if (curType != 1) {
				curType = 1;
				curPage = 1;
				adapterGroupBuyList.getList().clear();
				adapterGroupBuyList.notifyDataSetChanged();
				getData();
			}
			break;
		case R.id.ll_tab_item3:
			llTabItem1.setBackgroundResource(R.drawable.tab_chris_common1_nor);
			tvSortItem1.setTextColor(getResources().getColor(R.color.chris_text_default));
			llTabItem2.setBackgroundResource(R.drawable.tab_chris_common2_nor);
			tvSortItem2.setTextColor(getResources().getColor(R.color.chris_text_default));
			llTabItem3.setBackgroundResource(R.drawable.tab_chris3_press);
			tvSortItem3.setTextColor(getResources().getColor(R.color.chris_text_press));
			llTabItem4.setBackgroundResource(R.drawable.tab_chris_common4_nor);
			tvSortItem4.setTextColor(getResources().getColor(R.color.chris_text_default));
			if (curType != 2) {
				curType = 2;
				curPage = 1;
				adapterGroupBuyList.getList().clear();
				adapterGroupBuyList.notifyDataSetChanged();
				getData();
			}
			break;
		case R.id.ll_tab_item4:
			llTabItem1.setBackgroundResource(R.drawable.tab_chris_common1_nor);
			tvSortItem1.setTextColor(getResources().getColor(R.color.chris_text_default));
			llTabItem2.setBackgroundResource(R.drawable.tab_chris_common2_nor);
			tvSortItem2.setTextColor(getResources().getColor(R.color.chris_text_default));
			llTabItem3.setBackgroundResource(R.drawable.tab_chris_common3_nor);
			tvSortItem3.setTextColor(getResources().getColor(R.color.chris_text_default));
			llTabItem4.setBackgroundResource(R.drawable.tab_chris4_press);
			tvSortItem4.setTextColor(getResources().getColor(R.color.chris_text_press));
			if (curType != 3) {
				curType = 3;
				curPage = 1;
				adapterGroupBuyList.getList().clear();
				adapterGroupBuyList.notifyDataSetChanged();
				getData();
			}
			break;
		}
	}
	
	Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			JSONObject jsObj = (JSONObject)msg.obj;
			List<DomainGroupBuyItem> list = null;
			try {
				list = DomainGroupBuyItem
						.convertJsonToGroupBuyItemList(context,jsObj.getJSONObject("data").getString("grouponList"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LogUtil.v("", jsObj.toString());
			if(adapterGroupBuyList != null && adapterGroupBuyList.getList() != null && list != null){
				adapterGroupBuyList.getList().addAll(list);
				adapterGroupBuyList.notifyDataSetChanged();
			}
			
			dismissLoadingDialog();
			listView.onRefreshComplete();
		};
	};

}
