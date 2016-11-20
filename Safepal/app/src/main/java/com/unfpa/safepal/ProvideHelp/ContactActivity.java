package com.unfpa.safepal.ProvideHelp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unfpa.safepal.Location.TrackGPS;
import com.unfpa.safepal.R;
import com.unfpa.safepal.messages.EMessageDialogFragment;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.unfpa.safepal.report.WhoSGettingHelpFragment.randMessageIndex;

public class ContactActivity extends AppCompatActivity {

    private TextView contactEncouragingMessagesTv;
    private  TextView contactSafepalNo;

    /**
     * Next and buttonExit button
     */
    Button buttonFinish;
    Button buttonExit;

    private Toolbar contactToolbar;
    private LinearLayout contactPhoneEmailLl;
//    private RadioButton contactYesRB, contactNoRb;
    private EditText contactPhonenumber, contactEmail;

    CheckBox checkBoxContactMe;

    String sendLat, sendLng;

    //user location
    private TrackGPS gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //Toolbar of contact activity
        contactToolbar = (Toolbar) findViewById(R.id.contact_toolbar);
        setSupportActionBar(contactToolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

       // gps = new TrackGPS(ContactActivity.this);

        //getUserLocation();

        //assignment of UI in xml
        buttonExit = (Button) findViewById(R.id.exit_app);
        buttonFinish = (Button) findViewById(R.id.finish);

//        contactYesRB = (RadioButton)findViewById(R.id.contact_me_yes_rb);
        checkBoxContactMe = (CheckBox) findViewById(R.id.checkbox_contact_me);
//        contactNoRb = (RadioButton)findViewById(R.id.contact_me_not_rb);
        contactPhoneEmailLl = (LinearLayout)findViewById(R.id.contact_phone_email_ll);
        contactEncouragingMessagesTv = (TextView)findViewById(R.id.contact_ecouraging_messages_tv);
        contactSafepalNo = (TextView)findViewById(R.id.contact_safepal_no);

        contactPhonenumber = (EditText)findViewById(R.id.contact_phone_et);
        contactEmail = (EditText)findViewById(R.id.contact_email_et);

        loadContactFeedbackMessages();


        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               moveTaskToBack(true);
               android.os.Process.killProcess(android.os.Process.myPid());

            }
        });
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent csoIntent = new Intent(getApplicationContext(), CsoActivity.class);
                csoIntent.putExtra("safepalUniqueNumber",contactSafepalNo.getText().toString());

                if(checkBoxContactMe.isChecked()) {

                   if(contactPhonenumber.getText().length()<10 ){
                      Toast.makeText(getBaseContext(), "Provide us with a correct phone number", Toast.LENGTH_LONG).show();
                       return;
                   }

                    csoIntent.putExtra("sendUserPhonenumber", contactPhonenumber.getText().toString());

                    if(isEmailValid(contactEmail.getText().toString())){
                        csoIntent.putExtra("sendUserEmail",contactEmail.getText().toString());
                    }

                    startActivity(csoIntent);
                }


                else {
                    //user does not want to be contacted
//                    Toast.makeText(getBaseContext(), "Do you want to be contacted back? Choose???!!!", Toast.LENGTH_LONG).show();
                    startActivity(csoIntent);
                    return;
                }
            }
        });

        checkBoxContactMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){//user selcted contact me
                    //shows phone number and email
                    contactPhoneEmailLl.setVisibility(View.VISIBLE);
                }else{//user doesnt want to be contacted
                    //hides phonenumber and email on UI
                    contactPhoneEmailLl.setVisibility(View.GONE);

                }
            }
        });
    }

    /** All the  Methods **/
    //updates safepal number
    public void updateUIDTextView(){

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int k =0;
                while (k<100)  {
                    try{Thread.sleep(1000);
                    }catch (InterruptedException e ){
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Cursor cursor =  getContentResolver().query(
                                    ReportIncidentContentProvider.CONTENT_URI,
                                    null,
                                    null,
                                    null,
                                    null);

                            cursor.moveToLast();

                            ///action happens every sec
                            contactSafepalNo.setText("Your SafePal Number is : " +  cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER)));


                        }
                    });
                    k++;
                }
            }
        };

        new Thread(runnable).start();

    }
    private void  loadContactFeedbackMessages(){
        String[] wsghMessagesArray = getResources().getStringArray(R.array.seek_medical_care_messages);
        contactEncouragingMessagesTv.setText(wsghMessagesArray[randMessageIndex(0, wsghMessagesArray.length)].toString());
    }
    //expand encouraging messages
    public void onClickContactEncouragingMessages(View view){
       EMessageDialogFragment emDialog = EMessageDialogFragment.newInstance(
                getString(R.string.seek_medical_alert_head),
                contactEncouragingMessagesTv.getText().toString(),
                getString(R.string.close_dialog));
        emDialog.show(getFragmentManager(), "encouraging message");
           }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public void getUserLocation(){

         if(gps.canGetLocation()){
            double latitude =gps.getLatitude();
            double longitude = gps.getLongitude();

        }
        else
        {

            gps.showSettingsAlert();
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        gps.stopUsingGPS();
    }
}
