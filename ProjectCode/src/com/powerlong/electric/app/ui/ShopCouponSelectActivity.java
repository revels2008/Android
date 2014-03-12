/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ShopCouponSelectActivity.java
 * 
 * 2013年8月30日-下午7:26:59
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.CartCountEntity;
import com.powerlong.electric.app.entity.CashCouponEntity;
import com.powerlong.electric.app.entity.PlCashCouponEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.ui.model.ViewItem;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.widget.PlTableView;
import com.powerlong.electric.app.widget.PlTableView.ClickListener;

/**
 * 
 * ShopCouponSelectActivity
 * 
 * @author: YangCheng Miao
 * 2013年8月30日-下午7:26:59
 * 
 * @version 1.0.0
 * 
 */
public class ShopCouponSelectActivity extends BaseActivity implements OnClickListener {
	private PlTableView tableView;
	private TextView tvPrice;
	private TextView tvDescription;
	private TextView title;
	ImageView ivSelect;
	ImageView ivReturn;
	private int shopId;
	private int couponIndex;
	private String[] coupon;
	private double[] priceSelected;
 	private int positionShop;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settle_account_body);
		
		Bundle bundle = getIntent().getExtras(); 
		shopId = bundle.getInt("shopId");//
		positionShop = bundle.getInt("itemIndex");
		tableView = new PlTableView(getBaseContext(),null);
		getView();
//		getData();
		updateView();
	}

	private void getView(){
		title = (TextView) findViewById(R.id.txTitle);
		title.setText("商家抵用券");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(this);
	}
	/**
	 * getData(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void getData() {
		showLoadingDialog(null);
//		DataUtil.getCartCountData(mServerConnectionHandler);
	}
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("CouponSelectActivity", "msg.what = " + msg.what);
			LogUtil.d("CouponSelectActivity", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				updateView();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
			// mpProgressBar.setVisibility(View.GONE);
		}

	};
	private boolean isFirstGetIn= true;
	

	private void updateView() {
		HashMap<Integer, ArrayList<CashCouponEntity>> entities = DataCache.CashCouponListCache;		
		List<CashCouponEntity> mListCoupon = new ArrayList<CashCouponEntity>();
		CashCouponEntity none = new CashCouponEntity();
		none.setName("不使用");
		List<CartCountEntity> mListCount = DataCache.CartCountEntityCache;
		CashCouponEntity entity;
		if (entities == null || entities.size() <= 0) {
			return;
		}
		mListCoupon = entities.get(shopId);
		if(mListCoupon.size() == 0 || !mListCoupon.get(mListCoupon.size()-1).getName().equals("不使用")){
			mListCoupon.add(none);
		}
		RelativeLayout child[] = new RelativeLayout[mListCoupon.size()];
		CustomClickListener listener = new CustomClickListener();
		tableView.setClickListener(listener);
		coupon = new String[mListCoupon.size()];
		priceSelected = new double[mListCoupon.size()];

		LayoutInflater mInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for (int i = 0; i < mListCoupon.size(); i++) {
			child[i] = (RelativeLayout) mInflater.inflate(R.layout.settle_account_coupon_item, null);
			
			couponIndex = i;
			
			entity = mListCoupon.get(i);
			final String price = entity.getPrice()+"";
			final String description = entity.getName();
			tvPrice = (TextView) child[i]
					.findViewById(R.id.tv_coupon_price);
			tvDescription = (TextView) child[i]
					.findViewById(R.id.tv_coupon_description);
			tvPrice.setText(price.equals("0.0")?"":price);
			// tvNavigationCategory.setText(floorCategory[i]);
			tvDescription.setText(description);
			ivSelect = (ImageView) child[i].findViewById(R.id.iv_coupon_select);
			if (positionShop == i) {
				ivSelect.setVisibility(View.VISIBLE);
				tvPrice.setTextColor(Color.RED);
			} else {
				ivSelect.setVisibility(View.GONE);
			}
			ViewItem viteItem = new ViewItem(child[i]);
//			viteItem.setClickable(false);
			tableView.addViewItem(viteItem);
			
			coupon[i] = price + description;  
			priceSelected[i] = entity.getPrice();
			/*child[i].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String shopCoupon = price + description;
					Intent data = new Intent();
					data.putExtra("shopCoupon", shopCoupon);
					setResult(Constants.ResultType.RESULT_FROM_SHOP_COUPON, data);
					finish();
					
				}
			});*/
			
		}
//		String totalPrice = mListCount.get
		ScrollView sv = (ScrollView) findViewById(R.id.sv_settle_account);
		sv.addView(tableView);

		tableView.commit();
		
	}
	
	private class CustomClickListener implements ClickListener {

		/* (non-Javadoc)
		 * @see com.powerlong.electric.app.widget.PlTableView.ClickListener#onClick(int)
		 */
		@Override
		public void onClick(int index) {
			//if (index == couponIndex) {
				Intent data = new Intent();
				data.putExtra("shopCoupon", coupon[index]);
				data.putExtra("positionShop", index);
				data.putExtra("price", priceSelected[index]);
				setResult(Constants.ResultType.RESULT_FROM_SHOP_COUPON, data);
				finish();
			//}
			
		}
		
	}
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.ivReturn:
			finish();
			break;
		}
	}

}
