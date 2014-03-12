/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * CacheImageUtil.java
 * 
 * 2014年1月15日-下午4:32:20
 *  2014宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import android.os.Build;

/**
 * 
 * CacheImageUtil
 * 
 * @author: fancy
 * 2014年1月15日-下午4:32:20
 * 
 * @version 1.0.0
 * 
 */
public class CacheImageUtil {
	public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= 11;
    }
}
