package com.unfpa.safepal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AnotherPersonIncidentFormActivity extends AppCompatActivity {

    /**
     * apif - Stands for "Another Person Incident Form"
     * */

    //Global Variables
    Spinner apifIncidentTypeSpinner;
    FloatingActionButton apifAbortAppFab, apifBackFab, apifNextFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_person_incident_form);

        Toolbar apifToolbar = (Toolbar) findViewById(R.id.apif_toolbar);
        //Abort fab of  who's getting help activity
        apifAbortAppFab = (FloatingActionButton) findViewById(R.id.apif_abort_app_fab);
        //Back fab of  who's getting help activity
        apifBackFab = (FloatingActionButton) findViewById(R.id.apif_back_fab);

        // choose incident type spinner
        apifIncidentTypeSpinner = (Spinner) findViewById(R.id.apif_incident_type_spinner);


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

}
