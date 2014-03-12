/**
 * 宝龙电商
 * com.powerlong.electric.app.widget
 * DialogInterface.java
 * 
 * 2013-9-2-下午01:27:13
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.widget;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

/**
 * 
 * DialogInterface 对话框通用类
 * 
 * @author: Liang Wang 2013-9-2-下午01:27:13
 * 
 * @version 1.0.0
 * 
 */
public class DialogInterface {
	private Context mContext = null;
	private int mLayoutId = -1;
	private LayoutInflater mLayoutInflater = null;
	private View mLayoutView = null;
	private Dialog mDlg = null;

	/**
	 * 
	 * 创建一个新的实例 DialogInterface.
	 * 
	 * @param context
	 *            上下文
	 * @param layoutId
	 *            对话框布局文件编号
	 */
	public DialogInterface(Context context, int layoutId) {
		mContext = context;
		mLayoutId = layoutId;
		mLayoutInflater = LayoutInflater.from(context);
	}

	public View getDlgView() {
		return mLayoutView;
	}

	/**
	 * 显示对话框
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public Dialog createDialog() {
		switch (mLayoutId) {
		case R.layout.cart_edit_num:
			mLayoutView = mLayoutInflater.inflate(mLayoutId, null);
			mDlg = new Dialog(mContext, R.style.tc_dialogTheme);
			mDlg.setContentView(mLayoutView);
			mDlg.setCanceledOnTouchOutside(true);
			break;
		case R.xml.dlg_edit_good_info:
			mLayoutView = mLayoutInflater.inflate(mLayoutId, null);
			mDlg = new Dialog(mContext, R.style.tc_dialogTheme);
			mDlg.setContentView(mLayoutView);
			mDlg.setCanceledOnTouchOutside(true);
			break;
		}

		return mDlg;
	}

	public void showDialog() {
		if (mDlg != null && !mDlg.isShowing()) {
			mDlg.show();
			WindowManager.LayoutParams layoutParams = mDlg.getWindow()
					.getAttributes();
			layoutParams.width = (int) (Constants.displayWidth * 0.7);
			;
			layoutParams.height = (int) (Constants.displayHeight * 0.9);

			layoutParams.height = LayoutParams.WRAP_CONTENT;
			mDlg.getWindow().setAttributes(layoutParams);
			mDlg.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		}
	}

	public void dismissDialog() {
		if (mDlg != null && mDlg.isShowing()){
			mDlg.dismiss();
			mDlg = null;
		}
	}
}
