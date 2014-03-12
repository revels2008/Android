/**
 * 宝龙电商
 * com.powerlong.electric.app.widget
 * PlWebview.java
 * 
 * 2013-8-7-上午09:27:25
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.widget;

import com.powerlong.electric.app.listener.PlWebViewLoadingListener;
import com.powerlong.electric.app.utils.LogUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * 
 * PlWebview：网页通用视图
 * 
 * @author: Liang Wang 2013-8-7-上午09:27:25
 * 
 * @version 1.0.0
 * 
 */
public class PlWebView extends WebView {
	private String TAG = PlWebView.class.getName();
	private Context mContext = null;
	//private ProgressDialog mProgressDlg = null;
	private PlWebViewLoadingListener mLoadingListener = null;
	private OnJsInterfaceInvokeListener onJsInterfaceInvokeListener;
	/**
	 * 创建一个新的实例 PlWebview.
	 * 
	 * @param context
	 * @param attrs
	 */
	public PlWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initWebSettings();
		//mProgressDlg = new ProgressDialog(mContext);
	}

	private void initWebSettings() {
		WebSettings webSettings = getSettings();
		//设置字符集编码
		webSettings.setDefaultTextEncodingName("UTF-8");
		// 开启javascript脚本
		webSettings.setJavaScriptEnabled(true);
		//增加js调用java接口
		addJavascriptInterface(new JSInterface(), "javaAndroid");

		// 设置加载进来的页面自适应手机屏幕
		webSettings.setUseWideViewPort(true);

		// 设置显示放大缩小控件
		webSettings.setBuiltInZoomControls(true);
		webSettings.setSupportZoom(true);
//		 webSettings.setDefaultZoom(ZoomDensity.MEDIUM);
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setDefaultZoom(ZoomDensity.FAR);

		
		setWebViewClient(mWebViewClient);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.webkit.WebView#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && canGoBack()) {
			goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private WebViewClient mWebViewClient = new WebViewClient() {
		// 处理页面导航
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.indexOf(".3gp") != -1 || url.indexOf(".mp4") != -1
					|| url.indexOf(".flv") != -1) {
				Intent intent = new Intent("android.intent.action.VIEW",
						Uri.parse(url));
				view.getContext().startActivity(intent);
				return true;
			} else {
				loadUrl(url);
			}
			// 记得消耗掉这个事件。给不知道的朋友再解释一下，
			// Android中返回True的意思就是到此为止吧,事件就会不会冒泡传递了，我们称之为消耗掉
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			LogUtil.w(TAG, "onPageFinished...");
			//mProgressDlg.dismiss();
			if(mLoadingListener!=null)
				mLoadingListener.onLoadingSucced();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			LogUtil.w(TAG, "onPageStarted...");
			//mProgressDlg.show();
		}

		/* (non-Javadoc)
		 * @see android.webkit.WebViewClient#onReceivedError(android.webkit.WebView, int, java.lang.String, java.lang.String)
		 */
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			LogUtil.w(TAG, "onReceivedError...");
			if(mLoadingListener!=null)
				mLoadingListener.onLoadingFailed();
		}
	};
	
	public void setWebLoadingListener(PlWebViewLoadingListener listener){
		mLoadingListener = listener;
	}
	
	class JSInterface{
//		javaAndroid.invoke("param");
		public void invoke(String data){
			if(onJsInterfaceInvokeListener!=null){
				onJsInterfaceInvokeListener.onJsInvoked(data);
			}
		}
	}
	
	public interface OnJsInterfaceInvokeListener{
		public void onJsInvoked(String data);
	}
	
	//设置js调用java监听器
	public void setOnJsInterfaceInvokeListener(
			OnJsInterfaceInvokeListener onJsInterfaceInvokeListener) {
		this.onJsInterfaceInvokeListener = onJsInterfaceInvokeListener;
	}
	
	
	/* (non-Javadoc)
	 * @see android.webkit.WebView#loadDataWithBaseURL(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void loadDataWithBaseURL(String baseUrl, String data,
			String mimeType, String encoding, String historyUrl) {
		super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
	}
	
	
}
