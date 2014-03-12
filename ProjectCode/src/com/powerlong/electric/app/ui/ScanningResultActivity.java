/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ScanningResultActivity.java
 * 
 * 2013年11月11日-下午4:11:14
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
import com.powerlong.electric.app.R.id;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.widget.PlWebView;

/**
 * 
 * ScanningResultActivity
 * 
 * @author: YangCheng Miao
 * 2013年11月11日-下午4:11:14
 * 
 * @version 1.0.0
 * 
 */
public class ScanningResultActivity extends BaseActivity implements OnClickListener {
	private ImageView ivBack;
	private PlWebView mWebView;
	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scanning_result);
		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		findView();
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.loadUrl(url);
		
	}
	
	private void findView() {
		ivBack = (ImageView) findViewById(R.id.iv_scanning_Return);
		ivBack.setOnClickListener(this);
		mWebView = (PlWebView) findViewById(R.id.scanning_webview);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_scanning_Return:
			finish();
			break;

		default:
			break;
		}
		
	} 
}
