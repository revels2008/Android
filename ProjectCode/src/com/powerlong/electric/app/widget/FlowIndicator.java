/**
 * 宝龙电商
 * com.powerlong.electric.app.widget
 * FlowIndicator.java
 * 
 * 2013-8-22-上午09:26:55
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.widget;

import com.powerlong.electric.app.widget.PlBannerLayout.ViewSwitchListener;

/**
 * 
 * FlowIndicator
 * 
 * @author: Liang Wang
 * 2013-8-22-上午09:26:55
 * 
 * @version 1.0.0
 * 
 */
public interface FlowIndicator extends ViewSwitchListener {

	/**
	 * Set the current ViewFlow. This method is called by the ViewFlow when the
	 * FlowIndicator is attached to it.
	 * 
	 * @param view
	 */
	public void setViewFlow(PlBannerLayout view);

	/**
	 * The scroll position has been changed. A FlowIndicator may implement this
	 * method to reflect the current position
	 * 
	 * @param h
	 * @param v
	 * @param oldh
	 * @param oldv
	 */
	public void onScrolled(int h, int v, int oldh, int oldv);
}
