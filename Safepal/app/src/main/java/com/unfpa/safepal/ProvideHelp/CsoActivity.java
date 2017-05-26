package com.unfpa.safepal.ProvideHelp;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
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
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    private List<BeforeCsoInfo> beforeCsoList = new ArrayList<>();
    private List<TheCSO> csosList = new ArrayList<>();

    private RecyclerView csosRecyclerView;
    private CsoRvAdapter csosAdapter;

    //no internet connection
    private ProgressBar csoProgressBar;
    private LinearLayout csoNoInternetLL;
    private Button csoNoInternetButton;
    // TheCSOs json url
    private static final String URL_CSO_API = "http://52.43.152.73/api/location.php";
     //This is a temporary list of cso's hard coded here


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
                //Process.killProcess(Process.myPid());
                System.exit(1);
                //Log.d("distance", Double.toString(distanceCoordinates(0.3356299,32.5994707,0.3431490411038098,32.590298652648926,"K")));
                //Log.d("xxxxx", "3232");

            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "button next clicked");
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


    // Method pushes the data to json server suing volley
    private void getNearestCSOs(Float getLat, Float getLong) {


        beforeCsoList.add(new BeforeCsoInfo("Reproductive Health Uganda, Kamokya",0.3374639,32.58227210,"+256312207100"));
        beforeCsoList.add(new BeforeCsoInfo("Naguru Teenage Center , Bugolobi",0.3209888,32.6172358,"0800112222"));
        beforeCsoList.add(new BeforeCsoInfo("Fida, Kira Road",0.348204,32.596336,"+256414530848"));
        //beforeCsoList.add(new BeforeCsoInfo("Action Aid , Sir Apollo Rd",0.34204130858355,32.5625579059124,"00000000000"));

        for(int i =0 ; i<beforeCsoList.size(); i++){
            String disBetweenCso = String.format("%.2f", newDistanceBetween(getLat,getLong,beforeCsoList.get(i).getBefore_cso_lat(),beforeCsoList.get(i).getBefore_cso_long()));

            Log.d("all", disBetweenCso );
            csosList.add(new TheCSO(beforeCsoList.get(i).getBefore_cso_name(), disBetweenCso, beforeCsoList.get(i).getBefore_cso_phonenumber()));
        }
        Collections.sort(csosList, new Comparator<TheCSO>() {
            @Override
            public int compare(TheCSO o1, TheCSO o2) {
                return o1.getCso_distance().compareTo(o2.getCso_distance());
            }
        });

        csosAdapter.notifyDataSetChanged();






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
        cursorRetrieveLatLng.moveToLast();

        if (cursorRetrieveLatLng != null) {


            String dbLatString =  cursorRetrieveLatLng.getString(cursorRetrieveLatLng.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_LOCATION_LAT));
            String dbLngString =  cursorRetrieveLatLng.getString(cursorRetrieveLatLng.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_LOCATION_LNG));
            String dbPhoneString =  cursorRetrieveLatLng.getString(cursorRetrieveLatLng.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_PHONE_NUMBER));
            String dbEmailString =  cursorRetrieveLatLng.getString(cursorRetrieveLatLng.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_EMAIL));

            if(dbPhoneString.length()>8){
                csoContactInfo.setText("Contact Phonenumber: " + dbPhoneString);
                csoAssuranceHelp.setText("Safepal will contact you on the above phonenumber.");
                if(dbEmailString.length()>8){
                    csoContactInfo.setText("Contact Phonenumber: " + dbPhoneString+ "\nContact Email: " +dbEmailString);
                    csoAssuranceHelp.setText("Safepal will contact you on the above phonenumber or email. "); }
            }
            else {
                csoContactInfo.setText("No Contacts provided. " );
                csoAssuranceHelp.setText("Since you did not provide a contact number, safepal service providers will not be able to contact you directly. But you can still walk in to any of the service providers below with your safepal number and they will attend to you. ");

            }

            //finalCsoPreview(lat, lng);
            getNearestCSOs(Float.parseFloat(dbLatString), Float.parseFloat(dbLngString));
            Log.d("xx", dbLatString);
            Log.d("xx", dbLngString);




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






   /* private static double distanceCoordinates(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }
        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);

        return (dist);
    }*/


    private  float newDistanceBetween(double lat1, double lon1, double lat2, double lon2){
        float [] dist = new float[2];
        Location.distanceBetween(lat1, lon1, lat2, lon2, dist);

        return dist[1] * 0.000621371192f;
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }



}

