package com.unfpa.safepal.datepicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.unfpa.safepal.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * Created by Kisa on 9/23/2016.
 */
public class apifDatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    int currentYear;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        currentYear = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, currentYear, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //assign the date to the button
//        Button rfyButtonDate = (Button)getActivity().findViewById(R.id.apif_date_of_birth_button);
//        rfyButtonDate.setText( String.valueOf(day)+" - "+ getMonth(month)+" - "+ String.valueOf(currentYear));

        TextView textViewChosenDate = (TextView)getActivity().findViewById(R.id.chosen_date);
        textViewChosenDate.setText( "Chosen date: " + String.valueOf(day)+"-"+ getMonth(month)+"-"+ String.valueOf(year)
         + " (" + (currentYear-year) + " Years)");
    }
    //Coverstion of month from interger to text
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

}
