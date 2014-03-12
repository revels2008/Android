/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * SubNameUtil.java
 * 
 * 2013年9月11日-上午9:14:39
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

/**
 * 
 * SubNameUtil
 * 
 * @author: YangCheng Miao
 * 2013年9月11日-上午9:14:39
 * 
 * @version 1.0.0
 * 
 */
public class SubNameUtil {
	public static String subName(String name) {	
		return name.substring(0,1) + "**" + name.substring(name.length()-1);		
	}
}
