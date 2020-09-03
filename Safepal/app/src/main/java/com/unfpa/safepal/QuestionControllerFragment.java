package com.unfpa.safepal;

import android.app.Dialog;
import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.fragment.NavHostFragment;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.pixplicity.easyprefs.library.Prefs;
import com.unfpa.safepal.provider.answertable.AnswertableContentValues;
import com.unfpa.safepal.provider.articletable.ArticletableCursor;
import com.unfpa.safepal.provider.articletable.ArticletableSelection;
import com.unfpa.safepal.provider.questiontable.QuestiontableCursor;
import com.unfpa.safepal.provider.questiontable.QuestiontableSelection;
import com.unfpa.safepal.provider.quiztable.QuiztableCursor;
import com.unfpa.safepal.provider.quiztable.QuiztableSelection;

import timber.log.Timber;

import static com.unfpa.safepal.Utils.Constants.PERCENTAGE;
import static com.unfpa.safepal.provider.videotable.VideotableColumns.TITLE;

public class QuestionControllerFragment extends Fragment implements View.OnClickListener {

    Button yesButton, noButton, sometimesButton;
    TextView questionNumber, questionText, quizTitle, questionCountText;
    float progressCount = 10f;
    private CircularProgressBar circularProgressBar;
    private QuestiontableCursor questiontableCursor;
    private String correctAnswer = "YES";
    private int correctAnswerCount = 0;
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
        quizTitle = view.findViewById(R.id.quiz_title);
        questionCountText = view.findViewById(R.id.question_count_text);

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

            numberOfQuestions = questiontableCursor.getCount();
            circularProgressBar.setProgressMax(numberOfQuestions * 10f);

            //todo trim title when its very long
            quizTitle.setText(quizCursor.getTitle());

            questiontableCursor.moveToFirst();
            moveToNextQuestion(false);
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

        saveAnswer(userAnswer);
        displayNextQuestionDialog(view.getContext(), userAnswer.equals(correctAnswer));
    }

    private void saveAnswer(String userAnswer) {
        AnswertableContentValues values = new AnswertableContentValues();
        values.putQuiz(questiontableCursor.getQuiz());
        values.putPositionNumber(questiontableCursor.getPositionNumber());
        values.putContent(questiontableCursor.getContent());
        values.putCorrectAnswer(questiontableCursor.getCorrectAnswer());
        values.putYourAnswer(userAnswer);
        final Uri uri = values.insert(this.getActivity().getContentResolver());
        Timber.d("saved answer %s", uri);
    }

    private void navigateToResultScreen() {
        int percentage = (int) (correctAnswerCount / (float) numberOfQuestions * 100);
        Timber.d("percentage: %s", String.valueOf(percentage));
        Prefs.putInt(PERCENTAGE, percentage);
        NavHostFragment.findNavController(QuestionControllerFragment.this)
                .navigate(R.id.action_QuestionControllerFragment_to_QuizResultFragment);
    }

    private void moveToNextQuestion(boolean makeTransition) {
        try {
            if (makeTransition)
                questiontableCursor.moveToNext();
            questionText.setText(questiontableCursor.getContent());
            correctAnswer = questiontableCursor.getCorrectAnswer();
            questionNumber.setText(String.format(getString(R.string.question_number_string),
                    questiontableCursor.getPositionNumber()));
            questionCountText.setText(String.format(getString(R.string.question_count_string), questiontableCursor.getPositionNumber(), numberOfQuestions));
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void displayNextQuestionDialog(Context context, boolean answerResult) {
        final Dialog dialog = new Dialog(context, R.style.CustomAlertDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.next_question_dialog);

        TextView title = dialog.findViewById(R.id.title);
        ImageView resultImage = dialog.findViewById(R.id.result_image);
        Button ok = dialog.findViewById(R.id.next_question_button);

        title.setText(answerResult ? "CORRECT" : "INCORRECT");
        resultImage.setImageResource(answerResult ? R.drawable.ic_correct_48px : R.drawable.ic_wrong_48px);

        try {
            if (questiontableCursor.getPositionNumber() < numberOfQuestions)
                ok.setText(String.format(getString(R.string.question_number_string),
                        questiontableCursor.getPositionNumber() + 1));
            else
                ok.setText(getString(R.string.results));
        } catch (Exception e) {
            e.printStackTrace();
            ok.setText(getString(R.string.results));
        }

        ok.setOnClickListener(v -> {
            if (isLastQuestion())
                navigateToResultScreen();
            else
                moveToNextQuestion(true);
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
            questionCountText.setText(String.format(getString(R.string.question_count_string), questiontableCursor.getPositionNumber(), numberOfQuestions));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
