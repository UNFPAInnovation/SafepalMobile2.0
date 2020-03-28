package com.unfpa.safepal.messages;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.unfpa.safepal.R;

import java.util.List;

/**
 * Created by Kisa on 10/5/2016.
 */

public class csoDialogFragment extends DialogFragment {

    private static final String TAG = csoDialogFragment.class.getSimpleName();

    public static final csoDialogFragment newInstance(String sCsoTitle, String sCsoMessage, String sCsoPhoneNumber, String positiveButton, String negativeButton) {
        csoDialogFragment adf = new csoDialogFragment();
        Bundle bundle = new Bundle(5);
        bundle.putString("sCsoTitle", sCsoTitle);
        bundle.putString("sCsoMessage", sCsoMessage);
        bundle.putString("sCsoPhoneNumber", sCsoPhoneNumber);
        bundle.putString("positiveButton", positiveButton);
        bundle.putString("negativeButton", negativeButton);

        adf.setArguments(bundle);

        return adf;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String sCsoTitle = getArguments().getString("sCsoTitle");
        String sCsoMessage = getArguments().getString("sCsoMessage");
        final String sCsoPhonenumber = getArguments().getString("sCsoPhoneNumber");

        String positiveButtonText = getArguments().getString("positiveButton");
        String negativeButtonText = getArguments().getString("negativeButton");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("This service provider is " + sCsoMessage + "\nContacts : " + sCsoPhonenumber)
                .setTitle(Html.fromHtml("<center><font color='#01a89e'>" + sCsoTitle + "</font></center>"))
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(positiveButtonText, (dialog, id) -> {
                    try {
                        callCSO(sCsoPhonenumber);
                    } catch (ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(), "SafePal can't make call now", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton(negativeButtonText, (dialogInterface, i) -> {

        });
        return builder.create();
    }

    private void callCSO(final String csoPhoneNumber) {
        Log.d(TAG, "callCSO: calling cso started");

        try {
            Dexter.withActivity(getActivity()).withPermissions(Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_COARSE_LOCATION)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                Log.d(TAG, "onPermissionsChecked: call permission granted");
                                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + csoPhoneNumber)));

                            } else if (report.isAnyPermissionPermanentlyDenied()) {
                                showSettingsDialog();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                            Toast.makeText(getActivity().getApplicationContext(), "Safepal your permission to call the CSO for help", Toast.LENGTH_LONG).show();
                        }
                    }).check();
        } catch (Exception e) {
            Log.e(TAG, "callCSO: call error ", e);
        }
    }

    private void showSettingsDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.needs_permission);
        builder.setMessage(R.string.settings_permission_message);
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}