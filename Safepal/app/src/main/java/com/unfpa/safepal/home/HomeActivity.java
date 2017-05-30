package com.unfpa.safepal.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.unfpa.safepal.R;
import com.unfpa.safepal.Utils.General;
import com.unfpa.safepal.messages.EMessageDialogFragment;
import com.unfpa.safepal.report.ReportingActivity;

import io.fabric.sdk.android.Fabric;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    //Global Variables 1234
    /**
     * Next and buttonExit button
     */
    FloatingActionButton fabReportCase;
    Button buttonExit;
    Button buttonNext;
    RelativeLayout infoPanel;
    TextView textViewMessage;
    AppCompatCheckBox checkBoxAutoScroll;

    //guide for safepal
    ShowcaseView homeReportGuideSv, homeExitSv, homeNextSv;
    RelativeLayout.LayoutParams lps, nextLps;

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
        buttonExit = (Button) findViewById(R.id.exit_app);
        buttonNext = (Button) findViewById(R.id.home_next_message);
        fabReportCase = (FloatingActionButton) findViewById(R.id.fab_report_incident);
        infoPanel = (RelativeLayout) findViewById(R.id.home_info_panel);
        textViewMessage = (TextView) findViewById(R.id.message);
        checkBoxAutoScroll = (AppCompatCheckBox) findViewById(R.id.auto_scroll_CheckBox);


        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 21) finishAndRemoveTask();
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

                animateNextMessage();
               // getTokenFromServer();
            }
        });

        fabReportCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 12-Nov-16 restore
                startActivity(new Intent(getApplicationContext(), ReportingActivity.class));

            }
        });

        //for internal support
        checkBoxAutoScroll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    activateAutoScrollTimer();
                    Log.d(TAG, "activated timer");
                } else {
                    Log.d(TAG, "deactivated timer");
                    deactivateAutoScrollTimer();
                }
            }
        });


        //animations about messages
        animSlideIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.enter_from_right);
        animExit = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.exit_to_left);
        animExit.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                updateMessageText();
                infoPanel.startAnimation(animSlideIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animateNextMessage();//show first message
        showDisclaimer();

    }

    private void showDisclaimer() {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean(getString(R.string.first_time), true);
        if (isFirstTime) {
            homeReportGuide();
            General.showDisclaimerDialog(this);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(getString(R.string.first_time), false);
            editor.apply();//indicate that app has ever been opened
        } else {
            //General.showDisclaimerDialog(this);
        }
    }

    Animation animSlideIn;
    Animation animExit;

    void animateNextMessage() {
        infoPanel.startAnimation(animExit);
    }

    boolean isAutoScrollOn = true;
    Thread threadScrolling;

    private void activateAutoScrollTimer() {
        isAutoScrollOn = true;
        threadScrolling = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isAutoScrollOn) {
                    try {
                        Log.d(TAG, "loop!");
                        int timeOut = getResources().getInteger(R.integer.message_timeout);
                        Log.d(TAG, "timeout: " + timeOut);
                        Thread.sleep(timeOut);//wait a bit
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animateNextMessage();//change message
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.e(TAG, "error: " + e.toString());
                    }
                }
                Log.d(TAG, "done n thread");
            }
        });
        threadScrolling.start();

    }

    private void deactivateAutoScrollTimer() {
        isAutoScrollOn = false;

    }

    String TAG = HomeActivity.class.getSimpleName();

    private void updateMessageText() {//// TODO: 13-Nov-16 dynamically set messages
        //make adapter
        ArrayAdapter<CharSequence> messages = ArrayAdapter.createFromResource(this,
                R.array.home_contact_info, R.layout.spinner_item);//// TODO: 14-Nov-16 Is this the correct Array???
        Random random = new Random();
        int min = 0;
        int max = messages.getCount();
        Log.d(TAG, "max: " + max);
        int randomIndex = random.nextInt(max - min) + min;
        Log.d(TAG, "randomIndex: " + randomIndex);
        String msg = messages.getItem(randomIndex).toString();
        Log.d(TAG, "textViewMessage: " + msg);
        textViewMessage.setText(msg);

    }


    //expand encouraging messages
    public void onClickInfoPopUp(View view) {
        EMessageDialogFragment emDialog = EMessageDialogFragment.newInstance(
                "Safepal",
                textViewMessage.getText().toString(),
                getString(R.string.close_dialog));
        emDialog.show(getFragmentManager(), "encouraging message");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_disclaimer:

                General.showDisclaimerDialog(this);
                return true;
            case R.id.menu_guide:
                homeReportGuide();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //This is a tutorial for the first  time reporters
    public void homeReportGuide() {

        //guide for the first time users
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
                        homeExitGuide();
                    }
                })
                .build();
        homeReportGuideSv.setButtonPosition(lps);
    }

    public void homeExitGuide() {


        ViewTarget eTarget = new ViewTarget(R.id.exit_app, HomeActivity.this);
        homeExitSv = new ShowcaseView.Builder(HomeActivity.this)
                .withHoloShowcase()
                .setTarget(eTarget)
                .setContentTitle(R.string.home_guide_fab_exit_title)
                .setContentText(R.string.home_guide_fab_exit_text)
                .setStyle(R.style.ExitShowcaseTheme)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        homeExitSv.hide();
                        homeNextGuide();

                    }
                })
                .build();

    }

    public void homeNextGuide() {
      //guide for the first time users
        nextLps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nextLps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 12)).intValue();
        nextLps.setMargins(margin, margin, margin, margin);

        ViewTarget nTarget = new ViewTarget(R.id.home_next_message, HomeActivity.this);

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


}