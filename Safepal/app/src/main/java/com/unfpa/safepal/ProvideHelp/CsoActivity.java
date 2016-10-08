package com.unfpa.safepal.ProvideHelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.unfpa.safepal.R;
import com.unfpa.safepal.home.HomeActivity;

public class CsoActivity extends AppCompatActivity {
    Toolbar csoToolbar;
    FloatingActionButton contactAbortAppFab, contactBackFab, contactFinishFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cso);

        csoToolbar = (Toolbar) findViewById(R.id.cso_toolbar);
        setSupportActionBar(csoToolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Abort fab of  who's getting help activity
        contactAbortAppFab = (FloatingActionButton) findViewById(R.id.cso_abort_app_fab);
        //Back fab of  who's getting help activity
        contactBackFab = (FloatingActionButton) findViewById(R.id.cso_back_fab);
        //Next fab of  who's getting help activity
        contactFinishFab = (FloatingActionButton) findViewById(R.id.cso_finish_fab);
        // choose someone else relationship spinner

        contactAbortAppFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        contactBackFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   startActivity(new Intent(getApplicationContext(), ContactActivity.class));
            }
        });
        contactFinishFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });


    }

}
