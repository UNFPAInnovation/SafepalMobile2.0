package com.unfpa.safepal.datepicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;


import com.unfpa.safepal.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * Created by root on 2/16/16.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it

        Calendar c_max = Calendar.getInstance();
        Calendar c_min = Calendar.getInstance();

        c_min.set(1980,0,1);  // your min date boundary
        c_max.set(2006,11,31); // your max date boundary

        DatePickerDialog d = new DatePickerDialog(getActivity(), this, year, month, day);

        DatePicker dp = d.getDatePicker();
        dp.setMinDate(c_min.getTimeInMillis());
        dp.setMaxDate(c_max.getTimeInMillis());

        return d;
    }


    public void onDateSet(DatePicker view, int year, int month, int day) {
        //assign the date to the button
       Button rfyButtonDate = (Button)getActivity().findViewById(R.id.sif_date_of_birth_button);
        rfyButtonDate.setText(String.valueOf(day)+" - "+getMonth(month) +" - " + String.valueOf(year));
    }

    //Coverstion of month from interger to text
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }


}