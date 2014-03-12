/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * NavigationFloorDetailsEntity.java
 * 
 * 2013-8-24-下午05:00:40
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;
import com.tgb.lk.ahibernate.annotation.Table;

/**
 * 
 * NavigationFloorDetailsEntity 导航楼层详情实体/店铺详情实体
 * 
 * @author: Liang Wang
 * 2013-8-24-下午05:00:40
 * 
 * @version 1.0.0
 * 
 */
@Table (name = "floor_detail")
public class NavigationFloorDetailsEntity {
	@Column (name="id")
	private int id;//primary id
	@Column (name="self_id")
	private long selfId;//店铺编号
	@Column (name="level")
	private String level;
	@Column (name="itemList")
	private String itemList;
	@Column (name="itemId")
	private String itemId;
	@Column (name="favourNum")
	private long favourNum;//收藏人数
	@Column (name="shopName")
	private String shopName;//店铺名称
	@Column (name="shopClassficId")
	private String shopClassficId;
	@Column (name="shopClass")
	private String shopClass;
	@Column (name ="businessRange")
	private String businessRange;//经营范围
	@Column (name ="mallId")
	private String mallId;//商城编号
	@Column (name="floorNum")
	private String floorNum;//楼层编号
	@Column (name="brief")
	private String brief;//店铺简介
	@Column (name="logo")
	private String logo;//缩略图地址
	@Column (name="recommendItemNum")
	private String recommendItemNum;
	@Column (name="newItemMonthlyNum")
	private String newItemMonthlyNum;
	@Column (name="hotItemNum")
	private String hotItemNum;
	@Column (name="userId")
	private String userId;//店家编号
	@Column (name="shopUserName")
	private String shopUserName;//店家名字
	@Column (name="classification")
	private String classification;//分类名称
	@Column (name="fScore")
	private int fScore;//店铺星级
	@Column (name="newItemNum")
	private String newItemNum;
	@Column (name="itemListReCommend")
	private String itemListReCommend;
	@Column (name="itemListHot")
	private String itemListHot;
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
	/**设置店铺编号
	 * @param selfId 店铺编号(long)
	 */
	public void setSelfId(long selfId) {
		this.selfId = selfId;
	}
	/**
	 * 获取店铺编号
	 *
	 * @return  店铺编号(long)
	 * @since   1.0.0
	*/
	
	public long getSelfId() {
		return selfId;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * level
	 *
	 * @return  the level
	 * @since   1.0.0
	*/
	
	public String getLevel() {
		return level;
	}
	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(String itemList) {
		this.itemList = itemList;
	}
	/**
	 * itemList
	 *
	 * @return  the itemList
	 * @since   1.0.0
	*/
	
	public String getItemList() {
		return itemList;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * itemId
	 *
	 * @return  the itemId
	 * @since   1.0.0
	*/
	
	public String getItemId() {
		return itemId;
	}
	/**设置收藏人数
	 * @param favourNum 收藏人数(long)
	 */
	public void setFavourNum(long favourNum) {
		this.favourNum = favourNum;
	}
	/**
	 * 获取收藏人数
	 *
	 * @return  收藏人数(long)
	 * @since   1.0.0
	*/
	
	public long getFavourNum() {
		return favourNum;
	}
	/**设置店铺名
	 * @param shopName 店铺名(string)
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	/**
	 * 获取店铺名
	 *
	 * @return  店铺名(string)
	 * @since   1.0.0
	*/
	
	public String getShopName() {
		return shopName;
	}
	/**
	 * @param shopClassficId the shopClassficId to set
	 */
	public void setShopClassficId(String shopClassficId) {
		this.shopClassficId = shopClassficId;
	}
	/**
	 * shopClassficId
	 *
	 * @return  the shopClassficId
	 * @since   1.0.0
	*/
	
	public String getShopClassficId() {
		return shopClassficId;
	}
	/**
	 * @param shopClass the shopClass to set
	 */
	public void setShopClass(String shopClass) {
		this.shopClass = shopClass;
	}
	/**
	 * shopClass
	 *
	 * @return  the shopClass
	 * @since   1.0.0
	*/
	
	public String getShopClass() {
		return shopClass;
	}
	/**
	 * @param businessRange the businessRange to set
	 */
	public void setBusinessRange(String businessRange) {
		this.businessRange = businessRange;
	}
	/**
	 * businessRange
	 *
	 * @return  the businessRange
	 * @since   1.0.0
	*/
	
	public String getBusinessRange() {
		return businessRange;
	}
	/**
	 * @param mallId the mallId to set
	 */
	public void setMallId(String mallId) {
		this.mallId = mallId;
	}
	/**
	 * mallId
	 *
	 * @return  the mallId
	 * @since   1.0.0
	*/
	
	public String getMallId() {
		return mallId;
	}
	/**
	 * @param floorNum the floorNum to set
	 */
	public void setFloorNum(String floorNum) {
		this.floorNum = floorNum;
	}
	/**
	 * floorNum
	 *
	 * @return  the floorNum
	 * @since   1.0.0
	*/
	
	public String getFloorNum() {
		return floorNum;
	}
	/**设置店铺简介
	 * @param brief 简介(string)
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}
	/**
	 * 获取店铺简介
	 *
	 * @return  简介(string)
	 * @since   1.0.0
	*/
	
	public String getBrief() {
		return brief;
	}
	/**设置店铺缩略图地址
	 * @param logo 地址(string)
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * 获取店铺缩略图地址
	 *
	 * @return  地址(string)
	 * @since   1.0.0
	*/
	
	public String getLogo() {
		return logo;
	}
	/**
	 * @param recommendItemNum the recommendItemNum to set
	 */
	public void setRecommendItemNum(String recommendItemNum) {
		this.recommendItemNum = recommendItemNum;
	}
	/**
	 * recommendItemNum
	 *
	 * @return  the recommendItemNum
	 * @since   1.0.0
	*/
	
	public String getRecommendItemNum() {
		return recommendItemNum;
	}
	/**
	 * @param newItemMonthlyNum the newItemMonthlyNum to set
	 */
	public void setNewItemMonthlyNum(String newItemMonthlyNum) {
		this.newItemMonthlyNum = newItemMonthlyNum;
	}
	/**
	 * newItemMonthlyNum
	 *
	 * @return  the newItemMonthlyNum
	 * @since   1.0.0
	*/
	
	public String getNewItemMonthlyNum() {
		return newItemMonthlyNum;
	}
	/**
	 * @param hotItemNum the hotItemNum to set
	 */
	public void setHotItemNum(String hotItemNum) {
		this.hotItemNum = hotItemNum;
	}
	/**
	 * hotItemNum
	 *
	 * @return  the hotItemNum
	 * @since   1.0.0
	*/
	
	public String getHotItemNum() {
		return hotItemNum;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * userId
	 *
	 * @return  the userId
	 * @since   1.0.0
	*/
	
	public String getUserId() {
		return userId;
	}
	/**
	 * @param shopUserName the shopUserName to set
	 */
	public void setShopUserName(String shopUserName) {
		this.shopUserName = shopUserName;
	}
	/**
	 * shopUserName
	 *
	 * @return  the shopUserName
	 * @since   1.0.0
	*/
	
	public String getShopUserName() {
		return shopUserName;
	}
	/**设置店铺分类
	 * @param classification 分类(string)
	 */
	public void setClassification(String classification) {
		this.classification = classification;
	}
	/**
	 * 获取店铺分类
	 *
	 * @return  分类(string)
	 * @since   1.0.0
	*/
	
	public String getClassification() {
		return classification;
	}
	/**设置店铺星级
	 * @param fScore 星级(long)
	 */
	public void setfScore(int fScore) {
		this.fScore = fScore;
	}
	/**
	 * 获取店铺星级
	 *
	 * @return  店铺星级(long)
	 * @since   1.0.0
	*/
	
	public int getfScore() {
		return fScore;
	}
	/**
	 * @param newItemNum the newItemNum to set
	 */
	public void setNewItemNum(String newItemNum) {
		this.newItemNum = newItemNum;
	}
	/**
	 * newItemNum
	 *
	 * @return  the newItemNum
	 * @since   1.0.0
	*/
	
	public String getNewItemNum() {
		return newItemNum;
	}
	/**
	 * @param itemListReCommend the itemListReCommend to set
	 */
	public void setItemListReCommend(String itemListReCommend) {
		this.itemListReCommend = itemListReCommend;
	}
	/**
	 * itemListReCommend
	 *
	 * @return  the itemListReCommend
	 * @since   1.0.0
	*/
	
	public String getItemListReCommend() {
		return itemListReCommend;
	}
	/**
	 * @param itemListHot the itemListHot to set
	 */
	public void setItemListHot(String itemListHot) {
		this.itemListHot = itemListHot;
	}
	/**
	 * itemListHot
	 *
	 * @return  the itemListHot
	 * @since   1.0.0
	*/
	
	public String getItemListHot() {
		return itemListHot;
	}
}
