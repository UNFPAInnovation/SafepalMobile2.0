package com.unfpa.safepal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

public class WhoSGettingHelpActivity extends AppCompatActivity {

    Toolbar wsghToolbar;
    FloatingActionButton wsghAbortAppFab, wsghBackFab, wsghNextFab;
    Spinner wsghRelationshipSpinner;
    RadioButton wsghYesRB, wsghSomeelseRb;

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

        wsghYesRB = (RadioButton)findViewById(R.id.wsgh_yes_rb);
        wsghSomeelseRb = (RadioButton)findViewById(R.id.wsgh_someoneelse_rb);


        setSupportActionBar(wsghToolbar);

        /**  wsghRelationshipSpinner  **/

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> wsghRSadapter = ArrayAdapter.createFromResource(this,
                R.array.wsgh_someone_else_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        wsghRSadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the (wsghRSadapter to the spinner
        wsghRelationshipSpinner.setAdapter(wsghRSadapter);
        /** ends wsghRelationshipSpinner **/

        wsghAbortAppFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                if(wsghYesRB.isChecked()){
                startActivity(new Intent(getApplicationContext(), SurvivorIncidentFormActivity.class));}

                else if (wsghSomeelseRb.isChecked()){
                startActivity(new Intent(getApplicationContext(), AnotherPersonIncidentFormActivity.class));}

            }
        });
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

    public void onWSGHRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.wsgh_yes_rb:
                if (checked)
                    // Pirates are the best
                wsghRelationshipSpinner.setVisibility(View.GONE);
                    break;
            case R.id.wsgh_someoneelse_rb:
                if (checked)
                  wsghRelationshipSpinner.setVisibility(View.VISIBLE);
                    break;
        }
    }

}
