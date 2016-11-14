package com.unfpa.safepal.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unfpa.safepal.R;

import com.unfpa.safepal.report.ReportingActivity;
import com.unfpa.safepal.report.WhoSGettingHelpActivity;

public class HomeActivity extends AppCompatActivity {

        //Global Variables 1234
    /**
     * Next and buttonExit button
     */
    FloatingActionButton fabReportCase;
    Button buttonExit;
    Button buttonNext;
    RelativeLayout infoPanel;
    TextView message;

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
        buttonNext = (Button) findViewById(R.id.next_message);
        fabReportCase = (FloatingActionButton) findViewById(R.id.fab);
        infoPanel = (RelativeLayout)findViewById(R.id.info_panel);
        message = (TextView) findViewById(R.id.message);


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

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation animSlideIn = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.enter_from_right);
                Animation animExit = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.exit_to_left);

                animExit.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        setNewMessage();
                        infoPanel.startAnimation(animSlideIn);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                infoPanel.startAnimation(animExit);

            }
        });

        fabReportCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), WhoSGettingHelpActivity.class)); // TODO: 12-Nov-16 restore
                startActivity(new Intent(getApplicationContext(), ReportingActivity.class));

            }
        });




    }

    private void setNewMessage() {//// TODO: 13-Nov-16 dynamically set messages
       // message.set
    }
    //homeInfoTGL = new RotateLayout(this);




}
