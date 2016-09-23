package com.unfpa.safepal.Referral;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.unfpa.safepal.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ReferralActivity2 extends AppCompatActivity {

    private static final long MIN_TIME_4_LOC_UPDATE = 5000;
    private static final long MIN_DIST_4_LOC_UPDATE = 10;
    private static final int PROGRESS_DIALOG_TIMEOUT = 10;//10 seconds
    Snackbar snackbarNoGPS;
    LocationManager locationManager;
    String TAG = "//..." + ReferralActivity2.class.getSimpleName();
    AlertDialog.Builder builderEnableLocation;
    boolean hasA_atealeOne_CSO_LookupBeenMade = false;
    ProgressDialog progressDialog;
    Thread threadCancelProgDialog;
    AlertDialog alertDialogLocationFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_referral);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpLocationStaff();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(new LocationListnerForPassiveProvider());
        locationManager.removeUpdates(new LocationListnerForNetwork());
        locationManager.removeUpdates(new LocationListnerForGPS());
    }


    /**
     * calle dwhen its confirmed that GPA is enabled
     */
    void startCSOLookUp() {

        startProgressBar();//also if it failes to get a location, it trues to get the last used one....

        registerLocationUpdatesUsingNetwork();
        registerLocationUpdatesUsingGPS();
        registerLocationUpdatesUsingPassiveProvider();

    }

    private void getLastKnownLocations() {
        getLastKnownNETWORKLocation();
        getLastKnownGPSLocation();// TODO: 23-Sep-16 uncomment
        getLastKnownPASSIELocation();
    }

    private void isLocationEnabled() {
        if (builderEnableLocation == null) builderEnableLocation = new AlertDialog.Builder(this);
        if ((locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) ||//GPS enbled
                (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {//Networl-locationenabled
            hasA_atealeOne_CSO_LookupBeenMade = true;
            startCSOLookUp();


        } else {//location not enabled
            builderEnableLocation = new AlertDialog.Builder(this);
            builderEnableLocation.setCancelable(false);
            builderEnableLocation.setTitle("Enable Location");
            builderEnableLocation.setMessage("We need to get your location. Go to settings and enable location.");
            builderEnableLocation.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            //builder.setPositiveButton()
            builderEnableLocation.show();
        }
    }

    private void startProgressBar() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("CSO Lookup");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Getting your location, please wait...");
        progressDialog.show();
        createProgressTimeOutThread();

    }

    private void createProgressTimeOutThread() {
        threadCancelProgDialog = new Thread(new Runnable() {
            int numberOfSeconds = 0;

            @Override
            public void run() {
                boolean triedLastQuoterCheckUp = false;
                while (numberOfSeconds <= PROGRESS_DIALOG_TIMEOUT) {
                    ++numberOfSeconds;
                    if (!progressDialog.isShowing()) {//stop looping just in case it was cancelled some where else
                        Log.d(TAG, "Progress bar no longer showing so stopping cancelling timer");
                        break;
                    }
                    Log.d(TAG, "progress doialog cancelling thread active....");
                    //if nearing the timeout, just try using the kast known values
                    if (((numberOfSeconds * 100) / PROGRESS_DIALOG_TIMEOUT) >= 80) {

                        if (triedLastQuoterCheckUp) {
                            Log.d(TAG, "Already tried getting last known location at towards end of progress-dialog timeout");
                        } else {
                            Log.d(TAG, "since we are nearing the timeout, we are trying to get the last known locations");
                            triedLastQuoterCheckUp = true;
                            getLastKnownLocations();
                        }
                    }

                    Util.sleepMilli(1000);//sleep 4 a sec
                }
                try {
                    progressDialog.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onFailedToGetLocation();
                    }
                });

                Log.d(TAG, "progress cancelling thread has finished...");
            }

        });
        threadCancelProgDialog.start();
    }

    private void onFailedToGetLocation() {
        AlertDialog.Builder builderFailedGetLocation = new AlertDialog.Builder(this);
        builderFailedGetLocation.setTitle("Ahm...");
        builderFailedGetLocation.setCancelable(false);
        builderFailedGetLocation.setMessage("We could not get your location at this time.");
        builderFailedGetLocation.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startProgressBar();
            }
        });
        builderFailedGetLocation.setNegativeButton("Background", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialogLocationFailed.dismiss();
            }
        });
        //builder.setPositiveButton()
        alertDialogLocationFailed = builderFailedGetLocation.show();

    }

    private void setUpLocationStaff() {
        //set up google location staff
        //GoogleA

        //set up defalt location staff
        // progressDialog.setMessage("preparing to get location...");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    private void getLastKnownNETWORKLocation() {
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // progressDialog.setMessage("Getting last known location...");
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                locationGot(false, location, LocationManager.NETWORK_PROVIDER);
            } else {
                Log.e(TAG, "cannot get last known network-location as getLastKnownNETWORKLocation() returned NULL");
            }
        } else {
            Log.e(TAG, "Not attempting to get last known location using NETWORK_PROVIDER coz provider is OFF");
        }

    }

    private void getLastKnownGPSLocation() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //  progressDialog.setMessage("Getting last known location...");
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                locationGot(false, location, LocationManager.GPS_PROVIDER);
            } else {
                Log.e(TAG, "cannot get last known GPS-location as getLastKnownGPSLocation() returned NULL");
            }
        } else {
            Log.e(TAG, "Not attempting to get last known location using GPS_PROVIDER coz provider is OFF");
        }
    }

    private void getLastKnownPASSIELocation() {
        if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            // progressDialog.setMessage("Getting last known location...");
            Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (location != null) {
                locationGot(false, location, LocationManager.PASSIVE_PROVIDER);
            } else {
                Log.e(TAG, "cannot get last known passive-location as getLastKnownPASSIELocation() returned NULL");
            }
        } else {
            Log.e(TAG, "Not attempting to get last known location using PASSIVE_PROVIDER coz provider is OFF");
        }

    }


    void locationGot(Boolean isFreshLocation, Location location, String provider) {
        //get city
        String city = null;
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() >= 1) {
                city = addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            // Log.e(TAG, "cannot get city of gotten location");
        }


        if (isFreshLocation) {
            Log.d(TAG, "Fresh Location got using :" + provider);
            Log.d(TAG, "Latitude:" + location.getLatitude());
            Log.d(TAG, "Longitude:" + location.getLongitude());
            Log.d(TAG, "City:" + city);

        } else {
            Log.d(TAG, "Last known location (not fresh) fetched using :" + provider);
            Log.d(TAG, "Latitude:" + location.getLatitude());
            Log.d(TAG, "Longitude:" + location.getLongitude());
            Log.d(TAG, "City:" + city);
        }

        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "cannot dismiss progress dialog: " + e.toString());
        }
        //Log.d(TAG, "Location got: isFreshLocation:" + isFreshLocation + " City:" + city);
    }

    private void registerLocationUpdatesUsingGPS() {
        //progressDialog.setMessage("Getting location using GPS...");
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {//GPS present
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //my staff below
                Log.e(TAG, "Insufficient permissions to get location using GPS");
                Toast.makeText(getBaseContext(), "Go to settings and grant the app permission to access location", Toast.LENGTH_LONG).show();
//                Snackbar.make(findViewById(R.id.coordinator), "Go to settings and grant the app permission to access location", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, new LocationListnerForGPS());
            Log.d(TAG, "Requested GPS location updates");
        } else {//no GPS
            Log.e(TAG, "Error: GPS is OFF");
            snackbarNoGPS = Snackbar.make(findViewById(R.id.coordinator), "Error: GPS is OFF", Snackbar.LENGTH_INDEFINITE);
            snackbarNoGPS.setAction("Turn On", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbarNoGPS.dismiss();
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            })
                    .show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //this if statament helpf to keep checking whenver the activity is resumed
        //as longer as the user has not yet enabled GPS setting
        if (hasA_atealeOne_CSO_LookupBeenMade) {//dont make another one from here. It shall be made from the dialo itself

        } else {
            isLocationEnabled();
        }

    }

    private void registerLocationUpdatesUsingNetwork() {
        //progressDialog.setMessage("Getting location using network...");
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {//Network present
            //LocationListener locationListener = new LocationListnerForNetwork();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //my staff below
                Log.e(TAG, "Insufficient permissions to get location using network");
                Toast.makeText(getBaseContext(), "Go to settings and grant the app permission to access location", Toast.LENGTH_LONG).show();
//                Snackbar.make(findViewById(R.id.coordinator), "Go to settings and grant the app permission to access location", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_4_LOC_UPDATE, MIN_DIST_4_LOC_UPDATE, new LocationListnerForNetwork());
            Log.d(TAG, "Requested NETWORK location updates");
        } else {//no GPS
            Log.e(TAG, "Error: Network-based is OFF");
            snackbarNoGPS = Snackbar.make(findViewById(R.id.coordinator), "Error: Network-based is OFF", Snackbar.LENGTH_INDEFINITE);
            snackbarNoGPS.setAction("Turn On", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbarNoGPS.dismiss();
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            })
                    .show();
        }

    }


    private void registerLocationUpdatesUsingPassiveProvider() {
        //progressDialog.setMessage("Getting location using network...");
        if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {//Network present
            //LocationListener locationListener = new LocationListnerForNetwork();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //my staff below
                Log.e(TAG, "Insufficient permissions to get location using network");
                Toast.makeText(getBaseContext(), "Go to settings and grant the app permission to access location", Toast.LENGTH_LONG).show();
//                Snackbar.make(findViewById(R.id.coordinator), "Go to settings and grant the app permission to access location", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, MIN_TIME_4_LOC_UPDATE, MIN_DIST_4_LOC_UPDATE, new LocationListnerForPassiveProvider());
            Log.d(TAG, "Requested NETWORK location updates");
        } else {//no GPS
            Log.e(TAG, "Error: Network-based is OFF");
            snackbarNoGPS = Snackbar.make(findViewById(R.id.coordinator), "Error: Network-based is OFF", Snackbar.LENGTH_INDEFINITE);
            snackbarNoGPS.setAction("Turn On", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbarNoGPS.dismiss();
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            })
                    .show();
        }

    }


    private class LocationListnerForGPS implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "LocationListnerForGPS received a location update");
            Snackbar.make(findViewById(R.id.coordinator), "Location Changed: Lat: " + location.getLatitude()
                    + " Long: " + location.getLongitude(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            //get city
            String city = null;
            Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses.size() >= 1) {
                    Snackbar.make(findViewById(R.id.coordinator), "City: " + addresses.get(0).getLocality(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            } catch (IOException e) {

            }
            locationGot(true, location, LocationManager.GPS_PROVIDER);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    private class LocationListnerForNetwork implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "LocationListnerForNetwork received a location update");
            Snackbar.make(findViewById(R.id.coordinator), "Location Changed: Lat: " + location.getLatitude()
                    + " Long: " + location.getLongitude(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            //get city
            String city = null;
            Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses.size() >= 1) {
                    Snackbar.make(findViewById(R.id.coordinator), "City: " + addresses.get(0).getLocality(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            } catch (IOException e) {

            }
            locationGot(true, location, LocationManager.NETWORK_PROVIDER);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    private class LocationListnerForPassiveProvider implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "LocationListnerForPassiveProvider received a location update");
            Snackbar.make(findViewById(R.id.coordinator), "Location Changed: Lat: " + location.getLatitude()
                    + " Long: " + location.getLongitude(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            //get city
            String city = null;
            Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses.size() >= 1) {
                    Snackbar.make(findViewById(R.id.coordinator), "City: " + addresses.get(0).getLocality(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            } catch (IOException e) {

            }
            locationGot(true, location, LocationManager.PASSIVE_PROVIDER);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }


}
