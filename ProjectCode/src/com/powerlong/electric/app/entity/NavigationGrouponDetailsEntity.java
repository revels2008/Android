/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * GroupGoodsDetailEntity.java
 * 
 * 2013-8-27-上午11:18:06
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * GroupGoodsDetailEntity  团购商品详情实体
 * 
 * @author: Liang Wang
 * 2013-8-27-上午11:18:06
 * 
 * @version 1.0.0
 * 
 */
public class NavigationGrouponDetailsEntity {
	@Column (name = "self_id")
	private int selfId;//团购商品编号
	@Column (name = "path")
	private String picPath;//商品缩略图
	@Column (name = "type")
	private String type;//团购来源
	@Column (name ="grouponName")
	private String name;//商品名称
	@Column (name = "notice")
	private String notice;//团购说明
	@Column (name ="price")
	private String price;//市场价
	@Column (name ="grouponPrice")
	private double grouponPrice;//团购价
	@Column (name ="saleVolume")
	private double saleVolume;//已购买人数
	/** 设置团购商品编号
	 * @param selfId 商品编号(int)
	 */
	public void setSelfId(int selfId) {
		this.selfId = selfId;
	}
	/**
	 * 获取商品编号
	 *
	 * @return  商品编号(int)
	 * @since   1.0.0
	*/
	
	public int getSelfId() {
		return selfId;
	}
	/**设置商品缩略图地址
	 * @param picPath 图片地址(string)
	 */
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	/**
	 * 获取商品缩略图地址
	 *
	 * @return  图片地址(string)
	 * @since   1.0.0
	*/
	
	public String getPicPath() {
		return picPath;
	}
	/**设置团购来源
	 * @param type 团购来源(string)
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取团购来源
	 *
	 * @return  团购来源(string)
	 * @since   1.0.0
	*/
	
	public String getType() {
		return type;
	}
	/**设置商品名称
	 * @param name 商品名称(string)
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取商品名称
	 *
	 * @return  商品名称(string)
	 * @since   1.0.0
	*/
	
	public String getName() {
		return name;
	}
	/**设置团购说明
	 * @param notice 团购说明(string)
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}
	/**
	 * 获取团购说明
	 *
	 * @return  团购说明(string)
	 * @since   1.0.0
	*/
	
	public String getNotice() {
		return notice;
	}
	/**设置商品原价
	 * @param price 商品原价(string)
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * 获取商品原价
	 *
	 * @return  商品原价(string)
	 * @since   1.0.0
	*/
	
	public String getPrice() {
		return price;
	}
	/**设置团购价格
	 * @param grouponPrice 团购价格(string)
	 */
	public void setGrouponPrice(double grouponPrice) {
		this.grouponPrice = grouponPrice;
	}
	/**
	 * 获取团购价格
	 *
	 * @return  团购价格(string)
	 * @since   1.0.0
	*/
	
	public double getGrouponPrice() {
		return grouponPrice;
	}
	/**设置已购买人数
	 * @param saleVolume 已购买人数(double)
	 */
	public void setSaleVolume(double saleVolume) {
		this.saleVolume = saleVolume;
	}
	/**
	 * 获取已购买人数
	 *
	 * @return  已购买人数(double)
	 * @since   1.0.0
	*/
	
	public double getSaleVolume() {
		return saleVolume;
	}
}
