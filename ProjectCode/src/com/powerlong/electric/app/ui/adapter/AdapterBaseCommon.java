/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.adapter
 * AdapterBaseCommon.java
 * 
 * 2013-10-31-下午05:25:11
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.domain.DomainGroupMoreDetail;
import com.powerlong.electric.app.utils.BitmapUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * 
 * AdapterBaseCommon
 * 
 * @author: hegao
 * 2013-10-31-下午05:25:11
 * 
 * @version 1.0.0
 * 
 */
public abstract class AdapterBaseCommon<T> extends BaseAdapter {
	protected Context context;
	private List<T> list_temp = new ArrayList<T>();
	protected List<T> list_data ;
	protected LayoutInflater layoutInflater;
	private FinalBitmap fb;
	
	public AdapterBaseCommon(Context context){
		this.context = context;
		fb =BitmapUtils.getFinalBitmap(context);
		list_data = list_temp;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	protected void asynchronousLoadImage(ImageView imgView,String imgUrl){
		fb.display(imgView, imgUrl);
	}
	
	public void clearData(){
		list_data.clear();
		this.notifyDataSetChanged();
	}
	
	public T getDomain(int i){
		return list_data.get(i);
	}
	
	public void addData(List<T> newData){
		this.list_data.addAll(newData);
		this.notifyDataSetChanged();
	}

	public List<T> getList() {
		return list_data;
	}

	public void setList(List<T> list) {
		if(null == list){
			this.list_data = list_temp;
		}else{
			this.list_data = list;
		}
		this.notifyDataSetChanged();
	}
	

	@Override
	public int getCount() {
		return list_data.size();
	}

	@Override
	public Object getItem(int i) {
		return i;
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

}
