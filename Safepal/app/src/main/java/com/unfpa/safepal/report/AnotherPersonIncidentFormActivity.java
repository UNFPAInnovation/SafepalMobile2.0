package com.unfpa.safepal.report;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.unfpa.safepal.R;
import com.unfpa.safepal.Referral.ReferralActivity;
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
    private RadioGroup apifGenderRG;
    private RadioButton apifGenderRB;
    private Spinner apifIncidentTypeSpinner;
    private EditText apifIncidentLocationEt, apifIncidentDetailsEt;

    //Content Providers
    private Uri apifReportIncidentUri;
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
        apifIncidentLocationEt = (EditText)findViewById(R.id.apif_incident_location_et);
        apifIncidentDetailsEt = (EditText)findViewById(R.id.apif_incident_details_rt);
        apifReportIncidentUri = (bundle == null) ? null : (Uri) bundle
                .getParcelable(ReportIncidentContentProvider.CONTENT_ITEM_TYPE);


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

    /**
     * Code for referral was added here
     * @param view
     */
    public void onClickAddIncidentApif(View view){
        int apifGenderRBId = apifGenderRG.getCheckedRadioButtonId();

        //checks if gender radio group isn't selected;
        if(apifGenderRBId==-1){
            //feedback to developer
            apifFeedbackSnackbar = Snackbar.make(view,"Tell us your gender please!!!",Snackbar.LENGTH_LONG);
            apifFeedbackSnackbar.show();
            return;
        }
        View apifGenderRBView = apifGenderRG.findViewById(apifGenderRBId);
        int idx = apifGenderRG.indexOfChild(apifGenderRBView);
        apifGenderRB = (RadioButton) apifGenderRG.getChildAt(idx);
        //end check of radio group

        String apifReportedBy = getIntent().getStringExtra("relationshipToSurvivor");
        String apifSurvivorAge = "20";
        String apifSurvivorGender = (String)apifGenderRB.getText();
        String apifIncidentType =(String)apifIncidentTypeSpinner.getSelectedItem();
        String apifIncidentLocation = apifIncidentLocationEt.getText().toString();
        String apifIncidentStory = apifIncidentDetailsEt.getText().toString();;
        String apifUniqueIdentifier = "test_uid";;

        if (apifIncidentTypeSpinner.getSelectedItemPosition() <= 0) {
            apifFeedbackSnackbar = Snackbar.make(view,"Choose what happened to him or her.",Snackbar.LENGTH_LONG);
            apifFeedbackSnackbar.show();
            return;
        }

            if (apifIncidentLocationEt.length() == 0 && apifIncidentDetailsEt.length() == 0) {
            //feedback to developer
            apifFeedbackSnackbar = Snackbar.make(view, "Story or location is not filled", Snackbar.LENGTH_LONG);
            apifFeedbackSnackbar.show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(ReportIncidentTable.COLUMN_REPORTED_BY, apifReportedBy);
        values.put(ReportIncidentTable.COLUMN_SURVIVOR_DATE_OF_BIRTH, apifSurvivorAge);
        values.put(ReportIncidentTable.COLUMN_SURVIVOR_GENDER, apifSurvivorGender);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_TYPE, apifIncidentType);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_LOCATION, apifIncidentLocation);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_STORY, apifIncidentStory);
        values.put(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER, apifUniqueIdentifier );

        if (apifReportIncidentUri == null) {
            // New reported incident
            apifReportIncidentUri = getContentResolver().insert(
                    ReportIncidentContentProvider.CONTENT_URI, values);
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            //feedback to developer
            Toast.makeText(getBaseContext(),
                    apifReportIncidentUri.toString() + " success\n"+ apifReportedBy, Toast.LENGTH_LONG).show();


        } else {
            // Update reported incident
            getContentResolver().update(apifReportIncidentUri, values, null, null);
        }


        //referral of the user to the CSOs
        startActivity( new Intent(getBaseContext(), ReferralActivity.class));

    }

}
