package com.example.wooisso.sunnyapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.TextView;

public class Startactivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startactivity);

        final Button btn1 = (Button)findViewById(R.id.main_time);



        // Button을 눌러 TimePicker 호출하기
        btn1.setOnClickListener(
                new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        TimePickerFragment mTimePickerFragment = new TimePickerFragment();
                        mTimePickerFragment.show(getSupportFragmentManager(), "my_time_picker");
                        // Timepicker 호출 완료
                        int hour = mTimePickerFragment.getHour();
                        int min = mTimePickerFragment.getMin();

                        //SharedPreference에 timepicker 시간정보 저장
                        SharedPreferences st = getSharedPreferences("timeinfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = st.edit();
                        editor.remove("time");
                        editor.commit();

                        String hourstr, minstr;
                        if (hour<10)
                            hourstr = "0" + String.valueOf(hour);
                        else
                            hourstr = String.valueOf(hour);
                        if (min<10)
                            minstr = "0" + String.valueOf(min);
                        else
                            minstr = String.valueOf(min);
                        String newstr = hourstr + ":" + minstr;
                        editor.putString("time", newstr);
                        editor.commit();


                        btn1.setText(newstr);

                    }
                }

        );
        // 저장된 시간을 불러와 Button 위의 Text로 바꾸기

        // 저장된 시간을 불러와 Button 위의 Text로 바꾸기
        SharedPreferences st = getSharedPreferences("timeinfo",MODE_PRIVATE);

        String str = st.getString("time","");
        if (str != "") {
            btn1.setText(str);
        }
        if (str == "") {
            SharedPreferences.Editor editor = st.edit();
            editor.putString("time","00:00");
            editor.commit();
            btn1.setText("00:00");
        }
        // 저장된 시간을 불러와 Button 위의 Text로 바꾸기

    }
}

