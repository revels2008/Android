/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * CartShopListEntity.java
 * 
 * 2013-8-28-上午09:31:45
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * CartShopListEntity 购物车内商家列表实体
 * 
 * @author: Liang Wang
 * 2013-8-28-上午09:31:45
 * 
 * @version 1.0.0
 * 
 */
public class CartShopListEntity {
	@Column (name ="id")
	private int id;//primary id
	@Column (name ="shopId")
	private int shopId;//商家编号
	@Column (name ="shopName")
	private String shopName;//商家名称
	@Column (name ="totalNum")
	private int totalNum;//已购买商品总数
	@Column (name ="totalPrice")
	private double totalPrice;//已购买商品总价
	@Column (name ="isChecked")
	private boolean isChecked;//是否选中
	@Column (name ="tel")
	private String tel;//商家电话号码
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
	/**设置商铺编号
	 * @param shopI 商铺编号
	 */
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	/**
	 * 获取商铺编号
	 *
	 * @return  商铺编号
	 * @since   1.0.0
	*/
	
	public int getShopId() {
		return shopId;
	}
	/**设置商店名称
	 * @param shopName 商店名称
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	/**
	 * 获取商店名称
	 *
	 * @return  商店名称
	 * @since   1.0.0
	*/
	
	public String getShopName() {
		return shopName;
	}
	/**设置用户已购买商品总数
	 * @param totalNum 购买总数
	 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	/**
	 * 获取商品已购买总数
	 *
	 * @return  购买总数
	 * @since   1.0.0
	*/
	
	public int getTotalNum() {
		return totalNum;
	}
	/**设置已购买商品总价
	 * @param totalPrice 总价
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * 获取已购买商品总价
	 *
	 * @return  总价
	 * @since   1.0.0
	*/
	
	public double getTotalPrice() {
		return totalPrice;
	}
	/**
	 * @param isChecked the isChecked to set
	 */
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	/**
	 * isChecked
	 *
	 * @return  the isChecked
	 * @since   1.0.0
	*/
	
	public boolean isChecked() {
		return isChecked;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**获取商家电话号码
	 *
	 * @return  the tel
	 * @since   1.0.0
	*/
	
	public String getTel() {
		return tel;
	}
}
