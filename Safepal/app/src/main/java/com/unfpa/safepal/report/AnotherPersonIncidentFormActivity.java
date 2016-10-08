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
import android.widget.TextView;
import android.widget.Toast;

import com.unfpa.safepal.ProvideHelp.ContactActivity;
import com.unfpa.safepal.R;
import com.unfpa.safepal.Referral.ReferralActivity;
import com.unfpa.safepal.datepicker.DatePickerFragment;
import com.unfpa.safepal.datepicker.apifDatePickerFragment;
import com.unfpa.safepal.home.HomeActivity;
import com.unfpa.safepal.messages.messageDialog;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import static com.unfpa.safepal.report.WhoSGettingHelpActivity.randMessageIndex;

public class AnotherPersonIncidentFormActivity extends AppCompatActivity {

    /**
     * apif - Stands for "Another Person Incident Form"
     * */

    //Global Variables
    FloatingActionButton apifAbortAppFab, apifBackFab, apifSubmitFab;

    TextView apifEncouragingMessagesTv;

    //Form variables
    private Button apifDateOfBirthButton;
    private RadioGroup apifGenderRG;
    private RadioButton apifGenderRB;
    private Spinner apifIncidentTypeSpinner;
    private EditText apifIncidentLocationEt, apifIncidentDetailsEt;


    private Uri reportIncidentUri;
    final String URL_SAFEPAL_API = "http://52.43.152.73/api/addselfreport.php";
    private Snackbar apifFeedbackSnackbar;

    Intent apifRetrieveIntent;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_another_person_incident_form);

        Toolbar apifToolbar = (Toolbar) findViewById(R.id.apif_toolbar);
        //Abort fab of  who's getting help activity
        apifAbortAppFab = (FloatingActionButton) findViewById(R.id.apif_abort_app_fab);
        //Back fab of  who's getting help activity
        apifBackFab = (FloatingActionButton) findViewById(R.id.apif_back_fab);
        apifSubmitFab = (FloatingActionButton) findViewById(R.id.apif_submit_fab);
        

        apifEncouragingMessagesTv = (TextView)findViewById(R.id.apif_ecouraging_messages_tv);

       /*** Form initialization **/
        apifGenderRG=(RadioGroup)findViewById(R.id.apif_gender_rg);
        apifIncidentTypeSpinner = (Spinner) findViewById(R.id.apif_incident_type_spinner);
        apifIncidentLocationEt = (EditText)findViewById(R.id.apif_incident_location_actv);
        apifIncidentDetailsEt = (EditText)findViewById(R.id.apif_incident_details_rt);

        apifDateOfBirthButton = (Button)findViewById(R.id.sif_date_of_birth_button);

        setSupportActionBar(apifToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        apifLoadMessages();
        apifAbortAppFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

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

        apifSubmitFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apifRetrieveIntent  = new Intent(AnotherPersonIncidentFormActivity.this, ContactActivity.class);
            startActivity(apifRetrieveIntent);
            }
        });

    }



    public void showApifDatePickerDialog(View v) {
        DialogFragment newFragment = new apifDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void apifLoadMessages(){
        String[] apifMessagesArray = getResources().getStringArray(R.array.seek_medical_care_messages);
        apifEncouragingMessagesTv.setText(apifMessagesArray[randMessageIndex(0, apifMessagesArray.length)].toString());
    }

    public void onClickApifEncouragingMessages(View view){
        messageDialog apifMessageDialog = new messageDialog(apifEncouragingMessagesTv);
        apifMessageDialog.show(getSupportFragmentManager(), "messages");
    }

    public void onClickApifIVSpinner(View view){

        apifIncidentTypeSpinner.performClick();
    }

}
