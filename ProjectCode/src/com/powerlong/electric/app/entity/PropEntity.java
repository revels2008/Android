/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * PropEntity.java
 * 
 * 2013-9-9-下午12:03:29
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * PropEntity 规格实体
 * 
 * @author: Liang Wang
 * 2013-9-9-下午12:03:29
 * 
 * @version 1.0.0
 * 
 */
public class PropEntity {

	@Column(name = "idDefault")
	private int isDefault;//是否默认规格 1:默认
	@Column(name = "isEmpty")
	private int isEmpty;//规格是否为空
	@Column(name = "name")
	private String name;//规格名称
	@Column(name = "order")
	private int order;//规格顺序
	@Column(name = "stockNum")
	private int stockNum;//满足该规格商品库存
	@Column(name = "itemId")
	private int itemId;//商品id
	@Column(name = "isSelected")
	private int isSelected;
	/**设置默认规格
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
	/**
	 * 获取默认规格
	 *
	 * @return  the isDefault
	 * @since   1.0.0
	*/
	
	public int getIsDefault() {
		return isDefault;
	}
	/**设置规格是否为空
	 * @param isEmpty the isEmpty to set
	 */
	public void setIsEmpty(int isEmpty) {
		this.isEmpty = isEmpty;
	}
	/**
	 * 获取规格是否为空
	 *
	 * @return  the isEmpty
	 * @since   1.0.0
	*/
	
	public int getIsEmpty() {
		return isEmpty;
	}
	/**设置规格顺序
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}
	/**
	 * 获取规格顺序
	 *
	 * @return  the order
	 * @since   1.0.0
	*/
	
	public int getOrder() {
		return order;
	}
	/**设置满足该规格商品库存数
	 * @param stockNum the stockNum to set
	 */
	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}
	/**
	 * 获取满足该规格商品库存数
	 *
	 * @return  the stockNum
	 * @since   1.0.0
	*/
	
	public int getStockNum() {
		return stockNum;
	}
	/**设置规格名称
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 *获取规格名称
	 *
	 * @return  the name
	 * @since   1.0.0
	*/
	
	public String getName() {
		return name;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	/**
	 * isSelected
	 *
	 * @return  the isSelected
	 * @since   1.0.0
	 */
	
	public int getIsSelected() {
		return isSelected;
	}
	/**
	 * @param isSelected the isSelected to set
	 */
	public void setIsSelected(int isSelected) {
		this.isSelected = isSelected;
	}
	
}
