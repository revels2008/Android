/**
 * 宝龙电商
 * com.powerlong.electric.app.location
 * CurrentLocation.java
 * 
 * 2013-7-30-下午08:19:49
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.location;

import com.powerlong.electric.app.config.Constants.LocationStatus;

/**
 * 
 * CurrentLocation
 * 
 * @author: Liang Wang
 * 2013-7-30-下午08:19:49
 * 
 * @version 1.0.0
 * 
 */
public class CurrentLocation {
	static public int status = LocationStatus.LOADING;
	static public String curLocation ="";
	static public String myHome = "";
	static public double  lat = 0;
	static public double 	lng = 0;
}
