package com.unfpa.safepal.network;

/**
 * Created by Kisa on 10/17/2016.
 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.unfpa.safepal.BuildConfig;

public class NetworkChangeReceiver extends BroadcastReceiver {

    final String URL_SAFEPAL_API = BuildConfig.BASE_API_URL + "/reports/addreport";
    // Intent for starting the IntentService for submitting a survivor case
    private Intent mServiceIntent;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String status = NetworkUtil.getConnectivityStatusString(context);

        if (status == "Wifi enabled" || status == "Mobile data enabled") {
            mServiceIntent = new Intent(context, AddReportService.class).setData(Uri.parse(URL_SAFEPAL_API));
            context.startService(mServiceIntent);
            Toast.makeText(context, "You have submitted your incident .", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "No Internet Connection. Your report will be submitted when your device is connected to the internet.", Toast.LENGTH_LONG).show();
        }
    }
}