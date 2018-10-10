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
import android.widget.TextView;

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
                        try {
                            //
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });

                try {
                    Thread.sleep(500);
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
        startService(intent);
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
}

