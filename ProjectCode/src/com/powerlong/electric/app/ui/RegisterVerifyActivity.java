/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * RegisterVerifyActivity.java
 * 
 * 2013-9-6-上午10:44:49
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.CommonUtils;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.ToastUtil;

/**
 * 
 * RegisterVerifyActivity
 * 
 * @author: Liang Wang 2013-9-6-上午10:44:49
 * 
 * @version 1.0.0
 * 
 */
public class RegisterVerifyActivity extends BaseActivity implements
		OnClickListener {
	ImageView ivBack;
	TextView  tvTitleTips = null;
	TextView  tvCountDownTips = null;
	TextView tvNumber = null;
	private EditText etCode;
	Button   btnNext = null;
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("RegisterVerifyActivity", "msg.what = "+msg.what);
			LogUtil.d("RegisterVerifyActivity", "msg.arg1 = "+msg.arg1);
			dismissLoadingDialog();
			String msg1 = (String)msg.obj;
			switch(msg.what){
			case Constants.HttpStatus.SUCCESS:
				if(!isConfirm)
					msg1 = msg1+"验证码为：1234";
//				showCustomToast(msg1);
				if(isConfirm)
					finish();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				showCustomToast(msg1);
				break;
			}
			//mpProgressBar.setVisibility(View.GONE);
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verify_register);

		Intent intent = getIntent();
		String number = intent.getStringExtra("number");
		LogUtil.d("RegisterVerifyActivity", "number ="+number);
		String pwd = intent.getStringExtra("pwd");
		LogUtil.d("RegisterVerifyActivity", "pwd ="+pwd);
		
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		
		etCode = (EditText) findViewById(R.id.code_edit);
		
		tvNumber = (TextView)findViewById(R.id.mobile_num);
		if(null == number) {
			number = "";
		}
		tvNumber.setText(number);
		
		tvTitleTips = (TextView)findViewById(R.id.register_verify_tips);
		tvTitleTips.setText(Html.fromHtml(getResources().getString(R.string.str_register_verify_tips)));
		
		tvCountDownTips = (TextView)findViewById(R.id.count_down_tips);
		String text = String.format(getResources().getString(R.string.str_register_count_down_tips),60);
		tvCountDownTips.setText(text);
		
		btnNext = (Button)findViewById(R.id.ivNext);
		btnNext.setOnClickListener(this);
		
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("mobile", number);
			obj.put("pwd", pwd);
			HttpUtil.getMessageCode(getBaseContext(), mServerConnectionHandler, obj.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	boolean isConfirm =false;
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ivBack:
			finish();
			break;
		case R.id.ivNext:
			isConfirm = true;
			showLoadingDialog(null);
			if(etCode.getText().toString() == null) {
				ToastUtil.showExceptionTips(getBaseContext(), "请输入验证码");
			}
			
			JSONObject obj = new JSONObject();
			try {
				obj.put("mobile", tvNumber.getText());
				obj.put("pwd", "123456");
				obj.put("mall", Constants.mallId);
				obj.put("code",etCode.getText().toString());
				HttpUtil.requestNewRegister(getBaseContext(), obj.toString(), mServerConnectionHandler);
			} catch (JSONException e) {
				dismissLoadingDialog();
				e.printStackTrace();
			}
			break;
		}
	}
}
