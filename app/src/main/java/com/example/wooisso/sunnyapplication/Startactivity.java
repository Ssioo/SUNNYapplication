package com.example.wooisso.sunnyapplication;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class Startactivity extends AppCompatActivity {
    private final static String TAG = Startactivity.class.getSimpleName();

    public final static int APP_LIST_RESULT_ACITIVTYKEY = 100;
    public final static String USE_APP_LIST = "app_list";
    public final static String USE_APP_MODE = "app_allowed_mode";
    Button startButton = null;
    TextView appSelectTextView = null;

    Context activityContext =  null;
    ArrayList<String> appList = new ArrayList<String>();
    boolean            appMode = false;

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
        // 저장된 시간을 불러와 Button 위의 Text로 바꾸기p


        // 저장된 시간을 불러와 D-시간 계산하기 # Handler 사용

        Intent intent = new Intent(Startactivity.this, MyCounterService.class);
        startService(intent);

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


        appSelectTextView = (TextView) findViewById(R.id.app_list_text);

        startButton = (Button) findViewById(R.id.Edit_Button);
        activityContext = this;

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Click Button");
                Intent applist = new Intent(activityContext, AppPackageSelectListVIew.class);
                startActivityForResult(applist, APP_LIST_RESULT_ACITIVTYKEY);
            }
        });

    }

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        if (request == APP_LIST_RESULT_ACITIVTYKEY && result == Activity.RESULT_OK) {
            if (data == null) {
                return;  // failed
            }

            ArrayList<String> applist = data.getStringArrayListExtra(USE_APP_LIST);

            if (applist != null && applist.size() != 0) {
                StringBuilder stringBuf = new StringBuilder();
                for (String appPackageName : applist) {
                    Log.d(TAG, "App getpackage name :" + appPackageName);
                    stringBuf.append("App getpackage name :" + appPackageName);
                    stringBuf.append("\r\n");
                }

                // 앱 설정 리스트 획득 // get App list
                appList = (ArrayList<String>) applist.clone();
                // 앱 설정 모드 획득 // get Set App Mode
                appMode = data.getBooleanExtra(USE_APP_MODE, true);


                Log.d(TAG, "App Mode :" + appMode);
                stringBuf.append("\r\n");
                stringBuf.append("App Mode :" + appMode);

                appSelectTextView.setText(stringBuf);

            } else {
                Log.e(TAG, " onAcitivyResult Not Define : " +
                        request + " : " + (result == Activity.RESULT_OK ? "ok" : "cancel"));

            }

            return;
        }
    }
}
