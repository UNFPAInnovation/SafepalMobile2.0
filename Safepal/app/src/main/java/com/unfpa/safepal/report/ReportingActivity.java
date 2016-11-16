package com.unfpa.safepal.report;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.unfpa.safepal.ProvideHelp.ContactFragment;
import com.unfpa.safepal.ProvideHelp.CsoActivity;
import com.unfpa.safepal.R;

public class ReportingActivity extends AppCompatActivity implements SurvivorIncidentFormFragment.OnFragmentInteractionListener {

    String TAG = ReportingActivity.class.getSimpleName();
    /**
     * Next and buttonExit button
     */
    Button buttonNext;
    Button buttonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.reporting_toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        manageUI();

        loadWhoGetnHelpFragment();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public static final int STATUS_SUBMIT_REPORT_SUBMITED = 0;
    public static final int STATUS_SUBMIT_REPORT_ERROR = 1;
    public static final int STATUS_SUBMIT_REPORT_ALREADY_AVAILABLE = 2;
    private void manageUI() {
        //look for vies
        //Abort fab of  who's getting help activity
        buttonExit = (Button) findViewById(R.id.exit_app);
        //Next fab of  who's getting help activity
        buttonNext = (Button) findViewById(R.id.finish);

        //set listerns
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d(TAG, "button next clicked" );
                if (isFragmentVisible(getFragmentManager().findFragmentByTag(
                        WhoSGettingHelpFragment.class.getSimpleName()))) {//cuurent frag WhoSGettingHelpFragment
                    if (WhoSGettingHelpFragment.wsghYesRB.isChecked()) {//happened to me
                        Log.d(TAG, "loading reporting fragment for self");
                        loadReportingFormSelfFragment();
                        updateNextButtonToSubmit();
                    } else if (WhoSGettingHelpFragment.wsghSomeelseRb.isChecked()){//happened to someone else
                        if (WhoSGettingHelpFragment.wsghRelationshipSpinner.getSelectedItemPosition() <= 0) {
                            WhoSGettingHelpFragment.wsghFeedbackSnackbar = Snackbar.make(view, "what is your relationship to survivor?", Snackbar.LENGTH_LONG);
                            WhoSGettingHelpFragment.wsghFeedbackSnackbar.show();
                        }else {
                            Log.d(TAG, "loading reporting fragment for happeed to someone else");
                            loadReportingFormSomeOneElseFragment();
                            updateNextButtonToSubmit();
                        }
                    }else {
                        Toast.makeText(getBaseContext(), "Who did the incident happen to? Choose one of the options to proceed.", Toast.LENGTH_LONG).show();
                    }

                } else if (isFragmentVisible(getFragmentManager().findFragmentByTag(
                        AnotherPersonIncidentFormFragment.class.getSimpleName()))) {//cuurent frag AnotherPersonIncidentFormFragment
                    Log.d(TAG, "submitting another-person form");
                    int status = AnotherPersonIncidentFormFragment.submitForm(getBaseContext());//submit the form
                    if((status == ReportingActivity.STATUS_SUBMIT_REPORT_SUBMITED) || (status == ReportingActivity.STATUS_SUBMIT_REPORT_ALREADY_AVAILABLE)){
                        Log.d(TAG, "AnotherPersonIncidentFormFragment.submitForm successfull. Loading contact frag");
                        loadContactFragment();//ask whther to be contacted in next frag
                        updateSubmitButtonToNext();
                    }else{
                        Log.d(TAG, "error in data????");
                    }

                } else if (isFragmentVisible(getFragmentManager().findFragmentByTag(
                        SurvivorIncidentFormFragment.class.getSimpleName()))) {//cuurent frag SurvivorIncidentFormFragment
                    Log.d(TAG, "submitting self-form");
                    int status = SurvivorIncidentFormFragment.submitForm(getBaseContext());//submit the form
                    if((status == ReportingActivity.STATUS_SUBMIT_REPORT_SUBMITED) || (status == ReportingActivity.STATUS_SUBMIT_REPORT_ALREADY_AVAILABLE)){
                        loadContactFragment();//ask whther to be contacted in next frag
                        updateSubmitButtonToNext();
                        Log.d(TAG, "SurvivorIncidentFormFragment.submitForm successfull. Loading contact frag");
                    }else {
                        Log.d(TAG, "errpr on data????");
                    }

                }else if (isFragmentVisible(getFragmentManager().findFragmentByTag(
                        ContactFragment.class.getSimpleName()))) {//cuurent frag ContactFragment

                    if(ContactFragment.areFieldsSet(getBaseContext())){//if all foed are set
                        Intent csoIntent = new Intent(getBaseContext(), CsoActivity.class);
                        csoIntent.putExtra("safepalUniqueNumber",ContactFragment.contactSafepalNo.getText().toString());
                        startActivity(csoIntent);
                        finish();//close this activity after opening another.
                    }else{
                        Log.w(TAG, "some fields empty");
                    }

                }else {
                    Log.e(TAG, "Dont know what to do!!!!");
                }
            }
        });

        //exit the  application on click of exit
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(2);
            }
        });

        //unistall application on long press of exit
        buttonExit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Uri packageURI = Uri.parse("package:com.unfpa.safepal");
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);

                return true;
            }
        });

    }

    /**
     * changes text fron 'NEXT' to 'SUBMIT'
     * while on the last frgamnet of reporting an incident
     */
    protected void updateNextButtonToSubmit() {
        buttonNext.setText(getString(R.string.submit));
    }

    /**
     * changes text fron 'SUBMIT' to 'NEXT'
     */
    protected void updateSubmitButtonToNext() {
        buttonNext.setText(getString(R.string.next));
    }

    /**
     * Loads fragment with form for submitting details about someone else who has
     * suffered violence
     */
    public  void loadReportingFormSomeOneElseFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AnotherPersonIncidentFormFragment fragment = AnotherPersonIncidentFormFragment
                .newInstance(WhoSGettingHelpFragment.wsghRelationshipSpinner.getSelectedItem().toString(), "UNUSED");
        if (isFragmentVisible(fragment)) {
            Log.d(TAG, "AnotherPersonIncidentFormFragment is already visible, not reforming another...");
        } else {
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
            fragmentTransaction.replace(R.id.fragment_container, fragment, AnotherPersonIncidentFormFragment.class.getSimpleName());
            fragmentTransaction.commit();
            Log.d(TAG, "loaded 'AnotherPersonIncidentFormFragment' fragment");
        }
    }

    /**
     * loads reporting for for the survivir him self
     */
    private void loadReportingFormSelfFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SurvivorIncidentFormFragment fragment = new SurvivorIncidentFormFragment();
        if (isFragmentVisible(fragment)) {
            Log.d(TAG, "SurvivorIncidentFormFragment is already visible, not reforming another...");
        } else {
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
            fragmentTransaction.replace(R.id.fragment_container, fragment, SurvivorIncidentFormFragment.class.getSimpleName());
            fragmentTransaction.commit();
            Log.d(TAG, "loaded 'SurvivorIncidentFormFragment' fragment");
        }
    }

    /**
     * loads fragment for choosing who survived the incodent.
     * The survir gim self or someone else
     */
    private void loadWhoGetnHelpFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        WhoSGettingHelpFragment fragment = new WhoSGettingHelpFragment();
        if (isFragmentVisible(fragment)) {
            Log.d(TAG, "WhoSGettingHelpFragment is already visible, not reforming another...");
        } else {
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
            fragmentTransaction.replace(R.id.fragment_container, fragment, WhoSGettingHelpFragment.class.getSimpleName());
            fragmentTransaction.commit();
            Log.d(TAG, "loaded 'Who-is-getting-help' fragment");
        }


    }

    /**
     * loads reporting for for the survivir him self
     */
    private void loadContactFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ContactFragment fragment = new ContactFragment();
        if (isFragmentVisible(fragment)) {
            Log.d(TAG, "ContactFragment is already visible, not reforming another...");
        } else {
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
            fragmentTransaction.replace(R.id.fragment_container, fragment, ContactFragment.class.getSimpleName());
            fragmentTransaction.commit();
            Log.d(TAG, "loaded 'ContactFragment' fragment");
        }
    }

    private boolean isFragmentVisible(Fragment fragment) {
        if ((fragment != null) &&
                fragment.isVisible()) {
            return true;
        } else return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
