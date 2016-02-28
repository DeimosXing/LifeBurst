package com.almighty.studymode;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button startbtn;
    Button stopbtn;
    Studyservice.MyBinder binder;
    int numberOfstops=0;
    private boolean havestudied=false;
    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(MainActivity.this, "燃烧吧孩子(๑•̀ㅂ•́) ✧", Toast.LENGTH_SHORT).show();
            binder=(Studyservice.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }
    //初始化界面
    private void init(){
        startbtn=(Button)findViewById(R.id.startbtn);
        stopbtn=(Button)findViewById(R.id.stopbtn);
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService();
                numberOfstops++;
            }
        });

    }

    //启动service
    public void startService(){
        Intent intent=new Intent(this,Studyservice.class);
        bindService(intent, conn, Service.BIND_AUTO_CREATE);
    }
    //停止service
    public void stopService(){
        try{
            unbindService(conn);
            String studytimespan;
            int timespan=binder.getTimespan();
            if (timespan<30){
                studytimespan="才"+timespan+"分钟你就想休息了？！！回去学！！";
            }else{
                studytimespan="已燃烧"+timespan+"分钟"+"加油(。・`ω´・)";
            }
            Toast.makeText(MainActivity.this, "熄火了(⊙v⊙)", Toast.LENGTH_SHORT).show();
            interval();
            Toast.makeText(MainActivity.this, studytimespan, Toast.LENGTH_SHORT).show();
            havestudied=true;
        }catch (Exception e){
            if (havestudied){
                Toast.makeText(MainActivity.this, "继续烧吗", Toast.LENGTH_SHORT).show();
            }
            switch (numberOfstops){
                case 0:
                    Toast.makeText(MainActivity.this, "还没开始烧你就燃尽了。。我也是醉了", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(MainActivity.this, "别点了，点一万次我也不会讲笑话给你听的", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(MainActivity.this, "第三次", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(MainActivity.this, "第四次", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(MainActivity.this, "第五次", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(MainActivity.this, "你真有毅力", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Toast.makeText(MainActivity.this, "第六次", Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    Toast.makeText(MainActivity.this, "第七次", Toast.LENGTH_SHORT).show();
                    break;
                case 8:
                    Toast.makeText(MainActivity.this, "不玩了不玩了玩不过你行了吧╮(╯_╰)╭", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }

    //等待0.5秒
    public void interval(){
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
