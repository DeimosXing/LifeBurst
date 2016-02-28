package com.almighty.studymode;


import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.List;

public class Studyservice extends Service {
    private MyBinder binder=new MyBinder();
    private int timespan=0;
    private boolean studying=true;
    public class MyBinder extends Binder{
        public int getTimespan(){
            return timespan;
        }
    }
    @Override
    public IBinder onBind(Intent intent){
        return binder;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        new Thread(){
            @Override
            public void run(){
                for (int i=0;i<720;i+=2){
                        try{
                            sleep(3 * 1000);
                            if (!getForeground().equals("com.almighty.studymode")){
                                Intent it=new Intent();
                                it.setClass(Studyservice.this, MainActivity.class);
                                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(it);
                            }
                            timespan=i/2;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    System.out.println(studying);
                    if (!studying){
                        break;
                    }
                }
            }
        }.start();
    }
    //Service断开连接
    @Override
    public boolean onUnbind(Intent intent){
        return true;
    }
    //Service关闭
    @Override
    public void onDestroy(){
        studying=false;
        super.onDestroy();
        System.out.println("已关闭");
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
