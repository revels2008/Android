/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * ActivityDetailEntity.java
 * 
 * 2013-9-9-下午07:41:17
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * ActivityDetailEntity 活动详情实体
 * 
 * @author: Liang Wang
 * 2013-9-9-下午07:41:17
 * 
 * @version 1.0.0
 * 
 */
public class ActivityDetailEntity {
	@Column (name ="id")
	private long id;//活动编号
	@Column (name = "name")
	private String name;//活动名称
	@Column (name = "image")
	private String image;//活动图片列表
	@Column (name = "classification")
	private String classification;//活动类别
	@Column (name = "duration")
	private String duration;//活动持续时间
	@Column (name = "address")
	private String address;//活动地址
	@Column (name = "isPlazaActivity")
	private String isPlazaActivity;//活动地址是否是广场的活动 0：商家活动；1广场活动
	@Column (name = "rule")
	private int rule;//活动规则
	@Column (name = "tips")
	private int tips;//温馨提示
	@Column (name = "introduction")
	private String introduction;//活动介绍
	
	/**设置活动编号
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 获取活动编号
	 *
	 * @return  the id
	 * @since   1.0.0
	*/
	
	public long getId() {
		return id;
	}
	/**设置活动名称
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取活动名称
	 *
	 * @return  the name
	 * @since   1.0.0
	*/
	
	public String getName() {
		return name;
	}
	/**设置活动图片
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取图片列表
	 *
	 * @return  the image
	 * @since   1.0.0
	*/
	
	public String getImage() {
		return image;
	}
	/**设置活动类别
	 * @param classification the classification to set
	 */
	public void setClassification(String classification) {
		this.classification = classification;
	}
	/**
	 * 获取活动类别
	 *
	 * @return  the classification
	 * @since   1.0.0
	*/
	
	public String getClassification() {
		return classification;
	}
	/**设置活动持续时间
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}
	/**
	 * 获取活动持续时间
	 *
	 * @return  the duration
	 * @since   1.0.0
	*/
	
	public String getDuration() {
		return duration;
	}
	/**设置活动地址
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取活动地址
	 *
	 * @return  the address
	 * @since   1.0.0
	*/
	
	public String getAddress() {
		return address;
	}
	/**设置活动地址是否是广场的活动 0：商家活动；1广场活动
	 * @param isPlazaActivity the isPlazaActivity to set
	 */
	public void setIsPlazaActivity(String isPlazaActivity) {
		this.isPlazaActivity = isPlazaActivity;
	}
	/**
	 * 获取设置活动地址是否是广场的活动 0：商家活动；1广场活动
	 *
	 * @return  the isPlazaActivity
	 * @since   1.0.0
	*/
	
	public String getIsPlazaActivity() {
		return isPlazaActivity;
	}
	/**设置活动规则
	 * @param rule the rule to set
	 */
	public void setRule(int rule) {
		this.rule = rule;
	}
	/**
	 * 获取活动规则
	 *
	 * @return  the rule
	 * @since   1.0.0
	*/
	
	public int getRule() {
		return rule;
	}
	/**设置温馨提示
	 * @param tips the tips to set
	 */
	public void setTips(int tips) {
		this.tips = tips;
	}
	/**
	 * 获取温馨提示
	 *
	 * @return  the tips
	 * @since   1.0.0
	*/
	
	public int getTips() {
		return tips;
	}
	/**设置活动介绍
	 * @param instroduction the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	/**
	 * 获取活动介绍
	 *
	 * @return  the introduction
	 * @since   1.0.0
	*/
	
	public String getIntroduction() {
		return introduction;
	}
}
