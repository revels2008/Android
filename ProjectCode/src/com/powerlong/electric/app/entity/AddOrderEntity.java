/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * OrderDetailEntity.java
 * 
 * 2013-9-18-下午01:29:39
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * OrderDetailEntity
 * 
 * @author: YangCheng Miao 2013-9-18-下午01:29:39
 * 
 * @version 1.0.0
 * 
 */
public class AddOrderEntity {
	@Column(name = "orderNo")
	private String orderNo;// 订单编号
	@Column(name = "pakageNum")
	private int pakageNum;// 包裹数、
	@Column(name = "itemNum")
	private int itemNum;// 商品数量
	@Column(name = "price")
	private double price;// 金额
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getPakageNum() {
		return pakageNum;
	}
	public void setPakageNum(int pakageNum) {
		this.pakageNum = pakageNum;
	}
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}