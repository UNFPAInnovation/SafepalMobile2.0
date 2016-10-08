package com.unfpa.safepal.network;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Kisa on 10/8/2016.
 */

public class UidPullService extends IntentService {
    //used to write the system log from this class
    public static final String LOG_TAG = "UIDPullService";

    //defines and instantiates an object for handling status updates


    public UidPullService() {
        super("UIDPullService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        //gets a url to read from the incoming intent's data value
        String localUrlString =workIntent.getDataString();


    }
}
