package com.unfpa.safepal.network;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kisa on 10/8/2016.
 */

public class UIDPullService extends IntentService {

    // Used to write to the system log from this class.
    public static final String LOG_TAG = "UIDPullService";


    /**
    /**
     * An IntentService must always have a constructor that calls the super constructor. The
     * string supplied to the super constructor is used to give a name to the IntentService's
     * background thread.
     */
    public UIDPullService() {
        super("UIDPullService");
    }

    /**
     * In an IntentService, onHandleIntent is run on a background thread.  As it
     * runs, it broadcasts its current status using the LocalBroadcastManager.
     * @param workIntent The Intent that starts the IntentService. This Intent contains the
     * URL of the web site from which the RSS parser gets data.
     */

    @Override
    protected void onHandleIntent(Intent workIntent) {

        // Gets a URL to read from the incoming Intent's "data" value
        String localUrlString = workIntent.getDataString();

        // A cursor that's local to this method.

        /*
         * A block that tries to connect to the Picasa featured picture URL passed as the "data"
         * value in the incoming Intent. The block throws exceptions (see the end of the block).
         */
        Cursor cursor =  getContentResolver().query(
                ReportIncidentContentProvider.CONTENT_URI,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToLast();

            populateOnline(
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_SURVIVOR_GENDER)),
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_SURVIVOR_DATE_OF_BIRTH)),
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_INCIDENT_TYPE)),
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_INCIDENT_LOCATION)),
                    "WTBC",
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_INCIDENT_STORY)),
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_REPORTED_BY)), localUrlString,
                    new VolleyCallback() {



                        @Override
                        public void onSuccessResponse(String result) {
                            try {
                                JSONObject response = new JSONObject(result);
                                Log.d("",response.getString("unique_code"));
                                Cursor cursorUpdate =  getContentResolver().query(
                                        ReportIncidentContentProvider.CONTENT_URI,
                                        null,
                                        null,
                                        null,
                                        null);
                                ContentValues dataValues = new ContentValues();
                                dataValues.put(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER, response.getString("unique_code"));
                                Toast.makeText(getBaseContext(), " The SafePal No." + response.getString("unique_code"),Toast.LENGTH_SHORT).show();

                                if (cursorUpdate != null) {
                                    cursorUpdate.moveToLast();

                                    // Update reported incident
                                    getContentResolver().update(ReportIncidentContentProvider.CONTENT_URI, dataValues, ReportIncidentTable.COLUMN_ID + "=" +
                                            cursorUpdate.getString(cursorUpdate.getColumnIndex(
                                                    ReportIncidentTable.COLUMN_ID)), null);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                    }

            );
            cursor.close();
        }

    }

    // Method pushes the data to json server suing volley
    private void populateOnline(final String toServerSGender,
                                final String toServerSDOB,
                                final String toServerIType,
                                final String toServerILocation,
                                final String toServerStatus,
                                final String toServerIDescription,
                                final String toServerReportedBy, String URL_SAFEPAL_API, final VolleyCallback callback ){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAFEPAL_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccessResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("survivor_gender",toServerSGender);
                params.put("survivor_date_of_birth",toServerSDOB);

                params.put("incident_type", toServerIType);
                params.put("incident_location",toServerILocation);

                params.put("status",toServerStatus);
                params.put("incident_description", toServerIDescription);
                params.put("reported_by", toServerReportedBy);
                params.put("reporter_lat", "tmp_null");
                params.put("reporter_long","tmp_null");
                params.put("survivor_contact_phone_number", "tmp_null");
                params.put("survivor_contact_email","tmp_null");
                return params;
            }

        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


}
