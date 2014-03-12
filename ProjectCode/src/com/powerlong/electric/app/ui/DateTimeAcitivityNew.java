/**
 * 宝龙电商
 * com.powerlong.electric.app.ui
 * DateTimeAcitivityNew.java
 * 
 * 2013年12月13日-下午3:37:19
 *  2013宝龙公司-版权所有
 * 
 */
package com.powerlong.electric.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.powerlong.electric.app.R;
import com.powerlong.electric.app.cache.DataCache;
import com.powerlong.electric.app.config.Constants;
import com.powerlong.electric.app.entity.DateTimeEntity;
import com.powerlong.electric.app.ui.base.BaseActivity;
import com.powerlong.electric.app.utils.LogUtil;

/**
 * 
 * DateTimeAcitivityNew
 * 
 * @author: YangCheng Miao
 * 2013年12月13日-下午3:37:19
 * 
 * @version 1.0.0
 * 
 */
public class DateTimeAcitivityNew extends BaseActivity implements OnClickListener {
	private Spinner province;
	private Spinner city;
	private ArrayAdapter<String> dateAdapter;
	private ArrayAdapter<String> timeAdapter;
	private List<String> dateList = new ArrayList<String>();
	private List<String> timeList = new ArrayList<String>();
	private ImageView ivReturn;
    private ImageView ivFilter;
    private String time;
    String dateTime;
    String receiveTime;
    String receiveDate;
	/* (non-Javadoc)
	 * @see com.powerlong.electric.app.ui.base.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settle_account_receipt_date);
		province = (Spinner) this.findViewById(R.id.province);
		city = (Spinner) this.findViewById(R.id.city);
		ivReturn = (ImageView) this.findViewById(R.id.ivReturn);
        ivReturn.setOnClickListener(this);
        ivFilter = (ImageView) this.findViewById(R.id.ivAdd);
        ivFilter.setOnClickListener(this);
		getDate();
		// 从资源数组文件中获取数据
//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//				this, R.array.province, android.R.layout.simple_spinner_item);
		// 设置下拉列表的风格
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将数据绑定到Spinner视图上
		dateAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, dateList);
		dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		province.setAdapter(dateAdapter);
		province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// parent既是province对象
				Spinner spinner = (Spinner) parent;
				String pro = (String) spinner.getItemAtPosition(position);
				receiveDate = "";
				receiveDate = pro;
				
				getTime(pro);
				// 绑定数据到Spinner(City)上
				timeAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, timeList);
				timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				city.setAdapter(timeAdapter);
				city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						Spinner spinner = (Spinner) parent;
						String city = "";
						city = (String) spinner
								.getItemAtPosition(position);
						receiveTime = city;
						
//						textView.setText(city);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
	}
	
	private void getDate() {
		ArrayList<DateTimeEntity> dateListCache = DataCache.dateListCache;
		dateList.clear();
		for(int i=0; i<dateListCache.size(); i++) {
			String date = dateListCache.get(i).getDate();
			dateList.add(date);
		}
	}
	
	private void getTime(String date) {
		HashMap<String, ArrayList<DateTimeEntity>> timeListCache = DataCache.timeListCache;
		ArrayList<DateTimeEntity> list = timeListCache.get(date);
		timeList.clear();
		for(int i=0; i<list.size(); i++) {
			String time = list.get(i).getTime();
			timeList.add(time);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivReturn:
			finish();
			break;
		case R.id.ivAdd:
/*			dateText = etDate.getText().toString().trim();
			timeText = etTime.getText().toString().trim();
			if (!"".equals(dateText)  && !"".equals(timeText)) {
				time = dateText+" "+ timeText;
			} else if (!"".equals(dateText) && "".equals(timeText)) {
				time = etDate.getText()+ getCurrentTime();
			} else if ("".equals(dateText) && !"".equals(timeText)) {
				time =  getCurrentdate() + " "+etTime.getText();
			} else {
				time = "  " +getCurrentdate() +"  " +getCurrentTime();
			}*/
			
			Intent data = new Intent();
			data.putExtra("receiveTime", receiveTime);
			data.putExtra("receiveDate", receiveDate);
			LogUtil.d("receiveDate", receiveDate);
			setResult(Constants.ResultType.RESULT_FROM_TIME, data);
			finish();
			break;

		default:
			break;
		}
		
	}  
}
