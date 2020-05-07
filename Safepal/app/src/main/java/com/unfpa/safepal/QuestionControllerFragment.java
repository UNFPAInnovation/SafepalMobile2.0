package com.unfpa.safepal;

import android.app.Dialog;
import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigator;
import androidx.navigation.fragment.NavHostFragment;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.pixplicity.easyprefs.library.Prefs;
import com.unfpa.safepal.provider.articletable.ArticletableCursor;
import com.unfpa.safepal.provider.articletable.ArticletableSelection;
import com.unfpa.safepal.provider.questiontable.QuestiontableCursor;
import com.unfpa.safepal.provider.questiontable.QuestiontableSelection;
import com.unfpa.safepal.provider.quiztable.QuiztableCursor;
import com.unfpa.safepal.provider.quiztable.QuiztableSelection;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

import static com.unfpa.safepal.Utils.Constants.PERCENTAGE;
import static com.unfpa.safepal.provider.videotable.VideotableColumns.TITLE;

public class QuestionControllerFragment extends Fragment implements View.OnClickListener {

    Button yesButton, noButton, sometimesButton;
    TextView questionNumber, questionText;
    float progressCount = 10f;
    private CircularProgressBar circularProgressBar;
    private QuestiontableCursor questiontableCursor;
    private String correctAnswer = "YES";
    private int correctAnswerCount = 0;
    private String dialogTitle = "CORRECT";
    private int numberOfQuestions = 0;

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
            numberOfQuestions = questiontableCursor.getCount();
            circularProgressBar.setProgressMax(numberOfQuestions * 10f);

            questiontableCursor.moveToFirst();
            moveToNextQuestion();
        } catch (Exception e) {
            Timber.e(e);
            Toast.makeText(view.getContext(), "Sorry, there is no quiz for this article", Toast.LENGTH_SHORT).show();
            getActivity().finish();
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

        if (userAnswer.equals(correctAnswer))
            correctAnswerCount++;

        displayNextQuestionDialog(view.getContext(), userAnswer.equals(correctAnswer), generateRandomMessage());
    }

    @NotNull
    private String generateRandomMessage() {
        // todo generate encouragement messages
        return "You should never be pressured!";
    }

    private void navigateToResultScreen() {
        int percentage = (int) (correctAnswerCount / (float) numberOfQuestions * 100);
        Timber.d("percentage: %s", String.valueOf(percentage));
        Prefs.putInt(PERCENTAGE, percentage);
        NavHostFragment.findNavController(QuestionControllerFragment.this)
                .navigate(R.id.action_QuestionControllerFragment_to_QuizResultFragment);
//        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
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

    private void displayNextQuestionDialog(Context context, boolean answerResult, String messageText) {
        final Dialog dialog = new Dialog(context, R.style.CustomAlertDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.next_question_dialog);

        TextView title = dialog.findViewById(R.id.title);
        TextView message = dialog.findViewById(R.id.message);
        ImageView resultImage = dialog.findViewById(R.id.result_image);
        Button ok = dialog.findViewById(R.id.next_question_button);

        title.setText(answerResult ? "CORRECT" : "INCORRECT");
        message.setText(messageText);
        resultImage.setImageResource(answerResult ? R.drawable.ic_correct_48px : R.drawable.ic_wrong_48px);

        try {
            ok.setText(String.format(getString(R.string.question_number_string),
                    questiontableCursor.getPositionNumber()));
        } catch (Exception e) {
            e.printStackTrace();
            ok.setText(getString(R.string.results));
        }

        ok.setOnClickListener(v -> {
            if (isLastQuestion()) {
                navigateToResultScreen();
            } else {
                moveToNextQuestion();
            }
            moveProgressBar();

            dialog.dismiss();
        });

        dialog.show();
    }

    private boolean isLastQuestion() {
        return circularProgressBar.getProgress() == circularProgressBar.getProgressMax();
    }

    private void moveProgressBar() {
        progressCount += 10L;
        circularProgressBar.setProgressWithAnimation(progressCount, 500L);
    }

    @Override
    public void onResume() {
        super.onResume();
        // reset state when user repeats the quiz
        try {
            questiontableCursor.moveToFirst();
            correctAnswer = questiontableCursor.getCorrectAnswer();
            correctAnswerCount = 0;
            numberOfQuestions = questiontableCursor.getCount();
            circularProgressBar.setProgressMax(numberOfQuestions * 10f);
            progressCount = 10f;
            circularProgressBar.setProgressWithAnimation(progressCount, 500L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
