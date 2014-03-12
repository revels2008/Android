/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.base
 * BaseFragmentActivity.java
 * 
 * 2013-7-24-上午10:05:52
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.base;

import com.powerlong.electric.app.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

/**
 * 
 * BaseFragmentActivity
 * 
 * @author: Liang Wang
 * 2013-7-24-上午10:05:52
 * 
 * @version 1.0.0
 * 
 */
public class BaseFragmentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//MobclickAgent.onError(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//MobclickAgent.onResume(this);
	}
	
	public void finish()
	{
		super.finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	public void defaultFinish()
	{
		super.finish();
	}
}
