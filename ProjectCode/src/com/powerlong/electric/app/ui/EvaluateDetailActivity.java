/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * EvaluateDetailActivity.java
 * 
 * 2013年9月10日-下午8:25:21
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;

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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.ItemCommentEntity;
import com.powerlong.electric.app.handler.ServerConnectionHandler;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.DataUtil;
import com.powerlong.electric.app.utils.LogUtil;
import com.powerlong.electric.app.utils.StringUtil;
import com.powerlong.electric.app.utils.SubNameUtil;

/**
 * 
 * EvaluateDetailActivity 商品评论列表
 * 
 * @author: YangCheng Miao
 * 2013年9月10日-下午8:25:21
 * 
 * @version 1.0.0
 * 
 */
public class EvaluateDetailActivity extends BaseActivity implements OnClickListener {
	private ScrollView sv;
	private LayoutInflater mInflater; 
	private TextView tvUsrName;
	private RatingBar rbEvaluate;
	private TextView tvContent;
	private TextView tvSize;
	private TextView tvColor;
	private TextView tvDate;
	private ImageView ivBack;
	private LinearLayout llTabItem1, llTabItem2, llTabItem3, llTabItem4;
	private TextView tvSortItem1, tvSortItem2, tvSortItem3, tvSortItem4;
	
	private String mItemId;
	private String  mItemType;
	private int  page = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluate_detail);
		Intent intent = getIntent();
		mItemId = intent.getStringExtra("itemId");
		mItemType = intent.getStringExtra("type");
		
		findView();
		getData();
	}
	/**
	 * findView(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void findView() {
		sv = (ScrollView) findViewById(R.id.sv_evaluate);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		tvSortItem1 = (TextView) findViewById(R.id.tv_sort_item1);		
		tvSortItem2 = (TextView) findViewById(R.id.tv_sort_item2);		
		tvSortItem3 = (TextView) findViewById(R.id.tv_sort_item3);		
		tvSortItem4 = (TextView) findViewById(R.id.tv_sort_item4);		
		llTabItem1 = (LinearLayout) findViewById(R.id.ll_tab_item1);
		llTabItem1.setOnClickListener(this);
		llTabItem2 = (LinearLayout) findViewById(R.id.ll_tab_item2);
		llTabItem2.setOnClickListener(this);
		llTabItem3 = (LinearLayout) findViewById(R.id.ll_tab_item3);
		llTabItem3.setOnClickListener(this);
		llTabItem4 = (LinearLayout) findViewById(R.id.ll_tab_item4);
		llTabItem4.setOnClickListener(this);
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
		JSONObject obj = new JSONObject();
		try {
			obj.put("mall", Constants.mallId);
			obj.put("itemId", mItemId);
			obj.put("type", mItemType);
			obj.put("page", page+"");
			DataUtil.getItemComments(this,mServerConnectionHandler,obj.toString());
		} catch (JSONException e) {
			dismissLoadingDialog();
			e.printStackTrace();
		}
	}

	private ServerConnectionHandler mServerConnectionHandler = new ServerConnectionHandler() {

		@Override
		public void handleMessage(Message msg) {

			LogUtil.d("EvaluateDetailActivity", "msg.what = " + msg.what);
			LogUtil.d("EvaluateDetailActivity", "msg.arg1 = " + msg.arg1);
			dismissLoadingDialog();
			switch (msg.what) {
			case Constants.HttpStatus.SUCCESS:
				updateView();
				break;
			case Constants.HttpStatus.NORMAL_EXCEPTION:
			case Constants.HttpStatus.CONNECTION_EXCEPTION:
				String fail = (String) msg.obj;
				showCustomToast(fail);
				break;
			}
		}

	};

	private void updateView() {
		ArrayList<ItemCommentEntity> ItemCommentsList = DataCache.ItemCommentsListCache;
		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout llOut = new LinearLayout(sv.getContext());
		llOut.setOrientation(LinearLayout.VERTICAL);
		tvSortItem1.setText("全部(" + ItemCommentsList.size() +")");
		tvSortItem2.setText("好评");
		tvSortItem3.setText("中评");
		tvSortItem4.setText("有内容");
		for (int i=0; i<ItemCommentsList.size(); i++) {
			ItemCommentEntity entity = ItemCommentsList.get(i);
			LinearLayout ll = new LinearLayout(getBaseContext());
			ll = (LinearLayout) mInflater.inflate(R.layout.evaluate_detail_item, null);
			tvUsrName = (TextView) ll.findViewById(R.id.iv_evaluate_usr_name);
			rbEvaluate = (RatingBar) ll.findViewById(R.id.rb_evaluation_detail);
			tvContent = (TextView) ll.findViewById(R.id.tv_evaluate);
			tvSize = (TextView) ll.findViewById(R.id.iv_evaluate_size_text);
			tvColor = (TextView) ll.findViewById(R.id.tv_evaluate_color);
			//if ("true".equals(entity.getIsUsernameHidden())) {
			//	tvUsrName.setText(SubNameUtil.subName(entity.getNickname()));
			//} else {
				tvUsrName.setText(entity.getNickname());
			//}
			float f = Float.parseFloat(entity.getEvaluation()+"");
			rbEvaluate.setRating(f);
			tvContent.setText(entity.getContent());
//			tvSize.setText(entity.get);
			llOut.addView(ll);
		}
		sv.addView(llOut);
	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
			finish();
			break;
		case R.id.ll_tab_item1:
			llTabItem1.setBackgroundResource(R.drawable.tab_green1_press);
			tvSortItem1.setTextColor(getResources().getColor(R.color.white));
			llTabItem2.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem2.setTextColor(getResources().getColor(R.color.black));
			llTabItem3.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem3.setTextColor(getResources().getColor(R.color.black));
			llTabItem4.setBackgroundResource(R.drawable.tab_common3_nor);
			tvSortItem4.setTextColor(getResources().getColor(R.color.black));
			break;
		case R.id.ll_tab_item2:
			llTabItem1.setBackgroundResource(R.drawable.tab_common1_nor);
			tvSortItem1.setTextColor(getResources().getColor(R.color.black));
			llTabItem2.setBackgroundResource(R.drawable.tab_green2_press);
			tvSortItem2.setTextColor(getResources().getColor(R.color.white));
			llTabItem3.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem3.setTextColor(getResources().getColor(R.color.black));
			llTabItem4.setBackgroundResource(R.drawable.tab_common3_nor);
			tvSortItem4.setTextColor(getResources().getColor(R.color.black));
			break;
		case R.id.ll_tab_item3:
			llTabItem1.setBackgroundResource(R.drawable.tab_common1_nor);
			tvSortItem1.setTextColor(getResources().getColor(R.color.black));
			llTabItem2.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem2.setTextColor(getResources().getColor(R.color.black));
			llTabItem3.setBackgroundResource(R.drawable.tab_green2_press);
			tvSortItem3.setTextColor(getResources().getColor(R.color.white));
			llTabItem4.setBackgroundResource(R.drawable.tab_common3_nor);
			tvSortItem4.setTextColor(getResources().getColor(R.color.black));
			break;
		case R.id.ll_tab_item4:
			llTabItem1.setBackgroundResource(R.drawable.tab_common1_nor);
			tvSortItem1.setTextColor(getResources().getColor(R.color.black));
			llTabItem2.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem2.setTextColor(getResources().getColor(R.color.black));
			llTabItem3.setBackgroundResource(R.drawable.tab_common2_nor);
			tvSortItem3.setTextColor(getResources().getColor(R.color.black));
			llTabItem4.setBackgroundResource(R.drawable.tab_green3_press);
			tvSortItem4.setTextColor(getResources().getColor(R.color.white));
			break;
		default:
			break;
		}
	}
	
}
