/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * UserAddressEntity.java
 * 
 * 2013-8-29-下午04:08:52
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * UserAddressEntity 用户收货地址实体
 * 
 * @author: Liang Wang
 * 2013-8-29-下午04:08:52
 * 
 * @version 1.0.0
 * 
 */
public class UserAddressEntity {
	@Column (name ="id")
	private int id;//primary id
	@Column (name = "addressId")
	private int addressId;//地址编号
	@Column (name ="address")
	private String address;//详细地址
	@Column (name="area")
	private String area;//区
	@Column (name = "areaCode")
	private String areaCode;//区号
	@Column (name = "city")
	private String city;//市
	@Column (name ="consignee")
	private String consignee;//姓名
	@Column (name = "ext")
	private String ext;//分机号
	@Column (name = "mobile")
	private String mobile;//手机号码
	@Column (name ="province")
	private String province;//省
	@Column (name ="tel")
	private String tel;//座机号码
	@Column (name = "zipCode")
	private String zipCode;//邮政编码
	@Column (name = "oper_type")
	private String operType = "0";//消息类型：0：修改，1：新增
	@Column (name = "isDefault")
	private int isDefault;//是否默认：0：否，1：是
	@Column (name = "communityName")
	private String communityName;
	@Column (name = "communityId")
	private String communityId;
	@Column (name = "isThird")
	private int isThird;  //1 只能第三方
	
	/**设置地址编号
	 * @param addressId 地址编号
	 */
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	/**
	 * 获取地址编号
	 *
	 * @return  地址编号
	 * @since   1.0.0
	*/
	
	public int getAddressId() {
		return addressId;
	}
	/**设置收货地址
	 * @param 地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 *获取收货地址
	 *
	 * @return  地址
	 * @since   1.0.0
	*/
	
	public String getAddress() {
		return address;
	}
	/**设置地区信息
	 * @param area 地区
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * 获取地区信息
	 *
	 * @return  地区信息
	 * @since   1.0.0
	*/
	
	public String getArea() {
		return area;
	}
	/**设置区号
	 * @param areaCode 区号
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * 获取区号
	 *
	 * @return  区号
	 * @since   1.0.0
	*/
	
	public String getAreaCode() {
		return areaCode;
	}
	/**设置城市信息
	 * @param city 城市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取城市信息
	 *
	 * @return  城市
	 * @since   1.0.0
	*/
	
	public String getCity() {
		return city;
	}
	/**设置姓名
	 * @param consignee 姓名
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	/**
	 * 获取姓名
	 *
	 * @return  姓名
	 * @since   1.0.0
	*/
	
	public String getConsignee() {
		return consignee;
	}
	/**设置分机号
	 * @param ext 分机号
	 */
	public void setExt(String ext) {
		this.ext = ext;
	}
	/**
	 * 获取分机号
	 *
	 * @return  分机号
	 * @since   1.0.0
	*/
	
	public String getExt() {
		return ext;
	}
	/**设置手机号码
	 * @param mobile 手机号码
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取手机号码
	 *
	 * @return  手机号码
	 * @since   1.0.0
	*/
	
	public String getMobile() {
		return mobile;
	}
	/**设置省信息
	 * @param province 省
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * 获取省信息
	 *
	 * @return  省
	 * @since   1.0.0
	*/
	
	public String getProvince() {
		return province;
	}
	/**设置座机号码
	 * @param tel 座机号码
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * 获取座机号码
	 *
	 * @return  座机号码
	 * @since   1.0.0
	*/
	
	public String getTel() {
		return tel;
	}
	/**设置邮政编码
	 * @param zipCode 邮政编码
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * 获取邮政编码
	 *
	 * @return  邮政编码
	 * @since   1.0.0
	*/
	
	public String getZipCode() {
		return zipCode;
	}
	/**设置消息类型
	 * @param operType 类型
	 */
	public void setOperType(String operType) {
		this.operType = operType;
	}
	/**
	 * 获取消息类型
	 *
	 * @return  0：修改地址，1：新增地址
	 * @since   1.0.0
	*/
	
	public String getOperType() {
		return operType;
	}
	public int getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
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
	/**
	 * communityId
	 *
	 * @return  the communityId
	 * @since   1.0.0
	 */
	
	public String getCommunityId() {
		return communityId;
	}
	/**
	 * @param communityId the communityId to set
	 */
	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}
	/**
	 * isThird
	 *
	 * @return  the isThird
	 * @since   1.0.0
	 */
	
	public int getIsThird() {
		return isThird;
	}
	/**
	 * @param isThird the isThird to set
	 */
	public void setIsThird(int isThird) {
		this.isThird = isThird;
	}
	
	
}
