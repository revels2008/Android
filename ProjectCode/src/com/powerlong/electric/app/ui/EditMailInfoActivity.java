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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
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
 * EditMailInfoActivity   用户修改邮箱视图
 * 
 * @author: Liang Wang
 * 2013-9-5-下午12:50:02
 * 
 * @version 1.0.0
 * 
 */
public class EditMailInfoActivity extends BaseActivity implements OnClickListener  {
	private ImageView ivBack = null;
	private TextView  tvTitle = null; 
	private ImageView ivConfirm = null;
	private EditText  evMail = null;
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("EditMailInfoActivity", "msg.what = "+msg.what);
			LogUtil.d("EditMailInfoActivity", "msg.arg1 = "+msg.arg1);
			dismissLoadingDialog();
			String msg1 = (String)msg.obj;
			switch(msg.what){
			case Constants.HttpStatus.SUCCESS:
				Toast.makeText(EditMailInfoActivity.this, msg1,
						Toast.LENGTH_LONG).show();
				showCustomToast(msg1);
				finish();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				showCustomToast(msg1);
				break;
			}
		}
		
	};
	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_edit_mail_info);
		ivBack =(ImageView)findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(R.string.profile_edit_email_address);
		ivConfirm = (ImageView)findViewById(R.id.ivConfirm);
		ivConfirm.setOnClickListener(this);
		evMail = (EditText)findViewById(R.id.emailEdit);
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
			String email = evMail.getEditableText().toString();
//			if(StringUtil.isEmail(email)){
//				showCustomToast("抱歉,您输入的邮箱不正确,请检查后重新输入");
//				return;
//			}

			JSONObject obj = new JSONObject();
			try {
				obj.put("mall", Constants.mallId);
				obj.put("newEmail", email);
				obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
				HttpUtil.requestEditProfile(getBaseContext(), obj.toString(), mServerConnectionHandler);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		}
	}
}
