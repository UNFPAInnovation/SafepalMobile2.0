package com.unfpa.safepal.ProvideHelp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unfpa.safepal.R;
import com.unfpa.safepal.messages.EMessageDialogFragment;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import static com.unfpa.safepal.report.WhoSGettingHelpActivity.randMessageIndex;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {


    private TextView contactEncouragingMessagesTv;
    public static   TextView contactSafepalNo;

    /**
     * Next and buttonExit button
     */
    Button buttonFinish;
    Button buttonExit;

    private Toolbar contactToolbar;
    private LinearLayout contactPhoneEmailLl;
    //    private RadioButton contactYesRB, contactNoRb;
    private static EditText contactPhonenumber;
    private EditText contactEmail;
    static CheckBox checkBoxContactMe;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);


        //Toolbar of contact activity
        contactToolbar = (Toolbar) view.findViewById(R.id.contact_toolbar);
//        setSupportActionBar(contactToolbar);
//        //adds logo and title to toolbar
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //assignment of UI in xml
        buttonExit = (Button) view.findViewById(R.id.exit_app);
        buttonFinish = (Button) view.findViewById(R.id.finish);

//        contactYesRB = (RadioButton)view.findViewById(R.id.contact_me_yes_rb);
        checkBoxContactMe = (CheckBox) view.findViewById(R.id.checkbox_contact_me);
//        contactNoRb = (RadioButton)view.findViewById(R.id.contact_me_not_rb);
        contactPhoneEmailLl = (LinearLayout)view.findViewById(R.id.contact_phone_email_ll);
        contactEncouragingMessagesTv = (TextView)view.findViewById(R.id.contact_ecouraging_messages_tv);
        contactSafepalNo = (TextView)view.findViewById(R.id.contact_safepal_no);

        contactPhonenumber = (EditText)view.findViewById(R.id.contact_phone_et);
        contactEmail = (EditText)view.findViewById(R.id.contact_email_et);

        loadContactFeedbackMessages();
        //updates user with the safepal number
        //updateUIDTextView();

        //picks and shows the mobile reporters location
        //userLocationTracker();


//        buttonExit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
////                moveTaskToBack(true);
////                android.os.Process.killProcess(android.os.Process.myPid());
//////                System.buttonExit(1);
//            }
//        });
//        buttonFinish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent csoIntent = new Intent(getActivity(), CsoActivity.class);
//                csoIntent.putExtra("safepalUniqueNumber",contactSafepalNo.getText().toString());
//
//                if(checkBoxContactMe.isChecked()) {
//
//                    if(contactPhonenumber.getText().length()<5 ){
//                        Toast.makeText(getActivity(), "Provide us with a correct phone number", Toast.LENGTH_LONG).show();
//                        return;
//                    }
//                    startActivity(csoIntent);
//                }
//
////                else if(!checkBoxContactMe.isChecked()){//user doent want to be contacted
////                    startActivity(csoIntent);
////                }
//
//                else {//user doent want to be contacted
////                    Toast.makeText(getActivity(), "Do you want to be contacted back? Choose???!!!", Toast.LENGTH_LONG).show();
//                    startActivity(csoIntent);
//                    return;
//                }
//            }
//        });

        checkBoxContactMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){//user selcted contact me
                    //shows phone number and email
                    contactPhoneEmailLl.setVisibility(View.VISIBLE);
                }else{//user doesnt want to be contacted
                    //hides phonenumber and email on UI
                    contactPhoneEmailLl.setVisibility(View.GONE);

                }
            }
        });

        contactEncouragingMessagesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EMessageDialogFragment emDialog = EMessageDialogFragment.newInstance(
                        getString(R.string.seek_medical_alert_head),
                        contactEncouragingMessagesTv.getText().toString(),
                        getString(R.string.close_dialog));
                emDialog.show(getActivity().getFragmentManager(), "encouraging message");
            }
        });

        return view;

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

    public static boolean areFieldsSet(Context context) {
        if(checkBoxContactMe.isChecked()) {
            if(contactPhonenumber.getText().length() <=8  ){
                Toast.makeText(context, "Provide us with a correct phone number", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
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



    /** All the  Methods **/
    //updates safepal number
    public void updateUIDTextView(){

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int k =0;
                while (k<100)  {
                    try{Thread.sleep(1000);
                    }catch (InterruptedException e ){
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Cursor cursor =  getActivity().getContentResolver().query(
                                    ReportIncidentContentProvider.CONTENT_URI,
                                    null,
                                    null,
                                    null,
                                    null);

                            cursor.moveToLast();

                            ///action happens every sec
                            contactSafepalNo.setText("Your SafePal Number is : " +  cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER)));


                        }
                    });
                    k++;
                }
            }
        };

        new Thread(runnable).start();

    }
    private void  loadContactFeedbackMessages(){
        String[] wsghMessagesArray = getResources().getStringArray(R.array.seek_medical_care_messages);
        contactEncouragingMessagesTv.setText(wsghMessagesArray[randMessageIndex(0, wsghMessagesArray.length)].toString());
    }
    //expand encouraging messages
//    public void onClickContactEncouragingMessages(View view){
//        EMessageDialogFragment emDialog = EMessageDialogFragment.newInstance(
//                getString(R.string.seek_medical_alert_head),
//                contactEncouragingMessagesTv.getText().toString(),
//                getString(R.string.close_dialog));
//        emDialog.show(getActivity().getFragmentManager(), "encouraging message");
//    }
//    public void onContactRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // checks if user wants to be contacted
//        switch(view.getId()) {
//            case R.id.contact_me_yes_rb:
//                if (checked)
//                    //shows phone number and email
//                    contactPhoneEmailLl.setVisibility(View.VISIBLE);
//                break;
//            case R.id.contact_me_not_rb:
//                if (checked)
//                    //hides phonenumber and email on UI
//                    contactPhoneEmailLl.setVisibility(View.GONE);
//                    //starts cso activity to show nearest help
//                    startActivity(new Intent(getActivity(), CsoActivity.class));
//
//                break;
//        }
//    }





}
