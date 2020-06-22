package com.unfpa.safepal.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.crashlytics.android.Crashlytics;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.pixplicity.easyprefs.library.Prefs;
import com.unfpa.safepal.DiscoveryActivity;
import com.unfpa.safepal.FAQActivity;
import com.unfpa.safepal.R;
import com.unfpa.safepal.chatmodule.ChatActivity;
import com.unfpa.safepal.network.SetupIntentService;
import com.unfpa.safepal.report.ReportingActivity;

import io.fabric.sdk.android.Fabric;

import static com.unfpa.safepal.Utils.Constants.ANONYMOUS_USER_NAME;
import static com.unfpa.safepal.Utils.Constants.FIRST_LAUNCH;
import static com.unfpa.safepal.Utils.Constants.MAC_ADDRESS;
import static com.unfpa.safepal.Utils.Utilities.getMacAddress;
import static com.unfpa.safepal.Utils.Utilities.getRandomString;

public class HomeActivity extends AppCompatActivity {
    Button reportCaseButton;
    CardView discoverMoreCard;

    //guide for safepal
    ShowcaseView homeReportGuideSv, cardShowcaseView, chatShowcaseView, faqShowcaseView;
    RelativeLayout.LayoutParams lps, nextLps, chatLps, faqLps;
    private final String TAG = HomeActivity.class.getSimpleName();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        // Assignments of variables
        reportCaseButton = findViewById(R.id.report_incident_button);
        discoverMoreCard = findViewById(R.id.discover_more_card);

        startService(new Intent(this, SetupIntentService.class));

        reportCaseButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ReportingActivity.class)));
        discoverMoreCard.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), DiscoveryActivity.class)));

        SharedPreferences prefs = getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean(getString(R.string.first_time), true);
        if (isFirstTime) {
            reportTutorialGuide();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(getString(R.string.first_time), false);
            editor.apply();
        } else {
            showLocationSettingsDialog();
        }

        if (Prefs.getBoolean(FIRST_LAUNCH, true)) {
            Prefs.putString(MAC_ADDRESS, getMacAddress(this));
            Prefs.putString(ANONYMOUS_USER_NAME, "User" + getRandomString());
        }
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
            case R.id.menu_chat:
                startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                return true;
            case R.id.menu_faq:
                startActivity(new Intent(getApplicationContext(), FAQActivity.class));
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

        ViewTarget target = new ViewTarget(R.id.report_incident_button, this);
        homeReportGuideSv = new ShowcaseView.Builder(this)
                .withHoloShowcase()
                .setTarget(target)
                .setContentTitle(R.string.home_guide_fab_report_title)
                .setContentText(R.string.home_guide_fab_report_text)
                .setStyle(R.style.ReportShowcaseTheme)
                .setOnClickListener(v -> {
                    homeReportGuideSv.hide();
                    cardMessagesTutorialGuide();
                })
                .build();
        homeReportGuideSv.setButtonPosition(lps);
    }

    public void cardMessagesTutorialGuide() {
        nextLps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nextLps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 12)).intValue();
        nextLps.setMargins(margin, margin + 30, margin, margin);

        ViewTarget nTarget = new ViewTarget(R.id.discover_more_card, HomeActivity.this);

        cardShowcaseView = new ShowcaseView.Builder(HomeActivity.this)
                .withHoloShowcase()
                .setTarget(nTarget)
                .setContentTitle("Discover more")
                .setContentText("Get daily information about Gender Based Violence, Malaria, HIV prevention through videos, articles and quizzes")
                .setStyle(R.style.ExitShowcaseTheme)
                .setOnClickListener(v -> {
                    cardShowcaseView.hide();
                    chatTutorialGuide();
                })
                .build();
        cardShowcaseView.setButtonPosition(nextLps);
    }

    public void chatTutorialGuide() {
        chatLps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        chatLps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        chatLps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 5)).intValue();
        chatLps.setMargins(margin, margin, margin, margin);

        ViewTarget nTarget = new ViewTarget(R.id.menu_chat, HomeActivity.this);

        chatShowcaseView = new ShowcaseView.Builder(HomeActivity.this)
                .withHoloShowcase()
                .setTarget(nTarget)
                .setContentTitle("Chat discussion")
                .setContentText("Directly reach out to people who can help you when in need")
                .setStyle(R.style.ReportShowcaseTheme)
                .setOnClickListener(v -> {
                    chatShowcaseView.hide();
                    FAQTutorialGuide();
                })
                .build();
        chatShowcaseView.setButtonPosition(chatLps);
    }

    public void FAQTutorialGuide() {
        faqLps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        faqLps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        faqLps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 5)).intValue();
        faqLps.setMargins(margin, margin, margin, margin);

        ViewTarget nTarget = new ViewTarget(R.id.menu_faq, HomeActivity.this);

        faqShowcaseView = new ShowcaseView.Builder(HomeActivity.this)
                .withHoloShowcase()
                .setTarget(nTarget)
                .setContentTitle("Frequently Asked Questions")
                .setContentText("Find answers to the questions that you seek")
                .setStyle(R.style.NextShowcaseTheme)
                .setOnClickListener((View.OnClickListener) v ->  {
                    faqShowcaseView.hide();
                    // finally show the location request dialog for first time launch user
                    showLocationSettingsDialog();
                })
                .build();
        faqShowcaseView.setButtonPosition(faqLps);
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

            builder.setPositiveButton("TURN ON GPS", (dialog, which) -> {
                dialog.cancel();
                openLocationSettings();
            });

            builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel());
            builder.show();
        }
    }

    private void openLocationSettings() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }
}