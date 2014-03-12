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
public class NearbyShopEntity {
	@Column (name="id")
	private long id;//店铺编号
	@Column (name="picturePath")
	private String picturePath;//缩略图
	@Column (name="shopName")
	private String shopName;//店铺名称
	@Column (name="brief")
	private String brief;//简介
	@Column (name="favourNum")
	private int favourNum;//收藏人数
	@Column (name="classification")
	private String classification;//分类名称
	@Column (name="evaluation")
	private double evaluation;//店铺星级
	@Column (name="notice")
	private String notice;//店铺公告列表
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public int getFavourNum() {
		return favourNum;
	}
	public void setFavourNum(int favourNum) {
		this.favourNum = favourNum;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public double getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(double evaluation) {
		this.evaluation = evaluation;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}

	
}
