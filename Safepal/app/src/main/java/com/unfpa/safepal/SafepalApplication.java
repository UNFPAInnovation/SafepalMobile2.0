package com.unfpa.safepal;

import android.app.Application;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;

import timber.log.Timber;

public class SafepalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }

//    final OkHttpClient client = new OkHttpClient.Builder()
//            .protocols(Collections.singletonList(Protocol.HTTP_1_1))
//            .build();
//
//    final Picasso picasso = new Picasso.Builder(getApplicationContext())
//            .downloader(new OkHttp3Downloader(client))
//            .build();
}
