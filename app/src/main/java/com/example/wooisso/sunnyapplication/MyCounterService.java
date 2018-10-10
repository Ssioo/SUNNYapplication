package com.example.wooisso.sunnyapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class MyCounterService extends Service {


    private int count;
    private boolean isStop;

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



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
