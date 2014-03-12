/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ActivityDetailActivity.java
 * 
 * 2013年9月10日-下午7:02:04
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.ActivityDetailEntity;
import com.powerlong.electric.app.entity.ImageListEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.ToastUtil;
import com.powerlong.electric.app.utils.AsyncImageLoaderUtil.ILoadImageCallback;
import com.powerlong.electric.app.utils.StringUtil;

/**
 * 
 * ActivityDetailActivity
 * 
 * @author: YangCheng Miao
 * 2013年9月10日-下午7:02:04
 * 
 * @version 1.0.0
 * 
 */
public class ActivityDetailActivity extends BaseActivity implements OnClickListener {
	//private ProgressBar mpProgressBar = null;
	private long activityId;
	private ImageView ivTitle;
	private TextView tvCategory;
	private TextView tvName;
	private TextView tvTime;
	private TextView tvAddress;
	private TextView tvLocation;
	private TextView tvIntroduce;
	private TextView tvTip;
	private TextView tvRule;
	private ImageView ivReturn;
	private String methodParams ="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		Intent intent = getIntent();
		activityId = intent.getLongExtra("activityId", activityId);
		methodParams = intent.getStringExtra("methodParams");
		if(!StringUtil.isEmpty(methodParams)){
			try {
				JSONObject obj = new JSONObject(methodParams);
				activityId = obj.getInt("activityId");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		LogUtil.d("ActivityDetailActivity", "activityId = "+activityId);
		findView();
		getData();
	}

	private void findView() {
		ivTitle = (ImageView) findViewById(R.id.iv_activity_item);
		tvCategory = (TextView) findViewById(R.id.tv_activity_category);
		tvName = (TextView) findViewById(R.id.tv_activity_advertising);
		tvTime = (TextView) findViewById(R.id.tv_activity_time);
		tvAddress = (TextView) findViewById(R.id.tv_activity_address);
		tvLocation = (TextView) findViewById(R.id.tv_activity_sponsor);
		tvIntroduce = (TextView) findViewById(R.id.tv_activity_introduce_detail);
		tvTip = (TextView) findViewById(R.id.tv_activity_prompt_detail);
		tvRule = (TextView) findViewById(R.id.tv_activity_rule_detail);
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(this);
		
		//mpProgressBar = (ProgressBar)findViewById(R.id.progressbar);
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
		DataUtil.getNavActvityDetails(getBaseContext(), mServerConnectionHandler,activityId);
	}
	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler(){

		@Override
		public void handleMessage(Message msg) {
			
			LogUtil.d("ActivityDetailActivity", "msg.what = "+msg.what);
			LogUtil.d("ActivityDetailActivity", "msg.arg1 = "+msg.arg1);
			dismissLoadingDialog();
			switch(msg.what){
			case Constants.HttpStatus.SUCCESS:
				updateView(Integer.toString(msg.arg1));
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String)msg.obj;
				showCustomToast(fail);
				if(!Constants.isShowDefaultUi)
					finish();
				break;
			}
		}
		
	};
	
	protected void updateView(String navId) {
		HashMap<Long,ActivityDetailEntity> activityEntities = DataCache.NavActivityDetaillsCache;
		ActivityDetailEntity entity = activityEntities.get(activityId);

		if(entity==null)
			return;

		String imageUrl = entity.getImage();
		AsyncImageLoaderUtil imageLoader = new AsyncImageLoaderUtil();		
		Drawable cachedImage = imageLoader.loadDrawable(imageUrl,ivTitle, new ILoadImageCallback() {
			@Override
			public void onObtainDrawable(Drawable drawable,ImageView imageView) {
				imageView.setImageDrawable(drawable);
			}
			
		});
		if (cachedImage == null) {
			ivTitle.setImageResource(R.drawable.pic_56);
//			Log.e("Adapter", "null");
		} else {
			ivTitle.setImageDrawable(cachedImage);
		}
//		tvCategory.setText(entity.getClassification());
		tvName.setText(entity.getName());
//		tvTime.setText(entity.getAddress());
//		tvAddress.setText(entity.getAddress());
//		if (StringUtil.toInt(entity.getIsPlazaActivity()) == 0) {
//			tvLocation.setText("商家活动");
//		} else if (StringUtil.toInt(entity.getIsPlazaActivity()) == 1) {
//			tvLocation.setText("广场活动");
//		}
		tvIntroduce.setText(Html.fromHtml(entity.getIntroduction()));
//		tvTip.setText(entity.getTips());
//		tvRule.setText(entity.getRule());
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

		default:
			break;
		}
		
	}
}
