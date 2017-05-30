package com.unfpa.safepal.report;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unfpa.safepal.Location.TrackGPS;
import com.unfpa.safepal.Places.GooglePlacesAutocompleteAdapter;
import com.unfpa.safepal.R;
import com.unfpa.safepal.Utils.Layout;
import com.unfpa.safepal.messages.EMessageDialogFragment;
import com.unfpa.safepal.network.NetworkChangeReceiver;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import java.util.Random;

import static com.unfpa.safepal.report.WhoSGettingHelpFragment.randMessageIndex;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnotherPersonIncidentFormFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnotherPersonIncidentFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnotherPersonIncidentFormFragment extends Fragment {

    //Data and Network Layers
    //network changes broadcast receiver
    private static NetworkChangeReceiver apifNetReceiver = new NetworkChangeReceiver();
    private static Uri apifReportIncidentUri;
    /**
     * apif - Stands for "Another Person Incident Form"
     * */

    /**
     * Global Variables*/

    /* User Interface*/

    //apif toolbar
    Toolbar apifToolbar;
    /**
     * Next and buttonExit button
     */
//    Button buttonNext;
//    Button buttonExit;
    ImageView imageWhereHappen;
    ImageView imageStory;
    ImageView imageGender;
    ImageView imageAge;
    ImageView imageQnMark;

    //Logging purposes
    static final String TAG = AnotherPersonIncidentFormFragment.class.getSimpleName();

    //Encouraging messages
    TextView apifEncouragingMessagesTv;

    //Form variables
//    private Button apifDateOfBirthButton;
//    static TextView textViewChosenDate;
    private static RadioGroup apifGenderRG;
    private static RadioButton apifGenderRB;
    private static Spinner apifIncidentTypeSpinner;
    private static Spinner spinnerAgeRange;
    private static AutoCompleteTextView apifIncidentLocationEt;
    private static EditText apifIncidentDetailsEt;
    private  static TrackGPS apifGPS;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RELATIONSHIP_TO_SURVIVOR = "";
    private static final String ARG_PARAM2 = "";

    // TODO: Rename and change types of parameters
    private static String relationshipToSurvivor;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AnotherPersonIncidentFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param relationshipToSurvivor Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnotherPersonIncidentFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnotherPersonIncidentFormFragment newInstance(String relationshipToSurvivor, String param2) {
        AnotherPersonIncidentFormFragment fragment = new AnotherPersonIncidentFormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RELATIONSHIP_TO_SURVIVOR, relationshipToSurvivor);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            relationshipToSurvivor = getArguments().getString(ARG_RELATIONSHIP_TO_SURVIVOR);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Volley Request
        // check from the saved Instance
        apifReportIncidentUri = (bundle == null) ? null : (Uri) bundle
                .getParcelable(ReportIncidentContentProvider.CONTENT_ITEM_TYPE);
    }

    static View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_another_person_incident_form, container, false);


        /** Declaration of user interface **/
        apifToolbar = (Toolbar) rootView.findViewById(R.id.reporting_toolbar);
//        buttonExit = (Button) view.findViewById(R.id.exit_app);
//        buttonNext = (Button) view.findViewById(R.id.finish);
        imageWhereHappen = (ImageView) rootView.findViewById(R.id.image_where_take_place);
        imageStory = (ImageView) rootView.findViewById(R.id.image_story);
        imageGender = (ImageView) rootView.findViewById(R.id.image_gender);
        imageAge= (ImageView) rootView.findViewById(R.id.image_age);
        imageQnMark= (ImageView) rootView.findViewById(R.id.image_spinner_what_happed);
        textInputLayoutWhereHappened = (TextInputLayout)rootView.findViewById(R.id.inpu_latout_where);
        apifGPS = new TrackGPS(getActivity());


        //encouraging messages
        apifEncouragingMessagesTv = (TextView)rootView.findViewById(R.id.apif_encouraging_messages_tv);

        /** Form initialization **/
//        apifDateOfBirthButton = (Button)rootView.findViewById(R.id.date_of_birth_button);
//        textViewChosenDate = (TextView)rootView.findViewById(R.id.chosen_date);

        apifGenderRG=(RadioGroup)rootView.findViewById(R.id.gender_rg);
        apifIncidentTypeSpinner = (Spinner) rootView.findViewById(R.id.incident_type_spinner);
        //age range spinner
        spinnerAgeRange = (Spinner) rootView.findViewById(R.id.age_range_spinner);

        apifIncidentLocationEt = (AutoCompleteTextView) rootView.findViewById(R.id.incident_location_actv);
        apifIncidentDetailsEt = (EditText)rootView.findViewById(R.id.incident_details_rt);


        textInputLayoutStory = (TextInputLayout)rootView.findViewById(R.id.input_latout_story);

//        //setting up apif toolbar with logo
//        setSupportActionBar(apifToolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //load messages
        apifLoadMessages();

        //click actions

          //loads autocomplete places
        //ArrayAdapter<String> apifReportPlacesAdapter = new ArrayAdapter<String>(getActivity(),
          //      android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.auto_complete_report_places));
        apifIncidentLocationEt.setAdapter(new GooglePlacesAutocompleteAdapter(rootView.getContext(), R.layout.location_list_item));
        apifIncidentLocationEt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) parent.getItemAtPosition(position);
                Toast.makeText(rootView.getContext(), str, Toast.LENGTH_SHORT).show();
            }
        });






        //prepare adapter for age range spinner
        ArrayAdapter<CharSequence> ageRangeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.survivor_age_range, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        ageRangeAdapter.setDropDownViewResource( R.layout.spinner_dropdown);
        // Apply the apifIncidentTypeAdapter to the spinner
        spinnerAgeRange.setAdapter(ageRangeAdapter);


        //Calculations for the age range and where incident happened spinners
        spinnerAgeRange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>8){
                   ArrayAdapter<CharSequence> apifIncidentTypeAdapter = ArrayAdapter.createFromResource(getActivity(),
                            R.array.apif_incident_type_above, R.layout.spinner_item);
                    // Specify the layout to use when the list of choices appears
                    apifIncidentTypeAdapter.setDropDownViewResource( R.layout.spinner_dropdown);
                    // Apply the apifIncidentTypeAdapter to the spinner
                    apifIncidentTypeSpinner.setAdapter(apifIncidentTypeAdapter);

                }
                if(position<=8 && position>0){
                    ArrayAdapter<CharSequence> apifIncidentTypeAdapter = ArrayAdapter.createFromResource(getActivity(),
                            R.array.apif_incident_type_below, R.layout.spinner_item);
                    // Specify the layout to use when the list of choices appears
                    apifIncidentTypeAdapter.setDropDownViewResource( R.layout.spinner_dropdown);
                    // Apply the apifIncidentTypeAdapter to the spinner
                    apifIncidentTypeSpinner.setAdapter(apifIncidentTypeAdapter);

                }
                if(position==0){
                    ArrayAdapter<CharSequence> apifIncidentTypeAdapter = ArrayAdapter.createFromResource(getActivity(),
                            R.array.apif_incident_type_above, R.layout.spinner_item);
                    // Specify the layout to use when the list of choices appears
                    apifIncidentTypeAdapter.setDropDownViewResource( R.layout.spinner_dropdown);
                    // Apply the apifIncidentTypeAdapter to the spinner
                    apifIncidentTypeSpinner.setAdapter(apifIncidentTypeAdapter);
                    //Generates random feed back to the user
                    String [] randFeedback = {"What is his/her age?", "Please select his/her age.", "How old is He/She?"};
                    Random rn = new Random();
                    Toast.makeText(rootView.getContext(), randFeedback[rn.nextInt(randFeedback.length)], Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        apifEncouragingMessagesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EMessageDialogFragment emDialog = EMessageDialogFragment.newInstance(
                        getString(R.string.not_your_fault_alert_header),
                        apifEncouragingMessagesTv.getText().toString(),
                        getString(R.string.close_dialog));
                emDialog.show(getFragmentManager(), "encouraging message");

            }
        });

        //give color to images
        imageWhereHappen.setImageDrawable(
                Layout.changeImageColor(getActivity(),
                        imageWhereHappen.getDrawable(), getResources().getColor(R.color.colorImages)));
        imageStory.setImageDrawable(
                Layout.changeImageColor(getActivity(),
                        imageStory.getDrawable(), getResources().getColor(R.color.colorImages)));
        imageGender.setImageDrawable(
                Layout.changeImageColor(getActivity(),
                        imageGender.getDrawable(), getResources().getColor(R.color.colorImages)));
        imageAge.setImageDrawable(
                Layout.changeImageColor(getActivity(),
                        imageAge.getDrawable(), getResources().getColor(R.color.colorImages)));
        imageQnMark.setImageDrawable(
                Layout.changeImageColor(getActivity(),
                        imageQnMark.getDrawable(), getResources().getColor(R.color.colorImages)));
return rootView;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(apifNetReceiver);
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
    static Snackbar sifFeedbackSnackbar;
    /**
     * true = successfull
     *
     * 0=succesfull
     * 1=error in data or invalid data
     * 2=report already available
     *
     * @param context
     * @return
     */
    public static int submitForm(Context context) {
        int genderRBApifId = apifGenderRG.getCheckedRadioButtonId();

        if(genderRBApifId==-1){
            Toast.makeText(context, "Select the survivors gender?",Toast.LENGTH_LONG).show();
            return ReportingActivity.STATUS_SUBMIT_REPORT_ERROR;
        }

        View genderApifRBView = apifGenderRG.findViewById(genderRBApifId);
        int idx = apifGenderRG.indexOfChild(genderApifRBView);
        apifGenderRB =(RadioButton)apifGenderRG.getChildAt(idx);

        //checks if the location of the incident is filled by the user
        if (apifIncidentLocationEt.length() == 0 ) {
//            if(idx==0) Toast.makeText(context, "In what location did the incident happen her?",Toast.LENGTH_LONG).show();
//            else       Toast.makeText(context, "In what location did the incident happen him?",Toast.LENGTH_LONG).show();
            sifFeedbackSnackbar = Snackbar.make(rootView ,"In what location did the incident happen him/her?",
                    Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            textInputLayoutWhereHappened.setError(context.getString(R.string.cannotLeaveBlank));
            return ReportingActivity.STATUS_SUBMIT_REPORT_ERROR;
        }

        if (apifIncidentTypeSpinner.getSelectedItemPosition() <= 0) {
//            if(idx==0) Toast.makeText(context, "Select the type of incident that happened to her.",Toast.LENGTH_LONG).show();
//            else       Toast.makeText(context, "Select the type of incident happened to him.",Toast.LENGTH_LONG).show();
            sifFeedbackSnackbar = Snackbar.make(rootView ,"Select the type of incident happened to him/her.",
                    Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            return ReportingActivity.STATUS_SUBMIT_REPORT_ERROR;
        }
        //age range check out

        if (spinnerAgeRange.getSelectedItemPosition() <= 0) {
//            if(idx==0) Toast.makeText(context, "Select the type of incident that happened to her.",Toast.LENGTH_LONG).show();
//            else       Toast.makeText(context, "Select the type of incident happened to him.",Toast.LENGTH_LONG).show();
            sifFeedbackSnackbar = Snackbar.make(rootView ,"Please select his/her age.",
                    Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            return ReportingActivity.STATUS_SUBMIT_REPORT_ERROR;
        }

        //checks if the a proper story is told by the survivor
        if ( apifIncidentDetailsEt.length() == 0) {
            sifFeedbackSnackbar = Snackbar.make(rootView ,"Kindly narrate the story of the incident that happened him/her",
                    Snackbar.LENGTH_LONG);
            sifFeedbackSnackbar.show();
            textInputLayoutStory.setError(context.getString(R.string.cannotLeaveBlank));
            return ReportingActivity.STATUS_SUBMIT_REPORT_ERROR;
        }


        String apifReportedBy = relationshipToSurvivor;
        Log.d(TAG, "Relation shiop to..: " + apifReportedBy );
        String apifSurvivorAge = (String)spinnerAgeRange.getSelectedItem();
        String apifSurvivorGender = (String)apifGenderRB.getText();
        String apifIncidentType =(String)apifIncidentTypeSpinner.getSelectedItem();
        String apifIncidentLocation = apifIncidentLocationEt.getText().toString();
        String apifIncidentStory = apifIncidentDetailsEt.getText().toString();;
        String apifUniqueIdentifier = SurvivorIncidentFormFragment.generateTempSafePalNumber(10000,99999);

        /**
         * inserts incident report in to the mysql db through a content provider
         * **/
        ContentValues values = new ContentValues();
        values.put(ReportIncidentTable.COLUMN_REPORTED_BY, apifReportedBy);
        values.put(ReportIncidentTable.COLUMN_SURVIVOR_DATE_OF_BIRTH, apifSurvivorAge);
        values.put(ReportIncidentTable.COLUMN_SURVIVOR_GENDER, apifSurvivorGender);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_TYPE, apifIncidentType);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_LOCATION, apifIncidentLocation);
        values.put(ReportIncidentTable.COLUMN_INCIDENT_STORY, apifIncidentStory);
        values.put(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER, apifUniqueIdentifier);


        values.put(ReportIncidentTable.COLUMN_REPORTER_LOCATION_LAT, "0.33563");
       //Log.d("lat", Double.toString(apifGPS.getLatitude()));
       // Log.d("lat", Double.toString(apifGPS.getLongitude()));

        values.put(ReportIncidentTable.COLUMN_REPORTER_LOCATION_LNG, "32.559947");
        values.put(ReportIncidentTable.COLUMN_REPORTER_PHONE_NUMBER, "null");
        values.put(ReportIncidentTable.COLUMN_REPORTER_EMAIL, "null");



        Log.d("Last", "executed");
        /**
         *  Checks if the important fields are filled
         *  **/

        //checks if the incident type is selected
        //this inserts a new report in to the mysql db
        if (apifReportIncidentUri == null) {
            apifReportIncidentUri = context.getContentResolver().insert(ReportIncidentContentProvider.CONTENT_URI, values);

            //Broadcast receiver that checks for the network status
            IntentFilter apifNetMainFilter =  new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            apifNetReceiver = new NetworkChangeReceiver();
            context.registerReceiver(apifNetReceiver, apifNetMainFilter);


            Log.d(TAG, "part one executed");

            return ReportingActivity.STATUS_SUBMIT_REPORT_SUBMITED;
        }
        //updates the report if its already available // TODO: 13-Nov-16 look 4 try catches... is this difference okay
        else {
            Log.e(TAG, "is this normal???");
            Log.e(TAG, "The  case was not submitted");

             context.getContentResolver().update(apifReportIncidentUri, values, null, null);

            Log.d(TAG, "part two executed");
            return ReportingActivity.STATUS_SUBMIT_REPORT_ALREADY_AVAILABLE;
        }


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void apifLoadMessages(){
        String [] apifMessagesArray = getResources().getStringArray(R.array.seek_medical_care_messages);
        apifEncouragingMessagesTv.setText(apifMessagesArray[randMessageIndex(0, apifMessagesArray.length)].toString());
    }




    //shows spinner dropdown for apif incident types
    public void onClickApifIVSpinner(View view){
        apifIncidentTypeSpinner.performClick();
    }




}
