package com.unfpa.safepal;

import android.database.CursorIndexOutOfBoundsException;
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
import com.unfpa.safepal.provider.articletable.ArticletableCursor;
import com.unfpa.safepal.provider.articletable.ArticletableSelection;
import com.unfpa.safepal.provider.questiontable.QuestiontableCursor;
import com.unfpa.safepal.provider.questiontable.QuestiontableSelection;
import com.unfpa.safepal.provider.quiztable.QuiztableCursor;
import com.unfpa.safepal.provider.quiztable.QuiztableSelection;

import timber.log.Timber;

import static com.unfpa.safepal.provider.videotable.VideotableColumns.TITLE;

public class QuestionControllerFragment extends Fragment implements View.OnClickListener {

    Button yesButton, noButton, sometimesButton;
    TextView questionNumber, questionText;
    float progressCount = 10f;
    private CircularProgressBar circularProgressBar;
    private QuestiontableCursor questiontableCursor;
    private String correctAnswer = "YES";
    private int correctAnswerCount = 0;

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
        circularProgressBar.setProgressBarColor(getResources().getColor(R.color.colorAccent));
        circularProgressBar.setBackgroundProgressBarColor(getResources().getColor(R.color.colorGrey));
        circularProgressBar.setProgressBarWidth(5f);
        circularProgressBar.setBackgroundProgressBarWidth(5f);

        yesButton.setOnClickListener(QuestionControllerFragment.this);
        noButton.setOnClickListener(QuestionControllerFragment.this);
        sometimesButton.setOnClickListener(QuestionControllerFragment.this);

        String articleTitle = getActivity().getIntent().getExtras().getString(TITLE);

        try {
            ArticletableCursor articletableCursor = new ArticletableSelection().title(articleTitle)
                    .query(view.getContext());
            articletableCursor.moveToFirst();

            QuiztableCursor quizCursor = new QuiztableSelection().article(articletableCursor
                    .getServerid()).query(view.getContext());
            quizCursor.moveToFirst();

            questiontableCursor = new QuestiontableSelection().quiz(quizCursor.getServerid())
                    .orderByPositionNumber().query(view.getContext());

            // set a limit for the progress bar
            int numberOfQuestions = questiontableCursor.getCount();
            circularProgressBar.setProgressMax(numberOfQuestions * 10f);

            questiontableCursor.moveToFirst();
            moveToNextQuestion();
        } catch (Exception e) {
            Timber.e(e);
            Toast.makeText(view.getContext(), "Sorry, there is no quiz for this article", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        String userAnswer;
        switch (view.getId()) {
            case R.id.yes_button:
                userAnswer = "YES";
                break;
            case R.id.no_button:
                userAnswer = "NO";
                break;
            default:
                userAnswer = "SOMETIMES";
                break;
        }

        if (userAnswer.equals(correctAnswer)) {
            Toast.makeText(view.getContext(), "correct answer", Toast.LENGTH_SHORT).show();
            correctAnswerCount++;
        } else {
            Toast.makeText(view.getContext(), "wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (isLastQuestion()) {
            navigateToResultScreen();
        } else {
            moveToNextQuestion();
        }

        progressCount += 10L;
        circularProgressBar.setProgressWithAnimation(progressCount, 500L);
    }

    private boolean isLastQuestion() {
        return circularProgressBar.getProgress() == circularProgressBar.getProgressMax();
    }

    private void navigateToResultScreen() {
        NavHostFragment.findNavController(QuestionControllerFragment.this)
                .navigate(R.id.action_QuestionControllerFragment_to_QuizResultFragment);
    }

    private void moveToNextQuestion() {
        try {
            questionNumber.setText(String.format(getString(R.string.question_number_string),
                    questiontableCursor.getPositionNumber()));
            questionText.setText(questiontableCursor.getContent());
            correctAnswer = questiontableCursor.getCorrectAnswer();
            questiontableCursor.moveToNext();
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
