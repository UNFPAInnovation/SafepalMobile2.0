package com.unfpa.safepal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

public class FAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view);
        // add new lines to questions to allow the expanding capability to work for single lines
        String question = "Lorem ipsum dolor sit amet?\n\n\n\n\n\n\n\n\n";
        String answer = "Lorem ipsum dolor sit amet? consectetur adipiscing elit. " +
                "Ut volutpat interdum interdum. Nulla laoreet lacus diam, vitae " +
                "sodales sapien commodo faucibus. Vestibulum et feugiat enim. Donec " +
                "semper mi et euismod tempor. Sed sodales eleifend mi id varius. Nam " +
                "et ornare enim, sit amet gravida sapien. Quisque gravida et enim vel " +
                "volutpat. Vivamus egestas ut felis a blandit. Vivamus fringilla " +
                "dignissim mollis. Maecenas imperdiet interdum hendrerit. Aliquam" +
                " dictum hendrerit ultrices. Ut vitae vestibulum dolor. Donec auctor ante";
        expTv1.setText(question);
        expTv1.setOnExpandStateChangeListener((textView, isExpanded) -> {
            if (isExpanded)
                expTv1.setText(answer);
            else
                expTv1.setText(question);
        });
    }
}
