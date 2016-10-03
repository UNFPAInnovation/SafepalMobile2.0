package com.unfpa.safepal.report;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.unfpa.safepal.R;
import com.unfpa.safepal.home.HomeActivity;
import com.unfpa.safepal.messages.messageDialog;

import java.util.Random;

public class WhoSGettingHelpActivity extends AppCompatActivity {

    Toolbar wsghToolbar;
    FloatingActionButton wsghAbortAppFab, wsghBackFab, wsghNextFab;
    Spinner wsghRelationshipSpinner;
    RadioButton wsghYesRB, wsghSomeelseRb;
    RadioGroup wsghWhoRG;
    Snackbar wsghFeedbackSnackbar;
    RelativeLayout wsghSpinnerRl;
    TextView wsghEncouragingMessagesTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_s_getting_help);
        /***
         * Declaration and assignments of xml user interfaces*
         * */

        //Toolbar of the who's getting help activity
        wsghToolbar = (Toolbar) findViewById(R.id.wsgh_toolbar);
        //Abort fab of  who's getting help activity
        wsghAbortAppFab = (FloatingActionButton) findViewById(R.id.wsgh_abort_app_fab);
        //Back fab of  who's getting help activity
        wsghBackFab = (FloatingActionButton) findViewById(R.id.wsgh_back_fab);
        //Next fab of  who's getting help activity
        wsghNextFab = (FloatingActionButton) findViewById(R.id.wsgh_next_fab);
        // choose someone else relationship spinner
        wsghRelationshipSpinner = (Spinner) findViewById(R.id.wsgh_relationship_spinner);

        wsghSpinnerRl = (RelativeLayout)findViewById(R.id.wsgh_spinner_rl);
        wsghWhoRG = (RadioGroup)findViewById(R.id.wsgh_who_rg);
        wsghYesRB = (RadioButton)findViewById(R.id.wsgh_yes_rb);
        wsghSomeelseRb = (RadioButton)findViewById(R.id.wsgh_someoneelse_rb);
        wsghEncouragingMessagesTv = (TextView)findViewById(R.id.wsgh_ecouraging_messages_tv);

        setSupportActionBar(wsghToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        /**  wsghRelationshipSpinner  **/

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> wsghRSadapter = ArrayAdapter.createFromResource(this,
                R.array.wsgh_someone_else_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        wsghRSadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the (wsghRSadapter to the spinner
        wsghRelationshipSpinner.setAdapter(wsghRSadapter);
        /** ends wsghRelationshipSpinner **/

        loadFeedbackMessages();

        wsghAbortAppFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });


        wsghBackFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        wsghNextFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(wsghYesRB.isChecked())
                        startActivity(new Intent(getApplicationContext(), SurvivorIncidentFormActivity.class));

                    else if (wsghSomeelseRb.isChecked()){
                        //checks if the relationship to the survivor has been selected
                        if (wsghRelationshipSpinner.getSelectedItemPosition() <= 0) {
                            wsghFeedbackSnackbar = Snackbar.make(view, "what is your relationship to survivor?", Snackbar.LENGTH_LONG);
                            wsghFeedbackSnackbar.show();
                            return;
                        }

                        Intent apifIntent = new Intent(view.getContext(),AnotherPersonIncidentFormActivity.class);
                        apifIntent.putExtra("relationshipToSurvivor", wsghRelationshipSpinner.getSelectedItem().toString()
                        );
                        startActivity(apifIntent);

                    }
       }
        });
    }

    public void onWSGHRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.wsgh_yes_rb:
                if (checked)
                    // Pirates are the best
                wsghSpinnerRl.setVisibility(View.GONE);
                    break;
            case R.id.wsgh_someoneelse_rb:
                if (checked)
                    wsghSpinnerRl.setVisibility(View.VISIBLE);
                    break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_who_sgetting_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void  loadFeedbackMessages(){
        String[] wsghMessagesArray = getResources().getStringArray(R.array.not_your_fault_messages);
        wsghEncouragingMessagesTv.setText(wsghMessagesArray[randMessageIndex(0, wsghMessagesArray.length)].toString());
    }

    //random interger to randomly pick messages from arrays
    public static int randMessageIndex(int min, int max) {
        Random randomNum = new Random();
        int index = randomNum.nextInt(max - min);
        return index;
    }

    public void onClickWsghEncouragingMessages(View view){
        messageDialog wsghMessageDialog = new messageDialog(wsghEncouragingMessagesTv);
        wsghMessageDialog.show(getSupportFragmentManager(), "messages");
    }
}
