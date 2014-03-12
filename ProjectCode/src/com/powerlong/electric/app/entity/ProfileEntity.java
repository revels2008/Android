/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * ProfileEntity.java
 * 
 * 2013-9-11-下午08:20:56
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import com.tgb.lk.ahibernate.annotation.Column;

/**
 * 
 * ProfileEntity
 * 
 * @author: Liang Wang
 * 2013-9-11-下午08:20:56
 * 
 * @version 1.0.0
 * 
 */
public class ProfileEntity {
	@Column (name ="TGC")
	private String TGC;//验证码
	@Column (name ="nickname")
	private String nickname;//昵称
	@Column (name ="username")
	private String username;//用户名
	@Column (name ="email")
	private String email;//邮箱
	@Column (name ="mobile")
	private String mobile;//手机号码
	@Column (name ="birthday")
	private String birthday;//生日
	@Column (name ="lastlogin")
	private String lastlogin;//最近登录
	@Column (name ="sex")
	private String sex;//性别
	/**
	 * @param tGC the tGC to set
	 */
	public void setTGC(String tGC) {
		TGC = tGC;
	}
	/**
	 * tGC
	 *
	 * @return  the tGC
	 * @since   1.0.0
	*/
	
	public String getTGC() {
		return TGC;
	}
	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * nickname
	 *
	 * @return  the nickname
	 * @since   1.0.0
	*/
	
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * username
	 *
	 * @return  the username
	 * @since   1.0.0
	*/
	
	public String getUsername() {
		return username;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * email
	 *
	 * @return  the email
	 * @since   1.0.0
	*/
	
	public String getEmail() {
		return email;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * mobile
	 *
	 * @return  the mobile
	 * @since   1.0.0
	*/
	
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * birthday
	 *
	 * @return  the birthday
	 * @since   1.0.0
	*/
	
	public String getBirthday() {
		return birthday;
	}
	/**
	 * @param lastlogin the lastlogin to set
	 */
	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}
	/**
	 * lastlogin
	 *
	 * @return  the lastlogin
	 * @since   1.0.0
	*/
	
	public String getLastlogin() {
		return lastlogin;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * sex
	 *
	 * @return  the sex
	 * @since   1.0.0
	*/
	
	public String getSex() {
		return sex;
	}
}
