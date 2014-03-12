/**
 * 宝龙电商
 * com.powerlong.electric.app.widget
 * PlListView.java
 * 
 * 2013年8月30日-上午9:09:28
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ListView;

/**
 * 
 * PlListView
 * 
 * @author: YangCheng Miao 2013年8月30日-上午9:09:28
 * 
 * @version 1.0.0
 * 
 */
public class PlListView extends ListView {
	public PlListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PlListView(Context context) {
		super(context);
	}

	public PlListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}

//public class PlGridView extends GridView {
//	public PlGridView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//	}
//
//	public PlGridView(Context context) {
//		super(context);
//	}
//
//	public PlGridView(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//	}
//
//	@Override
//	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//				MeasureSpec.AT_MOST);
//		super.onMeasure(widthMeasureSpec, expandSpec);
//	}
//}
