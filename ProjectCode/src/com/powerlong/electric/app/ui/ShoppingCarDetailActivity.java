/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ShoppingCarDetailActivity.java
 * 
 * 2013-8-24-下午1:54:23
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.IntentUtil;

/**
 * 
 * ShoppingCarDetailActivity
 * 
 * @author: Liang Wang 2013-8-24-下午1:54:23
 * 
 * @version 1.0.0
 * 
 */
public class ShoppingCarDetailActivity extends BaseActivity {
	RelativeLayout rlAddress;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.powerlong.electric.app.ui.base.BaseActivity#onCreate(android.os.Bundle
	 * )
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_cart_address_detail);
		
		rlAddress = (RelativeLayout) findViewById(R.id.rl_shopping_cart_address_detail);
		
		rlAddress.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				IntentUtil.start_activity(ShoppingCarDetailActivity.this, AddressManageActivity.class);
			}
		});
		
	}
}
