package com.unfpa.safepal.messages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;

import com.unfpa.safepal.R;

/**
 * Created by Kisa on 10/5/2016.
 */

public class EMessageDialogFragment extends DialogFragment {

    public static final EMessageDialogFragment newInstance(String title, String message, String buttonText) {
        EMessageDialogFragment adf =  new EMessageDialogFragment();
        Bundle bundle = new Bundle(3);
        bundle.putString("title", title);
        bundle.putString("message", message);
        bundle.putString("buttonText", buttonText);
        adf.setArguments(bundle);
        return adf;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String emTitle = getArguments().getString("title");
        String eMessage = getArguments().getString("message");
        String emButtonText = getArguments().getString("buttonText");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

         builder.setMessage(eMessage)
                .setTitle(Html.fromHtml("<center><font color='#01a89e'>"+emTitle+"</font></center>"))
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(emButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}