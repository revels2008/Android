/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * CartItemListEntity.java
 * 
 * 2013-8-28-上午09:55:35
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * CartItemListEntity 购物车商家已选择商品列表实体
 * 
 * @author: Liang Wang
 * 2013-8-28-上午09:55:35
 * 
 * @version 1.0.0
 * 
 */
public class ItemListEntity {
	@Column (name = "id")
	private int id;//primary id
	@Column (name = "itemId")
	private long itemId;//商品编号
	@Column (name = "image")
	private String image;//商品图片地址
	@Column (name = "isGroupon")
	private String isGroupon;//商品类型 0：普通商品 1：团购商品
	@Column (name ="itemName")
	private String itemName;//商品名称
	@Column (name ="plPrice")
	private String plPrice;//商品折后价
	@Column (name ="listPrice")
	private String listPrice;//商品原价
	@Column (name ="storeNum")
	private int storeNum;//商品库存数
	@Column (name ="buyNum")
	private int buyNum;//商品已购买数
	@Column (name ="prop")
	private String prop;//商品规格
	@Column (name ="isChecked")
	private boolean isChecked = false;//是否选中
	@Column (name ="cartId")
	private long cartId;//是否选中
	
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
	/**设置商品编号
	 * @param itemId 商品编号
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
	/**设置商品图片地址
	 * @param image 图片地址
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取商品图片地址
	 *
	 * @return  图片地址
	 * @since   1.0.0
	*/
	
	public String getImage() {
		return image;
	}
	/**设置商品是否团购
	 * @param isGroupon 是否团购
	 */
	public void setIsGroupon(String isGroupon) {
		this.isGroupon = isGroupon;
	}
	/**
	 * 获取商品是否团购
	 *
	 * @return  0：普通商品；1：团购商品
	 * @since   1.0.0
	*/
	
	public String getIsGroupon() {
		return isGroupon;
	}
	/**设置商品名称
	 * @param itemName 商品名称
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * 获取商品名称
	 *
	 * @return  商品名称
	 * @since   1.0.0
	*/
	
	public String getItemName() {
		return itemName;
	}
	/**设置商品折后价
	 * @param plPrice 折后价
	 */
	public void setplPrice(String plPrice) {
		this.plPrice = plPrice;
	}
	/**
	 * 获取商品折后价
	 *
	 * @return  折后价
	 * @since   1.0.0
	*/
	
	public String getplPrice() {
		return plPrice;
	}
	/**设置商品原价
	 * @param listPrice 原价
	 */
	public void setListPrice(String listPrice) {
		this.listPrice = listPrice;
	}
	/**
	 * 获取商品原价
	 *
	 * @return  原价
	 * @since   1.0.0
	*/
	
	public String getListPrice() {
		return listPrice;
	}
	/**设置商品库存数
	 * @param storeNum 库存数
	 */
	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}
	/**
	 * 获取商品库存数
	 *
	 * @return  库存数
	 * @since   1.0.0
	*/
	
	public int getStoreNum() {
		return storeNum;
	}
	/**设置商品已购买数
	 * @param buyNum 购买数
	 */
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	/**
	 * 获取商品购买数
	 *
	 * @return  购买数
	 * @since   1.0.0
	*/
	
	public int getBuyNum() {
		return buyNum;
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
	/**设置商品规格
	 * @param prop the prop to set
	 */
	public void setProp(String prop) {
		this.prop = prop;
	}
	/**
	 * 获取商品规格
	 *
	 * @return  the prop
	 * @since   1.0.0
	*/
	
	public String getProp() {
		return prop;
	}
	public String getPlPrice() {
		return plPrice;
	}
	public void setPlPrice(String plPrice) {
		this.plPrice = plPrice;
	}
	public long getCartId() {
		return cartId;
	}
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}
	
}
