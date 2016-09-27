package com.unfpa.safepal.Referral;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.unfpa.safepal.R;

public class ReferralActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);
        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_referral);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if atleast a fied has been entered
                String phoneNumber = editTextPhone.getText().toString();
                String email = editTextEmail.getText().toString();
                if ((phoneNumber.length() >= 1 && phoneNumber.length() < 10)) {//check if phone number is valid
                    Snackbar.make(findViewById(R.id.coordinator), "Your Phone number is invalid, please correct it.", Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                if (email.length() >= 1 && !Util.isEmailValid(email)) {//check if email is valid
                    Snackbar.make(findViewById(R.id.coordinator), "Your Email is invalid, please correct it.", Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                if ((phoneNumber.length() >= 10) || (Util.isEmailValid(email))) {//at least one valid entry made
                    startActivity(new Intent(getBaseContext(), ReferralActivity2.class));
                } else {//no valid entry made
                    Snackbar.make(findViewById(R.id.coordinator), "Please Fill at least one field above", Snackbar.LENGTH_LONG)
                            .show();
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
