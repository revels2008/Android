/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * BrandListAdapter.java
 * 
 * 2013年7月31日-上午11:04:30
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.entity.BrandEntity;

/**
 * 
 * BrandListAdapter
 * 
 * @author: YangCheng Miao
 * 2013年7月31日-上午11:04:30
 * 
 * @version 1.0.0
 * 
 */
public class BrandListAdapter extends BaseAdapter{

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	private List<BrandEntity> list;
	private Context mContext;
	private LayoutInflater mInflater; 
	private static HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
	private static String[] sections;// 存放存在的汉语拼音首字母
	
	public BrandListAdapter(Context context, List<BrandEntity> list)
	{
		this.mContext = context;
		this.list = list;
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
	
	public void setBrandList(List<BrandEntity> list){
		this.list = list;
	}
	
	public  String[] getSections(){
		return sections;
	}

	
	@Override
	public int getCount() {
		return list.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.brand_list_item, null);
			holder=new ViewHolder();  
			holder.icon = (ImageView)convertView.findViewById(R.id.image_item);
			holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
			holder.name = (TextView)convertView.findViewById(R.id.text_item);
			
			convertView.setMinimumHeight(100);
			convertView.setTag(holder);
			
		}else {
			
			holder = (ViewHolder)convertView.getTag();
		}
		String currentStr = list.get(position).getNameSort();
		if(currentStr.equals("GPS")){
//			if(!TextUtils.isEmpty(CurrentLocation.curLocation)){
//				holder.name.setText(CurrentLocation.curLocation);
//			}else{
//				if(CurrentLocation.status == LocationStatus.LOADING)
//					holder.name.setText(R.string.loc_status_loading);
//				else if(CurrentLocation.status == LocationStatus.FAILED){
//					holder.name.setText(R.string.loc_status_failed);
//				}
//			}
		}else{
			holder.name.setText(list.get(position).getName());
//			holder.icon.setBackgroundResource(list.get(position).getIcon());
			holder.alpha = (TextView) convertView.findViewById(R.id.alpha_brand);
		
			String previewStr = (position - 1) >= 0 ? list.get(position - 1).getNameSort() : " ";
			if (!previewStr.equals(currentStr))
			{	
				convertView.setMinimumHeight(40);
				holder.alpha.setVisibility(View.VISIBLE);
				holder.alpha.setText(currentStr);
			} else{
				convertView.setMinimumHeight(80);
				holder.alpha.setVisibility(View.GONE);
			}
		}
		return convertView;
	
	}
	
	public final class ViewHolder{
		public ImageView icon;
		public TextView name;
		public TextView alpha;
	}
}
