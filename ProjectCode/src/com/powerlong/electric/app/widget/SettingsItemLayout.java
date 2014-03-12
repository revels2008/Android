/**
 * 宝龙电商
 * com.powerlong.electric.app.widget
 * SettingsItemLayout.java
 * 
 * 2013-9-4-下午01:41:36
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * SettingsItemLayout
 * 
 * @author: Liang Wang 2013-9-4-下午01:41:36
 * 
 * @version 1.0.0
 * 
 */
public class SettingsItemLayout extends RelativeLayout {
	public boolean isFunctionOn = true;
	public boolean isSetable = true;

	/**
	 * 创建一个新的实例 SettingsItemLayout.
	 * 
	 * @param context
	 */
	public SettingsItemLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建一个新的实例 SettingsItemLayout.
	 * 
	 * @param context
	 * @param attrs
	 */
	public SettingsItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建一个新的实例 SettingsItemLayout.
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SettingsItemLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 打开/关闭功能
	 * 
	 * @param on
	 *            true:打开；false:关闭
	 */
	public void setIsFunctionOn(boolean on) {
		if (this.isSetable) {
			this.isFunctionOn = on;
			View view = findViewWithTag("settings_SwitchButton");
			if ((view instanceof SwitchButton))
				((SwitchButton) view).setChecked(on);
		}
	}

	/**
	 * 设置item是否可点击
	 * 
	 * @param clickable
	 *            true:可点击；false:不可点击
	 */
	public void setSetable(boolean clickable) {
		this.isSetable = clickable;
		setClickable(clickable);

		View view = findViewWithTag("settings_SwitchButton");
		if ((view instanceof SwitchButton))
			((SwitchButton) view).setEnabled(clickable);
	}

	/**
	 * 
	 * 设置备注文本内容
	 * @param text 文本内容
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void setSubTitle(String text) {
		TextView view = (TextView) findViewWithTag("settings_item_subtitle");
		if (view != null)
			view.setText(text);
	}

	/**
	 * 
	 * 设置备注文本颜色
	 * @param color 色值
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void setSubTitleColor(int color) {
		TextView view = (TextView) findViewWithTag("settings_item_subtitle");
		if (view != null)
			view.setTextColor(color);
	}

	/**
	 * 
	 * 设置标题文本内容
	 * @param text 文本内容
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void setTitle(String text) {
		TextView view = (TextView) findViewWithTag("settings_item_title");
		if (view != null)
			view.setText(text);
	}

	/**
	 * 
	 * 设置标题文本颜色
	 * @param color 色值
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void setTitleColor(int color) {
		TextView view = (TextView) findViewWithTag("settings_item_title");
		if (view != null)
			view.setTextColor(color);
	}
	
	/**
	 * 
	 * 设置标题文本内容
	 * @param text 文本内容
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void setValue(String text) {
		TextView view = (TextView) findViewWithTag("settings_item_value");
		if (view != null)
			view.setText(text);
	}

	/**
	 * 
	 * 设置标题文本颜色
	 * @param color 色值
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void setValueColor(int color) {
		TextView view = (TextView) findViewWithTag("settings_item_value");
		if (view != null)
			view.setTextColor(color);
	}
}
