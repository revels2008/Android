/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * DateTimeActivity.java
 * 
 * 2013年8月31日-下午2:14:27
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.Calendar;  

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.ui.base.BaseActivity;

import android.app.Activity;  
import android.app.AlertDialog;  
import android.app.DatePickerDialog;
import android.app.Dialog;  
import android.app.TimePickerDialog;
import android.content.DialogInterface;  
import android.content.Intent;
import android.os.Bundle;  
import android.text.InputType;  
import android.view.MotionEvent;  
import android.view.View;   
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.DatePicker;
import android.widget.EditText;   
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 
 * DateTimeActivity
 * 
 * @author: YangCheng Miao
 * 2013年8月31日-下午2:14:27
 * 
 * @version 1.0.0
 * 
 */
import android.widget.TimePicker;

  
/** 
 * 功能描述：实现日期时间选择器 
 *  
 * @author android_ls 
 */  
public class DateTimeActivity extends BaseActivity implements View.OnClickListener {  
     
    private EditText etDate;   
    private EditText etTime;  
    private final static int DATE_DIALOG = 0;
    private final static int TIME_DIALOG = 1;
    private Calendar c = null;
    private ImageView ivReturn;
    private ImageView ivFilter;
    private TextView title;
    String time;
    String dateText;
    String timeText;
      
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.settle_account_receipt_time);  
          
        etDate = (EditText) this.findViewById(R.id.et_date);  
        etDate.setHint("  "+getCurrentdate());
        etDate.setHintTextColor(getResources().getColor(R.color.grey));
        etTime = (EditText) this.findViewById(R.id.et_time); 
        etTime.setHint("  "+getCurrentTime());
        etTime.setHintTextColor(getResources().getColor(R.color.grey));
        ivReturn = (ImageView) this.findViewById(R.id.ivReturn);
        ivReturn.setOnClickListener(this);
        ivFilter = (ImageView) this.findViewById(R.id.ivAdd);
        ivFilter.setOnClickListener(this);
        title = (TextView) this.findViewById(R.id.txTitle);
        title.setText("预约收货时间");
          
//        etDate.setOnTouchListener(this);  
//        etTime.setOnTouchListener(this);  
        
//        etDate.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				showDialog(DATE_DIALOG);				
//			}
//		});
        
        etDate.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				showDialog(DATE_DIALOG);
				return true;
			}
		});
        etTime.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				showDialog(TIME_DIALOG);
				return true;
			}
		});
    }
    
    private String getCurrentdate() {
    	c= Calendar.getInstance();
    	return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" +(c.get(Calendar.DAY_OF_MONTH)+1);
    }
    
    private String getCurrentTime() {
    	c= Calendar.getInstance();
    	return c.get(Calendar.HOUR_OF_DAY)+":"+ c.get(Calendar.MINUTE);
    }
    	
        
    
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
        case DATE_DIALOG:
            c = Calendar.getInstance();
            dialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker dp, int year,int month, int dayOfMonth) {
                        etDate.setText(year + "年" + (month+1) + "月" + dayOfMonth + "日");
                    }
                }, 
                c.get(Calendar.YEAR), // 传入年份
                c.get(Calendar.MONTH), // 传入月份
                c.get(Calendar.DAY_OF_MONTH) // 传入天数
            );
            break;
        case TIME_DIALOG:
            c=Calendar.getInstance();
            dialog=new TimePickerDialog(
                this, 
                new TimePickerDialog.OnTimeSetListener(){
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText(hourOfDay+"时"+minute+"分");
                    }
                },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                false
            );
            break;
        }
        return dialog;
    }


	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivReturn:
			finish();
			break;
		case R.id.ivAdd:
			dateText = etDate.getText().toString().trim();
			timeText = etTime.getText().toString().trim();
			if (!"".equals(dateText)  && !"".equals(timeText)) {
				time = dateText+" "+ timeText;
			} else if (!"".equals(dateText) && "".equals(timeText)) {
				time = etDate.getText()+ getCurrentTime();
			} else if ("".equals(dateText) && !"".equals(timeText)) {
				time =  getCurrentdate() + " "+etTime.getText();
			} else {
				time = "  " +getCurrentdate() +"  " +getCurrentTime();
			}
			
			Intent data = new Intent();
			data.putExtra("time", time);
			setResult(Constants.ResultType.RESULT_FROM_TIME, data);
			finish();
			break;

		default:
			break;
		}
		
	}  
    
}  