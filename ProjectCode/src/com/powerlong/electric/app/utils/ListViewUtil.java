/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * ListViewUtil.java
 * 
 * 2013年8月30日-上午9:12:09
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 
 * ListViewUtil
 * 
 * @author: YangCheng Miao 2013年8月30日-上午9:12:09
 * 
 * @version 1.0.0
 * 
 */
public class ListViewUtil {
//	public static void setListViewHeightBasedOnChildren(ListView listView) {
//		ListAdapter listAdapter = listView.getAdapter();
//		if (listAdapter == null) {
//			// pre-condition
//			return;
//		}
//
//		int totalHeight = 0;
//		for (int i = 0; i < listAdapter.getCount(); i++) {
//			View listItem = listAdapter.getView(i, null, listView);
//			listItem.measure(0, 0);
//			totalHeight += listItem.getMeasuredHeight();
//		}
//
//		ViewGroup.LayoutParams params = listView.getLayoutParams();
//		params.height = totalHeight
//				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//		listView.setLayoutParams(params);
//	}
	public static void setListViewHeightBasedOnChildren(ListView listView) {  
        //获取ListView对应的Adapter   
    ListAdapter listAdapter = listView.getAdapter();   
    if (listAdapter == null) {  
        // pre-condition   
        return;  
    }  

    int totalHeight = 0;  
    for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目   
        View listItem = listAdapter.getView(i, null, listView);  
        listItem.measure(0, 0);  //计算子项View 的宽高   
        totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度   
    }  

    ViewGroup.LayoutParams params = listView.getLayoutParams();  
    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
    //listView.getDividerHeight()获取子项间分隔符占用的高度   
    //params.height最后得到整个ListView完整显示需要的高度   
    listView.setLayoutParams(params);  
}  

	public static void setListViewHeight(ListView listView, int count, int height) {  
        //获取ListView对应的Adapter   
    ListAdapter listAdapter = listView.getAdapter();   
    if (listAdapter == null) {  
        // pre-condition   
        return;  
    }  

    int totalHeight = 0;  
    for (int i = 0, len = count; i < len; i++) {   //listAdapter.getCount()返回数据项的数目   
        View listItem = listAdapter.getView(i, null, listView);  
        listItem.measure(0, 0);  //计算子项View 的宽高   
        totalHeight += height;  //统计所有子项的总高度   
    }  
    listView.setMinimumHeight(height);
    ViewGroup.LayoutParams params = listView.getLayoutParams();  
    params.height = totalHeight + (listView.getDividerHeight() * (count - 1));  
    //listView.getDividerHeight()获取子项间分隔符占用的高度   
    //params.height最后得到整个ListView完整显示需要的高度   
    listView.setLayoutParams(params);  
	}  
}
