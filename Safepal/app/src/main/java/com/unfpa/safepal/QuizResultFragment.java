package com.unfpa.safepal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.pixplicity.easyprefs.library.Prefs;

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
        Button button = view.findViewById(R.id.finish_retry_button);

        percentage.setText(String.format(getString(R.string.percentage_number_string),
                String.valueOf(Prefs.getInt(PERCENTAGE, 10))));

        button.setText(Prefs.getInt(PERCENTAGE, 10) > 60 ? getString(R.string.finish)
                : getString(R.string.retry_quiz));

        button.setOnClickListener(view1 -> {
            if (Prefs.getInt(PERCENTAGE, 10) < 80)
                NavHostFragment.findNavController(QuizResultFragment.this).popBackStack(R.id.QuestionControllerFragment, false);
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            else
                getActivity().finish();
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
