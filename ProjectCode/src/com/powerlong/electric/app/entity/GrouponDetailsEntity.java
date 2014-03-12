/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * GrouponDetailsEntity.java
 * 
 * 2013-9-12-下午03:40:36
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * GrouponDetailsEntity 团购详情实体
 * 
 * @author: Liang Wang
 * 2013-9-12-下午03:40:36
 * 
 * @version 1.0.0
 * 
 */
public class GrouponDetailsEntity {
	@Column (name ="buyNotify")
	private String buyNotify;//购买须知
	@Column (name ="buyNum")
	private  int buyNum;//已经团购数量
	@Column (name ="commentNum")
	private int commentNum;
	@Column (name ="startDate")
	private long startDate;
	@Column (name ="countDownTime")
	private long countDownTime;//倒计时
	@Column (name ="content")
	private String content;//团购内容
	@Column (name ="image")
	private String image;//图片地址
	@Column (name ="isPaidfor")
	private String isPaidfor;//是否假一赔三 0 是 1否
	@Column (name ="isReturnItem")
	private String isReturnItem;//是否无理由退货 0 是 1否
	@Column (name ="isReturnMoney")
	private String isReturnMoney;//是否支持退款 0 是 1否
	@Column (name ="itemNum")
	private int itemNum;//包含商品数
	@Column (name ="listPrice")
	private double listPrice;//市场价
	@Column (name ="name")
	private String name;//团购名称
	@Column (name ="plPrice")
	private double plPrice;//价格
	@Column (name ="shopList")
	private String shopList;//适用商家信息
	@Column (name ="itemGroupList")
	private String itemGroupList;//
	@Column (name ="shopId")
	private long shopId;//
	@Column (name ="shopName")
	private String shopName;//
	@Column (name ="shopProp")
	private String shopProp;//
	@Column (name ="shopImage")
	private String shopImage;//
	@Column (name ="shopEvaluation")
	private String shopEvaluation;//
	@Column (name ="stat")
	private int stat;//
	/**设置购买须知
	 * @param buyNotify the buyNotify to set
	 */
	public void setBuyNotify(String buyNotify) {
		this.buyNotify = buyNotify;
	}
	/**
	 * 获取购买须知
	 *
	 * @return  the buyNotify
	 * @since   1.0.0
	*/
	
	public String getBuyNotify() {
		return buyNotify;
	}
	/**设置已参团数量
	 * @param buyNum the buyNum to set
	 */
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	/**
	 * 获取已参团数量
	 *
	 * @return  the buyNum
	 * @since   1.0.0
	*/
	
	public int getBuyNum() {
		return buyNum;
	}
	
	
	/**设置已参团数量
	 * @param buyNum the buyNum to set
	 */
	public void setStat(int stat) {
		this.stat = stat;
	}
	/**
	 * 获取已参团数量
	 *
	 * @return  the buyNum
	 * @since   1.0.0
	*/
	
	public int getStat() {
		return stat;
	}
	
	/**设置评论数量
	 * @param commentNum the commentNum to set
	 */
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	/**
	 * 获取评论数量
	 *
	 * @return  the commentNum
	 * @since   1.0.0
	*/
	
	public int getCommentNum() {
		return commentNum;
	}
	/**设置倒计时
	 * @param countDownTime the countDownTime to set
	 */
	public void setCountDownTime(long countDownTime) {
		this.countDownTime = countDownTime;
	}
	/**
	 * 获取倒计时间
	 *
	 * @return  the countDownTime
	 * @since   1.0.0
	*/
	
	public long getCountDownTime() {
		return countDownTime;
	}
	/**设置团购内容
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取团购内容
	 *
	 * @return  the content
	 * @since   1.0.0
	*/
	
	public String getContent() {
		return content;
	}
	/**设置团购图片
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取团购图片
	 *
	 * @return  the image
	 * @since   1.0.0
	*/
	
	public String getImage() {
		return image;
	}
	/**设置是否假一赔三
	 * @param isPaidfor the isPaidfor to set
	 */
	public void setIsPaidfor(String isPaidfor) {
		this.isPaidfor = isPaidfor;
	}
	/**
	 *获取是否假一赔三 0 是 1否
	 *
	 * @return  the isPaidfor
	 * @since   1.0.0
	*/
	
	public String getIsPaidfor() {
		return isPaidfor;
	}
	/**设置是否无理由退货
	 * @param isReturnItem the isReturnItem to set
	 */
	public void setIsReturnItem(String isReturnItem) {
		this.isReturnItem = isReturnItem;
	}
	/**
	 * 获取是否无理由退货 0 是 1否
	 *
	 * @return  the isReturnItem
	 * @since   1.0.0
	*/
	
	public String getIsReturnItem() {
		return isReturnItem;
	}
	/**设置市场价
	 * @param listPrice the listPrice to set
	 */
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	/**
	 * 获取市场价
	 *
	 * @return  the listPrice
	 * @since   1.0.0
	*/
	
	public double getListPrice() {
		return listPrice;
	}
	/**设置团购名称
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取团购名称
	 *
	 * @return  the name
	 * @since   1.0.0
	*/
	
	public String getName() {
		return name;
	}
	/**设置宝龙价
	 * @param plPrice the plPrice to set
	 */
	public void setPlPrice(double plPrice) {
		this.plPrice = plPrice;
	}
	/**获取宝龙价
	 *
	 * @return  the plPrice
	 * @since   1.0.0
	*/
	
	public double getPlPrice() {
		return plPrice;
	}
	/**设置店铺信息
	 * @param shopList the shopList to set
	 */
	public void setShopList(String shopList) {
		this.shopList = shopList;
	}
	/**
	 * 获取店铺信息
	 *
	 * @return  the shopList
	 * @since   1.0.0
	*/
	
	public String getShopList() {
		return shopList;
	}
	/**设置团购商品数量
	 * @param itemNum the itemNum to set
	 */
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	/**
	 * 获取团购商品数
	 *
	 * @return  the itemNum
	 * @since   1.0.0
	*/
	
	public int getItemNum() {
		return itemNum;
	}
	/**社会自是否支持退款
	 * @param isReturnMoney the isReturnMoney to set
	 */
	public void setIsReturnMoney(String isReturnMoney) {
		this.isReturnMoney = isReturnMoney;
	}
	/**
	 * 获取是否支持退款  0 是 1否
	 * @return  the isReturnMoney
	 * @since   1.0.0
	*/
	
	public String getIsReturnMoney() {
		return isReturnMoney;
	}
	/**
	 * itemGroupList
	 *
	 * @return  the itemGroupList
	 * @since   1.0.0
	 */
	
	public String getItemGroupList() {
		return itemGroupList;
	}
	/**
	 * @param itemGroupList the itemGroupList to set
	 */
	public void setItemGroupList(String itemGroupList) {
		this.itemGroupList = itemGroupList;
	}
	/**
	 * startDate
	 *
	 * @return  the startDate
	 * @since   1.0.0
	 */
	
	public long getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}
	/**
	 * shopId
	 *
	 * @return  the shopId
	 * @since   1.0.0
	 */
	
	public long getShopId() {
		return shopId;
	}
	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	/**
	 * shopName
	 *
	 * @return  the shopName
	 * @since   1.0.0
	 */
	
	public String getShopName() {
		return shopName;
	}
	/**
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	/**
	 * shopProp
	 *
	 * @return  the shopProp
	 * @since   1.0.0
	 */
	
	public String getShopProp() {
		return shopProp;
	}
	/**
	 * @param shopProp the shopProp to set
	 */
	public void setShopProp(String shopProp) {
		this.shopProp = shopProp;
	}
	/**
	 * shopImage
	 *
	 * @return  the shopImage
	 * @since   1.0.0
	 */
	
	public String getShopImage() {
		return shopImage;
	}
	/**
	 * @param shopImage the shopImage to set
	 */
	public void setShopImage(String shopImage) {
		this.shopImage = shopImage;
	}
	/**
	 * shopEvaluation
	 *
	 * @return  the shopEvaluation
	 * @since   1.0.0
	 */
	
	public String getShopEvaluation() {
		return shopEvaluation;
	}
	/**
	 * @param shopEvaluation the shopEvaluation to set
	 */
	public void setShopEvaluation(String shopEvaluation) {
		this.shopEvaluation = shopEvaluation;
	}
	
}
