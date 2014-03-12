/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * TouchTool.java
 * 
 * 2013-9-3-上午09:24:05
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

/**
 * 
 * TouchTool 管理触点位置
 * 
 * @author: Liang Wang
 * 2013-9-3-上午09:24:05
 * 
 * @version 1.0.0
 * 
 */
public class TouchTool {
	private int startX, startY;
	private int endX, endY;
	public TouchTool(int startX, int startY, int endX, int endY) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	public int getScrollX(float dx) {
		int xx = (int) (startX + dx / 2.5F);
		return xx;
	}

	public int getScrollY(float dy) {
		int yy = (int) (startY + dy / 2.5F);
		return yy;
	}
}
