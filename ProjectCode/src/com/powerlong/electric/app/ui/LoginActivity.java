package com.powerlong.electric.app.ui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.JSONUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.config.Constants.ServerUrl;
import com.powerlong.electric.app.handler.ServerConnectionHandler;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Message;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener {

	EditText etUser;
	EditText etPasswd;
	Button btnLogin;
	Button btnReg;
	CheckBox cbAutoLogin;
	ImageView ivBack;
	String name;
	String password;
	Boolean isAutoLogin = false;
	WifiInfo wifiInfo;
	
	TextView registerTv = null;
	private String url = "username=xxxx&password=xxxx";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_new);
		
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);  
		wifiInfo = wifiManager.getConnectionInfo(); 
		
		ivBack = (ImageView) findViewById(R.id.ivBack); 
		ivBack.setOnClickListener(this);
		
		btnLogin = (Button) findViewById(R.id.btn_log); 
		btnLogin.setOnClickListener(this);
		
		btnReg = (Button) findViewById(R.id.btn_reg); 
		btnReg.setOnClickListener(this);
		
		etUser = (EditText) findViewById(R.id.login_name_et);
		etUser.setText(Constants.logUserName);
		etPasswd = (EditText) findViewById(R.id.login_pwd_et);
		etPasswd.setText(Constants.logUserPassword);
		
		 
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();		
	}

	private String getUserName() {
		return "123";
	}

	private String getPassword() {
		return "123";
	}
	
	/**
	 * 保存用户名密码
	 * 
	 * @param String
	 *            用户名
	 * @param String
	 *            密码
	 */
	public void saveUserData(String name, String pwd)
	{
		SharedPreferences  pref = LoginActivity.this.getSharedPreferences("account_info", MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString("name", name);
		editor.putString("pwd", password);
		editor.putString("userId", Constants.userId+"");
		editor.commit();
	}
	
	private void clearUserData(){
		SharedPreferences  pref = LoginActivity.this.getSharedPreferences("account_info", MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString("name", "");
		editor.putString("pwd", "");
		editor.commit();
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.ivBack:
			finish();
			break;
		case R.id.btn_reg:
			IntentUtil.start_activity(this, RegisterActivity.class);
			finish();
			break;
		case R.id.btn_log:
			showLoadingDialog("正在登录中...");
			String username = etUser.getEditableText().toString().trim();
			String pwd = etPasswd.getEditableText().toString();
			Constants.logUserName = username;
			Constants.logUserPassword = pwd;
			HttpUtil.requestLogin(getBaseContext(),username,pwd,mServerConnectionHandler);
			
			break;
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("LoginActivity", "msg.what = "+msg.what);
			LogUtil.d("LoginActivity", "msg.arg1 = "+msg.arg1);
			dismissLoadingDialog();
			String tips = (String)msg.obj;
			switch(msg.what){
			case Constants.HttpStatus.SUCCESS:
				showCustomToast(tips);
				SharedPreferences  pref = LoginActivity.this.getSharedPreferences("account_info", MODE_PRIVATE);
				Editor editor = pref.edit();
				editor.putString("userId", Constants.userId+"");
				editor.commit();

				if(wifiInfo != null &&  wifiInfo.getSSID()!= null && wifiInfo.getSSID().contains("IPOWERLONG")) {
					HttpUtil.LoginWifi(getLoginWifiParam(), mLoginWifiHandler);
				}
			
				HttpUtil.queryUnReadMsgJson(LoginActivity.this,mServerConnectionHandlerNew2, tips);
				finish();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				showCustomToast(tips);
				break;
			}
		}
	};
	
	private ServerConnectionHandler mServerConnectionHandlerNew2 = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				if(Constants.unReadMessageNum > 0){
					//ToastUtil.showExceptionTips(HomeActivityNew.this, "收到新消息");
				}
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				//ToastUtil.showExceptionTips(HomeActivityNew.this, "连接失败");
				break;
			}
		}
	};
	
	private ServerConnectionHandler mLoginWifiHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("LoginActivity", "msg.what = "+msg.what);
			LogUtil.d("LoginActivity", "msg.arg1 = "+msg.arg1);
			dismissLoadingDialog();
			String tips = (String)msg.obj;
			switch(msg.what){
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
	
	private String intToIp(int i) {     

        return (i & 0xFF ) + "." +     
      ((i >> 8 ) & 0xFF) + "." +     
      ((i >> 16 ) & 0xFF) + "." +     
      ( i >> 24 & 0xFF) ;
   } 


	private String getLoginWifiParam() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
			obj.put("loginname", Constants.logUserName);
			obj.put("password", Constants.logUserPassword);
			obj.put("mac", wifiInfo.getMacAddress());
			obj.put("ssid", "IPOWERLONG");
			obj.put("ip", intToIp(wifiInfo.getIpAddress()));
			return obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}		
	}
	

}
