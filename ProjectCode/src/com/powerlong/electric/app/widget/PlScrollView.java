/**
 * 宝龙电商
 * com.powerlong.electric.app.widget
 * PlScrollView.java
 * 
 * 2013-11-28-下午2:37:29
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;

/**
 * 
 * PlScrollView
 * 
 * @author: Liang Wang
 * 2013-11-28-下午2:37:29
 * 
 * @version 1.0.0
 * 
 */
public class PlScrollView extends ScrollView {
	
	/**
	 * 创建一个新的实例 PlScrollView.
	 *
	 * @param context
	 */
	public PlScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	

	
	/**
	 * 创建一个新的实例 PlScrollView.
	 *
	 * @param context
	 * @param attrs
	 */
	public PlScrollView(Context context, AttributeSet attrs) {
		 super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.widget.ScrollView#requestChildFocus(android.view.View, android.view.View)
	 */
	@Override
	public void requestChildFocus(View child, View focused) {
		// TODO Auto-generated method stub
//		if(focused instanceof PlWebView){
//			return;
//		}
		super.requestChildFocus(child, focused);
	}
	
	
	    
	    
}
