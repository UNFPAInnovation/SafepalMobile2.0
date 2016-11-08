package com.unfpa.safepal.ProvideHelp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unfpa.safepal.Location.GPSTracker;
import com.unfpa.safepal.R;
import com.unfpa.safepal.messages.EMessageDialogFragment;
import com.unfpa.safepal.network.MySingleton;
import com.unfpa.safepal.network.VolleyCallback;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Intent.EXTRA_TITLE;
import static com.unfpa.safepal.R.attr.title;
import static com.unfpa.safepal.report.WhoSGettingHelpActivity.randMessageIndex;

public class ContactActivity extends AppCompatActivity {

    private TextView contactEncouragingMessagesTv;
    private  TextView contactSafepalNo;
    private FloatingActionButton contactAbortFAB, contactNextFAB;
    private Toolbar contactToolbar;
    private LinearLayout contactPhoneEmailLl;
    private RadioButton contactYesRB, contactNoRb;
    private EditText contactPhonenumberET, contactUserEmailET;


    Intent csoIntent;
    String sendUserPhoneNumber = "null";
    String sendUserEmail = "null";

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
        contactAbortFAB = (FloatingActionButton)findViewById(R.id.contact_abort_fab);
        contactNextFAB = (FloatingActionButton)findViewById(R.id.contact_next_fab);

        contactYesRB = (RadioButton)findViewById(R.id.contact_me_yes_rb);
        contactNoRb = (RadioButton)findViewById(R.id.contact_me_not_rb);
        contactPhoneEmailLl = (LinearLayout)findViewById(R.id.contact_phone_email_ll);
        contactEncouragingMessagesTv = (TextView)findViewById(R.id.contact_ecouraging_messages_tv);
        contactSafepalNo = (TextView)findViewById(R.id.contact_safepal_no);

        contactPhonenumberET = (EditText)findViewById(R.id.contact_phone_et);
        contactUserEmailET = (EditText)findViewById(R.id.contact_email_et);

        csoIntent = new Intent(getApplicationContext(), CsoActivity.class);

        loadContactFeedbackMessages();


        contactNextFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contactYesRB.isChecked()) {

                   if(contactPhonenumberET.getText().length()==10 ||contactPhonenumberET.getText().length()==13  ){

                       sendUserPhoneNumber = contactPhonenumberET.getText().toString();
                       sendUserEmail = contactUserEmailET.getText().toString();

                       csoIntent.putExtra("sendUserPhonenumber", sendUserPhoneNumber);
                       csoIntent.putExtra("sendUserEmail", sendUserEmail);
                   }else{
                       Toast.makeText(getBaseContext(), "Your phone number is incorrect", Toast.LENGTH_LONG).show();
                       return;
                   }

                    startActivity(csoIntent);
                }

                else if(contactNoRb.isChecked()){

                    startActivity(csoIntent);

                }

                else {
                    Toast.makeText(getBaseContext(), "Do you want to be contacted back? Choose???!!!", Toast.LENGTH_LONG).show();
                    return;
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
    public void onContactRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // checks if user wants to be contacted
        switch(view.getId()) {
            case R.id.contact_me_yes_rb:
                if (checked)

                //shows phone number and email
                    contactPhoneEmailLl.setVisibility(View.VISIBLE);
                break;
            case R.id.contact_me_not_rb:
                if (checked)
                    //hides phonenumber and email on UI

                    contactPhoneEmailLl.setVisibility(View.GONE);
                csoIntent.putExtra("sendUserPhonenumber", sendUserPhoneNumber);
                csoIntent.putExtra("sendUserEmail", sendUserEmail);

                //starts cso activity to show nearest help
                    startActivity(csoIntent);

                break;
        }
    }

}
