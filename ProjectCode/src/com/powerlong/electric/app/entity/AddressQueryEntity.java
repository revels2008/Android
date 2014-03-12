/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * AddressEntity.java
 * 
 * 2013年8月27日-下午5:27:20
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

/**
 * 
 * AddressEntity
 * 
 * @author: YangCheng Miao
 * 2013年8月27日-下午5:27:20
 * 
 * @version 1.0.0
 * 
 */
public class AddressQueryEntity {
	int id;
	String name;
	String province;
	String city;
	String area;
	String zipCode;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * 创建一个新的实例 AddressEntity.
	 *
	 * @param id
	 * @param name
	 * @param province
	 * @param city
	 * @param area
	 * @param zipCode
	 */
	public AddressQueryEntity(int id, String name, String province, String city,
			String area, String zipCode) {
		super();
		this.id = id;
		this.name = name;
		this.province = province;
		this.city = city;
		this.area = area;
		this.zipCode = zipCode;
	}	
	
	public AddressQueryEntity() {
		
	}
}