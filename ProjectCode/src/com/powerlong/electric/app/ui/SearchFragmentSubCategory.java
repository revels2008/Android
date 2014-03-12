/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * SearchFragmentSubCategory.java
 * 
 * 2013-11-5-下午02:20:57
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.domain.DomainSearchCategorySub;
import com.powerlong.electric.app.domain.DomainSearchHistory;
import com.powerlong.electric.app.ui.adapter.AdapterSearchCategorySub;
import com.powerlong.electric.app.ui.adapter.AdapterSearchHistory;
import com.powerlong.electric.app.ui.base.BaseFragment;
import com.powerlong.electric.app.utils.IntentUtil;

/**
 * 
 * SearchFragmentSubCategory
 * 
 * @author: hegao
 * 2013-11-5-下午02:20:57
 * 
 * @version 1.0.0
 * 
 */
public class SearchFragmentSubCategory extends BaseFragment {
	
	private Context context;
	private Activity mActivity;
	
	private String data = null;
	
	@ViewInject(id=R.id.list_category) private PullToRefreshListView listView_category;
	@ViewInject(id=R.id.list_search) private PullToRefreshListView list_search;
	
	private AdapterSearchCategorySub<DomainSearchCategorySub> adapterSearchCategorySub;
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
		this.mActivity = activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		data = bundle.getString("data");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View viewRoot = inflater.inflate(R.layout.search_fragment_layout_new, null);
		FinalActivity.initInjectedView(this, viewRoot);
		return viewRoot;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
		initEvents();
		init();
	}
	
	
	
	@Override
	protected void initViews() {
		list_search.setVisibility(View.GONE);
	}

	@Override
	protected void initEvents() {
		listView_category.setMode(Mode.DISABLED);
		listView_category.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				DomainSearchCategorySub searchCategorySub = adapterSearchCategorySub.getDomain(arg2-1);
				IntentUtil.startActivity(mActivity, SearchBaseActivityNew.class,searchCategorySub);
			}
		});
	}

	@Override
	protected void init() {
		adapterSearchCategorySub = new AdapterSearchCategorySub<DomainSearchCategorySub>(context);
		ImageView mIv = new ImageView(getActivity());
		LinearLayout mll = new LinearLayout(getActivity());
		mIv.setImageResource(R.drawable.line_full);
		mIv.setLayoutParams(new LayoutParams(1, LinearLayout.LayoutParams.WRAP_CONTENT));
//		mll.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
		mIv.setBackgroundDrawable(getResources().getDrawable(R.drawable.line_full));
		mll.addView(mIv);
		listView_category.getRefreshableView().addFooterView(mll);
		
		listView_category.setAdapter(adapterSearchCategorySub);
		
		List<DomainSearchCategorySub> searchCategorySubs = DomainSearchCategorySub.convertJsonToSearchCategorySub(data);
		adapterSearchCategorySub.setList(searchCategorySubs);
		
	}

}
