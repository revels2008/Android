/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * CartCountEntity.java
 * 
 * 2013-8-28-下午08:44:26
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * CartCountEntity 结算数据实体
 * 
 * @author: Liang Wang
 * 2013-8-28-下午08:44:26
 * 
 * @version 1.0.0
 * 
 */
public class CartCountEntity {
	@Column (name ="id")
	private int id;//primary id
	@Column (name ="address")
	private String address;//收货地址
	@Column (name ="address_id")
	private int addressId;//收货地址id
	@Column (name ="allPrice")
	private double allPrice;//订单总价
	@Column (name = "freight")
	private double freight;//运费
	@Column (name ="listPrice")
	private double listPrice;//市场价
	@Column (name = "receive_time")
	private String receiveTime;//收货时间
	@Column (name ="receiver")
	private String receiver;//收货人
	@Column (name = "reducePrice")
	private double reducePrice;//节省总价
	@Column (name = "tel")
	private String tel;//收货人联系方式
	@Column (name = "allNum")
	private int allNum;//商品总数
	@Column (name = "isOtherCommunity")
	private int isOtherCommunity;//是否其他区域  如果是1，则配送方式只能选择“商家送货”
	@Column (name = "view")
	private int view;
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
	/**设置收货人地址
	 * @param address 地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取收货人地址
	 *
	 * @return  地址
	 * @since   1.0.0
	*/
	
	public String getAddress() {
		return address;
	}
	/**设置收货地址编号
	 * @param addressId 地址编号
	 */
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	/**
	 * 获取收货地址编号
	 *
	 * @return  编号
	 * @since   1.0.0
	*/
	
	public int getAddressId() {
		return addressId;
	}
	/**设置订单总价
	 * @param allPrice 总价
	 */
	public void setAllPrice(double allPrice) {
		this.allPrice = allPrice;
	}
	/**
	 * 获取订单总价
	 *
	 * @return  总价
	 * @since   1.0.0
	*/
	
	public double getAllPrice() {
		return allPrice;
	}
	/**设置运费
	 * @param freight 运费
	 */
	public void setFreight(double freight) {
		this.freight = freight;
	}
	/**
	 * 获取运费
	 *
	 * @return  运费
	 * @since   1.0.0
	*/
	
	public double getFreight() {
		return freight;
	}
	/**设置订单原价
	 * @param listPrice 原价
	 */
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	/**
	 * 获取订单原价
	 *
	 * @return  原价
	 * @since   1.0.0
	*/
	
	public double getListPrice() {
		return listPrice;
	}
	/**设置收货时间
	 * @param receiveTime 收货时间
	 */
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	/**
	 * 获取收货时间
	 *
	 * @return  收货时间
	 * @since   1.0.0
	*/
	
	public String getReceiveTime() {
		return receiveTime;
	}
	/**设置收货人
	 * @param receiver 收货人
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	/**
	 * 获取收货人
	 *
	 * @return  收货人
	 * @since   1.0.0
	*/
	
	public String getReceiver() {
		return receiver;
	}
	/**设置节省总价
	 * @param reducePrice 节省总价
	 */
	public void setReducePrice(double reducePrice) {
		this.reducePrice = reducePrice;
	}
	/**
	 * 获取节省总价
	 *
	 * @return  节省总价
	 * @since   1.0.0
	*/
	
	public double getReducePrice() {
		return reducePrice;
	}
	/**设置收货人联系电话
	 * @param tel 联系电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * 获取收货人联系电话
	 *
	 * @return 联系电话
	 * @since   1.0.0
	*/
	
	public String getTel() {
		return tel;
	}
	public int getAllNum() {
		return allNum;
	}
	public void setAllNum(int allNum) {
		this.allNum = allNum;
	}
	public int getIsOtherCommunity() {
		return isOtherCommunity;
	}
	public void setIsOtherCommunity(int isOtherCommunity) {
		this.isOtherCommunity = isOtherCommunity;
	}
	/**
	 * view
	 *
	 * @return  the view
	 * @since   1.0.0
	 */
	
	public int getView() {
		return view;
	}
	/**
	 * @param view the view to set
	 */
	public void setView(int view) {
		this.view = view;
	}
	
}
