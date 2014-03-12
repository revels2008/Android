/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * SettingSrviceAgreement.java
 * 
 * 2013年9月16日-下午1:19:12
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.ui.base.BaseActivity;

/**
 * 
 * SettingSrviceAgreement
 * 
 * @author: Liang Wang 2013年9月16日-下午1:19:12
 * 
 * @version 1.0.0
 * 
 */
public class SettingServiceAgreementActivity extends BaseActivity implements
		OnClickListener {

	private ImageView iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_serviceagreement);
		InitView();
	}

	protected void InitView() {
		iv_back = (ImageView) this.findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		default:
			break;
		}

	}

}
