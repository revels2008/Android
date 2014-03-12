/**
 * 宝龙电商
 * com.powerlong.electric.app.adapter
 * MyFavourItemAdapter.java
 * 
 * 2013年9月16日-下午7:53:29
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.volleytest.cache.BitmapCache;
import com.google.zxing.common.StringUtils;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.R.bool;
import com.powerlong.electric.app.adapter.MyOrderAdapter.ViewHolder;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.entity.ItemFavourListEntity;
import com.powerlong.electric.app.entity.ItemListEntity;
import com.powerlong.electric.app.entity.OrderListEntity;
import com.powerlong.electric.app.utils.StringUtil;

/**
 * 
 * MyFavourItemAdapter
 * 
 * @author: Liang Wang
 * 2013年9月16日-下午7:53:29
 * 
 * @version 1.0.0
 * 
 */
public class MyFavourItemAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<ItemFavourListEntity> mValues;
	private ImageLoader imageLoader;
	private RequestQueue mRequestQueue;
	private ItemListEntity UserEntity;
	private Handler mHandler;
	private List<ItemFavourListEntity> checkedEntites;
	private ItemFavourListEntity entity;
    private int mpostion;
    private HashMap<Integer, Boolean> checkedState;
	public MyFavourItemAdapter(Context context, List<ItemFavourListEntity> values,Handler handler) {
		mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mValues = values;
		mRequestQueue = Volley.newRequestQueue(context);
		imageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
		mHandler=handler;
		checkedState=new HashMap<Integer, Boolean>();
		checkedEntites=new ArrayList<ItemFavourListEntity>();
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mValues != null)
			return mValues.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (position == getCount() - 1 || mValues == null) {
			return null;
		}
		return mValues.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if (position == getCount() - 1 || mValues == null) {
			return 0;
		}
		return mValues.get(position).getFavourId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_myfavour_goods, null);
			holder = new ViewHolder();
			holder.ck_commodity=(CheckBox) convertView.findViewById(R.id.ck_commodity);
			
			holder.iv_image = (NetworkImageView) convertView
					.findViewById(R.id.iv_collection_logo);
			
			holder.shopping_cart_list_brand=(TextView)convertView.findViewById(R.id.shopping_cart_list_brand);
			holder.shopping_cart_list_describtion=(TextView)convertView.findViewById(R.id.shopping_cart_list_describtion);
			
			holder.shopping_cart_list_price=(TextView)convertView.findViewById(R.id.shopping_cart_list_price);
			holder.floor_evaluation=(RatingBar)convertView.findViewById(R.id.floor_evaluation);
			holder.shopping_cart_list_price_org=(TextView)convertView.findViewById(R.id.shopping_cart_list_price_org);
			holder.shopping_cart_list_sales=(TextView)convertView.findViewById(R.id.shopping_cart_list_sales);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		 entity = mValues.get(position);
		 mpostion=position;
		if(!StringUtil.isNullOrEmpty(entity.getPicturePath())){
			holder.iv_image.setImageUrl(entity.getPicturePath(), imageLoader);
		}else{
			holder.iv_image.setImageResource(R.drawable.pic_56);
		}
		if(!StringUtil.isNullOrEmpty(entity.getItemName())){
			holder.shopping_cart_list_brand.setText(entity.getItemName());
		}else{
			holder.shopping_cart_list_brand.setText("");
		}	
//		holder.shopping_cart_list_describtion.setText();
		if(!StringUtil.isNullOrEmpty(String.valueOf(entity.getListPrice()))){
			holder.shopping_cart_list_price.setText("￥ "+String.valueOf(entity.getListPrice()));
		}else{
			holder.shopping_cart_list_price.setText("");
		}
		if(!StringUtil.isNullOrEmpty(String.valueOf(entity.getPlPrice()))){
			holder.shopping_cart_list_price_org.setText("￥ "+String.valueOf(entity.getPlPrice()));
		}else{
			holder.shopping_cart_list_price_org.setText("");
		}
		if(!StringUtil.isNullOrEmpty(String.valueOf(entity.getSellNum()))){
			holder.shopping_cart_list_sales.setText(String.valueOf(entity.getSellNum()));
		}else{
			holder.shopping_cart_list_sales.setText("");
		}
		holder.ck_commodity.setChecked(checkedState.get(position)==null? false : true); 
		holder.ck_commodity.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				 if(arg1){
					 checkedEntites.add(entity);
					 checkedState.put(mpostion, true);
					 
				 }else{
					 if(checkedEntites.contains(entity)){
						 checkedEntites.remove(entity);
						 checkedState.remove(mpostion);
					 }
					 
				 }
				 Message message;
				 if(checkedEntites.size()>0){
					 message=mHandler.obtainMessage(0);
				 }else{
					 message= mHandler.obtainMessage(1);
				 }
				 mHandler.sendMessage(message);
				
			}
		});
		
		return convertView;
	}

	public List<ItemFavourListEntity> getCheckedEntites(){
		return checkedEntites;
	}
	class ViewHolder {
		CheckBox ck_commodity;
		TextView shopping_cart_list_brand,shopping_cart_list_describtion;
		TextView shopping_cart_list_price, shopping_cart_list_price_org, shopping_cart_list_sales;
		RatingBar floor_evaluation;
		NetworkImageView iv_image;
		Button btn;
	}
}