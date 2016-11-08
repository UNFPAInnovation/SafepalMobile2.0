package com.unfpa.safepal.report;

import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.unfpa.safepal.ProvideHelp.ContactActivity;
import com.unfpa.safepal.R;
import com.unfpa.safepal.datepicker.apifDatePickerFragment;
import com.unfpa.safepal.messages.EMessageDialogFragment;
import com.unfpa.safepal.network.NetworkChangeReceiver;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import static com.unfpa.safepal.report.SurvivorIncidentFormActivity.netReceiver;
import static com.unfpa.safepal.report.SurvivorIncidentFormActivity.reportIncidentUri;

import static com.unfpa.safepal.report.WhoSGettingHelpActivity.randMessageIndex;

public class AnotherPersonIncidentFormActivity extends AppCompatActivity {

    /**
     * apif - Stands for "Another Person Incident Form"
     * */

    /**
     * Global Variables*/

    /* User Interface*/

    //apif toolbar
    Toolbar apifToolbar;
    //Floating action buttons
    FloatingActionButton apifAbortAppFab, apifBackFab, apifSubmitFab;
    //Encouraging messages
    TextView apifEncouragingMessagesTv;

    //Form variables
    private Button apifDateOfBirthButton;
    private RadioGroup apifGenderRG;
    private RadioButton apifGenderRB;
    private Spinner apifIncidentTypeSpinner;
    private EditText apifIncidentLocationEt, apifIncidentDetailsEt;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_another_person_incident_form);


        /** Declaration of user interface **/
        apifToolbar = (Toolbar) findViewById(R.id.apif_toolbar);
        apifAbortAppFab = (FloatingActionButton) findViewById(R.id.apif_abort_app_fab);
        apifBackFab = (FloatingActionButton) findViewById(R.id.apif_back_fab);
        apifSubmitFab = (FloatingActionButton) findViewById(R.id.apif_submit_fab);

        //encouraging messages
        apifEncouragingMessagesTv = (TextView)findViewById(R.id.apif_ecouraging_messages_tv);

       /** Form initialization **/
        apifDateOfBirthButton = (Button)findViewById(R.id.apif_date_of_birth_button);

        apifGenderRG=(RadioGroup)findViewById(R.id.apif_gender_rg);
        apifIncidentTypeSpinner = (Spinner) findViewById(R.id.apif_incident_type_spinner);

        apifIncidentLocationEt = (EditText)findViewById(R.id.apif_incident_location_actv);
        apifIncidentDetailsEt = (EditText)findViewById(R.id.apif_incident_details_rt);

        //setting up apif toolbar with logo
        setSupportActionBar(apifToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //load messages
        apifLoadMessages();

        //click actions
        //exit application
        apifAbortAppFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                }
        });

        //uninstall application
        apifAbortAppFab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Uri packageURI = Uri.parse("package:com.unfpa.safepal");
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);
                return true;
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

    public void apifLoadMessages(){
        String[] apifMessagesArray = getResources().getStringArray(R.array.seek_medical_care_messages);
        apifEncouragingMessagesTv.setText(apifMessagesArray[randMessageIndex(0, apifMessagesArray.length)].toString());
    }

    public void onClickSubmitApifIncident(View view){

        int genderRBApifId = apifGenderRG.getCheckedRadioButtonId();

        if(genderRBApifId==-1){
            Toast.makeText(getBaseContext(), "Select the survivors gender?",Toast.LENGTH_LONG).show();
            return;
        }

        View genderApifRBView = apifGenderRG.findViewById(genderRBApifId);
        int idx = apifGenderRG.indexOfChild(genderApifRBView);
        apifGenderRB =(RadioButton)apifGenderRG.getChildAt(idx);

        //checks if the location of the incident is filled by the user
        if (apifIncidentLocationEt.length() == 0 ) {
            if(idx==0) Toast.makeText(getBaseContext(), "In what location did the incident happen her?",Toast.LENGTH_LONG).show();
            else       Toast.makeText(getBaseContext(), "In what location did the incident happen him?",Toast.LENGTH_LONG).show();
            return;
        }

        if (apifIncidentTypeSpinner.getSelectedItemPosition() <= 0) {
            if(idx==0) Toast.makeText(getBaseContext(), "Select the type of incident that happened to her.",Toast.LENGTH_LONG).show();
            else       Toast.makeText(getBaseContext(), "Select the type of incident happened to him.",Toast.LENGTH_LONG).show();
            return;
        }
        //checks if the a proper story is told by the survivor
        if ( apifIncidentDetailsEt.length() == 0) {
            if(idx==0) Toast.makeText(getBaseContext(), "Kindly narrate the story of the incident that happened her",Toast.LENGTH_LONG).show();
            else Toast.makeText(getBaseContext(), "Kindly narrate the story of the incident that happened him",Toast.LENGTH_LONG).show();
            return;
        }


        String apifReportedBy = getIntent().getExtras().getString("relationshipToSurvivor");
        String apifSurvivorDateOfBirth = apifDateOfBirthButton.getText().toString();;
        String apifSurvivorGender = (String)apifGenderRB.getText();
        String apifIncidentType =(String)apifIncidentTypeSpinner.getSelectedItem();
        String apifIncidentLocation = apifIncidentLocationEt.getText().toString();
        String apifIncidentStory = apifIncidentDetailsEt.getText().toString();;
        String apifUniqueIdentifier = SurvivorIncidentFormActivity.generateTempSafePalNumber(10000000,99999999);

        /**
         * inserts incident report in to the mysql db through a content provider
         * **/
        ContentValues values = new ContentValues();
        values.put(ReportIncidentTable.COLUMN_REPORTED_BY, apifReportedBy);
        values.put(ReportIncidentTable.COLUMN_SURVIVOR_DATE_OF_BIRTH, apifSurvivorDateOfBirth);
        values.put(ReportIncidentTable.COLUMN_SURVIVOR_GENDER, apifSurvivorGender);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_TYPE, apifIncidentType);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_LOCATION, apifIncidentLocation);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_STORY, apifIncidentStory);
        values.put(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER, apifUniqueIdentifier);

        /**
         *  Checks if the important fields are filled
         *  **/

        //checks if the incident type is selected
        //this inserts a new report in to the mysql db
        if (reportIncidentUri == null) {
            reportIncidentUri = getContentResolver().insert(ReportIncidentContentProvider.CONTENT_URI, values);

            Toast.makeText(getBaseContext(), " The report is temporarily stored.", Toast.LENGTH_SHORT).show();

            //Broadcast receiver that checks for the network status
            IntentFilter netMainFilter =  new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            netReceiver = new NetworkChangeReceiver();
            registerReceiver(netReceiver, netMainFilter);

            //starts a the  help activity
            startActivity(new Intent(getApplicationContext(), ContactActivity.class));
        }
        //updates the report if its already available
        else {
            getContentResolver().update(reportIncidentUri, values, null, null);
        }

    }

    public void onClickApifEncouragingMessages(View view){

        EMessageDialogFragment emDialog = EMessageDialogFragment.newInstance(
                getString(R.string.not_your_fault_alert_header),
                apifEncouragingMessagesTv.getText().toString(),
                getString(R.string.close_dialog));
        emDialog.show(getSupportFragmentManager(), "encouraging message");
    }
    //shows spinner dropdown for apif incident types
    public void onClickApifIVSpinner(View view){
        apifIncidentTypeSpinner.performClick();
    }

}
