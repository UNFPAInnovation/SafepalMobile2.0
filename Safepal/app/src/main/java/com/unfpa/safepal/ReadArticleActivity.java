package com.unfpa.safepal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.unfpa.safepal.provider.articletable.ArticletableCursor;
import com.unfpa.safepal.provider.articletable.ArticletableSelection;
import com.unfpa.safepal.provider.videotable.VideotableCursor;
import com.unfpa.safepal.provider.videotable.VideotableSelection;

import static com.unfpa.safepal.provider.videotable.VideotableColumns.TITLE;

public class ReadArticleActivity extends AppCompatActivity {
    TextView title, category, questions, content;
    ImageView articleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_article);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = findViewById(R.id.title);
        category = findViewById(R.id.category);
        questions = findViewById(R.id.questions);
        content = findViewById(R.id.content);
        articleImage = findViewById(R.id.article_image);

        // Get the title passed to use from the VideoAdapter and query for the video
        ArticletableCursor articletableCursor = new ArticletableSelection().orderByCreatedAt(true)
                .title(getIntent().getStringExtra(TITLE)).query(getContentResolver());

        articletableCursor.moveToFirst();
        title.setText(articletableCursor.getTitle());
        category.setText(articletableCursor.getCategory());
        content.setText(articletableCursor.getContent());

        Picasso.get()
                .load(articletableCursor.getThumbnail())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(articleImage);
    }
}
