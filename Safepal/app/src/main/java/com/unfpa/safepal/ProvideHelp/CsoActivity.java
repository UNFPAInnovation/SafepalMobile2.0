package com.unfpa.safepal.ProvideHelp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.unfpa.safepal.ProvideHelp.RVCsoModel.BeforeCsoInfo;
import com.unfpa.safepal.ProvideHelp.RVCsoModel.CsoRvAdapter;
import com.unfpa.safepal.ProvideHelp.RVCsoModel.TheCSO;
import com.unfpa.safepal.R;
import com.unfpa.safepal.messages.EMessageDialogFragment;
import com.unfpa.safepal.store.RIContentObserver;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.unfpa.safepal.report.WhoSGettingHelpFragment.randMessageIndex;

public class CsoActivity extends AppCompatActivity {

    private static final int REQUEST_PHONE_CALL = 325;
    Button buttonNext;

    TextView csoSafepalNo, csoContactInfo, csoAssuranceHelp, csoEncouragingMessagesTV;

    private List<BeforeCsoInfo> beforeCsoList = new ArrayList<>();
    private List<TheCSO> csosList = new ArrayList<>();
    String TAG = CsoActivity.class.getSimpleName();

    private RecyclerView csosRecyclerView;
    private CsoRvAdapter csosAdapter;

    //no internet connection
    private ProgressBar csoProgressBar;
    private LinearLayout csoNoInternetLL;
    private Button csoNoInternetButton;
    // TheCSOs json url
    private static final String URL_CSO_API = "http://52.43.152.73/api/location.php";
    //This is a temporary list of cso's hard coded here
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cso);

        buttonNext = (Button) findViewById(R.id.finish);

        csoNoInternetButton = (Button) findViewById(R.id.cso_no_internet_button);
        csoNoInternetLL = (LinearLayout) findViewById(R.id.cso_no_internet_ll);

        // choose someone else relationship spinner
        csoEncouragingMessagesTV = (TextView) findViewById(R.id.cso_ecouraging_messages_tv);
        csoSafepalNo = (TextView) findViewById(R.id.cso_safepal_number);
        csoContactInfo = (TextView) findViewById(R.id.cso_contact_info);
        csoAssuranceHelp = (TextView) findViewById(R.id.cso_assurance_help);

        Toolbar csoToolbar = (Toolbar) findViewById(R.id.cso_toolbar);
        setSupportActionBar(csoToolbar);

        loadCsoMessages();

        updateSafepalNumber();

        csosRecyclerView = (RecyclerView) findViewById(R.id.cso_recycler_view);

        csosAdapter = new CsoRvAdapter(csosList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        csosRecyclerView.setLayoutManager(mLayoutManager);
        csosRecyclerView.setItemAnimator(new DefaultItemAnimator());
        csosRecyclerView.setAdapter(csosAdapter);
        retrieveCSOLocations();

        buttonNext.setOnClickListener(view -> finish());
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
        emDialog.show(getFragmentManager(), "encouraging message");
    }

    public void onClickCsoCall(View view) {

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CsoActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:116")));
            }
        } catch (ActivityNotFoundException ex) {
            Log.e(TAG, "onClickCsoCall: ", ex);
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:116")));
        }
    }

    public void updateSafepalNumber() {
        Cursor cursor = getContentResolver().query(
                ReportIncidentContentProvider.CONTENT_URI,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            StringBuilder offline = new StringBuilder();
            cursor.moveToLast();
            offline.append("Your SafePal Number is: " + cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER)));
            csoSafepalNo.setText(offline);
        }
        cursor.close();

        Handler riHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case 1:
                        String sb = (String) msg.obj;
                        csoSafepalNo.setText(sb);
                        break;

                    default:

                        break;
                }
            }
        };

        RIContentObserver rICsoContentObserver = new RIContentObserver(this, riHandler);

        getContentResolver().registerContentObserver(ReportIncidentContentProvider.CONTENT_URI,
                true,
                rICsoContentObserver);
    }

    //retrieves lat and lng from db and inserts them into the remote method for retrieving the csos
    public void retrieveCSOLocations() {
        Cursor cursorRetrieveLatLng = getContentResolver().query(
                ReportIncidentContentProvider.CONTENT_URI,
                null,
                null,
                null,
                null);

        if (cursorRetrieveLatLng != null) {
            cursorRetrieveLatLng.moveToLast();
            String currentLatString = cursorRetrieveLatLng.getString(cursorRetrieveLatLng.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_LOCATION_LAT));
            String currentLngString = cursorRetrieveLatLng.getString(cursorRetrieveLatLng.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_LOCATION_LNG));
            String dbPhoneString = cursorRetrieveLatLng.getString(cursorRetrieveLatLng.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_PHONE_NUMBER));
            String dbEmailString = cursorRetrieveLatLng.getString(cursorRetrieveLatLng.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_EMAIL));

            if (dbPhoneString.length() > 8) {
                csoContactInfo.setText("Contact Phonenumber: " + dbPhoneString);
                csoAssuranceHelp.setText("Safepal will contact you on the above phonenumber.");
                if (dbEmailString.length() > 8) {
                    csoContactInfo.setText("Contact Phonenumber: " + dbPhoneString + "\nContact Email: " + dbEmailString);
                    csoAssuranceHelp.setText("Safepal will contact you on the above phonenumber or email. ");
                }
            } else {
                csoContactInfo.setText("No Contacts provided. ");
                csoAssuranceHelp.setText("Since you did not provide a contact number, safepal service providers will not be able to contact you directly. But you can still walk in to any of the service providers below with your safepal number and they will attend to you. ");
            }

            CSODistanceTask csoDistanceTask = new CSODistanceTask();
            csoDistanceTask.execute(currentLatString, currentLngString);
            Log.d("cso lat and long", currentLatString + "- : -" + currentLngString);
        }
        cursorRetrieveLatLng.close();
    }

    private class CSODistanceTask extends AsyncTask<String, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: started");
            progressDialog = new ProgressDialog(CsoActivity.this);
            progressDialog.setMessage("Locating CSOs near you");
            progressDialog.setTitle("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.d(TAG, "doInBackground: started");
            String currentLat = params[0];
            String currentLong = params[1];

            Log.d(TAG, "getNearestCSOs: lat: " + currentLat + " long " + currentLong);
            beforeCsoList.add(new BeforeCsoInfo("Reproductive Health Uganda, Kamokya", 0.3374639, 32.58227210, "+256312207100"));
            beforeCsoList.add(new BeforeCsoInfo("Naguru Teenage Center , Bugolobi", 0.3209888, 32.6172358, "0800112222"));
            beforeCsoList.add(new BeforeCsoInfo("Fida, Kira Road", 0.348204, 32.596336, "+256414530848"));
            beforeCsoList.add(new BeforeCsoInfo("Action Aid , Sir Apollo Rd", 0.34204130858355, 32.5625579059124, "00000000000"));

            for (int i = 0; i < beforeCsoList.size(); i++) {
                if (currentLat.equalsIgnoreCase("0.0") || currentLong == "0.0") {
                    csosList.add(new TheCSO(beforeCsoList.get(i).getBefore_cso_name(), "We failed to locate you", beforeCsoList.get(i).getBefore_cso_phonenumber()));
                } else {
                    String disBetweenCso = String.format("%.1f", geographicalDistance(
                            Double.parseDouble(currentLat),
                            Double.parseDouble(currentLong),
                            beforeCsoList.get(i).getBefore_cso_lat(),
                            beforeCsoList.get(i).getBefore_cso_long()));

                    Log.d("location from db", currentLat + ":" + currentLong);
                    Log.d(TAG, "doInBackground: distance Between CSO " + disBetweenCso);
                    csosList.add(new TheCSO(beforeCsoList.get(i).getBefore_cso_name(), disBetweenCso + " Km away from you", beforeCsoList.get(i).getBefore_cso_phonenumber()));
                }
                Collections.sort(csosList, new Comparator<TheCSO>() {
                    @Override
                    public int compare(TheCSO o1, TheCSO o2) {
                        return o1.getCso_distance().compareTo(o2.getCso_distance());
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "onPostExecute: started");
            csosAdapter.notifyDataSetChanged();
            progressDialog.dismiss();
            super.onPostExecute(aVoid);
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

    public void csoGuide() {
        ViewTarget eTarget = new ViewTarget(R.id.cso_childhelpline_btn, this);
        ShowcaseView homeExitSv = new ShowcaseView.Builder(this)
                .withHoloShowcase()
                .setTarget(eTarget)
                .setContentTitle("Talk to someone")
                .setContentText("Click on the call button in order to talk to someone for help")
                .setStyle(R.style.ExitShowcaseTheme)
                .build();

    }


    private double geographicalDistance(double lat1, double lon1, double lat2, double lon2) {
        String directionUrl = String.format(Locale.getDefault(), "https://maps.googleapis.com/maps/api/directions/json?origin=%f,%f&destination=%f,%f&key=%s", lat1, lon1, lat2, lon2, getString(R.string.direction_api_key));
        Log.d(TAG, "geographicalDistance: started " + directionUrl);

        try {
            String jsonData = getDirectionFromGoogle(directionUrl);
            Log.d(TAG, "geographicalDistance: server response " + jsonData);
            JSONObject directionJsonObject = new JSONObject(jsonData);
            JSONArray routesJsonArray = directionJsonObject.getJSONArray("routes");

            JSONObject legsJsonObject = routesJsonArray.getJSONObject(0).getJSONArray("legs").getJSONObject(0);
            JSONObject distanceJsonObject = legsJsonObject.getJSONObject("distance");
            String distanceText = distanceJsonObject.getString("text");
            int distanceValue = distanceJsonObject.getInt("value");
            Log.d(TAG, "geographicalDistance: distance between " + distanceText + distanceValue);
            return distanceValue / 1000.0;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public String getDirectionFromGoogle(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:116")));
                }
                return;
            }
        }
    }
}

