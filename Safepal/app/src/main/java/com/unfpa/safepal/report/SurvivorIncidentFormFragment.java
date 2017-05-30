package com.unfpa.safepal.report;

import android.Manifest;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.unfpa.safepal.R;
import com.unfpa.safepal.datepicker.DatePickerFragment;
import com.unfpa.safepal.messages.EMessageDialogFragment;
import com.unfpa.safepal.network.NetworkChangeReceiver;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import java.util.Random;

import static com.unfpa.safepal.report.WhoSGettingHelpFragment.randMessageIndex;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link SurvivorIncidentFormFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SurvivorIncidentFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurvivorIncidentFormFragment extends Fragment {

   private static String userLongitude="0.2123232";
    private static  String userLatitude="32.123233";

    /**
     * sif - Stands for "Survivor Incident Form"
     * */

    /**
     * Global Variables **/

    /*User Interface*/
    Toolbar sifToolbar;

    //Encouraging messages
    TextView sifEncouragingMessagesTv;

    //Form variables
    private Button sifDateOfBirthButton;
    static TextView textViewChosenDate;
    private TextView sifQtnAgeTextView;
    private static RadioGroup sifGenderRG;
    private static RadioButton sifGenderRB;
    private static Spinner sifIncidentTypeSpinner;
    private static AutoCompleteTextView sifIncidentLocationEt;
    private static EditText sifIncidentDetailsEt;
    private static Snackbar sifFeedbackSnackbar;

    //Data Layer

    //content provider
    Bundle extras;
    private static Uri sifReportIncidentUri;
    //network changes broadcast receiver
    private static NetworkChangeReceiver sifNetReceiver = new NetworkChangeReceiver();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SurvivorIncidentFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SurvivorIncidentFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SurvivorIncidentFormFragment newInstance(String param1, String param2) {
        SurvivorIncidentFormFragment fragment = new SurvivorIncidentFormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // Volley Request
        // check from the saved Instance
        sifReportIncidentUri = (bundle == null) ? null : (Uri) bundle
                .getParcelable(ReportIncidentContentProvider.CONTENT_ITEM_TYPE);
    }

    static View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_survivor_incident_form, container, false);

        sifToolbar = (Toolbar) rootView.findViewById(R.id.reporting_toolbar);
        //Abort fab of  sif activity


        //location

        LocationManager sifLm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener ll = new userLocationListener();
        /* end location */
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        sifLm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);


        sifDateOfBirthButton = (Button)rootView.findViewById(R.id.date_of_birth_button);

        textViewChosenDate = (TextView)rootView.findViewById(R.id.sif_chosen_date);
        sifQtnAgeTextView= (TextView)rootView.findViewById(R.id.sif_qtn_age_tv);


        sifGenderRG=(RadioGroup)rootView.findViewById(R.id.gender_rg);
        sifIncidentTypeSpinner = (Spinner) rootView.findViewById(R.id.incident_type_spinner);
        sifIncidentLocationEt = (AutoCompleteTextView) rootView.findViewById(R.id.incident_location_actv);
        sifIncidentDetailsEt = (EditText)rootView.findViewById(R.id.incident_details_rt);

        sifEncouragingMessagesTv= (TextView) rootView.findViewById(R.id.sif_encouraging_messages_tv);

        textInputLayoutStory = (TextInputLayout)rootView.findViewById(R.id.input_latout_story);
        textInputLayoutWhereHappened = (TextInputLayout)rootView.findViewById(R.id.inpu_latout_where);


        //content provider
        extras = getActivity().getIntent().getExtras();

        //messages to user
        loadSifMessages();

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> sifIncidentTypeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sif_incident_type_above, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        sifIncidentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the sifIncidentTypeAdapter to the spinner
        sifIncidentTypeSpinner.setAdapter(sifIncidentTypeAdapter);

        sifDateOfBirthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.DialogFragment dateFragment = new DatePickerFragment();


                dateFragment.show(getFragmentManager(), "datePicker");
                sifDateOfBirthButton.setText("Change Date");
                sifQtnAgeTextView.setText("You were born  ");
            }
        });








        sifEncouragingMessagesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EMessageDialogFragment emDialog = EMessageDialogFragment.newInstance(
                        getString(R.string.not_your_fault_alert_header),
                        sifEncouragingMessagesTv.getText().toString(),
                        getString(R.string.close_dialog));
                emDialog.show(getFragmentManager(), "encouraging message");
            }
        });
        
        

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(sifNetReceiver);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "could not unregister receiver");
        }
    }
    

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    static TextInputLayout textInputLayoutWhereHappened;
    static TextInputLayout textInputLayoutStory;

    public static int submitForm(Context context) {
        int genderRBId = sifGenderRG.getCheckedRadioButtonId();

        //checks if gender radio group isn't selected;
        if(genderRBId==-1){
            //feedback to developer
            sifFeedbackSnackbar = Snackbar.make(rootView ,"Tell us your gender please!!!",Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            return ReportingActivity.STATUS_SUBMIT_REPORT_ERROR;
        }
        View genderRBView = sifGenderRG.findViewById(genderRBId);
        int idx = sifGenderRG.indexOfChild(genderRBView);
        sifGenderRB = (RadioButton) sifGenderRG.getChildAt(idx);
        //end check of radio group

        String reportedBy = "survivor";
//        String survivorDateOfBirth = sifDateOfBirthButton.getText().toString();;
        String apifSurvivorDateOfBirth = textViewChosenDate.getText().toString();
        Log.d("age", apifSurvivorDateOfBirth);
        String survivorGender = (String)sifGenderRB.getText();
        String incidentType =(String)sifIncidentTypeSpinner.getSelectedItem();
        String incidentLocation = sifIncidentLocationEt.getText().toString();
        String incidentStory = sifIncidentDetailsEt.getText().toString();;
        String uniqueIdentifier = generateTempSafePalNumber(1000,9999);

//
        /**
         *  Checks if the important fields are filled
         *  **/
        //checks if the birth of date is picked
        //check if date is selected
        if(textViewChosenDate.getText().toString().length() <= 2){
            sifFeedbackSnackbar = Snackbar.make(rootView,"Pick a date of birth",Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            return ReportingActivity.STATUS_SUBMIT_REPORT_ERROR;
        }
        //checks if the incident type is selected
        else if (sifIncidentTypeSpinner.getSelectedItemPosition() <= 0) {
            sifFeedbackSnackbar = Snackbar.make(rootView,"Choose what happened to you.",Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            return ReportingActivity.STATUS_SUBMIT_REPORT_ERROR;
        }
        //checks if the location of the incident is filled by the user
        if (sifIncidentLocationEt.length() == 0 ) {
            sifFeedbackSnackbar = Snackbar.make(rootView, "In what location did the incident happen?", Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            textInputLayoutWhereHappened.setError(context.getString(R.string.cannotLeaveBlank));
            return ReportingActivity.STATUS_SUBMIT_REPORT_ERROR;
        }
        //checks if the a proper story is told by the survivor
        if ( sifIncidentDetailsEt.length() == 0) {
            sifFeedbackSnackbar = Snackbar.make(rootView, "Kindly narrate the story of the incident that happened.", Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            textInputLayoutStory.setError(context.getString(R.string.cannotLeaveBlank));
            return ReportingActivity.STATUS_SUBMIT_REPORT_ERROR;
        }
        /**
         * inserts incident report in to the mysql db through a content provider
         * **/
        ContentValues values = new ContentValues();
        values.put(ReportIncidentTable.COLUMN_REPORTED_BY, reportedBy);
        values.put(ReportIncidentTable.COLUMN_SURVIVOR_DATE_OF_BIRTH, apifSurvivorDateOfBirth);
        values.put(ReportIncidentTable.COLUMN_SURVIVOR_GENDER, survivorGender);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_TYPE, incidentType);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_LOCATION, incidentLocation);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_STORY, incidentStory);
        values.put(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER, uniqueIdentifier);

       //Gets user locations

        //end of user location



        //"0.2123232""32.123233"

        values.put(ReportIncidentTable.COLUMN_REPORTER_LOCATION_LAT,userLatitude);
        values.put(ReportIncidentTable.COLUMN_REPORTER_LOCATION_LNG,userLongitude );
        values.put(ReportIncidentTable.COLUMN_REPORTER_PHONE_NUMBER, "null");
        values.put(ReportIncidentTable.COLUMN_REPORTER_EMAIL, "null");


        //this inserts a new report in to the mysql db
        if (sifReportIncidentUri == null) {
            sifReportIncidentUri = context.getContentResolver().insert(ReportIncidentContentProvider.CONTENT_URI, values);

            //Broadcast receiver that checks for the network status
            IntentFilter netMainFilter =  new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

            context.registerReceiver(sifNetReceiver, netMainFilter);



            return ReportingActivity.STATUS_SUBMIT_REPORT_SUBMITED;
        }
        //updates the report if its already available
        else {
            //Log.e(TAG, "is this really normal???");
            context.getContentResolver().update(sifReportIncidentUri, values, null, null);
            //Log.e(TAG, "is this normal");
            return ReportingActivity.STATUS_SUBMIT_REPORT_ALREADY_AVAILABLE;
        }


    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    static String TAG = SurvivorIncidentFormFragment.class.getSimpleName();

    public void showDatePickerDialog(View v) {
        android.app.DialogFragment dateFragment = new DatePickerFragment();

        dateFragment.show(getFragmentManager(), "datePicker");
    }
    //Randomly load encouraging messages to the Text View
    public void  loadSifMessages(){
        String[] sifMessagesArray = getResources().getStringArray(R.array.seek_medical_care_messages);
        sifEncouragingMessagesTv.setText(sifMessagesArray[randMessageIndex(0, sifMessagesArray.length)].toString());
    }


    //shows spinner drop down for sif incident types
    public void onClickSifIVSpinner(View view){
        sifIncidentTypeSpinner.performClick();
    }
    //Generates a temperory safepal number
    public static String generateTempSafePalNumber(int minimum, int maximum){
        Random rn = new Random();
        int n = maximum - minimum + 1;
        int i = rn.nextInt() % n;
        int randomNum =  minimum + i;
        //changes the negative number to  positve
        if(randomNum<0){randomNum= -randomNum;}

        return "TMP_SPL" + Integer.toString(randomNum);
    }


    private static class userLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {

            if(location!=null){
                double userLong = location.getLongitude();
                double userLat= location.getLatitude();
                userLongitude = Double.toString(userLong);
                userLatitude = Double.toString(userLat);

            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
