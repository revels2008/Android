/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * GrouponItemEntity.java
 * 
 * 2013-9-9-下午06:53:54
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * GrouponItemEntity 团购列表单项实体
 * 
 * @author: Liang Wang
 * 2013-9-9-下午06:53:54
 * 
 * @version 1.0.0
 * 
 */
public class GrouponItemEntity {
	@Column (name ="id")
	private long id;//团购编号
	@Column (name ="image")
	private String image;
	@Column (name ="listPrice")
	private double listPrice;//商品原价
	@Column (name="name")
	private String name;//商品名称
	@Column (name ="plPrice")
	private double plPrice;//宝龙价
	@Column (name ="sellNum")
	private int sellNum;//浏览量
	@Column (name ="type")
	private String type;//是否团购 0：普通商品 1：团购商品
	/**设置团购编号
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 获取团购编号
	 *
	 * @return  the id
	 * @since   1.0.0
	*/
	
	public long getId() {
		return id;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	/**设置商品市场价
	 * @param listPrice the listPrice to set
	 */
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	/**
	 * 获取商品市场价
	 *
	 * @return  the listPrice
	 * @since   1.0.0
	*/
	
	public double getListPrice() {
		return listPrice;
	}
	/**设置商品名称
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取商品名称
	 *
	 * @return  the name
	 * @since   1.0.0
	*/
	
	public String getName() {
		return name;
	}
	/**设置商品宝龙价
	 * @param plPrice the plPrice to set
	 */
	public void setPlPrice(double plPrice) {
		this.plPrice = plPrice;
	}
	/**
	 * 获取商品宝龙价
	 *
	 * @return  the plPrice
	 * @since   1.0.0
	*/
	
	public double getPlPrice() {
		return plPrice;
	}
	/**设置商品浏览数
	 * @param sellNum the sellNum to set
	 */
	public void setSellNum(int sellNum) {
		this.sellNum = sellNum;
	}
	/**
	 *获取商品浏览数
	 *
	 * @return  the sellNum
	 * @since   1.0.0
	*/
	
	public int getSellNum() {
		return sellNum;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * type
	 *
	 * @return  the type
	 * @since   1.0.0
	*/
	
	public String getType() {
		return type;
	}
}
