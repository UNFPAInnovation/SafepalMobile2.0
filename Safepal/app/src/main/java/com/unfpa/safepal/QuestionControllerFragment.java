package com.unfpa.safepal;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.fragment.NavHostFragment;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import timber.log.Timber;

public class QuestionControllerFragment extends Fragment implements View.OnClickListener {

    Button yesButton, noButton, sometimesButton;
    TextView questionNumber, questionText;
    float progressCount = 10f;
    private CircularProgressBar circularProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question_controller, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        yesButton = view.findViewById(R.id.yes_button);
        noButton = view.findViewById(R.id.no_button);
        sometimesButton = view.findViewById(R.id.sometimes_button);
        questionNumber = view.findViewById(R.id.question_number);
        questionText = view.findViewById(R.id.question_text);

        circularProgressBar = view.findViewById(R.id.progress_circular);
        circularProgressBar.setProgress(progressCount);
        circularProgressBar.setProgressMax(100f);
        circularProgressBar.setProgressBarColor(getResources().getColor(R.color.colorAccent));
        circularProgressBar.setBackgroundProgressBarColor(getResources().getColor(R.color.colorGrey));
        circularProgressBar.setProgressBarWidth(5f);
        circularProgressBar.setBackgroundProgressBarWidth(5f);
        circularProgressBar.setStartAngle(45f);
        circularProgressBar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_LEFT);

        yesButton.setOnClickListener(QuestionControllerFragment.this);
        noButton.setOnClickListener(QuestionControllerFragment.this);
        sometimesButton.setOnClickListener(QuestionControllerFragment.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.yes_button:
                Timber.d("Clicked yes");
                break;
            case R.id.no_button:
                Timber.d("Clicked no");
                break;
            case R.id.sometimes_button:
                Timber.d("Clicked sometimes");
                break;
            default:
                Timber.d("clicked nothing");
                break;
        }

        progressCount += progressCount;
        circularProgressBar.setProgress(progressCount);

        Toast.makeText(view.getContext(), "clicked", Toast.LENGTH_SHORT).show();
//        NavHostFragment.findNavController(QuestionControllerFragment.this)
//                .navigate(R.id.action_QuestionControllerFragment_to_QuizResultFragment);
    }
}
