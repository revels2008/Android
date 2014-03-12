/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * ChangeItemEntity.java
 * 
 * 2013-8-29-下午07:46:05
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * ChangeItemEntity
 * 
 * @author: Liang Wang
 * 2013-8-29-下午07:46:05
 * 
 * @version 1.0.0
 * 
 */
public class ChangeItemEntity {
	@Column (name ="shopId")
	private long shopId;//店铺编号
	@Column (name ="item_id")
	private long itemId;//商品编号
	@Column (name ="mall")
	private long mallId;//商场编号
	@Column (name ="userId")
	private String userId;//用户编号
	@Column (name ="item_num")
	private int itemNum;//商品数量
	@Column (name ="type")
	private String type;//商品類型 0 普通 1團購
	@Column (name ="cartId")
	private long cartId;//购物车编号
	/**设置店铺编号
	 * @param shopId 店铺编号
	 */
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	/**
	 * 获取店铺编号
	 *
	 * @return  编号
	 * @since   1.0.0
	*/
	
	public long getShopId() {
		return shopId;
	}
	/**设置商品编号
	 * @param itemId 商品编号
	 */
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	/**
	 * 获取商品编号
	 *
	 * @return  商品编号
	 * @since   1.0.0
	*/
	
	public long getItemId() {
		return itemId;
	}
	/**设置商城编号
	 * @param mallId 商城编号
	 */
	public void setMallId(long mallId) {
		this.mallId = mallId;
	}
	/**
	 * 获取商城编号
	 *
	 * @return  商城编号
	 * @since   1.0.0
	*/
	
	public long getMallId() {
		return mallId;
	}
	/**设置用户编号
	 * @param userId 用户编号
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取用户编号
	 *
	 * @return  用户编号
	 * @since   1.0.0
	*/
	
	public String getUserId() {
		return userId;
	}
	/**设置商品数目
	 * @param itemNum 商品数目
	 */
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	/**
	 * 获取商品数目
	 *
	 * @return  商品数目
	 * @since   1.0.0
	*/
	
	public int getItemNum() {
		return itemNum;
	}
	
	/**设商品类型
	 * @param userId 用户编号
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取商品类型
	 *
	 * @return  用户编号
	 * @since   1.0.0
	*/
	
	public String getType() {
		return type;
	}
	/**设置购物车编号
	 * @param cartId the cartId to set
	 */
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}
	/**
	 * 获取购物车编号
	 *
	 * @return  the cartId
	 * @since   1.0.0
	*/
	
	public long getCartId() {
		return cartId;
	}
}
