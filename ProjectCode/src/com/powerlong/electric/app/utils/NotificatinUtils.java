/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * NotificatinUtils.java
 * 
 * 2013-10-30-下午04:21:40
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.widget.HandyTextView;

/**
 * 
 * NotificatinUtils
 * 
 * @author: Liang Wang
 * 2013-10-30-下午04:21:40
 * 
 * @version 1.0.0
 * 
 */
public class NotificatinUtils {
	public static void showCustomToast(Context mContext,String text) {
		if(mContext == null)
			return;
		View toastRoot = LayoutInflater.from(mContext).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(mContext);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}
}
