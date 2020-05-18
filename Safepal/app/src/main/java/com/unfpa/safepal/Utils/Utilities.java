package com.unfpa.safepal.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.unfpa.safepal.R;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class Utilities {
    public static String formParameterPref = "FORM_PARAMETER_PREF";

    public static boolean getFormParameter(Context context, String formParameterName) {
        SharedPreferences prefs = context.getSharedPreferences(formParameterPref, MODE_PRIVATE);
        boolean formParameterValue = prefs.getBoolean(formParameterName, false);
        return formParameterValue;
    }


    public static void saveFormParameter(Context context, boolean isSomeOneElse, String wsghRelationshipSpinner) {
        SharedPreferences prefs = context.getSharedPreferences(formParameterPref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isSomeOneElse", isSomeOneElse);
        editor.putString("wsghRelationshipSpinner", wsghRelationshipSpinner);
        editor.apply();
    }


    public static void saveBackButtonPressed(Context context, String formParameterName, boolean formParameterValue) {
        SharedPreferences prefs = context.getSharedPreferences(formParameterPref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(formParameterName, formParameterValue);
        editor.apply();
    }

    public static boolean validateValue(String value, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static String getMacAddress(Context context) {
        WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        return !TextUtils.isEmpty(address) ? address :
                String.format("10:e4:c%d:f%d:g8:e%d",
                        getRandomInt(1, 9),
                        getRandomInt(1, 9),
                        getRandomInt(1, 9));
    }

    public static int getRandomInt(int low, int high) {
        return new Random().nextInt(high-low) + low;
    }

    public static String getRandomString() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
