/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * GrouponDetailShopEntity.java
 * 
 * 2013-9-12-下午05:00:54
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * GrouponDetailShopEntity 团购店铺信息
 * 
 * @author: Liang Wang
 * 2013-9-12-下午05:00:54
 * 
 * @version 1.0.0
 * 
 */
public class GrouponDetailShopEntity {
	@Column (name= "name")
	private String name;//店铺名称
	@Column (name= "evaluation")
	private double evaluation;//店铺评级
	@Column (name= "prop")
	private String prop;//店铺属性
	@Column (name= "id")
	private long id;//商家编号
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
	/**设置店铺评级
	 * @param evaluation the evaluation to set
	 */
	public void setEvaluation(double evaluation) {
		this.evaluation = evaluation;
	}
	/**
	 * 获取店铺评级
	 *
	 * @return  the evaluation
	 * @since   1.0.0
	*/
	
	public double getEvaluation() {
		return evaluation;
	}
	/**设置店铺属性
	 * @param prop the prop to set
	 */
	public void setProp(String prop) {
		this.prop = prop;
	}
	/**
	 * 获取店铺属性
	 *
	 * @return  the prop
	 * @since   1.0.0
	*/
	
	public String getProp() {
		return prop;
	}
	/**设置店铺编号
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 获取店铺编号
	 *
	 * @return  the id
	 * @since   1.0.0
	*/
	
	public long getId() {
		return id;
	}
}
