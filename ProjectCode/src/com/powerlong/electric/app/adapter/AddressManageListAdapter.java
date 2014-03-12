/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * AddressManageListAdapter.java
 * 
 * 2013年8月27日-下午5:25:43
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.AddressEntity;
import com.powerlong.electric.app.entity.UserAddressEntity;
import com.powerlong.electric.app.ui.AddressEditActivity;
import com.powerlong.electric.app.ui.AddressManageActivity;
import com.powerlong.electric.app.ui.ShopCouponSelectActivity;
import com.powerlong.electric.app.ui.ShoppingCartSettleAccountActivity;

/**
 * 
 * AddressManageListAdapter
 * 
 * @author: YangCheng Miao
 * 2013年8月27日-下午5:25:43
 * 
 * @version 1.0.0
 * 
 */
public class AddressManageListAdapter extends BaseAdapter {
	private Activity mActivity;
	private List<UserAddressEntity> AddressEntities;
	private Context mContext;
	private LayoutInflater mInflater; 
	
	public AddressManageListAdapter(Activity activity, Context context, List<UserAddressEntity> entities)
	{
		this.mActivity = activity;
		this.mContext = context;
		AddressEntities = entities;
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return AddressEntities.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return AddressEntities.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		String area;
//		final List<UserAddressEntity> usrAddressEntities= DataCache.UserAddressListCache;
		final UserAddressEntity entity = AddressEntities.get(position);
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.address_manage_item, null);
			holder=new ViewHolder();  
			holder.edit = (ImageView)convertView.findViewById(R.id.iv_edit_address);
			holder.name = (TextView) convertView.findViewById(R.id.tv_address_name);
			holder.phoneNum = (TextView) convertView.findViewById(R.id.tv_address_phoneNum);
			holder.city = (TextView)convertView.findViewById(R.id.tv_address_city);
			holder.address = (TextView)convertView.findViewById(R.id.tv_address);
			holder.select = (ImageView)convertView.findViewById(R.id.iv_address_select);
			
			convertView.setMinimumHeight(134);
			convertView.setTag(holder);
			
		}else {
			
			holder = (ViewHolder)convertView.getTag();
		}
			
			holder.edit.setBackgroundResource(R.drawable.btn_revise);
			holder.name.setText(entity.getConsignee());
			holder.phoneNum.setText(entity.getMobile());
			holder.city.setText(AddressManageActivity.getNameById(Integer.parseInt(entity.getProvince()))
					+ AddressManageActivity.getNameById(Integer.parseInt(entity.getCity()))
							+ AddressManageActivity.getNameById(Integer.parseInt(entity.getArea())));
			if(entity.getCommunityName() != null) {
				holder.address.setText(entity.getCommunityName() + "  " + entity.getAddress());	
			} else {
				holder.address.setText(entity.getAddress());	
			}
					
			holder.select.setBackgroundResource(R.drawable.confirm);
			convertView.setMinimumHeight(134);
			
			holder.edit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				
					Bundle bundle = new Bundle();
   					bundle.putString("nameManage", entity.getConsignee());
   					bundle.putString("phoneNumManage", entity.getMobile());
   					bundle.putString("cityManage", entity.getProvince() + entity.getCity() + entity.getArea() + "  ");
   					bundle.putString("communityName", entity.getCommunityName());
   					bundle.putString("communityId", entity.getCommunityId());
   					bundle.putString("addressManage", entity.getAddress());
   					bundle.putInt("positonManage", position);
   					bundle.putLong("addressId", entity.getAddressId());
   					bundle.putString("provinceId", entity.getProvince());
   					bundle.putString("cityId", entity.getCity());
   					bundle.putString("areaId", entity.getArea());
   					
   					Intent intent = new Intent(mActivity, AddressEditActivity.class);
   					intent.putExtras(bundle);
   					mActivity.startActivityForResult(intent , Constants.ResultType.RESULT_FROM_MANAGE_ADDRESS);
				}
			});
			
		return convertView;
	}
	public final class ViewHolder{
		public ImageView edit;
		public TextView name;
		public TextView phoneNum;
		public TextView city;
		public TextView address;
		public ImageView select;
	}
	
	
/*	public void addListener(View convertView) {     
	       ((ImageView)convertView.findViewById(R.id.iv_edit_address)).setOnClickListener(     
	               new View.OnClickListener() {     
	                   @Override     
	                   public void onClick(View v) {     
//	                         Toast.makeText(mcontent, "ok", Toast.LENGTH_LONG).show();   
	                	Bundle bundle = new Bundle();
	   					bundle.putString("name", convertView.findViewById(R.id.et_name).get);
	   					Intent intent = new Intent(mContext, ShopCouponSelectActivity.class);
	   					intent.putExtras(bundle);
	   					shopIndex = (Integer) v.getTag();
	   					startActivityForResult(intent , Constants.ResultType.RESULT_FROM_SHOP_COUPON);
	                   }     
	               });     
	    }   */

}
