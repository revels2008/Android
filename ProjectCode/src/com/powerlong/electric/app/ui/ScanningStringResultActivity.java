/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ScanningStringResultActivity.java
 * 
 * 2013年11月21日-上午9:28:15
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.ui.base.BaseActivity;

/**
 * 
 * ScanningStringResultActivity
 * 
 * @author: YangCheng Miao
 * 2013年11月21日-上午9:28:15
 * 
 * @version 1.0.0
 * 
 */
public class ScanningStringResultActivity extends BaseActivity implements OnClickListener {
	private ImageView ivReturn;
	private TextView tvResult;
	private String result;
	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scanning_string_result);
		
		Intent intent = getIntent();
		result = intent.getStringExtra("url");
		ivReturn = (ImageView) findViewById(R.id.iv_Return_scanning);
		ivReturn.setOnClickListener(this);
		tvResult = (TextView) findViewById(R.id.scanning_result);
		tvResult.setText(result);
		
	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_Return_scanning:
			finish();
			break;

		default:
			break;
		}
		
	}
	
}
