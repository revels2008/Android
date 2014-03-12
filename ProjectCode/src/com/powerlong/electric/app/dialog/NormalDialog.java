package com.powerlong.electric.app.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.TextView;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.ui.base.BaseDialog;


public class NormalDialog extends BaseDialog{
	private TextView mTxtInfo;
	private Context mContext = null;
	
	public NormalDialog(Context context) {
		super(context);
		mContext = context;
		setDialogContentView(R.layout.layout_normal_dialog);
		mTxtInfo = (TextView) findViewById(R.id.txt_info);
	}

	@Override
	public void setTitle(CharSequence text) {
		super.setTitle(text);
	}
	
	@Override
	public void setTitle(int id) {
		String title = mContext.getResources().getString(id);
		super.setTitle(title);
	}
	
	@Override
	public void setSubTitle(CharSequence text) {
		super.setSubTitle(text);
	}

	public void setButton(CharSequence text,
			DialogInterface.OnClickListener listener) {
		super.setButton1(text, listener);
	}

	public void setButton(CharSequence text1,
			DialogInterface.OnClickListener listener1, CharSequence text2,
			DialogInterface.OnClickListener listener2) {
		super.setButton1(text1, listener1);
		super.setButton2(text2, listener2);
	}
	
	private boolean isNull(EditText editText) {
		String text = editText.getText().toString().trim();
		if (text != null && text.length() > 0) {
			return false;
		}
		return true;
	}

	public interface onItemsClickListener {
		public void onDecreaseClicked(int num);
		public void onIncreaseClicked(int num);
	}

	public void setInfo(String info) {
		if (mTxtInfo != null) 
			mTxtInfo.setText(info);
	}
}
