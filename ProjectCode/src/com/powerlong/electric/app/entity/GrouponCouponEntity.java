/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * GrouponCouponEntity.java
 * 
 * 2013-9-18-下午08:52:45
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

/**
 * 
 * GrouponCouponEntity
 * 
 * @author: Liang Wang
 * 2013-9-18-下午08:52:45
 * 
 * @version 1.0.0
 * 
 */
public class GrouponCouponEntity {
	private long id;//编号
	private String name;//名称
	private String limitTime;//有效期
	private String grouponTicketCode;
	private String updateDate;
	private String grouponName;
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * id
	 *
	 * @return  the id
	 * @since   1.0.0
	*/
	
	public long getId() {
		return id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @param limitTime the limitTime to set
	 */
	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}
	/**
	 * limitTime
	 *
	 * @return  the limitTime
	 * @since   1.0.0
	*/
	
	public String getLimitTime() {
		return limitTime;
	}
	/**
	 * grouponTicketCode
	 *
	 * @return  the grouponTicketCode
	 * @since   1.0.0
	 */
	
	public String getGrouponTicketCode() {
		return grouponTicketCode;
	}
	/**
	 * @param grouponTicketCode the grouponTicketCode to set
	 */
	public void setGrouponTicketCode(String grouponTicketCode) {
		this.grouponTicketCode = grouponTicketCode;
	}
	/**
	 * updateDate
	 *
	 * @return  the updateDate
	 * @since   1.0.0
	 */
	
	public String getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * grouponName
	 *
	 * @return  the grouponName
	 * @since   1.0.0
	 */
	
	public String getGrouponName() {
		return grouponName;
	}
	/**
	 * @param grouponName the grouponName to set
	 */
	public void setGrouponName(String grouponName) {
		this.grouponName = grouponName;
	}
	
	
}
