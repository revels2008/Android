/**
 * 宝龙电商
 * com.powerlong.electric.app.utils
 * ToastUtil.java
 * 
 * 2013-9-11-上午10:25:08
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.utils;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * ToastUtil 浮动提示框通用类
 * 
 * @author: Liang Wang
 * 2013-9-11-上午10:25:08
 * 
 * @version 1.0.0
 * 
 */
public class ToastUtil {
	private static Toast toast;
	public static void showExceptionTips(Context ctx,String text){
		/*Toast toast = new Toast(ctx);
		TextView textView = new TextView(ctx);
		textView.setText(text);
		textView.setBackgroundResource(R.drawable.tu);
		textView.setTextColor(Color.BLACK);
		textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
		textView.setGravity(Gravity.CENTER);
		toast.setView(textView);
		toast.setGravity(Gravity.BOTTOM, 0, Constants.displayHeight/4);
		toast.show();*/
		
		if(toast == null) {
			toast = Toast.makeText(ctx, text, Toast.LENGTH_SHORT);
		}else {
			toast.setText(text);
		}
		toast.setGravity(Gravity.BOTTOM, 0, Constants.displayHeight/4);
		toast.show();
	}
	
	public static void showProgressBar(Activity act,int visible){
		ProgressBar mProgressBar = (ProgressBar) act.findViewById(R.id.progressbar);
		mProgressBar.setVisibility(visible);
	}
}
