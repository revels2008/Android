/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * RegisterActivity.java
 * 
 * 2013-9-5-下午08:48:21
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.io.Serializable;
import java.security.acl.Owner;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.adapter.SimpleListDialogAdapter;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.dialog.SimpleListDialog;
import com.powerlong.electric.app.dialog.SimpleListDialog.onSimpleListItemClickListener;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.ToastUtil;
import com.powerlong.electric.app.widget.SwitchButton;

/**
 * 
 * RegisterActivity
 * 
 * @author: Liang Wang
 * 2013-9-5-下午08:48:21
 * 
 * @version 1.0.0
 * 
 */
public class RegisterActivity extends BaseActivity implements OnClickListener, onSimpleListItemClickListener {
	ImageView ivBack;
	Button btnNext;
	Button btnRegByMail = null;
	Button btnRegByMobile = null;
	LinearLayout llRegByMobile = null;
	LinearLayout llRegByMail = null;
	private int mRegisterType = Constants.RegisterType.MOBILE;
	
	EditText evMobile = null;
	EditText evMail = null;
	EditText evPwd = null;
	
	TextView tvSelCountryCode = null;
	TextView tvCountryCode = null;
	
	SwitchButton seePwdSwitch = null;
	
	private SimpleListDialog mSimpleListDialog;
	private String[] mCountryCodes;
	private String mAreaCode = "+86";
	private String mCountryName = "中国";
	String phoneNum1;
	String pwd;
	String editUserName;
	String editPwd;
	String editPhoneNum;
	String editPhonePwd;
	private boolean isPhoneRegister = true;
	private TextView tvAgreement;
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("RegisterActivity", "msg.what = "+msg.what);
			LogUtil.d("RegisterActivity", "msg.arg1 = "+msg.arg1);
			dismissLoadingDialog();
			String msg1 = (String)msg.obj;
			switch(msg.what){
			case Constants.HttpStatus.SUCCESS:
				if(isPhoneRegister) {
					HttpUtil.requestLogin(getBaseContext(),editPhoneNum,editPhonePwd,mServerLogConnectionHandler);
				} else {
					HttpUtil.requestLogin(getBaseContext(),editUserName,editPwd,mServerLogConnectionHandler);
				}
				
				IntentUtil.start_activity(RegisterActivity.this,  HomeActivityNew.class);
				finish();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				break;
			}
			showCustomToast(msg1);
		}
		
	};
	
	
	private ServerConnectionHandler mServerLogConnectionHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("LoginActivity", "msg.what = "+msg.what);
			LogUtil.d("LoginActivity", "msg.arg1 = "+msg.arg1);
			dismissLoadingDialog();
			String tips = (String)msg.obj;
			switch(msg.what){
			case Constants.HttpStatus.SUCCESS:
//				showCustomToast(tips);
				SharedPreferences  pref = RegisterActivity.this.getSharedPreferences("account_info", MODE_PRIVATE);
				Editor editor = pref.edit();
				editor.putString("userId", Constants.userId+"");
				editor.commit();

//				HttpUtil.LoginWifi(getLoginWifiParam(), mLoginWifiHandler);*/
				
				finish();
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
		setContentView(R.layout.register);
		
		ivBack = (ImageView) findViewById(R.id.ivBack); 
		ivBack.setOnClickListener(this);
		
		btnNext = (Button) findViewById(R.id.ivNext);
		btnNext.setOnClickListener(this);
		
		llRegByMail = (LinearLayout)findViewById(R.id.byMailView);
		llRegByMail.setVisibility(View.GONE);
		llRegByMobile = (LinearLayout)findViewById(R.id.byMobileView);
		llRegByMobile.setVisibility(View.VISIBLE);
		
		btnRegByMail = (Button)findViewById(R.id.byMail);
		btnRegByMail.setOnClickListener(this);
		btnRegByMobile = (Button)findViewById(R.id.byMobile);
		btnRegByMobile.setOnClickListener(this);
		btnRegByMobile.setSelected(true);
		
		seePwdSwitch = (SwitchButton)findViewById(R.id.pwd_switch);
		seePwdSwitch.setVisibility(View.GONE);
		seePwdSwitch.setOnClickListener(this);
		
		evMobile = (EditText)findViewById(R.id.phone_edit);
		evMail = (EditText)findViewById(R.id.mail_edit);
		evPwd = (EditText)findViewById(R.id.pwd_edit);
		
		tvSelCountryCode = (TextView)findViewById(R.id.select_region_code);
		tvSelCountryCode.setOnClickListener(this);
		
		tvCountryCode = (TextView)findViewById(R.id.code_editor);
		tvAgreement = (TextView) findViewById(R.id.tv_agreement);
		tvAgreement.setOnClickListener(this);
	}

	
	private ServerConnectionHandler mIsNeedVerifyHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("RegisterActivity", "msg.what = "+msg.what);
			LogUtil.d("RegisterActivity", "msg.arg1 = "+msg.arg1);
			dismissLoadingDialog();
//			String msg1 = (String)msg.obj;
			switch(msg.what){
			case Constants.HttpStatus.SUCCESS:
				int type = (Integer)msg.obj;
				if(type == 0) {
					JSONObject obj = new JSONObject();
					try {
						obj.put("mobile",phoneNum1 );
						obj.put("pwd", pwd);
						obj.put("mall", Constants.mallId);
						HttpUtil.requestNewRegister(getBaseContext(), obj.toString(), mServerConnectionHandler);
					} catch (JSONException e) {
						dismissLoadingDialog();
						e.printStackTrace();
					}
				} else if (type == 1) {
					
					BasicNameValuePair nameValuePair = new BasicNameValuePair("number", phoneNum1);
					BasicNameValuePair nameValuePair1 = new BasicNameValuePair("pwd", pwd);
					IntentUtil.start_activity(RegisterActivity.this,RegisterVerifyActivity.class,nameValuePair,nameValuePair1);
					finish();
				}
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				break;
			}
//			showCustomToast(msg1);
		}
		
	};
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.ivBack:
			finish();
			break;
		case R.id.ivNext:
			if(mRegisterType == Constants.RegisterType.MOBILE){
				phoneNum1 = evMobile.getEditableText().toString();
				if(!StringUtil.isPhoneNumber(phoneNum1)){
					showCustomToast("抱歉,您输入的手机号码不正确,请检查后重新输入");
					return;
				}
				
				pwd = evPwd.getEditableText().toString();
				if(StringUtil.isEmpty(pwd)){
					showCustomToast("抱歉,密码不能为空！");
					return;
				}
				editPhoneNum = phoneNum1;
				editPhonePwd = pwd;
				HttpUtil.isNeedVerify(getBaseContext(),  mIsNeedVerifyHandler);
				isPhoneRegister = true;
				
			}else{
				isPhoneRegister = false;
				String mailText = evMail.getEditableText().toString().trim();
				if(!StringUtil.isEmail(mailText)){
					showCustomToast("抱歉,您输入的邮箱地址不正确,请检查后重新输入");
					return;
				}
				String pwd = evPwd.getEditableText().toString();
				if(StringUtil.isEmail(pwd)){
					showCustomToast("抱歉,密码不能为空！");
					return;
				}
				
				showLoadingDialog(null);
				JSONObject obj = new JSONObject();
				try {
					obj.put("email", mailText);
					obj.put("pwd", pwd);
					obj.put("mall", Constants.mallId);
					editUserName = mailText;
					editPwd = pwd;
					HttpUtil.requestRegister(getBaseContext(), obj.toString(), mServerConnectionHandler);
				} catch (JSONException e) {
					dismissLoadingDialog();
					e.printStackTrace();
				}
			}
			break;
		case R.id.byMail:
			if(mRegisterType == Constants.RegisterType.MAIL){
				return;
			}
			mRegisterType = Constants.RegisterType.MAIL;
			updateSortBar(mRegisterType);
			evPwd.setText("");
			break;
		case R.id.byMobile:
			if(mRegisterType == Constants.RegisterType.MOBILE){
				return;
			}
			mRegisterType = Constants.RegisterType.MOBILE;
			updateSortBar(mRegisterType);
			evPwd.setText("");
			break;
		case R.id.pwd_switch:
			break;
		case R.id.select_region_code:
			mCountryCodes = getResources()
			.getStringArray(R.array.country_codes);
			mSimpleListDialog = new SimpleListDialog(RegisterActivity.this);
			mSimpleListDialog.setTitle("选择国家区号");
			mSimpleListDialog.setTitleLineVisibility(View.GONE);
			mSimpleListDialog.setAdapter(new SimpleListDialogAdapter(
					RegisterActivity.this, mCountryCodes));
			mSimpleListDialog
				.setOnSimpleListItemClickListener(RegisterActivity.this);
			mSimpleListDialog.show();
			break;
		case R.id.tv_agreement:
			IntentUtil.start_activity(RegisterActivity.this, ServiceAgreementActivity.class);
			break;
		}
	}

	/**刷新视图
	 * @param type 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void updateSortBar(int type) {
		boolean ret = (mRegisterType == Constants.RegisterType.MOBILE);
		btnRegByMail.setSelected(ret?false:true);
		llRegByMail.setVisibility(ret?View.GONE:View.VISIBLE);
		//seePwdSwitch.setVisibility(ret?View.GONE:View.VISIBLE);
		btnRegByMobile.setSelected(ret?true:false);
		llRegByMobile.setVisibility(ret?View.VISIBLE:View.GONE);
	}

	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.dialog.SimpleListDialog.onSimpleListItemClickListener#onItemClick(int)
	 */
	@Override
	public void onItemClick(int position) {
		String areaCode = StringUtil.getCountryCodeBracketsInfo(
				mCountryCodes[position], mAreaCode);
		String country = StringUtil.getCountryNameBracketsInfo(
				mCountryCodes[position], mCountryName);
		
		tvSelCountryCode.setText(country+areaCode);
		tvCountryCode.setText(areaCode);
	}
}
