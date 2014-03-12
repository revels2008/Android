/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.model
 * Mall.java
 * 
 * 2014-1-15-下午4:14:06
 *  2014宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.model;

/**
 * 
 * Mall
 * 
 * @author: Liang Wang
 * 2014-1-15-下午4:14:06
 * 
 * @version 1.0.0
 * 
 */
public class Mall {
	private String mallName;
	private int mallId;
	
	/**
	 * 创建一个新的实例 Mall.
	 *
	 * @param mallName
	 * @param mallId
	 */
	public Mall(String mallName, int mallId) {
		super();
		this.mallName = mallName;
		this.mallId = mallId;
	}

	/**
	 * mallName
	 *
	 * @return  the mallName
	 * @since   1.0.0
	 */
	
	public String getMallName() {
		return mallName;
	}

	/**
	 * @param mallName the mallName to set
	 */
	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	/**
	 * mallId
	 *
	 * @return  the mallId
	 * @since   1.0.0
	 */
	
	public int getMallId() {
		return mallId;
	}

	/**
	 * @param mallId the mallId to set
	 */
	public void setMallId(int mallId) {
		this.mallId = mallId;
	}
	
	
}
