/**
 * 宝龙电商
 * com.powerlong.electric.app.handler
 * ServerConnectionHandler.java
 * 
 * 2013-8-20-下午03:43:45
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.handler;

import com.powerlong.electric.app.entity.ItemListEntity;
import com.powerlong.electric.app.widget.TableViewLayout.ItemViewHolder;

import android.os.Handler;

/**
 * 
 * ServerConnectionHandler
 * 
 * @author: Liang Wang
 * 2013-8-20-下午03:43:45
 * 
 * @version 1.0.0
 * 
 */
public class ServerConnectionHandler extends Handler {
	private ItemListEntity entity;
	private int shopId;
	private int goodIndex;
	private int shopIndex;
	private ItemViewHolder mHolder;
	/**
	 * shopId
	 *
	 * @return  the shopId
	 * @since   1.0.0
	 */
	
	public int getShopId() {
		return shopId;
	}

	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	/**
	 * goodIndex
	 *
	 * @return  the goodIndex
	 * @since   1.0.0
	 */
	
	public int getGoodIndex() {
		return goodIndex;
	}

	/**
	 * @param goodIndex the goodIndex to set
	 */
	public void setGoodIndex(int goodIndex) {
		this.goodIndex = goodIndex;
	}

	/**
	 * shopIndex
	 *
	 * @return  the shopIndex
	 * @since   1.0.0
	 */
	
	public int getShopIndex() {
		return shopIndex;
	}

	/**
	 * @param shopIndex the shopIndex to set
	 */
	public void setShopIndex(int shopIndex) {
		this.shopIndex = shopIndex;
	}

	public ItemListEntity getEntity() {
		return entity;
	}

	public void setEntity(ItemListEntity entity) {
		this.entity = entity;
	}

	/**
	 * mHolder
	 *
	 * @return  the mHolder
	 * @since   1.0.0
	 */
	
	public ItemViewHolder getmHolder() {
		return mHolder;
	}

	/**
	 * @param mHolder the mHolder to set
	 */
	public void setmHolder(ItemViewHolder mHolder) {
		this.mHolder = mHolder;
	}

	public ServerConnectionHandler() {}
 
	public ServerConnectionHandler(ItemListEntity entity, ItemViewHolder mHolder) {
		this.entity = entity;
		this.mHolder = mHolder;
	}
	
	public ServerConnectionHandler(int shopId, int goodIndex, int shopIndex, ItemListEntity entity,
			ItemViewHolder mHolder) {
		this.shopId = shopId;
		this.goodIndex = goodIndex;
		this.shopIndex = shopIndex;
		this.mHolder = mHolder;
		this.entity = entity;
	}
}
