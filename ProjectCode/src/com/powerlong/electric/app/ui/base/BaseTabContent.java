/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.base
 * BaseTabContent.java
 * 
 * 2013-7-27-下午04:41:03
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.base;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

/**
 * 
 * BaseTabContent
 * 
 * @author: Liang Wang
 * 2013-7-27-下午04:41:03
 * 
 * @version 1.0.0
 * 
 */
public class BaseTabContent implements TabContentFactory{
	private Context mContext;
	
	public BaseTabContent(Context context){
		mContext = context;
	}
	/* (non-Javadoc)
	 * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
	 */
	@Override
	public View createTabContent(String tag) {
		View view = new View(mContext);
		return view;
	}

}
