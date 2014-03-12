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
public class AddressEntity {
	String name;
	String phoneNum;
	String city;
	String address;
	int addressId;
	String communityName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	/**
	 * addressId
	 *
	 * @return  the addressId
	 * @since   1.0.0
	 */
	
	public int getAddressId() {
		return addressId;
	}
	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	/**
	 * 创建一个新的实例 AddressEntity.
	 *
	 * @param name
	 * @param phoneNum
	 * @param city
	 * @param address
	 */
	
	
	public AddressEntity(String name, String phoneNum, String city,
			String address, int addressId, String communityName) {
		super();
		this.name = name;
		this.phoneNum = phoneNum;
		this.city = city;
		this.address = address;
		this.addressId = addressId;
		this.communityName = communityName;
	}
	/**
	 * communityName
	 *
	 * @return  the communityName
	 * @since   1.0.0
	 */
	
	public String getCommunityName() {
		return communityName;
	}
	/**
	 * @param communityName the communityName to set
	 */
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	
	
}
