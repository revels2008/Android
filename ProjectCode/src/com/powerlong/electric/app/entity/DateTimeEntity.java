/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * PropEntity.java
 * 
 * 2013-9-9-下午12:03:29
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * DateTimeEntity 时间实体
 * 
 * @author: YangCheng Miao
 * 2013-9-9-下午12:03:29
 * 
 * @version 1.0.0
 * 
 */
public class DateTimeEntity {

	@Column(name = "date")
	private String date;//
	
	@Column(name = "time")
	private String time;//时间段

	/**
	 * date
	 *
	 * @return  the date
	 * @since   1.0.0
	 */
	
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * time
	 *
	 * @return  the time
	 * @since   1.0.0
	 */
	
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}	
	
}
