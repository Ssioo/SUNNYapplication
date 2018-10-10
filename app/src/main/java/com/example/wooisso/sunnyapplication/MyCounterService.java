package com.example.wooisso.sunnyapplication;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Button;

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
                        // 실행할 내용
                        long now = System.currentTimeMillis();

                        SharedPreferences st = getSharedPreferences("timeinfo",MODE_PRIVATE);
                        long setnow = st.getLong("time_mil",0);

                        long timer = setnow - now;


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
