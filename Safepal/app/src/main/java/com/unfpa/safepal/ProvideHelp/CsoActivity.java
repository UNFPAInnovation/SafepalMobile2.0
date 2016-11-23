package com.unfpa.safepal.ProvideHelp;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unfpa.safepal.ProvideHelp.RVCsoModel.CsoRvAdapter;
import com.unfpa.safepal.ProvideHelp.RVCsoModel.TheCSO;
import com.unfpa.safepal.R;
import com.unfpa.safepal.messages.EMessageDialogFragment;
import com.unfpa.safepal.network.MySingleton;
import com.unfpa.safepal.network.VolleyCallback;
import com.unfpa.safepal.store.RIContentObserver;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.unfpa.safepal.report.WhoSGettingHelpFragment.randMessageIndex;

public class CsoActivity extends AppCompatActivity {

    Toolbar csoToolbar;

    /**
     * Next and buttonExit button
     */
    Button buttonNext;
    Button buttonExit;

    TextView csoSafepalNo, csoContactInfo,csoAssuranceHelp, csoEncouragingMessagesTV;

    //variables for the nearest cso list
    private List<TheCSO> csosList = new ArrayList<>();
    private RecyclerView csosRecyclerView;
    private CsoRvAdapter csosAdapter;
    private ProgressBar csoProgressBar;

    // TheCSOs json url
    private static final String URL_CSO_API = "http://52.43.152.73/api/location.php";

     /**
     * Represents a geographical location.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cso);

        //buttonFinish and buttonExit buttons
        buttonNext = (Button) findViewById(R.id.finish);
        buttonExit = (Button) findViewById(R.id.exit_app);

        // choose someone else relationship spinner
        csoEncouragingMessagesTV = (TextView) findViewById(R.id.cso_ecouraging_messages_tv);
        csoSafepalNo = (TextView)findViewById(R.id.cso_safepal_number);
        csoContactInfo= (TextView)findViewById(R.id.cso_contact_info);
        csoAssuranceHelp = (TextView)findViewById(R.id.cso_assurance_help);

        Toolbar csoToolbar = (Toolbar) findViewById(R.id.cso_toolbar);
        setSupportActionBar(csoToolbar);


        loadCsoMessages();

        updateCsoUIDTV();


        csoProgressBar = (ProgressBar) findViewById(R.id.cso_progress_bar);
        csosRecyclerView = (RecyclerView) findViewById(R.id.cso_recycler_view);

        csosAdapter = new CsoRvAdapter(csosList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        csosRecyclerView.setLayoutManager(mLayoutManager);
        csosRecyclerView.setItemAnimator(new DefaultItemAnimator());
        csosRecyclerView.setAdapter(csosAdapter);
        updateUserWithCsos();

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
                Process.killProcess(Process.myPid());
                System.exit(1);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "button next clicked");
               finish();
            }
        });
    }
     String TAG = CsoActivity.class.getSimpleName();
    //Randomly load encouraging messages to the Text View
     public void loadCsoMessages() {
        String[] csoMessagesArray = getResources().getStringArray(R.array.signs_of_sgbv);
        csoEncouragingMessagesTV.setText(csoMessagesArray[randMessageIndex(0, csoMessagesArray.length)].toString());
    }

    //shows encouraging messages in dialog on click of the Text View
    public void onClickCsoEncouragingMessages(View view) {

        EMessageDialogFragment emDialog = EMessageDialogFragment.newInstance(
                getString(R.string.signs_of_sgbv_header),
                csoEncouragingMessagesTV.getText().toString(),
                getString(R.string.close_dialog));
        emDialog.show(getFragmentManager(), "encouraging message");
    }

    public void onClickCsoCall(View view) {

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:116")));
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "SafePal can't make call now", Toast.LENGTH_SHORT).show();
        }
    }

    public void finalCsoPreview(String lat, String lng) {

        getNearestCSOs(lat, lng, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                hidePDialog();


                try {
                    JSONObject response = new JSONObject(result);
                    JSONArray arr = response.getJSONArray("posts");

                    TheCSO newCsos;
                    for (int i = 0; i < arr.length(); i++)
                    {
                        String nearestCsoName = arr.getJSONObject(i).getString("cso_name");
                        String nearestCsoDistrict = arr.getJSONObject(i).getString("cso_location");
                        String nearestCsoDistance = arr.getJSONObject(i).getString("cso_distance");
                        String nearestCsophonenumber = arr.getJSONObject(i).getString("cso_phone_number");

                        newCsos = new TheCSO(nearestCsoName + " in " + nearestCsoDistrict, roundsOffCsoNearestDistance(nearestCsoDistance), nearestCsophonenumber);
                        csosList.add(newCsos);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                csosAdapter.notifyDataSetChanged();
            }
        });
    }

    // Method pushes the data to json server suing volley
    private void getNearestCSOs(final String toServerReporterLat, final String toServerReporterLng, final VolleyCallback callback) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CSO_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(getBaseContext(), response.toString(),Toast.LENGTH_LONG).show();

                        callback.onSuccessResponse(response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //VolleyLog.d("test", "Error: " + error.getMessage());
                        hidePDialog();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("reporter_lat", toServerReporterLat);
                params.put("reporter_long", toServerReporterLng);
                return params;
            }

        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    //hides the progress bar
    private void hidePDialog() {
        csoProgressBar.setVisibility(View.GONE);
    }

    private String roundsOffCsoNearestDistance(String survivorToCso){
      String convert=null;
       try{
           float floatValue = Float.valueOf(survivorToCso);
           floatValue= BigDecimal.valueOf(floatValue).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
           convert = Float.toString(floatValue);
       }catch (Exception  d){}
        return convert;

    }

    /** All the  Methods **/
    //updates safepal number
    public void updateCsoUIDTV(){
        Cursor cursor =  getContentResolver().query(
                ReportIncidentContentProvider.CONTENT_URI,
                null,
                null,
                null,
                null);
        if(cursor != null) {
            StringBuilder offline = new StringBuilder();
            cursor.moveToLast();
            offline.append("Your SafePal Number is: " + cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER)));

            cursor.close();
            csoSafepalNo.setText(offline);
        }

        Handler riHandler = new Handler(){
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        String sb = (String) msg.obj;
                        csoSafepalNo.setText(sb);
                        break;

                    default:

                        break;
                }
            };
        };

        RIContentObserver rICsoContentObserver = new RIContentObserver(this, riHandler);

        getContentResolver().registerContentObserver(ReportIncidentContentProvider.CONTENT_URI,
                true,
                rICsoContentObserver);


    }
    //retrieves lat and lng from db and inserts them into the remote method for retreiving the csos
    public void updateUserWithCsos(){

        Cursor cursorRetrieveLatLng =  getContentResolver().query(
                ReportIncidentContentProvider.CONTENT_URI,
                null,
                null,
                null,
                null);

        if (cursorRetrieveLatLng != null) {
            cursorRetrieveLatLng.moveToLast();

            String lat =  cursorRetrieveLatLng.getString(cursorRetrieveLatLng.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_LOCATION_LAT));
            String lng =  cursorRetrieveLatLng.getString(cursorRetrieveLatLng.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_LOCATION_LNG));
            String phone =  cursorRetrieveLatLng.getString(cursorRetrieveLatLng.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_PHONE_NUMBER));
            String email =  cursorRetrieveLatLng.getString(cursorRetrieveLatLng.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_EMAIL));

            if(phone.length()>8){
                csoContactInfo.setText("Contact Phonenumber: " + phone);
                csoAssuranceHelp.setText("Safepal will contact you on the above phonenumber.");
                if(email.length()>8){
                    csoContactInfo.setText("Contact Phonenumber: " + phone+ "\nContact Email: " +email);
                    csoAssuranceHelp.setText("Safepal will contact you on the above phonenumber or email. "); }
            }
            else {
                csoContactInfo.setText("No Contacts provided. " );
                csoAssuranceHelp.setText("Safepal service providers will not contact you back. Walk in to any provider below with our safepal number and they will attend to you. ");

            }

            finalCsoPreview(lat, lng);


        }

    }
}

