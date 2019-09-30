package com.unfpa.safepal.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.unfpa.safepal.R;

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
}
