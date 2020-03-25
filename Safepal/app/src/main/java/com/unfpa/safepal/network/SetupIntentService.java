package com.unfpa.safepal.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.unfpa.safepal.provider.videotable.VideotableColumns;
import com.unfpa.safepal.provider.videotable.VideotableContentValues;
import com.unfpa.safepal.retrofit.APIClient;
import com.unfpa.safepal.retrofit.APIInterface;
import com.unfpa.safepal.retrofitmodels.videos.Result;
import com.unfpa.safepal.retrofitmodels.videos.Videos;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Downloads videos, articles and quiz
 */
public class SetupIntentService extends IntentService {
    private static final String TAG = SetupIntentService.class.getSimpleName();
    private APIInterface apiInterface;

    public SetupIntentService() {
        super(SetupIntentService.class.getSimpleName());
    }

    public SetupIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: started");
        Timber.d("onHandleIntent: started service");
        apiInterface = APIClient.getClient().create(APIInterface.class);

        boolean isConnectedToInternet = isConnectedToInternet(this);
        Timber.d("is connected %s", isConnectedToInternet);

        if (isConnectedToInternet) {
//            try {
                loadVideos();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    private void loadVideos() {
        Timber.d("get videos list started");
        Call<Videos> call = apiInterface.getVideos();

        call.enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                Timber.d("onResponse() -> %s", response.code());
//                try {
                    if (response.code() == 200) {
                        saveVideos(response.body());
                    } else {
                        Timber.e("Failed to get videos");
                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {
                Timber.e("onFailure() -> %s", t.getMessage());
            }
        });
    }

    private void saveVideos(Videos videos) {
        Timber.d("INSERT: video starting");
        if (videos == null)
            throw new NullPointerException("Videos not found");
        List<Result> videosList = videos.getResults();

        long deleted = 0;
        try {
            if (videosList.size() > 1)
                deleted = getContentResolver().delete(VideotableColumns.CONTENT_URI, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Timber.d("deleted data count %s", deleted);

        for (Result video : videosList) {
            VideotableContentValues values = new VideotableContentValues();
            Timber.d("Video data " + video.getTitle() + video.getCreatedAt() + video.getUrl());
            values.putTitle(video.getTitle());
            values.putCategory(video.getCategory().getName());
            values.putServerid(video.getId());
            values.putDescription(video.getDescription());
            values.putThumbnail(video.getThumbnail());
            values.putCreatedAt(video.getCreatedAt());
            values.putRating(video.getRating());
            // insert 0 completion since user has not yet watched video
            values.putCompletionRate(0);
            final Uri uri = values.insert(getContentResolver());
            Timber.d("saved video %s", uri);
        }
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        try {
                            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://clients3.google.com").openConnection());
                            urlc.setRequestProperty("User-Agent", "Test");
                            urlc.setRequestProperty("Connection", "close");
                            urlc.setConnectTimeout(2000);
                            urlc.setReadTimeout(2000);
                            urlc.connect();
                            boolean isConnected = (urlc.getResponseCode() == 200);
                            urlc.disconnect();
                            // making a network request on 2G network will not wield proper results
                            // instead depend on the CONNECTED state of the network
                            if (!isConnected && getNetworkClass(context) == 2) {
                                isConnected = info[i].getState() == NetworkInfo.State.CONNECTED;
                            }
                            return isConnected;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
        }
        return false;
    }

    public static int getNetworkClass(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return 2;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return 3;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return 4;
            default:
                return 3;
        }
    }
}
