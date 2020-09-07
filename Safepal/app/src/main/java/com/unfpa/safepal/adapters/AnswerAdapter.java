package com.unfpa.safepal.adapters;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unfpa.safepal.R;
import com.unfpa.safepal.provider.answertable.AnswertableCursor;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private AnswertableCursor cursor;
    Activity activity;

    public AnswerAdapter(Activity activity) {
        this.activity = activity;
    }

    public AnswerAdapter(FragmentActivity activity, AnswertableCursor cursor) {
        this.activity = activity;
        this.cursor = cursor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        TextView yourAnswer;
        TextView correctAnswer;
        ImageView resultImage;

        public ViewHolder(View v) {
            super(v);
            question = v.findViewById(R.id.question);
            yourAnswer = v.findViewById(R.id.your_answer);
            correctAnswer = v.findViewById(R.id.correct_answer);
            resultImage = v.findViewById(R.id.result_image);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_result_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cursor.moveToPosition(position);

        holder.question.setText(String.format(activity.getString(R.string.number_question_string),
                cursor.getPositionNumber(), cursor.getContent()));
        holder.yourAnswer.setText(String.format(activity.getString(R.string.your_answer),
                cursor.getYourAnswer()));
        holder.correctAnswer.setText(String.format(activity.getString(R.string.correct_answer),
                cursor.getCorrectAnswer()));
        holder.resultImage.setImageResource(cursor.getYourAnswer().equals(cursor.getCorrectAnswer())
                ? R.drawable.ic_correct_48px : R.drawable.ic_wrong_48px);
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 0 : cursor.getCount();
    }
}