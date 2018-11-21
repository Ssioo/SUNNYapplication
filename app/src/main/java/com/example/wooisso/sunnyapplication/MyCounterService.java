package com.example.wooisso.sunnyapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Button;
import android.widget.Toast;

public class MyCounterService extends Service {

    public MyCounterService() {    }

    private int count;
    private boolean isStop;


    iMyCounterService.Stub binder = new iMyCounterService.Stub() {
        @Override
        public int getCount() throws RemoteException {
            return count;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        isStop = true;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Thread counter = new Thread(new Counter());
        counter.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isStop = true;
        return super.onUnbind(intent);
    }



    private class Counter implements Runnable {


        private Handler handler = new Handler();

        @Override
        public void run() {

            while (!isStop) {


                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // BackGround 에서 실행할 내용
                        SharedPreferences st = getSharedPreferences("timeinfo",MODE_PRIVATE);
                        long setnow = st.getLong("time_mil",0);
                        long timer;
                        boolean timesignal;



                        long now = (System.currentTimeMillis() + 9 * 60 * 60 * 1000) % (24 * 60 * 60 * 1000); // GMT +09:00 만큼 차이남. 우선 9시간만큼 더했는데, 오류해결법을 찾아야할 듯함.


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


                        // Alarm Maker
                        if (timesignal = true && setsec == 0) {

                            Toast.makeText(getApplicationContext(),setmin + "분 남았습니다.", Toast.LENGTH_LONG).show();
                        }


                        // App Detector



                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            handler.post((new Runnable() {
                @Override
                public void run() {

                }
            }));

        }
    }




}
