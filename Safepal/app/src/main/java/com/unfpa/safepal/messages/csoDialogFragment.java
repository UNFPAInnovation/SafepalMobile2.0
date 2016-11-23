package com.unfpa.safepal.messages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import com.unfpa.safepal.R;

/**
 * Created by Kisa on 10/5/2016.
 */

public class csoDialogFragment extends DialogFragment {

    public static final csoDialogFragment newInstance(String sCsoTitle, String sCsoMessage,String sCsoPhoneNumber, String positiveButton, String negativeButton) {
        csoDialogFragment adf =  new csoDialogFragment();
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

         builder.setMessage("This service provider is "+sCsoMessage+"\nContact : "+ sCsoPhonenumber)
                .setTitle(Html.fromHtml("<center><font color='#01a89e'>"+sCsoTitle+"</font></center>"))
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // call the cso

                        try {
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sCsoPhonenumber)));}
                        catch (ActivityNotFoundException ex) {
                            Toast.makeText(getActivity(), "SafePal can't make call now", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {

             }
         });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}