/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * LogisticsListEntity.java
 * 
 * 2013-8-29-下午01:38:04
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * LogisticsListEntity 配送方式实体
 * 
 * @author: YangCheng Miao
 * 2013-8-29-下午01:38:04
 * 
 * @version 1.0.0
 * 
 */
public class LogisticsEntity {
	@Column (name ="id")
	private int id;//primary id
	@Column (name = "logisticId")
	private int logisticId;//配送id
	@Column (name = "name")
	private String name;//配送名称
	/**
	 * logisticId
	 *
	 * @return  the logisticId
	 * @since   1.0.0
	 */
	
	public int getLogisticId() {
		return logisticId;
	}
	/**
	 * @param logisticId the logisticId to set
	 */
	public void setLogisticId(int logisticId) {
		this.logisticId = logisticId;
	}
	/**
	 * name
	 *
	 * @return  the name
	 * @since   1.0.0
	 */
	
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
