package com.unfpa.safepal.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unfpa.safepal.R;
import com.unfpa.safepal.WatchVideoActivity;
import com.unfpa.safepal.provider.videotable.VideotableCursor;
import com.unfpa.safepal.provider.videotable.VideotableSelection;

import java.util.Locale;

import static com.unfpa.safepal.provider.videotable.VideotableColumns.TITLE;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private VideotableCursor cursor;
    Activity activity;

    public VideoAdapter(Activity activity) {
        this.activity = activity;
    }

    public VideoAdapter(FragmentActivity activity, VideotableCursor cursor) {
        this.activity = activity;
        this.cursor = cursor;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            cursor.moveToPosition(position);

            holder.title.setText(cursor.getTitle());

            Picasso.get()
                    .load(cursor.getThumbnail())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.thumbnail);

            try {
                holder.duration.setText(String.format(Locale.ENGLISH,"%d mins", cursor.getDuration()));
            } catch (Exception e) {
                e.printStackTrace();
                holder.duration.setText("1 mins");
            }

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(activity, WatchVideoActivity.class)
                        .putExtra(TITLE, holder.title.getText().toString());
                activity.startActivity(intent);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filter(String text) {
        if (!text.isEmpty()) {
            text = text.toLowerCase();
            swapCursor(queryVideoTable(text));
        }
    }

    public VideotableCursor swapCursor(VideotableCursor cursor) {
        if (this.cursor == cursor) {
            return null;
        }
        VideotableCursor oldCursor = this.cursor;
        this.cursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    private VideotableCursor queryVideoTable(String text) {
        VideotableSelection selection = new VideotableSelection();
        selection.titleContains(text);
        return selection.query(activity.getContentResolver());
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 0 : cursor.getCount();
    }
}