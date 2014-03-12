/**
 * 宝龙电商
 * com.powerlong.electric.app.widget
 * CartDialog.java
 * 
 * 2014年1月26日-下午2:27:30
 *  2014宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

import com.powerlong.electric.app.R;

/**
 * 
 * CartDialog
 * 
 * @author: fancy
 * 2014年1月26日-下午2:27:30
 * 
 * @version 1.0.0
 * 
 */
public abstract class CartDialog extends Dialog {

	private TextView mTxtInfo;
	private Button mBtnConfirm;
	private Button mBtnCancel;
	private String info;
	
	/**
	 * 创建一个新的实例 CartDialog.
	 *
	 * @param context
	 */
	public CartDialog(Context context) {
		super(context);
	}
	
	public CartDialog(Context context, int theme, String info) {
		super(context, theme);
		this.info = info;
	}
	
	protected CartDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public void setDialogInfo(String info) {
		if (mTxtInfo != null)
			mTxtInfo.setText(info);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart_dialog);
		mTxtInfo = (TextView) findViewById(R.id.txt_dialog_info);
		mTxtInfo.setText(info);
		mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
		mBtnCancel = (Button) findViewById(R.id.btn_cancel);
		
		mBtnConfirm.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				confirm();
				return false;
			}
		});
		
		mBtnCancel.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				cancel();
				return false;
			}
		});
	}

	public abstract void confirm();
	
	public abstract void cancel();
}
