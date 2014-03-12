/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * NotificationActivity.java
 * 
 * 2013年9月7日-上午10:54:31
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
import com.powerlong.electric.app.ui.base.BaseActivity;

/**
 * 
 * NotificationActivity
 * 
 * @author: YangCheng Miao
 * 2013年9月7日-上午10:54:31
 * 
 * @version 1.0.0
 * 
 */
public class NotificationActivity extends BaseActivity implements OnClickListener {
	ImageView ivReturn;
	ImageView ivEdit;
	TextView tvTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_notification);
		
		findView();
	}
	
	private void findView() {
		ivReturn = (ImageView) findViewById(R.id.ivBack);
		ivReturn.setOnClickListener(this);
		ivEdit = (ImageView) findViewById(R.id.ivEdit);
		ivEdit.setOnClickListener(this);
		ivEdit.setVisibility(View.VISIBLE);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText("通知");
	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
			finish();
			break;

		default:
			break;
		}
	}

}
