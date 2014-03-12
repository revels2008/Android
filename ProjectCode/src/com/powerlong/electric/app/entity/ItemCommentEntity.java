/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * ItemCommentEntity.java
 * 
 * 2013-9-9-下午03:08:46
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * ItemCommentEntity 商品评论实体
 * 
 * @author: Liang Wang
 * 2013-9-9-下午03:08:46
 * 
 * @version 1.0.0
 * 
 */
public class ItemCommentEntity {
	@Column (name="content")
	private String content;//评论内容
	@Column (name="createdDate")
	private String createdDate;//创建时间
	@Column (name="isUsernameHidden")
	private String isUsernameHidden;//用户是否匿名
	@Column (name="nickname")
	private String nickname;//用户昵称
	@Column (name="evaluation")
	private double evaluation;//评价
	@Column (name = "prop")
	private String prop;//购买商品规格
	/**设置评论内容
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取评论内容
	 *
	 * @return  the content
	 * @since   1.0.0
	*/
	
	public String getContent() {
		return content;
	}
	/**设置评论时间
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * 获取评论创建时间
	 *
	 * @return  the createdDate
	 * @since   1.0.0
	*/
	
	public String getCreatedDate() {
		return createdDate;
	}
	/**设置用户匿名显示状态
	 * @param isUsernameHidden the isUsernameHidden to set
	 */
	public void setIsUsernameHidden(String isUsernameHidden) {
		this.isUsernameHidden = isUsernameHidden;
	}
	/**
	 * 获取用户匿名显示状态
	 *
	 * @return  the isUsernameHidden
	 * @since   1.0.0
	*/
	
	public String getIsUsernameHidden() {
		return isUsernameHidden;
	}
	/**设置用户昵称
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * 获取用户昵称
	 *
	 * @return  the nickname
	 * @since   1.0.0
	*/
	
	public String getNickname() {
		return nickname;
	}
	/**设置用户评价分数
	 * @param evaluation the evaluation to set
	 */
	public void setEvaluation(double evaluation) {
		this.evaluation = evaluation;
	}
	/**
	 * 获取用户评价分数
	 *
	 * @return  the evaluation
	 * @since   1.0.0
	*/
	
	public double getEvaluation() {
		return evaluation;
	}
	/**设置商品规格
	 * @param prop the prop to set
	 */
	public void setProp(String prop) {
		this.prop = prop;
	}
	/**
	 * 获取商品规格
	 *
	 * @return  the prop
	 * @since   1.0.0
	*/
	
	public String getProp() {
		return prop;
	}
}
