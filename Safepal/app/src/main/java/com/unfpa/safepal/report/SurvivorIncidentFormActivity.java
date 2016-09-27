package com.unfpa.safepal.report;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.unfpa.safepal.R;
import com.unfpa.safepal.Referral.ReferralActivity;
import com.unfpa.safepal.datepicker.DatePickerFragment;
import com.unfpa.safepal.network.AppController;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class SurvivorIncidentFormActivity extends AppCompatActivity {

    /*
     * sif - Stands for "Survivor Incident Form"
     * */

    /*** Global Variables **/

    //User Interface
    Toolbar sifToolbar;
    FloatingActionButton sifAbortAppFab, sifBackFab, sifSubmitFab;
    //Form
    private Button sifDateOfBirthButton;
    private RadioGroup sifGenderRG;
    private RadioButton sifGenderRB;
    private Spinner sifIncidentTypeSpinner;
    private EditText sifIncidentLocationEt, sifIncidentDetailsEt;
    private TextView sifEcouragingMessagesTv;
    private Snackbar sifFeedbackSnackbar;

    //system
    private String currentDateTime;

    //content provider
    Bundle extras;
    private Uri reportIncidentUri;
    final String URL_SAFEPAL_API = "http://52.43.152.73/api/addselfreport.php";



    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_survivor_incident_form);

        sifToolbar = (Toolbar) findViewById(R.id.sif_toolbar);
        //Abort fab of  sif activity
        sifAbortAppFab = (FloatingActionButton) findViewById(R.id.sif_abort_app_fab);
        //Back fab of  sif activity
        sifBackFab = (FloatingActionButton) findViewById(R.id.sif_back_fab);
        sifSubmitFab = (FloatingActionButton)findViewById(R.id.sif_submit_fab);

        sifDateOfBirthButton = (Button)findViewById(R.id.sif_date_of_birth_button);

        sifGenderRG=(RadioGroup)findViewById(R.id.sif_gender_rg);
        sifIncidentTypeSpinner = (Spinner) findViewById(R.id.sif_incident_type_spinner);
        sifIncidentLocationEt = (EditText)findViewById(R.id.sif_incident_location_actv);
        sifIncidentDetailsEt = (EditText)findViewById(R.id.sif_incident_details_rt);

        sifEcouragingMessagesTv= (TextView) findViewById(R.id.sif_ecouraging_messages_tv);
        //Toolbar
        setSupportActionBar(sifToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //content provider
        extras = getIntent().getExtras();
        // check from the saved Instance
        reportIncidentUri = (bundle == null) ? null : (Uri) bundle
                .getParcelable(ReportIncidentContentProvider.CONTENT_ITEM_TYPE);

        sifAbortAppFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(2);
            }
        });
        sifBackFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WhoSGettingHelpActivity.class));
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> sifIncidentTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.sif_incident_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        sifIncidentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the sifIncidentTypeAdapter to the spinner
        sifIncidentTypeSpinner.setAdapter(sifIncidentTypeAdapter);


    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    /**
     * Code for referral was added here
     * @param view
     */
    public void onClickAddIncidentSurvivor(View view) {

        int genderRBId = sifGenderRG.getCheckedRadioButtonId();

        //checks if gender radio group isn't selected;
        if(genderRBId==-1){
            //feedback to developer
            sifFeedbackSnackbar = Snackbar.make(view,"Tell us your gender please!!!",Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            return;
        }
        View genderRBView = sifGenderRG.findViewById(genderRBId);
        int idx = sifGenderRG.indexOfChild(genderRBView);
        sifGenderRB = (RadioButton) sifGenderRG.getChildAt(idx);
        //end check of radio group



        String reportedBy = "Survivor";
        String survivorDateOfBirth = sifDateOfBirthButton.getText().toString();;
        String survivorGender = (String)sifGenderRB.getText();
        String incidentType =(String)sifIncidentTypeSpinner.getSelectedItem();
        String incidentLocation = sifIncidentLocationEt.getText().toString();
        String incidentStory = sifIncidentDetailsEt.getText().toString();;
        String uniqueIdentifier = "testuid";;

        Intent referralIntet = new Intent(getApplicationContext(),ReferralActivity.class);


        if(sifDateOfBirthButton.getText().toString()== getResources().getText(R.string.sif_survivor_pick_age)){
            sifFeedbackSnackbar = Snackbar.make(view,"Pick a date of birth",Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            return;
        }


        if (sifIncidentTypeSpinner.getSelectedItemPosition() <= 0) {
            sifFeedbackSnackbar = Snackbar.make(view,"Choose what happened to you.",Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            return;
        }


        // only save if either location or story
        // is available

        if (sifIncidentLocationEt.length() == 0 && sifIncidentDetailsEt.length() == 0) {
            //feedback to developer
            sifFeedbackSnackbar = Snackbar.make(view, "Story or location is not filled", Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            return;
        }



        ContentValues values = new ContentValues();
        values.put(ReportIncidentTable.COLUMN_REPORTED_BY, reportedBy);
        values.put(ReportIncidentTable.COLUMN_SURVIVOR_DATE_OF_BIRTH, survivorDateOfBirth);
        values.put(ReportIncidentTable.COLUMN_SURVIVOR_GENDER, survivorGender);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_TYPE, incidentType);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_LOCATION, incidentLocation);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_STORY, incidentStory);
        values.put(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER, uniqueIdentifier);


        if (reportIncidentUri == null) {
            // New reported incident
            reportIncidentUri = getContentResolver().insert(ReportIncidentContentProvider.CONTENT_URI, values);
            startActivity(referralIntet);
                        //
                        // feedback to developer
        Toast.makeText(getBaseContext(),currentDateTime, Toast.LENGTH_LONG).show();
         // Or passed from the other activity
                Toast.makeText(getBaseContext(),
                        " The report has been successfully submitted. ", Toast.LENGTH_LONG).show();



        } else {
            // Update reported incident
            getContentResolver().update(reportIncidentUri, values, null, null);
        }
        populateOnline(survivorGender, survivorDateOfBirth, incidentType, incidentLocation, "WTBC", incidentStory, reportedBy);

        ///;
        //referral of the user to the CSOs
        //startActivity( new Intent(getBaseContext(), ReferralActivity.class));

    }




    // Method pushes the data to json server suing volley
    private void populateOnline(final String toServerSGender, final String toServerSDOB, final String toServerIType,
                                final String toServerILocation, final String toServerStatus, final String toServerIDescription,
                                final String toServerReportedBy){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAFEPAL_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SurvivorIncidentFormActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        //sendUniqueIdIntent.putExtra("fromOnlineUniqueId", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SurvivorIncidentFormActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("survivor_gender",toServerSGender);
                params.put("survivor_date_of_birth",toServerSDOB);
                params.put("incident_type", toServerIType);
                params.put("incident_location",toServerILocation);
                params.put("status",toServerStatus);
                params.put("incident_description", toServerIDescription);
                params.put("reported_by", toServerReportedBy);
                return params;
            }

            @Override
            protected void deliverResponse(String response) {

            }
        };

// Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void fffillData(Uri uri) {


        String[] projection = {
                ReportIncidentTable.COLUMN_SURVIVOR_GENDER,
                ReportIncidentTable.COLUMN_SURVIVOR_DATE_OF_BIRTH,
                ReportIncidentTable.COLUMN_INCIDENT_TYPE,
                ReportIncidentTable.COLUMN_INCIDENT_LOCATION,
                ReportIncidentTable.COLUMN_INCIDENT_STORY,
                ReportIncidentTable.COLUMN_REPORTED_BY,
        };

        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            String category = cursor.getString(cursor
                    .getColumnIndexOrThrow(ReportIncidentTable.COLUMN_SURVIVOR_GENDER));


            sifEcouragingMessagesTv.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(ReportIncidentTable.COLUMN_SURVIVOR_GENDER)));
            Toast.makeText(SurvivorIncidentFormActivity.this,cursor.getString(cursor
                    .getColumnIndexOrThrow(ReportIncidentTable.COLUMN_SURVIVOR_GENDER)),Toast.LENGTH_LONG).show();

            // always close the cursor
            cursor.close();
        }
    }

    // pulls the items from the content provider
    private void CPLastItem() {
        // Run query
        ContentResolver cr = getContentResolver();
        String[] projection = {
                ReportIncidentTable.COLUMN_SURVIVOR_GENDER,
                ReportIncidentTable.COLUMN_SURVIVOR_DATE_OF_BIRTH,
                ReportIncidentTable.COLUMN_INCIDENT_TYPE,
                ReportIncidentTable.COLUMN_INCIDENT_LOCATION,
                ReportIncidentTable.COLUMN_INCIDENT_STORY,
                ReportIncidentTable.COLUMN_REPORTED_BY,
        };
        Uri uri = Uri.parse(ReportIncidentContentProvider.CONTENT_ITEM_TYPE);

        // Submit the query and get a Cursor object back.
        Cursor cursor = cr.query(reportIncidentUri, projection, null, null, null);

        cursor.moveToFirst();

            String sgFromDp = cursor.getString(0);
            String sdobFromDp = cursor.getString(1);
            String itFromCP = cursor.getString(2);
            String sgFromCP = cursor.getString(3);
            String inFromCP = cursor.getString(4);
            String rbFromCP = cursor.getString(5);
            sifEcouragingMessagesTv.setText("1." + sgFromDp + "2." + sdobFromDp + "3." + itFromCP + "4." + sgFromCP + "5." + inFromCP + "6." + rbFromCP);


        cursor.close();
    }
}
