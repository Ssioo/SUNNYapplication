package com.example.wooisso.sunnyapplication;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Startactivity extends AppCompatActivity {


    private iMyCounterService binder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            binder = iMyCounterService.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private class GetCountThread implements Runnable {

        private Handler handler = new Handler();

        @Override
        public void run() {

            while(running) {
                if (binder==null)
                    continue;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 실시간 D-HOUR 측정


                        SharedPreferences st = getSharedPreferences("timeinfo",MODE_PRIVATE);
                        long setnow = st.getLong("time_mil",0);
                        long timer;
                        boolean timesignal;



                        long now = (System.currentTimeMillis() + 9 * 60 * 60 * 1000)% (24 * 60 * 60 * 1000); // GMT +09:00 만큼 차이남. 우선 그냥 더했는데 해결법을 찾아야 할 듯함.


                        if (setnow > now) {
                            timer = (setnow - now) / 1000; // ( Hour * 3600  + Min * 60  + sec )* 10 # 1sec 단위
                            timesignal = true;
                        }
                        else {
                            timer = (now - setnow) / 1000; // ( Hour * 3600  + Min * 60  + sec )* 10 # 1sec 단위)
                            timesignal = false;
                        }

                        // SystemCurrrentTimeMillis 는 1970년 1월 1일 부터 진행한 ms

                        int setsec = (int) timer % 60;
                        int setmin = (int) (timer / 60) % 60;
                        int sethour = (int) timer / 3600;




                        TextView txt1 = findViewById(R.id.D_HOUR);
                        if (timesignal)
                            txt1.setText(String.format("D - %02d:%02d:%02d", sethour, setmin, setsec));
                        else
                            txt1.setText(String.format("D + %02d:%02d:%02d", sethour, setmin, setsec));



                    }
                });

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private Intent intent;
    private boolean running = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startactivity);

        final Button btn1 = (Button)findViewById(R.id.main_time);


        // App package id 저장하기
        SharedPreferences appid  = getSharedPreferences("appinfo", MODE_PRIVATE);

        // 저장된 값을 불러와 Switch 값 변경하기

        Switch swc = (Switch)findViewById(R.id.switch_faceook);
        swc.setChecked(appid.getBoolean("com.facebook.katana",false));
        swc = (Switch)findViewById(R.id.switch_kakao);
        swc.setChecked(appid.getBoolean("com.kakao.talk", false));
        swc = (Switch)findViewById(R.id.switch_youtube);
        swc.setChecked(appid.getBoolean("com.google.android.youtube", false));



        // 저장된 시간을 불러와 Button 위의 Text로 바꾸기
        SharedPreferences st = getSharedPreferences("timeinfo",MODE_PRIVATE);

        String str = st.getString("time","");
        if (str != "") {
            btn1.setText(str);
        }
        else {
            SharedPreferences.Editor editor = st.edit();
            editor.putString("time","00:00");
            editor.commit();
            btn1.setText("00:00");
        }
        // 저장된 시간을 불러와 Button 위의 Text로 바꾸기


        // 저장된 시간을 불러와 D-시간 계산하기 # Handler 사용

        Intent intent = new Intent(Startactivity.this, MyCounterService.class);
        //startService(intent);
        bindService(intent, connection, BIND_AUTO_CREATE);
        running = true;
        new Thread(new GetCountThread()).start();

        // 저장된 시간을 불러와 D-시간 계산하기

        // Button을 눌러 TimePicker 호출하기
        btn1.setOnClickListener(
                new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        TimePickerFragment mTimePickerFragment = new TimePickerFragment();
                        mTimePickerFragment.show(getSupportFragmentManager(), "timePicker"); // show
                        // Timepicker 호출 완료

                    }
                }

        );
        // 저장된 시간을 불러와 Button 위의 Text로 바꾸기



    }

    @Override
    protected void onStop() {
        super.onStop();
        //Activity 종료 전 저장
        SharedPreferences app = getSharedPreferences("appinfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = app.edit();
        Switch swc = (Switch)findViewById(R.id.switch_faceook);
        editor.putBoolean("com.facebook.katana", swc.isChecked());
        swc = (Switch)findViewById(R.id.switch_kakao);
        editor.putBoolean("com.kakao.talk", swc.isChecked());
        swc = (Switch)findViewById(R.id.switch_youtube);
        editor.putBoolean("com.google.android.youtube", swc.isChecked());
        editor.commit();
    }
}

