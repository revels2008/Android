/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * ShoppingCartSettleEntity.java
 * 
 * 2013年8月29日-下午7:54:58
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

/**
 * 
 * ShoppingCartSettleEntity
 * 
 * @author: YangCheng Miao
 * 2013年8月29日-下午7:54:58
 * 
 * @version 1.0.0
 * 
 */
public class ShoppingCartSettleEntity {
	String imgUrl;
	String itemName;
	String description;
	String price;
	String count;
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String discription) {
		this.description = discription;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
	/**
	 * 创建一个新的实例 ShoppingCartSettleEntity.
	 *
	 * @param imgUrl
	 * @param itemName
	 * @param discription
	 * @param price
	 * @param count
	 */
	public ShoppingCartSettleEntity(String imgUrl, String itemName,String price, String count) {
		super();
		this.imgUrl = imgUrl;
		this.itemName = itemName;
//		this.description = discription;
		this.price = price;
		this.count = count;
	}
	
	
}
