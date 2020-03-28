package com.unfpa.safepal.report;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.unfpa.safepal.R;
import com.unfpa.safepal.Utils.Utilities;
import com.unfpa.safepal.messages.EMessageDialogFragment;

import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static com.unfpa.safepal.report.SurvivorIncidentFormFragment.TAG;

public class WhoSGettingHelpFragment extends Fragment {

    Toolbar wsghToolbar;
    static Spinner wsghRelationshipSpinner;
    static RadioButton wsghYesRB;
    static RadioButton wsghSomeelseRb;
    RadioGroup wsghWhoRG;
    static Snackbar wsghFeedbackSnackbar;
    RelativeLayout wsghSpinnerRl;
    TextView wsghEncouragingMessagesTv;


    public WhoSGettingHelpFragment() {
    }

    public static WhoSGettingHelpFragment newInstance() {
        WhoSGettingHelpFragment fragment = new WhoSGettingHelpFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_whos_getting_help, container, false);

        /***
         * Declaration and assignments of xml user interfaces*
         * */
        wsghToolbar = (Toolbar) rootView.findViewById(R.id.reporting_toolbar);
        // choose someone else relationship spinner
        wsghRelationshipSpinner = (Spinner) rootView.findViewById(R.id.wsgh_relationship_spinner);

        wsghSpinnerRl = (RelativeLayout) rootView.findViewById(R.id.wsgh_spinner_rl);
        wsghWhoRG = (RadioGroup) rootView.findViewById(R.id.wsgh_who_rg);
        wsghYesRB = (RadioButton) rootView.findViewById(R.id.wsgh_yes_rb);
        wsghSomeelseRb = (RadioButton) rootView.findViewById(R.id.wsgh_someoneelse_rb);
        wsghEncouragingMessagesTv = (TextView) rootView.findViewById(R.id.wsgh_ecouraging_messages_tv);

        /**  wsghRelationshipSpinner  **/

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> wsghRSadapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.wsgh_someone_else_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        wsghRSadapter.setDropDownViewResource(R.layout.spinner_dropdown);
        // Apply the (wsghRSadapter to the spinner
        wsghRelationshipSpinner.setAdapter(wsghRSadapter);
        /** ends wsghRelationshipSpinner **/

        loadFeedbackMessages();

        wsghEncouragingMessagesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WsghEncouragingMessagesDialog();
            }
        });

        // displayed previous input data when user presses previous
        displayPreviousInputData(wsghRSadapter);

        wsghYesRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Is the button now checked?
                boolean checked = ((RadioButton) view).isChecked();

                // Check which radio button was clicked
                switch (view.getId()) {
                    case R.id.wsgh_yes_rb:
                        if (checked)
                            wsghSpinnerRl.setVisibility(View.GONE);
                        break;
                    case R.id.wsgh_someoneelse_rb:
                        if (checked)
                            wsghSpinnerRl.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        wsghSomeelseRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Is the button now checked?
                boolean checked = ((RadioButton) view).isChecked();

                // Check which radio button was clicked
                switch (view.getId()) {
                    case R.id.wsgh_yes_rb:
                        if (checked)
                            //startActivity(new Intent(getApplicationContext(), SurvivorIncidentFormActivity.class));
                            // Pirates are the best
                            wsghSpinnerRl.setVisibility(View.GONE);


                        break;
                    case R.id.wsgh_someoneelse_rb:
                        if (checked)
                            wsghSpinnerRl.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        return rootView;
    }

    private void displayPreviousInputData(ArrayAdapter<CharSequence> wsghRSadapter) {
        if (Utilities.getFormParameter(getActivity().getApplicationContext(), "backButtonPressed")) {
            if (Utilities.getFormParameter(getActivity().getApplicationContext(), "isSomeOneElse")) {
                wsghSomeelseRb.setChecked(true);
                wsghSpinnerRl.setVisibility(View.VISIBLE);

                String formParameterPref = "FORM_PARAMETER_PREF";
                SharedPreferences prefs = getActivity().getSharedPreferences(formParameterPref, MODE_PRIVATE);
                wsghRelationshipSpinner.setSelection(wsghRSadapter.getPosition(prefs.getString("wsghRelationshipSpinner", "")));
            } else {
                wsghYesRB.setChecked(true);
            }
        }
    }

    public void loadFeedbackMessages() {
        //randomly picks a message
        String[] wsghMessagesArray = getResources().getStringArray(R.array.not_your_fault_messages);
        wsghEncouragingMessagesTv.setText(wsghMessagesArray[randMessageIndex(0, wsghMessagesArray.length)].toString());
        //shows the load message in a dialog
        //WsghEncouragingMessagesDialog();
    }

    //random interger to randomly pick messages from arrays
    public static int randMessageIndex(int min, int max) {
        Random randomNum = new Random();
        int index = randomNum.nextInt(max - min);
        return index;
    }

    public void WsghEncouragingMessagesDialog() {

        EMessageDialogFragment emDialog = EMessageDialogFragment.newInstance(
                getString(R.string.not_your_fault_alert_header),
                wsghEncouragingMessagesTv.getText().toString(),
                getString(R.string.close_dialog));
        emDialog.show(this.getFragmentManager(), "encouraging message");
    }
}

