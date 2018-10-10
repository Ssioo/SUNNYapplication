package com.example.wooisso.sunnyapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;

public class MyCounterService extends Service {


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

    public MyCounterService() {    }

    private class Counter implements Runnable {


        private Handler handler = new Handler();

        @Override
        public void run() {

            for (count=0;count<50;count++) {
                if (isStop) {
                    break;
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 실행할 내용
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
