package com.upomp.pay.activity;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.os.Bundle;

import com.powerlong.electric.app.utils.LogUtil;
import com.unionpay.upomp.lthj.util.PluginHelper;
import com.upomp.pay.info.Upomp_Pay_Info;


public class Star_Upomp_Pay {
	
	/*
	 * ����������÷�����PluginHelper������com_unionpay_upomp_lthj_lib.jar
	 */
	public  void start_upomp_pay(Activity thisActivity,String LanchPay) throws UnsupportedEncodingException{
		LogUtil.d("Star_Upomp_Pay", "LanchPay = " + LanchPay);
		byte[] to_upomp = LanchPay.getBytes("utf-8");
		LogUtil.d("Star_Upomp_Pay", "to_upomp = " + to_upomp);
		Bundle mbundle = new Bundle();
		// to_upompΪ�̻��ύ��XML
		mbundle.putByteArray("xml", to_upomp);
	
		mbundle.putString("action_cmd", Upomp_Pay_Info.CMD_PAY_PLUGIN);
		//������������������,valueΪtrue�ǲ��Բ�� ��Ϊfalse�������
	    mbundle.putBoolean("test", false);	
		PluginHelper.LaunchPlugin(thisActivity, mbundle);
	}	
	
}
