package com.unfpa.safepal.report;

import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
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

import com.unfpa.safepal.ProvideHelp.ContactActivity;
import com.unfpa.safepal.R;
import com.unfpa.safepal.datepicker.DatePickerFragment;
import com.unfpa.safepal.messages.EMessageDialogFragment;
import com.unfpa.safepal.network.NetworkChangeReceiver;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;


import java.util.Random;

import static com.unfpa.safepal.report.WhoSGettingHelpActivity.randMessageIndex;


public class SurvivorIncidentFormActivity extends AppCompatActivity {

    /**
     * sif - Stands for "Survivor Incident Form"
     * */

    /**
     * Global Variables **/

    /*User Interface*/
    Toolbar sifToolbar;

    /**
     * Next and buttonExit button
     */
    Button buttonNext;
    Button buttonExit;

    //Encouraging messages
    TextView sifEncouragingMessagesTv;

    //Form variables
    private Button sifDateOfBirthButton;
    TextView textViewChosenDate;
    private RadioGroup sifGenderRG;
    private RadioButton sifGenderRB;
    private Spinner sifIncidentTypeSpinner;
    private EditText sifIncidentLocationEt, sifIncidentDetailsEt;

    private Snackbar sifFeedbackSnackbar;

    //Intents
    public static Intent provideHelpIntent;

    //content provider
    Bundle extras;
    public static Uri reportIncidentUri;

    //network changes broadcast receiver
    public static NetworkChangeReceiver  netReceiver = new NetworkChangeReceiver();


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
        buttonExit = (Button) findViewById(R.id.exit_app);
        buttonNext = (Button) findViewById(R.id.finish);


//        sifDateOfBirthButton = (Button)findViewById(R.id.sif_date_of_birth_button);
        textViewChosenDate = (TextView)findViewById(R.id.chosen_date);

        sifGenderRG=(RadioGroup)findViewById(R.id.gender_rg);
        sifIncidentTypeSpinner = (Spinner) findViewById(R.id.incident_type_spinner);
        sifIncidentLocationEt = (EditText)findViewById(R.id.incident_location_actv);
        sifIncidentDetailsEt = (EditText)findViewById(R.id.incident_details_rt);

        sifEncouragingMessagesTv= (TextView) findViewById(R.id.ecouraging_messages_tv);
        //Toolbar
        setSupportActionBar(sifToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //content provider
        extras = getIntent().getExtras();


        //messages to user
        loadSifMessages();
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> sifIncidentTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.sif_incident_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        sifIncidentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the sifIncidentTypeAdapter to the spinner
        sifIncidentTypeSpinner.setAdapter(sifIncidentTypeAdapter);


        //exit the  application on click of exit
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(2);
            }
        });

        //unistall application on long press of exit
        buttonExit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Uri packageURI = Uri.parse("package:com.unfpa.safepal");
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);

                return true;
            }
        });
 }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(netReceiver);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "could not unregister receiver");
        }
    }

    String TAG = SurvivorIncidentFormActivity.class.getSimpleName();

    public void showDatePickerDialog(View v) {
        DialogFragment dateFragment = new DatePickerFragment();

        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }
    /**
     * Code for referral was added here
     * @param view
     */
    public void onClickSubmitIncident(View view) {

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

        String reportedBy = "survivor";
//        String survivorDateOfBirth = sifDateOfBirthButton.getText().toString();;
        String apifSurvivorDateOfBirth = textViewChosenDate.getText().toString();
        String survivorGender = (String)sifGenderRB.getText();
        String incidentType =(String)sifIncidentTypeSpinner.getSelectedItem();
        String incidentLocation = sifIncidentLocationEt.getText().toString();
        String incidentStory = sifIncidentDetailsEt.getText().toString();;
        String uniqueIdentifier = generateTempSafePalNumber(1000000,9999999);

        //declarations of intents
        provideHelpIntent = new Intent(SurvivorIncidentFormActivity.this, ContactActivity.class);

        /**
         *  Checks if the important fields are filled
         *  **/
        //checks if the birth of date is picked
        //check if date is selected
        if(textViewChosenDate.getText().toString().length() <= 2){
            Toast.makeText(getBaseContext(), "Pick a date of birth",Toast.LENGTH_LONG).show();
            return;
        }
        //checks if the incident type is selected
        else if (sifIncidentTypeSpinner.getSelectedItemPosition() <= 0) {
            sifFeedbackSnackbar = Snackbar.make(view,"Choose what happened to you.",Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            return;
        }
        //checks if the location of the incident is filled by the user
        if (sifIncidentLocationEt.length() == 0 ) {
            sifFeedbackSnackbar = Snackbar.make(view, "In what location did the incident happen?", Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            return;
        }
        //checks if the a proper story is told by the survivor
        if ( sifIncidentDetailsEt.length() == 0) {
            sifFeedbackSnackbar = Snackbar.make(view, "Kindly narrate the story of the incident that happened.", Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            return;
        }
        /**
         * inserts incident report in to the mysql db through a content provider
         * **/
        ContentValues values = new ContentValues();
        values.put(ReportIncidentTable.COLUMN_REPORTED_BY, reportedBy);
        values.put(ReportIncidentTable.COLUMN_SURVIVOR_DATE_OF_BIRTH, apifSurvivorDateOfBirth);
        values.put(ReportIncidentTable.COLUMN_SURVIVOR_GENDER, survivorGender);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_TYPE, incidentType);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_LOCATION, incidentLocation);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_STORY, incidentStory);
        values.put(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER, uniqueIdentifier);

        //this inserts a new report in to the mysql db
        if (reportIncidentUri == null) {
            reportIncidentUri = getContentResolver().insert(ReportIncidentContentProvider.CONTENT_URI, values);

            Toast.makeText(getBaseContext(), " The report is temporarily stored.", Toast.LENGTH_SHORT).show();

            //Broadcast receiver that checks for the network status
            IntentFilter netMainFilter =  new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

            registerReceiver(netReceiver, netMainFilter);

            //starts a the  help activity
            startActivity(provideHelpIntent);
        }
        //updates the report if its already available
        else {
            getContentResolver().update(reportIncidentUri, values, null, null);
        }
    }
    //Randomly load encouraging messages to the Text View
    public void  loadSifMessages(){
        String[] sifMessagesArray = getResources().getStringArray(R.array.seek_medical_care_messages);
        sifEncouragingMessagesTv.setText(sifMessagesArray[randMessageIndex(0, sifMessagesArray.length)].toString());
    }
    //shows encouraging messages in dialog on click of the Text View
    public void onClickSifEncouragingMessages(View view){

        EMessageDialogFragment emDialog = EMessageDialogFragment.newInstance(
                getString(R.string.not_your_fault_alert_header),
                sifEncouragingMessagesTv.getText().toString(),
                getString(R.string.close_dialog));
        emDialog.show(getSupportFragmentManager(), "encouraging message");
    }

    //shows spinner drop down for sif incident types
    public void onClickSifIVSpinner(View view){
        sifIncidentTypeSpinner.performClick();
    }
    //Generates a temperory safepal number
    public static String generateTempSafePalNumber(int minimum, int maximum){
        Random rn = new Random();
        int n = maximum - minimum + 1;
        int i = rn.nextInt() % n;
        int randomNum =  minimum + i;
        //changes the negative number to  positve
        if(randomNum<0){randomNum= -randomNum;}

        return Integer.toString(randomNum);
    }



}
