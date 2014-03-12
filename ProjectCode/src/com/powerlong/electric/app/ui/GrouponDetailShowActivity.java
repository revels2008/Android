/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * GrouponDetailShowActivity.java
 * 
 * 2013-11-29-下午8:49:57
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.GrouponDetailShopEntity;
import com.powerlong.electric.app.entity.GrouponDetailsEntity;
import com.powerlong.electric.app.entity.ImageListEntity;
import com.powerlong.electric.app.entity.ItemGroupListEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil.ILoadImageCallback;
import com.powerlong.electric.app.widget.PlWebView;

/**
 * 
 * GrouponDetailShowActivity
 * 
 * @author: Liang Wang 2013-11-29-下午8:49:57
 * 
 * @version 1.0.0
 * 
 */
public class GrouponDetailShowActivity extends BaseActivity {

	private PlWebView webView;
	private ImageView ivReturn;
	private String content = "";
	private long groupId = 1;
	long defaultValue = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.powerlong.electric.app.ui.base.BaseActivity#onCreate(android.os.Bundle
	 * )
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupon_detail_show);
		webView = (PlWebView) findViewById(R.id.webview_show);
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		content = getIntent().getStringExtra("content");
		groupId = getIntent().getLongExtra("groupId", defaultValue);
		if (content != null && content != "") {
			content = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html xmlns='http://www.w3.org/1999/xhtml'><head><title>宝龙最团购</title><meta name='viewport' content='width=device-width, initial-scale=1.0, user-scalable=yes' /><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/><style>body,p{margin:0; padding:0;}p{text-align:center;}</style></head><body>"
					+ content + "</body></html>";
			// webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
			// webView.getSettings().setJavaScriptEnabled(true);
			// webView.getSettings().setUseWideViewPort(true);
			// webView.getSettings().setLoadWithOverviewMode(true);
			webView.loadDataWithBaseURL(null, content, "text/html", "utf-8",
					null);
		} else {
			showLoadingDialog(null);
			DataUtil.getGrouponDetailsData(this, groupId,
					mServerConnectionHandler);
		}
		ivReturn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("GrouponDetailActivity", "msg.what = " + msg.what);
			LogUtil.d("GrouponDetailActivity", "msg.arg1 = " + msg.arg1);

			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				ArrayList<GrouponDetailsEntity> grouponEntities = DataCache.GrouponDetailCache;
				GrouponDetailsEntity entity = grouponEntities.get(0);
				content = entity.getContent();
				content = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html xmlns='http://www.w3.org/1999/xhtml'><head><title>宝龙最团购</title><meta name='viewport' content='width=device-width, initial-scale=1.0, user-scalable=yes' /><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/><style>body,p{margin:0; padding:0;}p{text-align:center;}</style></head><body>"
						+ content + "</body></html>";
				// webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
				// webView.getSettings().setJavaScriptEnabled(true);
				// webView.getSettings().setUseWideViewPort(true);
				// webView.getSettings().setLoadWithOverviewMode(true);
				webView.loadDataWithBaseURL(null, content, "text/html",
						"utf-8", null);
				dismissLoadingDialog();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				break;
			}
		}

	};
}
