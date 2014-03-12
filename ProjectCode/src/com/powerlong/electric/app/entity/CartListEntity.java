/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * CartListEntity.java
 * 
 * 2013-8-28-上午09:23:25
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * CartListEntity 购物车列表实体
 * 
 * @author: Liang Wang
 * 2013-8-28-上午09:23:25
 * 
 * @version 1.0.0
 * 
 */
public class CartListEntity {
	@Column (name ="id")
	private int id;//primary id
	@Column (name ="totalPrice")
	private long totalPrice;//总价
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
	/**设置购物车内商品总价
	 * @param totalPrice 总价
	 */
	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * 获取购物车内商品总价
	 *
	 * @return  总价
	 * @since   1.0.0
	*/
	
	public long getTotalPrice() {
		return totalPrice;
	}
}
