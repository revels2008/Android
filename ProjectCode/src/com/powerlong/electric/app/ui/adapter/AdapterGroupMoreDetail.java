/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.adapter
 * AdapterGroupMoreDetail.java
 * 
 * 2013-10-30-下午02:48:58
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.domain.DomainGroupMoreDetail;

/**
 * 
 * AdapterGroupMoreDetail
 * 
 * @author: hegao
 * 2013-10-30-下午02:48:58
 * 
 * @version 1.0.0
 * 
 */
public class AdapterGroupMoreDetail extends BaseAdapter {
	private Context context;
	private List<DomainGroupMoreDetail> list_temp = new ArrayList<DomainGroupMoreDetail>();
	private List<DomainGroupMoreDetail> list ;
	private LayoutInflater layoutInflater;
	
	public AdapterGroupMoreDetail(Context context){
		this.context = context;
		list = list_temp;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public List<DomainGroupMoreDetail> getList() {
		return list;
	}

	public void setList(List<DomainGroupMoreDetail> list) {
		if(list == null){
			this.list = list_temp;
		}else{
			this.list = list;
		}
		this.notifyDataSetChanged();
	}
	
	public void notifyDataChanged(){
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.groupon_list_item, null);
			vh.floor_shopimage = (ImageView) convertView.findViewById(R.id.floor_shopimage);
			vh.floor_title_shop = (TextView) convertView.findViewById(R.id.floor_title_shop);
			vh.floor_products_plprice = (TextView) convertView.findViewById(R.id.floor_products_plprice);
			vh.floor_products_listprice = (TextView) convertView.findViewById(R.id.floor_products_listprice);
			vh.floor_sell_num = (TextView) convertView.findViewById(R.id.floor_sell_num);
			convertView.setTag(vh);
		}
		vh = (ViewHolder) convertView.getTag();
		vh.floor_title_shop.setText(list.get(position).getItemName());
		vh.floor_products_plprice.setText("￥："+list.get(position).getPlPrice());
		vh.floor_products_listprice.setText("  ￥："+list.get(position).getListPrice());
		vh.floor_sell_num.setText(list.get(position).getSellNum());
		FinalBitmap fb = FinalBitmap.create(context);
		fb.configDiskCachePath(Constants.IMG_CACHE_PATH);
		fb.display(vh.floor_shopimage, list.get(position).getPicturePath());
		return convertView;
	}
	private class ViewHolder{
		public ImageView floor_shopimage;
		public TextView floor_title_shop;
		public TextView floor_products_plprice;
		public TextView floor_products_listprice;
		public TextView floor_sell_num;
	}
}
