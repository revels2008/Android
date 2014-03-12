/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * NavigationEntity.java
 * 
 * 2013年9月4日-下午3:56:00
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * NavigationEntity
 * 
 * @author: YangCheng Miao
 * 2013年9月4日-下午3:56:00
 * 
 * @version 1.0.0
 * 
 */
public class NavigationEntity {
	ImageView icon;
	TextView name;
	ImageView arrow;
	public ImageView getIcon() {
		return icon;
	}
	public void setIcon(ImageView icon) {
		this.icon = icon;
	}
	public TextView getName() {
		return name;
	}
	public void setName(TextView name) {
		this.name = name;
	}
	public ImageView getArrow() {
		return arrow;
	}
	public void setArrow(ImageView arrow) {
		this.arrow = arrow;
	}
	
	/**
	 * 创建一个新的实例 NavigationEntity.
	 *
	 * @param icon
	 * @param name
	 * @param arrow
	 */
	public NavigationEntity(ImageView icon, TextView name, ImageView arrow) {
		super();
		this.icon = icon;
		this.name = name;
		this.arrow = arrow;
	}
	
	
}
