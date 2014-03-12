/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * FloorDetailEntity.java
 * 
 * 2013年8月19日-上午10:28:08
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

/**
 * 
 * FloorDetailEntity
 * 
 * @author: YangCheng Miao
 * 2013年8月19日-上午10:28:08
 * 
 * @version 1.0.0
 * 
 */
public class FloorDetailEntity {
	String imageUrl;
	String name;
	String description;
	float grade;
	String category;
	String num;
	long selfId;
	
	public long getSelfId() {
		return selfId;
	}
	public void setSelfId(long selfId) {
		this.selfId = selfId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageId(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getGrade() {
		return grade;
	}
	public void setGrade(float grade) {
		this.grade = grade;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
	/**
	 * 创建一个新的实例 FloorDetailEntity.
	 *
	 * @param imageId
	 * @param name
	 * @param description
	 * @param grade
	 * @param category
	 * @param num
	 */
	public FloorDetailEntity(String imageUrl, String name, String description,
			float grade, String category, String num) {
		super();
		this.imageUrl = imageUrl;
		this.name = name;
		this.description = description;
		this.grade = grade;
		this.category = category;
		this.num = num;
	}
	public FloorDetailEntity(String imageUrl, String name, String description,
			float grade, String category, String num,long selfId) {
		super();
		this.imageUrl = imageUrl;
		this.name = name;
		this.description = description;
		this.grade = grade;
		this.category = category;
		this.num = num;
		this.selfId = selfId;
	}
	
	
}
