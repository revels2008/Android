/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * PaySelectActivity.java
 * 
 * 2013年8月31日-下午1:55:21
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.LogisticsEntity;
import com.powerlong.electric.app.entity.PayEntity;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.ui.model.ViewItem;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.widget.PlTableView;
import com.powerlong.electric.app.widget.PlTableView.ClickListener;

/**
 * 
 * PaySelectActivity
 * 
 * @author: YangCheng Miao
 * 2013年8月31日-下午1:55:21
 * 
 * @version 1.0.0
 * 
 */
public class PaySelectActivity extends BaseActivity implements OnClickListener {
	private PlTableView tableView;
	private TextView title;
	private TextView tvPayName;
	private TextView tvPayContent;
	private ImageView ivSelect;
	private ScrollView sv;
	private ImageView ivBack;
	private ListView lvPay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settle_account_logistic);
		
		findView();
		tableView = new PlTableView(getBaseContext(), null);
		createList();
		tableView.commit();
	}

	/**
	 * findView(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void findView() {
		ivBack = (ImageView) findViewById(R.id.ivReturn);
		ivBack.setOnClickListener(this);	
		title = (TextView) findViewById(R.id.txTitle);
		title.setText("支付方式");
		lvPay = (ListView) findViewById(R.id.lv_settle_account);
		
	}

	/**
	 * createList(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void createList() {
	
	/*	CustomClickListener listener = new CustomClickListener();
		tableView.setClickListener(listener);
	
		LayoutInflater mInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout child0 = (RelativeLayout) mInflater.inflate(
				R.layout.settle_account_pay_item, null);
		tvPayName = (TextView) child0.findViewById(R.id.tv_pay_name);
		tvPayName.setText("在线支付");
		tvPayContent = (TextView) child0.findViewById(R.id.tv_pay_content);
		tvPayContent.setText("安全、便捷、保障");
//		ivSelect = (ImageView) child0.findViewById(R.id.iv_select);
		ViewItem viewItem0 = new ViewItem(child0);
		tableView.addViewItem(viewItem0);
		
		RelativeLayout child1 = (RelativeLayout) mInflater.inflate(
				R.layout.settle_account_pay_item, null);
		tvPayName = (TextView) child1.findViewById(R.id.tv_pay_name);
		tvPayName.setText("货到付款");
		tvPayContent = (TextView) child1.findViewById(R.id.tv_pay_content);
		tvPayContent.setText("无需注册账户、无需开通网银");
//		ivSelect = (ImageView) child1.findViewById(R.id.iv_select);
//		ivSelect.setVisibility(View.GONE);
		ViewItem viewItem1 = new ViewItem(child1);
		tableView.addViewItem(viewItem1);
		
		RelativeLayout child2 = (RelativeLayout) mInflater.inflate(
				R.layout.settle_account_pay_item, null);
		tvPayName = (TextView) child2.findViewById(R.id.tv_pay_name);
		tvPayName.setText("支付宝WAP支付");
		tvPayContent = (TextView) child2.findViewById(R.id.tv_pay_content);
		tvPayContent.setText("支持支付宝余额、卡通");
//		ivSelect = (ImageView) child1.findViewById(R.id.iv_select);
//		ivSelect.setVisibility(View.GONE);
		ViewItem viewItem2 = new ViewItem(child2);
		tableView.addViewItem(viewItem2);
		
		sv = (ScrollView) findViewById(R.id.sv_settle_account);
		sv.addView(tableView);*/
		
		final ArrayList<PayEntity> payList = DataCache.payListCache;
		final List<String> mList = new ArrayList<String>();
		final List<Integer> mIdList = new ArrayList<Integer>();
		for(int i=0; i<payList.size(); i++) {
			PayEntity entity = payList.get(i);
			mList.add(entity.getName());
			mIdList.add(entity.getPayId());
		}
		lvPay.setAdapter(new ArrayAdapter<String>(this, R.layout.online_user_list_item, R.id.online_user_list_item_textview, mList));
		lvPay.setMinimumHeight((int) (80 * Constants.displayWidth / 480));
		
		lvPay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String pay = mList.get(arg2);
				Intent data = new Intent();
				data.putExtra("pay", pay);
				data.putExtra("payId", mIdList.get(arg2));
				setResult(Constants.ResultType.RESULT_FROM_PAY, data);
				finish();
			}
		});
	}
	
	private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(int index) {
			switch (index) {
			case 0:
				String pay = "在线支付";
				Intent data = new Intent();
				data.putExtra("pay", pay);
				setResult(Constants.ResultType.RESULT_FROM_PAY, data);
				finish();
				break;
			case 1:
				String pay1 = "货到付款";
				Intent data1 = new Intent();
				data1.putExtra("pay", pay1);
				setResult(Constants.ResultType.RESULT_FROM_PAY, data1);
				finish();
				break;
			case 2:
				String pay2 = "支付宝WAP支付";
				Intent data2 = new Intent();
				data2.putExtra("pay", pay2);
				setResult(Constants.ResultType.RESULT_FROM_PAY, data2);
				finish();	
				break;
			}
		}

	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivReturn:
			finish();
			break;
		}
		
	}
}


