package com.unfpa.safepal.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.unfpa.safepal.R;
import com.unfpa.safepal.Utils.Direction;
import com.unfpa.safepal.report.ReportingActivity;

import io.fabric.sdk.android.Fabric;

public class HomeActivity extends AppCompatActivity {
    Button fabReportCase;
    RelativeLayout infoPanel;
    TextView textViewMessage;

    //guide for safepal
    ShowcaseView homeReportGuideSv, homeNextSv;
    RelativeLayout.LayoutParams lps, nextLps;
    private final String TAG = HomeActivity.class.getSimpleName();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        // Assignments of variables
        fabReportCase = (Button) findViewById(R.id.fab_report_incident);
        textViewMessage = (TextView) findViewById(R.id.message);

        fabReportCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ReportingActivity.class));

            }
        });

        showLocationSettingsDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_guide:
                reportTutorialGuide();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void reportTutorialGuide() {
        lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 12)).intValue();
        lps.setMargins(margin, margin, margin, margin);

        ViewTarget target = new ViewTarget(R.id.fab_report_incident, this);
        homeReportGuideSv = new ShowcaseView.Builder(this)
                .withHoloShowcase()
                .setTarget(target)
                .setContentTitle(R.string.home_guide_fab_report_title)
                .setContentText(R.string.home_guide_fab_report_text)
                .setStyle(R.style.ReportShowcaseTheme)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        homeReportGuideSv.hide();
//                        cardMessagesTutorialGuide();
                    }
                })
                .build();
        homeReportGuideSv.setButtonPosition(lps);
    }

    public void cardMessagesTutorialGuide() {
        nextLps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nextLps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 12)).intValue();
        nextLps.setMargins(margin, margin + 30, margin, margin);

        ViewTarget nTarget = new ViewTarget(R.id.home_info_panel, HomeActivity.this);

        homeNextSv = new ShowcaseView.Builder(HomeActivity.this)
                .withHoloShowcase()
                .setTarget(nTarget)
                .setContentTitle(R.string.home_guide_fab_next_title)
                .setContentText(R.string.home_guide_fab_next_text)
                .setStyle(R.style.NextShowcaseTheme)
                .build();

        homeNextSv.setButtonPosition(nextLps);
        homeNextSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeNextSv.hide();
            }
        });
    }

    private void showLocationSettingsDialog() {
        LocationManager locationManager;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.d(TAG, "showLocationSettingsDialog: GPS enabled " + isGPSEnabled);

        if (!isGPSEnabled) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Needs Location");
            builder.setMessage("Please turn on your GPS to properly report the case");

            builder.setPositiveButton("TURN ON GPS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    openLocationSettings();
                }
            });

            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }

    private void openLocationSettings() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }
}