package com.unfpa.safepal.ProvideHelp;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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
import com.unfpa.safepal.home.HomeActivity;
import com.unfpa.safepal.messages.EMessageDialogFragment;
import com.unfpa.safepal.network.MySingleton;
import com.unfpa.safepal.network.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import static com.unfpa.safepal.report.WhoSGettingHelpActivity.randMessageIndex;

public class CsoActivity extends AppCompatActivity implements
        ConnectionCallbacks, OnConnectionFailedListener{

    Toolbar csoToolbar;

    /**
     * Next and buttonExit button
     */
    Button buttonNext;
    Button buttonExit;

    TextView csoSafepalNo, csoEncouragingMessagesTV;

    //variables for the nearest cso list
    private List<TheCSO> csosList = new ArrayList<>();
    private RecyclerView csosRecyclerView;
    private CsoRvAdapter csosAdapter;
    private ProgressBar csoProgressBar;

    // TheCSOs json url
    private static final String URL_CSO_API = "http://52.43.152.73/api/location.php";

    //location
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;
     /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    protected String mLatitudeLabel;
    protected String mLongitudeLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cso);

        //buttonNext and buttonExit buttons
        buttonNext = (Button) findViewById(R.id.next);
        buttonExit = (Button) findViewById(R.id.exit_app);

        // choose someone else relationship spinner

        csoEncouragingMessagesTV = (TextView) findViewById(R.id.cso_ecouraging_messages_tv);


//        csoSafepalNo = (TextView) findViewById(R.id.cso_safepal_no);
//        String safepalNumber =getIntent().getExtras().getString("safepalUniqueNumber").toString();
//        csoSafepalNo.setText(safepalNumber);
        //Encouraging messages

        csoToolbar = (Toolbar) findViewById(R.id.cso_toolbar);
        setSupportActionBar(csoToolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        loadCsoMessages();
        buildGoogleApiClient();

        csoProgressBar = (ProgressBar) findViewById(R.id.cso_progress_bar);
        csosRecyclerView = (RecyclerView) findViewById(R.id.cso_recycler_view);

        csosAdapter = new CsoRvAdapter(csosList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        csosRecyclerView.setLayoutManager(mLayoutManager);
        csosRecyclerView.setItemAnimator(new DefaultItemAnimator());
        csosRecyclerView.setAdapter(csosAdapter);



        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
                Process.killProcess(Process.myPid());
                System.exit(1);
            }
        });

        buttonExit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Uri packageURI = Uri.parse("package:com.unfpa.safepal");
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);

                return true;
            }
        });
//        csoFinishFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//            }
//        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });


    }

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
        emDialog.show(getSupportFragmentManager(), "encouraging message");
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

    public void finalCsoPreview(double lat, double lng) {

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
                        newCsos = new TheCSO(nearestCsoName + " in " + nearestCsoDistrict, roundsOffCsoNearestDistance(nearestCsoDistance));
                        csosList.add(newCsos);

                    }
                    Toast.makeText(getBaseContext(), Integer.toString(arr.length()), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                csosAdapter.notifyDataSetChanged();
            }
        });
    }

    // Method pushes the data to json server suing volley
    private void getNearestCSOs(final double toServerReporterLat, final double toServerReporterLng, final VolleyCallback callback) {


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
                params.put("reporter_lat", String.valueOf(toServerReporterLat));
                params.put("reporter_long", String.valueOf(toServerReporterLng));
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

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
        @Override
        public void onStop () {
            super.onStop();
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }

        }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {


            finalCsoPreview(mLastLocation.getLatitude(),mLastLocation.getLongitude());

        } else {
            Toast.makeText(this, "SafePal failed to get your location.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onConnectionSuspended(int result) {
        //Log.i("status", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("Location Status: ", "Connection suspended");
        mGoogleApiClient.connect();
    }
}

