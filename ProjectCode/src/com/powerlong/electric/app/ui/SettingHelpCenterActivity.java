/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * HelpCenter.java
 * 
 * 2013年9月16日-上午10:06:32
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.IntentUtil;

/**
 * 
 * HelpCenter
 * 
 * @author: Liang Wang
 * 2013年9月16日-上午10:06:32
 * 
 * @version 1.0.0
 * 
 */
public class SettingHelpCenterActivity extends BaseActivity  implements OnClickListener{
	private ImageView iv_back;
	private RelativeLayout rel_return_shows;
	private LinearLayout linear;
	private LayoutInflater mInflater;
	private LinearLayout tempLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.helpcenter);
		InitView();
	}
	private void InitView() {
		iv_back=(ImageView)findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		 rel_return_shows=(RelativeLayout)this.findViewById(R.id.return_shows);
		 rel_return_shows.setOnClickListener(this);
		
		
		 mInflater =LayoutInflater.from(this.getApplicationContext());
		 tempLayout=(LinearLayout)mInflater.inflate(R.layout.return_item_shows, null);
	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
	      switch (arg0.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.return_shows:
			IntentUtil.start_activity(this, SettingReturnItemShowsActivity.class);
			break;
		
		default:
			break;
		}
		
	}
	

}
