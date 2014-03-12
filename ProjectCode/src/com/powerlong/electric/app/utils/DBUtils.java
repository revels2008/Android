/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * DBUtils.java
 * 
 * 2013-11-7-上午09:37:25
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import net.tsz.afinal.FinalDb;
import android.content.Context;

/**
 * 
 * DBUtils
 * 
 * @author: HeGao
 * 2013-11-7-上午09:37:25
 * 
 * @version 1.0.0
 * 
 */
public class DBUtils {
	public static FinalDb getDB(Context context){
		FinalDb fb = FinalDb.create(context, "pl.db", false, 2, null);
		return fb;
	}
}
