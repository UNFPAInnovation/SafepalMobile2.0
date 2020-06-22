package com.unfpa.safepal;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.fragment.NavHostFragment;

import com.pixplicity.easyprefs.library.Prefs;
import com.unfpa.safepal.adapters.AnswerAdapter;
import com.unfpa.safepal.provider.answertable.AnswertableColumns;
import com.unfpa.safepal.provider.answertable.AnswertableCursor;
import com.unfpa.safepal.provider.answertable.AnswertableSelection;

import timber.log.Timber;

import static com.unfpa.safepal.Utils.Constants.PERCENTAGE;

public class QuizResultFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz_result, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView percentage = view.findViewById(R.id.percentage_text_view);
        TextView title = view.findViewById(R.id.title);
        Button button = view.findViewById(R.id.finish_retry_button);
        RecyclerView resultRecyclerView = view.findViewById(R.id.results_recycler);

        percentage.setText(String.format(getString(R.string.percentage_number_string),
                String.valueOf(Prefs.getInt(PERCENTAGE, 10))));

        button.setText(Prefs.getInt(PERCENTAGE, 10) < 80 ? getString(R.string.retry_quiz)
                : getString(R.string.finish));

        title.setText(Prefs.getInt(PERCENTAGE, 10) < 80 ? getString(R.string.failure_title)
                : getString(R.string.success_title));

        button.setOnClickListener(view1 -> {
            deletePreviousAnswers();
            if (Prefs.getInt(PERCENTAGE, 10) < 80)
                NavHostFragment.findNavController(QuizResultFragment.this).popBackStack(R.id.QuestionControllerFragment, false);
            else
                getActivity().finish();
        });

        AnswertableSelection selection = new AnswertableSelection();
        AnswertableCursor cursor = selection.orderByPositionNumber().query(getContext().getContentResolver());
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        resultRecyclerView.setAdapter(new AnswerAdapter(this.getActivity(), cursor));
    }

    private void deletePreviousAnswers() {
        try {
            getActivity().getContentResolver().delete(AnswertableColumns.CONTENT_URI, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        deletePreviousAnswers();
    }
}
