package com.unfpa.safepal.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.unfpa.safepal.R;
import com.unfpa.safepal.Utils.Direction;
import com.unfpa.safepal.Utils.General;
import com.unfpa.safepal.messages.EMessageDialogFragment;
import com.unfpa.safepal.report.ReportingActivity;

import io.fabric.sdk.android.Fabric;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    Button fabReportCase;
    RelativeLayout infoPanel;
    TextView textViewMessage;
    AppCompatCheckBox checkBoxAutoScroll;

    //guide for safepal
    ShowcaseView homeReportGuideSv, homeNextSv;
    RelativeLayout.LayoutParams lps, nextLps;
    private int currentIndex = 2;
    private Direction currentDirection;

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
        infoPanel = (RelativeLayout) findViewById(R.id.home_info_panel);
        textViewMessage = (TextView) findViewById(R.id.message);
        checkBoxAutoScroll = (AppCompatCheckBox) findViewById(R.id.auto_scroll_CheckBox);


        fabReportCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        swipeRightAnimationSetUp();

        swipeLeftAnimationSetUp();

        infoPanelAnimationSetUp();

        animatePrevMessage();//show first message
        showDisclaimer();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void infoPanelAnimationSetUp() {
        infoPanel.setOnTouchListener(new View.OnTouchListener() {
            int downX, upX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = (int) event.getX();
                    Log.i("event.getX()", " downX " + downX);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    upX = (int) event.getX();
                    Log.i("event.getX()", " upX " + upX);

                    // deactivate the auto scroll when user activates the manual scroll
                    checkBoxAutoScroll.setChecked(false);
                    deactivateAutoScrollTimer();

                    if (upX - downX > 100) {
                        currentDirection = Direction.RIGHT;
                        animateNextMessage();
                    } else if (downX - upX > -100) {
                        currentDirection = Direction.LEFT;
                        animatePrevMessage();
                    }
                    return true;

                }
                return false;
            }
        });
    }

    private void swipeLeftAnimationSetUp() {
        animSlideInFromLeft = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.enter_from_left);
        animExitToRight = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.exit_to_right);
        animExitToRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                updateMessageText(currentDirection);
                if (currentDirection == Direction.RIGHT)
                    animSlideInFromLeft = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.enter_from_left);
                infoPanel.startAnimation(animSlideInFromLeft);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void swipeRightAnimationSetUp() {
        animSlideInFromRight = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.enter_from_right);
        animExitToLeft = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.exit_to_left);
        animExitToLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                updateMessageText(currentDirection);
                if (currentDirection == Direction.LEFT)
                    animSlideInFromRight = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.enter_from_right);
                infoPanel.startAnimation(animSlideInFromRight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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

    Animation animSlideInFromRight;
    Animation animSlideInFromLeft;
    Animation animExitToLeft;
    Animation animExitToRight;

    void animatePrevMessage() {
        infoPanel.startAnimation(animExitToLeft);
    }

    void animateNextMessage() {
        infoPanel.startAnimation(animExitToRight);
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
                                animatePrevMessage();//change message
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

    private void updateMessageText() {
        //make adapter
        ArrayAdapter<CharSequence> messages = ArrayAdapter.createFromResource(this,
                R.array.home_contact_info, R.layout.spinner_item);
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

    private void updateMessageText(Direction direction) {
        //make adapter
        ArrayAdapter<CharSequence> messages = ArrayAdapter.createFromResource(this,
                R.array.home_contact_info, R.layout.spinner_item);
        String msg;
        int min = 0;
        int max = messages.getCount();
        Log.d(TAG, "max: " + max);

        Log.d(TAG, "currentIndex: " + currentIndex);

        if (direction == Direction.RIGHT) {
            Log.d(TAG, "updateMessageText: direction right");
            currentIndex = currentIndex < max - 1 ? currentIndex + 1 : min;
            msg = messages.getItem(currentIndex).toString();
        } else {
            Log.d(TAG, "updateMessageText: direction left");
            currentIndex = currentIndex <= 0 ? max - 1 : currentIndex - 1;
            msg = messages.getItem(currentIndex).toString();
        }

        Log.d(TAG, "currentIndex: after processing" + currentIndex);
        Log.d(TAG, "infopanel message : " + msg);
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
                        homeSwipeGuide();

                    }
                })
                .build();
        homeReportGuideSv.setButtonPosition(lps);
    }

    public void homeSwipeGuide() {
      //guide for the first time users
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


}