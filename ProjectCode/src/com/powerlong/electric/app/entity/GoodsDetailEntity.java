/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * GoodsDetailEntity.java
 * 
 * 2013-8-27-上午09:37:36
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * GoodsDetailEntity 商品详情实体
 * 
 * @author: Liang Wang
 * 2013-8-27-上午09:37:36
 * 
 * @version 1.0.0
 * 
 */
public class GoodsDetailEntity {
	@Column (name = "self_id")
	private int selfId;//商品编号
	@Column (name = "sequence")
	private String sequence;
	@Column (name = "sort")
	private String sort;//排序
	@Column (name ="itemName")
	private String name;//商品名称
	@Column (name = "picturePath")
	private String picPath;//商品缩略图
	@Column (name ="shopId")
	private String shopId;//店铺标识
	@Column (name ="listPrice")
	private double listPrice;//市场价
	@Column (name ="plPrice")
	private double plPrice;//折后价
	@Column (name ="favourNum")
	private int favourNum;//收藏数
	@Column (name ="sellNum")
	private int sellNum;//销售量
	@Column (name ="plPriceBegin")
	private String plPriceBegin;//打折开始时间
	@Column (name ="plPriceEnd")
	private String plPriceEnd;//打折结束时间
	@Column (name ="evaCount")
	private int evaCount;//
	@Column (name ="classificationId")
	private String classificationId;//商品分类标识
	@Column (name ="style")
	private String style;
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
	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	/**
	 * sequence
	 *
	 * @return  the sequence
	 * @since   1.0.0
	*/
	
	public String getSequence() {
		return sequence;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * sort
	 *
	 * @return  the sort
	 * @since   1.0.0
	*/
	
	public String getSort() {
		return sort;
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
	/**设置店铺编号
	 * @param shopId 店铺编号(string)
	 */
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	/**
	 * 获取店铺编号
	 *
	 * @return  店铺编号(int)
	 * @since   1.0.0
	*/
	
	public String getShopId() {
		return shopId;
	}
	/**设置商品原价
	 * @param listPrice 商品原价(double)
	 */
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	/**
	 * 获取商品原价
	 *
	 * @return  商品原价(double)
	 * @since   1.0.0
	*/
	
	public double getListPrice() {
		return listPrice;
	}
	/**设置商品折后价
	 * @param plPrice 商品折后价(double)
	 */
	public void setPlPrice(double plPrice) {
		this.plPrice = plPrice;
	}
	/**
	 * 获取商品折后价
	 *
	 * @return  商品折后价(double)
	 * @since   1.0.0
	*/
	
	public double getPlPrice() {
		return plPrice;
	}
	/**设置商品收藏数
	 * @param favourNum 收藏数(int)
	 */
	public void setFavourNum(int favourNum) {
		this.favourNum = favourNum;
	}
	/**
	 * 获取商品收藏数
	 *
	 * @return  商品收藏数(int)
	 * @since   1.0.0
	*/
	
	public int getFavourNum() {
		return favourNum;
	}
	/**设置商品销量
	 * @param sellNum 商品销量(int)
	 */
	public void setSellNum(int sellNum) {
		this.sellNum = sellNum;
	}
	/**
	 * 获取商品销量
	 *
	 * @return  商品销量(int)
	 * @since   1.0.0
	*/
	
	public int getSellNum() {
		return sellNum;
	}
	/**设置优惠开始时间
	 * @param plPriceBegin 优惠开始时间(string)
	 */
	public void setPlPriceBegin(String plPriceBegin) {
		this.plPriceBegin = plPriceBegin;
	}
	/**
	 * 获取优惠开始时间
	 *
	 * @return  优惠开始时间(string)
	 * @since   1.0.0
	*/
	
	public String getPlPriceBegin() {
		return plPriceBegin;
	}
	/**设置优惠截止时间
	 * @param plPriceEnd 优惠截止时间(string)
	 */
	public void setPlPriceEnd(String plPriceEnd) {
		this.plPriceEnd = plPriceEnd;
	}
	/**
	 * 获取优惠截止时间
	 *
	 * @return  优惠截止时间(string)
	 * @since   1.0.0
	*/
	
	public String getPlPriceEnd() {
		return plPriceEnd;
	}
	/**
	 * @param evaCount the evaCount to set
	 */
	public void setEvaCount(int evaCount) {
		this.evaCount = evaCount;
	}
	/**
	 * evaCount
	 *
	 * @return  the evaCount
	 * @since   1.0.0
	*/
	
	public int getEvaCount() {
		return evaCount;
	}
	/**设置商品种类
	 * @param classificationId 商品种类(string)
	 */
	public void setClassificationId(String classificationId) {
		this.classificationId = classificationId;
	}
	/**
	 * 获取商品种类
	 *
	 * @return  商品种类(string)
	 * @since   1.0.0
	*/
	
	public String getClassificationId() {
		return classificationId;
	}
	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * style
	 *
	 * @return  the style
	 * @since   1.0.0
	*/
	
	public String getStyle() {
		return style;
	}
	
}
