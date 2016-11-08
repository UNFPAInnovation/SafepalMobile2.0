package com.unfpa.safepal.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.unfpa.safepal.R;

import com.unfpa.safepal.report.WhoSGettingHelpActivity;

public class HomeActivity extends AppCompatActivity {

        //Global Variables 1234
    /**
     * Next and buttonExit button
     */
    Button fabReportCase;
    Button buttonExit;

        RotateLayout homeInfoTGL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        // Assignments of variables
        buttonExit = (Button) findViewById(R.id.exit_app);
        fabReportCase = (Button) findViewById(R.id.next);


        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT>=21)  finishAndRemoveTask();
                else finish();
            }
        });
        buttonExit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Uri packageURI = Uri.parse("package:com.unfpa.safepal");
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);

                return true;
            }
        });

        fabReportCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WhoSGettingHelpActivity.class));

            }
        });
//        homeInfoTGL = new RotateLayout(this);
    }
        //homeInfoTGL = new RotateLayout(this);



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_home_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
