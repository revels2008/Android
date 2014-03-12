/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * CartItemEntity.java
 * 
 * 2013年9月16日-上午9:35:49
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

/**
 * 
 * CartItemEntity
 * 
 * @author: YangCheng Miao
 * 2013年9月16日-上午9:35:49
 * 
 * @version 1.0.0
 * 
 */
public class CartItemEntity {
	int itemId;
	int buyNum;
	int plPrice;
	Boolean isChecked;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	public int getPlPrice() {
		return plPrice;
	}
	public void setPlPrice(int plPrice) {
		this.plPrice = plPrice;
	}
	public Boolean getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	
}
