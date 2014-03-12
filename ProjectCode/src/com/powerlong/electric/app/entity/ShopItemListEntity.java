/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * ShopItemListEntity.java
 * 
 * 2013-9-9-上午10:48:33
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * ShopItemListEntity 店铺商品列表实体
 * 
 * @author: Liang Wang
 * 2013-9-9-上午10:48:33
 * 
 * @version 1.0.0
 * 
 */
public class ShopItemListEntity {
	@Column (name= "name")
	private String name;//商品名称
	@Column (name = "image")
	private String image;//图片路径
	@Column (name = "id")
	private long id;//商品编号
	@Column (name = "isCart")
	private int isCart;//是否购物车内商品 0:false;1:true
	@Column (name = "isFavour")
	private  int isFavour;//是否收藏夹内商品 0:false;1:true
	@Column (name = "listPrice")
	private double listPrice;//市场价
	@Column (name = "plPrice")
	private double plPrice;//宝龙价格
	@Column (name = "sellNumMonth")
	private int sellNumMonth;//月销量
	@Column (name = "type")
	private int type;//是否团购 0:商品 1：团购
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
	/**获取商品图片
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 设置商品图片
	 *
	 * @return  the image
	 * @since   1.0.0
	*/
	
	public String getImage() {
		return image;
	}
	/**获取商品编号
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 设置商品编号
	 *
	 * @return  the id
	 * @since   1.0.0
	*/
	
	public long getId() {
		return id;
	}
	/**设置商品是否在购物车
	 * @param isCart the isCart to set
	 */
	public void setIsCart(int isCart) {
		this.isCart = isCart;
	}
	/**
	 * 获取商品是否在购物车
	 *
	 * @return  the isCart  0:false;1:true
	 * @since   1.0.0
	*/
	
	public int getIsCart() {
		return isCart;
	}
	/**设置商品是否在收藏夹
	 * @param isFavour the isFavour to set
	 */
	public void setIsFavour(int isFavour) {
		this.isFavour = isFavour;
	}
	/**
	 * 获取商品是否在收藏夹  0:false;1:true
	 *
	 * @return  the isFavour
	 * @since   1.0.0
	*/
	
	public int getIsFavour() {
		return isFavour;
	}
	/**设置商品市场价
	 * @param listPrice the listPrice to set
	 */
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	/**
	 * 获取商品市场价
	 *
	 * @return  the listPrice
	 * @since   1.0.0
	*/
	
	public double getListPrice() {
		return listPrice;
	}
	/**设置商品宝龙价格
	 * @param plPrice the plPrice to set
	 */
	public void setPlPrice(double plPrice) {
		this.plPrice = plPrice;
	}
	/**
	 *获取商品宝龙价格
	 *
	 * @return  the plPrice
	 * @since   1.0.0
	*/
	
	public double getPlPrice() {
		return plPrice;
	}
	/**设置商品月销量
	 * @param sellNumMonth the sellNumMonth to set
	 */
	public void setSellNumMonth(int sellNumMonth) {
		this.sellNumMonth = sellNumMonth;
	}
	/**
	 * 获取商品月销量
	 *
	 * @return  the sellNumMonth
	 * @since   1.0.0
	*/
	
	public int getSellNumMonth() {
		return sellNumMonth;
	}
	/**设置商品类型
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * 获取商品类型 0:商品 1：团购
	 *
	 * @return  the type
	 * @since   1.0.0
	*/
	
	public int getType() {
		return type;
	}
}
