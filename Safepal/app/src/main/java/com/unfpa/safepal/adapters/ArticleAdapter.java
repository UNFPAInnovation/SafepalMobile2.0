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

import com.squareup.picasso.Picasso;
import com.unfpa.safepal.R;
import com.unfpa.safepal.ReadArticleActivity;
import com.unfpa.safepal.provider.articletable.ArticletableCursor;
import static com.unfpa.safepal.provider.videotable.VideotableColumns.TITLE;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private ArticletableCursor cursor;
    Activity activity;

    public ArticleAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArticleAdapter(FragmentActivity activity, ArticletableCursor cursor) {
        this.activity = activity;
        this.cursor = cursor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        // can be in two states; start reading or x% completed
        public TextView completionRate;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            thumbnail = v.findViewById(R.id.thumbnail);
            completionRate = v.findViewById(R.id.completion_rate);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cursor.moveToPosition(position);

        holder.title.setText(cursor.getTitle());

        Picasso.get()
                .load(cursor.getThumbnail())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.thumbnail);

        try {
            int completionRateValue = cursor.getCompletionRate();
            if (completionRateValue <= 0 || completionRateValue == 100) {
                holder.completionRate.setText("Start reading");
            } else {
                holder.completionRate.setText(String.valueOf(cursor.getCompletionRate()) + "% completed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            holder.completionRate.setText("Start reading");
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, ReadArticleActivity.class)
                    .putExtra(TITLE, holder.title.getText().toString());
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 0 : cursor.getCount();
    }
}