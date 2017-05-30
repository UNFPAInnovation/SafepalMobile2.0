package com.unfpa.safepal.network;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unfpa.safepal.store.ReportIncidentContentProvider;
import com.unfpa.safepal.store.ReportIncidentTable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jjingo on 30/05/2017.
 */

public class UpdateContactService extends IntentService {

    public UpdateContactService() {
        super("UpdateContact");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String updateContactUrl = "https://api-safepal.herokuapp.com/index.php/api/v1/reports/addcontact";

        Log.d("UpdateContact ","Service Started");


        Cursor cursor =  getContentResolver().query(
                ReportIncidentContentProvider.CONTENT_URI,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            cursor.moveToLast();

            updateContactToServer(
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER)),
                    cursor.getString(cursor.getColumnIndex(ReportIncidentTable.COLUMN_REPORTER_PHONE_NUMBER)),
                    updateContactUrl);

        }
cursor.close();




    }

    public  void updateContactToServer(final String toServerCasenumber, final String toServerContact, final String updateContactUrl){

        getUpdateTokenFromServer(new VolleyCallback() {
            @Override
            public void onSuccessResponse(String tokenResponse)  {

                try{
                JSONObject tokenObject = new JSONObject(tokenResponse);
                final  String  serverReceivedToken = tokenObject.getString("token");
                    // This volley request sends a report to the server with the received token
                    StringRequest updateContactRequest = new StringRequest(Request.Method.POST, updateContactUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String updateContactReponse) {
                                       Log.d("kkkkk", updateContactReponse);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("Not Submitted", error.getMessage());
                                }
                            }){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> updateContact = new HashMap<String, String>();

                            updateContact.put("token", serverReceivedToken);
                            updateContact.put("caseNumber", toServerCasenumber);
                            updateContact.put("contact",toServerContact);
                            return updateContact;                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> updateContactHeaders = new HashMap<String, String>();
                            updateContactHeaders.put("userid", "C7rPaEAN9NpPGR8e9wz9bzw");
                            return  updateContactHeaders;
                        }

                    };


                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(updateContactRequest);



                }catch (Exception e){e.printStackTrace();}
            }
        });
    }

    //gets a new token from server
    public void getUpdateTokenFromServer(final VolleyCallback tokenCallback) {


        final String tokenUrl = " https://api-safepal.herokuapp.com/index.php/api/v1/auth/newtoken";

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
                        Log.d("Failed to get token", error.getMessage());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("userid", "C7rPaEAN9NpPGR8e9wz9bzw");

                return headers;
            }
        };
        //add request to queue

        MySingleton.getInstance(this).addToRequestQueue(tokenRequest);

    }

}
