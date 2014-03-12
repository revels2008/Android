/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * PlCashCouponEntity.java
 * 
 * 2013-8-29-下午03:34:00
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * PlCashCouponEntity 宝龙抵用券实体
 * 
 * @author: Liang Wang
 * 2013-8-29-下午03:34:00
 * 
 * @version 1.0.0
 * 
 */
public class PlCashCouponEntity {
	@Column (name ="id")
	private int id;//primary id
	@Column (name = "couponId")
	private int couponId;//抵用券id
	@Column (name ="isActive")
	private String isActive;//抵用券是否可用
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
	/**设置抵用券是否可用
	 * @param isActive 是否可用
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	/**
	 * 获取抵用券是否可用
	 *
	 * @return  0：不可用，1：可用
	 * @since   1.0.0
	*/
	
	public String getIsActive() {
		return isActive;
	}
}
