package com.almighty.studymode;


import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;

public class Myservice extends IntentService {
    public Myservice(){
        super("Myservice");
    }
    @Override
    protected void onHandleIntent(Intent intent){
        for (int i=0;i<720;i+=2){
            synchronized (this){
                try{
                    wait(3 * 1000);
                    if (!getForeground().equals("com.almighty.studymode")){
                        Intent it=new Intent();
                        it.setClass(this, MainActivity.class);
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(it);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }
    //获取当前前台应用的包名
    public String getForeground() {
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        //获取到当前所有进程
        List<ActivityManager.RunningAppProcessInfo> processInfoList = activityManager.getRunningAppProcesses();
        if (processInfoList ==null || processInfoList.isEmpty()) {
            return "";
        }
        //遍历进程列表，找到第一个前台进程
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return processInfo.processName;
            }
        }
        return "";
    }
}
