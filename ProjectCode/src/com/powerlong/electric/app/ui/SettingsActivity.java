/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * SettingsActivity.java
 * 
 * 2013-9-4-下午03:14:06
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import org.apache.http.message.BasicNameValuePair;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.Contacts.Data;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.update.UpdateManager;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.HttpUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.ToastUtil;
import com.powerlong.electric.app.widget.SettingsItemLayout;

/**
 * 
 * SettingsActivity
 * 
 * @author: Liang Wang
 * 2013-9-4-下午03:14:06
 * 
 * @version 1.0.0
 * 
 */
public class SettingsActivity extends BaseActivity implements OnClickListener {
	private ImageView ivBack = null;
	private TextView  tvTitle = null; 
	
	private SettingsItemLayout profileLayout = null;
	private SettingsItemLayout nickLayout = null;
	private SettingsItemLayout helpLayout=null; 
	private SettingsItemLayout agreementLayout=null;
	private SettingsItemLayout aboutusLayout;
	private LinearLayout llLogOut = null;
	private String SECTIONTITLE_TAG = "settings_item_title";
	
	private SettingsItemLayout notifytimeLayout=null;
	private SettingsItemLayout supportcontactsLayout=null;
	private SettingsItemLayout supportscoredLayout=null;
	private SettingsItemLayout aboutupdateLayout=null;
	private static final int  NICK_NAME_EDIT = 0;
	private TextView tvName;
	SharedPreferences prefs;
	Editor editor;
	UpdateManager mUpdateManager;
	private String curVersionName;
	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		ivBack =(ImageView)findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(R.string.str_setting_title);
		tvName = (TextView) findViewById(R.id.tv_nickname);
		
		profileLayout = (SettingsItemLayout)findViewById(R.id.settings_profile);
		profileLayout.setOnClickListener(this);
		nickLayout = (SettingsItemLayout)findViewById(R.id.settings_nickname);
		nickLayout.setOnClickListener(this);
		helpLayout=(SettingsItemLayout)findViewById(R.id.settings_support_help);
		helpLayout.setOnClickListener(this);
		agreementLayout=(SettingsItemLayout)findViewById(R.id.settings_about_agreement);
		agreementLayout.setOnClickListener(this);
		aboutusLayout=(SettingsItemLayout) findViewById(R.id.settings_about_us);
		aboutusLayout.setOnClickListener(this);
		
		notifytimeLayout=(SettingsItemLayout)findViewById(R.id.settings_notify_time);
		supportcontactsLayout=(SettingsItemLayout)findViewById(R.id.settings_support_contacts);
		supportscoredLayout=(SettingsItemLayout)findViewById(R.id.settings_support_scored);
		aboutupdateLayout=(SettingsItemLayout)findViewById(R.id.settings_about_update);
		notifytimeLayout.setOnClickListener(this);
		supportcontactsLayout.setOnClickListener(this);
		supportscoredLayout.setOnClickListener(this);
		aboutupdateLayout.setOnClickListener(this);
		initTitle();
		
		llLogOut = (LinearLayout)findViewById(R.id.ll_logout);
		llLogOut.setOnClickListener(this);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		editor = prefs.edit();
		editor.putString("nickName", "");
		editor.commit();//提交修改
	}
	/**
	 *设置用户昵称和我的账户section的title信息
	 * @exception 
	 * @since  1.0.0
	*/
	private void initTitle() {
		String nickName = DataCache.UserDataCache.get(0).getNickname();
		String username = DataCache.UserDataCache.get(0).getUsername();
		
		nickLayout.setTitle(!StringUtil.isEmpty(nickName) ? nickName
				: getResources().getString(R.string.str_setting_no_value));
		profileLayout.setTitle(!StringUtil.isEmpty(username) ? username
				: getResources().getString(R.string.str_setting_no_value));
	}
	
	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		switch(resultCode){
		case NICK_NAME_EDIT:
			String nickName = data.getStringExtra("nickName");
			tvName.setText(nickName);
			break;
		}
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
		case R.id.settings_profile:
			IntentUtil.start_activity(this, MyProfileActivity.class);
			break;
		case R.id.ll_logout:
			showLoadingDialog("正在注销...");
			Constants.unReadMessageNum = 0;
			Constants.unReadNotifyNum = 0;
			((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
			DataUtil.clearUserData(getBaseContext());
			dismissLoadingDialog();
			finish();
			break;
		case R.id.settings_nickname:
			String nickname = DataCache.UserDataCache.get(0).getNickname();
			BasicNameValuePair nameValuePair = new BasicNameValuePair("nickname", nickname);
			IntentUtil.start_activity(this,EditNickNameActivity.class,nameValuePair);
			break;
		case R.id.settings_support_help:
			IntentUtil.start_activity(this,SettingHelpCenterActivity.class);
			break;
		case R.id.settings_about_agreement:
			IntentUtil.start_activity(this,SettingServiceAgreementActivity.class);
			break;
		case R.id.settings_about_us:
			IntentUtil.start_activity(this,SettingAboutUsActivity.class);
			break;
		case R.id.settings_notify_time:
			IntentUtil.start_activity(this,SettingNotifyTimeAcitvity.class);
			break;
		case R.id.settings_support_contacts:
			IntentUtil.start_activity(this,SettingNotesContactActity.class);
			break;
		case R.id.settings_support_scored:
			IntentUtil.start_activity(this,SettingCommentUs.class);
			break;
		case R.id.settings_about_update:
			//IntentUtil.start_activity(this,SettingVersionsUpdate.class);
			try {
				PackageManager packageManager = SettingsActivity.this.getPackageManager();
				PackageInfo packageInfo = packageManager.getPackageInfo(
						"com.powerlong.electric.app", 0);
				curVersionName = packageInfo.versionName;
				if (!TextUtils.isEmpty(curVersionName)) {

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			mUpdateManager = new UpdateManager(SettingsActivity.this);
			HttpUtil.getVersionCode(SettingsActivity.this, mServerConnectionHandlerNew);
			break;
		}
	}
	
	private ServerConnectionHandler mServerConnectionHandlerNew = new ServerConnectionHandler() {
		@Override
		public void handleMessage(Message msg) {
			// .d("HomeActivity", "msg.what = " + msg.what);
			// .d("HomeActivity", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				//long versionName = Long.parseLong(Integer.toString(msg.arg1));
				LogUtil.d("current remote version", DataCache.versionCode + "");
				if (DataCache.versionCode!= null && curVersionName != null &&!DataCache.versionCode.equalsIgnoreCase(curVersionName)) {
					mUpdateManager.checkUpdateInfo(SettingsActivity.this);
				}else{
					ToastUtil.showExceptionTips(SettingsActivity.this, "当前版本已是最新版本");
				}
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				//String fail = (String) msg.obj;
				//showCustomToast(fail);
				break;
			}
		}

	};
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(!prefs.getString("nickName", "").equals("") && !tvName.getText().toString().equals(prefs.getString("nickName", ""))){
			tvName.setText(prefs.getString("nickName", ""));
		}else{
			
			editor.putString("nickName", tvName.getText().toString());
			editor.commit();//提交修改
		}
		
	};
}
