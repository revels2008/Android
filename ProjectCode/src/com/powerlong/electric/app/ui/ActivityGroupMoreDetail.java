/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * ActivityGroupMoreDetail.java
 * 
 * 2013-10-30-下午02:46:05
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.domain.DomainGroupMoreDetail;
import com.powerlong.electric.app.ui.adapter.AdapterGroupMoreDetail;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.NotificatinUtils;

/**
 * 
 * ActivityGroupMoreDetail
 * 
 * @author: hegao
 * 2013-10-30-下午02:46:05
 * 
 * @version 1.0.0
 * 
 */
public class ActivityGroupMoreDetail extends BaseActivity {
	private Context context;
	private ImageView ivBack;
	private PullToRefreshListView listView;
	private AdapterGroupMoreDetail adapterGroupMoreDetail;
	
	private int curPage = 1;
	private String grouponId;
	private List<DomainGroupMoreDetail> list_all = new ArrayList<DomainGroupMoreDetail>(); 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_more_detail);
		initService();
		initView();
		initData();
	}
	private void initData() {
		getData();
	}
	private void initView() {
		findViewById(R.id.ivList).setVisibility(View.GONE);
		findViewById(R.id.ivGrid).setVisibility(View.GONE);
		findViewById(R.id.btn_buy_now).setVisibility(View.GONE);
		listView = (PullToRefreshListView) findViewById(R.id.list_more_detail);
		listView.setMode(Mode.BOTH);
		listView.getRefreshableView().setCacheColorHint(0);
		adapterGroupMoreDetail = new AdapterGroupMoreDetail(context);
		listView.setAdapter(adapterGroupMoreDetail);
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				curPage = 1;
				list_all.clear();
				getData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				curPage++;
				getData();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				DomainGroupMoreDetail groupMoreDetail = list_all.get(arg2-1);
				Intent intent = new Intent(ActivityGroupMoreDetail.this,ItemDetailActivity.class);
				intent.putExtra("itemId", Long.parseLong(groupMoreDetail.getId()));
				intent.putExtra("type", 1);
				startActivity(intent);
			}
		});
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	private void initService() {
		context = this;
		grouponId = getIntent().getStringExtra("grouponId");
	}
	
	private void getData(){
		try {
			JSONObject jsobj = new JSONObject();
			jsobj.put("mall", Constants.mallId);
			jsobj.put("page", curPage+"");
			jsobj.put("type", "0");
			jsobj.put("grouponId", grouponId);
			FinalHttp finalHttp = new FinalHttp();
			AjaxParams params = new AjaxParams();
			params.put("data", jsobj.toString());
			finalHttp.get(Constants.ServerUrl.GET_GROUPON_MORE_DETAIL, params, new AjaxCallBack<String>() {
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					super.onFailure(t, errorNo, strMsg);
					if(t!=null && strMsg!=null){
						NotificatinUtils.showCustomToast(context, strMsg);
						listView.onRefreshComplete();
					}
				}
				
				@Override
				public void onSuccess(String t) {
					try {
						JSONObject jsObj = new JSONObject(t);
						if(jsObj.getInt("code")!=0){
							NotificatinUtils.showCustomToast(context, jsObj.getString("msg"));
						}else{
							List<DomainGroupMoreDetail> list = DomainGroupMoreDetail.convertJson2GroupMoreDetailList(jsObj.getString("data"));
							list_all.addAll(list);
							adapterGroupMoreDetail.setList(list_all);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						listView.onRefreshComplete();
					}
				}
			});
		} catch (Exception e) {
			NotificatinUtils.showCustomToast(context, e.getMessage());
		}
	}
}
