package com.unfpa.safepal.Utils;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.unfpa.safepal.R;

/**
 * Created by Brian on 12-Jan-17.
 */

public class General {

    public static void showDisclaimerDialog(AppCompatActivity appCompatActivity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(appCompatActivity);
        builder.setCancelable(false);
        builder.setTitle(appCompatActivity.getString(R.string.disclaimer));
        builder.setMessage(appCompatActivity.getString(R.string.disclaimer_detail));
        builder.setNegativeButton(appCompatActivity.getString(R.string.close_dialog), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
//        builder.setPositiveButton(getString(R.string.go_back), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                setQuickDelayedFinishing();
//            }
//        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}
