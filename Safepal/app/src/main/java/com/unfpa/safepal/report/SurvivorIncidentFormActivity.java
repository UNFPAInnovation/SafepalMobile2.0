package com.unfpa.safepal.report;

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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unfpa.safepal.ProvideHelp.ContactActivity;
import com.unfpa.safepal.R;
import com.unfpa.safepal.datepicker.DatePickerFragment;
import com.unfpa.safepal.messages.messageDialog;
import com.unfpa.safepal.network.MySingleton;
import com.unfpa.safepal.network.VolleyCallback;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.unfpa.safepal.report.WhoSGettingHelpActivity.randMessageIndex;


public class SurvivorIncidentFormActivity extends AppCompatActivity {

    /*
     * sif - Stands for "Survivor Incident Form"
     * */

    /*** Global Variables **/

    //User Interface
    Toolbar sifToolbar;
    FloatingActionButton sifAbortAppFab, sifBackFab, sifSubmitFab;
    //messages
    TextView sifEncouragingMessagesTv;

    //Form
    private Button sifDateOfBirthButton;
    private RadioGroup sifGenderRG;
    private RadioButton sifGenderRB;
    private Spinner sifIncidentTypeSpinner;
    private EditText sifIncidentLocationEt, sifIncidentDetailsEt;

    private Snackbar sifFeedbackSnackbar;

    //Intents
   public static Intent referralIntent;

    //content provider
    Bundle extras;
    private Uri reportIncidentUri;

    //Volley requests
    final String URL_SAFEPAL_API = "http://52.43.152.73/api/addselfreport.php";
    String UniqueIdFromServer="";



    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_survivor_incident_form);
        // Volley Request
        // check from the saved Instance
        reportIncidentUri = (bundle == null) ? null : (Uri) bundle
                .getParcelable(ReportIncidentContentProvider.CONTENT_ITEM_TYPE);


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

        sifEncouragingMessagesTv= (TextView) findViewById(R.id.sif_ecouraging_messages_tv);
        //Toolbar
        setSupportActionBar(sifToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //content provider
        extras = getIntent().getExtras();


        //messages to user
        loadSifMessages();

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
        DialogFragment dateFragment = new DatePickerFragment();

        dateFragment.show(getSupportFragmentManager(), "datePicker");
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

        referralIntent = new Intent(SurvivorIncidentFormActivity.this, ContactActivity.class);


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

               // Or passed from the other activity
                Toast.makeText(getBaseContext(), " The report is temporarily stored. Awaiting Network Connection.", Toast.LENGTH_LONG).show();
                referralIntent.putExtra("uniqueFromServerSent", UniqueIdFromServer);
                startActivity(referralIntent);
                retrieveUniqueId();


        } else {
            // Update reported incident
            getContentResolver().update(reportIncidentUri, values, null, null);
        }


    }




    //Returns the unique_id from the server
    public  void retrieveUniqueId() {

        // Retrieve report records

        Uri uriReturnReports = Uri.parse(ReportIncidentContentProvider.CONTENT_URI + "/");
        Cursor c = managedQuery(uriReturnReports, null, null, null, null);

        if (c != null) {
            c.moveToLast();

            populateOnline(
                    c.getString(c.getColumnIndex(ReportIncidentTable.COLUMN_SURVIVOR_GENDER)),
                    c.getString(c.getColumnIndex(ReportIncidentTable.COLUMN_SURVIVOR_DATE_OF_BIRTH)),
                    c.getString(c.getColumnIndex(ReportIncidentTable.COLUMN_INCIDENT_TYPE)),
                    c.getString(c.getColumnIndex(ReportIncidentTable.COLUMN_INCIDENT_LOCATION)),
                    "WTBD",
                    c.getString(c.getColumnIndex(ReportIncidentTable.COLUMN_INCIDENT_STORY)),
                    c.getString(c.getColumnIndex(ReportIncidentTable.COLUMN_REPORTED_BY)),
                    new VolleyCallback() {



                        @Override
                        public void onSuccessResponse(String result) {
                            try {
                                JSONObject response = new JSONObject(result);



                                Toast.makeText(SurvivorIncidentFormActivity.this,"Your safepal number is "+response.getString("unique_code"),Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }

            );
            c.close();
        }
    }


    // Method pushes the data to json server suing volley
    private void populateOnline(final String toServerSGender,
                                final String toServerSDOB,
                                final String toServerIType,
                                final String toServerILocation,
                                final String toServerStatus,
                                final String toServerIDescription,
                                final String toServerReportedBy, final VolleyCallback callback){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAFEPAL_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       callback.onSuccessResponse(response);
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

        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    //load messages to tv
    public void  loadSifMessages(){
        String[] sifMessagesArray = getResources().getStringArray(R.array.seek_medical_care_messages);
        sifEncouragingMessagesTv.setText(sifMessagesArray[randMessageIndex(0, sifMessagesArray.length)].toString());
    }

    public void onClickSifEncouragingMessages(View view){
        messageDialog sifMessageDialog = new messageDialog(sifEncouragingMessagesTv);
        sifMessageDialog.show(getSupportFragmentManager(), "messages");
    }

    public void onClickSifIVSpinner(View view){

        sifIncidentTypeSpinner.performClick();
    }


}
