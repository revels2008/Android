/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * CategoryEntity.java
 * 
 * 2013年7月29日-下午2:57:01
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import java.io.Serializable;


/**
 * 
 * CategoryEntity
 * 
 * @author: YangCheng Miao
 * 2013年7月29日-下午2:57:01
 * 
 * @version 1.0.0
 * 
 */
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private int iconID;
	public int partId = 0;
	public boolean ifTop = false;

	public String getName() {
		return name;
	}
	
	public int getIcon() {
		return iconID;
	}
	public int getPartId() {
		return partId;
	}
	public void setPartId(int partId) {
		this.partId = partId;
	}
	public CategoryEntity(String name, int iconId, int partId) {
		super();
		this.name = name;
		this.iconID = iconId;
		this.partId = partId;
	}
}
