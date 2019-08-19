package com.unfpa.safepal.report;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

public class ReportingActivity extends AppCompatActivity implements SurvivorIncidentFormFragment.OnFragmentInteractionListener,
ContactFragment.OnFragmentInteractionListener, AnotherPersonIncidentFormFragment.OnFragmentInteractionListener{

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

//        Utilities.saveFormParameter(getApplicationContext(), "backButtonPressed", false);
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

        //set listerns
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d(TAG, "button next clicked" );
                if (isFragmentVisible(getFragmentManager().findFragmentByTag(
                        WhoSGettingHelpFragment.class.getSimpleName()))) {//cuurent frag WhoSGettingHelpFragment
                    Utilities.saveFormParameter(getApplicationContext(),
                            WhoSGettingHelpFragment.wsghSomeelseRb.isChecked(),
                            WhoSGettingHelpFragment.wsghRelationshipSpinner.getSelectedItem().toString());

                    if (WhoSGettingHelpFragment.wsghYesRB.isChecked()) {//happened to me
                        //Log.d(TAG, "loading reporting fragment for self");
                        loadReportingFormSelfFragment();//used in the WHoIsGettingHelp Fragment
                        updateNextButtonToSubmit();
                        buttonPrev.setVisibility(View.VISIBLE);
                    } else if (WhoSGettingHelpFragment.wsghSomeelseRb.isChecked()){//happened to someone else
                        if (WhoSGettingHelpFragment.wsghRelationshipSpinner.getSelectedItemPosition() <= 0) {
                            WhoSGettingHelpFragment.wsghFeedbackSnackbar = Snackbar.make(view, "what is your relationship to survivor?", Snackbar.LENGTH_LONG);
                            WhoSGettingHelpFragment.wsghFeedbackSnackbar.show();
                        }else {
                            Log.d(TAG, "loading reporting fragment for happeed to someone else");
                            loadReportingFormSomeOneElseFragment();
                            updateNextButtonToSubmit();
                            buttonPrev.setVisibility(View.VISIBLE);
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
                        buttonPrev.setVisibility(View.GONE);
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
                        Log.d("Code", "reached");
                        updateNetworkContat();

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
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    protected void removePreviousButton() {
        Log.d(TAG, "removePreviousButton: called");
        buttonPrev.setVisibility(View.GONE);
    }

    /**
     * Loads fragment with form for submitting details about someone else who has
     * suffered violence
     */
    public  void loadReportingFormSomeOneElseFragment() {
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
     * loads reporting for for the survivir him self
     */
    private void loadReportingFormSelfFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //SurvivorIncidentFormFragment fragment = new SurvivorIncidentFormFragment();
        SurvivorIncidentFormFragment fragment = SurvivorIncidentFormFragment
                .newInstance( "UNUSED", "UNUSED");
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


    public void updateNetworkContat(){

        String updateContactUrl = "https://api-safepal.herokuapp.com/index.php/api/v1/reports/addcontact";

        Log.d("UpdateContact ","Service Started");


        Cursor cursor =  getContentResolver().query(
                ReportIncidentContentProvider.CONTENT_URI,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToLast();

            updateContactToServer(
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER)),
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_PHONE_NUMBER)),
                    updateContactUrl);

        }
        cursor.close();


    }


    public  void updateContactToServer(final String toServerCasenumber, final String toServerContact, final String updateContactUrl){

        getUpdateTokenFromServer(new VolleyCallback() {
            @Override
            public void onSuccessResponse(String tokenResponse)  {

                try{
                    JSONObject tokenObject = new JSONObject(tokenResponse);
                    final  String  serverReceivedToken = tokenObject.getString("token");
                    // This volley request sends a report to the server with the received token
                    StringRequest updateContactRequest = new StringRequest(Request.Method.POST, updateContactUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String updateContactReponse) {
                                    Log.d("kkkkk", updateContactReponse);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("Not Submitted", error.getMessage());
                                }
                            }){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> updateContact = new HashMap<String, String>();

                            updateContact.put("token", serverReceivedToken);
                            updateContact.put("caseNumber", toServerCasenumber);
                            updateContact.put("contact",toServerContact);
                            return updateContact;                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> updateContactHeaders = new HashMap<String, String>();
                            updateContactHeaders.put("userid", "C7rPaEAN9NpPGR8e9wz9bzw");
                            return  updateContactHeaders;
                        }

                    };


                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(updateContactRequest);



                }catch (Exception e){e.printStackTrace();}
            }
        });
    }

    //gets a new token from server
    public void getUpdateTokenFromServer(final VolleyCallback tokenCallback) {


        final String tokenUrl = " https://api-safepal.herokuapp.com/index.php/api/v1/auth/newtoken";

        // This volley request gets a token from the server
        StringRequest tokenRequest = new StringRequest(Request.Method.GET, tokenUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String tokenResponse) {
                        tokenCallback.onSuccessResponse(tokenResponse);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Failed to get token", error.getMessage());
                    }
                }){
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
