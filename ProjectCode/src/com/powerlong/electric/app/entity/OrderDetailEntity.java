/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * OrderDetailEntity.java
 * 
 * 2013-9-18-下午01:29:39
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * OrderDetailEntity
 * 
 * @author: Liang Wang 2013-9-18-下午01:29:39
 * 
 * @version 1.0.0
 * 
 */
public class OrderDetailEntity {
	@Column(name = "address")
	private String address;// 收货地址
	@Column(name = "allNum")
	private int allNum;// 商品总数
	@Column(name = "freight")
	private double freight;// 运费
	@Column(name = "allPrice")
	private double allPrice;// 总价
	@Column(name = "listPrice")
	private double listPrice;// 市场价
	@Column(name = "logistics")
	private String logistics;// 最近一个物流状态
	@Column(name = "logisticsId")
	private String logisticsId;// 物流编号
	@Column(name = "logisticsTime")
	private String logisticsTime;// 最近一个物流状态时间
	@Column(name = "orderNo")
	private String orderNo;// 订单编号
	@Column(name = "orderStat")
	private String orderStat;// 订单状态
	@Column(name = "pakageNum")
	private String pakageNum;// 包裹数
	@Column(name = "pay")
	private String pay;// 支付方式描述
	@Column(name = "payId")
	private String payId;// 支付方式编号
	@Column(name = "payNo")
	private String payNo;// 支付宝订单号
	@Column(name = "receiveTime")
	private String receiveTime;// 顾客填写的收货时间
	@Column(name = "receiver")
	private String receiver;// 收货人
	@Column(name = "receivedTime")
	private String receivedTime;// 收货时间
	@Column(name = "tel")
	private String tel;// 收货电话

	/**
	 * 设置收货地址
	 * 
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取收货地址
	 * 
	 * @return the address
	 * @since 1.0.0
	 */

	public String getAddress() {
		return address;
	}

	/**
	 * 设置商品总数
	 * 
	 * @param allNum
	 *            the allNum to set
	 */
	public void setAllNum(int allNum) {
		this.allNum = allNum;
	}

	/**
	 * 获取商品总数
	 * 
	 * @return the allNum
	 * @since 1.0.0
	 */

	public int getAllNum() {
		return allNum;
	}

	/**
	 * 设置运费
	 * 
	 * @param freight
	 *            the freight to set
	 */
	public void setFreight(double freight) {
		this.freight = freight;
	}

	/**
	 * 获取运费
	 * 
	 * @return the freight
	 * @since 1.0.0
	 */

	public double getFreight() {
		return freight;
	}

	/**
	 * 设置商品总价
	 * 
	 * @param allPrice
	 *            the allPrice to set
	 */
	public void setAllPrice(double allPrice) {
		this.allPrice = allPrice;
	}

	/**
	 * 获取商品总价
	 * 
	 * @return the allPrice
	 * @since 1.0.0
	 */

	public double getAllPrice() {
		return allPrice;
	}

	/**
	 * 设置商品原价
	 * 
	 * @param listPrice
	 *            the listPrice to set
	 */
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}

	/**
	 * 获取商品原价
	 * 
	 * @return the listPrice
	 * @since 1.0.0
	 */

	public double getListPrice() {
		return listPrice;
	}

	/**
	 * 设置最近物流状态
	 * 
	 * @param logistics
	 *            the logistics to set
	 */
	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}

	/**
	 * 获取最近物流状态
	 * 
	 * @return the logistics
	 * @since 1.0.0
	 */

	public String getLogistics() {
		return logistics;
	}

	/**
	 * 设置物流编号
	 * 
	 * @param logisticsId
	 *            the logisticsId to set
	 */
	public void setLogisticsId(String logisticsId) {
		this.logisticsId = logisticsId;
	}

	/**
	 * 获取物流编号
	 * 
	 * @return the logisticsId
	 * @since 1.0.0
	 */

	public String getLogisticsId() {
		return logisticsId;
	}

	/**
	 * 设置最近物流状态时间
	 * 
	 * @param logisticsTime
	 *            the logisticsTime to set
	 */
	public void setLogisticsTime(String logisticsTime) {
		this.logisticsTime = logisticsTime;
	}

	/**
	 * l获取最近物流状态时间
	 * 
	 * @return the logisticsTime
	 * @since 1.0.0
	 */

	public String getLogisticsTime() {
		return logisticsTime;
	}

	/**
	 * 设置订单号
	 * 
	 * @param orderNo
	 *            the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 获取订单号
	 * 
	 * @return the orderNo
	 * @since 1.0.0
	 */

	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * 设置订单状态
	 * 
	 * @param orderStat
	 *            the orderStat to set
	 */
	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}

	/**
	 * 获取订单状态
	 * 
	 * @return the orderStat 0：已提交待支付 1：已付款待发货 2：已发货待客户确认
	 *         3：申请退款（已经提交申请，已发货）待商家审核 4：申请退款（已经提交申请，未发货）待商家审核 5：商家审核未通过
	 *         6：商家审核通过，待客户退货 7：待退款 8：退款成功 9：交易成功 10：取消订单 11：评论 12：拒签
	 * 
	 * @since 1.0.0
	 */

	public String getOrderStat() {
		return orderStat;
	}

	/**设置包裹总数
	 * @param pakageNum
	 *            the pakageNum to set
	 */
	public void setPakageNum(String pakageNum) {
		this.pakageNum = pakageNum;
	}

	/**
	 * 获取包裹总数
	 * 
	 * @return the pakageNum
	 * @since 1.0.0
	 */

	public String getPakageNum() {
		return pakageNum;
	}

	/**设置支付方式
	 * @param pay
	 *            the pay to set
	 */
	public void setPay(String pay) {
		this.pay = pay;
	}

	/**
	 * 获取支付方式
	 * 
	 * @return the pay
	 * @since 1.0.0
	 */

	public String getPay() {
		return pay;
	}

	/**设置支付方式编号
	 * @param payId
	 *            the payId to set
	 */
	public void setPayId(String payId) {
		this.payId = payId;
	}

	/**
	 * 获取支付方式编号
	 * 
	 * @return the payId
	 * @since 1.0.0
	 */

	public String getPayId() {
		return payId;
	}

	/**设置支付宝订单号
	 * @param payNo
	 *            the payNo to set
	 */
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	/**
	 * 获取支付宝订单号
	 * 
	 * @return the payNo
	 * @since 1.0.0
	 */

	public String getPayNo() {
		return payNo;
	}

	/**设置顾客填写的收货时间
	 * @param receiveTime
	 *            the receiveTime to set
	 */
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	/**
	 * 获取顾客填写的收货时间
	 * 
	 * @return the receiveTime
	 * @since 1.0.0
	 */

	public String getReceiveTime() {
		return receiveTime;
	}

	/**设置收件人姓名
	 * @param receiver
	 *            the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * 获取收件人姓名
	 * @return the receiver
	 * @since 1.0.0
	 */

	public String getReceiver() {
		return receiver;
	}

	/**设置已收货时间
	 * @param receivedTime
	 *            the receivedTime to set
	 */
	public void setReceivedTime(String receivedTime) {
		this.receivedTime = receivedTime;
	}

	/**
	 * 获取已收到货的时间
	 * 
	 * @return the receivedTime
	 * @since 1.0.0
	 */

	public String getReceivedTime() {
		return receivedTime;
	}

	/**设置电话号码
	 * @param tel
	 *            the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 获取电话号码
	 * 
	 * @return the tel
	 * @since 1.0.0
	 */

	public String getTel() {
		return tel;
	}
}
