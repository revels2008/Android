/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * LowerCategoriesListEntity.java
 * 
 * 2013-9-29-下午07:31:56
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * LowerCategoriesListEntity
 * 
 * @author: Liang Wang
 * 2013-9-29-下午07:31:56
 * 
 * @version 1.0.0
 * 
 */
public class LowerCategoriesListEntity {
	@Column(name="appItemCategoryId")
	private long appItemCategoryId;
	@Column(name="data")
	private String data;
	@Column(name="description")
	private String description;
	@Column(name="id")
	private long id;
	@Column(name="level")
	private int level;
	@Column(name="logo")
	private String logo;
	@Column(name="mallId")
	private int mallId;
	@Column(name="method")
	private String method;
	@Column(name="name")
	private String name;
	@Column(name="pid")
	private long pid;
	/**
	 * @param appItemCategoryId the appItemCategoryId to set
	 */
	public void setAppItemCategoryId(long appItemCategoryId) {
		this.appItemCategoryId = appItemCategoryId;
	}
	/**
	 * appItemCategoryId
	 *
	 * @return  the appItemCategoryId
	 * @since   1.0.0
	*/
	
	public long getAppItemCategoryId() {
		return appItemCategoryId;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * data
	 *
	 * @return  the data
	 * @since   1.0.0
	*/
	
	public String getData() {
		return data;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * description
	 *
	 * @return  the description
	 * @since   1.0.0
	*/
	
	public String getDescription() {
		return description;
	}
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
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * level
	 *
	 * @return  the level
	 * @since   1.0.0
	*/
	
	public int getLevel() {
		return level;
	}
	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * logo
	 *
	 * @return  the logo
	 * @since   1.0.0
	*/
	
	public String getLogo() {
		return logo;
	}
	/**
	 * @param mallId the mallId to set
	 */
	public void setMallId(int mallId) {
		this.mallId = mallId;
	}
	/**
	 * mallId
	 *
	 * @return  the mallId
	 * @since   1.0.0
	*/
	
	public int getMallId() {
		return mallId;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * method
	 *
	 * @return  the method
	 * @since   1.0.0
	*/
	
	public String getMethod() {
		return method;
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
	 * @param pid the pid to set
	 */
	public void setPid(long pid) {
		this.pid = pid;
	}
	/**
	 * pid
	 *
	 * @return  the pid
	 * @since   1.0.0
	*/
	
	public long getPid() {
		return pid;
	}
}
