/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * BuyActivity.java
 * 
 * 2013年7月30日-下午4:17:43
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import android.os.Bundle;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.ui.base.BaseActivity;

/**
 * 
 * BuyActivity
 * 
 * @author: YangCheng Miao
 * 2013年7月30日-下午4:17:43
 * 
 * @version 1.0.0
 * 
 */
public class BuyActivity extends BaseActivity{
	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_buy);
	}
}
