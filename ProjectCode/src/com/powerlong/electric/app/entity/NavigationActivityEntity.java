/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * NavigationActivityEntity.java
 * 
 * 2013-8-19-下午04:31:53
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;
import com.tgb.lk.ahibernate.annotation.Id;
import com.tgb.lk.ahibernate.annotation.Table;

/**
 * 
 * NavigationActivityEntity
 * 
 * @author: Liang Wang
 * 2013-8-19-下午04:31:53
 * 
 * @version 1.0.0
 * 
 */
@Table (name="navigationactivity")
public class NavigationActivityEntity {
	@Id
	@Column(name = "id")
	private int id;// primary_id

	@Column(name ="self_id")
	private long self_id;
	
	@Column(name = "mallId")
	private int mallId;

	@Column(name = "name")
	private String name;

	@Column(name = "thumbnail")
	private String thumbnail;// 分类标识或品牌标识

	@Column(name = "no")
	private String no;

	@Column(name = "shopId")
	private int shopId;

	@Column(name ="createdDate")
	private long createdDate;
	
	@Column(name ="createdDateForDisplay")
	private String createdDateForDisplay;
	
	@Column(name ="updateDate")
	private long updateDate;
	
	@Column(name ="updateDateForDisplay")
	private String updateDateForDisplay;
	
	@Column(name="isDel")
	private String isDel;
	
	@Column(name ="isRecruit")
	private String isRecruit;
	
	@Column(name ="type")
	private String type;
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
	 * @param self_id the self_id to set
	 */
	public void setSelfId(long self_id) {
		this.self_id = self_id;
	}

	/**
	 * self_id
	 *
	 * @return  the self_id
	 * @since   1.0.0
	*/
	
	public long getSelfId() {
		return self_id;
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
	 * @param thumbnail the thumbnail to set
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * thumbnail
	 *
	 * @return  the thumbnail
	 * @since   1.0.0
	*/
	
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * @param no the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * no
	 *
	 * @return  the no
	 * @since   1.0.0
	*/
	
	public String getNo() {
		return no;
	}

	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	/**
	 * shopId
	 *
	 * @return  the shopId
	 * @since   1.0.0
	*/
	
	public int getShopId() {
		return shopId;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * createdDate
	 *
	 * @return  the createdDate
	 * @since   1.0.0
	*/
	
	public long getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDateForDisplay the createdDateForDisplay to set
	 */
	public void setCreatedDateForDisplay(String createdDateForDisplay) {
		this.createdDateForDisplay = createdDateForDisplay;
	}

	/**
	 * createdDateForDisplay
	 *
	 * @return  the createdDateForDisplay
	 * @since   1.0.0
	*/
	
	public String getCreatedDateForDisplay() {
		return createdDateForDisplay;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(long updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * updateDate
	 *
	 * @return  the updateDate
	 * @since   1.0.0
	*/
	
	public long getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDateForDisplay the updateDateForDisplay to set
	 */
	public void setUpdateDateForDisplay(String updateDateForDisplay) {
		this.updateDateForDisplay = updateDateForDisplay;
	}

	/**
	 * updateDateForDisplay
	 *
	 * @return  the updateDateForDisplay
	 * @since   1.0.0
	*/
	
	public String getUpdateDateForDisplay() {
		return updateDateForDisplay;
	}

	/**
	 * @param isDel the isDel to set
	 */
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	/**
	 * isDel
	 *
	 * @return  the isDel
	 * @since   1.0.0
	*/
	
	public String getIsDel() {
		return isDel;
	}

	/**
	 * @param isRecruit the isRecruit to set
	 */
	public void setIsRecruit(String isRecruit) {
		this.isRecruit = isRecruit;
	}

	/**
	 * isRecruit
	 *
	 * @return  the isRecruit
	 * @since   1.0.0
	*/
	
	public String getIsRecruit() {
		return isRecruit;
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
