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

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
public class EditNickNameActivity extends BaseActivity implements OnClickListener  {
	private ImageView ivBack = null;
	private TextView  tvTitle = null; 
	private ImageView ivConfirm = null;
	private ImageView ivConfirmDisable = null;
	private EditText  evNickName = null;
	private String nickName;
	SharedPreferences prefs;
	Editor editor;
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("EditNickNameActivity", "msg.what = "+msg.what);
			LogUtil.d("EditNickNameActivity", "msg.arg1 = "+msg.arg1);
			dismissLoadingDialog();
			String msg1 = (String)msg.obj;
			switch(msg.what){
			case Constants.HttpStatus.SUCCESS:
				showCustomToast("修改成功");
				nickName = evNickName.getEditableText().toString();
				Constants.nickName = nickName;
				Intent data = new Intent();
				data.putExtra("nickName", nickName);
				setResult(0, data);
				editor.putString("nickName", nickName);
				editor.commit();//提交修改
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_edit_nickname);
		
		Intent intent = getIntent();
		nickName = intent.getStringExtra("nickname");
		LogUtil.d("EditNickNameActivity", "nickname ="+nickName);
		
		ivBack =(ImageView)findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(R.string.profile_edit_nickname);
		ivConfirm = (ImageView)findViewById(R.id.ivConfirm);
		ivConfirm.setOnClickListener(this);
		ivConfirmDisable = (ImageView) findViewById(R.id.ivConfirmDisable);
		ivConfirmDisable.setEnabled(false);
		evNickName = (EditText)findViewById(R.id.evNickName);
		if(!StringUtil.isEmpty(nickName))
			evNickName.setText(nickName);
		
		evNickName.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String content = evNickName.getText().toString().trim();
				if (content != null && !"".equals(content)) {
					ivConfirm.setVisibility(View.VISIBLE);
					ivConfirmDisable.setVisibility(View.GONE);
				} else {
					ivConfirm.setVisibility(View.GONE);
					ivConfirmDisable.setVisibility(View.VISIBLE);
				}
				
			}
		});
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		editor = prefs.edit();//获取编辑器
		
	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.ivBack:
			Intent data = new Intent();
			data.putExtra("nickName", nickName);
			setResult(0, data);
			editor.putString("nickName", "");
			editor.commit();//提交修改
			finish();
			break;
		case R.id.ivConfirm:
			String nickname = evNickName.getEditableText().toString();
			if(StringUtil.isEmpty(nickname)){
				showCustomToast("抱歉,昵称不能为空,请检查后重新输入");
				return;
			}
//			showLongToast(null);
			JSONObject obj = new JSONObject();
			try {
				obj.put("mall", Constants.mallId);
				obj.put("newNickname", nickname);
				obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
				HttpUtil.requestEditProfile(getBaseContext(), obj.toString(), mServerConnectionHandler);
			} catch (JSONException e) {
				dismissLoadingDialog();
				e.printStackTrace();
			}
			break;
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
