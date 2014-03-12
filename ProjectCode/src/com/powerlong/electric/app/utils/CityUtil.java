/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * CityUtil.java
 * 
 * 2013-7-30-下午04:17:34
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

/**
 * 
 * CityUtil
 * 
 * @author: Liang Wang
 * 2013-7-30-下午04:17:34
 * 
 * @version 1.0.0
 * 
 */
public class CityUtil {
	private String CityName; //城市名字
	private String NameSort; //城市首字母

	public String getCityName()
	{
		return CityName;
	}

	public void setCityName(String cityName)
	{
		CityName = cityName;
	}

	public String getNameSort()
	{
		return NameSort;
	}

	public void setNameSort(String nameSort)
	{
		NameSort = nameSort;
	}
}
