package com.unfpa.safepal.ProvideHelp;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.unfpa.safepal.store.RIContentObserver;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import static com.unfpa.safepal.report.WhoSGettingHelpFragment.randMessageIndex;

/**
 * Provides list of CSO that user can contact for help
 */
public class ContactFragment extends Fragment {
    private TextView contactEncouragingMessagesTv;

    public static TextView contactSafepalNo;

    RIContentObserver reportIncidentContentObserver;
    Button buttonFinish;
    Button buttonExit;

    private LinearLayout contactPhoneEmailLl;
    private static EditText contactPhonenumber;
    static CheckBox checkBoxContactMe;
    private OnFragmentInteractionListener mListener;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        buttonExit = (Button) view.findViewById(R.id.exit_app);
        buttonFinish = (Button) view.findViewById(R.id.finish);
        checkBoxContactMe = (CheckBox) view.findViewById(R.id.checkbox_contact_me);
        contactPhoneEmailLl = (LinearLayout) view.findViewById(R.id.contact_phone_email_ll);
        contactEncouragingMessagesTv = (TextView) view.findViewById(R.id.contact_ecouraging_messages_tv);
        contactSafepalNo = (TextView) view.findViewById(R.id.contact_safepal_no);
        contactPhonenumber = (EditText) view.findViewById(R.id.contact_phone_et);
        displayPhoneNumberRemoveCheckbox();

        contactPhonenumber.addTextChangedListener(new TextWatcher() {
            int length_before = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                length_before = s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (length_before < s.length()) {

                    if (s.length() == 3 || s.length() == 7)
                        s.append("-");
                    if (s.length() > 3) {
                        if (Character.isDigit(s.charAt(3)))
                            s.insert(3, "-");
                    }
                    if (s.length() > 7) {
                        if (Character.isDigit(s.charAt(7)))
                            s.insert(7, "-");

                    }
                }
            }
        });

        loadContactFeedbackMessages();
        //update unique number
        updateUIDTextView();

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

    private void displayPhoneNumberRemoveCheckbox() {
        contactPhoneEmailLl.setVisibility(View.VISIBLE);
        checkBoxContactMe.setVisibility(View.INVISIBLE);
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
        if (checkBoxContactMe.isChecked()) {
            if (contactPhonenumber.getText().length() <= 8) {
                Toast.makeText(context, "Please enter a correct phone number", Toast.LENGTH_LONG).show();
                return false;
            }

            //updates the  phone number, email, latitude and longitude
            Cursor cursorUpdate = context.getContentResolver().query(
                    ReportIncidentContentProvider.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
            ContentValues dataValues = new ContentValues();
            // end of the updates


            dataValues.put(ReportIncidentTable.COLUMN_REPORTER_PHONE_NUMBER, contactPhonenumber.getText().toString());

            if (cursorUpdate != null) {
                cursorUpdate.moveToLast();

                // Update reported incident
                context.getContentResolver().update(ReportIncidentContentProvider.CONTENT_URI, dataValues, ReportIncidentTable.COLUMN_ID + "=" +
                        cursorUpdate.getString(cursorUpdate.getColumnIndex(
                                ReportIncidentTable.COLUMN_ID)), null);

            }
        }
        return true;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //updates safepal number to the user
    public void updateUIDTextView() {
        Cursor cursor = getActivity().getContentResolver().query(
                ReportIncidentContentProvider.CONTENT_URI,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            StringBuilder offline = new StringBuilder();
            cursor.moveToLast();
            offline.append("Your SafePal Number is: " + cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER)));

            cursor.close();
            contactSafepalNo.setText(offline);
        }

        Handler riHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        String sb = (String) msg.obj;
                        contactSafepalNo.setText(sb);
                        break;

                    default:

                        break;
                }
            }

            ;
        };

        reportIncidentContentObserver = new RIContentObserver(getActivity(), riHandler);
        getActivity().getContentResolver().registerContentObserver(ReportIncidentContentProvider.CONTENT_URI,
                true,
                reportIncidentContentObserver);
    }

    private void loadContactFeedbackMessages() {
        String[] wsghMessagesArray = getResources().getStringArray(R.array.seek_medical_care_messages);
        contactEncouragingMessagesTv.setText(wsghMessagesArray[randMessageIndex(0, wsghMessagesArray.length)].toString());
    }
}

