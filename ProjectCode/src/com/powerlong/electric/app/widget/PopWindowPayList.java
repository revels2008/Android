/**
 * 宝龙电商
 * com.powerlong.electric.app.widget
 * PopWindowPayList.java
 * 
 * 2013-11-2-下午05:27:36
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.widget;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alipay.android.app.sdk.AliPay;
import com.alipay.android.msp.demo.Keys;
import com.alipay.android.msp.demo.Result;
import com.alipay.android.msp.demo.Rsa;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.domain.DomainChannelItem;
import com.powerlong.electric.app.domain.DomainChannelMsg;
import com.powerlong.electric.app.ui.MyGrouponCouponActivity;
import com.powerlong.electric.app.ui.MyOrderActivityNew;
import com.powerlong.electric.app.ui.ShoppingCartSettleAccountActivity;
import com.powerlong.electric.app.ui.adapter.AdapterPayList;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.MD5Utils;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.ToastUtil;
import com.upomp.pay.activity.Star_Upomp_Pay;
import com.upomp.pay.help.CreateOriginal;
import com.upomp.pay.info.Upomp_Pay_Info;
import com.upomp.pay.info.XmlDefinition;

/**
 * 
 * PopWindowPayList
 * 
 * @author: hegao 2013-11-2-下午05:27:36
 * 
 * @version 1.0.0
 * 
 */
public class PopWindowPayList {
	private Context context;
	private LayoutInflater layoutInflater;
	private ListView listView;
	private AdapterPayList<DomainChannelItem> adapterPayList;
	private View mainView;
	private PopupWindow popupWindow;
	private DomainChannelMsg channelMsg;
	private boolean isAllGroupon = false;

	private ProgressDialog mProgress = null;
	boolean mbPaying = false;
	private Activity mActivity = null;
	private Integer lock = 0;
//	private IAlixPay mAlixPay = null;

	private String TAG = "AliPay";
	private String totalName = "商品";
	Star_Upomp_Pay star;

	private static final int RQF_PAY = 1;

	private static final int RQF_LOGIN = 2;

	public PopWindowPayList(Context context, String totalName) {
		this.context = context;
		if (totalName != null && totalName != "") {
			this.totalName = totalName;
		}
		mActivity = (Activity) context;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mainView = layoutInflater.inflate(R.layout.popwindow_paylist, null);

		popupWindow = new PopupWindow(mainView,
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT, true);
		// popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.background_view_rounded_middle));
		popupWindow.setAnimationStyle(R.style.popupwindow_pay_setting_config);
		listView = (ListView) mainView.findViewById(R.id.list_pay_list);
		adapterPayList = new AdapterPayList<DomainChannelItem>(context);
		listView.setAdapter(adapterPayList);
		initEvent();
	}

	public DomainChannelMsg getChannelMsg() {
		return channelMsg;
	}

	public void setChannelMsg(DomainChannelMsg channelMsg) {
		this.channelMsg = channelMsg;
	}

	public void show(View parent) {
		popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
	}

	public void addData(List<DomainChannelItem> list) {
		adapterPayList.addData(list);
	}
	
	public void isAllGroupon(boolean isGroupon) {
		isAllGroupon = isGroupon;
	}

	public void clearData() {
		adapterPayList.clearData();
	}

	/**
	 * initEvent(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void initEvent() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
				final DomainChannelItem channelItem = adapterPayList.getList()
						.get(arg2);

				popupWindow.dismiss();

				Toast.makeText(
						context,
						channelItem.getChannelId() + ":"
								+ channelItem.getName(), Toast.LENGTH_SHORT)
						.show();
				if ("ALIPAY".equals(channelItem.getCode())) {
					// 1.先根据URL:
					// http://{域名}/OCC_GATEWAY_Web/mobile/saveChannel.htm
					// channelId和serialNum 来向服务器通知支付方式。
					
					FinalHttp finalHttp = new FinalHttp();
					JSONObject jsParam = null;
					try {
						jsParam = new JSONObject();
						jsParam.put("channelId", channelItem.getChannelId());
						jsParam.put("serialNum", channelMsg.getSerialNum());
						jsParam.put("channelCode", channelItem.getCode());
						jsParam.put("responseType", "json");
						jsParam.put("appCode", Constants.APPCODE);
						jsParam.put("sign", makeSignString(jsParam));
					} catch (Exception e1) {
						Toast.makeText(context, e1.getLocalizedMessage(),
								Toast.LENGTH_SHORT).show();
						return;
					}
					AjaxParams ajaxParams_channel = new AjaxParams();
					ajaxParams_channel.put("data", jsParam.toString());
					finalHttp.addHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						finalHttp.addHeader("TGC",
								DataCache.UserDataCache.get(0).getTGC());
					}
					finalHttp.addHeader("mac", Constants.mac);
					finalHttp.addHeader("uid", Constants.userId + "");
					finalHttp.addHeader("client", "android");
					finalHttp.addHeader("version", Constants.version);
					LogUtil.v("popWindow url = ",
							Constants.ServerUrl.SAVE_CHANNEL);
					LogUtil.v("popWindow jsParam = ", jsParam.toString());

					finalHttp.post(Constants.ServerUrl.SAVE_CHANNEL,
							ajaxParams_channel, new AjaxCallBack<String>() {
								@Override
								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									if (t != null && strMsg != null) {
										Toast.makeText(context, strMsg,
												Toast.LENGTH_SHORT).show();
									}
								}

								@Override
								public void onSuccess(final String t) {
									new Thread(new Runnable() {
										@Override
										public void run() {
											try {
												JSONObject jsObj = new JSONObject(
														t);
												// 保存channel消息成功。就开始调用支付宝支付。
												if (0 == jsObj.getInt("code")) {
													// 根据订单信息开始进行支付
/*													try {
														
														String sign = jsObj.optString("sign", null);
														String code = jsObj.optString("code", null);
														String msg = jsObj.optString("msg", null);
														String appCode = jsObj.optString("appCode", null);
														String str = "";
														
														if(!StringUtil.isNullOrEmpty(appCode)) {
															str += appCode; 
														}
														if(!StringUtil.isNullOrEmpty(code)) {
															str += code; 
														}
														if(!StringUtil.isNullOrEmpty(msg)) {
															str += msg; 
														}
//														if(!(MD5Utils.getMd5Str(str).equals(sign)))
//															return;
														// prepare the order
														// info.
														// 准备订单信息
														String orderInfo = makeOrderInfo(
																channelMsg,
																channelItem);
														// 这里根据签名方式对订单信息进行签名
														String signType = getSignType();
														String strsign = sign(
																signType,
																orderInfo);
														// 对签名进行编码
														strsign = URLEncoder
																.encode(strsign);
														// 组装好参数
														String info = orderInfo
																+ "&sign="
																+ "\""
																+ strsign
																+ "\"" + "&"
																+ getSignType();
														// start the pay.
														// 调用pay方法进行支付
														MobileSecurePayer msp = new MobileSecurePayer();
														Looper.prepare();
														boolean bRet = msp.pay(
																info, mHandler,
																AlixId.RQF_PAY,
																mActivity);

														if (bRet) {
															// show the progress
															// bar to indicate
															// that we have
															// started
															// paying.
															// 显示“正在支付”进度条
															closeProgress();
															mProgress = BaseHelper
																	.showProgress(
																			context,
																			null,
																			"正在支付",
																			false,
																			true);
														} else {
															Toast.makeText(
																	context,
																	"else",
																	Toast.LENGTH_SHORT)
																	.show();
														}
														
													} catch (Exception ex) {
														Toast.makeText(
																context,
																R.string.remote_call_failed,
																Toast.LENGTH_SHORT)
																.show();
													}*/
													
													try {
														Log.i("ExternalPartner", "onItemClick");
														String info = getNewOrderInfo(arg2);
														String sign = Rsa.sign(info, Keys.PRIVATE);
														sign = URLEncoder.encode(sign);
														info += "&sign=\"" + sign + "\"&" + getSignType();
														Log.i("ExternalPartner", "start pay");
														// start the pay.
														Log.i(TAG, "info = " + info);

														final String orderInfo = info;
														new Thread() {
															public void run() {
																AliPay alipay = new AliPay(mActivity, mHandler);
																
																//设置为沙箱模式，不设置默认为线上环境
																//alipay.setSandBox(true);

																String result = alipay.pay(orderInfo);

																Log.i(TAG, "result = " + result);
																Message msg = new Message();
																msg.what = RQF_PAY;
																msg.obj = result;
																mHandler.sendMessage(msg);
															}
														}.start();

													} catch (Exception ex) {
														ex.printStackTrace();
														Toast.makeText(context, R.string.remote_call_failed,
																Toast.LENGTH_SHORT).show();
													}
												} else {
													Toast.makeText(
															context,
															jsObj.getString("msg"),
															Toast.LENGTH_SHORT)
															.show();
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}).start();
								}
							});
				} else if ("UPOP".equals(channelItem.getCode())) {
					FinalHttp finalHttp = new FinalHttp();
					JSONObject jsParam = null;
					try {
						jsParam = new JSONObject();
						jsParam.put("channelId", channelItem.getChannelId());
						jsParam.put("serialNum", channelMsg.getSerialNum());
						jsParam.put("channelCode", channelItem.getCode());
						jsParam.put("responseType", "json");
						jsParam.put("appCode", Constants.APPCODE);
						jsParam.put("sign", makeSignString(jsParam));
					} catch (Exception e1) {
						Toast.makeText(context, e1.getLocalizedMessage(),
								Toast.LENGTH_SHORT).show();
						return;
					}
					AjaxParams ajaxParams_channel = new AjaxParams();
					ajaxParams_channel.put("data", jsParam.toString());
					finalHttp.addHeader("type", "APP");
					if (DataCache.UserDataCache.size() > 0) {
						finalHttp.addHeader("TGC",
								DataCache.UserDataCache.get(0).getTGC());
					}
					finalHttp.addHeader("mac", Constants.mac);
					finalHttp.addHeader("uid", Constants.userId + "");
					finalHttp.addHeader("client", "android");
					finalHttp.addHeader("version", Constants.version);
					LogUtil.v("popWindow url = ",
							Constants.ServerUrl.SAVE_CHANNEL);
					LogUtil.v("popWindow jsParam = ", jsParam.toString());

					finalHttp.post(Constants.ServerUrl.SAVE_CHANNEL,
							ajaxParams_channel, new AjaxCallBack<String>() {
								@Override
								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									if (t != null && strMsg != null) {
										Toast.makeText(context, strMsg,
												Toast.LENGTH_SHORT).show();
									}
								}

								@Override
								public void onSuccess(final String t) {
									new Thread(new Runnable() {
										@Override
										public void run() {
											try {
												JSONObject jsObj = new JSONObject(
														t);
												// 保存channel消息成功。就开始调用支付宝支付。
												if (0 == jsObj.getInt("code")) {
													// 根据订单信息开始进行支付
													try {
														// prepare the order
														// info.
														// 准备订单信息
														Upomp_Pay_Info.merchantId = jsObj.optString("merchantId", "");
														Upomp_Pay_Info.merchantOrderTime = jsObj.optString("merchantOrderTime", "");
														Upomp_Pay_Info.merchantOrderId = jsObj.optString("merchantOrderId", "");
														String originalsign = jsObj.optString("mobileSign", "");
														LogUtil.d(Upomp_Pay_Info.tag, "merchantId = " + jsObj.optString("merchantId", ""));
														LogUtil.d(Upomp_Pay_Info.tag, "merchantOrderTime = " + jsObj.optString("merchantOrderTime", ""));
														LogUtil.d(Upomp_Pay_Info.tag, "merchantOrderId = " + jsObj.optString("merchantOrderId", ""));
														LogUtil.d(Upomp_Pay_Info.tag, "originalsign = " + originalsign);
														try {

															/*
															 * 创建订单验证的3位原串
															 */
//															Upomp_Pay_Info.originalsign = CreateOriginal
//																	.CreateOriginal_Sign(3);
//															LogUtil.d(Upomp_Pay_Info.tag, "这是订单验证的3位原串===" + "\n"
//																	+ Upomp_Pay_Info.originalsign);

															/*
															 * 生成订单验证报文，向插件提交数据
															 */
															String LanchPay = XmlDefinition.ReturnXml(
																	originalsign, 3);
															LogUtil.d(Upomp_Pay_Info.tag, "这是订单验证报文===" + "\n" + LanchPay);
															/*
															 * 向插件提交xml数据
															 */
															star = new Star_Upomp_Pay();
															star.start_upomp_pay(mActivity, LanchPay);

														} catch (Exception e) {
															Log.d(Upomp_Pay_Info.tag, "Exception is " + e);
														}
														
													} catch (Exception ex) {
														Toast.makeText(
																context,
																R.string.remote_call_failed,
																Toast.LENGTH_SHORT)
																.show();
													}
												} else {
													Toast.makeText(
															context,
															jsObj.getString("msg"),
															Toast.LENGTH_SHORT)
															.show();
												}
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}).start();
								}
							});
				}
			}
		});
	}
	
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Result result = new Result((String) msg.obj);

			switch (msg.what) {
			case RQF_PAY:
			case RQF_LOGIN: {
				Toast.makeText(context, result.getResult(),
						Toast.LENGTH_SHORT).show();

			}
				break;
			default:
				break;
			}
		};
	};
	
	private String getNewOrderInfo(int position) {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(Keys.DEFAULT_PARTNER);
		sb.append("\"&out_trade_no=\"");
		sb.append(getOutTradeNo());
		sb.append("\"&subject=\"");
		sb.append(totalName);
		sb.append("\"&body=\"");
		sb.append("商品描述");
		sb.append("\"&total_fee=\"");
		sb.append(channelMsg.getAmount());
		sb.append("\"&notify_url=\"");

		// 网址需要做URL编码
		sb.append(URLEncoder.encode("http://notify.java.jpxx.org/index.jsp"));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(Keys.DEFAULT_SELLER);

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		sb.append("\"&it_b_pay=\"1m");
		sb.append("\"");

		return new String(sb);
	}

	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String key = format.format(date);

		java.util.Random r = new java.util.Random();
		key += r.nextInt();
		key = key.substring(0, 15);
		Log.d(TAG, "outTradeNo: " + key);
		return key;
	}

	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

/*	private void initListView() {
		ListView lv = (ListView) findViewById(R.id.list_view);
		lv.setAdapter(new ExternalPartnerAdapter());
		lv.setOnItemClickListener(this);
	}*/

/*	private void doLogin() {
		final String orderInfo = getUserInfo();
		new Thread() {
			public void run() {
				String result = new AliPay(ExternalPartner.this, mHandler)
						.pay(orderInfo);

				Log.i(TAG, "result = " + result);
				Message msg = new Message();
				msg.what = RQF_LOGIN;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		}.start();
	}*/

/*	private String getUserInfo() {
		String userId = mUserId.getText().toString();
		return trustLogin(Keys.DEFAULT_PARTNER, userId);

	}*/

	private String trustLogin(String partnerId, String appUserId) {
		StringBuilder sb = new StringBuilder();
		sb.append("app_name=\"mc\"&biz_type=\"trust_login\"&partner=\"");
		sb.append(partnerId);
		Log.d("TAG", "UserID = " + appUserId);
		if (!TextUtils.isEmpty(appUserId)) {
			appUserId = appUserId.replace("\"", "");
			sb.append("\"&app_id=\"");
			sb.append(appUserId);
		}
		sb.append("\"");

		String info = sb.toString();

		// 请求信息签名
		String sign = Rsa.sign(info, Keys.PRIVATE);
		try {
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		info += "&sign=\"" + sign + "\"&" + getSignType();

		return info;
	}

/*	private String getSignType() {
		String getSignType = "sign_type=" + "\"" + "RSA" + "\"";
		return getSignType;
	}*/

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param signType
	 *            签名方式
	 * @param content
	 *            待签名订单信息
	 * @return
	 */
/*	private String sign(String signType, String content) {
		return Rsa.sign(content, PartnerConfig.RSA_PRIVATE);
	}*/

	// 这里接收支付结果，支付宝手机端同步通知
	/*private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				String strRet = (String) msg.obj;

				switch (msg.what) {
				case AlixId.RQF_PAY: {
					//
					closeProgress();

					BaseHelper.log(TAG, strRet);

					// 从通知中获取参数
					try {
						// 获取交易状态，具体状态代码请参看文档
						String memo = "memo=";
						int imemoStart = strRet.indexOf("memo=");
						imemoStart += memo.length();
						int imemoEnd = strRet.indexOf(";result=");
						memo = strRet.substring(imemoStart, imemoEnd);
						// 对通知进行验签
						ResultChecker resultChecker = new ResultChecker(strRet);

						int retVal = resultChecker.checkSign();
						// 返回验签结果以及交易状态
						if (retVal == ResultChecker.RESULT_CHECK_SIGN_FAILED) {
							BaseHelper.showDialog(
									mActivity,
									"提示",
									context.getResources().getString(
											R.string.check_sign_failed),
									android.R.drawable.ic_dialog_alert);
						} else {
							String str = memo;
							if (str.equals("{操作已经取消。}")) {
								Intent intent = new Intent(mActivity,
										MyOrderActivityNew.class);
								intent.putExtra("stat", 0);
								mActivity.startActivity(intent);
							} else {
								// BaseHelper.showDialog(((Activity)context),
								// "提示", memo,
								// R.drawable.infoicon);
								if(isAllGroupon) {
									Intent intent = new Intent(mActivity,
											MyGrouponCouponActivity.class);
									mActivity.startActivity(intent);
									((Activity) context).finish();
								} else {
									Intent intent = new Intent(mActivity,
											MyOrderActivityNew.class);
									intent.putExtra("stat", 1);
									mActivity.startActivity(intent);
									((Activity) context).finish();
								}
								
							}

						}

					} catch (Exception e) {
						e.printStackTrace();

						BaseHelper.showDialog(mActivity, "提示", strRet,
								R.drawable.infoicon);
					}
				}
					break;
				}

				super.handleMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private String makeOrderInfo(DomainChannelMsg channelMsg,
			DomainChannelItem channelItem) {
		// StringBuilder sb = new StringBuilder();
		String strOrderInfo = "partner=" + "\"" + channelItem.getPartnerId()
				+ "\"";
		strOrderInfo += "&";
		strOrderInfo += "seller=" + "\"" + channelItem.getSellerAccount()
				+ "\"";
		strOrderInfo += "&";
		strOrderInfo += "out_trade_no=" + "\"" + channelMsg.getSerialNum()
				+ "\"";
		strOrderInfo += "&";
		strOrderInfo += "subject=" + "\"" + totalName + "\"";
		strOrderInfo += "&";
		strOrderInfo += "body=" + "\"" + "商品描述" + "\"";
		strOrderInfo += "&";
		strOrderInfo += "total_fee=" + "\"" + channelMsg.getAmount() + "\"";
		strOrderInfo += "&";
		strOrderInfo += "notify_url=" + "\"" + channelItem.getNotifyUrl()
				+ "\"";
		return strOrderInfo;
	};

	/**
	 * This implementation is used to receive callbacks from the remote service.
	 * 实现安全支付的回调
	 */
	/*private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() {
		*//**
		 * This is called by the remote service regularly to tell us about new
		 * values. Note that IPC calls are dispatched through a thread pool
		 * running in each process, so the code executing here will NOT be
		 * running in our main thread like most other things -- so, to update
		 * the UI, we need to use a Handler to hop over there. 通过IPC机制启动安全支付服务
		 *//*
		public void startActivity(String packageName, String className,
				int iCallingPid, Bundle bundle) throws RemoteException {
			Intent intent = new Intent(Intent.ACTION_MAIN, null);

			if (bundle == null)
				bundle = new Bundle();
			// else ok.

			try {
				bundle.putInt("CallingPid", iCallingPid);
				intent.putExtras(bundle);
			} catch (Exception e) {
				e.printStackTrace();
			}

			intent.setClassName(packageName, className);
			mActivity.startActivity(intent);
			// ShoppingCartSettleAccountActivity.finish();
		}
	};*/

	// 和安全支付服务建立连接
/*	private ServiceConnection mAlixPayConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder service) {
			//
			// wake up the binder to continue.
			// 获得通信通道
			synchronized (lock) {
				mAlixPay = IAlixPay.Stub.asInterface(service);
				lock.notify();
			}
		}

		public void onServiceDisconnected(ComponentName className) {
			mAlixPay = null;
		}
	};

	*//**
	 * 向支付宝发送支付请求
	 * 
	 * @param strOrderInfo
	 *            订单信息
	 * @param callback
	 *            回调handler
	 * @param myWhat
	 *            回调信息
	 * @param activity
	 *            目标activity
	 * @return
	 *//*
	public boolean pay(final String strOrderInfo, final Handler callback,
			final int myWhat, final Activity activity) {
		if (mbPaying)
			return false;
		mbPaying = true;

		//
		mActivity = activity;

		// bind the service.
		// 绑定服务
		if (mAlixPay == null) {
			// 绑定安全支付服务需要获取上下文环境，
			// 如果绑定不成功使用mActivity.getApplicationContext().bindService
			// 解绑时同理
			mActivity.getApplicationContext().bindService(
					new Intent(IAlixPay.class.getName()), mAlixPayConnection,
					Context.BIND_AUTO_CREATE);
		}
		// else ok.

		// 实例一个线程来进行支付
		new Thread(new Runnable() {
			public void run() {
				try {
					// wait for the service bind operation to completely
					// finished.
					// Note: this is important,otherwise the next mAlixPay.Pay()
					// will fail.
					// 等待安全支付服务绑定操作结束
					// 注意：这里很重要，否则mAlixPay.Pay()方法会失败
					synchronized (lock) {
						if (mAlixPay == null)
							lock.wait();
					}

					// register a Callback for the service.
					// 为安全支付服务注册一个回调
					mAlixPay.registerCallback(mCallback);

					// call the MobileSecurePay service.
					// 调用安全支付服务的pay方法
					String strRet = mAlixPay.Pay(strOrderInfo);
					// BaseHelper.log(TAG, "After Pay: " + strRet);

					// set the flag to indicate that we have finished.
					// unregister the Callback, and unbind the service.
					// 将mbPaying置为false，表示支付结束
					// 移除回调的注册，解绑安全支付服务
					mbPaying = false;
					mAlixPay.unregisterCallback(mCallback);
					mActivity.getApplicationContext().unbindService(
							mAlixPayConnection);

					// send the result back to caller.
					// 发送交易结果
					Message msg = new Message();
					msg.what = myWhat;
					msg.obj = strRet;
					callback.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();

					// send the result back to caller.
					// 发送交易结果
					Message msg = new Message();
					msg.what = myWhat;
					msg.obj = e.toString();
					callback.sendMessage(msg);
				}
			}
		}).start();

		return true;
	}*/

	// 关闭进度框
	private void closeProgress() {
		try {
			if (mProgress != null) {
				mProgress.dismiss();
				mProgress = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String makeSignString(JSONObject jsParam) {
		// amount={value}&appCode={value}&bizCode={value}&bizDescription={value}
		// &bizType={value}&type=1&username={value}&{key}
		// sign =com.plocc.framework.utils.Md5Utility.getMD5String(生成签名的字符串)

		// jsParam.put("appCode", Constants.APPCODE);
		// jsParam.put("bizCode", orderMsg.getOrderNo());
		// jsParam.put("bizType", "1");
		// jsParam.put("bizDescription", "测试用商品");
		// jsParam.put("amount","0.01");
		// jsParam.put("type", "1");
		// jsParam.put("userName",
		// DataCache.UserDataCache.get(0).getUsername());

		StringBuilder sb = new StringBuilder("");
		sb.append("appCode=");
		sb.append(jsParam.optString("appCode", ""));
		sb.append("&channelCode=");
		sb.append(jsParam.optString("channelCode", ""));
		sb.append("&channelId=");
		sb.append(jsParam.optString("channelId", ""));
		sb.append("&responseType=");
		sb.append(jsParam.optString("responseType", ""));

		sb.append("&serialNum=");
		sb.append(jsParam.optString("serialNum", ""));

		sb.append("&");
		sb.append(Constants.KEY);
		LogUtil.v("getChannelMsg befor Md5 = ", sb.toString());
		return MD5Utils.getMd5Str(sb.toString());
	}
	
	
}
