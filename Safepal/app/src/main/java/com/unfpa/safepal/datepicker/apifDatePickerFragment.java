package com.unfpa.safepal.datepicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;

import com.unfpa.safepal.R;

import java.util.Calendar;

/**
 * Created by Kisa on 9/23/2016.
 */
public class apifDatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //assign the date to the button
        Button rfyButtonDate = (Button)getActivity().findViewById(R.id.apif_date_of_birth_button);
        rfyButtonDate.setText( String.valueOf(month)+" / "+ String.valueOf(day)+" / "+ String.valueOf(year));
    }
}
