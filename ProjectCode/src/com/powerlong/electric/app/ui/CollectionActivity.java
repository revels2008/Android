/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * CollectionActivity.java
 * 
 * 2013年8月28日-下午8:39:42
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.CollectionEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.LogUtil;

/**
 * 
 * CollectionActivity
 * 
 * @author: YangCheng Miao
 * 2013年8月28日-下午8:39:42
 * 
 * @version 1.0.0
 * 
 */
public class CollectionActivity extends BaseActivity implements OnClickListener {
	private ListView listView;
	private ArrayAdapter<CollectionEntity> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_cart);
	}

	private void getData() {
//		setProgressBarVisibility(true);
//		DataUtil.getNavFloorDetailsData(getBaseContext(), method, methodParams, mServerConnectionHandler);
	}
	
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("BrandActivity", "msg.what = " + msg.what);
			LogUtil.d("BrandActivity", "msg.arg1 = " + msg.arg1);
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				updateView(Integer.toString(msg.arg1));
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				break;
			}
			dismissLoadingDialog();
		}

		private void updateView(String navId) {
			
		}

	};
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		
	}

}
