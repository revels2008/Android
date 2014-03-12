/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * NavBrandDetailsEntity.java
 * 
 * 2013-8-28-下午05:28:07
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * NavBrandDetailsEntity 品牌详情实体
 * 
 * @author: Liang Wang
 * 2013-8-28-下午05:28:07
 * 
 * @version 1.0.0
 * 
 */
public class NavigationBrandDetailsEntity {
	@Column (name = "id")
	private int id;//primary id
	@Column (name ="shopName")
	private String shopName;//店铺名
	@Column (name ="brief")
	private String brief;//店铺简介
	@Column (name ="logo")
	private String logo;//店铺logo
	@Column (name ="fscore")
	private double fscore;//店铺评价
	@Column (name ="favourNum")
	private int favourNum;//店铺收藏数
	@Column (name = "bussinessRange")
	private String bussinessRange;//经营范围
	
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
	
	/**设置商品名称
	 * @param shopName 商品名称
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	/**
	 * 获取商品名称
	 *
	 * @return  商品名称
	 * @since   1.0.0
	*/
	
	public String getShopName() {
		return shopName;
	}
	/**设置店铺简介
	 * @param 简介
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}
	/**
	 * 获取店铺简介
	 *
	 * @return  简介
	 * @since   1.0.0
	*/
	
	public String getBrief() {
		return brief;
	}
	/**设置店铺缩略图
	 * @param logo 缩略图
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * 获取店铺缩略图
	 *
	 * @return  图片地址
	 * @since   1.0.0
	*/
	
	public String getLogo() {
		return logo;
	}
	/**设置店铺评价
	 * @param fscore 评价
	 */
	public void setFscore(double fscore) {
		this.fscore = fscore;
	}
	/**
	 * 获取店铺评价
	 *
	 * @return  评价
	 * @since   1.0.0
	*/
	
	public double getFscore() {
		return fscore;
	}
	/**设置收藏人数
	 * @param favourNum 收藏人数
	 */
	public void setFavourNum(int favourNum) {
		this.favourNum = favourNum;
	}
	/**
	 * 获取收藏人数
	 *
	 * @return  收藏人数
	 * @since   1.0.0
	*/
	
	public int getFavourNum() {
		return favourNum;
	}
	/**设置经营范围
	 * @param bussinessRange 经营范围
	 */
	public void setBussinessRange(String bussinessRange) {
		this.bussinessRange = bussinessRange;
	}
	/**
	 * 获取经营范围
	 *
	 * @return  经营范围
	 * @since   1.0.0
	*/
	
	public String getBussinessRange() {
		return bussinessRange;
	}
	
}
