/**
 * 宝龙电商
 * com.powerlong.electric.app.widget
 * PlWebViewNew.java
 * 
 * 2013-11-29-上午11:34:28
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 
 * PlWebViewNew
 * 
 * @author: Liang Wang
 * 2013-11-29-上午11:34:28
 * 
 * @version 1.0.0
 * 
 */
public class PlWebViewNew extends PlWebView {

	
	/**
	 * 创建一个新的实例 PlWebViewNew.
	 *
	 * @param context
	 * @param attrs
	 */
	public PlWebViewNew(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	/* (non-Javadoc)
	 * @see android.webkit.WebView#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

}
