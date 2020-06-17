package com.unfpa.safepal.report;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unfpa.safepal.ProvideHelp.ContactFragment;
import com.unfpa.safepal.ProvideHelp.CsoActivity;
import com.unfpa.safepal.R;
import com.unfpa.safepal.Utils.Utilities;
import com.unfpa.safepal.network.MySingleton;
import com.unfpa.safepal.network.VolleyCallback;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.unfpa.safepal.Utils.Constants.BASE_API_URL;

public class ReportingActivity extends AppCompatActivity implements SurvivorIncidentFormFragment.OnFragmentInteractionListener,
        ContactFragment.OnFragmentInteractionListener, AnotherPersonIncidentFormFragment.OnFragmentInteractionListener {

    String TAG = ReportingActivity.class.getSimpleName();
    Button buttonNext;
    Button buttonPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.reporting_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Utilities.saveBackButtonPressed(getApplicationContext(), "backButtonPressed", false);

        manageUI();
        removePreviousButton();
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
        buttonPrev = (Button) findViewById(R.id.exit_app);
        buttonNext = (Button) findViewById(R.id.finish);

        buttonNext.setOnClickListener(view -> {
            Log.d(TAG, "button next clicked");
            if (isFragmentVisible(getFragmentManager().findFragmentByTag(
                    WhoSGettingHelpFragment.class.getSimpleName()))) {
                Utilities.saveFormParameter(getApplicationContext(),
                        WhoSGettingHelpFragment.wsghSomeelseRb.isChecked(),
                        WhoSGettingHelpFragment.wsghRelationshipSpinner.getSelectedItem().toString());

                if (WhoSGettingHelpFragment.wsghYesRB.isChecked()) {
                    //Log.d(TAG, "loading reporting fragment for self");
                    loadReportingFormSelfFragment();
                    updateNextButtonToSubmit();
                    buttonPrev.setVisibility(View.VISIBLE);
                } else if (WhoSGettingHelpFragment.wsghSomeelseRb.isChecked()) {
                    if (WhoSGettingHelpFragment.wsghRelationshipSpinner.getSelectedItemPosition() <= 0) {
                        WhoSGettingHelpFragment.wsghFeedbackSnackbar = Snackbar.make(view, "what is your relationship to survivor?", Snackbar.LENGTH_LONG);
                        WhoSGettingHelpFragment.wsghFeedbackSnackbar.show();
                    } else {
                        Log.d(TAG, "loading reporting fragment for happeed to someone else");
                        loadReportingFormSomeOneElseFragment();
                        updateNextButtonToSubmit();
                        buttonPrev.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Who did the incident happen to? Choose one of the options to proceed.", Toast.LENGTH_LONG).show();
                }

            } else if (isFragmentVisible(getFragmentManager().findFragmentByTag(
                    AnotherPersonIncidentFormFragment.class.getSimpleName()))) {
                int status = AnotherPersonIncidentFormFragment.submitForm(getBaseContext());

                if ((status == ReportingActivity.STATUS_SUBMIT_REPORT_SUBMITED) || (status == ReportingActivity.STATUS_SUBMIT_REPORT_ALREADY_AVAILABLE)) {
                    Log.d(TAG, "AnotherPersonIncidentFormFragment.submitForm successfull. Loading contact frag");
                    Intent csoIntent = new Intent(getBaseContext(), CsoActivity.class);
                    startActivity(csoIntent);
                    buttonPrev.setVisibility(View.GONE);
                    finish();
                }
            } else if (isFragmentVisible(getFragmentManager().findFragmentByTag(
                    SurvivorIncidentFormFragment.class.getSimpleName()))) {
                Log.d(TAG, "submitting self-form");

                int status = SurvivorIncidentFormFragment.submitForm(getBaseContext());
                if ((status == ReportingActivity.STATUS_SUBMIT_REPORT_SUBMITED) || (status == ReportingActivity.STATUS_SUBMIT_REPORT_ALREADY_AVAILABLE)) {
                    Intent csoIntent = new Intent(getBaseContext(), CsoActivity.class);
                    startActivity(csoIntent);
                    finish();
                    Log.d(TAG, "SurvivorIncidentFormFragment.submitForm successfull. Loading contact frag");
                }
            } else if (isFragmentVisible(getFragmentManager().findFragmentByTag(
                    ContactFragment.class.getSimpleName()))) {

                if (ContactFragment.areFieldsSet(getBaseContext())) {//if all foed are set
                    Log.d("Code", "reached");
                    Intent csoIntent = new Intent(getBaseContext(), CsoActivity.class);
                    csoIntent.putExtra("safepalUniqueNumber", ContactFragment.contactSafepalNo.getText().toString());
                    startActivity(csoIntent);
                    finish();
                }
            }
        });

        //exit the  application on click of exit
        buttonPrev.setOnClickListener(view -> {
            Log.d(TAG, "onClick: prevbutton clicked");
            Utilities.saveBackButtonPressed(getApplicationContext(), "backButtonPressed", true);

            if (isFragmentVisible(getFragmentManager().findFragmentByTag(
                    SurvivorIncidentFormFragment.class.getSimpleName())) ||
                    isFragmentVisible(getFragmentManager().findFragmentByTag(
                            AnotherPersonIncidentFormFragment.class.getSimpleName()))) {

                boolean isSomeOneElse = Utilities.getFormParameter(getApplicationContext(), "isSomeOneElse");
                Log.d(TAG, "onClick: isSomeOne shared pref " + isSomeOneElse);

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
                    updateSubmitButtonToNext();
                    buttonPrev.setVisibility(View.GONE);
                }

                Log.d(TAG, "onClick: set is someone else checked");
                WhoSGettingHelpFragment.wsghYesRB.setChecked(true);
            }
        });
    }

    /**
     * changes text from 'NEXT' to 'SUBMIT'
     * while on the last fragment of reporting an incident
     */
    protected void updateNextButtonToSubmit() {
        buttonNext.setText(getString(R.string.submit));
    }

    /**
     * changes text from 'SUBMIT' to 'NEXT'
     */
    protected void updateSubmitButtonToNext() {
        buttonNext.setText(getString(R.string.next));
    }

    protected void removePreviousButton() {
        Log.d(TAG, "removePreviousButton: called");
        buttonPrev.setVisibility(View.GONE);
    }

    /**
     * Loads fragment with form for submitting details about someone else who has
     * suffered violence
     */
    public void loadReportingFormSomeOneElseFragment() {
        Log.d(TAG, "loadReportingFormSomeOneElseFragment: relationship value " + WhoSGettingHelpFragment.wsghRelationshipSpinner.getSelectedItem().toString());
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
     * loads reporting for the survivor
     */
    private void loadReportingFormSelfFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SurvivorIncidentFormFragment fragment = SurvivorIncidentFormFragment
                .newInstance("UNUSED", "UNUSED");
        if (isFragmentVisible(fragment)) {
            Log.d(TAG, "SurvivorIncidentFormFragment is already visible, not reforming another...");
        } else {
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
            fragmentTransaction.replace(R.id.fragment_container, fragment, SurvivorIncidentFormFragment.class.getSimpleName());
            fragmentTransaction.commit();
            Log.d(TAG, "loaded 'SurvivorIncidentFormFragment' fragment!");
        }
    }

    /**
     * loads fragment for choosing who survived the incodent.
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
     * loads reporting for the survivor him self
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

    public void updateContactToServer(final String toServerCasenumber, final String toServerContact, final String updateContactUrl) {
        getUpdateTokenFromServer(tokenResponse -> {

            try {
                JSONObject tokenObject = new JSONObject(tokenResponse);
                final String serverReceivedToken = tokenObject.getString("token");
                // This volley request sends a report to the server with the received token
                StringRequest updateContactRequest = new StringRequest(Request.Method.POST, updateContactUrl,
                        updateContactReponse -> Log.d("kkkkk", updateContactReponse),
                        error -> Log.d("Not Submitted", error.getMessage())) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> updateContact = new HashMap<String, String>();

                        updateContact.put("token", serverReceivedToken);
                        updateContact.put("caseNumber", toServerCasenumber);
                        updateContact.put("contact", toServerContact);
                        return updateContact;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> updateContactHeaders = new HashMap<String, String>();
                        updateContactHeaders.put("userid", "C7rPaEAN9NpPGR8e9wz9bzw");
                        return updateContactHeaders;
                    }

                };
                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(updateContactRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //gets a new token from server
    public void getUpdateTokenFromServer(final VolleyCallback tokenCallback) {
        final String tokenUrl = BASE_API_URL + "/auth/newtoken";

        // This volley request gets a token from the server
        StringRequest tokenRequest = new StringRequest(Request.Method.GET, tokenUrl,
                tokenResponse -> tokenCallback.onSuccessResponse(tokenResponse),
                error -> Log.d("Failed to get token", error.getMessage())) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("userid", "C7rPaEAN9NpPGR8e9wz9bzw");

                return headers;
            }
        };
        //add request to queue
        MySingleton.getInstance(this).addToRequestQueue(tokenRequest);
    }
}
