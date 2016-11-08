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

import com.unfpa.safepal.R;
import com.unfpa.safepal.messages.EMessageDialogFragment;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import static com.unfpa.safepal.report.WhoSGettingHelpActivity.randMessageIndex;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //Toolbar of contact activity
        contactToolbar = (Toolbar) findViewById(R.id.contact_toolbar);
        setSupportActionBar(contactToolbar);
        //adds logo and title to toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
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
        //updates user with the safepal number
        //updateUIDTextView();

        //picks and shows the mobile reporters location
         //userLocationTracker();


        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



//                moveTaskToBack(true);
//                android.os.Process.killProcess(android.os.Process.myPid());
////                System.buttonExit(1);
            }
        });
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent csoIntent = new Intent(getApplicationContext(), CsoActivity.class);
                csoIntent.putExtra("safepalUniqueNumber",contactSafepalNo.getText().toString());

                if(checkBoxContactMe.isChecked()) {

                   if(contactPhonenumber.getText().length()<5 ){
                      Toast.makeText(getBaseContext(), "Provide us with a correct phone number", Toast.LENGTH_LONG).show();
                       return;
                   }
                    startActivity(csoIntent);
                }

//                else if(!checkBoxContactMe.isChecked()){//user doent want to be contacted
//                    startActivity(csoIntent);
//                }

                else {//user doent want to be contacted
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
        emDialog.show(getSupportFragmentManager(), "encouraging message");
           }
//    public void onContactRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // checks if user wants to be contacted
//        switch(view.getId()) {
//            case R.id.contact_me_yes_rb:
//                if (checked)
//                    //shows phone number and email
//                    contactPhoneEmailLl.setVisibility(View.VISIBLE);
//                break;
//            case R.id.contact_me_not_rb:
//                if (checked)
//                    //hides phonenumber and email on UI
//                    contactPhoneEmailLl.setVisibility(View.GONE);
//                    //starts cso activity to show nearest help
//                    startActivity(new Intent(getApplicationContext(), CsoActivity.class));
//
//                break;
//        }
//    }



}
