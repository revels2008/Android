/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * ShopFavourListEntity.java
 * 
 * 2013-9-16-上午10:10:00
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * ShopFavourListEntity 商品收藏列表实体
 * 
 * @author: Liang Wang
 * 2013-9-16-上午10:10:00
 * 
 * @version 1.0.0
 * 
 */
public class ShopFavourListEntity {
	@Column(name="favourId")
	private long favourId;//收藏编号
	@Column(name="shopId")
	private long shopId;//店铺编号
	@Column(name="image")
	private String image;//店铺图片
	@Column(name="shopName")
	private String shopName;//店铺名称
	@Column(name="brief")
	private String brief;//店铺简介
	@Column(name="pageviewNum")
	private long pageviewNum;//浏览人数
	@Column(name="favourNum")
	private long favourNum;//收藏人数
	@Column(name="classification")
	private String classification;//店铺分类
	@Column(name="evaluation")
	private double evaluation;//浏览人数
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
	/**设置店铺编号
	 * @param shopId the shopId to set
	 */
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	/**
	 * 获取店铺编号
	 *
	 * @return  the shopId
	 * @since   1.0.0
	*/
	
	public long getShopId() {
		return shopId;
	}
	/**设置店铺图片
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取店铺图片
	 *
	 * @return  the image
	 * @since   1.0.0
	*/
	
	public String getImage() {
		return image;
	}
	/**设置店铺名称
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	/**
	 * 获取店铺名称
	 *
	 * @return  the shopName
	 * @since   1.0.0
	*/
	
	public String getShopName() {
		return shopName;
	}
	/**设置店铺简介
	 * @param brief the brief to set
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}
	/**
	 * 获取店铺简介
	 *
	 * @return  the brief
	 * @since   1.0.0
	*/
	
	public String getBrief() {
		return brief;
	}
	/**设置店铺浏览次数
	 * @param pageviewNum the pageviewNum to set
	 */
	public void setPageviewNum(long pageviewNum) {
		this.pageviewNum = pageviewNum;
	}
	/**
	 * 获取店铺浏览次数
	 *
	 * @return  the pageviewNum
	 * @since   1.0.0
	*/
	
	public long getPageviewNum() {
		return pageviewNum;
	}
	/**设置店铺收藏数
	 * @param favourNum the favourNum to set
	 */
	public void setFavourNum(long favourNum) {
		this.favourNum = favourNum;
	}
	/**
	 *获取店铺收藏数
	 *
	 * @return  the favourNum
	 * @since   1.0.0
	*/
	
	public long getFavourNum() {
		return favourNum;
	}
	/**设置店铺分类
	 * @param classification the classification to set
	 */
	public void setClassification(String classification) {
		this.classification = classification;
	}
	/**
	 * 获取店铺分类
	 *
	 * @return  the classification
	 * @since   1.0.0
	*/
	
	public String getClassification() {
		return classification;
	}
	/**设置店铺星级
	 * @param evaluation the evaluation to set
	 */
	public void setEvaluation(double evaluation) {
		this.evaluation = evaluation;
	}
	/**
	 * 获取店铺星级
	 *
	 * @return  the evaluation
	 * @since   1.0.0
	*/
	
	public double getEvaluation() {
		return evaluation;
	}
}
