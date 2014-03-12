/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * FavourListEntity.java
 * 
 * 2013-9-16-上午09:53:47
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * ItemFavourListEntity 商品收藏列表实体
 * 
 * @author: Liang Wang
 * 2013-9-16-上午09:53:47
 * 
 * @version 1.0.0
 * 
 */
public class ItemFavourListEntity {
	@Column (name="favourId")
	private long favourId;//收藏编号
	@Column (name="itemId")
	private long itemId;//商品编号
	@Column (name="picturePath")
	private String picturePath;//收藏编号
	@Column (name="itemName")
	private String itemName;//商品名称
	@Column (name="listPrice")
	private double listPrice;//原价
	@Column (name="plPrice")
	private double plPrice;//销量
	@Column (name="sellNum")
	private String sellNum;//折后价格
	@Column (name="favourNum")
	private double favourNum;//收藏数
	@Column (name="type")
	private int type;//商品类型  0：普通商品 1：团购
	@Column (name="isChecked")
	private boolean isChecked;//商品类型  0：普通商品 1：团购
	
	/**设置收藏编号
	 * @param favourId the favourId to set
	 */
	public void setFavourId(long favourId) {
		this.favourId = favourId;
	}
	/**
	 * 获取收藏编号
	 *
	 * @return  the favourId
	 * @since   1.0.0
	*/
	
	public long getFavourId() {
		return favourId;
	}
	/**设置商品编号
	 * @param itemId the itemId to set
	 */
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	/**
	 * 获取商品编号
	 *
	 * @return  the itemId
	 * @since   1.0.0
	*/
	
	public long getItemId() {
		return itemId;
	}
	/**设置图片地址
	 * @param picturePath the picturePath to set
	 */
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	/**
	 * 获取图片地址
	 *
	 * @return  the picturePath
	 * @since   1.0.0
	*/
	
	public String getPicturePath() {
		return picturePath;
	}
	/**设置商品名称
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 *获取商品名称
	 *
	 * @return  the itemName
	 * @since   1.0.0
	*/
	
	public String getItemName() {
		return itemName;
	}
	/**设置商品原价
	 * @param listPrice the listPrice to set
	 */
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	/**
	 * 获取商品原价
	 *
	 * @return  the listPrice
	 * @since   1.0.0
	*/
	
	public double getListPrice() {
		return listPrice;
	}
	/**设置商品销量
	 * @param sellNum the sellNum to set
	 */
	public void setSellNum(String sellNum) {
		this.sellNum = sellNum;
	}
	/**
	 * 获取商品销量
	 *
	 * @return  the sellNum
	 * @since   1.0.0
	*/
	
	public String getSellNum() {
		return sellNum;
	}
	/**设置商品折后价格
	 * @param plPrice the plPrice to set
	 */
	public void setPlPrice(double plPrice) {
		this.plPrice = plPrice;
	}
	/**
	 * 获取商品折后价格
	 *
	 * @return  the plPrice
	 * @since   1.0.0
	*/
	
	public double getPlPrice() {
		return plPrice;
	}
	/**设置商品收藏数
	 * @param favourNum the favourNum to set
	 */
	public void setFavourNum(double favourNum) {
		this.favourNum = favourNum;
	}
	/**
	 * 获取商品收藏数
	 *
	 * @return  the favourNum
	 * @since   1.0.0
	*/
	
	public double getFavourNum() {
		return favourNum;
	}
	/**设置商品类型
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * 获取商品类型 0：普通 1：团购
	 *
	 * @return  the type
	 * @since   1.0.0
	*/
	
	public int getType() {
		return type;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	

}
