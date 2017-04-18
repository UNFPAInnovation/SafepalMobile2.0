package com.unfpa.safepal.ProvideHelp;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
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

    //no internet connection
    private ProgressBar csoProgressBar;
    private LinearLayout csoNoInternetLL;
    private Button csoNoInternetButton;
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

        csoNoInternetButton = (Button)findViewById(R.id.cso_no_internet_button);
        csoNoInternetLL = (LinearLayout)findViewById(R.id.cso_no_internet_ll);

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


                try {
                    hidePDialog();
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
                       showInternetRetry();
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

    //hides the progress bar and no internet text
    private void hidePDialog() {
        csoProgressBar.setVisibility(View.GONE);
        csoNoInternetLL.setVisibility(View.GONE);
    }
    private void showPDialog(){
        csoProgressBar.setVisibility(View.VISIBLE);
        csoNoInternetLL.setVisibility(View.GONE);
    }

    private void showInternetRetry(){
        csoProgressBar.setVisibility(View.GONE);
        csoNoInternetLL.setVisibility(View.VISIBLE);

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


            csoSafepalNo.setText(offline);
        }
        cursor.close();

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
                csoAssuranceHelp.setText("Since you did not provide a contact number, safepal service providers will not be able to contact you directly. But you can still walk in to any of the service providers below with your safepal number and they will attend to you. ");

            }

            finalCsoPreview(lat, lng);


        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cso, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_cso_guide:
                csoGuide();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void csoGuide(){
        ViewTarget eTarget = new ViewTarget(R.id.cso_childhelpline_btn, this);
        ShowcaseView homeExitSv = new ShowcaseView.Builder(this)
                .withHoloShowcase()
                .setTarget(eTarget)
                .setContentTitle("Talk to someone")
                .setContentText("Click on the call button in order to talk to someone for help")
                .setStyle(R.style.ExitShowcaseTheme)
                .build();

    }

}

