package com.unfpa.safepal.ProvideHelp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.unfpa.safepal.R;
import com.unfpa.safepal.messages.MedicalCareMessageDialog;
import com.unfpa.safepal.messages.messageDialog;

import static com.unfpa.safepal.report.WhoSGettingHelpActivity.randMessageIndex;

public class ContactActivity extends AppCompatActivity {

    private TextView contactEncouragingMessagesTv;
    private  TextView contactSafepalNo;
    private FloatingActionButton contactAbortFAB, contactNextFAB;
    private Toolbar contactToolbar;
    private LinearLayout contactPhoneEmailLl;
    private RadioButton contactYesRB, contactNoRb;
    private EditText contactPhonenumber, contactEmail;

    Context context;
    public ContactActivity(Context context){
        this.context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contactToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(contactToolbar);
        setSupportActionBar(contactToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        contactAbortFAB = (FloatingActionButton)findViewById(R.id.contact_abort_fab);
        contactNextFAB = (FloatingActionButton)findViewById(R.id.contact_next_fab);

        contactYesRB = (RadioButton)findViewById(R.id.contact_me_yes_rb);
        contactNoRb = (RadioButton)findViewById(R.id.contact_me_not_rb);
        contactPhoneEmailLl = (LinearLayout)findViewById(R.id.contact_phone_email_ll);
        contactEncouragingMessagesTv = (TextView)findViewById(R.id.contact_ecouraging_messages_tv);
        contactSafepalNo = (TextView)findViewById(R.id.contact_safepal_no);

        contactPhonenumber = (EditText)findViewById(R.id.contact_phone_et);
        contactEmail = (EditText)findViewById(R.id.contact_email_et);

        //contactSafepalNo.setText("Your SafePal Number is : "+ getIntent().getStringExtra("uniqueFromServerSent"));
        loadContactFeedbackMessages();

        contactAbortFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        contactNextFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contactYesRB.isChecked()) {

                   if(contactPhonenumber.getText().length()<5 ){
                      Toast.makeText(getBaseContext(), "Provide us with a correct phone number", Toast.LENGTH_LONG).show();
                       return;
                   }

                    startActivity(new Intent(getApplicationContext(), CsoActivity.class));
                }
                else if(contactNoRb.isChecked()){
                    startActivity(new Intent(getApplicationContext(), CsoActivity.class));
                }
                else {
                    Toast.makeText(getBaseContext(), "Do you want to be contacted back? Choose???!!!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }


    //expand encouraging messages
    public void onClickContactEncouragingMessages(View view){
        MedicalCareMessageDialog contactMessageDialog = new MedicalCareMessageDialog(contactEncouragingMessagesTv);
        contactMessageDialog.show(getSupportFragmentManager(), "messages");
    }

    private void  loadContactFeedbackMessages(){
        String[] wsghMessagesArray = getResources().getStringArray(R.array.seek_medical_care_messages);
        contactEncouragingMessagesTv.setText(wsghMessagesArray[randMessageIndex(0, wsghMessagesArray.length)].toString());
    }
    public void onContactRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.contact_me_yes_rb:
                if (checked)
                    // Pirates are the best
                    contactPhoneEmailLl.setVisibility(View.VISIBLE);
                break;
            case R.id.contact_me_not_rb:
                if (checked)
                    contactPhoneEmailLl.setVisibility(View.GONE);
                break;
        }
    }
}
