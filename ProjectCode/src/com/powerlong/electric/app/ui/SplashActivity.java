package com.powerlong.electric.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

/**
 * 
 * @{# SplashActivity.java Create on 2013-11-2 下午9:10:01
 * 
 *     class desc: 启动画面 (1)判断是否是首次加载应用--采取读取SharedPreferences的方法
 *     (2)是，则进入GuideActivity；否，则进入MainActivity (3)3s后执行(2)操作
 * 
 *     <p>
 *     Copyright: Copyright(c) 2013
 *     </p>
 * @Version 1.0
 * @Author <
 * 
 * 
 */
public class SplashActivity extends BaseActivity {
	boolean isFirstIn = false;

	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE = 1001;
	// 延迟3秒
	private static final long SPLASH_DELAY_MILLIS = 1000;

	private static final String SHAREDPREFERENCES_NAME = "first_pref";
	WifiInfo wifiInfo;
	SharedPreferences pref;

	/**
	 * Handler:跳转到不同界面
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_GUIDE:
				goGuide();
				break;
			}
			super.handleMessage(msg);
		}
	};

	private ServerConnectionHandler mLoginWifiHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("LoginActivity", "msg.what = " + msg.what);
			LogUtil.d("LoginActivity", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			String tips = (String) msg.obj;
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				showCustomToast(tips);
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				showCustomToast(tips);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		wifiInfo = wifiManager.getConnectionInfo();
		pref = SplashActivity.this.getSharedPreferences("account_info",
				MODE_PRIVATE);

		// if( wifiInfo.getSSID().contains("IPOWERLONG")) {
		// if(DataUtil.isUserDataExisted(getBaseContext()))
		// {
		// HttpUtil.LoginWifi(getLoginWifiParam(), mLoginWifiHandler);
		// } else {
		// IntentUtil.start_activity(this, LoginActivity.class);
		// }

		// }
		init();
		try {
			PackageManager packageManager = this.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					"cn.testgethandsetinfo", 0);
			String versionName = packageInfo.versionName;
			if (!TextUtils.isEmpty(versionName)) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String intToIp(int i) {

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}

	private String getLoginWifiParam() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
			obj.put("loginname", pref.getString("name", ""));
			obj.put("password", pref.getString("pwd", ""));
			obj.put("mac", wifiInfo.getMacAddress());
			obj.put("ssid", "IPOWERLONG");
			obj.put("ip", intToIp(wifiInfo.getIpAddress()));
			return obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void init() {
		// 读取SharedPreferences中需要的数据
		// 使用SharedPreferences来记录程序的使用次数
		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, MODE_PRIVATE);

		// 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
		isFirstIn = preferences.getBoolean("isFirstIn", true);

		// 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
		if (!isFirstIn) {
			// 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
			mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
		}

	}

	private void goHome() {
		Intent intent = new Intent(SplashActivity.this, HomeActivityNew.class);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}

	private void goGuide() {
		Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}
}