/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * CommonUtils.java
 * 
 * 2013年11月29日-下午9:26:57
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

/**
 * 
 * CommonUtils
 * 
 * @author: YangCheng Miao
 * 2013年11月29日-下午9:26:57
 * 
 * @version 1.0.0
 * 
 */
public class CommonUtils {
	private static long lastClickTime;  
    public static boolean isFastDoubleClick() {  
        long time = System.currentTimeMillis();  
        long timeD = time - lastClickTime;  
        if ( 0 < timeD && timeD < 800) {     
        	lastClickTime = time;     
            return true;   
        	
        }
		return false;
    }  
}
