/**
 * 宝龙电商
 * com.powerlong.electric.app.ui.base
 * BaseDialogFragment.java
 * 
 * 2013-7-24-下午05:29:33
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui.base;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.linstener.base.BaseDlgListener;
import com.powerlong.electric.app.listener.LogoutDlgListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 
 * BaseDialogFragment
 * 
 * @author: Liang Wang 2013-7-24-下午05:29:33
 * 
 * @version 1.0.0
 * 
 */
public class BaseDialogFragment extends DialogFragment {
	private static String DialogId = "DLG_ID";
	private static BaseDlgListener mListener = null;

	public static BaseDialogFragment newInstance(int DlgId) {
		// 创建一个新的带有指定参数的Fragment实例
		BaseDialogFragment fragment = new BaseDialogFragment();
		Bundle args = new Bundle();
		args.putInt(DialogId, DlgId);
		fragment.setArguments(args);
		return fragment;
	}

	public void setListener(BaseDlgListener listener) {
		mListener = listener;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// 使用AlertBuilder创建新的对话框
		Dialog dlg = null;
		int id = getArguments().getInt(DialogId);
		switch (id) {
		case Constants.DialogIdentity.DLG_LOGOUT:
			dlg = new AlertDialog.Builder(getActivity())
					.setMessage(R.string.logout_dlg_msg)
					.setPositiveButton(R.string.logout_pos_btn,
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (mListener != null
											&& mListener instanceof LogoutDlgListener) {
										LogoutDlgListener listener = (LogoutDlgListener) mListener;
										listener.doPositiveClick();
									}
								}
							})
					.setNegativeButton(R.string.logout_neg_btn,
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (mListener != null
											&& mListener instanceof LogoutDlgListener) {
										LogoutDlgListener listener = (LogoutDlgListener) mListener;
										listener.doNegativeClick();
									}
								}
							}).create();
			dlg.setCancelable(true);
			dlg.setCanceledOnTouchOutside(true);
			return dlg;
		case Constants.DialogIdentity.DLG_EDIT_GOODS:
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			View view = inflater.inflate(R.layout.dlg_edit_good_info, null);
			dlg = new AlertDialog.Builder(getActivity()).setView(view)
					.setMessage(R.string.logout_dlg_msg).create();
			dlg.setCancelable(true);
			dlg.setCanceledOnTouchOutside(true);
			return dlg;
		default:
			break;
		}
		return null;
	}
}
