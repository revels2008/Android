/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.base
 * BaseSlidingFragmentActivity.java
 * 
 * 2013-7-24-上午10:10:06
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.base;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.dialog.FlippingLoadingDialog;
import com.powerlong.electric.app.slidingmenu.SlidingActivityBase;
import com.powerlong.electric.app.slidingmenu.SlidingActivityHelper;
import com.powerlong.electric.app.slidingmenu.SlidingMenu;
import com.powerlong.electric.app.widget.HandyTextView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

/**
 * 
 * BaseSlidingFragmentActivity
 * 
 * @author: Liang Wang
 * 2013-7-24-上午10:10:06
 * 
 * @version 1.0.0
 * 
 */
public class BaseSlidingFragmentActivity extends BaseFragmentActivity implements SlidingActivityBase {

	private SlidingActivityHelper mHelper;
	protected FlippingLoadingDialog mLoadingDialog;
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHelper = new SlidingActivityHelper(this);
		mHelper.onCreate(savedInstanceState);
		mLoadingDialog = new FlippingLoadingDialog(this, "请求提交中");
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 */
	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate(savedInstanceState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#findViewById(int)
	 */
	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v != null)
			return v;
		return mHelper.findViewById(id);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mHelper.onSaveInstanceState(outState);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#setContentView(int)
	 */
	@Override
	public void setContentView(int id) {
		setContentView(getLayoutInflater().inflate(id, null));
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#setContentView(android.view.View)
	 */
	@Override
	public void setContentView(View v) {
		setContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
	 */
	@Override
	public void setContentView(View v, LayoutParams params) {
		super.setContentView(v, params);
		mHelper.registerAboveContentView(v, params);
	}

	/* (non-Javadoc)
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#setBehindContentView(int)
	 */
	public void setBehindContentView(int id) {
		setBehindContentView(getLayoutInflater().inflate(id, null));
	}

	/* (non-Javadoc)
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#setBehindContentView(android.view.View)
	 */
	public void setBehindContentView(View v) {
		setBehindContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/* (non-Javadoc)
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#setBehindContentView(android.view.View, android.view.ViewGroup.LayoutParams)
	 */
	public void setBehindContentView(View v, LayoutParams params) {
		mHelper.setBehindContentView(v, params);
	}
	
	/**
	 * Set the secondary behind view (right menu) content from a layout resource. The resource will be inflated, adding all top-level views
	 * to the behind view.
	 *
	 * @param res the new content
	 */
	public void setSecondaryMenu(int res) {
		mHelper.setSecondaryMenu(getLayoutInflater().inflate(res, null));
	}
	/**
	 * Set the secondary behind view (right menu) content to the given View.
	 *
	 * @param view The desired content to display.
	 */
	public void setSecondaryMenu(View v) {
		mHelper.setSecondaryMenu(v);
		//		mViewBehind.invalidate();
	}
	/* (non-Javadoc)
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#getSlidingMenu()
	 */
	public SlidingMenu getSlidingMenu() {
		return mHelper.getSlidingMenu();
	}

	/* (non-Javadoc)
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#toggle()
	 */
	public void toggle() {
		mHelper.toggle();
	}

	/* (non-Javadoc)
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#showAbove()
	 */
	public void showContent() {
		mHelper.showContent();
	}

	/* (non-Javadoc)
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#showBehind()
	 */
	public void showMenu() {
		mHelper.showMenu();
	}

	/* (non-Javadoc)
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#showSecondaryMenu()
	 */
	public void showSecondaryMenu() {
		mHelper.showSecondaryMenu();
	}

	/* (non-Javadoc)
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#setSlidingActionBarEnabled(boolean)
	 */
	public void setSlidingActionBarEnabled(boolean b) {
		mHelper.setSlidingActionBarEnabled(b);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyUp(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean b = mHelper.onKeyUp(keyCode, event);
		if (b) return b;
		return super.onKeyUp(keyCode, event);
	}
	
	protected void showLoadingDialog(String text) {
		if (text != null) {
			mLoadingDialog.setText(text);
		}
		mLoadingDialog.show();
	}

	protected void dismissLoadingDialog() {
		if (mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}
	}

	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

}
