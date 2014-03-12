/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * ChatMsgEntity.java
 * 
 * 2013-9-17-下午10:01:27
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * NearbyShopEntity
 * 
 * @author: YangCheng Miao
 * 2013-9-17-下午10:01:27
 * 
 * @version 1.0.0
 * 
 */
public class NearbySearchEntity {
	@Column (name="shopid")
	private long shopid;//店铺编号
	@Column (name="floor")
	private String floor;//缩略图
	@Column (name="shopname")
	private String shopname;//店铺名称
	@Column (name="classname")
	private String classname;//简介
	@Column (name="favourNum")
	private String favourNum;//收藏人数
	@Column (name="locx")
	private String locx;//分类名称
	@Column (name="locy")
	private String locy;//店铺星级
	@Column (name="storeNo")
	private String storeNo;//店铺公告列表
	@Column (name="locate")
	private String locate;//店铺公告列表
	@Column (name="floorid")
	private long floorid;//店铺编号
	public long getShopid() {
		return shopid;
	}
	public void setShopid(long shopid) {
		this.shopid = shopid;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getFavourNum() {
		return favourNum;
	}
	public void setFavourNum(String favourNum) {
		this.favourNum = favourNum;
	}
	public String getLocx() {
		return locx;
	}
	public void setLocx(String locx) {
		this.locx = locx;
	}
	public String getLocy() {
		return locy;
	}
	public void setLocy(String locy) {
		this.locy = locy;
	}
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	/**
	 * locate
	 *
	 * @return  the locate
	 * @since   1.0.0
	 */
	
	public String getLocate() {
		return locate;
	}
	/**
	 * @param locate the locate to set
	 */
	public void setLocate(String locate) {
		this.locate = locate;
	}
	/**
	 * floorid
	 *
	 * @return  the floorid
	 * @since   1.0.0
	 */
	
	public long getFloorid() {
		return floorid;
	}
	/**
	 * @param floorid the floorid to set
	 */
	public void setFloorid(long floorid) {
		this.floorid = floorid;
	}
	
	
}
