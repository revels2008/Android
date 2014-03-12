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
 * ItemGroupItemListEntity 团购商品列表实体
 * 
 * @author: YangCheng Miao
 * 2013-8-28-上午09:55:35
 * 
 * @version 1.0.0
 * 
 */
public class ItemGroupItemListEntity {
	@Column (name = "id")
	private long id;//primary id	
	@Column (name ="itemName")
	private String itemName;//商品名称
	@Column (name ="plPrice")
	private double plPrice;//商品折后价
	@Column (name ="num")
	private int num;//商品已购买数
	@Column (name ="itemGroupId")
	private long itemGroupId;//是否选中
	@Column (name ="groupStat")
	private int groupStat;//是否开始
	
	/**
	 * @param stat the id to stat
	 */
	public void setGroupStat(int groupStat){
		this.groupStat = groupStat;
	}
	
	/**
	 * stat
	 *
	 * @return  获取团购状态
	 * @since   1.0.0
	*/
	
	public int getGroupStat() {
		return groupStat;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * id
	 *
	 * @return  the id
	 * @since   1.0.0
	*/
	
	public long getId() {
		return id;
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
	
	
	/**设置商品已购买数
	 * @param buyNum 购买数
	 */
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * 获取商品购买数
	 *
	 * @return  购买数
	 * @since   1.0.0
	*/
	
	public int getNum() {
		return num;
	}
	
	public double getPlPrice() {
		return plPrice;
	}
	public void setPlPrice(double plPrice) {
		this.plPrice = plPrice;
	}
	public long getItemGroupId() {
		return itemGroupId;
	}
	public void setItemGrouptId(long itemGroupId) {
		this.itemGroupId = itemGroupId;
	}
	
}
