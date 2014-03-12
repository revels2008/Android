/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * CashCouponEntity.java
 * 
 * 2013-8-29-下午01:16:44
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * CashCouponEntity 抵用券实体
 * 
 * @author: Liang Wang
 * 2013-8-29-下午01:16:44
 * 
 * @version 1.0.0
 * 
 */
public class CashCouponEntity {
	@Column (name ="id")
	private int id;//primary id
	@Column (name = "couponId")
	private int couponId;//抵用券id
	@Column (name = "name")
	private String name;//抵用券名称
	@Column (name ="price")
	private double price;//金额
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * id
	 *
	 * @return  the id
	 * @since   1.0.0
	*/
	
	public int getId() {
		return id;
	}
	/**设置抵用券编号
	 * @param couponId 抵用券编号
	 */
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	/**
	 * 获取抵用券编号
	 *
	 * @return  编号
	 * @since   1.0.0
	*/
	
	public int getCouponId() {
		return couponId;
	}
	/**设置抵用券名称
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取抵用券名称
	 *
	 * @return  名称
	 * @since   1.0.0
	*/
	
	public String getName() {
		return name;
	}
	/**设置抵用券金额
	 * @param price 金额
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * 获取抵用券金额
	 *
	 * @return  金额
	 * @since   1.0.0
	*/
	
	public double getPrice() {
		return price;
	}
}
