/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * OrderDetailActivity.java
 * 
 * 2013年9月14日-上午11:52:00
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import android.content.Context;
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
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.LogUtil;

/**
 * 
 * OrderDetailActivity
 * 
 * @author: YangCheng Miao
 * 2013年9月14日-上午11:52:00
 * 
 * @version 1.0.0
 * 
 */
public class OrderDetailActivity extends BaseActivity implements OnClickListener {
	private ScrollView sv;
	private ImageView ivReturn;
	private TextView usrName;
	private TextView usrPhoneNum;
	private TextView usrAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);
		
		findView();
		getData();
	}

	private void findView() {
		sv = (ScrollView) findViewById(R.id.sv_order_detail);
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(this);
	}
	
	private void getData() {
		// setProgressBarVisibility(true);
		//DataUtil.getCartCountData(mServerConnectionHandler);
	}
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("OrderDetailActivity", "msg.what = " + msg.what);
			LogUtil.d("OrderDetailActivity", "msg.arg1 = " + msg.arg1);
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
	
	private void updateView() {
		LayoutInflater mInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		RelativeLayout rl = new RelativeLayout(getBaseContext());
		rl = (RelativeLayout) mInflater.inflate(R.layout.default_address, null);
		usrName = (TextView) rl.findViewById(R.id.tv_username);
		usrPhoneNum = (TextView) rl.findViewById(R.id.tv_userphone);
		usrAddress = (TextView) rl.findViewById(R.id.tv_useraddr);
		
		LinearLayout llOut = new LinearLayout(sv.getContext());
		llOut.setOrientation(LinearLayout.VERTICAL);
		llOut.addView(rl);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		
	}
}
