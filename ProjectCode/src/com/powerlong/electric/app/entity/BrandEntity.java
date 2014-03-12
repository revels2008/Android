/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * BrandEntity.java
 * 
 * 2013年7月31日-上午11:07:59
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

/**
 * 
 * BrandEntity
 * 
 * @author: YangCheng Miao
 * 2013年7月31日-上午11:07:59
 * 
 * @version 1.0.0
 * 
 */
public class BrandEntity {
	private String brandName;
//	private int iconID;
	private String imageUrl;
	private String NameSort;

	public String getName() {
		return brandName;
	}
	
	public String getIcon() {
		return imageUrl;
	}
	
	public String getNameSort()
	{
		return NameSort;
	}

	public void setNameSort(String nameSort)
	{
		NameSort = nameSort;
	}
	
	public void setName(String name)
	{
		brandName = name;
	}
	
	public void setIcon(String url)
	{
		imageUrl = url;
	}
	public BrandEntity(String name, String imageUrl, String nameSort) {
		super();
		this.brandName = name;
		this.imageUrl = imageUrl;
		this.NameSort = nameSort;
	}
	
	public BrandEntity() {
		
	}
}
