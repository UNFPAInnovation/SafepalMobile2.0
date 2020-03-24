package com.unfpa.safepal.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unfpa.safepal.R;
import com.unfpa.safepal.WatchVideoActivity;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    Activity activity;

    public VideoAdapter(Activity activity) {
        this.activity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView duration;
        public ImageView thumbnail;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            thumbnail = v.findViewById(R.id.thumbnail);
            duration = v.findViewById(R.id.duration);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, WatchVideoActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}