/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * OrderListEntity.java
 * 
 * 2013-9-14-上午10:37:33
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * OrderListEntity
 * 
 * @author: Liang Wang 2013-9-14-上午10:37:33
 * 
 * @version 1.0.0
 * 
 */
public class OrderListEntity {
	@Column (name ="id")
	private long id;//订单编号
	@Column(name = "restNum")
	private int restNum;// 订单未显示商品数量
	@Column(name = "allPrice")
	private double allPrice;// 订单总价格
	@Column(name = "orderStat")
	private String orderStat;// 订单状态
	@Column(name = "orderType")
	private int orderType;// 订单类型 0：普通订单 1：团购订单
	@Column(name = "allNum")
	private int allNum;// 订单类商品总数

	/**
	 * 设置未显示商品数量
	 * 
	 * @param restNum
	 *            the restNum to set
	 */
	public void setRestNum(int restNum) {
		this.restNum = restNum;
	}

	/**
	 * 获取未显示商品数量
	 * 
	 * @return the restNum
	 * @since 1.0.0
	 */

	private int getRestNum() {
		return restNum;
	}

	/**
	 * 设置订单总价
	 * 
	 * @param allPrice
	 *            the allPrice to set
	 */
	public void setAllPrice(double allPrice) {
		this.allPrice = allPrice;
	}

	/**
	 * 获取订单总价
	 * 
	 * @return the allPrice
	 * @since 1.0.0
	 */

	public double getAllPrice() {
		return allPrice;
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
	 * @return orderStat 订单状态 0：已提交 待支付 1：已付款 待发货 2：已发货 待客户确认
	 *         3：申请退款（已经提交申请，已发货）待商家审核 4：申请退款（已经提交申请，未发货）待商家审核 5：商家审核未通过
	 *         6：商家审核通过，待客户退货 7：待退款 8：退款成功 9：交易成功 10：取消订单 11：评论 12：拒签
	 * @since 1.0.0
	 */

	public String getOrderStat() {
		return orderStat;
	}

	/** 设置订单类型
	 * @param orderType
	 *            the orderType to set
	 */
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	/**
	 * 获取订单类型
	 * 
	 * @return the orderType 0：普通订单 1：团购订单
	 * @since 1.0.0
	 */

	public int getOrderType() {
		return orderType;
	}

	/**设置订单内商品总数
	 * @param allNum
	 *            the allNum to set
	 */
	public void setAllNum(int allNum) {
		this.allNum = allNum;
	}

	/**
	 * 获取订单内商品总数
	 * 
	 * @return the allNum
	 * @since 1.0.0
	 */

	public int getAllNum() {
		return allNum;
	}

	/**设置订单编号
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 获取订单编号
	 *
	 * @return  the id
	 * @since   1.0.0
	*/
	
	public long getId() {
		return id;
	}
}
