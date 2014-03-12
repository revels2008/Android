package com.powerlong.electric.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class CrashHandler implements UncaughtExceptionHandler { 
    
    public static final String TAG = "CrashHandler"; 
     
    //系统默锟较碉拷UncaughtException锟斤拷锟斤拷锟斤拷  
    private Thread.UncaughtExceptionHandler mDefaultHandler; 
    //CrashHandler实锟斤拷 
    private static CrashHandler INSTANCE = new CrashHandler(); 
    //锟斤拷锟斤拷锟紺ontext锟斤拷锟斤拷 
    private Context mContext; 
    //锟斤拷4锟芥储锟借备锟斤拷息锟斤拷锟届常锟斤拷息 
    private Map<String, String> infos = new HashMap<String, String>(); 
 
    //锟斤拷锟节革拷式锟斤拷锟斤拷锟斤拷,锟斤拷为锟斤拷志锟侥硷拷锟斤拷锟揭伙拷锟斤拷锟�
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss"); 
 
              //锟斤拷证只锟斤拷一锟斤拷CrashHandler实锟斤拷 
    private CrashHandler() { 
    } 
 
    // 锟斤拷取CrashHandler实锟斤拷 ,锟斤拷锟斤拷模式 
    public static CrashHandler getInstance() { 
        return INSTANCE; 
    } 
 

    public void init(Context context) { 
        mContext = context; 
        //锟斤拷取系统默锟较碉拷UncaughtException锟斤拷锟斤拷锟斤拷 
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler(); 
        //锟斤拷锟矫革拷CrashHandler为锟斤拷锟斤拷锟侥拷洗锟斤拷锟斤拷锟�
        Thread.setDefaultUncaughtExceptionHandler(this); 
    } 
 

	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
//				Log.e(TAG, "error : ", e);
			}
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}
 

    private boolean handleException(Throwable ex) { 
        if (ex == null) { 
            return false; 
        } 
        //使锟斤拷Toast4锟斤拷示锟届常锟斤拷息 
        new Thread() { 
            @Override 
            public void run() { 
                Looper.prepare(); 
                Toast.makeText(mContext, "系统异常.", Toast.LENGTH_LONG).show(); 
               
                Looper.loop(); 
            } 
        }.start(); 
        //锟秸硷拷锟借备锟斤拷锟斤拷锟斤拷息  
        collectDeviceInfo(mContext); 
        //锟斤拷锟斤拷锟斤拷志锟侥硷拷  
        final String path = saveCrashInfo2File(ex); 
     // sendEmail(mContext,path);
        return true; 
    } 
     
    private void sendEmail(Context context,String filepath)
	{
    	if(filepath==null){
    		return;
    	}
    	Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("plain/text");  
		intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "send bug report");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"595890498@qq.com"});
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filepath)));
		intent.putExtra(Intent.EXTRA_SUBJECT, "sendBug report");
		this.mContext.startActivity(Intent.createChooser(intent, "send bug report"));
	}


    public void collectDeviceInfo(Context ctx) { 
        try { 
            PackageManager pm = ctx.getPackageManager(); 
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES); 
            if (pi != null) { 
                String versionName = pi.versionName == null ? "null" : pi.versionName; 
                String versionCode = pi.versionCode + ""; 
                infos.put("versionName", versionName); 
                infos.put("versionCode", versionCode); 
            } 
        } catch (NameNotFoundException e) { 
//            Log.e(TAG, "an error occured when collect package info", e); 
        } 
        Field[] fields = Build.class.getDeclaredFields(); 
        for (Field field : fields) { 
            try { 
                field.setAccessible(true); 
                infos.put(field.getName(), field.get(null).toString()); 
//                Log.d(TAG, field.getName() + " : " + field.get(null)); 
            } catch (Exception e) { 
//                Log.e(TAG, "an error occured when collect crash info", e); 
            } 
        } 
    } 
 

    private String saveCrashInfo2File(Throwable ex) { 
         
        StringBuffer sb = new StringBuffer(); 
        for (Map.Entry<String, String> entry : infos.entrySet()) { 
            String key = entry.getKey(); 
            String value = entry.getValue(); 
            sb.append(key + "=" + value + "\n"); 
        } 
         
        Writer writer = new StringWriter(); 
        PrintWriter printWriter = new PrintWriter(writer); 
        ex.printStackTrace(printWriter); 
        Throwable cause = ex.getCause(); 
        while (cause != null) { 
            cause.printStackTrace(printWriter); 
            cause = cause.getCause(); 
        } 
        printWriter.close(); 
        String result = writer.toString(); 
        sb.append(result); 
        try { 
//            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String time = formatter.format(new Date(System.currentTimeMillis())); 
            String path = "/sdcard/powerlong/info/exception"; 
//            String path = this.mContext.getExternalCacheDir().toString();
            String fileName = path+"error-" + time + ".txt"; 
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { 
                File dir = new File(path); 
                if (!dir.exists()) { 
                    dir.mkdirs(); 
                } 
                if(!new File(fileName).exists()){
                	new File(fileName).createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(fileName); 
                fos.write(sb.toString().getBytes()); 
                fos.close(); 
            } 
            return fileName; 
        } catch (Exception e) { 
//            Log.e(TAG, "an error occured while writing file...", e); 
        } 
        return null; 
    } 
}