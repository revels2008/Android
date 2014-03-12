/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * MyProfileActivity.java
 * 
 * 2013-9-5-上午09:50:52
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.widget.SettingsItemLayout;

/**
 * 
 * MyProfileActivity
 * 
 * @author: Liang Wang 2013-9-5-上午09:50:52
 * 
 * @version 1.0.0
 * 
 */
public class MyProfileActivity extends BaseActivity implements OnClickListener {
	private ImageView ivBack = null;
	private TextView tvTitle = null;
	private SettingsItemLayout mailLayout = null;
	private SettingsItemLayout mobileLayout = null;
	private TextView tvMobile;
	private TextView tvEmail;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.powerlong.electric.app.ui.base.BaseActivity#onCreate(android.os.Bundle
	 * )
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_my_profile);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText(R.string.str_setting_profile_tag);
		mailLayout = (SettingsItemLayout) findViewById(R.id.settings_profile_mail);
		mailLayout.setOnClickListener(this);
		mobileLayout = (SettingsItemLayout) findViewById(R.id.settings_profile_mobile);
		mobileLayout.setOnClickListener(this);
		tvMobile = (TextView) findViewById(R.id.mobile_num);
		tvEmail = (TextView) findViewById(R.id.mail_info);
	}
	
	

	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		initValue();
	}



	/**
	 * 设置邮箱和手机内容
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void initValue() {
		String mobile = DataCache.UserDataCache.get(0).getMobile();
		String mail = DataCache.UserDataCache.get(0).getEmail();

//		mobileLayout.setValue(!StringUtil.isEmpty(mobile) ? mobile
//				: getResources().getString(R.string.str_setting_no_value));
//		mailLayout.setValue(!StringUtil.isEmpty(mail) ? mail : getResources()
//				.getString(R.string.str_setting_no_value));
		if(mobile == null || "null".equals(mobile)) {
//			mobileLayout.setValue(getResources().getString(R.string.str_setting_no_value));
			tvMobile.setText(getResources().getString(R.string.str_setting_no_value));
		} else {
			tvMobile.setText(mobile);
		}
		if(mail == null || "null".equals(mail)) {
//			mailLayout.setValue(getResources().getString(R.string.str_setting_no_value));
			tvEmail.setText(getResources().getString(R.string.str_setting_no_value));
		} else {
			tvEmail.setText(mail);
		}
		
	}

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
		case R.id.settings_profile_mail:
			IntentUtil.start_activity(this, EditMailInfoActivity.class);
			break;
		case R.id.settings_profile_mobile:
			IntentUtil.start_activity(this, EditMobileInfoActivity.class);
			break;
		}
	}
}
