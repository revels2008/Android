/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ReturnItemShowsActivity.java
 * 
 * 2013年9月16日-上午11:58:44
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
 * ReturnItemShowsActivity
 * 
 * @author: Liang Wang
 * 2013年9月16日-上午11:58:44
 * 
 * @version 1.0.0
 * 
 */
public class SettingReturnItemShowsActivity extends BaseActivity implements OnClickListener {
	private ImageView ivback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.return_item_shows);
		InitView();
	}
	protected void InitView(){
		ivback=(ImageView) findViewById(R.id.iv_back);
		ivback.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		default:
			break;
		}
	}

}
