/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * ChatMsgEntity.java
 * 
 * 2013-9-17-下午10:01:27
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * ChatMsgListEntity
 * 
 * @author: Liang Wang
 * 2013-9-17-下午10:01:27
 * 
 * @version 1.0.0
 * 
 */
public class ChatMsgListEntity {
	@Column (name="content")
	private String content;//消息内容
	@Column (name="createDate")
	private String createDate;//消息创建时间
	@Column (name="shopId")
	private long shopId;//店家编号
	@Column (name="sender")
	private String sender;//发送人
	@Column (name="image")
	private String image;//头像
	/**设置消息内容
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取消息内容
	 *
	 * @return  the content
	 * @since   1.0.0
	*/
	
	public String getContent() {
		return content;
	}
	/**设置发送日期
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取发送日期
	 *
	 * @return  the createDate
	 * @since   1.0.0
	*/
	
	public String getCreateDate() {
		return createDate;
	}
	/**设置主键
	 * @param id the id to set
	 */
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	/**
	 * 获取主键
	 *
	 * @return  the id
	 * @since   1.0.0
	*/
	
	public long getShopId() {
		return shopId;
	}
	/**设置发送人
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	/**
	 * 获取发送人
	 *
	 * @return  the sender
	 * @since   1.0.0
	*/
	
	public String getSender() {
		return sender;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取头像
	 *
	 * @return  the image
	 * @since   1.0.0
	*/
	
	public String getImage() {
		return image;
	}
	
}
