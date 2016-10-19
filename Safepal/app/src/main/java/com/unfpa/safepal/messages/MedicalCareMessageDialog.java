package com.unfpa.safepal.messages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import com.unfpa.safepal.R;

/**
 * Created by Kisa on 10/5/2016.
 */

public class MedicalCareMessageDialog extends DialogFragment {

    private TextView encourageMessage;

    public MedicalCareMessageDialog() {
    }

    public MedicalCareMessageDialog(TextView encourageMessage){
        this.encourageMessage = encourageMessage;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(encourageMessage.getText()).setTitle("Seek Medical Care")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(R.string.em_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}