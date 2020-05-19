package com.unfpa.safepal.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unfpa.safepal.ProvideHelp.CsoActivity;
import com.unfpa.safepal.R;
import com.unfpa.safepal.provider.organizationtable.OrganizationtableCursor;

import timber.log.Timber;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.ViewHolder>
        implements ActivityCompat.OnRequestPermissionsResultCallback{

    private OrganizationtableCursor cursor;
    Activity activity;
    private static final int REQUEST_PHONE_CALL = 325;
    private String phoneNumber;

    public OrganizationAdapter(Activity activity) {
        this.activity = activity;
    }

    public OrganizationAdapter(FragmentActivity activity, OrganizationtableCursor cursor) {
        this.activity = activity;
        this.cursor = cursor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView address;
        TextView workingHours;
        TextView website;
        ImageView callButton;
        TextView phoneNumber;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.cso_name);
            address = v.findViewById(R.id.address);
            phoneNumber = v.findViewById(R.id.phone_number);
            workingHours = v.findViewById(R.id.working_hours);
            website = v.findViewById(R.id.website);
            callButton = v.findViewById(R.id.call);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cso_directory_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cursor.moveToPosition(position);

        holder.name.setText(cursor.getFacilityName());
        holder.phoneNumber.setText(cursor.getPhoneNumber());
        holder.address.setText(cursor.getAddress());
        holder.workingHours.setText(String.format(activity.getString(R.string.working_hours),
                cursor.getOpenHour(),
                cursor.getCloseHour()));
        holder.name.setText(cursor.getFacilityName());
        holder.website.setText(cursor.getLink());

        holder.callButton.setOnClickListener(v -> {
            phoneNumber = holder.phoneNumber.getText().toString();
            try {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    activity.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
                }
            } catch (ActivityNotFoundException e) {
                Timber.e(e);
                activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 0 : cursor.getCount();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    activity.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
                }
                return;
            }
        }
    }
}