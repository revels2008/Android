/**
 * 宝龙电商
 * com.powerlong.electric.app.entity
 * SearchEntity.java
 * 
 * 2013-8-5-下午01:26:08
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * SearchEntity
 * 
 * @author: Liang Wang
 * 2013-8-5-下午01:26:08
 * 
 * @version 1.0.0
 * 
 */
public class SearchEntity {
	private int mImage = 0;
	private String mTitle = null;
	private String mSummary = null;
	private List<String> mDetails = new ArrayList<String>();
	
	public SearchEntity(int img, String title, String summary, List<String> details){
		mImage = img;
		mTitle = title;
		mSummary = summary;
		mDetails.addAll(details);
	}
	
	public int getImg(){
		return mImage;
	}
	
	public String getTitle(){
		return mTitle;
	}
	
	public String getSummary(){
		return mSummary;
	}
	
	public List<String> getDetails(){
		return mDetails;
	}
}
