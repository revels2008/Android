/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * EditMailInfoActivity.java
 * 
 * 2013-9-5-下午12:50:02
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.ToastUtil;

/**
 * 
 * EditMobileInfoActivity 用户修改手机号码视图
 * 
 * @author: Liang Wang
 * 2013-9-5-下午12:50:02
 * 
 * @version 1.0.0
 * 
 */
public class EditMobileInfoActivity extends BaseActivity implements OnClickListener  {
	private ImageView ivBack = null;
	private TextView  tvTitle = null; 
	private ImageView ivConfirm = null;
	private EditText  evMobile = null;
	private EditText  evVerify = null;
	private RelativeLayout mProgressBar = null;
	private Button btnGetCode = null;
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("EditMobileInfoActivity", "msg.what = "+msg.what);
			LogUtil.d("EditMobileInfoActivity", "msg.arg1 = "+msg.arg1);
			dismissLoadingDialog();
			String msg1 = (String)msg.obj;
			switch(msg.what){
			case Constants.HttpStatus.SUCCESS:
				if(!isConfirm)
					msg1 = msg1+"验证码为：1234";
				showCustomToast(msg1);
				if(isConfirm)
					finish();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				showCustomToast(msg1);
				break;
			}
		}
		
	};
	
	boolean isConfirm = false;
	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_edit_mobile_info);
		
		mProgressBar = (RelativeLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.waiting_bar_layout, null);
			
		ivBack =(ImageView)findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(R.string.profile_edit_mobile_number);
		ivConfirm = (ImageView)findViewById(R.id.ivConfirm);
		ivConfirm.setOnClickListener(this);
		
		evMobile = (EditText)findViewById(R.id.mobileEdit);
		evVerify = (EditText)findViewById(R.id.inputcode);
		
		btnGetCode = (Button)findViewById(R.id.get_code);
		btnGetCode.setOnClickListener(this);
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
		case R.id.ivConfirm:
			isConfirm = true;
			
			String phoneNum1 = evMobile.getEditableText().toString();
			if(StringUtil.isPhoneNumber(phoneNum1)){
				showCustomToast("抱歉,您输入的手机号码不正确,请检查后重新输入");
				return;
			}
			String code = evVerify.getEditableText().toString();
			if(StringUtil.isEmail(code)){
				showCustomToast("抱歉,验证码不能为空！");
				return;
			}
			
			showLoadingDialog(null);
			JSONObject obj = new JSONObject();
			try {
				obj.put("mall", Constants.mallId);
				obj.put("newMobile", phoneNum1);
				obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
				obj.put("code", "1234");
				HttpUtil.requestEditProfile(getBaseContext(), obj.toString(), mServerConnectionHandler);
			} catch (JSONException e) {
				dismissLoadingDialog();
				e.printStackTrace();
			}
			
			break;
		case R.id.get_code:
			String phoneNum = evMobile.getEditableText().toString();
			if(!StringUtil.isPhoneNumber(phoneNum)){
				showCustomToast("抱歉,您输入的手机号码不正确,请检查后重新输入");
				return;
			}
			showLoadingDialog(null);
			JSONObject obj1 = new JSONObject();
			try {
				obj1.put("mall", Constants.mallId);
				obj1.put("mobile", phoneNum);
				HttpUtil.getMessageCode(getBaseContext(), mServerConnectionHandler, obj1.toString());
			} catch (JSONException e) {
				dismissLoadingDialog();
				e.printStackTrace();
			}
			break;
		}
	}
}
