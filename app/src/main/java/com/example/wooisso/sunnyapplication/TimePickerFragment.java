package com.example.wooisso.sunnyapplication;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;


import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
                    implements TimePickerDialog.OnTimeSetListener{
    @Override
    public void onTimeSet(TimePicker timePicker, int hourofday, int minute) {



    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar mCalendar = Calendar.getInstance();

        //SharedPreferences st = getSharedPreferences("timeinfo",MODE_PRIVATE);

        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int min = mCalendar.get(Calendar.MINUTE);
        TimePickerDialog mTimePickerDialog = new TimePickerDialog(
                getContext(), android.R.style.Theme_DeviceDefault_Light,this, hour, min, true
        );

        return mTimePickerDialog;
    }

    public int getHour() {
        Calendar mCalendar = Calendar.getInstance();
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public int getMin() {
        Calendar mCalendar = Calendar.getInstance();
        int min = mCalendar.get(Calendar.MINUTE);
        return min;
    }


}
