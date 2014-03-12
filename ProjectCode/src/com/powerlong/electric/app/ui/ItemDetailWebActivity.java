/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * NearbyMapActivity.java
 * 
 * 2013年9月26日-下午8:10:43
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.listener.PlWebViewLoadingListener;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.widget.PlWebView;

/**
 * 
 * NearbyMapActivity
 * 
 * @author: YangCheng Miao 2013年9月26日-下午8:10:43
 * 
 * @version 1.0.0
 * 
 */
public class ItemDetailWebActivity extends BaseActivity implements
		OnClickListener {
	private PlWebView mPlWebview = null;
	private ImageView ivBack;
	private TextView tvTitle;
	private String mUrl = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detail_webview);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		findView();

		Intent intent = getIntent();
		mUrl = intent.getStringExtra("detailUrl");

		init();
		mPlWebview.setWebLoadingListener(new PlWebViewLoadingListener() {

			@Override
			public void onLoadingSucced() {
				dismissLoadingDialog();
				showCustomToast("加载成功");
			}

			@Override
			public void onLoadingFailed() {
				dismissLoadingDialog();
				showCustomToast("加载失败");
			}
		});

	}

	private void findView() {
		mPlWebview = (PlWebView) findViewById(R.id.webview);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		// tvTitle = (TextView) findViewById(R.id.tvTitle);
		// tvTitle.setText("地图");

	}

	protected void init() {
		// showLoadingDialog("正在加载,请稍后...");
		mPlWebview.loadUrl(mUrl);
		// mPlWebview.setFitsSystemWindows(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	/*
	 * (non-Javadoc)
	 * 
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
