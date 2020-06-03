package com.unfpa.safepal.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.unfpa.safepal.R;
import com.unfpa.safepal.provider.faqtable.FaqtableCursor;
import com.unfpa.safepal.retrofit.APIClient;
import com.unfpa.safepal.retrofit.APIInterface;
import com.unfpa.safepal.retrofitmodels.faqratings.FaqRating;
import com.unfpa.safepal.retrofitmodels.faqs.Faq;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> {

    private FaqtableCursor cursor;
    Activity activity;
    private ItemClickListener mClickListener;
    private int count = 0;
    APIInterface apiInterface;

    public FaqAdapter(Activity activity) {
        this.activity = activity;
    }

    public FaqAdapter(FragmentActivity activity, FaqtableCursor cursor) {
        this.activity = activity;
        this.cursor = cursor;
        this.count = cursor.getCount();
        this.apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        TextView answerTextView;
        TextView userTextView;
        ImageView expanderButton;
        ConstraintLayout constraintLayout;
        RatingBar ratingBar;

        public ViewHolder(View v) {
            super(v);
            questionTextView = v.findViewById(R.id.questionTextView);
            answerTextView = v.findViewById(R.id.answerTextView);
            constraintLayout = v.findViewById(R.id.constraintLayout);
            expanderButton = v.findViewById(R.id.expander_button);
            userTextView = v.findViewById(R.id.usefulTextView);
            ratingBar = v.findViewById(R.id.ratingbar);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faq_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cursor.moveToPosition(position);

        holder.questionTextView.setText(cursor.getQuestion());
        holder.answerTextView.setText(cursor.getAnswer());
        holder.answerTextView.setVisibility(View.GONE);
        holder.constraintLayout.setBackgroundResource(R.color.colorWhite);
        holder.expanderButton.setBackground(activity.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_48px));

        holder.userTextView.setVisibility(View.GONE);
        holder.ratingBar.setNumStars(5);
        holder.ratingBar.setVisibility(View.GONE);

        holder.questionTextView.setOnClickListener(v -> {
            toggle_view_details(holder);
        });

        holder.expanderButton.setOnClickListener(v -> {
            toggle_view_details(holder);
        });

    }

    private void toggle_view_details(@NonNull ViewHolder holder) {
        if (holder.answerTextView.getVisibility() == View.VISIBLE) {
            holder.answerTextView.setVisibility(View.GONE);
            holder.ratingBar.setVisibility(View.GONE);
            holder.userTextView.setVisibility(View.GONE);
            holder.constraintLayout.setBackgroundResource(R.color.colorWhite);
            holder.expanderButton.setBackground(activity.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_48px));
        } else {
            holder.answerTextView.setVisibility(View.VISIBLE);
            holder.ratingBar.setVisibility(View.VISIBLE);
            holder.userTextView.setVisibility(View.VISIBLE);
            holder.ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
                Call<FaqRating> call = apiInterface.postFaqRatings(new FaqRating((int) rating, cursor.getServerid()));

                call.enqueue(new Callback<FaqRating>() {
                    @Override
                    public void onResponse(Call<FaqRating> call, Response<FaqRating> response) {
                        Timber.d("saved faq rating");
                    }

                    @Override
                    public void onFailure(Call<FaqRating> call, Throwable t) {
                        Timber.d("failed to save faq rating");
                    }
                });

                Toast.makeText(activity, "Thank you. Your feedback has been submitted", Toast.LENGTH_SHORT).show();
            });
            holder.constraintLayout.setBackgroundResource(R.color.colorAccentLight);
            holder.expanderButton.setBackground(activity.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_48px));
        }

        if (mClickListener != null)
            mClickListener.onItemClick(holder.questionTextView, holder.getAdapterPosition(), count);
    }

    // allows clicks events to be caught. activity registers here as the listener
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, int count);
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 0 : count;
    }
}