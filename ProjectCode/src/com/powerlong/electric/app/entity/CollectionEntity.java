/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * CollectionEntity.java
 * 
 * 2013年8月28日-下午8:20:28
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

/**
 * 
 * CollectionEntity
 * 
 * @author: YangCheng Miao
 * 2013年8月28日-下午8:20:28
 * 
 * @version 1.0.0
 * 
 */
public class CollectionEntity {
	String url;
	String brand;
	String description;
	String price;
	String priceOrg;
	String sales;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPriceOrg() {
		return priceOrg;
	}
	public void setPriceOrg(String priceOrg) {
		this.priceOrg = priceOrg;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	
	/**
	 * 创建一个新的实例 CollectionEntity.
	 *
	 * @param url
	 * @param description
	 * @param price
	 * @param priceOrg
	 * @param sales
	 */
	public CollectionEntity(String url, String brand, String price,
			String priceOrg, String sales) {
		super();
		this.url = url;
		this.brand = brand;
		this.price = price;
		this.priceOrg = priceOrg;
		this.sales = sales;
	}
}
