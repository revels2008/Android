/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.adapter
 * AdapterGroupBuyItem.java
 * 
 * 2013-10-31-下午05:22:27
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.adapter;

import java.text.DecimalFormat;

import net.tsz.afinal.FinalBitmap;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.R.layout;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.domain.DomainGroupBuyItem;
import com.powerlong.electric.app.utils.DoubleUtil;
import com.powerlong.electric.app.utils.LogUtil;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * AdapterGroupBuyItem
 * 
 * @author: hegao
 * 2013-10-31-下午05:22:27
 * 
 * @version 1.0.0
 * 
 */
public class AdapterGroupBuyList<T> extends AdapterBaseCommon<T>{
	private DecimalFormat decimalFormat;
	
	public AdapterGroupBuyList(Context context){
		super(context);
		decimalFormat = new DecimalFormat();
		decimalFormat.applyPattern("0.0");
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public View getView(int position, View convertView, ViewGroup viewgroup) {
		ViewHolder vh = null;
		if(null == convertView){
			vh = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.adapter_group_buy_item, null);
			vh.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			vh.tv_list_price = (TextView) convertView.findViewById(R.id.tv_list_price);
			vh.tv_discount = (TextView) convertView.findViewById(R.id.tv_discount);
			vh.tv_pl_price = (TextView) convertView.findViewById(R.id.tv_pl_price);
			vh.tv_sells = (TextView) convertView.findViewById(R.id.tv_sells);
			vh.img_item = (ImageView) convertView.findViewById(R.id.img_item);
			vh.iv_price_cut = (ImageView) convertView.findViewById(R.id.iv_cut_price);
			convertView.setTag(vh);
		}
		vh = (ViewHolder) convertView.getTag();
		DomainGroupBuyItem groupBuyItem = (DomainGroupBuyItem) list_data.get(position);
		vh.tv_name.setText(groupBuyItem.getName());
		double list_price = 1;
		double pl_price = 0;
		if(groupBuyItem.getPlPrice() != null && groupBuyItem.getPlPrice() != ""){
			pl_price = Double.parseDouble(groupBuyItem.getPlPrice());
		}
		if(groupBuyItem.getListPrice() != null && groupBuyItem.getListPrice() != ""){
			list_price = Double.parseDouble(groupBuyItem.getListPrice());
		}
		vh.tv_list_price.setText(Constants.moneyTag + pl_price);
//		vh.iv_price_cut.setLayoutParams(new RelativeLayout.LayoutParams(vh.tv_list_price.getWidth(),1));
		vh.tv_pl_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		vh.tv_discount.setText(DoubleUtil.subPrice(DoubleUtil.round((pl_price/list_price),2) * 10) +"折  ");
		vh.tv_pl_price.setText(""+Constants.moneyTag+list_price);
		vh.tv_sells.setText(groupBuyItem.getSellNum()+"人购买");
		String str = null ; 
		String url = groupBuyItem.getImage();
		if(url.contains("_246_246")) {
			if (url.indexOf("_246_246") != -1)
			{
				str = url.replaceAll("_246_246", "");
			}
		}
//		LogUtil.v("grouponListImageOriginal", str);
		if(str != null) {
			asynchronousLoadImage(vh.img_item, str);
		} else {
			asynchronousLoadImage(vh.img_item, url);
		}
		
		LogUtil.v("grouponListImage", groupBuyItem.getImage());
		return convertView;
	}
	
	private class ViewHolder{
		public TextView tv_name;
		private TextView tv_list_price;
		private TextView tv_discount;
		private TextView tv_pl_price;
		private TextView tv_sells;
		private ImageView img_item;
		private ImageView iv_price_cut;
	}
	
}
