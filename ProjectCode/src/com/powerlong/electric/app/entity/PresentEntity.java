/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * PresentEntity.java
 * 
 * 2013-8-29-下午01:28:05
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * PresentEntity 赠品实体
 * 
 * @author: Liang Wang
 * 2013-8-29-下午01:28:05
 * 
 * @version 1.0.0
 * 
 */
public class PresentEntity {
	@Column (name ="id")
	private int id;//primary id
	@Column (name = "presentId")
	private int presentId;//赠品id
	@Column (name = "name")
	private String name;//赠品名称
	@Column (name ="num")
	private int num;//赠品数量
	@Column (name ="image")
	private String image;//赠品图片
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
	/**设置赠品编号
	 * @param presentId 赠品编号
	 */
	public void setPresentId(int presentId) {
		this.presentId = presentId;
	}
	/**
	 * 获取赠品编号
	 *
	 * @return  编号
	 * @since   1.0.0
	*/
	
	public int getPresentId() {
		return presentId;
	}
	/**设置赠品名称
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取赠品名称
	 *
	 * @return  名称
	 * @since   1.0.0
	*/
	
	public String getName() {
		return name;
	}
	/**设置赠品数量
	 * @param num 赠品数量
	 */
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * 获取赠品数量
	 *
	 * @return  数量
	 * @since   1.0.0
	*/
	
	public int getNum() {
		return num;
	}
	/**设置赠品图片
	 * @param image 图片地址
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**获取赠品图片地址
	 *
	 * @return  地址
	 * @since   1.0.0
	*/
	
	public String getImage() {
		return image;
	}
}
