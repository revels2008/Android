/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * AdveringWebviewActivity.java
 * 
 * 2013年11月27日-下午11:11:51
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
import com.powerlong.electric.app.widget.PlTableView;
import com.powerlong.electric.app.widget.PlWebView;

/**
 * 
 * AdveringWebviewActivity
 * 
 * @author: YangCheng Miao
 * 2013年11月27日-下午11:11:51
 * 
 * @version 1.0.0
 * 
 */
public class AdveringWebviewActivity extends BaseActivity implements OnClickListener {
	private ImageView ivBack;
	private PlWebView webView;
	private TextView tvTitle;
	private String title;
	private String url;
	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advertising_webview);
		
		url = getIntent().getExtras().getString("params");
		title = getIntent().getExtras().getString("adDis");
		initView();
	}
 
	private void initView() {
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		webView = (PlWebView) findViewById(R.id.adv_webview);
		webView.loadUrl(url);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText(title);
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
