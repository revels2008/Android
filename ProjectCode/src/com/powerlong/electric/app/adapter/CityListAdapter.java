/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * CityListAdapter.java
 * 
 * 2013-7-30-下午04:23:16
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants.LocationStatus;
import com.powerlong.electric.app.location.CurrentLocation;
import com.powerlong.electric.app.utils.CityUtil;

/**
 * 
 * CityListAdapter
 * 
 * @author: Liang Wang
 * 2013-7-30-下午04:23:16
 * 
 * @version 1.0.0
 * 
 */
public class CityListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<CityUtil> list;
	private static HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
	private static String[] sections;// 存放存在的汉语拼音首字母
	
	public CityListAdapter(Context context, List<CityUtil> list)
	{

		this.inflater = LayoutInflater.from(context);
		this.list = list;
		alphaIndexer = new HashMap<String, Integer>();
		sections = new String[list.size()];

		for (int i = 0; i < list.size(); i++)
		{
			// 当前汉语拼音首字母
			// getAlpha(list.get(i));
			String currentStr = list.get(i).getNameSort();
			// 上一个汉语拼音首字母，如果不存在为“ ”
			String previewStr = (i - 1) >= 0 ? list.get(i - 1).getNameSort() : " ";
			if (!previewStr.equals(currentStr))
			{
				String name = list.get(i).getNameSort();
				alphaIndexer.put(name, i);
				sections[i] = name;
			}
		}

	}
	
	public HashMap<String, Integer> getAlphaIndexer(){
		return alphaIndexer;
	}
	
	public void setCityList(List<CityUtil> list){
		this.list = list;
	}
	
	public  String[] getSections(){
		return sections;
	}

	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public Object getItem(int position)
	{
		return list.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		String currentStr = list.get(position).getNameSort();
		if(currentStr.equals("GPS")){
			if(!TextUtils.isEmpty(CurrentLocation.curLocation)){
				holder.name.setText(CurrentLocation.curLocation);
			}else{
				if(CurrentLocation.status == LocationStatus.LOADING)
					holder.name.setText(R.string.loc_status_loading);
				else if(CurrentLocation.status == LocationStatus.FAILED){
					holder.name.setText(R.string.loc_status_failed);
				}
			}
		}else{
			holder.name.setText(list.get(position).getCityName());
		}
		String previewStr = (position - 1) >= 0 ? list.get(position - 1).getNameSort() : " ";
		if (!previewStr.equals(currentStr))
		{	
			convertView.setMinimumHeight(40);
			holder.alpha.setVisibility(View.VISIBLE);
			holder.alpha.setText(currentStr);
		} else
		{
			convertView.setMinimumHeight(80);
			holder.alpha.setVisibility(View.GONE);
		}
		
		holder.alpha.setEnabled(false);
		
		return convertView;
	}

	private class ViewHolder
	{
		TextView alpha;
		TextView name;
	}
}
