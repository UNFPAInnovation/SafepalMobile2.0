package com.unfpa.safepal.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Jjingo on 30/05/2017.
 */

public class UpdateContactBroadReceiver extends BroadcastReceiver {
    //Update contact service
    private Intent updateContactIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
        updateContactIntent = new Intent(context, UpdateContactService.class);

        context.startService(updateContactIntent);


        Log.d("success", "update contact broadcast");
    }
}
