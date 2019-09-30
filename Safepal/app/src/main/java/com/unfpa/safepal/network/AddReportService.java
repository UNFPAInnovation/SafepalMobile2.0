package com.unfpa.safepal.network;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.unfpa.safepal.Utils.Constants.BASE_API_URL;
import static com.unfpa.safepal.Utils.Constants.SOCKET_TIMEOUT;

/**
 * Created by Kisa on 10/8/2016.
 */

public class AddReportService extends IntentService {

    // Used to write to the system log from this class.
    public static final String LOG_TAG = "AddReportService";
    private static final String TAG = AddReportService.class.getSimpleName();


    public AddReportService() {
        super("AddReportService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {

        // Gets a URL to read from the incoming Intent's "data" value
        String localUrlString = workIntent.getDataString();
        Log.d(TAG, "onHandleIntent: localUrlString" + localUrlString);

        /*
         * A block that tries to connect to the Picasa featured picture URL passed as the "data"
         * value in the incoming Intent. The block throws exceptions (see the end of the block).
         */
        Cursor cursor = getContentResolver().query(
                ReportIncidentContentProvider.CONTENT_URI,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToLast();

            sendReportToServer(
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_INCIDENT_LOCATION)),
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_SURVIVOR_GENDER)),
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_SURVIVOR_DATE_OF_BIRTH)),
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_INCIDENT_TYPE)),
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_INCIDENT_STORY)),
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_REPORTED_BY)),
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_LOCATION_LAT)),
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_LOCATION_LNG)),
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_PHONE_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_DISABILITY)),
                    localUrlString,

                    new VolleyCallback() {

                        @Override
                        public void onSuccessResponse(String result) {
                            try {
                                JSONObject response = new JSONObject(result);
                                Cursor cursorUpdate = getContentResolver().query(
                                        ReportIncidentContentProvider.CONTENT_URI,
                                        null,
                                        null,
                                        null,
                                        null);
                                ContentValues dataValues = new ContentValues();
                                dataValues.put(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER, response.getString("casenumber"));
                                Toast.makeText(getBaseContext(), " The SafePal No." + response.getString("casenumber"), Toast.LENGTH_SHORT).show();

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

    public void sendReportToServer(
            final String toServerIncidentLocation,
            final String toServerSGender,
            final String toServerSDOB,
            final String toServerIType,
            final String toServerIDescription,
            final String toServerReportedBy,
            final String toServerReporterLat,
            final String toServerReportedLng,
            final String toServerReporterPhonenumber,
            final String toServerDisability,
            final String addReportUrl, final VolleyCallback reportCallback) {

        final String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


        getTokenFromServer(new VolleyCallback() {
            @Override
            public void onSuccessResponse(String tokenResponse) {
                try {
                    JSONObject tokenObject = new JSONObject(tokenResponse);
                    final String serverReceivedToken = tokenObject.getString("token");

                    // This volley request sends a report to the server with the received token
                    StringRequest addReportRequest = new StringRequest(Request.Method.POST, addReportUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String addReportReponse) {
                                    Log.d("Submitted", "onResponse: Success " + addReportReponse);
                                    reportCallback.onSuccessResponse(addReportReponse);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //todo check this error with server
                                    Log.e("Not Submitted", "onErrorResponse: ", error);
//                                    Log.d("Not Submitted", error.getMessage());
                                }
                            }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> addReport = new HashMap<String, String>();

                            String age = toServerSDOB.split(" ")[0];
                            Log.d(TAG, "getParams: age value " + age);

                            addReport.put("token", serverReceivedToken);
                            addReport.put("type", toServerIType);
                            addReport.put("gender", toServerSGender);
                            addReport.put("reporter", toServerReportedBy);
                            addReport.put("incident_date", "null");
                            addReport.put("perpetuator", "Unknown");
                            addReport.put("age", age);
                            addReport.put("contact", toServerReporterPhonenumber);
                            addReport.put("latitude", toServerReporterLat);
                            addReport.put("longitude", toServerReportedLng);
                            addReport.put("location", toServerIncidentLocation);
                            addReport.put("details", toServerIDescription);
                            addReport.put("report_source", "android user");
                            addReport.put("reportDate", currentDate);
                            addReport.put("disability", toServerDisability);

                            Log.d(TAG, "getParams: param values " + addReport.toString());
                            return addReport;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> addReportHeaders = new HashMap<String, String>();
                            addReportHeaders.put("userid", "C7rPaEAN9NpPGR8e9wz9bzw");
                            return addReportHeaders;
                        }

                    };

                    // increases the connection timeout
                    addReportRequest.setRetryPolicy(new DefaultRetryPolicy(
                            SOCKET_TIMEOUT,
                            0,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(addReportRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }


    private void getTokenFromServer(final VolleyCallback tokenCallback) {


        try {
            String tokenUrl = BASE_API_URL + "/auth/newtoken";

            // This volley request gets a token from the server
            StringRequest tokenRequest = new StringRequest(Request.Method.GET, tokenUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String tokenResponse) {
                            tokenCallback.onSuccessResponse(tokenResponse);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error == null || error.networkResponse == null) {
                                Log.e(LOG_TAG, "onErrorResponse: erro is null");
                                return;
                            }

                            try {
                                String body;
                                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                                Log.d(LOG_TAG, "onErrorResponse: " + statusCode);
                                body = new String(error.networkResponse.data, "UTF-8");
                                Log.d(LOG_TAG, "onErrorResponse: " + body);
                            } catch (UnsupportedEncodingException e) {
                                Log.e(LOG_TAG, "onErrorResponse: ", e);
                            }

                            Log.d("Failed to get token", error.getMessage());
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("userid", "C7rPaEAN9NpPGR8e9wz9bzw");

                    return headers;
                }
            };
            //add request to queue

            MySingleton.getInstance(this).addToRequestQueue(tokenRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "getTokenFromServer: ", e);
        }

    }

}
