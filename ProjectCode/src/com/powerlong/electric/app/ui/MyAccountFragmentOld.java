/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * HomeFragment.java
 * 
 * 2013-7-27-下午04:35:42
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import com.baidu.location.BDLocation;
import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.config.Constants.LocationStatus;
import com.powerlong.electric.app.listener.LogoutDlgListener;
import com.powerlong.electric.app.listener.PlLocationListener;
import com.powerlong.electric.app.location.CurrentLocation;
import com.powerlong.electric.app.location.Location;
import com.powerlong.electric.app.ui.base.BaseDialogFragment;
import com.powerlong.electric.app.utils.IntentUtil;
import com.powerlong.electric.app.utils.LogUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * HomeFragment
 * 
 * @author: Liang Wang
 * 2013-7-27-下午04:35:42
 * 
 * @version 1.0.0
 * 
 */
public class MyAccountFragmentOld extends Fragment implements OnClickListener {
	private ImageView profileIconIv = null;
	private TextView  profileNameTv = null;
	private ImageView editProfileIv = null;
	private TextView  editLocationTv = null;
	
	Boolean bLogin = false;
	private SharedPreferences pref = null;
	private String username = "";
	
	Location mLocation = null;
	
//	private PlLocationListener mListener = new PlLocationListener(getActivity()) {
//		
//		@Override
//		public void OnTimeout() {
//			LogUtil.d("Location", "time out");
//			CurrentLocation.status = LocationStatus.FAILED;
//			mLocation.stop();
//			editLocationTv.setText(R.string.home_loading_failed);
//		}
//		
//		@Override
//		public void OnGetLocationSuccess(BDLocation location) {
//			String city = location.getCity();
//			double mLatitude = location.getLatitude();
//			double mLongitude = location.getLongitude();
//			LogUtil.d("Location", "mLocation = "+city);
//			LogUtil.d("Location", "mLatitude = "+mLatitude);
//			LogUtil.d("Location", "mLongitude = "+mLongitude);
//			CurrentLocation.status = LocationStatus.SUCCESS;
//			CurrentLocation.curLocation = city;
//			CurrentLocation.lat = mLatitude;
//			CurrentLocation.lng = mLongitude;
//			mLocation.stop();
//			editLocationTv.setText(city);
//		}
//	};
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		pref = getActivity().getSharedPreferences("account_info", Context.MODE_PRIVATE);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_account_fragment_layout, container, false);
		initControls(view);
		mLocation = Location.getInstance(getActivity());
		//mLocation.setListener(mListener);
		return view;
	}
	
	

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LogUtil.d("myaccount", "onResume");
		checkUserData();
		initViews();
		mLocation.setListener(new PlLocationListener(getActivity()) {
			@Override
			public void OnTimeout() {
//				LogUtil.d("Location", "time out");
				CurrentLocation.status = LocationStatus.FAILED;
				mLocation.stop();
				editLocationTv.setText(R.string.home_loading_failed);
			}
			
			@Override
			public void OnGetLocationSuccess(BDLocation location) {
				String city = location.getCity();
				double mLatitude = location.getLatitude();
				double mLongitude = location.getLongitude();
				LogUtil.d("MyAccountFragment", "mLocation = "+city);
				LogUtil.d("MyAccountFragment", "mLatitude = "+mLatitude);
				LogUtil.d("MyAccountFragment", "mLongitude = "+mLongitude);
				CurrentLocation.status = LocationStatus.SUCCESS;
				CurrentLocation.myHome = city;
				CurrentLocation.lat = mLatitude;
				CurrentLocation.lng = mLongitude;
				mLocation.stop();
				editLocationTv.setText(city);
			}
		});
		
		if(TextUtils.isEmpty(CurrentLocation.myHome))
			mLocation.start();
		else
			editLocationTv.setText(CurrentLocation.myHome);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		CurrentLocation.status = LocationStatus.LOADING;
		mLocation.stop();
	}

	/**
	 * initControls(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void initControls(View view) {
		profileIconIv = (ImageView)view.findViewById(R.id.icon);
		profileIconIv.setOnClickListener(this);
		profileNameTv = (TextView)view.findViewById(R.id.text1);
		profileNameTv.setOnClickListener(this);
		editProfileIv = (ImageView)view.findViewById(R.id.my_edit_name_cont);
		editProfileIv.setOnClickListener(this);
		editLocationTv = (TextView)view.findViewById(R.id.myhome);
		editLocationTv.setEnabled(true);
		editLocationTv.setOnClickListener(this);
	}
	
	/**
	 * checkUserData(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void checkUserData() {
		username = pref.getString("name", "");
		if (!TextUtils.isEmpty(username)) {
			bLogin = true;
		}
	}
	
	/**
	 * initViews(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选) 
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void initViews() {
		if(bLogin)
			editProfileIv.setVisibility(View.VISIBLE);
		else
			editProfileIv.setVisibility(View.GONE);
		
		if(bLogin)
			profileNameTv.setText(username);
		else
			profileNameTv.setText(R.string.account_need_login);
		
		editLocationTv.setText(R.string.home_loading);
	}

	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.icon:
			if(!bLogin){
				IntentUtil.start_activity(getActivity(), LoginActivity.class);
			}else{
				String tag = "my_dialog";
				BaseDialogFragment myFragment = BaseDialogFragment
						.newInstance(Constants.DialogIdentity.DLG_LOGOUT);
				// Animation anim=AnimationUtils.loadAnimation(this,
				// R.anim.push_top_in);
				// myFragment.show(getSupportFragmentManager(), tag);
				getActivity().getWindow().setWindowAnimations(R.anim.push_top_in);
				FragmentTransaction ft = getActivity().getSupportFragmentManager()
						.beginTransaction();
				ft.add(myFragment, tag);
				ft.setCustomAnimations(R.anim.push_left_in, R.anim.push_top_out);
				ft.commit();
				myFragment.setListener(new LogoutDlgListener() {

					@Override
					public void doPositiveClick() {
						// press ok btn
						Editor editor = pref.edit();
						editor.putString("name", "");
						editor.putString("pwd", "");
						editor.commit();
						if (profileNameTv != null) {
							username = pref.getString("name", "");
							if (!TextUtils.isEmpty(username)) {
								profileNameTv.setText(username);
							} else {
								profileNameTv.setText(R.string.account_need_login);
							}
						}
						bLogin = false;
						initViews();
					}

					@Override
					public void doNegativeClick() {
						// press cancel btn
					}
				});
				ft.show(myFragment);
			}
			break;
		case R.id.my_edit_name_cont:
			break;
		case R.id.myhome:
			IntentUtil.start_activity(getActivity(), CityListActivity.class);
			getActivity().overridePendingTransition(R.anim.roll_up, R.anim.fade_out);
			break;
		}
	}
	
}
