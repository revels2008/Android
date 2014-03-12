/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * ItemDetailEntity.java
 * 
 * 2013-9-9-上午11:46:15
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * ItemDetailEntity
 * 
 * @author: Liang Wang
 * 2013-9-9-上午11:46:15
 * 
 * @version 1.0.0
 * 
 */
public class ItemDetailEntity {
	@Column(name = "name")
	private String name;//商品名称
	@Column(name = "commentNum")
	private int commentNum;//评论数
	@Column(name = "detailUrl")
	private String detailUrl;//规格名称
	@Column(name = "evaluation")
	private double evaluation;//规格名称
	@Column(name = "favourNum")
	private  int favourNum;//收藏数
	@Column(name = "listPrice")
	private double listPrice;//市场价
	@Column(name = "plPrice")
	private double plPrice;//宝龙价格
	@Column(name = "prop")
	private String prop;//规格名称
	@Column(name = "prop1Name")
	private String prop1Name;//规格名称1
	@Column(name = "prop2Name")
	private String prop2Name;//规格名称2
	@Column(name = "sellNum30")
	private int sellNum30;//30天售出
	@Column(name = "shopEvaluation")
	private double shopEvaluation;//店铺评级
	@Column(name = "shopId")
	private long shopId;//店铺编号
	@Column(name = "shopProp")
	private String shopProp;//店铺描述
	@Column(name = "shopImage")
	private String shopImage;//店铺图片
	@Column(name = "shopName")
	private String shopName;//店铺名称
	@Column(name = "propList1")
	private String propList1;//规格名称列表
	@Column(name = "imageList")
	private String imageList;//规格名称
	@Column(name = "barginList")
	private String barginList;//规格名称
	@Column(name = "freight")
	private double freight;//运费
	@Column(name = "type")
	private int type;// 0 实体商品 1展示商品 2虚拟商品 3即将推出
	@Column(name = "grouponId")
	private long grouponId;// 
	@Column(name = "stat")
	private int stat;// 
	/**设置商品名称
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取商品名称
	 *
	 * @return  the name
	 * @since   1.0.0
	*/
	
	public String getName() {
		return name;
	}
	/**设置评论数
	 * @param commentNum the commentNum to set
	 */
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	/**
	 * 获取评论数
	 *
	 * @return  the commentNum
	 * @since   1.0.0
	*/
	
	public int getCommentNum() {
		return commentNum;
	}
	/**设置宝贝详情网页地址
	 * @param detailUrl the detailUrl to set
	 */
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	/**
	 * 获取宝贝详情网页地址
	 *
	 * @return  the detailUrl
	 * @since   1.0.0
	*/
	
	public String getDetailUrl() {
		return detailUrl;
	}
	/**设置商品评分
	 * @param evaluation the evaluation to set
	 */
	public void setEvaluation(double evaluation) {
		this.evaluation = evaluation;
	}
	/**
	 * 获取商品评分
	 *
	 * @return  the evaluation
	 * @since   1.0.0
	*/
	
	public double getEvaluation() {
		return evaluation;
	}
	/**设置商品收藏数
	 * @param favourNum the favourNum to set
	 */
	public void setFavourNum(int favourNum) {
		this.favourNum = favourNum;
	}
	/**
	 * 获取商品收藏数
	 *
	 * @return  the favourNum
	 * @since   1.0.0
	*/
	
	public int getFavourNum() {
		return favourNum;
	}
	/**设置商品原价
	 * @param listPrice the listPrice to set
	 */
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	/**
	 * 获取商品原价
	 *
	 * @return  the listPrice
	 * @since   1.0.0
	*/
	
	public double getListPrice() {
		return listPrice;
	}
	/**设置商品宝龙价
	 * @param plPrice the plPrice to set
	 */
	public void setPlPrice(double plPrice) {
		this.plPrice = plPrice;
	}
	/**
	 * 获取商品宝龙家
	 *
	 * @return  the plPrice
	 * @since   1.0.0
	*/
	
	public double getPlPrice() {
		return plPrice;
	}
	/**设置商品规格描述
	 * @param prop the prop to set
	 */
	public void setProp(String prop) {
		this.prop = prop;
	}
	/**
	 * 获取商品规格描述
	 *
	 * @return  the prop
	 * @since   1.0.0
	*/
	
	public String getProp() {
		return prop;
	}
	/**设置颜色规格内容
	 * @param prop1Name the prop1Name to set
	 */
	public void setProp1Name(String prop1Name) {
		this.prop1Name = prop1Name;
	}
	/**
	 * 获取颜色规格内容
	 *
	 * @return  the prop1Name
	 * @since   1.0.0
	*/
	
	public String getProp1Name() {
		return prop1Name;
	}
	/**设置尺码规格内容
	 * @param prop2Name the prop2Name to set
	 */
	public void setProp2Name(String prop2Name) {
		this.prop2Name = prop2Name;
	}
	/**
	 * 获取尺码规格内容
	 *
	 * @return  the prop2Name
	 * @since   1.0.0
	*/
	
	public String getProp2Name() {
		return prop2Name;
	}
	/**设置30天内销量
	 * @param sellNum30 the sellNum30 to set
	 */
	public void setSellNum30(int sellNum30) {
		this.sellNum30 = sellNum30;
	}
	/**
	 *获取30天内销量
	 *
	 * @return  the sellNum30
	 * @since   1.0.0
	*/
	
	public int getSellNum30() {
		return sellNum30;
	}
	/**设置店铺评价
	 * @param shopEvaluation the shopEvaluation to set
	 */
	public void setShopEvaluation(double shopEvaluation) {
		this.shopEvaluation = shopEvaluation;
	}
	/**
	 * 获取店铺评价
	 *
	 * @return  the shopEvaluation
	 * @since   1.0.0
	*/
	
	public double getShopEvaluation() {
		return shopEvaluation;
	}
	/**设置店铺编号
	 * @param shopId the shopId to set
	 */
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	/**
	 *获取店铺编号
	 *
	 * @return  the shopId
	 * @since   1.0.0
	*/
	
	public long getShopId() {
		return shopId;
	}
	/**设置店铺名称
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	/**
	 *获取店铺名称
	 *
	 * @return  the shopName
	 * @since   1.0.0
	*/
	
	public String getShopName() {
		return shopName;
	}
	/**设置宝贝图片地址
	 * @param imageList the imageList to set
	 */
	public void setImageList(String imageList) {
		this.imageList = imageList;
	}
	/**
	 * 获取宝贝图片地址
	 *
	 * @return  the imageList
	 * @since   1.0.0
	*/
	
	public String getImageList() {
		return imageList;
	}
	/**设置商品优惠列表
	 * @param barginList the barginList to set
	 */
	public void setBarginList(String barginList) {
		this.barginList = barginList;
	}
	/**
	 * 获取商品优惠列表
	 *
	 * @return  the barginList
	 * @since   1.0.0
	*/
	
	public String getBarginList() {
		return barginList;
	}
	/**设置运费
	 * @param freight the freight to set
	 */
	public void setFreight(double freight) {
		this.freight = freight;
	}
	/**
	 * 获取运费
	 *
	 * @return  the freight
	 * @since   1.0.0
	*/
	
	public double getFreight() {
		return freight;
	}
	/**设置店铺图片地址
	 * @param shopImage the shopImage to set
	 */
	public void setShopImage(String shopImage) {
		this.shopImage = shopImage;
	}
	/**
	 * 获取店铺图片地址
	 *
	 * @return  the shopImage
	 * @since   1.0.0
	*/
	
	public String getShopImage() {
		return shopImage;
	}
	/**设置店铺描述
	 * @param shopProp the shopProp to set
	 */
	public void setShopProp(String shopProp) {
		this.shopProp = shopProp;
	}
	/**
	 * 获取店铺描述
	 *
	 * @return  the shopProp
	 * @since   1.0.0
	*/
	
	public String getShopProp() {
		return shopProp;
	}
	/**设置店铺规格列表
	 * @param propList1 the propList1 to set
	 */
	public void setPropList1(String propList1) {
		this.propList1 = propList1;
	}
	/**
	 * 获取店铺规格列表
	 *
	 * @return  the propList1
	 * @since   1.0.0
	*/
	
	public String getPropList1() {
		return propList1;
	}
	/**
	 * type
	 *
	 * @return  the type
	 * @since   1.0.0
	 */
	
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * grouponId
	 *
	 * @return  the grouponId
	 * @since   1.0.0
	 */
	
	public long getGrouponId() {
		return grouponId;
	}
	/**
	 * @param grouponId the grouponId to set
	 */
	public void setGrouponId(long grouponId) {
		this.grouponId = grouponId;
	}
	/**
	 * stat
	 *
	 * @return  the stat
	 * @since   1.0.0
	 */
	
	public int getStat() {
		return stat;
	}
	/**
	 * @param stat the stat to set
	 */
	public void setStat(int stat) {
		this.stat = stat;
	}
	
	
}
