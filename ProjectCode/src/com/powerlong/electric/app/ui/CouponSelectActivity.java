/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * CouponSelectActivity.java
 * 
 * 2013年8月30日-下午4:54:54
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.CartShopListEntity;
import com.powerlong.electric.app.entity.PlCashCouponEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.ui.model.ViewItem;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.widget.PlTableView;
import com.powerlong.electric.app.widget.PlTableView.ClickListener;

/**
 * 
 * CouponSelectActivity
 * 
 * @author: YangCheng Miao
 * 2013年8月30日-下午4:54:54
 * 
 * @version 1.0.0
 * 
 */
public class CouponSelectActivity extends BaseActivity implements OnClickListener {
	private PlTableView tableView;
	private TextView tvPrice;
	private TextView tvDescription;
	private TextView title;
	ImageView ivSelect;
	ImageView ivReturn;
	private double[] price;
	private String[] description;
	private Map<Integer, ImageView> ivMap = new HashMap<Integer, ImageView>();
	private int positionNow;
	private double totalPrice;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settle_account_body);
		
		Bundle bundle = getIntent().getExtras();
		positionNow = bundle.getInt("positionNow");
		totalPrice = bundle.getDouble("price");
		tableView = new PlTableView(getBaseContext(),null);
		getView();
		getData();
	}

	private void getView(){
		title = (TextView) findViewById(R.id.txTitle);
		title.setText("宝龙抵用券");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(this);
	}
	
	private String getParam() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("TGC", DataCache.UserDataCache.get(0).getTGC());
			obj.put("price", totalPrice);
			return obj.toString();
		}catch (JSONException e) {
			e.printStackTrace();
			return null;
		}		
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
		DataUtil.getPlCashCouponListData(mServerConnectionHandler, getParam());		
	}
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("CouponSelectActivity", "msg.what = " + msg.what);
			LogUtil.d("CouponSelectActivity", "msg.arg1 = " + msg.arg1);
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
			dismissLoadingDialog();
		}

	};
	

	private void updateView() {
		List<PlCashCouponEntity> entities = DataCache.PLCashCouponListCache;
		PlCashCouponEntity none = new PlCashCouponEntity();
		none.setCouponId(1);
		none.setId(1);
		none.setIsActive("1");
		none.setName("不使用");
		entities.add(0,none);
		PlCashCouponEntity entity;
		RelativeLayout child[] = new RelativeLayout[entities.size()];

		CustomClickListener listener = new CustomClickListener();
		tableView.setClickListener(listener);

		LayoutInflater mInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		price = new double[entities.size()];
		description = new String[entities.size()];
		
		for (int i = 0; i < entities.size(); i++) {
			child[i] = (RelativeLayout) mInflater.inflate(R.layout.settle_account_coupon_item, null);
			entity = entities.get(i);
			tvPrice = (TextView) child[i]
					.findViewById(R.id.tv_coupon_price);
			tvDescription = (TextView) child[i]
					.findViewById(R.id.tv_coupon_description);
			tvPrice.setText(entity.getPrice() == 0.0 ? "" : entity.getPrice()+"");
			// tvNavigationCategory.setText(floorCategory[i]);
			tvDescription.setText(entity.getName());
			ivSelect = (ImageView) child[i].findViewById(R.id.iv_coupon_select);
			if (positionNow == i) {
				ivSelect.setVisibility(View.VISIBLE);
			} else {
				ivSelect.setVisibility(View.GONE);
			}
			
			ivMap.put(i, ivSelect);
			price[i] = entity.getPrice();
			description[i] = entity.getName();
			
			ViewItem viteItem = new ViewItem(child[i]);
			tableView.addViewItem(viteItem);
		}

		ScrollView sv = (ScrollView) findViewById(R.id.sv_settle_account);
		sv.addView(tableView);

		tableView.commit();
	}
	
	private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(int index) {
			Intent data = new Intent(); 
			data.putExtra("price", price[index]);
			data.putExtra("description", description[index]);
			data.putExtra("positionCoupon", index);
			setResult(Constants.ResultType.RESULT_FROM_COUPON, data);
			ivMap.get(index).setVisibility(View.VISIBLE);
			finish();
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
