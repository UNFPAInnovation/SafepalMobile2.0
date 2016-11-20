package com.unfpa.safepal.network;

/**
 * Created by Kisa on 10/17/2016.
 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver{

    //Volley requests
    final String URL_SAFEPAL_API = "http://52.43.152.73/api/addselfreport.php";
    // Intent for starting the IntentService that downloads the Picasa featured picture RSS feed
    private Intent mServiceIntent;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String status = NetworkUtil.getConnectivityStatusString(context);

        if(status=="Wifi enabled"||status=="Mobile data enabled"){
            mServiceIntent = new Intent(context, UIDPullService.class).setData(Uri.parse(URL_SAFEPAL_API));
            context.startService(mServiceIntent);

            Toast.makeText(context, " Your report has been successfully received.", Toast.LENGTH_SHORT).show();

        }
        else {

            Toast.makeText(context, "No Internet Connection. Your report will be submitted once you are connected", Toast.LENGTH_LONG).show();
        }
    }
}