/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * ShopDetailsEntity.java
 * 
 * 2013-9-9-上午10:34:52
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * ShopDetailsEntity 店铺详情实体
 * 
 * @author: Liang Wang
 * 2013-9-9-上午10:34:52
 * 
 * @version 1.0.0
 * 
 */
public class ShopDetailsEntity {
	@Column (name= "name")
	private String name;//店铺名称
	@Column (name = "image")
	private String image;//图片
	@Column (name =  "instroduction")
	private String instroduction;//简介
	@Column (name =  "itemNum")
	private int itemNum;//商品数量
	@Column (name =  "classification")
	private String classification;//店铺分类
	@Column (name =  "favourNum")
	private int favourNum;//收藏数
	@Column (name =  "evaluation")
	private double evaluation;//评分
	@Column (name =  "categoryList")
	private String categoryList;//类目列表
	@Column (name =  "orderType")
	private int orderType;//排序方式
	@Column (name =  "showType")
	private int showType;//显示方式
	/**设置店铺名称
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取店铺名称
	 *
	 * @return  the name
	 * @since   1.0.0
	*/
	
	public String getName() {
		return name;
	}
	/**设置店铺图片地址
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取店铺图片地址
	 *
	 * @return  the image
	 * @since   1.0.0
	*/
	
	public String getImage() {
		return image;
	}
	/**设置店铺简介
	 * @param instroduction the instroduction to set
	 */
	public void setInstroduction(String instroduction) {
		this.instroduction = instroduction;
	}
	/**
	 * 获取店铺简介
	 *
	 * @return  the instroduction
	 * @since   1.0.0
	*/
	
	public String getInstroduction() {
		return instroduction;
	}
	/**设置店铺商品数
	 * @param itemNum the itemNum to set
	 */
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	/**
	 * 获取店铺商品数量
	 *
	 * @return  the itemNum
	 * @since   1.0.0
	*/
	
	public int getItemNum() {
		return itemNum;
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
	/**设置店铺收藏数
	 * @param favourNum the favourNum to set
	 */
	public void setFavourNum(int favourNum) {
		this.favourNum = favourNum;
	}
	/**
	 * 获取店铺收藏数
	 *
	 * @return  the favourNum
	 * @since   1.0.0
	*/
	
	public int getFavourNum() {
		return favourNum;
	}
	/**设置店铺评价
	 * @param evaluation the evaluation to set
	 */
	public void setEvaluation(double evaluation) {
		this.evaluation = evaluation;
	}
	/**
	 * 获取店铺评价
	 *
	 * @return  the evaluation
	 * @since   1.0.0
	*/
	
	public double getEvaluation() {
		return evaluation;
	}
	
	/**
	 * @param categoryList the categoryList to set
	 */
	public void setCategoryList(String categoryList) {
		this.categoryList = categoryList;
	}
	/**
	 * categoryList
	 *
	 * @return  the categoryList
	 * @since   1.0.0
	*/
	
	public String getCategoryList() {
		return categoryList;
	}
	/**设置店铺排序方式
	 * @param orderType the orderType to set
	 */
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	/**
	 * 获取店铺排序方式
	 *
	 * @return  the orderType
	 * @since   1.0.0
	*/
	
	public int getOrderType() {
		return orderType;
	}
	/**设置店铺显示方式
	 * @param showType the showType to set
	 */
	public void setShowType(int showType) {
		this.showType = showType;
	}
	/**
	 * 获取店铺显示方式
	 *
	 * @return  the showType
	 * @since   1.0.0
	*/
	
	public int getShowType() {
		return showType;
	}
}
