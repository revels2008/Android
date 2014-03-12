/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * CommunityActivity.java
 * 
 * 2013年7月30日-下午4:19:50
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import android.os.Bundle;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.ui.base.BaseActivity;

/**
 * 
 * CommunityActivity
 * 
 * @author: YangCheng Miao
 * 2013年7月30日-下午4:19:50
 * 
 * @version 1.0.0
 * 
 */
public class CommunityActivity extends BaseActivity{
	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_community);
	}
}
