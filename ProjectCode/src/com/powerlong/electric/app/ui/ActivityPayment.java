/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ActivityPayment.java
 * 
 * 2013-11-12-下午10:00:43
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.powerlong.electric.app.domain.DomainChannelItem;
import com.powerlong.electric.app.ui.adapter.AdapterPayList;
import com.powerlong.electric.app.ui.base.BaseActivity;

/**
 * 
 * ActivityPayment
 * 
 * @author: He Gao
 * 2013-11-12-下午10:00:43
 * 
 * @version 1.0.0
 * 
 */
public class ActivityPayment extends BaseActivity {
	private Context context;
	private ListView listView_pay_type;
	private AdapterPayList<DomainChannelItem> adapterPayList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initService();
	}

	private void initService() {
		context = this;
		adapterPayList = new AdapterPayList<DomainChannelItem>(context);
	}
	
	
	
	
}
