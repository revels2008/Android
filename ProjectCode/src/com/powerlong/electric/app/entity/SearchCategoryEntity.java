/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * SearchCategoryEntity.java
 * 
 * 2013-8-26-上午09:14:56
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import java.io.Serializable;

import com.tgb.lk.ahibernate.annotation.Column;
import com.tgb.lk.ahibernate.annotation.Id;
import com.tgb.lk.ahibernate.annotation.Table;

/**
 * 
 * SearchCategoryEntity:搜索模块商品/店铺分类界面实体
 * 
 * @author: Liang Wang
 * 2013-8-26-上午09:14:56
 * 
 * @version 1.0.0
 * 
 */
@Table (name = "search_category")
public class SearchCategoryEntity implements Serializable {
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since 1.0.0
	 */
	
	private static final long serialVersionUID = 3195117485955157092L;
	@Id
	@Column (name ="id")
	private int id;//primary id
	@Column (name = "selfId")
	private long selfId;//分类标识
	@Column (name ="description")
	private String description;//分类描述
	@Column (name="level")
	private int level;//页面层级标识
	@Column (name ="mallId")
	private int mallId;//商场标识
	@Column (name="appItemCategoryId")
	private int appItemCategoryId;
	@Column (name="name")
	private String name;//分类名称
	@Column (name="pid")
	private int pid;
	@Column (name="logo")
	private String logo;
	@Column (name="lowerCategoryList")
	private String lowerCategoryList;
	
	public String getLowerCategoryList() {
		return lowerCategoryList;
	}
	public void setLowerCategoryList(String lowerCategoryList) {
		this.lowerCategoryList = lowerCategoryList;
	}
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
	/**
	 * @param selfId the selfId to set
	 */
	public void setSelfId(long selfId) {
		this.selfId = selfId;
	}
	/**
	 * selfId
	 *
	 * @return  the selfId
	 * @since   1.0.0
	*/
	
	public long getSelfId() {
		return selfId;
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
	 * @param appItemCategoryId the appItemCategoryId to set
	 */
	public void setAppItemCategoryId(int appItemCategoryId) {
		this.appItemCategoryId = appItemCategoryId;
	}
	/**
	 * appItemCategoryId
	 *
	 * @return  the appItemCategoryId
	 * @since   1.0.0
	*/
	
	public int getAppItemCategoryId() {
		return appItemCategoryId;
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
	public void setPid(int pid) {
		this.pid = pid;
	}
	/**
	 * pid
	 *
	 * @return  the pid
	 * @since   1.0.0
	*/
	
	public int getPid() {
		return pid;
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
}
