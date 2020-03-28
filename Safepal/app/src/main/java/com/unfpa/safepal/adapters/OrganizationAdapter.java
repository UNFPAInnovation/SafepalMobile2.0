package com.unfpa.safepal.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unfpa.safepal.R;
import com.unfpa.safepal.provider.organizationtable.OrganizationtableCursor;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.ViewHolder> {

    private OrganizationtableCursor cursor;
    Activity activity;

    public OrganizationAdapter(Activity activity) {
        this.activity = activity;
    }

    public OrganizationAdapter(FragmentActivity activity, OrganizationtableCursor cursor) {
        this.activity = activity;
        this.cursor = cursor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView address;
        public TextView workingHours;
        public TextView website;
        public ImageView callButton;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.cso_name);
            address = v.findViewById(R.id.address);
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
        holder.address.setText(cursor.getAddress());
        holder.workingHours.setText(cursor.getOpenHour());
        holder.name.setText(cursor.getFacilityName());
        holder.website.setText(cursor.getLink());

        holder.callButton.setOnClickListener(v -> {
            Toast.makeText(activity, "Call CSO", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 0 : cursor.getCount();
    }
}