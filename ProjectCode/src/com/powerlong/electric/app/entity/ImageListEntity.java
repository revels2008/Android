/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * ImageListEntity.java
 * 
 * 2013-9-9-下午02:19:43
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * ImageListEntity 图片地址实体
 * 
 * @author: Liang Wang
 * 2013-9-9-下午02:19:43
 * 
 * @version 1.0.0
 * 
 */
public class ImageListEntity {
	@Column (name = "id")
	private int id;//primary id
	@Column (name = "name")
	private String imageName;//图片地址
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
	/**设置图片地址
	 * @param imageName 地址
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	/**
	 * 获取图片地址
	 *
	 * @return  名称
	 * @since   1.0.0
	*/
	
	public String getImageName() {
		return imageName;
	}
}
