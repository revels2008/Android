/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * CartBagainListEntity.java
 * 
 * 2013-8-28-上午09:42:19
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * CartBagainListEntity 购物车商家优惠券列表实体
 * 
 * @author: Liang Wang
 * 2013-8-28-上午09:42:19
 * 
 * @version 1.0.0
 * 
 */
public class BarginListEntity {
	@Column (name = "id")
	private int id;//primary id
	@Column (name = "name")
	private String bagainName;//优惠券名字
	@Column (name = "id")
	private long bargainId;//优惠券编号
	@Column (name = "limitTime")
	private String limitTime;//使用时限
	@Column (name = "price")
	private double price;//扣减价格
	@Column (name = "prop")
	private String prop;//规则说明
	@Column (name = "shopName")
	private String shopName;//店铺名称
	@Column (name = "stat")
	private int stat;//抵用券状态：1，已用；2：可用；3：已过期
	
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
	/**设置优惠券名称
	 * @param bagainName 名称
	 */
	public void setBagainName(String bagainName) {
		this.bagainName = bagainName;
	}
	/**
	 * 获取优惠券名称
	 *
	 * @return  名称
	 * @since   1.0.0
	*/
	
	public String getBagainName() {
		return bagainName;
	}
	/**设置抵用券编号
	 * @param bargainId the bargainId to set
	 */
	public void setBargainId(long bargainId) {
		this.bargainId = bargainId;
	}
	/**
	 * 获取抵用券编号
	 *
	 * @return  the bargainId
	 * @since   1.0.0
	*/
	
	public long getBargainId() {
		return bargainId;
	}
	/**设置抵用券使用时限
	 * @param limitTime the limitTime to set
	 */
	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}
	/**
	 * 获取抵用券使用时限
	 *
	 * @return  the limitTime
	 * @since   1.0.0
	*/
	
	public String getLimitTime() {
		return limitTime;
	}
	/**设置抵用券抵扣价格
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * 获取抵用券抵扣价格
	 *
	 * @return  the price
	 * @since   1.0.0
	*/
	
	public double getPrice() {
		return price;
	}
	/**设置抵用券使用条件说明
	 * @param prop the prop to set
	 */
	public void setProp(String prop) {
		this.prop = prop;
	}
	/**
	 * 获取抵用券使用条件说明
	 *
	 * @return  the prop
	 * @since   1.0.0
	*/
	
	public String getProp() {
		return prop;
	}
	/**设置抵用券店铺名称
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	/**
	 * 获取抵用券店铺名称
	 *
	 * @return  the shopName
	 * @since   1.0.0
	*/
	
	public String getShopName() {
		return shopName;
	}
	/**设置抵用券状态
	 * @param stat the stat to set
	 */
	public void setStat(int stat) {
		this.stat = stat;
	}
	/**
	 * 获取抵用券状态：1，已用；2：可用；3：已过期
	 *
	 * @return  the stat
	 * @since   1.0.0
	*/
	
	public int getStat() {
		return stat;
	}
}
