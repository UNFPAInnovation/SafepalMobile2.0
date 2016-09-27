package com.unfpa.safepal.report;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.unfpa.safepal.R;
import com.unfpa.safepal.Referral.ReferralActivity;
import com.unfpa.safepal.datepicker.DatePickerFragment;
import com.unfpa.safepal.datepicker.apifDatePickerFragment;
import com.unfpa.safepal.home.HomeActivity;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

public class AnotherPersonIncidentFormActivity extends AppCompatActivity {

    /**
     * apif - Stands for "Another Person Incident Form"
     * */

    //Global Variables
    FloatingActionButton apifAbortAppFab, apifBackFab, apifNextFab;

    //Form variables
    private Button apifDateOfBirthButton;
    private RadioGroup apifGenderRG;
    private RadioButton apifGenderRB;
    private Spinner apifIncidentTypeSpinner;
    private EditText apifIncidentLocationEt, apifIncidentDetailsEt;


    private Uri reportIncidentUri;
    final String URL_SAFEPAL_API = "http://52.43.152.73/api/addselfreport.php";

    private Snackbar apifFeedbackSnackbar;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_another_person_incident_form);

        Toolbar apifToolbar = (Toolbar) findViewById(R.id.apif_toolbar);
        //Abort fab of  who's getting help activity
        apifAbortAppFab = (FloatingActionButton) findViewById(R.id.apif_abort_app_fab);
        //Back fab of  who's getting help activity
        apifBackFab = (FloatingActionButton) findViewById(R.id.apif_back_fab);

       /*** Form initialization **/
        apifGenderRG=(RadioGroup)findViewById(R.id.apif_gender_rg);
        apifIncidentTypeSpinner = (Spinner) findViewById(R.id.apif_incident_type_spinner);
        apifIncidentLocationEt = (EditText)findViewById(R.id.apif_incident_location_actv);
        apifIncidentDetailsEt = (EditText)findViewById(R.id.apif_incident_details_rt);

        apifDateOfBirthButton = (Button)findViewById(R.id.sif_date_of_birth_button);

        setSupportActionBar(apifToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apifAbortAppFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        apifBackFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WhoSGettingHelpActivity.class));
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> apifIncidentTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.apif_incident_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        apifIncidentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the apifIncidentTypeAdapter to the spinner
        apifIncidentTypeSpinner.setAdapter(apifIncidentTypeAdapter);
    }





    public void showApifDatePickerDialog(View v) {
        DialogFragment newFragment = new apifDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
