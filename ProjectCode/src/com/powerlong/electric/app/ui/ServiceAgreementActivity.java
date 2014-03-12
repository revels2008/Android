/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ServiceAgreementActivity.java
 * 
 * 2013年11月20日-下午3:43:12
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.ui.base.BaseActivity;

/**
 * 
 * ServiceAgreementActivity
 * 
 * @author: YangCheng Miao
 * 2013年11月20日-下午3:43:12
 * 
 * @version 1.0.0
 * 
 */
public class ServiceAgreementActivity extends BaseActivity {
	private ImageView ivReturn;
	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_agreement);
		
		ivReturn = (ImageView) findViewById(R.id.iv_agree_Return);
		ivReturn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}
	

}
